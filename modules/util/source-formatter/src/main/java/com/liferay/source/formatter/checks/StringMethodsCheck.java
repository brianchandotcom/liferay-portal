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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class StringMethodsCheck extends BaseFileCheck {

	@Override
	public boolean isPortalCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (isExcludedPath(RUN_OUTSIDE_PORTAL_EXCLUDES, absolutePath)) {
			return content;
		}

		_checkInefficientStringMethods(
			fileName, content, "\\.toLowerCase\\(\\)", "toLowerCase");
		_checkInefficientStringMethods(
			fileName, content, "\\.toUpperCase\\(\\)", "toUpperCase");
		_checkInefficientStringMethods(
			fileName, content, "(?<!StringUtil)\\.equalsIgnoreCase\\(",
			"equalsIgnoreCase");

		return content;
	}

	protected boolean isJavaSource(String content, int pos) {
		return true;
	}

	private void _checkInefficientStringMethods(
		String fileName, String content, String regex, String methodName) {

		Pattern pattern = Pattern.compile(regex);

		Matcher matcher = pattern.matcher(content);

		while (matcher.find()) {
			if (isJavaSource(content, matcher.start())) {
				addMessage(
					fileName, "Use StringUtil." + methodName,
					getLineCount(content, matcher.start()));
			}
		}
	}

}