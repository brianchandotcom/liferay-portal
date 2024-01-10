/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.File;
import java.io.IOException;

import java.net.URL;

import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Hashimoto
 */
public class MergePortalSubrepositoryUtil {

	public static void mergePortalSubrepository(
			URL portalGitHubURL, String portalUpstreamBranchName,
			URL subrepositoryGitHubURL, String subrepositoryUpstreamBranchName,
			String targetGitRepoCommitSHA)
		throws IOException {

		GitWorkingDirectory portalGitWorkingDirectory = _getGitWorkingDirectory(
			portalGitHubURL, portalUpstreamBranchName);

		GitWorkingDirectory subrepositoryGitWorkingDirectory =
			_getGitWorkingDirectory(
				subrepositoryGitHubURL, subrepositoryUpstreamBranchName);

		String currentGitRepoCommitSHA = _getCurrentGitRepoCommitSHA(
			portalGitHubURL, portalGitWorkingDirectory,
			subrepositoryGitWorkingDirectory, targetGitRepoCommitSHA);

		LocalGitBranch portalCurrentLocalGitBranch =
			portalGitWorkingDirectory.getCurrentLocalGitBranch();

		String portalCurrentBranchSHA = portalCurrentLocalGitBranch.getSHA();

		_fetchSubrepositoryBranchToPortalRepository(
			portalCurrentBranchSHA, portalGitWorkingDirectory,
			subrepositoryGitWorkingDirectory);

		_checkMergeCommitSHA(
			currentGitRepoCommitSHA, targetGitRepoCommitSHA,
			portalGitWorkingDirectory, portalGitHubURL,
			subrepositoryGitWorkingDirectory);

		_createPatch(
			portalGitWorkingDirectory, subrepositoryGitWorkingDirectory,
			currentGitRepoCommitSHA, targetGitRepoCommitSHA);

		_applyPatch(
			portalGitWorkingDirectory, subrepositoryGitWorkingDirectory);

		_commitGitRepoUpdates(
			portalGitWorkingDirectory, subrepositoryGitWorkingDirectory,
			portalCurrentBranchSHA, targetGitRepoCommitSHA);

		_pushUpdatesToRemoteBranch(
			portalGitHubURL, portalGitWorkingDirectory,
			portalUpstreamBranchName);
	}

	private static void _applyPatch(
		GitWorkingDirectory portalGitWorkingDirectory,
		GitWorkingDirectory subrepositoryGitWorkingDirectory) {

		GitUtil.ExecutionResult executionResult =
			portalGitWorkingDirectory.executeBashCommands(
				3, GitUtil.MILLIS_RETRY_DELAY, 1000 * 60 * 10,
				"(git am --abort || true)",
				JenkinsResultsParserUtil.combine(
					"git am --directory=\"",
					_getSubrepositoryModuleDirPath(
						portalGitWorkingDirectory,
						subrepositoryGitWorkingDirectory),
					"\" --keep-cr --whitespace=nowarn *.patch"));

		if (executionResult.getExitValue() != 0) {
			throw new RuntimeException(
				"Unable to apply patch \n" +
					executionResult.getStandardError());
		}

		System.out.println(executionResult.getStandardOut());
	}

	private static void _checkMergeCommitSHA(
		String currentGitRepoCommitSHA, String targetGitRepoCommitSHA,
		GitWorkingDirectory portalGitWorkingDirectory, URL portalGitHubURL,
		GitWorkingDirectory subrepositoryGitWorkingDirectory) {

		if (Objects.equals(
				subrepositoryGitWorkingDirectory.getMergeBaseCommitSHA(
					currentGitRepoCommitSHA, targetGitRepoCommitSHA),
				currentGitRepoCommitSHA)) {

			return;
		}

		StringBuilder sb = new StringBuilder();

		sb.append(_getGitHubURLString(portalGitHubURL, targetGitRepoCommitSHA));
		sb.append(" is incompatible with SHA found in '");

		sb.append(
			JenkinsResultsParserUtil.getPathRelativeTo(
				_getGitRepoFile(
					portalGitWorkingDirectory,
					subrepositoryGitWorkingDirectory),
				portalGitWorkingDirectory.getWorkingDirectory()));
		sb.append("'");

		System.out.println(sb.toString());

		throw new RuntimeException(sb.toString());
	}

