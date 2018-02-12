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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class PropertiesLiferayPluginPackageFileCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (fileName.endsWith("/liferay-plugin-package.properties")) {
			return _formatPluginPackageProperties(absolutePath, content);
		}

		return content;
	}

	private String _fixIncorrectLicenses(String absolutePath, String content) {
		if (!isModulesApp(absolutePath, false)) {
			return content;
		}

		Matcher matcher = _licensesPattern.matcher(content);

		if (!matcher.find()) {
			return content;
		}

		String licenses = matcher.group(1);

		String expectedLicenses = "LGPL";

		if (isModulesApp(absolutePath, true)) {
			expectedLicenses = "DXP";
		}

		if (licenses.equals(expectedLicenses)) {
			return content;
		}

		return StringUtil.replace(
			content, "licenses=" + licenses, "licenses=" + expectedLicenses,
			matcher.start());
	}

	private String _formatPluginPackageProperties(
		String absolutePath, String content) {

		content = StringUtil.replace(content, "\n\n", "\n");

		content = StringUtil.replace(
			content, StringPool.TAB, StringPool.FOUR_SPACES);

		Matcher matcher = _singleValueOnMultipleLinesPattern.matcher(content);

		if (matcher.find()) {
			content = StringUtil.replaceFirst(
				content, matcher.group(1), StringPool.BLANK, matcher.start());
		}

		return _fixIncorrectLicenses(absolutePath, content);
	}

	private final Pattern _licensesPattern = Pattern.compile(
		"\nlicenses=(\\w+)\n");
	private final Pattern _singleValueOnMultipleLinesPattern = Pattern.compile(
		"\n.*=(\\\\\n *).*(\n[^ ]|\\Z)");

}