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

package com.liferay.jenkins.results.parser;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jgit.api.ResetCommand.ResetType;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.RemoteConfig;

/**
 * @author Peter Yoo
 */
public class LocalGitSyncUtil {

	public static void deleteCachedBranch(
			GitWorkingDirectory gitWorkingDirectory, String receiverUsername,
			String senderBranchName, String senderUsername,
			String senderBranchSha, String upstreamBranchName,
			String upstreamBranchSha)
		throws GitAPIException {

		List<RemoteConfig> localGitRemoteConfigs = null;

		try {
			localGitRemoteConfigs = getLocalGitRemoteConfigs(
				gitWorkingDirectory);

			deleteCachedBranch(
				getCacheBranchName(
					receiverUsername, senderUsername, senderBranchSha,
					upstreamBranchSha),
				gitWorkingDirectory, localGitRemoteConfigs);
		}
		finally {
			if (localGitRemoteConfigs != null) {
				gitWorkingDirectory.removeRemotes(localGitRemoteConfigs);
			}
		}
	}

	public static List<RemoteConfig> getLocalGitRemoteConfigs(
			GitWorkingDirectory gitWorkingDirectory)
		throws GitAPIException {

		List<String> localGitRemoteURLs = getLocalGitRemoteURLs(
			gitWorkingDirectory);

		List<RemoteConfig> localGitRemoteConfigs = new ArrayList<>(
			localGitRemoteURLs.size());

		for (String localGitRemoteURL : localGitRemoteURLs) {
			String url = localGitRemoteURL.replace(
				"${username}", gitWorkingDirectory.getRepositoryUsername());

			url = url.replace(
				"${repository-name}", gitWorkingDirectory.getRepositoryName());

			String localGitRemoteName =
				"local-git-remote-" +
					localGitRemoteURLs.indexOf(localGitRemoteURL);

			RemoteConfig remoteConfig = gitWorkingDirectory.getRemoteConfig(
				localGitRemoteName);

			if ((remoteConfig == null) ||
				!url.equals(GitWorkingDirectory.getRemoteURL(remoteConfig))) {

				remoteConfig = gitWorkingDirectory.addRemote(
					true, localGitRemoteName, url);
			}

			localGitRemoteConfigs.add(remoteConfig);
		}

		return localGitRemoteConfigs;
	}

	public static String synchronizeToLocalGit(
			GitWorkingDirectory gitWorkingDirectory, String receiverUsername,
			String senderBranchName, String senderUsername,
			String senderBranchSha, String upstreamBranchName,
			String upstreamBranchSha)
		throws GitAPIException, IOException {

		return synchronizeToLocalGit(
			gitWorkingDirectory, receiverUsername, 0, senderBranchName,
			senderUsername, senderBranchSha, upstreamBranchName,
			upstreamBranchSha);
	}

	protected static void cacheBranch(
			GitWorkingDirectory gitWorkingDirectory, String localBranchName,
			RemoteConfig remoteConfig)
		throws GitAPIException {

		gitWorkingDirectory.pushToRemote(remoteConfig);

		gitWorkingDirectory.pushToRemote(
			JenkinsResultsParserUtil.combine(
				localBranchName, "-",
				Long.toString(System.currentTimeMillis())),
			remoteConfig);
	}

	protected static void cacheBranches(
			final GitWorkingDirectory gitWorkingDirectory,
			final String localBranchName,
			List<RemoteConfig> localGitRemoteConfigs,
			final String upstreamBranchName, final String upstreamUsername)
		throws GitAPIException {

		long start = System.currentTimeMillis();

		ExecutorService executorService = Executors.newFixedThreadPool(
			localGitRemoteConfigs.size());

		for (final RemoteConfig localGitRemoteConfig : localGitRemoteConfigs) {
			executorService.execute(
				new Runnable() {

					@Override
					public void run() {
						try {
							cacheBranch(
								gitWorkingDirectory, localBranchName,
								localGitRemoteConfig);

							if (upstreamUsername.equals("liferay")) {
								gitWorkingDirectory.pushToRemote(
									upstreamBranchName, upstreamBranchName,
									localGitRemoteConfig);
							}
						}
						catch (GitAPIException gapie) {
							throw new RuntimeException(gapie);
						}
					}

				});
		}

		executorService.shutdown();

		try {
			executorService.awaitTermination(30, TimeUnit.MINUTES);
		}
		catch (InterruptedException ie) {
			throw new RuntimeException(ie);
		}

		long duration = System.currentTimeMillis() - start;

		System.out.println(
			"Cache branches pushed up in " +
				JenkinsResultsParserUtil.toDurationString(duration));
	}

