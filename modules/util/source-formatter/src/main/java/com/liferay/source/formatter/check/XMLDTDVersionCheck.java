/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alan Huang
 */
public class XMLDTDVersionCheck extends BaseFileCheck {

	protected String checkDTDVersion(String content) throws IOException {
		Matcher matcher = _doctypePattern.matcher(content);

		if (!matcher.find()) {
			return content;
		}

		String lpVersion = getLPVersion();

		if (lpVersion == null) {
			return content;
		}

		String lpVersionDTD = getLPVersionDTD();

		if (lpVersionDTD == null) {
			return content;
		}

		return StringUtil.replaceFirst(
			content, matcher.group(),
			StringBundler.concat(
				matcher.group(1), lpVersion, matcher.group(3), lpVersionDTD,
				matcher.group(5)),
			matcher.start());
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		_readReleaseProperties();

		if ((_releaseProperties == null) || !fileName.endsWith(".xml")) {
			return content;
		}

		return checkDTDVersion(content);
	}

	protected String getLPVersion() {
		return _releaseProperties.getProperty("lp.version");
	}

	protected String getLPVersionDTD() {
		return _releaseProperties.getProperty("lp.version.dtd");
	}

	private void _readReleaseProperties() throws IOException {
		if (_releaseProperties != null) {
			return;
		}

		File releasePropertiesFile = new File(
			getPortalDir(), _RELEASE_PROPERTIES_FILE_NAME);

		if (!releasePropertiesFile.exists()) {
			return;
		}

		_releaseProperties = new Properties();

		_releaseProperties.load(
			Files.newInputStream(releasePropertiesFile.toPath()));
	}

	private static final String _RELEASE_PROPERTIES_FILE_NAME =
		"release.properties";

	private static final Pattern _doctypePattern = Pattern.compile(
		"(<!DOCTYPE .+ PUBLIC \"-//Liferay//DTD .+ )([0-9]+\\.[0-9]+" +
			"\\.[0-9]+)(//EN\"\\s*\"http://www.liferay.com/dtd/.+_)([0-9]" +
				"+_[0-9]+_[0-9]+)(\\.dtd\">)");

	private Properties _releaseProperties;

}