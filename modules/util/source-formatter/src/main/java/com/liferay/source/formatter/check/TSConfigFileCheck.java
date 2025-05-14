/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ToolsUtil;

import java.io.File;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alan Huang
 */
public class TSConfigFileCheck extends BaseFileCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (!absolutePath.contains("/modules/test/playwright/tests/") ||
			!fileName.endsWith("/config.ts")) {

			return content;
		}

		int x = absolutePath.lastIndexOf(StringPool.SLASH);

		String playwrightTestDirLocation = absolutePath.substring(0, x);

		File file = new File(playwrightTestDirLocation + "/test.properties");

		if (!file.exists()) {
			addMessage(
				fileName,
				"Missing test.properties, test.properties should be in the " +
					"same folder as config.ts");
		}

		x = playwrightTestDirLocation.lastIndexOf(StringPool.SLASH);

		String path = playwrightTestDirLocation.substring(0, x);

		file = new File(path + "/main");

		if (!file.exists()) {
			addMessage(
				fileName,
				StringBundler.concat(
					"Missing \"main\" folder in ", playwrightTestDirLocation,
					", ts.config should be placed in \"main\" folder or ",
					"another folder at the same level of \"main\""));

			return content;
		}

		return _checkConfig(fileName, content, playwrightTestDirLocation);
	}

	private String _checkConfig(
		String fileName, String content, String playwrightTestDirLocation) {

		int x = content.indexOf("export const config = ");

		if (x == -1) {
			addMessage(fileName, "Missing \"export const config\"");

			return content;
		}

		String config = _getConfig(content, x);

		if (config == null) {
			return content;
		}

		Matcher matcher = _namePattern.matcher(config);

		if (!matcher.find()) {
			addMessage(fileName, "Missing \"name\" in \"export const config\"");

			return content;
		}

		String[] parts = playwrightTestDirLocation.split("/");

		int length = parts.length;

		String projectName = parts[length - 2] + "." + parts[length - 1];

		String newConfig = StringUtil.replaceFirst(
			config, matcher.group(),
			StringBundler.concat(matcher.group(1), ": '", projectName, "'"));

		if (!config.equals(newConfig)) {
			return StringUtil.replaceFirst(content, config, newConfig, x);
		}

		return content;
	}

	private String _getConfig(String content, int x) {
		int y = x + 22;

		while (true) {
			y = content.indexOf("}", y + 1);

			if (y == -1) {
				return null;
			}

			if (ToolsUtil.isInsideQuotes(content, y)) {
				continue;
			}

			String s = content.substring(x, y + 1);

			if (getLevel(s, "{", "}") == 0) {
				return s;
			}
		}
	}

	private static final Pattern _namePattern = Pattern.compile(
		"([ \t]name)\\s*:\\s*'(.*)'");

}