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

import com.jcraft.jsch.Session;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.net.URISyntaxException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.CleanCommand;
import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.CreateBranchCommand;
import org.eclipse.jgit.api.DeleteBranchCommand;
import org.eclipse.jgit.api.FetchCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.api.ListBranchCommand.ListMode;
import org.eclipse.jgit.api.LsRemoteCommand;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.RebaseCommand;
import org.eclipse.jgit.api.RebaseResult;
import org.eclipse.jgit.api.RemoteAddCommand;
import org.eclipse.jgit.api.RemoteListCommand;
import org.eclipse.jgit.api.RemoteRemoveCommand;
import org.eclipse.jgit.api.ResetCommand;
import org.eclipse.jgit.api.ResetCommand.ResetType;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryState;
import org.eclipse.jgit.merge.ResolveMerger;
import org.eclipse.jgit.merge.ResolveMerger.MergeFailureReason;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.FetchResult;
import org.eclipse.jgit.transport.JschConfigSessionFactory;
import org.eclipse.jgit.transport.OpenSshConfig.Host;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.jgit.transport.RemoteRefUpdate;
import org.eclipse.jgit.transport.SshSessionFactory;
import org.eclipse.jgit.transport.URIish;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 * @author Peter Yoo
 */
public class GitWorkingDirectory {

	public GitWorkingDirectory(String workingDirectory)
		throws GitAPIException, IOException {

		this("master", workingDirectory);
	}

	public GitWorkingDirectory(
			String upstreamBranchName, String workingDirectory)
		throws GitAPIException, IOException {

		_upstreamBranchName = upstreamBranchName;

		setWorkingDirectory(workingDirectory);

		waitForIndexLock();

		FileRepositoryBuilder fileRepositoryBuilder =
			new FileRepositoryBuilder();

		fileRepositoryBuilder.setGitDir(_gitDirectory);
		fileRepositoryBuilder.setWorkTree(_workingDirectory);

		_repository = fileRepositoryBuilder.build();

		_git = new Git(_repository);

		_repositoryName = loadRepositoryName();
		_repositoryUsername = loadRepositoryUsername();
	}