	protected static void copyUpstreamRefsToHeads(
			GitWorkingDirectory gitWorkingDirectory)
		throws IOException {

		File gitDirectory = gitWorkingDirectory.getGitDirectory();

		File headsDir = new File(gitDirectory, "refs/heads");
		File upstreamDir = new File(gitDirectory, "refs/remotes/upstream-temp");

		for (File file : upstreamDir.listFiles()) {
			System.out.println(
				JenkinsResultsParserUtil.combine(
					"Copying ", headsDir.getPath(), " to ",
					upstreamDir.getPath()));
			JenkinsResultsParserUtil.copy(
				file, new File(headsDir, file.getName()));
		}
	}

	protected static void deleteCachedBranch(
			final String cachedBranchName,
			final GitWorkingDirectory gitWorkingDirectory,
			List<RemoteConfig> localGitRemoteConfigs)
		throws GitAPIException {

		long start = System.currentTimeMillis();

		ExecutorService executorService = Executors.newFixedThreadPool(
			localGitRemoteConfigs.size());

		for (final RemoteConfig localGitRemoteConfig : localGitRemoteConfigs) {
			executorService.execute(
				new Runnable() {

					@Override
					public void run() {
						try {
							deleteCachedBranch(
								cachedBranchName, gitWorkingDirectory,
								localGitRemoteConfig);
						}
						catch (GitAPIException gapie) {
							throw new RuntimeException(gapie);
						}
					}

				});
		}

		executorService.shutdown();

		try {
			executorService.awaitTermination(15, TimeUnit.MINUTES);
		}
		catch (InterruptedException ie) {
			throw new RuntimeException(ie);
		}

		long duration = System.currentTimeMillis() - start;

		System.out.println(
			"Expired cache branches deleted in " +
				JenkinsResultsParserUtil.toDurationString(duration));
	}

	protected static void deleteCachedBranch(
			String cachedBranchName, GitWorkingDirectory gitWorkingDirectory,
			RemoteConfig remoteConfig)
		throws GitAPIException {

		for (String remoteBranchName :
				gitWorkingDirectory.getRemoteRepositoryBranchNames(
					remoteConfig)) {

			if (!remoteBranchName.startsWith(cachedBranchName)) {
				continue;
			}

			try {
				deleteRemoteRepositoryCachedBranch(
					gitWorkingDirectory, remoteBranchName, remoteConfig);
			}
			catch (GitAPIException gapie) {
				System.out.println(
					JenkinsResultsParserUtil.combine(
						"Unable to delete cached branch ", remoteBranchName,
						" from ",
						GitWorkingDirectory.getRemoteURL(remoteConfig)));

				gapie.printStackTrace();
			}
		}
	}

	protected static void deleteExpiredCacheBranches(
			final GitWorkingDirectory gitWorkingDirectory,
			List<RemoteConfig> localGitRemoteConfigs)
		throws GitAPIException {

		long start = System.currentTimeMillis();

		ExecutorService executorService = Executors.newFixedThreadPool(
			localGitRemoteConfigs.size());

		for (final RemoteConfig localGitRemoteConfig : localGitRemoteConfigs) {
			executorService.execute(
				new Runnable() {

					@Override
					public void run() {
						try {
							deleteExpiredCacheBranches(
								gitWorkingDirectory, localGitRemoteConfig);
						}
						catch (GitAPIException gapie) {
							throw new RuntimeException(gapie);
						}
					}

				});
		}

		executorService.shutdown();

		try {
			executorService.awaitTermination(15, TimeUnit.MINUTES);
		}
		catch (InterruptedException ie) {
			throw new RuntimeException(ie);
		}

		long duration = System.currentTimeMillis() - start;

		System.out.println(
			"Expired cache branches deleted in " +
				JenkinsResultsParserUtil.toDurationString(duration));
	}

