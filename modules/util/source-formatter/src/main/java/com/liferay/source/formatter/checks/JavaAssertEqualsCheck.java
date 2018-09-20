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

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.checks.util.JavaSourceUtil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaAssertEqualsCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (fileName.endsWith("Test.java")) {
			content = _formatAssertEquals(content);
		}

		return content;
	}

	private String _formatAssertEquals(String content) {
		Matcher matcher = _assertEqualsPattern.matcher(content);

		while (matcher.find()) {
			String parameters = StringUtil.trim(matcher.group(1));

			List<String> parametersList = JavaSourceUtil.splitParameters(
				parameters);

			if (parametersList.size() != 2) {
				continue;
			}

			String actualParameter = parametersList.get(1);

			String strippedQuotesActualParameter = stripQuotes(actualParameter);

			if (!actualParameter.startsWith("expected") &&
				!Validator.isDigit(actualParameter) &&
				Validator.isNotNull(strippedQuotesActualParameter)) {

				continue;
			}

			String assertEquals = matcher.group();
			String expectedParameter = parametersList.get(0);

			String newAssertEquals = StringUtil.replaceFirst(
				assertEquals, expectedParameter, actualParameter,
				assertEquals.indexOf(CharPool.OPEN_PARENTHESIS));

			newAssertEquals = StringUtil.replaceLast(
				newAssertEquals, actualParameter, expectedParameter);

			return StringUtil.replace(content, assertEquals, newAssertEquals);
		}

		return content;
	}

	private static final Pattern _assertEqualsPattern = Pattern.compile(
		"Assert\\.assertEquals\\((.*?)\\);\n", Pattern.DOTALL);

}