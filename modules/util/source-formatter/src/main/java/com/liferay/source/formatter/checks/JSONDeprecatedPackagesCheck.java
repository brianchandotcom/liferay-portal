/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.source.formatter.checks;

import com.liferay.source.formatter.checks.util.SourceUtil;

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

		for (String deprecatedPackageName : _DEPRECATED_PACKAGE_NAMES) {
			int x = -1;

			while (true) {
				x = content.indexOf(
					"\"" + deprecatedPackageName + "\":", x + 1);

				if (x == -1) {
					break;
				}

				int lineNumber = SourceUtil.getLineNumber(content, x);

				addMessage(
					fileName,
					"Do not use deprecated package '" + deprecatedPackageName +
						"'",
					lineNumber);
			}
		}

		return content;
	}

	private static final String[] _DEPRECATED_PACKAGE_NAMES = {
		"@clayui/checkbox", "liferay-module-config-generator", "metal-cli"
	};

}