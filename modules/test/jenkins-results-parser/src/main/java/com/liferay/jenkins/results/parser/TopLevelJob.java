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
public class TopLevelJob extends BaseJob {

	public TopLevelJob(String buildURL) {
		super(buildURL);

		downstreamJobs = new ArrayList<>();
	}

	public void add(DownstreamJob downstreamJob) {
		downstreamJobs.add(downstreamJob);
	}

	public int getDownstreamJobCount() {
		return downstreamJobs.size();
	}

	public int getDownstreamJobCount(String status) {
		return getDownstreamJobs(status).size();
	}

	public List<DownstreamJob> getDownstreamJobs() {
		return downstreamJobs;
	}

	public List<DownstreamJob> getDownstreamJobs(String status) {
		List<DownstreamJob> downstreamJobWithStatus = new ArrayList<>();

		for (DownstreamJob downstreamJob : downstreamJobs) {
			if (downstreamJob.getStatus().equals(status)) {
				downstreamJobWithStatus.add(downstreamJob);
			}
		}

		return downstreamJobWithStatus;
	}

	protected List<DownstreamJob> downstreamJobs;

}