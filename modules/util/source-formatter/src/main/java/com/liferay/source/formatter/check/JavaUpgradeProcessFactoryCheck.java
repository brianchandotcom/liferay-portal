/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.BNDSettings;
import com.liferay.source.formatter.check.util.BNDSourceUtil;
import com.liferay.source.formatter.check.util.JavaSourceUtil;
import com.liferay.source.formatter.check.util.SourceUtil;
import com.liferay.source.formatter.util.FileUtil;

import java.io.File;
import java.io.IOException;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Element;

/**
 * @author Alan Huang
 */
public class JavaUpgradeProcessFactoryCheck extends BaseFileCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		return _sortMethodCalls(fileName, absolutePath, content);
	}

	private int _getTableIndex(Element serviceXMLElement, String tableName) {
		int i = 0;

		for (Element entityElement :
				(List<Element>)serviceXMLElement.elements("entity")) {

			if (tableName.equals(entityElement.attributeValue("name"))) {
				return i;
			}

			i++;
		}

		return -1;
	}

	private String _sortMethodCalls(
			String fileName, String absolutePath, String content)
		throws IOException {

		String packagePath = "";

		if (absolutePath.contains("/portal-impl/")) {
			packagePath = "portal-impl";
		}
		else {
			BNDSettings bndSettings = getBNDSettings(fileName);

			if (bndSettings == null) {
				return content;
			}

			String bundleSymbolicName = BNDSourceUtil.getDefinitionValue(
				bndSettings.getContent(), "Bundle-SymbolicName");

			packagePath = StringUtil.removeLast(bundleSymbolicName, ".service");
		}

		Matcher matcher1 = _methodCallsPattern.matcher(content);

		while (matcher1.find()) {
			String methodCallsCodeBlock = matcher1.group();

			String previousColumnName = null;
			String previousMatch = null;
			String previousMethodName = null;
			String previousTableName = null;

			Matcher matcher2 = _methodCallPattern.matcher(methodCallsCodeBlock);

			while (matcher2.find()) {
				String match = matcher2.group();

				String methodName = matcher2.group(1);

				if (!methodName.equals("alterColumnName") &&
					!methodName.equals("alterColumnType")) {

					continue;
				}

				List<String> parameterList = JavaSourceUtil.getParameterList(
					methodCallsCodeBlock.substring(matcher2.start()));

				String columnName = StringUtil.unquote(parameterList.get(1));
				String tableName = StringUtil.unquote(parameterList.get(0));

				if (!methodName.equals(previousMethodName)) {
					previousColumnName = columnName;
					previousMatch = match;
					previousMethodName = methodName;
					previousTableName = tableName;

					continue;
				}

				populateModelInformations();

				Object[] modelInformation = getModelInformation(packagePath);

				if (modelInformation == null) {
					continue;
				}

				String tablesSQLFileLocation = (String)modelInformation[1];

				if (Validator.isNull(tablesSQLFileLocation)) {
					continue;
				}

				File file = new File(tablesSQLFileLocation);

				if (!file.exists()) {
					continue;
				}

				String tablesSQLContent = FileUtil.read(file);

				if (tablesSQLContent == null) {
					continue;
				}

				if (tableName.equals(previousTableName)) {
					int index1 = SourceUtil.getColumnIndex(
						tablesSQLContent, tableName, previousColumnName);
					int index2 = SourceUtil.getColumnIndex(
						tablesSQLContent, tableName, columnName);

					if ((index2 != -1) && (index1 > index2)) {
						int x = matcher2.start();

						int y = content.lastIndexOf(previousMatch, x);

						content = StringUtil.replaceFirst(
							content, match, previousMatch,
							matcher1.start() + x);

						return StringUtil.replaceFirst(
							content, previousMatch, match,
							matcher1.start() + y);
					}
				}
				else {
					int index1 = _getTableIndex(
						(Element)modelInformation[0], previousTableName);
					int index2 = _getTableIndex(
						(Element)modelInformation[0], tableName);

					if ((index2 != -1) && (index1 > index2)) {
						int x = matcher2.start();

						int y = content.lastIndexOf(previousMatch, x);

						content = StringUtil.replaceFirst(
							content, match, previousMatch,
							matcher1.start() + x);

						return StringUtil.replaceFirst(
							content, previousMatch, match,
							matcher1.start() + y);
					}
				}

				previousColumnName = columnName;
				previousMatch = match;
				previousMethodName = methodName;
				previousTableName = tableName;
			}
		}

		return content;
	}

	private static final Pattern _methodCallPattern = Pattern.compile(
		"UpgradeProcessFactory\\.(\\w+)\\([^;]+?\\)(?=,|\\))");
	private static final Pattern _methodCallsPattern = Pattern.compile(
		"([ \\t]*UpgradeProcessFactory\\.\\w+\\([^;]+?\\)(,|\\))\\s*){2,}");

}