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

package com.liferay.source.formatter.checks;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;

import java.net.URL;

import java.util.Objects;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class GradleLRFISDependencyVersionCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		if (!absolutePath.endsWith("/build.gradle")) {
			return content;
		}

		Properties gradleProperties = _getGradleProperties();

		String action = gradleProperties.getProperty(
			"liferay.workspace.target.platform.version.action");

		if (!Objects.equals(action, "add") &&
			!Objects.equals(action, "remove")) {

			return content;
		}

		Properties modulesProperties = _getModulesProperties(gradleProperties);

		if (modulesProperties.isEmpty()) {
			return content;
		}

		if (action.equals("add")) {
			return _addVersions(content, modulesProperties);
		}

		return _removeVersions(content, modulesProperties);
	}

	private String _addVersions(String content, Properties modulesProperties) {
		Matcher matcher = _addVersionPattern.matcher(content);

		while (matcher.find()) {
			String version = modulesProperties.getProperty(
				"bundle.version[" + matcher.group(1) + "]");

			if (version != null) {
				return StringUtil.insert(
					content, ", version: \"" + version + "\"",
					matcher.end() - 1);
			}
		}

		return content;
	}

	private Properties _getGradleProperties() throws IOException {
		if (_gradleProperties != null) {
			return _gradleProperties;
		}

		Properties gradleProperties = new Properties();

		File gradlePropertiesFile = new File(
			getBaseDirName() + "gradle.properties");

		if (gradlePropertiesFile.exists()) {
			gradleProperties.load(new FileInputStream(gradlePropertiesFile));
		}

		_gradleProperties = gradleProperties;

		return gradleProperties;
	}

	private Properties _getModulesProperties(Properties gradleProperties) {
		if (_modulesProperties != null) {
			return _modulesProperties;
		}

		Properties modulesProperties = new Properties();

		String modulesPropertiesURLString = gradleProperties.getProperty(
			"liferay.workspace.target.platform.version.url");

		if (modulesPropertiesURLString != null) {
			try {
				URL url = new URL(modulesPropertiesURLString);

				if (url != null) {
					modulesProperties.load(
						new StringReader(StringUtil.read(url.openStream())));
				}
			}
			catch (Exception exception) {
			}
		}

		_modulesProperties = modulesProperties;

		return modulesProperties;
	}

	private String _removeVersions(
		String content, Properties modulesProperties) {

		Matcher matcher = _removeVersionPattern.matcher(content);

		while (matcher.find()) {
			String version = modulesProperties.getProperty(
				"bundle.version[" + matcher.group(1) + "]");

			if (Objects.equals(version, matcher.group(3))) {
				return StringUtil.replaceFirst(
					content, matcher.group(2), StringPool.BLANK,
					matcher.start());
			}
		}

		return content;
	}

	private static final Pattern _addVersionPattern = Pattern.compile(
		", name: \"(com\\.liferay\\.[^\"]+)\"\n");
	private static final Pattern _removeVersionPattern = Pattern.compile(
		", name: \"(com\\.liferay\\.[^\"]+)\"(, version: \"([^\"]+)\")\n");

	private Properties _gradleProperties;
	private Properties _modulesProperties;

}