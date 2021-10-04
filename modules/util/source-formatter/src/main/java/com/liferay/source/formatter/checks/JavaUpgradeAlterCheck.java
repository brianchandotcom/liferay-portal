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
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.checks.util.JavaSourceUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaClassParser;
import com.liferay.source.formatter.parser.JavaTerm;
import com.liferay.source.formatter.util.FileUtil;

import java.io.File;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Kevin Lee
 */
public class JavaUpgradeAlterCheck extends BaseFileCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (!absolutePath.contains("/upgrade/") ||
			!content.contains("alter(")) {

			return content;
		}

		_columnNamesByTables.clear();

		Matcher matcher1 = _alterPattern.matcher(content);

		while (matcher1.find()) {
			List<String> parameterList = JavaSourceUtil.getParameterList(
				_getMethodCall(content, matcher1.start()));

			String tableName = null;

			Matcher matcher2 = _alterTableClassPattern.matcher(
				parameterList.get(0));

			if (matcher2.find()) {
				tableName = matcher2.group(1);

				if (!_columnNamesByTables.containsKey(tableName)) {
					_readColumnNamesFromTableClass(tableName, absolutePath);
				}
			}

			_checkAlterObjects(
				tableName, fileName, content,
				parameterList.subList(1, parameterList.size()));
		}

		return content;
	}

	private void _checkAlterColumnType(
		int start, String tableName, String fileName, String content,
		String alterObject) {

		Matcher matcher = _alterColumnTypePattern.matcher(alterObject);

		if (!matcher.find()) {
			return;
		}

		String columnName = matcher.group(1);

		if ((tableName != null) &&
			_columnNamesByTables.containsKey(tableName)) {

			Set<String> columnNames = _columnNamesByTables.get(tableName);

			if (!columnNames.contains(columnName)) {
				String message = String.format(
					"The column \"%s\" does not exist in table \"%s\"",
					columnName, tableName);

				addMessage(
					fileName, message,
					getLineNumber(content, start + matcher.start(1)));
			}
		}

		String dataType = matcher.group(3);

		if (!ArrayUtil.contains(_VALID_TYPES, dataType)) {
			String message = String.format(
				"Only the following data types are valid: %s",
				Arrays.toString(_VALID_TYPES));

			addMessage(
				fileName, message,
				getLineNumber(content, start + matcher.start(3)));

			return;
		}

		if (!ArrayUtil.contains(_STRING_TYPES, dataType)) {
			return;
		}

		String newType = matcher.group(2);

		if (!(newType.contains("null") || newType.contains("not null"))) {
			String message = String.format(
				"Specify whether the new type for \"%s\" is nullable",
				columnName);

			addMessage(
				fileName, message,
				getLineNumber(content, start + matcher.start(2)));
		}
	}

	private void _checkAlterObjects(
		String tableName, String fileName, String content,
		List<String> alterObjects) {

		for (String alterObject : alterObjects) {
			Matcher matcher = _alterObjectPattern.matcher(alterObject);

			while (matcher.find()) {
				String alterType = matcher.group(1);

				int pos = content.indexOf(alterObject, matcher.start());

				if (Objects.equals(alterType, "AlterColumnType")) {
					_checkAlterColumnType(
						pos, tableName, fileName, content, alterObject);
				}
			}
		}
	}

	private String _getMethodCall(String content, int start) {
		int x = start;

		while (true) {
			x = content.indexOf(StringPool.CLOSE_PARENTHESIS, x + 1);

			if (ToolsUtil.isInsideQuotes(content, x + 1)) {
				continue;
			}

			String methodCall = content.substring(start, x + 1);

			if (getLevel(methodCall) == 0) {
				return methodCall;
			}
		}
	}

	private void _readColumnNamesFromTableClass(
			String tableName, String absolutePath)
		throws Exception {

		int x = absolutePath.lastIndexOf("/");

		if (x == -1) {
			return;
		}

		String fileName = StringBundler.concat(
			absolutePath.substring(0, x), "/util/", tableName, "Table.java");

		if (!FileUtil.exists(fileName)) {
			return;
		}

		String fileContent = FileUtil.read(new File(fileName));

		if (!fileContent.contains("@generated")) {
			return;
		}

		JavaClass javaClass = JavaClassParser.parseJavaClass(
			fileName, fileContent);

		for (JavaTerm javaTerm : javaClass.getChildJavaTerms()) {
			if (!javaTerm.isJavaVariable() ||
				!Objects.equals(javaTerm.getName(), "TABLE_COLUMNS")) {

				continue;
			}

			Set<String> columnNames = new HashSet<>();

			Matcher matcher = _stringPattern.matcher(javaTerm.getContent());

			while (matcher.find()) {
				columnNames.add(matcher.group(1));
			}

			_columnNamesByTables.put(tableName, columnNames);

			break;
		}
	}

	private static final String[] _STRING_TYPES = {"STRING", "TEXT", "VARCHAR"};

	private static final String[] _VALID_TYPES = {
		"BLOB", "SBLOB", "BOOLEAN", "DATE", "DOUBLE", "INTEGER", "LONG",
		"STRING", "TEXT", "VARCHAR"
	};

	private static final Pattern _alterColumnTypePattern = Pattern.compile(
		"AlterColumnType\\(\\s*\"(\\w+)\",\\s*\"((\\w+).*)\"\\)");
	private static final Pattern _alterObjectPattern = Pattern.compile(
		"new (Alter\\w+)\\(");
	private static final Pattern _alterPattern = Pattern.compile(
		"alter\\(\\s*");
	private static final Pattern _alterTableClassPattern = Pattern.compile(
		"(\\w+)Table\\.class");
	private static final Pattern _stringPattern = Pattern.compile("\"(\\w+)\"");

	private final Map<String, Set<String>> _columnNamesByTables =
		new HashMap<>();

}