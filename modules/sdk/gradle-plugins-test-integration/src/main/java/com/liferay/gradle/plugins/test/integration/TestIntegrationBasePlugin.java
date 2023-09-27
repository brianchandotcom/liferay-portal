/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.test.integration;

import com.liferay.gradle.plugins.test.integration.internal.util.GradleUtil;
import com.liferay.gradle.plugins.test.integration.internal.util.ReflectionUtil;
import com.liferay.gradle.util.FileUtil;

import java.io.File;

import java.lang.reflect.Method;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.GradleException;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.internal.ConventionMapping;
import org.gradle.api.invocation.Gradle;
import org.gradle.api.plugins.JavaBasePlugin;
import org.gradle.api.plugins.JavaLibraryPlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.PluginContainer;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetOutput;
import org.gradle.api.tasks.testing.Test;
import org.gradle.language.base.plugins.LifecycleBasePlugin;
import org.gradle.plugins.ide.eclipse.EclipsePlugin;
import org.gradle.plugins.ide.eclipse.model.EclipseClasspath;
import org.gradle.plugins.ide.eclipse.model.EclipseModel;
import org.gradle.plugins.ide.idea.IdeaPlugin;
import org.gradle.plugins.ide.idea.model.IdeaModel;
import org.gradle.plugins.ide.idea.model.IdeaModule;
import org.gradle.util.VersionNumber;

/**
 * @author Andrea Di Giorgi
 */
public class TestIntegrationBasePlugin implements Plugin<Project> {

	public static final String TEST_INTEGRATION_SOURCE_SET_NAME =
		"testIntegration";

	public static final String TEST_INTEGRATION_TASK_NAME = "testIntegration";

