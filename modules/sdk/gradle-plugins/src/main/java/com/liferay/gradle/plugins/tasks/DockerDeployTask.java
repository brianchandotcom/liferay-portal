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
	}

	@Override
	@SuppressWarnings("unchecked")
	public void exec() {
		setArgs(_getCompleteArgs());

		super.exec();
	}

	public String getDockerContainerId() {
		return GradleUtil.toString(_dockerContainerId);
	}

	public String getDockerDeployDir() {
		return GradleUtil.toString(_dockerDeployDir);
	}

	public File getSourceFile() {
		return GradleUtil.toFile(getProject(), _sourceFile);
	}

	public void setDockerContainerId(Object dockerContainerId) {
		_dockerContainerId = dockerContainerId;
	}

	public void setDockerDeployDir(Object dockerDeployDir) {
		_dockerDeployDir = dockerDeployDir;
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

		sb.append(getDockerContainerId());
		sb.append(':');
		sb.append(getDockerDeployDir());
		sb.append(sourceFile.getName());

		args.add(sb.toString());

		return args;
	}

	private Object _dockerContainerId;
	private Object _dockerDeployDir;
	private Object _sourceFile;

}