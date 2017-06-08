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

import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class YMLDefintionOrderCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		return _sortDefinitions(content);
	}

	private List<String> _getDefinitions(String content) {
		List<String> definitions = new ArrayList<>();

		Matcher matcher = _definitionPattern.matcher(content);

		while (matcher.find()) {
			definitions.add(matcher.group());
		}

		return definitions;
	}

	private String _sortDefinitions(String content) {
		List<String> definitions = _getDefinitions(content);

		DefinitionComparator definitionComparator = new DefinitionComparator();

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
		}

		return content;
	}

	private final Pattern _definitionPattern = Pattern.compile(
		"^[a-z].*:.*(\n|\\Z)(([^a-z\n].*)?(\n|\\Z))*", Pattern.MULTILINE);

	private static class DefinitionComparator
		implements Comparator<String>, Serializable {

		@Override
		public int compare(String definition1, String definition2) {
			String definitionKey1 = _getDefinitionKey(definition1);
			String definitionKey2 = _getDefinitionKey(definition2);

			int definitionKeyWeight1 = _getDefinitionKeyWeight(definitionKey1);
			int definitionKeyWeight2 = _getDefinitionKeyWeight(definitionKey2);

			if ((definitionKeyWeight1 != -1) || (definitionKeyWeight2 != -1)) {
				return definitionKeyWeight1 - definitionKeyWeight2;
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

		private int _getDefinitionKeyWeight(String definitionKey) {
			if (_definitionKeyWeightMap.containsKey(definitionKey)) {
				return _definitionKeyWeightMap.get(definitionKey);
			}

			return -1;
		}

		private static final Map<String, Integer> _definitionKeyWeightMap =
			new HashMap<>();

		static {
			_definitionKeyWeightMap.put("after_deploy", 11);
			_definitionKeyWeightMap.put("after_failure", 8);
			_definitionKeyWeightMap.put("after_script", 12);
			_definitionKeyWeightMap.put("after_success", 7);
			_definitionKeyWeightMap.put("before_cache", 5);
			_definitionKeyWeightMap.put("before_deploy", 9);
			_definitionKeyWeightMap.put("before_install", 1);
			_definitionKeyWeightMap.put("before_script", 3);
			_definitionKeyWeightMap.put("cache", 6);
			_definitionKeyWeightMap.put("deploy", 10);
			_definitionKeyWeightMap.put("install", 2);
			_definitionKeyWeightMap.put("script", 4);
		}

		private final Pattern _definitionKeyPattern = Pattern.compile("(.*?):");

	}

}