	private static void _commitGitRepoUpdates(
		GitWorkingDirectory portalGitWorkingDirectory,
		GitWorkingDirectory subrepositoryGitWorkingDirectory,
		String portalParentBranchSHA, String targetGitRepoCommitSHA) {

		File gitRepoFile = _getGitRepoFile(
			portalGitWorkingDirectory, subrepositoryGitWorkingDirectory);

		String gitRepoFilePath = JenkinsResultsParserUtil.getPathRelativeTo(
			gitRepoFile, portalGitWorkingDirectory.getWorkingDirectory());

		try {
			String gitRepoFileContent = JenkinsResultsParserUtil.read(
				gitRepoFile);

			gitRepoFileContent = gitRepoFileContent.replaceAll(
				"commit = [0-9a-f]{40}", "commit = " + targetGitRepoCommitSHA);
			gitRepoFileContent = gitRepoFileContent.replaceAll(
				"parent = [0-9a-f]{40}", "parent = " + portalParentBranchSHA);

			JenkinsResultsParserUtil.write(gitRepoFile, gitRepoFileContent);

			portalGitWorkingDirectory.stageFileInCurrentLocalGitBranch(
				JenkinsResultsParserUtil.getPathRelativeTo(
					gitRepoFile,
					portalGitWorkingDirectory.getWorkingDirectory()));

			portalGitWorkingDirectory.commitFileToCurrentBranch(
				gitRepoFilePath,
				"subrepo:ignore Update '" + gitRepoFilePath + "'.");
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Failed to update " + gitRepoFilePath, ioException);
		}
	}

	private static void _createPatch(
		GitWorkingDirectory portalGitWorkingDirectory,
		GitWorkingDirectory subrepositoryGitWorkingDirectory, String gitRepoSHA,
		String targetGitRepoCommitSHA) {

		Set<File> modifiedFilesInCommitRange =
			subrepositoryGitWorkingDirectory.getModifiedFilesInCommitRange(
				gitRepoSHA, targetGitRepoCommitSHA);

		StringBuilder sb = new StringBuilder();

		sb.append("git format-patch --root \"");
		sb.append(gitRepoSHA);
		sb.append("..");
		sb.append(targetGitRepoCommitSHA);
		sb.append("\" -- ");

		boolean foundModifiedFiles = false;

		for (File modifiedFile : modifiedFilesInCommitRange) {
			String modifiedFilePath = JenkinsResultsParserUtil.getCanonicalPath(
				modifiedFile);

			if (modifiedFilePath.endsWith("gradle.properties") ||
				modifiedFilePath.endsWith("gradlew") ||
				modifiedFilePath.endsWith("gradlew.bat") ||
				modifiedFilePath.contains("gradle/")) {

				continue;
			}

			sb.append(" ");
			sb.append(
				JenkinsResultsParserUtil.getPathRelativeTo(
					modifiedFile,
					subrepositoryGitWorkingDirectory.getWorkingDirectory()));

			foundModifiedFiles = true;
		}

		if (!foundModifiedFiles) {
			throw new RuntimeException("No found modified files");
		}

		GitUtil.ExecutionResult executionResult =
			portalGitWorkingDirectory.executeBashCommands(
				3, GitUtil.MILLIS_RETRY_DELAY, 1000 * 60 * 10, "rm -f *.patch",
				sb.toString());

		if (executionResult.getExitValue() != 0) {
			throw new RuntimeException(
				"Failed to create a patch \n" +
					executionResult.getStandardError());
		}

		System.out.println(executionResult.getStandardOut());
	}

	private static void _fetchSubrepositoryBranchToPortalRepository(
		String portalCurrentBranchSHA,
		GitWorkingDirectory portalGitWorkingDirectory,
		GitWorkingDirectory subrepositoryGitWorkingDirectory) {

		portalGitWorkingDirectory.fetch(
			null, subrepositoryGitWorkingDirectory.getCurrentLocalGitBranch());

		portalGitWorkingDirectory.reset("--hard " + portalCurrentBranchSHA);
	}

	private static String _getCurrentGitRepoCommitSHA(
		URL portalGitHubURL, GitWorkingDirectory portalGitWorkingDirectory,
		GitWorkingDirectory subrepositoryGitWorkingDirectory,
		String targetGitRepoCommitSHA) {

		File gitRepoFile = _getGitRepoFile(
			portalGitWorkingDirectory, subrepositoryGitWorkingDirectory);

		Properties gitRepoProperties = JenkinsResultsParserUtil.getProperties(
			gitRepoFile);

		String currentGitRepoCommitSHA = gitRepoProperties.getProperty(
			"commit");

		if (Objects.equals(currentGitRepoCommitSHA, targetGitRepoCommitSHA)) {
			StringBuilder sb = new StringBuilder();

			sb.append(
				_getGitHubURLString(portalGitHubURL, targetGitRepoCommitSHA));
			sb.append(" already found in '");
			sb.append(
				JenkinsResultsParserUtil.getPathRelativeTo(
					gitRepoFile,
					portalGitWorkingDirectory.getWorkingDirectory()));
			sb.append("'");

			System.out.println(sb.toString());

			throw new RuntimeException(sb.toString());
		}

		return currentGitRepoCommitSHA;
	}

