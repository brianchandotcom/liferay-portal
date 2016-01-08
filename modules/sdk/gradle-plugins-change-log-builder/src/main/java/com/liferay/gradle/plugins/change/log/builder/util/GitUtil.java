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

package com.liferay.gradle.plugins.change.log.builder.util;

import com.liferay.gradle.util.FileUtil;

import java.io.File;

import java.util.Date;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.LogCommand;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryCache;
import org.eclipse.jgit.lib.RepositoryCache.FileKey;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.revwalk.filter.AndRevFilter;
import org.eclipse.jgit.revwalk.filter.CommitTimeRevFilter;
import org.eclipse.jgit.revwalk.filter.MaxCountRevFilter;
import org.eclipse.jgit.util.FS;

import org.gradle.api.GradleException;
import org.gradle.api.Project;

/**
 * @author Andrea Di Giorgi
 */
public class GitUtil {

	public static Iterable<RevCommit> getCommits(
			File dir, String rangeStart, String rangeEnd, Repository repository)
		throws Exception {

		try (Git git = Git.wrap(repository)) {
			LogCommand logCommand = git.log();

			logCommand.addPath(_relativize(dir, repository));

			logCommand.addRange(
				repository.resolve(rangeStart), repository.resolve(rangeEnd));

			return logCommand.call();
		}
	}

	public static String getHashBefore(Date date, Repository repository)
		throws Exception {

		try (RevWalk revWalk = new RevWalk(repository)) {
			revWalk.markStart(
				revWalk.parseCommit(repository.resolve(Constants.HEAD)));

			revWalk.setRetainBody(false);

			revWalk.setRevFilter(
				AndRevFilter.create(
					CommitTimeRevFilter.before(date),
					MaxCountRevFilter.create(1)));

			RevCommit revCommit = revWalk.next();

			if (revCommit == null) {
				throw new GradleException(
					"Unable to find any commit before " + date);
			}

			return revCommit.name();
		}
	}

	public static String getHashHead(Repository repository) throws Exception {
		ObjectId objectId = repository.resolve(Constants.HEAD);

		return objectId.name();
	}

	public static Repository openRepository(Project project) throws Exception {
		File gitDir = _getGitDir(project.getRootDir());

		return RepositoryCache.open(FileKey.exact(gitDir, FS.DETECTED));
	}

	private static File _getGitDir(File dir) {
		do {
			File gitDir = FileKey.resolve(dir, FS.DETECTED);

			if (gitDir != null) {
				return gitDir;
			}

			dir = dir.getParentFile();
		}
		while (dir != null);

		throw new GradleException("Unable to locate .git directory");
	}

	private static String _relativize(File file, Repository repository) {
		File gitDir = repository.getDirectory();

		String relativePath = FileUtil.relativize(file, gitDir.getParentFile());

		if (File.separatorChar == '\\') {
			relativePath = relativePath.replace('\\', '/');
		}

		return relativePath;
	}

}