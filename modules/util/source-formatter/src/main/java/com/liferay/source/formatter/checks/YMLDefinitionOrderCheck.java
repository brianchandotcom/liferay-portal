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
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.checks.util.YMLSourceUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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

		content = _sortPathParameters(sb.toString());

		return content;
	}

	private List<String> _combineComments(
		List<String> definitions, String indent) {

		List<String> definitionsList = new ArrayList<>();

		StringBundler sb = new StringBundler();

		String previousDefinition = StringPool.BLANK;

		for (String definition : definitions) {
			if (definition.startsWith(indent + StringPool.POUND)) {
				sb.append(definition);
				sb.append("\n");
			}
			else if (previousDefinition.startsWith(indent + StringPool.POUND)) {
				sb.append(definition);

				definitionsList.add(sb.toString());

				sb.setIndex(0);
			}
			else {
				definitionsList.add(definition);
			}

			previousDefinition = definition;
		}

		if (sb.index() > 0) {
			definitionsList.add(StringUtil.trimTrailing(sb.toString()));
		}

		return definitionsList;
	}

	private String _getParameterType(String definition) {
		return definition.replaceAll("(?s).*in: (\\S*).*", "$1");
	}

	private int _getParameterTypeWeight(String definitionKey) {
		if (_parameterTypesWeightMap.containsKey(definitionKey)) {
			return _parameterTypesWeightMap.get(definitionKey);
		}

		return -1;
	}

	private int _getSpecialQueryKeyWeight(String definitionKey) {
		if (_specialQueriesKeyWeightMap.containsKey(definitionKey)) {
			return _specialQueriesKeyWeightMap.get(definitionKey);
		}

		return -1;
	}

	private List<String> _removeDuplicateAttribute(List<String> list) {
		List<String> definitions = new ArrayList<>();
		Iterator<String> itr = list.iterator();

		while (itr.hasNext()) {
			String s = itr.next();

			if (!definitions.contains(s) || s.startsWith("{{") ||
				s.startsWith("#")) {

				definitions.add(s);
			}
		}

		return definitions;
	}

	private String _sortDefinitions(
		String fileName, String content, String indent) {

		List<String> definitions = YMLSourceUtil.getDefinitions(
			content, indent);

		if ((definitions.size() == 1) && !content.contains("\n")) {
			return content;
		}

		definitions = _removeDuplicateAttribute(definitions);

		definitions = _combineComments(definitions, indent);

		List<String> oldDefinitions = new ArrayList<>(definitions);

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
						trimmedDefinition2.startsWith("{{") ||
						Validator.isNull(trimmedDefinition1) ||
						Validator.isNull(trimmedDefinition2)) {

						return 0;
					}

					String[] definition1Lines = StringUtil.splitLines(
						definition1);
					String[] definition2Lines = StringUtil.splitLines(
						definition2);

					String trimmedDefinition1Line = definition1Lines[0];
					String trimmedDefinition2Line = definition2Lines[0];

					if (trimmedDefinition1Line.equals(StringPool.DASH) &&
						trimmedDefinition2Line.equals(StringPool.DASH)) {

						if (definition1Lines[1].contains("in: ") &&
							definition2Lines[1].contains("in: ")) {

							return _sortSpecificDefinitions(
								definition1, definition2, "name");
						}

						String value1 = definition1Lines[1].trim();
						String value2 = definition2Lines[1].trim();

						return value1.compareTo(value2);
					}

					if (trimmedDefinition1Line.startsWith("in:") ||
						trimmedDefinition2Line.startsWith("in:")) {

						if (trimmedDefinition1Line.startsWith("in:")) {
							return -1;
						}

						return 1;
					}

					String definition1Key = definition1.replaceAll(
						"( *#.*(\\Z|\n))*(.*)", "$3");
					String definition2Key = definition2.replaceAll(
						"( *#.*(\\Z|\n))*(.*)", "$3");

					if (Validator.isNull(definition1Key) ||
						Validator.isNull(definition2Key)) {

						return 0;
					}

					return definition1Key.compareTo(definition2Key);
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
			String[] lines = StringUtil.splitLines(definition);

			if ((lines.length != 0) &&
				lines[0].matches(" *(description:|.+: +.+)")) {

				continue;
			}

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

	private String _sortPathParameters(String content) {
		Matcher matcher1 = _pathPattern.matcher(content);

		Pattern pattern = null;

		while (matcher1.find()) {
			String path = matcher1.group();

			pattern = Pattern.compile("\\{([^{}]+)\\}");

			Matcher matcher2 = pattern.matcher(path);

			Map<String, String> inPathsMap = new LinkedHashMap<>();

			while (matcher2.find()) {
				inPathsMap.put(matcher2.group(1), "");
			}

			int inPathCount = inPathsMap.size();

			pattern = Pattern.compile(
				"( *-\n( +)in: path(\n\\2.+)*\n){" + inPathCount + "}");

			matcher2 = pattern.matcher(path);

			while (matcher2.find()) {
				String inPaths = null;

				inPaths = matcher2.group();

				pattern = Pattern.compile(" *-\n( +)in: path(\n\\1.+)*\n");

				Matcher matcher3 = pattern.matcher(inPaths);

				while (matcher3.find()) {
					String inPath = matcher3.group();

					inPathsMap.replace(
						inPath.replaceAll("(?s).*name: (\\S+).*", "$1"),
						inPath);
				}

				StringBundler sb = new StringBundler(inPathCount);

				for (Map.Entry<String, String> entry : inPathsMap.entrySet()) {
					sb.append(entry.getValue());
				}

				content = StringUtil.replaceFirst(
					content, inPaths, sb.toString());
			}
		}

		return content;
	}

	private int _sortSpecificDefinitions(
		String definition1, String definition2, String key) {

		String parameter1Type = _getParameterType(definition1);
		String parameter2Type = _getParameterType(definition2);

		Pattern pattern = Pattern.compile(
			"^ *" + key + ": *(\\S*)(\n|\\Z)", Pattern.MULTILINE);

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

		if (parameter1Type.equals(parameter2Type)) {
			if (parameter1Type.equals("query")) {
				int weight1 = _getSpecialQueryKeyWeight(value1);
				int weight2 = _getSpecialQueryKeyWeight(value2);

				if ((weight1 != -1) || (weight2 != -1)) {
					return weight1 - weight2;
				}
			}

			return value1.compareTo(value2);
		}

		return _getParameterTypeWeight(parameter1Type) -
			_getParameterTypeWeight(parameter2Type);
	}

	private static final Map<String, Integer> _parameterTypesWeightMap =
		HashMapBuilder.put(
			"cookie", 4
		).put(
			"header", 3
		).put(
			"path", 1
		).put(
			"query", 2
		).build();
	private static final Pattern _pathPattern = Pattern.compile(
		"(?<=\n)( *)\"([^{}\"]*\\{[^}]+\\}[^{}\"]*){2,}\":(\n\\1 .*)*");
	private static final Map<String, Integer> _specialQueriesKeyWeightMap =
		HashMapBuilder.put(
			"filter", 1
		).put(
			"page", 2
		).put(
			"pageSize", 3
		).put(
			"search", 4
		).put(
			"sort", 5
		).build();

}