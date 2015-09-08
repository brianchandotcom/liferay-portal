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

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.BaseSourceProcessor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hugo Huijser
 */
public class GitUtil {

	public static List<String> getCurrentBranchFileNames(String baseDirName)
		throws Exception {

		int gitLevel = getGitLevel(baseDirName);

		if (gitLevel == -1) {
			return new ArrayList<>();
		}

		UnsyncBufferedReader unsyncBufferedReader = getGitCommandReader(
			"git log " + _WORKING_BRANCH_NAME + "..head");

		if (unsyncBufferedReader == null) {
			return new ArrayList<>();
		}

		String line = null;

		List<String> latestBranchCommitIds = new ArrayList<>();

		while ((line = unsyncBufferedReader.readLine()) != null) {
			if (line.startsWith("commit ")) {
				latestBranchCommitIds.add(line.substring(7));
			}
		}

		return getFileNames(latestBranchCommitIds, gitLevel);
	}

	public static List<String> getLatestAuthorFileNames(String baseDirName)
		throws Exception {

		int gitLevel = getGitLevel(baseDirName);

		if (gitLevel == -1) {
			return new ArrayList<>();
		}

		UnsyncBufferedReader unsyncBufferedReader = getGitCommandReader(
			"git log");

		if (unsyncBufferedReader == null) {
			return new ArrayList<>();
		}

		String line = null;

		String commitId = null;
		String latestAuthor = null;
		List<String> latestAuthorCommitIds = new ArrayList<>();

		while ((line = unsyncBufferedReader.readLine()) != null) {
			if (line.startsWith("commit ")) {
				commitId = line.substring(7);
			}
			else if (line.startsWith("Author: ")) {
				if (latestAuthor == null) {
					int x = line.lastIndexOf(CharPool.LESS_THAN);
					int y = line.lastIndexOf(CharPool.GREATER_THAN);

					latestAuthor = line.substring(x + 1, y);

					latestAuthorCommitIds.add(commitId);
				}
				else if (line.endsWith("<" + latestAuthor + ">")) {
					latestAuthorCommitIds.add(commitId);
				}
				else {
					break;
				}
			}
		}

		return getFileNames(latestAuthorCommitIds, gitLevel);
	}

	public static List<String> getLocalChangesFileNames(String baseDirName)
		throws Exception {

		List<String> localChangesFileNames = new ArrayList<>();

		int gitLevel = getGitLevel(baseDirName);

		if (gitLevel == -1) {
			return localChangesFileNames;
		}

		UnsyncBufferedReader unsyncBufferedReader = getGitCommandReader(
			"git add . --dry-run");

		if (unsyncBufferedReader == null) {
			return localChangesFileNames;
		}

		String line = null;

		while ((line = unsyncBufferedReader.readLine()) != null) {
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

	protected static List<String> getFileNames(
			List<String> commitIds, int gitLevel)
		throws Exception {

		List<String> fileNames = new ArrayList<>();

		String line = null;

		for (String commitId : commitIds) {
			UnsyncBufferedReader unsyncBufferedReader = getGitCommandReader(
				"git diff-tree --no-commit-id --name-only -r " + commitId);

			while ((line = unsyncBufferedReader.readLine()) != null) {
				if (StringUtil.count(line, StringPool.SLASH) < gitLevel) {
					continue;
				}

				String fileName = getFileName(line, gitLevel);

				if (!fileNames.contains(fileName)) {
					fileNames.add(fileName);
				}
			}
		}

		return fileNames;
	}

	protected static UnsyncBufferedReader getGitCommandReader(String gitCommand)
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
					"Add Git to your PATH system variable first.");

				return null;
			}

			throw ioe;
		}

		return new UnsyncBufferedReader(
			new InputStreamReader(process.getInputStream()));
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
			"Unable to retrieve files because .git directory is missing.");

		return -1;
	}

	private static final String _WORKING_BRANCH_NAME = "master";

}