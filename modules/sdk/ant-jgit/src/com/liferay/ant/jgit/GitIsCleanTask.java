
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

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.taskdefs.condition.Condition;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.StatusCommand;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryCache;
import org.eclipse.jgit.submodule.SubmoduleWalk.IgnoreSubmoduleMode;
import org.eclipse.jgit.util.FS;

/**
 * @author Shuyang Zhou
 */
public class GitIsCleanTask extends Task implements Condition {

	@Override
	public boolean eval() throws BuildException {
		if (_path == null) {
			throw new BuildException(
				"Path attribute is required", getLocation());
		}

		File gitDir = PathUtil.getGitDir(_gitDir, getProject(), getLocation());

		try (Repository repository = RepositoryCache.open(
				RepositoryCache.FileKey.exact(gitDir, FS.DETECTED))) {

			Git git = new Git(new DirCacheCachedRepositoryWrapper(repository));

			StatusCommand statusCommand = git.status();

			statusCommand.setIgnoreSubmodules(IgnoreSubmoduleMode.ALL);

			statusCommand.addPath(PathUtil.toRelativePath(gitDir, _path));

			Status status = statusCommand.call();

			return status.isClean();
		}
		catch (Exception e) {
			throw new BuildException(
				"Unable to check cleanness for path " + _path, e);
		}
	}

	@Override
	public void execute() throws BuildException {
		if (_property == null) {
			throw new BuildException(
				"Property attribute is required", getLocation());
		}

		if (eval()) {
			Project currentProject = getProject();

			if (_value == null) {
				currentProject.setNewProperty(_property, "true");
			}
			else {
				currentProject.setNewProperty(_property, _value);
			}
		}
	}

	public void setGitDir(File gitDir) {
		_gitDir = gitDir;
	}

	public void setPath(String path) {
		_path = path;
	}

	public void setProperty(String property) {
		_property = property;
	}

	public void setValue(String value) {
		_value = value;
	}

	private File _gitDir;
	private String _path;
	private String _property;
	private String _value;

}