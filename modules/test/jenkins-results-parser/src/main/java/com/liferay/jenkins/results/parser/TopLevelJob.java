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
public class TopLevelJob extends BaseJob {

	public TopLevelJob(String url) throws Exception {
		super(url);
	}

	public int getDownstreamJobCount() {
		return _downstreamJobs.size();
	}

	public int getDownstreamJobCount(String status) {
		List<DownstreamJob> downstreamJobs = getDownstreamJobs(status); 

		return downstreamJobs.size();
	}

	public List<DownstreamJob> getDownstreamJobs() {
		return getDownstreamJobs(null);
	}

	public List<DownstreamJob> getDownstreamJobs(String status) {
		if (status == null) {
			return _downstreamJobs;
		}

		List<DownstreamJob> filteredDownstreamJobs = new ArrayList<>();

		for (DownstreamJob downstreamJob : _downstreamJobs) {
			if (status.equals(downstreamJob.getStatus())) {
				filteredDownstreamJobs.add(downstreamJob);
			}
		}

		return filteredDownstreamJobs;
	}

	@Override
	public void update() throws Exception {
		for (DownstreamJob downstreamJob : _downstreamJobs) {
			downstreamJob.update();
		}
		
		if (_downstreamJobs.size() == getDownstreamJobCount("completed")) {
			setStatus("completed");

			return;
		}

		setStatus("running");
	}

	public void waitForDownstreamJobs(
			long updatePeriod, long maxStartingTime, long maxWaitTime)
		throws Exception {

		long startTime = System.currentTimeMillis();

		while (true) {
			update();

			System.out.print(getDownstreamJobCount("completed"));
			System.out.print(" Completed / ");
			System.out.print(getDownstreamJobCount("running"));
			System.out.print(" Running / ");
			System.out.print(getDownstreamJobCount("queued"));
			System.out.print(" Queued / ");
			System.out.print(getDownstreamJobCount("starting"));
			System.out.print(" Starting / ");
			System.out.print(getDownstreamJobCount());
			System.out.println(" Total");

			List<DownstreamJob> missingJobs = getDownstreamJobs("missing");

			for (DownstreamJob missingJob : missingJobs) {
				long time = System.currentTimeMillis();

				if ((time - missingJob.getStatusModifiedTime()) >
						maxStartingTime) {

					throw new TimeoutException("Downstream job disappeared");
				}
			}

			long elapsedTime = System.currentTimeMillis() - startTime;

			if ((elapsedTime > maxStartingTime) &&
				(getDownstreamJobCount("starting") > 0)) {

				throw new TimeoutException("Unable to find downstream job");
			}
			else if ((elapsedTime > maxWaitTime) &&
					 (getDownstreamJobCount("completed") <
						 getDownstreamJobCount())) {

				throw new TimeoutException("Downstream job timeout");
			}
			else if (getDownstreamJobCount("completed") ==
						getDownstreamJobCount()) {

				break;
			}
			else {
				Thread.sleep(updatePeriod);
			}
		}
	}

	protected List<String> getDownstreamURLs(String status) throws Exception {
		List<DownstreamJob> downstreamJobs = getDownstreamJobs(status);
		List<String> downstreamURLs = new ArrayList<>(downstreamJobs.size());

		for (DownstreamJob downstreamJob : downstreamJobs) {
			downstreamURLs.add(downstreamJob.getURL());
		}

		return downstreamURLs;
	}

	private List<DownstreamJob> _downstreamJobs = new ArrayList<>();

}