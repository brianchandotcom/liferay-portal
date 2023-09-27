/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JSStylingCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (content.contains("debugger.")) {
			addMessage(fileName, "Do not use debugger");
		}

		return _formatMultipleVarsOnSingleLine(content);
	}

	private String _formatMultipleVarsOnSingleLine(String content) {
		while (true) {
			Matcher matcher = _multipleVarsOnSingleLinePattern.matcher(content);

			if (!matcher.find()) {
				break;
			}

			String match = matcher.group();

			int pos = match.indexOf("var ");

			content = StringUtil.replace(
				content, match,
				StringBundler.concat(
					match.substring(0, match.length() - 2), ";\n",
					match.substring(0, pos + 4)));
		}

		return content;
	}

	private static final Pattern _multipleVarsOnSingleLinePattern =
		Pattern.compile("\t+var \\w+\\, ");

}