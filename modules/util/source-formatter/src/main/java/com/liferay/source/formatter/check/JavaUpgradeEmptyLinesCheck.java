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

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Qi Zhang
 */
public class JavaUpgradeEmptyLinesCheck extends BaseFileCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (!fileName.endsWith("UpgradeProcess.java")) {
			return content;
		}

		return _fixUpgradeClass(content);
	}

	private String _fixUpgradeClass(String content) {
		for (String methodName : _DB_PROCESS_METHODS) {
			Pattern pattern1 = Pattern.compile(
				"(?<=\n)(\t+\\b" + methodName + "\\(.+?\\);\n+)+",
				Pattern.DOTALL);

			Matcher matcher1 = pattern1.matcher(content);

			while (matcher1.find()) {
				String methodCalls = matcher1.group();

				Pattern pattern2 = Pattern.compile("\\b" + methodName + "\\(");

				Matcher matcher2 = pattern2.matcher(methodCalls);

				String previousMethodCallFirstParameter = StringPool.BLANK;
				int previousMethodCallEndLineNumber = -1;
				int startPos = matcher1.start();
				int methodCallEndPosition = -1;

				while (matcher2.find()) {
					String methodCall = JavaSourceUtil.getMethodCall(
						methodCalls, matcher2.start());

					List<String> parameterList =
						JavaSourceUtil.getParameterList(methodCall);

					String firstParameter;

					if (ListUtil.isEmpty(parameterList)) {
						firstParameter = StringPool.BLANK;
					}
					else {
						firstParameter = parameterList.get(0);
					}

					if (previousMethodCallEndLineNumber == -1) {
						methodCallEndPosition =
							matcher2.start() + methodCall.length() + 1;

						previousMethodCallEndLineNumber = getLineNumber(
							methodCalls, methodCallEndPosition);

						previousMethodCallFirstParameter = firstParameter;

						continue;
					}

					int lineNumber = getLineNumber(
						methodCalls, matcher2.start());

					if (StringUtil.equals(
							previousMethodCallFirstParameter, firstParameter) &&
						((previousMethodCallEndLineNumber + 1) != lineNumber)) {

						return StringUtil.replaceFirst(
							content, StringPool.NEW_LINE, StringPool.BLANK,
							startPos + methodCallEndPosition);
					}
					else if (!StringUtil.equals(
								previousMethodCallFirstParameter,
								firstParameter) &&
							 ((previousMethodCallEndLineNumber + 2) !=
								 lineNumber)) {

						return StringUtil.insert(
							content, StringPool.NEW_LINE,
							startPos + methodCallEndPosition);
					}

					methodCallEndPosition =
						matcher2.start() + methodCall.length() + 1;

					previousMethodCallEndLineNumber = getLineNumber(
						methodCalls, methodCallEndPosition);

					previousMethodCallFirstParameter = firstParameter;
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