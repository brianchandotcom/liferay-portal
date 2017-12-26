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
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.checks.util.JavaSourceUtil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaLogStringBundlerCheck extends BaseFileCheck {

	@Override
	public boolean isPortalCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		Matcher matcher1 = _logPattern.matcher(content);

		while (matcher1.find()) {
			if (ToolsUtil.isInsideQuotes(content, matcher1.start())) {
				continue;
			}

			List<String> parametersList = JavaSourceUtil.getParameterList(
				matcher1.group());

			String firstParameter = StringUtil.trim(parametersList.get(0));

			Matcher matcher2 = _sbPattern.matcher(firstParameter);

			if (matcher2.find()) {
				String sbVariableName = matcher2.group(2);

				String newFirstParameter = StringUtil.replaceFirst(
					firstParameter, sbVariableName,
					sbVariableName + ".toString()", matcher2.start(2));

				return StringUtil.replaceFirst(
					content, firstParameter, newFirstParameter,
					matcher1.start());
			}
		}

		return content;
	}

	private final Pattern _logPattern = Pattern.compile(
		"_log\\.(debug|error|fatal|info|trace|warn)\\((.+?)\\);\n",
		Pattern.DOTALL);
	private final Pattern _sbPattern = Pattern.compile(
		"^(.*\\+\\s+)?(_?(sb|\\w*SB)([0-9]*)?)(\\w\\+.+)?$", Pattern.DOTALL);

}