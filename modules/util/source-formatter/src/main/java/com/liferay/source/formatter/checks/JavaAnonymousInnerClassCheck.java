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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.checks.util.SourceUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaClassParser;
import com.liferay.source.formatter.parser.JavaMethod;
import com.liferay.source.formatter.parser.JavaParameter;
import com.liferay.source.formatter.parser.JavaSignature;
import com.liferay.source.formatter.parser.JavaTerm;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class JavaAnonymousInnerClassCheck extends BaseJavaTermCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, JavaTerm javaTerm,
			String fileContent)
		throws Exception {

		String content = javaTerm.getContent();

		List<JavaClass> anonymousClasses =
			JavaClassParser.parseAnonymousClasses(content);

		if (anonymousClasses.isEmpty()) {
			return content;
		}

		for (JavaClass anonymousClass : anonymousClasses) {
			content = _convertToLambda(
				content, anonymousClass,
				"ActionableDynamicQuery.AddCriteriaMethod", false);
			content = _convertToLambda(
				content, anonymousClass,
				"ActionableDynamicQuery.PerformActionMethod", true);
		}

		return content;
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CONSTRUCTOR, JAVA_METHOD};
	}

	private String _convertToLambda(
			String content, JavaClass anonymousClass, String className,
			boolean useParameterType)
		throws Exception {

		String anonymousClassContent = anonymousClass.getContent();

		if (!StringUtil.startsWith(
				StringUtil.trimLeading(anonymousClassContent),
				"new " + className)) {

			return content;
		}

		List<JavaTerm> javaTerms = anonymousClass.getChildJavaTerms();

		if (javaTerms.size() != 1) {
			return content;
		}

		JavaTerm javaTerm = javaTerms.get(0);

		if (!javaTerm.hasAnnotation("Override") ||
			!(javaTerm instanceof JavaMethod)) {

			return content;
		}

		JavaMethod javaMethod = (JavaMethod)javaTerm;

		int x = anonymousClassContent.indexOf("{\n");

		String indent = SourceUtil.getIndent(anonymousClassContent);

		String expectedContent = StringBundler.concat(
			anonymousClassContent.substring(0, x + 2), "\n",
			javaMethod.getContent(), "\n", indent, "}");

		if (!expectedContent.equals(anonymousClassContent)) {
			return content;
		}

		String methodBody = _getMethodBody(javaMethod.getContent());

		if (methodBody == null) {
			return content;
		}

		StringBundler sb = new StringBundler(7);

		sb.append(indent);
		sb.append(
			_getLambdaSignature(javaMethod.getSignature(), useParameterType));
		sb.append(" -> {\n");
		sb.append(methodBody);
		sb.append("\n");
		sb.append(indent);
		sb.append("}");

		return StringUtil.replace(
			content, anonymousClassContent, sb.toString());
	}

	private String _getLambdaSignature(
		JavaSignature signature, boolean useParameterType) {

		StringBundler sb = new StringBundler();

		List<JavaParameter> parameters = signature.getParameters();

		if (parameters.isEmpty() || (parameters.size() > 1) ||
			useParameterType) {

			sb.append(CharPool.OPEN_PARENTHESIS);
		}

		for (JavaParameter parameter : parameters) {
			if (useParameterType) {
				sb.append(parameter.getParameterType());
				sb.append(CharPool.SPACE);
			}

			sb.append(parameter.getParameterName());
			sb.append(CharPool.COMMA);
		}

		if (!parameters.isEmpty()) {
			sb.setIndex(sb.index() - 1);
		}

		if (parameters.isEmpty() || (parameters.size() > 1) ||
			useParameterType) {

			sb.append(CharPool.CLOSE_PARENTHESIS);
		}

		return sb.toString();
	}

	private String _getMethodBody(String content) {
		int x = content.indexOf("{\n");

		int y = content.lastIndexOf("}");

		y = content.lastIndexOf("\n", y);

		String body = content.substring(x + 1, y);

		if (getLevel(body, "{", "}") != 0) {
			return null;
		}

		body = StringUtil.replace(body, "\n\t", "\n");

		return body.substring(1);
	}

}