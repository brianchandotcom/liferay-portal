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

package com.liferay.portal.tools;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import java.nio.file.Path;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Hugo Huijser
 * @author Andrea Di Giorgi
 */
public class GitUtil {

	public static List<String> getCurrentBranchDeletedFileNames(
			String baseDirName, String gitWorkingBranchName)
		throws Exception {

		return getDeletedFileNames(
			baseDirName, getCurrentBranchCommitId(gitWorkingBranchName));
	}

	public static String getCurrentBranchFileContent(
			String gitWorkingBranchName, String fileName)
		throws Exception {

		return getFileContent(gitWorkingBranchName, fileName);
	}

	public static List<String> getCurrentBranchFileNames(
			String baseDirName, String gitWorkingBranchName)
		throws Exception {

		return getFileNames(
			baseDirName, getCurrentBranchCommitId(gitWorkingBranchName));
	}

	public static List<String> getLatestAuthorDeletedFileNames(
			String baseDirName)
		throws Exception {

		return getDeletedFileNames(baseDirName, getLatestAuthorCommitId());
	}

	public static String getLatestAuthorFileContent(String fileName)
		throws Exception {

		return getFileContent(getLatestAuthorCommitId(), fileName);
	}

	public static List<String> getLatestAuthorFileNames(String baseDirName)
		throws Exception {

		return getFileNames(baseDirName, getLatestAuthorCommitId());
	}

	public static List<String> getLocalChangesDeletedFileNames(
			String baseDirName)
		throws Exception {

		return getLocalChangesFileNames(baseDirName, "remove");
	}

	public static String getLocalChangesFileContent(String fileName)
		throws Exception {

		return getFileContent("HEAD", fileName);
	}

	public static List<String> getLocalChangesFileNames(String baseDirName)
		throws Exception {

		return getLocalChangesFileNames(baseDirName, "add");
	}

	public static void main(String[] args) throws Exception {
		Map<String, String> arguments = ArgumentsUtil.parseArguments(args);

		String baseDirName = ArgumentsUtil.getString(
			arguments, "git.base.dir", "./");
		String markerFileName = ArgumentsUtil.getString(
			arguments, "git.marker.file", null);
		String type = ArgumentsUtil.getString(
			arguments, "git.type", "current-branch");

		try {
			Iterable<String> fileNames = null;

			if (type.equals("current-branch")) {
				String gitWorkingBranchName = ArgumentsUtil.getString(
					arguments, "git.working.branch.name", "master");

				fileNames = getCurrentBranchFileNames(
					baseDirName, gitWorkingBranchName);
			}
			else if (type.equals("latest-author")) {
				fileNames = getLatestAuthorFileNames(baseDirName);
			}
			else if (type.equals("local-changes")) {
				fileNames = getLocalChangesFileNames(baseDirName);
			}
			else {
				throw new IllegalArgumentException();
			}

			if (Validator.isNotNull(markerFileName)) {
				fileNames = getDirNames(baseDirName, fileNames, markerFileName);
			}

			for (String fileName : fileNames) {
				System.out.println(fileName);
			}
		}
		catch (Exception e) {
			ArgumentsUtil.processMainException(arguments, e);
		}
	}

	protected static String getCurrentBranchCommitId(
			String gitWorkingBranchName)
		throws Exception {

		UnsyncBufferedReader unsyncBufferedReader = getGitCommandReader(
			"git merge-base HEAD " + gitWorkingBranchName);

		return unsyncBufferedReader.readLine();
	}

