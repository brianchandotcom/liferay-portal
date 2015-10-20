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
import org.eclipse.jgit.revwalk.filter.MaxCountRevFilter;
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
			Repository repository, String path, String since)
		throws IOException {

		RevWalk revWalk = new RevWalk(repository);

		try {
			RevCommit sinceRevCommit = null;

			try {
				sinceRevCommit = revWalk.parseCommit(repository.resolve(since));
			}
			catch (MissingObjectException moe) {
				return true;
			}

			revWalk.setRetainBody(false);

			revWalk.markStart(
				revWalk.parseCommit(repository.resolve(Constants.HEAD)));
			revWalk.markUninteresting(sinceRevCommit);

			revWalk.setRevFilter(MaxCountRevFilter.create(2));

			revWalk.setTreeFilter(
				AndTreeFilter.create(
					PathFilter.create(path), TreeFilter.ANY_DIFF
				));

			boolean changedSince = true;

			if ((revWalk.next() != null) && (revWalk.next() == null)) {
				changedSince = false;
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