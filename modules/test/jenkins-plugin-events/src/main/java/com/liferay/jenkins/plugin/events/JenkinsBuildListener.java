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

package com.liferay.jenkins.plugin.events;

import hudson.Extension;

import hudson.model.Build;
import hudson.model.Executor;
import hudson.model.TaskListener;
import hudson.model.listeners.RunListener;

/**
 * @author Michael Hashimoto
 */
@Extension
public class JenkinsBuildListener extends RunListener<Build> {

	@Override
	public void onCompleted(Build build, TaskListener taskListener) {
		JenkinsEventsConfigurationUtil.publish(
			JenkinsWebHook.EventTrigger.BUILD_COMPLETED, build);

		Executor executor = build.getExecutor();

		JenkinsEventsConfigurationUtil.publish(
			JenkinsWebHook.EventTrigger.COMPUTER_IDLE, executor.getOwner());
	}

	@Override
	public void onStarted(Build build, TaskListener listener) {
		Executor executor = build.getExecutor();

		JenkinsEventsConfigurationUtil.publish(
			JenkinsWebHook.EventTrigger.COMPUTER_BUSY, executor.getOwner());

		JenkinsEventsConfigurationUtil.publish(
			JenkinsWebHook.EventTrigger.BUILD_STARTED, build);
	}

}