/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.io.unsync.UnsyncStringReader;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alan Huang
 */
public class SHSubshellCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		if (Validator.isBlank(content)) {
			return content;
		}

		List<String> allowedSubshellNames = getAttributeValues(
			_ALLOWED_SUBSHELL_NAMES_KEY, absolutePath);

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			boolean insideDoubleQuotes = false;
			boolean insideLocalBlock = false;
			boolean insideSingleQuotes = false;
			String line = StringPool.BLANK;
			int lineNumber = 0;
			int startLineNumber = 0;

			StringBundler sb = new StringBundler();

			while ((line = unsyncBufferedReader.readLine()) != null) {
				lineNumber++;

				String trimmedLine = line.trim();

				if (!insideLocalBlock &&
					(Validator.isBlank(trimmedLine) ||
					 trimmedLine.startsWith("#"))) {

					continue;
				}

				if (!insideLocalBlock) {
					Matcher matcher = _variableDefinitionPattern.matcher(
						trimmedLine);

					if (!matcher.matches()) {
						continue;
					}

					insideLocalBlock = true;
					startLineNumber = lineNumber;

					sb.append(matcher.group(2));
				}
				else {
					sb.append(line);
				}

				insideSingleQuotes = _updateQuotesState(
					line, '\'', insideSingleQuotes);
				insideDoubleQuotes = _updateQuotesState(
					line, '"', insideDoubleQuotes);

				if (insideSingleQuotes || insideDoubleQuotes ||
					trimmedLine.endsWith("\\")) {

					sb.append(StringPool.NEW_LINE);

					continue;
				}

				String s = sb.toString();

				s = s.replaceAll("(?s)'.*?'", "''");

				if (s.contains("`") && ((StringUtil.count(s, '`') % 2) == 0)) {
					addMessage(
						fileName,
						"Use \"$()\" for subshells instead of legacy backticks",
						startLineNumber);
				}

				Matcher matcher = _subshellPattern.matcher(s);

				while (matcher.find()) {
					String name = matcher.group(1);

					if (allowedSubshellNames.contains(name) ||
						name.startsWith("_")) {

						continue;
					}

					addMessage(
						fileName,
						StringBundler.concat(
							"Do not declare and assign \"local\" variables ",
							"using subshell outputs in a single line, extract ",
							"\"local\" variable declaration and assignment ",
							"via subshell into two separate lines"),
						startLineNumber);
				}

				insideDoubleQuotes = false;
				insideLocalBlock = false;
				insideSingleQuotes = false;

				sb = new StringBundler();
			}
		}

		return content;
	}

	private boolean _updateQuotesState(String s, char c, boolean insideQuotes) {
		int count = 0;

		for (int i = 0; i < s.length(); i++) {
			if ((s.charAt(i) != c) || ((i > 0) && (s.charAt(i - 1) == '\\'))) {
				continue;
			}

			count++;
		}

		if ((count % 2) != 0) {
			return !insideQuotes;
		}

		return insideQuotes;
	}

	private static final String _ALLOWED_SUBSHELL_NAMES_KEY =
		"allowedSubshellNames";

	private static final Pattern _subshellPattern = Pattern.compile(
		"\\$\\(\\s*(?!\\()(\\w+)");
	private static final Pattern _variableDefinitionPattern = Pattern.compile(
		"local\\s+(-[a-zA-Z]+\\s+)?\\w+=(.*)");

}