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
public class TLDTypeCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		return _formatTypes(fileName, content);
	}

	private String _formatTypes(String fileName, String content) {
		Matcher matcher = _typePattern.matcher(content);

		while (matcher.find()) {
			String typeName = matcher.group(1);

			if (typeName.matches("[A-Z]\\w*")) {
				addMessage(
					fileName, "Use fully qualified class name, see LPS-61841",
					getLineNumber(content, matcher.start(1)));
			}
			else if (typeName.equals("java.lang.String")) {
				return StringUtil.replaceFirst(content, matcher.group(), "\n");
			}
		}

		return content;
	}

	private static final Pattern _typePattern = Pattern.compile(
		"\n\t*<type>(.*)</type>\n");

}