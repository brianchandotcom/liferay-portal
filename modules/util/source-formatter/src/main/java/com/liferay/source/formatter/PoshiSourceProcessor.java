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
import com.liferay.poshi.core.PoshiContext;
import com.liferay.poshi.core.elements.PoshiElement;
import com.liferay.poshi.core.elements.PoshiNodeFactory;
import com.liferay.poshi.core.script.PoshiScriptParserException;
import com.liferay.poshi.core.util.FileUtil;
import com.liferay.source.formatter.checks.util.SourceUtil;
import com.liferay.source.formatter.util.DebugUtil;

import java.io.File;
import java.io.IOException;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

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

	private synchronized void _populateFunctionAndMacroFiles()
		throws Exception {

		if (_populated) {
			return;
		}

		Files.walkFileTree(
			getPortalDir().toPath(),
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(
						Path dirPath, BasicFileAttributes basicFileAttributes)
					throws IOException {

					if (ArrayUtil.contains(
							_SKIP_DIR_NAMES,
							String.valueOf(dirPath.getFileName()))) {

						return FileVisitResult.SKIP_SUBTREE;
					}

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(
					Path filePath, BasicFileAttributes basicFileAttributes) {

					String absolutePath = SourceUtil.getAbsolutePath(filePath);

					if (absolutePath.endsWith(".function")) {
						PoshiContext.setFunctionFileNames(
							absolutePath.replaceFirst(
								".+/(.+)\\.function", "$1"));
					}
					else if (absolutePath.endsWith(".macro")) {
						PoshiContext.setMacroFileNames(
							absolutePath.replaceFirst(".+/(.+)\\.macro", "$1"));
					}

					return FileVisitResult.CONTINUE;
				}

			});

		_populated = true;
	}

	private static final String[] _INCLUDES = {
		"**/*.function", "**/*.macro", "**/*.testcase"
	};

	private static final String[] _SKIP_DIR_NAMES = {
		".git", ".gradle", ".idea", ".m2", ".releng", ".settings", "bin",
		"build", "classes", "node_modules", "node_modules_cache", "poshi",
		"sdk", "third-party"
	};

	private static boolean _populated;

}