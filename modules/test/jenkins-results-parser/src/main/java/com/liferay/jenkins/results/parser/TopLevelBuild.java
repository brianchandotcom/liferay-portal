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

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kevin Yen
 */
public class TopLevelBuild extends BaseBuild {

	public TopLevelBuild(String buildURL) throws Exception {
		super(buildURL);
	}

	public void addDownstreamBuilds(String... invocationURLs) throws Exception {
		for (String invocationURL : invocationURLs) {
			_downstreamBuilds.add(new DownstreamBuild(invocationURL, this));
		}

		String status = getStatus();

		if (status.equals("completed")) {
			setStatus(null);
		}

		update();
	}

	public List<DownstreamBuild> getDownstreamBuilds(String status) {
		if (status == null) {
			return _downstreamBuilds;
		}

		List<DownstreamBuild> filteredDownstreamBuilds = new ArrayList<>();

		for (DownstreamBuild downstreamBuild : _downstreamBuilds) {
			if (status.equals(downstreamBuild.getStatus())) {
				filteredDownstreamBuilds.add(downstreamBuild);
			}
		}

		return filteredDownstreamBuilds;
	}

	@Override
	public void update() throws Exception {
		String status = getStatus();

		if (status == null) {
			setStatus("running");
		}

		if (status.equals("completed")) {
			return;
		}

		for (DownstreamBuild downstreamBuild : _downstreamBuilds) {
			downstreamBuild.update();
		}

		if (_downstreamBuilds.size() == getDownstreamBuildCount("completed")) {
			setStatus("completed");

			return;
		}

		if (getDownstreamBuildCount("missing") > 0) {
			setStatus("missing");

			return;
		}

		if (getDownstreamBuildCount("starting") > 0) {
			setStatus("starting");

			return;
		}

		setStatus("running");
	}

	protected int getDownstreamBuildCount(String status) {
		List<DownstreamBuild> downstreamBuilds = getDownstreamBuilds(status);

		return downstreamBuilds.size();
	}

	private final List<DownstreamBuild> _downstreamBuilds = new ArrayList<>();

}