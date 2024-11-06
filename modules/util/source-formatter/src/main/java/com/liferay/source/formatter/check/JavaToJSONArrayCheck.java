/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
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
public class JavaToJSONArrayCheck extends BaseFileCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		Matcher matcher = _toJSONArrayPattern.matcher(content);

		while (matcher.find()) {
			List<String> parameterList = JavaSourceUtil.getParameterList(
				JavaSourceUtil.getMethodCall(content, matcher.start()));

			if (parameterList.size() != 1) {
				continue;
			}

			String parameter = parameterList.get(0);

			if (!parameter.startsWith("\"['") || !parameter.endsWith("']\"")) {
				continue;
			}

			String newParameter = StringUtil.replace(
				parameter, new String[] {"' ,'", "','"},
				new String[] {"', '", "', '"});

			if (!parameter.equals(newParameter)) {
				return StringUtil.replaceFirst(
					content, parameter, newParameter, matcher.end());
			}
		}

		return content;
	}

	private static final Pattern _toJSONArrayPattern = Pattern.compile(
		"toJSONArray\\(");

}