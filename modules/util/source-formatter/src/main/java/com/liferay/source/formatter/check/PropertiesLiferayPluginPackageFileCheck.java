/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;
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
		String fileName, String absolutePath, String content) {

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
			content, CharPool.TAB, StringPool.FOUR_SPACES);

		Matcher matcher = _singleValueOnMultipleLinesPattern.matcher(content);

		if (matcher.find()) {
			content = StringUtil.replaceFirst(
				content, matcher.group(1), StringPool.BLANK, matcher.start());
		}

		return _fixIncorrectLicenses(absolutePath, content);
	}

	private static final Pattern _licensesPattern = Pattern.compile(
		"\nlicenses=(\\w+)\n");
	private static final Pattern _singleValueOnMultipleLinesPattern =
		Pattern.compile("\n.*=(\\\\\n *).*(\n[^ ]|\\Z)");

}