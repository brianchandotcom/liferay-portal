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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Hugo Huijser
 */
public class JSONIndentationCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		int expectedTabCount = 0;

		StringBundler sb = new StringBundler();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			String line = null;

			int lineCount = 0;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				lineCount++;

				_checkIndentation(line, fileName, expectedTabCount, lineCount);

				sb.append(line);

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

		return content;
	}

	private void _checkIndentation(
		String line, String fileName, int expectedTabCount, int lineCount) {

		if (Validator.isNull(line)) {
			return;
		}

		int leadingTabCount = getLeadingTabCount(line);

		if (line.matches("\t*[\\}\\]].*")) {
			expectedTabCount -= 1;
		}

		if (leadingTabCount == expectedTabCount) {
			return;
		}

		StringBundler sb = new StringBundler(5);

		sb.append("Line starts with '");
		sb.append(leadingTabCount);
		sb.append("' tabs, but '");
		sb.append(expectedTabCount);
		sb.append("' tabs are expected");

		addMessage(fileName, sb.toString(), lineCount);
	}

}