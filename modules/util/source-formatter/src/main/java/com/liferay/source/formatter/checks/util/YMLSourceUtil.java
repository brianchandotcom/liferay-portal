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

package com.liferay.source.formatter.checks.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Peter Shin
 */
public class YMLSourceUtil {

	public static List<String> getDefinitions(String content, String indent) {
		List<String> definitions = new ArrayList<>();

		Pattern pattern = Pattern.compile(
			StringBundler.concat(
				"^", indent, "[a-z].*:.*(\n|\\Z)((", indent,
				"[^a-z\n].*)?(\n|\\Z))*"),
			Pattern.MULTILINE);

		Matcher matcher = pattern.matcher(content);

		while (matcher.find()) {
			definitions.add(matcher.group());
		}

		return definitions;
	}

	public static String getNestedDefinitionIndent(String definition) {
		String[] lines = StringUtil.splitLines(definition);

		if (lines.length <= 1) {
			return StringPool.BLANK;
		}

		for (int i = 1; i < lines.length; i++) {
			String line = lines[i];

			String indent = line.replaceAll("^(\\s+).+", "$1");

			if (!indent.equals(line)) {
				return indent;
			}
		}

		return StringPool.BLANK;
	}

}