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

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.tasks.Exec;

/**
 * @author Andrea Di Giorgi
 */
public class WeDeployDataPlugin implements Plugin<Project> {

	public static final String DELETE_WE_DATA_TASK_NAME = "deleteWeData";

	public static final String DEPLOY_WE_DATA_TASK_NAME = "deployWeData";

	@Override
	public void apply(Project project) {
		String wedeployProject = _getProperty(project, ".wedeploy.project");
		String wedeployRemote = _getProperty(project, ".wedeploy.remote");

		File wedeployJsonFile = project.file("wedeploy.json");

		JsonSlurper jsonSlurper = new JsonSlurper();

		Map<String, ?> wedeployJsonMap = (Map<String, ?>)jsonSlurper.parse(
			wedeployJsonFile);

		String wedeployService = (String)wedeployJsonMap.get("id");

		_addTaskDeleteWeData(
			project, wedeployProject, wedeployRemote, wedeployService);

		_addTaskDeployWeData(project, wedeployProject, wedeployRemote);
	}

	private Exec _addTaskDeleteWeData(
		Project project, String wedeployProject, String wedeployRemote,
		Object wedeployService) {

		Exec exec = GradleUtil.addTask(
			project, DELETE_WE_DATA_TASK_NAME, Exec.class);

		exec.args("delete", "--force");

		if (Validator.isNotNull(wedeployProject)) {
			exec.args("--project", wedeployProject);
		}

		if (Validator.isNotNull(wedeployRemote)) {
			exec.args("--remote", wedeployRemote);
		}

		exec.args("--service", wedeployService);

		exec.setExecutable("we");

		exec.setDescription("Deletes the data " + project + " from WeDeploy.");
		exec.setGroup(BasePlugin.UPLOAD_GROUP);

		return exec;
	}

	private Exec _addTaskDeployWeData(
		Project project, String wedeployProject, String wedeployRemote) {

		Exec exec = GradleUtil.addTask(
			project, DEPLOY_WE_DATA_TASK_NAME, Exec.class);

		exec.args("deploy");

		if (Validator.isNotNull(wedeployProject)) {
			exec.args("--project", wedeployProject);
		}

		if (Validator.isNotNull(wedeployRemote)) {
			exec.args("--remote", wedeployRemote);
		}

		exec.setExecutable("we");

		exec.setDescription("Deploys the data " + project + " to WeDeploy.");
		exec.setGroup(BasePlugin.UPLOAD_GROUP);

		return exec;
	}

	private String _getProperty(Project project, String suffix) {
		Map<String, ?> properties = project.getProperties();

		for (Map.Entry<String, ?> entry : properties.entrySet()) {
			String key = entry.getKey();

			if (key.endsWith(suffix)) {
				return GradleUtil.toString(entry.getValue());
			}
		}

		return null;
	}

}