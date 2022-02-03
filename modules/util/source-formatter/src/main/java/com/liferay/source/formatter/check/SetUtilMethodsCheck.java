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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.check.util.JavaSourceUtil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alan Huang
 */
public class SetUtilMethodsCheck extends BaseFileCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		return _checkFromArrayCalls(content);
	}

	private String _checkFromArrayCalls(String content) {
		Matcher matcher = _fromArrayPattern.matcher(content);

		while (matcher.find()) {
			if (ToolsUtil.isInsideQuotes(content, matcher.start())) {
				continue;
			}

			List<String> parameterList = JavaSourceUtil.getParameterList(
				content.substring(matcher.start()));

			if (parameterList.size() != 1) {
				continue;
			}

			String parameter = parameterList.get(0);

			String arrayParameters = parameter.replaceFirst(
				".+\\{([\\s\\S]+)\\}", "$1");

			return _fixFromArrayParamters(
				content, arrayParameters, matcher.start());
		}

		return content;
	}

	private String _fixFromArrayParamters(
		String content, String arraryParameters, int pos) {

		int x = pos;

		while (true) {
			x = content.indexOf(StringPool.CLOSE_PARENTHESIS, x + 1);

			String call = content.substring(pos, x + 1);

			if ((ToolsUtil.getLevel(call, "(", ")") == 0) &&
				(ToolsUtil.getLevel(call, "{", "}") == 0)) {

				String replacement = StringBundler.concat(
					"SetUtil.fromArray(", arraryParameters, ")");

				return StringUtil.replaceFirst(content, call, replacement, pos);
			}
		}
	}

	private static final Pattern _fromArrayPattern = Pattern.compile(
		"SetUtil\\.fromArray\\(\\s*(?=new )");

}