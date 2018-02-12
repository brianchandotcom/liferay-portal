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

import com.liferay.petra.string.StringPool;
import com.liferay.source.formatter.checks.util.JavaSourceUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaModuleServiceReferenceCheck extends BaseFileCheck {

	@Override
	public boolean isModulesCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		String packageName = JavaSourceUtil.getPackageName(content);

		int pos = packageName.indexOf(".service.");

		if (pos == -1) {
			return content;
		}

		String servicePackageName = packageName.substring(0, pos + 8);

		_checkServiceReferences(
			fileName, content, packageName, servicePackageName);

		return content;
	}

	private void _checkServiceReferences(
		String fileName, String content, String packageName,
		String servicePackageName) {

		Matcher matcher = _serviceReferencePattern.matcher(content);

		while (matcher.find()) {
			String className = _getFullClassName(
				content, matcher.group(1), packageName);

			if (className.startsWith(servicePackageName)) {
				addMessage(
					fileName, "Use @BeanReference instead of @ServiceReference",
					getLineCount(content, matcher.start()));
			}
		}
	}

	private String _getFullClassName(
		String content, String className, String packageName) {

		if (className.contains(StringPool.PERIOD)) {
			return className;
		}

		Pattern pattern = Pattern.compile("import (.*" + className + ");");

		Matcher matcher = pattern.matcher(content);

		if (matcher.find()) {
			return matcher.group(1);
		}

		return packageName + StringPool.PERIOD + className;
	}

	private final Pattern _serviceReferencePattern = Pattern.compile(
		"@ServiceReference\\(\\s*type = ([\\w.]+)\\.class\\)\n");

}