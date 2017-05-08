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
import com.liferay.source.formatter.parser.JavaTerm;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaTermStylingCheck extends BaseJavaTermCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, JavaTerm javaTerm,
		String fileContent) {

		Matcher matcher = _lineBreakPattern.matcher(javaTerm.getContent());

		while (matcher.find()) {
			if (getLevel(matcher.group(2)) >= 0) {
				continue;
			}

			int pos = fileContent.indexOf(matcher.group()) + 1;

			int lineCount = getLineCount(fileContent, pos);

			addMessage(
				fileName,
				"Create a new var for '" + StringUtil.trim(matcher.group(1)) +
					"' for better readability",
				lineCount);
		}

		return javaTerm.getContent();
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CONSTRUCTOR, JAVA_METHOD};
	}

	private final Pattern _lineBreakPattern = Pattern.compile(
		"\n(.*)\\(\n((.+,\n)*.*\\)) \\+\n");

}