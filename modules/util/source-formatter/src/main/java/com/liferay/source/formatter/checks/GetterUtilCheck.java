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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.checks.util.JavaSourceUtil;

import java.lang.reflect.Field;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class GetterUtilCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (!fileName.endsWith("GetterUtilTest.java")) {
			_checkGetterUtilGet(fileName, content);
		}

		return content;
	}

	private void _checkGetterUtilGet(String fileName, String content)
		throws Exception {

		Matcher matcher = _getterUtilGetPattern.matcher(content);

		while (matcher.find()) {
			if (ToolsUtil.isInsideQuotes(content, matcher.start())) {
				continue;
			}

			List<String> parametersList = JavaSourceUtil.getParameterList(
				matcher.group());

			if (parametersList.size() != 2) {
				continue;
			}

			String defaultVariableName =
				"DEFAULT_" + StringUtil.toUpperCase(matcher.group(1));

			Field defaultValuefield = GetterUtil.class.getDeclaredField(
				defaultVariableName);

			String defaultValue = String.valueOf(defaultValuefield.get(null));

			defaultValue = defaultValue.replaceFirst("\\.0", StringPool.BLANK);

			String value = parametersList.get(1);

			value = value.replaceFirst("0(\\.0)?[dDfFlL]?", "0");

			if (value.equals("StringPool.BLANK")) {
				value = StringPool.BLANK;
			}

			if (Objects.equals(value, defaultValue)) {
				addMessage(
					fileName,
					"No need to pass default value '" + parametersList.get(1) +
						"'",
					getLineCount(content, matcher.start()));
			}
		}
	}

	private final Pattern _getterUtilGetPattern = Pattern.compile(
		"GetterUtil\\.get(Boolean|Double|Float|Integer|Long|Number|Object|" +
			"Short|String)\\((.*?)\\);\n",
		Pattern.DOTALL);

}