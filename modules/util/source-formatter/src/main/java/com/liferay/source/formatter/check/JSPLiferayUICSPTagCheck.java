/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

/**
 * @author Marco Leo
 */
public class JSPLiferayUICSPTagCheck extends BaseFileCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (isExcludedPath(_LIFERAY_UI_CSP_EXCLUDES, absolutePath)) {
			return content;
		}

		int x = -1;

		while (true) {
			x = content.indexOf("<liferay-ui:csp", x + 1);

			if (x == -1) {
				return content;
			}

			addMessage(
				fileName,
				"Do not use <liferay-ui:csp> tag, use a React component " +
					"instead, see LPD-73599",
				getLineNumber(content, x));
		}
	}

	private static final String _LIFERAY_UI_CSP_EXCLUDES =
		"liferay.ui.csp.excludes";

}
