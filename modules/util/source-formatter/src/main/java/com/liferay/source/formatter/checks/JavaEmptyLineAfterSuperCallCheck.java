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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.parser.JavaConstructor;
import com.liferay.source.formatter.parser.JavaTerm;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaEmptyLineAfterSuperCallCheck extends BaseJavaTermCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, JavaTerm javaTerm,
		String fileContent) {

		Pattern pattern = null;

		if (javaTerm instanceof JavaConstructor) {
			pattern = _constructorSuperCallPattern;
		}
		else {
			pattern = Pattern.compile(
				"\tsuper\\.\\s*" + javaTerm.getName() + "\\(.*?;\n\t*([^\t])",
				Pattern.DOTALL);
		}

		String javaTermContent = javaTerm.getContent();

		Matcher matcher = pattern.matcher(javaTermContent);

		if (!matcher.find()) {
			return javaTermContent;
		}

		String s = matcher.group(1);

		if (!s.equals(StringPool.CLOSE_CURLY_BRACE) &&
			!s.equals(StringPool.NEW_LINE) &&
			(getLevel(javaTermContent.substring(0, matcher.start())) == 0)) {

			return StringUtil.replaceFirst(
				javaTermContent, ";\n", ";\n\n", matcher.start());
		}

		return javaTermContent;
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CONSTRUCTOR, JAVA_METHOD};
	}

	private final Pattern _constructorSuperCallPattern = Pattern.compile(
		"\tsuper\\(.*?;\n\t*([^\t])", Pattern.DOTALL);

}