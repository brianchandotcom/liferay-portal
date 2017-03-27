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
import com.liferay.source.formatter.checks.util.BNDSourceUtil;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Hugo Huijser
 */
public class BNDWebContextPathCheck extends BaseFileCheck {

	@Override
	public Tuple process(String fileName, String absolutePath, String content)
		throws Exception {

		if (!fileName.endsWith("/bnd.bnd") ||
			absolutePath.contains("/testIntegration/") ||
			absolutePath.contains("/third-party/")) {

			return new Tuple(content, Collections.emptySet());
		}

		Set<SourceFormatterMessage> sourceFormatterMessages = new HashSet<>();

		_checkWebContextPath(
			sourceFormatterMessages, fileName, absolutePath, content);

		return new Tuple(content, sourceFormatterMessages);
	}

	private void _checkWebContextPath(
		Set<SourceFormatterMessage> sourceFormatterMessages, String fileName,
		String absolutePath, String content) {

		String moduleName = BNDSourceUtil.getModuleName(absolutePath);

		if (moduleName.contains("-import-") ||
			moduleName.contains("-private-")) {

			return;
		}

		String webContextPath = BNDSourceUtil.getDefinitionValue(
			content, "Web-ContextPath");

		if ((webContextPath != null) &&
			!webContextPath.equals("/" + moduleName)) {

			addMessage(
				sourceFormatterMessages, fileName,
				"Incorrect Web-ContextPath '" + webContextPath + "'");
		}
	}

}