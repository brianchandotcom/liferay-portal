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

/**
 * @author Hugo Huijser
 */
public class JavaVerifyUpgradeConnectionCheck extends BaseFileCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (absolutePath.contains("/test/") ||
			fileName.endsWith("DBUpgrader.java") ||
			fileName.endsWith("Test.java") ||
			fileName.endsWith("UpgradeTableListener.java") ||
			content.contains("Callable<Void>")) {

			return content;
		}

		String className = JavaSourceUtil.getClassName(fileName);

		if (className.contains("Upgrade") || className.contains("Verify")) {
			_checkConnectionField(fileName, content, "getConnection");
			_checkConnectionField(
				fileName, content, "getUpgradeOptimizedConnection");
		}

		return content;
	}

	private void _checkConnectionField(
		String fileName, String content, String methodName) {

		int x = -1;

		while (true) {
			x = content.indexOf("DataAccess." + methodName, x + 1);

			if (x == -1) {
				break;
			}

			addMessage(
				fileName,
				"Use existing connection field instead of DataAccess." +
					methodName,
				getLineNumber(content, x));
		}
	}

}