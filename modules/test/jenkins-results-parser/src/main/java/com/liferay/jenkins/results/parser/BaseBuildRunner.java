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

import java.io.File;
import java.io.IOException;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseBuildRunner<T extends BuildData>
	implements BuildRunner<T> {

	public T getBuildData() {
		return _buildData;
	}

	@Override
	public void run() {
		initWorkspace();

		setUpWorkspace();
	}

	@Override
	public void setUp() {
		writeJenkinsJSONObjectToFile();
	}

	@Override
	public void tearDown() {
		initWorkspace();

		tearDownWorkspace();
	}

	protected BaseBuildRunner(T buildData) {
		_buildData = buildData;

		_jenkinsJSONObjectFile = new File(
			buildData.getWorkspaceDir(),
			BuildData.JENKINS_BUILD_DATA_FILE_NAME);

		_jenkinsJSONObject = _getJenkinsJSONObjectFromFile();

		_jenkinsJSONObject.addBuildData(_buildData);

		_job = JobFactory.newJob(_buildData);

		_job.readJobProperties();
	}

	protected Job getJob() {
		return _job;
	}

	protected abstract void initWorkspace();

	protected void setUpWorkspace() {
		if (workspace == null) {
			throw new RuntimeException("Workspace is null");
		}

		workspace.setUp(getJob());
	}

	protected void tearDownWorkspace() {
		if (workspace == null) {
			throw new RuntimeException("Workspace is null");
		}

		workspace.tearDown();
	}

	protected void writeJenkinsJSONObjectToFile() {
		try {
			JenkinsResultsParserUtil.write(
				_jenkinsJSONObjectFile, _jenkinsJSONObject.toString());
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	protected Workspace workspace;

	private JenkinsJSONObject _getJenkinsJSONObjectFromFile() {
		if (!_jenkinsJSONObjectFile.exists()) {
			return new JenkinsJSONObject();
		}

		try {
			return new JenkinsJSONObject(
				JenkinsResultsParserUtil.read(_jenkinsJSONObjectFile));
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	private final T _buildData;
	private final JenkinsJSONObject _jenkinsJSONObject;
	private final File _jenkinsJSONObjectFile;
	private final Job _job;

}