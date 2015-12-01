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
import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.Validator;
import com.liferay.portal.tools.service.builder.ServiceBuilderArgs;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.gradle.api.Project;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.JavaExec;
import org.gradle.api.tasks.Optional;
import org.gradle.util.CollectionUtils;
import org.gradle.util.GUtil;

/**
 * @author Andrea Di Giorgi
 */
public class BuildServiceTask extends JavaExec {

	public BuildServiceTask() {
		modelHintsConfigs((Object[])ServiceBuilderArgs.MODEL_HINTS_CONFIGS);
		readOnlyPrefixes((Object[])ServiceBuilderArgs.READ_ONLY_PREFIXES);
		resourceActionsConfigs(
			(Object[])ServiceBuilderArgs.RESOURCE_ACTION_CONFIGS);
		setMain("com.liferay.portal.tools.service.builder.ServiceBuilder");
		springNamespaces("beans");
	}

	@Override
	public void exec() {
		setArgs(getCompleteArgs());

		super.exec();
	}

	@Input
	public File getApiDir() {
		return GradleUtil.toFile(getProject(), _apiDir);
	}

	@Input
	public String getBeanLocatorUtil() {
		return GradleUtil.toString(_beanLocatorUtil);
	}

	@Input
	public long getBuildNumber() {
		return _buildNumber;
	}

	@Input
	public File getHbmFile() {
		return GradleUtil.toFile(getProject(), _hbmFile);
	}

	@Input
	public File getImplDir() {
		return GradleUtil.toFile(getProject(), _implDir);
	}

	@InputFile
	public File getInputFile() {
		return GradleUtil.toFile(getProject(), _inputFile);
	}

	@Input
	public List<String> getModelHintsConfigs() {
		return GradleUtil.toStringList(_modelHintsConfigs);
	}

	@Input
	public File getModelHintsFile() {
		return GradleUtil.toFile(getProject(), _modelHintsFile);
	}

	@Input
	public String getPluginName() {
		return GradleUtil.toString(_pluginName);
	}

	@Input
	public String getPropsUtil() {
		return GradleUtil.toString(_propsUtil);
	}

	@Input
	public List<String> getReadOnlyPrefixes() {
		return GradleUtil.toStringList(_readOnlyPrefixes);
	}

	@Input
	public List<String> getResourceActionsConfigs() {
		return GradleUtil.toStringList(_resourceActionsConfigs);
	}

	@Input
	public File getResourcesDir() {
		return GradleUtil.toFile(getProject(), _resourcesDir);
	}

	@Input
	public File getSpringFile() {
		return GradleUtil.toFile(getProject(), _springFile);
	}

	@Input
	public List<String> getSpringNamespaces() {
		return GradleUtil.toStringList(_springNamespaces);
	}

	@Input
	public File getSqlDir() {
		return GradleUtil.toFile(getProject(), _sqlDir);
	}

	@Input
	public String getSqlFileName() {
		return GradleUtil.toString(_sqlFileName);
	}

	@Input
	public String getSqlIndexesFileName() {
		return GradleUtil.toString(_sqlIndexesFileName);
	}

	@Input
	public String getSqlSequencesFileName() {
		return GradleUtil.toString(_sqlSequencesFileName);
	}

	@Input
	@Optional
	public String getTargetEntityName() {
		return GradleUtil.toString(_targetEntityName);
	}

	@Input
	@Optional
	public File getTestDir() {
		return GradleUtil.toFile(getProject(), _testDir);
	}

	@Input
	public boolean isAutoImportDefaultReferences() {
		return _autoImportDefaultReferences;
	}

	@Input
	public boolean isAutoNamespaceTables() {
		return _autoNamespaceTables;
	}

	@Input
	public boolean isBuildNumberIncrement() {
		return _buildNumberIncrement;
	}

	@Input
	public boolean isOsgiModule() {
		return _osgiModule;
	}

	public BuildServiceTask modelHintsConfigs(
		Iterable<Object> modelHintsConfigs) {

		GUtil.addToCollection(_modelHintsConfigs, modelHintsConfigs);

		return this;
	}

	public BuildServiceTask modelHintsConfigs(Object ... modelHintsConfigs) {
		return modelHintsConfigs(Arrays.asList(modelHintsConfigs));
	}

	public BuildServiceTask readOnlyPrefixes(
		Iterable<Object> readOnlyPrefixes) {

		GUtil.addToCollection(_readOnlyPrefixes, readOnlyPrefixes);

		return this;
	}