	public RemoteConfig addRemote(
			boolean force, String remoteName, String remoteURL)
		throws GitAPIException {

		String forceString = "";

		if (force) {
			forceString = "-f ";
		}

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"remote add ", forceString, remoteName, " url: ", remoteURL));

		RemoteConfig remoteConfig = getRemoteConfig(remoteName);

		if (remoteConfig != null) {
			if (force) {
				removeRemote(remoteConfig);
			}
			else {
				throw new RuntimeException(
					JenkinsResultsParserUtil.combine(
						"Remote ", remoteName, " already exists"));
			}
		}

		RemoteAddCommand remoteAddCommand = _git.remoteAdd();

		remoteAddCommand.setName(remoteName);

		try {
			remoteAddCommand.setUri(new URIish(remoteURL));
		}
		catch (URISyntaxException urise) {
			throw new RuntimeException(
				"Invalid remote url " + remoteURL, urise);
		}

		remoteAddCommand.call();

		_remoteConfigs = null;

		return getRemoteConfig(remoteName);
	}

	public void checkoutBranch(String branchName) throws GitAPIException {
		checkoutBranch(branchName, "-f");
	}

	public void checkoutBranch(String branchName, String options)
		throws GitAPIException {

		System.out.println("Checkout branch " + branchName);

		waitForIndexLock();

		StringBuilder sb = new StringBuilder();

		sb.append("git checkout ");

		if (options != null) {
			sb.append(options);
			sb.append(" ");
		}

		sb.append(branchName);

		try {
			JenkinsResultsParserUtil.executeBashCommands(
				true, _workingDirectory, sb.toString());
		}
		catch (IOException ioe) {
			throw new RuntimeException(
				"Unable to checkout branch " + branchName, ioe);
		}
		catch (InterruptedException ie) {
			throw new RuntimeException(
				"Unable to checkout branch " + branchName, ie);
		}

		int timeout = 0;

		File headFile = new File(_gitDirectory, "HEAD");

		while (true) {
			try {
				String headContent = JenkinsResultsParserUtil.read(headFile);

				headContent = headContent.trim();

				if (!branchName.contains("/")) {
					if (headContent.matches(
							JenkinsResultsParserUtil.combine(
								"ref: refs/heads/", branchName))) {

						break;
					}
				}
				else {
					int i = branchName.indexOf("/");

					String remoteBranchName = branchName.substring(i + 1);

					String remoteName = branchName.substring(0, i);

					String gitHubBranchCommit = getGitHubBranchCommit(
						remoteBranchName, getRemoteConfig(remoteName));

					System.out.println(
						JenkinsResultsParserUtil.combine(
							"headContent: \"", headContent, "\""));
					System.out.println(
						JenkinsResultsParserUtil.combine(
							"gitHubBranchCommit: \"", gitHubBranchCommit,
							"\""));

					if (headContent.equals(gitHubBranchCommit)) {
						break;
					}
					else {
						System.out.println("Not equal");
					}
				}

				System.out.println("Waiting for branch to be updated");

				JenkinsResultsParserUtil.sleep(5000);

				timeout++;

				if (timeout >= 24) {
					throw new RuntimeException(
						"Unable to checkout branch " + branchName);
				}
			}
			catch (IOException ioe) {
				throw new RuntimeException(
					"Unable to read file " + headFile.getPath(), ioe);
			}
		}
	}

	public void clean() throws GitAPIException {
		CleanCommand cleanCommand = _git.clean();

		cleanCommand.setCleanDirectories(true);
		cleanCommand.setForce(true);

		System.out.println("clean -dfx");

		cleanCommand.call();
	}

	public void commitFileToCurrentBranch(String fileName, String message)
		throws GitAPIException {

		System.out.println("Commit file to current branch " + fileName);

		stageFileInCurrentBranch(fileName);

		commitStagedFilesToCurrentBranch(message);
	}

	public void commitStagedFilesToCurrentBranch(String message)
		throws GitAPIException {

		System.out.println("Commit staged files to current branch");

		CommitCommand commitCommand = _git.commit();

		commitCommand.setMessage(message);

		commitCommand.call();
	}

	public void copyUpstreamRefsToHeads() throws IOException {
		File headsDir = new File(_gitDirectory, "refs/heads");
		File upstreamDir = new File(_gitDirectory, "refs/remotes/upstream");

		for (File file : upstreamDir.listFiles()) {
			System.out.println(
				JenkinsResultsParserUtil.combine(
					"copying ", headsDir.getPath(), " to ",
					upstreamDir.getPath()));
			JenkinsResultsParserUtil.copy(
				file, new File(headsDir, file.getName()));
		}
	}

	public void createBranch(String branchName) throws GitAPIException {
		createBranch(branchName, false, "HEAD");
	}

	public void createBranch(String branchName, boolean force, String sha)
		throws GitAPIException {

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"Create branch ", branchName, " force ",
				Boolean.toString(force), " SHA ", sha));

		CreateBranchCommand createBranchCommand = _git.branchCreate();

		createBranchCommand.setForce(force);
		createBranchCommand.setName(branchName);

		if (sha != null) {
			createBranchCommand.setStartPoint(sha);
		}

		createBranchCommand.call();
	}

	public String createPullRequest(
			String title, String body, String receiverUserName,
			String pullRequestBranchName)
		throws IOException {

		JSONObject requestJSONObject = new JSONObject();

		requestJSONObject.put("base", _upstreamBranchName);
		requestJSONObject.put("body", body);
		requestJSONObject.put(
			"head", receiverUserName + ":" + pullRequestBranchName);
		requestJSONObject.put("title", title);

		String url = JenkinsResultsParserUtil.combine(
			"https://api.github.com/repos/", receiverUserName, "/",
			_repositoryName, "/pulls");

		JSONObject responseJSONObject = JenkinsResultsParserUtil.toJSONObject(
			url, requestJSONObject.toString());

		String pullRequestURL = responseJSONObject.getString("html_url");

		System.out.println("Created a pull request at " + pullRequestURL);

		return pullRequestURL;
	}

	public void deleteBranch(String branchName) throws GitAPIException {
		System.out.println("Delete branch " + branchName);

		DeleteBranchCommand deleteBranchCommand = _git.branchDelete();

		deleteBranchCommand.setBranchNames(branchName);
		deleteBranchCommand.setForce(true);

		deleteBranchCommand.call();
	}

	public void deleteExpiredCacheBranches(
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
							deleteExpiredCacheBranches(localGitRemoteConfig);
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

	public void fetch(RemoteConfig remoteConfig, RefSpec refSpec)
		throws GitAPIException {

		FetchCommand fetchCommand = _git.fetch();

		if (refSpec != null) {
			fetchCommand.setRefSpecs(refSpec);
		}
		else {
			fetchCommand.setRefSpecs(remoteConfig.getFetchRefSpecs());
		}

		fetchCommand.setRemote(getRemoteURL(remoteConfig));
		fetchCommand.setTimeout(360);

		int retries = 0;

		while (true) {
			try {
				if (refSpec == null) {
					System.out.println(
						JenkinsResultsParserUtil.combine(
							"fetch ", getRemoteURL(remoteConfig)));
				}
				else {
					System.out.println(
						JenkinsResultsParserUtil.combine(
							"fetch ", remoteConfig.getName(), " ",
							refSpec.toString()));
				}

				FetchResult fetchResult = fetchCommand.call();

				System.out.println(fetchResult.getMessages());

				return;
			}
			catch (TransportException te) {
				if (retries < 3) {
					System.out.println(
						JenkinsResultsParserUtil.combine(
							"Fetch Attempt ", Integer.toString(retries),
							" failed with a TransportException. ",
							te.getMessage(), " Retrying fetch."));

					retries++;
				}
				else {
					throw te;
				}
			}
		}
	}

	public void fetch(
			String localBranchName, String remoteBranchName,
			RemoteConfig remoteConfig)
		throws GitAPIException {

		RefSpec refSpec = new RefSpec(
			JenkinsResultsParserUtil.combine(
				"refs/heads/", remoteBranchName, ":", localBranchName));

		fetch(remoteConfig, refSpec);
	}

	public List<Ref> getAllBranchRefs() throws GitAPIException {
		ListBranchCommand listBranchCommand = _git.branchList();

		listBranchCommand.setListMode(ListMode.ALL);

		return listBranchCommand.call();
	}

	public List<String> getAllLocalBranches() throws GitAPIException {
		List<Ref> allLocalBranchRefs = new ArrayList<>();

		for (Ref branchRef : getAllBranchRefs()) {
			String branchName = branchRef.getName();

			if (branchName.startsWith("refs/heads")) {
				allLocalBranchRefs.add(branchRef);
			}
		}

		return toShortNameList(allLocalBranchRefs);
	}

	public List<String> getAllRemoteBranchNames(RemoteConfig remoteConfig)
		throws GitAPIException {

		LsRemoteCommand lsRemoteCommand = Git.lsRemoteRepository();

		lsRemoteCommand.setHeads(true);
		lsRemoteCommand.setRemote(getRemoteURL(remoteConfig));
		lsRemoteCommand.setTags(false);

		List<String> remoteBranchNames = toShortNameList(
			lsRemoteCommand.call());

		Collections.sort(remoteBranchNames);

		return remoteBranchNames;
	}

	public String getCurrentBranch() {
		waitForIndexLock();

		try {
			return _repository.getBranch();
		}
		catch (IOException ioe) {
			throw new RuntimeException(
				"Could not get current branch name from repository", ioe);
		}
	}

	public String getDependentRepositoryName() {
		if (_repositoryName.equals("liferay-plugins")) {
			return "liferay-portal";
		}
		else if (_repositoryName.equals("liferay-plugins-ee")) {
			return "liferay-portal-ee";
		}
		else if (_repositoryName.equals("liferay-portal")) {
			return "liferay-plugins";
		}
		else if (_repositoryName.equals("liferay-portal-ee")) {
			return "liferay-plugins-ee";
		}

		return null;
	}

	public Git getGit() {
		return _git;
	}

	public String getGitHubBranchCommit(
			String branchName, RemoteConfig remoteConfig)
		throws GitAPIException, IOException {

		String userName = getGitHubUserName(remoteConfig);

		String url = JenkinsResultsParserUtil.combine(
			"https://api.github.com/repos/", userName, "/", getRepositoryName(),
			"/git/refs/heads/", branchName);

		JSONObject branchJSONObject = JenkinsResultsParserUtil.toJSONObject(
			url, false);

		JSONObject objectJSONObject = branchJSONObject.getJSONObject("object");

		return objectJSONObject.getString("sha");
	}

	public String getGitHubUserName(RemoteConfig remoteConfig)
		throws GitAPIException {

		String remoteURL = getRemoteURL(remoteConfig);

		if (!remoteURL.contains("github.com")) {
			throw new IllegalArgumentException(
				JenkinsResultsParserUtil.combine(
					remoteConfig.getName(),
					" does not point to a GitHub repository"));
		}

		String userName = null;

		if (remoteURL.startsWith("https://github.com/")) {
			userName = remoteURL.substring("https://github.com/".length());
		}
		else {
			userName = remoteURL.substring("git@github.com:".length());
		}

		return userName.substring(0, userName.indexOf("/"));
	}

	public List<RemoteConfig> getLocalGitRemoteConfigs()
		throws GitAPIException {

		if (_localGitRemoteConfigs != null) {
			return _localGitRemoteConfigs;
		}

		List<String> localGitRemoteURLs = getLocalGitRemoteURLs();

		List<RemoteConfig> localGitRemoteConfigs = new ArrayList<>(
			localGitRemoteURLs.size());

		for (String localGitRemoteURL : localGitRemoteURLs) {
			String url = localGitRemoteURL.replace(
				"${username}", getRepositoryUsername());

			url = url.replace("${repository-name}", getRepositoryName());

			/*try {
				File file = new File(localGitRemoteURL);

				if (file.exists()) {
					url = localGitRemoteURL;
				}

				String content = JenkinsResultsParserUtil.toString(
					localGitRemoteURL);

				if (content != null) {
					url = localGitRemoteURL;
				}
			}
			catch (Exception e) {
				url = JenkinsResultsParserUtil.combine(
					"git@", localGitRemoteURL, ":", getRepositoryUsername(),
					"/", getRepositoryName(), ".git");
			}*/

			String localGitRemoteName =
				"local-git-remote-" +
					localGitRemoteURLs.indexOf(localGitRemoteURL);

			RemoteConfig remoteConfig = getRemoteConfig(localGitRemoteName);

			if (remoteConfig == null) {
				remoteConfig = addRemote(true, localGitRemoteName, url);
			}

			localGitRemoteConfigs.add(remoteConfig);
		}

		return localGitRemoteConfigs;
	}

	public List<String> getLocalGitRemoteURLs() {
		if (_localGitRemoteURLs != null) {
			return _localGitRemoteURLs;
		}

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
		
		_localGitRemoteURLs = new ArrayList<>(gitCacheHostnames.length);
		
		for (String gitCacheHostname : gitCacheHostnames) {
			if (gitCacheHostname.startsWith("file:") ||
					gitCacheHostname.startsWith("http:")) {

				_localGitRemoteURLs.add(gitCacheHostname);

				continue;
			}
			
			_localGitRemoteURLs.add(JenkinsResultsParserUtil.combine(
				"git@", gitCacheHostname, ":", getRepositoryUsername(), "/",
				getRepositoryName(), ".git"));
		}

		return _localGitRemoteURLs;
	}

	public RemoteConfig getRemoteConfig(String remoteName)
		throws GitAPIException {

		List<RemoteConfig> remoteConfigs = getRemoteConfigs();

		for (RemoteConfig remoteConfig : remoteConfigs) {
			if (remoteName.equals(remoteConfig.getName())) {
				return remoteConfig;
			}
		}

		return null;
	}

	public List<RemoteConfig> getRemoteConfigs() throws GitAPIException {
		if (_remoteConfigs != null) {
			return _remoteConfigs;
		}

		RemoteListCommand remoteListCommand = _git.remoteList();

		_remoteConfigs = remoteListCommand.call();

		return _remoteConfigs;
	}

	public Set<String> getRemoteNames() throws GitAPIException {
		List<RemoteConfig> remoteConfigs = getRemoteConfigs();

		Set<String> remoteNames = new HashSet<>(remoteConfigs.size());

		for (RemoteConfig remoteConfig : remoteConfigs) {
			remoteNames.add(remoteConfig.getName());
		}

		return remoteNames;
	}

	public String getRepositoryName() {
		return _repositoryName;
	}

	public String getRepositoryUsername() {
		return _repositoryUsername;
	}

	public String getUpstreamBranchName() {
		return _upstreamBranchName;
	}

	public File getWorkingDirectory() {
		return _workingDirectory;
	}

	public boolean pushToRemote(RemoteConfig remoteConfig)
		throws GitAPIException {

		return pushToRemote(remoteConfig, getCurrentBranch());
	}

	public boolean pushToRemote(
			RemoteConfig remoteConfig, String remoteBranchName)
		throws GitAPIException {

		return pushToRemote(getCurrentBranch(), remoteBranchName, remoteConfig);
	}

	public boolean pushToRemote(String remoteBranchName, String remoteURL)
		throws GitAPIException {

		RemoteConfig remoteConfig = null;

		try {
			remoteConfig = addRemote(true, "temp", remoteURL);

			return pushToRemote(remoteConfig, remoteBranchName);
		}
		finally {
			removeRemote(remoteConfig);
		}
	}

	public boolean pushToRemote(
			String localBranchName, String remoteBranchName,
			RemoteConfig remoteConfig)
		throws GitAPIException {

		String remoteURL = getRemoteURL(remoteConfig);

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"Push ", localBranchName, " to ", remoteURL, " ",
				remoteBranchName));

		PushCommand pushCommand = null;

		synchronized (_git) {
			pushCommand = _git.push();
		}

		String remoteRefName = "refs/heads/" + remoteBranchName;

		RefSpec refSpec = new RefSpec(
			JenkinsResultsParserUtil.combine(
				localBranchName, ":", remoteRefName));

		synchronized (pushCommand) {
			pushCommand.setRefSpecs(refSpec);

			pushCommand.setRemote(remoteURL);

			for (PushResult pushResult : pushCommand.call()) {
				for (RemoteRefUpdate remoteRefUpdate :
						pushResult.getRemoteUpdates()) {

					if ((remoteRefUpdate != null) &&
						(remoteRefUpdate.getStatus() !=
							RemoteRefUpdate.Status.OK)) {

						System.out.println(remoteRefUpdate);

						return false;
					}
				}
			}

			return true;
		}
	}

	public void rebase(String branchName, String refSHA)
		throws GitAPIException, IOException {

		if (!branchName.equals(getCurrentBranch())) {
			checkoutBranch(branchName);
		}

		RebaseCommand rebaseCommand = _git.rebase();

		rebaseCommand.setOperation(RebaseCommand.Operation.BEGIN);

		RevCommit revCommit = createRevCommit(refSHA);

		if (revCommit == null) {
			throw new RuntimeException("Commit not found.");
		}

		rebaseCommand.setUpstream(revCommit);

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"rebase ", getCurrentBranch(), " ", refSHA));

		RebaseResult result = rebaseCommand.call();

		RebaseResult.Status status = result.getStatus();

		if (!status.isSuccessful()) {
			System.out.println("Rebase failed. " + status.toString());

			Map<String, ResolveMerger.MergeFailureReason> failingPathsMap =
				result.getFailingPaths();

			if (failingPathsMap != null) {
				for (Entry<String, MergeFailureReason> entry :
						failingPathsMap.entrySet()) {

					System.out.println(
						JenkinsResultsParserUtil.combine(
							entry.getValue().toString(), " -- ",
							entry.getKey()));
				}
			}

			throw new RuntimeException("Rebase failed.");
		}
	}

	public void rebaseAbort() throws GitAPIException {
		if (!_rebaseRepositoryStates.contains(
				_repository.getRepositoryState())) {

			return;
		}

		RebaseCommand rebaseCommand = _git.rebase();

		rebaseCommand.setOperation(RebaseCommand.Operation.ABORT);

		System.out.println(
			"rebase " + RebaseCommand.Operation.ABORT.toString());

		rebaseCommand.call();
	}

	public boolean remoteExists(String remoteName) throws GitAPIException {
		Set<String> remoteNames = getRemoteNames();

		return remoteNames.contains(remoteName);
	}

	public void removeRemote(RemoteConfig remoteConfig) {
		try {
			Set<String> remoteNames = getRemoteNames();

			if (!remoteNames.contains(remoteConfig.getName())) {
				throw new RuntimeException(
					JenkinsResultsParserUtil.combine(
						"Unable to remove Remote ", remoteConfig.getName(),
						" because ", "it does not exist"));
			}

			System.out.println("remote remove " + remoteConfig.getName());

			RemoteRemoveCommand remoteRemoveCommand = _git.remoteRemove();

			remoteRemoveCommand.setName(remoteConfig.getName());

			remoteRemoveCommand.call();

			_remoteConfigs = null;
		}
		catch (GitAPIException gapie) {
			gapie.printStackTrace();
		}
	}

	public void removeRemotes(List<RemoteConfig> remoteConfigs) {
		for (RemoteConfig remoteConfig : remoteConfigs) {
			removeRemote(remoteConfig);
		}
	}

	public void reset(String ref, ResetCommand.ResetType resetType)
		throws GitAPIException {

		ResetCommand resetCommand = _git.reset();

		resetCommand.setMode(resetType);

		resetCommand.setRef(ref);

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"reset ", resetType.toString(), " ", ref));

		resetCommand.call();
	}

	public void stageFileInCurrentBranch(String fileName)
		throws GitAPIException {

		AddCommand addCommand = _git.add();

		addCommand.addFilepattern(fileName);

		System.out.println("Stage file in current branch " + fileName);

		addCommand.call();
	}

	public String synchronizeToLocalGit(
			String senderBranchName, String senderUsername, String senderSHA,
			String upstreamBranchName, String upstreamUsername,
			String upstreamSHA)
		throws GitAPIException, IOException {

		RemoteConfig senderRemoteConfig = null;
		RemoteConfig tempUpstreamRemoteConfig = null;

		try {
			tempUpstreamRemoteConfig = addRemote(
				true, "upstream-temp",
				getGitHubRemoteURL(getRepositoryName(), upstreamUsername));

			if (upstreamSHA == null) {
				upstreamSHA = getGitHubBranchCommit(
					upstreamBranchName, tempUpstreamRemoteConfig);
			}

			senderRemoteConfig = addRemote(
				true, "sender-temp",
				getGitHubRemoteURL(getRepositoryName(), senderUsername));

			if (senderSHA == null) {
				senderSHA = getGitHubBranchCommit(
					senderBranchName, senderRemoteConfig);
			}

			boolean pullRequest = !upstreamSHA.equals(senderSHA);

			String cacheBranchName = JenkinsResultsParserUtil.combine(
				"cache-", upstreamUsername, "-", upstreamSHA, "-",
				senderUsername, "-", senderSHA);

			List<RemoteConfig> localGitRemoteConfigs = null;

			try {
				localGitRemoteConfigs = getLocalGitRemoteConfigs();

				deleteExpiredCacheBranches(localGitRemoteConfigs);

				int randomIndex = JenkinsResultsParserUtil.getRandomValue(
					0, localGitRemoteConfigs.size() - 1);

				List<String> remoteBranchNames = getAllRemoteBranchNames(
					localGitRemoteConfigs.get(randomIndex));

				if (remoteBranchNames.contains(cacheBranchName)) {
					System.out.println(
						"cacheBranch " + cacheBranchName + " already exists.");
					return cacheBranchName;
				}

				rebaseAbort();

				clean();

				reset("head", ResetType.HARD);

				fetch(tempUpstreamRemoteConfig, null);

				checkoutBranch(
					tempUpstreamRemoteConfig.getName() + "/master", "-f");

				deleteBranch("master");

				checkoutBranch("master", "-b");

				// deleteExpiredCacheBranches(localGitRemoteConfigs);

				// Is this necessary? What does this do?

				//copyUpstreamRefsToHeads();

				createBranch(cacheBranchName, true, null);

				fetch(cacheBranchName, senderBranchName, senderRemoteConfig);

				createBranch(cacheBranchName, true, senderSHA);

				if (pullRequest) {
					rebase(cacheBranchName, upstreamSHA);
				}

				checkoutBranch(cacheBranchName);

				cacheBranches(
					cacheBranchName, localGitRemoteConfigs, upstreamBranchName,
					upstreamUsername);

				return cacheBranchName;
			}
			finally {
				if (localGitRemoteConfigs != null) {
					removeRemotes(localGitRemoteConfigs);
				}
			}
		}
		finally {
			if (tempUpstreamRemoteConfig != null) {
				removeRemote(tempUpstreamRemoteConfig);
			}

			if (senderRemoteConfig != null) {
				removeRemote(senderRemoteConfig);
			}
		}
	}

	protected static String getGitHubRemoteURL(
		String repositoryName, String userName) {

		return JenkinsResultsParserUtil.combine(
			"git@github.com:", userName, "/", repositoryName, ".git");
	}

	protected static String getRemoteURL(RemoteConfig remoteConfig) {
		List<URIish> uris = remoteConfig.getURIs();

		URIish uri = uris.get(0);

		return uri.toString();
	}

	protected void cacheBranch(
			String localBranchName, RemoteConfig remoteConfig)
		throws GitAPIException {

		pushToRemote(remoteConfig);

		pushToRemote(
			remoteConfig,
			JenkinsResultsParserUtil.combine(
				localBranchName, "-",
				Long.toString(System.currentTimeMillis())));
	}

	protected void cacheBranches(
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
							cacheBranch(localBranchName, localGitRemoteConfig);

							if (upstreamUsername.equals("liferay")) {
								pushToRemote(
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

	protected RevCommit createRevCommit(String sha)
		throws GitAPIException, IOException {

		ObjectId objectId = ObjectId.fromString(sha);

		try (RevWalk revWalk = new RevWalk(_repository)) {
			return revWalk.parseCommit(objectId);
		}
	}

	protected void deleteCachedBranchFromRemote(
			String remoteBranchName, String remoteTimestampBranchName,
			RemoteConfig remoteConfig)
		throws GitAPIException {

		if (pushToRemote("", remoteBranchName, remoteConfig)) {
			System.out.println(
				JenkinsResultsParserUtil.combine(
					"Deleted ", remoteBranchName, " from ",
					getRemoteURL(remoteConfig)));
		}
		else {
			System.out.println(
				JenkinsResultsParserUtil.combine(
					"Unable to delete ", remoteBranchName, " from ",
					getRemoteURL(remoteConfig)));
		}

		if (pushToRemote("", remoteTimestampBranchName, remoteConfig)) {
			System.out.println(
				JenkinsResultsParserUtil.combine(
					"Deleted ", remoteTimestampBranchName, " from ",
					getRemoteURL(remoteConfig)));
		}
		else {
			System.out.println(
				JenkinsResultsParserUtil.combine(
					"Unable to delete ", remoteTimestampBranchName, " from ",
					getRemoteURL(remoteConfig)));
		}
	}

	protected void deleteExpiredCacheBranches(RemoteConfig remoteConfig)
		throws GitAPIException {

		for (String remoteBranchName : getAllRemoteBranchNames(remoteConfig)) {
			Matcher matcher = _cachedBranchPattern.matcher(remoteBranchName);

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
						deleteCachedBranchFromRemote(
							matcher.group("name"), remoteBranchName,
							remoteConfig);
					}
					catch (GitAPIException gapie) {
						System.out.println(
							JenkinsResultsParserUtil.combine(
								"WARNING - Unable to delete cached branch ",
								remoteBranchName, " from ",
								getRemoteURL(remoteConfig)));
					}
				}
			}
		}
	}

	protected String loadRepositoryName() throws GitAPIException {
		String remoteURL = getRemoteURL(getRemoteConfig("upstream"));

		int x = remoteURL.lastIndexOf("/") + 1;
		int y = remoteURL.indexOf(".git");

		String repositoryName = remoteURL.substring(x, y);

		if (!_upstreamBranchName.contains("ee-") &&
			!_upstreamBranchName.contains("-private") &&
			!repositoryName.equals("liferay-jenkins-ee") &&
			!repositoryName.equals("liferay-jenkins-tools-private")) {

			repositoryName = repositoryName.replace("-ee", "");
			repositoryName = repositoryName.replace("-private", "");
		}

		return repositoryName;
	}

	protected String loadRepositoryUsername() throws GitAPIException {
		String remoteURL = getRemoteURL(getRemoteConfig("upstream"));

		int x = remoteURL.indexOf(":") + 1;
		int y = remoteURL.indexOf("/");

		return remoteURL.substring(x, y);
	}

	protected void setWorkingDirectory(String workingDirectory)
		throws GitAPIException, IOException {

		_workingDirectory = new File(workingDirectory);

		if (!_workingDirectory.exists()) {
			throw new FileNotFoundException(
				_workingDirectory.getPath() + " is unavailable");
		}

		_gitDirectory = new File(workingDirectory, ".git");

		if (!_gitDirectory.exists()) {
			throw new FileNotFoundException(
				_gitDirectory.getPath() + " is unavailable");
		}
	}

	protected List<String> toShortNameList(Collection<Ref> refs) {
		List<String> shortNames = new ArrayList<>(refs.size());

		for (Ref ref : refs) {
			String refName = ref.getName();

			shortNames.add(refName.substring(refName.lastIndexOf("/") + 1));
		}

		return shortNames;
	}

	protected void waitForIndexLock() {
		int retries = 0;

		File file = new File(_gitDirectory, "index.lock");

		while (file.exists()) {
			System.out.println("Waiting for index.lock to be cleared.");

			JenkinsResultsParserUtil.sleep(5000);

			retries++;

			if (retries >= 24) {
				file.delete();
			}
		}
	}

	private static final long _BRANCH_EXPIRE_AGE_MILLIS = 1000;
		//1000 * 60 * 60 * 24 * 7;

	private static final Pattern _cachedBranchPattern = Pattern.compile(
		"(?<name>cache-.*)-(?<timestamp>\\d+)");
	private static final List<RepositoryState> _rebaseRepositoryStates =
		Arrays.asList(
			RepositoryState.REBASING, RepositoryState.REBASING_INTERACTIVE,
			RepositoryState.REBASING_MERGE, RepositoryState.REBASING_REBASING);

	static {
		JschConfigSessionFactory jschConfigSessionFactory =
			new JschConfigSessionFactory() {

				@Override
				protected void configure(Host host, Session session) {
					session.setConfig("StrictHostKeyChecking", "no");
				}

			};

		SshSessionFactory.setInstance(jschConfigSessionFactory);
	}

	private final Git _git;
	private File _gitDirectory;
	private List<String> _localGitRemoteURLs;
	private List<RemoteConfig> _localGitRemoteConfigs;
	private List<RemoteConfig> _remoteConfigs;
	private final Repository _repository;
	private final String _repositoryName;
	private final String _repositoryUsername;
	private final String _upstreamBranchName;
	private File _workingDirectory;

}