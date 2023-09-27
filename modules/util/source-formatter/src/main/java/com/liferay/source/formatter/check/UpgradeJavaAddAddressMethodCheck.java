/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
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
public class UpgradeJavaAddAddressMethodCheck extends BaseUpgradeCheck {

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

			Matcher matcher = _addAdressPattern.matcher(javaMethodContent);

			while (matcher.find()) {
				String methodCall = JavaSourceUtil.getMethodCall(
					javaMethodContent, matcher.start());

				String variableName = getVariableName(methodCall);

				if (!variableName.contains("AddressLocalServiceUtil") &&
					!variableName.contains("AddressServiceUtil") &&
					!hasClassOrVariableName(
						"AddressLocalService", content, content, methodCall) &&
					!hasClassOrVariableName(
						"AddressService", content, content, methodCall)) {

					continue;
				}

				String message = StringBundler.concat(
					"Unable to format method addAddress from ",
					"AddressLocalService, AddressLocalServiceUtil, ",
					"AddressService and AddressServiceUtil. Fill the new ",
					"parameters manually, see LPS-193462");

				List<String> parameterList = JavaSourceUtil.getParameterList(
					methodCall);

				String newMethod = null;

				String lastParameter = parameterList.get(
					parameterList.size() - 1);

				if (!lastParameter.equals("serviceContext")) {
					String[] parameterTypes = {
						"long", "String", "long", "String", "String", "String",
						"String", "String", "long", "long", "long", "boolean",
						"boolean"
					};

					if (!hasValidParameters(
							13, fileName, javaMethodContent, message,
							parameterList, parameterTypes)) {

						continue;
					}

					newMethod = JavaSourceUtil.addMethodNewParameters(
						JavaSourceUtil.getIndent(methodCall),
						new int[] {
							0, 4, 5, parameterList.size() + 3,
							parameterList.size() + 4
						},
						matcher.group(),
						new String[] {
							null, null, null, null,
							"ServiceContextThreadLocal.getServiceContext()"
						},
						parameterList);

					newContent = StringUtil.replace(
						content, methodCall, newMethod);
				}
				else {
					String[] parameterTypes = {
						"long", "String", "long", "String", "String", "String",
						"String", "String", "long", "long", "long", "boolean",
						"boolean", "ServiceContext"
					};

					if (!hasValidParameters(
							14, fileName, javaMethodContent, message,
							parameterList, parameterTypes)) {

						continue;
					}

					newMethod = JavaSourceUtil.addMethodNewParameters(
						JavaSourceUtil.getIndent(methodCall),
						new int[] {0, 4, 5, parameterList.size() + 2},
						matcher.group(), new String[] {null, null, null, null},
						parameterList);

					newContent = StringUtil.replace(
						content, methodCall, newMethod);
				}
			}
		}

		return newContent;
	}

	@Override
	protected String[] getNewImports() {
		return new String[] {
			"com.liferay.portal.kernel.service.ServiceContextThreadLocal"
		};
	}

	private static final Pattern _addAdressPattern = Pattern.compile(
		"\\t*\\w+\\.addAddress\\(");

}