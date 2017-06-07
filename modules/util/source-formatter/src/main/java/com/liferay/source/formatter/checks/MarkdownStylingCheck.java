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
public class MarkdownStylingCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		return _formatHeaders(content);
	}

	private String _formatHeaders(String content) {
		Matcher matcher = _incorrectHeaderNotationPattern.matcher(content);

		return matcher.replaceAll("$1$2$4");
	}

	private final Pattern _incorrectHeaderNotationPattern = Pattern.compile(
		"(\\A|\n)(#+[^#\n]+)(#+)(\n)");

}