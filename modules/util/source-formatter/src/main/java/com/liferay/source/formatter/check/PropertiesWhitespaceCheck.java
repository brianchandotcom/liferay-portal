/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

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
					line = StringUtil.replace(line, " \t", "     ");
				}

				if (line.matches("\\s*[^\\s#].*[,=]\\\\")) {
					line = _fixLeadingSpaces(line);
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

					if (line.matches(" {4,}]")) {
						line = line.substring(4);
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

		return trimmedLine.endsWith(StringPool.EQUAL);
	}

	private String _fixLeadingSpaces(String line) {
		String leadingSpaces = _getLeadingSpaces(line);

		int leadingSpacesLength = leadingSpaces.length();

		int remainder = leadingSpacesLength % 4;

		if (remainder == 0) {
			return leadingSpaces + StringUtil.trimLeading(line);
		}

		if ((leadingSpacesLength / 4) > 0) {
			leadingSpaces = leadingSpaces.substring(remainder);
		}
		else {
			StringBundler sb = new StringBundler(remainder);

			sb.append(leadingSpaces);

			for (int i = 0; i < (remainder - 1); i++) {
				sb.append(StringPool.SPACE);
			}

			leadingSpaces = sb.toString();
		}

		return leadingSpaces + StringUtil.trimLeading(line);
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