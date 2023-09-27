/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import org.gradle.api.publish.plugins.PublishingPlugin;
import org.gradle.api.tasks.Exec;

/**
 * @author Andrea Di Giorgi
 * @author Eddie Olson
 */
public class WeDeployPlugin implements Plugin<Project> {

	public static final String DELETE_WEDEPLOY_TASK_NAME = "deleteWeDeploy";

	public static final String DEPLOY_WEDEPLOY_TASK_NAME = "deployWeDeploy";

	@Override
	public void apply(Project project) {
		String wedeployProject = _getProperty(project, ".wedeploy.project");
		String wedeployRemote = _getProperty(project, ".wedeploy.remote");

		_addTaskDeleteWeDeploy(project, wedeployProject, wedeployRemote);
		_addTaskDeployWeDeploy(project, wedeployProject, wedeployRemote);
	}

	@SuppressWarnings("unchecked")
	private Exec _addTaskDeleteWeDeploy(
		Project project, String wedeployProject, String wedeployRemote) {

		Exec exec = GradleUtil.addTask(
			project, DELETE_WEDEPLOY_TASK_NAME, Exec.class);

		exec.args("delete", "--force");

		if (Validator.isNotNull(wedeployProject)) {
			exec.args("--project", wedeployProject);
		}

		if (Validator.isNotNull(wedeployRemote)) {
			exec.args("--remote", wedeployRemote);
		}

		File wedeployJsonFile = project.file("wedeploy.json");

		JsonSlurper jsonSlurper = new JsonSlurper();

		Map<String, ?> wedeployJsonMap = (Map<String, ?>)jsonSlurper.parse(
			wedeployJsonFile);

		String wedeployId = (String)wedeployJsonMap.get("id");

		exec.args("--service", wedeployId);

		exec.setExecutable("we");

		exec.setDescription("Deletes the project from WeDeploy.");
		exec.setGroup(PublishingPlugin.PUBLISH_TASK_GROUP);

		return exec;
	}

	private Exec _addTaskDeployWeDeploy(
		Project project, String wedeployProject, String wedeployRemote) {

		Exec exec = GradleUtil.addTask(
			project, DEPLOY_WEDEPLOY_TASK_NAME, Exec.class);

		exec.args("deploy");

		if (Validator.isNotNull(wedeployProject)) {
			exec.args("--project", wedeployProject);
		}

		if (Validator.isNotNull(wedeployRemote)) {
			exec.args("--remote", wedeployRemote);
		}

		exec.setExecutable("we");

		exec.setDescription("Deploys the project to WeDeploy.");
		exec.setGroup(PublishingPlugin.PUBLISH_TASK_GROUP);

		return exec;
	}

	private String _getProperty(Project project, String suffix) {
		Properties properties = GradleUtil.getGradleProperties(
			project.getRootDir());

		if (properties != null) {
			for (Object key : properties.keySet()) {
				String s = (String)key;

				if (s.endsWith(suffix)) {
					return GradleUtil.toString(properties.get(key));
				}
			}
		}

		return null;
	}

}