/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.util.JavaSourceUtil;
import com.liferay.source.formatter.parser.JavaClass;
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

		JavaClass javaClass = (JavaClass)javaTerm;

		if (!_isUpgradeJavaClass(javaClass)) {
			return javaClass.getContent();
		}

		String content = javaTerm.getContent();

		for (String methodName : _DB_PROCESS_METHODS) {
			Pattern pattern1 = Pattern.compile(
				"(?<=\n)(\t+\\b" + methodName + "\\(.+?\\);\n+)+",
				Pattern.DOTALL);

			Matcher matcher1 = pattern1.matcher(content);

			while (matcher1.find()) {
				String methodCalls = matcher1.group();

				Pattern pattern2 = Pattern.compile("\\b" + methodName + "\\(");

				Matcher matcher2 = pattern2.matcher(methodCalls);

				int methodCallEndPosition = -1;
				int previousMethodCallEndLineNumber = -1;
				String previousMethodCallFirstParameter = StringPool.BLANK;
				int startPos = matcher1.start();

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

					if (!StringUtil.equals(
							previousMethodCallFirstParameter, firstParameter) &&
						((previousMethodCallEndLineNumber + 2) != lineNumber)) {

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

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CLASS};
	}

	private boolean _isUpgradeJavaClass(JavaClass javaClass) {
		List<String> extendedClassNames = javaClass.getExtendedClassNames();

		for (String extendedClassName : extendedClassNames) {
			if (extendedClassName.endsWith("UpgradeProcess")) {
				return true;
			}
		}

		return false;
	}

	private static final String[] _DB_PROCESS_METHODS = {
		"addColumn", "addIndexes", "alterColumnName", "alterColumnType",
		"alterTableAddColumn", "alterTableDropColumn", "dropColumn",
		"dropIndexes", "renameColumn"
	};

}