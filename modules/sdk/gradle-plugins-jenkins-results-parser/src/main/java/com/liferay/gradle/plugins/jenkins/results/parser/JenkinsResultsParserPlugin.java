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

package com.liferay.gradle.plugins.jenkins.results.parser;

import com.liferay.gradle.util.GradleUtil;

import java.io.File;

import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.file.FileCollection;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.tasks.JavaExec;
import org.gradle.util.GUtil;

/**
 * @author Andrea Di Giorgi
 */
public class JenkinsResultsParserPlugin implements Plugin<Project> {

	public static final String EXPORT_TESTRAY_RESULTS_TASK_NAME =
		"exportTestrayResults";

	public static final String JENKINS_RESULTS_PARSER_CONFIGURATION_NAME =
		"jenkinsResultsParser";

	@Override
	public void apply(Project project) {
		GradleUtil.applyPlugin(project, BasePlugin.class);

		final JenkinsResultsParserExtension jenkinsResultsParserExtension =
			GradleUtil.addExtension(
				project, "jenkinsResultsParser",
				JenkinsResultsParserExtension.class);

		_addConfigurationJenkinsResultsParser(
			project, jenkinsResultsParserExtension);

		final JavaExec exportTestrayResultsTask = _addTaskExportTestrayResults(
			project);

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					_configureTaskExportTestrayResults(
						exportTestrayResultsTask,
						jenkinsResultsParserExtension);
				}

			});
	}

	private Configuration _addConfigurationJenkinsResultsParser(
		final Project project,
		final JenkinsResultsParserExtension jenkinsResultsParserExtension) {

		Configuration configuration = GradleUtil.addConfiguration(
			project, JENKINS_RESULTS_PARSER_CONFIGURATION_NAME);

		configuration.defaultDependencies(
			new Action<DependencySet>() {

				@Override
				public void execute(DependencySet dependencySet) {
					_addDependenciesJenkinsResultsParser(
						project, jenkinsResultsParserExtension);
				}

			});

		configuration.setDescription(
			"Configures Jenkins Results Parser for this project.");
		configuration.setVisible(false);

		return configuration;
	}

	private void _addDependenciesJenkinsResultsParser(
		Project project,
		JenkinsResultsParserExtension jenkinsResultsParserExtension) {

		GradleUtil.addDependency(
			project, JENKINS_RESULTS_PARSER_CONFIGURATION_NAME, "com.liferay",
			"com.liferay.jenkins.results.parser",
			jenkinsResultsParserExtension.getVersion());
	}

	private JavaExec _addTaskExportTestrayResults(Project project) {
		JavaExec javaExec = GradleUtil.addTask(
			project, EXPORT_TESTRAY_RESULTS_TASK_NAME, JavaExec.class);

		javaExec.setClasspath(_getJenkinsResultsParserClasspath(project));
		javaExec.setDescription("Export Testray results.");
		javaExec.setGroup("testray");
		javaExec.setMain(
			"com.liferay.jenkins.results.parser.testray." +
				"DXPCloudClientTestrayImporter");

		return javaExec;
	}

	private void _configureTaskExportTestrayResults(
		JavaExec javaExec,
		JenkinsResultsParserExtension jenkinsResultsParserExtension) {

		Properties testrayProperties = _getTestrayProperties(
			jenkinsResultsParserExtension);

		_populateSystemProperties(
			javaExec.getSystemProperties(), testrayProperties);
	}

	private File _getExtPropertiesFile(File propertiesFile) {
		String fileName = propertiesFile.getName();

		int pos = fileName.lastIndexOf('.');

		if (pos <= 0) {
			return new File(propertiesFile.getParentFile(), fileName + "-ext");
		}

		String extension = fileName.substring(pos + 1);

		String shortFileName = fileName.substring(
			0, fileName.length() - extension.length() - 1);

		return new File(
			propertiesFile.getParentFile(),
			shortFileName + "-ext." + extension);
	}

	private FileCollection _getJenkinsResultsParserClasspath(Project project) {
		Configuration jenkinsResultsParserConfiguration = GradleUtil.getConfiguration(
			project, JENKINS_RESULTS_PARSER_CONFIGURATION_NAME);

		return project.files(jenkinsResultsParserConfiguration);
	}

	private Properties _getTestrayProperties(
		JenkinsResultsParserExtension jenkinsResultsParserExtension) {

		Properties testrayProperties = new Properties();

		File testrayPropertiesFile =
			jenkinsResultsParserExtension.getTestrayPropertiesFile();

		if ((testrayPropertiesFile == null) ||
			!testrayPropertiesFile.exists()) {

			return testrayProperties;
		}

		testrayProperties = GUtil.loadProperties(testrayPropertiesFile);

		File testrayExtPropertiesFile = _getExtPropertiesFile(
			testrayPropertiesFile);

		if (testrayExtPropertiesFile.exists()) {
			testrayProperties.putAll(
				GUtil.loadProperties(testrayExtPropertiesFile));
		}

		return testrayProperties;
	}

	private void _populateSystemProperties(
		Map<String, Object> systemProperties, Properties properties) {

		Enumeration<String> enumeration =
			(Enumeration<String>)properties.propertyNames();

		while (enumeration.hasMoreElements()) {
			String key = enumeration.nextElement();

			systemProperties.put(key, properties.getProperty(key));
		}
	}

}