/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Map;
import java.util.StringTokenizer;

import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author Manuel de la Peña
 */
public class TinyMCELanguageBuilder {
	
	public static void main(String[] args) {		
		_tinyMCELanguageBuilder = new TinyMCELanguageBuilder(args);
		
		processLanguages(_languages);	
	}
	
	private static void downloadLanguageFile(TinyMCELanguage tinyLang) 
		throws IOException {
		
		URL u = null;
	    InputStream is = null;
	    OutputStream out = null;
	 
	    String downloadURL = _baseURL + tinyLang._tinyID; 
	    String destFilePath = _destinationDirectory + "/" +
	    	tinyLang._tinyCode + ".js";
	    File dest = new File(destFilePath);
	    
	    try {
	    	u = new URL(downloadURL);	    	
	    	is = u.openStream();
	    	out = new FileOutputStream(dest);
	    	
	    	byte[] buf = new byte[1024];
	    	int len;
	
	    	while ((len = is.read(buf)) > 0) {
	    		out.write(buf, 0, len);
	    	}
	    } catch(IOException ioe) {
	    	System.out.println("An error has been produced downloading the " +
    			"file from [" + downloadURL + "]");
	    }
	    finally {
	    	is.close();
	    	out.close();
	    }
	}
	
	private static void processLanguages(String languages) {		
		StringTokenizer tokenizer = new StringTokenizer(languages,";");
		
		while (tokenizer.hasMoreElements()) {
			String lang = tokenizer.nextToken();
			
			int firstComma = lang.indexOf(",");
			int lastComma = lang.lastIndexOf(",");
			
			try {
				String liferayCode = lang.substring(1,firstComma);
				String tinyCode = lang.substring(firstComma + 1,lastComma);
				int tinyID = GetterUtil.getInteger(
					lang.substring(lastComma + 1));
				
				TinyMCELanguage tinyLang = 
					_tinyMCELanguageBuilder.new TinyMCELanguage(
							liferayCode, tinyCode, tinyID);
				
				downloadLanguageFile(tinyLang);
			}
			catch (IndexOutOfBoundsException ioobe) {
				ioobe.printStackTrace();
			}				
			catch (IOException e) {
				e.printStackTrace();
			}			
		}		
	}
	
	private TinyMCELanguageBuilder(String[] args) {
		Map<String, String> arguments = ArgumentsUtil.parseArguments(args);

		_baseURL = GetterUtil.getString(
			arguments.get("tinyMCE.download.baseurl"));
		_destinationDirectory = GetterUtil.getString(
				arguments.get("tinyMCE.dest.dir"));
		_languages = GetterUtil.getString(arguments.get("tinyMCE.languages"));		
	}
	
	private class TinyMCELanguage {
		
		public TinyMCELanguage(
			String liferayCode, String tinyCode, int tinyID) {			
			this._liferayCode = liferayCode;
			this._tinyCode = tinyCode;
			this._tinyID = tinyID;
		}
		
		private String _liferayCode;
		private String _tinyCode;
		private int _tinyID;
	}
	
	private static String _baseURL;
	private static String _destinationDirectory;
	private static String _languages;	
	private static TinyMCELanguageBuilder _tinyMCELanguageBuilder;
	
}