/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ToolsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Peter Shin
 * @author Hugo Huijser
 */
public class GradleSourceUtil {

	public static String getConfiguration(String dependency) {
		int pos = dependency.indexOf(StringPool.SPACE);

		if (pos != -1) {
			return StringUtil.trim(dependency.substring(0, pos));
		}

		return StringUtil.trim(dependency);
	}

	public static List<String> getDependenciesBlocks(String content) {
		List<String> dependenciesBlocks = new ArrayList<>();

		Matcher matcher = _dependenciesPattern.matcher(content);

		while (matcher.find()) {
			int y = matcher.start();

			while (true) {
				y = content.indexOf("}", y + 1);

				if (y == -1) {
					return dependenciesBlocks;
				}

				String dependencies = content.substring(
					matcher.start(2), y + 1);

				int level = ToolsUtil.getLevel(dependencies, "{", "}");

				if (level == 0) {
					if (!dependencies.contains("}\n")) {
						dependenciesBlocks.add(dependencies);
					}

					break;
				}
			}
		}

		return dependenciesBlocks;
	}

	public static boolean isSpringBootExecutable(String content) {
		Matcher matcher = _springBootPattern.matcher(content);

		if (!matcher.find()) {
			return false;
		}

		int x = matcher.start();

		while (true) {
			x = content.indexOf("}", x + 1);

			if (x == -1) {
				return false;
			}

			String s = content.substring(matcher.start(), x + 1);

			if (ToolsUtil.getLevel(s, "{", "}") != 0) {
				continue;
			}

			if (s.matches("(?is).*executable\\s*=\\s*true.*") &&
				s.matches("(?is).*mainClass\\s*=.*")) {

				return true;
			}

			return false;
		}
	}

	private static final Pattern _dependenciesPattern = Pattern.compile(
		"(\n|\\A)(\t*)dependencies \\{\n");
	private static final Pattern _springBootPattern = Pattern.compile(
		"\\s*springBoot\\s*\\{", Pattern.CASE_INSENSITIVE);

}