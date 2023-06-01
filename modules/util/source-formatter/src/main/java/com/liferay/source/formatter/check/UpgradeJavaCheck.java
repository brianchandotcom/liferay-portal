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

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaClassParser;
import com.liferay.source.formatter.util.FileUtil;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import java.io.File;

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

		List<String> oldVariablesList = new ArrayList<>();
		List<String> newVariablesList = new ArrayList<>();

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
				oldVariablesList.add(className);
				oldVariablesList.add(
					StringUtil.lowerCaseFirstLetter(className));
				newVariablesList.add(newClassName);
				newVariablesList.add(
					StringUtil.lowerCaseFirstLetter(newClassName));
			}
		}

		if (!newVariablesList.isEmpty()) {
			content = StringUtil.replace(
				content, oldVariablesList.toArray(new String[0]),
				newVariablesList.toArray(new String[0]));
		}

		return content;
	}

	private synchronized Map<String, String> _getImportsMap() throws Exception {
		if (_importsMap == null) {
			_importsMap = _getMap("/imports.txt");
		}

		return _importsMap;
	}

	private Map<String, String> _getMap(String fileName) throws Exception {
		Map<String, String> map = new HashMap<>();

		File importsFile = SourceFormatterUtil.getFile(
			getBaseDirName(),
			"modules/util/source-formatter/src/main/resources/dependencies/" +
				fileName,
			getMaxDirLevel());

		if (importsFile == null) {
			return map;
		}

		String[] lines = StringUtil.splitLines(FileUtil.read(importsFile));

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