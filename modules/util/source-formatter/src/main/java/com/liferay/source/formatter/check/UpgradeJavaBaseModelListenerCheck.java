/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.poshi.core.util.StringPool;
import com.liferay.source.formatter.check.util.JavaSourceUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaClassParser;
import com.liferay.source.formatter.parser.JavaMethod;
import com.liferay.source.formatter.parser.JavaParameter;
import com.liferay.source.formatter.parser.JavaSignature;
import com.liferay.source.formatter.parser.JavaTerm;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Cavalcanti
 */
public class UpgradeJavaBaseModelListenerCheck extends BaseUpgradeCheck {

	@Override
	protected String format(
			String fileName, String absolutePath, String content)
		throws Exception {

		JavaClass javaClass = JavaClassParser.parseJavaClass(fileName, content);

		List<String> implementedClassNames =
			javaClass.getImplementedClassNames();

		if (implementedClassNames.contains("BaseModelListener")) {
			return content;
		}

		for (JavaTerm childJavaTerm : javaClass.getChildJavaTerms()) {
			if (!childJavaTerm.isJavaMethod()) {
				continue;
			}

			JavaMethod javaMethod = (JavaMethod)childJavaTerm;

			String newJavaMethodContent = _formatMethodDefinition(javaMethod);

			content = StringUtil.replace(
				content, javaMethod.getContent(), newJavaMethodContent);
		}

		return content;
	}

	private String _formatMethodDefinition(JavaMethod javaMethod) {
		String javaMethodContent = javaMethod.getContent();

		String javaMethodName = javaMethod.getName();

		if (!javaMethodName.equals("onAfterUpdate") &&
			!javaMethodName.equals("onBeforeUpdate")) {

			return javaMethodContent;
		}

		javaMethodContent = _formatSuper(javaMethodContent);

		JavaSignature javaSignature = javaMethod.getSignature();

		List<JavaParameter> parameters = javaSignature.getParameters();

		if (parameters.size() != 1) {
			return javaMethodContent;
		}

		JavaParameter javaParameter = parameters.get(0);

		String javaParameterName = javaParameter.getParameterName();

		String javaParameterType = javaParameter.getParameterType();

		String newParameter = StringBundler.concat(
			javaParameterType, " original",
			StringUtil.upperCaseFirstLetter(javaParameterName));

		String newParameters = StringBundler.concat(
			newParameter, StringPool.COMMA_AND_SPACE, javaParameterType,
			StringPool.SPACE, javaParameterName);

		return StringUtil.replace(
			javaMethodContent, JavaSourceUtil.getParameters(javaMethodContent),
			newParameters);
	}

	private String _formatSuper(String javaMethodContent) {
		Matcher matcher = _superPattern.matcher(javaMethodContent);

		if (!matcher.find()) {
			return javaMethodContent;
		}

		String methodCall = JavaSourceUtil.getMethodCall(
			javaMethodContent, matcher.start());

		List<String> parameterList = JavaSourceUtil.getParameterList(
			methodCall);

		if (parameterList.size() != 1) {
			return javaMethodContent;
		}

		String parameter = JavaSourceUtil.getParameters(methodCall);

		String newParameters = StringBundler.concat(
			"original", StringUtil.upperCaseFirstLetter(parameter),
			StringPool.COMMA_AND_SPACE, parameter);

		String newMethodCall = StringUtil.replace(
			methodCall, parameter, newParameters);

		return StringUtil.replace(javaMethodContent, methodCall, newMethodCall);
	}

	private static final Pattern _superPattern = Pattern.compile(
		"super.\\s*\\w+\\(\\s*.+\\)");

}