/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.check.util.JavaSourceUtil;
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

		List<String> imports = javaClass.getImportNames();

		boolean hasPetraStringStringBundler = imports.contains(
			"com.liferay.petra.string.StringBundler");

		String content = javaTerm.getContent();

		Matcher matcher = _stringBundlerConcatPattern.matcher(content);

		while (matcher.find()) {
			if (ToolsUtil.isInsideQuotes(content, matcher.start() + 1)) {
				continue;
			}

			String stringBundlerConcatMethodCall = JavaSourceUtil.getMethodCall(
				content, matcher.start());

			List<String> parameterList = JavaSourceUtil.getParameterList(
				stringBundlerConcatMethodCall);

			if (parameterList.size() < 3) {
				addMessage(
					fileName,
					"Do not use 'StringBundler.concat' when concatenating " +
						"less than 3 elements",
					javaTerm.getLineNumber(matcher.start()));
			}

			if (!hasPetraStringStringBundler) {
				continue;
			}

			for (String parameter : parameterList) {
				String newParameter = _removeUnnecessaryTypeCast(parameter);

				if (StringUtil.equals(parameter, newParameter)) {
					continue;
				}

				String newStringBundlerConcatMethodCall =
					StringUtil.replaceFirst(
						stringBundlerConcatMethodCall, parameter, newParameter);

				return StringUtil.replace(
					content, stringBundlerConcatMethodCall,
					newStringBundlerConcatMethodCall, matcher.start());
			}
		}

		return javaTerm.getContent();
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CLASS};
	}

	private String _removeUnnecessaryTypeCast(String parameter) {
		if (parameter.startsWith("String.valueOf(")) {
			String stringValueOfMethodCall = JavaSourceUtil.getMethodCall(
				parameter, 0);

			List<String> parameterList = JavaSourceUtil.getParameterList(
				stringValueOfMethodCall);

			if (parameterList.size() == 1) {
				return parameterList.get(0);
			}
		}

		if (parameter.endsWith(".toString()") &&
			!StringUtil.equals(parameter, "super.toString()")) {

			return parameter.substring(0, parameter.lastIndexOf(".toString()"));
		}

		return parameter;
	}

	private static final Pattern _stringBundlerConcatPattern = Pattern.compile(
		"StringBundler\\.concat\\(");

}