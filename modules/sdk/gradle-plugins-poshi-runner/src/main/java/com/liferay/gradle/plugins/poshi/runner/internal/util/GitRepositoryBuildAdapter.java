/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.poshi.runner.internal.util;

import java.io.ByteArrayOutputStream;
import java.io.File;

import java.util.HashMap;
import java.util.Map;

import org.gradle.BuildAdapter;
import org.gradle.BuildResult;
import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;
import org.gradle.process.ExecSpec;

/**
 * @author Andrea Di Giorgi
 */
public class GitRepositoryBuildAdapter extends BuildAdapter {

	@Override
	public void buildFinished(BuildResult buildResult) {
		_gitRepositoryBags.clear();
	}

	public String getBranchName(Project project) {
		GitRepositoryBag gitRepositoryBag = _getGitRepositoryBag(project);

		return gitRepositoryBag.branchName;
	}

	public String getHeadHash(Project project) {
		GitRepositoryBag gitRepositoryBag = _getGitRepositoryBag(project);

		return gitRepositoryBag.hashHead;
	}

	private synchronized GitRepositoryBag _getGitRepositoryBag(
		Project project) {

		File rootDir = project.getRootDir();

		GitRepositoryBag gitRepositoryBag = _gitRepositoryBags.get(rootDir);

		if (gitRepositoryBag != null) {
			return gitRepositoryBag;
		}

		long start = System.currentTimeMillis();

		gitRepositoryBag = new GitRepositoryBag(
			_getGitResult(project, "rev-parse", "--abbrev-ref", "HEAD"),
			_getGitResult(project, "rev-parse", "--short", "HEAD"));

		_gitRepositoryBags.put(rootDir, gitRepositoryBag);

		if (_logger.isInfoEnabled()) {
			_logger.info(
				"Getting data from Git repository in \"{}\" took {} ms.",
				rootDir, System.currentTimeMillis() - start);
		}

		return gitRepositoryBag;
	}

	private String _getGitResult(Project project, final Object... args) {
		final ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		project.exec(
			new Action<ExecSpec>() {

				@Override
				public void execute(ExecSpec execSpec) {
					execSpec.args(args);
					execSpec.setExecutable("git");
					execSpec.setIgnoreExitValue(true);
					execSpec.setStandardOutput(byteArrayOutputStream);
				}

			});

		String result = byteArrayOutputStream.toString();

		return result.trim();
	}

	private static final Logger _logger = Logging.getLogger(
		GitRepositoryBuildAdapter.class);

	private final Map<File, GitRepositoryBag> _gitRepositoryBags =
		new HashMap<>();

	private static class GitRepositoryBag {

		public GitRepositoryBag(String branchName, String hashHead) {
			this.branchName = branchName;
			this.hashHead = hashHead;
		}

		public final String branchName;
		public final String hashHead;

	}

}