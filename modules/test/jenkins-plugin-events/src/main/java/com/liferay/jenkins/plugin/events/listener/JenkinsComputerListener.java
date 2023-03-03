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

package com.liferay.jenkins.plugin.events.listener;

import com.liferay.jenkins.plugin.events.publisher.JenkinsPublisher;
import com.liferay.jenkins.plugin.events.publisher.JenkinsPublisherUtil;

import hudson.Extension;

import hudson.model.Computer;
import hudson.model.TaskListener;

import hudson.slaves.ComputerListener;
import hudson.slaves.OfflineCause;

import javax.annotation.Nonnull;

/**
 * @author Michael Hashimoto
 */
@Extension
public class JenkinsComputerListener extends ComputerListener {

	@Override
	public void onOffline(
		@Nonnull Computer computer, OfflineCause offlineCause) {

		JenkinsPublisherUtil.publish(
			JenkinsPublisher.EventTrigger.COMPUTER_OFFLINE, computer);
	}

	@Override
	public void onOnline(Computer computer, TaskListener taskListener) {
		JenkinsPublisherUtil.publish(
			JenkinsPublisher.EventTrigger.COMPUTER_ONLINE, computer);
	}

	@Override
	public void onTemporarilyOffline(
		Computer computer, OfflineCause offlineCause) {

		JenkinsPublisherUtil.publish(
			JenkinsPublisher.EventTrigger.COMPUTER_TEMPORARILY_OFFLINE,
			computer);
	}

	@Override
	public void onTemporarilyOnline(Computer computer) {
		JenkinsPublisherUtil.publish(
			JenkinsPublisher.EventTrigger.COMPUTER_TEMPORARILY_ONLINE,
			computer);
	}

}