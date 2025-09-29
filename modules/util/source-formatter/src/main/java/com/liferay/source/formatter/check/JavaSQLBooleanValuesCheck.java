/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
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
		String fileName, String sqlString, int lineNumber) {

		int index = StringUtil.indexOfAny(
			sqlString, new String[] {"[$FALSE$]", "[$TRUE$]"});

		if (index == -1) {
			return;
		}

		index = sqlString.lastIndexOf("SQLTransformer.transform(", index);

		if (index != -1) {
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

		int x = -1;

		while (true) {
			x = content.indexOf(methodName + "(", x + 1);

			if (x == -1) {
				return;
			}

			if (ToolsUtil.isInsideQuotes(content, x)) {
				continue;
			}

			List<String> getParameterList = JavaSourceUtil.getParameterList(
				JavaSourceUtil.getMethodCall(content, x));

			if (getParameterList.isEmpty()) {
				return;
			}

			String sqlString = StringPool.BLANK;

			int parameterSize = getParameterList.size();

			if (methodName.equals("AutoBatchPreparedStatementUtil.autoBatch") ||
				methodName.equals(
					"AutoBatchPreparedStatementUtil.concurrentAutoBatch") ||
				methodName.equals("connection.prepareStatement")) {

				if (parameterSize != 1) {
					return;
				}

				sqlString = getParameterList.get(0);
			}
			else if (methodName.equals("runSQL")) {
				if (parameterSize == 1) {
					sqlString = getParameterList.get(0);
				}
				else if (parameterSize == 2) {
					sqlString = getParameterList.get(1);
				}
			}
			else if (methodName.equals("StringBundler.concat")) {
				for (String parameter : getParameterList) {
					sqlString = sqlString + StringUtil.unquote(parameter);
				}

				if (!sqlString.startsWith("delete ") &&
					!sqlString.startsWith("insert into ") &&
					!sqlString.startsWith("select ") &&
					!sqlString.startsWith("update ") &&
					!sqlString.startsWith("where ")) {

					continue;
				}
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

				return;
			}

			_checkMissingTransformCall(fileName, sqlString, lineNumber);
		}
	}

	private static final String[] _METHOD_NAMES = {
		"AutoBatchPreparedStatementUtil.autoBatch",
		"AutoBatchPreparedStatementUtil.concurrentAutoBatch",
		"connection.prepareStatement", "runSQL", "StringBundler.concat"
	};

	private static final Pattern _falseTruePattern = Pattern.compile(
		"=\\s*\\b(false|true)\\b");

}