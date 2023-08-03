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

		File deployMarkerFile = new File(
			absolutePath.substring(0, pos + 1) + ".lfrbuild-portal");

		String liferayBlock = _getLiferayBlock(content);

		if ((liferayBlock == null) ||
			!liferayBlock.contains(
				"deployDir = file(\"${liferayHome}/osgi/test\")")) {

			if (deployMarkerFile.exists()) {
				addMessage(fileName, "Missing 'deployDir'");
			}
		}
		else {
			if (!deployMarkerFile.exists()) {
				addMessage(
					fileName,
					"Do not have 'deployDir' in liferay when not have a " +
						"deploy marker file(.lfrbuild-portal)");
			}
		}

		return content;
	}

	private String _getLiferayBlock(String content) {
		Matcher matcher = _liferayPattern.matcher(content);

		if (matcher.find()) {
			int y = matcher.start();

			while (true) {
				y = content.indexOf("}", y + 1);

				if (y == -1) {
					return null;
				}

				String codeBlock = content.substring(matcher.start(2), y + 1);

				int level = ToolsUtil.getLevel(codeBlock, "{", "}");

				if (level == 0) {
					return codeBlock;
				}
			}
		}

		return null;
	}

	private static final Pattern _liferayPattern = Pattern.compile(
		"(\n|\\A)(\t*)liferay \\{\n");

}