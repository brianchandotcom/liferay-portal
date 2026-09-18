/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.client.persistence.internal.upgrade.v2_0_0.test;

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
public class OAuthClientIndexedColumnSizeUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		_db = DBManagerUtil.getDB();
	}

	@Test
	public void testUpgradeASLocalMetadataIssuer() throws Exception {
		_testUpgrade(
			"issuer", "IX_328DCB29", _AS_LOCAL_METADATA_PRIMARY_KEY,
			_AS_LOCAL_METADATA);
	}

	@Test
	public void testUpgradeASLocalMetadataLocalWellKnownURI() throws Exception {
		_testUpgrade(
			"localWellKnownURI", "IX_E5878996", _AS_LOCAL_METADATA_PRIMARY_KEY,
			_AS_LOCAL_METADATA);
	}

	@Test
	public void testUpgradeASLocalMetadataOAuthASLocalWellKnownURI()
		throws Exception {

		_testUpgrade(
			"oAuthASLocalWellKnownURI", "IX_B2201FE9",
			_AS_LOCAL_METADATA_PRIMARY_KEY, _AS_LOCAL_METADATA);
	}

	@Test
	public void testUpgradeASLocalMetadataWithDuplicateUniqueIndexEntries()
		throws Exception {

		_testUpgradeWithDuplicateUniqueIndexEntries(
			"issuer", _AS_LOCAL_METADATA_PRIMARY_KEY, _AS_LOCAL_METADATA);
	}

	@Test
	public void testUpgradeEntryAuthServerWellKnownURI() throws Exception {
		_testUpgrade(
			"authServerWellKnownURI", "IX_FEC415C2", _ENTRY_PRIMARY_KEY,
			_ENTRY);
	}

	@Test
	public void testUpgradeEntryClientId() throws Exception {
		_testUpgrade("clientId", "IX_FEC415C2", _ENTRY_PRIMARY_KEY, _ENTRY);
	}

	@Test
	public void testUpgradeEntryWithDuplicateUniqueIndexEntries()
		throws Exception {

		_testUpgradeWithDuplicateUniqueIndexEntries(
			"clientId", _ENTRY_PRIMARY_KEY, _ENTRY);
	}

	@Test
	public void testUpgradePRLocalMetadataLocalWellKnownURI() throws Exception {
		_testUpgrade(
			"localWellKnownURI", "IX_E3839C6", _PR_LOCAL_METADATA_PRIMARY_KEY,
			_PR_LOCAL_METADATA);
	}

	@Test
	public void testUpgradePRLocalMetadataProtectedResourceURI()
		throws Exception {

		_testUpgrade(
			"protectedResourceURI", "IX_B54739F0",
			_PR_LOCAL_METADATA_PRIMARY_KEY, _PR_LOCAL_METADATA);
	}

	@Test
	public void testUpgradePRLocalMetadataWithDuplicateUniqueIndexEntries()
		throws Exception {

		_testUpgradeWithDuplicateUniqueIndexEntries(
			"protectedResourceURI", _PR_LOCAL_METADATA_PRIMARY_KEY,
			_PR_LOCAL_METADATA);
	}

	private void _assertColumnValue(
			String columnName, String expectedValue, long id,
			String primaryKeyColumnName, String tableName)
		throws Exception {

		try (Connection connection = DataAccess.getConnection();

			PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select ", columnName, " from ", tableName, " where ",
					primaryKeyColumnName, " = ?"))) {

			preparedStatement.setLong(1, id);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				Assert.assertTrue(resultSet.next());

				Assert.assertEquals(
					expectedValue, resultSet.getString(columnName));
			}
		}
	}

	private void _delete(long id, String primaryKeyColumnName, String tableName)
		throws Exception {

		_db.runSQL(
			StringBundler.concat(
				"delete from ", tableName, " where ", primaryKeyColumnName,
				" = ", id));
	}

	private UpgradeProcess _getUpgradeProcess() {
		return UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator,
			"com.liferay.oauth.client.persistence.internal.upgrade.v2_0_0." +
				"OAuthClientIndexedColumnSizeUpgradeProcess");
	}

	private void _insert(
			String columnName, String columnValue, long id,
			String primaryKeyColumnName, String tableName)
		throws Exception {

		_db.runSQL(
			StringBundler.concat(
				"insert into ", tableName, " (", primaryKeyColumnName, ", ",
				columnName, ") values (", id, ", '", columnValue, "')"));
	}

	private void _restore(
			String columnName, long firstId, long secondId,
			String primaryKeyColumnName, String tableName)
		throws Exception {

		_delete(firstId, primaryKeyColumnName, tableName);
		_delete(secondId, primaryKeyColumnName, tableName);

		try (Connection connection = DataAccess.getConnection()) {
			_db.alterColumnType(
				connection, tableName, columnName, "VARCHAR(255) null");
		}
	}

	private void _testUpgrade(
			String columnName, String indexName, String primaryKeyColumnName,
			String tableName)
		throws Exception {

		try (Connection connection = DataAccess.getConnection()) {
			_db.alterColumnType(
				connection, tableName, columnName, "VARCHAR(256) null");
		}

		String maxValue = RandomTestUtil.randomString(255);
		String oversizedValue = RandomTestUtil.randomString(256);

		long maxValueId = RandomTestUtil.nextLong();
		long oversizedValueId = RandomTestUtil.nextLong();

		try {
			_insert(
				columnName, maxValue, maxValueId, primaryKeyColumnName,
				tableName);
			_insert(
				columnName, oversizedValue, oversizedValueId,
				primaryKeyColumnName, tableName);

			UpgradeProcess upgradeProcess = _getUpgradeProcess();

			upgradeProcess.upgrade();

			try (Connection connection = DataAccess.getConnection()) {
				DBInspector dbInspector = new DBInspector(connection);

				Assert.assertTrue(
					dbInspector.hasColumnType(
						tableName, columnName, "VARCHAR(255) null"));
				Assert.assertTrue(dbInspector.hasIndex(tableName, indexName));
			}

			_assertColumnValue(
				columnName, maxValue, maxValueId, primaryKeyColumnName,
				tableName);
			_assertColumnValue(
				columnName, oversizedValue.substring(0, 255), oversizedValueId,
				primaryKeyColumnName, tableName);
		}
		finally {
			_restore(
				columnName, maxValueId, oversizedValueId, primaryKeyColumnName,
				tableName);
		}
	}

	private void _testUpgradeWithDuplicateUniqueIndexEntries(
			String columnName, String primaryKeyColumnName, String tableName)
		throws Exception {

		try (Connection connection = DataAccess.getConnection()) {
			_db.alterColumnType(
				connection, tableName, columnName, "VARCHAR(256) null");
		}

		String maxValue = RandomTestUtil.randomString(255);

		long maxValueId = RandomTestUtil.nextLong();
		long oversizedValueId = RandomTestUtil.nextLong();

		try {
			_insert(
				columnName, maxValue, maxValueId, primaryKeyColumnName,
				tableName);
			_insert(
				columnName, maxValue + "x", oversizedValueId,
				primaryKeyColumnName, tableName);

			UpgradeProcess upgradeProcess = _getUpgradeProcess();

			Assert.assertThrows(
				UpgradeException.class, upgradeProcess::upgrade);
		}
		finally {
			_restore(
				columnName, maxValueId, oversizedValueId, primaryKeyColumnName,
				tableName);
		}
	}

	private static final String _AS_LOCAL_METADATA =
		"OAuthClientASLocalMetadata";

	private static final String _AS_LOCAL_METADATA_PRIMARY_KEY =
		"oAuthClientASLocalMetadataId";

	private static final String _ENTRY = "OAuthClientEntry";

	private static final String _ENTRY_PRIMARY_KEY = "oAuthClientEntryId";

	private static final String _PR_LOCAL_METADATA =
		"OAuthClientPRLocalMetadata";

	private static final String _PR_LOCAL_METADATA_PRIMARY_KEY =
		"oAuthClientPRLocalMetadataId";

	private static DB _db;

	@Inject(
		filter = "component.name=com.liferay.oauth.client.persistence.internal.upgrade.registry.OAuthClientPersistenceServiceUpgradeStepRegistrator"
	)
	private UpgradeStepRegistrator _upgradeStepRegistrator;

}