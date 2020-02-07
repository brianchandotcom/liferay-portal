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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Hashimoto
 * @author Peter Yoo
 */
public class GitHubDevSyncUtil {

	public static void clone(String repositoryName, File workingDirectory) {
		List<String> usedGitHubDevRemoteHostnames = new ArrayList<>();

		while (true) {
			String gitHubDevRemoteHostname =
				JenkinsResultsParserUtil.getRandomGitHubDevNodeHostname(
					usedGitHubDevRemoteHostnames);

			usedGitHubDevRemoteHostnames.add(gitHubDevRemoteHostname);

			String gitHubDevRemoteURL = JenkinsResultsParserUtil.combine(
				"git@", gitHubDevRemoteHostname, ":liferay/", repositoryName);

			try {
				GitUtil.clone(gitHubDevRemoteURL, workingDirectory);
			}
			catch (Exception exception) {
				String message = JenkinsResultsParserUtil.combine(
					"Unable to clone ", repositoryName, " from ",
					gitHubDevRemoteURL, ".");

				if (usedGitHubDevRemoteHostnames.size() == 3) {
					throw new RuntimeException(message, exception);
				}

				System.out.println("Retrying: " + message);
			}

			break;
		}
	}

	public static LocalGitBranch createCacheLocalGitBranch(
		LocalGitRepository localGitRepository, LocalGitBranch localGitBranch,
		boolean synchronize) {

		return _createCacheLocalGitBranch(
			localGitRepository, "liferay", localGitBranch.getName(), "liferay",
			localGitBranch.getSHA(), localGitBranch.getSHA(), synchronize);
	}

	public static LocalGitBranch createCacheLocalGitBranch(
		LocalGitRepository localGitRepository, PullRequest pullRequest,
		boolean synchronize) {

		return _createCacheLocalGitBranch(
			localGitRepository, pullRequest.getReceiverUsername(),
			pullRequest.getSenderBranchName(), pullRequest.getSenderUsername(),
			pullRequest.getSenderSHA(), pullRequest.getLiferayRemoteBranchSHA(),
			synchronize);
	}

	public static LocalGitBranch createCacheLocalGitBranch(
		LocalGitRepository localGitRepository, RemoteGitRef remoteGitRef,
		boolean synchronize) {

		return _createCacheLocalGitBranch(
			localGitRepository, remoteGitRef.getUsername(),
			remoteGitRef.getName(), remoteGitRef.getUsername(),
			remoteGitRef.getSHA(), remoteGitRef.getSHA(), synchronize);
	}

	public static LocalGitBranch createCacheLocalGitBranch(
		LocalGitRepository localGitRepository, String name, String sha,
		boolean synchronize) {

		return _createCacheLocalGitBranch(
			localGitRepository, "liferay", name, "liferay", sha, sha,
			synchronize);
	}

