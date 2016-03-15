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

	public TopLevelBuild(String url) throws Exception {
		super(url);
	}

	public void addDownstreamJob(String invocationURL) throws Exception {
		_downstreamJobs.add(new DownstreamBuild(invocationURL, this));
	}

	public List<DownstreamBuild> getDownstreamJobs(String status) {
		if (status == null) {
			return _downstreamJobs;
		}

		List<DownstreamBuild> filteredDownstreamJobs = new ArrayList<>();

		for (DownstreamBuild downstreamJob : _downstreamJobs) {
			if (status.equals(downstreamJob.getStatus())) {
				filteredDownstreamJobs.add(downstreamJob);
			}
		}

		return filteredDownstreamJobs;
	}

	@Override
	public void update() throws Exception {
		if (_downstreamJobs == null) {
			setStatus("running");

			return;
		}

		for (DownstreamBuild downstreamJob : _downstreamJobs) {
			downstreamJob.update();
		}

		if (_downstreamJobs.size() == getDownstreamJobCount("completed")) {
			setStatus("completed");

			return;
		}

		setStatus("running");
	}

	public void waitForDownstreamJobs(
			long sleepTime, long maxStartTime, long maxWaitTime)
		throws Exception {

		long startTime = System.currentTimeMillis();

		while (true) {
			update();

			StringBuilder sb = new StringBuilder();

			sb.append(getDownstreamJobCount("completed"));
			sb.append(" Completed / ");
			sb.append(getDownstreamJobCount("running"));
			sb.append(" Running / ");
			sb.append(getDownstreamJobCount("queued"));
			sb.append(" Queued / ");
			sb.append(getDownstreamJobCount("starting"));
			sb.append(" Starting / ");
			sb.append(_downstreamJobs.size());
			sb.append(" Total");

			System.out.println(sb.toString());

			List<DownstreamBuild> missingDownstreamJobs = getDownstreamJobs(
				"missing");

			for (DownstreamBuild missingDownstreamJob : missingDownstreamJobs) {
				long time = System.currentTimeMillis();

				if ((time - missingDownstreamJob.statusModifiedTime) >
						maxStartTime) {

					throw new TimeoutException("Missing downstream job");
				}
			}

			long elapsedTime = System.currentTimeMillis() - startTime;

			if ((elapsedTime > maxStartTime) &&
				(getDownstreamJobCount("starting") > 0)) {

				throw new TimeoutException("Unable to find downstream job");
			}
			else if ((elapsedTime > maxWaitTime) &&
					 (getDownstreamJobCount("completed") <
						 _downstreamJobs.size())) {

				throw new TimeoutException("Timed out downstream job");
			}
			else if (getDownstreamJobCount("completed") ==
						_downstreamJobs.size()) {

				break;
			}
			else {
				Thread.sleep(sleepTime);
			}
		}
	}

	protected int getDownstreamJobCount(String status) {
		List<DownstreamBuild> downstreamJobs = getDownstreamJobs(status);

		return downstreamJobs.size();
	}

	private final List<DownstreamBuild> _downstreamJobs = new ArrayList<>();

}