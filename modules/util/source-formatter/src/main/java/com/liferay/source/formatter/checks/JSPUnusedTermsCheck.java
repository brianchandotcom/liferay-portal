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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.checks.util.JSPSourceUtil;
import com.liferay.source.formatter.util.SourceFormatterUtil;

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
public class JSPUnusedTermsCheck extends BaseFileCheck {

	@Override
	public void init() throws Exception {
		_contentsMap = _getContentsMap();
	}

	@Override
	public void setAllFileNames(List<String> allFileNames) {
		_allFileNames = allFileNames;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		// When running tests, the contentsMap is empty, because the file
		// extension of the test files is *.testjsp

		if (_contentsMap.isEmpty()) {
			_contentsMap.put(fileName, content);
		}

		content = _removeUnusedImports(fileName, content);

		content = JSPSourceUtil.compressImportsOrTaglibs(
			fileName, content, "<%@ page import=");
		content = JSPSourceUtil.compressImportsOrTaglibs(
			fileName, content, "<%@ tag import=");

		if (isPortalSource() || isSubrepository()) {
			content = _removeUnusedPortletDefineObjects(fileName, content);
			content = _removeUnusedTaglibs(fileName, content);
			content = _removeUnusedVariables(fileName, absolutePath, content);
		}

		_contentsMap.put(fileName, content);

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

			String regex = "[^A-Za-z0-9_\"]" + className + "[^A-Za-z0-9_\"]";

			if (_hasUnusedJSPTerm(
					fileName, regex, "class", checkedFileNames,
					includeFileNames, _contentsMap)) {

				unneededImports.add(importLine);
			}
		}
	}

	private Map<String, String> _getContentsMap() throws Exception {
		String[] excludes = {"**/null.jsp", "**/tools/**"};

		List<String> allJSPFileNames = SourceFormatterUtil.filterFileNames(
			_allFileNames, excludes,
			new String[] {"**/*.jsp", "**/*.jspf", "**/*.tag"},
			getSourceFormatterExcludes(), true);

		return JSPSourceUtil.getContentsMap(allJSPFileNames);
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

		StringBundler sb = new StringBundler(4);

		sb.append("((/)|(\\*)|(\\+(\\+)?)|(-(-)?)|\\(|=)?( )?");
		sb.append(portletDefineObjectProperty);
		sb.append("( )?(\\.|(((\\+)|(-)|(\\*)|(/)|(%)|(\\|)|(&)|(\\^))?(=))");
		sb.append("|(\\+(\\+)?)|(-(-)?)|(\\)))?");

		return _hasUnusedJSPTerm(
			fileName, sb.toString(), "portletDefineObjectProperty",
			checkedFileNames, includeFileNames, _contentsMap);
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

		StringBundler sb = new StringBundler(4);

		sb.append("((/)|(\\*)|(\\+(\\+)?)|(-(-)?)|\\(|=)?( )?");
		sb.append(variableName);
		sb.append("( )?(\\.|(((\\+)|(-)|(\\*)|(/)|(%)|(\\|)|(&)|(\\^))?(=))");
		sb.append("|(\\+(\\+)?)|(-(-)?)|(\\)))?");

		return _hasUnusedJSPTerm(
			fileName, sb.toString(), "variable", checkedFileNames,
			includeFileNames, _contentsMap);
	}

	private boolean _isJSPDuplicateImport(
		String fileName, String importLine, boolean checkFile) {

		String content = _contentsMap.get(fileName);

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
			fileName, includeFileName, _contentsMap);

		return _isJSPDuplicateImport(includeFileName, importLine, true);
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

		Pattern pattern = Pattern.compile(regex);

		Matcher matcher = pattern.matcher(content);

		if (matcher.find() &&
			(!type.equals("variable") || (checkedForUnusedJSPTerm.size() > 1) ||
			 matcher.find())) {

			return true;
		}

		if (!checkedForIncludesFileNames.contains(fileName)) {
			includeFileNames.addAll(
				JSPSourceUtil.getJSPIncludeFileNames(
					fileName, includeFileNames, contentsMap));
			includeFileNames.addAll(
				JSPSourceUtil.getJSPReferenceFileNames(
					fileName, includeFileNames, contentsMap));
		}

		checkedForIncludesFileNames.add(fileName);

		String[] includeFileNamesArray = includeFileNames.toArray(
			new String[includeFileNames.size()]);

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

	private String _removeUnusedImports(String fileName, String content)
		throws Exception {

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

	private String _removeUnusedTaglibs(String fileName, String content) {
		Set<String> checkedFileNames = new HashSet<>();
		Set<String> includeFileNames = new HashSet<>();

		return _removeUnusedTaglibs(
			fileName, content, checkedFileNames, includeFileNames);
	}

	private String _removeUnusedTaglibs(
		String fileName, String content, Set<String> checkedFileNames,
		Set<String> includeFileNames) {

		Matcher matcher = _taglibURIPattern.matcher(content);

		while (matcher.find()) {
			String regex = StringBundler.concat(
				StringPool.LESS_THAN, matcher.group(1), StringPool.COLON,
				StringPool.PIPE, "\\$\\{" + matcher.group(1), StringPool.COLON);

			if (_hasUnusedJSPTerm(
					fileName, regex, "taglib", checkedFileNames,
					includeFileNames, _contentsMap)) {

				return StringUtil.removeSubstring(content, matcher.group());
			}
		}

		return content;
	}

	private String _removeUnusedVariables(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (absolutePath.contains("/src/main/resources/alloy_mvc/jsp/") &&
			absolutePath.endsWith(".jspf")) {

			return content;
		}

		Set<String> checkedFileNames = new HashSet<>();
		Set<String> includeFileNames = new HashSet<>();

		StringBundler sb = new StringBundler();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			int lineCount = 0;

			String line = null;

			boolean javaSource = false;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				lineCount++;

				String trimmedLine = StringUtil.trimLeading(line);

				if (trimmedLine.equals("<%") || trimmedLine.equals("<%!")) {
					javaSource = true;
				}
				else if (trimmedLine.equals("%>")) {
					javaSource = false;
				}

				if (!javaSource ||
					isExcludedPath(
						_UNUSED_VARIABLES_EXCLUDES, absolutePath, lineCount) ||
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

	private List<String> _allFileNames;
	private final Pattern _compressedJSPImportPattern = Pattern.compile(
		"(<.*\n*(?:page|tag) import=\".*>\n*)+", Pattern.MULTILINE);
	private Map<String, String> _contentsMap;
	private final Pattern _taglibURIPattern = Pattern.compile(
		"<%@\\s+taglib uri=.* prefix=\"(.*?)\" %>");

}