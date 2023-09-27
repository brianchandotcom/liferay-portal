/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
					"DLFileEntryLocalService", content, content, methodCall)) {

				content = _formatMethodCall(
					content, methodCall, matcher.group(1));
			}

			replaced = true;
		}

		if (replaced) {
			content = JavaSourceUtil.addImports(
				content, "com.liferay.portal.kernel.util.FileUtil");
		}

		return content;
	}

	private String _formatMethodCall(
		String content, String methodCall, String variableName) {

		List<String> parameterList = JavaSourceUtil.getParameterList(
			methodCall);

		if (parameterList.size() != 3) {
			return content;
		}

		return StringUtil.replace(
			content, variableName + methodCall,
			_getNewMethodCall(methodCall, parameterList, variableName));
	}

	private String _getNewMethodCall(
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