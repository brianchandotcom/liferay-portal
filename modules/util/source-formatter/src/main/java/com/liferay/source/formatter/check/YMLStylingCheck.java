/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

/**
 * @author Alan Huang
 */
public class YMLStylingCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		if (content.startsWith("---\n")) {
			content = content.substring(4);
		}

		content = content.replaceAll(
			"(\\A|\n)( *)(description:) (?!\\|-)(.+)(\\Z|\n)",
			"$1$2$3\n    $2$4$5");
		content = content.replaceAll("(\\A|\n) *description:\n +\"\"", "");
		content = content.replaceAll(
			"(\\A|\n)( *#)@? ?(review)(\\Z|\n)", "$1$2 @$3$4");

		if (fileName.endsWith("/rest-config.yaml")) {
			content = content.replaceAll(
				"(\\A|\n)( *baseURI: ((['\"](?!/))|(?!['\"/])))(.*)",
				"$1$2/$5");
		}

		return _formatQuotes(content);
	}

	private String _fixQuotes(String s) {
		if (Validator.isNull(s) || (s.length() == 1)) {
			return s;
		}

		if ((s.charAt(0) == CharPool.APOSTROPHE) &&
			(s.charAt(s.length() - 1) == CharPool.APOSTROPHE)) {

			if (s.length() == 2) {
				return StringPool.QUOTE + StringPool.QUOTE;
			}

			String unquotedValue = s.substring(1, s.length() - 1);

			unquotedValue = StringUtil.replace(unquotedValue, "''", "'");
			unquotedValue = StringUtil.replace(unquotedValue, "\"", "\\\"");

			return CharPool.QUOTE + unquotedValue + CharPool.QUOTE;
		}

		if ((s.charAt(0) == CharPool.QUOTE) &&
			(s.charAt(s.length() - 1) == CharPool.QUOTE)) {

			if (s.length() == 2) {
				return s;
			}

			String unquotedValue = s.substring(1, s.length() - 1);

			if (unquotedValue.contains("\\") ||
				unquotedValue.matches("\\d+(\\.\\d*)?") ||
				unquotedValue.matches("[Ff]alse") ||
				unquotedValue.matches("[Tt]rue") ||
				unquotedValue.startsWith("#") ||
				unquotedValue.startsWith("&") ||
				unquotedValue.startsWith("*") ||
				unquotedValue.startsWith("[") ||
				unquotedValue.startsWith("{")) {

				return s;
			}

			return s.substring(1, s.length() - 1);
		}

		return s;
	}

	private String _formatQuotes(String content) throws IOException {
		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			String line = null;
			int lineNumber = 0;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				lineNumber++;

				String trimmedLine = StringUtil.trimLeading(line);

				int x = trimmedLine.indexOf(": ");

				if (x == -1) {
					continue;
				}

				String key = trimmedLine.substring(0, x);

				String newKey = _fixQuotes(key);

				if (!key.equals(newKey)) {
					return StringUtil.replaceFirst(
						content, key, newKey,
						getLineStartPos(content, lineNumber));
				}

				String value = trimmedLine.substring(x + 2);

				String newValue = _fixQuotes(value);

				if (value.equals(newValue)) {
					continue;
				}

				return StringUtil.replaceFirst(
					content, value, newValue,
					getLineStartPos(content, lineNumber));
			}
		}

		return content;
	}

}