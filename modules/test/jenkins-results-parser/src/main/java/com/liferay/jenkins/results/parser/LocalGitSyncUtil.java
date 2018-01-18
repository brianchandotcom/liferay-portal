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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Peter Yoo
 */
public class LocalGitSyncUtil {

	public static List<GitWorkingDirectory.Remote> getLocalGitRemotes(
		GitWorkingDirectory gitWorkingDirectory) {

		List<String> localGitRemoteURLs = getLocalGitRemoteURLs(
			gitWorkingDirectory);

		List<GitWorkingDirectory.Remote> localGitRemotes = new ArrayList<>(
			localGitRemoteURLs.size());

		for (String localGitRemoteURL : localGitRemoteURLs) {
			String localGitRemoteName =
				"local-git-remote-" +
					localGitRemoteURLs.indexOf(localGitRemoteURL);

			GitWorkingDirectory.Remote remote = gitWorkingDirectory.getRemote(
				localGitRemoteName);

			if ((remote == null) ||
				!localGitRemoteURL.equals(remote.getRemoteURL())) {

				remote = gitWorkingDirectory.addRemote(
					true, localGitRemoteName, localGitRemoteURL);
			}

			localGitRemotes.add(remote);
		}

		return localGitRemotes;
	}

	public static String synchronizeToLocalGit(
			GitWorkingDirectory gitWorkingDirectory, String receiverUsername,
			String senderBranchName, String senderUsername,
			String senderBranchSHA, String upstreamBranchSHA)
		throws IOException {

		return synchronizeToLocalGit(
			gitWorkingDirectory, receiverUsername, 0, senderBranchName,
			senderUsername, senderBranchSHA, upstreamBranchSHA);
	}

	protected static void cacheBranch(
		GitWorkingDirectory gitWorkingDirectory,
		GitWorkingDirectory.Branch localBranch,
		GitWorkingDirectory.Remote remote, long timestamp) {

		gitWorkingDirectory.pushToRemote(
			true, localBranch, localBranch.getName(), remote);

		gitWorkingDirectory.pushToRemote(
			true, localBranch,
			JenkinsResultsParserUtil.combine(
				localBranch.getName(), "-", Long.toString(timestamp)),
			remote);
	}

	protected static void cacheBranches(
		final GitWorkingDirectory gitWorkingDirectory,
		final GitWorkingDirectory.Branch localBranch,
		List<GitWorkingDirectory.Remote> localGitRemotes,
		final String upstreamUsername) {

		String localBranchName = localBranch.getName();
		GitWorkingDirectory.Branch currentBranch =
			gitWorkingDirectory.getCurrentBranch();

		if ((currentBranch == null) ||
			!localBranchName.equals(currentBranch.getName())) {

			gitWorkingDirectory.checkoutBranch(localBranch, "-f");
		}

		final long start = System.currentTimeMillis();

		final GitWorkingDirectory.Branch upstreamBranch =
			gitWorkingDirectory.getBranch(
				gitWorkingDirectory.getUpstreamBranchName(),
				gitWorkingDirectory.getRemote("upstream"));

		List<Callable<Object>> callables = new ArrayList<>();

		for (final GitWorkingDirectory.Remote localGitRemote :
				localGitRemotes) {

			Callable<Object> callable = new Callable<Object>() {

				@Override
				public Object call() {
					cacheBranch(
						gitWorkingDirectory, localBranch, localGitRemote,
						start);

					if (upstreamUsername.equals("liferay")) {
						GitWorkingDirectory.Branch localUpstreamBranch =
							gitWorkingDirectory.getBranch(
								upstreamBranch.getName(), null);

						gitWorkingDirectory.pushToRemote(
							true, localUpstreamBranch, upstreamBranch.getName(),
							localGitRemote);
					}

					return null;
				}

			};

			callables.add(callable);
		}

		ParallelExecutor<Object> parallelExecutor = new ParallelExecutor<>(
			callables, _threadPoolExecutor);

		parallelExecutor.execute();

		long duration = System.currentTimeMillis() - start;

		System.out.println(
			"Cache branches pushed up in " +
				JenkinsResultsParserUtil.toDurationString(duration));
	}

