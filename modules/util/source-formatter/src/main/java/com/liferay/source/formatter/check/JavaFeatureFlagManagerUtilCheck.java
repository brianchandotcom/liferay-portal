/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.util.JavaSourceUtil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alan Huang
 */
public class JavaFeatureFlagManagerUtilCheck extends BaseFileCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		int pos = fileName.lastIndexOf(StringPool.SLASH);

		String shortFileName = fileName.substring(pos + 1);

		if (absolutePath.contains("/feature-flag/") ||
			shortFileName.equals("FeatureFlagManagerUtil.java")) {

			return content;
		}

		_checkGetterUtilGetBooleanMethodCall(fileName, content);
		_checkIsEnabledMethodCall(fileName, content);

		return content;
	}

	private void _checkGetterUtilGetBooleanMethodCall(
		String fileName, String content) {

		Matcher matcher = _getterUtilGetBooleanPattern.matcher(content);

		while (matcher.find()) {
			List<String> parameterList = JavaSourceUtil.getParameterList(
				JavaSourceUtil.getMethodCall(content, matcher.start()));

			if (parameterList.size() != 1) {
				continue;
			}

			String parameter = parameterList.get(0);

			if (!parameter.startsWith("PropsUtil.get(")) {
				continue;
			}

			parameterList = JavaSourceUtil.getParameterList(
				JavaSourceUtil.getMethodCall(parameter, 0));

			if (parameterList.size() != 1) {
				continue;
			}

			if (StringUtil.startsWith(
					parameterList.get(0), "\"feature.flag.")) {

				addMessage(
					fileName,
					"Use \"FeatureFlagManagerUtil.isEnabled\" instead of " +
						"\"PropsUtil.get\" for feature flag",
					getLineNumber(content, matcher.start()));
			}
		}
	}

	private void _checkIsEnabledMethodCall(String fileName, String content) {
		Matcher matcher = _isEnabledPattern.matcher(content);

		while (matcher.find()) {
			String variableTypeName = getVariableTypeName(
				content, null, content, fileName, matcher.group(1));

			if (variableTypeName == null) {
				continue;
			}

			if (variableTypeName.equals("FeatureFlagManager")) {
				addMessage(
					fileName,
					"Use \"FeatureFlagManagerUtil.isEnabled\" instead",
					getLineNumber(content, matcher.start()));
			}
		}
	}

	private static final Pattern _getterUtilGetBooleanPattern = Pattern.compile(
		"GetterUtil\\.getBoolean\\(");
	private static final Pattern _isEnabledPattern = Pattern.compile(
		"\\W(\\w+)\\.isEnabled\\(");

}