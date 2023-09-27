/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringPool;
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

			if (newParameter.endsWith(";\"") &&
				!newParameter.endsWith("\";\"")) {

				newParameter = StringUtil.removeLast(
					newParameter, StringPool.SEMICOLON);
			}

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