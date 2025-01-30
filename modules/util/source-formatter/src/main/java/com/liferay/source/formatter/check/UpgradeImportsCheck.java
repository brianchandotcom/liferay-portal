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

		_getImportsMap();

		if (!fileName.endsWith(".java") && !fileName.endsWith(".jsp") &&
			!fileName.endsWith(".ftl")) {

			return content;
		}

		return _fixImports(fileName, content);
	}

	private static void _checkClassNames(
		String importName, String newImportName) {

		String className = SourceFormatterUtil.getSimpleName(importName);
		String newClassName = SourceFormatterUtil.getSimpleName(newImportName);

		if (!className.equals(newClassName)) {
			_variablesMap.put(className, newClassName);
			_variablesMap.put(
				StringUtil.lowerCaseFirstLetter(className),
				StringUtil.lowerCaseFirstLetter(newClassName));
		}
	}

	private static String _replaceVariables(String content) {
		String newContent = content;

		if (!_variablesMap.isEmpty()) {
			newContent = StringUtil.replace(
				content, ArrayUtil.toStringArray(_variablesMap.keySet()),
				ArrayUtil.toStringArray(_variablesMap.values()), true);

			for (Map.Entry<String, String> entry : _variablesMap.entrySet()) {
				String regex = StringBundler.concat(
					"\\b([_a-z]\\w*)", entry.getKey(), "\\b");

				Pattern pattern = Pattern.compile(regex);

				Matcher variableMatcher = pattern.matcher(newContent);

				if (variableMatcher.find()) {
					newContent = newContent.replaceAll(
						regex, variableMatcher.group(1) + entry.getValue());
				}
			}
		}

		return newContent;
	}

	private String _fixImports(String fileName, String content)
		throws Exception {

		List<String> importNames = new ArrayList<>();

		String newContent = content;

		if (fileName.endsWith(".java")) {
			JavaClass javaClass = JavaClassParser.parseJavaClass(
				fileName, content);

			importNames = javaClass.getImportNames();

			newContent = javaClass.getContent();
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

		_variablesMap = new HashMap<>();

		for (String importName : importNames) {
			String newImportName = _importsMap.get(importName);

			if (newImportName == null) {
				continue;
			}

			content = StringUtil.replace(content, importName, newImportName);

			_checkClassNames(importName, newImportName);
		}

		if (fileName.endsWith(".java")) {
			return StringUtil.replace(
				content, newContent, _replaceVariables(newContent));
		}

		return _replaceVariables(content);
	}

	private synchronized void _getImportsMap() throws Exception {
		if (_importsMap == null) {
			_importsMap = _getMap("imports.txt");
		}
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

	private static final Pattern _ftlImportNamePattern = Pattern.compile(
		"(?:findService|staticUtil)[(\\[]\"([^\\s\"]+)\"[)\\]]");
	private static Map<String, String> _variablesMap;

	private Map<String, String> _importsMap;

}