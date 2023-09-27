/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.portal.kernel.util.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class PrincipalExceptionCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		return _formatPrincipalException(content);
	}

	private String _formatPrincipalException(String content) {
		Matcher matcher = _principalExceptionPattern.matcher(content);

		while (matcher.find()) {
			String match = matcher.group();

			String replacement = StringUtil.replace(
				match, "class.getName", "getNestedClasses");

			content = StringUtil.replace(content, match, replacement);
		}

		return content;
	}

	private static final Pattern _principalExceptionPattern = Pattern.compile(
		"SessionErrors\\.contains\\(\n?\t*(renderR|r)equest, " +
			"PrincipalException\\.class\\.getName\\(\\)");

}