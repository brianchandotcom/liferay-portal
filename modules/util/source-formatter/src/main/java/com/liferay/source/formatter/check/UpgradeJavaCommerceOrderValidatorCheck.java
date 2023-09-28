/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.util.JavaSourceUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaClassParser;
import com.liferay.source.formatter.parser.JavaMethod;
import com.liferay.source.formatter.parser.JavaTerm;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Micaelle Silva
 */
public class UpgradeJavaCommerceOrderValidatorCheck extends BaseUpgradeCheck {

	@Override
	protected String format(
			String fileName, String absolutePath, String content)
		throws Exception {

		JavaClass javaClass = JavaClassParser.parseJavaClass(fileName, content);

		List<String> implementedClassNames =
			javaClass.getImplementedClassNames();

		if (!implementedClassNames.contains("CommerceOrderValidator")) {
			return content;
		}

		for (JavaTerm childJavaTerm : javaClass.getChildJavaTerms()) {
			if (!childJavaTerm.isJavaMethod()) {
				continue;
			}

			JavaMethod javaMethod = (JavaMethod)childJavaTerm;

			String javaMethodContent = javaMethod.getContent();

			Matcher matcher = _validatePattern.matcher(javaMethodContent);

			if (!matcher.find()) {
				continue;
			}

			String newJavaMethodContent = _formatMethod(
				javaMethodContent, matcher);

			content = StringUtil.replace(
				content, javaMethodContent, newJavaMethodContent);
		}

		return content;
	}

	@Override
	protected String[] getNewImports() {
		return new String[] {"java.math.BigDecimal"};
	}

	private static String _formatMethod(
		String javaMethodContent, Matcher matcher) {

		String subjavaMethodContent = javaMethodContent.substring(
			javaMethodContent.indexOf(CharPool.OPEN_CURLY_BRACE));

		String deprecatedParameterName = matcher.group(1);

		javaMethodContent = StringUtil.replace(
			javaMethodContent, subjavaMethodContent,
			StringUtil.replace(
				subjavaMethodContent, new String[] {deprecatedParameterName},
				new String[] {deprecatedParameterName + "Int"}, true));

		String methodCall = matcher.group();

		javaMethodContent = StringUtil.replace(
			javaMethodContent, methodCall,
			StringUtil.replace(
				methodCall, "int quantity", "BigDecimal quantity"));

		javaMethodContent = StringUtil.replaceFirst(
			javaMethodContent, CharPool.OPEN_CURLY_BRACE,
			StringBundler.concat(
				"{\n\n", JavaSourceUtil.getIndent(javaMethodContent),
				"\tint quantityInt = quantity.intValue();"));

		return javaMethodContent;
	}

	private static final Pattern _validatePattern = Pattern.compile(
		"validate\\(\\s*Locale \\w+,\\s*CommerceOrder \\w+,\\s*" +
			"CPInstance \\w+,\\s*int (\\w+)\\)");

}