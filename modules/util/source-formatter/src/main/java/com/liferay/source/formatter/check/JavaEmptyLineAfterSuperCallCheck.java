/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
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

		if (javaTerm.isJavaConstructor()) {
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

	private static final Pattern _constructorSuperCallPattern = Pattern.compile(
		"\tsuper\\(.*?;\n\t*([^\t])", Pattern.DOTALL);

}