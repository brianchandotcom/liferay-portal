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
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Peter Yoo
 */
public class AutoCloseRule {

	public AutoCloseRule(String ruleData) {
		this.ruleData = ruleData;

		String[] ruleDataArray = ruleData.split("\\|");

		rulePattern = Pattern.compile(ruleDataArray[0]);

		if (ruleDataArray[1].endsWith("%")) {
			String percentageRule = ruleDataArray[1];

			maxFailPercentage = Integer.parseInt(
				percentageRule.substring(0, percentageRule.length() - 1)) / 100;
		}
		else {
			maxFailCount = Integer.parseInt(ruleDataArray[1]);
		}
	}

	public List<Build> evaluate(List<Build> downstreamBuilds) {
		downstreamBuilds = getMatchingBuilds(downstreamBuilds);

		if (downstreamBuilds.isEmpty()) {
			return Collections.emptyList();
		}

		List<Build> failedDownstreamBuilds = new ArrayList<>(
			downstreamBuilds.size());

		int failLimit = 0;

		if (maxFailPercentage != -1) {
			failLimit = (int)(maxFailPercentage * downstreamBuilds.size());

			if (failLimit > 0) {
				failLimit--;
			}
		}
		else {
			failLimit = maxFailCount;
		}

		for (Build downstreamBuild : downstreamBuilds) {
			String status = downstreamBuild.getStatus();

			if (!status.equals("completed")) {
				continue;
			}

			String result = downstreamBuild.getResult();

			if ((result != null) && !result.equals("SUCCESS")) {
				failedDownstreamBuilds.add(downstreamBuild);
			}
		}

		if (failedDownstreamBuilds.size() > failLimit) {
			return failedDownstreamBuilds;
		}

		return Collections.emptyList();
	}

	@Override
	public String toString() {
		return ruleData;
	}

	protected String getBatchName(Build build) {
		String batchName = build.getParameterValue("JOB_VARIANT");

		if ((batchName == null) || batchName.isEmpty()) {
			batchName = build.getParameterValue("JENKINS_JOB_VARIANT");
		}

		return batchName;
	}

	protected List<Build> getMatchingBuilds(List<Build> downstreamBuilds) {
		List<Build> filteredDownstreamBuilds = new ArrayList<>(
			downstreamBuilds.size());

		for (Build downstreamBuild : downstreamBuilds) {
			String batchName = getBatchName(downstreamBuild);

			if ((batchName == null) || batchName.isEmpty()) {
				continue;
			}

			Matcher matcher = rulePattern.matcher(
				getBatchName(downstreamBuild));

			if (matcher.matches()) {
				filteredDownstreamBuilds.add(downstreamBuild);
			}
		}

		return filteredDownstreamBuilds;
	}

	protected int maxFailCount = -1;
	protected float maxFailPercentage = -1;
	protected String ruleData;
	protected Pattern rulePattern;

}