/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.source.formatter.parser.JavaTerm;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alan Huang
 */
public class JavaExpandoBridgeAttributesCallOrderCheck
	extends BaseJavaTermCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, JavaTerm javaTerm,
		String fileContent) {

		if (!fileName.endsWith("LocalServiceImpl.java")) {
			return javaTerm.getContent();
		}

		String content = javaTerm.getContent();

		Matcher matcher1 = _setExpandoBridgeAttributesCallPattern.matcher(
			content);

		while (matcher1.find()) {
			String followingCode = content.substring(matcher1.end());

			String variableName = matcher1.group(1);

			Pattern pattern = Pattern.compile(
				"\\w+Persistence\\.update\\(\\s*" + variableName + "\\b");

			Matcher matcher2 = pattern.matcher(followingCode);

			if (!matcher2.find()) {
				continue;
			}

			pattern = Pattern.compile("\\b" + variableName + "\\.set\\w+\\(");

			Matcher matcher3 = pattern.matcher(followingCode);

			if (!matcher3.find() || (matcher3.start() >= matcher2.start())) {
				continue;
			}

			addMessage(
				fileName,
				"\"setExpandoBridgeAttributes\" should be the final setter " +
					"called on a model before the update",
				javaTerm.getLineNumber(matcher1.start()));
		}

		return javaTerm.getContent();
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_METHOD};
	}

	private static final Pattern _setExpandoBridgeAttributesCallPattern =
		Pattern.compile(
			"\\b(\\w+)\\.\\s*setExpandoBridgeAttributes\\(.*?\\);\n",
			Pattern.DOTALL);

}