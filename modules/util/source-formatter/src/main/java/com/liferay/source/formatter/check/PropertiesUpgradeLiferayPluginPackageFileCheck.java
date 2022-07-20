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

package com.liferay.source.formatter.check;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Kevin Lee
 */
public class PropertiesUpgradeLiferayPluginPackageFileCheck
	extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (!fileName.endsWith("/liferay-plugin-package.properties")) {
			return content;
		}

		String upgradeToVersion = getAttributeValue(
			SourceFormatterUtil.UPGRADE_TO_VERSION, absolutePath);

		if (upgradeToVersion == null) {
			return content;
		}

		return _formatPluginPackageProperties(content);
	}

	private String _addNoEEProperty(String content) {
		Matcher matcher = _noEEPattern.matcher(content);

		if (matcher.find()) {
			String value = matcher.group(1);

			if (value.equals("false")) {
				content = StringUtil.replace(
					content, value, "true", matcher.start(1));
			}
		}
		else {
			content += "\n\n-noee: true";
		}

		return content;
	}

	private String _formatPluginPackageProperties(String content) {
		content = _removePortalDependencyJarsProperty(content);
		content = _addNoEEProperty(content);

		return content;
	}

	private String _removePortalDependencyJarsProperty(String content) {
		Matcher matcher = _portalDependencyJarsPattern.matcher(content);

		if (!matcher.find()) {
			return content;
		}

		return StringUtil.removeSubstring(content, matcher.group(0));
	}

	private static final Pattern _noEEPattern = Pattern.compile(
		"-noee:\\s*(true|false)\n*");
	private static final Pattern _portalDependencyJarsPattern = Pattern.compile(
		"portal-dependency-jars=\\\\(\n[\t| ]+.+){2,}\n*");

}