/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.ReleaseInfo;
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
		Map<String, String> arguments = ArgumentsUtil.parseArguments(args);

		try {
			new PropertiesDocIndexBuilder(arguments);
		}
		catch (Exception e) {
			e.printStackTrace();

			if (ArgumentsUtil.shouldThrowExceptions(arguments)) {
				throw e;
			}
		}
	}

	public PropertiesDocIndexBuilder(Map<String, String> arguments) {
		String propertiesDirName = GetterUtil.getString(
			arguments.get("properties.dir"));

		File propertiesDir = new File(propertiesDirName);

		if (!propertiesDir.exists()) {
			System.out.println(propertiesDirName + " not found");

			return;
		}

		List<String> propertiesHTMLFileNames = new ArrayList<>();

		File[] files = propertiesDir.listFiles();

		for (File file : files) {
			String fileName = file.getName();

			if (fileName.endsWith(".properties.html")) {
				String propertiesHTMLFileName = fileName.substring(
					0, fileName.length() - 5);

				propertiesHTMLFileNames.add(propertiesHTMLFileName);
			}
		}

		if (propertiesHTMLFileNames.isEmpty()) {
			System.out.println(
				"No properties HTML files found in " + propertiesDirName);

			return;
		}

		Map<String, Object> context = new HashMap<>();

		context.put("propertiesHTMLFileNames", propertiesHTMLFileNames);
		context.put("releaseInfoVersion", ReleaseInfo.getVersion());

		try {
			String indexHTMLFileName = propertiesDirName + "/index.html";

			File indexHTMLFile = new File(indexHTMLFileName);

			System.out.println("Writing " + indexHTMLFile);

			Writer writer = new FileWriter(indexHTMLFile);

			try {
				FreeMarkerUtil.process(
					"com/liferay/portal/tools/propertiesdoc/dependencies" +
						"/index.ftl",
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

}