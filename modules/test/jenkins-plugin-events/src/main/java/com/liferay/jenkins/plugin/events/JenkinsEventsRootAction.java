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

import hudson.model.Describable;
import hudson.model.Descriptor;
import hudson.model.RootAction;

import java.io.IOException;

import javax.servlet.ServletException;

import jenkins.model.Jenkins;

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

		load();
	}

	public void doJenkinsEventsConfiguration(
			StaplerRequest staplerRequest, StaplerResponse staplerResponse)
		throws IOException, ServletException {

		JSONObject jsonObject = new JSONObject(
			staplerRequest.getParameter("json"));

		webHookURL = jsonObject.getString("webHookURL");

		save();

		Jenkins jenkins = Jenkins.getActiveInstance();

		staplerResponse.sendRedirect(jenkins.getRootUrl());
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

	@Override
	public String getUrlName() {
		return "jenkins-events";
	}

	public String webHookURL;

}