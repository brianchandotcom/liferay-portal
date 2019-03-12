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

package com.liferay.portal.upgrade.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.PortalUpgradeProcess;

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
import org.junit.runner.RunWith;

/**
 * @author Alberto Chaparro
 */
@RunWith(Arquillian.class)
public class PortalUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws SQLException {
		try (Connection connection = DataAccess.getConnection()) {
			_currentSchemaVersion =
				PortalUpgradeProcess.getCurrentSchemaVersion(connection);
		}
	}

	@Before
	public void setUp() throws SQLException {
		_innerPortalUpgradeProcess = new InnerPortalUpgradeProcess();
	}

	@After
	public void tearDown() throws SQLException {
		_innerPortalUpgradeProcess.updateSchemaVersion(_currentSchemaVersion);

		_innerPortalUpgradeProcess.close();
	}

	@Test
	public void testGetLatestSchemaVersion() {
		Set<Version> pendingSchemaVersions =
			_innerPortalUpgradeProcess.getPendingSchemaVersions(
				_ORIGINAL_SCHEMA_VERSION);

		Iterator<Version> itr = pendingSchemaVersions.iterator();

		Version latestSchemaVersion = itr.next();

		while (itr.hasNext()) {
			latestSchemaVersion = itr.next();
		}

		Assert.assertEquals(
			latestSchemaVersion, PortalUpgradeProcess.getLatestSchemaVersion());
	}

	@Test
	public void testGetRequiredSchemaVersion() {
		Version latestSchemaVersion =
			PortalUpgradeProcess.getLatestSchemaVersion();

		Version requiredSchemaVersion =
			PortalUpgradeProcess.getRequiredSchemaVersion();

		Assert.assertEquals(
			latestSchemaVersion.getMinor(), requiredSchemaVersion.getMinor());

		Assert.assertEquals(
			latestSchemaVersion.getMajor(), requiredSchemaVersion.getMajor());
	}

	@Test
	public void testIsInLatestSchemaVersion() throws SQLException {
		_innerPortalUpgradeProcess.updateSchemaVersion(
			PortalUpgradeProcess.getLatestSchemaVersion());

		try (Connection connection = DataAccess.getConnection()) {
			Assert.assertTrue(
				PortalUpgradeProcess.isInLatestSchemaVersion(connection));
		}
	}

	@Test
	public void testIsInRequiredSchemaVersion() throws SQLException {
		_innerPortalUpgradeProcess.updateSchemaVersion(
			PortalUpgradeProcess.getRequiredSchemaVersion());

		try (Connection connection = DataAccess.getConnection()) {
			Assert.assertTrue(
				PortalUpgradeProcess.isInRequiredSchemaVersion(connection));
		}
	}

	@Test
	public void testIsNotInLatestSchemaVersion() throws SQLException {
		_innerPortalUpgradeProcess.updateSchemaVersion(
			_ORIGINAL_SCHEMA_VERSION);

		try (Connection connection = DataAccess.getConnection()) {
			Assert.assertFalse(
				PortalUpgradeProcess.isInLatestSchemaVersion(connection));
		}
	}

	@Test
	public void testIsNotInRequiredSchemaVersion() throws SQLException {
		_innerPortalUpgradeProcess.updateSchemaVersion(
			_ORIGINAL_SCHEMA_VERSION);

		try (Connection connection = DataAccess.getConnection()) {
			Assert.assertFalse(
				PortalUpgradeProcess.isInRequiredSchemaVersion(connection));
		}
	}

	@Test
	public void testUpgradeWhenCoreIsInLatestSchemaVersion()
		throws SQLException {

		_innerPortalUpgradeProcess.updateSchemaVersion(
			PortalUpgradeProcess.getLatestSchemaVersion());

		PortalUpgradeProcess portalServiceUpgrade = new PortalUpgradeProcess();

		try {
			portalServiceUpgrade.upgrade();
		}
		catch (Exception e) {
			throw new SQLException(
				"No upgrade processes should have been executed", e);
		}

		try (Connection connection = DataAccess.getConnection()) {
			Assert.assertTrue(
				PortalUpgradeProcess.isInLatestSchemaVersion(connection));
		}
	}

	@Test
	public void testUpgradeWhenCoreIsInRequiredSchemaVersion()
		throws SQLException {

		_innerPortalUpgradeProcess.updateSchemaVersion(
			PortalUpgradeProcess.getRequiredSchemaVersion());

		PortalUpgradeProcess portalServiceUpgrade = new PortalUpgradeProcess();

		try {
			portalServiceUpgrade.upgrade();
		}
		catch (Exception e) {
			throw new SQLException(
				"The execution of the upgrade process failed after being " +
					"reexecuted. Upgrade processes must be harmless if they " +
						"were executed previously.",
				e);
		}

		try (Connection connection = DataAccess.getConnection()) {
			Assert.assertTrue(
				PortalUpgradeProcess.isInLatestSchemaVersion(connection));
		}
	}

	@Test
	public void testValidateCoreIsInRequiredSchemaVersion()
		throws SQLException {

		try (Connection connection = DataAccess.getConnection()) {
			Assert.assertTrue(
				"You must first upgrade the portal to the required schema " +
					"version " +
						PortalUpgradeProcess.getRequiredSchemaVersion(),
				PortalUpgradeProcess.isInRequiredSchemaVersion(connection));
		}
	}

	private static final Version _ORIGINAL_SCHEMA_VERSION = new Version(
		0, 0, 0);

	private static Version _currentSchemaVersion;

	private InnerPortalUpgradeProcess _innerPortalUpgradeProcess;

	private static class InnerPortalUpgradeProcess
		extends PortalUpgradeProcess {

		public void close() throws SQLException {
			connection.close();
		}

		private InnerPortalUpgradeProcess() throws SQLException {
			connection = DataAccess.getConnection();
		}

	}

}