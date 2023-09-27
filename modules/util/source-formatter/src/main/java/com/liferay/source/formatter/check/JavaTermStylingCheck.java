/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.parser.JavaTerm;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaTermStylingCheck extends BaseJavaTermCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, JavaTerm javaTerm,
		String fileContent) {

		Matcher matcher = _lineBreakPattern.matcher(javaTerm.getContent());

		while (matcher.find()) {
			if (getLevel(matcher.group(2)) >= 0) {
				continue;
			}

			int pos = fileContent.indexOf(matcher.group()) + 1;

			addMessage(
				fileName,
				"Create a new var for '" + StringUtil.trim(matcher.group(1)) +
					"' for better readability",
				getLineNumber(fileContent, pos));
		}

		return javaTerm.getContent();
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CONSTRUCTOR, JAVA_METHOD};
	}

	private static final Pattern _lineBreakPattern = Pattern.compile(
		"\n(.*)\\(\n((.+,\n)*.*\\)) \\+\n");

}