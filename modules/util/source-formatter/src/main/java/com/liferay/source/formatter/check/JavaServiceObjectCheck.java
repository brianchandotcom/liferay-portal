/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.check.util.SourceUtil;
import com.liferay.source.formatter.parser.JavaTerm;
import com.liferay.source.formatter.util.FileUtil;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import java.io.File;
import java.io.IOException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
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
			javaTerm, fileContent, fileName, absolutePath, importNames);

		return _formatSetterMethodCalls(
			javaTerm, javaTermContent, fileContent, fileName, absolutePath,
			importNames);
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_METHOD};
	}

	private String _formatGetterMethodCalls(
			JavaTerm javaTerm, String fileContent, String fileName,
			String absolutePath, List<String> importNames)
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
					absolutePath, variableTypeName, getterObjectName,
					importNames)) {

				return StringUtil.replaceFirst(
					content, "get", "is", matcher.start(2));
			}
		}

		return content;
	}

	private String _formatSetterMethodCalls(
			JavaTerm javaTerm, String content, String fileContent,
			String fileName, String absolutePath, List<String> importNames)
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

				Object[] modelInformation = _getModelInformation(
					_packagePath(absolutePath, variableTypeName, importNames));

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

				String tableName = _getTableName(
					variableTypeName, serviceXMLElement);

				int index1 = _getColumnIndex(
					tablesSQLContent, tableName, previousSetterObjectName);
				int index2 = _getColumnIndex(
					tablesSQLContent, tableName, setterObjectName);

				if ((index2 != -1) && (index1 > index2)) {
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

	private int _getColumnIndex(
		String tablesSQLContent, String tableName, String columnName) {

		String tableSQL = _getTableSQL(tablesSQLContent, tableName);

		if (tableSQL == null) {
			return -1;
		}

		Pattern pattern = Pattern.compile(
			StringBundler.concat(
				"(?i)\n\\s*", columnName, "_?\\s+([\\w\\(\\)]+)[\\s,]"));

		Matcher matcher = pattern.matcher(tableSQL);

		if (matcher.find()) {
			return matcher.start();
		}

		return -1;
	}

	private synchronized Object[] _getModelInformation(String packagePath) {
		if (_modelInformationsMap != null) {
			return _modelInformationsMap.get(packagePath);
		}

		_modelInformationsMap = new HashMap<>();

		try {
			_populateTablesSQLFileLocations();
		}
		catch (IOException ioException) {
			if (_log.isDebugEnabled()) {
				_log.debug(ioException);
			}

			return null;
		}

		return _modelInformationsMap.get(packagePath);
	}

	private String _getTableName(
		String variableTypeName, Element serviceXMLElement) {

		for (Element entityElement :
				(List<Element>)serviceXMLElement.elements("entity")) {

			if (!variableTypeName.equals(
					entityElement.attributeValue("name"))) {

				continue;
			}

			String tableName = entityElement.attributeValue("table");

			if (Validator.isNotNull(tableName)) {
				return tableName;
			}
		}

		return variableTypeName;
	}

	private String _getTableSQL(String tablesSQLContent, String tableName) {
		Pattern pattern = Pattern.compile("create table " + tableName + "_? ");

		Matcher matcher = pattern.matcher(tablesSQLContent);

		if (!matcher.find()) {
			return null;
		}

		int x = tablesSQLContent.indexOf(");", matcher.start());

		if (x == -1) {
			return null;
		}

		return tablesSQLContent.substring(matcher.start(), x + 1);
	}

	private boolean _isBooleanColumn(
			String absolutePath, String variableTypeName,
			String getterObjectName, List<String> importNames)
		throws IOException {

		Object[] modelInformation = _getModelInformation(
			_packagePath(absolutePath, variableTypeName, importNames));

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

	private String _packagePath(
		String absolutePath, String variableTypeName,
		List<String> importNames) {

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

		if (absolutePath.contains("/portal-impl/")) {
			return "portal-impl";
		}

		return StringPool.BLANK;
	}

	private void _populateTablesSQLFileLocations() throws IOException {
		File portalDir = getPortalDir();

		List<String> serviceXMLFileNames = SourceFormatterUtil.scanForFileNames(
			portalDir.getCanonicalPath(), new String[] {"**/service.xml"});

		for (String serviceXMLFileName : serviceXMLFileNames) {
			Document serviceXMLDocument = SourceUtil.readXML(
				FileUtil.read(new File(serviceXMLFileName)));

			if (serviceXMLDocument == null) {
				continue;
			}

			Element serviceXMLElement = serviceXMLDocument.getRootElement();

			serviceXMLFileName = StringUtil.replace(
				serviceXMLFileName, CharPool.BACK_SLASH, CharPool.SLASH);

			String packagePath = "";
			String tablesSQLFilePath = "";

			if (serviceXMLFileName.contains("/portal-impl/")) {
				packagePath = "portal-impl";
				tablesSQLFilePath = portalDir + "/sql/portal-tables.sql";
			}
			else {
				packagePath = serviceXMLElement.attributeValue("package-path");

				int x = serviceXMLFileName.lastIndexOf("/");

				tablesSQLFilePath =
					serviceXMLFileName.substring(0, x) +
						"/src/main/resources/META-INF/sql/tables.sql";
			}

			_modelInformationsMap.put(
				packagePath,
				new Object[] {serviceXMLElement, tablesSQLFilePath});
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JavaServiceObjectCheck.class);

	private static final Pattern _getterCallPattern = Pattern.compile(
		"\\W(\\w+)\\.\\s*(get)([A-Z]\\w*)\\(\\)");
	private static final Pattern _setterCallPattern = Pattern.compile(
		"(\\w+)\\.\\s*set([A-Z]\\w*)\\([^;]+;");
	private static final Pattern _setterCallsPattern = Pattern.compile(
		"(^[ \t]*\\w+\\.\\s*set[A-Z]\\w*\\([^;]+;\n)+",
		Pattern.DOTALL | Pattern.MULTILINE);

	private Map<String, Object[]> _modelInformationsMap;

}