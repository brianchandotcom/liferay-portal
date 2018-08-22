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
import com.liferay.source.formatter.checks.util.YMLSourceUtil;

import java.io.Serializable;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class YMLDefinitionOrderCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		return _sortDefinitions(fileName, content, StringPool.BLANK);
	}

	private String _sortDefinitions(
		String fileName, String content, String indent) {

		List<String> definitions = YMLSourceUtil.getDefinitions(
			content, indent);

		DefinitionComparator definitionComparator = new DefinitionComparator(
			fileName);

		for (int i = 1; i < definitions.size(); i++) {
			String definition = definitions.get(i);
			String previousDefinition = definitions.get(i - 1);

			if (definitionComparator.compare(previousDefinition, definition) >
					0) {

				definition = StringUtil.trimTrailing(definition);
				previousDefinition = StringUtil.trimTrailing(
					previousDefinition);

				content = StringUtil.replaceFirst(
					content, previousDefinition, definition);

				return StringUtil.replaceLast(
					content, definition, previousDefinition);
			}

			String nestedDefinitionIndent =
				YMLSourceUtil.getNestedDefinitionIndent(definition);

			if (!nestedDefinitionIndent.equals(StringPool.BLANK)) {
				String newDefinition = _sortDefinitions(
					fileName, definition, nestedDefinitionIndent);

				if (!newDefinition.equals(definition)) {
					return StringUtil.replaceFirst(
						content, definition, newDefinition);
				}
			}

			nestedDefinitionIndent = YMLSourceUtil.getNestedDefinitionIndent(
				previousDefinition);

			if (!nestedDefinitionIndent.equals(StringPool.BLANK)) {
				String newDefinition = _sortDefinitions(
					fileName, previousDefinition, nestedDefinitionIndent);

				if (!newDefinition.equals(previousDefinition)) {
					return StringUtil.replaceFirst(
						content, previousDefinition, newDefinition);
				}
			}
		}

		return content;
	}

	private static class DefinitionComparator
		implements Comparator<String>, Serializable {

		public DefinitionComparator(String fileName) {
			_fileName = fileName;
		}

		@Override
		public int compare(String definition1, String definition2) {
			String definitionKey1 = _getDefinitionKey(definition1);
			String definitionKey2 = _getDefinitionKey(definition2);

			if (_fileName.endsWith("/.travis.yml")) {
				int weight1 = _getTravisDefinitionKeyWeight(definitionKey1);
				int weight2 = _getTravisDefinitionKeyWeight(definitionKey2);

				if ((weight1 != -1) || (weight2 != -1)) {
					return weight1 - weight2;
				}
			}

			return definitionKey1.compareTo(definitionKey2);
		}

		private String _getDefinitionKey(String definition) {
			Matcher matcher = _definitionKeyPattern.matcher(definition);

			if (matcher.find()) {
				return matcher.group(1);
			}

			return definition;
		}

		private int _getTravisDefinitionKeyWeight(String definitionKey) {
			if (_travisDefinitionKeyWeightMap.containsKey(definitionKey)) {
				return _travisDefinitionKeyWeightMap.get(definitionKey);
			}

			return -1;
		}

		private static final Map<String, Integer>
			_travisDefinitionKeyWeightMap = new HashMap<String, Integer>() {
				{
					put("after_deploy", 11);
					put("after_failure", 8);
					put("after_script", 12);
					put("after_success", 7);
					put("before_cache", 5);
					put("before_deploy", 9);
					put("before_install", 1);
					put("before_script", 3);
					put("cache", 6);
					put("deploy", 10);
					put("install", 2);
					put("script", 4);
				}
			};

		private final Pattern _definitionKeyPattern = Pattern.compile("(.*?):");
		private final String _fileName;

	}

}