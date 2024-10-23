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
 * @author Kyle Miho
 * @author Michael Cavalcanti
 */
public class UpgradeJavaBaseModelListenerCheck extends BaseUpgradeCheck {

	@Override
	protected String format(
			String fileName, String absolutePath, String content)
		throws Exception {

		JavaClass javaClass = JavaClassParser.parseJavaClass(fileName, content);

		List<String> extendedClassNames = javaClass.getExtendedClassNames();

		if (!extendedClassNames.contains("BaseModelListener")) {
			return content;
		}

		_modelType = _getModelType(content);

		for (JavaTerm childJavaTerm : javaClass.getChildJavaTerms()) {
			if (!childJavaTerm.isJavaMethod()) {
				continue;
			}

			JavaMethod javaMethod = (JavaMethod)childJavaTerm;

			String newJavaMethodContent = _formatMethod(javaMethod);

			content = StringUtil.replace(
				content, javaMethod.getContent(), newJavaMethodContent);
		}

		return content;
	}

	private String _formatMethod(JavaMethod javaMethod) {
		String javaMethodName = javaMethod.getName();

		if (javaMethodName.equals("onAfterUpdate") ||
			javaMethodName.equals("onBeforeUpdate")) {

			return _formatMethodDefinitionWithParameterUpgrade(javaMethod);
		}

		return _formatMethodDefinitionWithoutParameterUpgrade(javaMethod);
	}

	private String _formatMethodDefinitionWithoutParameterUpgrade(
		JavaMethod javaMethod) {

		String javaMethodContent = javaMethod.getContent();

		Matcher matcher = _superMethodPattern.matcher(javaMethodContent);

		if (!matcher.find()) {
			return javaMethodContent;
		}

		String methodCall = JavaSourceUtil.getMethodCall(
			javaMethodContent, matcher.start());

		methodCall = methodCall.trim();

		return StringUtil.replace(
			javaMethodContent, methodCall,
			_formatSuperMethod(methodCall, false));
	}

	private String _formatMethodDefinitionWithParameterUpgrade(
		JavaMethod javaMethod) {

		String javaMethodContent = javaMethod.getContent();

		JavaSignature javaSignature = javaMethod.getSignature();

		List<JavaParameter> parameters = javaSignature.getParameters();

		if (parameters.size() != 1) {
			return javaMethodContent;
		}

		javaMethodContent = _formatSuperMethod(javaMethodContent, true);

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

	private String _formatSuperMethod(
		String javaMethodContent, boolean parameterUpgrade) {

		Matcher matcher = _superMethodPattern.matcher(javaMethodContent);

		if (!matcher.find()) {
			return javaMethodContent;
		}

		String methodCall = JavaSourceUtil.getMethodCall(
			javaMethodContent, matcher.start());

		List<String> parameters = JavaSourceUtil.getParameterNames(methodCall);

		if (parameters.size() != 1) {
			return javaMethodContent;
		}

		String parameter = parameters.get(0);

		String newParameters;

		if (parameterUpgrade) {
			newParameters = StringBundler.concat(
				"original", StringUtil.upperCaseFirstLetter(parameter),
				StringPool.COMMA_AND_SPACE, parameter);
		}
		else {
			newParameters = StringBundler.concat(
				StringPool.OPEN_PARENTHESIS, _modelType,
				StringPool.CLOSE_PARENTHESIS, parameter, ".clone(), ",
				parameter);
		}

		String newMethodCall = StringUtil.replace(
			methodCall, parameter, newParameters);

		return StringUtil.replace(javaMethodContent, methodCall, newMethodCall);
	}

	private String _getModelType(String content) throws Exception {
		Matcher matcher = _modelTypePattern.matcher(content);

		if (!matcher.find()) {
			throw new Exception("Cannot find model type");
		}

		return matcher.group(1);
	}

	private static final Pattern _modelTypePattern = Pattern.compile(
		"extends\\s+\\w+\\<(\\w+)\\>");
	private static final Pattern _superMethodPattern = Pattern.compile(
		"super\\.\\s*(onAfterUpdate|onBeforeUpdate)\\(\\s*\\w+\\)");

	private String _modelType;

}