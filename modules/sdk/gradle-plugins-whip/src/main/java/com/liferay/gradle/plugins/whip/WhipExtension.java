/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.whip;

import com.liferay.gradle.util.GradleUtil;

import java.io.File;

import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.process.JavaForkOptions;

/**
 * @author Andrea Di Giorgi
 */
public class WhipExtension {

	public static final String TASK_EXTENSION_NAME = "whip";

	public WhipExtension(Project project) {
		_project = project;
	}

	public <T extends JavaForkOptions & Task> void applyTo(T task) {
		WhipTaskExtension whipTaskExtension = GradleUtil.addExtension(
			task, TASK_EXTENSION_NAME, WhipTaskExtension.class);

		whipTaskExtension.setDataFile(_project.file("test-coverage/whip.dat"));
		whipTaskExtension.setWhipJarFile(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					Configuration configuration = GradleUtil.getConfiguration(
						_project, WhipPlugin.CONFIGURATION_NAME);

					for (File file : configuration.getFiles()) {
						String fileName = file.getName();

						if (fileName.startsWith("com.liferay.whip-")) {
							return file;
						}
					}

					return null;
				}

			});

		task.doFirst(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					WhipTaskExtension whipTaskExtension =
						GradleUtil.getExtension(task, WhipTaskExtension.class);

					if (!whipTaskExtension.isEnabled()) {
						return;
					}

					JavaForkOptions javaForkOptions = (JavaForkOptions)task;

					javaForkOptions.jvmArgs(whipTaskExtension.getAsJvmArgs());
					javaForkOptions.systemProperties(
						whipTaskExtension.getAsSystemProperties());
				}

			});
	}

	public String getVersion() {
		return _version;
	}

	public void setVersion(String version) {
		_version = version;
	}

	private final Project _project;
	private String _version = "latest.release";

}