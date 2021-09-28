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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Peter Shin
 * @author Alan Huang
 */
public class YMLSourceUtil {

	public static List<String> getContentBlocks(
		String content, Pattern styleBlockPattern) {

		List<String> contentBlocks = new ArrayList<>();

		Matcher matcher = styleBlockPattern.matcher(content);

		int lastEndPos = 0;

		while (matcher.find()) {
			contentBlocks.add(
				content.substring(lastEndPos, matcher.start(1) - 1));
			contentBlocks.add(
				content.substring(matcher.start(1), matcher.end(1)));

			lastEndPos = matcher.end(1) + 1;
		}

		if (lastEndPos < content.length()) {
			contentBlocks.add(content.substring(lastEndPos));
		}

		if (contentBlocks.isEmpty()) {
			contentBlocks.add(content);
		}

		return contentBlocks;
	}

	public static List<String> getDefinitions(String content, String indent) {
		List<String> definitions = new ArrayList<>();

		String[] lines = content.split("\n");

		StringBundler sb = new StringBundler();

		for (String line : lines) {
			if (line.length() == 0) {
				sb.append("\n");

				continue;
			}

			if (!line.startsWith(indent)) {
				continue;
			}

			String s = line.substring(indent.length(), indent.length() + 1);

			if (!s.equals(StringPool.SPACE) && (sb.length() != 0)) {
				sb.setIndex(sb.index() - 1);

				definitions.add(sb.toString());

				sb.setIndex(0);
			}

			sb.append(line);
			sb.append("\n");
		}

		if (sb.index() > 0) {
			sb.setIndex(sb.index() - 1);
		}

		definitions.add(sb.toString());

		return definitions;
	}

	public static String getNestedDefinitionIndent(String definition) {
		String[] lines = StringUtil.splitLines(definition);

		if (lines.length <= 1) {
			return StringPool.BLANK;
		}

		for (int i = 1; i < lines.length; i++) {
			String line = lines[i];

			String indent = line.replaceFirst("^( +).+", "$1");

			if (!indent.equals(line)) {
				return indent;
			}
		}

		return StringPool.BLANK;
	}

	public static List<String> splitDirectives(String content) {
		List<String> directives = new ArrayList<>();

		String[] lines = content.split("\n");

		StringBundler sb = new StringBundler();

		for (String line : lines) {
			if (!line.equals("---")) {
				sb.append(line);
				sb.append("\n");

				continue;
			}

			if (sb.index() > 0) {
				sb.setIndex(sb.index() - 1);

				directives.add(sb.toString());

				sb.setIndex(0);
			}
		}

		if (sb.index() > 0) {
			sb.setIndex(sb.index() - 1);

			directives.add(sb.toString());
		}

		return directives;
	}

}