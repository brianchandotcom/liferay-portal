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

package com.liferay.portal.upgrade.internal.registry;

import com.liferay.portal.kernel.dao.db.DBProcessContext;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.osgi.framework.Version;

/**
 * @author Carlos Sierra Andrés
 */
public class UpgradeStepRegistryTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testCreateUpgradeInfos() {
		UpgradeStepRegistry upgradeStepRegistry = new UpgradeStepRegistry(0);

		TestUpgradeStep testUpgradeStep = new TestUpgradeStep();

		upgradeStepRegistry.register(
			"0.0.0", "1.0.0", testUpgradeStep, testUpgradeStep, testUpgradeStep,
			testUpgradeStep);

		List<UpgradeInfo> upgradeInfos = upgradeStepRegistry.getUpgradeInfos();

		Assert.assertEquals(upgradeInfos.toString(), 4, upgradeInfos.size());
		Assert.assertEquals(
			Arrays.asList(
				new UpgradeInfo("0.0.0", "1.0.0.step-3", 0, testUpgradeStep),
				new UpgradeInfo(
					"1.0.0.step-3", "1.0.0.step-2", 0, testUpgradeStep),
				new UpgradeInfo(
					"1.0.0.step-2", "1.0.0.step-1", 0, testUpgradeStep),
				new UpgradeInfo("1.0.0.step-1", "1.0.0", 0, testUpgradeStep)),
			upgradeInfos);

		for (UpgradeInfo upgradeInfo : upgradeInfos) {
			new Version(upgradeInfo.getFromSchemaVersionString());
			new Version(upgradeInfo.getToSchemaVersionString());
		}
	}

	@Test
	public void testCreateUpgradeInfosWithNoSteps() {
		UpgradeStepRegistry upgradeStepRegistry = new UpgradeStepRegistry(0);

		upgradeStepRegistry.register("0.0.0", "1.0.0");

		List<UpgradeInfo> upgradeInfos = upgradeStepRegistry.getUpgradeInfos();

		Assert.assertTrue(upgradeInfos.toString(), upgradeInfos.isEmpty());
	}

	@Test
	public void testCreateUpgradeInfosWithOneStep() {
		UpgradeStepRegistry upgradeStepRegistry = new UpgradeStepRegistry(0);

		TestUpgradeStep testUpgradeStep = new TestUpgradeStep();

		upgradeStepRegistry.register("0.0.0", "1.0.0", testUpgradeStep);

		List<UpgradeInfo> upgradeInfos = upgradeStepRegistry.getUpgradeInfos();

		Assert.assertEquals(upgradeInfos.toString(), 1, upgradeInfos.size());
		Assert.assertEquals(
			new UpgradeInfo("0.0.0", "1.0.0", 0, testUpgradeStep),
			upgradeInfos.get(0));
	}

	@Test
	public void testCreateUpgradeInfosWithPostUpgradeSteps() {
		_registerAndCheckPreAndPostUpgradeSteps(
			new UpgradeStep[0],
			new UpgradeStep[] {new TestUpgradeStep(), new TestUpgradeStep()});
	}

	@Test
	public void testCreateUpgradeInfosWithPreAndPostUpgradeSteps() {
		_registerAndCheckPreAndPostUpgradeSteps(
			new UpgradeStep[] {new TestUpgradeStep()},
			new UpgradeStep[] {new TestUpgradeStep()});
	}

	@Test
	public void testCreateUpgradeInfosWithPreUpgradeSteps() {
		_registerAndCheckPreAndPostUpgradeSteps(
			new UpgradeStep[] {new TestUpgradeStep(), new TestUpgradeStep()},
			new UpgradeStep[0]);
	}

	private void _registerAndCheckPreAndPostUpgradeSteps(
		UpgradeStep[] preUpgradeSteps, UpgradeStep[] postUpgradeSteps) {

		UpgradeProcess upgradeProcess = new UpgradeProcess() {

			@Override
			protected void doUpgrade() throws Exception {
			}

			@Override
			protected UpgradeStep[] getPostUpgradeSteps() {
				return postUpgradeSteps;
			}

			@Override
			protected UpgradeStep[] getPreUpgradeSteps() {
				return preUpgradeSteps;
			}

		};

		UpgradeStepRegistry upgradeStepRegistry = new UpgradeStepRegistry(0);

		upgradeStepRegistry.register("0.0.0", "1.0.0", upgradeProcess);

		UpgradeStep[] sortedUpgradeSteps = ArrayUtil.append(
			preUpgradeSteps, new UpgradeStep[] {upgradeProcess},
			postUpgradeSteps);

		List<UpgradeInfo> upgradeInfos = upgradeStepRegistry.getUpgradeInfos();

		Assert.assertEquals(upgradeInfos.toString(), 3, upgradeInfos.size());
		Assert.assertEquals(
			Arrays.asList(
				new UpgradeInfo(
					"0.0.0", "1.0.0.step-2", 0, sortedUpgradeSteps[0]),
				new UpgradeInfo(
					"1.0.0.step-2", "1.0.0.step-1", 0, sortedUpgradeSteps[1]),
				new UpgradeInfo(
					"1.0.0.step-1", "1.0.0", 0, sortedUpgradeSteps[2])),
			upgradeInfos);
	}

	private static class TestUpgradeStep implements UpgradeStep {

		@Override
		public void upgrade(DBProcessContext dbProcessContext) {
		}

	}

}