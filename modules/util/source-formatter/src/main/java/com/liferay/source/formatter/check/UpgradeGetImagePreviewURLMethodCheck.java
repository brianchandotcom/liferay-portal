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

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.util.JavaSourceUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Tamyris Bernardo
 */
public class UpgradeGetImagePreviewURLMethodCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (!fileName.endsWith(".java") && !fileName.endsWith(".jsp")) {
			return content;
		}

		boolean replaced = false;

		Matcher getImagePreviewURLMatcher = _getImagePreviewURLPattern.matcher(
			content);

		while (getImagePreviewURLMatcher.find()) {
			String methodCall = getImagePreviewURLMatcher.group();

			content = StringUtil.replace(
				content, methodCall,
				StringUtil.replace(methodCall, "DLUtil", "_dlURLHelper"));

			replaced = true;
		}

		if (fileName.endsWith(".java") && replaced) {
			content = JavaSourceUtil.addImports(
				content, "com.liferay.document.library.util.DLURLHelper");
			content = StringUtil.replaceLast(
				content, CharPool.CLOSE_CURLY_BRACE,
				"\t@Reference\n\tprivate DLURLHelper _dlURLHelper;\n\n}");
		}

		return content;
	}

	private static final Pattern _getImagePreviewURLPattern = Pattern.compile(
		"DLUtil\\.\\s*getImagePreviewURL\\(");

}