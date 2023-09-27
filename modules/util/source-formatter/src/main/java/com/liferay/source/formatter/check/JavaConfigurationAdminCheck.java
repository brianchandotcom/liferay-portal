/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.source.formatter.check.util.JavaSourceUtil;

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