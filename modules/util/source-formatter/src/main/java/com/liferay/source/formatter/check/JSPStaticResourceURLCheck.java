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
public class JSPStaticResourceURLCheck extends BaseFileCheck {

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
				int insertPos =
					matcher.start() + parameters.indexOf(secondParameter);

				return StringUtil.insert(
					content, "PortalUtil.getPathProxy() + ", insertPos);
			}
		}

		return content;
	}

	private static final Pattern _staticResourceURLParamPattern =
		Pattern.compile("PortalUtil.getStaticResourceURL\\(");

}