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

package com.liferay.portal.upgrade.internal.graph;

import com.liferay.portal.kernel.dao.db.DatabaseProcessContext;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.upgrade.internal.UpgradeInfo;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Carlos Sierra Andrés
 * @author Miguel Pastor
 */
public class ReleaseGraphManagerTest {

	@Test
	public void testGetAutoUpgradePath() {
		UpgradeInfo upi1 = createUpgradeInfo("0.0.0", "0.1.0");
		UpgradeInfo upi2 = createUpgradeInfo("0.1.0", "0.2.0");
		UpgradeInfo upi3 = createUpgradeInfo("0.2.0", "1.0.0");
		UpgradeInfo upi4 = createUpgradeInfo("1.0.0", "2.0.0");

		ReleaseGraphManager releaseGraphManager = new ReleaseGraphManager(
			Arrays.asList(upi1, upi2, upi3, upi4));

		List<UpgradeInfo> upgradePath = releaseGraphManager.getUpgradeInfos(
			"0.0.0");

		Assert.assertEquals(Arrays.asList(upi1, upi2, upi3, upi4), upgradePath);
	}

	@Test
	public void testGetAutoUpgradePathWhenInEndNode() {
		UpgradeInfo upi1 = createUpgradeInfo("0.0.0", "0.1.0");
		UpgradeInfo upi2 = createUpgradeInfo("0.1.0", "0.2.0");
		UpgradeInfo upi3 = createUpgradeInfo("0.2.0", "1.0.0");
		UpgradeInfo upi4 = createUpgradeInfo("1.0.0", "2.0.0");
		UpgradeInfo upi5 = createUpgradeInfo("0.1.0", "0.1.0.1");
		UpgradeInfo upi6 = createUpgradeInfo("0.1.0.1", "0.1.0");

		ReleaseGraphManager releaseGraphManager = new ReleaseGraphManager(
			Arrays.asList(upi1, upi2, upi3, upi4, upi5, upi6));

		List<UpgradeInfo> upgradePath = releaseGraphManager.getUpgradeInfos(
			"0.1.0.1");

		Assert.assertEquals(Arrays.asList(upi6, upi2, upi3, upi4), upgradePath);
	}

	@Test(expected = IllegalStateException.class)
	public void testGetAutoUpgradePathWhenInEndNodeAndMultipleSinkNodes() {
		UpgradeInfo upi1 = createUpgradeInfo("0.0.0", "0.1.0");
		UpgradeInfo upi2 = createUpgradeInfo("0.1.0", "0.2.0");
		UpgradeInfo upi3 = createUpgradeInfo("0.2.0", "1.0.0");
		UpgradeInfo upi4 = createUpgradeInfo("1.0.0", "2.0.0");
		UpgradeInfo upi5 = createUpgradeInfo("0.1.0", "0.1.0.1");
		UpgradeInfo upi6 = createUpgradeInfo("0.1.0.1", "0.1.0");
		UpgradeInfo upi7 = createUpgradeInfo("0.1.0.1", "0.1.0.2");

		ReleaseGraphManager releaseGraphManager = new ReleaseGraphManager(
			Arrays.asList(upi1, upi2, upi3, upi4, upi5, upi6, upi7));

		releaseGraphManager.getUpgradeInfos("0.1.0.1");
	}

	@Test(expected = IllegalStateException.class)
	public void testGetAutoUpgradePathWhithoutEndNodes() {
		UpgradeInfo upi1 = createUpgradeInfo("0.0.0", "0.1.0");
		UpgradeInfo upi2 = createUpgradeInfo("0.1.0", "0.0.0");
		UpgradeInfo upi3 = createUpgradeInfo("0.1.0", "0.2.0");
		UpgradeInfo upi4 = createUpgradeInfo("0.2.0", "0.1.0");

		ReleaseGraphManager releaseGraphManager = new ReleaseGraphManager(
			Arrays.asList(upi1, upi2, upi3, upi4));

		releaseGraphManager.getUpgradeInfos("0.0.0");
	}

	@Test
	public void testGetSinkNodes() {
		UpgradeInfo upi1 = createUpgradeInfo("0.0.0", "0.1.0");
		UpgradeInfo upi2 = createUpgradeInfo("0.1.0", "0.2.0");
		UpgradeInfo upi3 = createUpgradeInfo("0.2.0", "1.0.0");
		UpgradeInfo upi4 = createUpgradeInfo("1.0.0", "2.0.0");

		ReleaseGraphManager releaseGraphManager = new ReleaseGraphManager(
			Arrays.asList(upi1, upi2, upi3, upi4));

		List<String> sinkNodes = releaseGraphManager.getEndVertices();

		Assert.assertTrue(sinkNodes.contains("2.0.0"));
	}

	@Test
	public void testgetSinkNodesWithMultipleEndNodes() {
		UpgradeInfo upi1 = createUpgradeInfo("0.0.0", "0.1.0");
		UpgradeInfo upi2 = createUpgradeInfo("0.1.0", "0.2.0");
		UpgradeInfo upi3 = createUpgradeInfo("0.2.0", "1.0.0");
		UpgradeInfo upi4 = createUpgradeInfo("1.0.0", "2.0.0");
		UpgradeInfo upi5 = createUpgradeInfo("1.0.0", "2.2.0");

		ReleaseGraphManager releaseGraphManager = new ReleaseGraphManager(
			Arrays.asList(upi1, upi2, upi3, upi4, upi5));

		List<String> sinkNodes = releaseGraphManager.getEndVertices();

		Assert.assertTrue(sinkNodes.contains("2.0.0"));
		Assert.assertTrue(sinkNodes.contains("2.2.0"));
	}

