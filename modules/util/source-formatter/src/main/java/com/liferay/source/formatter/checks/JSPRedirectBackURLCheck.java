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
public class JSPRedirectBackURLCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		content = _fixRedirectBackURL(content);

		return content;
	}

	private String _fixRedirectBackURL(String content) {
		Matcher matcher = _redirectBackURLPattern.matcher(content);

		String newContent = content;

		while (matcher.find()) {
			newContent = StringUtil.replaceFirst(
				newContent, matcher.group(),
				matcher.group(1) + "\n\n" + matcher.group(2), matcher.start());
		}

		return newContent;
	}

	private static final Pattern _redirectBackURLPattern = Pattern.compile(
		"(String redirect = ParamUtil\\.getString\\(request, \"redirect\".*" +
			"\\);)\n(String backURL = ParamUtil\\.getString\\(request, \"" +
				"backURL\", redirect\\);)");

}