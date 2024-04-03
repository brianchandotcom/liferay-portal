/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

/**
 * @author Alan Huang
 */
public class MarkdownEmptyLinesCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		return _fixMissingEmptyLines(absolutePath, content);
	}

	private String _fixMissingEmptyLines(String absolutePath, String content)
		throws IOException {

		StringBundler sb = new StringBundler();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			boolean codeBlock = false;
			String line = StringPool.BLANK;
			String previousLine = StringPool.BLANK;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				String trimmedLine = line.trim();

				if (Validator.isBlank(trimmedLine)) {
					sb.append("\n");

					previousLine = StringPool.BLANK;

					continue;
				}

				if (trimmedLine.startsWith("```") &&
					(StringUtil.count(trimmedLine, "```") == 1)) {

					if (!codeBlock && !Validator.isBlank(previousLine)) {
						sb.append("\n");
					}

					sb.append(line);
					sb.append("\n");

					codeBlock = !codeBlock;
					previousLine = line;

					continue;
				}

				if (!absolutePath.endsWith(
						"readme/BREAKING_CHANGES_AMENDMENTS.markdown") &&
					codeBlock) {

					sb.append(line);
					sb.append("\n");
					previousLine = line;

					continue;
				}

				if ((!codeBlock &&
					 (_isHeader(line) ||
					  (previousLine.startsWith("```") &&
					   (StringUtil.count(previousLine, "```") == 1))) &&
					 (sb.index() > 0) && !Validator.isBlank(previousLine)) ||
					(_isHeader(previousLine) && !line.startsWith("- ") &&
					 !line.startsWith("* ")) ||
					(_isHeader(line) && !_isHeader(previousLine) &&
					 !Validator.isBlank(previousLine))) {

					sb.append("\n");
				}

				sb.append(line);
				sb.append("\n");

				previousLine = line;
			}
		}

		if (sb.index() > 0) {
			sb.setIndex(sb.index() - 1);
		}

		return sb.toString();
	}

	private boolean _isHeader(String line) {
		if (line.matches("#+ .*") || line.matches("\\-{3,}")) {
			return true;
		}

		return false;
	}

}