	@Test
	public void testGetUpgradePath() {
		UpgradeInfo upi1 = createUpgradeInfo("0.0.0", "0.1.0");
		UpgradeInfo upi2 = createUpgradeInfo("0.1.0", "0.2.0");
		UpgradeInfo upi3 = createUpgradeInfo("0.2.0", "1.0.0");
		UpgradeInfo upi4 = createUpgradeInfo("1.0.0", "2.0.0");

		ReleaseGraphManager releaseGraphManager = new ReleaseGraphManager(
			Arrays.asList(upi1, upi2, upi3, upi4));

		List<UpgradeInfo> upgradePath = releaseGraphManager.getUpgradeInfos(
			"0.0.0", "2.0.0");

		Assert.assertEquals(Arrays.asList(upi1, upi2, upi3, upi4), upgradePath);
	}

	@Test
	public void testGetUpgradePathNotInOrder() {
		UpgradeInfo upi1 = createUpgradeInfo("0.0.0", "0.1.0");
		UpgradeInfo upi2 = createUpgradeInfo("0.1.0", "0.2.0");
		UpgradeInfo upi3 = createUpgradeInfo("0.2.0", "1.0.0");
		UpgradeInfo upi4 = createUpgradeInfo("1.0.0", "2.0.0");

		ReleaseGraphManager releaseGraphManager = new ReleaseGraphManager(
			Arrays.asList(upi4, upi2, upi1, upi3));

		List<UpgradeInfo> upgradePath = releaseGraphManager.getUpgradeInfos(
			"0.0.0", "2.0.0");

		Assert.assertEquals(Arrays.asList(upi1, upi2, upi3, upi4), upgradePath);
	}

	@Test
	public void testGetUpgradePathWithCyclesReturnsShortestPath() {
		UpgradeInfo upi1 = createUpgradeInfo("0.0.0", "0.1.0");
		UpgradeInfo upi2 = createUpgradeInfo("0.1.0", "0.2.0");
		UpgradeInfo upi3 = createUpgradeInfo("0.2.0", "1.0.0");
		UpgradeInfo upi4 = createUpgradeInfo("1.0.0", "2.0.0");
		UpgradeInfo upi5 = createUpgradeInfo("0.0.0", "2.0.0");

		ReleaseGraphManager releaseGraphManager = new ReleaseGraphManager(
			Arrays.asList(upi1, upi2, upi3, upi4, upi5));

		List<UpgradeInfo> upgradePath = releaseGraphManager.getUpgradeInfos(
			"0.0.0", "2.0.0");

		Assert.assertEquals(Arrays.asList(upi5), upgradePath);
	}

	@Test
	public void testGetUpgradePathWithCyclesReturnsShortestPathWhenNotZero() {
		UpgradeInfo upi1 = createUpgradeInfo("0.0.0", "0.1.0");
		UpgradeInfo upi2 = createUpgradeInfo("0.1.0", "0.2.0");
		UpgradeInfo upi3 = createUpgradeInfo("0.2.0", "1.0.0");
		UpgradeInfo upi4 = createUpgradeInfo("1.0.0", "2.0.0");
		UpgradeInfo upi5 = createUpgradeInfo("0.0.0", "2.0.0");

		ReleaseGraphManager releaseGraphManager = new ReleaseGraphManager(
			Arrays.asList(upi1, upi2, upi3, upi4, upi5));

		List<UpgradeInfo> upgradePath = releaseGraphManager.getUpgradeInfos(
			"0.1.0", "2.0.0");

		Assert.assertEquals(Arrays.asList(upi2, upi3, upi4), upgradePath);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetUpgradePathWithIllegalArguments() {
		UpgradeInfo upi1 = createUpgradeInfo("0.0.0", "0.1.0");
		UpgradeInfo upi2 = createUpgradeInfo("0.1.0", "0.2.0");
		UpgradeInfo upi3 = createUpgradeInfo("0.2.0", "1.0.0");
		UpgradeInfo upi4 = createUpgradeInfo("1.0.0", "2.0.0");

		ReleaseGraphManager releaseGraphManager = new ReleaseGraphManager(
			Arrays.asList(upi4, upi2, upi1, upi3));

		releaseGraphManager.getUpgradeInfos("0.0.0", "2.0.1");
	}

	protected UpgradeInfo createUpgradeInfo(String from, String to) {
		return new UpgradeInfo(
			from, to, new TestUpgradeStep(from + " -> " + to));
	}

	private static class TestUpgradeStep implements UpgradeStep {

		public TestUpgradeStep(String name) {
			_name = name;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) {
				return true;
			}

			if ((o == null) || (getClass() != o.getClass())) {
				return false;
			}

			TestUpgradeStep testUpgradeProcess = (TestUpgradeStep)o;

			if (!_name.equals(testUpgradeProcess._name)) {
				return false;
			}

			return true;
		}

		@Override
		public int hashCode() {
			return _name.hashCode();
		}

		@Override
		public void upgrade(DatabaseProcessContext databaseContext)
			throws UpgradeException {
		}

		private final String _name;

	}

}