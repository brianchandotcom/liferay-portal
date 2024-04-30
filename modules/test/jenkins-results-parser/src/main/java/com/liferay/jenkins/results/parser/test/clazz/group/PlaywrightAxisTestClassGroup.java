/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.clazz.group;

import com.liferay.jenkins.results.parser.test.clazz.PlaywrightJUnitTestClass;
import com.liferay.jenkins.results.parser.test.clazz.TestClass;

import java.util.List;

import org.json.JSONObject;

/**
 * @author Kenji Heigel
 */
public class PlaywrightAxisTestClassGroup extends AxisTestClassGroup {

	@Override
	public Integer getMinimumSlaveRAM() {
		List<TestClass> testClasses = getTestClasses();

		if (testClasses.isEmpty()) {
			return super.getMinimumSlaveRAM();
		}

		TestClass testClass = testClasses.get(0);

		if (!(testClass instanceof PlaywrightJUnitTestClass)) {
			return super.getMinimumSlaveRAM();
		}

		PlaywrightJUnitTestClass playwrightJUnitTestClass =
			(PlaywrightJUnitTestClass)testClass;

		Integer minimumSlaveRAM = playwrightJUnitTestClass.getMinimumSlaveRAM();

		if (minimumSlaveRAM == null) {
			return super.getMinimumSlaveRAM();
		}

		return minimumSlaveRAM;
	}

	@Override
	public String getSlaveLabel() {
		List<TestClass> testClasses = getTestClasses();

		if (testClasses.isEmpty()) {
			return super.getSlaveLabel();
		}

		TestClass testClass = testClasses.get(0);

		if (!(testClass instanceof PlaywrightJUnitTestClass)) {
			return super.getSlaveLabel();
		}

		PlaywrightJUnitTestClass playwrightJUnitTestClass =
			(PlaywrightJUnitTestClass)testClass;

		String slaveLabel = playwrightJUnitTestClass.getSlaveLabel();

		if (slaveLabel == null) {
			return super.getSlaveLabel();
		}

		return slaveLabel;
	}

	protected PlaywrightAxisTestClassGroup(
		BatchTestClassGroup batchTestClassGroup) {

		super(batchTestClassGroup);
	}

	protected PlaywrightAxisTestClassGroup(
		JSONObject jsonObject, SegmentTestClassGroup segmentTestClassGroup) {

		super(jsonObject, segmentTestClassGroup);
	}

}