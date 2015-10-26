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
import com.liferay.gradle.util.Validator;
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

	public String getApiDirName() {
		return _serviceBuilderArgs.getApiDirName();
	}

	@Override
	public List<String> getArgs() {
		List<String> args = new ArrayList<>();

		args.add("service.api.dir=" + getApiDirName());
		args.add(
			"service.auto.import.default.references=" +
				isAutoImportDefaultReferences());
		args.add("service.auto.namespace.tables=" + isAutoNamespaceTables());
		args.add("service.bean.locator.util=" + getBeanLocatorUtil());
		args.add("service.build.number.increment=" + isBuildNumberIncrement());
		args.add("service.build.number=" + getBuildNumber());
		args.add("service.hbm.file=" + getHbmFileName());
		args.add("service.impl.dir=" + getImplDirName());
		args.add("service.input.file=" + getInputFileName());
		args.add(
			"service.model.hints.configs=" +
				StringUtil.merge(getModelHintsConfigs(), ","));
		args.add("service.model.hints.file=" + getModelHintsFileName());
		args.add("service.osgi.module=" + isOsgiModule());
		args.add("service.plugin.name=" + getPluginName());
		args.add("service.props.util=" + getPropsUtil());
		args.add(
			"service.read.only.prefixes=" +
				StringUtil.merge(getReadOnlyPrefixes(), ","));
		args.add(
			"service.resource.actions.configs=" +
				StringUtil.merge(getResourceActionsConfigs(), ","));
		args.add("service.resources.dir=" + getResourcesDirName());
		args.add("service.spring.file=" + getSpringFileName());
		args.add(
			"service.spring.namespaces=" +
				StringUtil.merge(getSpringNamespaces(), ","));
		args.add("service.sql.dir=" + getSqlDirName());
		args.add("service.sql.file=" + getSqlFileName());
		args.add("service.sql.indexes.file=" + getSqlIndexesFileName());
		args.add("service.sql.sequences.file=" + getSqlSequencesFileName());

		String targetEntityName = getTargetEntityName();

		if (Validator.isNull(targetEntityName)) {
			targetEntityName = "${service.target.entity.name}";
		}

		args.add("service.target.entity.name=" + targetEntityName);

		args.add("service.test.dir=" + getTestDirName());

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

	public String getHbmFileName() {
		return _serviceBuilderArgs.getHbmFileName();
	}

	public String getImplDirName() {
		return _serviceBuilderArgs.getImplDirName();
	}

	public String getInputFileName() {
		return _serviceBuilderArgs.getInputFileName();
	}

	@Override
	public String getMain() {
		return "com.liferay.portal.tools.service.builder.ServiceBuilder";
	}

	public String[] getModelHintsConfigs() {
		return _serviceBuilderArgs.getModelHintsConfigs();
	}

	public String getModelHintsFileName() {
		return _serviceBuilderArgs.getModelHintsFileName();
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

	public String[] getResourceActionsConfigs() {
		return _serviceBuilderArgs.getResourceActionsConfigs();
	}

	public String getResourcesDirName() {
		return _serviceBuilderArgs.getResourcesDirName();
	}

	public String getSpringFileName() {
		return _serviceBuilderArgs.getSpringFileName();
	}

	public String[] getSpringNamespaces() {
		return _serviceBuilderArgs.getSpringNamespaces();
	}

	public String getSqlDirName() {
		return _serviceBuilderArgs.getSqlDirName();
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

	public String getTestDirName() {
		return _serviceBuilderArgs.getTestDirName();
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