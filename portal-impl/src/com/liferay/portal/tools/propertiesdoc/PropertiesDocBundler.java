/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.tools.propertiesdoc;

import com.liferay.portal.freemarker.FreeMarkerUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ArgumentsUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jesse Rao
 * @author James Hinkey
 */
public class PropertiesDocBundler {

	public static void main(String[] args) {
		try {
			new PropertiesDocBundler (args);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public PropertiesDocBundler(String[] args) {
		Map<String, String> arguments = ArgumentsUtil.parseArguments(args);

		String lpVersion = GetterUtil.getString(arguments.get("lpVersion"));
		String propertiesDir = GetterUtil.getString(
			arguments.get("propertiesDir"));

		File[] files = new File(propertiesDir).listFiles();

		List<PropertiesHtmlFile> htmlFiles =
			new ArrayList<PropertiesHtmlFile>();

		for (File file : files) {
			if (file.getName().endsWith(".properties.html")) {
				htmlFiles.add(new PropertiesHtmlFile(file.getName()));
			}
		}

		Map<String, Object> context = new HashMap<String, Object>();

		context.put("htmlFiles", htmlFiles);

		context.put("lpVersion", lpVersion);

		try {
			File indexHTMLFile = new File (propertiesDir + "/index.html");

			Writer writer = new FileWriter(indexHTMLFile);

			try {
				FreeMarkerUtil.process(
					"com/liferay/portal/tools/propertiesdoc" +
						"/dependencies/index.ftl",
					context, writer);
			}
			catch (Exception e) {
				e.printStackTrace();
			}

			writer.flush();
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	/**
	 * @author James Hinkey
	 */
	public class PropertiesHtmlFile {

		public PropertiesHtmlFile(String fileName) {
			_fileName = fileName;

			_propertiesFileName = StringUtil.replaceLast(
				_fileName, ".html", StringPool.BLANK);
		}

		public String getFileName() {
			return _fileName;
		}

		public String getPropertiesFileName() {
			return _propertiesFileName;
		}

		private final String _fileName;
		private final String _propertiesFileName;

	}

}