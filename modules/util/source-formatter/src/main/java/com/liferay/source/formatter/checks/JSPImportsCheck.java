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

package com.liferay.source.formatter.checks;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ImportsFormatter;
import com.liferay.source.formatter.JSPImportsFormatter;
import com.liferay.source.formatter.checks.util.JSPSourceUtil;

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
		throws Exception {

		content = _formatJSPImportsOrTaglibs(
			fileName, content, _jspImportPattern,
			_uncompressedJSPImportPattern);
		content = _formatJSPImportsOrTaglibs(
			fileName, content, _jspTaglibPattern,
			_uncompressedJSPTaglibPattern);

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
		throws Exception {

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

	private final Pattern _incorrectTaglibPattern = Pattern.compile(
		"(taglib )(prefix=\".+\") (uri=\".*\")");
	private final Pattern _jspImportPattern = Pattern.compile(
		"(<%@\\s+(page|tag)\\s+import=\".+?\\s+%>\\s*)+");
	private final Pattern _jspTaglibPattern = Pattern.compile(
		"(<%@\\s+taglib\\s+uri=\".+?\\s+%>\\s*)+");
	private final Pattern _taglibSingleLinePattern = Pattern.compile(
		"(<%@)\\s+(page|tag|taglib)\\s+((import|uri)=.+?)\\s+(%>)\\s*");
	private final Pattern _uncompressedJSPImportPattern = Pattern.compile(
		"(<.*(?:page|tag) import=\".*>\n*)+", Pattern.MULTILINE);
	private final Pattern _uncompressedJSPTaglibPattern = Pattern.compile(
		"(<.*taglib uri=\".*>\n*)+", Pattern.MULTILINE);

}