/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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