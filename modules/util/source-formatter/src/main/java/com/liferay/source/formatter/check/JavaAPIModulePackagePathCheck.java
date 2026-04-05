/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.source.formatter.check.util.JavaSourceUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

		if (!absolutePath.contains("-api/src/") ||
			absolutePath.contains("/gradleTest/")) {

			return content;
		}

		Matcher matcher = _packageNamePattern.matcher(
			JavaSourceUtil.getPackageName(content));

		if (matcher.find()) {
			addMessage(
				fileName,
				"Do not use \"api\", \"impl\", or \"internal\" in package " +
					"names for classes within API modules");
		}

		return content;
	}

	private static final Pattern _packageNamePattern = Pattern.compile(
		"\\.(api|impl|internal)(\\.|\\Z)");

}