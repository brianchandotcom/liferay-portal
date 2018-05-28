/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.source.formatter.checks;

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

		content = _formatTypes(fileName, content);

		return content;
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