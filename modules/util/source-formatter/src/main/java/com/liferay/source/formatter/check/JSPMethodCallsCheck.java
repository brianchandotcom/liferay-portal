/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alan Huang
 */
public class JSPMethodCallsCheck extends BaseStylingCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		Matcher matcher = _incorrectMethodCallPattern.matcher(content);

		while (matcher.find()) {
			addMessage(
				fileName,
				"Use type 'LiferayPortletResponse' to call 'getNamespace()'",
				getLineNumber(content, matcher.start()));
		}

		return content;
	}

	private static final Pattern _incorrectMethodCallPattern = Pattern.compile(
		"(?<!\\bliferayPortlet)Response\\.getNamespace\\(\\)");

}