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
import com.liferay.source.formatter.checks.util.JavaSourceUtil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaConfigurationAdminCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (fileName.contains("/test/") ||
			fileName.contains("/testIntegration/")) {

			return content;
		}

		Matcher matcher = _getConfigurationPattern.matcher(content);

		while (matcher.find()) {
			List<String> parameterList = JavaSourceUtil.getParameterList(
				content.substring(matcher.start()));

			if (parameterList.size() == 2) {
				String parameterName = parameterList.get(1);

				if (parameterName.equals("StringPool.QUESTION") ||
					parameterName.equals("\"?\"")) {

					continue;
				}
			}

			StringBundler sb = new StringBundler(5);

			sb.append("Incorrect call to '");
			sb.append(matcher.group(1));
			sb.append(StringPool.PERIOD);
			sb.append(matcher.group(2));
			sb.append(StringPool.APOSTROPHE);

			addMessage(
				fileName, sb.toString(),
				getLineNumber(content, matcher.start()));
		}

		return content;
	}

	private static final Pattern _getConfigurationPattern = Pattern.compile(
		"\\W_?([cC]onfigurationAdmin)\\.\\s*((get|createFactory)" +
			"Configuration)\\(");

}