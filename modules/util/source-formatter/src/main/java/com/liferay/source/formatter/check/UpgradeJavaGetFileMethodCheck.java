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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.util.JavaSourceUtil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Tamyris Bernardo
 */
public class UpgradeJavaGetFileMethodCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (!fileName.endsWith(".java")) {
			return content;
		}

		List<String> importNames = JavaSourceUtil.getImportNames(content);

		if (!importNames.contains(
				"com.liferay.document.library.kernel.service." +
					"DLFileEntryLocalServiceUtil") &&
			!importNames.contains(
				"com.liferay.document.library.kernel.service." +
					"DLFileEntryLocalService")) {

			return content;
		}

		boolean replaced = false;

		Matcher matcher = _getFilePattern.matcher(content);

		while (matcher.find()) {
			String methodCall = matcher.group(2);

			if (methodCall.contains("DLFileEntryLocalServiceUtil") ||
				hasClassOrVariableName(
					"DLFileEntryLocalService", content, methodCall)) {

				content = _format(content, methodCall, matcher.group(1));
			}

			replaced = true;
		}

		if (replaced) {
			content = JavaSourceUtil.addImports(
				content, "com.liferay.portal.kernel.util.FileUtil");
		}

		return content;
	}

	private String _format(
		String content, String methodCall, String variableName) {

		List<String> parameterList = JavaSourceUtil.getParameterList(
			methodCall);

		if (parameterList.size() != 3) {
			return content;
		}

		return StringUtil.replace(
			content, variableName + methodCall,
			_toAssembleMethod(methodCall, parameterList, variableName));
	}

	private String _toAssembleMethod(
		String methodCall, List<String> parameterList, String variableName) {

		StringBundler sb = new StringBundler(12);

		sb.append(StringPool.TAB);
		sb.append(StringPool.TAB);
		sb.append("InputStream inputStream = ");
		sb.append(getVariableName(methodCall));
		sb.append(".getFileAsStream(");
		sb.append(StringUtil.merge(parameterList, ", "));
		sb.append(StringPool.CLOSE_PARENTHESIS);
		sb.append(StringPool.SEMICOLON);
		sb.append(StringPool.NEW_LINE);
		sb.append(StringPool.NEW_LINE);
		sb.append(variableName);
		sb.append("FileUtil.createTempFile(inputStream)");

		return sb.toString();
	}

	private static final Pattern _getFilePattern = Pattern.compile(
		"(\\t+?\\s+?\\w*\\s*\\w+\\s*[^)]\\s+)(\\s*\\w+\\.getFile\\([^)]+\\))");

}