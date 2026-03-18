/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
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
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.data.cleanup.IllegalCharactersContentDataCleanupPreupgradeProcess;

import java.io.StringReader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.List;

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
public class IllegalCharactersContentDataCleanupPreupgradeProcessTest
	extends IllegalCharactersContentDataCleanupPreupgradeProcess {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_connection = DataAccess.getConnection();

		_db = DBManagerUtil.getDB();

		_db.alterTableAddColumn(
			_connection, "JournalArticle", "content", "TEXT null");

		_dbInspector = new DBInspector(_connection);
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		try {
			_db.runSQL(
				"delete from DDMContent where contentId = " + _contentId);

			_db.alterTableDropColumn(_connection, "JournalArticle", "content");

			_db.runSQL("delete from JournalArticle where id_ = " + _journalId);
		}
		finally {
			DataAccess.cleanUp(_connection);
		}
	}

	@Test
	public void testUpgrade() throws Exception {
		String sanitizedContent = RandomTestUtil.randomString() + "\\n";

		String content = sanitizedContent + "\\u0000";

		_contentId = RandomTestUtil.nextLong();

		try (PreparedStatement preparedStatement = _connection.prepareStatement(
				"insert into DDMContent (mvccVersion, ctCollectionId, " +
					"contentId, groupId, data_) values (0, 0, ?, ?, ?)")) {

			preparedStatement.setLong(1, _contentId);
			preparedStatement.setLong(2, RandomTestUtil.nextLong());
			preparedStatement.setString(3, content);

			preparedStatement.executeUpdate();
		}

		_journalId = RandomTestUtil.nextLong();

		try (PreparedStatement preparedStatement = _connection.prepareStatement(
				"insert into JournalArticle (mvccVersion, ctCollectionId, " +
					"id_, groupId, content) values (0, 0, ?, ?, ?)")) {

			preparedStatement.setLong(1, _journalId);
			preparedStatement.setLong(2, RandomTestUtil.nextLong());
			preparedStatement.setString(3, content);

			preparedStatement.executeUpdate();
		}

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				IllegalCharactersContentDataCleanupPreupgradeProcess.class.
					getName(),
				LoggerTestUtil.INFO)) {

			upgrade();

			List<String> messages = logCapture.getMessages();

			Assert.assertEquals(messages.toString(), 2, messages.size());
			Assert.assertTrue(
				messages.toString(),
				messages.contains(
					StringBundler.concat(
						"Table ", _dbInspector.normalizeName("DDMContent"),
						", 1 row updated column ",
						_dbInspector.normalizeName("data_"),
						" because it had invalid character sequence ",
						"\"\\u0000\"")));
			Assert.assertTrue(
				messages.toString(),
				messages.contains(
					StringBundler.concat(
						"Table ", _dbInspector.normalizeName("JournalArticle"),
						", 1 row updated column ",
						_dbInspector.normalizeName("content"),
						" because it had invalid character sequence ",
						"\"\\u0000\"")));
		}

		try (PreparedStatement preparedStatement = _connection.prepareStatement(
				"select data_ from DDMContent where contentId = ?")) {

			preparedStatement.setLong(1, _contentId);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				Assert.assertTrue(resultSet.next());

				Assert.assertEquals(
					sanitizedContent, resultSet.getString("data_"));
			}
		}

		try (PreparedStatement preparedStatement = _connection.prepareStatement(
				"select content from JournalArticle where id_ = ?")) {

			preparedStatement.setLong(1, _journalId);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				Assert.assertTrue(resultSet.next());

				Assert.assertEquals(
					sanitizedContent, resultSet.getString("content"));
			}
		}
	}

	@Test
	public void testUpgradeIllegalCharacters() throws Exception {
		int[] illegalCharacters = ReflectionTestUtil.getFieldValue(
			IllegalCharactersContentDataCleanupPreupgradeProcess.class,
			"_ILLEGAL_CHARACTER_CODES");

		DB db = DBManagerUtil.getDB();

		for (int charCode : illegalCharacters) {
			if ((charCode == 0) &&
				((db.getDBType() == DBType.DB2) ||
				 (db.getDBType() == DBType.POSTGRESQL))) {

				continue;
			}

			String sanitizedContent =
				RandomTestUtil.randomString() +
					"äëïöüÄËÏÖÜàèìòùÀÈÌÒÙáéíóúÁÉÍÓÚâêîôûÂÊÎÔÛ";

			String content = sanitizedContent + (char)charCode;

			_contentId = RandomTestUtil.nextLong();

			try (PreparedStatement preparedStatement =
					_connection.prepareStatement(
						StringBundler.concat(
							"insert into DDMContent (mvccVersion, ",
							"ctCollectionId, contentId, groupId, data_) ",
							"values (0, 0, ?, ?, ?)"))) {

				preparedStatement.setLong(1, _contentId);
				preparedStatement.setLong(2, RandomTestUtil.nextLong());
				preparedStatement.setCharacterStream(
					3, new StringReader(content), content.length());

				preparedStatement.executeUpdate();
			}

			_journalId = RandomTestUtil.nextLong();

			try (PreparedStatement preparedStatement =
					_connection.prepareStatement(
						StringBundler.concat(
							"insert into JournalArticle (mvccVersion, ",
							"ctCollectionId, id_, groupId, content) values (",
							"0, 0, ?, ?, ?)"))) {

				preparedStatement.setLong(1, _journalId);
				preparedStatement.setLong(2, RandomTestUtil.nextLong());
				preparedStatement.setCharacterStream(
					3, new StringReader(content), content.length());

				preparedStatement.executeUpdate();
			}

			try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
					IllegalCharactersContentDataCleanupPreupgradeProcess.class.
						getName(),
					LoggerTestUtil.INFO)) {

				upgrade();

				List<String> messages = logCapture.getMessages();

				Assert.assertEquals(messages.toString(), 2, messages.size());
				Assert.assertTrue(
					messages.toString(),
					messages.contains(
						StringBundler.concat(
							"Table ", _dbInspector.normalizeName("DDMContent"),
							", 1 row updated column ",
							_dbInspector.normalizeName("data_"),
							" because it had invalid character ",
							String.format("0x%02X", charCode))));
				Assert.assertTrue(
					messages.toString(),
					messages.contains(
						StringBundler.concat(
							"Table ",
							_dbInspector.normalizeName("JournalArticle"),
							", 1 row updated column ",
							_dbInspector.normalizeName("content"),
							" because it had invalid character ",
							String.format("0x%02X", charCode))));
			}

			try (PreparedStatement preparedStatement =
					_connection.prepareStatement(
						"select data_ from DDMContent where contentId = ?")) {

				preparedStatement.setLong(1, _contentId);

				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					Assert.assertTrue(resultSet.next());

					Assert.assertEquals(
						sanitizedContent, resultSet.getString("data_"));
				}
			}

			try (PreparedStatement preparedStatement =
					_connection.prepareStatement(
						"select content from JournalArticle where id_ = ?")) {

				preparedStatement.setLong(1, _journalId);

				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					Assert.assertTrue(resultSet.next());

					Assert.assertEquals(
						sanitizedContent, resultSet.getString("content"));
				}
			}
		}
	}

	private static Connection _connection;
	private static long _contentId;
	private static DB _db;
	private static DBInspector _dbInspector;
	private static long _journalId;

}