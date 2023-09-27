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
public class JavaProcessCallableCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		Matcher matcher = _processCallablePattern.matcher(content);

		if (!matcher.find() ||
			content.contains("private static final long serialVersionUID")) {

			return content;
		}

		addMessage(
			fileName,
			"Assign ProcessCallable implementation a serialVersionUID");

		return content;
	}

	private static final Pattern _processCallablePattern = Pattern.compile(
		"implements ProcessCallable\\b");

}