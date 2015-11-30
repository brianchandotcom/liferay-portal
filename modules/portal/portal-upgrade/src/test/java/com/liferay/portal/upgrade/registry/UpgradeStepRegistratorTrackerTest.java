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

package com.liferay.portal.upgrade.registry;

import com.liferay.portal.kernel.dao.db.DBProcessContext;
import com.liferay.portal.kernel.upgrade.UpgradeStep;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Carlos Sierra Andrés
 */
public class UpgradeStepRegistratorTrackerTest {

	@Test
	public void testCreateUpgradeInfos() {
		TestUpgradeStep testUpgradeStep = new TestUpgradeStep();

		List<UpgradeInfo> upgradeInfos =
			UpgradeStepRegistratorTracker.createUpgradeInfos(
				"0.0.0", "1.0.0", testUpgradeStep, testUpgradeStep,
				testUpgradeStep, testUpgradeStep);

		Assert.assertEquals(4, upgradeInfos.size());
		Assert.assertEquals(
			Arrays.asList(
				new UpgradeInfo("0.0.0", "1.0.0-step-3", testUpgradeStep),
				new UpgradeInfo(
					"1.0.0-step-3", "1.0.0-step-2", testUpgradeStep),
				new UpgradeInfo(
					"1.0.0-step-2", "1.0.0-step-1", testUpgradeStep),
				new UpgradeInfo("1.0.0-step-1", "1.0.0", testUpgradeStep)),
			upgradeInfos);
	}

	@Test
	public void testCreateUpgradeInfosWithNoSteps() {
		List<UpgradeInfo> upgradeInfos =
			UpgradeStepRegistratorTracker.createUpgradeInfos("0.0.0", "1.0.0");

		Assert.assertTrue(upgradeInfos.isEmpty());
	}

	@Test
	public void testCreateUpgradeInfosWithOneStep() {
		TestUpgradeStep testUpgradeStep = new TestUpgradeStep();

		List<UpgradeInfo> upgradeInfos =
			UpgradeStepRegistratorTracker.createUpgradeInfos(
				"0.0.0", "1.0.0", testUpgradeStep);

		Assert.assertEquals(1, upgradeInfos.size());
		Assert.assertEquals(
			new UpgradeInfo("0.0.0", "1.0.0", testUpgradeStep),
			upgradeInfos.get(0));
	}

	private static class TestUpgradeStep implements UpgradeStep {

		@Override
		public void upgrade(DBProcessContext dbProcessContext) {
		}

	}

}