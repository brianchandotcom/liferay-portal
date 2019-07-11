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

package com.liferay.gradle.plugins.tasks;

import com.liferay.gradle.plugins.internal.util.FileUtil;
import com.liferay.gradle.plugins.internal.util.GradleUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import org.gradle.api.tasks.Exec;

/**
 * @author Peter Shin
 */
public class DockerDeployTask extends Exec {

	public DockerDeployTask() {
		setExecutable("docker");
		setLifearyHome("/opt/liferay");
	}

	@Override
	@SuppressWarnings("unchecked")
	public void exec() {
		setArgs(_getCompleteArgs());

		super.exec();
	}

	public String getContainerId() {
		return GradleUtil.toString(_containerId);
	}

	public String getDeployDir() {
		return GradleUtil.toString(_deployDir);
	}

	public String getLiferayHome() {
		return GradleUtil.toString(_liferayHome);
	}

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