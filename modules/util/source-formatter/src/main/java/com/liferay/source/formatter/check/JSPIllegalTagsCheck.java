/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;

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

		List<String> illegalTagNames = getAttributeValues(
			_ILLEGAL_TAG_NAMES_KEY, absolutePath);

		for (String illegalTagName : illegalTagNames) {
			int x = -1;

			while (true) {
				x = content.indexOf(CharPool.LESS_THAN + illegalTagName, x + 1);

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
						"Do not use <" + illegalTagName +
							"> tag, see LPD-18227",
						getLineNumber(content, x));
				}
			}
		}

		return content;
	}

	private static final String _ILLEGAL_TAG_NAMES_KEY = "illegalTagNames";

}