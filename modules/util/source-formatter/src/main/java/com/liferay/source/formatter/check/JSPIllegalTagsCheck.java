/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.poshi.core.util.StringUtil;

import java.util.List;

/**
 * @author Alan Huang
 */
public class JSPIllegalTagsCheck extends BaseFileCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		String lowerCaseFileName = StringUtil.lowerCase(fileName);

		List<String> illegalTagNames = getAttributeValues(
			_ILLEGAL_TAG_NAMES_KEY, absolutePath);

		if (lowerCaseFileName.endsWith(".jsp") ||
			lowerCaseFileName.endsWith(".jspf") ||
			lowerCaseFileName.endsWith(".jspx")) {

			for (String illegalTagName : illegalTagNames) {
				int x = -1;

				while (true) {
					x = content.indexOf(
						CharPool.LESS_THAN + illegalTagName, x + 1);

					if (x == -1) {
						break;
					}

					int nextCharPosition = x + illegalTagName.length() + 1;

					if (nextCharPosition >= content.length()) {
						break;
					}

					char nextChar = content.charAt(nextCharPosition);

					if ((nextChar == CharPool.GREATER_THAN) ||
						(nextChar == CharPool.NEW_LINE) ||
						(nextChar == CharPool.SPACE)) {

						addMessage(
							fileName,
							StringBundler.concat(
								"Do not use <", illegalTagName, "> tag (use ",
								"<aui:", illegalTagName, "> instead), see ",
								"LPD-18227"),
							getLineNumber(content, x));
					}
				}
			}
		}
		else if (lowerCaseFileName.endsWith(".ftl")) {
			_checkAttribute(
				"${nonceAttribute}", content, fileName, illegalTagNames);
		}
		else if (lowerCaseFileName.endsWith(".vm")) {
			_checkAttribute(
				"$nonceAttribute", content, fileName, illegalTagNames);
		}

		return content;
	}

	private void _checkAttribute(
		String attribute, String content, String fileName,
		List<String> illegalTagNames) {

		for (String illegalTagName : illegalTagNames) {
			int x = -1;

			while (true) {
				x = content.indexOf(CharPool.LESS_THAN + illegalTagName, x + 1);

				if (x == -1) {
					break;
				}

				int y = content.indexOf(CharPool.GREATER_THAN, x + 1);

				if (y == -1) {
					y = content.length() - 1;
				}

				String tagSubstring = content.substring(x, y);

				if (!tagSubstring.contains(attribute)) {
					addMessage(
						fileName,
						StringBundler.concat(
							"Tag <", illegalTagName, "> is missing attribute '",
							attribute, "', see LPD-18227"),
						getLineNumber(content, x));
				}
			}
		}
	}

	private static final String _ILLEGAL_TAG_NAMES_KEY = "illegalTagNames";

}