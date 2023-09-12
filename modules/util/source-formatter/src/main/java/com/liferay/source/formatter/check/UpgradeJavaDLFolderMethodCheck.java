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
public class UpgradeJavaDLFolderMethodCheck extends BaseUpgradeCheck {

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

			Matcher matcher = _addFolderPattern.matcher(javaMethodContent);

			while (matcher.find()) {
				String methodCall = JavaSourceUtil.getMethodCall(
					javaMethodContent, matcher.start());

				List<String> parameterList = JavaSourceUtil.getParameterList(
					methodCall);

				if (_isDlFolderService(
						newContent, fileName, javaMethodContent, methodCall,
						parameterList)) {

					newContent = StringUtil.replace(
						newContent, methodCall,
						JavaSourceUtil.addMethodNewParameters(
							JavaSourceUtil.getIndent(methodCall), new int[] {0},
							matcher.group(), new String[] {"null"},
							parameterList));
				}
			}
		}

		return newContent;
	}

	private boolean _isDlFolderService(
		String content, String fileName, String javaMethodContent,
		String methodCall, List<String> parameterList) {

		String variableName = getVariableName(methodCall);

		boolean valid = false;

		String message = StringBundler.concat(
			"Unable to format method addFolder from DLFolderService, ",
			"DLFolderLocalService, DLFolderServiceUtil and ",
			"DLFolderLocalServiceUtil. Fill the new parameter manually, see ",
			"LPS-194001.");

		if (variableName.contains("DLFolderLocalServiceUtil") ||
			hasClassOrVariableName(
				"DLFolderLocalService", content, content, methodCall)) {

			String[] parameterTypes = {
				"long", "long", "long", "boolean", "long", "String", "String",
				"boolean", "ServiceContext"
			};

			valid = hasValidParameters(
				9, fileName, javaMethodContent, message, parameterList,
				parameterTypes);
		}
		else if (variableName.contains("DLFolderServiceUtil") ||
				 hasClassOrVariableName(
					 "DLFolderService", content, content, methodCall)) {

			String[] parameterTypes = {
				"long", "long", "boolean", "long", "String", "String",
				"ServiceContext"
			};

			valid = hasValidParameters(
				7, fileName, javaMethodContent, message, parameterList,
				parameterTypes);
		}

		return valid;
	}

	private static final Pattern _addFolderPattern = Pattern.compile(
		"\\t*\\w+\\.addFolder\\(");

}