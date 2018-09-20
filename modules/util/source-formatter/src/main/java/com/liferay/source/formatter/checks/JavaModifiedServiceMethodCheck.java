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
import com.liferay.source.formatter.parser.JavaTerm;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaModifiedServiceMethodCheck extends BaseJavaTermCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, JavaTerm javaTerm,
		String fileContent) {

		String javaTermContent = javaTerm.getContent();

		String methodName = javaTerm.getName();

		if (!methodName.equals("modifiedService")) {
			return javaTermContent;
		}

		Matcher matcher = _missingEmptyLinePattern.matcher(javaTermContent);

		if (matcher.find()) {
			return StringUtil.replaceFirst(
				javaTermContent, "\n", "\n\n", matcher.start(1));
		}

		return javaTermContent;
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_METHOD};
	}

	private static final Pattern _missingEmptyLinePattern = Pattern.compile(
		"\tremovedService\\([^;]+;(\n)\t+addingService\\(");

}