/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.check.util.JavaSourceUtil;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class JavaBooleanUsageCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		return _fixIncorrectBooleanUse(content, "setAttribute");
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

			List<String> parameterList = JavaSourceUtil.getParameterList(
				methodCall);

			if (parameterList.size() != 2) {
				continue;
			}

			String secondParameterName = parameterList.get(1);

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