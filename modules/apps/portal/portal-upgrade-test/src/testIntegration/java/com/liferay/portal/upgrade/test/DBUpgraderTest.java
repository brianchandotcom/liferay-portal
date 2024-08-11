/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.concurrent.DCLSingleton;
import com.liferay.portal.events.StartupHelperUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.model.ReleaseConstants;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.tools.DBUpgrader;
import com.liferay.portal.upgrade.PortalUpgradeProcess;
import com.liferay.portal.util.PropsUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Luis Ortiz
 */
@RunWith(Arquillian.class)
public class DBUpgraderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_connection = DataAccess.getConnection();

		_currentBuildNumber = PortalUpgradeProcess.getCurrentBuildNumber(
			_connection);

		_currentState = PortalUpgradeProcess.getCurrentState(_connection);

		_upgrading = ReflectionTestUtil.getAndSetFieldValue(
			StartupHelperUtil.class, "_upgrading", true);
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		DataAccess.cleanUp(_connection);

		ReflectionTestUtil.setFieldValue(
			StartupHelperUtil.class, "_upgrading", _upgrading);
	}

	@After
	public void tearDown() throws Exception {
		_updatePortalRelease(_currentBuildNumber, _currentState);
	}

	@Test
	public void testUpgrade() throws Exception {
		_updatePortalRelease(
			ReleaseInfo.RELEASE_7_1_0_BUILD_NUMBER,
			ReleaseConstants.STATE_GOOD);

		DBUpgrader.upgradePortal();
	}

	@Test
	public void testUpgradeModuleIndexes() throws Exception {
		DB db = DBManagerUtil.getDB();

		db.runSQL("create index IX_TEST on Lock_ (createDate)");

		Boolean newRelease = ReflectionTestUtil.getAndSetFieldValue(
			StartupHelperUtil.class, "_newRelease", false);

		String upgradeDatabaseAutoRun = PropsUtil.get(
			PropsKeys.UPGRADE_DATABASE_AUTO_RUN);

		try {
			PropsUtil.set(PropsKeys.UPGRADE_DATABASE_AUTO_RUN, "false");

			DBUpgrader.upgradeModules();

			DBInspector dbInspector = new DBInspector(_connection);

			Assert.assertTrue(dbInspector.hasIndex("Lock_", "IX_TEST"));

			PropsUtil.set(PropsKeys.UPGRADE_DATABASE_AUTO_RUN, "true");

			DBUpgrader.upgradeModules();

			Assert.assertTrue(dbInspector.hasIndex("Lock_", "IX_TEST"));

			ReflectionTestUtil.setFieldValue(
				StartupHelperUtil.class, "_newRelease", true);

			DBUpgrader.upgradeModules();

			Assert.assertFalse(dbInspector.hasIndex("Lock_", "IX_TEST"));
		}
		finally {
			PropsUtil.set(
				PropsKeys.UPGRADE_DATABASE_AUTO_RUN, upgradeDatabaseAutoRun);

			ReflectionTestUtil.setFieldValue(
				StartupHelperUtil.class, "_newRelease", newRelease);
		}
	}

	@Test
	public void testUpgradeWithFailureDoesNotSupportRetry() throws Exception {
		_updatePortalRelease(
			ReleaseInfo.RELEASE_6_2_0_BUILD_NUMBER,
			ReleaseConstants.STATE_UPGRADE_FAILURE);

		try {
			DBUpgrader.upgradePortal();

			Assert.fail();
		}
		catch (IllegalStateException illegalStateException) {
		}
	}

	@Test
	public void testUpgradeWithFailureSupportsRetry() throws Exception {
		_updatePortalRelease(
			ReleaseInfo.RELEASE_7_1_0_BUILD_NUMBER,
			ReleaseConstants.STATE_UPGRADE_FAILURE);

		DBUpgrader.upgradePortal();
	}

	private void _updatePortalRelease(int buildNumber, int state)
		throws Exception {

		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"update Release_ set buildNumber = ?, state_ = ? where " +
					"releaseId = ?")) {

			preparedStatement.setInt(1, buildNumber);
			preparedStatement.setInt(2, state);
			preparedStatement.setLong(3, ReleaseConstants.DEFAULT_ID);

			preparedStatement.executeUpdate();
		}

		DCLSingleton<?> dclSingleton = ReflectionTestUtil.getFieldValue(
			PortalUpgradeProcess.class, "_currentPortalReleaseDTODCLSingleton");

		dclSingleton.destroy(null);
	}

	private static Connection _connection;
	private static int _currentBuildNumber;
	private static int _currentState;
	private static boolean _upgrading;

}