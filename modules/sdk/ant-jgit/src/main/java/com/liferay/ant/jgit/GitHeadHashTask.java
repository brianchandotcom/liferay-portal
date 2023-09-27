/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ant.jgit;

import java.io.File;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryCache;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.revwalk.filter.MaxCountRevFilter;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.AndTreeFilter;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.eclipse.jgit.treewalk.filter.TreeFilter;
import org.eclipse.jgit.util.FS;

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

		File gitDir = PathUtil.getGitDir(_gitDir, getProject(), getLocation());

		String relativePath = PathUtil.toRelativePath(gitDir, _path);

		if (_useCache) {
			String hash = _hashes.get(relativePath);

			if (hash != null) {
				Project currentProject = getProject();

				currentProject.setNewProperty(_property, hash);

				return;
			}
		}

		RepositoryCache.FileKey fileKey = RepositoryCache.FileKey.exact(
			gitDir, FS.DETECTED);

		try (Repository repository = fileKey.open(true)) {
			RevWalk revWalk = new RevWalk(repository);

			revWalk.setRetainBody(false);

			revWalk.markStart(
				revWalk.parseCommit(repository.resolve(Constants.HEAD)));

			if (_ignoreFileName == null) {
				revWalk.setRevFilter(MaxCountRevFilter.create(1));
			}
			else {
				revWalk.setRevFilter(MaxCountRevFilter.create(2));
			}

			revWalk.setTreeFilter(
				AndTreeFilter.create(
					PathFilter.create(relativePath), TreeFilter.ANY_DIFF));

			RevCommit revCommit = revWalk.next();

			if (revCommit == null) {
				throw new IllegalStateException(
					"Unable to find any commit under " + _path);
			}

			if (hasIgnoreFile(repository, revCommit, relativePath)) {
				RevCommit secondRevCommit = revWalk.next();

				if (secondRevCommit != null) {
					revCommit = secondRevCommit;
				}
			}

			Project currentProject = getProject();

			String hash = revCommit.name();

			currentProject.setNewProperty(_property, hash);

			if (_useCache) {
				_hashes.put(relativePath, hash);
			}

			revWalk.dispose();
		}
		catch (Exception exception) {
			throw new BuildException(
				"Unable to get head hash for path " + _path, exception);
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

	public void setUseCache(boolean useCache) {
		_useCache = useCache;
	}

	protected boolean hasIgnoreFile(
			Repository repository, RevCommit revCommit, String relativePath)
		throws Exception {

		if (_ignoreFileName == null) {
			return false;
		}

		try (TreeWalk treeWalk = new TreeWalk(repository)) {
			treeWalk.addTree(revCommit.getTree());

			if (revCommit.getParentCount() > 0) {
				RevCommit parentRevCommit = revCommit.getParent(0);

				treeWalk.addTree(parentRevCommit.getTree());
			}

			treeWalk.setRecursive(true);

			treeWalk.setFilter(
				AndTreeFilter.create(
					PathFilter.create(relativePath + "/" + _ignoreFileName),
					TreeFilter.ANY_DIFF));

			return treeWalk.next();
		}
	}

	private static final Map<String, String> _hashes =
		new ConcurrentHashMap<>();

	private File _gitDir;
	private String _ignoreFileName;
	private String _path;
	private String _property;
	private boolean _useCache = true;

}