/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Peter Shin
 * @author Alan Huang
 */
public class YMLSourceUtil {

	public static List<String> getDefinitions(String content, String indent) {
		List<String> definitions = new ArrayList<>();

		String[] lines = content.split("\n");

		StringBundler sb = new StringBundler();

		for (String line : lines) {
			if (line.length() == 0) {
				sb.append("\n");

				continue;
			}

			if (line.matches(" +")) {
				sb.append(line);

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

	public static Map<Integer, String> getDocumentsMap(String content) {
		Map<Integer, String> documentsMap = new HashMap<>();

		int x = -1;

		while (true) {
			x = content.lastIndexOf("\n---\n");

			if (x == -1) {
				break;
			}

			String s = content.substring(x + 5);

			int startLineNumber = SourceUtil.getLineNumber(content, x + 5);

			documentsMap.put(startLineNumber, s);

			content = content.substring(0, x);
		}

		documentsMap.put(1, content);

		return documentsMap;
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

	public static boolean isBlockStyle(String line) {
		String trimmedLine = StringUtil.trimTrailing(line);

		if (trimmedLine.endsWith(">") || trimmedLine.endsWith(">+") ||
			trimmedLine.endsWith(">-") || trimmedLine.endsWith("|") ||
			trimmedLine.endsWith("|+") || trimmedLine.endsWith("|-")) {

			return true;
		}

		return false;
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

	public static List<String> splitDocuments(String content) {
		List<String> documents = new ArrayList<>();

		int x = -1;

		while (true) {
			x = content.lastIndexOf("\n---\n");

			if (x == -1) {
				break;
			}

			String s = content.substring(x + 5);

			documents.add(0, s);

			content = content.substring(0, x);
		}

		documents.add(0, content);

		return documents;
	}

}