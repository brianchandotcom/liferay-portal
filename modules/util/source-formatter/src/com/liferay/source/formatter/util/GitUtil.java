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

package com.liferay.source.formatter.util;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.BaseSourceProcessor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hugo Huijser
 */
public class GitUtil {

	public static List<String> getLocalChangesFileNames(String baseDirName)
		throws Exception {

		List<String> localChangesFileNames = new ArrayList<>();

		int gitLevel = getGitLevel(baseDirName);

		if (gitLevel == -1) {
			return localChangesFileNames;
		}

		InputStream gitCommandInputStream = getGitCommandInputStream(
			"git add . --dry-run");

		if (gitCommandInputStream == null) {
			return localChangesFileNames;
		}

		String output = StringUtil.read(gitCommandInputStream);

		for (String line : StringUtil.splitLines(output)) {
			if (!line.startsWith("add '") ||
				(StringUtil.count(line, StringPool.SLASH) < gitLevel)) {

				continue;
			}

			String fileName = getFileName(
				line.substring(5, line.length() - 1), gitLevel);

			localChangesFileNames.add(fileName);
		}

		return localChangesFileNames;
	}

	protected static String getFileName(String fileName, int gitLevel) {
		for (int i = 0; i < gitLevel; i++) {
			int x = fileName.indexOf(StringPool.SLASH);

			fileName = fileName.substring(x + 1);
		}

		return fileName;
	}

	protected static InputStream getGitCommandInputStream(String gitCommand)
		throws Exception {

		Runtime runtime = Runtime.getRuntime();

		Process process = null;

		try {
			process = runtime.exec(gitCommand);
		}
		catch (IOException ioe) {
			String errorMessage = ioe.getMessage();

			if (errorMessage.contains("Cannot run program")) {
				System.out.println(
					"Add Git to your PATH system variable before executing " +
						"'ant format-source-local-changes'.");

				return null;
			}

			throw ioe;
		}

		return process.getInputStream();
	}

	protected static int getGitLevel(String baseDirName) {
		for (int i = 0; i < BaseSourceProcessor.PORTAL_MAX_DIR_LEVEL; i++) {
			File file = new File(baseDirName + ".git");

			if (file.exists()) {
				return i;
			}

			baseDirName = "../" + baseDirName;
		}

		System.out.println(
			"Cannot retrieve files because .git directory is missing.");

		return -1;
	}

}