/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		Gradle gradle = project.getGradle();

		gradle.addBuildListener(_gitRepositoryBuildAdapter);

		_artifactAppendix = new Callable<String>() {

			@Override
			public String call() throws Exception {
				return _gitRepositoryBuildAdapter.getBranchName(project);
			}

		};

		_artifactVersion = new Callable<String>() {

			@Override
			public String call() throws Exception {
				Date date = new Date();

				DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

				return dateFormat.format(date) + "-" +
					_gitRepositoryBuildAdapter.getHeadHash(project);
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

	public String getVersion() {
		return GradleUtil.toString(_version);
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

	public void setVersion(Object version) {
		_version = version;
	}

	private static final GitRepositoryBuildAdapter _gitRepositoryBuildAdapter =
		new GitRepositoryBuildAdapter();

	private Object _artifactAppendix;
	private Object _artifactVersion;
	private Object _baseName = "default";
	private final Set<Object> _dirs = new HashSet<>();
	private final Project _project;
	private Object _rootDirName;
	private Object _version = "1.0.14";

}