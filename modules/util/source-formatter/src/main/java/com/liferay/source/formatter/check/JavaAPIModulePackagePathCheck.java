/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.source.formatter.check.util.JavaSourceUtil;

/**
 * @author Alan Huang
 */
public class JavaAPIModulePackagePathCheck extends BaseFileCheck {

	@Override
	public boolean isModuleSourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (!absolutePath.contains("-api/src/")) {
			return content;
		}

		String packageName = JavaSourceUtil.getPackageName(content);

		if (packageName.contains(".api.") || packageName.contains(".impl.") ||
			packageName.contains(".internal.") ||
			packageName.endsWith(".api") || packageName.endsWith(".impl") ||
			packageName.endsWith(".internal")) {

			addMessage(
				fileName,
				"Do not use \"api\", \"impl\" or \"internal\" in package " +
					"names for classes within API modules");
		}

		return content;
	}

}