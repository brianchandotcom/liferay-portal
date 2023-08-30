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
 * @author Micaelle Silva
 */
public class UpgradeJavaAddCategoryParameterCheck extends BaseUpgradeCheck {

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

			Matcher matcher = _addCategoryPattern.matcher(javaMethodContent);

			while (matcher.find()) {
				String methodCall = JavaSourceUtil.getMethodCall(
					javaMethodContent, matcher.start());

				String variableName = getVariableName(methodCall);

				if (!variableName.contains("AssetCategoryLocalServiceUtil") &&
					!hasClassOrVariableName(
						"AssetCategoryLocalService", content, content,
						methodCall)) {

					continue;
				}

				String message = StringBundler.concat(
					"Unable to format method addCategory from ",
					"AssetCategoryLocalService and ",
					"AssetCategoryLocalServiceUtil. Fill the new parameters ",
					"manually, see LPS-192320");

				List<String> parameterList = JavaSourceUtil.getParameterList(
					methodCall);

				String[] parameterTypes = {
					"long", "long", "long", "Map<Locale, String>",
					"Map<Locale, String>", "long", "String[]", "ServiceContext"
				};

				if (!hasValidParameters(
						8, fileName, javaMethodContent, message, parameterList,
						parameterTypes)) {

					continue;
				}

				String newMethod = JavaSourceUtil.addMethodNewParameters(
					JavaSourceUtil.getIndent(methodCall), new int[] {0},
					matcher.group(), new String[] {null}, parameterList);

				newContent = StringUtil.replace(
					newContent, methodCall, newMethod);
			}
		}

		return newContent;
	}

	private static final Pattern _addCategoryPattern = Pattern.compile(
		"\\t*\\w*\\.addCategory\\(");

}