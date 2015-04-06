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

package com.liferay.portal.tools.servicebuilder.ant;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ModelHintsUtil;
import com.liferay.portal.tools.servicebuilder.ModelHintsImpl;
import com.liferay.portal.tools.servicebuilder.ServiceBuilder;

import java.util.Set;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

/**
 * @author Raymond Augé
 */
public class ServiceBuilderTask extends Task {

	@Override
	public void execute() throws BuildException {
		try {
			Set<String> permissionModels =
				ServiceBuilder.collectPermissionModels(
					_implDir, _resourceActionsConfigs);

			ModelHintsImpl modelHintsImpl = new ModelHintsImpl();

			modelHintsImpl.setModelHintsConfigs(_modelHintsConfigs);
			modelHintsImpl.afterPropertiesSet();

			ModelHintsUtil modelHintsUtil = new ModelHintsUtil();

			modelHintsUtil.setModelHints(modelHintsImpl);

			new ServiceBuilder(
				_apiDir, _autoImportDefaultReferences, _autoNamespaceTables,
				_beanLocatorUtil, _buildNumber, _buildNumberIncrement,
				_hbmFileName, _implDir, _inputFileName, _modelHintsFileName,
				_osgiModule, permissionModels, _pluginName, _propsUtil,
				_readOnlyPrefixes, _remotingFileName, _resourcesDir,
				_springFileName, _springNamespaces, _sqlDir, _sqlFileName,
				_sqlIndexesFileName, _sqlSequencesFileName, _targetEntityName,
				_testDir, true);
		}
		catch (Exception e) {
			throw new BuildException(e);
		}
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

	public void setMergeModelHintsConfigs(String modelHintsConfigs) {
		_setModelHintsConfigs(
			ArrayUtil.append(
				_modelHintsConfigs, StringUtil.split(modelHintsConfigs)));
	}

	public void setMergeReadOnlyPrefixes(String readOnlyPrefixes) {
		_setReadOnlyPrefixes(
			ArrayUtil.append(
				_readOnlyPrefixes, StringUtil.split(readOnlyPrefixes)));
	}

	public void setMergeResourceActionsConfigs(String resourceActionsConfigs) {
		_setResourceActionsConfigs(
			ArrayUtil.append(
				_resourceActionsConfigs,
				StringUtil.split(resourceActionsConfigs)));
	}

	public void setModelHintsConfigs(String modelHintsConfigs) {
		_setModelHintsConfigs(StringUtil.split(modelHintsConfigs));
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
		_setReadOnlyPrefixes(StringUtil.split(readOnlyPrefixes));
	}

	public void setRemotingFileName(String remotingFileName) {
		_remotingFileName = remotingFileName;
	}

	public void setResourceActionsConfigs(String resourceActionsConfigs) {
		_setResourceActionsConfigs(StringUtil.split(resourceActionsConfigs));
	}

	public void setResourcesDir(String resourcesDir) {
		_resourcesDir = resourcesDir;
	}

	public void setSpringFileName(String springFileName) {
		_springFileName = springFileName;
	}

	public void setSpringNamespaces(String springNamespaces) {
		_springNamespaces = StringUtil.split(springNamespaces);
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

	private void _setModelHintsConfigs(String[] modelHintsConfigs) {
		if (_modelHintsConfigsSet) {
			throw new BuildException(
				"Only one of modelHintsConfigs and mergeModelHintsConfigs " +
					"can be used.");
		}

		_modelHintsConfigsSet = true;
		_modelHintsConfigs = modelHintsConfigs;
	}

	private void _setReadOnlyPrefixes(String[] readOnlyPrefixes) {
		if (_READ_ONLY_PREFIXES_SET) {
			throw new BuildException(
				"Only one of readOnlyPrefixes and mergeReadOnlyPrefixes " +
					"can be used.");
		}

		_readOnlyPrefixes = readOnlyPrefixes;
	}

	private void _setResourceActionsConfigs(String[] resourceActionsConfigs) {
		if (_RESOURCE_ACTIONS_CONFIGS_SET) {
			throw new BuildException(
				"Only one of resourceActionsConfigs and " +
					"mergeResourceActionsConfigs can be used.");
		}

		_resourceActionsConfigs = resourceActionsConfigs;
	}

	private static final boolean _READ_ONLY_PREFIXES_SET = false;

	private static final boolean _RESOURCE_ACTIONS_CONFIGS_SET = false;

	private String _apiDir = "../portal-service/src";
	private boolean _autoImportDefaultReferences = true;
	private boolean _autoNamespaceTables = false;
	private String _beanLocatorUtil =
		"com.liferay.portal.kernel.bean.PortalBeanLocatorUtil";
	private long _buildNumber = 1;
	private boolean _buildNumberIncrement = true;
	private String _hbmFileName = "src/META-INF/portal-hbm.xml";
	private String _implDir = "src";
	private String _inputFileName = "service.xml";
	private String[] _modelHintsConfigs = StringUtil.split(
		ServiceBuilder.MODEL_HINTS_CONFIGS);
	private boolean _modelHintsConfigsSet = false;
	private String _modelHintsFileName = "src/META-INF/portal-model-hints.xml";
	private boolean _osgiModule = false;
	private String _pluginName;
	private String _propsUtil = "com.liferay.portal.util.PropsUtil";
	private String[] _readOnlyPrefixes = StringUtil.split(
		ServiceBuilder.READ_ONLY_PREFIXES);
	private String _remotingFileName =
		"../portal-web/docroot/WEB-INF/remoting-servlet.xml";
	private String[] _resourceActionsConfigs = StringUtil.split(
		ServiceBuilder.RESOURCE_ACTION_CONFIGS);
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