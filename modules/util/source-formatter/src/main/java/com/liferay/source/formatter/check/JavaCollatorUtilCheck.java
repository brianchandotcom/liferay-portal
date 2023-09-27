/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.portal.tools.ToolsUtil;

/**
 * @author Preston Crary
 */
public class JavaCollatorUtilCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (!fileName.endsWith("CollatorUtil.java") &&
			!fileName.endsWith("CollatorUtilTest.java")) {

			_checkCollatorGetInstance(fileName, content);
		}

		return content;
	}

	private void _checkCollatorGetInstance(String fileName, String content) {
		int index = content.indexOf("Collator.getInstance(");

		while (index != -1) {
			if (!ToolsUtil.isInsideQuotes(content, index)) {
				addMessage(
					fileName, "Use CollatorUtil.getInstance(Locale)",
					getLineNumber(content, index));
			}

			index = content.indexOf("Collator.getInstance(", index + 1);
		}
	}

}