/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.util.BNDSourceUtil;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class BNDDefinitionKeysCheck extends BaseDefinitionKeysCheck {

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
			addMessage(fileName, "Unknown key \"" + definitionKey + "\"");

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

			String definitionKey1 = StringUtil.extractFirst(
				definition1, StringPool.COLON);
			String definitionKey2 = StringUtil.extractFirst(
				definition2, StringPool.COLON);

			if ((definitionKey1 != null) && (definitionKey2 != null)) {
				return definitionKey1.compareTo(definitionKey2);
			}

			return definition1.compareTo(definition2);
		}

	}

}