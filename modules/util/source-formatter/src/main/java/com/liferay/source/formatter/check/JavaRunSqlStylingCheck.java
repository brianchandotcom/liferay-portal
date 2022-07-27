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

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.util.JavaSourceUtil;
import com.liferay.source.formatter.parser.JavaTerm;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alan Huang
 */
public class JavaRunSqlStylingCheck extends BaseJavaTermCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, JavaTerm javaTerm,
		String fileContent) {

		String content = javaTerm.getContent();

		Matcher matcher = _runSqlPattern.matcher(content);

		while (matcher.find()) {
			String runSqlMethodCall = JavaSourceUtil.getMethodCall(
				content, matcher.start());

			List<String> parameterList = JavaSourceUtil.getParameterList(
				runSqlMethodCall);

			if (parameterList.isEmpty() || (parameterList.size() != 1)) {
				continue;
			}

			String parameter = parameterList.get(0);

			String newParameter = parameter.replaceAll(
				"([\\s\\S]*)?(?<!\\[\\$)\\b(FALSE|TRUE)\\b(?!\\$\\])" +
					"([\\s\\S]*)",
				"$1[\\$$2\\$]$3");

			if (parameter.equals(newParameter)) {
				continue;
			}

			return StringUtil.replaceFirst(
				content, parameter, newParameter, matcher.start());
		}

		return javaTerm.getContent();
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CLASS};
	}

	private static final Pattern _runSqlPattern = Pattern.compile(
		"\\brunSQL\\(");

}