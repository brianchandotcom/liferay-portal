/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.ant;

import com.liferay.portal.tools.service.builder.ServiceBuilder;
import com.liferay.portal.tools.service.builder.ServiceBuilderArgs;
import com.liferay.portal.tools.service.builder.ServiceBuilderInvoker;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

/**
 * @author Raymond Augé
 */
public class BuildServiceTask extends Task {

	@Override
	public void execute() throws BuildException {
		try {
			Project project = getProject();

			ServiceBuilder serviceBuilder = ServiceBuilderInvoker.invoke(
				project.getBaseDir(), _serviceBuilderArgs);

			project.addIdReference(
				ServiceBuilderArgs.OUTPUT_KEY_MODIFIED_FILES,
				serviceBuilder.getModifiedFileNames());
		}
		catch (Exception exception) {
			throw new BuildException(exception);
		}
	}

	public void setApiDirName(String apiDirName) {
		_serviceBuilderArgs.setApiDirName(apiDirName);
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

	public void setDatabaseNameMaxLength(int databaseNameMaxLength) {
		_serviceBuilderArgs.setDatabaseNameMaxLength(databaseNameMaxLength);
	}

	public void setHbmFileName(String hbmFileName) {
		_serviceBuilderArgs.setHbmFileName(hbmFileName);
	}

	public void setImplDirName(String implDirName) {
		_serviceBuilderArgs.setImplDirName(implDirName);
	}

	public void setIncubationFeatures(String incubationFeatures) {
		_serviceBuilderArgs.setIncubationFeatures(incubationFeatures);
	}

	public void setInputFileName(String inputFileName) {
		_serviceBuilderArgs.setInputFileName(inputFileName);
	}

	public void setMergeModelHintsConfigs(String mergeModelHintsConfigs) {
		_serviceBuilderArgs.setMergeModelHintsConfigs(mergeModelHintsConfigs);
	}

	public void setMergeReadOnlyPrefixes(String mergeReadOnlyPrefixes) {
		_serviceBuilderArgs.setMergeReadOnlyPrefixes(mergeReadOnlyPrefixes);
	}

	public void setMergeResourceActionsConfigs(
		String mergeResourceActionsConfigs) {

		_serviceBuilderArgs.setMergeResourceActionsConfigs(
			mergeResourceActionsConfigs);
	}

	public void setModelHintsConfigs(String modelHintsConfigs) {
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

	public void setReadOnlyPrefixes(String readOnlyPrefixes) {
		_serviceBuilderArgs.setReadOnlyPrefixes(readOnlyPrefixes);
	}

	public void setResourceActionsConfigs(String resourceActionsConfigs) {
		_serviceBuilderArgs.setResourceActionsConfigs(resourceActionsConfigs);
	}

	public void setResourcesDirName(String resourcesDirName) {
		_serviceBuilderArgs.setResourcesDirName(resourcesDirName);
	}

	public void setSpringFileName(String springFileName) {
		_serviceBuilderArgs.setSpringFileName(springFileName);
	}

	public void setSpringNamespaces(String springNamespaces) {
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

	private final ServiceBuilderArgs _serviceBuilderArgs =
		new ServiceBuilderArgs();

}