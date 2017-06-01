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
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (fileName.endsWith("Test.java") ||
			fileName.endsWith("UpgradeTableListener.java") ||
			content.contains("ThrowableAwareRunnable")) {

			return content;
		}

		String className = JavaSourceUtil.getClassName(fileName);

		if (!className.contains("Upgrade") && !className.contains("Verify")) {
			return content;
		}

		int x = -1;

		while (true) {
			x = content.indexOf(
				"DataAccess.getUpgradeOptimizedConnection", x + 1);

			if (x == -1) {
				break;
			}

			addMessage(
				fileName,
				"Use existing connection field instead of " +
					"DataAccess.getUpgradeOptimizedConnection",
				getLineCount(content, x));
		}

		return content;
	}

}