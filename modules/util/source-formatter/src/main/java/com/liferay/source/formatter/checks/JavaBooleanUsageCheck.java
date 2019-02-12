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

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.checks.util.JavaSourceUtil;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class JavaBooleanUsageCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		content = _fixIncorrectBooleanUse(content, "setAttribute");

		return content;
	}

	private String _fixIncorrectBooleanUse(String content, String methodName) {
		int x = -1;

		while (true) {
			x = content.indexOf("." + methodName + "(", x + 1);

			if (x == -1) {
				return content;
			}

			if (ToolsUtil.isInsideQuotes(content, x)) {
				continue;
			}

			String methodCall = null;

			int y = x;

			while (true) {
				y = content.indexOf(")", y + 1);

				if (y == -1) {
					return content;
				}

				methodCall = content.substring(x, y + 1);

				if (!ToolsUtil.isInsideQuotes(content, y) &&
					(getLevel(methodCall) == 0)) {

					break;
				}
			}

			if (methodCall.contains("\t//") ||
				(content.charAt(y + 1) != CharPool.SEMICOLON)) {

				continue;
			}

			List<String> parametersList = JavaSourceUtil.getParameterList(
				methodCall);

			if (parametersList.size() != 2) {
				continue;
			}

			String secondParameterName = parametersList.get(1);

			if (secondParameterName.equals("false") ||
				secondParameterName.equals("true")) {

				String replacement = StringUtil.replaceLast(
					methodCall, secondParameterName,
					"Boolean." + StringUtil.toUpperCase(secondParameterName));

				return StringUtil.replace(content, methodCall, replacement);
			}
		}
	}

}