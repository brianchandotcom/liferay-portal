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
public class JSPIllegalSyntaxCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		_checkIllegalSyntax(
			fileName, content, "console.log(", "Do not use console.log");
		_checkIllegalSyntax(
			fileName, content, "debugger.", "Do not use debugger");

		if (!fileName.endsWith("test.jsp")) {
			_checkIllegalSyntax(
				fileName, content, "System.out.print",
				"Do not call 'System.out.print'");
		}

		return content;
	}

	private void _checkIllegalSyntax(
		String fileName, String content, String syntax, String message) {

		int x = -1;

		while (true) {
			x = content.indexOf(syntax, x + 1);

			if (x == -1) {
				return;
			}

			if (!ToolsUtil.isInsideQuotes(content, x)) {
				addMessage(fileName, message, getLineNumber(content, x));
			}
		}
	}

}