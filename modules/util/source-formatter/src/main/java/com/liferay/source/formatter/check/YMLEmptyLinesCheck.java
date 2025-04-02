/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.check.util.SourceUtil;

import java.io.IOException;

/**
 * @author Alan Huang
 */
public class YMLEmptyLinesCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		content = _fixEmptyLines(content);

		return _removeEmptyLinesAroundDocumentSeparator(content);
	}

	private String _fixEmptyLines(String content) throws IOException {
		StringBundler sb = new StringBundler();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			boolean insideMultiline = false;
			String leadingSpaces = null;
			String line = null;
			String multilineLeadingSpaces = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				if (line.startsWith("{{- define ") && (sb.index() > 0)) {
					sb.append("\n");
				}

				if (!insideMultiline) {
					if (line.endsWith("|") || line.endsWith("|+") ||
						line.endsWith("|-")) {

						insideMultiline = true;
						multilineLeadingSpaces = SourceUtil.getLeadingSpaces(
							line);
					}

					if (Validator.isBlank(line)) {
						continue;
					}

					sb.append(line);

					sb.append("\n");

					continue;
				}

				sb.append(line);

				sb.append("\n");

				leadingSpaces = SourceUtil.getLeadingSpaces(line);

				if (!Validator.isBlank(line) &&
					(leadingSpaces.length() <=
						multilineLeadingSpaces.length())) {

					insideMultiline = false;
				}
			}
		}

		if (sb.length() > 0) {
			sb.setIndex(sb.index() - 1);
		}

		return sb.toString();
	}

	private String _removeEmptyLinesAroundDocumentSeparator(String content) {
		String trimmedContent = content.trim();

		if (trimmedContent.startsWith("---")) {
			return trimmedContent.substring(3);
		}

		if (trimmedContent.endsWith("---")) {
			return trimmedContent.substring(0, trimmedContent.length() - 3);
		}

		return content;
	}

}