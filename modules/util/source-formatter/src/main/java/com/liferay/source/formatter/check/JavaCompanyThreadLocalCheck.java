/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.tools.ToolsUtil;

/**
 * @author Alan Huang
 */
public class JavaCompanyThreadLocalCheck extends BaseFileCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (absolutePath.contains("/test/") ||
			absolutePath.contains("/testIntegration/")) {

			return content;
		}

		_checkMethodCall(fileName, content, "CompanyThreadLocal.lock");
		_checkMethodCall(fileName, content, "CompanyThreadLocal.setCompanyId");
		_checkMethodCall(
			fileName, content,
			"CompanyThreadLocal.setCompanyIdWithSafeCloseable");

		return content;
	}

	private void _checkMethodCall(
		String fileName, String content, String methodCall) {

		int x = -1;

		while (true) {
			x = content.indexOf(methodCall + "(", x + 1);

			if (x == -1) {
				return;
			}

			if (ToolsUtil.isInsideQuotes(content, x)) {
				continue;
			}

			addMessage(
				fileName,
				StringBundler.concat(
					"Do not use \"", methodCall,
					"\". Contact the Database Infrastructure Team if you need ",
					"to use it, see LPD-81801."),
				getLineNumber(content, x));
		}
	}

}