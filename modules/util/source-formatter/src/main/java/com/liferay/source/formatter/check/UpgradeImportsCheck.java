/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.JSPImportsFormatter;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaClassParser;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 * @author Nícolas Moura
 */
public class UpgradeImportsCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (!fileName.endsWith(".java") && !fileName.endsWith(".jsp") &&
			!fileName.endsWith(".ftl")) {

			return content;
		}

		return _fixImports(fileName, content);
	}

	private static List<String> _getImportNames(String fileName, String content)
		throws Exception {

		List<String> importNames = new ArrayList<>();

		if (fileName.endsWith(".java")) {
			JavaClass javaClass = JavaClassParser.parseJavaClass(
				fileName, content);

			importNames = javaClass.getImportNames();
		}
		else if (fileName.endsWith(".jsp")) {
			importNames = JSPImportsFormatter.getImportNames(content);
		}
		else {
			Matcher matcher = _ftlImportNamePattern.matcher(content);

			while (matcher.find()) {
				importNames.add(matcher.group(1));
			}
		}

		return importNames;
	}

	private static String _replaceVariables(
		String content, Map<String, String> variablesMap) {

		if (variablesMap.isEmpty()) {
			return content;
		}

		String newContent = StringUtil.replace(
			content, ArrayUtil.toStringArray(variablesMap.keySet()),
			ArrayUtil.toStringArray(variablesMap.values()), true);

		for (Map.Entry<String, String> entry : variablesMap.entrySet()) {
			String regex = StringBundler.concat(
				"\\b([_a-z]\\w*)", entry.getKey(), "\\b");

			Pattern pattern = Pattern.compile(regex);

			Matcher variableMatcher = pattern.matcher(newContent);

			if (variableMatcher.find()) {
				newContent = newContent.replaceAll(
					regex, variableMatcher.group(1) + entry.getValue());
			}
		}

		return newContent;
	}

	private synchronized String _fixImports(String fileName, String content)
		throws Exception {

		List<String> importNames = _getImportNames(fileName, content);

		Map<String, String> importsMap = _getMap("imports.txt");

		for (String importName : importNames) {
			String newImportName = importsMap.get(importName);

			if (newImportName == null) {
				continue;
			}

			content = StringUtil.replace(content, importName, newImportName);
		}

		Map<String, String> variablesMap = _getVariablesMap(importsMap);

		if (fileName.endsWith(".java")) {
			JavaClass javaClass = JavaClassParser.parseJavaClass(
				fileName, content);

			String newContent = javaClass.getContent();

			return StringUtil.replace(
				content, newContent,
				_replaceVariables(newContent, variablesMap));
		}

		return _replaceVariables(content, variablesMap);
	}

	private Map<String, String> _getMap(String fileName) throws Exception {
		Map<String, String> map = new HashMap<>();

		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		InputStream inputStream = classLoader.getResourceAsStream(
			"dependencies/" + fileName);

		if (inputStream == null) {
			return map;
		}

		String[] lines = StringUtil.splitLines(StringUtil.read(inputStream));

		for (String line : lines) {
			int separatorIndex = line.indexOf(StringPool.EQUAL);

			map.put(
				line.substring(0, separatorIndex),
				line.substring(separatorIndex + 1));
		}

		return map;
	}

	private Map<String, String> _getVariablesMap(
		Map<String, String> importsMap) {

		Map<String, String> variablesMap = new HashMap<>();

		for (Map.Entry<String, String> entry : importsMap.entrySet()) {
			String className = SourceFormatterUtil.getSimpleName(
				entry.getKey());
			String newClassName = SourceFormatterUtil.getSimpleName(
				entry.getValue());

			if (!className.equals(newClassName)) {
				variablesMap.put(className, newClassName);
				variablesMap.put(
					StringUtil.lowerCaseFirstLetter(className),
					StringUtil.lowerCaseFirstLetter(newClassName));
			}
		}

		return variablesMap;
	}

	private static final Pattern _ftlImportNamePattern = Pattern.compile(
		"(?:findService|staticUtil)[(\\[]\"([^\\s\"]+)\"[)\\]]");

}