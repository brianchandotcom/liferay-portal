/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaClassParser;
import com.liferay.source.formatter.parser.JavaMethod;
import com.liferay.source.formatter.parser.JavaTerm;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Cavalcanti
 */
public class UpgradeJavaGetLayoutDisplayPageProviderCheck
	extends BaseUpgradeCheck {

	@Override
	protected String format(
			String fileName, String absolutePath, String content)
		throws Exception {

		JavaClass javaClass = JavaClassParser.parseJavaClass(fileName, content);

		for (JavaTerm childJavaTerm : javaClass.getChildJavaTerms()) {
			if (!childJavaTerm.isJavaMethod()) {
				continue;
			}

			JavaMethod javaMethod = (JavaMethod)childJavaTerm;

			String javaMethodContent = javaMethod.getContent();

			Matcher matcher = _pattern.matcher(javaMethodContent);

			while (matcher.find()) {
				if (!hasClassOrVariableName(
						"LayoutDisplayPageProviderRegistry", javaMethodContent,
						content, matcher.group())) {

					continue;
				}

				String line = matcher.group();

				String newLine = StringUtil.replace(
					line, "getLayoutDisplayPageProvider",
					"getLayoutDisplayPageProviderByClassName");

				javaMethodContent = StringUtil.replace(
					javaMethodContent, line, newLine);
			}

			content = StringUtil.replace(
				content, javaMethod.getContent(), javaMethodContent);
		}

		return content;
	}

	private static final Pattern _pattern = Pattern.compile(
		"\\w+\\.\\s*getLayoutDisplayPageProvider\\s*\\(\\s*.+\\s*\\)\\;");

}