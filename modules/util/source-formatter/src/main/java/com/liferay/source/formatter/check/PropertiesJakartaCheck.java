/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

/**
 * @author Alan Huang
 */
public class PropertiesJakartaCheck extends BaseJakartaCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (fileName.endsWith("/Language.properties")) {
			if (isModulesFile(absolutePath) ||
				isTopLevelProjectsFile(absolutePath)) {

				return replace(content);
			}
		}
		else if (isTopLevelProjectsFile(absolutePath)) {
			return replace(content);
		}

		return content;
	}

}