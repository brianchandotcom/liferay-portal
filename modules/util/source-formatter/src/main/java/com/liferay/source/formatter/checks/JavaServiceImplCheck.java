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
import com.liferay.source.formatter.checks.util.SourceUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaTerm;

/**
 * @author Hugo Huijser
 */
public class JavaServiceImplCheck extends BaseJavaTermCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, JavaTerm javaTerm,
		String fileContent) {

		JavaClass javaClass = javaTerm.getParentJavaClass();

		String className = javaClass.getName();

		if (className.endsWith("ServiceImpl")) {
			return _formatServiceImpl(javaTerm);
		}

		return javaTerm.getContent();
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_METHOD};
	}

	private String _formatServiceImpl(JavaTerm javaTerm) {
		String javaTermContent = javaTerm.getContent();
		String javaTermName = javaTerm.getName();

		if ((!javaTermName.equals("afterPropertiesSet") &&
			 !javaTermName.equals("destroy")) ||
			!javaTerm.hasAnnotation("Override")) {

			return javaTermContent;
		}

		String superMethodCall = "super." + javaTermName + "();";

		if (javaTermContent.contains(superMethodCall)) {
			return javaTermContent;
		}

		String indent = SourceUtil.getIndent(javaTermContent) + "\t";

		return StringUtil.replaceFirst(
			javaTermContent, "{\n", "{\n" + indent + superMethodCall + "\n\n");
	}

}