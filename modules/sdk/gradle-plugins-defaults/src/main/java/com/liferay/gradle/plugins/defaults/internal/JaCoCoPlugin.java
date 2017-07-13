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