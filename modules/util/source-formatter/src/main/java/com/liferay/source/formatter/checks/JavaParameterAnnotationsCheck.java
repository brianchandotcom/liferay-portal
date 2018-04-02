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

package com.liferay.source.formatter.checks;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.checks.util.SourceUtil;
import com.liferay.source.formatter.parser.JavaConstructor;
import com.liferay.source.formatter.parser.JavaMethod;
import com.liferay.source.formatter.parser.JavaParameter;
import com.liferay.source.formatter.parser.JavaSignature;
import com.liferay.source.formatter.parser.JavaTerm;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Peter Shin
 */
public class JavaParameterAnnotationsCheck extends BaseJavaTermCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, JavaTerm javaTerm,
		String fileContent) {

		String parameters = _getParameters(javaTerm);

		if (Validator.isNull(parameters) ||
			!parameters.contains(StringPool.AT)) {

			return javaTerm.getContent();
		}

		return _fixAnnotations(parameters, javaTerm);
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CONSTRUCTOR, JAVA_METHOD};
	}

	private String _fixAnnotations(String parameters, JavaTerm javaTerm) {
		List<JavaParameter> javaParameters = _getJavaParameters(javaTerm);

		if (javaParameters.isEmpty()) {
			return javaTerm.getContent();
		}

		StringBundler sb = new StringBundler();

		for (JavaParameter javaParameter : javaParameters) {
			Set<String> parameterAnnotations =
				javaParameter.getParameterAnnotations();

			if (sb.length() != 0) {
				sb.append(StringPool.COMMA_AND_SPACE);
			}

			sb.append(StringUtil.merge(parameterAnnotations, " "));

			if (!parameterAnnotations.isEmpty()) {
				sb.append(StringPool.SPACE);
			}

			if (javaParameter.isFinal()) {
				sb.append("final ");
			}

			sb.append(javaParameter.getParameterType());
			sb.append(StringPool.SPACE);
			sb.append(javaParameter.getParameterName());
		}

		String newParameters = sb.toString();

		sb.setIndex(0);

		for (String word : newParameters.split("\\s+")) {
			sb.append(Pattern.quote(word));
			sb.append("\\s*");
		}

		sb.setIndex(sb.length() - 1);

		Pattern pattern = Pattern.compile(sb.toString());

		Matcher matcher = pattern.matcher(parameters);

		if (matcher.find()) {
			return javaTerm.getContent();
		}

		return StringUtil.replaceFirst(
			javaTerm.getContent(), parameters, newParameters);
	}

	private List<JavaParameter> _getJavaParameters(JavaTerm javaTerm) {
		JavaSignature javaSignature = null;

		if (javaTerm.isJavaConstructor()) {
			JavaConstructor javaConstructor = (JavaConstructor)javaTerm;

			javaSignature = javaConstructor.getSignature();
		}
		else if (javaTerm.isJavaMethod()) {
			JavaMethod javaMethod = (JavaMethod)javaTerm;

			javaSignature = javaMethod.getSignature();
		}

		if (javaSignature == null) {
			return Collections.emptyList();
		}

		return javaSignature.getParameters();
	}

	private String _getParameters(JavaTerm javaTerm) {
		Pattern pattern = Pattern.compile(
			SourceUtil.getIndent(javaTerm.getContent()) +
				javaTerm.getAccessModifier() + " .*?[;{]",
			Pattern.DOTALL);

		Matcher matcher = pattern.matcher(javaTerm.getContent());

		if (!matcher.find()) {
			return StringPool.BLANK;
		}

		String signature = matcher.group();

		int beginIndex = signature.indexOf(CharPool.OPEN_PARENTHESIS);
		int endIndex = signature.lastIndexOf(CharPool.CLOSE_PARENTHESIS);

		if ((beginIndex == -1) || (endIndex == -1)) {
			return StringPool.BLANK;
		}

		return StringUtil.trim(signature.substring(beginIndex + 1, endIndex));
	}

}