	private static String _getGitHubURLString(
		URL gitHubURL, String gitBranchSHA) {

		StringBuilder sb = new StringBuilder();

		Matcher matcher = _gitHubURLPattern.matcher(String.valueOf(gitHubURL));

		if (matcher.find()) {
			sb.append(matcher.group("userName"));
			sb.append("/");
			sb.append(matcher.group("repositoryName"));
			sb.append("/");
			sb.append(matcher.group("branchName"));
		}
		else {
			sb.append(gitHubURL);
		}

		if (!JenkinsResultsParserUtil.isNullOrEmpty(gitBranchSHA)) {
			sb.append(" (");
			sb.append(gitBranchSHA.substring(0, 7));
			sb.append(")");
		}

		return sb.toString();
	}

	private static File _getGitRepoFile(
		GitWorkingDirectory portalGitWorkingDirectory,
		GitWorkingDirectory subrepositoryGitWorkingDirectory) {

		GitRemote subrepositoryUpstreamGitRemote =
			subrepositoryGitWorkingDirectory.getUpstreamGitRemote();

		Set<File> gitRepoFiles = portalGitWorkingDirectory.findFiles(
			".gitrepo", subrepositoryUpstreamGitRemote.getRemoteURL());

		for (File gitRepoFile : gitRepoFiles) {
			return gitRepoFile;
		}

		return null;
	}

	private static GitWorkingDirectory _getGitWorkingDirectory(
			URL gitHubURL, String upstreamBranchName)
		throws IOException {

		Matcher gitHubURLMatcher = _gitHubURLPattern.matcher(
			String.valueOf(gitHubURL));

		if (!gitHubURLMatcher.find()) {
			throw new RuntimeException("Invalid GitHub URL " + gitHubURL);
		}

		String baseRepositoryDirPath =
			JenkinsResultsParserUtil.getBuildProperty("base.repository.dir");

		String repositoryName = gitHubURLMatcher.group("repositoryName");

		String repositoryDirPath = JenkinsResultsParserUtil.combine(
			baseRepositoryDirPath, "/", repositoryName);

		if (repositoryName.equals("liferay-portal-ee") &&
			upstreamBranchName.matches("\\d+\\.\\d+\\.x")) {

			repositoryDirPath = JenkinsResultsParserUtil.combine(
				baseRepositoryDirPath, "/liferay-portal-", upstreamBranchName);
		}

		return GitWorkingDirectoryFactory.newGitWorkingDirectory(
			upstreamBranchName, repositoryDirPath, repositoryName);
	}

	private static String _getSubrepositoryModuleDirPath(
		GitWorkingDirectory portalGitWorkingDirectory,
		GitWorkingDirectory subrepositoryGitWorkingDirectory) {

		String relativeFilePath = JenkinsResultsParserUtil.getPathRelativeTo(
			_getGitRepoFile(
				portalGitWorkingDirectory, subrepositoryGitWorkingDirectory),
			portalGitWorkingDirectory.getWorkingDirectory());

		return relativeFilePath.replaceAll("/\\.gitrepo", "");
	}

	private static void _pushUpdatesToRemoteBranch(
		URL portalGitHubURL, GitWorkingDirectory portalGitWorkingDirectory,
		String portalUpstreamBranchName) {

		Matcher gitHubURLMatcher = _gitHubURLPattern.matcher(
			String.valueOf(portalGitHubURL));

		if (!gitHubURLMatcher.find()) {
			throw new RuntimeException("Invalid GitHub URL " + portalGitHubURL);
		}

		String remoteURL = JenkinsResultsParserUtil.combine(
			"git@github.com:", gitHubURLMatcher.group("userName"), "/",
			portalGitWorkingDirectory.getGitRepositoryName(), ".git");

		RemoteGitBranch remoteGitBranch =
			portalGitWorkingDirectory.pushToRemoteGitRepository(
				false, portalGitWorkingDirectory.getCurrentLocalGitBranch(),
				portalUpstreamBranchName, remoteURL);

		if (remoteGitBranch == null) {
			throw new RuntimeException(
				"Failed to push updates to " + remoteURL);
		}
	}

	private static final Pattern _gitHubURLPattern = Pattern.compile(
		"https://github.com/(?<userName>[^/]+)/(?<repositoryName>[^/]+)/tree/" +
			"(?<branchName>[^/]+)");

}