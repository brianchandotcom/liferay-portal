/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.concurrent.DCLSingleton;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.db.DBResourceUtil;
import com.liferay.portal.db.index.IndexUpdaterUtil;
import com.liferay.portal.events.StartupHelperUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.model.ReleaseConstants;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.tools.DBUpgrader;
import com.liferay.portal.upgrade.PortalUpgradeProcess;
import com.liferay.portal.util.PropsUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.BundleTracker;

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
		try (Connection connection = DataAccess.getConnection()) {
			_currentBuildNumber = PortalUpgradeProcess.getCurrentBuildNumber(
				connection);

			_currentState = PortalUpgradeProcess.getCurrentState(connection);
		}

		_upgrading = ReflectionTestUtil.getAndSetFieldValue(
			StartupHelperUtil.class, "_upgrading", true);

		Bundle bundle = FrameworkUtil.getBundle(DBUpgraderTest.class);

		CountDownLatch countDownLatch = new CountDownLatch(1);

		BundleTracker<Bundle> bundleTracker = new BundleTracker<Bundle>(
			bundle.getBundleContext(), Bundle.ACTIVE, null) {

			@Override
			public Bundle addingBundle(Bundle bundle, BundleEvent event) {
				String symbolicName = bundle.getSymbolicName();

				if (symbolicName.equals("com.liferay.portal.lock.service")) {
					_moduleBundle = bundle;

					countDownLatch.countDown();

					close();
				}

				return null;
			}

		};

		bundleTracker.open();

		_connection = DataAccess.getConnection();

		_db = DBManagerUtil.getDB();

		_dbInspector = new DBInspector(_connection);

		_processedServletContextNames = ReflectionTestUtil.getFieldValue(
			IndexUpdaterUtil.class, "_processedServletContextNames");

		countDownLatch.await(10, TimeUnit.SECONDS);

		_initIndexNames();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		ReflectionTestUtil.setFieldValue(
			StartupHelperUtil.class, "_upgrading", _upgrading);
		DataAccess.cleanUp(_connection);
	}

	@After
	public void tearDown() throws Exception {
		_updatePortalRelease(_currentBuildNumber, _currentState);
		_processedServletContextNames.clear();
	}

	@Test
	public void testRegenerateModuleIndexes() throws Exception {
		_dropIndex(_moduleTableIndexName, _moduleIndexName);

		PropsUtil.set("upgrade.database.auto.run", "false");

		DBUpgrader.upgradeModules(false);

		Assert.assertFalse(
			_dbInspector.hasIndex(_moduleTableIndexName, _moduleIndexName));

		PropsUtil.set("upgrade.database.auto.run", "true");

		DBUpgrader.upgradeModules(false);

		Assert.assertTrue(
			_dbInspector.hasIndex(_moduleTableIndexName, _moduleIndexName));
	}

	@Test
	public void testUpgrade() throws Exception {
		_updatePortalRelease(
			ReleaseInfo.RELEASE_7_1_0_BUILD_NUMBER,
			ReleaseConstants.STATE_GOOD);

		DBUpgrader.upgradePortal();
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

	private static String _getIndexName(String indexesSQL) {
		return indexesSQL.substring(
			indexesSQL.indexOf("IX_"), indexesSQL.indexOf(" on"));
	}

	private static String _getTableIndexName(String indexesSQL) {
		return indexesSQL.substring(
			indexesSQL.indexOf("on ") + 3, indexesSQL.indexOf(" ("));
	}

	private static void _initIndexNames() throws Exception {
		String moduleIndexesSQL = DBResourceUtil.getModuleIndexesSQL(
			_moduleBundle);

		_moduleIndexName = _getIndexName(moduleIndexesSQL);
		_moduleTableIndexName = _getTableIndexName(moduleIndexesSQL);
	}

	private void _dropIndex(String tableName, String indexName)
		throws Exception {

		_db.runSQL(
			StringBundler.concat("drop index ", indexName, " on ", tableName));
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
	private static DB _db;
	private static DBInspector _dbInspector;
	private static Bundle _moduleBundle;
	private static String _moduleIndexName;
	private static String _moduleTableIndexName;
	private static Set<String> _processedServletContextNames;
	private static boolean _upgrading;

}