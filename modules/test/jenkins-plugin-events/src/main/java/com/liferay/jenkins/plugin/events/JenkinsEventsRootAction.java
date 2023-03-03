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

import com.liferay.jenkins.plugin.events.publisher.JenkinsPublisher;

import hudson.Extension;

import hudson.model.Describable;
import hudson.model.Descriptor;
import hudson.model.RootAction;

import java.io.IOException;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;

import jenkins.model.Jenkins;

import org.json.JSONArray;
import org.json.JSONObject;

import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

/**
 * @author Michael Hashimoto
 */
@Extension
public class JenkinsEventsRootAction
	extends Descriptor<JenkinsEventsRootAction>
	implements Describable<JenkinsEventsRootAction>, RootAction {

	public JenkinsEventsRootAction() {
		super(JenkinsEventsRootAction.class);

		jenkinsPublishers = new LinkedList<>();

		load();
	}

	public void doJenkinsEventsConfiguration(
			StaplerRequest staplerRequest, StaplerResponse staplerResponse)
		throws IOException, ServletException {

		jenkinsPublishers.clear();

		JSONObject jsonObject = new JSONObject(
			staplerRequest.getParameter("json"));

		Object jenkinsPublishersObject = jsonObject.get("jenkinsPublishers");

		if (jenkinsPublishersObject instanceof JSONArray) {
			JSONArray jenkinsPublishersJSONArray =
				(JSONArray)jenkinsPublishersObject;

			for (int i = 0; i < jenkinsPublishersJSONArray.length(); i++) {
				JSONObject jenkinsPublisherJSONObject =
					jenkinsPublishersJSONArray.optJSONObject(i);

				if (jenkinsPublisherJSONObject == null) {
					continue;
				}

				jenkinsPublishers.add(
					new JenkinsPublisher(jenkinsPublisherJSONObject));
			}
		}
		else if (jenkinsPublishersObject instanceof JSONObject) {
			jenkinsPublishers.add(
				new JenkinsPublisher((JSONObject)jenkinsPublishersObject));
		}

		save();

		Jenkins jenkins = Jenkins.getInstanceOrNull();

		if (jenkins != null) {
			staplerResponse.sendRedirect(jenkins.getRootUrl());
		}
	}

	@Override
	public Descriptor<JenkinsEventsRootAction> getDescriptor() {
		return this;
	}

	@Override
	public String getDisplayName() {
		return "Jenkins Events";
	}

	@Override
	public String getIconFileName() {
		return "clipboard.png";
	}

	public List<JenkinsPublisher> getJenkinsPublishers() {
		return jenkinsPublishers;
	}

	@Override
	public String getUrlName() {
		return "jenkins-events";
	}

	public List<JenkinsPublisher> jenkinsPublishers;

}