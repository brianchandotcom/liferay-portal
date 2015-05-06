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

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.tools.ant.DirectoryScanner;

/**
 * @author Igor Spasic
 * @author Brian Wing Shun Chan
 */
public class SourceFormatterHelper {

	public SourceFormatterHelper(boolean useProperties) {
		_useProperties = useProperties;
	}

	public void close() throws IOException {
		if (!_useProperties) {
			return;
		}

		String newPropertiesContent = PropertiesUtil.toString(_properties);

		if (!_propertiesContent.equals(newPropertiesContent)) {
			FileUtils.writeStringToFile(
				_propertiesFile, newPropertiesContent, StringPool.UTF8);
		}
	}

	public void init() throws IOException {
		if (!_useProperties) {
			return;
		}

		File basedirFile = new File("./");

		String basedirAbsolutePath = StringUtil.replace(
			basedirFile.getAbsolutePath(), new String[] {".", ":", "/", "\\"},
			new String[] {"_", "_", "_", "_"});

		String propertiesFileName =
			System.getProperty("java.io.tmpdir") + "/SourceFormatter." +
				basedirAbsolutePath;

		_propertiesFile = new File(propertiesFileName);

		if (_propertiesFile.exists()) {
			_propertiesContent = readFileToString(_propertiesFile);

			PropertiesUtil.load(_properties, _propertiesContent);
		}
	}

	public void printError(String fileName, File file) {
		printError(fileName, file.toString());
	}

	public void printError(String fileName, String message) {
		if (_useProperties) {
			String encodedFileName = StringUtil.replace(
				fileName, StringPool.BACK_SLASH, StringPool.SLASH);

			_properties.remove(encodedFileName);
		}

		System.out.println(message);
	}

	public String readFileToString(File file) throws IOException {
		String s = FileUtils.readFileToString(file, StringPool.UTF8);

		return StringUtil.replace(
			s, StringPool.RETURN_NEW_LINE, StringPool.NEW_LINE);
	}

	public List<String> scanForFiles(DirectoryScanner directoryScanner) {
		directoryScanner.scan();

		String[] fileNamesArray = directoryScanner.getIncludedFiles();

		if (!_useProperties) {
			return ListUtil.toList(fileNamesArray);
		}

		List<String> fileNames = new ArrayList<>(fileNamesArray.length);

		for (String fileName : fileNamesArray) {
			File file = new File(fileName);

			String encodedFileName = StringUtil.replace(
				fileName, StringPool.BACK_SLASH, StringPool.SLASH);

			long timestamp = GetterUtil.getLong(
				_properties.getProperty(encodedFileName));

			if (timestamp < file.lastModified()) {
				fileNames.add(fileName);

				_properties.setProperty(
					encodedFileName, String.valueOf(file.lastModified()));
			}
		}

		return fileNames;
	}

	private final Properties _properties = new Properties();
	private String _propertiesContent = StringPool.BLANK;
	private File _propertiesFile;
	private final boolean _useProperties;

}