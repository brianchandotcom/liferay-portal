/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringUtil;
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
public class UpgradeJavaAddFileEntryParameterCheck extends BaseUpgradeCheck {

	@Override
	protected String format(
			String fileName, String absolutePath, String content)
		throws Exception {

		String newContent = content;

		JavaClass javaClass = JavaClassParser.parseJavaClass(fileName, content);

		for (JavaTerm childJavaTerm : javaClass.getChildJavaTerms()) {
			if (!childJavaTerm.isJavaMethod()) {
				continue;
			}

			JavaMethod javaMethod = (JavaMethod)childJavaTerm;

			String javaMethodContent = javaMethod.getContent();

			Matcher matcher = _addFileEntryPattern.matcher(javaMethodContent);

			while (matcher.find()) {
				String methodCall = JavaSourceUtil.getMethodCall(
					javaMethodContent, matcher.start());

				String variableName = getVariableName(methodCall);

				if (!variableName.contains("DLAppLocalServiceUtil") &&
					!hasClassOrVariableName(
						"DLAppLocalService", content, content, methodCall)) {

					continue;
				}

				String message = StringBundler.concat(
					"Unable to format method addFileEntry from ",
					"DLAppLocalService and DLAppLocalServiceUtil. Fill the ",
					"new parameters manually, see LPS-194818");

				List<String> parameterList = JavaSourceUtil.getParameterList(
					methodCall);

				String[] parameterTypes = {
					"long", "long", "long", "String", "String", "String",
					"String", "String", "byte[]", "ServiceContext"
				};

				if (!hasValidParameters(
						10, fileName, javaMethodContent, message, parameterList,
						parameterTypes)) {

					continue;
				}

				String newMethod = JavaSourceUtil.addMethodNewParameters(
					JavaSourceUtil.getIndent(methodCall),
					new int[] {0, 7, 11, 12}, matcher.group(),
					new String[] {null, null, null, null}, parameterList);

				newContent = StringUtil.replace(
					newContent, methodCall, newMethod);
			}
		}

		return newContent;
	}

	private static final Pattern _addFileEntryPattern = Pattern.compile(
		"\\t*\\w+\\.addFileEntry\\(");

}