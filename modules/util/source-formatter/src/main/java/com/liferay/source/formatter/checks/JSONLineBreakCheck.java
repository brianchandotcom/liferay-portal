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

/**
 * @author Hugo Huijser
 */
public class JSONLineBreakCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		for (int i = 0; i < content.length(); i++) {
			if (ToolsUtil.isInsideQuotes(content, i)) {
				continue;
			}

			char c = content.charAt(i);

			if (i < (content.length() - 1)) {
				char nextChar = content.charAt(i + 1);

				if ((nextChar != CharPool.NEW_LINE) &&
					(((c == CharPool.OPEN_BRACKET) &&
					  (nextChar != CharPool.CLOSE_BRACKET)) ||
					 ((c == CharPool.OPEN_CURLY_BRACE) &&
					  (nextChar != CharPool.CLOSE_CURLY_BRACE)) ||
					 (c == CharPool.COMMA))) {

					return StringUtil.insert(content, "\n", i + 1);
				}
			}

			if (i > 0) {
				char previousChar = content.charAt(i - 1);

				if ((((c == CharPool.CLOSE_BRACKET) &&
					  (previousChar != CharPool.OPEN_BRACKET)) ||
					 ((c == CharPool.CLOSE_CURLY_BRACE) &&
					  (previousChar != CharPool.OPEN_CURLY_BRACE))) &&
					!_isAtLineStart(content, i)) {

					return StringUtil.insert(content, "\n", i);
				}
			}
		}

		return content;
	}

	private boolean _isAtLineStart(String content, int pos) {
		while (true) {
			if (pos == 0) {
				return true;
			}

			char c = content.charAt(pos - 1);

			if (c == CharPool.NEW_LINE) {
				return true;
			}

			if (!Character.isWhitespace(c)) {
				return false;
			}

			pos--;
		}
	}

}