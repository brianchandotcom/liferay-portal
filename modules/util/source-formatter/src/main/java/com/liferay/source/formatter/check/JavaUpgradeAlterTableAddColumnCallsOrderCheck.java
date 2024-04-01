/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.BNDSettings;
import com.liferay.source.formatter.check.util.BNDSourceUtil;
import com.liferay.source.formatter.check.util.JavaSourceUtil;
import com.liferay.source.formatter.check.util.SourceUtil;
import com.liferay.source.formatter.util.FileUtil;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import java.io.File;
import java.io.IOException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * @author Alan Huang
 */
public class JavaUpgradeAlterTableAddColumnCallsOrderCheck
	extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		if (!fileName.endsWith("UpgradeProcess.java") ||
			!absolutePath.contains("-service/")) {

			return content;
		}

		BNDSettings bndSettings = getBNDSettings(fileName);

		if (bndSettings == null) {
			return content;
		}

		String bundleSymbolicName = BNDSourceUtil.getDefinitionValue(
			bndSettings.getContent(), "Bundle-SymbolicName");

		bundleSymbolicName = StringUtil.replace(
			bundleSymbolicName, ".service", ".model");

		Matcher matcher = _alterTableAddColumnCallPattern.matcher(content);

		while (matcher.find()) {
			String methodCall1 = _getMethodCall(content, matcher.start(1));

			if (methodCall1 == null) {
				continue;
			}

			List<String> parameterNames1 = JavaSourceUtil.getParameterNames(
				methodCall1);

			if (ListUtil.isEmpty(parameterNames1) ||
				(parameterNames1.size() != 3)) {

				continue;
			}

			int x = matcher.start(1) + methodCall1.length();

			String followingContent = content.substring(x);

			if (!followingContent.startsWith(matcher.group(1))) {
				continue;
			}

			String methodCall2 = _getMethodCall(content, x);

			if (methodCall2 == null) {
				continue;
			}

			List<String> parameterNames2 = JavaSourceUtil.getParameterNames(
				methodCall2);

			if (ListUtil.isEmpty(parameterNames2) ||
				(parameterNames2.size() != 3)) {

				continue;
			}

			Object[] modelInformation = _getModelInformation(
				bundleSymbolicName);

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

			String tableName = StringUtil.unquote(parameterNames1.get(0));

			int index1 = _getColumnIndex(
				tablesSQLContent, tableName,
				StringUtil.unquote(parameterNames1.get(1)));
			int index2 = _getColumnIndex(
				tablesSQLContent, tableName,
				StringUtil.unquote(parameterNames2.get(1)));

			if ((index2 != -1) && (index1 > index2)) {
				content = StringUtil.replaceFirst(
					content, methodCall2, methodCall1, matcher.start());

				return StringUtil.replaceFirst(
					content, methodCall1, methodCall2, matcher.start());
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

	private String _getMethodCall(String content, int start) {
		int end = start;

		while (true) {
			end = content.indexOf(");\n", end + 1);

			if (end == -1) {
				return null;
			}

			String methodCall = content.substring(start, end + 3);

			if (getLevel(methodCall) == 0) {
				return methodCall;
			}
		}
	}

	private synchronized Object[] _getModelInformation(String packageName) {
		if (_modelInformationsMap != null) {
			return _modelInformationsMap.get(packageName);
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

		return _modelInformationsMap.get(packageName);
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

	private void _populateTablesSQLFileLocations() throws IOException {
		File file = getPortalDir();

		List<String> serviceXMLFileNames = SourceFormatterUtil.scanForFileNames(
			file.getCanonicalPath(), new String[] {"**/service.xml"});

		for (String serviceXMLFileName : serviceXMLFileNames) {
			Document serviceXMLDocument = SourceUtil.readXML(
				FileUtil.read(new File(serviceXMLFileName)));

			if (serviceXMLDocument == null) {
				continue;
			}

			Element serviceXMLElement = serviceXMLDocument.getRootElement();

			String packagePath = serviceXMLElement.attributeValue(
				"api-package-path");

			if (packagePath == null) {
				packagePath = serviceXMLElement.attributeValue("package-path");
			}

			if (packagePath == null) {
				continue;
			}

			serviceXMLFileName = StringUtil.replace(
				serviceXMLFileName, CharPool.BACK_SLASH, CharPool.SLASH);

			String tablesSQLFilePath = "";

			if (serviceXMLFileNames.contains("portal-impl/")) {
				tablesSQLFilePath =
					SourceUtil.getRootDirName(serviceXMLFileName) +
						"/sql/portal-tables.sql";
			}
			else {
				int x = serviceXMLFileName.lastIndexOf("/");

				tablesSQLFilePath =
					serviceXMLFileName.substring(0, x) +
						"/src/main/resources/META-INF/sql/tables.sql";
			}

			_modelInformationsMap.put(
				packagePath + ".model",
				new Object[] {serviceXMLElement, tablesSQLFilePath});
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JavaUpgradeAlterTableAddColumnCallsOrderCheck.class);

	private static final Pattern _alterTableAddColumnCallPattern =
		Pattern.compile("\n(\t+alterTableAddColumn\\()");

	private Map<String, Object[]> _modelInformationsMap;

}