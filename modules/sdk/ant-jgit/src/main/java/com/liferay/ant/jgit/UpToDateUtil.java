/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ant.jgit;

import java.io.IOException;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.StatusCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.revwalk.filter.AndRevFilter;
import org.eclipse.jgit.revwalk.filter.MaxCountRevFilter;
import org.eclipse.jgit.revwalk.filter.MessageRevFilter;
import org.eclipse.jgit.revwalk.filter.NotRevFilter;
import org.eclipse.jgit.submodule.SubmoduleWalk;
import org.eclipse.jgit.treewalk.filter.AndTreeFilter;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.eclipse.jgit.treewalk.filter.TreeFilter;

/**
 * @author Shuyang Zhou
 */
public class UpToDateUtil {

	public static String getProcessName() {
		RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();

		return runtimeMXBean.getName();
	}

	public static boolean hasChangedSince(
			Repository repository, String path, String since,
			String ignoredMessagePattern)
		throws IOException {

		RevWalk revWalk = new RevWalk(repository);

		try {
			RevCommit sinceRevCommit = null;

			try {
				sinceRevCommit = revWalk.parseCommit(repository.resolve(since));
			}
			catch (MissingObjectException missingObjectException) {
				return true;
			}

			revWalk.markStart(
				revWalk.parseCommit(repository.resolve(Constants.HEAD)));
			revWalk.markUninteresting(sinceRevCommit);

			revWalk.setTreeFilter(
				AndTreeFilter.create(
					PathFilter.create(path), TreeFilter.ANY_DIFF));

			boolean changedSince = true;

			if ((ignoredMessagePattern != null) &&
				!ignoredMessagePattern.isEmpty()) {

				revWalk.setRevFilter(
					AndRevFilter.create(
						MaxCountRevFilter.create(1),
						NotRevFilter.create(
							MessageRevFilter.create(ignoredMessagePattern))));

				if (revWalk.next() == null) {
					changedSince = false;
				}
			}
			else {
				revWalk.setRetainBody(false);
				revWalk.setRevFilter(MaxCountRevFilter.create(2));

				if ((revWalk.next() != null) && (revWalk.next() == null)) {
					changedSince = false;
				}
			}

			return changedSince;
		}
		finally {
			revWalk.dispose();
		}
	}

	public static boolean isClean(Git git, String path) throws GitAPIException {
		StatusCommand statusCommand = git.status();

		statusCommand.addPath(path);
		statusCommand.setIgnoreSubmodules(
			SubmoduleWalk.IgnoreSubmoduleMode.ALL);

		Status status = statusCommand.call();

		return status.isClean();
	}

}