	protected static void deleteExpiredCacheBranches(
			GitWorkingDirectory gitWorkingDirectory, RemoteConfig remoteConfig)
		throws GitAPIException {

		int branchCount = 0;
		int deleteCount = 0;
		long oldestBranchAge = Long.MIN_VALUE;

		List<String> remoteRepositoryBranchNames = null;

		try {
			remoteRepositoryBranchNames =
				gitWorkingDirectory.getRemoteRepositoryBranchNames(
					remoteConfig);
		}
		catch (GitAPIException gapie) {
			gapie.printStackTrace();

			System.out.println(
				"Unable to get remote repository branch names from " +
					GitWorkingDirectory.getRemoteURL(remoteConfig));

			return;
		}

		for (String remoteBranchName : remoteRepositoryBranchNames) {
			Matcher matcher = _cachedTimestampBranchPattern.matcher(
				remoteBranchName);

			if (matcher.matches()) {
				branchCount++;

				long remoteBranchTimestamp = Long.parseLong(
					matcher.group("timestamp"));

				long branchAge =
					System.currentTimeMillis() - remoteBranchTimestamp;

				if (branchAge > _BRANCH_EXPIRE_AGE_MILLIS) {
					try {
						deleteRemoteRepositoryCachedBranch(
							gitWorkingDirectory, matcher.group("name"),
							remoteConfig);
						deleteRemoteRepositoryCachedBranch(
							gitWorkingDirectory, remoteBranchName,
							remoteConfig);

						deleteCount++;
					}
					catch (GitAPIException gapie) {
						System.out.println(
							JenkinsResultsParserUtil.combine(
								"Unable to delete cached branch ",
								remoteBranchName, " from ",
								GitWorkingDirectory.getRemoteURL(
									remoteConfig)));

						gapie.printStackTrace();
					}
				}
				else {
					oldestBranchAge = Math.max(oldestBranchAge, branchAge);
				}
			}
		}

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"Found ", Integer.toString(branchCount), " cached branches on ",
				GitWorkingDirectory.getRemoteURL(remoteConfig), " ",
				Integer.toString(deleteCount), " were deleted. ",
				Integer.toString(branchCount - deleteCount),
				" remain. The oldest branch is ",
				JenkinsResultsParserUtil.toDurationString(oldestBranchAge),
				" old."));
	}

	protected static void deleteLocalCacheBranches(
			String excludeBranchName, GitWorkingDirectory gitWorkingDirectory)
		throws GitAPIException {

		for (String localBranchName :
				gitWorkingDirectory.getLocalBranchNames()) {

			if (localBranchName.matches(_cachedBranchRegex) &&
				!localBranchName.equals(excludeBranchName)) {

				gitWorkingDirectory.deleteLocalBranch(localBranchName);
			}
		}
	}

	protected static void deleteRemoteRepositoryCachedBranch(
			GitWorkingDirectory gitWorkingDirectory, String remoteBranchName,
			RemoteConfig remoteConfig)
		throws GitAPIException {

		if (gitWorkingDirectory.pushToRemote(
				"", remoteBranchName, remoteConfig)) {

			System.out.println(
				JenkinsResultsParserUtil.combine(
					"Deleted ", remoteBranchName, " from ",
					GitWorkingDirectory.getRemoteURL(remoteConfig)));
		}

		else {
			System.out.println(
				JenkinsResultsParserUtil.combine(
					"Unable to delete ", remoteBranchName, " from ",
					GitWorkingDirectory.getRemoteURL(remoteConfig)));
		}
	}

	protected static String getCacheBranchName(
		String receiverUsername, String senderUsername, String senderSHA,
		String upstreamSHA) {

		return JenkinsResultsParserUtil.combine(
			"cache-", receiverUsername, "-", upstreamSHA, "-", senderUsername,
			"-", senderSHA);
	}

	protected static String getGitHubRemoteURL(
		String repositoryName, String userName) {

		return JenkinsResultsParserUtil.combine(
			"git@github.com:", userName, "/", repositoryName, ".git");
	}

	protected static List<String> getLocalGitRemoteURLs(
		GitWorkingDirectory gitWorkingDirectory) {

		Properties buildProperties;

		try {
			buildProperties = JenkinsResultsParserUtil.getBuildProperties();
		}
		catch (IOException ioe) {
			throw new RuntimeException("Unable to get build properties.");
		}

		String gitCacheHostnamesPropertyValue = buildProperties.getProperty(
			"github.cache.hostnames");

		String[] gitCacheHostnames = gitCacheHostnamesPropertyValue.split(",");

		List<String> localGitRemoteURLs = new ArrayList<>(
			gitCacheHostnames.length);

		for (String gitCacheHostname : gitCacheHostnames) {
			if (gitCacheHostname.startsWith("file:") ||
				gitCacheHostname.startsWith("http:")) {

				localGitRemoteURLs.add(gitCacheHostname);

				continue;
			}

			localGitRemoteURLs.add(
				JenkinsResultsParserUtil.combine(
					"git@", gitCacheHostname, ":",
					gitWorkingDirectory.getRepositoryUsername(), "/",
					gitWorkingDirectory.getRepositoryName(), ".git"));
		}

		return localGitRemoteURLs;
	}

	protected static String synchronizeToLocalGit(
			GitWorkingDirectory gitWorkingDirectory, String receiverUsername,
			int retryCount, String senderBranchName, String senderUsername,
			String senderBranchSha, String upstreamBranchName,
			String upstreamBranchSha)
		throws GitAPIException, IOException {

		long start = System.currentTimeMillis();

		String originalBranchName = gitWorkingDirectory.getCurrentBranch();

		RemoteConfig senderRemoteConfig = null;
		RemoteConfig upstreamRemoteConfig = null;

		try {
			senderRemoteConfig = gitWorkingDirectory.addRemote(
				true, "sender-temp",
				getGitHubRemoteURL(
					gitWorkingDirectory.getRepositoryName(), senderUsername));
			upstreamRemoteConfig = gitWorkingDirectory.getRemoteConfig(
				"upstream");

			boolean pullRequest = !upstreamBranchSha.equals(senderBranchSha);

			String cacheBranchName = getCacheBranchName(
				receiverUsername, senderUsername, senderBranchSha,
				upstreamBranchSha);

			List<RemoteConfig> localGitRemoteConfigs = null;

			try {
				localGitRemoteConfigs = getLocalGitRemoteConfigs(
					gitWorkingDirectory);

				deleteLocalCacheBranches(cacheBranchName, gitWorkingDirectory);

				deleteExpiredCacheBranches(
					gitWorkingDirectory, localGitRemoteConfigs);

				int randomIndex = JenkinsResultsParserUtil.getRandomValue(
					0, localGitRemoteConfigs.size() - 1);

				List<String> remoteBranchNames =
					gitWorkingDirectory.getRemoteRepositoryBranchNames(
						localGitRemoteConfigs.get(randomIndex));

				if (remoteBranchNames.contains(cacheBranchName)) {
					System.out.println(
						"Cache branch " + cacheBranchName + " already exists.");

					return cacheBranchName;
				}

				gitWorkingDirectory.rebaseAbort();

				gitWorkingDirectory.clean();

				gitWorkingDirectory.reset(null, ResetType.HARD);

				gitWorkingDirectory.fetch(null, upstreamRemoteConfig);

				gitWorkingDirectory.checkoutBranch(
					JenkinsResultsParserUtil.combine(
						upstreamRemoteConfig.getName(), "/",
						upstreamBranchName),
					"-f");

				gitWorkingDirectory.deleteLocalBranch(upstreamBranchName);

				gitWorkingDirectory.checkoutBranch(upstreamBranchName, "-b");

				gitWorkingDirectory.createLocalBranch(
					cacheBranchName, true, null);

				gitWorkingDirectory.fetch(
					cacheBranchName, senderBranchName, senderRemoteConfig);

				gitWorkingDirectory.createLocalBranch(
					cacheBranchName, true, senderBranchSha);

				if (pullRequest) {
					if (!gitWorkingDirectory.rebase(
							true, cacheBranchName, upstreamBranchSha)) {

						throw new RuntimeException("Rebase failed.");
					}
				}

				gitWorkingDirectory.checkoutBranch(cacheBranchName);

				cacheBranches(
					gitWorkingDirectory, cacheBranchName, localGitRemoteConfigs,
					upstreamBranchName, "liferay");

				return cacheBranchName;
			}
			catch (Exception e) {
				if (retryCount == 1) {
					throw e;
				}

				try {
					deleteCachedBranch(
						gitWorkingDirectory, receiverUsername, senderBranchName,
						senderUsername, senderBranchSha, upstreamBranchName,
						upstreamBranchSha);
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}

				localGitRemoteConfigs = null;
				senderRemoteConfig = null;

				System.out.println(
					"Synchronization with local-git failed. Retrying.");

				return synchronizeToLocalGit(
					gitWorkingDirectory, receiverUsername, retryCount + 1,
					senderBranchName, senderUsername, senderBranchSha,
					upstreamBranchName, upstreamBranchSha);
			}
			finally {
				if (localGitRemoteConfigs != null) {
					gitWorkingDirectory.removeRemotes(localGitRemoteConfigs);
				}

				if (gitWorkingDirectory.localBranchExists(originalBranchName)) {
					gitWorkingDirectory.checkoutBranch(originalBranchName);
				}
				else {
					gitWorkingDirectory.checkoutBranch(
						gitWorkingDirectory.getUpstreamBranchName());
				}

				gitWorkingDirectory.deleteLocalBranch(cacheBranchName);
			}
		}
		finally {
			if (senderRemoteConfig != null) {
				gitWorkingDirectory.removeRemote(senderRemoteConfig);
			}

			System.out.println(
				"Synchronization with local git completed in " +
					JenkinsResultsParserUtil.toDurationString(
						System.currentTimeMillis() - start));
		}
	}

	private static final long _BRANCH_EXPIRE_AGE_MILLIS =
		1000 * 60 * 60 * 24 * 2;

	private static final String _cachedBranchRegex = ".*cache-.+-.+-.+-.+";
	private static final Pattern _cachedTimestampBranchPattern =
		Pattern.compile("(?<name>cache-.*)-(?<timestamp>\\d+)");

}