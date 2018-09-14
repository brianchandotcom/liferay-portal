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

package com.liferay.jenkins.results.parser;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseWorkspace implements Workspace {

	@Override
	public void addJenkinsWorkbench(String jenkinsGitHubURL) {
		if (!JenkinsResultsParserUtil.isCINode()) {
			return;
		}

		if (jenkinsGitHubURL == null) {
			return;
		}

		Workbench workbench = WorkbenchFactory.newWorkbench(
			jenkinsGitHubURL, "master");

		if (!(workbench instanceof JenkinsWorkbench)) {
			throw new RuntimeException("Invalid workbench " + workbench);
		}

		_jenkinsWorkbench = (JenkinsWorkbench)workbench;
	}

	@Override
	public JenkinsWorkbench getJenkinsWorkbench() {
		return _jenkinsWorkbench;
	}

	@Override
	public void setUp() {
		setUp(null);
	}

	@Override
	public void setUp(Job job) {
		setUpWorkbenches();

		if (job != null) {
			setWorkbenchJobProperties(job);
		}

		writeWorkbenchPropertiesFiles();
	}

	@Override
	public void tearDown() {
		tearDownWorkbenches();
	}

	protected void setUpJenkinsWorkbench() {
		if (_jenkinsWorkbench != null) {
			_jenkinsWorkbench.setUp();
		}
	}

	protected void setUpWorkbenches() {
		setUpJenkinsWorkbench();
	}

	protected abstract void setWorkbenchJobProperties(Job job);

	protected void tearDownJenkinsWorkbench() {
		if (_jenkinsWorkbench != null) {
			_jenkinsWorkbench.tearDown();
		}
	}

	protected void tearDownWorkbenches() {
		tearDownJenkinsWorkbench();
	}

	protected abstract void writeWorkbenchPropertiesFiles();

	private JenkinsWorkbench _jenkinsWorkbench;

}