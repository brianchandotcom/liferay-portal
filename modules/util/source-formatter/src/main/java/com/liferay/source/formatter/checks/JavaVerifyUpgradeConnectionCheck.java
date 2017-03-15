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

import com.liferay.portal.kernel.util.Tuple;
import com.liferay.source.formatter.SourceFormatterMessage;
import com.liferay.source.formatter.checks.util.JavaSourceUtil;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Hugo Huijser
 */
public class JavaVerifyUpgradeConnectionCheck extends BaseFileCheck {

	public JavaVerifyUpgradeConnectionCheck(List<String> excludes) {
		_excludes = excludes;
	}

	@Override
	public Tuple process(String fileName, String absolutePath, String content)
		throws Exception {

		if (isExcludedPath(_excludes, absolutePath) ||
			fileName.endsWith("Test.java") ||
			fileName.endsWith("UpgradeTableListener.java") ||
			content.contains("ThrowableAwareRunnable")) {

			return new Tuple(content, Collections.emptySet());
		}

		String className = JavaSourceUtil.getClassName(fileName);

		if (!className.contains("Upgrade") && !className.contains("Verify")) {
			return new Tuple(content, Collections.emptySet());
		}

		Set<SourceFormatterMessage> sourceFormatterMessages = new HashSet<>();

		int x = -1;

		while (true) {
			x = content.indexOf(
				"DataAccess.getUpgradeOptimizedConnection", x + 1);

			if (x == -1) {
				break;
			}

			addMessage(
				sourceFormatterMessages, fileName,
				"Use existing connection field instead of " +
					"DataAccess.getUpgradeOptimizedConnection",
				getLineCount(content, x));
		}

		return new Tuple(content, sourceFormatterMessages);
	}

	private final List<String> _excludes;

}