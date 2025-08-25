/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.data.cleanup.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.upgrade.data.cleanup.util.OrphanReferencesDataCleanupUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.data.cleanup.DDMStructureDataCleanupPreupgradeProcess;

import java.sql.Connection;

import java.util.List;

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
public class DDMStructureDataCleanupPreupgradeProcessTest
	extends DDMStructureDataCleanupPreupgradeProcess {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_connection = DataAccess.getConnection();

		_dbInspector = new DBInspector(_connection);
	}

	@Test
	public void testUpgradeFrom62() throws Exception {
		connection = _connection;

		try {
			alterTableAddColumn(
				"JournalArticle", "structureId", "VARCHAR(75) null");
			alterTableAddColumn(
				"JournalFeed", "structureId", "VARCHAR(75) null");

			String structureId = RandomTestUtil.randomString();

			_test(
				() -> {
					runSQL(
						StringBundler.concat(
							"insert into JournalArticle (",
							"mvccVersion, ctCollectionId, id_, groupId, ",
							"structureId) values (0, 0, ",
							RandomTestUtil.nextLong(), ", ",
							RandomTestUtil.nextLong(), ", '", structureId,
							"')"));
					runSQL(
						StringBundler.concat(
							"insert into JournalFeed (",
							"mvccVersion, ctCollectionId, id_, groupId, ",
							"structureId) values (0, 0, ",
							RandomTestUtil.nextLong(), ", ",
							RandomTestUtil.nextLong(), ", '", structureId,
							"')"));
				},
				messages -> {
					Assert.assertTrue(
						messages.contains(
							_getExpectedMessage(
								1, "structureId", "JournalArticle",
								"structureKey", "DDMStructure", structureId)));
					Assert.assertTrue(
						messages.contains(
							_getExpectedMessage(
								1, "structureId", "JournalFeed", "structureKey",
								"DDMStructure", structureId)));
				});
		}
		finally {
			alterTableDropColumn("JournalArticle", "structureId");
			alterTableDropColumn("JournalFeed", "structureId");
		}
	}

	@Test
	public void testUpgradeFrom70to73() throws Exception {
		connection = _connection;

		try {
			alterTableAddColumn(
				"JournalArticle", "DDMStructureKey", "VARCHAR(75) null");
			alterTableAddColumn(
				"JournalFeed", "DDMStructureKey", "VARCHAR(75) null");

			String structureId = RandomTestUtil.randomString();

			_test(
				() -> {
					runSQL(
						StringBundler.concat(
							"insert into JournalArticle (",
							"mvccVersion, ctCollectionId, id_, groupId, ",
							"DDMStructureKey) values (0, 0, ",
							RandomTestUtil.nextLong(), ", ",
							RandomTestUtil.nextLong(), ", '", structureId,
							"')"));
					runSQL(
						StringBundler.concat(
							"insert into JournalFeed (",
							"mvccVersion, ctCollectionId, id_, groupId, ",
							"DDMStructureKey) values (0, 0, ",
							RandomTestUtil.nextLong(), ", ",
							RandomTestUtil.nextLong(), ", '", structureId,
							"')"));
				},
				messages -> {
					Assert.assertTrue(
						messages.contains(
							_getExpectedMessage(
								1, "DDMStructureKey", "JournalArticle",
								"structureKey", "DDMStructure", structureId)));
					Assert.assertTrue(
						messages.contains(
							_getExpectedMessage(
								1, "DDMStructureKey", "JournalFeed",
								"structureKey", "DDMStructure", structureId)));
				});
		}
		finally {
			alterTableDropColumn("JournalArticle", "structureId");
			alterTableDropColumn("JournalFeed", "structureId");
		}
	}

	@Test
	public void testUpgradeFrom74() throws Exception {
		long structureId = RandomTestUtil.nextLong();

		_test(
			() -> {
				runSQL(
					StringBundler.concat(
						"insert into JournalArticle (",
						"mvccVersion, ctCollectionId, id_, groupId, ",
						"DDMStructureId) values (0, 0, ",
						RandomTestUtil.nextLong(), ", ",
						RandomTestUtil.nextLong(), ", ", structureId, ")"));
				runSQL(
					StringBundler.concat(
						"insert into JournalFeed (",
						"mvccVersion, ctCollectionId, id_, groupId, ",
						"DDMStructureId) values (0, 0, ",
						RandomTestUtil.nextLong(), ", ",
						RandomTestUtil.nextLong(), ", ", structureId, ")"));
			},
			messages -> {
				Assert.assertTrue(
					messages.contains(
						_getExpectedMessage(
							1, "DDMStructureId", "JournalArticle",
							"structureId", "DDMStructure", structureId)));
				Assert.assertTrue(
					messages.contains(
						_getExpectedMessage(
							1, "DDMStructureId", "JournalFeed", "structureId",
							"DDMStructure", structureId)));
			});
	}

	private String _getExpectedMessage(
			long count, String sourceColumnName, String sourceTableName,
			String targetColumnName, String targetTableName, Object targetValue)
		throws Exception {

		return StringBundler.concat(
			"Table ", _dbInspector.normalizeName(sourceTableName), ", ", count,
			(count == 1) ? " row " : " rows ", "deleted because ",
			_dbInspector.normalizeName(sourceColumnName), StringPool.SPACE,
			targetValue, " was not found in ",
			_dbInspector.normalizeName(targetTableName), StringPool.PERIOD,
			_dbInspector.normalizeName(targetColumnName));
	}

	private void _test(
			UnsafeRunnable<Exception> preupgradeUnsafeRunnable,
			UnsafeConsumer<List<String>, Exception> verifyUnsafeConsumer)
		throws Exception {

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				OrphanReferencesDataCleanupUtil.class.getName(),
				LoggerTestUtil.INFO)) {

			preupgradeUnsafeRunnable.run();

			doUpgrade();

			verifyUnsafeConsumer.accept(logCapture.getMessages());
		}
	}

	private static Connection _connection;
	private static DBInspector _dbInspector;

}