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

package com.liferay.gradle.plugins.poshi.runner.internal.util;

import java.io.File;
import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jgit.lib.AbbreviatedObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectDatabase;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryCache.FileKey;
import org.eclipse.jgit.util.FS;

import org.gradle.BuildAdapter;
import org.gradle.BuildResult;
import org.gradle.api.Project;
import org.gradle.api.UncheckedIOException;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;

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

	private static File _getGitDir(File dir) throws IOException {
		do {
			File gitDir = FileKey.resolve(dir, FS.DETECTED);

			if (gitDir != null) {
				return gitDir;
			}

			dir = dir.getParentFile();
		}
		while (dir != null);

		throw new IOException("Unable to locate .git directory");
	}

	private static String _getHashHead(Repository repository)
		throws IOException {

		ObjectId objectId = repository.resolve(Constants.HEAD);

		ObjectDatabase objectDatabase = repository.getObjectDatabase();

		try (ObjectReader objectReader = objectDatabase.newReader()) {
			AbbreviatedObjectId abbreviatedObjectId = objectReader.abbreviate(
				objectId);

			return abbreviatedObjectId.name();
		}
	}

	private synchronized GitRepositoryBag _getGitRepositoryBag(
		Project project) {

		File rootDir = project.getRootDir();

		GitRepositoryBag gitRepositoryBag = _gitRepositoryBags.get(rootDir);

		if (gitRepositoryBag != null) {
			return gitRepositoryBag;
		}

		long start = System.currentTimeMillis();

		try {
			FileKey fileKey = FileKey.exact(_getGitDir(rootDir), FS.DETECTED);

			try (Repository repository = fileKey.open(true)) {
				String branchName = repository.getBranch();
				String hashHead = _getHashHead(repository);

				gitRepositoryBag = new GitRepositoryBag(branchName, hashHead);

				_gitRepositoryBags.put(rootDir, gitRepositoryBag);

				if (_logger.isInfoEnabled()) {
					_logger.info(
						"Getting data from Git repository in \"{}\" took {} " +
							"ms.",
						repository.getDirectory(),
						System.currentTimeMillis() - start);
				}

				return gitRepositoryBag;
			}
		}
		catch (IOException ioe) {
			throw new UncheckedIOException(ioe);
		}
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