	@Override
	public void apply(Project project) {
		GradleUtil.applyPlugin(project, JavaLibraryPlugin.class);

		SourceSet testIntegrationSourceSet = _addSourceSetTestIntegration(
			project);

		Test testIntegrationTask = _addTaskTestIntegration(
			project, testIntegrationSourceSet);

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					_configureEclipse(project, testIntegrationSourceSet);
					_configureIdea(project, testIntegrationSourceSet);
				}

			});

		_configureTaskCheck(testIntegrationTask);
	}

	private SourceSet _addSourceSetTestIntegration(Project project) {
		SourceSet testIntegrationSourceSet = GradleUtil.addSourceSet(
			project, TEST_INTEGRATION_SOURCE_SET_NAME);

		Configuration testIntegrationImplementationConfiguration =
			GradleUtil.getConfiguration(
				project,
				testIntegrationSourceSet.getImplementationConfigurationName());

		Configuration testImplementationConfiguration =
			GradleUtil.getConfiguration(
				project, JavaPlugin.TEST_IMPLEMENTATION_CONFIGURATION_NAME);

		testIntegrationImplementationConfiguration.extendsFrom(
			testImplementationConfiguration);

		Configuration testIntegrationRuntimeOnlyConfiguration =
			GradleUtil.getConfiguration(
				project,
				testIntegrationSourceSet.getRuntimeOnlyConfigurationName());

		Configuration testRuntimeOnlyConfiguration =
			GradleUtil.getConfiguration(
				project, JavaPlugin.TEST_RUNTIME_ONLY_CONFIGURATION_NAME);

		testIntegrationRuntimeOnlyConfiguration.extendsFrom(
			testRuntimeOnlyConfiguration,
			testIntegrationImplementationConfiguration);

		SourceSet mainSourceSet = GradleUtil.getSourceSet(
			project, SourceSet.MAIN_SOURCE_SET_NAME);

		FileCollection compileClasspath =
			testIntegrationSourceSet.getCompileClasspath();

		testIntegrationSourceSet.setCompileClasspath(
			compileClasspath.plus(mainSourceSet.getOutput()));

		FileCollection runtimeClasspath =
			testIntegrationSourceSet.getRuntimeClasspath();

		testIntegrationSourceSet.setRuntimeClasspath(
			runtimeClasspath.plus(mainSourceSet.getOutput()));

		return testIntegrationSourceSet;
	}

	private Test _addTaskTestIntegration(
		Project project, final SourceSet testIntegrationSourceSet) {

		final Test test = GradleUtil.addTask(
			project, TEST_INTEGRATION_TASK_NAME, Test.class);

		test.mustRunAfter(JavaPlugin.TEST_TASK_NAME);

		test.setDescription("Runs the integration tests.");
		test.setForkEvery(null);
		test.setGroup(JavaBasePlugin.VERIFICATION_GROUP);

		ConventionMapping conventionMapping = test.getConventionMapping();

		conventionMapping.map(
			"classpath",
			new Callable<FileCollection>() {

				@Override
				public FileCollection call() throws Exception {
					return testIntegrationSourceSet.getRuntimeClasspath();
				}

			});

		final SourceSetOutput sourceSetOutput =
			testIntegrationSourceSet.getOutput();

		final Method getClassesDirsMethod = ReflectionUtil.getMethod(
			sourceSetOutput, "getClassesDirs");

		if (getClassesDirsMethod != null) {

			// https://github.com/gradle/gradle/issues/2343

			Gradle gradle = project.getGradle();

			VersionNumber versionNumber = VersionNumber.parse(
				gradle.getGradleVersion());

			if ((versionNumber.getMajor() == 4) &&
				(versionNumber.getMinor() < 1)) {

				Method setTestClassesDirsMethod = ReflectionUtil.getMethod(
					test, "setTestClassesDirs", FileCollection.class);

				try {
					FileCollection testClassesDirs =
						(FileCollection)getClassesDirsMethod.invoke(
							sourceSetOutput);

					setTestClassesDirsMethod.invoke(test, testClassesDirs);
				}
				catch (Exception exception) {
					throw new GradleException(
						"Unable to set the \"testClassesDirs\" property of " +
							test,
						exception);
				}
			}

			conventionMapping.map(
				"testClassesDirs",
				new Callable<FileCollection>() {

					@Override
					public FileCollection call() throws Exception {
						return (FileCollection)getClassesDirsMethod.invoke(
							sourceSetOutput);
					}

				});
		}
		else {
			conventionMapping.map(
				"testClassesDir",
				new Callable<File>() {

					@Override
					public File call() throws Exception {
						return FileUtil.getJavaClassesDir(
							testIntegrationSourceSet);
					}

				});
		}

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					Set<String> includes = test.getIncludes();

					if (includes.isEmpty()) {
						test.setIncludes(
							Collections.singleton("**/*Test.class"));
					}
				}

			});

		return test;
	}

	private void _configureEclipse(
		final Project project, final SourceSet testIntegrationSourceSet) {

		PluginContainer pluginContainer = project.getPlugins();

		pluginContainer.withType(
			EclipsePlugin.class,
			new Action<EclipsePlugin>() {

				@Override
				public void execute(EclipsePlugin eclipsePlugin) {
					_configureEclipseClasspath(
						project, testIntegrationSourceSet);
				}

			});
	}

	private void _configureEclipseClasspath(
		Project project, SourceSet testIntegrationSourceSet) {

		EclipseModel eclipseModel = GradleUtil.getExtension(
			project, EclipseModel.class);

		EclipseClasspath eclipseClasspath = eclipseModel.getClasspath();

		Collection<Configuration> plusConfigurations =
			eclipseClasspath.getPlusConfigurations();

		plusConfigurations.add(
			GradleUtil.getConfiguration(
				project,
				testIntegrationSourceSet.
					getRuntimeClasspathConfigurationName()));
	}

	private void _configureIdea(
		final Project project, final SourceSet testIntegrationSourceSet) {

		PluginContainer pluginContainer = project.getPlugins();

		pluginContainer.withType(
			IdeaPlugin.class,
			new Action<IdeaPlugin>() {

				@Override
				public void execute(IdeaPlugin ideaPlugin) {
					_configureIdeaModule(project, testIntegrationSourceSet);
				}

			});
	}

	private void _configureIdeaModule(
		Project project, SourceSet testIntegrationSourceSet) {

		IdeaModel ideaModel = GradleUtil.getExtension(project, IdeaModel.class);

		IdeaModule ideaModule = ideaModel.getModule();

		Map<String, Map<String, Collection<Configuration>>> scopes =
			ideaModule.getScopes();

		Map<String, Collection<Configuration>> testScope = scopes.get("TEST");

		Collection<Configuration> plusConfigurations = testScope.get("plus");

		plusConfigurations.add(
			GradleUtil.getConfiguration(
				project,
				testIntegrationSourceSet.
					getRuntimeClasspathConfigurationName()));

		Set<File> testSrcDirs = new LinkedHashSet<>(
			ideaModule.getTestSourceDirs());

		SourceDirectorySet sourceDirectorySet =
			testIntegrationSourceSet.getAllSource();

		testSrcDirs.addAll(sourceDirectorySet.getSrcDirs());

		ideaModule.setTestSourceDirs(testSrcDirs);
	}

	private void _configureTaskCheck(Test test) {
		Task task = GradleUtil.getTask(
			test.getProject(), LifecycleBasePlugin.CHECK_TASK_NAME);

		task.dependsOn(test);
	}

}