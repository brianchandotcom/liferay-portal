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
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.nio.CharBuffer;

import java.util.Collections;
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
			_db.runSQL(createInserts());
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
	public void testSelectBigText_3999() {
		checkResult(
			runSelect(_BIG_TEXT_A_3999, ""), new String[] {_BIG_TEXT_A_3999});
	}

	@Test
	public void testSelectBigText_4000() {

		// those match all rows sharing the first 4000 characters
		// as CAST_CLOB_TEXT truncates data prior to comparison

		checkResult(
			runSelect(_BIG_TEXT_A_4000, ""),
			new String[] {
				_BIG_TEXT_A_4000, _BIG_TEXT_A_4001, _BIG_TEXT_A_4000_B
			});

		checkResult(
			runSelect(_BIG_TEXT_A_3999_B, ""),
			new String[] {_BIG_TEXT_A_3999_B, _BIG_TEXT_A_3999_BB});
	}

	@Test
	public void testSelectBigText_4001() {

		// those match nothing as CAST_CLOB_TEXT truncates data prior to
		// comparison. Note this is intended behavior

		// selects where data = _BIG_TEXT_A_4001

		checkResult(
			runSelect(_BIG_TEXT_A_4000, String.valueOf(_A)), _EMPTY_RESULT);

		// selects where data = _BIG_TEXT_A_3999_BB

		checkResult(
			runSelect(_BIG_TEXT_A_3999_B, String.valueOf(_B)), _EMPTY_RESULT);

		// selects where data = _BIG_TEXT_A_4000_B

		checkResult(
			runSelect(_BIG_TEXT_A_4000, String.valueOf(_B)), _EMPTY_RESULT);
	}

	@Test
	public void testSelectText_1() {

		// matches nothing

		checkResult(runSelect(String.valueOf(_B), ""), _EMPTY_RESULT);

		checkResult(runSelect(String.valueOf(_A), ""), _EMPTY_RESULT);
	}

	private static String[] createInserts() {
		String[] sqls = new String[6];

		sqls[0] = StringUtil.replace(
			_SQL_INSERT_LONG_STRING,
			new String[] {"[$ID$]", "[$DATA_1$]", "[$DATA_2$]"},
			new String[] {"0", _BIG_TEXT_A_3999, ""});

		sqls[1] = StringUtil.replace(
			_SQL_INSERT_LONG_STRING,
			new String[] {"[$ID$]", "[$DATA_1$]", "[$DATA_2$]"},
			new String[] {"1", _BIG_TEXT_A_4000, ""});

		// inserts _BIG_TEXT_A_4001

		sqls[2] = StringUtil.replace(
			_SQL_INSERT_LONG_STRING,
			new String[] {"[$ID$]", "[$DATA_1$]", "[$DATA_2$]"},
			new String[] {"2", _BIG_TEXT_A_4000, String.valueOf(_A)});

		sqls[3] = StringUtil.replace(
			_SQL_INSERT_LONG_STRING,
			new String[] {"[$ID$]", "[$DATA_1$]", "[$DATA_2$]"},
			new String[] {"3", _BIG_TEXT_A_3999_B, ""});

		// inserts _BIG_TEXT_A_3999_BB

		sqls[4] = StringUtil.replace(
			_SQL_INSERT_LONG_STRING,
			new String[] {"[$ID$]", "[$DATA_1$]", "[$DATA_2$]"},
			new String[] {"4", _BIG_TEXT_A_3999_B, String.valueOf(_B)});

		// inserts _BIG_TEXT_A_4000_B

		sqls[5] = StringUtil.replace(
			_SQL_INSERT_LONG_STRING,
			new String[] {"[$ID$]", "[$DATA_1$]", "[$DATA_2$]"},
			new String[] {"5", _BIG_TEXT_A_4000, String.valueOf(_B)});

		return sqls;
	}

	private void checkResult(List<?> queryResult, String[] expectedResult) {
		Assert.assertEquals(queryResult.size(), expectedResult.length);

		List<String> expected = ListUtil.fromArray(expectedResult);

		for (Object result : queryResult) {
			Object[] cols = (Object[])result;

			// data from the data DB column comes in 2 cols. Let's recompose it

			String data = (String)cols[0];

			if (cols[1] != null) {
				data = data + (String)cols[1];
			}

			Assert.assertTrue(expected.contains(data));
		}
	}

	private List<?> runSelect(String data1, String data2) {
		List<?> list = null;

		String sql = null;

		if ((data1.length() + data2.length()) > 4000) {
			sql = _SQL_SELECT_COMPARE_STRINGS_ABOVE_4000;
		}
		else {
			sql = _SQL_SELECT_COMPARE_STRINGS_4000_AND_BELOW;
		}

		Session session = null;

		try {
			session = _sessionFactory.openSession();

			Query q = session.createSynchronizedSQLQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(data1);

			qPos.add(data2);

			list = QueryUtil.list(
				q, _sessionFactory.getDialect(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, false);

			list = Collections.unmodifiableList(list);
		}
		finally {
			_sessionFactory.closeSession(session);
		}

		return list;
	}

	private static final Character _A = 'a';

	private static final Character _B = 'b';

	private static final String _BIG_TEXT_A_3999 = CharBuffer.allocate(
		3999).toString().replace('\0', _A);

	private static final String _BIG_TEXT_A_3999_B = _BIG_TEXT_A_3999 + _B;

	private static final String _BIG_TEXT_A_3999_BB = _BIG_TEXT_A_3999_B + _B;

	private static final String _BIG_TEXT_A_4000 = _BIG_TEXT_A_3999 + _A;

	private static final String _BIG_TEXT_A_4000_B = _BIG_TEXT_A_4000 + _B;

	private static final String _BIG_TEXT_A_4001 = _BIG_TEXT_A_4000 + _A;

	private static final String[] _EMPTY_RESULT = new String[] {};

	private static final String _SQL_CREATE_TABLE =
		"create table TestCastClobText (" +
		"id number(30,0) not null primary key," +
		"data clob null)";

	private static final String _SQL_DROP_TABLE = "DROP TABLE TestCastClobText";

	// this query inserts clobs longer than 4000 chars from 2 string chunks

	private static final String _SQL_INSERT_LONG_STRING =
		"INSERT INTO TestCastClobText VALUES " +
		"([$ID$], to_clob('[$DATA_1$]') || to_clob('[$DATA_2$]'))";

	// following queries select from a clob column into 2 string chunks.
	// This allows to fetch strings longer than 4000 chars.
	// They also compare a clob column with a value made up from 2 strings
	// Both apply the CAST_CLOB_TEXT function to the column being compared,
	// just like *PersistenceImpl does. This way we test how that function
	// works when transformed to an oracle native function

	private static final String _SQL_SELECT_COMPARE_STRINGS_4000_AND_BELOW =
		"SELECT " +
		" DBMS_LOB.SUBSTR(data, 4000, 1), DBMS_LOB.SUBSTR(data, 1, 4001) " +
		"FROM " +
		" TestCastClobText " +
		"WHERE " +
		" CAST_CLOB_TEXT(TestCastClobText.data) = ? || ?";

	private static final String _SQL_SELECT_COMPARE_STRINGS_ABOVE_4000 =
		"SELECT " +
		" DBMS_LOB.SUBSTR(data, 4000, 1), DBMS_LOB.SUBSTR(data, 1, 4001) " +
		"FROM " +
		" TestCastClobText " +
		"WHERE " +
		" DBMS_LOB.COMPARE(CAST_CLOB_TEXT(TestCastClobText.data), " +
		" to_clob(?) || to_clob(?)) = 0";

	private static DB _db;

	private final SessionFactory _sessionFactory =
		(SessionFactory)PortalBeanLocatorUtil.locate("liferaySessionFactory");

}