/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

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
 * @author Tamyris Bernardo
 */
public class UpgradeJavaUpdateFileEntryMethodCheck extends BaseUpgradeCheck {

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

			Matcher matcher = _updateFileEntryPattern.matcher(
				javaMethodContent);

			while (matcher.find()) {
				String methodCall = JavaSourceUtil.getMethodCall(
					javaMethodContent, matcher.start());

				List<String> parameterList = JavaSourceUtil.getParameterList(
					methodCall);

				if (parameterList.size() != 10) {
					continue;
				}

				String variableName = getVariableName(methodCall);

				if (!hasClassOrVariableName(
						"DLAppLocalService", content, content, methodCall) &&
					!variableName.contains("DLAppLocalServiceUtil")) {

					continue;
				}

				String className = getVariableTypeName(
					newContent, newContent, parameterList.get(8), true);

				if (!className.equals("byte[]")) {
					continue;
				}

				String newMethod = null;

				String message = StringBundler.concat(
					"Unable to format method updateFileEntry from ",
					"DLAppLocalService and DLAppLocalServiceUtil. Fill the ",
					"new parameters manually, see LPS-194134.");

				String parameterClass = getVariableTypeName(
					content, content, parameterList.get(7));

				if (parameterClass.equals("DLVersionNumberIncrease")) {
					String[] parameterTypes = {
						"long", "long", "String", "String", "String", "String",
						"String", "DLVersionNumberIncrease", "byte[]",
						"ServiceContext"
					};

					if (!hasValidParameters(
							parameterTypes.length, fileName, javaMethodContent,
							message, parameterList, parameterTypes)) {

						continue;
					}

					newMethod = JavaSourceUtil.addMethodNewParameters(
						JavaSourceUtil.getIndent(methodCall),
						new int[] {5, 10, 11}, matcher.group(),
						new String[] {null, null, null}, parameterList);

					newContent = StringUtil.replace(
						newContent, methodCall, newMethod);
				}
				else {
					String[] parameterTypes = {
						"long", "long", "String", "String", "String", "String",
						"String", "boolean", "byte[]", "ServiceContext"
					};

					if (!hasValidParameters(
							parameterTypes.length, fileName, javaMethodContent,
							message, parameterList, parameterTypes)) {

						continue;
					}

					parameterList.remove(7);

					newMethod = JavaSourceUtil.addMethodNewParameters(
						JavaSourceUtil.getIndent(methodCall),
						new int[] {5, 8, 10, 11}, matcher.group(),
						new String[] {
							null, "dlVersionNumberIncrease", null, null
						},
						parameterList);

					newContent = StringUtil.replace(
						newContent, methodCall, newMethod);
				}
			}
		}

		return newContent;
	}

	@Override
	protected String[] getNewImports() {
		return new String[] {
			"com.liferay.document.library.kernel.model.DLVersionNumberIncrease",
			"java.util.Date"
		};
	}

	private static final Pattern _updateFileEntryPattern = Pattern.compile(
		"\\t*\\w+\\.updateFileEntry\\(");

}