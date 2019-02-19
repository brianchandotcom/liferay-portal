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

package com.liferay.portal.tools.rest.builder.internal.format.java.checks;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaClassParser;
import com.liferay.source.formatter.parser.JavaTerm;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaTermDividersCheck {

	public static String format(String content, String fileName)
		throws Exception {

		String oldContent = content;

		while (true) {
			String newContent = _format(oldContent, fileName);

			if (oldContent.equals(newContent)) {
				return newContent;
			}

			oldContent = newContent;
		}
	}

	private static String _fixJavaTermDivider(
		String classContent, JavaTerm previousJavaTerm, JavaTerm javaTerm) {

		String javaTermContent = javaTerm.getContent();

		String previousJavaTermContent = previousJavaTerm.getContent();

		String afterPreviousJavaTerm = StringUtil.trim(
			classContent.substring(
				classContent.indexOf("\n" + previousJavaTermContent) +
					previousJavaTermContent.length() + 1));

		if (!afterPreviousJavaTerm.startsWith(
				StringUtil.trim(javaTermContent))) {

			return classContent;
		}

		if (!javaTerm.isJavaVariable() || !previousJavaTerm.isJavaVariable()) {
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

	private static String _fixJavaTermDivider(
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

	private static String _format(String content, String fileName)
		throws Exception {

		JavaClass javaClass = JavaClassParser.parseJavaClass(fileName, content);

		String javaClassContent = javaClass.getContent();

		String javaClassHeader = content.substring(
			0, content.indexOf(javaClassContent));

		List<JavaTerm> childJavaTerms = javaClass.getChildJavaTerms();

		if (childJavaTerms.isEmpty()) {
			Matcher matcher = _missingEmptyLinePattern.matcher(
				javaClassContent);

			if (matcher.find()) {
				return matcher.replaceAll("$1\n$2");
			}

			return javaClassHeader + javaClassContent;
		}

		JavaTerm previousChildJavaTerm = null;

		for (JavaTerm childJavaTerm : childJavaTerms) {
			if (previousChildJavaTerm != null) {
				javaClassContent = _fixJavaTermDivider(
					javaClassContent, previousChildJavaTerm, childJavaTerm);
			}

			previousChildJavaTerm = childJavaTerm;
		}

		String lastJavaTermContent = previousChildJavaTerm.getContent();

		if (!javaClassContent.contains(lastJavaTermContent + "\n")) {
			javaClassContent = StringUtil.replace(
				javaClassContent, lastJavaTermContent,
				lastJavaTermContent + "\n");
		}

		return javaClassHeader + javaClassContent;
	}

	private static final Pattern _missingEmptyLinePattern = Pattern.compile(
		"([^{\n]\n)(\t*\\}\n?)$");

}