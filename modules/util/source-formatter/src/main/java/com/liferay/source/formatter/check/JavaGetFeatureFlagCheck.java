/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.util.JavaSourceUtil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alan Huang
 */
public class JavaGetFeatureFlagCheck extends BaseFileCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		Matcher matcher1 = _getterUtilGetBooleanPattern.matcher(content);

		while (matcher1.find()) {
			List<String> parameterList = JavaSourceUtil.getParameterList(
				JavaSourceUtil.getMethodCall(content, matcher1.start()));

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
					"Use 'FeatureFlagManagerUtil.isEnabled' instead of " +
						"'PropsUtil.get' for feature flag",
					getLineNumber(content, matcher1.start()));
			}
		}

		return content;
	}

	private static final Pattern _getterUtilGetBooleanPattern = Pattern.compile(
		"GetterUtil\\.getBoolean\\(");

}