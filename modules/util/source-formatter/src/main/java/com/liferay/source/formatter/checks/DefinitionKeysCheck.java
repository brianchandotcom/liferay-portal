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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public abstract class DefinitionKeysCheck extends BaseFileCheck {

	protected List<String> getDefinitions(String content) {
		List<String> definitions = new ArrayList<>();

		Matcher matcher = _definitionPattern.matcher(content);

		while (matcher.find()) {
			String definition = matcher.group();

			if (Validator.isNotNull(matcher.group(1)) &&
				definition.endsWith("\n")) {

				definition = definition.substring(0, definition.length() - 1);
			}

			definitions.add(definition);
		}

		return definitions;
	}

	protected String sortDefinitionKeys(
		String content, List<String> definitions,
		Comparator<String> comparator) {

		for (int i = 1; i < definitions.size(); i++) {
			String definition = definitions.get(i);
			String previousDefinition = definitions.get(i - 1);

			int value = comparator.compare(previousDefinition, definition);

			if (value > 0) {
				content = StringUtil.replaceFirst(
					content, previousDefinition, definition);
				content = StringUtil.replaceLast(
					content, definition, previousDefinition);

				return content;
			}

			if (value == 0) {
				return StringUtil.replaceFirst(
					content, previousDefinition + "\n", StringPool.BLANK);
			}
		}

		return content;
	}

	private static final Pattern _definitionPattern = Pattern.compile(
		"^([A-Za-z-.]+?)[:=](\n|[\\s\\S]*?)(?=(\n[A-Za-z-.]+?[:=])|\\Z)",
		Pattern.MULTILINE);

}