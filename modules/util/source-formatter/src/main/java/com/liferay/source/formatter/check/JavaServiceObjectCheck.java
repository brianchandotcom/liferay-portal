/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.check.util.SourceUtil;
import com.liferay.source.formatter.parser.JavaTerm;
import com.liferay.source.formatter.util.FileUtil;

import java.io.File;
import java.io.IOException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Element;

/**
 * @author Hugo Huijser
 */
public class JavaServiceObjectCheck extends BaseJavaTermCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, JavaTerm javaTerm,
			String fileContent)
		throws IOException {

		List<String> importNames = getImportNames(javaTerm);

		if (importNames.isEmpty()) {
			return javaTerm.getContent();
		}

		String javaTermContent = _formatGetterMethodCalls(
			javaTerm, fileContent, fileName, importNames);

		return _formatSetterMethodCalls(
			javaTerm, javaTermContent, fileContent, fileName, importNames);
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_METHOD};
	}

	private String _formatGetterMethodCalls(
			JavaTerm javaTerm, String fileContent, String fileName,
			List<String> importNames)
		throws IOException {

		String content = javaTerm.getContent();

		Matcher matcher = _getterCallPattern.matcher(content);

		while (matcher.find()) {
			String variableName = matcher.group(1);

			String variableTypeName = getVariableTypeName(
				content, javaTerm, fileContent, fileName, variableName, false,
				true);

			if (variableTypeName == null) {
				continue;
			}

			String getterObjectName = TextFormatter.format(
				matcher.group(3), TextFormatter.I);

			if (_isBooleanColumn(
					variableTypeName, getterObjectName, importNames)) {

				return StringUtil.replaceFirst(
					content, "get", "is", matcher.start(2));
			}
		}

		return content;
	}

	private String _formatSetterMethodCalls(
			JavaTerm javaTerm, String content, String fileContent,
			String fileName, List<String> importNames)
		throws IOException {

		Matcher matcher1 = _setterCallsPattern.matcher(content);

		outerLoop:
		while (matcher1.find()) {
			String setterCallsCodeBlock = matcher1.group();

			String previousMatch = null;
			String previousSetterObjectName = null;
			String previousVariableName = null;
			String variableTypeName = null;

			Matcher matcher2 = _setterCallPattern.matcher(setterCallsCodeBlock);

			while (matcher2.find()) {
				String match = matcher2.group();
				String setterObjectName = TextFormatter.format(
					matcher2.group(2), TextFormatter.I);
				String variableName = matcher2.group(1);

				if (!variableName.equals(previousVariableName)) {
					previousMatch = match;
					previousSetterObjectName = setterObjectName;
					previousVariableName = variableName;

					variableTypeName = getVariableTypeName(
						content, javaTerm, fileContent, fileName, variableName,
						false, true);

					if (variableTypeName == null) {
						continue outerLoop;
					}

					continue;
				}

				populateModelInformations();

				Object[] modelInformation = getModelInformation(
					_getPackagePath(variableTypeName, importNames));

				if (modelInformation == null) {
					continue outerLoop;
				}

				Element serviceXMLElement = (Element)modelInformation[0];

				if (serviceXMLElement == null) {
					continue outerLoop;
				}

				String tablesSQLFileLocation = (String)modelInformation[1];

				if (Validator.isNull(tablesSQLFileLocation)) {
					continue outerLoop;
				}

				File file = new File(tablesSQLFileLocation);

				if (!file.exists()) {
					continue outerLoop;
				}

				String tablesSQLContent = FileUtil.read(file);

				if (tablesSQLContent == null) {
					continue outerLoop;
				}

				int x = variableTypeName.lastIndexOf(StringPool.PERIOD);

				if (x != -1) {
					variableTypeName = variableTypeName.substring(x + 1);
				}

				String[] parts = StringUtil.split(
					_getTableAndColumName(
						variableTypeName, previousSetterObjectName,
						serviceXMLElement),
					":");

				int index1 = -1;

				if (parts.length == 2) {
					index1 = SourceUtil.getColumnIndex(
						tablesSQLContent, parts[0], parts[1]);
				}

				parts = StringUtil.split(
					_getTableAndColumName(
						variableTypeName, setterObjectName, serviceXMLElement),
					":");

				int index2 = -1;

				if (parts.length == 2) {
					index2 = SourceUtil.getColumnIndex(
						tablesSQLContent, parts[0], parts[1]);
				}

				if ((index2 != -1) && ((index1 > index2) || (index1 == -1))) {
					x = matcher2.start();

					int y = content.lastIndexOf(previousMatch, x);

					content = StringUtil.replaceFirst(
						content, match, previousMatch, matcher1.start() + x);

					return StringUtil.replaceFirst(
						content, previousMatch, match, matcher1.start() + y);
				}

				previousMatch = match;
				previousSetterObjectName = setterObjectName;
				previousVariableName = variableName;
			}
		}

		return content;
	}

	private String _getPackagePath(
		String variableTypeName, List<String> importNames) {

		int x = variableTypeName.lastIndexOf(StringPool.PERIOD);

		if (x != -1) {
			String packageName = variableTypeName.substring(0, x);

			if (packageName.startsWith("com.liferay.") &&
				packageName.endsWith(".model")) {

				return StringUtil.replaceLast(
					packageName, ".model", StringPool.BLANK);
			}

			return StringPool.BLANK;
		}

		for (String importName : importNames) {
			if (importName.startsWith("com.liferay.") &&
				importName.endsWith(".model." + variableTypeName)) {

				return StringUtil.replaceLast(
					importName, ".model." + variableTypeName, StringPool.BLANK);
			}
		}

		return StringPool.BLANK;
	}

	private String _getTableAndColumName(
		String variableTypeName, String setterObjectName,
		Element serviceXMLElement) {

		String columnName = null;
		String dbName = null;
		String tableName = variableTypeName;

		for (Element entityElement :
				(List<Element>)serviceXMLElement.elements("entity")) {

			if (!tableName.equals(entityElement.attributeValue("name"))) {
				continue;
			}

			if (Validator.isNotNull(entityElement.attributeValue("table"))) {
				tableName = entityElement.attributeValue("table");
			}

			String convertedSetterObjectName = setterObjectName.replaceFirst(
				"^(create|modified)Date$", "$1Time");

			convertedSetterObjectName = convertedSetterObjectName.replaceFirst(
				"(.+?)(List|Map|(Unicode)?Properties)$", "$1");

			Map<String, String> columnNamesMap = new HashMap<>();

			for (Element columnElement :
					(List<Element>)entityElement.elements("column")) {

				columnNamesMap.put(
					StringUtil.toLowerCase(
						columnElement.attributeValue("name")),
					columnElement.attributeValue("db-name"));
			}

			String lowerCaseName = StringUtil.toLowerCase(setterObjectName);

			if (columnNamesMap.containsKey(lowerCaseName)) {
				columnName = setterObjectName;
				dbName = columnNamesMap.get(lowerCaseName);

				break;
			}

			lowerCaseName = StringUtil.toLowerCase(convertedSetterObjectName);

			if (columnNamesMap.containsKey(lowerCaseName)) {
				columnName = convertedSetterObjectName;
				dbName = columnNamesMap.get(lowerCaseName);

				break;
			}

			lowerCaseName = StringUtil.toLowerCase(setterObjectName + "Id");

			if (!setterObjectName.endsWith("Id") &&
				columnNamesMap.containsKey(lowerCaseName)) {

				columnName = setterObjectName + "Id";
				dbName = columnNamesMap.get(lowerCaseName);

				break;
			}
		}

		if (columnName == null) {
			return tableName + ":" + setterObjectName;
		}

		if (dbName != null) {
			return tableName + ":" + dbName;
		}

		return tableName + ":" + columnName;
	}

	private boolean _isBooleanColumn(
			String variableTypeName, String getterObjectName,
			List<String> importNames)
		throws IOException {

		populateModelInformations();

		Object[] modelInformation = getModelInformation(
			_getPackagePath(variableTypeName, importNames));

		if (modelInformation == null) {
			return false;
		}

		int x = variableTypeName.lastIndexOf(StringPool.PERIOD);

		if (x != -1) {
			variableTypeName = variableTypeName.substring(x + 1);
		}

		Element serviceXMLElement = (Element)modelInformation[0];

		if (serviceXMLElement == null) {
			return false;
		}

		for (Element entityElement :
				(List<Element>)serviceXMLElement.elements("entity")) {

			if (!variableTypeName.equals(
					entityElement.attributeValue("name"))) {

				continue;
			}

			for (Element columnElement :
					(List<Element>)entityElement.elements("column")) {

				if (getterObjectName.equals(
						columnElement.attributeValue("name")) &&
					Objects.equals(
						columnElement.attributeValue("type"), "boolean")) {

					return true;
				}
			}
		}

		return false;
	}

	private static final Pattern _getterCallPattern = Pattern.compile(
		"\\W(\\w+)\\.\\s*(get)([A-Z]\\w*)\\(\\)");
	private static final Pattern _setterCallPattern = Pattern.compile(
		"(\\w+)\\.\\s*set([A-Z]\\w*)\\([^;]+;");
	private static final Pattern _setterCallsPattern = Pattern.compile(
		"(^[ \t]*\\w+\\.\\s*set[A-Z]\\w*\\([^;]+;\n)+",
		Pattern.DOTALL | Pattern.MULTILINE);

}