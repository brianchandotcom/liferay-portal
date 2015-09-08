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

package com.liferay.gradle.plugins.node.tasks;

import com.liferay.gradle.plugins.node.util.NodeExecutor;
import com.liferay.gradle.util.FileUtil;
import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.OSDetector;

import java.io.File;
import java.io.IOException;

import org.gradle.api.Action;
import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.file.CopySpec;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;
import org.gradle.process.ExecSpec;

/**
 * @author Andrea Di Giorgi
 */
public class DownloadNodeTask extends DefaultTask {

	public DownloadNodeTask() {
		_nodeExecutor = new NodeExecutor(getProject());

		onlyIf(
			new Spec<Task>() {

				@Override
				public boolean isSatisfiedBy(Task task) {
					File nodeDir = getNodeDir();

					if ((nodeDir != null) && nodeDir.exists()) {
						return false;
					}

					return true;
				}

			});
	}

	@TaskAction
	public void downloadNode() throws IOException {
		final File nodeDir = getNodeDir();
		final String nodeUrl = getNodeUrl();
		final Project project = getProject();

		final File nodeFile = FileUtil.get(project, nodeUrl);

		if (nodeUrl.endsWith(".tar.gz")) {
			project.delete(nodeDir);

			// Avoid using project#tarTree to extract the tarball because of
			// GRADLE-2844

			project.exec(
				new Action<ExecSpec>() {

					@Override
					public void execute(ExecSpec execSpec) {
						execSpec.args("xfz", project.relativePath(nodeFile));
						execSpec.args(
							"-C",
							project.relativePath(nodeDir.getParentFile()));

						execSpec.setExecutable("tar");
					}

				});

			String dirName = nodeFile.getName();

			dirName = dirName.substring(0, dirName.lastIndexOf(".tar.gz"));

			File dir = new File(nodeDir.getParentFile(), dirName);

			dir.renameTo(nodeDir);
		}
		else {
			project.copy(
				new Action<CopySpec>() {

					@Override
					public void execute(CopySpec copySpec) {
						copySpec.from(nodeFile);
						copySpec.into(nodeDir);
					}

				});
		}

		if (OSDetector.isWindows()) {
			final File npmFile = FileUtil.get(project, getNpmUrl());

			project.copy(
				new Action<CopySpec>() {

					@Override
					public void execute(CopySpec copySpec) {
						copySpec.from(project.zipTree(npmFile));
						copySpec.into(nodeDir);
					}

				});
		}

		_nodeExecutor.setArgs(
			"config", "set", "cache", getCacheDir(), "--global");
		_nodeExecutor.setCommand("npm");
		_nodeExecutor.setWorkingDir(getNodeDir());

		_nodeExecutor.execute();
	}

	public File getCacheDir() {
		return new File(getNodeDir(), ".cache");
	}

	@OutputDirectory
	public File getNodeDir() {
		return _nodeExecutor.getNodeDir();
	}

	@Input
	public String getNodeUrl() {
		return GradleUtil.toString(_nodeUrl);
	}

	@Input
	public String getNpmUrl() {
		return GradleUtil.toString(_npmUrl);
	}

	public void setNodeDir(Object nodeDir) {
		_nodeExecutor.setNodeDir(nodeDir);
	}

	public void setNodeUrl(Object nodeUrl) {
		_nodeUrl = nodeUrl;
	}

	public void setNpmUrl(Object npmUrl) {
		_npmUrl = npmUrl;
	}

	private final NodeExecutor _nodeExecutor;
	private Object _nodeUrl;
	private Object _npmUrl;

}