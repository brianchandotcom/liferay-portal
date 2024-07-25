/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_0_1.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.processor.RawMetadataProcessor;
import com.liferay.portal.events.StartupHelperUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.tools.DBUpgrader;
import com.liferay.portal.upgrade.PortalUpgradeProcess;
import com.liferay.portal.upgrade.v7_0_1.UpgradeDocumentLibrary;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.commons.lang.time.StopWatch;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alicia García
 */
@RunWith(Arquillian.class)
public class UpgradeDocumentLibraryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		_originalStopWatch = ReflectionTestUtil.getAndSetFieldValue(
			DBUpgrader.class, "_stopWatch", null);
	}

	@AfterClass
	public static void tearDownClass() {
		ReflectionTestUtil.setFieldValue(
			DBUpgrader.class, "_stopWatch", _originalStopWatch);
	}

	@Before
	public void setUp() throws Exception {
		try (Connection connection = DataAccess.getConnection()) {
			_currentSchemaVersion =
				PortalUpgradeProcess.getCurrentSchemaVersion(connection);

			PortalUpgradeProcess.updateSchemaVersion(
				connection, _ORIGINAL_SCHEMA_VERSION);

			_upgrading = StartupHelperUtil.isUpgrading();
		}
	}

	@After
	public void tearDown() throws Exception {
		try (Connection connection = DataAccess.getConnection()) {
			PortalUpgradeProcess.updateSchemaVersion(
				connection, _currentSchemaVersion);
		}
	}

	@Test
	public void testUpgrade() throws Exception {
		Connection connection = DataAccess.getConnection();

		long classNameId = _getClassNameId(
			RawMetadataProcessor.class.getName(), connection);

		try {
			StartupHelperUtil.setUpgrading(true);

			_updateClassName(_CLASS_NAME, classNameId, connection);

			Assert.assertEquals(
				_CLASS_NAME, _getValue(classNameId, connection));

			UpgradeProcess upgradeProcess = new UpgradeDocumentLibrary();

			upgradeProcess.upgrade();

			Assert.assertEquals(
				RawMetadataProcessor.class.getName(),
				_getValue(classNameId, connection));
		}
		finally {
			_updateClassName(
				RawMetadataProcessor.class.getName(), classNameId, connection);

			StartupHelperUtil.setUpgrading(_upgrading);
		}
	}

	private long _getClassNameId(String className, Connection connection)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select classNameId from ClassName_ where value = ?")) {

			preparedStatement.setString(1, className);

			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				return resultSet.getLong("classNameId");
			}

			return 0;
		}
	}

	private String _getValue(long classNameId, Connection connection)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select value from ClassName_ where classNameId = ?")) {

			preparedStatement.setLong(1, classNameId);

			ResultSet resultSet = preparedStatement.executeQuery();

			resultSet.next();

			return resultSet.getString(1);
		}
	}

	private void _updateClassName(
			String className, long classNameId, Connection connection)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update ClassName_ set value = ? where classNameId = ?")) {

			preparedStatement.setString(1, className);
			preparedStatement.setLong(2, classNameId);

			preparedStatement.executeUpdate();
		}
	}

	private static final String _CLASS_NAME =
		"com.liferay.document.library.kernel.util.RawMetadataProcessor";

	private static final Version _ORIGINAL_SCHEMA_VERSION = new Version(
		7, 0, 0);

	private static Version _currentSchemaVersion;
	private static StopWatch _originalStopWatch;

	private boolean _upgrading;

}