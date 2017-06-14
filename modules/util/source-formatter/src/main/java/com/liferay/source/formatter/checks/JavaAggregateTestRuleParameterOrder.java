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

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.checks.util.JavaSourceUtil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaAggregateTestRuleParameterOrder extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		Matcher matcher = _aggregateTestRulePattern.matcher(content);

		while (matcher.find()) {
			List<String> parametersList = JavaSourceUtil.getParameterList(
				content.substring(matcher.start()));

			if ("false".equals(parametersList.get(0))) {
				return content;
			}

			for (int i = 1; i < parametersList.size(); i++) {
				String parameter = parametersList.get(i);

				String className = _getClassName(parameter);

				String previousParameter = parametersList.get(i - 1);

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

	private final Pattern _aggregateTestRulePattern = Pattern.compile(
		"new AggregateTestRule\\(");
	private final Pattern _classNamePattern = Pattern.compile(
		"(new )?([A-Z]\\w*?)[\\.\\(]");

}