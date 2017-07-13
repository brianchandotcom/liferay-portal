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

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.source.formatter.util.FileUtil;

import java.io.File;
import java.io.FileInputStream;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.maven.artifact.versioning.ComparableVersion;

/**
 * @author Hugo Huijser
 */
public class BNDSettings {

	public BNDSettings(String fileLocation, String content) {
		_fileLocation = fileLocation;
		_content = content;
	}

	public String getBundleSymbolicName() throws Exception {
		if (_bundleSymbolicName != null) {
			return _bundleSymbolicName;
		}

		Matcher matcher = _bundleSymbolicNamePattern.matcher(_content);

		if (!matcher.find()) {
			return null;
		}

		String bundleSymbolicName = matcher.group(1);

		if (!bundleSymbolicName.matches("\\$\\{.*\\}")) {
			_bundleSymbolicName = bundleSymbolicName;

			return _bundleSymbolicName;
		}

		File buildFile = new File(_fileLocation + "build.xml");

		if (!buildFile.exists()) {
			_bundleSymbolicName = StringPool.BLANK;

			return _bundleSymbolicName;
		}

		String propertyName = bundleSymbolicName.substring(
			2, bundleSymbolicName.length() - 1);

		Pattern pattern = Pattern.compile(
			"name=\"" + propertyName + "\" value=\"(.*?)[;\"]");

		matcher = pattern.matcher(FileUtil.read(buildFile));

		if (matcher.find()) {
			_bundleSymbolicName = matcher.group(1);
		}
		else {
			_bundleSymbolicName = StringPool.BLANK;
		}

		return _bundleSymbolicName;
	}

	public String getContent() {
		return _content;
	}

	public String getFileLocation() {
		return _fileLocation;
	}

	public Properties getLanguageProperties() throws Exception {
		if (_languageProperties != null) {
			return _languageProperties;
		}

		if (_content.matches(
				"[\\s\\S]*Provide-Capability:[\\s\\S]*liferay\\.resource\\." +
					"bundle[\\s\\S]*")) {

			// Return null, in order to skip checking for language keys for
			// modules that use LanguageExtender. No fix in place for this right
			// now.

			return null;
		}

		Properties languageProperties = new Properties();

		Matcher matcher = _contentDirPattern.matcher(_content);

		if (matcher.find()) {
			File file = new File(
				_fileLocation + matcher.group(1) + "/Language.properties");

			if (file.exists()) {
				languageProperties.load(new FileInputStream(file));
			}
		}

		_languageProperties = languageProperties;

		return _languageProperties;
	}

	public ComparableVersion getReleaseComparableVersion() {
		if (_releaseComparableVersion != null) {
			return _releaseComparableVersion;
		}

		Matcher matcher = _releaseVersionPattern.matcher(_content);

		if (!matcher.find()) {
			return null;
		}

		String releaseVersion = matcher.group(1);

		int pos = releaseVersion.lastIndexOf(CharPool.PERIOD);

		releaseVersion = releaseVersion.substring(0, pos) + ".0";

		_releaseComparableVersion = new ComparableVersion(releaseVersion);

		return _releaseComparableVersion;
	}

	private String _bundleSymbolicName;
	private final Pattern _bundleSymbolicNamePattern = Pattern.compile(
		"Bundle-SymbolicName: (.*)(\n|\\Z)");
	private final String _content;
	private final Pattern _contentDirPattern = Pattern.compile(
		"\\scontent=(.*?)(,\\\\|\n|$)");
	private final String _fileLocation;
	private Properties _languageProperties;
	private ComparableVersion _releaseComparableVersion;
	private final Pattern _releaseVersionPattern = Pattern.compile(
		"Bundle-Version: (.*)(\n|\\Z)");

}