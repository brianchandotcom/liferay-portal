/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ImportsFormatter;
import com.liferay.source.formatter.JSPImportsFormatter;
import com.liferay.source.formatter.check.util.JSPSourceUtil;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JSPImportsCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		content = _formatJSPImportsOrTaglibs(
			fileName, content, _jspImportPattern,
			_uncompressedJSPImportPattern);
		content = _formatJSPImportsOrTaglibs(
			fileName, content, _jspTaglibPattern,
			_uncompressedJSPTaglibPattern);

		content = _orderObjects(
			content, _includeInitPattern, _uncompressedJSPTaglibPattern,
			_uncompressedJSPImportPattern, _defineObjectsPattern);

		if ((isPortalSource() || isSubrepository()) &&
			content.contains("page import=") &&
			!fileName.contains("init.jsp") && !fileName.contains("init.tag") &&
			!fileName.contains("init-ext.jsp") &&
			!fileName.contains("/taglib/aui/") &&
			!fileName.endsWith("touch.jsp") &&
			(fileName.endsWith(".jspf") || content.contains("include file="))) {

			addMessage(fileName, "Move imports to init.jsp");
		}

		content = JSPSourceUtil.compressImportsOrTaglibs(
			fileName, content, "<%@ page import=");
		content = JSPSourceUtil.compressImportsOrTaglibs(
			fileName, content, "<%@ tag import=");
		content = JSPSourceUtil.compressImportsOrTaglibs(
			fileName, content, "<%@ taglib uri=");

		Matcher matcher = _incorrectTaglibPattern.matcher(content);

		return matcher.replaceAll("$1$3 $2");
	}

	private String _formatJSPImportsOrTaglibs(
			String fileName, String content, Pattern compressedPattern,
			Pattern uncompressedPattern)
		throws IOException {

		if (fileName.endsWith("init-ext.jsp")) {
			return content;
		}

		Matcher matcher = compressedPattern.matcher(content);

		List<String> groups = new ArrayList<>();

		while (matcher.find()) {
			groups.add(matcher.group());
		}

		if (groups.isEmpty()) {
			return content;
		}

		String imports = StringUtil.merge(groups, "\n");

		matcher = _taglibSingleLinePattern.matcher(imports);

		String newImports = matcher.replaceAll("$1 $2 $3 $5\n");

		for (int i = 1; i < groups.size(); i++) {
			content = StringUtil.removeSubstring(content, groups.get(i));
		}

		content = StringUtil.replaceFirst(
			content, groups.get(0), newImports + "\n");

		content = StringUtil.replaceFirst(content, imports, newImports);

		ImportsFormatter importsFormatter = new JSPImportsFormatter();

		return importsFormatter.format(content, uncompressedPattern);
	}

	private String _orderObjects(String content, Pattern... patternArray) {
		for (int i = 0; i < (patternArray.length - 1); i++) {
			Pattern pattern1 = patternArray[i];

			Matcher matcher1 = pattern1.matcher(content);

			if (!matcher1.find()) {
				continue;
			}

			Pattern pattern2 = patternArray[i + 1];

			Matcher matcher2 = pattern2.matcher(content);

			if (!matcher2.find()) {
				i++;

				continue;
			}

			int x = matcher1.start();
			int y = matcher2.start();

			if (x < y) {
				continue;
			}

			String match1 = matcher1.group();
			String match2 = matcher2.group();

			content = StringUtil.replaceFirst(
				content, match1, match2 + "\n", x);

			content = StringUtil.replaceFirst(
				content, match2, match1 + "\n", y);

			return _orderObjects(content, patternArray);
		}

		return content;
	}

	private static final Pattern _defineObjectsPattern = Pattern.compile(
		"(<[\\w-]:defineObjects />\n*)+", Pattern.MULTILINE);
	private static final Pattern _includeInitPattern = Pattern.compile(
		"(<%@ include file=\".*init\\.jsp\" %>\n*)+", Pattern.MULTILINE);
	private static final Pattern _incorrectTaglibPattern = Pattern.compile(
		"(taglib )(prefix=\".+\") (uri=\".*\")");
	private static final Pattern _jspImportPattern = Pattern.compile(
		"(<%@\\s*(page|tag)\\s+import=\".+?\\s*%>\\s*)+");
	private static final Pattern _jspTaglibPattern = Pattern.compile(
		"(<%@\\s*taglib\\s+uri=\".+?\\s*%>\\s*)+");
	private static final Pattern _taglibSingleLinePattern = Pattern.compile(
		"(<%@)\\s*(page|tag|taglib)\\s+((import|uri)=.+?)\\s*(%>)\\s*");
	private static final Pattern _uncompressedJSPImportPattern =
		Pattern.compile(
			"(<.*(?:page|tag) import=\".*>\n*)+", Pattern.MULTILINE);
	private static final Pattern _uncompressedJSPTaglibPattern =
		Pattern.compile("(<.*taglib uri=\".*>\n*)+", Pattern.MULTILINE);

}