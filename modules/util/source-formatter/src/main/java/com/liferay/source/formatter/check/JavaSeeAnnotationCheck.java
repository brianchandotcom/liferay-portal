/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaSeeAnnotationCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		Matcher matcher = _seeAnnotationPattern.matcher(content);

		while (matcher.find()) {
			addMessage(
				fileName, "Do not use @see with another annotation",
				getLineNumber(content, matcher.start() + 1));
		}

		return content;
	}

	private static final Pattern _seeAnnotationPattern = Pattern.compile(
		"[\n\t] ?\\* @see.*@");

}