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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.InputStream;

import java.nio.CharBuffer;

import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;

import java.util.ArrayList;
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
		checkResult(runSelect(_BIG_TEXT_A_3999), _BIG_TEXT_A_3999);
	}

	@Test
	public void testSelectBigText_4000() throws Exception {

		// those match all rows sharing the first 4000 characters
		// as CAST_CLOB_TEXT truncates data prior to comparison

		checkResult(
			runSelect(_BIG_TEXT_A_4000), _BIG_TEXT_A_4000, _BIG_TEXT_A_4001,
			_BIG_TEXT_A_4000_B_1);

		checkResult(
			runSelect(_BIG_TEXT_A_3999_B_1), _BIG_TEXT_A_3999_B_1,
			_BIG_TEXT_A_3999_B_2);
	}

	@Test
	public void testSelectBigText_4001() throws Exception {

		// those match nothing as CAST_CLOB_TEXT truncates data prior to
		// comparison. Note this is intended behavior

		// selects where data = _BIG_TEXT_A_4001

		checkResult(runSelect(_BIG_TEXT_A_4000, String.valueOf(_A)));

		// selects where data = _BIG_TEXT_A_3999_BB

		checkResult(runSelect(_BIG_TEXT_A_3999_B_1, String.valueOf(_B)));

		// selects where data = _BIG_TEXT_A_4000_B

		checkResult(runSelect(_BIG_TEXT_A_4000, String.valueOf(_B)));
	}

	@Test
	public void testSelectText_1() throws Exception {

		// matches nothing

		checkResult(runSelect(String.valueOf(_B)));

		checkResult(runSelect(String.valueOf(_A)));
	}

	private void checkResult(List<?> queryResult, String... expectedResult) {
		Assert.assertEquals(queryResult.size(), expectedResult.length);

		List<String> expected = ListUtil.fromArray(expectedResult);

		for (Object result : queryResult) {
			Assert.assertTrue(expected.contains(result));
		}
	}

	private List<String> runSelect(String data) throws Exception {
		return runSelect(data, StringPool.BLANK);
	}

	private List<String> runSelect(String data1, String data2)
		throws Exception {

		Session session = null;

		try {
			session = _sessionFactory.openSession();

			Query q = session.createSynchronizedSQLQuery(
				_SQL_SELECT_COMPARE_STRINGS);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(data1);

			qPos.add(data2);

			List<Clob> clobs = (List<Clob>)QueryUtil.list(
				q, _sessionFactory.getDialect(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, false);

			List<String> dataStrings = new ArrayList<>(clobs.size());

			for (Clob clob : clobs) {
				try (InputStream inputStream = clob.getAsciiStream()) {
					dataStrings.add(StringUtil.read(inputStream));
				}
			}

			return dataStrings;
		}
		finally {
			_sessionFactory.closeSession(session);
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
		"create table TestCastClobText (" +
		"id number(30,0) not null primary key," +
		"data clob null)";

	private static final String _SQL_DROP_TABLE = "DROP TABLE TestCastClobText";

	private static final String _SQL_SELECT_COMPARE_STRINGS =
		"SELECT data FROM TestCastClobText WHERE " +
			"DBMS_LOB.COMPARE(CAST_CLOB_TEXT(TestCastClobText.data), " +
				" to_clob(?) || to_clob(?)) = 0";

	private static DB _db;

	private final SessionFactory _sessionFactory =
		(SessionFactory)PortalBeanLocatorUtil.locate("liferaySessionFactory");

}