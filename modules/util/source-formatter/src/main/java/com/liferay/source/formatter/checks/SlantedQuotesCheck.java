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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.tools.ToolsUtil;

/**
 * @author Hugo Huijser
 */
public class SlantedQuotesCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		content = _fixSlantedQuotes(
			content, _SLANTED_DOUBLE_QUOTE_CHARS, StringPool.QUOTE);
		content = _fixSlantedQuotes(
			content, _SLANTED_SINGLE_QUOTE_CHARS, StringPool.APOSTROPHE);

		return content;
	}

	private String _fixSlantedQuotes(
		String content, char[] chars, String replacement) {

		int x = content.length() + 1;

		while (true) {
			int y = -1;

			for (char c : chars) {
				y = Math.max(y, content.lastIndexOf(c, x - 1));
			}

			if (y == -1) {
				return content;
			}

			if (!ToolsUtil.isInsideQuotes(content, y)) {
				content = StringBundler.concat(
					content.substring(0, y), replacement,
					content.substring(y + 1));
			}

			x = y;
		}
	}

	private static final char[] _SLANTED_DOUBLE_QUOTE_CHARS = {
		'\u201c', '\u201d', '\u201e', '\u201f'
	};

	private static final char[] _SLANTED_SINGLE_QUOTE_CHARS = {
		'\u2018', '\u2019', '\u201a', '\u201b'
	};

}