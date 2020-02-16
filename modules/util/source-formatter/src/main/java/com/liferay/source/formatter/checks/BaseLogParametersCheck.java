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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.checks.util.JavaSourceUtil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public abstract class BaseLogParametersCheck extends BaseFileCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	protected String formatLogParameters(String content) {
		Matcher matcher = _logPattern.matcher(content);

		while (matcher.find()) {
			if (ToolsUtil.isInsideQuotes(content, matcher.start()) ||
				!isJavaSource(content, matcher.start())) {

				continue;
			}

			List<String> parameterList = JavaSourceUtil.getParameterList(
				matcher.group());

			if (parameterList.isEmpty()) {
				continue;
			}

			String firstParameter = StringUtil.trim(parameterList.get(0));

			if (!Validator.isVariableName(firstParameter)) {
				continue;
			}

			String variableTypeName = getVariableTypeName(
				content, content, firstParameter);

			if (variableTypeName == null) {
				continue;
			}

			if (variableTypeName.equals("StringBundler")) {
				return StringUtil.replaceFirst(
					content, firstParameter, firstParameter + ".toString()",
					matcher.start(2));
			}

			if ((parameterList.size() == 1) &&
				variableTypeName.endsWith("Exception")) {

				return StringUtil.replaceFirst(
					content, firstParameter,
					StringBundler.concat(
						firstParameter, StringPool.COMMA_AND_SPACE,
						firstParameter),
					matcher.start(2));
			}
		}

		return content;
	}

	protected boolean isJavaSource(String content, int pos) {
		return true;
	}

	private static final Pattern _logPattern = Pattern.compile(
		"_log\\.(debug|error|fatal|info|trace|warn)\\((.+?)\\);\n",
		Pattern.DOTALL);

}