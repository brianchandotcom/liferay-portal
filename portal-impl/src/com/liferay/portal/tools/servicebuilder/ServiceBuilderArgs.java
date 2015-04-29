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

package com.liferay.portal.tools.servicebuilder;

/**
 * @author Raymond Augé
 */
public class ServiceBuilderArgs {

	public static final String[] MODEL_HINTS_CONFIGS = {
		"classpath*:META-INF/portal-model-hints.xml",
		"META-INF/portal-model-hints.xml",
		"classpath*:META-INF/ext-model-hints.xml",
		"META-INF/portlet-model-hints.xml"
	};

	public static final String OUTPUT_KEY_MODIFIED_FILES =
		"service.builder.modified.files";

	public static final String[] READ_ONLY_PREFIXES = {
		"fetch", "get", "has", "is", "load", "reindex", "search"
	};

	public static final String[] RESOURCE_ACTION_CONFIGS = {
		"META-INF/resource-actions/default.xml", "resource-actions/default.xml"
	};

	public String getApiDir() {
		return _apiDir;
	}

	public String getBeanLocatorUtil() {
		return _beanLocatorUtil;
	}

	public long getBuildNumber() {
		return _buildNumber;
	}

	public String getHbmFileName() {
		return _hbmFileName;
	}

	public String getImplDir() {
		return _implDir;
	}

	public String getInputFileName() {
		return _inputFileName;
	}

	public String[] getModelHintsConfigs() {
		return _modelHintsConfigs;
	}

	public String getModelHintsFileName() {
		return _modelHintsFileName;
	}

	public String getPluginName() {
		return _pluginName;
	}

	public String getPropsUtil() {
		return _propsUtil;
	}

	public String[] getReadOnlyPrefixes() {
		return _readOnlyPrefixes;
	}

	public String getRemotingFileName() {
		return _remotingFileName;
	}

	public String[] getResourceActionsConfigs() {
		return _resourceActionsConfigs;
	}

	public String getResourcesDir() {
		return _resourcesDir;
	}

	public String getSpringFileName() {
		return _springFileName;
	}

	public String[] getSpringNamespaces() {
		return _springNamespaces;
	}

	public String getSqlDir() {
		return _sqlDir;
	}

	public String getSqlFileName() {
		return _sqlFileName;
	}

	public String getSqlIndexesFileName() {
		return _sqlIndexesFileName;
	}

	public String getSqlSequencesFileName() {
		return _sqlSequencesFileName;
	}

	public String getTargetEntityName() {
		return _targetEntityName;
	}

	public String getTestDir() {
		return _testDir;
	}

	public boolean isAutoImportDefaultReferences() {
		return _autoImportDefaultReferences;
	}

	public boolean isAutoNamespaceTables() {
		return _autoNamespaceTables;
	}

	public boolean isBuildNumberIncrement() {
		return _buildNumberIncrement;
	}

	public boolean isOsgiModule() {
		return _osgiModule;
	}

	public void setApiDir(String apiDir) {
		_apiDir = apiDir;
	}

	public void setAutoImportDefaultReferences(
		boolean autoImportDefaultReferences) {

		_autoImportDefaultReferences = autoImportDefaultReferences;
	}

	public void setAutoNamespaceTables(boolean autoNamespaceTables) {
		_autoNamespaceTables = autoNamespaceTables;
	}

	public void setBeanLocatorUtil(String beanLocatorUtil) {
		_beanLocatorUtil = beanLocatorUtil;
	}

	public void setBuildNumber(long buildNumber) {
		_buildNumber = buildNumber;
	}

	public void setBuildNumberIncrement(boolean buildNumberIncrement) {
		_buildNumberIncrement = buildNumberIncrement;
	}

	public void setHbmFileName(String hbmFileName) {
		_hbmFileName = hbmFileName;
	}

	public void setImplDir(String implDir) {
		_implDir = implDir;
	}

	public void setInputFileName(String inputFileName) {
		_inputFileName = inputFileName;
	}

	public void setMergeModelHintsConfigs(String mergeModelHintsConfigs) {
		_setModelHintsConfigs(
			_append(_modelHintsConfigs, _split(mergeModelHintsConfigs)));
	}

	public void setMergeReadOnlyPrefixes(String mergeReadOnlyPrefixes) {
		_setReadOnlyPrefixes(
			_append(_readOnlyPrefixes, _split(mergeReadOnlyPrefixes)));
	}

	public void setMergeResourceActionsConfigs(
		String mergeResourceActionsConfigs) {

		_setResourceActionsConfigs(
			_append(
				_resourceActionsConfigs, _split(mergeResourceActionsConfigs)));
	}

	public void setModelHintsConfigs(String modelHintsConfigs) {
		_setModelHintsConfigs(_split(modelHintsConfigs));
	}

	public void setModelHintsFileName(String modelHintsFileName) {
		_modelHintsFileName = modelHintsFileName;
	}

