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
import com.liferay.gradle.plugins.defaults.tasks.WriteFindBugsProjectTask;
import com.liferay.gradle.plugins.jasper.jspc.CompileJSPTask;
import com.liferay.gradle.plugins.jasper.jspc.JspCPlugin;

import java.io.File;

import java.util.Set;
import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.Transformer;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.logging.Logger;
import org.gradle.api.plugins.JavaBasePlugin;
import org.gradle.api.reporting.ReportingExtension;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.JavaExec;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetOutput;
import org.gradle.api.tasks.compile.JavaCompile;
import org.gradle.language.base.plugins.LifecycleBasePlugin;

/**
 * @author Andrea Di Giorgi
 */
public class FindSecurityBugsPlugin implements Plugin<Project> {

	public static final String FIND_SECURITY_BUGS_CONFIGURATION_NAME =
		"findSecurityBugs";

	public static final String FIND_SECURITY_BUGS_PLUGINS_CONFIGURATION_NAME =
		"findSecurityBugsPlugins";

	public static final String FIND_SECURITY_BUGS_TASK_NAME =
		"findSecurityBugs";

	public static final Plugin<Project> INSTANCE = new FindSecurityBugsPlugin();

	public static final String WRITE_FIND_BUGS_PROJECT_TASK_NAME =
		"writeFindBugsProject";

	@Override
	public void apply(Project project) {
		Configuration findSecurityBugsConfiguration =
			_addConfigurationFindSecurityBugs(project);
		Configuration findSecurityBugsPluginsConfiguration =
			_addConfigurationFindSecurityBugsPlugins(project);

		WriteFindBugsProjectTask writeFindBugsProjectTask =
			_addTaskWriteFindBugsProject(project);

		Task findSecurityBugsTask = _addTaskFindSecurityBugs(
			writeFindBugsProjectTask, findSecurityBugsConfiguration,
			findSecurityBugsPluginsConfiguration);

		_checkTaskCheck(findSecurityBugsTask);
	}

	private FindSecurityBugsPlugin() {
	}

	private Configuration _addConfigurationFindSecurityBugs(
		final Project project) {

		Configuration configuration = GradleUtil.addConfiguration(
			project, FIND_SECURITY_BUGS_CONFIGURATION_NAME);

		configuration.defaultDependencies(
			new Action<DependencySet>() {

				@Override
				public void execute(DependencySet dependencySet) {
					_addDependenciesFindSecurityBugs(project);
				}

			});

		configuration.setDescription(
			"Configures FindBugs for the '" + FIND_SECURITY_BUGS_TASK_NAME +
				"' task.");
		configuration.setVisible(false);

		return configuration;
	}

	private Configuration _addConfigurationFindSecurityBugsPlugins(
		final Project project) {

		Configuration configuration = GradleUtil.addConfiguration(
			project, FIND_SECURITY_BUGS_PLUGINS_CONFIGURATION_NAME);

		configuration.defaultDependencies(
			new Action<DependencySet>() {

				@Override
				public void execute(DependencySet dependencySet) {
					_addDependenciesFindSecurityBugsPlugins(project);
				}

			});

		configuration.setDescription("Configures FindSecurityBugs.");
		configuration.setVisible(false);

		return configuration;
	}

	private void _addDependenciesFindSecurityBugs(Project project) {
		GradleUtil.addDependency(
			project, FIND_SECURITY_BUGS_CONFIGURATION_NAME,
			"com.google.code.findbugs", "findbugs", "3.0.1");
	}

	private void _addDependenciesFindSecurityBugsPlugins(Project project) {
		GradleUtil.addDependency(
			project, FIND_SECURITY_BUGS_PLUGINS_CONFIGURATION_NAME,
			"com.liferay", "com.h3xstream.findsecbugs", _VERSION);
	}

