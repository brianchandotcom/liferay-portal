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

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

/**
 * @author Hugo Huijser
 */
public class PropertiesWhitespaceCheck extends WhitespaceCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		StringBundler sb = new StringBundler();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			String line = null;
			String previousLine = StringPool.BLANK;

			int lineCount = 0;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				lineCount++;

				if (line.startsWith(StringPool.TAB)) {
					line = line.replace(StringPool.TAB, StringPool.FOUR_SPACES);
				}

				if (line.contains(" \t")) {
					line = line.replace(" \t", " " + StringPool.FOUR_SPACES);
				}

				sb.append(line);
				sb.append("\n");

				if (!previousLine.matches("\\s+[^\\s#].*[,=]\\\\")) {
					previousLine = line;

					continue;
				}

				int leadingSpaceCount = _getLeadingSpaceCount(line);

				int expectedLeadingSpaceCount = _getLeadingSpaceCount(
					previousLine);

				if (previousLine.endsWith("=\\")) {
					expectedLeadingSpaceCount += 4;
				}

				if (leadingSpaceCount != expectedLeadingSpaceCount) {
					StringBundler sb2 = new StringBundler(5);

					sb2.append("Line starts with '");
					sb2.append(leadingSpaceCount);
					sb2.append("' spaces, but '");
					sb2.append(expectedLeadingSpaceCount);
					sb2.append("' spaces are expected");

					addMessage(fileName, sb2.toString(), lineCount);
				}

				previousLine = line;
			}
		}

		content = sb.toString();

		if (content.endsWith("\n")) {
			content = content.substring(0, content.length() - 1);
		}

		return super.doProcess(fileName, absolutePath, content);
	}

	private int _getLeadingSpaceCount(String line) {
		int leadingSpaceCount = 0;

		while (line.startsWith(StringPool.SPACE)) {
			line = line.substring(1);

			leadingSpaceCount++;
		}

		return leadingSpaceCount;
	}

}