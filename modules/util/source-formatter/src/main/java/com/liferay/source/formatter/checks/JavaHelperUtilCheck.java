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

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaMethod;
import com.liferay.source.formatter.parser.JavaTerm;

import java.io.IOException;

import java.util.List;
import java.util.Objects;

/**
 * @author Hugo Huijser
 */
public class JavaHelperUtilCheck extends BaseJavaTermCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, JavaTerm javaTerm,
			String fileContent)
		throws IOException {

		JavaClass javaClass = (JavaClass)javaTerm;

		if (javaClass.isAbstract() || javaClass.isInterface() ||
			javaClass.hasAnnotation("Deprecated") ||
			(javaClass.getParentJavaClass() != null)) {

			return javaTerm.getContent();
		}

		String className = javaClass.getName();

		boolean helper = false;

		if (className.endsWith("Helper")) {
			helper = true;

			for (String extendedClassName : javaClass.getExtendedClassNames()) {
				if (extendedClassName.endsWith("Helper")) {
					return javaTerm.getContent();
				}
			}
		}
		else if (!className.endsWith("Util")) {
			return javaTerm.getContent();
		}

		int publicMethodCount = 0;

		List<JavaTerm> childJavaTerms = javaClass.getChildJavaTerms();

		for (JavaTerm childJavaTerm : childJavaTerms) {
			if (childJavaTerm.hasAnnotation("Deprecated") ||
				!(childJavaTerm instanceof JavaMethod) ||
				(!childJavaTerm.isProtected() && !childJavaTerm.isPublic())) {

				continue;
			}

			if (!Objects.equals(childJavaTerm.getName(), "main")) {
				publicMethodCount++;
			}

			if (childJavaTerm.isStatic()) {
				continue;
			}

			if (helper) {
				return javaTerm.getContent();
			}

			if (!_isAllowedNonstaticUtilMethod(childJavaTerm, childJavaTerms)) {
				addMessage(
					fileName,
					"'*Util' class can only have static protected or public " +
						"methods");

				return javaTerm.getContent();
			}
		}

		if (helper && (publicMethodCount > 0)) {
			addMessage(
				fileName,
				"Classes with only static protected or public methods should " +
					"have name ending with 'Util'");
		}

		return javaTerm.getContent();
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CLASS};
	}

	private boolean _isAllowedNonstaticUtilMethod(
		JavaTerm javaTerm, List<JavaTerm> childJavaTerms) {

		String name = javaTerm.getName();

		if (Objects.equals(
				javaTerm.getAccessModifier(),
				JavaTerm.ACCESS_MODIFIER_PUBLIC)) {

			if (name.equals("destroy") || name.matches("set[A-Z].*")) {
				return true;
			}

			return false;
		}

		if (javaTerm.hasAnnotation(
				"Activate", "Deactivate", "Modified", "Reference")) {

			return true;
		}

		for (JavaTerm childJavaTerm : childJavaTerms) {
			if (!(childJavaTerm instanceof JavaMethod) ||
				!childJavaTerm.hasAnnotation("Reference")) {

				continue;
			}

			String content = childJavaTerm.getContent();

			if (content.contains("unbind = \"-\"")) {
				continue;
			}

			if (content.contains("unbind = \"" + name + "\"")) {
				return true;
			}

			String defaultUnbindMethodName = null;

			String bindMethodName = childJavaTerm.getName();

			if (bindMethodName.startsWith("add")) {
				defaultUnbindMethodName = StringUtil.replaceFirst(
					bindMethodName, "add", "remove");
			}
			else {
				defaultUnbindMethodName = "un" + bindMethodName;
			}

			if (name.equals(defaultUnbindMethodName)) {
				return true;
			}
		}

		return false;
	}

}