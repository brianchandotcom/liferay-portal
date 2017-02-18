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
import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.CreateBranchCommand;
import org.eclipse.jgit.api.DeleteBranchCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

/**
 * @author Michael Hashimoto
 */
public class GitWorkingDirectory {

	public GitWorkingDirectory(String workingDirectory)
		throws GitAPIException, InterruptedException, IOException {

		_setGitDirectory(workingDirectory + "/.git");
		_setWorkingDirectory(workingDirectory);

		_clearIndexLock();

		FileRepositoryBuilder fileRepositoryBuilder =
			new FileRepositoryBuilder();

		fileRepositoryBuilder.setGitDir(new File(_gitDirectory));
		fileRepositoryBuilder.setWorkTree(new File(_workingDirectory));

		_repository = fileRepositoryBuilder.build();

		_git = new Git(_repository);
	}

	public void checkoutBranch(String branchName)
		throws InterruptedException, IOException {

		System.out.println("Checkout branch " + branchName);

		_clearIndexLock();

		StringBuilder sb = new StringBuilder();

		sb.append("git checkout ");
		sb.append(branchName);

		JenkinsResultsParserUtil.executeBashCommands(
			true, new File(_workingDirectory), sb.toString());

		int timeout = 0;

		while (true) {
			File file = new File(_gitDirectory + "/HEAD");

			String content = JenkinsResultsParserUtil.read(file);

			if (content.matches("ref: refs/heads/" + branchName + "\\n")) {
				break;
			}

			System.out.println("Waiting for branch to be updated");

			Thread.sleep(5000);

			timeout++;

			if (timeout >= 24) {
				throw new InterruptedException(
					"Failed to checkout branch " + branchName);
			}
		}
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

	public void createBranch(String branchName) throws GitAPIException {
		System.out.println("Create branch " + branchName);

		CreateBranchCommand createBranchCommand = _git.branchCreate();

		createBranchCommand.setName(branchName);

		createBranchCommand.call();
	}

	public void deleteBranch(String branchName) throws GitAPIException {
		System.out.println("Delete branch " + branchName);

		DeleteBranchCommand deleteBranchCommand = _git.branchDelete();

		deleteBranchCommand.setBranchNames(branchName);
		deleteBranchCommand.setForce(true);

		deleteBranchCommand.call();
	}

	public String getCurrentBranch() throws InterruptedException, IOException {
		_clearIndexLock();

		return _repository.getBranch();
	}

	public String getWorkingDirectory() {
		return _workingDirectory;
	}

	public void pushBranchToOrigin(String branchName, String origin)
		throws GitAPIException {

		System.out.println("Push branch " + branchName + " to " + origin);

		PushCommand pushCommand = _git.push();

		pushCommand.add(branchName);
		pushCommand.setRemote(origin);

		pushCommand.call();
	}

	public void resetHardToHEAD()
		throws GitAPIException, InterruptedException, IOException {

		System.out.println("Reset hard to HEAD");

		_clearIndexLock();

		JenkinsResultsParserUtil.executeBashCommands(
			true, new File(_workingDirectory), "git reset --hard");
	}

	public void stageFileInCurrentBranch(String fileName)
		throws GitAPIException {

		System.out.println("Stage file in current branch " + fileName);

		AddCommand addCommand = _git.add();

		addCommand.addFilepattern(fileName);

		addCommand.call();
	}

	private void _clearIndexLock() throws InterruptedException {
		int timeout = 0;

		while (true) {
			File file = new File(_gitDirectory + "/index.lock");

			if (!file.exists()) {
				break;
			}

			System.out.println("Waiting for index.lock to be cleared.");

			Thread.sleep(5000);

			timeout++;

			if (timeout >= 24) {
				file.delete();

				break;
			}
		}
	}

	private void _setGitDirectory(String gitDirectory)
		throws GitAPIException, IOException {

		File file = new File(gitDirectory);

		if (!file.exists()) {
			throw new FileNotFoundException(gitDirectory + " is unavailable");
		}

		_gitDirectory = file.getCanonicalPath();
	}

	private void _setWorkingDirectory(String workingDirectory)
		throws GitAPIException, IOException {

		File file = new File(workingDirectory);

		if (!file.exists()) {
			throw new FileNotFoundException(
				workingDirectory + " is unavailable");
		}

		_workingDirectory = file.getCanonicalPath();
	}

	private final Git _git;
	private String _gitDirectory;
	private final Repository _repository;
	private String _workingDirectory;

}