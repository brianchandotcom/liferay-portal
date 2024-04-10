/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.util.SourceUtil;

import java.io.IOException;

/**
 * @author Alan Huang
 */
public abstract class BaseBreakingChangesCheck extends BaseFileCheck {

	protected void checkBreakingChanges(
			String fileName, String absolutePath, String[] breakingChanges,
			String message, boolean bndFile)
		throws IOException {

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
						message,
						"Each breaking change should have one, and only one ",
						"'# breaking', '## What', '## Why' and ## (Optional). ",
						"Use '----' to split each breaking change."));

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
					message +
						"The correct order of headers should be '## What' | '" +
							"## Why' | '## Alternatives'");

				return;
			}

			int lineNumber = SourceUtil.getLineNumber(
				breakingChange, whatPosition);

			String trimmedLine = StringUtil.trimLeading(
				SourceUtil.getLine(breakingChange, lineNumber));

			if (trimmedLine.length() == 7) {
				addMessage(
					fileName,
					message + "There should be one file path after '## What'");

				return;
			}

			if (!bndFile) {
				return;
			}

			String filePath = StringUtil.trim(trimmedLine.substring(7));

			if (getPortalContent(filePath, absolutePath, true) == null) {
				addMessage(
					fileName,
					StringBundler.concat(
						message, StringUtil.quote(filePath),
						" points to nonexistent file. '## What' should be ",
						"followed by only one path, which is from ",
						_LIFERAY_PORTAL_MASTER_URL, "."));

				return;
			}
		}
	}

	private static final String _LIFERAY_PORTAL_MASTER_URL =
		"https://github.com/liferay/liferay-portal/blob/master/";

}