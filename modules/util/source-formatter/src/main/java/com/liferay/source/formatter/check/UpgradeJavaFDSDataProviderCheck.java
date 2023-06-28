/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Cavalcanti
 */
public class UpgradeJavaFDSDataProviderCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (!fileName.endsWith(".java")) {
			return content;
		}

		JavaClass javaClass = JavaClassParser.parseJavaClass(fileName, content);

		List<String> importNames = javaClass.getImportNames();

		if (!importNames.contains(
				"com.liferay.frontend.data.set.provider.FDSDataProvider")) {

			return content;
		}

		return _reorderParametersGetItemsAndGetItemsCount(content, javaClass);
	}

	private String _checkMethodCalls(String content, String javaMethodContent) {
		Matcher methodCallGetItemsMatcher = _methodCallGetItemsPattern.matcher(
			javaMethodContent);

		while (methodCallGetItemsMatcher.find()) {
			String methodCall = JavaSourceUtil.getMethodCall(
				javaMethodContent, methodCallGetItemsMatcher.start());

			List<String> parameterList = JavaSourceUtil.getParameterList(
				methodCall);

			if (_hasClassNameHttpServletRequest(
					javaMethodContent, content, parameterList.get(0)) &&
				_isFDSDataProviderMethodCall(
					javaMethodContent, content, methodCall)) {

				javaMethodContent = StringUtil.replace(
					javaMethodContent, methodCall,
					_reorderParametersGetItems(
						methodCall, methodCallGetItemsMatcher.group(1),
						parameterList));
			}
		}

		Matcher methodCallGetItemsCountMatcher =
			_methodCallGetItemsCountPattern.matcher(javaMethodContent);

		while (methodCallGetItemsCountMatcher.find()) {
			String methodCall = JavaSourceUtil.getMethodCall(
				javaMethodContent, methodCallGetItemsCountMatcher.start());

			List<String> parameterList = JavaSourceUtil.getParameterList(
				methodCall);

			if (_hasClassNameHttpServletRequest(
					javaMethodContent, content, parameterList.get(0)) &&
				_isFDSDataProviderMethodCall(
					javaMethodContent, content, methodCall)) {

				javaMethodContent = StringUtil.replace(
					javaMethodContent, methodCall,
					_reorderParametersGetItemsCount(
						methodCall, methodCallGetItemsCountMatcher.group(1),
						parameterList));
			}
		}

		return javaMethodContent;
	}

	private String _checkMethods(String javaMethodContent) {
		Matcher methodGetItemsMatcher = _methodGetItemsPattern.matcher(
			javaMethodContent);

		if (methodGetItemsMatcher.find()) {
			String methodCall = JavaSourceUtil.getMethodCall(
				javaMethodContent, methodGetItemsMatcher.start());

			List<String> parameterList = JavaSourceUtil.getParameterList(
				methodCall);

			String firstParameter = parameterList.get(0);

			if (firstParameter.contains("HttpServletRequest")) {
				javaMethodContent = StringUtil.replace(
					javaMethodContent, methodCall,
					_reorderParametersGetItems(
						methodCall, methodGetItemsMatcher.group(1),
						parameterList));
			}
		}

		Matcher methodGetItemsCountMatcher =
			_methodGetItemsCountPattern.matcher(javaMethodContent);

		if (methodGetItemsCountMatcher.find()) {
			String methodCall = JavaSourceUtil.getMethodCall(
				javaMethodContent, methodGetItemsCountMatcher.start());

			List<String> parameterList = JavaSourceUtil.getParameterList(
				methodCall);

			String firstParameter = parameterList.get(0);

			if (firstParameter.contains("HttpServletRequest")) {
				javaMethodContent = StringUtil.replace(
					javaMethodContent, methodCall,
					_reorderParametersGetItemsCount(
						methodCall, methodGetItemsCountMatcher.group(1),
						parameterList));
			}
		}

		return javaMethodContent;
	}

	private boolean _hasClassNameHttpServletRequest(
		String content, String fileContent, String variableName) {

		return Objects.equals(
			getVariableTypeName(content, fileContent, variableName),
			"HttpServletRequest");
	}

	private boolean _isFDSDataProviderMethodCall(
		String content, String fileContent, String methodCall) {

		String variableTypeName = getVariableTypeName(
			content, fileContent,
			methodCall.substring(0, methodCall.indexOf(StringPool.PERIOD)),
			true);

		return variableTypeName.contains("FDSDataProvider");
	}

	private String _reorderParametersGetItems(
		String methodCall, String orderParameters, List<String> parameterList) {

		String newOrderParameters = StringBundler.concat(
			parameterList.get(1), StringPool.COMMA_AND_SPACE,
			parameterList.get(2), StringPool.COMMA_AND_SPACE,
			parameterList.get(0), StringPool.COMMA_AND_SPACE,
			parameterList.get(3));

		return StringUtil.replace(
			methodCall, orderParameters, newOrderParameters);
	}

	private String _reorderParametersGetItemsAndGetItemsCount(
		String content, JavaClass javaClass) {

		List<String> implementedClassNames =
			javaClass.getImplementedClassNames();

		for (JavaTerm childJavaTerm : javaClass.getChildJavaTerms()) {
			if (!childJavaTerm.isJavaMethod()) {
				continue;
			}

			JavaMethod javaMethod = (JavaMethod)childJavaTerm;

			String javaMethodContent = javaMethod.getContent();

			if (implementedClassNames.contains("FDSDataProvider")) {
				content = StringUtil.replace(
					content, javaMethodContent,
					_checkMethods(javaMethodContent));
			}

			content = StringUtil.replace(
				content, javaMethodContent,
				_checkMethodCalls(javaClass.getContent(), javaMethodContent));
		}

		return content;
	}

	private String _reorderParametersGetItemsCount(
		String methodCall, String orderParameters, List<String> parameterList) {

		String newOrderParameters = StringBundler.concat(
			parameterList.get(1), StringPool.COMMA_AND_SPACE,
			parameterList.get(0));

		return StringUtil.replace(
			methodCall, orderParameters, newOrderParameters);
	}

	private static final Pattern _methodCallGetItemsCountPattern =
		Pattern.compile("\\w+\\.getItemsCount\\((\\s*.+,\\s*.+)\\s*\\)");
	private static final Pattern _methodCallGetItemsPattern = Pattern.compile(
		"\\w+\\.getItems\\((\\s*.+,\\s*.+,\\s*.+,\\s*.+)\\s*\\)");
	private static final Pattern _methodGetItemsCountPattern = Pattern.compile(
		"getItemsCount\\((\\s*.+,\\s*.+)\\s*\\)");
	private static final Pattern _methodGetItemsPattern = Pattern.compile(
		"getItems\\((\\s*.+,\\s*.+,\\s*.+,\\s*.+)\\s*\\)");

}