	protected static void checkoutUpstreamBranch(
		GitWorkingDirectory gitWorkingDirectory, String upstreamBranchSHA) {

		GitWorkingDirectory.Branch localUpstreamBranch =
			updateLocalUpstreamBranch(gitWorkingDirectory, upstreamBranchSHA);

		if (localUpstreamBranch != null) {
			gitWorkingDirectory.checkoutBranch(localUpstreamBranch);
		}
	}

	protected static void copyUpstreamRefsToHeads(
			GitWorkingDirectory gitWorkingDirectory)
		throws IOException {

		File gitDir = gitWorkingDirectory.getGitDirectory();

		File headsDir = new File(gitDir, "refs/heads");
		File upstreamDir = new File(gitDir, "refs/remotes/upstream-temp");

		for (File file : upstreamDir.listFiles()) {
			System.out.println(
				JenkinsResultsParserUtil.combine(
					"Copying ", headsDir.getPath(), " to ",
					upstreamDir.getPath()));
			JenkinsResultsParserUtil.copy(
				file, new File(headsDir, file.getName()));
		}
	}

	protected static void deleteExpiredCacheBranches(
		GitWorkingDirectory gitWorkingDirectory,
		GitWorkingDirectory.Remote remote, long timestamp) {

		int branchCount = 0;
		int deleteCount = 0;
		long oldestBranchAge = Long.MIN_VALUE;

		Map<String, GitWorkingDirectory.Branch> remoteBranches =
			new HashMap<>();

		for (GitWorkingDirectory.Branch remoteBranch :
				gitWorkingDirectory.getRemoteBranches(null, remote)) {

			remoteBranches.put(remoteBranch.getName(), remoteBranch);
		}

		for (Map.Entry<String, GitWorkingDirectory.Branch> entry :
				remoteBranches.entrySet()) {

			GitWorkingDirectory.Branch remoteBranch = entry.getValue();

			Matcher matcher = _cacheTimestampBranchPattern.matcher(
				entry.getKey());

			if (matcher.matches()) {
				branchCount++;

				long remoteBranchTimestamp = Long.parseLong(
					matcher.group("timestamp"));

				long branchAge = timestamp - remoteBranchTimestamp;

				if (branchAge > _BRANCH_EXPIRE_AGE_MILLIS) {
					GitWorkingDirectory.Branch remoteRepositoryBaseCacheBranch =
						remoteBranches.get(matcher.group("name"));

					if (remoteRepositoryBaseCacheBranch != null) {
						deleteRemoteRepositoryCacheBranch(
							gitWorkingDirectory,
							remoteRepositoryBaseCacheBranch);
					}

					deleteRemoteRepositoryCacheBranch(
						gitWorkingDirectory, remoteBranch);

					deleteCount++;
				}
				else {
					oldestBranchAge = Math.max(oldestBranchAge, branchAge);
				}
			}
		}

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"Found ", Integer.toString(branchCount), " cache branches on ",
				remote.getRemoteURL(), " ", Integer.toString(deleteCount),
				" were deleted. ", Integer.toString(branchCount - deleteCount),
				" remain. The oldest branch is ",
				JenkinsResultsParserUtil.toDurationString(oldestBranchAge),
				" old."));
	}

	protected static void deleteExpiredCacheBranches(
		final GitWorkingDirectory gitWorkingDirectory,
		List<GitWorkingDirectory.Remote> localGitRemotes) {

		final long start = System.currentTimeMillis();

		List<Callable<Object>> callables = new ArrayList<>();

		for (final GitWorkingDirectory.Remote localGitRemote :
				localGitRemotes) {

			Callable<Object> callable = new Callable<Object>() {

				@Override
				public Object call() {
					deleteExpiredCacheBranches(
						gitWorkingDirectory, localGitRemote, start);

					return null;
				}

			};

			callables.add(callable);
		}

		ParallelExecutor<Object> parallelExecutor = new ParallelExecutor<>(
			callables, _threadPoolExecutor);

		parallelExecutor.execute();

		long duration = System.currentTimeMillis() - start;

		System.out.println(
			"Expired cache branches deleted in " +
				JenkinsResultsParserUtil.toDurationString(duration));
	}

	protected static void deleteLocalCacheBranches(
		String excludeBranchName, GitWorkingDirectory gitWorkingDirectory) {

		for (String localBranchName :
				gitWorkingDirectory.getLocalBranchNames()) {

			if (localBranchName.matches(_CACHE_BRANCH_REGEX) &&
				!localBranchName.equals(excludeBranchName)) {

				gitWorkingDirectory.deleteBranch(localBranchName, null);
			}
		}
	}

	protected static void deleteRemoteCacheBranch(
		String cacheBranchName, GitWorkingDirectory gitWorkingDirectory,
		Map<String, GitWorkingDirectory.Branch> remoteBranches) {

		for (Map.Entry<String, GitWorkingDirectory.Branch> entry :
				remoteBranches.entrySet()) {

			String remoteBranchName = entry.getKey();

			if (!remoteBranchName.startsWith(cacheBranchName)) {
				continue;
			}

			deleteRemoteRepositoryCacheBranch(
				gitWorkingDirectory, entry.getValue());
		}
	}

	protected static void deleteRemoteRepositoryCacheBranch(
		GitWorkingDirectory gitWorkingDirectory,
		GitWorkingDirectory.Branch remoteBranch) {

		GitWorkingDirectory.Remote remote = remoteBranch.getRemote();

		if (gitWorkingDirectory.pushToRemote(true, null, remoteBranch)) {
			System.out.println(
				JenkinsResultsParserUtil.combine(
					"Deleted ", remoteBranch.getName(), " from ",
					remote.getName()));
		}
		else {
			System.out.println(
				JenkinsResultsParserUtil.combine(
					"Unable to delete ", remoteBranch.getName(), " from ",
					remote.getName()));
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
			throw new RuntimeException("Unable to get build properties");
		}

		String gitCacheHostnamesPropertyValue = buildProperties.getProperty(
			"github.cache.hostnames");

		String[] gitCacheHostnames = gitCacheHostnamesPropertyValue.split(",");

		List<String> localGitRemoteURLs = new ArrayList<>(
			gitCacheHostnames.length);

		for (String gitCacheHostname : gitCacheHostnames) {
			localGitRemoteURLs.add(
				JenkinsResultsParserUtil.combine(
					"git@", gitCacheHostname, ":",
					gitWorkingDirectory.getRepositoryUsername(), "/",
					gitWorkingDirectory.getRepositoryName(), ".git"));
		}

		return validateLocalGitRemoteURLs(
			localGitRemoteURLs, gitWorkingDirectory);
	}

	protected static GitWorkingDirectory.Remote getRandomRemote(
		List<GitWorkingDirectory.Remote> remotes) {

		return remotes.get(
			JenkinsResultsParserUtil.getRandomValue(0, remotes.size() - 1));
	}

	protected static List<GitWorkingDirectory.Branch> getRemoteCacheBranches(
		GitWorkingDirectory gitWorkingDirectory,
		GitWorkingDirectory.Remote remote) {

		List<GitWorkingDirectory.Branch> remoteCacheBranches =
			new ArrayList<>();

		Map<String, GitWorkingDirectory.Branch> remoteBranches =
			new HashMap<>();

		for (GitWorkingDirectory.Branch remoteBranch :
				gitWorkingDirectory.getRemoteBranches(null, remote)) {

			remoteBranches.put(remoteBranch.getName(), remoteBranch);
		}

		for (Map.Entry<String, GitWorkingDirectory.Branch> entry :
				remoteBranches.entrySet()) {

			String remoteBranchName = entry.getKey();

			if (remoteBranchName.matches(_CACHE_BRANCH_REGEX)) {
				if (hasTimestampBranch(remoteBranchName, remoteBranches)) {
					remoteCacheBranches.add(entry.getValue());
				}
				else {
					deleteRemoteCacheBranch(
						remoteBranchName, gitWorkingDirectory, remoteBranches);
				}
			}
		}

		return remoteCacheBranches;
	}

	protected static boolean hasTimestampBranch(
		String cacheBranchName,
		Map<String, GitWorkingDirectory.Branch> remoteBranches) {

		for (String remoteBranchName : remoteBranches.keySet()) {
			Matcher matcher = _cacheTimestampBranchPattern.matcher(
				remoteBranchName);

			if (matcher.matches()) {
				return true;
			}
		}

		return false;
	}

	protected static boolean isBranchCached(
		String branchName, GitWorkingDirectory gitWorkingDirectory,
		List<GitWorkingDirectory.Remote> remotes) {

		for (GitWorkingDirectory.Remote remote : remotes) {
			if (gitWorkingDirectory.branchExists(branchName, remote)) {
				continue;
			}

			return false;
		}

		return true;
	}

	protected static Map<GitWorkingDirectory.Remote, Boolean> pushToAllRemotes(
		final boolean force, final GitWorkingDirectory gitWorkingDirectory,
		final GitWorkingDirectory.Branch localBranch,
		final String remoteBranchName,
		final List<GitWorkingDirectory.Remote> remotes) {

		if (localBranch != null) {
			String localBranchName = localBranch.getName();
			GitWorkingDirectory.Branch currentBranch =
				gitWorkingDirectory.getCurrentBranch();

			if ((currentBranch == null) ||
				!localBranchName.equals(currentBranch.getName())) {

				gitWorkingDirectory.checkoutBranch(localBranch, "-f");
			}
		}

		final long start = System.currentTimeMillis();

		final Map<GitWorkingDirectory.Remote, Boolean> resultsMap =
			Collections.synchronizedMap(
				new HashMap<GitWorkingDirectory.Remote, Boolean>(
					remotes.size()));

		List<Callable<Boolean>> callables = new ArrayList<>();

		for (final GitWorkingDirectory.Remote remote : remotes) {
			Callable<Boolean> callable = new Callable<Boolean>() {

				@Override
				public Boolean call() {
					Boolean result = gitWorkingDirectory.pushToRemote(
						force, localBranch, remoteBranchName, remote);

					resultsMap.put(remote, result);

					return result;
				}

			};

			callables.add(callable);
		}

		ParallelExecutor<Boolean> parallelExecutor = new ParallelExecutor<>(
			callables, _threadPoolExecutor);

		parallelExecutor.execute();

		long duration = System.currentTimeMillis() - start;

		if (localBranch == null) {
			System.out.println(
				JenkinsResultsParserUtil.combine(
					"Deleted ", remoteBranchName, " on ",
					Integer.toString(remotes.size()), " git nodes in ",
					JenkinsResultsParserUtil.toDurationString(duration)));
		}
		else {
			System.out.println(
				JenkinsResultsParserUtil.combine(
					"Pushed ", localBranch.getName(), " to ", remoteBranchName,
					" on ", Integer.toString(remotes.size()), " git nodes in ",
					JenkinsResultsParserUtil.toDurationString(duration)));
		}

		return resultsMap;
	}

	protected static String synchronizeToLocalGit(
			GitWorkingDirectory gitWorkingDirectory, String receiverUsername,
			int retryCount, String senderBranchName, String senderUsername,
			String senderBranchSHA, String upstreamBranchSHA)
		throws IOException {

		long start = System.currentTimeMillis();

		File repositoryDirectory = gitWorkingDirectory.getWorkingDirectory();

		GitWorkingDirectory.Branch originalBranch =
			gitWorkingDirectory.getCurrentBranch();

		if (originalBranch == null) {
			gitWorkingDirectory.reset("--hard");

			originalBranch = gitWorkingDirectory.getBranch(
				gitWorkingDirectory.getUpstreamBranchName(), null);

			if (originalBranch != null) {
				gitWorkingDirectory.checkoutBranch(originalBranch);
			}
		}

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"Starting synchronization with local-git. Current repository ",
				"directory is ", repositoryDirectory.getPath(), ". Current ",
				"branch is ", originalBranch.getName(), "."));

		GitWorkingDirectory.Remote senderRemote = null;

		try {
			senderRemote = gitWorkingDirectory.addRemote(
				true, "sender-temp",
				getGitHubRemoteURL(
					gitWorkingDirectory.getRepositoryName(), senderUsername));

			boolean pullRequest = !upstreamBranchSHA.equals(senderBranchSHA);

			String cacheBranchName = getCacheBranchName(
				receiverUsername, senderUsername, senderBranchSHA,
				upstreamBranchSHA);

			String upstreamBranchName =
				gitWorkingDirectory.getUpstreamBranchName();

			List<GitWorkingDirectory.Remote> localGitRemotes = null;

			try {
				localGitRemotes = getLocalGitRemotes(gitWorkingDirectory);

				deleteLocalCacheBranches(cacheBranchName, gitWorkingDirectory);

				deleteExpiredCacheBranches(
					gitWorkingDirectory, localGitRemotes);

				if (isBranchCached(
						cacheBranchName, gitWorkingDirectory,
						localGitRemotes)) {

					System.out.println(
						JenkinsResultsParserUtil.combine(
							"Cache branch ", cacheBranchName,
							" already exists"));

					GitWorkingDirectory.Remote localGitRemote = getRandomRemote(
						localGitRemotes);

					GitWorkingDirectory.Branch remoteCacheBranch =
						gitWorkingDirectory.getBranch(
							cacheBranchName, localGitRemote);

					gitWorkingDirectory.fetch(null, remoteCacheBranch);

					gitWorkingDirectory.deleteBranch(cacheBranchName, null);

					gitWorkingDirectory.createLocalBranch(
						cacheBranchName, true, remoteCacheBranch.getSHA());

					if (!gitWorkingDirectory.branchExists(
							upstreamBranchName, null)) {

						updateLocalUpstreamBranch(
							gitWorkingDirectory, upstreamBranchSHA);
					}

					updateCacheBranchTimestamp(
						cacheBranchName, gitWorkingDirectory, localGitRemotes);

					return cacheBranchName;
				}

				GitWorkingDirectory.Branch localCacheBranch =
					gitWorkingDirectory.getBranch(cacheBranchName, null);

				if (localCacheBranch == null) {
					localCacheBranch = gitWorkingDirectory.createLocalBranch(
						cacheBranchName, true, null);
				}

				senderBranchName = senderBranchName.trim();

				gitWorkingDirectory.fetch(
					localCacheBranch,
					gitWorkingDirectory.getBranch(
						senderBranchName, senderRemote));

				updateLocalUpstreamBranch(
					gitWorkingDirectory, upstreamBranchSHA);

				gitWorkingDirectory.createLocalBranch(
					cacheBranchName, true, senderBranchSHA);

				if (pullRequest) {
					gitWorkingDirectory.checkoutBranch(localCacheBranch);

					gitWorkingDirectory.rebase(
						true,
						gitWorkingDirectory.getBranch(upstreamBranchName, null),
						localCacheBranch);
				}

				cacheBranches(
					gitWorkingDirectory, localCacheBranch, localGitRemotes,
					"liferay");

				return cacheBranchName;
			}
			catch (Exception e) {
				if (retryCount == 1) {
					throw e;
				}

				localGitRemotes = null;
				senderRemote = null;

				System.out.println(
					"Synchronization with local-git failed. Retrying.");

				e.printStackTrace();

				gitWorkingDirectory.checkoutBranch(originalBranch);

				return synchronizeToLocalGit(
					gitWorkingDirectory, receiverUsername, retryCount + 1,
					senderBranchName, senderUsername, senderBranchSHA,
					upstreamBranchSHA);
			}
			finally {
				if (localGitRemotes != null) {
					try {
						gitWorkingDirectory.removeRemotes(localGitRemotes);
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}

				if (gitWorkingDirectory.branchExists(
						originalBranch.getName(), null)) {

					gitWorkingDirectory.checkoutBranch(originalBranch);
				}
				else {
					checkoutUpstreamBranch(
						gitWorkingDirectory, upstreamBranchSHA);
				}

				gitWorkingDirectory.deleteBranch(cacheBranchName, null);
			}
		}
		finally {
			if (senderRemote != null) {
				try {
					gitWorkingDirectory.removeRemote(senderRemote);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}

			System.out.println(
				"Synchronization with local Git completed in " +
					JenkinsResultsParserUtil.toDurationString(
						System.currentTimeMillis() - start));
		}
	}

	protected static void updateCacheBranchTimestamp(
		final String cacheBranchName,
		final GitWorkingDirectory gitWorkingDirectory,
		List<GitWorkingDirectory.Remote> localGitRemotes) {

		long start = System.currentTimeMillis();

		GitWorkingDirectory.Remote localGitRemote = getRandomRemote(
			localGitRemotes);

		List<GitWorkingDirectory.Branch> remoteCacheBranches =
			getRemoteCacheBranches(gitWorkingDirectory, localGitRemote);

		boolean updated = false;

		for (GitWorkingDirectory.Branch remoteCacheBranch :
				remoteCacheBranches) {

			String remoteCacheBranchName = remoteCacheBranch.getName();

			Matcher matcher = _cacheTimestampBranchPattern.matcher(
				remoteCacheBranchName);

			if (remoteCacheBranchName.contains(cacheBranchName) &&
				matcher.matches()) {

				if (updated) {
					pushToAllRemotes(
						true, gitWorkingDirectory, null, remoteCacheBranchName,
						localGitRemotes);

					continue;
				}

				long currentTimestamp = System.currentTimeMillis();
				long existingTimestamp = Long.parseLong(
					matcher.group("timestamp"));

				if ((currentTimestamp - existingTimestamp) <
						(1000 * 60 * 60 * 24)) {

					return;
				}

				String newTimestampBranchName =
					JenkinsResultsParserUtil.combine(
						cacheBranchName, "-", Long.toString(currentTimestamp));

				System.out.println(
					JenkinsResultsParserUtil.combine(
						"\nUpdating cache branch timestamp from ",
						remoteCacheBranchName, "to ", newTimestampBranchName));

				System.out.println(
					JenkinsResultsParserUtil.combine(
						"Updating existing timestamp for branch ",
						remoteCacheBranchName, " to ", newTimestampBranchName));

				GitWorkingDirectory.Branch currentBranch =
					gitWorkingDirectory.getCurrentBranch();

				if (currentBranch == null) {
					currentBranch = gitWorkingDirectory.getBranch(
						gitWorkingDirectory.getUpstreamBranchName(), null);
				}

				GitWorkingDirectory.Branch newTimestampBranch =
					gitWorkingDirectory.createLocalBranch(
						newTimestampBranchName);

				gitWorkingDirectory.fetch(
					newTimestampBranch, remoteCacheBranch);

				try {
					pushToAllRemotes(
						true, gitWorkingDirectory, newTimestampBranch,
						newTimestampBranchName, localGitRemotes);
					pushToAllRemotes(
						true, gitWorkingDirectory, null, remoteCacheBranchName,
						localGitRemotes);

					updated = true;
				}
				finally {
					if ((currentBranch != null) &&
						gitWorkingDirectory.branchExists(
							currentBranch.getName(), null)) {

						gitWorkingDirectory.checkoutBranch(currentBranch);
					}
					else {
						checkoutUpstreamBranch(gitWorkingDirectory, null);
					}

					gitWorkingDirectory.deleteBranch(newTimestampBranch);
				}
			}
		}

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"Cache branch timestamp updated in ",
				JenkinsResultsParserUtil.toDurationString(
					System.currentTimeMillis() - start)));
	}

	protected static GitWorkingDirectory.Branch updateLocalUpstreamBranch(
		GitWorkingDirectory gitWorkingDirectory, String upstreamBranchSHA) {

		String upstreamBranchName = gitWorkingDirectory.getUpstreamBranchName();

		GitWorkingDirectory.Branch remoteUpstreamBranch =
			gitWorkingDirectory.getBranch(
				upstreamBranchName, gitWorkingDirectory.getRemote("upstream"));

		GitWorkingDirectory.Branch localUpstreamBranch =
			gitWorkingDirectory.getBranch(upstreamBranchName, null);

		if (localUpstreamBranch == null) {
			localUpstreamBranch = gitWorkingDirectory.createLocalBranch(
				upstreamBranchName);

			gitWorkingDirectory.fetch(
				localUpstreamBranch, remoteUpstreamBranch);
		}

		String localUpstreamBranchSHA = localUpstreamBranch.getSHA();

		String remoteUpstreamBranchSHA = remoteUpstreamBranch.getSHA();

		if ((upstreamBranchSHA != null) &&
			!remoteUpstreamBranchSHA.equals(upstreamBranchSHA)) {

			remoteUpstreamBranchSHA = upstreamBranchSHA;
		}

		if (localUpstreamBranchSHA.equals(remoteUpstreamBranchSHA)) {
			return localUpstreamBranch;
		}

		gitWorkingDirectory.rebaseAbort();

		gitWorkingDirectory.clean();

		gitWorkingDirectory.reset("--hard");

		gitWorkingDirectory.fetch(null, remoteUpstreamBranch);

		String tempBranchName = "temp-" + System.currentTimeMillis();

		GitWorkingDirectory.Branch tempBranch = null;

		try {
			tempBranch = gitWorkingDirectory.createLocalBranch(
				tempBranchName, true, remoteUpstreamBranchSHA);

			gitWorkingDirectory.checkoutBranch(tempBranch, "-f");

			gitWorkingDirectory.deleteBranch(upstreamBranchName, null);

			localUpstreamBranch = gitWorkingDirectory.createLocalBranch(
				remoteUpstreamBranch.getName(), true, remoteUpstreamBranchSHA);

			gitWorkingDirectory.checkoutBranch(localUpstreamBranch);
		}
		finally {
			if (tempBranch != null) {
				gitWorkingDirectory.deleteBranch(tempBranch);
			}
		}

		return localUpstreamBranch;
	}

	protected static List<String> validateLocalGitRemoteURLs(
		List<String> localGitRemoteURLs,
		final GitWorkingDirectory gitWorkingDirectory) {

		List<Callable<String>> callables = new ArrayList<>();

		for (final String localGitRemoteURL : localGitRemoteURLs) {
			Callable<String> callable = new Callable<String>() {

				@Override
				public String call() {
					if (gitWorkingDirectory.isRemoteRepositoryAlive(
							localGitRemoteURL)) {

						return localGitRemoteURL;
					}

					return null;
				}

			};

			callables.add(callable);
		}

		ParallelExecutor<String> parallelExecutor = new ParallelExecutor<>(
			callables, _threadPoolExecutor);

		List<String> validatedLocalGitRemoteURLs = new ArrayList<>();

		for (String validatedLocalGitRemoteURL : parallelExecutor.execute()) {
			if (validatedLocalGitRemoteURL != null) {
				validatedLocalGitRemoteURLs.add(validatedLocalGitRemoteURL);
			}
		}

		return validatedLocalGitRemoteURLs;
	}

	private static final long _BRANCH_EXPIRE_AGE_MILLIS =
		1000 * 60 * 60 * 24 * 2;

	private static final String _CACHE_BRANCH_REGEX = ".*cache-.+-.+-.+-[^-]+";

	private static final Pattern _cacheTimestampBranchPattern = Pattern.compile(
		"(?<name>cache-[^-]+-[^-]+-[^-]+-[^-]+)-(?<timestamp>\\d+)");
	private static final ThreadPoolExecutor _threadPoolExecutor =
		JenkinsResultsParserUtil.getNewThreadPoolExecutor(5, true);

}