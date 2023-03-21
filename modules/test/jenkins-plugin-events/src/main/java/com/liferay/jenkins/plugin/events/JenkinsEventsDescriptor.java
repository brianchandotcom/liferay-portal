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

import hudson.model.Describable;
import hudson.model.Descriptor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael Hashimoto
 */
@Extension
public class JenkinsEventsDescriptor
	extends Descriptor<JenkinsEventsDescriptor>
	implements Describable<JenkinsEventsDescriptor> {

	public JenkinsEventsDescriptor() {
		super(JenkinsEventsDescriptor.class);

		jenkinsPublishers = new ArrayList<>();

		load();

		for (JenkinsPublisher jenkinsPublisher : jenkinsPublishers) {
			jenkinsPublisher.subscribe();
		}

		JenkinsPublisherUtil.setJenkinsEventsDescriptor(this);
	}

	@Override
	public Descriptor<JenkinsEventsDescriptor> getDescriptor() {
		return this;
	}

	public List<JenkinsPublisher> getJenkinsPublishers() {
		return jenkinsPublishers;
	}

	public List<JenkinsPublisher> jenkinsPublishers;

}