	public BuildServiceTask readOnlyPrefixes(Object ... readOnlyPrefixes) {
		return readOnlyPrefixes(Arrays.asList(readOnlyPrefixes));
	}

	public BuildServiceTask resourceActionsConfigs(
		Iterable<Object> resourceActionsConfigs) {

		GUtil.addToCollection(_resourceActionsConfigs, resourceActionsConfigs);

		return this;
	}

	public BuildServiceTask resourceActionsConfigs(
		Object ... resourceActionsConfigs) {

		return resourceActionsConfigs(Arrays.asList(resourceActionsConfigs));
	}

	public void setApiDir(Object apiDir) {
		_apiDir = apiDir;
	}

	public void setAutoImportDefaultReferences(
		boolean autoImportDefaultReferences) {

		_autoImportDefaultReferences = autoImportDefaultReferences;
	}

	public void setAutoNamespaceTables(boolean autoNamespaceTables) {
		_autoNamespaceTables = autoNamespaceTables;
	}

	public void setBeanLocatorUtil(Object beanLocatorUtil) {
		_beanLocatorUtil = beanLocatorUtil;
	}

	public void setBuildNumber(long buildNumber) {
		_buildNumber = buildNumber;
	}

	public void setBuildNumberIncrement(boolean buildNumberIncrement) {
		_buildNumberIncrement = buildNumberIncrement;
	}

	public void setHbmFile(Object hbmFile) {
		_hbmFile = hbmFile;
	}

	public void setImplDir(Object implDir) {
		_implDir = implDir;
	}

	public void setInputFile(Object inputFile) {
		_inputFile = inputFile;
	}

	public void setModelHintsConfigs(Iterable<Object> modelHintsConfigs) {
		_modelHintsConfigs.clear();

		modelHintsConfigs(modelHintsConfigs);
	}

	public void setModelHintsConfigs(Object ... modelHintsConfigs) {
		setModelHintsConfigs(Arrays.asList(modelHintsConfigs));
	}

	public void setModelHintsFile(Object modelHintsFile) {
		_modelHintsFile = modelHintsFile;
	}

	public void setOsgiModule(boolean osgiModule) {
		_osgiModule = osgiModule;
	}

	public void setPluginName(Object pluginName) {
		_pluginName = pluginName;
	}

	public void setPropsUtil(Object propsUtil) {
		_propsUtil = propsUtil;
	}

	public void setReadOnlyPrefixes(Iterable<Object> readOnlyPrefixes) {
		_readOnlyPrefixes.clear();

		readOnlyPrefixes(readOnlyPrefixes);
	}

	public void setReadOnlyPrefixes(Object ... readOnlyPrefixes) {
		setReadOnlyPrefixes(Arrays.asList(readOnlyPrefixes));
	}

	public void setResourceActionsConfigs(
		Iterable<Object> resourceActionsConfigs) {

		_resourceActionsConfigs.clear();

		resourceActionsConfigs(resourceActionsConfigs);
	}

	public void setResourceActionsConfigs(Object ... resourceActionsConfigs) {
		setResourceActionsConfigs(Arrays.asList(resourceActionsConfigs));
	}

	public void setResourcesDir(Object resourcesDir) {
		_resourcesDir = resourcesDir;
	}

	public void setSpringFile(Object springFile) {
		_springFile = springFile;
	}

	public void setSpringNamespaces(Iterable<Object> springNamespaces) {
		_springNamespaces.clear();

		springNamespaces(springNamespaces);
	}

	public void setSpringNamespaces(Object ... springNamespaces) {
		setSpringNamespaces(Arrays.asList(springNamespaces));
	}

	public void setSqlDir(Object sqlDir) {
		_sqlDir = sqlDir;
	}

	public void setSqlFileName(Object sqlFileName) {
		_sqlFileName = sqlFileName;
	}

	public void setSqlIndexesFileName(Object sqlIndexesFileName) {
		_sqlIndexesFileName = sqlIndexesFileName;
	}

	public void setSqlSequencesFileName(Object sqlSequencesFileName) {
		_sqlSequencesFileName = sqlSequencesFileName;
	}

	public void setTargetEntityName(Object targetEntityName) {
		_targetEntityName = targetEntityName;
	}

	public void setTestDir(Object testDir) {
		_testDir = testDir;
	}

	public BuildServiceTask springNamespaces(
		Iterable<Object> springNamespaces) {

		GUtil.addToCollection(_springNamespaces, springNamespaces);

		return this;
	}

