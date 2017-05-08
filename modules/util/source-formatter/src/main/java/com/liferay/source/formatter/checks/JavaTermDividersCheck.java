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
import com.liferay.source.formatter.parser.JavaTerm;
import com.liferay.source.formatter.parser.JavaVariable;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class JavaTermDividersCheck extends BaseJavaTermCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, JavaTerm javaTerm,
		String fileContent) {

		JavaClass javaClass = (JavaClass)javaTerm;

		String classContent = javaClass.getContent();

		List<JavaTerm> childJavaTerms = javaClass.getChildJavaTerms();

		if (childJavaTerms.isEmpty()) {
			return classContent;
		}

		JavaTerm previousChildJavaTerm = null;

		for (JavaTerm childJavaTerm : childJavaTerms) {
			if (previousChildJavaTerm != null) {
				classContent = _fixJavaTermDivider(
					classContent, previousChildJavaTerm, childJavaTerm);
			}

			previousChildJavaTerm = childJavaTerm;
		}

		String lastJavaTermContent = previousChildJavaTerm.getContent();

		if (!classContent.contains(lastJavaTermContent + "\n")) {
			classContent = StringUtil.replace(
				classContent, lastJavaTermContent, lastJavaTermContent + "\n");
		}

		return classContent;
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CLASS};
	}

	private String _fixJavaTermDivider(
		String classContent, JavaTerm previousJavaTerm, JavaTerm javaTerm) {

		String previousJavaTermContent = previousJavaTerm.getContent();

		String afterPreviousJavaTerm = StringUtil.trim(
			classContent.substring(
				classContent.indexOf(previousJavaTermContent) +
					previousJavaTermContent.length()));

		if (afterPreviousJavaTerm.startsWith("//")) {
			return classContent;
		}

		String javaTermContent = javaTerm.getContent();

		if (!(javaTerm instanceof JavaVariable) ||
			!(previousJavaTerm instanceof JavaVariable)) {

			return _fixJavaTermDivider(classContent, javaTermContent, true);
		}

		if (previousJavaTerm.isStatic() ^ javaTerm.isStatic()) {
			return _fixJavaTermDivider(classContent, javaTermContent, true);
		}

		String javaTermAccessModifier = javaTerm.getAccessModifier();
		String previousJavaTermAccessModifier =
			previousJavaTerm.getAccessModifier();

		if (!previousJavaTermAccessModifier.equals(javaTermAccessModifier)) {
			return _fixJavaTermDivider(classContent, javaTermContent, true);
		}

		String javaTermName = javaTerm.getName();
		String previousJavaTermName = previousJavaTerm.getName();

		if ((StringUtil.isUpperCase(javaTermName) &&
			 !StringUtil.isLowerCase(javaTermName)) ||
			(StringUtil.isUpperCase(previousJavaTermName) &&
			 !StringUtil.isLowerCase(previousJavaTermName))) {

			return _fixJavaTermDivider(classContent, javaTermContent, true);
		}

		if (javaTermContent.matches("\\s*[/@*][\\S\\s]*") ||
			previousJavaTermContent.matches("\\s*[/@*][\\S\\s]*")) {

			return _fixJavaTermDivider(classContent, javaTermContent, true);
		}

		if (javaTermContent.contains("\n\n\t") ||
			previousJavaTermContent.contains("\n\n\t")) {

			return _fixJavaTermDivider(classContent, javaTermContent, true);
		}

		if (previousJavaTerm.isStatic() &&
			(previousJavaTermName.equals("_instance") ||
			 previousJavaTermName.equals("_log") ||
			 previousJavaTermName.equals("_logger"))) {

			return _fixJavaTermDivider(classContent, javaTermContent, true);
		}

		return _fixJavaTermDivider(classContent, javaTermContent, false);
	}

	private String _fixJavaTermDivider(
		String classContent, String javaTermContent,
		boolean requiresEmptyLine) {

		if (requiresEmptyLine) {
			if (classContent.contains("\n\n" + javaTermContent)) {
				return classContent;
			}

			return StringUtil.replace(
				classContent, "\n" + javaTermContent, "\n\n" + javaTermContent);
		}

		if (!classContent.contains("\n\n" + javaTermContent)) {
			return classContent;
		}

		return StringUtil.replace(
			classContent, "\n\n" + javaTermContent, "\n" + javaTermContent);
	}

}