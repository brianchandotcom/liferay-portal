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
import com.liferay.jenkins.results.parser.test.clazz.group.AxisTestClassGroup;

/**
 * @author Michael Hashimoto
 */
public class BatchTestrayCaseResult extends TestrayCaseResult {

	public BatchTestrayCaseResult(
		TestrayBuild testrayBuild, TopLevelBuild topLevelBuild,
		AxisTestClassGroup axisTestClassGroup) {

		super(testrayBuild, topLevelBuild);

		_axisTestClassGroup = axisTestClassGroup;
	}

	public String getBatchName() {
		return _axisTestClassGroup.getBatchName();
	}

	public Build getBuild() {
		TopLevelBuild topLevelBuild = getTopLevelBuild();

		return topLevelBuild.getDownstreamAxisBuild(
			_axisTestClassGroup.getAxisName());
	}

	@Override
	public String getName() {
		return _axisTestClassGroup.getAxisName();
	}

	@Override
	public Status getStatus() {
		Build build = getBuild();

		if ((build == null) || build.isFailing()) {
			return Status.FAILED;
		}

		return Status.PASSED;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(getStatus());
		sb.append(" ");
		sb.append(getBatchName());
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

	private final AxisTestClassGroup _axisTestClassGroup;

}