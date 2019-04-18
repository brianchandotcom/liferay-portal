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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class BNDSettings {

	public BNDSettings(String fileName, String content) {
		_fileName = fileName;
		_content = content;
	}

	public String getContent() {
		return _content;
	}

	public List<String> getExportPackageNames() throws IOException {
		if (_exportPackageNames != null) {
			return _exportPackageNames;
		}

		List<String> exportPackageNames = new ArrayList<>();

		Matcher matcher = _exportPackagePattern.matcher(_content);

		if (!matcher.find()) {
			_exportPackageNames = exportPackageNames;

			return exportPackageNames;
		}

		for (String line : StringUtil.splitLines(matcher.group(3))) {
			line = StringUtil.trim(line);

			if (Validator.isNull(line) || line.equals("\\")) {
				continue;
			}

			line = StringUtil.removeSubstring(line, ",\\");

			if (line.indexOf(StringPool.SEMICOLON) != -1) {
				line = line.substring(0, line.indexOf(StringPool.SEMICOLON));
			}

			exportPackageNames.add(line);
		}

		matcher = _exportContentsPattern.matcher(_content);

		if (!matcher.find()) {
			_exportPackageNames = exportPackageNames;

			return exportPackageNames;
		}

		for (String line : StringUtil.splitLines(matcher.group(3))) {
			line = StringUtil.trim(line);

			if (Validator.isNull(line) || line.equals("\\")) {
				continue;
			}

			line = StringUtil.removeSubstring(line, ",\\");

			if (line.indexOf(StringPool.SEMICOLON) != -1) {
				line = line.substring(0, line.indexOf(StringPool.SEMICOLON));
			}

			exportPackageNames.add(line);
		}

		_exportPackageNames = exportPackageNames;

		return exportPackageNames;
	}

	public String getFileLocation() {
		int pos = _fileName.lastIndexOf(CharPool.SLASH);

		return _fileName.substring(0, pos + 1);
	}

	public String getFileName() {
		return _fileName;
	}

	public Properties getLanguageProperties() throws IOException {
		if (_languageProperties != null) {
			return _languageProperties;
		}

		if (_content.matches(
				"[\\s\\S]*Provide-Capability:[\\s\\S]*liferay\\.resource\\." +
					"bundle[\\s\\S]*") ||
			_content.matches(
				"[\\s\\S]*-liferay-aggregate-resource-bundles:[\\s\\S]*")) {

			// Return null, in order to skip checking for language keys for
			// modules that use LanguageExtender. No fix in place for this right
			// now.

			return null;
		}

		Properties languageProperties = new Properties();

		Matcher matcher = _contentDirPattern.matcher(_content);

		if (matcher.find()) {
			File file = new File(
				getFileLocation() + matcher.group(1) + "/Language.properties");

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

	private static final Pattern _contentDirPattern = Pattern.compile(
		"\\scontent=(.*?)(,\\\\|\n|$)");
	private static final Pattern _exportContentsPattern = Pattern.compile(
		"\n-exportcontents:(\\\\\n| )((.*?)(\n[^\t]|\\Z))",
		Pattern.DOTALL | Pattern.MULTILINE);
	private static final Pattern _exportPackagePattern = Pattern.compile(
		"\nExport-Package:(\\\\\n| )((.*?)(\n[^\t]|\\Z))",
		Pattern.DOTALL | Pattern.MULTILINE);
	private static final Pattern _releaseVersionPattern = Pattern.compile(
		"Bundle-Version: (.*)(\n|\\Z)");

	private final String _content;
	private List<String> _exportPackageNames;
	private final String _fileName;
	private Properties _languageProperties;
	private String _releaseVersion;

}