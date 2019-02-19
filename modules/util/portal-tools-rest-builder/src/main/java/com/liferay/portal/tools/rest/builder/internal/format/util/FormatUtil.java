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

package com.liferay.portal.tools.rest.builder.internal.format.util;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.rest.builder.internal.format.java.checks.JavaImportsCheck;
import com.liferay.portal.tools.rest.builder.internal.format.java.checks.JavaLeadingTabsCheck;
import com.liferay.portal.tools.rest.builder.internal.format.java.checks.JavaLongLinesCheck;
import com.liferay.portal.tools.rest.builder.internal.format.java.checks.JavaSignatureStylingCheck;
import com.liferay.portal.tools.rest.builder.internal.format.java.checks.JavaTermDividersCheck;
import com.liferay.portal.tools.rest.builder.internal.format.java.checks.JavaTermOrderCheck;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Peter Shin
 */
public class FormatUtil {

	public static String format(String content, String fileName)
		throws Exception {

		Matcher matcher = _multiNewLinePattern.matcher(content);

		String newContent = matcher.replaceAll("\n");

		newContent = newContent.trim();

		if (!StringUtil.endsWith(fileName, ".java")) {
			return newContent;
		}

		newContent = StringUtil.replace(
			newContent, new String[] {"\t ", "( ", " ,", " )"},
			new String[] {"", "(", ",", ")"});

		newContent = JavaImportsCheck.format(newContent, fileName);
		newContent = JavaLeadingTabsCheck.format(newContent, fileName);
		newContent = JavaLongLinesCheck.format(newContent, fileName);
		newContent = JavaSignatureStylingCheck.format(newContent, fileName);
		newContent = JavaTermDividersCheck.format(newContent, fileName);
		newContent = JavaTermOrderCheck.format(newContent, fileName);

		newContent = StringUtil.replace(
			newContent, new String[] {"\n\n\t}\n"}, new String[] {"\n\t}\n"});

		return newContent;
	}

	private static final Pattern _multiNewLinePattern = Pattern.compile(
		"^\n+", Pattern.MULTILINE);

}