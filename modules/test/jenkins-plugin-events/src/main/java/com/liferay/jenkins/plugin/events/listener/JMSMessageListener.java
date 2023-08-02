/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.plugin.events.listener;

import hudson.model.Action;
import hudson.model.Label;
import hudson.model.ParameterValue;
import hudson.model.ParametersAction;
import hudson.model.Project;
import hudson.model.Queue;
import hudson.model.StringParameterValue;
import hudson.model.TopLevelItem;
import hudson.model.labels.LabelAssignmentAction;
import hudson.model.queue.SubTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

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

		queue.schedule(
			(Project)topLevelItem, 0, _getLabelAction(jsonObject),
			_getParametersAction(jsonObject));
	}

	private Action _getLabelAction(JSONObject jsonObject) {
		JSONObject jenkinsNodeJSONObject = jsonObject.optJSONObject(
			"jenkinsNode");

		if (jenkinsNodeJSONObject == null) {
			return null;
		}

		String primaryLabel = jenkinsNodeJSONObject.optString("primaryLabel");

		if ((primaryLabel == null) || primaryLabel.isEmpty()) {
			return null;
		}

		return new SimpleLabelAssignmentAction(primaryLabel);
	}

	private Action _getParametersAction(JSONObject jsonObject) {
		JSONObject jobParametersJSONObject = jsonObject.optJSONObject(
			"jobParameters");

		if ((jobParametersJSONObject == null) ||
			jobParametersJSONObject.isEmpty()) {

			return null;
		}

		List<ParameterValue> parameterValues = new ArrayList<>();

		for (String key : jobParametersJSONObject.keySet()) {
			parameterValues.add(
				new StringParameterValue(
					key, jobParametersJSONObject.getString(key)));
		}

		return new ParametersAction(parameterValues);
	}

	private static class SimpleLabelAssignmentAction
		implements LabelAssignmentAction {

		@Override
		public Label getAssignedLabel(@Nonnull SubTask subTask) {
			return Label.get(_label);
		}

		@Override
		public String getDisplayName() {
			return "simple";
		}

		@Override
		public String getIconFileName() {
			return null;
		}

		@Override
		public final String getUrlName() {
			return "simple";
		}

		private SimpleLabelAssignmentAction(String label) {
			_label = label;
		}

		private final String _label;

	}

}