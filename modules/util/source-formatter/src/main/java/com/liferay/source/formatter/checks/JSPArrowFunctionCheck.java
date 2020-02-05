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

import com.liferay.portal.tools.ToolsUtil;

/**
 * @author Hugo Huijser
 */
public class JSPArrowFunctionCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		int x = -1;

		while (true) {
			x = content.indexOf("=>", x + 1);

			if (x == -1) {
				return content;
			}

			if (!ToolsUtil.isInsideQuotes(content, x)) {
				addMessage(
					fileName, "Do not use arrow function",
					getLineNumber(content, x));
			}
		}
	}

}