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
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaParameter;
import com.liferay.source.formatter.parser.JavaSignature;
import com.liferay.source.formatter.parser.JavaTerm;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaTestMethodAnnotationsCheck extends BaseJavaTermCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, JavaTerm javaTerm,
		String fileContent) {

		if (!javaTerm.isPublic() || !fileName.endsWith("Test.java")) {
			return javaTerm.getContent();
		}

		JavaClass parentJavaClass = javaTerm.getParentJavaClass();

		if (parentJavaClass.getParentJavaClass() != null) {
			return javaTerm.getContent();
		}

		_checkAnnotationForMethod(
			fileName, javaTerm, "^tearDown(?!Class)", false, "After",
			"AfterEach");
		_checkAnnotationForMethod(
			fileName, javaTerm, "^tearDownClass", true, "AfterAll",
			"AfterClass");
		_checkAnnotationForMethod(
			fileName, javaTerm, "^setUp(?!Class)", false, "Before",
			"BeforeEach");
		_checkAnnotationForMethod(
			fileName, javaTerm, "^setUpClass", true, "BeforeAll",
			"BeforeClass");
		_checkAnnotationForMethod(fileName, javaTerm, "^test", false, "Test");

		return javaTerm.getContent();
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_METHOD};
	}

	private void _checkAnnotationForMethod(
		String fileName, JavaTerm javaTerm, String requiredMethodNameRegex,
		boolean staticRequired, String... annotations) {

		String methodName = javaTerm.getName();

		Pattern pattern = Pattern.compile(requiredMethodNameRegex);

		Matcher matcher = pattern.matcher(methodName);

		boolean hasAnnotation = false;

		for (String annotation : annotations) {
			if (javaTerm.hasAnnotation(annotation)) {
				hasAnnotation = true;

				break;
			}
		}

		if (hasAnnotation) {
			if (!matcher.find()) {
				addMessage(
					fileName, "Incorrect method name '" + methodName + "'",
					javaTerm.getLineNumber());
			}
			else if (javaTerm.isStatic() != staticRequired) {
				addMessage(
					fileName, "Incorrect method type for '" + methodName + "'",
					javaTerm.getLineNumber());
			}

			return;
		}

		if (!matcher.find()) {
			return;
		}

		JavaSignature signature = javaTerm.getSignature();

		List<JavaParameter> parameters = signature.getParameters();

		if (!parameters.isEmpty()) {
			return;
		}

		JavaClass javaClass = javaTerm.getParentJavaClass();

		if (javaClass.isAnonymous()) {
			return;
		}

		JavaClass parentJavaClass = javaClass.getParentJavaClass();

		if (parentJavaClass == null) {
			StringBundler sb = new StringBundler();

			for (String annotation : annotations) {
				sb.append("@");
				sb.append(annotation);
				sb.append(" or ");
			}

			sb.setIndex(sb.index() - 1);

			addMessage(
				fileName,
				StringBundler.concat(
					"Annotation ", sb.toString(), " required for '", methodName,
					"'"),
				javaTerm.getLineNumber());
		}
	}

}