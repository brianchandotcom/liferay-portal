/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.check.util.JavaSourceUtil;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaAggregateTestRuleParameterOrderCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		Matcher matcher = _aggregateTestRulePattern.matcher(content);

		while (matcher.find()) {
			List<String> parameterList = JavaSourceUtil.getParameterList(
				content.substring(matcher.start()));

			if (Objects.equals(parameterList.get(0), "false")) {
				return content;
			}

			for (int i = 1; i < parameterList.size(); i++) {
				String parameter = parameterList.get(i);

				String className = _getClassName(parameter);

				String previousParameter = parameterList.get(i - 1);

				String previousClassName = _getClassName(previousParameter);

				if (Validator.isNotNull(className) &&
					Validator.isNotNull(previousClassName) &&
					(previousClassName.compareTo(className) > 0)) {

					content = StringUtil.replaceFirst(
						content, parameter, previousParameter, matcher.start());
					content = StringUtil.replaceFirst(
						content, previousParameter, parameter, matcher.start());

					break;
				}
			}
		}

		return content;
	}

	private String _getClassName(String parameterName) {
		Matcher matcher = _classNamePattern.matcher(parameterName);

		if (matcher.find()) {
			return matcher.group(2);
		}

		return null;
	}

	private static final Pattern _aggregateTestRulePattern = Pattern.compile(
		"new AggregateTestRule\\(");
	private static final Pattern _classNamePattern = Pattern.compile(
		"(new )?([A-Z]\\w*?)[\\.\\(]");

}