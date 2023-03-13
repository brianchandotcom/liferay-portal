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
import com.liferay.jenkins.plugin.events.publisher.JenkinsPublisherUtil;

import hudson.Extension;

import hudson.model.ManagementLink;

import hudson.security.Permission;

import java.io.IOException;

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
public class JenkinsEventsManagementLink extends ManagementLink {

	public JenkinsEventsManagementLink() {
		jenkinsEventsDescriptor = new JenkinsEventsDescriptor();

		jenkinsEventsDescriptor.load();
	}

	public void doJenkinsEventsConfiguration(
			StaplerRequest staplerRequest, StaplerResponse staplerResponse)
		throws IOException, ServletException {

		jenkinsEventsDescriptor.jenkinsPublishers.clear();

		JSONObject jsonObject = new JSONObject(
			staplerRequest.getParameter("json"));

		Object jenkinsPublishersObject = jsonObject.opt("jenkinsPublishers");

		if (jenkinsPublishersObject instanceof JSONArray) {
			JSONArray jenkinsPublishersJSONArray =
				(JSONArray)jenkinsPublishersObject;

			for (int i = 0; i < jenkinsPublishersJSONArray.length(); i++) {
				JSONObject jenkinsPublisherJSONObject =
					jenkinsPublishersJSONArray.optJSONObject(i);

				if (jenkinsPublisherJSONObject == null) {
					continue;
				}

				jenkinsEventsDescriptor.jenkinsPublishers.add(
					new JenkinsPublisher(jenkinsPublisherJSONObject));
			}
		}
		else if (jenkinsPublishersObject instanceof JSONObject) {
			jenkinsEventsDescriptor.jenkinsPublishers.add(
				new JenkinsPublisher((JSONObject)jenkinsPublishersObject));
		}
		else {
			jenkinsEventsDescriptor.jenkinsPublishers.clear();
		}

		jenkinsEventsDescriptor.save();

		JenkinsPublisherUtil.setJenkinsEventsDescriptor(
			jenkinsEventsDescriptor);

		Jenkins jenkins = Jenkins.getInstanceOrNull();

		if (jenkins != null) {
			staplerResponse.sendRedirect(jenkins.getRootUrl() + "/manage");
		}
	}

	@Override
	public String getDescription() {
		return "Configure Jenkins event listeners and publishers";
	}

	@Override
	public String getDisplayName() {
		return "Jenkins Events";
	}

	@Override
	public String getIconFileName() {
		return "clipboard.png";
	}

	@Override
	public Permission getRequiredPermission() {
		return Jenkins.ADMINISTER;
	}

	@Override
	public String getUrlName() {
		return "jenkins-events-configuration";
	}

	public JenkinsEventsDescriptor jenkinsEventsDescriptor;

}