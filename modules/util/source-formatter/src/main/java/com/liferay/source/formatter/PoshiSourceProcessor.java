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

import com.liferay.poshi.core.PoshiContext;
import com.liferay.poshi.core.elements.PoshiElement;
import com.liferay.poshi.core.elements.PoshiNodeFactory;
import com.liferay.poshi.core.script.PoshiScriptParserException;
import com.liferay.poshi.core.util.FileUtil;
import com.liferay.source.formatter.checks.util.SourceUtil;
import com.liferay.source.formatter.util.DebugUtil;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import java.io.File;
import java.io.IOException;

import java.util.List;
import java.util.Set;

/**
 * @author Hugo Huijser
 */
public class PoshiSourceProcessor extends BaseSourceProcessor {

	@Override
	protected List<String> doGetFileNames() throws IOException {
		return getFileNames(new String[0], getIncludes());
	}

	@Override
	protected String[] doGetIncludes() {
		return _INCLUDES;
	}

	@Override
	protected File format(
			File file, String fileName, String absolutePath, String content)
		throws Exception {

		if (SourceUtil.isXML(content)) {
			return file;
		}

		return super.format(file, fileName, absolutePath, content);
	}

	@Override
	protected String parse(
			File file, String fileName, String content,
			Set<String> modifiedMessages)
		throws Exception {

		_populateFunctionAndMacroFiles();

		PoshiElement poshiElement =
			(PoshiElement)PoshiNodeFactory.newPoshiNodeFromFile(
				FileUtil.getURL(file));

		PoshiScriptParserException.throwExceptions(
			SourceUtil.getAbsolutePath(file));

		String newContent = poshiElement.toPoshiScript();

		if (!content.equals(newContent)) {
			modifiedMessages.add(file.toString() + " (PoshiParser)");

			SourceFormatterArgs sourceFormatterArgs = getSourceFormatterArgs();

			if (sourceFormatterArgs.isShowDebugInformation()) {
				DebugUtil.printContentModifications(
					"PoshiParser", fileName, content, newContent);
			}
		}

		return newContent;
	}

	private synchronized void _populateFunctionAndMacroFiles() {
		if (_populated) {
			return;
		}

		List<String> functionAndMacroFileNames =
			SourceFormatterUtil.filterFileNames(
				getAllFileNames(), new String[0],
				new String[] {"**/*.function", "**/*.macro"},
				getSourceFormatterExcludes(), true);

		for (String fileName : functionAndMacroFileNames) {
			if (fileName.endsWith(".function")) {
				PoshiContext.setFunctionFileNames(
					fileName.replaceFirst(".+/(.+)\\.function", "$1"));
			}
			else if (fileName.endsWith(".macro")) {
				PoshiContext.setMacroFileNames(
					fileName.replaceFirst(".+/(.+)\\.macro", "$1"));
			}
		}

		_populated = true;
	}

	private static final String[] _INCLUDES = {
		"**/*.function", "**/*.macro", "**/*.testcase"
	};

	private static boolean _populated;

}