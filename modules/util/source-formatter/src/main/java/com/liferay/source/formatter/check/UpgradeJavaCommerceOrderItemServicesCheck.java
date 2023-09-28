/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.util.JavaSourceUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaClassParser;
import com.liferay.source.formatter.parser.JavaMethod;
import com.liferay.source.formatter.parser.JavaTerm;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Nícolas Moura
 */
public class UpgradeJavaCommerceOrderItemServicesCheck
	extends BaseUpgradeCheck {

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

				if (!variableName.contains(
						"CommerceOrderItemLocalServiceUtil") &&
					!variableName.contains("CommerceOrderItemServiceUtil") &&
					!hasClassOrVariableName(
						"CommerceOrderItemLocalService", newContent, newContent,
						methodCall) &&
					!hasClassOrVariableName(
						"CommerceOrderItemService", newContent, newContent,
						methodCall)) {

					continue;
				}

				String message = StringBundler.concat(
					"Unable to format methods addCommerceOrderItem and ",
					"deleteCommerceOrderItems from CommerceOrderItemLocal",
					"Service, CommerceOrderItemLocalServiceUtil, CommerceOrder",
					"ItemService, CommerceOrderItemServiceUtil. Fill the new ",
					"parameters manually, see LPS-196580");

				List<String> parameterList = JavaSourceUtil.getParameterList(
					methodCall);

				String newMethodCall = methodCall;

				if (methodCall.contains(".addCommerceOrderItem")) {
					String[] parameterTypes = {
						"long", "long", "int", "int", "String",
						"CommerceContext", "ServiceContext"
					};

					if (!hasValidParameters(
							7, fileName, javaMethodContent, message,
							parameterList, parameterTypes)) {

						continue;
					}

					List<String> newParmeterList = new ArrayList<>(7);

					if (!variableName.contains(
							"CommerceOrderItemLocalServiceUtil") &&
						!variableName.contains(
							"CommerceOrderItemServiceUtil")) {

						newParmeterList.add("0");
					}

					newParmeterList.add(parameterList.get(0));
					newParmeterList.add(parameterList.get(1));
					newParmeterList.add(parameterList.get(4));
					newParmeterList.add(
						StringBundler.concat(
							"BigDecimal.valueOf(", parameterList.get(2),
							StringPool.CLOSE_PARENTHESIS));
					newParmeterList.add(
						StringBundler.concat(
							"BigDecimal.valueOf(", parameterList.get(3),
							StringPool.CLOSE_PARENTHESIS));
					newParmeterList.add(parameterList.get(5));
					newParmeterList.add(parameterList.get(6));

					if (newParmeterList.size() == 7) {
						newMethodCall = JavaSourceUtil.addMethodNewParameters(
							JavaSourceUtil.getIndent(methodCall),
							new int[] {4, 6}, matcher.group(),
							new String[] {parameterList.get(1), null},
							newParmeterList);
					}
					else {
						newMethodCall = JavaSourceUtil.addMethodNewParameters(
							JavaSourceUtil.getIndent(methodCall),
							new int[] {5, 7}, matcher.group(),
							new String[] {parameterList.get(1), null},
							newParmeterList);
					}
				}
				else if (methodCall.contains(".deleteCommerceOrderItems") &&
						 (variableName.contains(
							 "CommerceOrderItemLocalServiceUtil") ||
						  hasClassOrVariableName(
							  "CommerceOrderItemLocalService", newContent,
							  newContent, methodCall))) {

					String[] parameterTypes = {"long"};

					if (!hasValidParameters(
							1, fileName, javaMethodContent, message,
							parameterList, parameterTypes)) {

						continue;
					}

					newMethodCall = JavaSourceUtil.addMethodNewParameters(
						JavaSourceUtil.getIndent(methodCall), new int[] {0},
						matcher.group(), new String[] {"0"}, parameterList);
				}

				newContent = StringUtil.replace(
					newContent, methodCall, newMethodCall);
			}
		}

		return newContent;
	}

	private static final Pattern _addOrUpdateLayoutPattern = Pattern.compile(
		"\\t*\\w+\\.(?:addCommerceOrderItem|deleteCommerceOrderItems)\\(");

}