	private JavaExec _addTaskFindSecurityBugs(
		final WriteFindBugsProjectTask writeFindBugsProjectTask,
		FileCollection classpath, final FileCollection pluginClasspath) {

		Project project = writeFindBugsProjectTask.getProject();

		JavaExec javaExec = GradleUtil.addTask(
			project, FIND_SECURITY_BUGS_TASK_NAME, JavaExec.class);

		javaExec.args(
			"-bugCategories", "SECURITY", "-effort:max", "-html", "-low",
			"-progress", "-timestampNow");

		File excludeDir = GradleUtil.getRootDir(
			project, _FIND_SECURITY_BUGS_EXCLUDE_FILE_NAME);

		if (excludeDir != null) {
			File excludeFile = new File(
				excludeDir, _FIND_SECURITY_BUGS_EXCLUDE_FILE_NAME);

			javaExec.args("-exclude", FileUtil.getAbsolutePath(excludeFile));
		}

		File includeDir = GradleUtil.getRootDir(
			project, _FIND_SECURITY_BUGS_INCLUDE_FILE_NAME);

		if (includeDir != null) {
			File includeFile = new File(
				includeDir, _FIND_SECURITY_BUGS_INCLUDE_FILE_NAME);

			javaExec.args("-include", FileUtil.getAbsolutePath(includeFile));
		}

		javaExec.args(
			"-project",
			new Object() {

				@Override
				public String toString() {
					return FileUtil.getAbsolutePath(
						writeFindBugsProjectTask.getOutputFile());
				}

			});

		final Transformer<File, Task> outputFileGetter =
			new Transformer<File, Task>() {

				@Override
				public File transform(Task task) {
					ReportingExtension reportingExtension =
						GradleUtil.getExtension(
							task.getProject(), ReportingExtension.class);

					return new File(
						reportingExtension.getBaseDir(),
						task.getName() + "/reports.html");
				}

			};

		javaExec.doFirst(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					JavaExec javaExec = (JavaExec)task;

					Logger logger = javaExec.getLogger();

					File outputFile = outputFileGetter.transform(javaExec);

					File outputDir = outputFile.getParentFile();

					outputDir.mkdirs();

					javaExec.args(
						"-outputFile", FileUtil.getAbsolutePath(outputFile));

					javaExec.args("-pluginList", pluginClasspath.getAsPath());

					if (logger.isLifecycleEnabled()) {
						logger.lifecycle(
							"Using Find Security Bugs version " + _VERSION);
					}
				}

			});

		javaExec.doLast(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					Logger logger = task.getLogger();

					File outputFile = outputFileGetter.transform(task);

					if (logger.isLifecycleEnabled()) {
						logger.lifecycle(
							"Find Security Bugs report saved to {}.",
							outputFile.getAbsolutePath());
					}
				}

			});

		javaExec.dependsOn(writeFindBugsProjectTask);

		javaExec.onlyIf(
			new Spec<Task>() {

				@Override
				public boolean isSatisfiedBy(Task task) {
					FileCollection fileCollection =
						writeFindBugsProjectTask.getClasspath();

					if (fileCollection == null) {
						return true;
					}

					Set<File> files = fileCollection.getFiles();

					return _containsClassOrJar(
						files.toArray(new File[files.size()]));
				}

				private boolean _containsClassOrJar(File[] files) {
					for (File file : files) {
						if (!file.exists()) {
							continue;
						}

						if (file.isFile()) {
							String fileName = file.getName();

							if (fileName.endsWith(".class") ||
								fileName.endsWith(".jar")) {

								return true;
							}
						}
						else if (_containsClassOrJar(file.listFiles())) {
							return true;
						}
					}

					return false;
				}

			});

		javaExec.setClasspath(classpath);
		javaExec.setDescription("Runs FindSecurityBugs on this project.");
		javaExec.setGroup(JavaBasePlugin.VERIFICATION_GROUP);
		javaExec.setMain("edu.umd.cs.findbugs.FindBugs2");

		javaExec.systemProperty(
			"findsecbugs.injection.customconfigfile.SqlInjectionDetector",
			"liferay-config/liferay-SqlInjectionDetector.txt|" +
				"SQL_INJECTION_HIBERNATE");
		javaExec.systemProperty(
			"findsecbugs.injection.customconfigfile.XssJspDetector",
			"liferay-config/liferay-XssJspDetector.txt|XSS_JSP_PRINT");

		javaExec.systemProperty("findsecbugs.taint.outputsummaries", "true");

		String customConfigFile = "liferay-config/liferay.txt";

		File derivedSummariesTxtFile = project.file("derived-summaries.txt");

		if (derivedSummariesTxtFile.exists()) {
			customConfigFile =
				customConfigFile + File.pathSeparator +
					FileUtil.getAbsolutePath(derivedSummariesTxtFile);
		}

		File falsePositivesTxtFile = project.file(
			"find-security-bugs-false-positives.txt");

		if (falsePositivesTxtFile.exists()) {
			customConfigFile =
				customConfigFile + File.pathSeparator +
					FileUtil.getAbsolutePath(falsePositivesTxtFile);
		}

		javaExec.systemProperty(
			"findsecbugs.taint.customconfigfile", customConfigFile);

		return javaExec;
	}

	private WriteFindBugsProjectTask _addTaskWriteFindBugsProject(
		final Project project) {

		WriteFindBugsProjectTask writeFindBugsProjectTask = GradleUtil.addTask(
			project, WRITE_FIND_BUGS_PROJECT_TASK_NAME,
			WriteFindBugsProjectTask.class);

		JavaCompile compileJSPTask = (JavaCompile)GradleUtil.getTask(
			project, JspCPlugin.COMPILE_JSP_TASK_NAME);

		writeFindBugsProjectTask.dependsOn(
			_UNZIP_JAR_TASK_NAME, compileJSPTask);

		final SourceSet sourceSet = GradleUtil.getSourceSet(
			project, SourceSet.MAIN_SOURCE_SET_NAME);

		FileCollection auxClasspath = project.files(
			sourceSet.getCompileClasspath(), compileJSPTask.getClasspath());

		writeFindBugsProjectTask.setAuxClasspath(auxClasspath);

		FileCollection classpath = project.files(
			compileJSPTask.getDestinationDir(),
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					SourceSetOutput sourceSetOutput = sourceSet.getOutput();

					return sourceSetOutput.getClassesDir();
				}

			});

		writeFindBugsProjectTask.setClasspath(classpath);

		writeFindBugsProjectTask.setDescription(
			"Writes the FindBugs project file.");

		writeFindBugsProjectTask.setOutputFile(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return new File(project.getBuildDir(), "findbugs.xml");
				}

			});

		writeFindBugsProjectTask.setProjectName(project.getName());

		CompileJSPTask generateJSPJavaTask = (CompileJSPTask)GradleUtil.getTask(
			project, JspCPlugin.GENERATE_JSP_JAVA_TASK_NAME);
		SourceDirectorySet sourceDirectorySet = sourceSet.getJava();

		FileCollection srcDirs = project.files(
			sourceDirectorySet.getSrcDirs(),
			generateJSPJavaTask.getDestinationDir());

		writeFindBugsProjectTask.setSrcDirs(srcDirs);

		return writeFindBugsProjectTask;
	}

	private void _checkTaskCheck(Task findSecurityBugsTask) {
		Task task = GradleUtil.getTask(
			findSecurityBugsTask.getProject(),
			LifecycleBasePlugin.CHECK_TASK_NAME);

		task.dependsOn(findSecurityBugsTask);
	}

	private static final String _FIND_SECURITY_BUGS_EXCLUDE_FILE_NAME =
		"fsb-exclude.xml";

	private static final String _FIND_SECURITY_BUGS_INCLUDE_FILE_NAME =
		"fsb-include.xml";

	/**
	 * Copied from <code>com.liferay.gradle.plugins.internal.JspCDefaultsPlugin</code>.
	 */
	private static final String _UNZIP_JAR_TASK_NAME = "unzipJar";

	private static final String _VERSION = "1.6.0.LIFERAY-PATCHED-2";

}