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
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

/**
 * @author Alan Huang
 */
public class PythonWhitespaceCheck extends WhitespaceCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		StringBundler sb = new StringBundler();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			boolean insideMultiLines = false;

			String line = null;
			String previousLine = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				while (line.matches("^\t*    .*")) {
					if (previousLine.endsWith(StringPool.BACK_SLASH) ||
						insideMultiLines) {

						break;
					}

					line = StringUtil.replaceFirst(
						line, StringPool.FOUR_SPACES, StringPool.TAB);
				}

				if (line.contains("'''") || line.contains("\"\"\"")) {
					insideMultiLines = insideMultiLines ^ true;
				}

				sb.append(line);
				sb.append("\n");

				previousLine = line;
			}
		}

		content = sb.toString();

		if (content.endsWith("\n")) {
			content = content.substring(0, content.length() - 1);
		}

		return content;
	}

}