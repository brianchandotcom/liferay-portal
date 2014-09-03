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

package com.liferay.portal.tools.sourceformatter;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.FileImpl;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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
			_fileUtil.write(_propertiesFile, newPropertiesContent);
		}
	}

	public void init() throws IOException {
		long l1 = System.currentTimeMillis();

		if (!_useProperties) {
			System.out.println("SourceFormatterHelper.init return");
			return;
		}

		long l2 = System.currentTimeMillis();

		File basedirFile = new File("./");

		long l3 = System.currentTimeMillis();

		String basedirAbsolutePath = StringUtil.replace(
			basedirFile.getAbsolutePath(), new String[] {".", ":", "/", "\\"},
			new String[] {"_", "_", "_", "_"});

		long l4 = System.currentTimeMillis();

		String propertiesFileName =
			System.getProperty("java.io.tmpdir") + "/SourceFormatter." +
				basedirAbsolutePath;

		long l5 = System.currentTimeMillis();

		_propertiesFile = new File(propertiesFileName);

		long l6 = System.currentTimeMillis();

		if (_propertiesFile.exists()) {
			_propertiesContent = _fileUtil.read(_propertiesFile);

			PropertiesUtil.load(_properties, _propertiesContent);
		}

		long l7 = System.currentTimeMillis();

		System.out.println("SourceFormatterHelper.init_1: " + (l2 - l1));
		System.out.println("SourceFormatterHelper.init_2: " + (l3 - l2));
		System.out.println("SourceFormatterHelper.init_3: " + (l4 - l3));
		System.out.println("SourceFormatterHelper.init_4: " + (l5 - l4));
		System.out.println("SourceFormatterHelper.init_5: " + (l6 - l5));
		System.out.println("SourceFormatterHelper.init_6: " + (l7 - l6));
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

	public List<String> scanForFiles(DirectoryScanner directoryScanner) {
		directoryScanner.scan();

		String[] fileNamesArray = directoryScanner.getIncludedFiles();

		if (!_useProperties) {
			return ListUtil.toList(fileNamesArray);
		}

		List<String> fileNames = new ArrayList<String>(fileNamesArray.length);

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

	private static FileImpl _fileUtil = FileImpl.getInstance();

	private Properties _properties = new Properties();
	private String _propertiesContent = StringPool.BLANK;
	private File _propertiesFile;
	private boolean _useProperties;

}