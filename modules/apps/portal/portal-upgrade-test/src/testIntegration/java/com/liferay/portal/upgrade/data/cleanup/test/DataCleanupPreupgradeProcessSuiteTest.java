/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.data.cleanup.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.concurrent.DCLSingleton;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.model.ReleaseConstants;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.upgrade.data.cleanup.DataCleanupPreupgradeException;
import com.liferay.portal.kernel.upgrade.data.cleanup.DataCleanupPreupgradeProcess;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.PortalUpgradeProcess;
import com.liferay.portal.upgrade.data.cleanup.DataCleanupPreupgradeProcessSuite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

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
 * @author Luis Ortiz
 */
@RunWith(Arquillian.class)
public class DataCleanupPreupgradeProcessSuiteTest
	extends DataCleanupPreupgradeProcessSuite {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"select schemaVersion from Release_ where releaseId = " +
					ReleaseConstants.DEFAULT_ID);
			ResultSet resultSet = preparedStatement.executeQuery()) {

			resultSet.next();

			_currentPortalSchemaVersion = resultSet.getString(1);

			_updatePortalSchemaVersion(_currentPortalSchemaVersion + ".0");
		}
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_updatePortalSchemaVersion(_currentPortalSchemaVersion);
	}

	@Before
	public void setUp() {
		_originalDataCleanupPreupgradeProcesses =
			ReflectionTestUtil.getFieldValue(
				this, "_dataCleanupPreupgradeProcesses");
	}

	@After
	public void tearDown() {
		ReflectionTestUtil.setFieldValue(
			this, "_dataCleanupPreupgradeProcesses",
			_originalDataCleanupPreupgradeProcesses);
	}

	@Test
	public void testCleanUpWithMultipleDataCleanupPreupgradeProcesses()
		throws Exception {

		List<DataCleanupPreupgradeProcess> dataCleanupPreupgradeProcesses =
			new ArrayList<>();

		dataCleanupPreupgradeProcesses.add(
			_createDataCleanupPreupgradeProcess(
				() -> _cleanUpMessages.add(_SUCCESS_MESSAGE_1)));

		dataCleanupPreupgradeProcesses.add(
			_createDataCleanupPreupgradeProcess(
				() -> _cleanUpMessages.add(_SUCCESS_MESSAGE_2)));

		ReflectionTestUtil.setFieldValue(
			this, "_dataCleanupPreupgradeProcesses",
			dataCleanupPreupgradeProcesses);

		cleanUp();

		Assert.assertEquals(
			_cleanUpMessages.toString(), 2, _cleanUpMessages.size());

		Assert.assertTrue(_cleanUpMessages.contains(_SUCCESS_MESSAGE_1));
		Assert.assertTrue(_cleanUpMessages.contains(_SUCCESS_MESSAGE_2));
	}

	@Test
	public void testCleanUpWithOneFailingDataCleanupPreupgradeProcesses() {
		List<DataCleanupPreupgradeProcess> dataCleanupPreupgradeProcesses =
			new ArrayList<>();

		dataCleanupPreupgradeProcesses.add(
			_createDataCleanupPreupgradeProcess(
				() -> _cleanUpMessages.add(_SUCCESS_MESSAGE_1)));

		dataCleanupPreupgradeProcesses.add(
			_createDataCleanupPreupgradeProcess(
				() -> {
					throw new Exception(_EXCEPTION_MESSAGE);
				}));

		dataCleanupPreupgradeProcesses.add(
			_createDataCleanupPreupgradeProcess(
				() -> _cleanUpMessages.add(_SUCCESS_MESSAGE_2)));

		ReflectionTestUtil.setFieldValue(
			this, "_dataCleanupPreupgradeProcesses",
			dataCleanupPreupgradeProcesses);

		try {
			cleanUp();

			Assert.fail();
		}
		catch (Exception exception) {
			Assert.assertTrue(
				exception instanceof DataCleanupPreupgradeException);

			String message = exception.getMessage();

			Assert.assertTrue(message.contains(_EXCEPTION_MESSAGE));
		}

		Assert.assertEquals(
			_cleanUpMessages.toString(), 1, _cleanUpMessages.size());

		Assert.assertFalse(_cleanUpMessages.contains(_SUCCESS_MESSAGE_2));
		Assert.assertTrue(_cleanUpMessages.contains(_SUCCESS_MESSAGE_1));
	}

	private static void _updatePortalSchemaVersion(String schemaVersion)
		throws Exception {

		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"update Release_ set schemaVersion = ? where releaseId = ?")) {

			preparedStatement.setString(1, schemaVersion);
			preparedStatement.setLong(2, ReleaseConstants.DEFAULT_ID);

			preparedStatement.executeUpdate();
		}

		DCLSingleton<?> dclSingleton = ReflectionTestUtil.getFieldValue(
			PortalUpgradeProcess.class, "_currentPortalReleaseDTODCLSingleton");

		dclSingleton.destroy(null);
	}

	private DataCleanupPreupgradeProcess _createDataCleanupPreupgradeProcess(
		UnsafeRunnable<Exception> unsafeRunnable) {

		return new DataCleanupPreupgradeProcess() {

			@Override
			protected void doUpgrade() throws Exception {
				unsafeRunnable.run();
			}

		};
	}

	private static final String _EXCEPTION_MESSAGE =
		RandomTestUtil.randomString();

	private static final String _SUCCESS_MESSAGE_1 =
		RandomTestUtil.randomString();

	private static final String _SUCCESS_MESSAGE_2 =
		RandomTestUtil.randomString();

	private static String _currentPortalSchemaVersion;

	private final List<String> _cleanUpMessages = new ArrayList<>();
	private List<DataCleanupPreupgradeProcess>
		_originalDataCleanupPreupgradeProcesses;

}