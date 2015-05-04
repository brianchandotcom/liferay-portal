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

import com.liferay.gradle.util.FileUtil;
import com.liferay.gradle.util.StringUtil;
import com.liferay.portal.tools.service.builder.ServiceBuilderArgs;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import org.gradle.api.Project;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.file.FileCollection;
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

	public File getApiDir() {
		Project project = getProject();

		return project.file(_serviceBuilderArgs.getApiDirName());
	}

	@Override
	public List<String> getArgs() {
		List<String> args = new ArrayList<>();

		args.add("service.api.dir=" + FileUtil.getAbsolutePath(getApiDir()));
		args.add(
			"service.auto.import.default.references=" +
				isAutoImportDefaultReferences());
		args.add("service.auto.namespace.tables=" + isAutoNamespaceTables());
		args.add("service.bean.locator.util=" + getBeanLocatorUtil());
		args.add("service.build.number.increment=" + isBuildNumberIncrement());
		args.add("service.build.number=" + getBuildNumber());
		args.add("service.hbm.file=" + FileUtil.getAbsolutePath(getHbmFile()));
		args.add("service.impl.dir=" + FileUtil.getAbsolutePath(getImplDir()));
		args.add(
			"service.input.file=" + FileUtil.getAbsolutePath(getInputFile()));
		args.add(
			"service.model.hints.configs=" +
				StringUtil.merge(getModelHintsConfigs(), ","));
		args.add(
			"service.model.hints.file=" +
				FileUtil.getAbsolutePath(getModelHintsFile()));
		args.add("service.osgi.module=" + isOsgiModule());
		args.add("service.plugin.name=" + getPluginName());
		args.add("service.props.util=" + getPropsUtil());
		args.add(
			"service.read.only.prefixes=" +
				StringUtil.merge(getReadOnlyPrefixes(), ","));
		args.add(
			"service.remoting.file=" +
				FileUtil.getAbsolutePath(getRemotingFile()));
		args.add(
			"service.resource.actions.configs=" +
				StringUtil.merge(getResourceActionsConfigs(), ","));
		args.add(
			"service.resources.dir=" +
				FileUtil.getAbsolutePath(getResourcesDir()));
		args.add(
			"service.spring.file=" + FileUtil.getAbsolutePath(getSpringFile()));
		args.add(
			"service.spring.namespaces=" +
				StringUtil.merge(getSpringNamespaces(), ","));
		args.add("service.sql.dir=" + FileUtil.getAbsolutePath(getSqlDir()));
		args.add("service.sql.file=" + getSqlFileName());
		args.add("service.sql.indexes.file=" + getSqlIndexesFileName());
		args.add("service.sql.sequences.file=" + getSqlSequencesFileName());
		args.add("service.target.entity.name=" + getTargetEntityName());
		args.add("service.test.dir=" + FileUtil.getAbsolutePath(getTestDir()));

		return args;
	}

	public String getBeanLocatorUtil() {
		return _serviceBuilderArgs.getBeanLocatorUtil();
	}

	public long getBuildNumber() {
		return _serviceBuilderArgs.getBuildNumber();
	}

	@Override
	public FileCollection getClasspath() {
		Project project = getProject();

		ConfigurationContainer configurationContainer =
			project.getConfigurations();

		return configurationContainer.getByName(
			ServiceBuilderPlugin.CONFIGURATION_NAME);
	}

	public File getHbmFile() {
		Project project = getProject();

		return project.file(_serviceBuilderArgs.getHbmFileName());
	}

	public File getImplDir() {
		Project project = getProject();

		return project.file(_serviceBuilderArgs.getImplDirName());
	}

	public File getInputFile() {
		Project project = getProject();

		return project.file(_serviceBuilderArgs.getInputFileName());
	}

	@Override
	public String getMain() {
		return "com.liferay.portal.tools.service.builder.ServiceBuilder";
	}

	public String[] getModelHintsConfigs() {
		return _serviceBuilderArgs.getModelHintsConfigs();
	}

	public File getModelHintsFile() {
		Project project = getProject();

		return project.file(_serviceBuilderArgs.getModelHintsFileName());
	}

	public String getPluginName() {
		return _serviceBuilderArgs.getPluginName();
	}

	public String getPropsUtil() {
		return _serviceBuilderArgs.getPropsUtil();
	}

	public String[] getReadOnlyPrefixes() {
		return _serviceBuilderArgs.getReadOnlyPrefixes();
	}

	public File getRemotingFile() {
		Project project = getProject();

		return project.file(_serviceBuilderArgs.getRemotingFileName());
	}

	public String[] getResourceActionsConfigs() {
		return _serviceBuilderArgs.getResourceActionsConfigs();
	}

	public File getResourcesDir() {
		Project project = getProject();

		return project.file(_serviceBuilderArgs.getResourcesDirName());
	}

	public File getSpringFile() {
		Project project = getProject();

		return project.file(_serviceBuilderArgs.getSpringFileName());
	}

	public String[] getSpringNamespaces() {
		return _serviceBuilderArgs.getSpringNamespaces();
	}

	public File getSqlDir() {
		Project project = getProject();

		return project.file(_serviceBuilderArgs.getSqlDirName());
	}

	public String getSqlFileName() {
		return _serviceBuilderArgs.getSqlFileName();
	}

	public String getSqlIndexesFileName() {
		return _serviceBuilderArgs.getSqlIndexesFileName();
	}

	public String getSqlSequencesFileName() {
		return _serviceBuilderArgs.getSqlSequencesFileName();
	}

	public String getTargetEntityName() {
		return _serviceBuilderArgs.getTargetEntityName();
	}

	public File getTestDir() {
		Project project = getProject();

		return project.file(_serviceBuilderArgs.getTestDirName());
	}

	@Override
	public File getWorkingDir() {
		Project project = getProject();

		return project.getProjectDir();
	}

	public boolean isAutoImportDefaultReferences() {
		return _serviceBuilderArgs.isAutoImportDefaultReferences();
	}

	public boolean isAutoNamespaceTables() {
		return _serviceBuilderArgs.isAutoNamespaceTables();
	}

	public boolean isBuildNumberIncrement() {
		return _serviceBuilderArgs.isBuildNumberIncrement();
	}

	public boolean isOsgiModule() {
		return _serviceBuilderArgs.isOsgiModule();
	}

	public void modelHintsConfigs(String ... modelHintsConfigs) {
		_serviceBuilderArgs.setMergeModelHintsConfigs(modelHintsConfigs);
	}

	public void readOnlyPrefixes(String ... readOnlyPrefixes) {
		_serviceBuilderArgs.setMergeReadOnlyPrefixes(readOnlyPrefixes);
	}

	public void resourceActionsConfigs(String ... resourceActionsConfigs) {
		_serviceBuilderArgs.setMergeResourceActionsConfigs(
			resourceActionsConfigs);
	}

	public void setApiDirName(String apiDirName) {
		_serviceBuilderArgs.setApiDirName(apiDirName);
	}

	@Override
	public JavaExec setArgs(Iterable<?> applicationArgs) {
		throw new UnsupportedOperationException();
	}

	public void setAutoImportDefaultReferences(
		boolean autoImportDefaultReferences) {

		_serviceBuilderArgs.setAutoImportDefaultReferences(
			autoImportDefaultReferences);
	}

	public void setAutoNamespaceTables(boolean autoNamespaceTables) {
		_serviceBuilderArgs.setAutoNamespaceTables(autoNamespaceTables);
	}

	public void setBeanLocatorUtil(String beanLocatorUtil) {
		_serviceBuilderArgs.setBeanLocatorUtil(beanLocatorUtil);
	}

	public void setBuildNumber(long buildNumber) {
		_serviceBuilderArgs.setBuildNumber(buildNumber);
	}

	public void setBuildNumberIncrement(boolean buildNumberIncrement) {
		_serviceBuilderArgs.setBuildNumberIncrement(buildNumberIncrement);
	}

	@Override
	public JavaExec setClasspath(FileCollection classpath) {
		throw new UnsupportedOperationException();
	}

	public void setHbmFileName(String hbmFileName) {
		_serviceBuilderArgs.setHbmFileName(hbmFileName);
	}

	public void setImplDirName(String implDirName) {
		_serviceBuilderArgs.setImplDirName(implDirName);
	}

	public void setInputFileName(String inputFileName) {
		_serviceBuilderArgs.setInputFileName(inputFileName);
	}

	public void setModelHintsConfigs(String[] modelHintsConfigs) {
		_serviceBuilderArgs.setModelHintsConfigs(modelHintsConfigs);
	}

	public void setModelHintsFileName(String modelHintsFileName) {
		_serviceBuilderArgs.setModelHintsFileName(modelHintsFileName);
	}

	public void setOsgiModule(boolean osgiModule) {
		_serviceBuilderArgs.setOsgiModule(osgiModule);
	}

	public void setPluginName(String pluginName) {
		_serviceBuilderArgs.setPluginName(pluginName);
	}

	public void setPropsUtil(String propsUtil) {
		_serviceBuilderArgs.setPropsUtil(propsUtil);
	}

	public void setReadOnlyPrefixes(String[] readOnlyPrefixes) {
		_serviceBuilderArgs.setReadOnlyPrefixes(readOnlyPrefixes);
	}

	public void setRemotingFileName(String remotingFileName) {
		_serviceBuilderArgs.setRemotingFileName(remotingFileName);
	}

	public void setResourceActionsConfigs(String[] resourceActionsConfigs) {
		_serviceBuilderArgs.setResourceActionsConfigs(resourceActionsConfigs);
	}

	public void setResourcesDirName(String resourcesDirName) {
		_serviceBuilderArgs.setResourcesDirName(resourcesDirName);
	}

	public void setSpringFileName(String springFileName) {
		_serviceBuilderArgs.setSpringFileName(springFileName);
	}

	public void setSpringNamespaces(String[] springNamespaces) {
		_serviceBuilderArgs.setSpringNamespaces(springNamespaces);
	}

	public void setSqlDirName(String sqlDirName) {
		_serviceBuilderArgs.setSqlDirName(sqlDirName);
	}

	public void setSqlFileName(String sqlFileName) {
		_serviceBuilderArgs.setSqlFileName(sqlFileName);
	}

	public void setSqlIndexesFileName(String sqlIndexesFileName) {
		_serviceBuilderArgs.setSqlIndexesFileName(sqlIndexesFileName);
	}

	public void setSqlSequencesFileName(String sqlSequencesFileName) {
		_serviceBuilderArgs.setSqlSequencesFileName(sqlSequencesFileName);
	}

	public void setTargetEntityName(String targetEntityName) {
		_serviceBuilderArgs.setTargetEntityName(targetEntityName);
	}

	public void setTestDirName(String testDirName) {
		_serviceBuilderArgs.setTestDirName(testDirName);
	}

	@Override
	public void setWorkingDir(Object dir) {
		throw new UnsupportedOperationException();
	}

	private final ServiceBuilderArgs _serviceBuilderArgs =
		new ServiceBuilderArgs();

}