	public void setOsgiModule(boolean osgiModule) {
		_osgiModule = osgiModule;
	}

	public void setPluginName(String pluginName) {
		_pluginName = pluginName;
	}

	public void setPropsUtil(String propsUtil) {
		_propsUtil = propsUtil;
	}

	public void setReadOnlyPrefixes(String readOnlyPrefixes) {
		_setReadOnlyPrefixes(_split(readOnlyPrefixes));
	}

	public void setRemotingFileName(String remotingFileName) {
		_remotingFileName = remotingFileName;
	}

	public void setResourceActionsConfigs(String resourceActionsConfigs) {
		_setResourceActionsConfigs(_split(resourceActionsConfigs));
	}

	public void setResourcesDir(String resourcesDir) {
		_resourcesDir = resourcesDir;
	}

	public void setSpringFileName(String springFileName) {
		_springFileName = springFileName;
	}

	public void setSpringNamespaces(String springNamespaces) {
		_springNamespaces = _split(springNamespaces);
	}

	public void setSqlDir(String sqlDir) {
		_sqlDir = sqlDir;
	}

	public void setSqlFileName(String sqlFileName) {
		_sqlFileName = sqlFileName;
	}

	public void setSqlIndexesFileName(String sqlIndexesFileName) {
		_sqlIndexesFileName = sqlIndexesFileName;
	}

	public void setSqlSequencesFileName(String sqlSequencesFileName) {
		_sqlSequencesFileName = sqlSequencesFileName;
	}

	public void setTargetEntityName(String targetEntityName) {
		_targetEntityName = targetEntityName;
	}

	public void setTestDir(String testDir) {
		_testDir = testDir;
	}

	private String[] _append(String[] array1, String[] array2) {
		String[] newArray = new String[array1.length + array2.length];

		System.arraycopy(array1, 0, newArray, 0, array1.length);

		System.arraycopy(array2, 0, newArray, array1.length, array2.length);

		return newArray;
	}

	private void _setModelHintsConfigs(String[] modelHintsConfigs) {
		if (_modelHintsConfigsSet) {
			throw new IllegalStateException(
				"Unable to call both setMergeModelHintsConfigs and " +
					"setModelHintsConfigs");
		}

		_modelHintsConfigsSet = true;

		_modelHintsConfigs = modelHintsConfigs;
	}

	private void _setReadOnlyPrefixes(String[] readOnlyPrefixes) {
		if (_readOnlyPrefixesSet) {
			throw new IllegalStateException(
				"Unable to call both setMergeReadOnlyPrefixes and " +
					"setReadOnlyPrefixes");
		}

		_readOnlyPrefixesSet = true;

		_readOnlyPrefixes = readOnlyPrefixes;
	}

	private void _setResourceActionsConfigs(String[] resourceActionsConfigs) {
		if (_resourceActionsConfigsSet) {
			throw new IllegalStateException(
				"Unable to call both setMergeResourceActionsConfigs and " +
					"setResourceActionsConfigs");
		}

		_resourceActionsConfigsSet = true;

		_resourceActionsConfigs = resourceActionsConfigs;
	}

	private String[] _split(String s) {
		return s.split(",");
	}

	private String _apiDir = "../portal-service/src";
	private boolean _autoImportDefaultReferences = true;
	private boolean _autoNamespaceTables;
	private String _beanLocatorUtil =
		"com.liferay.portal.kernel.bean.PortalBeanLocatorUtil";
	private long _buildNumber = 1;
	private boolean _buildNumberIncrement = true;
	private String _hbmFileName = "src/META-INF/portal-hbm.xml";
	private String _implDir = "src";
	private String _inputFileName = "service.xml";
	private String[] _modelHintsConfigs = MODEL_HINTS_CONFIGS;
	private boolean _modelHintsConfigsSet;
	private String _modelHintsFileName = "src/META-INF/portal-model-hints.xml";
	private boolean _osgiModule;
	private String _pluginName;
	private String _propsUtil = "com.liferay.portal.util.PropsUtil";
	private String[] _readOnlyPrefixes = READ_ONLY_PREFIXES;
	private boolean _readOnlyPrefixesSet;
	private String _remotingFileName =
		"../portal-web/docroot/WEB-INF/remoting-servlet.xml";
	private String[] _resourceActionsConfigs = RESOURCE_ACTION_CONFIGS;
	private boolean _resourceActionsConfigsSet;
	private String _resourcesDir = "src";
	private String _springFileName = "src/META-INF/portal-spring.xml";
	private String[] _springNamespaces = new String[] {"beans"};
	private String _sqlDir = "../sql";
	private String _sqlFileName = "portal-tables.sql";
	private String _sqlIndexesFileName = "indexes.sql";
	private String _sqlSequencesFileName = "sequences.sql";
	private String _targetEntityName;
	private String _testDir = "test/integration";

}