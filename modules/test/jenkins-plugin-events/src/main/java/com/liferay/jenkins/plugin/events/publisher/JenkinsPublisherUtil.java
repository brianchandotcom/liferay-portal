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

package com.liferay.jenkins.plugin.events.publisher;

import com.liferay.jenkins.plugin.events.JenkinsEventsRootAction;

import hudson.model.Build;
import hudson.model.Computer;
import hudson.model.Executor;
import hudson.model.Job;
import hudson.model.ParameterValue;
import hudson.model.ParametersAction;
import hudson.model.Queue;

import java.util.HashMap;
import java.util.Map;

import jenkins.model.Jenkins;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class JenkinsPublisherUtil {

	public static void publish(
		JenkinsPublisher.EventTrigger eventTrigger, Object eventObject) {

		Jenkins jenkins = Jenkins.getInstanceOrNull();

		if (jenkins == null) {
			return;
		}

		JSONObject payloadJSONObject = new JSONObject();

		payloadJSONObject.put("build", _getBuildJSONObject(eventObject));
		payloadJSONObject.put(
			"computer", _getComputerJSONObject(eventObject, eventTrigger));
		payloadJSONObject.put("eventTrigger", eventTrigger);
		payloadJSONObject.put("jenkins", _getJenkinsJSONObject(jenkins));
		payloadJSONObject.put("job", _getJobJSONObject(eventObject));
		payloadJSONObject.put(
			"queueItem", _getQueueItemJSONObject(eventObject));

		JenkinsEventsRootAction jenkinsEventsRootAction =
			jenkins.getDescriptorByType(JenkinsEventsRootAction.class);

		for (JenkinsPublisher jenkinsPublisher :
				jenkinsEventsRootAction.getJenkinsPublishers()) {

			if (!jenkinsPublisher.containsEventTrigger(eventTrigger)) {
				continue;
			}

			jenkinsPublisher.publish(
				payloadJSONObject.toString(), eventTrigger);
		}
	}

	private static Build _getBuild(Object eventObject) {
		if (eventObject instanceof Build) {
			return (Build)eventObject;
		}

		return null;
	}

	private static JSONObject _getBuildJSONObject(Object eventObject) {
		Build build = _getBuild(eventObject);

		if (build == null) {
			return null;
		}

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("duration", build.getDuration());
		jsonObject.put("number", build.getNumber());
		jsonObject.put("result", build.getResult());

		return jsonObject;
	}

	private static Computer _getComputer(Object eventObject) {
		if (eventObject instanceof Computer) {
			return (Computer)eventObject;
		}

		Build build = _getBuild(eventObject);

		if (build != null) {
			Executor executor = build.getExecutor();

			return executor.getOwner();
		}

		return null;
	}

	private static JSONObject _getComputerJSONObject(
		Object eventObject, JenkinsPublisher.EventTrigger eventTrigger) {

		Computer computer = _getComputer(eventObject);

		if (computer == null) {
			return null;
		}

		JSONObject jsonObject = new JSONObject();

		if (eventTrigger == JenkinsPublisher.EventTrigger.COMPUTER_IDLE) {
			jsonObject.put("busy", false);
		}
		else if (eventTrigger == JenkinsPublisher.EventTrigger.COMPUTER_BUSY) {
			jsonObject.put("busy", true);
		}
		else {
			jsonObject.put("busy", !computer.isIdle());
		}

		jsonObject.put("name", computer.getDisplayName());
		jsonObject.put("online", computer.isOnline());

		return jsonObject;
	}

	private static JSONObject _getJenkinsJSONObject(Jenkins jenkins) {
		if (jenkins == null) {
			return null;
		}

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("url", jenkins.getRootUrl());

		return jsonObject;
	}

	private static Job _getJob(Object eventObject) {
		if (eventObject instanceof Job) {
			return (Job)eventObject;
		}

		Build build = _getBuild(eventObject);

		if (build != null) {
			return build.getParent();
		}

		return null;
	}

	private static JSONObject _getJobJSONObject(Object eventObject) {
		Job job = _getJob(eventObject);

		if (job == null) {
			return null;
		}

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("name", job.getName());

		return jsonObject;
	}

	private static Queue.Item _getQueueItem(Object eventObject) {
		if (eventObject instanceof Queue.Item) {
			return (Queue.Item)eventObject;
		}

		return null;
	}

	private static JSONObject _getQueueItemJSONObject(Object eventObject) {
		Queue.Item queueItem = _getQueueItem(eventObject);

		if (queueItem == null) {
			return null;
		}

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("id", queueItem.getId());

		if (queueItem instanceof Queue.BuildableItem) {
			Queue.BuildableItem buildableItem = (Queue.BuildableItem)queueItem;

			jsonObject.put("pending", buildableItem.isPending());
			jsonObject.put("stuck", buildableItem.isStuck());
		}
		else if (queueItem instanceof Queue.LeftItem) {
			Queue.LeftItem leftItem = (Queue.LeftItem)queueItem;

			jsonObject.put("canceled", leftItem.isCancelled());
		}

		Map<String, Object> parameters = new HashMap<>();

		for (ParametersAction parametersAction :
				queueItem.getActions(ParametersAction.class)) {

			for (ParameterValue parameterValue :
					parametersAction.getParameters()) {

				parameters.put(
					parameterValue.getName(), parameterValue.getValue());
			}
		}

		jsonObject.put("parameters", parameters);

		jsonObject.put("task", _getQueueTaskJSONObject(queueItem.task));

		return jsonObject;
	}

	private static JSONObject _getQueueTaskJSONObject(Queue.Task queueTask) {
		if (queueTask == null) {
			return null;
		}

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("concurrent", queueTask.isConcurrentBuild());
		jsonObject.put("name", queueTask.getDisplayName());

		return jsonObject;
	}

}