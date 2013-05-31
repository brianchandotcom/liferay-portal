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
public class PropertiesDocIndexBuilder {

	public static void main(String[] args) {
		try {
			new PropertiesDocIndexBuilder(args);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public PropertiesDocIndexBuilder(String[] args) {
		Map<String, String> arguments = ArgumentsUtil.parseArguments(args);

		String lpVersion = GetterUtil.getString(arguments.get("lp.version"));
		String propertiesDir = GetterUtil.getString(
			arguments.get("properties.dir"));

		File propertiesDirectory = new File(propertiesDir);

		if (!propertiesDirectory.exists()) {
			System.out.println(
				propertiesDirectory.getPath() + " not found");

			return;
		}

		File[] files = propertiesDirectory.listFiles();

		List<String> htmlFileNames = new ArrayList<String>();

		for (File file : files) {
			String fileName = file.getName();

			if (fileName.endsWith(".properties.html")) {
				htmlFileNames.add(fileName.substring(0, fileName.length() - 5));
			}
		}

		if (htmlFileNames.isEmpty()) {
			System.out.println(
				"No *.properties.html files found in " +
					propertiesDirectory.getPath());

			return;
		}

		Map<String, Object> context = new HashMap<String, Object>();

		context.put("htmlFileNames", htmlFileNames);

		context.put("lpVersion", lpVersion);

		try {
			String indexHTMLFileName = propertiesDir + "/index.html";

			File indexHTMLFile = new File (indexHTMLFileName);

			indexHTMLFileName = StringUtil.replace(
				indexHTMLFileName, StringPool.BACK_SLASH, StringPool.SLASH);

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

			System.out.println("Writing " + indexHTMLFileName);

			writer.flush();
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

}