	public static RemoteGitBranch fetchCacheBranchFromGitHubDev(
		GitWorkingDirectory gitWorkingDirectory, String cacheBranchName) {

		List<GitRemote> gitHubDevGitRemotes = getGitHubDevGitRemotes(
			gitWorkingDirectory);

		try {
			int retries = 0;

			while ((retries < 10) && !gitHubDevGitRemotes.isEmpty()) {
				retries++;

				GitRemote gitHubDevGitRemote = getRandomGitRemote(
					gitHubDevGitRemotes);

				gitHubDevGitRemotes.remove(gitHubDevGitRemote);

				try {
					RemoteGitBranch cacheRemoteGitBranch =
						gitWorkingDirectory.getRemoteGitBranch(
							cacheBranchName, gitHubDevGitRemote, true);

					gitWorkingDirectory.fetch(cacheRemoteGitBranch);

					return cacheRemoteGitBranch;
				}
				catch (Exception exception) {
					String message = JenkinsResultsParserUtil.combine(
						"Unable to fetch ", cacheBranchName, " from ",
						gitHubDevGitRemote.getHostname());

					if (retries == 10) {
						throw new RuntimeException(message, exception);
					}

					JenkinsResultsParserUtil.sleep(30000);

					System.out.println("Retrying: " + message);
				}
				finally {
					gitWorkingDirectory.removeGitRemote(gitHubDevGitRemote);
				}
			}

			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to fetch ", cacheBranchName,
					" from git@github-dev.com"));
		}
		finally {
			gitWorkingDirectory.removeGitRemotes(gitHubDevGitRemotes);
		}
	}

	public static String getCacheBranchName(PullRequest pullRequest) {
		return getCacheBranchName(
			pullRequest.getReceiverUsername(), pullRequest.getSenderUsername(),
			pullRequest.getSenderSHA(),
			pullRequest.getLiferayRemoteBranchSHA());
	}

	public static String getCacheBranchName(RemoteGitRef remoteGitRef) {
		return getCacheBranchName(
			remoteGitRef.getUsername(), remoteGitRef.getUsername(),
			remoteGitRef.getSHA(), remoteGitRef.getSHA());
	}

	public static List<GitRemote> getGitHubDevGitRemotes(
		GitWorkingDirectory gitWorkingDirectory) {

		List<String> gitHubDevRemoteURLs = getGitHubDevRemoteURLs(
			gitWorkingDirectory);

		List<GitRemote> gitHubDevGitRemotes = new ArrayList<>(
			gitHubDevRemoteURLs.size());

		for (String gitHubDevRemoteURL : gitHubDevRemoteURLs) {
			String gitHubDevRemoteName =
				"git-hub-dev-remote-" +
					gitHubDevRemoteURLs.indexOf(gitHubDevRemoteURL);

			GitRemote gitRemote = gitWorkingDirectory.getGitRemote(
				gitHubDevRemoteName);

			if ((gitRemote == null) ||
				!gitHubDevRemoteURL.equals(gitRemote.getRemoteURL())) {

				gitRemote = gitWorkingDirectory.addGitRemote(
					true, gitHubDevRemoteName, gitHubDevRemoteURL);
			}

			gitHubDevGitRemotes.add(gitRemote);
		}

		return gitHubDevGitRemotes;
	}

	public static String synchronizeToGitHubDev(
			GitWorkingDirectory gitWorkingDirectory, String receiverUsername,
			String senderBranchName, String senderUsername,
			String senderBranchSHA, String upstreamBranchSHA)
		throws IOException {

		return synchronizeToGitHubDev(
			gitWorkingDirectory, receiverUsername, 0, senderBranchName,
			senderUsername, senderBranchSHA, upstreamBranchSHA);
	}

	protected static void cacheBranch(
		GitWorkingDirectory gitWorkingDirectory, LocalGitBranch localGitBranch,
		GitRemote gitRemote, long timestamp) {

		RemoteGitBranch lockRemoteGitBranch = null;

		try {
			lockRemoteGitBranch = gitWorkingDirectory.pushToRemoteGitRepository(
				true, localGitBranch, localGitBranch.getName() + "-LOCK",
				gitRemote);

			gitWorkingDirectory.pushToRemoteGitRepository(
				true, localGitBranch, localGitBranch.getName(), gitRemote);

			gitWorkingDirectory.pushToRemoteGitRepository(
				true, localGitBranch,
				JenkinsResultsParserUtil.combine(
					localGitBranch.getName(), "-", String.valueOf(timestamp)),
				gitRemote);
		}
		finally {
			if (lockRemoteGitBranch != null) {
				gitWorkingDirectory.deleteRemoteGitBranch(lockRemoteGitBranch);
			}
		}
	}

	protected static void cacheBranches(
		final GitWorkingDirectory gitWorkingDirectory,
		final LocalGitBranch localGitBranch,
		List<GitRemote> gitHubDevGitRemotes, final String upstreamUsername) {

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
				gitWorkingDirectory.getGitRemote("upstream"), true);

		List<Callable<Object>> callables = new ArrayList<>();

		for (final GitRemote gitHubDevGitRemote : gitHubDevGitRemotes) {
			Callable<Object> callable = new SafeCallable<Object>() {

				@Override
				public Object safeCall() {
					cacheBranch(
						gitWorkingDirectory, localGitBranch, gitHubDevGitRemote,
						start);

					if (upstreamUsername.equals("liferay")) {
						LocalGitBranch upstreamLocalGitBranch =
							gitWorkingDirectory.getLocalGitBranch(
								upstreamRemoteGitBranch.getName(), true);

						gitWorkingDirectory.pushToRemoteGitRepository(
							true, upstreamLocalGitBranch,
							upstreamRemoteGitBranch.getName(),
							gitHubDevGitRemote);
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

	protected static void deleteExpiredCacheBranches(
		GitRemote gitRemote, long timestamp) {

		int branchCount = 0;
		int deleteCount = 0;
		long oldestBranchAge = Long.MIN_VALUE;

		Map<String, RemoteGitBranch> remoteGitBranches = new HashMap<>();

		GitWorkingDirectory gitWorkingDirectory =
			gitRemote.getGitWorkingDirectory();

		for (RemoteGitBranch remoteGitBranch :
				gitWorkingDirectory.getRemoteGitBranches(gitRemote)) {

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

			if (!lastBlock.matches("\\d+")) {
				continue;
			}

			branchCount++;

			long remoteGitBranchTimestamp = Long.parseLong(lastBlock);

			long branchAge = timestamp - remoteGitBranchTimestamp;

			if (branchAge > _MILLIS_BRANCH_EXPIRATION) {
				String gitRepositoryBaseRemoteGitBranchName =
					remoteGitBranchName.replaceAll("(.*)-\\d+", "$1");

				RemoteGitBranch gitRepositoryBaseRemoteGitBranch =
					remoteGitBranches.get(gitRepositoryBaseRemoteGitBranchName);

				if (gitRepositoryBaseRemoteGitBranch != null) {
					expiredRemoteGitBranches.add(
						gitRepositoryBaseRemoteGitBranch);
				}

				expiredRemoteGitBranches.add(remoteGitBranch);

				deleteCount++;
			}
			else {
				oldestBranchAge = Math.max(oldestBranchAge, branchAge);
			}
		}

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"Deleting ", String.valueOf(expiredRemoteGitBranches.size()),
				" branches from ", gitRemote.getRemoteURL()));

		gitWorkingDirectory.deleteRemoteGitBranches(expiredRemoteGitBranches);

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"Found ", String.valueOf(branchCount), " cache branches on ",
				gitRemote.getRemoteURL(), " ", String.valueOf(deleteCount),
				" were deleted. ", String.valueOf(branchCount - deleteCount),
				" remain. The oldest branch is ",
				JenkinsResultsParserUtil.toDurationString(oldestBranchAge),
				" old."));
	}

	protected static void deleteExpiredRemoteGitBranches(
		final GitWorkingDirectory gitWorkingDirectory,
		List<GitRemote> gitHubDevGitRemotes) {

		final long start = System.currentTimeMillis();

		List<Callable<Object>> callables = new ArrayList<>();

		for (final GitRemote gitHubDevGitRemote : gitHubDevGitRemotes) {
			Callable<Object> callable = new SafeCallable<Object>() {

				@Override
				public Object safeCall() {
					deleteExpiredCacheBranches(gitHubDevGitRemote, start);

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

	protected static void deleteExtraTimestampBranches(
		GitRemote gitHubDevGitRemote) {

		GitWorkingDirectory gitWorkingDirectory =
			gitHubDevGitRemote.getGitWorkingDirectory();

		List<RemoteGitBranch> remoteGitBranches =
			gitWorkingDirectory.getRemoteGitBranches(gitHubDevGitRemote);

		Collections.sort(remoteGitBranches);

		Map<String, List<RemoteGitBranch>> remoteGitBranchesMap =
			new HashMap<>();

		for (RemoteGitBranch remoteGitBranch : remoteGitBranches) {
			String remoteGitBranchName = remoteGitBranch.getName();

			if (remoteGitBranchName.matches(
					_cacheBranchPattern.pattern() + "-\\d+")) {

				String baseCacheBranchName = remoteGitBranchName.replaceAll(
					"(.*)-\\d+", "$1");

				List<RemoteGitBranch> timestampedRemoteGitBranches =
					remoteGitBranchesMap.computeIfAbsent(
						baseCacheBranchName, key -> new ArrayList<>());

				timestampedRemoteGitBranches.add(remoteGitBranch);
			}
		}

		for (Map.Entry<String, List<RemoteGitBranch>> entry :
				remoteGitBranchesMap.entrySet()) {

			List<RemoteGitBranch> timestampedRemoteGitBranches =
				entry.getValue();

			if (timestampedRemoteGitBranches.size() > 1) {
				timestampedRemoteGitBranches.remove(
					timestampedRemoteGitBranches.size() - 1);

				gitWorkingDirectory.deleteRemoteGitBranches(
					timestampedRemoteGitBranches);
			}
		}
	}

	protected static void deleteExtraTimestampBranches(
		List<GitRemote> gitHubDevGitRemotes) {

		long start = System.currentTimeMillis();

		List<Callable<Object>> callables = new ArrayList<>();

		for (final GitRemote gitHubDevGitRemote : gitHubDevGitRemotes) {
			Callable<Object> callable = new SafeCallable<Object>() {

				@Override
				public Object safeCall() {
					deleteExtraTimestampBranches(gitHubDevGitRemote);

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

	protected static void deleteFromAllRemotes(
		final String remoteGitBranchName, final List<GitRemote> gitRemotes) {

		final long start = System.currentTimeMillis();

		List<Callable<Boolean>> callables = new ArrayList<>();

		for (final GitRemote gitRemote : gitRemotes) {
			Callable<Boolean> callable = new SafeCallable<Boolean>() {

				@Override
				public Boolean safeCall() {
					GitWorkingDirectory gitWorkingDirectory =
						gitRemote.getGitWorkingDirectory();

					gitWorkingDirectory.deleteRemoteGitBranch(
						remoteGitBranchName, gitRemote);

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
				String.valueOf(gitRemotes.size()), " git nodes in ",
				JenkinsResultsParserUtil.toDurationString(duration)));
	}

	protected static void deleteOrphanedCacheBranches(GitRemote gitRemote) {
		List<RemoteGitBranch> cacheRemoteGitBranches =
			getCacheRemoteGitBranches(gitRemote);

		Map<String, RemoteGitBranch> baseCacheRemoteGitBranchesMap =
			new HashMap<>();

		Map<String, RemoteGitBranch> timestampedCacheRemoteGitBranchMap =
			new HashMap<>();

		for (RemoteGitBranch cacheRemoteGitBranch : cacheRemoteGitBranches) {
			String cacheRemoteGitBranchName = cacheRemoteGitBranch.getName();

			if (cacheRemoteGitBranchName.matches(
					_cacheBranchPattern.pattern())) {

				if (cacheRemoteGitBranchName.matches(
						_cacheBranchPattern.pattern() + "-\\d+")) {

					timestampedCacheRemoteGitBranchMap.put(
						cacheRemoteGitBranchName, cacheRemoteGitBranch);
				}
				else {
					baseCacheRemoteGitBranchesMap.put(
						cacheRemoteGitBranchName, cacheRemoteGitBranch);
				}
			}
		}

		Map<String, RemoteGitBranch> orphanedBaseCacheRemoteGitBranchesMap =
			new HashMap<>(baseCacheRemoteGitBranchesMap);
		Map<String, RemoteGitBranch>
			orphanedTimestampedCacheRemoteGitBranchesMap = new HashMap<>(
				timestampedCacheRemoteGitBranchMap);

		for (String baseCacheRemoteGitBranchName :
				baseCacheRemoteGitBranchesMap.keySet()) {

			String timestampedCacheRemoteGitBranchNamePattern =
				Pattern.quote(baseCacheRemoteGitBranchName) + "-\\d+";

			for (String timestampedCacheRemoteGitBranchName :
					timestampedCacheRemoteGitBranchMap.keySet()) {

				if (timestampedCacheRemoteGitBranchName.matches(
						timestampedCacheRemoteGitBranchNamePattern)) {

					orphanedBaseCacheRemoteGitBranchesMap.remove(
						baseCacheRemoteGitBranchName);
				}
			}
		}

		for (String timestampedCacheRemoteGitBranchName :
				timestampedCacheRemoteGitBranchMap.keySet()) {

			String baseCacheRemoteGitBranchName =
				timestampedCacheRemoteGitBranchName.replaceAll(
					"(.*)-\\d+", "$1");

			if (baseCacheRemoteGitBranchesMap.containsKey(
					baseCacheRemoteGitBranchName)) {

				orphanedTimestampedCacheRemoteGitBranchesMap.remove(
					timestampedCacheRemoteGitBranchName);
			}
		}

		StringBuilder sb = new StringBuilder();

		for (String orphanedBaseCacheRemoteGitBranchName :
				orphanedBaseCacheRemoteGitBranchesMap.keySet()) {

			sb.append(orphanedBaseCacheRemoteGitBranchName);
			sb.append("\n");
		}

		for (String orphanedTimestampedCacheRemoteGitBranchName :
				orphanedTimestampedCacheRemoteGitBranchesMap.keySet()) {

			sb.append(orphanedTimestampedCacheRemoteGitBranchName);
			sb.append("\n");
		}

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"Found ",
				String.valueOf(orphanedBaseCacheRemoteGitBranchesMap.size()),
				" orphaned base cache branches ", "and ",
				String.valueOf(
					orphanedTimestampedCacheRemoteGitBranchesMap.size()),
				" orphaned timestamp branches on ", gitRemote.getRemoteURL(),
				".\n", sb.toString()));

		List<RemoteGitBranch> orphanedCacheRemoteGitBranches = new ArrayList<>(
			orphanedBaseCacheRemoteGitBranchesMap.size() +
				orphanedTimestampedCacheRemoteGitBranchesMap.size());

		orphanedCacheRemoteGitBranches.addAll(
			orphanedBaseCacheRemoteGitBranchesMap.values());
		orphanedCacheRemoteGitBranches.addAll(
			orphanedTimestampedCacheRemoteGitBranchesMap.values());

		GitWorkingDirectory gitWorkingDirectory =
			gitRemote.getGitWorkingDirectory();

		gitWorkingDirectory.deleteRemoteGitBranches(
			orphanedCacheRemoteGitBranches);
	}

	protected static void deleteOrphanedCacheBranches(
		List<GitRemote> gitRemotes) {

		List<Callable<Object>> callables = new ArrayList<>(gitRemotes.size());

		for (final GitRemote gitRemote : gitRemotes) {
			Callable<Object> callable = new SafeCallable<Object>() {

				@Override
				public Object safeCall() {
					deleteOrphanedCacheBranches(gitRemote);

					return null;
				}

			};

			callables.add(callable);
		}

		ParallelExecutor<Object> parallelExecutor = new ParallelExecutor<>(
			callables, _threadPoolExecutor);

		parallelExecutor.execute();
	}

	protected static String getCacheBranchName(
		String receiverUsername, String senderUsername, String senderSHA,
		String upstreamSHA) {

		return JenkinsResultsParserUtil.combine(
			"cache-", receiverUsername, "-", upstreamSHA, "-", senderUsername,
			"-", senderSHA);
	}

	protected static List<RemoteGitBranch> getCacheRemoteGitBranches(
		GitRemote gitRemote) {

		List<RemoteGitBranch> cacheRemoteGitBranches = new ArrayList<>();
		Set<String> lockedBaseCacheRemoteGitBranchNames = new HashSet<>();
		Map<String, RemoteGitBranch> remoteGitBranches = new HashMap<>();

		GitWorkingDirectory gitWorkingDirectory =
			gitRemote.getGitWorkingDirectory();

		for (RemoteGitBranch remoteGitBranch :
				gitWorkingDirectory.getRemoteGitBranches(gitRemote)) {

			Matcher matcher = _lockedCacheBranchPattern.matcher(
				remoteGitBranch.getName());

			if (matcher.matches()) {
				lockedBaseCacheRemoteGitBranchNames.add(matcher.group(1));

				continue;
			}

			remoteGitBranches.put(remoteGitBranch.getName(), remoteGitBranch);
		}

		for (String remoteGitBranchName :
				new HashSet<>(remoteGitBranches.keySet())) {

			for (String lockedBaseCacheRemoteGitBranchName :
					lockedBaseCacheRemoteGitBranchNames) {

				if (remoteGitBranchName.startsWith(
						lockedBaseCacheRemoteGitBranchName)) {

					remoteGitBranches.remove(remoteGitBranchName);

					System.out.println(
						JenkinsResultsParserUtil.combine(
							"Ignoring ", remoteGitBranchName,
							" because this branch is currently locked."));

					break;
				}
			}
		}

		for (Map.Entry<String, RemoteGitBranch> entry :
				remoteGitBranches.entrySet()) {

			String remoteGitBranchName = entry.getKey();

			if (remoteGitBranchName.matches(_cacheBranchPattern.pattern())) {
				if (hasTimestampBranch(remoteGitBranches)) {
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

	protected static List<String> getGitHubDevNodeHostnames() {
		if (gitHubDevNodeHostnames != null) {
			return new ArrayList<>(gitHubDevNodeHostnames);
		}

		gitHubDevNodeHostnames =
			JenkinsResultsParserUtil.getGitHubCacheHostnames();

		return gitHubDevNodeHostnames;
	}

	protected static List<String> getGitHubDevRemoteURLs(
		GitWorkingDirectory gitWorkingDirectory) {

		List<String> gitHubDevRemoteURLs = new ArrayList<>();

		for (String gitHubDevNodeHostname : getGitHubDevNodeHostnames()) {
			if (gitHubDevNodeHostname.startsWith("slave-")) {
				gitHubDevRemoteURLs.add(
					JenkinsResultsParserUtil.combine(
						"root@", gitHubDevNodeHostname.substring(6),
						":/opt/dev/projects/github/",
						gitWorkingDirectory.getGitRepositoryName()));

				continue;
			}

			gitHubDevRemoteURLs.add(
				JenkinsResultsParserUtil.combine(
					"git@", gitHubDevNodeHostname, ":",
					gitWorkingDirectory.getGitRepositoryUsername(), "/",
					gitWorkingDirectory.getGitRepositoryName(), ".git"));
		}

		return gitHubDevRemoteURLs;
	}

	protected static String getGitHubRemoteURL(
		String repositoryName, String userName) {

		return JenkinsResultsParserUtil.combine(
			"git@github.com:", userName, "/", repositoryName, ".git");
	}

	protected static GitRemote getRandomGitRemote(List<GitRemote> gitRemotes) {
		return gitRemotes.get(
			JenkinsResultsParserUtil.getRandomValue(0, gitRemotes.size() - 1));
	}

	protected static boolean hasTimestampBranch(
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

	protected static void pushToAllRemotes(
		final boolean force, final LocalGitBranch localGitBranch,
		final String remoteGitBranchName, final List<GitRemote> gitRemotes) {

		if (localGitBranch == null) {
			throw new RuntimeException("Local Git branch is null");
		}

		final long start = System.currentTimeMillis();

		List<Callable<Boolean>> callables = new ArrayList<>();

		for (final GitRemote gitRemote : gitRemotes) {
			Callable<Boolean> callable = new SafeCallable<Boolean>() {

				@Override
				public Boolean safeCall() {
					GitWorkingDirectory gitWorkingDirectory =
						gitRemote.getGitWorkingDirectory();

					RemoteGitBranch remoteGitBranch =
						gitWorkingDirectory.pushToRemoteGitRepository(
							force, localGitBranch, remoteGitBranchName,
							gitRemote);

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
				remoteGitBranchName, " on ", String.valueOf(gitRemotes.size()),
				" git nodes in ",
				JenkinsResultsParserUtil.toDurationString(duration)));
	}

	protected static boolean remoteGitBranchExists(
		final String remoteGitBranchName,
		final GitWorkingDirectory gitWorkingDirectory,
		List<GitRemote> gitRemotes) {

		List<Callable<Boolean>> callables = new ArrayList<>(gitRemotes.size());

		for (final GitRemote gitRemote : gitRemotes) {
			Callable<Boolean> callable = new SafeCallable<Boolean>() {

				@Override
				public Boolean safeCall() {
					try {
						return gitWorkingDirectory.remoteGitBranchExists(
							remoteGitBranchName, gitRemote);
					}
					catch (Exception exception) {
						exception.printStackTrace();

						return true;
					}
				}

			};

			callables.add(callable);
		}

		ParallelExecutor<Boolean> parallelExecutor = new ParallelExecutor<>(
			callables, _threadPoolExecutor);

		for (Boolean bool : parallelExecutor.execute()) {
			if (!bool) {
				return false;
			}
		}

		return true;
	}

	protected static String synchronizeToGitHubDev(
		GitWorkingDirectory gitWorkingDirectory, String receiverUsername,
		int retryCount, String senderBranchName, String senderUsername,
		String senderBranchSHA, String upstreamBranchSHA) {

		long start = System.currentTimeMillis();

		File gitRepositoryDirectory = gitWorkingDirectory.getWorkingDirectory();

		LocalGitBranch currentLocalGitBranch =
			gitWorkingDirectory.getCurrentLocalGitBranch();

		if (currentLocalGitBranch == null) {
			LocalGitBranch localUpstreamGitBranch =
				gitWorkingDirectory.getUpstreamLocalGitBranch();

			gitWorkingDirectory.checkoutLocalGitBranch(localUpstreamGitBranch);

			currentLocalGitBranch = localUpstreamGitBranch;
		}

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"Starting synchronization with local-git. Current repository ",
				"directory is ", gitRepositoryDirectory.getPath(), ". Current ",
				"branch is ", currentLocalGitBranch.getName(), "."));

		GitRemote senderGitRemote = null;

		try {
			senderGitRemote = gitWorkingDirectory.addGitRemote(
				true, "sender-temp",
				getGitHubRemoteURL(
					gitWorkingDirectory.getGitRepositoryName(),
					senderUsername));

			String cacheBranchName = getCacheBranchName(
				receiverUsername, senderUsername, senderBranchSHA,
				upstreamBranchSHA);

			String upstreamBranchName =
				gitWorkingDirectory.getUpstreamBranchName();

			List<GitRemote> gitHubDevGitRemotes = null;

			try {
				gitHubDevGitRemotes = getGitHubDevGitRemotes(
					gitWorkingDirectory);

				deleteCacheLocalGitBranches(
					cacheBranchName, gitWorkingDirectory);

				if (JenkinsResultsParserUtil.getRandomValue(1, 10) == 5) {
					deleteExtraTimestampBranches(gitHubDevGitRemotes);

					deleteOrphanedCacheBranches(gitHubDevGitRemotes);

					deleteExpiredRemoteGitBranches(
						gitWorkingDirectory, gitHubDevGitRemotes);
				}

				RemoteGitBranch cacheRemoteGitBranch = null;

				try {
					cacheRemoteGitBranch = fetchCacheBranchFromGitHubDev(
						gitWorkingDirectory, cacheBranchName);
				}
				catch (Exception exception) {
					cacheRemoteGitBranch = null;

					System.out.println(
						JenkinsResultsParserUtil.combine(
							"Cache branch ", cacheBranchName,
							" does not exist"));
				}

				if (cacheRemoteGitBranch != null) {
					System.out.println(
						JenkinsResultsParserUtil.combine(
							"Cache branch ", cacheBranchName,
							" already exists"));

					gitWorkingDirectory.deleteLocalGitBranch(cacheBranchName);

					gitWorkingDirectory.createLocalGitBranch(
						cacheBranchName, true, cacheRemoteGitBranch.getSHA());

					if (!gitWorkingDirectory.localGitBranchExists(
							upstreamBranchName)) {

						updateUpstreamLocalGitBranch(
							gitWorkingDirectory, upstreamBranchSHA);
					}

					updateCacheRemoteGitBranchTimestamp(
						cacheBranchName, gitWorkingDirectory,
						gitHubDevGitRemotes);

					return cacheBranchName;
				}

				senderBranchName = senderBranchName.trim();

				LocalGitBranch cacheLocalGitBranch =
					gitWorkingDirectory.getRebasedLocalGitBranch(
						cacheBranchName, senderBranchName,
						senderGitRemote.getRemoteURL(), senderBranchSHA,
						upstreamBranchName, upstreamBranchSHA);

				cacheBranches(
					gitWorkingDirectory, cacheLocalGitBranch,
					gitHubDevGitRemotes, "liferay");

				return cacheBranchName;
			}
			catch (Exception exception) {
				if (retryCount == 1) {
					throw exception;
				}

				gitHubDevGitRemotes = null;
				senderGitRemote = null;

				System.out.println(
					"Synchronization with local-git failed. Retrying.");

				exception.printStackTrace();

				gitWorkingDirectory.checkoutLocalGitBranch(
					currentLocalGitBranch);

				return synchronizeToGitHubDev(
					gitWorkingDirectory, receiverUsername, retryCount + 1,
					senderBranchName, senderUsername, senderBranchSHA,
					upstreamBranchSHA);
			}
			finally {
				if (gitHubDevGitRemotes != null) {
					try {
						gitWorkingDirectory.removeGitRemotes(
							gitHubDevGitRemotes);
					}
					catch (Exception exception) {
						exception.printStackTrace();
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
			if (senderGitRemote != null) {
				try {
					gitWorkingDirectory.removeGitRemote(senderGitRemote);
				}
				catch (Exception exception) {
					exception.printStackTrace();
				}
			}

			String durationString = JenkinsResultsParserUtil.toDurationString(
				System.currentTimeMillis() - start);

			System.out.println(
				"Synchronization with local Git completed in " +
					durationString);
		}
	}

	protected static void updateCacheRemoteGitBranchTimestamp(
		final String cacheBranchName,
		final GitWorkingDirectory gitWorkingDirectory,
		List<GitRemote> gitHubDevGitRemotes) {

		long start = System.currentTimeMillis();

		List<RemoteGitBranch> cacheRemoteGitBranches = null;
		GitRemote gitHubDevGitRemote = null;

		while (cacheRemoteGitBranches == null) {
			try {
				gitHubDevGitRemote = getRandomGitRemote(gitHubDevGitRemotes);

				cacheRemoteGitBranches = getCacheRemoteGitBranches(
					gitHubDevGitRemote);
			}
			catch (Exception exception) {
				exception.printStackTrace();

				gitHubDevGitRemotes.remove(gitHubDevGitRemote);

				if (gitHubDevGitRemotes.isEmpty()) {
					throw new RuntimeException(
						"No remote repositories could be reached", exception);
				}
			}
		}

		RemoteGitBranch oldTimestampCacheRemoteGitBranch = null;

		Pattern pattern = Pattern.compile(
			Pattern.quote(cacheBranchName) + "-(\\d+)");

		for (RemoteGitBranch cacheRemoteGitBranch : cacheRemoteGitBranches) {
			Matcher matcher = pattern.matcher(cacheRemoteGitBranch.getName());

			if (!matcher.matches()) {
				continue;
			}

			long existingTimestamp = Long.parseLong(matcher.group(1));

			if ((System.currentTimeMillis() - existingTimestamp) >
					_MILLIS_BRANCH_UPDATE_AGE) {

				oldTimestampCacheRemoteGitBranch = cacheRemoteGitBranch;
			}

			break;
		}

		if (oldTimestampCacheRemoteGitBranch == null) {
			return;
		}

		String newTimestampCacheRemoteBranchName =
			JenkinsResultsParserUtil.combine(
				cacheBranchName, "-",
				String.valueOf(System.currentTimeMillis()));

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"Updating existing timestamp for branch ",
				oldTimestampCacheRemoteGitBranch.getName(), " to ",
				newTimestampCacheRemoteBranchName));

		LocalGitBranch originalCheckedOutLocalGitBranch =
			gitWorkingDirectory.getCurrentLocalGitBranch();

		if (originalCheckedOutLocalGitBranch == null) {
			originalCheckedOutLocalGitBranch =
				gitWorkingDirectory.getUpstreamLocalGitBranch();
		}

		LocalGitBranch newTimestampLocalGitBranch =
			gitWorkingDirectory.createLocalGitBranch(
				newTimestampCacheRemoteBranchName);

		newTimestampLocalGitBranch = gitWorkingDirectory.fetch(
			newTimestampLocalGitBranch, oldTimestampCacheRemoteGitBranch);

		try {
			pushToAllRemotes(
				true, newTimestampLocalGitBranch,
				newTimestampCacheRemoteBranchName, gitHubDevGitRemotes);

			deleteFromAllRemotes(
				oldTimestampCacheRemoteGitBranch.getName(),
				gitHubDevGitRemotes);
		}
		finally {
			gitWorkingDirectory.checkoutLocalGitBranch(
				originalCheckedOutLocalGitBranch);

			gitWorkingDirectory.deleteLocalGitBranch(
				newTimestampLocalGitBranch);
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
				upstreamBranchName, gitWorkingDirectory.getUpstreamGitRemote(),
				true);

		LocalGitBranch upstreamLocalGitBranch =
			gitWorkingDirectory.getUpstreamLocalGitBranch();

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

	protected static List<String> gitHubDevNodeHostnames;

	private static LocalGitBranch _createCacheLocalGitBranch(
		LocalGitRepository localGitRepository, String receiverUsername,
		String senderBranchName, String senderUsername, String senderBranchSHA,
		String upstreamBranchSHA, boolean synchronize) {

		GitWorkingDirectory gitWorkingDirectory =
			localGitRepository.getGitWorkingDirectory();

		if (!JenkinsResultsParserUtil.isCINode()) {
			return gitWorkingDirectory.getRebasedLocalGitBranch(
				JenkinsResultsParserUtil.combine(
					gitWorkingDirectory.getUpstreamBranchName(), "-temp-",
					String.valueOf(System.currentTimeMillis())),
				senderBranchName,
				JenkinsResultsParserUtil.combine(
					"git@github.com:", senderUsername, "/",
					localGitRepository.getName()),
				senderBranchSHA, gitWorkingDirectory.getUpstreamBranchName(),
				upstreamBranchSHA);
		}

		if (synchronize) {
			synchronizeToGitHubDev(
				gitWorkingDirectory, receiverUsername, 0, senderBranchName,
				senderUsername, senderBranchSHA, upstreamBranchSHA);
		}

		String cacheBranchName = getCacheBranchName(
			receiverUsername, senderUsername, senderBranchSHA,
			upstreamBranchSHA);

		LocalGitBranch cacheLocalGitBranch = GitBranchFactory.newLocalGitBranch(
			localGitRepository,
			JenkinsResultsParserUtil.combine(
				gitWorkingDirectory.getUpstreamBranchName(), "-temp-",
				String.valueOf(System.currentTimeMillis())),
			upstreamBranchSHA);

		RemoteGitBranch cacheRemoteGitBranch =
			gitWorkingDirectory.getRemoteGitBranch(
				cacheBranchName,
				getRandomGitRemote(
					getGitHubDevGitRemotes(gitWorkingDirectory)));

		return gitWorkingDirectory.fetch(
			cacheLocalGitBranch, cacheRemoteGitBranch);
	}

	private static final long _MILLIS_BRANCH_EXPIRATION =
		1000 * 60 * 60 * 24 * 2;

	private static final long _MILLIS_BRANCH_UPDATE_AGE = 1000 * 60 * 60 * 24;

	private static final Pattern _cacheBranchPattern = Pattern.compile(
		"cache(-([^-]+))+");
	private static final Pattern _lockedCacheBranchPattern = Pattern.compile(
		"(cache-.*)-LOCK");
	private static final ThreadPoolExecutor _threadPoolExecutor =
		JenkinsResultsParserUtil.getNewThreadPoolExecutor(16, true);

	private abstract static class SafeCallable<T> implements Callable<T> {

		@Override
		public final T call() {
			try {
				return safeCall();
			}
			catch (Exception exception) {
				exception.printStackTrace();
			}

			return null;
		}

		public abstract T safeCall();

	}

}