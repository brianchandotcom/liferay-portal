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

/**
 * @author Peter Yoo
 */
public class FunctionalBatchBuild extends BatchBuild {

	public FunctionalBatchBuild(String url) {
		super(url);
	}

	public FunctionalBatchBuild(String url, TopLevelBuild topLevelBuild) {
		super(url, topLevelBuild);
	}

	@Override
	public void update() {
		super.update();

		if (!badBuildNumbers.isEmpty()) {
			return;
		}

		Build failedAxisBuild = null;

		for (Build axisBuild : getDownstreamBuilds("completed")) {
			String axisBuildResult = axisBuild.getResult();

			if ((axisBuildResult == null) ||
				axisBuildResult.equals("SUCCESS")) {

				continue;
			}

			failedAxisBuild = axisBuild;

			break;
		}

		if (failedAxisBuild != null) {
			StringBuilder sb = new StringBuilder();

			sb.append("Functional test failure detected at ");
			sb.append(failedAxisBuild.getBuildURL());
			sb.append(". This batch will be reinvoked.");

			System.out.println(sb);

			reinvoke();
		}
	}

}