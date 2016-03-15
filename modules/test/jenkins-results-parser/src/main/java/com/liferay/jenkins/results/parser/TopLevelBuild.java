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
import java.util.concurrent.TimeoutException;

/**
 * @author Kevin Yen
 */
public class TopLevelBuild extends BaseBuild {

	public TopLevelBuild(String buildURL) throws Exception {
		super(buildURL);
	}

	public void addDownstreamBuild(String invocationURL) throws Exception {
		_downstreamBuilds.add(new DownstreamBuild(invocationURL, this));
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
		if (_downstreamBuilds == null) {
			setStatus("running");

			return;
		}

		for (DownstreamBuild downstreamBuild : _downstreamBuilds) {
			downstreamBuild.update();
		}

		if (_downstreamBuilds.size() == getDownstreamBuildCount("completed")) {
			setStatus("completed");

			return;
		}

		setStatus("running");
	}

	public void waitForDownstreamBuilds(
			long sleepTime, long maxStartTime, long maxWaitTime)
		throws Exception {

		long startTime = System.currentTimeMillis();

		while (true) {
			update();

			StringBuilder sb = new StringBuilder();

			sb.append(getDownstreamBuildCount("completed"));
			sb.append(" Completed / ");
			sb.append(getDownstreamBuildCount("running"));
			sb.append(" Running / ");
			sb.append(getDownstreamBuildCount("queued"));
			sb.append(" Queued / ");
			sb.append(getDownstreamBuildCount("starting"));
			sb.append(" Starting / ");
			sb.append(_downstreamBuilds.size());
			sb.append(" Total");

			System.out.println(sb.toString());

			List<DownstreamBuild> missingDownstreamBuilds = getDownstreamBuilds(
				"missing");

			for (DownstreamBuild missingDownstreamBuild :
					missingDownstreamBuilds) {

				long time = System.currentTimeMillis();

				if ((time - missingDownstreamBuild.statusModifiedTime) >
						maxStartTime) {

					throw new TimeoutException("Missing downstream build");
				}
			}

			long elapsedTime = System.currentTimeMillis() - startTime;

			if ((elapsedTime > maxStartTime) &&
				(getDownstreamBuildCount("starting") > 0)) {

				throw new TimeoutException("Unable to find downstream build");
			}
			else if ((elapsedTime > maxWaitTime) &&
					 (getDownstreamBuildCount("completed") <
						 _downstreamBuilds.size())) {

				throw new TimeoutException("Timed out downstream build");
			}
			else if (getDownstreamBuildCount("completed") ==
						_downstreamBuilds.size()) {

				break;
			}
			else {
				Thread.sleep(sleepTime);
			}
		}
	}

	protected int getDownstreamBuildCount(String status) {
		List<DownstreamBuild> downstreamBuilds = getDownstreamBuilds(status);

		return downstreamBuilds.size();
	}

	private final List<DownstreamBuild> _downstreamBuilds = new ArrayList<>();

}