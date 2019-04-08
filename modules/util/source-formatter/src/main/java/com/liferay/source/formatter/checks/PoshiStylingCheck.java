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
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.checks.util.PoshiSourceUtil;

import java.io.IOException;

/**
 * @author Alan Huang
 */
public class PoshiStylingCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		_checkLineBreak(fileName, content);

		return content;
	}

	private void _checkLineBreak(String fileName, String content) {
		int x = -1;

		int[] multiLineStringPositions =
			PoshiSourceUtil.getMultiLineStringPositions(content);

		while (true) {
			x = content.indexOf(CharPool.SEMICOLON, x + 1);

			if (x == -1) {
				return;
			}

			if ((content.charAt(x + 1) != CharPool.NEW_LINE) &&
				!ToolsUtil.isInsideQuotes(content, x) &&
				!PoshiSourceUtil.isInsideMultiLineString(
					getLineNumber(content, x), multiLineStringPositions)) {

				addMessage(
					fileName, "There should be a line break after ';'",
					getLineNumber(content, x));
			}
		}
	}

}