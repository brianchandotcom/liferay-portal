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

package com.liferay.gradle.plugins.service.builder;

import com.liferay.gradle.util.StringUtil;
import com.liferay.portal.tools.service.builder.ServiceBuilderArgs;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import org.gradle.api.Project;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.file.FileCollection;
import org.gradle.api.plugins.ExtensionContainer;
import org.gradle.api.tasks.JavaExec;
import org.gradle.process.JavaExecSpec;

/**
 * @author Andrea Di Giorgi
 */
public class BuildServiceTask extends JavaExec {

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
		super.setArgs(getArgs());
		super.setClasspath(getClasspath());
		super.setWorkingDir(getWorkingDir());

		super.exec();
	}

	@Override
	public List<String> getArgs() {
		List<String> args = new ArrayList<>();

		Project project = getProject();

		ExtensionContainer extensionContainer = project.getExtensions();

		ServiceBuilderArgs serviceBuilderArgs = extensionContainer.getByType(
			ServiceBuilderArgs.class);

		args.add("service.api.dir=" + serviceBuilderArgs.getApiDirName());
		args.add(
			"service.auto.import.default.references=" +
				serviceBuilderArgs.isAutoImportDefaultReferences());
		args.add(
			"service.auto.namespace.tables=" +
				serviceBuilderArgs.isAutoNamespaceTables());
		args.add(
			"service.bean.locator.util=" +
				serviceBuilderArgs.getBeanLocatorUtil());
		args.add(
			"service.build.number.increment=" +
				serviceBuilderArgs.isBuildNumberIncrement());
		args.add("service.build.number=" + serviceBuilderArgs.getBuildNumber());
		args.add("service.hbm.file=" + serviceBuilderArgs.getHbmFileName());
		args.add("service.impl.dir=" + serviceBuilderArgs.getImplDirName());
		args.add("service.input.file=" + serviceBuilderArgs.getInputFileName());
		args.add(
			"service.model.hints.configs=" +
				StringUtil.merge(
					serviceBuilderArgs.getModelHintsConfigs(), ","));
		args.add(
			"service.model.hints.file=" +
				serviceBuilderArgs.getModelHintsFileName());
		args.add("service.osgi.module=" + serviceBuilderArgs.isOsgiModule());
		args.add("service.plugin.name=" + serviceBuilderArgs.getPluginName());
		args.add("service.props.util=" + serviceBuilderArgs.getPropsUtil());
		args.add(
			"service.read.only.prefixes=" +
				StringUtil.merge(
					serviceBuilderArgs.getReadOnlyPrefixes(), ","));
		args.add(
			"service.remoting.file=" +
				serviceBuilderArgs.getRemotingFileName());
		args.add(
			"service.resource.actions.configs=" +
				StringUtil.merge(
					serviceBuilderArgs.getResourceActionsConfigs(), ","));
		args.add(
			"service.resources.dir=" +
				serviceBuilderArgs.getResourcesDirName());
		args.add(
			"service.spring.file=" + serviceBuilderArgs.getSpringFileName());
		args.add(
			"service.spring.namespaces=" +
				StringUtil.merge(
					serviceBuilderArgs.getSpringNamespaces(), ","));
		args.add("service.sql.dir=" + serviceBuilderArgs.getSqlDirName());
		args.add("service.sql.file=" + serviceBuilderArgs.getSqlFileName());
		args.add(
			"service.sql.indexes.file=" +
				serviceBuilderArgs.getSqlIndexesFileName());
		args.add(
			"service.sql.sequences.file=" +
				serviceBuilderArgs.getSqlSequencesFileName());
		args.add(
			"service.target.entity.name=" +
				serviceBuilderArgs.getTargetEntityName());
		args.add("service.test.dir=" + serviceBuilderArgs.getTestDirName());

		return args;
	}

	@Override
	public FileCollection getClasspath() {
		Project project = getProject();

		ConfigurationContainer configurationContainer =
			project.getConfigurations();

		return configurationContainer.getByName(
			ServiceBuilderPlugin.CONFIGURATION_NAME);
	}

	@Override
	public String getMain() {
		return "com.liferay.portal.tools.service.builder.ServiceBuilder";
	}

	@Override
	public File getWorkingDir() {
		Project project = getProject();

		return project.getProjectDir();
	}

	@Override
	public JavaExec setArgs(Iterable<?> applicationArgs) {
		throw new UnsupportedOperationException();
	}

	@Override
	public JavaExec setClasspath(FileCollection classpath) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setWorkingDir(Object dir) {
		throw new UnsupportedOperationException();
	}

}