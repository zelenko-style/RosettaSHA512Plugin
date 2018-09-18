package com.exlibris.dps.repository.plugin.fixity;

import com.exlibris.core.sdk.strings.StringUtils;
import com.exlibris.dps.repository.plugin.CustomFixityPlugin;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.xml.bind.DatatypeConverter;


public class CustomFixitySHA512Plugin
  implements CustomFixityPlugin
{
  private static final String PLUGIN_VERSION_INIT_PARAM = "PLUGIN_VERSION_INIT_PARAM";
  private String pluginVersion = null;
  private boolean result = true;
  
  private List<String> errors = null;
  

  public CustomFixitySHA512Plugin() {}
  
  public List<String> getErrors()
  {
    return errors;
  }

  public String getFixity(String filePath, String oldFixity)
    throws Exception
  {
    File file = new File(filePath);
    
    String newFixity = toHex(CustomFixitySHA512Plugin.HashFunction.access$2(CustomFixitySHA512Plugin.HashFunction.SHA512, file));
    
    if ((oldFixity != null) && (!newFixity.equals(oldFixity))) {
      errors = new ArrayList();
      errors.add("Fixity mismatch. Old fixity was " + oldFixity + ", new fixity is " + newFixity);
    }
    
    return newFixity;
  }
  
  public String getAlgorithm()
  {
    return "SHA-512";
  }
  

  public String getAgent()
  {
    return "Custom fixity SHA-512, Plugin Version " + pluginVersion;
  }
  
  public void initParams(Map<String, String> initParams) {
    pluginVersion = ((String)initParams.get("PLUGIN_VERSION_INIT_PARAM"));
    if (!StringUtils.isEmptyString((String)initParams.get("fixityScanResult"))) {
      result = Boolean.parseBoolean(((String)initParams.get("fixityScanResult")).trim());
    }
  }

  private static String toHex(byte[] bytes)
  {
    return DatatypeConverter.printHexBinary(bytes);
  }
}