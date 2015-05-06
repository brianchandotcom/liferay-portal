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

package com.liferay.source.formatter;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.ArgumentsUtil;
import com.liferay.source.formatter.util.FileUtil;

import java.io.File;

import java.util.Map;

import org.apache.tools.ant.DirectoryScanner;

/**
 * @author Hugo Huijser
 */
public class XMLAttributesSorter {

	public static void main(String[] args) throws Exception {
		Map<String, String> arguments = ArgumentsUtil.parseArguments(args);

		try {
			new XMLAttributesSorter(args);
		}
		catch (Exception e) {
			ArgumentsUtil.processMainException(arguments, e);
		}
	}

	public XMLAttributesSorter(String[] args) throws Exception {
		Map<String, String> arguments = ArgumentsUtil.parseArguments(args);

		String fileName = arguments.get("sort.xml.file.name");

		if (Validator.isNull(fileName) || fileName.startsWith("$")) {
			System.out.println("Specify file name");

			return;
		}

		/*if (!fileName.endsWith(".xml")) {
			System.out.println("Specify file with .xml extension");

			return;
		}*/

		String basedir = "./";

		DirectoryScanner directoryScanner = new DirectoryScanner();

		directoryScanner.setBasedir(basedir);
		directoryScanner.setIncludes(new String[] {"**\\" + fileName});

		directoryScanner.scan();

		String[] fileNames = directoryScanner.getIncludedFiles();

		if (fileNames.length == 0) {
			System.out.println("No files found with name=" + fileName);

			return;
		}

		for (String fullFileName : fileNames) {
			File file = new File(basedir + fullFileName);

			fullFileName = StringUtil.replace(
				fullFileName, StringPool.BACK_SLASH, StringPool.SLASH);

			String content = FileUtil.read(file);

			String newContent = XMLSourceProcessor.sortAttributes(content);

			if (!content.equals(newContent)) {
				FileUtil.write(file, newContent);

				System.out.println(fullFileName);
			}
		}
	}

}