/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.io.unsync.UnsyncStringReader;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

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

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			String line = StringPool.BLANK;
			int lineNumber = 0;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				lineNumber++;

				String trimmedLine = line.trim();

				if (Validator.isBlank(trimmedLine) ||
					trimmedLine.startsWith("#")) {

					continue;
				}

				Matcher matcher = _variableDefinitionPattern.matcher(
					trimmedLine);

				if (!matcher.matches()) {
					continue;
				}

				String s = matcher.group(2);

				s = s.replaceAll("'.*?'", "''");

				if (s.contains("`") ||
					(s.contains("$(") && !s.contains("$(("))) {

					addMessage(
						fileName,
						StringBundler.concat(
							"Do not declare and assign \"local\" variables ",
							"using subshell outputs in a single line, extract ",
							"\"local\" variable declaration and assignment ",
							"via subshell into two separate lines"),
						lineNumber);
				}
			}
		}

		return content;
	}

	private static final Pattern _variableDefinitionPattern = Pattern.compile(
		"local\\s+(-[a-zA-Z]+\\s+)?\\w+=(.*)");

}