/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;

import java.io.IOException;

/**
 * @author Hugo Huijser
 */
public class CodeownersWhitespaceCheck extends WhitespaceCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		content = super.doProcess(fileName, absolutePath, content);

		StringBundler sb = new StringBundler();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				if (line.startsWith(StringPool.POUND)) {
					sb.append(line);
				}
				else {
					String[] words = line.split("\\s+");

					if (words.length == 1) {
						sb.append(line);
					}
					else {
						sb.append(words[0]);
						sb.append(StringPool.FOUR_SPACES);

						for (int i = 1; i < (words.length - 1); i++) {
							sb.append(words[i]);
							sb.append(StringPool.SPACE);
						}

						sb.append(words[words.length - 1]);
					}
				}

				sb.append("\n");
			}
		}

		content = sb.toString();

		if (content.endsWith("\n")) {
			content = content.substring(0, content.length() - 1);
		}

		return content;
	}

}