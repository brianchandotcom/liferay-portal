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

package com.liferay.portal.dao.orm.common;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.InputStream;

import java.nio.CharBuffer;

import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Daniel Sanz
 */
public class SQLTransformerCastClobTextOracleTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), TransactionalTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		_db = DBFactoryUtil.getDB();

		String dbType = _db.getType();

		if (dbType.equals(DB.TYPE_ORACLE)) {
			_db.runSQL(_SQL_CREATE_TABLE);

			try (Connection connection = DataAccess.getConnection();
				PreparedStatement preparedStatement =
					connection.prepareStatement(
						"INSERT INTO TestCastClobText VALUES (?, ?)")) {

				preparedStatement.setLong(1, 1);
				preparedStatement.setClob(
					2, new UnsyncStringReader(_BIG_TEXT_A_3999));

				Assert.assertEquals(1, preparedStatement.executeUpdate());

				preparedStatement.setLong(1, 2);
				preparedStatement.setClob(
					2, new UnsyncStringReader(_BIG_TEXT_A_4000));

				Assert.assertEquals(1, preparedStatement.executeUpdate());

				preparedStatement.setLong(1, 3);
				preparedStatement.setClob(
					2, new UnsyncStringReader(_BIG_TEXT_A_4001));

				Assert.assertEquals(1, preparedStatement.executeUpdate());

				preparedStatement.setLong(1, 4);
				preparedStatement.setClob(
					2, new UnsyncStringReader(_BIG_TEXT_A_3999_B_1));

				Assert.assertEquals(1, preparedStatement.executeUpdate());

				preparedStatement.setLong(1, 5);
				preparedStatement.setClob(
					2, new UnsyncStringReader(_BIG_TEXT_A_3999_B_2));

				Assert.assertEquals(1, preparedStatement.executeUpdate());

				preparedStatement.setLong(1, 6);
				preparedStatement.setClob(
					2, new UnsyncStringReader(_BIG_TEXT_A_4000_B_1));

				Assert.assertEquals(1, preparedStatement.executeUpdate());
			}
		}
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		String dbType = _db.getType();

		if (dbType.equals(DB.TYPE_ORACLE)) {
			_db.runSQL(_SQL_DROP_TABLE);
		}
	}

	@Before
	public void setUp() {
		String dbType = _db.getType();

		Assume.assumeTrue(dbType.equals(DB.TYPE_ORACLE));
	}

	@Test
	public void testSelectBigText_3999() throws Exception {
		Assert.assertEquals(
			Arrays.asList(_BIG_TEXT_A_3999), runSelect(_BIG_TEXT_A_3999));
	}

	@Test
	public void testSelectBigText_4000() throws Exception {

		// those match all rows sharing the first 4000 characters
		// as CAST_CLOB_TEXT truncates data prior to comparison

		Assert.assertEquals(
			Arrays.asList(
				_BIG_TEXT_A_4000, _BIG_TEXT_A_4001, _BIG_TEXT_A_4000_B_1),
			runSelect(_BIG_TEXT_A_4000));
		Assert.assertEquals(
			Arrays.asList(_BIG_TEXT_A_3999_B_1, _BIG_TEXT_A_3999_B_2),
			runSelect(_BIG_TEXT_A_3999_B_1));
	}

	@Test
	public void testSelectBigText_4001() throws Exception {

		// those match nothing as CAST_CLOB_TEXT truncates data prior to
		// comparison. Note this is intended behavior

		// selects where data = _BIG_TEXT_A_4001

		List<String> list = runSelect(_BIG_TEXT_A_4000, String.valueOf(_A));

		Assert.assertTrue(list.toString(), list.isEmpty());

		// selects where data = _BIG_TEXT_A_3999_BB

		list = runSelect(_BIG_TEXT_A_3999_B_1, String.valueOf(_B));

		Assert.assertTrue(list.toString(), list.isEmpty());

		// selects where data = _BIG_TEXT_A_4000_B

		list = runSelect(_BIG_TEXT_A_4000, String.valueOf(_B));

		Assert.assertTrue(list.toString(), list.isEmpty());
	}

	@Test
	public void testSelectText_1() throws Exception {

		// matches nothing

		List<String> list = runSelect(String.valueOf(_A));

		Assert.assertTrue(list.toString(), list.isEmpty());

		list = runSelect(String.valueOf(_B));

		Assert.assertTrue(list.toString(), list.isEmpty());
	}

	private List<String> runSelect(String data) throws Exception {
		return runSelect(data, StringPool.BLANK);
	}

	private List<String> runSelect(String data1, String data2)
		throws Exception {

		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				SQLTransformer.transform(_SQL_SELECT_COMPARE_STRINGS))) {

			Clob clob1 = connection.createClob();

			clob1.setString(1, data1);

			preparedStatement.setClob(1, clob1);

			Clob clob2 = connection.createClob();

			clob2.setString(1, data2);

			preparedStatement.setClob(2, clob2);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				List<String> dataStrings = new ArrayList<>();

				while (resultSet.next()) {
					Clob clob = resultSet.getClob(1);

					try (InputStream inputStream = clob.getAsciiStream()) {
						dataStrings.add(StringUtil.read(inputStream));
					}
				}

				return dataStrings;
			}
		}
	}

	private static final Character _A = 'a';

	private static final Character _B = 'b';

	private static final String _BIG_TEXT_A_3999 = CharBuffer.allocate(
		3999).toString().replace('\0', _A);

	private static final String _BIG_TEXT_A_3999_B_1 = _BIG_TEXT_A_3999 + _B;

	private static final String _BIG_TEXT_A_3999_B_2 =
		_BIG_TEXT_A_3999_B_1 + _B;

	private static final String _BIG_TEXT_A_4000 = _BIG_TEXT_A_3999 + _A;

	private static final String _BIG_TEXT_A_4000_B_1 = _BIG_TEXT_A_4000 + _B;

	private static final String _BIG_TEXT_A_4001 = _BIG_TEXT_A_4000 + _A;

	private static final String _SQL_CREATE_TABLE =
		"create table TestCastClobText (id LONG not null primary key, " +
			"data TEXT null)";

	private static final String _SQL_DROP_TABLE = "DROP TABLE TestCastClobText";

	private static final String _SQL_SELECT_COMPARE_STRINGS =
		"SELECT data FROM TestCastClobText WHERE " +
			"DBMS_LOB.COMPARE(CAST_CLOB_TEXT(TestCastClobText.data), ? || ?) " +
				"= 0 ORDER BY id";

	private static DB _db;

}