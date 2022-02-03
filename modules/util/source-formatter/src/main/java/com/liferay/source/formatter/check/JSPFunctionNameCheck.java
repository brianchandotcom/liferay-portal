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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JSPFunctionNameCheck extends BaseFileCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		Matcher matcher = _functionPattern.matcher(content);

		while (matcher.find()) {
			String functionName = matcher.group(2);

			if (!functionName.matches(_FUNCTION_NAME_REGEX)) {
				addMessage(
					fileName,
					StringBundler.concat(
						"Function '", functionName, "' much match pattern '",
						_FUNCTION_NAME_REGEX, "'"),
					getLineNumber(content, matcher.start(2)));
			}
		}

		return content;
	}

	private static final String _FUNCTION_NAME_REGEX =
		"^[a-z0-9][_a-zA-Z0-9]*$";

	private static final Pattern _functionPattern = Pattern.compile(
		"[\n\t]function (<.*>)?(\\w+)\\(");

}