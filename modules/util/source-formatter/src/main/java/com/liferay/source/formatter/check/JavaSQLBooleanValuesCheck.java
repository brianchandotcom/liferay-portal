/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.check.util.JavaSourceUtil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alan Huang
 */
public class JavaSQLBooleanValuesCheck extends BaseFileCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		for (String methodName : _METHOD_NAMES) {
			_checkSQLBooleanValues(fileName, content, methodName);
		}

		return content;
	}

	private void _checkMissingTransformCall(
		String fileName, String methodCall, String sqlString, int lineNumber) {

		int index = StringUtil.indexOfAny(
			sqlString, new String[] {"[$FALSE$]", "[$TRUE$]"});

		if ((index == -1) || methodCall.contains("SQLTransformer.transform(")) {
			return;
		}

		addMessage(
			fileName,
			"Use \"SQLTransformer.transform\" to wrap SQL statement if it " +
				"contains \"[$FALSE$]\" or \"[$TRUE$]\"",
			lineNumber);
	}

	private void _checkSQLBooleanValues(
		String fileName, String content, String methodName) {

		String sqlString = StringPool.BLANK;

		int x = -1;

		while (true) {
			x = content.indexOf(methodName + "(", x + 1);

			if (x == -1) {
				return;
			}

			if (ToolsUtil.isInsideQuotes(content, x)) {
				continue;
			}

			String methodCall = JavaSourceUtil.getMethodCall(content, x);

			List<String> getParameterList = JavaSourceUtil.getParameterList(
				methodCall);

			if (getParameterList.isEmpty()) {
				return;
			}

			int parameterSize = getParameterList.size();

			if (methodName.equals("AutoBatchPreparedStatementUtil.autoBatch") ||
				methodName.equals(
					"AutoBatchPreparedStatementUtil.concurrentAutoBatch")) {

				if (parameterSize != 2) {
					return;
				}

				sqlString = _stripOuterMethod(getParameterList.get(1));
			}
			else if (methodName.equals("connection.prepareStatement")) {
				if (parameterSize != 1) {
					return;
				}

				sqlString = _stripOuterMethod(getParameterList.get(0));
			}
			else if (methodName.equals("runSQL")) {
				if (parameterSize == 1) {
					sqlString = _stripOuterMethod(getParameterList.get(0));
				}
				else if (parameterSize == 2) {
					sqlString = _stripOuterMethod(getParameterList.get(1));
				}
			}
			else if (methodName.equals("StringBundler.concat")) {
				String s = StringUtil.trimTrailing(content.substring(0, x));

				if (!s.endsWith("=")) {
					continue;
				}

				String firstParameter = getParameterList.get(0);

				if (!firstParameter.startsWith("\"delete ") &&
					!firstParameter.startsWith("\"insert into ") &&
					!firstParameter.startsWith("\"select ") &&
					!firstParameter.startsWith("\"update ") &&
					!firstParameter.startsWith("\"where ")) {

					continue;
				}

				sqlString = _stripOuterMethod(methodCall);
			}

			if (sqlString.equals(StringPool.BLANK)) {
				continue;
			}

			int lineNumber = getLineNumber(content, x);

			sqlString = sqlString.replaceAll("\n", "");
			sqlString = sqlString.replaceAll("\\s{2,}", " ");

			sqlString = StringUtil.removeSubstring(sqlString, "\" + \"");
			sqlString = StringUtil.removeSubstring(sqlString, "\", \"");

			Matcher matcher = _falseTruePattern.matcher(sqlString);

			while (matcher.find()) {
				String match = matcher.group(1);

				String s1 = sqlString.substring(0, matcher.start(1));
				String s2 = sqlString.substring(matcher.start(1));

				int count1 = StringUtil.count(s1, CharPool.APOSTROPHE) % 2;
				int count2 = StringUtil.count(s2, CharPool.APOSTROPHE) % 2;

				if ((count1 == 1) && (count2 == 1)) {
					continue;
				}

				addMessage(
					fileName,
					StringBundler.concat(
						"Use \"[$", StringUtil.toUpperCase(match),
						"$]\" instead of \"", match, "\" in SQL statements"),
					lineNumber);
			}

			if (methodName.equals("runSQL") ||
				methodName.equals("StringBundler.concat")) {

				continue;
			}

			_checkMissingTransformCall(
				fileName, methodCall, sqlString, lineNumber);
		}
	}

	private String _stripOuterMethod(String parameter) {
		String trimmedParameter = parameter.trim();

		if (trimmedParameter.endsWith(")") &&
			trimmedParameter.startsWith("SQLTransformer.transform(")) {

			trimmedParameter = trimmedParameter.substring(
				25, trimmedParameter.length() - 1);
		}

		trimmedParameter = trimmedParameter.trim();

		if (trimmedParameter.endsWith(")") &&
			trimmedParameter.startsWith("StringBundler.concat(")) {

			trimmedParameter = trimmedParameter.substring(
				21, trimmedParameter.length() - 1);
		}

		return trimmedParameter;
	}

	private static final String[] _METHOD_NAMES = {
		"AutoBatchPreparedStatementUtil.autoBatch",
		"AutoBatchPreparedStatementUtil.concurrentAutoBatch",
		"connection.prepareStatement", "runSQL", "StringBundler.concat"
	};

	private static final Pattern _falseTruePattern = Pattern.compile(
		"=\\s*\\b(false|true)\\b", Pattern.CASE_INSENSITIVE);

}