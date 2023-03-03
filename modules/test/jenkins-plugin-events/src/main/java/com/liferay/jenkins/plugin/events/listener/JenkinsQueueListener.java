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

import hudson.model.Queue;
import hudson.model.queue.QueueListener;

/**
 * @author Michael Hashimoto
 */
@Extension
public class JenkinsQueueListener extends QueueListener {

	@Override
	public void onEnterBlocked(Queue.BlockedItem blockedItem) {
		JenkinsPublisherUtil.publish(
			JenkinsPublisher.EventTrigger.QUEUE_ITEM_ENTER_BLOCKED,
			blockedItem);
	}

	@Override
	public void onEnterBuildable(Queue.BuildableItem buildableItem) {
		JenkinsPublisherUtil.publish(
			JenkinsPublisher.EventTrigger.QUEUE_ITEM_ENTER_BUILDABLE,
			buildableItem);
	}

	@Override
	public void onEnterWaiting(Queue.WaitingItem waitingItem) {
		JenkinsPublisherUtil.publish(
			JenkinsPublisher.EventTrigger.QUEUE_ITEM_ENTER_WAITING,
			waitingItem);
	}

	@Override
	public void onLeaveBlocked(Queue.BlockedItem blockedItem) {
		JenkinsPublisherUtil.publish(
			JenkinsPublisher.EventTrigger.QUEUE_ITEM_LEAVE_BLOCKED,
			blockedItem);
	}

	@Override
	public void onLeaveBuildable(Queue.BuildableItem buildableItem) {
		JenkinsPublisherUtil.publish(
			JenkinsPublisher.EventTrigger.QUEUE_ITEM_LEAVE_BUILDABLE,
			buildableItem);
	}

	@Override
	public void onLeaveWaiting(Queue.WaitingItem waitingItem) {
		JenkinsPublisherUtil.publish(
			JenkinsPublisher.EventTrigger.QUEUE_ITEM_LEAVE_WAITING,
			waitingItem);
	}

	@Override
	public void onLeft(Queue.LeftItem leftItem) {
		JenkinsPublisherUtil.publish(
			JenkinsPublisher.EventTrigger.QUEUE_ITEM_LEFT, leftItem);
	}

}