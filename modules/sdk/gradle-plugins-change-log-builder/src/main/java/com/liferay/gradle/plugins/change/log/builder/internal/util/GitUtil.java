/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.change.log.builder.internal.util;

import com.liferay.gradle.util.FileUtil;

import java.io.File;

import java.util.Date;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.LogCommand;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryCache;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevSort;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.revwalk.filter.AndRevFilter;
import org.eclipse.jgit.revwalk.filter.CommitTimeRevFilter;
import org.eclipse.jgit.revwalk.filter.MaxCountRevFilter;
import org.eclipse.jgit.util.FS;

import org.gradle.api.GradleException;

/**
 * @author Andrea Di Giorgi
 */
public class GitUtil {

	public static Iterable<RevCommit> getCommits(
			Iterable<File> dirs, String rangeStart, String rangeEnd,
			Repository repository)
		throws Exception {

		try (Git git = Git.wrap(repository)) {
			LogCommand logCommand = git.log();

			for (File dir : dirs) {
				logCommand.addPath(_relativize(dir, repository));
			}

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
				return null;
			}

			return revCommit.name();
		}
	}

	public static String getHashHead(Repository repository) throws Exception {
		ObjectId objectId = repository.resolve(Constants.HEAD);

		return objectId.name();
	}

	public static String getHashOldest(Repository repository) throws Exception {
		try (RevWalk revWalk = new RevWalk(repository)) {
			revWalk.markStart(
				revWalk.parseCommit(repository.resolve(Constants.HEAD)));

			revWalk.sort(RevSort.COMMIT_TIME_DESC, true);
			revWalk.sort(RevSort.REVERSE, true);

			RevCommit revCommit = revWalk.next();

			return revCommit.name();
		}
	}

	public static Repository openRepository(File gitDir) throws Exception {
		gitDir = _getGitDir(gitDir);

		RepositoryCache.FileKey fileKey = RepositoryCache.FileKey.exact(
			gitDir, FS.DETECTED);

		return fileKey.open(true);
	}

	private static File _getGitDir(File dir) {
		do {
			File gitDir = RepositoryCache.FileKey.resolve(dir, FS.DETECTED);

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