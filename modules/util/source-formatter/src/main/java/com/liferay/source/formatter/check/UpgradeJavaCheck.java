/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaClassParser;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Hugo Huijser
 */
public class UpgradeJavaCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (!fileName.endsWith(".java")) {
			return content;
		}

		JavaClass javaClass = JavaClassParser.parseJavaClass(fileName, content);

		return _fixImports(javaClass, content);
	}

	private String _fixImports(JavaClass javaClass, String content)
		throws Exception {

		Map<String, String> importsMap = _getImportsMap();

		List<String> variables = new ArrayList<>();
		List<String> newVariables = new ArrayList<>();

		for (String importName : javaClass.getImportNames()) {
			String newImportName = importsMap.get(importName);

			if (newImportName == null) {
				continue;
			}

			content = StringUtil.replace(
				content,
				StringBundler.concat(
					"import ", importName, StringPool.SEMICOLON),
				StringBundler.concat(
					"import ", newImportName, StringPool.SEMICOLON));

			String className = SourceFormatterUtil.getSimpleName(importName);
			String newClassName = SourceFormatterUtil.getSimpleName(
				newImportName);

			if (!className.equals(newClassName)) {
				variables.add(className);
				variables.add(StringUtil.lowerCaseFirstLetter(className));

				newVariables.add(newClassName);
				newVariables.add(StringUtil.lowerCaseFirstLetter(newClassName));
			}
		}

		String javaClassContent = javaClass.getContent();

		String newJavaClassContent = javaClassContent;

		if (!newVariables.isEmpty()) {
			newJavaClassContent = StringUtil.replace(
				javaClassContent, ArrayUtil.toStringArray(variables),
				ArrayUtil.toStringArray(newVariables));
		}

		return StringUtil.replace(
			content, javaClassContent, newJavaClassContent);
	}

	private synchronized Map<String, String> _getImportsMap() throws Exception {
		if (_importsMap == null) {
			_importsMap = _getMap("imports.txt");
		}

		return _importsMap;
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

	private Map<String, String> _importsMap;

}