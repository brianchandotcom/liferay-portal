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

package com.liferay.source.formatter.processor;

import com.liferay.source.formatter.SourceFormatterArgs;

import java.io.IOException;

import java.util.Collections;
import java.util.List;

/**
 * @author Qi Zhang
 */
public class LibrarySourceProcessor extends BaseSourceProcessor {

	@Override
	protected List<String> doGetFileNames() throws IOException {
		List<String> fileNames = getFileNames(new String[0], getIncludes());

		SourceFormatterArgs sourceFormatterArgs = getSourceFormatterArgs();

		if (fileNames.isEmpty() ||
			((sourceFormatterArgs.getCommitCount() == 0) &&
			 !sourceFormatterArgs.isFormatCurrentBranch() &&
			 !sourceFormatterArgs.isFormatLatestAuthor() &&
			 !sourceFormatterArgs.isFormatLocalChanges())) {

			return Collections.emptyList();
		}

		return fileNames;
	}

	@Override
	protected String[] doGetIncludes() {
		return _INCLUDES;
	}

	private static final String[] _INCLUDES = {
		"**/build.gradle", "**/build-buildscript.gradle",
		"**/dependencies.properties", "**/ivy.xml", "**/package.json",
		"**/pakcage-lock.json", "**/pom.xml", "**/releng.gradle",
		"**/releng-pom-compile-only-dependencies.properties",
		"**/settings.gradle"
	};

}