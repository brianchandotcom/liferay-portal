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
 * @author Eddie Olson
 */
public class WeDeployMessageQueuePlugin implements Plugin<Project> {

	public static final String DELETE_WEDEPLOY_MESSAGE_QUEUE_TASK_NAME =
		"deleteWeDeployMessageQueue";

	public static final String DEPLOY_WEDEPLOY_MESSAGE_QUEUE_TASK_NAME =
		"deployWeDeployMessageQueue";

	@Override
	public void apply(Project project) {
		String wedeployProject = _getProperty(project, ".wedeploy.project");
		String wedeployRemote = _getProperty(project, ".wedeploy.remote");

		File wedeployJsonFile = project.file("wedeploy.json");

		JsonSlurper jsonSlurper = new JsonSlurper();

		Map<String, ?> wedeployJsonMap = (Map<String, ?>)jsonSlurper.parse(
			wedeployJsonFile);

		String wedeployService = (String)wedeployJsonMap.get("id");

		_addTaskDeleteWeDeployMessageQueue(
			project, wedeployProject, wedeployRemote, wedeployService);

		_addTaskDeployWeDeployMessageQueue(project, wedeployProject, wedeployRemote);
	}

	private Exec _addTaskDeleteWeDeployMessageQueue(
		Project project, String wedeployProject, String wedeployRemote,
		Object wedeployService) {

		Exec exec = GradleUtil.addTask(
			project, DELETE_WEDEPLOY_MESSAGE_QUEUE_TASK_NAME, Exec.class);

		exec.args("delete", "--force");

		if (Validator.isNotNull(wedeployProject)) {
			exec.args("--project", wedeployProject);
		}

		if (Validator.isNotNull(wedeployRemote)) {
			exec.args("--remote", wedeployRemote);
		}

		exec.args("--service", wedeployService);

		exec.setExecutable("we");

		exec.setDescription("Deletes the message queue " + project + " from WeDeploy.");
		exec.setGroup(BasePlugin.UPLOAD_GROUP);

		return exec;
	}

	private Exec _addTaskDeployWeDeployMessageQueue(
		Project project, String wedeployProject, String wedeployRemote) {

		Exec exec = GradleUtil.addTask(
			project, DEPLOY_WEDEPLOY_MESSAGE_QUEUE_TASK_NAME, Exec.class);

		exec.args("deploy");

		if (Validator.isNotNull(wedeployProject)) {
			exec.args("--project", wedeployProject);
		}

		if (Validator.isNotNull(wedeployRemote)) {
			exec.args("--remote", wedeployRemote);
		}

		exec.setExecutable("we");

		exec.setDescription("Deploys the message queue " + project + " to WeDeploy.");
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