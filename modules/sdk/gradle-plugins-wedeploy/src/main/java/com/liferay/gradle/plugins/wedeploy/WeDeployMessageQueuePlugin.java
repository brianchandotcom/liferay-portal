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

import org.gradle.api.Project;

/**
 * @author Eddie Olson
 */
public class WeDeployMessageQueuePlugin extends BaseWeDeployPlugin {

	public static final String DELETE_WEDEPLOY_MESSAGE_QUEUE_TASK_NAME =
		"deleteWeDeployMessageQueue";

	public static final String DEPLOY_WEDEPLOY_MESSAGE_QUEUE_TASK_NAME =
		"deployWeDeployMessageQueue";

	protected String getDeleteWeDeployTaskDescription(Project project) {
		return "Deletes the message queue " + project + " from WeDeploy.";
	}

	protected String getDeleteWeDeployTaskName() {
		return DELETE_WEDEPLOY_MESSAGE_QUEUE_TASK_NAME;
	}

	protected String getDeployWeDeployTaskDescription(Project project) {
		return "Deploys the message queue " + project + " to WeDeploy.";
	}

	protected String getDeployWeDeployTaskName() {
		return DEPLOY_WEDEPLOY_MESSAGE_QUEUE_TASK_NAME;
	}

}