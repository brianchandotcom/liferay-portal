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

import java.io.File;

import java.util.Iterator;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.revwalk.filter.MaxCountRevFilter;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.AndTreeFilter;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.eclipse.jgit.treewalk.filter.TreeFilter;

/**
 * @author Shuyang Zhou
 */
public class GitHeadHashTask extends Task {

	@Override
	public void execute() throws BuildException {
		if (_property == null) {
			throw new BuildException(
				"Property attribute is required", getLocation());
		}

		if (_path == null) {
			throw new BuildException(
				"Path attribute is required", getLocation());
		}

		if (_gitDir == null) {
			Project currentProject = getProject();

			_gitDir = currentProject.getBaseDir();
		}

		FileRepositoryBuilder fileRepositoryBuilder =
			new FileRepositoryBuilder();

		fileRepositoryBuilder.readEnvironment();
		fileRepositoryBuilder.findGitDir(_gitDir);

		String relativePath = PathUtil.toRelativePath(
			fileRepositoryBuilder.getGitDir(), _path);

		try {
			Repository repository = fileRepositoryBuilder.build();

			RevWalk revWalk = new RevWalk(repository);

			RevCommit headRevCommit = revWalk.lookupCommit(
				repository.resolve(Constants.HEAD));

			revWalk.setTreeFilter(
				AndTreeFilter.create(
					PathFilter.create(relativePath), TreeFilter.ANY_DIFF
				));

			revWalk.setRevFilter(MaxCountRevFilter.create(2));
			revWalk.markStart(headRevCommit);

			Iterator<RevCommit> iterator = revWalk.iterator();

			while (iterator.hasNext()) {
				RevCommit revCommit = iterator.next();

				TreeWalk treeWalk = new TreeWalk(repository);

				treeWalk.addTree(revCommit.getTree());
				treeWalk.setRecursive(true);
				treeWalk.setFilter(
					AndTreeFilter.create(
						PathFilter.create(relativePath + "/" + _ignoreFileName),
						TreeFilter.ANY_DIFF));

				if (!treeWalk.next()) {
					Project currentProject = getProject();

					currentProject.setNewProperty(_property, revCommit.name());

					break;
				}
			}

			revWalk.dispose();
		}
		catch (Exception e) {
			throw new BuildException(
				"Unable to get head hash for path " + _path, e);
		}
	}

	public void setGitDir(File gitDir) {
		_gitDir = gitDir;
	}

	public void setIgnoreFileName(String ignoreFileName) {
		_ignoreFileName = ignoreFileName;
	}

	public void setPath(String path) {
		_path = path;
	}

	public void setProperty(String property) {
		_property = property;
	}

	private File _gitDir;
	private String _ignoreFileName = "snapshot.properties";
	private String _path;
	private String _property;

}