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
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

/**
 * @author Alan Huang
 */
public class HTMLEmptyLinesCheck extends BaseEmptyLinesCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		content = fixEmptyLinesInMultiLineTags(content);

		content = fixEmptyLinesInNestedTags(content);

		content = fixEmptyLinesBetweenTags(content);

		content = fixMissingEmptyLineAfterDoctype(content);

		content = _fixMissingEmptyLineAroundSingleLineComment(content);

		return content;
	}

	private String _fixMissingEmptyLineAroundSingleLineComment(String content)
		throws IOException {

		StringBundler sb = new StringBundler();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			String line = null;
			String previousLine = StringPool.BLANK;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				String trimmedLine = StringUtil.trim(line);

				if ((trimmedLine.startsWith("<!--") &&
					 trimmedLine.endsWith("-->") &&
					 Validator.isNotNull(previousLine)) ||
					(previousLine.startsWith("<!--") &&
					 previousLine.endsWith("-->") &&
					 Validator.isNotNull(line))) {

					sb.append("\n");
				}

				sb.append(line);
				sb.append("\n");

				previousLine = trimmedLine;
			}
		}

		if (sb.index() > 0) {
			sb.setIndex(sb.index() - 1);
		}

		return sb.toString();
	}

}