/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Alan Huang
 */
public class PoshiWhitespaceCheck extends WhitespaceCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		return _removeRedundantWhitespaceInTaskDescription(fileName, content);
	}

	private String _removeRedundantWhitespaceInTaskDescription(
		String fileName, String content) {

		if (!fileName.endsWith("macro") && !fileName.endsWith("testcase")) {
			return content;
		}

		String[] lines = content.split("\n");

		StringBundler sb = new StringBundler(lines.length * 2);

		for (String line : lines) {
			String trimmedLine = StringUtil.trim(line);

			if (!trimmedLine.startsWith("task (")) {
				sb.append(line);
				sb.append(StringPool.NEW_LINE);

				continue;
			}

			String newLine = trimmedLine.replaceAll(" {2,}", " ");

			if (StringUtil.equals(trimmedLine, newLine)) {
				sb.append(line);
			}
			else {
				sb.append(StringUtil.replaceFirst(line, trimmedLine, newLine));
			}

			sb.append(StringPool.NEW_LINE);
		}

		if (sb.index() > 1) {
			sb.setIndex(sb.index() - 1);
		}

		return sb.toString();
	}

}