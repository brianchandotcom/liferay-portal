/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.source.formatter.check.util.SourceUtil;

import java.util.List;

/**
 * @author Alan Huang
 */
public class JSONDeprecatedPackagesCheck extends BaseFileCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (!absolutePath.endsWith("/package.json")) {
			return content;
		}

		List<String> deprecatedPackageNames = getAttributeValues(
			_DEPRECATED_PACKAGE_NAMES_KEY, absolutePath);

		for (String deprecatedPackageName : deprecatedPackageNames) {
			int x = -1;

			while (true) {
				x = content.indexOf(
					"\"" + deprecatedPackageName + "\":", x + 1);

				if (x == -1) {
					break;
				}

				addMessage(
					fileName,
					"Do not use deprecated package '" + deprecatedPackageName +
						"'",
					SourceUtil.getLineNumber(content, x));
			}
		}

		return content;
	}

	private static final String _DEPRECATED_PACKAGE_NAMES_KEY =
		"deprecatedPackageNames";

}