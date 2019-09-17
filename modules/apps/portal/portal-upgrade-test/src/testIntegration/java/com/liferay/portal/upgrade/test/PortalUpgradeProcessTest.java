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
import com.liferay.portal.kernel.test.ReflectionTestUtil;
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
	public void tearDown() throws Exception {
		_updateSchemaVersion(_currentSchemaVersion);

		_innerPortalUpgradeProcess.close();
	}

	@Test
	public void testDefineNewMajorSchemaVersion() throws Exception {
		Version previousMajorSchemaVersion = new Version(
			_currentSchemaVersion.getMajor() - 1, 0, 0);

		_updateSchemaVersion(previousMajorSchemaVersion);

		try (Connection connection = DataAccess.getConnection()) {
			Assert.assertFalse(
				"Major schema version changes require the upgrade tool " +
					"execution",
				PortalUpgradeProcess.isInRequiredSchemaVersion(connection));
		}
	}

	@Test
	public void testDefineNewMicroSchemaVersion() throws Exception {
		if (_currentSchemaVersion.getMicro() > 0) {
			Version previousMicroSchemaVersion = new Version(
				_currentSchemaVersion.getMajor(),
				_currentSchemaVersion.getMinor(),
				_currentSchemaVersion.getMicro() - 1);

			_updateSchemaVersion(previousMicroSchemaVersion);
		}

		try (Connection connection = DataAccess.getConnection()) {
			Assert.assertTrue(
				"Micro schema version changes must be optional",
				PortalUpgradeProcess.isInRequiredSchemaVersion(connection));
		}
	}

	@Test
	public void testDefineNewMinorSchemaVersion() throws Exception {
		Version previousMinorSchemaVersion = new Version(
			_currentSchemaVersion.getMajor(),
			_currentSchemaVersion.getMinor() - 1, 0);

		_updateSchemaVersion(previousMinorSchemaVersion);

		try (Connection connection = DataAccess.getConnection()) {
			Assert.assertFalse(
				"Minor schema version changes require the upgrade tool " +
					"exectution",
				PortalUpgradeProcess.isInRequiredSchemaVersion(connection));
		}
	}

	@Test
	public void testGetLatestSchemaVersion() {
		Set<Version> pendingSchemaVersions = ReflectionTestUtil.invoke(
			_innerPortalUpgradeProcess, "getPendingSchemaVersions",
			new Class<?>[] {Version.class}, _ORIGINAL_SCHEMA_VERSION);

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
	public void testIsInLatestSchemaVersion() throws Exception {
		_updateSchemaVersion(PortalUpgradeProcess.getLatestSchemaVersion());

		try (Connection connection = DataAccess.getConnection()) {
			Assert.assertTrue(
				PortalUpgradeProcess.isInLatestSchemaVersion(connection));
		}
	}

	@Test
	public void testIsInRequiredSchemaVersion() throws Exception {
		_updateSchemaVersion(PortalUpgradeProcess.getRequiredSchemaVersion());

		try (Connection connection = DataAccess.getConnection()) {
			Assert.assertTrue(
				PortalUpgradeProcess.isInRequiredSchemaVersion(connection));
		}
	}

	@Test
	public void testIsNotInLatestSchemaVersion() throws Exception {
		_updateSchemaVersion(_ORIGINAL_SCHEMA_VERSION);

		try (Connection connection = DataAccess.getConnection()) {
			Assert.assertFalse(
				PortalUpgradeProcess.isInLatestSchemaVersion(connection));
		}
	}

	@Test
	public void testRevertCodeToPreviousMajorSchemaVersion() throws Exception {
		Version nextMajorSchemaVersion = new Version(
			_currentSchemaVersion.getMajor() + 1, 0, 0);

		_updateSchemaVersion(nextMajorSchemaVersion);

		try (Connection connection = DataAccess.getConnection()) {
			Assert.assertFalse(
				"Major schema version changes must be nonrevertible",
				PortalUpgradeProcess.isInRequiredSchemaVersion(connection));
		}
	}

	@Test
	public void testRevertCodeToPreviousMicroSchemaVersion() throws Exception {
		Version nextMicroSchemaVersion = new Version(
			_currentSchemaVersion.getMajor(), _currentSchemaVersion.getMinor(),
			_currentSchemaVersion.getMicro() + 1);

		_updateSchemaVersion(nextMicroSchemaVersion);

		try (Connection connection = DataAccess.getConnection()) {
			Assert.assertTrue(
				"Micro schema version changes must be revertible",
				PortalUpgradeProcess.isInRequiredSchemaVersion(connection));
		}
	}

	@Test
	public void testRevertCodeToPreviousMinorSchemaVersion() throws Exception {
		Version nextMinorSchemaVersion = new Version(
			_currentSchemaVersion.getMajor(),
			_currentSchemaVersion.getMinor() + 1, 0);

		_updateSchemaVersion(nextMinorSchemaVersion);

		try (Connection connection = DataAccess.getConnection()) {
			Assert.assertTrue(
				"Minor schema version changes must be revertible",
				PortalUpgradeProcess.isInRequiredSchemaVersion(connection));
		}
	}

	@Test
	public void testUpgradeWhenCoreIsInLatestSchemaVersion() throws Exception {
		_updateSchemaVersion(PortalUpgradeProcess.getLatestSchemaVersion());

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
		throws Exception {

		_updateSchemaVersion(PortalUpgradeProcess.getRequiredSchemaVersion());

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

	private void _updateSchemaVersion(Version version) {
		ReflectionTestUtil.invoke(
			_innerPortalUpgradeProcess, "updateSchemaVersion",
			new Class<?>[] {Version.class}, version);
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