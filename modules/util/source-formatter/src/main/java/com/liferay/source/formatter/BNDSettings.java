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

import java.io.File;
import java.io.FileInputStream;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class BNDSettings {

	public BNDSettings(String fileLocation, String content) {
		_fileLocation = fileLocation;
		_content = content;
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
				"[\\s\\S]*Provide-Capability:.*liferay\\.resource\\.bundle" +
					"[\\s\\S]*")) {

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

	public String getReleaseVersion() {
		if (_releaseVersion != null) {
			return _releaseVersion;
		}

		Matcher matcher = _releaseVersionPattern.matcher(_content);

		if (!matcher.find()) {
			return null;
		}

		_releaseVersion = matcher.group(1);

		return _releaseVersion;
	}

	public boolean isInheritRequired() {
		return _inheritRequired;
	}

	public void setInheritRequired(boolean inheritRequired) {
		_inheritRequired = inheritRequired;
	}

	private final String _content;
	private final Pattern _contentDirPattern = Pattern.compile(
		"\\scontent=(.*?)(,\\\\|\n|$)");
	private final String _fileLocation;
	private boolean _inheritRequired;
	private Properties _languageProperties;
	private String _releaseVersion;
	private final Pattern _releaseVersionPattern = Pattern.compile(
		"Bundle-Version: (.*)(\n|\\Z)");

}