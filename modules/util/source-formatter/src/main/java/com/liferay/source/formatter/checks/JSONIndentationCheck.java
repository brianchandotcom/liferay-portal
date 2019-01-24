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
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

/**
 * @author Hugo Huijser
 */
public class JSONIndentationCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		int expectedTabCount = 0;

		StringBundler sb = new StringBundler();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				sb.append(_fixIndentation(line, expectedTabCount));

				sb.append("\n");

				expectedTabCount = getLevel(
					line,
					new String[] {
						StringPool.OPEN_BRACKET, StringPool.OPEN_CURLY_BRACE
					},
					new String[] {
						StringPool.CLOSE_BRACKET, StringPool.CLOSE_CURLY_BRACE
					},
					expectedTabCount);
			}
		}

		String newContent = StringUtil.trimTrailing(sb.toString());

		if (fileName.endsWith("/package.json") &&
			newContent.equals(StringUtil.trimTrailing(content))) {

			return content;
		}

		return newContent;
	}

	private String _fixIndentation(String line, int expectedTabCount) {
		if (Validator.isNull(line)) {
			return line;
		}

		int leadingTabCount = getLeadingTabCount(line);

		if (line.matches("\t*[\\}\\]].*")) {
			expectedTabCount -= 1;
		}

		if (leadingTabCount == expectedTabCount) {
			return line;
		}

		String expectedTab = new String(new char[expectedTabCount]);

		return expectedTab.replace("\0", "\t") + StringUtil.trimLeading(line);
	}

}