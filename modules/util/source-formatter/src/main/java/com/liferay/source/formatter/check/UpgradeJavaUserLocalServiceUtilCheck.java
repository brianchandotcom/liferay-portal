/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
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
 * @author Michael Cavalcanti
 */
public class UpgradeJavaUserLocalServiceUtilCheck extends BaseUpgradeCheck {

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

			content = StringUtil.replace(
				content, javaMethodContent,
				_checkAddUser(javaMethodContent, content, fileName));

			content = StringUtil.replace(
				content, javaMethodContent,
				_checkUpdateStatus(javaMethodContent, content, fileName));
		}

		return content;
	}

	@Override
	protected String[] getNewImports() {
		return new String[] {
			"com.liferay.portal.kernel.service.ServiceContextThreadLocal"
		};
	}

	private String _checkAddUser(
		String content, String fileContent, String fileName) {

		String newContent = content;

		Matcher addUserMatcher = _addUserPattern.matcher(content);

		while (addUserMatcher.find()) {
			String methodCall = JavaSourceUtil.getMethodCall(
				content, addUserMatcher.start());

			if (!_checkMethodCall(
					content, fileContent, addUserMatcher.group(2))) {

				continue;
			}

			String message = StringBundler.concat(
				"Unable to format method addUser from UserLocalService, ",
				"UserLocalServiceUtil, UserService and UserServiceUtil. Fill ",
				"the new parameter manually, see LPS-192661 and LPS-196617.");

			List<String> parameterList = JavaSourceUtil.getParameterList(
				methodCall);

			if (!hasValidParameters(
					26, fileName, content, message, parameterList,
					new String[] {
						"long", "boolean", "String", "String", "boolean",
						"String", "String", "long", "String", "Locale",
						"String", "String", "String", "long", "long", "boolean",
						"int", "int", "int", "String", "long[]", "long[]",
						"long[]", "long[]", "boolean", "ServiceContext"
					}) &&
				!hasValidParameters(
					27, fileName, content, message, parameterList,
					new String[] {
						"long", "long", "boolean", "String", "String",
						"boolean", "String", "String", "long", "String",
						"Locale", "String", "String", "String", "long", "long",
						"boolean", "int", "int", "int", "String", "long[]",
						"long[]", "long[]", "long[]", "boolean",
						"ServiceContext"
					}) &&
				!hasValidParameters(
					31, fileName, content, message, parameterList,
					new String[] {
						"long", "boolean", "String", "String", "boolean",
						"String", "String", "long", "String", "Locale",
						"String", "String", "String", "long", "long", "boolean",
						"int", "int", "int", "String", "long[]", "long[]",
						"long[]", "long[]", "List<Address>",
						"List<EmailAddress>", "List<Phone>", "List<Website>",
						"List<AnnouncementsDelivery>", "boolean",
						"ServiceContext"
					})) {

				continue;
			}

			newContent = StringUtil.replace(
				newContent, methodCall,
				_removeParameters(
					JavaSourceUtil.getIndent(methodCall), methodCall,
					parameterList, getVariableName(methodCall)));
		}

		return newContent;
	}

	private boolean _checkMethodCall(
		String content, String fileContent, String methodCall) {

		String variableName = getVariableName(methodCall);

		if (variableName.equals("UserLocalServiceUtil") ||
			variableName.equals("UserServiceUtil") ||
			hasClassOrVariableName(
				"UserLocalService", content, fileContent, methodCall) ||
			hasClassOrVariableName(
				"UserService", content, fileContent, methodCall)) {

			return true;
		}

		return false;
	}

	private String _checkUpdateStatus(
		String content, String fileContent, String fileName) {

		String newContent = content;

		Matcher updateStatusMatcher = _updateStatusPattern.matcher(content);

		while (updateStatusMatcher.find()) {
			String methodCall = JavaSourceUtil.getMethodCall(
				content, updateStatusMatcher.start());

			String message = StringBundler.concat(
				"Unable to format method updateStatus from UserLocalService, ",
				"UserLocalServiceUtil, UserService and UserServiceUtil. The ",
				"method signature has changed to updateStatus(long userId, ",
				"int status, ServiceContext serviceContext). Fill the new ",
				"parameter manually, see LPS-191999.");

			List<String> parameterList = JavaSourceUtil.getParameterList(
				methodCall);

			if (!_checkMethodCall(
					content, fileContent, updateStatusMatcher.group(2)) ||
				!hasValidParameters(
					2, fileName, content, message, parameterList,
					new String[] {"long", "int"})) {

				continue;
			}

			newContent = StringUtil.replace(
				newContent, methodCall,
				JavaSourceUtil.addMethodNewParameters(
					JavaSourceUtil.getIndent(methodCall), new int[] {2},
					updateStatusMatcher.group(),
					new String[] {
						"ServiceContextThreadLocal.getServiceContext()"
					},
					parameterList));
		}

		return newContent;
	}

	private String _removeParameters(
		String indent, String methodCall, List<String> parameterList,
		String variableName) {

		if (parameterList.size() == 27) {
			parameterList.remove(8);
			parameterList.remove(8);

			parameterList.add(19, "0");
		}
		else {
			parameterList.remove(7);
			parameterList.remove(7);
		}

		StringBundler sb = new StringBundler();

		sb.append(variableName);
		sb.append(".addUser(\n");
		sb.append(indent);
		sb.append(StringPool.TAB);
		sb.append(parameterList.get(0));

		for (int i = 1; i < parameterList.size(); i++) {
			if ((i % 4) == 0) {
				sb.append(StringPool.COMMA);
				sb.append(StringPool.NEW_LINE);
				sb.append(indent);
				sb.append(StringPool.TAB);
				sb.append(parameterList.get(i));

				continue;
			}

			sb.append(StringPool.COMMA_AND_SPACE);
			sb.append(parameterList.get(i));
		}

		sb.append(StringPool.CLOSE_PARENTHESIS);

		return StringUtil.replace(methodCall, methodCall, sb.toString());
	}

	private static final Pattern _addUserPattern = Pattern.compile(
		"(|\\t*\\w*\\s*\\w+\\s*\\=|\\t*return?)\\t*\\s?(\\w+\\.addUser\\()");
	private static final Pattern _updateStatusPattern = Pattern.compile(
		"(|\\t*\\w*\\s*\\w+\\s*\\=|\\t*return?)\\t*\\s?" +
			"(\\w+\\.updateStatus\\()");

}