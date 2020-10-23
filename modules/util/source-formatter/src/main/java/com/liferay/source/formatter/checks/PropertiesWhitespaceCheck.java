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
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

/**
 * @author Hugo Huijser
 */
public class PropertiesWhitespaceCheck extends WhitespaceCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		StringBundler sb = new StringBundler();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			String line = null;
			String previousLine = StringPool.BLANK;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				if (line.startsWith(StringPool.TAB)) {
					line = StringUtil.replace(
						line, CharPool.TAB, StringPool.FOUR_SPACES);
				}

				if (line.contains(" \t")) {
					line = StringUtil.replace(
						line, " \t", " " + StringPool.FOUR_SPACES);
				}

				if (previousLine.matches("\\s*[^\\s#].*[,=]\\\\")) {
					String leadingSpaces = _getLeadingSpaces(line);

					String expectedLeadingSpaces = _getLeadingSpaces(
						previousLine);

					if (previousLine.endsWith("=\\")) {
						expectedLeadingSpaces += StringPool.FOUR_SPACES;
					}

					if (!leadingSpaces.equals(expectedLeadingSpaces)) {
						line = StringUtil.replaceFirst(
							line, leadingSpaces, expectedLeadingSpaces);
					}
				}

				sb.append(line);
				sb.append("\n");

				previousLine = line;
			}
		}

		return super.doProcess(fileName, absolutePath, sb.toString());
	}

	@Override
	protected boolean isAllowTrailingSpaces(String line) {
		String trimmedLine = StringUtil.removeChar(line, CharPool.SPACE);

		if (trimmedLine.endsWith(StringPool.EQUAL)) {
			return true;
		}

		return false;
	}

	private String _getLeadingSpaces(String line) {
		for (int i = 0; i < line.length(); i++) {
			if (line.charAt(i) != CharPool.SPACE) {
				return line.substring(0, i);
			}
		}

		return line;
	}

}