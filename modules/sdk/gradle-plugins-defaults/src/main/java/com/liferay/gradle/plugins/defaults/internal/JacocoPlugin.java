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

import com.liferay.gradle.plugins.defaults.internal.util.FileUtil;
import com.liferay.gradle.plugins.defaults.internal.util.GradleUtil;
import com.liferay.gradle.util.Validator;

import java.io.File;

import java.util.List;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.testing.Test;

/**
 * @author Andrea Di Giorgi
 * @author Tina Tian
 */
public class JacocoPlugin implements Plugin<Project> {

	public static final Plugin<Project> INSTANCE = new JacocoPlugin();

	public static final String JACOCO_AGENT_CONFIGURATION_NAME = "jacocoAgent";

	@Override
	public void apply(Project project) {
		Configuration jacocoAgentConfiguration = _addConfigurationJacocoAgent(
			project);

		_configureTasksTest(project, jacocoAgentConfiguration);
	}

	private JacocoPlugin() {
	}

	private Configuration _addConfigurationJacocoAgent(final Project project) {
		Configuration configuration = GradleUtil.addConfiguration(
			project, JACOCO_AGENT_CONFIGURATION_NAME);

		configuration.defaultDependencies(
			new Action<DependencySet>() {

				@Override
				public void execute(DependencySet dependencySet) {
					_addDependenciesJacocoAgent(project);
				}

			});

		configuration.setDescription(
			"Configures Jacoco Agent to apply to the test tasks.");
		configuration.setTransitive(false);
		configuration.setVisible(false);

		return configuration;
	}

	private void _addDependenciesJacocoAgent(Project project) {
		GradleUtil.addDependency(
			project, JACOCO_AGENT_CONFIGURATION_NAME, "org.jacoco",
			"org.jacoco.agent", "0.7.9", "runtime", true);
	}

	private void _configureTasksTest(
		Project project, final Configuration jacocoAgentConfiguration) {

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			Test.class,
			new Action<Test>() {

				@Override
				public void execute(Test test) {
					_configureTaskTest(test, jacocoAgentConfiguration);
				}

			});
	}

	private void _configureTaskTest(
		Test test, final Configuration jacocoAgentConfiguration) {

		test.doFirst(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					Test test = (Test)task;

					Project project = test.getProject();

					String jacocoAgentFileName = FileUtil.getAbsolutePath(
						jacocoAgentConfiguration.getSingleFile());

					String jacocoDumpFileName = System.getProperty(
						"jacoco.dump.file");

					if (Validator.isNull(jacocoDumpFileName)) {
						File jacocoDumpFile = new File(
							project.getBuildDir(),
							"jacoco/" + test.getName() + ".exec");

						jacocoDumpFileName = FileUtil.getAbsolutePath(
							jacocoDumpFile);
					}

					String jacocoJvmArg =
						"-javaagent:" + jacocoAgentFileName + "=destfile=" +
							jacocoDumpFileName + ",output=file,append=true";

					List<String> allJVMArgs = test.getAllJvmArgs();

					for (int i = 0; i < allJVMArgs.size(); i++) {
						String jvmArg = allJVMArgs.get(i);

						if (jvmArg.contains("-javaagent:")) {
							jvmArg = jvmArg.replaceFirst(
								"-javaagent:", jacocoJvmArg + " -javaagent:");

							allJVMArgs.set(i, jvmArg);

							test.setAllJvmArgs(allJVMArgs);

							return;
						}
					}

					test.jvmArgs(jacocoJvmArg);
				}

			});

		test.systemProperty("jacoco.code.coverage", "true");
	}

}