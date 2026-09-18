/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.sso.openid.connect.persistence.internal.upgrade.v3_0_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alvaro Saugar
 */
@RunWith(Arquillian.class)
public class OpenIdConnectSessionIndexedColumnSizeUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		_db = DBManagerUtil.getDB();
	}

	@Test
	public void testUpgradeAuthServerWellKnownURI() throws Exception {
		_testUpgrade("authServerWellKnownURI");
	}

	@Test
	public void testUpgradeClientId() throws Exception {
		_testUpgrade("clientId");
	}

	@Test
	public void testUpgradeWithDuplicateUniqueIndexEntries() throws Exception {
		_testUpgradeWithDuplicateUniqueIndexEntries("clientId");
	}

	private void _assertColumnValue(
			String columnName, String expectedValue, long id)
		throws Exception {

		try (Connection connection = DataAccess.getConnection();

			PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select ", columnName, " from ", _TABLE_NAME, " where ",
					_PRIMARY_KEY_COLUMN_NAME, " = ?"))) {

			preparedStatement.setLong(1, id);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				Assert.assertTrue(resultSet.next());

				Assert.assertEquals(
					expectedValue, resultSet.getString(columnName));
			}
		}
	}

	private void _delete(long... ids) throws Exception {
		for (long id : ids) {
			_db.runSQL(
				StringBundler.concat(
					"delete from ", _TABLE_NAME, " where ",
					_PRIMARY_KEY_COLUMN_NAME, " = ", id));
		}
	}

	private UpgradeProcess _getUpgradeProcess() {
		return UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator,
			"com.liferay.portal.security.sso.openid.connect.persistence." +
				"internal.upgrade.v3_0_0." +
					"OpenIdConnectSessionIndexedColumnSizeUpgradeProcess");
	}

	private void _insert(String columnName, String columnValue, long id)
		throws Exception {

		_db.runSQL(
			StringBundler.concat(
				"insert into ", _TABLE_NAME, " (", _PRIMARY_KEY_COLUMN_NAME,
				", ", columnName, ") values (", id, ", '", columnValue, "')"));
	}

	private void _restore(String columnName, long... ids) throws Exception {
		_delete(ids);

		try (Connection connection = DataAccess.getConnection()) {
			_db.alterColumnType(
				connection, _TABLE_NAME, columnName, "VARCHAR(255) null");
		}
	}

	private void _testUpgrade(String columnName) throws Exception {
		try (Connection connection = DataAccess.getConnection()) {
			_db.alterColumnType(
				connection, _TABLE_NAME, columnName, "VARCHAR(256) null");
		}

		String maxValue = RandomTestUtil.randomString(255);
		String oversizedValue = RandomTestUtil.randomString(256);

		long maxValueId = RandomTestUtil.nextLong();
		long oversizedValueId = RandomTestUtil.nextLong();

		try {
			_insert(columnName, maxValue, maxValueId);
			_insert(columnName, oversizedValue, oversizedValueId);

			UpgradeProcess upgradeProcess = _getUpgradeProcess();

			upgradeProcess.upgrade();

			try (Connection connection = DataAccess.getConnection()) {
				DBInspector dbInspector = new DBInspector(connection);

				Assert.assertTrue(
					dbInspector.hasColumnType(
						_TABLE_NAME, columnName, "VARCHAR(255) null"));
				Assert.assertTrue(
					dbInspector.hasIndex(_TABLE_NAME, _INDEX_NAME));
			}

			_assertColumnValue(columnName, maxValue, maxValueId);
			_assertColumnValue(
				columnName, oversizedValue.substring(0, 255), oversizedValueId);
		}
		finally {
			_restore(columnName, maxValueId, oversizedValueId);
		}
	}

	private void _testUpgradeWithDuplicateUniqueIndexEntries(String columnName)
		throws Exception {

		try (Connection connection = DataAccess.getConnection()) {
			_db.alterColumnType(
				connection, _TABLE_NAME, columnName, "VARCHAR(256) null");
		}

		String maxValue = RandomTestUtil.randomString(255);

		long maxValueId = RandomTestUtil.nextLong();
		long oversizedValueId = RandomTestUtil.nextLong();

		try {
			_insert(columnName, maxValue, maxValueId);
			_insert(columnName, maxValue + "x", oversizedValueId);

			UpgradeProcess upgradeProcess = _getUpgradeProcess();

			Assert.assertThrows(
				UpgradeException.class, upgradeProcess::upgrade);
		}
		finally {
			_restore(columnName, maxValueId, oversizedValueId);
		}
	}

	private static final String _INDEX_NAME = "IX_60980B41";

	private static final String _PRIMARY_KEY_COLUMN_NAME =
		"openIdConnectSessionId";

	private static final String _TABLE_NAME = "OpenIdConnectSession";

	private static DB _db;

	@Inject(
		filter = "component.name=com.liferay.portal.security.sso.openid.connect.persistence.internal.upgrade.registry.OpenIdConnectServiceUpgradeStepRegistrator"
	)
	private UpgradeStepRegistrator _upgradeStepRegistrator;

}