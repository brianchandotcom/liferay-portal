/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

/**
 * @author Alan Huang
 */
public class JavaDefaultAdminScreenNameCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (absolutePath.contains("/test/") ||
			absolutePath.contains("/testIntegration/") ||
			absolutePath.endsWith(
				"/portal-impl/src/com/liferay/portal/service/impl" +
					"/CompanyLocalServiceImpl.java") ||
			absolutePath.endsWith(
				"/portal-kernel/src/com/liferay/portal/kernel/util" +
					"/PropsValues.java")) {

			return content;
		}

		if (content.contains("PropsKeys.DEFAULT_ADMIN_SCREEN_NAME") ||
			content.contains("PropsValues.DEFAULT_ADMIN_SCREEN_NAME")) {

			addMessage(
				fileName,
				"Do not use \"PropsKeys.DEFAULT_ADMIN_SCREEN_NAME\" or " +
					"\"PropsValues.DEFAULT_ADMIN_SCREEN_NAME\", see LPD-59150");
		}

		return content;
	}

}