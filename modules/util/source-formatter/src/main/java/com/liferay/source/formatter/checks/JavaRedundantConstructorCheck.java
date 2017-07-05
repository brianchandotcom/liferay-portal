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

import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.source.formatter.checks.util.JavaSourceUtil;
import com.liferay.source.formatter.checks.util.SourceUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaConstructor;
import com.liferay.source.formatter.parser.JavaTerm;
import com.liferay.source.formatter.util.ThreadSafeSortedClassLibraryBuilder;

import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.parser.ParseException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaRedundantConstructorCheck extends BaseJavaTermCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, JavaTerm javaTerm,
		String fileContent) {

		String constructorContent = javaTerm.getContent();

		String indent = SourceUtil.getIndent(constructorContent);

		if (!constructorContent.contains("{\n" + indent + "}\n")) {
			return constructorContent;
		}

		JavaClass javaClass = javaTerm.getParentJavaClass();

		if (_getConstructorCount(javaClass) > 1) {
			return constructorContent;
		}

		String classAccessModifier = javaClass.getAccessModifier();
		String constructorAccessModifier = javaTerm.getAccessModifier();

		if ((constructorAccessModifier.equals(
				JavaTerm.ACCESS_MODIFIER_PRIVATE) &&
			 !classAccessModifier.equals(JavaTerm.ACCESS_MODIFIER_PRIVATE)) ||
			(constructorAccessModifier.equals(
				JavaTerm.ACCESS_MODIFIER_PROTECTED) &&
			 !classAccessModifier.equals(JavaTerm.ACCESS_MODIFIER_PRIVATE) &&
			 !classAccessModifier.equals(JavaTerm.ACCESS_MODIFIER_PROTECTED))) {

			return constructorContent;
		}

		Pattern pattern = Pattern.compile(
			"class " + javaClass.getName() + "[ \t\n]+extends");

		Matcher matcher = pattern.matcher(javaClass.getContent());

		if (!matcher.find()) {
			return constructorContent;
		}

		JavaProjectBuilder javaProjectBuilder = new JavaProjectBuilder(
			new ThreadSafeSortedClassLibraryBuilder());

		try {
			javaProjectBuilder.addSource(new UnsyncStringReader(fileContent));
		}
		catch (ParseException pe) {
			return constructorContent;
		}

		com.thoughtworks.qdox.model.JavaClass qdoxJavaClass =
			javaProjectBuilder.getClassByName(
				_getClassName(fileContent, javaClass));

		com.thoughtworks.qdox.model.JavaClass superJavaClass =
			qdoxJavaClass.getSuperJavaClass();

		JavaMethod superJavaClassConstructor =
			superJavaClass.getMethodBySignature(superJavaClass.getName(), null);

		if ((superJavaClassConstructor != null) &&
			ListUtil.isEmpty(superJavaClassConstructor.getExceptions())) {

			return StringPool.BLANK;
		}

		return constructorContent;
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CONSTRUCTOR};
	}

	private String _getClassName(String fileContent, JavaClass javaClass) {
		if (javaClass.getParentJavaClass() == null) {
			return JavaSourceUtil.getPackagePath(fileContent) +
				StringPool.PERIOD + javaClass.getName();
		}

		return _getClassName(fileContent, javaClass.getParentJavaClass()) +
			StringPool.DOLLAR + javaClass.getName();
	}

	private int _getConstructorCount(JavaClass javaClass) {
		int count = 0;

		for (JavaTerm javaTerm : javaClass.getChildJavaTerms()) {
			if (javaTerm instanceof JavaConstructor) {
				count++;
			}
		}

		return count;
	}

}