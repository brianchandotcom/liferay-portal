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
import com.liferay.source.formatter.parser.JavaConstructor;
import com.liferay.source.formatter.parser.JavaMethod;
import com.liferay.source.formatter.parser.JavaStaticBlock;
import com.liferay.source.formatter.parser.JavaTerm;
import com.liferay.source.formatter.parser.JavaVariable;

/**
 * @author Hugo Huijser
 */
public abstract class BaseJavaTermCheck
	extends BaseSourceCheck implements JavaTermCheck {

	@Override
	public String process(
			String fileName, String absolutePath, JavaClass javaClass,
			String content)
		throws Exception {

		clearSourceFormatterMessages(fileName);

		return _walkJavaClass(
			fileName, absolutePath, javaClass, content, content);
	}

	protected abstract String doProcess(
			String filename, String absolutePath, JavaTerm javaTerm,
			String fileContent)
		throws Exception;

	protected abstract String[] getCheckableJavaTermNames();

	protected static final String JAVA_CLASS = JavaClass.class.getName();

	protected static final String JAVA_CONSTRUCTOR =
		JavaConstructor.class.getName();

	protected static final String JAVA_METHOD = JavaMethod.class.getName();

	protected static final String JAVA_STATIC_BLOCK =
		JavaStaticBlock.class.getName();

	protected static final String JAVA_VARIABLE = JavaVariable.class.getName();

	private boolean _isCheckableJavaTerm(JavaTerm javaTerm) {
		Class<?> clazz = javaTerm.getClass();

		String className = clazz.getName();

		for (String name : getCheckableJavaTermNames()) {
			if (name.equals(className)) {
				return true;
			}
		}

		return false;
	}

	private String _walkJavaClass(
			String fileName, String absolutePath, JavaClass javaClass,
			String parentContent, String fileContent)
		throws Exception {

		String javaClassContent = javaClass.getContent();

		String newJavaClassContent = javaClassContent;

		if (_isCheckableJavaTerm(javaClass)) {
			newJavaClassContent = doProcess(
				fileName, absolutePath, javaClass, fileContent);

			if (!javaClassContent.equals(newJavaClassContent)) {
				return StringUtil.replace(
					parentContent, javaClassContent, newJavaClassContent);
			}
		}

		for (JavaTerm javaTerm : javaClass.getChildJavaTerms()) {
			if (javaTerm instanceof JavaClass) {
				JavaClass childJavaClass = (JavaClass)javaTerm;

				newJavaClassContent = _walkJavaClass(
					fileName, absolutePath, childJavaClass, javaClassContent,
					fileContent);

				if (!newJavaClassContent.equals(javaClassContent)) {
					return StringUtil.replace(
						parentContent, javaClassContent, newJavaClassContent);
				}
			}
			else if (_isCheckableJavaTerm(javaTerm)) {
				String javaTermContent = javaTerm.getContent();

				String newJavaTermContent = doProcess(
					fileName, absolutePath, javaTerm, fileContent);

				if (!javaTermContent.equals(newJavaTermContent)) {
					newJavaClassContent = StringUtil.replace(
						javaClassContent, javaTermContent, newJavaTermContent);

					return StringUtil.replace(
						parentContent, javaClassContent, newJavaClassContent);
				}
			}
		}

		return parentContent;
	}

}