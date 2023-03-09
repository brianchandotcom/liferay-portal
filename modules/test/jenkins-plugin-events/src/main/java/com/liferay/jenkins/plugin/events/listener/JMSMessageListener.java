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

import hudson.model.ParameterValue;
import hudson.model.ParametersAction;
import hudson.model.Project;
import hudson.model.Queue;
import hudson.model.StringParameterValue;
import hudson.model.TopLevelItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import jenkins.model.Jenkins;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class JMSMessageListener implements MessageListener {

	@Override
	public void onMessage(Message message) {
		Jenkins jenkins = Jenkins.getInstanceOrNull();

		if ((jenkins == null) || !(message instanceof TextMessage)) {
			return;
		}

		Queue queue = jenkins.getQueue();

		if (queue == null) {
			return;
		}

		TextMessage textMessage = (TextMessage)message;

		String text;

		try {
			text = textMessage.getText();
		}
		catch (JMSException jmsException) {
			throw new RuntimeException(jmsException);
		}

		JSONObject jsonObject;

		try {
			jsonObject = new JSONObject(text);
		}
		catch (JSONException jsonException) {
			throw new RuntimeException();
		}

		String jobName = jsonObject.optString("jobName");

		if (jobName == null) {
			return;
		}

		Map<String, TopLevelItem> topLevelItems = jenkins.getItemMap();

		TopLevelItem topLevelItem = topLevelItems.get(jobName);

		if (!(topLevelItem instanceof Project)) {
			return;
		}

		Project project = (Project)topLevelItem;

		List<ParameterValue> parameterValues = new ArrayList<>();

		JSONObject jobParametersJSONObject = jsonObject.optJSONObject(
			"jobParameters");

		if (jobParametersJSONObject != JSONObject.NULL) {
			for (String key : jobParametersJSONObject.keySet()) {
				parameterValues.add(
					new StringParameterValue(
						key, jobParametersJSONObject.getString(key)));
			}
		}

		ParametersAction parametersAction = new ParametersAction(
			parameterValues);

		queue.schedule(project, 0, parametersAction);
	}

}