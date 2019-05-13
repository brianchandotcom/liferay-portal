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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.checks.util.YMLSourceUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 * @author Alan Huang
 */
public class YMLDefinitionOrderCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (fileName.endsWith(".travis.yml")) {
			return content;
		}

		String[] ymlDefinitions = content.split("\n---\n");

		StringBundler sb = new StringBundler(ymlDefinitions.length * 2);

		for (String ymlDefinition : ymlDefinitions) {
			sb.append(
				_sortDefinitions(fileName, ymlDefinition, StringPool.BLANK));
			sb.append("\n---\n");
		}

		sb.setIndex(sb.index() - 1);

		return sb.toString();
	}

	private String _sortDefinitions(
		String fileName, String content, String indent) {

		List<String> definitions = YMLSourceUtil.getDefinitions(
			content, indent);

		if ((definitions.size() == 1) && !content.contains("\n")) {
			return content;
		}

		List<String> oldDefinitions = new ArrayList<>(definitions);

		ListUtil.distinct(definitions);

		Collections.sort(
			definitions,
			new Comparator<String>() {

				@Override
				public int compare(String definition1, String definition2) {
					String trimmedDefinition1 = StringUtil.trimLeading(
						definition1);
					String trimmedDefinition2 = StringUtil.trimLeading(
						definition2);

					if (trimmedDefinition1.startsWith("{{") ||
						trimmedDefinition2.startsWith("{{")) {

						return 0;
					}

					if (Validator.isNull(definition1) ||
						Validator.isNull(definition2)) {

						return 0;
					}

					String[] definition1Lines = StringUtil.splitLines(
						definition1);
					String[] definition2Lines = StringUtil.splitLines(
						definition2);

					String trimmedDefinition1Line = definition1Lines[0];
					String trimmedDefinition2Line = definition2Lines[0];

					if (trimmedDefinition1Line.startsWith(StringPool.POUND) ||
						trimmedDefinition2Line.startsWith(StringPool.POUND)) {

						return 0;
					}

					if (trimmedDefinition1Line.equals(StringPool.DASH) &&
						trimmedDefinition2Line.equals(StringPool.DASH)) {

						String value1 = definition1Lines[1].trim();
						String value2 = definition2Lines[1].trim();

						if (value1.equals("in: query") &&
							value1.equals(value2)) {

							return _sortSpecificDefinitions(
								definition1, definition2, "name");
						}

						return value1.compareTo(value2);
					}

					return trimmedDefinition1Line.compareTo(
						trimmedDefinition2Line);
				}

			});

		if (!oldDefinitions.equals(definitions)) {
			StringBundler sb = new StringBundler();

			for (String definition : definitions) {
				sb.append(definition);
				sb.append("\n");
			}

			sb.setIndex(sb.index() - 1);

			String[] lines = content.split("\n");

			if (!indent.equals("")) {
				content = lines[0] + "\n" + sb.toString();
			}
			else {
				content = sb.toString();
			}
		}

		definitions = YMLSourceUtil.getDefinitions(content, indent);

		for (String definition : definitions) {
			String nestedDefinitionIndent =
				YMLSourceUtil.getNestedDefinitionIndent(definition);

			if (!nestedDefinitionIndent.equals(StringPool.BLANK)) {
				content = StringUtil.replaceFirst(
					content, definition,
					_sortDefinitions(
						fileName, definition, nestedDefinitionIndent));
			}
		}

		return content;
	}

	private int _sortSpecificDefinitions(
		String definition1, String definition2, String key) {

		Pattern pattern = Pattern.compile(
			"^ *" + key + ": *(\\w*)(\n|\\Z)", Pattern.MULTILINE);

		String value1 = StringPool.BLANK;

		Matcher matcher = pattern.matcher(definition1);

		if (matcher.find()) {
			value1 = matcher.group(1);
		}

		String value2 = StringPool.BLANK;

		matcher = pattern.matcher(definition2);

		if (matcher.find()) {
			value2 = matcher.group(1);
		}

		return value1.compareTo(value2);
	}

}