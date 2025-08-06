/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.data.cleanup.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.data.cleanup.NullUnicodeContentDataCleanupPreupgradeProcess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
public class NullUnicodeContentDataCleanupPreupgradeProcessTest
	extends NullUnicodeContentDataCleanupPreupgradeProcess {

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
	public void testUpgrade() throws Exception {
		String cleanContent = RandomTestUtil.randomString();

		String content = "'" + cleanContent + "\\u0000'";

		DB db = DBManagerUtil.getDB();

		if (db.getDBType() == DBType.SQLSERVER) {
			content = "N" + content;
		}

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				NullUnicodeContentDataCleanupPreupgradeProcess.class.getName(),
				LoggerTestUtil.INFO)) {

			connection = _connection;

			long contentId = RandomTestUtil.nextLong();

			runSQL(
				StringBundler.concat(
					"insert into DDMContent (",
					"mvccVersion, ctCollectionId, contentId, groupId, data_) ",
					"values (0, 0, ", contentId, ", ",
					RandomTestUtil.nextLong(), ", ", content, ")"));

			alterTableAddColumn("JournalArticle", "content", "TEXT null");

			long journalId = RandomTestUtil.nextLong();

			runSQL(
				StringBundler.concat(
					"insert into JournalArticle (",
					"mvccVersion, ctCollectionId, id_, groupId, content) ",
					"values (0, 0, ", journalId, ", ",
					RandomTestUtil.nextLong(), ", ", content, ")"));

			doUpgrade();

			List<String> messages = logCapture.getMessages();

			Assert.assertEquals(messages.toString(), 2, messages.size());
			Assert.assertTrue(
				messages.contains(
					StringBundler.concat(
						"Table ", _dbInspector.normalizeName("DDMContent"),
						", 1 entries updated because ",
						_dbInspector.normalizeName("data_"),
						" had invalid characters")));
			Assert.assertTrue(
				messages.contains(
					StringBundler.concat(
						"Table ", _dbInspector.normalizeName("JournalArticle"),
						", 1 entries updated because ",
						_dbInspector.normalizeName("content"),
						" had invalid characters")));

			try (PreparedStatement preparedStatement =
					_connection.prepareStatement(
						"select data_ from DDMContent where contentId = " +
							contentId);
				ResultSet resultSet = preparedStatement.executeQuery()) {

				Assert.assertTrue(resultSet.next());

				Assert.assertEquals(cleanContent, resultSet.getString(1));
			}

			try (PreparedStatement preparedStatement =
					_connection.prepareStatement(
						"select content from JournalArticle where id_ = " +
							journalId);
				ResultSet resultSet = preparedStatement.executeQuery()) {

				Assert.assertTrue(resultSet.next());

				Assert.assertEquals(cleanContent, resultSet.getString(1));
			}
		}
		finally {
			alterTableDropColumn("JournalArticle", "content");
		}
	}

	private static Connection _connection;
	private static DBInspector _dbInspector;

}