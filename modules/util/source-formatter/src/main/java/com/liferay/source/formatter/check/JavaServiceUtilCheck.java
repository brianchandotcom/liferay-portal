/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.source.formatter.check.util.JavaSourceUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 * @author Brent Krone-Schmidt
 */
public class JavaServiceUtilCheck extends BaseFileCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		String className = JavaSourceUtil.getClassName(fileName);

		if (className.equals("BaseServiceImpl") ||
			!className.endsWith("ServiceImpl")) {

			return content;
		}

		Matcher matcher = _serviceUtilPattern.matcher(content);

		if (matcher.find()) {
			addMessage(
				fileName,
				"Do not use a portal-kernel *ServiceUtil in a *ServiceImpl " +
					"class, create a reference via service.xml instead");
		}

		return content;
	}

	private static final Pattern _serviceUtilPattern = Pattern.compile(
		"import com\\.liferay\\.[a-z]+\\.kernel\\..*ServiceUtil;");

}