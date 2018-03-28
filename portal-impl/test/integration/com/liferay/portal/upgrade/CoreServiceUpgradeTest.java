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

import java.sql.Connection;
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
public class CoreServiceUpgradeTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_currentSchemaVersion = CoreServiceUpgrade.getCurrentSchemaVersion();
	}

	@Before
	public void setUp() throws Exception {
		_innerCoreServiceUpgrade = new InnerCoreServiceUpgrade();
	}

	@After
	public void tearDown() throws Exception {
		_innerCoreServiceUpgrade.updateSchemaVersion(_currentSchemaVersion);
	}

	@Test
	public void testGetLatestSchemaVersion() throws Exception {
		Set<Version> pendingSchemaVersions =
			_innerCoreServiceUpgrade.getPendingSchemaVersions(
				_ORIGINAL_SCHEMA_VERSION);

		Iterator<Version> itr = pendingSchemaVersions.iterator();

		Version latestSchemaVersion = itr.next();

		while (itr.hasNext()) {
			latestSchemaVersion = itr.next();
		}

		Assert.assertEquals(
			latestSchemaVersion, CoreServiceUpgrade.getLatestSchemaVersion());
	}

	@Test
	public void testGetRequiredSchemaVersion() throws Exception {
		Version latestSchemaVersion =
			CoreServiceUpgrade.getLatestSchemaVersion();

		Version requiredSchemaVersion =
			CoreServiceUpgrade.getRequiredSchemaVersion();

		Assert.assertEquals(
			latestSchemaVersion.getMinor(), requiredSchemaVersion.getMinor());

		Assert.assertEquals(
			latestSchemaVersion.getMajor(), requiredSchemaVersion.getMajor());
	}

	@Test
	public void testIsInLatestSchemaVersion() throws Exception {
		_innerCoreServiceUpgrade.updateSchemaVersion(
			CoreServiceUpgrade.getLatestSchemaVersion());

		Assert.assertTrue(CoreServiceUpgrade.isInLatestSchemaVersion());
	}

	@Test
	public void testIsInRequiredSchemaVersion() throws Exception {
		_innerCoreServiceUpgrade.updateSchemaVersion(
			CoreServiceUpgrade.getRequiredSchemaVersion());

		Assert.assertTrue(CoreServiceUpgrade.isInRequiredSchemaVersion());
	}

	@Test
	public void testIsNotInLatestSchemaVersion() throws Exception {
		_innerCoreServiceUpgrade.updateSchemaVersion(_ORIGINAL_SCHEMA_VERSION);

		Assert.assertFalse(CoreServiceUpgrade.isInLatestSchemaVersion());
	}

	@Test
	public void testIsNotInRequiredSchemaVersion() throws Exception {
		_innerCoreServiceUpgrade.updateSchemaVersion(_ORIGINAL_SCHEMA_VERSION);

		Assert.assertFalse(CoreServiceUpgrade.isInRequiredSchemaVersion());
	}

	@Test
	public void testUpgradeWhenCoreIsInLatestSchemaVersion() throws Exception {
		_innerCoreServiceUpgrade.updateSchemaVersion(
			CoreServiceUpgrade.getLatestSchemaVersion());

		CoreServiceUpgrade coreServiceUpgrade = new CoreServiceUpgrade();

		try {
			coreServiceUpgrade.doUpgrade();
		}
		catch (Exception e) {
			Assert.fail("No upgrade processes should have been executed");

			return;
		}

		Assert.assertTrue(CoreServiceUpgrade.isInLatestSchemaVersion());
	}

	@Test
	public void testUpgradeWhenCoreIsInRequiredSchemaVersion()
		throws Exception {

		_innerCoreServiceUpgrade.updateSchemaVersion(
			CoreServiceUpgrade.getRequiredSchemaVersion());

		CoreServiceUpgrade coreServiceUpgrade = new CoreServiceUpgrade();

		try {
			coreServiceUpgrade.upgrade();
		}
		catch (Exception e) {
			Assert.fail(
				"The execution of the upgrade process failed after being " +
					"re-executed. Upgrade processes must be harmless if they " +
						"were executed previously");

			return;
		}

		Assert.assertTrue(CoreServiceUpgrade.isInLatestSchemaVersion());
	}

	@Test
	public void testValidateCoreIsInRequiredSchemaVersion() throws Exception {
		Assert.assertTrue(
			"You must first upgrade the Core to the required Schema Version " +
				CoreServiceUpgrade.getRequiredSchemaVersion(),
			CoreServiceUpgrade.isInRequiredSchemaVersion());
	}

	public class InnerCoreServiceUpgrade extends CoreServiceUpgrade {

		public InnerCoreServiceUpgrade() throws Exception {
		}

		public Set<Version> getPendingSchemaVersions(
			Version fromSchemaVersion) {

			return super.getPendingSchemaVersions(fromSchemaVersion);
		}

		public void updateSchemaVersion(Version newSchemaVersion)
			throws SQLException {

			try (Connection con = DataAccess.getUpgradeOptimizedConnection()) {
				connection = con;

				super.updateSchemaVersion(newSchemaVersion);
			}
			finally {
				connection = null;
			}
		}

	}

	private static final Version _ORIGINAL_SCHEMA_VERSION = new Version(
		"0.0.0");

	private static Version _currentSchemaVersion;
	private static InnerCoreServiceUpgrade _innerCoreServiceUpgrade;

}