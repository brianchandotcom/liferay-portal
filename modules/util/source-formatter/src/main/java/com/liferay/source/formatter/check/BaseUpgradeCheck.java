/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.util.JavaSourceUtil;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Nícolas Moura
 */
public abstract class BaseUpgradeCheck extends BaseFileCheck {

	public boolean hasValidParameters(
		int expectedParametersSize, String fileName, String javaMethodContent,
		String message, List<String> parameterList, String[] parameterTypes) {

		if (parameterList.size() != expectedParametersSize) {
			return false;
		}

		if (!hasParameterTypes(
				javaMethodContent, javaMethodContent,
				ArrayUtil.toStringArray(parameterList), parameterTypes)) {

			addMessage(fileName, message);

			return false;
		}

		return true;
	}

	protected String addNewImports(String fileName, String newContent) {
		String[] newImports = getNewImports();

		if (newImports == null) {
			return newContent;
		}

		if (fileName.endsWith(".java")) {
			newContent = JavaSourceUtil.addImports(newContent, newImports);
		}
		else if (fileName.endsWith(".jsp")) {
			newContent = addNewImportsJSP(newContent, newImports);
		}

		return newContent;
	}

	protected String addNewImportsJSP(String newContent, String[] newImports) {
		Arrays.sort(newImports);

		Matcher includesMatcher = _includesPattern.matcher(newContent);

		if (includesMatcher.find()) {
			String jspHeader = includesMatcher.group();

			return StringUtil.replaceFirst(
				newContent, jspHeader,
				getJspHeader(StringUtil.splitLines(jspHeader), newImports));
		}

		Matcher copyrightMatcher = _copyrightPattern.matcher(newContent);

		if (copyrightMatcher.find()) {
			String jspHeader = copyrightMatcher.group(1);

			return StringUtil.replaceFirst(
				newContent, jspHeader,
				getJspHeader(new String[] {jspHeader}, newImports));
		}

		return getJspHeader(new String[0], newImports) + newContent;
	}

	protected String afterFormat(
		String fileName, String absolutePath, String content,
		String newContent) {

		return addNewImports(fileName, newContent);
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (!isValidExtension(fileName)) {
			return content;
		}

		String newContent = format(fileName, absolutePath, content);

		if (!content.equals(newContent)) {
			newContent = afterFormat(
				fileName, absolutePath, content, newContent);
		}

		return newContent;
	}

	protected abstract String format(
			String fileName, String absolutePath, String content)
		throws Exception;

	protected String getJspHeader(String[] jspHeaders, String[] newImports) {
		StringBundler sb = new StringBundler(4);

		for (String jspHeader : jspHeaders) {
			sb.append(jspHeader);
			sb.append(StringPool.NEW_LINE);
		}

		for (String newImport : newImports) {
			sb.append("<%@ page import=\"");
			sb.append(newImport);
			sb.append("\" %>");
			sb.append(StringPool.NEW_LINE);
			sb.append(StringPool.NEW_LINE);
		}

		return sb.toString();
	}

	protected String[] getNewImports() {
		return null;
	}

	protected String[] getValidExtensions() {
		return new String[] {"java"};
	}

	protected boolean isValidExtension(String fileName) {
		for (String extension : getValidExtensions()) {
			if (fileName.endsWith(CharPool.PERIOD + extension)) {
				return true;
			}
		}

		return false;
	}

	private static final Pattern _copyrightPattern = Pattern.compile(
		"(<%--\\s*(\\/\\*)+(\\n|.)*(\\*\\/)+\\s*--%>)");
	private static final Pattern _includesPattern = Pattern.compile(
		"(<%@\\s*include\\s*(.+)%>\\s*)+", Pattern.MULTILINE);

}