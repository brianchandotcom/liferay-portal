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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Peter Shin
 */
public class XMLProjectElementCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (!absolutePath.endsWith(".pom")) {
			return content;
		}

		String[] lines = StringUtil.splitLines(content);

		if (lines.length == 0) {
			return content;
		}

		String firstLine = lines[0];

		if (!firstLine.matches("\\s*<project.*>\\s*")) {
			return content;
		}

		List<String> list = new ArrayList<>();

		Matcher matcher = _pattern.matcher(firstLine);

		while (matcher.find()) {
			list.add(StringUtil.trim(matcher.group()));
		}

		if (list.isEmpty()) {
			return content;
		}

		list.add(0, "<project");
		list.add(">");

		return StringUtil.replaceFirst(
			content, firstLine, StringUtil.merge(list, "\n\t"));
	}

	private final Pattern _pattern = Pattern.compile(
		"\\s*\\S*\\s*=\\s*\"[^\"]*\"");

}