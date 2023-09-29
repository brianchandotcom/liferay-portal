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
import com.liferay.source.formatter.parser.ParseException;

import java.io.IOException;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Albert Gomes Cabral
 */
public class UpgradeJavaGetFDSTableSchemaParameterCheck
	extends BaseUpgradeCheck {

	@Override
	protected String format(
			String fileName, String absolutePath, String content)
		throws IOException, ParseException {

		String newContent = content;

		JavaClass javaClass = JavaClassParser.parseJavaClass(fileName, content);

		List<String> extendedClassNames = javaClass.getExtendedClassNames();

		for (JavaTerm childJavaTerm : javaClass.getChildJavaTerms()) {
			if (!childJavaTerm.isJavaMethod()) {
				continue;
			}

			if (extendedClassNames.contains("BaseTableFDSView")) {
				JavaMethod javaMethod = (JavaMethod)childJavaTerm;

				String javaMethodContent = javaMethod.getContent();

				Matcher matcher = _getFDSTableSchemaPattern.matcher(
					javaMethodContent);

				while (matcher.find()) {
					String methodCall = matcher.group();

					newContent = StringUtil.replace(
						content, methodCall,
						StringUtil.replace(
							methodCall, matcher.group(1), "(Locale locale)"));
				}
			}
		}

		return newContent;
	}

	@Override
	protected String[] getNewImports() {
		return new String[] {"java.util.Locale"};
	}

	private static final Pattern _getFDSTableSchemaPattern = Pattern.compile(
		"\\w+\\s+FDSTableSchema\\s+getFDSTableSchema(\\(\\))");

}