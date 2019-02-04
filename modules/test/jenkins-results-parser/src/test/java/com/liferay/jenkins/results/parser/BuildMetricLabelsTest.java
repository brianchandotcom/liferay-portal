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

import java.util.List;
import java.util.Map;

import org.junit.Test;

/**
 * @author Peter Yoo
 */
public class BuildMetricLabelsTest extends BuildTest {

	@Test
	public void testMetricLabelGeneration() throws Exception {
		expectedMessageGenerator = new ExpectedMessageGenerator() {

			@Override
			public String getMessage(TestSample testSample) {
				Build build = BuildFactory.newBuildFromArchive(
					testSample.getSampleDirName());

				StringBuilder sb = new StringBuilder();

				sb.append(_getMetricLabelsString(build));

				List<Build> downstreamBuilds = build.getDownstreamBuilds(null);

				for (Build downstreamBuild : downstreamBuilds) {
					sb.append(_getMetricLabelsString(downstreamBuild));
				}

				return sb.toString();
			}

		};

		assertSamples();
	}

	@Override
	protected File getExpectedMessageFile(TestSample testSample) {
		return new File(testSample.getSampleDir(), "expected-metric-labels");
	}

	private String _getMetricLabelsString(Build build) {
		Map<String, String> metricLabels = build.getMetricLabels();

		StringBuilder sb = new StringBuilder();

		for (Map.Entry<String, String> metricLabel : metricLabels.entrySet()) {
			sb.append(build.getJobName());
			sb.append("_");

			String jobVariant = build.getJobVariant();

			if ((jobVariant != null) && !jobVariant.equals("")) {
				sb.append(jobVariant);
				sb.append("_");
			}

			sb.append(metricLabel.getKey());
			sb.append("=");
			sb.append(metricLabel.getValue());
			sb.append("\n");
		}

		return sb.toString();
	}

}