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