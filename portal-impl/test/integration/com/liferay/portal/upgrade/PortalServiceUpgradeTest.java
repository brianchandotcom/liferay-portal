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

package com.liferay.portal.upgrade;

import aQute.bnd.version.Version;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.sql.SQLException;

import java.util.Iterator;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Alberto Chaparro
 */
public class PortalServiceUpgradeTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws SQLException {
		_currentSchemaVersion = PortalServiceUpgrade.getCurrentSchemaVersion(
			DataAccess.getUpgradeOptimizedConnection());
	}

	@Before
	public void setUp() throws SQLException {
		_innerPortalServiceUpgrade = new InnerPortalServiceUpgrade();
	}

	@After
	public void tearDown() throws SQLException {
		_innerPortalServiceUpgrade.updateSchemaVersion(_currentSchemaVersion);
	}

	@Test
	public void testGetLatestSchemaVersion() {
		Set<Version> pendingSchemaVersions =
			_innerPortalServiceUpgrade.getPendingSchemaVersions(
				_ORIGINAL_SCHEMA_VERSION);

		Iterator<Version> itr = pendingSchemaVersions.iterator();

		Version latestSchemaVersion = itr.next();

		while (itr.hasNext()) {
			latestSchemaVersion = itr.next();
		}

		Assert.assertEquals(
			latestSchemaVersion, PortalServiceUpgrade.getLatestSchemaVersion());
	}

	@Test
	public void testGetRequiredSchemaVersion() {
		Version latestSchemaVersion =
			PortalServiceUpgrade.getLatestSchemaVersion();

		Version requiredSchemaVersion =
			PortalServiceUpgrade.getRequiredSchemaVersion();

		Assert.assertEquals(
			latestSchemaVersion.getMinor(), requiredSchemaVersion.getMinor());

		Assert.assertEquals(
			latestSchemaVersion.getMajor(), requiredSchemaVersion.getMajor());
	}

	@Test
	public void testIsInLatestSchemaVersion() throws SQLException {
		_innerPortalServiceUpgrade.updateSchemaVersion(
			PortalServiceUpgrade.getLatestSchemaVersion());

		Assert.assertTrue(
			PortalServiceUpgrade.isInLatestSchemaVersion(
				DataAccess.getUpgradeOptimizedConnection()));
	}

	@Test
	public void testIsInRequiredSchemaVersion() throws SQLException {
		_innerPortalServiceUpgrade.updateSchemaVersion(
			PortalServiceUpgrade.getRequiredSchemaVersion());

		Assert.assertTrue(
			PortalServiceUpgrade.isInRequiredSchemaVersion(
				DataAccess.getUpgradeOptimizedConnection()));
	}

	@Test
	public void testIsNotInLatestSchemaVersion() throws SQLException {
		_innerPortalServiceUpgrade.updateSchemaVersion(
			_ORIGINAL_SCHEMA_VERSION);

		Assert.assertFalse(
			PortalServiceUpgrade.isInLatestSchemaVersion(
				DataAccess.getUpgradeOptimizedConnection()));
	}

	@Test
	public void testIsNotInRequiredSchemaVersion() throws SQLException {
		_innerPortalServiceUpgrade.updateSchemaVersion(
			_ORIGINAL_SCHEMA_VERSION);

		Assert.assertFalse(
			PortalServiceUpgrade.isInRequiredSchemaVersion(
				DataAccess.getUpgradeOptimizedConnection()));
	}

	@Test
	public void testUpgradeWhenCoreIsInLatestSchemaVersion()
		throws SQLException {

		_innerPortalServiceUpgrade.updateSchemaVersion(
			PortalServiceUpgrade.getLatestSchemaVersion());

		PortalServiceUpgrade portalServiceUpgrade = new PortalServiceUpgrade();

		try {
			portalServiceUpgrade.upgrade();
		}
		catch (Exception e) {
			Assert.fail("No upgrade processes should have been executed");

			return;
		}

		Assert.assertTrue(
			PortalServiceUpgrade.isInLatestSchemaVersion(
				DataAccess.getUpgradeOptimizedConnection()));
	}

	@Test
	public void testUpgradeWhenCoreIsInRequiredSchemaVersion()
		throws SQLException {

		_innerPortalServiceUpgrade.updateSchemaVersion(
			PortalServiceUpgrade.getRequiredSchemaVersion());

		PortalServiceUpgrade portalServiceUpgrade = new PortalServiceUpgrade();

		try {
			portalServiceUpgrade.upgrade();
		}
		catch (Exception e) {
			Assert.fail(
				"The execution of the upgrade process failed after being " +
					"reexecuted. Upgrade processes must be harmless if they " +
						"were executed previously.");

			return;
		}

		Assert.assertTrue(
			PortalServiceUpgrade.isInLatestSchemaVersion(
				DataAccess.getUpgradeOptimizedConnection()));
	}

	@Test
	public void testValidateCoreIsInRequiredSchemaVersion()
		throws SQLException {

		Assert.assertTrue(
			"You must first upgrade the portal to the required schema " +
				"version " + PortalServiceUpgrade.getRequiredSchemaVersion(),
			PortalServiceUpgrade.isInRequiredSchemaVersion(
				DataAccess.getUpgradeOptimizedConnection()));
	}

	private static final Version _ORIGINAL_SCHEMA_VERSION = new Version(
		"0.0.0");

	private static Version _currentSchemaVersion;

	private InnerPortalServiceUpgrade _innerPortalServiceUpgrade;

	private static class InnerPortalServiceUpgrade
		extends PortalServiceUpgrade {

		private InnerPortalServiceUpgrade() throws SQLException {
			connection = DataAccess.getUpgradeOptimizedConnection();
		}

	}

}