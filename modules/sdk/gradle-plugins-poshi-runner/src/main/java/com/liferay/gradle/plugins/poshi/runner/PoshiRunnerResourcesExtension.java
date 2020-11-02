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

package com.liferay.gradle.plugins.poshi.runner;

import com.liferay.gradle.plugins.poshi.runner.internal.util.GitRepositoryBuildAdapter;
import com.liferay.gradle.util.GUtil;
import com.liferay.gradle.util.GradleUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;

import org.gradle.api.Project;
import org.gradle.api.invocation.Gradle;

/**
 * @author Andrea Di Giorgi
 */
public class PoshiRunnerResourcesExtension {

	public PoshiRunnerResourcesExtension(Project project) {
		_project = project;

		Gradle gradle = _project.getGradle();

		gradle.addBuildListener(_gitRepositoryBuildAdapter);

		_artifactAppendix = new Callable<String>() {

			@Override
			public String call() throws Exception {
				return _gitRepositoryBuildAdapter.getBranchName(_project);
			}

		};

		_artifactVersion = new Callable<String>() {

			@Override
			public String call() throws Exception {
				Date now = new Date();

				DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

				return dateFormat.format(now) + "-" +
					_gitRepositoryBuildAdapter.getHeadHash(_project);
			}

		};
	}

	public PoshiRunnerResourcesExtension dirs(Iterable<?> dirs) {
		GUtil.addToCollection(_dirs, dirs);

		return this;
	}

	public String getArtifactAppendix() {
		return GradleUtil.toString(_artifactAppendix);
	}

	public String getArtifactVersion() {
		return GradleUtil.toString(_artifactVersion);
	}

	public String getBaseName() {
		return GradleUtil.toString(_baseName);
	}

	public Iterable<Object> getDirs() {
		return _dirs;
	}

	public String getRootDirName() {
		return GradleUtil.toString(_rootDirName);
	}

	public void setArtifactAppendix(Object artifactAppendix) {
		_artifactAppendix = artifactAppendix;
	}

	public void setArtifactVersion(Object artifactVersion) {
		_artifactVersion = artifactVersion;
	}

	public void setBaseName(Object baseName) {
		_baseName = baseName;
	}

	public void setDirs(Iterable<?> dirs) {
		_dirs.clear();

		dirs(dirs);
	}

	public void setRootDirName(Object rootDirName) {
		_rootDirName = rootDirName;
	}

	private static final GitRepositoryBuildAdapter _gitRepositoryBuildAdapter =
		new GitRepositoryBuildAdapter();

	private Object _artifactAppendix;
	private Object _artifactVersion;
	private Object _baseName;
	private final Set<Object> _dirs = new HashSet<>();
	private final Project _project;
	private Object _rootDirName;

}