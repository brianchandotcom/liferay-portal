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

import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.reporting.ReportingExtension;
import org.gradle.api.tasks.JavaExec;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.compile.JavaCompile;

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

		_addTaskFindSecurityBugs(
			writeFindBugsProjectTask, findSecurityBugsConfiguration,
			findSecurityBugsPluginsConfiguration);
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

		javaExec.doFirst(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					JavaExec javaExec = (JavaExec)task;

					ReportingExtension reportingExtension =
						GradleUtil.getExtension(
							javaExec.getProject(), ReportingExtension.class);

					File outputDir = new File(
						reportingExtension.getBaseDir(), javaExec.getName());

					outputDir.mkdirs();

					File outputFile = new File(outputDir, "reports.html");

					javaExec.args(
						"-outputFile", FileUtil.getAbsolutePath(outputFile));

					javaExec.args("-pluginList", pluginClasspath.getAsPath());
				}

			});

		javaExec.dependsOn(writeFindBugsProjectTask);

		javaExec.setClasspath(classpath);
		javaExec.setDescription("Runs FindSecurityBugs on this project.");
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
				customConfigFile + ":" +
					FileUtil.getAbsolutePath(derivedSummariesTxtFile);
		}

		File falsePositivesTxtFile = project.file(
			"find-security-bugs-false-positives.txt");

		if (falsePositivesTxtFile.exists()) {
			customConfigFile =
				customConfigFile + ":" +
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

		SourceSet sourceSet = GradleUtil.getSourceSet(
			project, SourceSet.MAIN_SOURCE_SET_NAME);

		FileCollection auxClasspath = project.files(
			sourceSet.getCompileClasspath(), compileJSPTask.getClasspath());

		writeFindBugsProjectTask.setAuxClasspath(auxClasspath);

		FileCollection classpath = project.files(
			compileJSPTask.getDestinationDir(), _getUnzippedJarDir(project));

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

	/**
	 * Copied from <code>com.liferay.gradle.plugins.internal.JspCDefaultsPlugin</code>.
	 */
	private File _getUnzippedJarDir(Project project) {
		return new File(project.getBuildDir(), "unzipped-jar");
	}

	private static final String _FIND_SECURITY_BUGS_EXCLUDE_FILE_NAME =
		"fsb-exclude.xml";

	private static final String _FIND_SECURITY_BUGS_INCLUDE_FILE_NAME =
		"fsb-include.xml";

	/**
	 * Copied from <code>com.liferay.gradle.plugins.internal.JspCDefaultsPlugin</code>.
	 */
	private static final String _UNZIP_JAR_TASK_NAME = "unzipJar";

	private static final String _VERSION = "1.5.0.LIFERAY-PATCHED-1";

}