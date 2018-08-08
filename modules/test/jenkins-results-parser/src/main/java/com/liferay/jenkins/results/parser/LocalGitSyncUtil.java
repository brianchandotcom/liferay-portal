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

	public static void cleanUpLocalGitNodes(
		List<GitWorkingDirectory.Remote> localGitRemotes) {

		final long start = System.currentTimeMillis();

		List<Callable<Object>> callables = new ArrayList<>();

		for (final GitWorkingDirectory.Remote localGitRemote :
				localGitRemotes) {

			Callable<Object> callable = new SafeCallable<Object>() {

				@Override
				public Object safeCall() {
					GitWorkingDirectory gitWorkingDirectory =
						localGitRemote.getGitWorkingDirectory();

					List<RemoteGitBranch> remoteGitBranches =
						gitWorkingDirectory.getRemoteGitBranches(
							localGitRemote);

					Collections.sort(remoteGitBranches);

					Map<String, List<RemoteGitBranch>> remoteGitBranchMap =
						new HashMap<>();

					for (RemoteGitBranch remoteGitBranch : remoteGitBranches) {
						String remoteGitBranchName = remoteGitBranch.getName();

						if (remoteGitBranchName.matches(
								_cacheBranchPattern.pattern() + "-\\d+")) {

							String baseCacheBranchName =
								remoteGitBranchName.replaceAll(
									"(.*)-\\d+", "$1");

							if (!remoteGitBranchMap.containsKey(
									baseCacheBranchName)) {

								remoteGitBranchMap.put(
									baseCacheBranchName,
									new ArrayList<RemoteGitBranch>());
							}

							List<RemoteGitBranch> timestampedRemoteGitBranches =
								remoteGitBranchMap.get(baseCacheBranchName);

							timestampedRemoteGitBranches.add(remoteGitBranch);
						}
					}

					for (Map.Entry<String, List<RemoteGitBranch>> entry :
							remoteGitBranchMap.entrySet()) {

						List<RemoteGitBranch> timestampedRemoteGitBranches =
							entry.getValue();

						if (timestampedRemoteGitBranches.size() > 1) {
							timestampedRemoteGitBranches.remove(
								timestampedRemoteGitBranches.size() - 1);

							gitWorkingDirectory.deleteRemoteGitBranches(
								timestampedRemoteGitBranches);
						}
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
			"Local git nodes cleaned in " +
				JenkinsResultsParserUtil.toDurationString(duration));
	}

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
		GitWorkingDirectory gitWorkingDirectory, LocalGitBranch localGitBranch,
		GitWorkingDirectory.Remote remote, long timestamp) {

		gitWorkingDirectory.pushToRemote(
			true, localGitBranch, localGitBranch.getName(), remote);

		gitWorkingDirectory.pushToRemote(
			true, localGitBranch,
			JenkinsResultsParserUtil.combine(
				localGitBranch.getName(), "-", String.valueOf(timestamp)),
			remote);
	}

	protected static void cacheBranches(
		final GitWorkingDirectory gitWorkingDirectory,
		final LocalGitBranch localGitBranch,
		List<GitWorkingDirectory.Remote> localGitRemotes,
		final String upstreamUsername) {

		String currentBranchName = gitWorkingDirectory.getCurrentBranchName();
		String localGitBranchName = localGitBranch.getName();

		if ((currentBranchName == null) ||
			!currentBranchName.equals(localGitBranchName)) {

			gitWorkingDirectory.checkoutLocalGitBranch(localGitBranch, "-f");
		}

		final long start = System.currentTimeMillis();

		final RemoteGitBranch upstreamRemoteGitBranch =
			gitWorkingDirectory.getRemoteGitBranch(
				gitWorkingDirectory.getUpstreamBranchName(),
				gitWorkingDirectory.getRemote("upstream"), true);

		List<Callable<Object>> callables = new ArrayList<>();

		for (final GitWorkingDirectory.Remote localGitRemote :
				localGitRemotes) {

			Callable<Object> callable = new SafeCallable<Object>() {

				@Override
				public Object safeCall() {
					cacheBranch(
						gitWorkingDirectory, localGitBranch, localGitRemote,
						start);

					if (upstreamUsername.equals("liferay")) {
						LocalGitBranch upstreamLocalGitBranch =
							gitWorkingDirectory.getLocalGitBranch(
								upstreamRemoteGitBranch.getName(), true);

						gitWorkingDirectory.pushToRemote(
							true, upstreamLocalGitBranch,
							upstreamRemoteGitBranch.getName(), localGitRemote);
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

	protected static void checkoutUpstreamLocalGitBranch(
		GitWorkingDirectory gitWorkingDirectory, String upstreamBranchSHA) {

		LocalGitBranch upstreamLocalGitBranch = updateUpstreamLocalGitBranch(
			gitWorkingDirectory, upstreamBranchSHA);

		if (upstreamLocalGitBranch != null) {
			gitWorkingDirectory.checkoutLocalGitBranch(upstreamLocalGitBranch);
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

	protected static void deleteCacheLocalGitBranches(
		String excludeBranchName, GitWorkingDirectory gitWorkingDirectory) {

		for (String localGitBranchName :
				gitWorkingDirectory.getLocalGitBranchNames()) {

			if (localGitBranchName.matches(_cacheBranchPattern.pattern()) &&
				!localGitBranchName.equals(excludeBranchName)) {

				gitWorkingDirectory.deleteLocalGitBranch(localGitBranchName);
			}
		}
	}

	protected static void deleteCacheRemoteGitBranch(
		String cacheBranchName, GitWorkingDirectory gitWorkingDirectory,
		Map<String, RemoteGitBranch> remoteGitBranches) {

		List<RemoteGitBranch> cacheRemoteGitBranches = new ArrayList<>(2);

		for (Map.Entry<String, RemoteGitBranch> entry :
				remoteGitBranches.entrySet()) {

			String remoteGitBranchName = entry.getKey();

			if (!remoteGitBranchName.startsWith(cacheBranchName)) {
				continue;
			}

			cacheRemoteGitBranches.add(entry.getValue());
		}

		if (!cacheRemoteGitBranches.isEmpty()) {
			gitWorkingDirectory.deleteRemoteGitBranches(cacheRemoteGitBranches);
		}
	}

	protected static void deleteExpiredRemoteGitBranches(
		GitWorkingDirectory gitWorkingDirectory,
		GitWorkingDirectory.Remote remote, long timestamp) {

		int branchCount = 0;
		int deleteCount = 0;
		long oldestBranchAge = Long.MIN_VALUE;

		Map<String, RemoteGitBranch> remoteGitBranches = new HashMap<>();

		for (RemoteGitBranch remoteGitBranch :
				gitWorkingDirectory.getRemoteGitBranches(remote)) {

			remoteGitBranches.put(remoteGitBranch.getName(), remoteGitBranch);
		}

		List<RemoteGitBranch> expiredRemoteGitBranches = new ArrayList<>();

		for (Map.Entry<String, RemoteGitBranch> entry :
				remoteGitBranches.entrySet()) {

			RemoteGitBranch remoteGitBranch = entry.getValue();

			String remoteGitBranchName = remoteGitBranch.getName();

			Matcher matcher = _cacheBranchPattern.matcher(remoteGitBranchName);

			if (!matcher.matches()) {
				continue;
			}

			String lastBlock = matcher.group(2);

			if (lastBlock.matches("\\d+")) {
				branchCount++;

				long remoteGitBranchTimestamp = Long.parseLong(lastBlock);

				long branchAge = timestamp - remoteGitBranchTimestamp;

				if (branchAge > _BRANCH_EXPIRE_AGE_MILLIS) {
					String repositoryBaseRemoteGitBranchName =
						remoteGitBranchName.replace("-" + lastBlock, "");

					RemoteGitBranch repositoryBaseRemoteGitBranch =
						remoteGitBranches.get(
							repositoryBaseRemoteGitBranchName);

					if (repositoryBaseRemoteGitBranch != null) {
						expiredRemoteGitBranches.add(
							repositoryBaseRemoteGitBranch);
					}

					expiredRemoteGitBranches.add(remoteGitBranch);

					deleteCount++;
				}
				else {
					oldestBranchAge = Math.max(oldestBranchAge, branchAge);
				}
			}
		}

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"Deleting ", String.valueOf(expiredRemoteGitBranches.size()),
				" branches from ", remote.getName()));

		gitWorkingDirectory.deleteRemoteGitBranches(expiredRemoteGitBranches);

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"Found ", String.valueOf(branchCount), " cache branches on ",
				remote.getRemoteURL(), " ", String.valueOf(deleteCount),
				" were deleted. ", String.valueOf(branchCount - deleteCount),
				" remain. The oldest branch is ",
				JenkinsResultsParserUtil.toDurationString(oldestBranchAge),
				" old."));
	}

	protected static void deleteExpiredRemoteGitBranches(
		final GitWorkingDirectory gitWorkingDirectory,
		List<GitWorkingDirectory.Remote> localGitRemotes) {

		final long start = System.currentTimeMillis();

		List<Callable<Object>> callables = new ArrayList<>();

		for (final GitWorkingDirectory.Remote localGitRemote :
				localGitRemotes) {

			Callable<Object> callable = new SafeCallable<Object>() {

				@Override
				public Object safeCall() {
					deleteExpiredRemoteGitBranches(
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

	protected static void deleteFromAllRemotes(
		final GitWorkingDirectory gitWorkingDirectory,
		final String remoteGitBranchName,
		final List<GitWorkingDirectory.Remote> remotes) {

		final long start = System.currentTimeMillis();

		List<Callable<Boolean>> callables = new ArrayList<>();

		for (final GitWorkingDirectory.Remote remote : remotes) {
			Callable<Boolean> callable = new SafeCallable<Boolean>() {

				@Override
				public Boolean safeCall() {
					gitWorkingDirectory.deleteRemoteGitBranch(
						remoteGitBranchName, remote);

					return true;
				}

			};

			callables.add(callable);
		}

		ParallelExecutor<Boolean> parallelExecutor = new ParallelExecutor<>(
			callables, _threadPoolExecutor);

		parallelExecutor.execute();

		long duration = System.currentTimeMillis() - start;

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"Deleted ", remoteGitBranchName, " on ",
				String.valueOf(remotes.size()), " git nodes in ",
				JenkinsResultsParserUtil.toDurationString(duration)));
	}

	protected static String getCacheBranchName(
		String receiverUsername, String senderUsername, String senderSHA,
		String upstreamSHA) {

		return JenkinsResultsParserUtil.combine(
			"cache-", receiverUsername, "-", upstreamSHA, "-", senderUsername,
			"-", senderSHA);
	}

	protected static List<RemoteGitBranch> getCacheRemoteGitBranches(
		GitWorkingDirectory gitWorkingDirectory,
		GitWorkingDirectory.Remote remote) {

		List<RemoteGitBranch> cacheRemoteGitBranches = new ArrayList<>();

		Map<String, RemoteGitBranch> remoteGitBranches = new HashMap<>();

		for (RemoteGitBranch remoteGitBranch :
				gitWorkingDirectory.getRemoteGitBranches(remote)) {

			remoteGitBranches.put(remoteGitBranch.getName(), remoteGitBranch);
		}

		for (Map.Entry<String, RemoteGitBranch> entry :
				remoteGitBranches.entrySet()) {

			String remoteGitBranchName = entry.getKey();

			if (remoteGitBranchName.matches(_cacheBranchPattern.pattern())) {
				if (hasTimestampBranch(
						remoteGitBranchName, remoteGitBranches)) {

					cacheRemoteGitBranches.add(entry.getValue());
				}
				else {
					deleteCacheRemoteGitBranch(
						remoteGitBranchName, gitWorkingDirectory,
						remoteGitBranches);
				}
			}
		}

		return cacheRemoteGitBranches;
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

	protected static boolean hasTimestampBranch(
		String cacheBranchName,
		Map<String, RemoteGitBranch> remoteGitBranches) {

		for (String remoteGitBranchName : remoteGitBranches.keySet()) {
			Matcher matcher = _cacheBranchPattern.matcher(remoteGitBranchName);

			if (matcher.matches()) {
				String lastBlock = matcher.group(2);

				if (lastBlock.matches("\\d+")) {
					return true;
				}
			}
		}

		return false;
	}

	protected static boolean isRemoteGitBranch(
		String remoteGitBranchName, GitWorkingDirectory gitWorkingDirectory,
		List<GitWorkingDirectory.Remote> remotes) {

		for (GitWorkingDirectory.Remote remote : remotes) {
			try {
				if (gitWorkingDirectory.remoteGitBranchExists(
						remoteGitBranchName, remote)) {

					continue;
				}
			}
			catch (Exception e) {
				e.printStackTrace();

				continue;
			}

			return false;
		}

		return true;
	}

	protected static void pushToAllRemotes(
		final boolean force, final GitWorkingDirectory gitWorkingDirectory,
		final LocalGitBranch localGitBranch, final String remoteGitBranchName,
		final List<GitWorkingDirectory.Remote> remotes) {

		if (localGitBranch == null) {
			throw new RuntimeException("Local Git branch is null");
		}

		String localGitBranchName = localGitBranch.getName();
		LocalGitBranch currentLocalGitBranch =
			gitWorkingDirectory.getCurrentLocalGitBranch();

		if ((currentLocalGitBranch == null) ||
			!localGitBranchName.equals(currentLocalGitBranch.getName())) {

			gitWorkingDirectory.checkoutLocalGitBranch(localGitBranch, "-f");
		}

		final long start = System.currentTimeMillis();

		List<Callable<Boolean>> callables = new ArrayList<>();

		for (final GitWorkingDirectory.Remote remote : remotes) {
			Callable<Boolean> callable = new SafeCallable<Boolean>() {

				@Override
				public Boolean safeCall() {
					RemoteGitBranch remoteGitBranch =
						gitWorkingDirectory.pushToRemote(
							force, localGitBranch, remoteGitBranchName, remote);

					return Boolean.valueOf(remoteGitBranch != null);
				}

			};

			callables.add(callable);
		}

		ParallelExecutor<Boolean> parallelExecutor = new ParallelExecutor<>(
			callables, _threadPoolExecutor);

		parallelExecutor.execute();

		long duration = System.currentTimeMillis() - start;

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"Pushed ", localGitBranch.getName(), " to ",
				remoteGitBranchName, " on ", String.valueOf(remotes.size()),
				" git nodes in ",
				JenkinsResultsParserUtil.toDurationString(duration)));
	}

	protected static String synchronizeToLocalGit(
		GitWorkingDirectory gitWorkingDirectory, String receiverUsername,
		int retryCount, String senderBranchName, String senderUsername,
		String senderBranchSHA, String upstreamBranchSHA) {

		long start = System.currentTimeMillis();

		File repositoryDirectory = gitWorkingDirectory.getWorkingDirectory();

		LocalGitBranch currentLocalGitBranch =
			gitWorkingDirectory.getCurrentLocalGitBranch();

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"Starting synchronization with local-git. Current repository ",
				"directory is ", repositoryDirectory.getPath(), ". Current ",
				"branch is ", currentLocalGitBranch.getName(), "."));

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

				deleteCacheLocalGitBranches(
					cacheBranchName, gitWorkingDirectory);

				cleanUpLocalGitNodes(localGitRemotes);

				deleteExpiredRemoteGitBranches(
					gitWorkingDirectory, localGitRemotes);

				if (isRemoteGitBranch(
						cacheBranchName, gitWorkingDirectory,
						localGitRemotes)) {

					System.out.println(
						JenkinsResultsParserUtil.combine(
							"Cache branch ", cacheBranchName,
							" already exists"));

					GitWorkingDirectory.Remote localGitRemote = getRandomRemote(
						localGitRemotes);

					RemoteGitBranch cacheRemoteGitBranch =
						gitWorkingDirectory.getRemoteGitBranch(
							cacheBranchName, localGitRemote, true);

					gitWorkingDirectory.fetch(cacheRemoteGitBranch);

					gitWorkingDirectory.deleteLocalGitBranch(cacheBranchName);

					gitWorkingDirectory.createLocalGitBranch(
						cacheBranchName, true, cacheRemoteGitBranch.getSHA());

					if (!gitWorkingDirectory.localGitBranchExists(
							upstreamBranchName)) {

						updateUpstreamLocalGitBranch(
							gitWorkingDirectory, upstreamBranchSHA);
					}

					updateCacheRemoteGitBranchTimestamp(
						cacheBranchName, gitWorkingDirectory, localGitRemotes);

					return cacheBranchName;
				}

				LocalGitBranch cacheLocalGitBranch =
					gitWorkingDirectory.getLocalGitBranch(cacheBranchName);

				if (cacheLocalGitBranch == null) {
					cacheLocalGitBranch =
						gitWorkingDirectory.createLocalGitBranch(
							cacheBranchName, true);
				}

				senderBranchName = senderBranchName.trim();

				gitWorkingDirectory.fetch(
					cacheLocalGitBranch,
					gitWorkingDirectory.getRemoteGitBranch(
						senderBranchName, senderRemote, true));

				updateUpstreamLocalGitBranch(
					gitWorkingDirectory, upstreamBranchSHA);

				gitWorkingDirectory.createLocalGitBranch(
					cacheBranchName, true, senderBranchSHA);

				if (pullRequest) {
					gitWorkingDirectory.checkoutLocalGitBranch(
						cacheLocalGitBranch);

					gitWorkingDirectory.rebase(
						true,
						gitWorkingDirectory.getLocalGitBranch(
							upstreamBranchName, true),
						cacheLocalGitBranch);
				}

				cacheBranches(
					gitWorkingDirectory, cacheLocalGitBranch, localGitRemotes,
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

				gitWorkingDirectory.checkoutLocalGitBranch(
					currentLocalGitBranch);

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

				if (gitWorkingDirectory.localGitBranchExists(
						currentLocalGitBranch.getName())) {

					gitWorkingDirectory.checkoutLocalGitBranch(
						currentLocalGitBranch);
				}
				else {
					checkoutUpstreamLocalGitBranch(
						gitWorkingDirectory, upstreamBranchSHA);
				}

				gitWorkingDirectory.deleteLocalGitBranch(cacheBranchName);
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

	protected static void updateCacheRemoteGitBranchTimestamp(
		final String cacheBranchName,
		final GitWorkingDirectory gitWorkingDirectory,
		List<GitWorkingDirectory.Remote> localGitRemotes) {

		long start = System.currentTimeMillis();

		GitWorkingDirectory.Remote localGitRemote = getRandomRemote(
			localGitRemotes);

		List<RemoteGitBranch> cacheRemoteGitBranches =
			getCacheRemoteGitBranches(gitWorkingDirectory, localGitRemote);

		boolean updated = false;

		for (RemoteGitBranch cacheRemoteGitBranch : cacheRemoteGitBranches) {
			String cacheRemoteGitBranchName = cacheRemoteGitBranch.getName();

			Matcher matcher = _cacheBranchPattern.matcher(
				cacheRemoteGitBranchName);

			if (!cacheRemoteGitBranchName.contains(cacheBranchName) ||
				!matcher.matches()) {

				continue;
			}

			String lastBlock = matcher.group(2);

			if (!lastBlock.matches("\\d+")) {
				continue;
			}

			if (updated) {
				deleteFromAllRemotes(
					gitWorkingDirectory, cacheRemoteGitBranchName,
					localGitRemotes);

				continue;
			}

			long currentTimestamp = System.currentTimeMillis();
			long existingTimestamp = Long.parseLong(lastBlock);

			if ((currentTimestamp - existingTimestamp) <
					_BRANCH_UPDATE_AGE_MILLIS) {

				return;
			}

			String newTimestampBranchName = JenkinsResultsParserUtil.combine(
				cacheBranchName, "-", String.valueOf(currentTimestamp));

			System.out.println(
				JenkinsResultsParserUtil.combine(
					"Updating existing timestamp for branch ",
					cacheRemoteGitBranchName, " to ", newTimestampBranchName));

			LocalGitBranch currentLocalGitBranch =
				gitWorkingDirectory.getCurrentLocalGitBranch();

			if (currentLocalGitBranch == null) {
				currentLocalGitBranch = gitWorkingDirectory.getLocalGitBranch(
					gitWorkingDirectory.getUpstreamBranchName(), true);
			}

			LocalGitBranch newTimestampLocalGitBranch =
				gitWorkingDirectory.createLocalGitBranch(
					newTimestampBranchName);

			newTimestampLocalGitBranch = gitWorkingDirectory.fetch(
				newTimestampLocalGitBranch, cacheRemoteGitBranch);

			try {
				pushToAllRemotes(
					true, gitWorkingDirectory, newTimestampLocalGitBranch,
					newTimestampBranchName, localGitRemotes);

				deleteFromAllRemotes(
					gitWorkingDirectory, cacheRemoteGitBranchName,
					localGitRemotes);

				updated = true;
			}
			finally {
				if ((currentLocalGitBranch != null) &&
					gitWorkingDirectory.localGitBranchExists(
						currentLocalGitBranch.getName())) {

					gitWorkingDirectory.checkoutLocalGitBranch(
						currentLocalGitBranch);
				}
				else {
					checkoutUpstreamLocalGitBranch(gitWorkingDirectory, null);
				}

				gitWorkingDirectory.deleteLocalGitBranch(
					newTimestampLocalGitBranch);
			}
		}

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"Cache branch timestamp updated in ",
				JenkinsResultsParserUtil.toDurationString(
					System.currentTimeMillis() - start)));
	}

	protected static LocalGitBranch updateUpstreamLocalGitBranch(
		GitWorkingDirectory gitWorkingDirectory, String upstreamBranchSHA) {

		String upstreamBranchName = gitWorkingDirectory.getUpstreamBranchName();

		RemoteGitBranch upstreamRemoteGitBranch =
			gitWorkingDirectory.getRemoteGitBranch(
				upstreamBranchName, gitWorkingDirectory.getUpstreamRemote(),
				true);

		LocalGitBranch upstreamLocalGitBranch =
			gitWorkingDirectory.getLocalGitBranch(upstreamBranchName);

		if (upstreamLocalGitBranch == null) {
			upstreamLocalGitBranch = gitWorkingDirectory.createLocalGitBranch(
				upstreamBranchName);

			gitWorkingDirectory.fetch(
				upstreamLocalGitBranch, upstreamRemoteGitBranch);
		}

		String upstreamLocalGitBranchSHA = upstreamLocalGitBranch.getSHA();

		String upstreamRemoteGitBranchSHA = upstreamRemoteGitBranch.getSHA();

		if ((upstreamBranchSHA != null) &&
			!upstreamRemoteGitBranchSHA.equals(upstreamBranchSHA)) {

			upstreamRemoteGitBranchSHA = upstreamBranchSHA;
		}

		if (upstreamLocalGitBranchSHA.equals(upstreamRemoteGitBranchSHA)) {
			return upstreamLocalGitBranch;
		}

		gitWorkingDirectory.rebaseAbort();

		gitWorkingDirectory.clean();

		gitWorkingDirectory.reset("--hard");

		gitWorkingDirectory.fetch(upstreamRemoteGitBranch);

		String tempBranchName = "temp-" + System.currentTimeMillis();

		LocalGitBranch tempLocalGitBranch = null;

		try {
			tempLocalGitBranch = gitWorkingDirectory.createLocalGitBranch(
				tempBranchName, true, upstreamRemoteGitBranchSHA);

			gitWorkingDirectory.checkoutLocalGitBranch(
				tempLocalGitBranch, "-f");

			gitWorkingDirectory.deleteLocalGitBranch(upstreamBranchName);

			upstreamLocalGitBranch = gitWorkingDirectory.createLocalGitBranch(
				upstreamRemoteGitBranch.getName(), true,
				upstreamRemoteGitBranchSHA);

			gitWorkingDirectory.checkoutLocalGitBranch(upstreamLocalGitBranch);
		}
		finally {
			if (tempLocalGitBranch != null) {
				gitWorkingDirectory.deleteLocalGitBranch(tempLocalGitBranch);
			}
		}

		return upstreamLocalGitBranch;
	}

	protected static List<String> validateLocalGitRemoteURLs(
		List<String> localGitRemoteURLs,
		final GitWorkingDirectory gitWorkingDirectory) {

		List<Callable<String>> callables = new ArrayList<>();

		for (final String localGitRemoteURL : localGitRemoteURLs) {
			Callable<String> callable = new SafeCallable<String>() {

				@Override
				public String safeCall() {
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

	private static final long _BRANCH_UPDATE_AGE_MILLIS = 1000 * 60 * 60 * 24;

	private static final Pattern _cacheBranchPattern = Pattern.compile(
		"cache(-([^-]+))+");
	private static final ThreadPoolExecutor _threadPoolExecutor =
		JenkinsResultsParserUtil.getNewThreadPoolExecutor(16, true);

	private abstract static class SafeCallable<T> implements Callable<T> {

		@Override
		public final T call() {
			try {
				return safeCall();
			}
			catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}

		public abstract T safeCall();

	}

}