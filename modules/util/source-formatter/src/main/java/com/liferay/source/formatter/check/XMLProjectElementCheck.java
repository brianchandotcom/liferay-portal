/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

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
		String fileName, String absolutePath, String content) {

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

	private static final Pattern _pattern = Pattern.compile(
		"\\s*\\S*\\s*=\\s*\"[^\"]*\"");

}