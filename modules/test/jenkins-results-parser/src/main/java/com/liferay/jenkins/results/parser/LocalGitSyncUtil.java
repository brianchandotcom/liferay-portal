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
			GitWorkingDirectory gitWorkingDirectory, String senderBranchName,
			String senderUsername, String senderSHA, String upstreamBranchName,
			String upstreamUsername, String upstreamSHA)
		throws GitAPIException {

		List<RemoteConfig> localGitRemoteConfigs = null;

		try {
			localGitRemoteConfigs = getLocalGitRemoteConfigs(
				gitWorkingDirectory);

			deleteCachedBranch(
				getCacheBranchName(
					senderUsername, senderSHA, upstreamUsername, upstreamSHA),
				gitWorkingDirectory, localGitRemoteConfigs);
		}
		finally {
			if (localGitRemoteConfigs != null) {
				gitWorkingDirectory.removeRemotes(localGitRemoteConfigs);
			}
		}
	}

	public static String synchronizeToLocalGit(
			GitWorkingDirectory gitWorkingDirectory, String senderBranchName,
			String senderUsername, String senderSHA, String upstreamBranchName,
			String upstreamUsername, String upstreamSHA)
		throws GitAPIException, IOException {

		long start = System.currentTimeMillis();

		String originalBranchName = gitWorkingDirectory.getCurrentBranch();

		RemoteConfig senderRemoteConfig = null;
		RemoteConfig upstreamRemoteConfig = null;

		if ((senderBranchName == null) || senderBranchName.isEmpty()) {
			senderBranchName = upstreamBranchName;
		}

		if ((senderSHA == null) || senderSHA.isEmpty()) {
			senderSHA = upstreamSHA;
		}

		if ((senderUsername == null) || senderUsername.isEmpty()) {
			senderUsername = upstreamUsername;
		}

		try {
			senderRemoteConfig = gitWorkingDirectory.addRemote(
				true, "sender-temp",
				getGitHubRemoteURL(
					gitWorkingDirectory.getRepositoryName(), senderUsername));
			upstreamRemoteConfig = gitWorkingDirectory.getRemoteConfig(
				"upstream");

			boolean pullRequest = !upstreamSHA.equals(senderSHA);

			String cacheBranchName = getCacheBranchName(
				senderUsername, senderSHA, upstreamUsername, upstreamSHA);

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
						"cacheBranch " + cacheBranchName + " already exists.");

					return cacheBranchName;
				}

				gitWorkingDirectory.rebaseAbort();

				gitWorkingDirectory.clean();

				gitWorkingDirectory.reset("HEAD", ResetType.HARD);

				gitWorkingDirectory.fetch(upstreamRemoteConfig, null);

				gitWorkingDirectory.checkoutBranch(
					upstreamRemoteConfig.getName() + "/master", "-f");

				gitWorkingDirectory.deleteLocalBranch("master");

				gitWorkingDirectory.checkoutBranch("master", "-b");

				// Is this necessary? What does this do?

				// gitWorkingDirectory.copyUpstreamRefsToHeads();

				gitWorkingDirectory.createLocalBranch(
					cacheBranchName, true, null);

				gitWorkingDirectory.fetch(
					cacheBranchName, senderBranchName, senderRemoteConfig);

				gitWorkingDirectory.createLocalBranch(
					cacheBranchName, true, senderSHA);

				if (pullRequest) {
					gitWorkingDirectory.rebase(cacheBranchName, upstreamSHA);
				}

				gitWorkingDirectory.checkoutBranch(cacheBranchName);

				cacheBranches(
					gitWorkingDirectory, cacheBranchName, localGitRemoteConfigs,
					upstreamBranchName, upstreamUsername);

				return cacheBranchName;
			}
			finally {
				if (localGitRemoteConfigs != null) {
					gitWorkingDirectory.removeRemotes(localGitRemoteConfigs);
				}

				if (gitWorkingDirectory.localBranchExists(originalBranchName)) {
					gitWorkingDirectory.checkoutBranch(originalBranchName);
				}
				else {
					gitWorkingDirectory.checkoutBranch("master");
				}

				gitWorkingDirectory.deleteLocalBranch(cacheBranchName);
			}
		}
		finally {
			if (senderRemoteConfig != null) {
				gitWorkingDirectory.removeRemote(senderRemoteConfig);
			}

			System.out.println(
				"SynchronizeToLocalGit completed in " +
					JenkinsResultsParserUtil.toDurationString(
						System.currentTimeMillis() - start));
		}
	}

	protected static void cacheBranch(
			GitWorkingDirectory gitWorkingDirectory, String localBranchName,
			RemoteConfig remoteConfig)
		throws GitAPIException {

		gitWorkingDirectory.pushToRemote(remoteConfig);

		gitWorkingDirectory.pushToRemote(
			remoteConfig,
			JenkinsResultsParserUtil.combine(
				localBranchName, "-",
				Long.toString(System.currentTimeMillis())));
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
					"copying ", headsDir.getPath(), " to ",
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
						"WARNING - Unable to delete cached branch ",
						remoteBranchName, " from ",
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

		for (String remoteBranchName :
				gitWorkingDirectory.getRemoteRepositoryBranchNames(
					remoteConfig)) {

			Matcher matcher = _cachedTimestampBranchPattern.matcher(
				remoteBranchName);

			if (matcher.matches()) {
				long remoteBranchTimestamp = Long.parseLong(
					matcher.group("timestamp"));

				long branchAge =
					System.currentTimeMillis() - remoteBranchTimestamp;

				System.out.println(
					JenkinsResultsParserUtil.combine(
						remoteBranchName, " is ",
						JenkinsResultsParserUtil.toDurationString(branchAge),
						" old."));

				if (branchAge > _BRANCH_EXPIRE_AGE_MILLIS) {
					try {
						deleteRemoteRepositoryCachedBranch(
							gitWorkingDirectory, matcher.group("name"),
							remoteConfig);
						deleteRemoteRepositoryCachedBranch(
							gitWorkingDirectory, remoteBranchName,
							remoteConfig);
					}
					catch (GitAPIException gapie) {
						System.out.println(
							JenkinsResultsParserUtil.combine(
								"WARNING - Unable to delete cached branch ",
								remoteBranchName, " from ",
								GitWorkingDirectory.getRemoteURL(
									remoteConfig)));

						gapie.printStackTrace();
					}
				}
			}
		}
	}

	protected static void deleteLocalCacheBranches(
			String excludeBranchName, GitWorkingDirectory gitWorkingDirectory)
		throws GitAPIException {

		for (String localBranchName :
				gitWorkingDirectory.getLocalBranchNames()) {

			System.out.println("Checking local branch: " + localBranchName);

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
		String senderUsername, String senderSHA, String upstreamUsername,
		String upstreamSHA) {

		return JenkinsResultsParserUtil.combine(
			"cache-", upstreamUsername, "-", upstreamSHA, "-", senderUsername,
			"-", senderSHA);
	}

	protected static String getGitHubRemoteURL(
		String repositoryName, String userName) {

		return JenkinsResultsParserUtil.combine(
			"git@github.com:", userName, "/", repositoryName, ".git");
	}

	protected static List<RemoteConfig> getLocalGitRemoteConfigs(
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

			if (remoteConfig == null) {
				remoteConfig = gitWorkingDirectory.addRemote(
					true, localGitRemoteName, url);
			}

			localGitRemoteConfigs.add(remoteConfig);
		}

		return localGitRemoteConfigs;
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

	private static final long _BRANCH_EXPIRE_AGE_MILLIS =
		1000 * 60 * 60 * 24 * 2;

	private static final String _cachedBranchRegex = ".*cache-.+-.+-.+-.+";
	private static final Pattern _cachedTimestampBranchPattern =
		Pattern.compile("(?<name>cache-.*)-(?<timestamp>\\d+)");

}