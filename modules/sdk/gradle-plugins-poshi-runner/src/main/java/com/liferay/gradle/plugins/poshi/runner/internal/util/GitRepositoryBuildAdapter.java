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
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.jgit.lib.AbbreviatedObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectDatabase;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryCache;
import org.eclipse.jgit.lib.RepositoryCache.FileKey;
import org.eclipse.jgit.util.FS;

import org.gradle.BuildAdapter;
import org.gradle.BuildResult;
import org.gradle.api.Project;
import org.gradle.api.UncheckedIOException;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;
import org.gradle.util.Clock;

/**
 * @author Andrea Di Giorgi
 */
public class GitRepositoryBuildAdapter extends BuildAdapter {

	@Override
	public void buildFinished(BuildResult buildResult) {
		Set<Map.Entry<File, GitRepositoryBag>> entries =
			_gitRepositoryBags.entrySet();

		Iterator<Map.Entry<File, GitRepositoryBag>> iterator =
			entries.iterator();

		while (iterator.hasNext()) {
			Map.Entry<File, GitRepositoryBag> entry = iterator.next();

			GitRepositoryBag gitRepositoryBag = entry.getValue();

			gitRepositoryBag.repository.close();

			iterator.remove();
		}
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

		try {
			File rootDir = project.getRootDir();

			GitRepositoryBag gitRepositoryBag = _gitRepositoryBags.get(rootDir);

			if (gitRepositoryBag == null) {
				Clock clock = new Clock();

				File gitDir = _getGitDir(rootDir);

				Repository repository = RepositoryCache.open(
					FileKey.exact(gitDir, FS.DETECTED));

				String branchName = repository.getBranch();
				String hashHead = _getHashHead(repository);

				gitRepositoryBag = new GitRepositoryBag(
					repository, branchName, hashHead);

				_gitRepositoryBags.put(rootDir, gitRepositoryBag);

				if (_logger.isInfoEnabled()) {
					_logger.info(
						"Getting data from Git repository in \"{}\" took {}.",
						gitDir, clock.getTime());
				}
			}

			return gitRepositoryBag;
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

		public GitRepositoryBag(
			Repository repository, String branchName, String hashHead) {

			this.repository = repository;
			this.branchName = branchName;
			this.hashHead = hashHead;
		}

		public final String branchName;
		public final String hashHead;
		public final Repository repository;

	}

}