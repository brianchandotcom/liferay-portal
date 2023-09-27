/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

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
public abstract class BaseDefinitionKeysCheck extends BaseFileCheck {

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