	public BuildServiceTask springNamespaces(Object ... springNamespaces) {
		return springNamespaces(Arrays.asList(springNamespaces));
	}

	protected List<String> getCompleteArgs() {
		List<String> args = new ArrayList<>(getArgs());

		args.add("service.api.dir=" + _relativize(getApiDir()));
		args.add(
			"service.auto.import.default.references=" +
				isAutoImportDefaultReferences());
		args.add("service.auto.namespace.tables=" + isAutoNamespaceTables());
		args.add("service.bean.locator.util=" + getBeanLocatorUtil());
		args.add("service.build.number.increment=" + isBuildNumberIncrement());
		args.add("service.build.number=" + getBuildNumber());
		args.add("service.hbm.file=" + _relativize(getHbmFile()));
		args.add("service.impl.dir=" + _relativize(getImplDir()));
		args.add("service.input.file=" + _relativize(getInputFile()));
		args.add(
			"service.model.hints.configs=" +
				CollectionUtils.join(",", getCompleteModelHintsConfigs()));
		args.add(
			"service.model.hints.file=" + _relativize(getModelHintsFile()));
		args.add("service.osgi.module=" + isOsgiModule());
		args.add("service.plugin.name=" + getPluginName());
		args.add("service.props.util=" + getPropsUtil());
		args.add(
			"service.read.only.prefixes=" +
				CollectionUtils.join(",", getReadOnlyPrefixes()));
		args.add(
			"service.resource.actions.configs=" +
				CollectionUtils.join(",", getResourceActionsConfigs()));
		args.add("service.resources.dir=" + _relativize(getResourcesDir()));
		args.add("service.spring.file=" + _relativize(getSpringFile()));
		args.add(
			"service.spring.namespaces=" +
				CollectionUtils.join(",", getSpringNamespaces()));
		args.add("service.sql.dir=" + _relativize(getSqlDir()));
		args.add("service.sql.file=" + getSqlFileName());
		args.add("service.sql.indexes.file=" + getSqlIndexesFileName());
		args.add("service.sql.sequences.file=" + getSqlSequencesFileName());

		String targetEntityName = getTargetEntityName();

		if (Validator.isNull(targetEntityName)) {
			targetEntityName = "${service.target.entity.name}";
		}

		args.add("service.target.entity.name=" + targetEntityName);

		File testDir = getTestDir();

		if (testDir != null) {
			args.add("service.test.dir=" + _relativize(testDir));
		}

		return args;
	}

	protected List<String> getCompleteModelHintsConfigs() {
		List<String> modelHintsConfigs = getModelHintsConfigs();

		File modelHintsFile = getModelHintsFile();
		Project project = getProject();

		boolean found = false;

		for (String config : modelHintsConfigs) {
			if (config.startsWith("classpath*:")) {
				continue;
			}

			File configFile = project.file(config);

			if (configFile.equals(modelHintsFile)) {
				found = true;

				break;
			}
		}

		if (!found) {
			modelHintsConfigs.add(_relativize(modelHintsFile));
		}

		return modelHintsConfigs;
	}

	private String _relativize(File file) {
		String relativePath = FileUtil.relativize(file, getWorkingDir());

		return relativePath.replace('\\', '/');
	}

	private Object _apiDir;
	private boolean _autoImportDefaultReferences = true;
	private boolean _autoNamespaceTables = true;
	private Object _beanLocatorUtil =
		"com.liferay.util.bean.PortletBeanLocatorUtil";
	private long _buildNumber = 1;
	private boolean _buildNumberIncrement = true;
	private Object _hbmFile;
	private Object _implDir;
	private Object _inputFile;
	private final Set<Object> _modelHintsConfigs = new LinkedHashSet<>();
	private Object _modelHintsFile;
	private boolean _osgiModule;
	private Object _pluginName;
	private Object _propsUtil;
	private final Set<Object> _readOnlyPrefixes = new HashSet<>();
	private final Set<Object> _resourceActionsConfigs = new LinkedHashSet<>();
	private Object _resourcesDir;
	private Object _springFile;
	private final Set<Object> _springNamespaces = new LinkedHashSet<>();
	private Object _sqlDir;
	private Object _sqlFileName = "tables.sql";
	private Object _sqlIndexesFileName = "indexes.sql";
	private Object _sqlSequencesFileName = "sequences.sql";
	private Object _targetEntityName;
	private Object _testDir;

}