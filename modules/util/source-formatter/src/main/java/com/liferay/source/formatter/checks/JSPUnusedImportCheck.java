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

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.checks.util.JSPSourceUtil;

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
public class JSPUnusedImportCheck extends JSPUnusedTermCheck {

	@Override
	public void init() throws Exception {
		_contentsMap = getContentsMap();
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (!_contentsMap.isEmpty()) {
			content = _removeUnusedImports(fileName, content);

			content = JSPSourceUtil.compressImportsOrTaglibs(
				fileName, content, "<%@ page import=");
		}

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

			if (hasUnusedJSPTerm(
					fileName, regex, "class", checkedFileNames,
					includeFileNames, _contentsMap)) {

				unneededImports.add(importLine);
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
				continue;
			}

			if ((x < y) && _isJSPDuplicateImport(fileName, importLine, false)) {
				duplicateImports.add(importLine);
			}
		}

		return duplicateImports;
	}

	private boolean _isJSPDuplicateImport(
		String fileName, String importLine, boolean checkFile) {

		String content = _contentsMap.get(fileName);

		if (Validator.isNull(content)) {
			return false;
		}

		int x = importLine.indexOf("page");

		if (x == -1) {
			return false;
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

	private final Pattern _compressedJSPImportPattern = Pattern.compile(
		"(<.*\n*page import=\".*>\n*)+", Pattern.MULTILINE);
	private Map<String, String> _contentsMap;

}