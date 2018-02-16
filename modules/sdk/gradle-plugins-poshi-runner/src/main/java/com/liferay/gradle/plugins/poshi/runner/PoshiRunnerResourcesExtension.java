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
import com.liferay.gradle.util.GradleUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
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

				DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

				return dateFormat.format(now) + "-" +
					_gitRepositoryBuildAdapter.getHeadHash(_project);
			}

		};
	}

	public PoshiRunnerResourcesExtension baseNameDir(
		Object baseName, Object dir) {

		_baseNameDirs.put(baseName, dir);

		return this;
	}

	public String getArtifactAppendix() {
		return GradleUtil.toString(_artifactAppendix);
	}

	public String getArtifactVersion() {
		return GradleUtil.toString(_artifactVersion);
	}

	public Map<Object, Object> getBaseNameDirs() {
		return _baseNameDirs;
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

	public void setBaseNameDirs(Map<?, ?> baseNameDirs) {
		_baseNameDirs.putAll(baseNameDirs);
	}

	public void setRootDirName(Object rootDirName) {
		_rootDirName = rootDirName;
	}

	private static final GitRepositoryBuildAdapter _gitRepositoryBuildAdapter =
		new GitRepositoryBuildAdapter();

	private Object _artifactAppendix;
	private Object _artifactVersion;
	private final Map<Object, Object> _baseNameDirs = new LinkedHashMap<>();
	private final Project _project;
	private Object _rootDirName;

}