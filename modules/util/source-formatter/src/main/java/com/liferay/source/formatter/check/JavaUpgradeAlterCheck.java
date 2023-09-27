/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.util.JavaSourceUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaClassParser;
import com.liferay.source.formatter.parser.JavaTerm;
import com.liferay.source.formatter.util.FileUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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

		Matcher matcher = _alterPattern.matcher(content);

		while (matcher.find()) {
			List<String> parameterList = JavaSourceUtil.getParameterList(
				JavaSourceUtil.getMethodCall(content, matcher.start()));

			String firstParameter = parameterList.get(0);

			String tableClassName = _getTableClassName(content, firstParameter);

			if (tableClassName == null) {
				continue;
			}

			List<String> columnNames = _getColumnNames(
				absolutePath, tableClassName);

			if (columnNames.isEmpty()) {
				continue;
			}

			String newContent = _formatAlterObjects(
				firstParameter.substring(0, firstParameter.length() - 6),
				fileName, content,
				parameterList.subList(1, parameterList.size()), columnNames,
				matcher.start());

			if (!newContent.equals(content)) {
				return newContent;
			}
		}

		return content;
	}

	private void _checkAlterColumnType(
		int start, String tableName, String fileName, String content,
		String alterObject, List<String> columnNames) {

		Matcher matcher = _alterColumnTypePattern.matcher(alterObject);

		if (!matcher.find()) {
			return;
		}

		String columnName = matcher.group(1);

		if (!columnNames.contains(columnName)) {
			String message = String.format(
				"The column \"%s\" does not exist in table \"%s\"", columnName,
				tableName);

			addMessage(
				fileName, message,
				getLineNumber(content, start + matcher.start(1)));
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

	private String _formatAlterObjects(
		String tableName, String fileName, String content,
		List<String> alterObjects, List<String> columnNames, int pos) {

		String previousAlterType = null;
		int previousColumnIndex = -1;

		for (int i = 0; i < alterObjects.size(); i++) {
			String alterObject = alterObjects.get(i);

			Matcher matcher = _alterObjectPattern.matcher(alterObject);

			if (!matcher.find()) {
				previousAlterType = null;

				continue;
			}

			String alterType = matcher.group(1);

			if (Objects.equals(alterType, "AlterColumnType")) {
				_checkAlterColumnType(
					content.indexOf(alterObject, matcher.start()), tableName,
					fileName, content, alterObject, columnNames);
			}

			int columnIndex = _getColumnIndex(alterObject, columnNames);

			if (columnIndex == -1) {
				previousAlterType = null;

				continue;
			}

			if ((previousAlterType != null) &&
				previousAlterType.equals(alterType) &&
				(previousColumnIndex > columnIndex)) {

				String previousAlterObject = alterObjects.get(i - 1);

				content = StringUtil.replaceFirst(
					content, alterObject, previousAlterObject, pos);

				return StringUtil.replaceFirst(
					content, previousAlterObject, alterObject, pos);
			}

			previousAlterType = alterType;
			previousColumnIndex = columnIndex;
		}

		return content;
	}

	private int _getColumnIndex(String alterObject, List<String> columnNames) {
		List<String> parameters = JavaSourceUtil.getParameterList(alterObject);

		if (parameters.isEmpty()) {
			return -1;
		}

		Matcher matcher = _stringPattern.matcher(parameters.get(0));

		if (!matcher.find()) {
			return -1;
		}

		String columnName = matcher.group(1);

		for (int i = 0; i < columnNames.size(); i++) {
			if (columnName.equals(columnNames.get(i))) {
				return i;
			}
		}

		return -1;
	}

	private synchronized List<String> _getColumnNames(
			String absolutePath, String tableClassName)
		throws Exception {

		List<String> columnNames = _columnNamesMap.get(tableClassName);

		if (columnNames != null) {
			return columnNames;
		}

		columnNames = new ArrayList<>();

		int x = absolutePath.indexOf("/com/liferay/");

		if (x == -1) {
			_columnNamesMap.put(tableClassName, columnNames);

			return columnNames;
		}

		String fileName = StringBundler.concat(
			absolutePath.substring(0, x + 1),
			StringUtil.replace(tableClassName, '.', '/'), ".java");

		if (!FileUtil.exists(fileName)) {
			_columnNamesMap.put(tableClassName, columnNames);

			return columnNames;
		}

		String fileContent = FileUtil.read(new File(fileName));

		if (!fileContent.contains("@generated")) {
			_columnNamesMap.put(tableClassName, columnNames);

			return columnNames;
		}

		JavaClass javaClass = JavaClassParser.parseJavaClass(
			fileName, fileContent);

		for (JavaTerm javaTerm : javaClass.getChildJavaTerms()) {
			if (!javaTerm.isJavaVariable() ||
				!Objects.equals(javaTerm.getName(), "TABLE_COLUMNS")) {

				continue;
			}

			Matcher matcher = _stringPattern.matcher(javaTerm.getContent());

			while (matcher.find()) {
				columnNames.add(matcher.group(1));
			}

			break;
		}

		_columnNamesMap.put(tableClassName, columnNames);

		return columnNames;
	}

	private String _getTableClassName(String content, String parameter) {
		if (!parameter.endsWith(".class")) {
			return null;
		}

		Pattern pattern = Pattern.compile(
			StringBundler.concat(
				"import ([\\w.]+\\.",
				parameter.substring(0, parameter.length() - 6), ");"));

		Matcher matcher = pattern.matcher(content);

		if (matcher.find()) {
			return matcher.group(1);
		}

		return null;
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
	private static final Pattern _stringPattern = Pattern.compile("\"(\\w+)\"");

	private final Map<String, List<String>> _columnNamesMap = new HashMap<>();

}