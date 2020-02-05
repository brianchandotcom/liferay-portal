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

import com.liferay.source.formatter.checks.util.JavaSourceUtil;

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