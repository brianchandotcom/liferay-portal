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
import com.liferay.source.formatter.parser.JavaTerm;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaLogParametersCheck extends BaseJavaTermCheck {

	@Override
	public boolean isPortalCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, JavaTerm javaTerm,
		String fileContent) {

		String content = javaTerm.getContent();

		Matcher matcher = _logPattern.matcher(content);

		while (matcher.find()) {
			if (ToolsUtil.isInsideQuotes(content, matcher.start())) {
				continue;
			}

			List<String> parametersList = JavaSourceUtil.getParameterList(
				matcher.group());

			if (parametersList.isEmpty()) {
				continue;
			}

			String firstParameter = StringUtil.trim(parametersList.get(0));

			if (!Validator.isVariableName(firstParameter)) {
				continue;
			}

			String variableTypeName = getVariableTypeName(
				content, fileContent, firstParameter);

			if (variableTypeName == null) {
				continue;
			}

			if (variableTypeName.equals("StringBundler")) {
				return StringUtil.replaceFirst(
					content, firstParameter, firstParameter + ".toString()",
					matcher.start(2));
			}

			if ((parametersList.size() == 1) &&
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

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CONSTRUCTOR, JAVA_METHOD};
	}

	private final Pattern _logPattern = Pattern.compile(
		"_log\\.(debug|error|fatal|info|trace|warn)\\((.+?)\\);\n",
		Pattern.DOTALL);

}