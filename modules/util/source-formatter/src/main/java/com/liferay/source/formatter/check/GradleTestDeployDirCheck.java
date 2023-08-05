/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.parser.ParseException;

import java.io.File;
import java.io.IOException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.DocumentException;

/**
 * @author Qi Zhang
 */
public class GradleTestDeployDirCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws DocumentException, IOException, ParseException {

		if (!absolutePath.endsWith("-test-util/build.gradle")) {
			return content;
		}

		int pos = absolutePath.lastIndexOf(CharPool.SLASH);

		File lfrBuildPortalFile = new File(
			absolutePath.substring(0, pos + 1) + ".lfrbuild-portal");

		if (!_isDeployedInOSGITestDir(content)) {
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

	private boolean _isDeployedInOSGITestDir(String content) {
		Matcher matcher = _liferayPattern.matcher(content);

		if (matcher.find()) {
			int x = matcher.start();

			while (true) {
				x = content.indexOf("}", x + 1);

				if (x == -1) {
					return false;
				}

				String codeBlock = content.substring(matcher.start(2), x + 1);

				if (ToolsUtil.getLevel(codeBlock, "{", "}") != 0) {
					continue;
				}

				if (codeBlock.contains(
						"deployDir = file(\"${liferayHome}/osgi/test\")")) {

					return true;
				}
			}
		}

		return false;
	}

	private static final Pattern _liferayPattern = Pattern.compile(
		"(\n|\\A)(\t*)liferay \\{\n");

}