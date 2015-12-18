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

import com.liferay.gradle.util.FileUtil;
import com.liferay.gradle.util.GradleUtil;

import java.io.InputStream;

import java.util.List;

import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.file.FileCollection;
import org.gradle.api.tasks.JavaExec;
import org.gradle.process.JavaExecSpec;

/**
 * @author Andrea Di Giorgi
 */
public abstract class BasePortalToolsTask extends JavaExec {

	public BasePortalToolsTask() {
		project = getProject();

		addConfiguration();
	}

	@Override
	public JavaExecSpec args(Iterable<?> args) {
		throw new UnsupportedOperationException();
	}

	@Override
	public JavaExec args(Object... args) {
		throw new UnsupportedOperationException();
	}

	@Override
	public JavaExec classpath(Object... paths) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void exec() {
		doExec(getArgs());
	}

	@Override
	public abstract List<String> getArgs();

	@Override
	public FileCollection getClasspath() {
		return GradleUtil.getConfiguration(project, getConfigurationName());
	}

	@Override
	public abstract String getMain();

	@Override
	public JavaExec setArgs(Iterable<?> args) {
		throw new UnsupportedOperationException();
	}

	@Override
	public JavaExec setClasspath(FileCollection fileCollection) {
		throw new UnsupportedOperationException();
	}

	@Override
	public JavaExec setStandardInput(InputStream inputStream) {
		throw new UnsupportedOperationException();
	}

	protected Configuration addConfiguration() {
		ConfigurationContainer configurationContainer =
			project.getConfigurations();

		Configuration configuration = configurationContainer.findByName(
			getConfigurationName());

		if (configuration != null) {
			return configuration;
		}

		configuration = GradleUtil.addConfiguration(
			project, getConfigurationName());

		configuration.setDescription(
			"Configures the " + getToolName() + " tool for this project.");
		configuration.setVisible(false);

		GradleUtil.executeIfEmpty(
			configuration,
			new Action<Configuration>() {

				@Override
				public void execute(Configuration configuration) {
					addDependencies();
				}

			});

		return configuration;
	}

	protected void addDependencies() {
		addDependency("com.liferay.portal", "portal-impl", "default");
		addDependency("com.liferay.portal", "portal-service", "default");
		addDependency("com.liferay.portal", "util-java", "default");
		addDependency("com.thoughtworks.xstream", "xstream", "1.4.3");
		addDependency("commons-configuration", "commons-configuration", "1.6");
		addDependency("commons-io", "commons-io", "2.1");
		addDependency("commons-lang", "commons-lang", "2.6");
		addDependency("easyconf", "easyconf", "0.9.5", false);
		addDependency("javax.servlet", "javax.servlet-api", "3.0.1");
	}

	protected void addDependency(String group, String name, String version) {
		addDependency(group, name, version, true);
	}

	protected void addDependency(
		String group, String name, String version, boolean transitive) {

		GradleUtil.addDependency(
			project, getConfigurationName(), group, name, version, transitive);
	}

	protected void doExec(List<String> args) {
		super.setArgs(args);
		super.setClasspath(FileUtil.shrinkClasspath(project, getClasspath()));
		super.setErrorOutput(System.err);

		super.exec();
	}

	protected String getConfigurationName() {
		return "portalTools" + getToolName();
	}

	protected abstract String getToolName();

	protected final Project project;

}