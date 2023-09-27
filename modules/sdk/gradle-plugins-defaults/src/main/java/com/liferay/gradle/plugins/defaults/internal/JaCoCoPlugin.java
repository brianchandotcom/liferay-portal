/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.defaults.internal;

import com.liferay.gradle.plugins.defaults.internal.util.GradleUtil;

import java.util.List;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.testing.Test;

/**
 * @author Andrea Di Giorgi
 * @author Tina Tian
 */
public class JaCoCoPlugin implements Plugin<Project> {

	public static final Plugin<Project> INSTANCE = new JaCoCoPlugin();

	@Override
	public void apply(Project project) {
		Test test = (Test)GradleUtil.getTask(
			project, JavaPlugin.TEST_TASK_NAME);

		test.doFirst(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					Test test = (Test)task;

					Project project = test.getProject();

					String jaCoCoAgentJar = GradleUtil.getProperty(
						project, "jacoco.agent.jar", (String)null);
					String jaCoCoAgentConfiguration = GradleUtil.getProperty(
						project, "jacoco.agent.configuration", (String)null);

					String jaCoCoJvmArg =
						"-javaagent:" + jaCoCoAgentJar +
							jaCoCoAgentConfiguration;

					List<String> allJVMArgs = test.getAllJvmArgs();

					for (int i = 0; i < allJVMArgs.size(); i++) {
						String jvmArg = allJVMArgs.get(i);

						if (jvmArg.contains("-javaagent:")) {
							jvmArg = jvmArg.replaceFirst(
								"-javaagent:", jaCoCoJvmArg + " -javaagent:");

							allJVMArgs.set(i, jvmArg);

							test.setAllJvmArgs(allJVMArgs);

							return;
						}
					}

					test.jvmArgs(jaCoCoJvmArg);
				}

			});

		test.systemProperty("junit.code.coverage", "true");
	}

	private JaCoCoPlugin() {
	}

}