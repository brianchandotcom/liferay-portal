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

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael Hashimoto
 */
public abstract class TopLevelBuildRunner<T extends TopLevelBuildData>
	extends BaseBuildRunner<T> {

	public List<String> getBatchNames() {
		return _batchNames;
	}

	@Override
	public void run() {
		super.run();

		propagateDistFilesToDistNodes();
	}

	protected TopLevelBuildRunner(T topLevelBuildData) {
		super(topLevelBuildData);
	}

	protected String[] getDistFileNames() {
		return new String[] {BuildData.JENKINS_BUILD_DATA_FILE_NAME};
	}

	protected void propagateDistFilesToDistNodes() {
		if (!JenkinsResultsParserUtil.isCINode()) {
			return;
		}

		writeJenkinsJSONObjectToFile();

		BuildData buildData = getBuildData();

		File workspaceDir = buildData.getWorkspaceDir();

		FilePropagator filePropagator = new FilePropagator(
			getDistFileNames(),
			JenkinsResultsParserUtil.combine(
				buildData.getHostname(), ":", workspaceDir.toString()),
			buildData.getDistPath(), buildData.getDistNodes());

		filePropagator.setCleanUpCommand(_FILE_PROPAGATOR_CLEAN_UP_COMMAND);

		filePropagator.start(_FILE_PROPAGATOR_THREAD_COUNT);
	}

	private static final String _FILE_PROPAGATOR_CLEAN_UP_COMMAND =
		JenkinsResultsParserUtil.combine(
			"find ", BuildData.DIST_ROOT_PATH,
			"/*/* -maxdepth 1 -type d -mmin +",
			String.valueOf(TopLevelBuildRunner._FILE_PROPAGATOR_EXPIRATION),
			" -exec rm -frv {} \\;");

	private static final int _FILE_PROPAGATOR_EXPIRATION = 180;

	private static final int _FILE_PROPAGATOR_THREAD_COUNT = 1;

	private final List<String> _batchNames = new ArrayList<>();

}