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

package com.liferay.portal.dao.orm.common.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.AssumeTestRule;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.InputStream;

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
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Daniel Sanz
 * @author Shuyang Zhou
 */
@RunWith(Arquillian.class)
public class SQLTransformerOracleCastClobTextTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new AssumeTestRule("assume"), new LiferayIntegrationTestRule());

	public static void assume() {
		DB db = DBManagerUtil.getDB();

		Assume.assumeTrue(db.getDBType() == DBType.ORACLE);
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
		_db = DBManagerUtil.getDB();

		_db.runSQL(
			"create table TestCastClobText (id LONG not null primary key, " +
				"data TEXT null)");

		String createTableSQL = "insert into TestCastClobText values (?, ?)";

		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				createTableSQL)) {

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

	@AfterClass
	public static void tearDownClass() throws Exception {
		_db.runSQL("drop table TestCastClobText");
	}

	@Test
	public void testSelectBigText_3999() throws Exception {
		Assert.assertEquals(
			Arrays.asList(_BIG_TEXT_A_3999), select(_BIG_TEXT_A_3999));
	}

	@Test
	public void testSelectBigText_4000() throws Exception {
		Assert.assertEquals(
			Arrays.asList(
				_BIG_TEXT_A_4000, _BIG_TEXT_A_4001, _BIG_TEXT_A_4000_B_1),
			select(_BIG_TEXT_A_4000));
		Assert.assertEquals(
			Arrays.asList(_BIG_TEXT_A_3999_B_1, _BIG_TEXT_A_3999_B_2),
			select(_BIG_TEXT_A_3999_B_1));
	}

	@Test
	public void testSelectBigText_4001() throws Exception {
		List<String> values = select(_BIG_TEXT_A_4000, _A);

		Assert.assertTrue(values.toString(), values.isEmpty());

		values = select(_BIG_TEXT_A_3999_B_1, _B);

		Assert.assertTrue(values.toString(), values.isEmpty());

		values = select(_BIG_TEXT_A_4000, _B);

		Assert.assertTrue(values.toString(), values.isEmpty());
	}

	@Test
	public void testSelectText_1() throws Exception {
		List<String> values = select(_A);

		Assert.assertTrue(values.toString(), values.isEmpty());

		values = select(_B);

		Assert.assertTrue(values.toString(), values.isEmpty());
	}

	protected List<String> select(String data) throws Exception {
		return select(data, StringPool.BLANK);
	}

	protected List<String> select(String data1, String data2) throws Exception {
		String sql =
			"select data from TestCastClobText where DBMS_LOB.COMPARE(" +
				"CAST_CLOB_TEXT(TestCastClobText.data), ? || ?) = 0 order by " +
					"id";

		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				SQLTransformer.transform(sql))) {

			Clob clob1 = connection.createClob();

			clob1.setString(1, data1);

			preparedStatement.setClob(1, clob1);

			Clob clob2 = connection.createClob();

			clob2.setString(1, data2);

			preparedStatement.setClob(2, clob2);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				List<String> values = new ArrayList<>();

				while (resultSet.next()) {
					Clob clob = resultSet.getClob(1);

					try (InputStream inputStream = clob.getAsciiStream()) {
						values.add(StringUtil.read(inputStream));
					}
				}

				return values;
			}
		}
	}

	private static final String _A = "a";

	private static final String _B = "b";

	private static final String _BIG_TEXT_A_3999;

	private static final String _BIG_TEXT_A_3999_B_1;

	private static final String _BIG_TEXT_A_3999_B_2;

	private static final String _BIG_TEXT_A_4000;

	private static final String _BIG_TEXT_A_4000_B_1;

	private static final String _BIG_TEXT_A_4001;

	private static DB _db;

	static {
		char[] chars = new char[3999];

		Arrays.fill(chars, CharPool.LOWER_CASE_A);

		_BIG_TEXT_A_3999 = new String(chars);

		_BIG_TEXT_A_3999_B_1 = _BIG_TEXT_A_3999 + _B;

		_BIG_TEXT_A_3999_B_2 = _BIG_TEXT_A_3999_B_1 + _B;

		_BIG_TEXT_A_4000 = _BIG_TEXT_A_3999 + _A;

		_BIG_TEXT_A_4000_B_1 = _BIG_TEXT_A_4000 + _B;

		_BIG_TEXT_A_4001 = _BIG_TEXT_A_4000 + _A;
	}

}