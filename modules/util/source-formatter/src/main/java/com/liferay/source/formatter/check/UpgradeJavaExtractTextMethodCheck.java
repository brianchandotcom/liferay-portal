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
public class UpgradeJavaExtractTextMethodCheck
	extends BaseUpgradeMatcherReplacementCheck {

	@Override
	protected String afterFormat(
		String fileName, String absolutePath, String content,
		String newContent) {

		newContent = addNewImports(newContent);

		return StringUtil.replaceLast(
			newContent, CharPool.CLOSE_CURLY_BRACE,
			"\n\t@Reference\n\tprivate HtmlParser _htmlParser;\n}");
	}

	@Override
	protected String formatIteration(
		String content, String newContent, Matcher matcher) {

		String methodCall = JavaSourceUtil.getMethodCall(
			content, matcher.start());

		return StringUtil.replace(
			newContent, methodCall,
			StringUtil.replace(
				methodCall, "HtmlUtil.extractText(",
				"_htmlParser.extractText("));
	}

	@Override
	protected String[] getNewImports() {
		return new String[] {
			"com.liferay.portal.kernel.util.HtmlParser",
			"org.osgi.service.component.annotations.Reference"
		};
	}

	@Override
	protected Pattern getPattern() {
		return Pattern.compile("HtmlUtil\\.extractText\\(");
	}

}