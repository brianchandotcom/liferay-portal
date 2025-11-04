/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.portal.tools.ToolsUtil;

/**
 * @author Alan Huang
 */
public class JavaUpgradeCompanyThreadLocalCheck extends BaseFileCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (!absolutePath.contains("/upgrade/") ||
			absolutePath.contains("/test/") ||
			absolutePath.contains("/testIntegration/") ||
			!isUpgradeProcess(absolutePath, content)) {

			return content;
		}

		int x = -1;

		while (true) {
			x = content.indexOf("CompanyThreadLocal.setCompanyId", x + 1);

			if (x == -1) {
				return content;
			}

			if (ToolsUtil.isInsideQuotes(content, x)) {
				continue;
			}

			addMessage(
				fileName,
				"Do not use CompanyThreadLocal.setCompanyId* in upgrade " +
					"classes",
				getLineNumber(content, x));
		}
	}

}