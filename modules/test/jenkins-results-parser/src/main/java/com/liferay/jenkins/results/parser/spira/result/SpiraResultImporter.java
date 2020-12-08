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

package com.liferay.jenkins.results.parser.spira.result;

import com.liferay.jenkins.results.parser.Build;
import com.liferay.jenkins.results.parser.BuildFactory;
import com.liferay.jenkins.results.parser.Job;
import com.liferay.jenkins.results.parser.TopLevelBuild;
import com.liferay.jenkins.results.parser.test.clazz.group.AxisTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.TestClassGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael Hashimoto
 */
public class SpiraResultImporter {

	public SpiraResultImporter(String buildURL) {
		Build build = BuildFactory.newBuild(buildURL, null);

		if (!(build instanceof TopLevelBuild)) {
			throw new RuntimeException("Invalid top level build" + buildURL);
		}

		_topLevelBuild = (TopLevelBuild)build;

		_spiraBuildResult = SpiraResultFactory.newSpiraBuildResult(
			_topLevelBuild);
	}

	public void record() {
		Job job = _topLevelBuild.getJob();

		List<SpiraTestResult> spiraTestResults = new ArrayList<>();

		for (AxisTestClassGroup axisTestClassGroup :
				job.getAxisTestClassGroups()) {

			for (TestClassGroup.TestClass testClass :
					axisTestClassGroup.getTestClasses()) {

				spiraTestResults.add(
					SpiraResultFactory.newSpiraTestResult(
						_spiraBuildResult, axisTestClassGroup, testClass));
			}
		}

		for (SpiraTestResult spiraTestResult : spiraTestResults) {
			spiraTestResult.record();
		}
	}

	private final SpiraBuildResult _spiraBuildResult;
	private final TopLevelBuild _topLevelBuild;

}