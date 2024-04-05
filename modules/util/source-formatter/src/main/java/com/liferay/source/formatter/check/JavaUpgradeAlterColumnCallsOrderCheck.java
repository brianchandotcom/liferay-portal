/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.portal.kernel.util.ListUtil;
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

/**
 * @author Alan Huang
 */
public class JavaUpgradeAlterColumnCallsOrderCheck extends BaseFileCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		if (!absolutePath.contains("/upgrade/")) {
			return content;
		}

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

		content = _sortMethodCallsByTableAndColumnName(
			content, "alterTableAddColumn", packagePath);
		content = _sortMethodCallsByTableAndColumnName(
			content, "UpgradeProcessFactory.alterColumnName", packagePath);
		content = _sortMethodCallsByTableAndColumnName(
			content, "UpgradeProcessFactory.alterColumnType", packagePath);

		return content;
	}

	private String _sortMethodCallsByTableAndColumnName(
			String content, String methodName, String packagePath)
		throws IOException {

		Pattern pattern = Pattern.compile("\n(\t+" + methodName + "\\()");

		Matcher matcher = pattern.matcher(content);

		while (matcher.find()) {
			String methodCall1 = JavaSourceUtil.getMethodCall(
				content, matcher.start(1));

			List<String> parameterNames1 = JavaSourceUtil.getParameterNames(
				methodCall1);

			if (ListUtil.isEmpty(parameterNames1) ||
				(parameterNames1.size() != 3)) {

				continue;
			}

			int x = matcher.start(1) + methodCall1.length();

			String followingContent = content.substring(x);

			if (!followingContent.startsWith(",\n") &&
				!followingContent.startsWith(";\n")) {

				continue;
			}

			followingContent = followingContent.substring(2);

			if (!followingContent.startsWith(matcher.group(1))) {
				continue;
			}

			String methodCall2 = JavaSourceUtil.getMethodCall(content, x + 2);

			List<String> parameterNames2 = JavaSourceUtil.getParameterNames(
				methodCall2);

			if (ListUtil.isEmpty(parameterNames2) ||
				(parameterNames2.size() != 3)) {

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

			String tableName1 = StringUtil.unquote(parameterNames1.get(0));
			String tableName2 = StringUtil.unquote(parameterNames2.get(0));

			if (!tableName1.equals(tableName2)) {
				continue;
			}

			int index1 = SourceUtil.getColumnIndex(
				tablesSQLContent, tableName1,
				StringUtil.unquote(parameterNames1.get(1)));
			int index2 = SourceUtil.getColumnIndex(
				tablesSQLContent, tableName1,
				StringUtil.unquote(parameterNames2.get(1)));

			if ((index2 != -1) && ((index1 > index2) || (index1 == -1))) {
				content = StringUtil.replaceFirst(
					content, methodCall2, methodCall1, matcher.start());

				return StringUtil.replaceFirst(
					content, methodCall1, methodCall2, matcher.start());
			}
		}

		return content;
	}

}