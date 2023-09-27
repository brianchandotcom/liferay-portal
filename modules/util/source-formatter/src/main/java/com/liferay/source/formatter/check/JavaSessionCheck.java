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
public class JavaSessionCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		Matcher matcher = _flushClearPattern.matcher(content);

		return matcher.replaceAll("$1\n$4");
	}

	private static final Pattern _flushClearPattern = Pattern.compile(
		"(\t((\\w*)[sS]ession)\\.flush\\(\\);\n)(\t+\\2\\.clear\\(\\);\n)");

}