/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.util.JavaSourceUtil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Qi Zhang
 */
public class JSPGetStaticResourceURLCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		Matcher matcher = _staticResourceURLParamPattern.matcher(content);

		while (matcher.find()) {
			String parameters = null;
			int x = matcher.start();

			while (true) {
				x = content.indexOf(StringPool.CLOSE_PARENTHESIS, x);

				if (x == -1) {
					return content;
				}

				parameters = content.substring(matcher.start(), x + 1);

				int level = getLevel(
					parameters, StringPool.OPEN_PARENTHESIS,
					StringPool.CLOSE_PARENTHESIS);

				if (level == 0) {
					break;
				}

				x++;
			}

			List<String> parameterList = JavaSourceUtil.getParameterList(
				parameters);

			if (ListUtil.isEmpty(parameterList) || (parameterList.size() < 2)) {
				continue;
			}

			String secondParameter = parameterList.get(1);

			if (secondParameter.startsWith("application.getContextPath()")) {
				return StringUtil.insert(
					content, "PortalUtil.getPathProxy() + ",
					matcher.start() + parameters.indexOf(secondParameter));
			}
		}

		return content;
	}

	private static final Pattern _staticResourceURLParamPattern =
		Pattern.compile("PortalUtil\\.getStaticResourceURL\\(");

}