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
import com.liferay.source.formatter.GitException;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hugo Huijser
 */
public class GitUtil {

	public static List<String> getCurrentBranchFileNames(String baseDirName)
		throws Exception {

		UnsyncBufferedReader unsyncBufferedReader = getGitCommandReader(
			"git merge-base HEAD " + _WORKING_BRANCH_NAME);

		String mergeBaseCommitId = unsyncBufferedReader.readLine();

		return getFileNames(baseDirName, mergeBaseCommitId);
	}

	public static List<String> getLatestAuthorFileNames(String baseDirName)
		throws Exception {

		UnsyncBufferedReader unsyncBufferedReader = getGitCommandReader(
			"git log");

		String line = null;

		String firstDifferentAuthorCommitId = null;
		String latestAuthor = null;

		while ((line = unsyncBufferedReader.readLine()) != null) {
			if (line.startsWith("commit ")) {
				firstDifferentAuthorCommitId = line.substring(7);
			}
			else if (line.startsWith("Author: ")) {
				if (latestAuthor == null) {
					int x = line.lastIndexOf(CharPool.LESS_THAN);
					int y = line.lastIndexOf(CharPool.GREATER_THAN);

					latestAuthor = line.substring(x + 1, y);
				}
				else if (!line.endsWith("<" + latestAuthor + ">")) {
					break;
				}
			}
		}

		return getFileNames(baseDirName, firstDifferentAuthorCommitId);
	}

	public static List<String> getLocalChangesFileNames(String baseDirName)
		throws Exception {

		List<String> localChangesFileNames = new ArrayList<>();

		UnsyncBufferedReader unsyncBufferedReader = getGitCommandReader(
			"git add . --dry-run");

		String line = null;

		int gitLevel = getGitLevel(baseDirName);

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
			String baseDirName, String commitId)
		throws Exception {

		List<String> fileNames = new ArrayList<>();

		String line = null;

		int gitLevel = getGitLevel(baseDirName);

		UnsyncBufferedReader unsyncBufferedReader = getGitCommandReader(
			"git diff --diff-filter=AM --name-only " + commitId);

		while ((line = unsyncBufferedReader.readLine()) != null) {
			if (StringUtil.count(line, StringPool.SLASH) >= gitLevel) {
				fileNames.add(getFileName(line, gitLevel));
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
				throw new GitException(
					"Add Git to your PATH system variable first.");
			}

			throw ioe;
		}

		return new UnsyncBufferedReader(
			new InputStreamReader(process.getInputStream()));
	}

	protected static int getGitLevel(String baseDirName) throws GitException {
		for (int i = 0; i < BaseSourceProcessor.PORTAL_MAX_DIR_LEVEL; i++) {
			File file = new File(baseDirName + ".git");

			if (file.exists()) {
				return i;
			}

			baseDirName = "../" + baseDirName;
		}

		throw new GitException(
			"Unable to retrieve files because .git directory is missing.");
	}

	private static final String _WORKING_BRANCH_NAME = "master";

}