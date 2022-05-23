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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.util.JavaSourceUtil;
import com.liferay.source.formatter.parser.JavaTerm;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Qi Zhang
 */
public class JavaUpgradeEmptyLinesCheck extends BaseJavaTermCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, JavaTerm javaTerm,
		String fileContent) {

		if (!fileName.endsWith("UpgradeProcess.java")) {
			return javaTerm.getContent();
		}

		return _fixUpgradeClass(javaTerm.getContent());
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_METHOD};
	}

	private String _fixUpgradeClass(String content) {
		for (String methodName : _DB_PROCESS_METHODS) {
			Pattern pattern = Pattern.compile(
				"(\t*\\b" + methodName + "\\()[^;]+;(\n*\\1[^;]+;)+");

			Matcher matcher = pattern.matcher(content);

			while (matcher.find()) {
				String statement = matcher.group();

				Pattern sqlPattern = Pattern.compile(
					"\\b" + methodName + "\\(");

				Matcher sqlMatcher = sqlPattern.matcher(statement);

				String preFirstParameter = StringPool.BLANK;
				int preLineNumber = -1;
				int startPos = matcher.start();
				int sqlEndPos = -1;

				while (sqlMatcher.find()) {
					String methodCall = JavaSourceUtil.getMethodCall(
						statement, sqlMatcher.start());

					List<String> parameterList =
						JavaSourceUtil.getParameterList(methodCall);

					String firstParameter;

					if (ListUtil.isEmpty(parameterList)) {
						firstParameter = StringPool.BLANK;
					}
					else {
						firstParameter = parameterList.get(0);
					}

					if (preLineNumber == -1) {
						sqlEndPos =
							sqlMatcher.start() + methodCall.length() + 1;

						preLineNumber = getLineNumber(statement, sqlEndPos);

						preFirstParameter = firstParameter;

						continue;
					}

					int lineNumber = getLineNumber(
						statement, sqlMatcher.start());

					if (StringUtil.equals(preFirstParameter, firstParameter) &&
						((preLineNumber + 1) != lineNumber)) {

						return StringUtil.replaceFirst(
							content, StringPool.NEW_LINE, StringPool.BLANK,
							startPos + sqlEndPos);
					}
					else if (!StringUtil.equals(
								preFirstParameter, firstParameter) &&
							 ((preLineNumber + 2) != lineNumber)) {

						return StringUtil.insert(
							content, StringPool.NEW_LINE, startPos + sqlEndPos);
					}

					sqlEndPos = sqlMatcher.start() + methodCall.length() + 1;

					preLineNumber = getLineNumber(statement, sqlEndPos);

					preFirstParameter = firstParameter;
				}
			}
		}

		return content;
	}

	private static final String[] _DB_PROCESS_METHODS = {
		"addColumn", "addIndexes", "alterColumnName", "alterColumnType",
		"alterTableAddColumn", "alterTableDropColumn", "dropColumn",
		"dropIndexes", "renameColumn"
	};

}