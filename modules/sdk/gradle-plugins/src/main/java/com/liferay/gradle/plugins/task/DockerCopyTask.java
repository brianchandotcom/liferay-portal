/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.task;

import com.liferay.gradle.plugins.internal.util.FileUtil;
import com.liferay.gradle.plugins.internal.util.GradleUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import org.gradle.api.logging.Logger;
import org.gradle.api.tasks.CacheableTask;
import org.gradle.api.tasks.Exec;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Internal;
import org.gradle.util.CollectionUtils;

/**
 * @author Peter Shin
 */
@CacheableTask
public class DockerCopyTask extends Exec {

	public DockerCopyTask() {
		setExecutable("docker");
		setLifearyHome("/opt/liferay");
	}

	@Override
	public void exec() {
		Logger logger = getLogger();

		if (logger.isLifecycleEnabled()) {
			logger.lifecycle(
				"Running: {} {}", getExecutable(),
				CollectionUtils.join(" ", _getCompleteArgs()));
		}

		setArgs(_getCompleteArgs());

		super.exec();

		if (logger.isLifecycleEnabled()) {
			logger.lifecycle(
				"Files of {} deployed to {}", getProject(), getDeployDir());
		}
	}

	@Input
	public String getContainerId() {
		return GradleUtil.toString(_containerId);
	}

	@Input
	public String getDeployDir() {
		return GradleUtil.toString(_deployDir);
	}

	@Input
	public String getLiferayHome() {
		return GradleUtil.toString(_liferayHome);
	}

	@Internal
	public File getSourceFile() {
		return GradleUtil.toFile(getProject(), _sourceFile);
	}

	public void setContainerId(Object containerId) {
		_containerId = containerId;
	}

	public void setDeployDir(Object deployDir) {
		_deployDir = deployDir;
	}

	public void setLifearyHome(Object liferayHome) {
		_liferayHome = liferayHome;
	}

	public void setSourceFile(Object sourceFile) {
		_sourceFile = sourceFile;
	}

	private List<String> _getCompleteArgs() {
		List<String> args = new ArrayList<>();

		args.add("cp");

		File sourceFile = getSourceFile();

		args.add(FileUtil.getAbsolutePath(sourceFile));

		StringBuilder sb = new StringBuilder();

		sb.append(getContainerId());
		sb.append(':');
		sb.append(getDeployDir());
		sb.append('/');
		sb.append(sourceFile.getName());

		args.add(sb.toString());

		return args;
	}

	private Object _containerId;
	private Object _deployDir;
	private Object _liferayHome;
	private Object _sourceFile;

}