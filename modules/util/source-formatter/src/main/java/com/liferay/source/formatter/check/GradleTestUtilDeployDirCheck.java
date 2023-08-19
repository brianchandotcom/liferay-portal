/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.tools.ToolsUtil;

import java.io.File;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Qi Zhang
 */
public class GradleTestUtilDeployDirCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (!absolutePath.endsWith("-test-util/build.gradle")) {
			return content;
		}

		int pos = absolutePath.lastIndexOf(CharPool.SLASH);

		File lfrBuildPortalFile = new File(
			absolutePath.substring(0, pos + 1) + ".lfrbuild-portal");

		if (!_isDeployedToOSGiTestDir(content)) {
			if (lfrBuildPortalFile.exists()) {
				addMessage(
					fileName,
					"Missing deploy to 'osgi/test' when '.lfrbuild-portal' " +
						"exists");
			}
		}
		else {
			if (!lfrBuildPortalFile.exists()) {
				addMessage(
					fileName,
					"Do not deploy to 'osgi/test' when '.lfrbuild-portal' " +
						"does not exist");
			}
		}

		return content;
	}

	private boolean _isDeployedToOSGiTestDir(String content) {
		Matcher matcher = _liferayPattern.matcher(content);

		if (!matcher.find()) {
			return false;
		}

		int x = matcher.start();

		while (true) {
			x = content.indexOf("}", x + 1);

			if (x == -1) {
				return false;
			}

			String match = content.substring(matcher.end(2), x + 1);

			if (ToolsUtil.getLevel(match, "{", "}") != 0) {
				continue;
			}

			if (match.contains(
					"deployDir = file(\"${liferayHome}/osgi/test\")")) {

				return true;
			}
		}
	}

	private static final Pattern _liferayPattern = Pattern.compile(
		"(\n|\\A)(\t*)liferay \\{\n");

}