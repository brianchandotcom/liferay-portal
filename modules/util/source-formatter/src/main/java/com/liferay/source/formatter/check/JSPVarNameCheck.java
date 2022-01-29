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

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JSPVarNameCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		Matcher matcher = _varNamePattern.matcher(content);

		while (matcher.find()) {
			String varName = matcher.group(1);

			String expectedVarName = null;

			if (varName.matches(".*[a-z]Url")) {
				expectedVarName = StringUtil.replaceLast(varName, "Url", "URL");
			}
			else if (varName.matches(".*[a-z]Html")) {
				expectedVarName = StringUtil.replaceLast(
					varName, "Html", "HTML");
			}

			if (expectedVarName != null) {
				addMessage(
					fileName,
					StringBundler.concat(
						"Rename var '", varName, "' to '", expectedVarName,
						"'"),
					getLineNumber(content, matcher.start()));
			}
		}

		return content;
	}

	private static final Pattern _varNamePattern = Pattern.compile(
		"\\svar=\"(\\w+)\"");

}