	protected static List<String> getDeletedFileNames(
			String baseDirName, String commitId)
		throws Exception {

		List<String> fileNames = new ArrayList<>();

		UnsyncBufferedReader unsyncBufferedReader = getGitCommandReader(
			"git rev-parse HEAD");

		String latestCommitId = unsyncBufferedReader.readLine();

		unsyncBufferedReader = getGitCommandReader(
			StringBundler.concat(
				"git diff --diff-filter=RD --name-status ", commitId, " ",
				latestCommitId));

		String line = null;

		int gitLevel = getGitLevel(baseDirName);

		while ((line = unsyncBufferedReader.readLine()) != null) {
			String[] array = line.split("\\s+");

			if (array.length < 2) {
				continue;
			}

			if (StringUtil.count(array[1], CharPool.SLASH) >= gitLevel) {
				fileNames.add(getFileName(array[1], gitLevel));
			}
		}

		return fileNames;
	}

	protected static Set<String> getDirNames(
		String baseDirName, Iterable<String> fileNames, String markerFileName) {

		File baseDir = new File(baseDirName);

		Path baseDirPath = baseDir.toPath();

		Set<String> dirNames = new HashSet<>();

		for (String fileName : fileNames) {
			File file = new File(baseDir, fileName);

			File dir = getRootDir(
				file.getParentFile(), baseDir, markerFileName);

			if (dir != null) {
				String dirName = String.valueOf(
					baseDirPath.relativize(dir.toPath()));

				dirName = StringUtil.replace(
					dirName, File.separatorChar, CharPool.SLASH);

				dirNames.add(dirName);
			}
		}

		return dirNames;
	}

	protected static String getFileContent(String committish, String fileName)
		throws Exception {

		StringBundler sb = new StringBundler();

		String gitCommand = StringBundler.concat(
			"git show ", committish, ":", fileName);

		try (UnsyncBufferedReader unsyncBufferedReader =
				getGitCommandReader(gitCommand)) {

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				sb.append(line);

				sb.append("\n");
			}
		}

		if (sb.length() > 0) {
			sb.setIndex(sb.index() - 1);
		}

		return sb.toString();
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

		UnsyncBufferedReader unsyncBufferedReader = getGitCommandReader(
			"git rev-parse HEAD");

		String latestCommitId = unsyncBufferedReader.readLine();

		unsyncBufferedReader = getGitCommandReader(
			StringBundler.concat(
				"git diff --diff-filter=AM --name-only ", commitId, " ",
				latestCommitId));

		String line = null;

		int gitLevel = getGitLevel(baseDirName);

		while ((line = unsyncBufferedReader.readLine()) != null) {
			if (StringUtil.count(line, CharPool.SLASH) >= gitLevel) {
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
		for (int i = 0; i < ToolsUtil.PORTAL_MAX_DIR_LEVEL; i++) {
			File file = new File(baseDirName + ".git");

			if (file.exists()) {
				return i;
			}

			baseDirName = "../" + baseDirName;
		}

		throw new GitException(
			"Unable to retrieve files because .git directory is missing.");
	}

	protected static String getLatestAuthorCommitId() throws Exception {
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

		return firstDifferentAuthorCommitId;
	}

	protected static List<String> getLocalChangesFileNames(
			String baseDirName, String command)
		throws Exception {

		List<String> localChangesFileNames = new ArrayList<>();

		UnsyncBufferedReader unsyncBufferedReader = getGitCommandReader(
			"git add . --dry-run");

		String line = null;

		int gitLevel = getGitLevel(baseDirName);

		while ((line = unsyncBufferedReader.readLine()) != null) {
			if (StringUtil.count(line, CharPool.SLASH) < gitLevel) {
				continue;
			}

			if (Validator.isNull(command) || !line.startsWith(command + " '")) {
				continue;
			}

			String fileName = line.substring(
				command.length() + 2, line.length() - 1);

			localChangesFileNames.add(getFileName(fileName, gitLevel));
		}

		return localChangesFileNames;
	}

	protected static File getRootDir(
		File dir, File baseDir, String markerFileName) {

		while (true) {
			File markerFile = new File(dir, markerFileName);

			if (markerFile.exists()) {
				return dir;
			}

			dir = dir.getParentFile();

			if ((dir == null) || baseDir.equals(dir)) {
				return null;
			}
		}
	}

}