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

package com.liferay.ant.gradle.exec;

import java.io.File;

import java.util.Arrays;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.ExecTask;

/**
 * @author Chas Austin
 */
public class GradleExecTask extends ExecTask {

	@Override
	public void execute() throws BuildException {
		_gradleExecTask.setDir(_dir);
		_gradleExecTask.setExecutable(_gradleExecutable);
		_gradleExecTask.setFailonerror(true);
		_gradleExecTask.setOwningTarget(getOwningTarget());
		_gradleExecTask.setProject(getProject());
		_gradleExecTask.setTaskName("gradle-exec");

		if (_task != null) {
			_gradleExecTask.createArg().setValue(_task);
		}
		else {
			for (String task : _tasks) {
				_gradleExecTask.createArg().setValue(task);
			}
		}

		_addGradleExecArg(_getAppServerParentDirArg());
		_addGradleExecArg(_getBuildFileArg());
		_addGradleExecArg(_getForcedCacheEnabledArg());
		_addGradleExecArg(_getLiferayHomeArg());
		_addGradleExecArg(_getParallelArg());
		_addGradleExecArg(_getPortalBuildArg());
		_addGradleExecArg(_getProjectCacheDirArg());
		_addGradleExecArg(_getQuietArg());
		_addGradleExecArg("--no-daemon");
		_addGradleExecArg("--stacktrace");

		checkConfiguration();

		_gradleExecTask.execute();
	}

	public void init() {
		_dir = new File(getProject().getBaseDir(), "modules");

		_gradleExecutable = getProject().getProperty("gradle.executable");
	}

	public void setBuildFile(String buildFile) {
		_buildFile = new File(buildFile);
	}

	@Override
	public void setDir(File dir) {
		_dir = dir;
	}

	@Override
	public void setExecutable(String gradleExecutable) {
		_gradleExecutable = gradleExecutable;
	}

	public void setForcedCacheEnabled(boolean forcedCacheEnabled) {
		_forcedCacheEnabled = forcedCacheEnabled;
	}

	public void setParallel(boolean parallel) {
		_parallel = parallel;
	}

	public void setPortalBuild(boolean portalBuild) {
		_portalBuild = portalBuild;
	}

	public void setQuiet(boolean quiet) {
		_quiet = quiet;
	}

	public void setTask(String task) {
		if (_tasks != null) {
			throw new BuildException(
				"The \"task\" and \"tasks\" attributes cannot both be" +
					"specified");
		}
		else {
			_task = task;
		}
	}

	public void setTasks(String tasks) {
		if (_task != null) {
			throw new BuildException(
				"The \"task\" and \"tasks\" attributes cannot both be" +
					"specified");
		}
		else {
			_tasks = Arrays.asList(tasks.split(","));
		}
	}

	@Override
	protected void checkConfiguration() throws BuildException {
		File gradleExecutableFile = new File(_gradleExecutable);

		if (!gradleExecutableFile.exists()) {
			throw new BuildException(
				"The specified Gradle executable does not exist: " +
					gradleExecutableFile.getAbsolutePath());
		}
		else if (!_dir.exists()) {
			throw new BuildException(
				"The specified directory does not exist: " +
					_dir.getAbsolutePath());
		}
	}

	private void _addGradleExecArg(String argValue) {
		if (argValue != null) {
			_gradleExecTask.createArg().setValue(argValue);
		}
	}

	private String _getAppServerParentDirArg() {
		File appServerParentDir = new File(
			getProject().getProperty("app.server.parent.dir"));

		return "-Dapp.server.parent.dir=" + appServerParentDir.toString();
	}

	private String _getBuildFileArg() {
		if (_buildFile != null) {
			return "--build-file=" + _buildFile.toString();
		}

		return null;
	}

	private String _getForcedCacheEnabledArg() {
		return "-Dforced.cache.enabled=" + _forcedCacheEnabled;
	}

	private String _getLiferayHomeArg() {
		File liferayHome = new File(getProject().getProperty("liferay.home"));

		return "-Dliferay.home=" + liferayHome.toString();
	}

	private String _getParallelArg() {
		if (_parallel) {
			return "--parallel";
		}

		return null;
	}

	private String _getPortalBuildArg() {
		return "-Dportal.build=" + _portalBuild;
	}

	private String _getProjectCacheDirArg() {
		File projectDir = new File(getProject().getProperty("project.dir"));

		File projectCacheDir = new File(projectDir, ".gradle");

		return "--project-cache-dir=" + projectCacheDir.toString();
	}

	private String _getQuietArg() {
		if (_quiet) {
			return "--quiet";
		}

		return null;
	}

	private File _buildFile;
	private File _dir;
	private boolean _forcedCacheEnabled = true;
	private final ExecTask _gradleExecTask = new ExecTask();
	private String _gradleExecutable;
	private boolean _parallel = true;
	private boolean _portalBuild = true;
	private boolean _quiet;
	private String _task;
	private List<String> _tasks;

}