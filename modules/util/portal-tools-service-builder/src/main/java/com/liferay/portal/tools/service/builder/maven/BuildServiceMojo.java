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

package com.liferay.portal.tools.service.builder.maven;

import com.liferay.portal.tools.service.builder.ServiceBuilder;
import com.liferay.portal.tools.service.builder.ServiceBuilderArgs;
import com.liferay.portal.tools.service.builder.ServiceBuilderInvoker;

import java.io.File;

import java.util.Map;
import java.util.Set;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * Builds Liferay Service Builder services.
 *
 * @author Raymond Augé
 * @goal build-service
 */
public class BuildServiceMojo extends AbstractMojo {

	@Override
	public void execute() throws MojoExecutionException {
		try {
			@SuppressWarnings("rawtypes")
			Map pluginContext = getPluginContext();

			ServiceBuilder serviceBuilder = ServiceBuilderInvoker.invoke(
				baseDir, _serviceBuilderArgs);

			Set<String> modifiedFileNames =
				serviceBuilder.getModifiedFileNames();

			pluginContext.put(
				ServiceBuilderArgs.OUTPUT_KEY_MODIFIED_FILES,
				modifiedFileNames);
		}
		catch (Exception e) {
			throw new MojoExecutionException(e.getMessage(), e);
		}
	}

	/**
	 * @parameter
	 */
	public void setApiDirName(String apiDirName) {
		_serviceBuilderArgs.setApiDirName(apiDirName);
	}

	/**
	 * @parameter
	 */
	public void setAutoImportDefaultReferences(
		boolean autoImportDefaultReferences) {

		_serviceBuilderArgs.setAutoImportDefaultReferences(
			autoImportDefaultReferences);
	}

	/**
	 * @parameter
	 */
	public void setAutoNamespaceTables(boolean autoNamespaceTables) {
		_serviceBuilderArgs.setAutoNamespaceTables(autoNamespaceTables);
	}

	/**
	 * @parameter
	 */
	public void setBeanLocatorUtil(String beanLocatorUtil) {
		_serviceBuilderArgs.setBeanLocatorUtil(beanLocatorUtil);
	}

	/**
	 * @parameter
	 */
	public void setBuildNumber(long buildNumber) {
		_serviceBuilderArgs.setBuildNumber(buildNumber);
	}

	/**
	 * @parameter
	 */
	public void setBuildNumberIncrement(boolean buildNumberIncrement) {
		_serviceBuilderArgs.setBuildNumberIncrement(buildNumberIncrement);
	}

	/**
	 * @parameter
	 */
	public void setHbmFileName(String hbmFileName) {
		_serviceBuilderArgs.setHbmFileName(hbmFileName);
	}

	/**
	 * @parameter
	 */
	public void setImplDirName(String implDirName) {
		_serviceBuilderArgs.setImplDirName(implDirName);
	}

	/**
	 * @parameter
	 */
	public void setInputFileName(String inputFileName) {
		_serviceBuilderArgs.setInputFileName(inputFileName);
	}

	/**
	 * @parameter
	 */
	public void setMergeModelHintsConfigs(String mergeModelHintsConfigs) {
		_serviceBuilderArgs.setMergeModelHintsConfigs(mergeModelHintsConfigs);
	}

	/**
	 * @parameter
	 */
	public void setMergeReadOnlyPrefixes(String mergeReadOnlyPrefixes) {
		_serviceBuilderArgs.setMergeReadOnlyPrefixes(mergeReadOnlyPrefixes);
	}

	/**
	 * @parameter
	 */
	public void setMergeResourceActionsConfigs(
		String mergeResourceActionsConfigs) {

		_serviceBuilderArgs.setMergeResourceActionsConfigs(
			mergeResourceActionsConfigs);
	}

	/**
	 * @parameter
	 */
	public void setModelHintsConfigs(String modelHintsConfigs) {
		_serviceBuilderArgs.setModelHintsConfigs(modelHintsConfigs);
	}

	/**
	 * @parameter
	 */
	public void setModelHintsFileName(String modelHintsFileName) {
		_serviceBuilderArgs.setModelHintsFileName(modelHintsFileName);
	}

	/**
	 * @parameter
	 */
	public void setOsgiModule(boolean osgiModule) {
		_serviceBuilderArgs.setOsgiModule(osgiModule);
	}

	/**
	 * @parameter
	 */
	public void setPluginName(String pluginName) {
		_serviceBuilderArgs.setPluginName(pluginName);
	}

	/**
	 * @parameter
	 */
	public void setPropsUtil(String propsUtil) {
		_serviceBuilderArgs.setPropsUtil(propsUtil);
	}

	/**
	 * @parameter
	 */
	public void setReadOnlyPrefixes(String readOnlyPrefixes) {
		_serviceBuilderArgs.setReadOnlyPrefixes(readOnlyPrefixes);
	}

	/**
	 * @parameter
	 */
	public void setResourceActionsConfigs(String resourceActionsConfigs) {
		_serviceBuilderArgs.setResourceActionsConfigs(resourceActionsConfigs);
	}

	/**
	 * @parameter
	 */
	public void setResourcesDirName(String resourcesDirName) {
		_serviceBuilderArgs.setResourcesDirName(resourcesDirName);
	}

	/**
	 * @parameter
	 */
	public void setSpringFileName(String springFileName) {
		_serviceBuilderArgs.setSpringFileName(springFileName);
	}

	/**
	 * @parameter
	 */
	public void setSpringNamespaces(String springNamespaces) {
		_serviceBuilderArgs.setSpringNamespaces(springNamespaces);
	}

	/**
	 * @parameter
	 */
	public void setSqlDirName(String sqlDirName) {
		_serviceBuilderArgs.setSqlDirName(sqlDirName);
	}

	/**
	 * @parameter
	 */
	public void setSqlFileName(String sqlFileName) {
		_serviceBuilderArgs.setSqlFileName(sqlFileName);
	}

	/**
	 * @parameter
	 */
	public void setSqlIndexesFileName(String sqlIndexesFileName) {
		_serviceBuilderArgs.setSqlIndexesFileName(sqlIndexesFileName);
	}

	/**
	 * @parameter
	 */
	public void setSqlSequencesFileName(String sqlSequencesFileName) {
		_serviceBuilderArgs.setSqlSequencesFileName(sqlSequencesFileName);
	}

	/**
	 * @parameter
	 */
	public void setTargetEntityName(String targetEntityName) {
		_serviceBuilderArgs.setTargetEntityName(targetEntityName);
	}

	/**
	 * @parameter
	 */
	public void setTestDirName(String testDirName) {
		_serviceBuilderArgs.setTestDirName(testDirName);
	}

	/**
	 * @parameter default-value="${project.basedir}"
	 * @readonly
	 */
	protected File baseDir;

	private final ServiceBuilderArgs _serviceBuilderArgs =
		new ServiceBuilderArgs();

}