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
import com.liferay.source.formatter.check.util.SourceUtil;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alan Huang
 */
public class MarkdownBreakingChangesAmendmentsFileCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		if (!absolutePath.endsWith(
				"readme/BREAKING_CHANGES_AMENDMENTS.markdown")) {

			return content;
		}

		List<String> breakingChangesAmendments =
			_splitBreakingChangesAmendments(content);

		_checkBreakingChangesAmendments(fileName, breakingChangesAmendments);

		return content;
	}

	private void _checkBreakingChanges(
		String fileName, String sha, String[] breakingChanges) {

		for (String breakingChange : breakingChanges) {
			int alternativesCount = StringUtil.count(
				breakingChange, "## Alternatives");
			int breakingCount = StringUtil.count(
				breakingChange, "# breaking\n");
			int whatCount = StringUtil.count(breakingChange, "## What");
			int whyCount = StringUtil.count(breakingChange, "## Why");

			if ((alternativesCount > 1) || (breakingCount != 1) ||
				(whatCount != 1) || (whyCount != 1)) {

				addMessage(
					fileName,
					StringBundler.concat(
						"Incorrect breaking change content in ", sha, ": ",
						"Each breaking change should have one, and only ",
						"one '# breaking', '## What', '## Why' and ## ",
						"'Alternatives'(Optional). Use '----' to split ",
						"each breaking change."));

				return;
			}

			int alternativesPosition = breakingChange.indexOf(
				"## Alternatives");
			int whatPosition = breakingChange.indexOf("## What");
			int whyPosition = breakingChange.indexOf("## Why");

			if ((whatPosition > whyPosition) ||
				((alternativesPosition != -1) &&
				 (whyPosition > alternativesPosition))) {

				addMessage(
					fileName,
					StringBundler.concat(
						"Incorrect breaking change content in ", sha, ": ",
						"The correct order of headers should be '## What' ",
						"| '## Why' | '## Alternatives'"));

				return;
			}

			int lineNumber = SourceUtil.getLineNumber(
				breakingChange, whatPosition);

			String trimmedLine = StringUtil.trimLeading(
				SourceUtil.getLine(breakingChange, lineNumber));

			if (trimmedLine.length() == 7) {
				addMessage(
					fileName,
					StringBundler.concat(
						"Incorrect breaking change content in ", sha, ": ",
						"There should be one file path after '## What'"));

				return;
			}
		}
	}

	private void _checkBreakingChangesAmendments(
			String fileName, List<String> breakingChangesAmendments)
		throws IOException {

		for (String breakingChangesAmendment : breakingChangesAmendments) {
			breakingChangesAmendment = StringUtil.trimLeading(
				breakingChangesAmendment);

			int x = breakingChangesAmendment.indexOf("\n");

			if (x == -1) {
				return;
			}

			String firstLine = breakingChangesAmendment.substring(0, x);

			if (!firstLine.matches("# [0-9a-f]{40}")) {
				addMessage(
					fileName,
					"The first line in each amendment should be '# SHA'");

				continue;
			}

			x = breakingChangesAmendment.indexOf("```");

			if (x == -1) {
				continue;
			}

			int y = breakingChangesAmendment.lastIndexOf("```");

			if (y == -1) {
				continue;
			}

			String breakingChanges = breakingChangesAmendment.substring(
				x + 3, y);

			_checkBreakingChanges(
				fileName, firstLine, breakingChanges.split("\n----"));
		}
	}

	private List<String> _splitBreakingChangesAmendments(String content)
		throws IOException {

		StringBundler sb = new StringBundler();

		List<String> breakingChangesAmendments = new ArrayList<>();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			boolean codeBlock = false;
			String line = StringPool.BLANK;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				if (Validator.isBlank(line)) {
					sb.append("\n");

					continue;
				}

				if (line.equals("----") && !codeBlock && (sb.index() > 0)) {
					sb.append(line);
					sb.append("\n");

					breakingChangesAmendments.add(sb.toString());

					sb.setIndex(0);

					continue;
				}

				sb.append(line);
				sb.append("\n");

				if (line.startsWith("```") &&
					(StringUtil.count(line, "```") == 1)) {

					codeBlock = !codeBlock;
				}
			}

			if (sb.index() > 0) {
				breakingChangesAmendments.add(sb.toString());
			}
		}

		return breakingChangesAmendments;
	}

}