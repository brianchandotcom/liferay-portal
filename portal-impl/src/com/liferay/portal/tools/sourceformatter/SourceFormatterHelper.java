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
			_propertiesContent = _fileUtil.read(_propertiesFile);

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

	public List<String> scanForFiles(DirectoryScanner directoryScanner) {
		return scanForFiles(directoryScanner, false);
	}

	public List<String> scanForFiles(
		DirectoryScanner directoryScanner, boolean log) {

		long l1 = System.currentTimeMillis();

		directoryScanner.scan();

		long l2 = System.currentTimeMillis();

		String[] fileNamesArray = directoryScanner.getIncludedFiles();

		long l3 = System.currentTimeMillis();

		if (log) {
			System.out.println("l1: " + (l2 - l1));
			System.out.println("l2: " + (l3 - l2));
		}

		if (!_useProperties) {
			return ListUtil.toList(fileNamesArray);
		}

		long l4 = System.currentTimeMillis();

		List<String> fileNames = new ArrayList<String>(fileNamesArray.length);

		long l5 = System.currentTimeMillis();

		for (String fileName : fileNamesArray) {
			long l7 = System.currentTimeMillis();

			File file = new File(fileName);

			long l8 = System.currentTimeMillis();

			String encodedFileName = StringUtil.replace(
				fileName, StringPool.BACK_SLASH, StringPool.SLASH);

			long l9 = System.currentTimeMillis();

			long timestamp = GetterUtil.getLong(
				_properties.getProperty(encodedFileName));

			long l10 = System.currentTimeMillis();

			if (timestamp < file.lastModified()) {
				fileNames.add(fileName);

				_properties.setProperty(
					encodedFileName, String.valueOf(file.lastModified()));
			}

			long l11 = System.currentTimeMillis();

			if (log) {
				System.out.println("l7: " + (l8 - l7));
				System.out.println("l8: " + (l9 - l8));
				System.out.println("l9: " + (l10 - l9));
				System.out.println("l10: " + (l11 - l10));
			}
		}

		long l6 = System.currentTimeMillis();

		if (log) {
			System.out.println("l3: " + (l4 - l3));
			System.out.println("l4: " + (l5 - l4));
			System.out.println("l5: " + (l6 - l5));
		}

		return fileNames;
	}

	private static FileImpl _fileUtil = FileImpl.getInstance();

	private Properties _properties = new Properties();
	private String _propertiesContent = StringPool.BLANK;
	private File _propertiesFile;
	private boolean _useProperties;

}