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
import com.liferay.jenkins.results.parser.test.clazz.group.AxisTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.TestClassGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael Hashimoto
 */
public class SpiraResultImporter {

	public static void record(String buildURL) {
		Build build = BuildFactory.newBuild(buildURL, null);

		Job job = build.getJob();

		List<SpiraResult> spiraResults = new ArrayList<>();

		for (AxisTestClassGroup axisTestClassGroup :
				job.getAxisTestClassGroups()) {

			for (TestClassGroup.TestClass testClass :
					axisTestClassGroup.getTestClasses()) {

				spiraResults.add(
					SpiraResultFactory.newSpiraResult(
						axisTestClassGroup, testClass));
			}
		}

		for (SpiraResult spiraResult : spiraResults) {
			spiraResult.record();
		}
	}

}