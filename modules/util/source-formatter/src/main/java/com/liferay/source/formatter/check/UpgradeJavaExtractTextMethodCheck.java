/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	protected String formatMatcherIteration(
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