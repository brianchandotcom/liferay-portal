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
 * @author Nícolas Moura
 */
public class UpgradeJavaExtractTextMethodCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (!fileName.endsWith(".java")) {
			return content;
		}

		String newContent = content;

		boolean replaced = false;

		Matcher extractTextMatcher = _extractTextPattern.matcher(content);

		while (extractTextMatcher.find()) {
			String methodCall = JavaSourceUtil.getMethodCall(
				content, extractTextMatcher.start());

			newContent = StringUtil.replace(
				newContent, methodCall,
				StringUtil.replace(
					methodCall, "HtmlUtil.extractText(",
					"_htmlParser.extractText("));

			replaced = true;
		}

		if (replaced) {
			newContent = JavaSourceUtil.addImports(
				newContent, "com.liferay.portal.kernel.util.HtmlParser",
				"org.osgi.service.component.annotations.Reference");
			newContent = StringUtil.replaceLast(
				newContent, CharPool.CLOSE_CURLY_BRACE,
				"\n\t@Reference\n\tprivate HtmlParser _htmlParser;\n}");
		}

		return newContent;
	}

	private static final Pattern _extractTextPattern = Pattern.compile(
		"HtmlUtil\\.extractText\\(");

}