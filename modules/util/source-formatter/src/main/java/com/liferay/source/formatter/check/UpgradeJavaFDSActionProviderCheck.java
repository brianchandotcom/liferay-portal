/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
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
public class UpgradeJavaFDSActionProviderCheck extends BaseUpgradeCheck {

	@Override
	protected String format(
			String fileName, String absolutePath, String content)
		throws Exception {

		JavaClass javaClass = JavaClassParser.parseJavaClass(fileName, content);

		List<String> implementedClassNames =
			javaClass.getImplementedClassNames();

		for (JavaTerm childJavaTerm : javaClass.getChildJavaTerms()) {
			if (!childJavaTerm.isJavaMethod()) {
				continue;
			}

			JavaMethod javaMethod = (JavaMethod)childJavaTerm;

			String javaMethodContent = javaMethod.getContent();

			String newJavaMethodContent = javaMethodContent;

			if (implementedClassNames.contains("FDSActionProvider")) {
				newJavaMethodContent = _formatMethodDefinition(
					newJavaMethodContent);
			}

			newJavaMethodContent = _formatMethodCalls(
				content, fileName, newJavaMethodContent);

			content = StringUtil.replace(
				content, javaMethodContent, newJavaMethodContent);
		}

		return content;
	}

	private String _formatMethodCalls(
		String fileContent, String fileName, String javaMethodContent) {

		Matcher matcher = _getDropdownItemsMethodCallPattern.matcher(
			javaMethodContent);

		while (matcher.find()) {
			String methodCall = JavaSourceUtil.getMethodCall(
				javaMethodContent, matcher.start());

			List<String> parameterList = JavaSourceUtil.getParameterList(
				methodCall);

			String variableTypeName = getVariableTypeName(
				javaMethodContent, null, fileContent, fileName,
				parameterList.get(0));

			if (variableTypeName == null) {
				continue;
			}

			if (variableTypeName.equals("HttpServletRequest") &&
				hasClassOrVariableName(
					"FDSActionProvider", javaMethodContent, fileContent,
					fileName, methodCall)) {

				javaMethodContent = StringUtil.replace(
					javaMethodContent, methodCall,
					_reorderParameters(methodCall));
			}
		}

		return javaMethodContent;
	}

	private String _formatMethodDefinition(String javaMethodContent) {
		Matcher matcher = _getDropdownItemsMethodPattern.matcher(
			javaMethodContent);

		if (!matcher.find()) {
			return javaMethodContent;
		}

		String methodCall = JavaSourceUtil.getMethodCall(
			javaMethodContent, matcher.start());

		return StringUtil.replace(
			javaMethodContent, methodCall, _reorderParameters(methodCall));
	}

	private String _reorderParameters(String methodCall) {
		List<String> parameterList = JavaSourceUtil.getParameterList(
			methodCall);

		return StringUtil.replace(
			methodCall, JavaSourceUtil.getParameters(methodCall),
			StringBundler.concat(
				parameterList.get(1), StringPool.COMMA_AND_SPACE,
				parameterList.get(0), StringPool.COMMA_AND_SPACE,
				parameterList.get(2)));
	}

	private static final Pattern _getDropdownItemsMethodCallPattern =
		Pattern.compile(
			"\\w+\\.getDropdownItems\\s*\\(\\s*.+,\\s*.+,\\s*.+\\s*\\)");
	private static final Pattern _getDropdownItemsMethodPattern =
		Pattern.compile(
			"getDropdownItems\\s*\\(\\s*HttpServletRequest\\s+.+,\\s*.+," +
				"\\s*.+\\s*\\)");

}