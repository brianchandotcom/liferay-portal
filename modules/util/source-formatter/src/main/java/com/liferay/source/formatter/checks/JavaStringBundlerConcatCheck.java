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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.checks.util.JavaSourceUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaTerm;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaStringBundlerConcatCheck extends BaseJavaTermCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, JavaTerm javaTerm,
		String fileContent) {

		JavaClass javaClass = (JavaClass)javaTerm;

		List<String> imports = javaClass.getImports();

		boolean hasPetraStringStringBundler = imports.contains(
			"com.liferay.petra.string.StringBundler");

		String content = javaTerm.getContent();

		Matcher matcher1 = _stringBundlerConcatPattern.matcher(content);

		while (matcher1.find()) {
			String stringBundlerConcatMethodCall = _getMethodCall(
				content, matcher1.start());

			List<String> parameterList = JavaSourceUtil.getParameterList(
				stringBundlerConcatMethodCall);

			if (parameterList.size() < 3) {
				addMessage(
					fileName,
					"Do not use 'StringBundler.concat' when concatenating " +
						"less than 3 elements",
					javaTerm.getLineNumber(matcher1.start()));
			}

			if (!hasPetraStringStringBundler) {
				continue;
			}

			Matcher matcher2 = _stringValueOfPattern.matcher(
				stringBundlerConcatMethodCall);

			while (matcher2.find()) {
				int level = getLevel(
					stringBundlerConcatMethodCall.substring(
						0, matcher2.start()));

				if (level != 1) {
					continue;
				}

				String stringValueOfMethodCall = _getMethodCall(
					stringBundlerConcatMethodCall, matcher2.start());

				parameterList = JavaSourceUtil.getParameterList(
					stringValueOfMethodCall);

				if (parameterList.size() != 1) {
					continue;
				}

				String newStringBundlerConcatMethodCall =
					StringUtil.replaceFirst(
						stringBundlerConcatMethodCall, stringValueOfMethodCall,
						parameterList.get(0));

				return StringUtil.replace(
					content, stringBundlerConcatMethodCall,
					newStringBundlerConcatMethodCall);
			}
		}

		return javaTerm.getContent();
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CLASS};
	}

	private String _getMethodCall(String s, int start) {
		int x = start;

		while (true) {
			x = s.indexOf(StringPool.CLOSE_PARENTHESIS, x + 1);

			if (ToolsUtil.isInsideQuotes(s, x + 1)) {
				continue;
			}

			String methodCall = s.substring(start, x + 1);

			if (getLevel(methodCall) == 0) {
				return methodCall;
			}
		}
	}

	private static final Pattern _stringBundlerConcatPattern = Pattern.compile(
		"StringBundler\\.concat\\(");
	private static final Pattern _stringValueOfPattern = Pattern.compile(
		"String\\.valueOf\\(");

}