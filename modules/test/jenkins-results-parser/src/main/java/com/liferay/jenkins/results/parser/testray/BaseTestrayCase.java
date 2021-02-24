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

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.Build;
import com.liferay.jenkins.results.parser.TopLevelBuild;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseTestrayCase implements TestrayCase {

	public BaseTestrayCase(
		TestrayBuild testrayBuild, TopLevelBuild topLevelBuild) {

		_testrayBuild = testrayBuild;
		_topLevelBuild = topLevelBuild;
	}

	@Override
	public String getStatus() {
		Build build = getBuild();

		if ((build == null) || build.isFailing()) {
			return "failed";
		}

		return "passed";
	}

	@Override
	public TestrayBuild getTestrayBuild() {
		return _testrayBuild;
	}

	@Override
	public TestrayProject getTestrayProject() {
		return _testrayBuild.getTestrayProject();
	}

	@Override
	public TestrayRoutine getTestrayRoutine() {
		return _testrayBuild.getTestrayRoutine();
	}

	@Override
	public TestrayServer getTestrayServer() {
		return _testrayBuild.getTestrayServer();
	}

	@Override
	public TopLevelBuild getTopLevelBuild() {
		return _topLevelBuild;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(getStatus());
		sb.append(" ");
		sb.append(getName());
		sb.append(" ");

		Build build = getBuild();

		if (build != null) {
			sb.append(build.getBuildURL());
		}
		else {
			sb.append(" No build found");
		}

		return sb.toString();
	}

	private final TestrayBuild _testrayBuild;
	private final TopLevelBuild _topLevelBuild;

}