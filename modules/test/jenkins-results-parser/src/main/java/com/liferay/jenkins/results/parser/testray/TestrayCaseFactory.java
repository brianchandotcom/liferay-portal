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

import com.liferay.jenkins.results.parser.TopLevelBuild;
import com.liferay.jenkins.results.parser.test.clazz.group.AxisTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.FunctionalAxisTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.JUnitAxisTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.TestClassGroup;

/**
 * @author Michael Hashimoto
 */
public class TestrayCaseFactory {

	public static TestrayCase newTestrayCase(
		TestrayBuild testrayBuild, TopLevelBuild topLevelBuild,
		AxisTestClassGroup axisTestClassGroup,
		TestClassGroup.TestClass testClass) {

		if (testrayBuild == null) {
			throw new RuntimeException("Please set a testray build");
		}

		if (topLevelBuild == null) {
			throw new RuntimeException("Please set a top level build");
		}

		if (axisTestClassGroup == null) {
			throw new RuntimeException("Please set an axis test class group");
		}

		if (testClass != null) {
			if (axisTestClassGroup instanceof FunctionalAxisTestClassGroup) {
				return new FunctionalBatchTestrayCase(
					testrayBuild, topLevelBuild, axisTestClassGroup, testClass);
			}
			else if (axisTestClassGroup instanceof JUnitAxisTestClassGroup) {
				return new JUnitBatchTestrayCase(
					testrayBuild, topLevelBuild, axisTestClassGroup, testClass);
			}
		}

		return new BatchTestrayCase(
			testrayBuild, topLevelBuild, axisTestClassGroup);
	}

}