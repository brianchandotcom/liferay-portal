/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.dao.orm;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Sampsa Sohlman
 */
@ExecutionTestListeners(listeners = { PersistenceExecutionTestListener.class })
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class QueryUtilTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		_db = DBFactoryUtil.getDB();

		_db.runSQL(_SQL_CREATE_TABLE);
		_db.runSQL(createInserts(_AMOUNT));
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_db.runSQL(_SQL_DROP_TABLE);
	}

	@Test
	public void testListModifiableAllPos() throws Exception {
		doListTest(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, false, _AMOUNT, 0,
			_AMOUNT - 1);
	}

	@Test
	public void testListModifiableFaultyParameters1() throws Exception {
		doListTest(-1, 0, false, 0, 0, 0);
	}

	@Test
	public void testListModifiableFaultyParameters2() throws Exception {
		doListTest(0, -1, false, 0, 0, 0);
	}

	@Test
	public void testListModifiableFaultyParameters3() throws Exception {
		doListTest(-2, -2, false, 0, 0, 0);
	}

	@Test
	public void testListModifiableFaultyParameters4() throws Exception {
		doListTest(-1, 10, false, 10, 0, 9);
	}

	@Test
	public void testListModifiableFaultyParameters5() throws Exception {
		doListTest(10, 8, false, 0, 0, 0);
	}

	@Test
	public void testListModifiableFirstTen() throws Exception {
		doListTest(0, 10, false, 10, 0, 9);
	}

	@Test
	public void testListModifiableFiveAfterFive() throws Exception {
		doListTest(5, 10, false, 5, 5, 9);
	}

	@Test
	public void testListModifiableTooBigRange() throws Exception {
		doListTest(_AMOUNT + 1, _AMOUNT + 21, false, 0, 0, 0);
	}

	@Test
	public void testListUnmodifiableAllPos() throws Exception {
		doListTest(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, true, _AMOUNT, 0,
			_AMOUNT - 1);
	}

	@Test
	public void testListUnmodifiableFaultyParameters1() throws Exception {
		doListTest(-1, 0, true, 0, 0, 0);
	}

	@Test
	public void testListUnmodifiableFaultyParameters2() throws Exception {
		doListTest(0, -1, true, 0, 0, 0);
	}

	@Test
	public void testListUnmodifiableFaultyParameters3() throws Exception {
		doListTest(-2, -2, true, 0, 0, 0);
	}

	@Test
	public void testListUnmodifiableFaultyParameters4() throws Exception {
		doListTest(-1, 10, true, 10, 0, 9);
	}

	@Test
	public void testListUnmodifiableFaultyParameters5() throws Exception {
		doListTest(10, 8, true, 0, 0, 0);
	}

	@Test
	public void testListUnmodifiableFirstTen() throws Exception {
		doListTest(0, 10, true, 10, 0, 9);
	}

	@Test
	public void testListUnmodifiableFiveAfterFive() throws Exception {
		doListTest(5, 10, true, 5, 5, 9);
	}

	@Test
	public void testListUnmodifiableTooBigRange() throws Exception {
		doListTest(_AMOUNT + 1, _AMOUNT + 21, true, 0, 0, 0);
	}

	protected static String[] createInserts(int amount) {
		String[] sqls = new String[amount];

		for (int i = 0; i < amount; i++) {
			sqls[i] = StringUtil.replace(
				_SQL_INSERT, new String[] {"[$ID$]", "[$VALUE$]"},
				new String[] {String.valueOf(i), String.valueOf(i)});
		}

		return sqls;
	}

	@SuppressWarnings("unchecked")
	protected void doListTest(
			int start, int end, boolean unmodifiable, int expectedResultSize,
			int expectedFirstValue, int expectedLastValue)
		throws Exception {

		Session session = null;

		try {
			session = _sessionFactory.openSession();

			SQLQuery q = session.createSQLQuery(_SQL_SELECT);

			List<Object[]> result = (List<Object[]>)QueryUtil.list(
				q, _sessionFactory.getDialect(), start, end, unmodifiable);

			Assert.assertNotNull("Verify that result is not null", result);
			Assert.assertEquals(
				"Verify result size", expectedResultSize, result.size());

			if (expectedResultSize > 0) {
				Object[] firstRow = (Object[])result.get(0);
				Object[] lastRow = (Object[])result.get(result.size() - 1);

				Number firstId = (Number)firstRow[0];
				Number lastId  =  (Number)lastRow[0];

				Assert.assertEquals(
					"Verify firstId", expectedFirstValue, firstId.intValue());
				Assert.assertEquals(
					"Verify lastId", expectedLastValue, lastId.intValue());
			}

			try {
				result.add(new Object[0]);

				expectedResultSize++;

				Assert.assertFalse("Verify modifiable", unmodifiable);
			}
			catch (UnsupportedOperationException e) {
				Assert.assertTrue("Verify unmodifiable", unmodifiable);
			}

			Assert.assertEquals(
				"Verify size after modification", expectedResultSize,
				result.size());
		}
		finally {
			_sessionFactory.closeSession(session);
		}
	}

	private static final int _AMOUNT = 20;

	private static final String _SQL_CREATE_TABLE =
		"CREATE TABLE QueryUtilTest (id INTEGER NOT NULL PRIMARY KEY, " +
			"value INTEGER)";

	private static final String _SQL_DROP_TABLE = "DROP TABLE QueryUtilTest";

	private static final String _SQL_INSERT =
		"INSERT INTO QueryUtilTest VALUES ([$ID$], [$VALUE$])";

	private static final String _SQL_SELECT =
		"SELECT id, value FROM QueryUtilTest ORDER BY id ASC";

	private static DB _db;

	private SessionFactory _sessionFactory =
		(SessionFactory)PortalBeanLocatorUtil.locate("liferaySessionFactory");

}