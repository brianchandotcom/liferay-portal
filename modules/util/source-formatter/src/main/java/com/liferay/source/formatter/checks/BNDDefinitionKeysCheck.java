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
import com.liferay.source.formatter.checks.util.BNDSourceUtil;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class BNDDefinitionKeysCheck extends DefinitionKeysCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		List<String> definitions = getDefinitions(content);

		if (definitions.isEmpty()) {
			return content;
		}

		content = sortDefinitionKeys(
			content, definitions, new DefinitionComparator());

		Map<String, Map<String, String>> fileSpecificDefinitionKeysMap =
			BNDSourceUtil.getFileSpecificDefinitionKeysMap();
		Map<String, String> generalDefinitionKeysMap =
			BNDSourceUtil.getDefinitionKeysMap();

		for (String definition : definitions) {
			content = _formatDefinitionKey(
				fileName, content, definition, fileSpecificDefinitionKeysMap,
				generalDefinitionKeysMap);
		}

		return content;
	}

	private String _formatDefinitionKey(
		String fileName, String content, String definition,
		Map<String, Map<String, String>> fileSpecificDefinitionKeysMap,
		Map<String, String> generalDefinitionKeysMap) {

		if (definition.endsWith(",\\")) {
			return StringUtil.replace(
				content, definition,
				definition.substring(0, definition.length() - 2));
		}

		Matcher matcher = _definitionKeyPattern.matcher(definition);

		if (!matcher.find()) {
			return content;
		}

		String definitionKey = matcher.group(1);

		if (definitionKey.contains(StringPool.PERIOD)) {
			definitionKey = StringUtil.extractFirst(
				definitionKey, StringPool.PERIOD);
		}

		String lowerCaseDefinitionKey = StringUtil.toLowerCase(definitionKey);

		String correctKey = null;

		if (definitionKey.equals("Conditional-Package")) {
			correctKey = "-conditionalpackage";
		}
		else {
			correctKey = generalDefinitionKeysMap.get(lowerCaseDefinitionKey);
		}

		if (correctKey == null) {
			int pos = fileName.lastIndexOf(StringPool.SLASH);

			String shortFileName = fileName.substring(pos + 1);

			Map<String, String> definitionKeysMap =
				fileSpecificDefinitionKeysMap.get(shortFileName);

			if (definitionKeysMap != null) {
				correctKey = definitionKeysMap.get(lowerCaseDefinitionKey);
			}
		}

		if (correctKey == null) {
			addMessage(
				fileName, "Unknown key \"" + definitionKey + "\"",
				"bnd_definition_keys.markdown");

			return content;
		}

		if (correctKey.equals(definitionKey)) {
			return StringUtil.replace(
				content, definitionKey + "=", definitionKey + ":");
		}

		if (content.startsWith(definitionKey)) {
			return StringUtil.replaceFirst(content, definitionKey, correctKey);
		}

		return StringUtil.replace(
			content, "\n" + definitionKey + ":", "\n" + correctKey + ":");
	}

	private static final Pattern _definitionKeyPattern = Pattern.compile(
		"([A-Za-z-.]+?)[:=]");

	private static class DefinitionComparator implements Comparator<String> {

		@Override
		public int compare(String definition1, String definition2) {
			if (definition1.startsWith(StringPool.DASH) ^
				definition2.startsWith(StringPool.DASH)) {

				return -definition1.compareTo(definition2);
			}

			return definition1.compareTo(definition2);
		}

	}

}