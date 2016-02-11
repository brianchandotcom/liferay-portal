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
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang.StringUtils;
import org.apache.tools.ant.Project;

/**
 * @author Kevin Yen
 */
public class WaitForInvokedJobs {

	public static final String BEANSHELL_MAP_TOP_LEVEL_JOB_KEY = "topLevelJob";

	public static final String COMPLETED_BUILD_URLS_PROPERTY_NAME =
		"completed.build.urls";

	public static final String MAX_STARTING_TIME_PROPERTY_NAME =
		"max.starting.time";

	public static final String MAX_WAIT_TIME_PROPERTY_NAME = "max.wait.time";

	public static final String UPDATE_PERIOD_PROPERTY_NAME = "update.period";

	public static TopLevelJob wait(
			Project project, Map<String, Object> beanShellMap)
		throws Exception {

		TopLevelJob topLevelJob = (TopLevelJob)beanShellMap.get(
			BEANSHELL_MAP_TOP_LEVEL_JOB_KEY);

		if (topLevelJob == null) {
			throw new NullPointerException(
				"topLevelJob does not exist in beanShellMap");
		}

		beanShellMap.remove(BEANSHELL_MAP_TOP_LEVEL_JOB_KEY);

		long maxStartingTime = 900000;

		if (project.getProperty(MAX_STARTING_TIME_PROPERTY_NAME) != null) {
			maxStartingTime = Long.parseLong(
				project.getProperty(MAX_STARTING_TIME_PROPERTY_NAME)) * 60000;
		}

		long maxWaitTime = 7200000;

		if (project.getProperty(MAX_WAIT_TIME_PROPERTY_NAME) != null) {
			maxWaitTime = Long.parseLong(
				project.getProperty(MAX_WAIT_TIME_PROPERTY_NAME)) * 60000;
		}

		long updatePeriod = 30000;

		if (project.getProperty(UPDATE_PERIOD_PROPERTY_NAME) != null) {
			updatePeriod = Long.parseLong(
				project.getProperty(UPDATE_PERIOD_PROPERTY_NAME)) * 1000;
		}

		wait(topLevelJob, updatePeriod, maxStartingTime, maxWaitTime);

		List<DownstreamJob> completedJobs = topLevelJob.getDownstreamJobs(
			"completed");

		String completedBuildURLs = StringUtils.join(
			getBuildURLs(completedJobs), ",");

		project.setProperty(
			COMPLETED_BUILD_URLS_PROPERTY_NAME, completedBuildURLs);

		return topLevelJob;
	}

	public static void wait(
			TopLevelJob topLevelJob, long updatePeriod, long maxStartingTime,
			long maxWaitTime)
		throws Exception {

		long startTime = System.currentTimeMillis();

		while (true) {
			for (DownstreamJob downstreamJob :
					topLevelJob.getDownstreamJobs()) {

				downstreamJob.update();
			}

			System.out.print(topLevelJob.getDownstreamJobCount("completed"));
			System.out.print(" Completed / ");
			System.out.print(topLevelJob.getDownstreamJobCount("running"));
			System.out.print(" Running / ");
			System.out.print(topLevelJob.getDownstreamJobCount("queued"));
			System.out.print(" Queued / ");
			System.out.print(topLevelJob.getDownstreamJobCount("starting"));
			System.out.print(" Starting / ");
			System.out.print(topLevelJob.getDownstreamJobCount());
			System.out.println(" Total");

			long elapsedTime = System.currentTimeMillis() - startTime;

			if ((elapsedTime > maxStartingTime) &&
				(topLevelJob.getDownstreamJobCount("starting") > 0)) {

				throw new TimeoutException("Unable to find downstream job");
			}
			else if ((elapsedTime > maxWaitTime) &&
					 (topLevelJob.getDownstreamJobCount("completed") <
						 topLevelJob.getDownstreamJobCount())) {

				throw new TimeoutException("Downstream job timeout");
			}
			else if (topLevelJob.getDownstreamJobCount("completed") ==
						topLevelJob.getDownstreamJobCount()) {

				break;
			}
			else {
				Thread.sleep(updatePeriod);
			}
		}
	}

	private static List<String> getBuildURLs(List<DownstreamJob> downstreamJobs)
		throws Exception {

		List<String> buildURLs = new ArrayList<>();

		for (DownstreamJob downstreamJob : downstreamJobs) {
			buildURLs.add(downstreamJob.getBuildURL());
		}

		return buildURLs;
	}

	private static List<DownstreamJob> getFoundJobs(TopLevelJob topLevelJob) {
		List<DownstreamJob> foundJobs = topLevelJob.getDownstreamJobs(
			"completed");
		foundJobs.addAll(topLevelJob.getDownstreamJobs("running"));

		return foundJobs;
	}

}