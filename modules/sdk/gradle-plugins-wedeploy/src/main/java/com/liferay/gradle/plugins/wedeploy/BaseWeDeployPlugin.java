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

package com.liferay.gradle.plugins.wedeploy;

import com.liferay.gradle.plugins.wedeploy.internal.util.GradleUtil;
import com.liferay.gradle.util.Validator;

import groovy.json.JsonSlurper;

import java.io.File;

import java.util.Map;
import java.util.Properties;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.tasks.Exec;
import org.gradle.util.GUtil;

/**
 * @author Andrea Di Giorgi
 */
public abstract class BaseWeDeployPlugin implements Plugin<Project> {

	@Override
	public void apply(Project project) {
		String wedeployProject = _getProperty(project, ".wedeploy.project");
		String wedeployRemote = _getProperty(project, ".wedeploy.remote");

		File wedeployJsonFile = project.file("wedeploy.json");

		JsonSlurper jsonSlurper = new JsonSlurper();

		Map<String, ?> wedeployJsonMap = (Map<String, ?>)jsonSlurper.parse(
			wedeployJsonFile);

		String wedeployService = (String)wedeployJsonMap.get("id");

		_addTaskDeleteWeDeploy(
			project, wedeployProject, wedeployRemote, wedeployService);

		_addTaskDeployWeDeploy(project, wedeployProject, wedeployRemote);
	}

	protected abstract String getDeleteWeDeployTaskDescription(Project project);

	protected abstract String getDeleteWeDeployTaskName();

	protected abstract String getDeployWeDeployTaskDescription(Project project);

	protected abstract String getDeployWeDeployTaskName();

	private Exec _addTaskDeleteWeDeploy(
		Project project, String wedeployProject, String wedeployRemote,
		Object wedeployService) {

		Exec exec = GradleUtil.addTask(
			project, getDeleteWeDeployTaskName(), Exec.class);

		exec.args("delete", "--force");

		if (Validator.isNotNull(wedeployProject)) {
			exec.args("--project", wedeployProject);
		}

		if (Validator.isNotNull(wedeployRemote)) {
			exec.args("--remote", wedeployRemote);
		}

		exec.args("--service", wedeployService);

		exec.setExecutable("we");

		exec.setDescription(getDeleteWeDeployTaskDescription(project));
		exec.setGroup(BasePlugin.UPLOAD_GROUP);

		return exec;
	}

	private Exec _addTaskDeployWeDeploy(
		Project project, String wedeployProject, String wedeployRemote) {

		Exec exec = GradleUtil.addTask(
			project, getDeployWeDeployTaskName(), Exec.class);

		exec.args("deploy");

		if (Validator.isNotNull(wedeployProject)) {
			exec.args("--project", wedeployProject);
		}

		if (Validator.isNotNull(wedeployRemote)) {
			exec.args("--remote", wedeployRemote);
		}

		exec.setExecutable("we");

		exec.setDescription(getDeployWeDeployTaskDescription(project));
		exec.setGroup(BasePlugin.UPLOAD_GROUP);

		return exec;
	}

	private String _getProperty(Project project, String suffix) {
		File rootDir = project.getRootDir();

		File propertiesFile = new File(rootDir, "gradle.properties");

		if (!propertiesFile.exists()) {
			return null;
		}

		Properties properties = GUtil.loadProperties(propertiesFile);

		propertiesFile = new File(rootDir, "gradle-ext.properties");

		if (propertiesFile.exists()) {
			Properties extProperties = GUtil.loadProperties(propertiesFile);

			properties.putAll(extProperties);
		}

		for (Object key : properties.keySet()) {
			String s = (String)key;

			if (s.endsWith(suffix)) {
				return GradleUtil.toString(properties.get(key));
			}
		}

		return null;
	}

}