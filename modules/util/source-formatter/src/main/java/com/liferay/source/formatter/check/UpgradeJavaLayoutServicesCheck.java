/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.ArrayUtil;
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
public class UpgradeJavaLayoutServicesCheck extends BaseUpgradeCheck {

	public boolean validateParameters(
		String fileName, String javaMethodContent, List<String> parameterList,
		String[] parameterTypes) {

		if (!hasParameterTypes(
				javaMethodContent, javaMethodContent,
				ArrayUtil.toStringArray(parameterList), parameterTypes)) {

			addMessage(
				fileName,
				StringBundler.concat(
					"Unable to format methods addLayout and updateLayout from ",
					"LayoutService, LayoutLocalService, LayoutServiceUtil and ",
					"LayoutLocalServiceUtil. Fill the new parameters manually,",
					"see LPS-188828 and LPS-190401"));

			return false;
		}

		return true;
	}

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

			Matcher matcher = _addOrUpdateLayoutPattern.matcher(
				javaMethodContent);

			while (matcher.find()) {
				String methodCall = JavaSourceUtil.getMethodCall(
					javaMethodContent, matcher.start());

				String variableName = getVariableName(methodCall);

				if (!variableName.contains("LayoutLocalServiceUtil") &&
					!variableName.contains("LayoutServiceUtil") &&
					!hasClassOrVariableName(
						"LayoutLocalService", newContent, newContent,
						methodCall) &&
					!hasClassOrVariableName(
						"LayoutService", newContent, newContent, methodCall)) {

					continue;
				}

				List<String> parameterList = JavaSourceUtil.getParameterList(
					methodCall);

				if (methodCall.contains(".addLayout")) {
					String[] parameterTypes = {
						"long", "long", "boolean", "long", "long", "long",
						"Map<java.util.Locale, String>",
						"Map<java.util.Locale, String>",
						"Map<java.util.Locale, String>",
						"Map<java.util.Locale, String>",
						"Map<java.util.Locale, String>", "String", "String",
						"boolean", "boolean", "Map<java.util.Locale, String>",
						"ServiceContext"
					};

					newContent = _replaceAddOrUpdateLayout(
						newContent, 17, fileName, javaMethodContent,
						new int[] {parameterList.size() - 1}, matcher,
						methodCall, new String[] {"0"}, parameterList,
						parameterTypes);
				}
				else if (methodCall.contains(".updateLayout")) {
					String[] parameterTypes = {
						"long", "boolean", "long", "long",
						"Map<java.util.Locale, String>",
						"Map<java.util.Locale, String>",
						"Map<java.util.Locale, String>",
						"Map<java.util.Locale, String>",
						"Map<java.util.Locale, String>", "String", "boolean",
						"Map<java.util.Locale, String>", "boolean", "byte[]",
						"ServiceContext"
					};

					int index = parameterList.size() - 1;

					newContent = _replaceAddOrUpdateLayout(
						newContent, 15, fileName, javaMethodContent,
						new int[] {index, index, index}, matcher, methodCall,
						new String[] {"0", "0", "0"}, parameterList,
						parameterTypes);
				}
			}
		}

		return newContent;
	}

	private String _replaceAddOrUpdateLayout(
		String content, int expectedParametersSize, String fileName,
		String javaMethodContent, int[] indexNewParameters, Matcher matcher,
		String methodCall, String[] newParameters, List<String> parameterList,
		String[] parameterTypes) {

		if ((parameterList.size() != expectedParametersSize) ||
			!validateParameters(
				fileName, javaMethodContent, parameterList, parameterTypes)) {

			return content;
		}

		String newMethod = JavaSourceUtil.addMethodNewParameters(
			JavaSourceUtil.getIndent(methodCall), indexNewParameters,
			matcher.group(), newParameters, parameterList);

		return StringUtil.replace(content, methodCall, newMethod);
	}

	private static final Pattern _addOrUpdateLayoutPattern = Pattern.compile(
		"\\t*\\w+\\.(?:updateLayout|addLayout)\\(");

}