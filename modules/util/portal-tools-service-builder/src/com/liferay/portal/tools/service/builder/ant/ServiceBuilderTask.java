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

package com.liferay.portal.tools.service.builder.ant;

import com.liferay.portal.tools.service.builder.ServiceBuilder;
import com.liferay.portal.tools.service.builder.ServiceBuilderBean;
import com.liferay.portal.tools.service.builder.ServiceBuilderInvoker;

import java.util.Set;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

/**
 * @author Raymond Augé
 */
public class ServiceBuilderTask extends Task {

	@Override
	public void execute() throws BuildException {
		try {
			Project project = getProject();

			ServiceBuilder serviceBuilder = ServiceBuilderInvoker.invoke(
				project.getBaseDir(), _serviceBuilderBean);

			Set<String> modifiedFileNames =
				serviceBuilder.getModifiedFileNames();

			project.addIdReference(
				ServiceBuilder.MODIFIED_FILES_ATTRIBUTE, modifiedFileNames);
		}
		catch (Exception e) {
			throw new BuildException(e);
		}
	}

	public void setApiDir(String apiDir) {
		_serviceBuilderBean.setApiDir(apiDir);
	}

	public void setAutoImportDefaultReferences(
		boolean autoImportDefaultReferences) {

		_serviceBuilderBean.setAutoImportDefaultReferences(
			autoImportDefaultReferences);
	}

	public void setAutoNamespaceTables(boolean autoNamespaceTables) {
		_serviceBuilderBean.setAutoNamespaceTables(autoNamespaceTables);
	}

	public void setBeanLocatorUtil(String beanLocatorUtil) {
		_serviceBuilderBean.setBeanLocatorUtil(beanLocatorUtil);
	}

	public void setBuildNumber(long buildNumber) {
		_serviceBuilderBean.setBuildNumber(buildNumber);
	}

	public void setBuildNumberIncrement(boolean buildNumberIncrement) {
		_serviceBuilderBean.setBuildNumberIncrement(buildNumberIncrement);
	}

	public void setHbmFileName(String hbmFileName) {
		_serviceBuilderBean.setHbmFileName(hbmFileName);
	}

	public void setImplDir(String implDir) {
		_serviceBuilderBean.setImplDir(implDir);
	}

	public void setInputFileName(String inputFileName) {
		_serviceBuilderBean.setInputFileName(inputFileName);
	}

	public void setMergeModelHintsConfigs(String mergeModelHintsConfigs) {
		_serviceBuilderBean.setMergeModelHintsConfigs(mergeModelHintsConfigs);
	}

	public void setMergeReadOnlyPrefixes(String mergeReadOnlyPrefixes) {
		_serviceBuilderBean.setMergeReadOnlyPrefixes(mergeReadOnlyPrefixes);
	}

	public void setMergeResourceActionsConfigs(
		String mergeResourceActionsConfigs) {

		_serviceBuilderBean.setMergeResourceActionsConfigs(
			mergeResourceActionsConfigs);
	}

	public void setModelHintsConfigs(String modelHintsConfigs) {
		_serviceBuilderBean.setModelHintsConfigs(modelHintsConfigs);
	}

	public void setModelHintsFileName(String modelHintsFileName) {
		_serviceBuilderBean.setModelHintsFileName(modelHintsFileName);
	}

	public void setOsgiModule(boolean osgiModule) {
		_serviceBuilderBean.setOsgiModule(osgiModule);
	}

	public void setPluginName(String pluginName) {
		_serviceBuilderBean.setPluginName(pluginName);
	}

	public void setPropsUtil(String propsUtil) {
		_serviceBuilderBean.setPropsUtil(propsUtil);
	}

	public void setReadOnlyPrefixes(String readOnlyPrefixes) {
		_serviceBuilderBean.setReadOnlyPrefixes(readOnlyPrefixes);
	}

	public void setRemotingFileName(String remotingFileName) {
		_serviceBuilderBean.setRemotingFileName(remotingFileName);
	}

	public void setResourceActionsConfigs(String resourceActionsConfigs) {
		_serviceBuilderBean.setResourceActionsConfigs(resourceActionsConfigs);
	}

	public void setResourcesDir(String resourcesDir) {
		_serviceBuilderBean.setResourcesDir(resourcesDir);
	}

	public void setSpringFileName(String springFileName) {
		_serviceBuilderBean.setSpringFileName(springFileName);
	}

	public void setSpringNamespaces(String springNamespaces) {
		_serviceBuilderBean.setSpringNamespaces(springNamespaces);
	}

	public void setSqlDir(String sqlDir) {
		_serviceBuilderBean.setSqlDir(sqlDir);
	}

	public void setSqlFileName(String sqlFileName) {
		_serviceBuilderBean.setSqlFileName(sqlFileName);
	}

	public void setSqlIndexesFileName(String sqlIndexesFileName) {
		_serviceBuilderBean.setSqlIndexesFileName(sqlIndexesFileName);
	}

	public void setSqlSequencesFileName(String sqlSequencesFileName) {
		_serviceBuilderBean.setSqlSequencesFileName(sqlSequencesFileName);
	}

	public void setTargetEntityName(String targetEntityName) {
		_serviceBuilderBean.setTargetEntityName(targetEntityName);
	}

	public void setTestDir(String testDir) {
		_serviceBuilderBean.setTestDir(testDir);
	}

	private final ServiceBuilderBean _serviceBuilderBean =
		new ServiceBuilderBean();

}