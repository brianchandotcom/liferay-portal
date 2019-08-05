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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.checks.util.JSPSourceUtil;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JSPUnusedTermsCheck extends JSPTermsBaseCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		populateContentsMap(fileName, content);

		content = _removeUnusedImports(fileName, content);

		content = JSPSourceUtil.compressImportsOrTaglibs(
			fileName, content, "<%@ page import=");
		content = JSPSourceUtil.compressImportsOrTaglibs(
			fileName, content, "<%@ tag import=");

		if (isPortalSource() || isSubrepository()) {
			content = _removeUnusedPortletDefineObjects(fileName, content);

			content = _removeDuplicateDefineObjects(fileName, content);

			content = _removeUnusedTaglibs(fileName, content);

			content = JSPSourceUtil.compressImportsOrTaglibs(
				fileName, content, "<%@ taglib uri=");

			content = _removeUnusedVariables(fileName, absolutePath, content);
		}

		put(fileName, content);

		return content;
	}

	private void _addJSPUnusedImports(
		String fileName, List<String> importLines,
		List<String> unneededImports) {

		Set<String> checkedFileNames = new HashSet<>();
		Set<String> includeFileNames = new HashSet<>();

		for (String importLine : importLines) {
			int x = importLine.indexOf(CharPool.QUOTE);

			int y = importLine.indexOf(CharPool.QUOTE, x + 1);

			if ((x == -1) || (y == -1)) {
				continue;
			}

			String className = importLine.substring(x + 1, y);

			className = className.substring(
				className.lastIndexOf(CharPool.PERIOD) + 1);

			if (_hasUnusedJSPTerm(
					fileName, "\\W" + className + "[^\\w\"]", "class",
					checkedFileNames, includeFileNames, getContentsMap())) {

				unneededImports.add(importLine);
			}
		}
	}

	private void _addJSPUnusedTaglibs(
		String fileName, List<String> taglibLines,
		List<String> unneededTaglibs) {

		Set<String> checkedFileNames = new HashSet<>();
		Set<String> includeFileNames = new HashSet<>();

		for (String taglibLine : taglibLines) {
			int x = taglibLine.indexOf("prefix=\"");

			int y = taglibLine.indexOf(CharPool.QUOTE, x + 8);

			if ((x == -1) || (y == -1)) {
				continue;
			}

			String prefix = taglibLine.substring(x + 8, y);

			String regex = StringBundler.concat(
				StringPool.LESS_THAN, prefix, StringPool.COLON, StringPool.PIPE,
				"\\$\\{", prefix, StringPool.COLON);

			if (_hasUnusedJSPTerm(
					fileName, regex, "taglib", checkedFileNames,
					includeFileNames, getContentsMap())) {

				unneededTaglibs.add(taglibLine);
			}
		}
	}

	private List<String> _getJSPDuplicateImports(
		String fileName, String content, List<String> importLines) {

		List<String> duplicateImports = new ArrayList<>();

		for (String importLine : importLines) {
			int x = content.indexOf("<%@ include file=");

			if (x == -1) {
				continue;
			}

			int y = content.indexOf("<%@ page import=");

			if (y == -1) {
				y = content.indexOf("<%@ tag import=");

				if (y == -1) {
					continue;
				}
			}

			if ((x < y) && _isJSPDuplicateImport(fileName, importLine, false)) {
				duplicateImports.add(importLine);
			}
		}

		return duplicateImports;
	}

	private List<String> _getJSPDuplicateTaglibs(
		String fileName, String content, List<String> taglibLines) {

		List<String> duplicateTaglibs = new ArrayList<>();

		for (String taglibLine : taglibLines) {
			int x = content.indexOf("<%@ include file=");

			if (x == -1) {
				continue;
			}

			int y = content.indexOf("<%@ taglib uri=");

			if (y == -1) {
				continue;
			}

			if ((x < y) && _isJSPDuplicateTaglib(fileName, taglibLine, false)) {
				duplicateTaglibs.add(taglibLine);
			}
		}

		return duplicateTaglibs;
	}

	private String _getVariableName(String line) {
		if (!line.endsWith(";") || line.startsWith("//")) {
			return null;
		}

		String variableName = null;

		int x = line.indexOf(" = ");

		if (x == -1) {
			int y = line.lastIndexOf(CharPool.SPACE);

			if (y != -1) {
				variableName = line.substring(y + 1, line.length() - 1);
			}
		}
		else {
			line = line.substring(0, x);

			int y = line.lastIndexOf(CharPool.SPACE);

			if (y != -1) {
				variableName = line.substring(y + 1);
			}
		}

		if (Validator.isVariableName(variableName)) {
			return variableName;
		}

		return null;
	}

	private boolean _hasUnusedJSPTerm(
		String fileName, String regex, String type,
		Set<String> checkedForIncludesFileNames, Set<String> includeFileNames,
		Map<String, String> contentsMap) {

		includeFileNames.add(fileName);

		Set<String> checkedForUnusedJSPTerm = new HashSet<>();

		return !_isJSPTermRequired(
			fileName, regex, type, checkedForUnusedJSPTerm,
			checkedForIncludesFileNames, includeFileNames, contentsMap);
	}

	private boolean _hasUnusedPortletDefineObjectsProperty(
		String fileName, String portletDefineObjectProperty,
		Set<String> checkedFileNames, Set<String> includeFileNames) {

		return _hasUnusedJSPTerm(
			fileName, "\\W" + portletDefineObjectProperty + "\\W",
			"portletDefineObjectProperty", checkedFileNames, includeFileNames,
			getContentsMap());
	}

	private boolean _hasUnusedVariable(
		String fileName, String line, Set<String> checkedFileNames,
		Set<String> includeFileNames) {

		if (line.contains(": ")) {
			return false;
		}

		String variableName = _getVariableName(line);

		if (Validator.isNull(variableName) || variableName.equals("false") ||
			variableName.equals("true")) {

			return false;
		}

		return _hasUnusedJSPTerm(
			fileName, "\\W" + variableName + "\\W", "variable",
			checkedFileNames, includeFileNames, getContentsMap());
	}

	private boolean _isJSPDuplicateDefineObjects(
		String fileName, String defineObjects, boolean checkFile) {

		Map<String, String> contentsMap = getContentsMap();

		String content = contentsMap.get(fileName);

		if (Validator.isNull(content)) {
			return false;
		}

		if (checkFile && content.contains(defineObjects)) {
			return true;
		}

		int x = content.indexOf("<%@ include file=");

		if (x == -1) {
			return false;
		}

		x = content.indexOf(CharPool.QUOTE, x);

		if (x == -1) {
			return false;
		}

		int y = content.indexOf(CharPool.QUOTE, x + 1);

		if (y == -1) {
			return false;
		}

		String includeFileName = content.substring(x + 1, y);

		includeFileName = JSPSourceUtil.buildFullPathIncludeFileName(
			fileName, includeFileName, getContentsMap());

		return _isJSPDuplicateDefineObjects(
			includeFileName, defineObjects, true);
	}

	private boolean _isJSPDuplicateImport(
		String fileName, String importLine, boolean checkFile) {

		Map<String, String> contentsMap = getContentsMap();

		String content = contentsMap.get(fileName);

		if (Validator.isNull(content)) {
			return false;
		}

		int x = importLine.indexOf("page");

		if (x == -1) {
			x = importLine.indexOf("tag");

			if (x == -1) {
				return false;
			}
		}

		if (checkFile && content.contains(importLine.substring(x))) {
			return true;
		}

		int y = content.indexOf("<%@ include file=");

		if (y == -1) {
			return false;
		}

		y = content.indexOf(CharPool.QUOTE, y);

		if (y == -1) {
			return false;
		}

		int z = content.indexOf(CharPool.QUOTE, y + 1);

		if (z == -1) {
			return false;
		}

		String includeFileName = content.substring(y + 1, z);

		includeFileName = JSPSourceUtil.buildFullPathIncludeFileName(
			fileName, includeFileName, getContentsMap());

		return _isJSPDuplicateImport(includeFileName, importLine, true);
	}

	private boolean _isJSPDuplicateTaglib(
		String fileName, String taglibLine, boolean checkFile) {

		Map<String, String> contentsMap = getContentsMap();

		String content = contentsMap.get(fileName);

		if (Validator.isNull(content)) {
			return false;
		}

		int x = taglibLine.indexOf("taglib");

		if (x == -1) {
			return false;
		}

		if (checkFile && content.contains(taglibLine.substring(x))) {
			return true;
		}

		int y = content.indexOf("<%@ include file=");

		if (y == -1) {
			return false;
		}

		y = content.indexOf(CharPool.QUOTE, y);

		if (y == -1) {
			return false;
		}

		int z = content.indexOf(CharPool.QUOTE, y + 1);

		if (z == -1) {
			return false;
		}

		String includeFileName = content.substring(y + 1, z);

		includeFileName = JSPSourceUtil.buildFullPathIncludeFileName(
			fileName, includeFileName, getContentsMap());

		return _isJSPDuplicateTaglib(includeFileName, taglibLine, true);
	}

	private boolean _isJSPTermRequired(
		String fileName, String regex, String type,
		Set<String> checkedForUnusedJSPTerm,
		Set<String> checkedForIncludesFileNames, Set<String> includeFileNames,
		Map<String, String> contentsMap) {

		if (checkedForUnusedJSPTerm.contains(fileName)) {
			return false;
		}

		checkedForUnusedJSPTerm.add(fileName);

		String content = contentsMap.get(fileName);

		if (Validator.isNull(content)) {
			return false;
		}

		int count = 0;

		Pattern pattern = Pattern.compile(regex);

		Matcher matcher = pattern.matcher(content);

		while (matcher.find()) {
			if (!JSPSourceUtil.isJavaSource(content, matcher.start()) ||
				!ToolsUtil.isInsideQuotes(content, matcher.start() + 1)) {

				count++;
			}
		}

		if ((count > 1) ||
			((count == 1) &&
			 (!type.equals("variable") ||
			  (checkedForUnusedJSPTerm.size() > 1)))) {

			return true;
		}

		if (!checkedForIncludesFileNames.contains(fileName)) {
			includeFileNames.addAll(
				JSPSourceUtil.getJSPIncludeFileNames(
					fileName, includeFileNames, contentsMap, false));
			includeFileNames.addAll(
				JSPSourceUtil.getJSPReferenceFileNames(
					fileName, includeFileNames, contentsMap,
					".*init(-ext)?\\.(jsp|jspf|tag)"));
		}

		checkedForIncludesFileNames.add(fileName);

		String[] includeFileNamesArray = includeFileNames.toArray(
			new String[0]);

		for (String includeFileName : includeFileNamesArray) {
			if (!checkedForUnusedJSPTerm.contains(includeFileName) &&
				_isJSPTermRequired(
					includeFileName, regex, type, checkedForUnusedJSPTerm,
					checkedForIncludesFileNames, includeFileNames,
					contentsMap)) {

				return true;
			}
		}

		return false;
	}

	private String _removeDuplicateDefineObjects(
		String fileName, String content) {

		Matcher matcher = _defineObjectsPattern.matcher(content);

		while (matcher.find()) {
			if (_isJSPDuplicateDefineObjects(
					fileName, matcher.group(), false)) {

				return StringUtil.replaceFirst(
					content, matcher.group(), StringPool.BLANK,
					matcher.start());
			}
		}

		return content;
	}

	private String _removeUnusedImports(String fileName, String content)
		throws IOException {

		if (fileName.endsWith("init-ext.jsp")) {
			return content;
		}

		Matcher matcher = _compressedJSPImportPattern.matcher(content);

		if (!matcher.find()) {
			return content;
		}

		String imports = matcher.group();

		String newImports = StringUtil.replace(
			imports, new String[] {"<%@\r\n", "<%@\n", " %><%@ "},
			new String[] {"\r\n<%@ ", "\n<%@ ", " %>\n<%@ "});

		List<String> importLines = new ArrayList<>();

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new UnsyncStringReader(newImports));

		String line = null;

		while ((line = unsyncBufferedReader.readLine()) != null) {
			if (line.contains("import=")) {
				importLines.add(line);
			}
		}

		List<String> unneededImports = _getJSPDuplicateImports(
			fileName, content, importLines);

		_addJSPUnusedImports(fileName, importLines, unneededImports);

		for (String unneededImport : unneededImports) {
			newImports = StringUtil.replace(
				newImports, unneededImport, StringPool.BLANK);
		}

		return StringUtil.replaceFirst(content, imports, newImports);
	}

	private String _removeUnusedPortletDefineObjects(
		String fileName, String content) {

		if (!content.contains("<portlet:defineObjects />\n")) {
			return content;
		}

		Set<String> checkedFileNames = new HashSet<>();
		Set<String> includeFileNames = new HashSet<>();

		for (String portletDefineObjectProperty :
				_PORTLET_DEFINE_OBJECTS_PROPERTIES) {

			if (!_hasUnusedPortletDefineObjectsProperty(
					fileName, portletDefineObjectProperty, checkedFileNames,
					includeFileNames)) {

				return content;
			}
		}

		return StringUtil.removeSubstring(content, "<portlet:defineObjects />");
	}

	private String _removeUnusedTaglibs(String fileName, String content)
		throws IOException {

		if (fileName.endsWith("init-ext.jsp")) {
			return content;
		}

		Matcher matcher = _compressedJSPTaglibPattern.matcher(content);

		if (!matcher.find()) {
			return content;
		}

		String taglibs = matcher.group();

		String newTaglibs = StringUtil.replace(
			taglibs, new String[] {"<%@\r\n", "<%@\n", " %><%@ "},
			new String[] {"\r\n<%@ ", "\n<%@ ", " %>\n<%@ "});

		List<String> taglibLines = new ArrayList<>();

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new UnsyncStringReader(newTaglibs));

		String line = null;

		while ((line = unsyncBufferedReader.readLine()) != null) {
			if (line.contains("uri=")) {
				taglibLines.add(line);
			}
		}

		List<String> unneededTaglibs = _getJSPDuplicateTaglibs(
			fileName, content, taglibLines);

		_addJSPUnusedTaglibs(fileName, taglibLines, unneededTaglibs);

		for (String unneededTaglib : unneededTaglibs) {
			newTaglibs = StringUtil.replace(
				newTaglibs, unneededTaglib, StringPool.BLANK);
		}

		return StringUtil.replaceFirst(content, taglibs, newTaglibs);
	}

	private String _removeUnusedVariables(
			String fileName, String absolutePath, String content)
		throws IOException {

		if (absolutePath.contains("/src/main/resources/alloy_mvc/jsp/") &&
			absolutePath.endsWith(".jspf")) {

			return content;
		}

		Set<String> checkedFileNames = new HashSet<>();
		Set<String> includeFileNames = new HashSet<>();

		StringBundler sb = new StringBundler();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			int lineNumber = 0;

			String line = null;

			boolean javaSource = false;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				lineNumber++;

				String trimmedLine = StringUtil.trimLeading(line);

				if (trimmedLine.equals("<%") || trimmedLine.equals("<%!")) {
					javaSource = true;
				}
				else if (trimmedLine.equals("%>")) {
					javaSource = false;
				}

				if (!javaSource ||
					isExcludedPath(
						_UNUSED_VARIABLES_EXCLUDES, absolutePath, lineNumber) ||
					!_hasUnusedVariable(
						fileName, trimmedLine, checkedFileNames,
						includeFileNames)) {

					sb.append(line);
					sb.append("\n");
				}
			}
		}

		content = sb.toString();

		if (content.endsWith("\n")) {
			content = content.substring(0, content.length() - 1);
		}

		return content;
	}

	private static final String[] _PORTLET_DEFINE_OBJECTS_PROPERTIES = {
		"actionRequest", "actionResponse", "eventRequest", "eventResponse",
		"liferayPortletRequest", "liferayPortletResponse", "portletConfig",
		"portletName", "portletPreferences", "portletPreferencesValues",
		"portletSession", "portletSessionScope", "renderResponse",
		"renderRequest", "resourceRequest", "resourceResponse"
	};

	private static final String _UNUSED_VARIABLES_EXCLUDES =
		"jsp.unused.variables.excludes";

	private static final Pattern _compressedJSPImportPattern = Pattern.compile(
		"(<.*\n*(?:page|tag) import=\".*>\n*)+", Pattern.MULTILINE);
	private static final Pattern _compressedJSPTaglibPattern = Pattern.compile(
		"(<.*\n*taglib uri=\".*>\n*)+", Pattern.MULTILINE);
	private static final Pattern _defineObjectsPattern = Pattern.compile(
		"<[\\w-]+:defineObjects />");

}