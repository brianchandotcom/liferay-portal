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

package com.liferay.gradle.plugins.lang.merger.tasks;

import com.liferay.gradle.util.GradleUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import java.util.Properties;
import java.util.Set;

import org.gradle.api.tasks.Input;

/**
 * @author Andrea Di Giorgi
 */
public class MergePropertiesTask extends BaseMergeTask {

	@Input
	public String getCharsetName() {
		return GradleUtil.toString(_charsetName);
	}

	@Override
	public String getPattern() {
		return _PATTERN;
	}

	public void setCharsetName(Object charsetName) {
		_charsetName = charsetName;
	}

	@Override
	protected void merge(Set<File> sourceFiles, File destinationFile)
		throws IOException {

		Charset charset = Charset.forName(getCharsetName());

		Properties mergedProperties = new Properties();

		for (File sourceFile : sourceFiles) {
			if (!sourceFile.exists()) {
				continue;
			}

			Properties sourceProperties = new Properties();

			try (BufferedReader bufferedReader = Files.newBufferedReader(
					sourceFile.toPath(), charset)) {

				sourceProperties.load(bufferedReader);
			}

			mergedProperties.putAll(sourceProperties);
		}

		try (BufferedWriter bufferedWriter = Files.newBufferedWriter(
				destinationFile.toPath(), charset)) {

			mergedProperties.store(bufferedWriter, null);
		}
	}

	private static final String _PATTERN = "*.properties";

	private Object _charsetName = StandardCharsets.UTF_8.name();

}