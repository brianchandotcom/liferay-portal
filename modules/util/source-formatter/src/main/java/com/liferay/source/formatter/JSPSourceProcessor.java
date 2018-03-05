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

package com.liferay.source.formatter;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.checks.util.JSPSourceUtil;
import com.liferay.source.formatter.checkstyle.util.CheckstyleUtil;
import com.liferay.source.formatter.util.CheckType;
import com.liferay.source.formatter.util.DebugUtil;
import com.liferay.source.formatter.util.FileUtil;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import com.puppycrawl.tools.checkstyle.api.Configuration;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Hugo Huijser
 */
public class JSPSourceProcessor extends BaseSourceProcessor {

	@Override
	protected List<String> doGetFileNames() throws Exception {
		String[] excludes = {"**/null.jsp", "**/tools/**"};

		List<String> fileNames = getFileNames(excludes, getIncludes());

		if (fileNames.isEmpty() ||
			(!sourceFormatterArgs.isFormatCurrentBranch() &&
			 !sourceFormatterArgs.isFormatLatestAuthor() &&
			 !sourceFormatterArgs.isFormatLocalChanges())) {

			return fileNames;
		}

		List<String> allJSPFileNames = getFileNames(
			excludes, getIncludes(), true);

		return JSPSourceUtil.addIncludedAndReferencedFileNames(
			fileNames, new HashSet<String>(),
			JSPSourceUtil.getContentsMap(allJSPFileNames));
	}

	@Override
	protected String[] doGetIncludes() {
		return _INCLUDES;
	}

	@Override
	protected File format(
			File file, String fileName, String absolutePath, String content)
		throws Exception {

		// When executing 'format-source-current-branch',
		// 'format-source-latest-author', or 'format-source-local-changes', we
		// add included and referenced file names in order to detect unused
		// imports or variable names. As a result, file names that are excluded
		// via source-formatter.properties#source.formatter.excludes are also
		// added to the list. Here we make sure we do not format files that
		// should be excluded.

		if (sourceFormatterArgs.isFormatCurrentBranch() ||
			sourceFormatterArgs.isFormatLatestAuthor() ||
			sourceFormatterArgs.isFormatLocalChanges()) {

			List<String> fileNames = SourceFormatterUtil.filterFileNames(
				Arrays.asList(fileName), new String[0], new String[] {"*.*"},
				getSourceFormatterExcludes(), false);

			if (fileNames.isEmpty()) {
				return file;
			}
		}

		file = super.format(file, fileName, absolutePath, content);

		_processCheckstyle(absolutePath, content);

		return file;
	}

	@Override
	protected void postFormat() throws Exception {
		_processCheckstyle(
			_ungeneratedFiles.toArray(new File[_ungeneratedFiles.size()]));

		_ungeneratedFiles.clear();

		for (SourceFormatterMessage sourceFormatterMessage :
				_sourceFormatterMessages) {

			String fileName = sourceFormatterMessage.getFileName();

			processMessage(fileName, sourceFormatterMessage);

			printError(fileName, sourceFormatterMessage.toString());
		}
	}

	private synchronized void _processCheckstyle(
			String absolutePath, String content)
		throws Exception {

		if (!absolutePath.contains(CheckstyleUtil.ALLOY_MVC_SRC_DIR)) {
			return;
		}

		if (!absolutePath.endsWith(".jspf")) {
			return;
		}

		if (!content.matches("(?s)<%--.*--%>(\\s*<%@[^\\n]*)*\\s*<%!\\s.*")) {
			return;
		}

		if (StringUtil.count(content, "<%!") != 1) {
			return;
		}

		if (!content.endsWith("\n%>")) {
			return;
		}

		File tempFile = new File(CheckstyleUtil.getJavaFileName(absolutePath));

		String tempContent = StringUtil.replace(
			content, new String[] {"<%--", "--%>", "<%@", "<%!"},
			new String[] {"//<%--", "//--%>", "//<%@", "//<%!"});

		tempContent = StringUtil.replaceLast(tempContent, "\n%>", "");

		FileUtil.write(tempFile, tempContent);

		_ungeneratedFiles.add(tempFile);

		if (_ungeneratedFiles.size() == CheckstyleUtil.BATCH_SIZE) {
			_processCheckstyle(
				_ungeneratedFiles.toArray(new File[_ungeneratedFiles.size()]));

			_ungeneratedFiles.clear();
		}
	}

	private void _processCheckstyle(File[] files) throws Exception {
		if (ArrayUtil.isEmpty(files)) {
			return;
		}

		if (_configuration == null) {
			Configuration configuration = CheckstyleUtil.getConfiguration(
				"checkstyle.xml");

			configuration = CheckstyleUtil.addAttribute(
				configuration, "maxLineLength",
				String.valueOf(sourceFormatterArgs.getMaxLineLength()),
				"com.liferay.source.formatter.checkstyle.checks.Append");
			configuration = CheckstyleUtil.addAttribute(
				configuration, "maxLineLength",
				String.valueOf(sourceFormatterArgs.getMaxLineLength()),
				"com.liferay.source.formatter.checkstyle.checks.Concat");
			configuration = CheckstyleUtil.addAttribute(
				configuration, "maxLineLength",
				String.valueOf(sourceFormatterArgs.getMaxLineLength()),
				"com.liferay.source.formatter.checkstyle.checks.PlusStatement");
			configuration = CheckstyleUtil.addAttribute(
				configuration, "showDebugInformation",
				String.valueOf(sourceFormatterArgs.isShowDebugInformation()),
				"com.liferay.*");

			_configuration = configuration;

			if (sourceFormatterArgs.isShowDebugInformation()) {
				DebugUtil.addCheckNames(
					CheckType.CHECKSTYLE,
					CheckstyleUtil.getCheckNames(configuration));
			}
		}

		Set<SourceFormatterMessage> sourceFormatterMessages =
			CheckstyleUtil.getSourceFormatterMessages(
				_configuration, files, getSourceFormatterSuppressions(),
				sourceFormatterArgs);

		_sourceFormatterMessages.addAll(sourceFormatterMessages);
	}

	private static final String[] _INCLUDES =
		{"**/*.jsp", "**/*.jspf", "**/*.tag", "**/*.tpl", "**/*.vm"};

	private Configuration _configuration;
	private final Set<SourceFormatterMessage> _sourceFormatterMessages =
		new HashSet<>();
	private final List<File> _ungeneratedFiles = new ArrayList<>();

}