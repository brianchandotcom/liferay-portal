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
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;

import java.sql.Connection;

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
	public static void setUpClass() {
		DB db = DBFactoryUtil.getDB();
		Connection connection = null;
		try {
			connection = DataAccess.getConnection();
			db.runSQL(connection, _CREATE_TABLE);
			db.runSQL(connection, createInserts(_AMOUNT));
			connection.commit();
		}
		catch (Exception e) {
			DataAccess.cleanUp(connection);
		}
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		DB db = DBFactoryUtil.getDB();
		db.runSQL(_DROP_TABLE);
	}

	@Test
	public void testListModifiableAllPos() throws Exception {
		doListTest(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, false, _AMOUNT, 1, _AMOUNT);
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
		doListTest(-1, 10, false, 10, 1, 10);
	}

	@Test
	public void testListModifiableFaultyParameters5() throws Exception {
		doListTest(10, 8, false, 0, 0, 0);
	}

	@Test
	public void testListModifiableFirstTen() throws Exception {
		doListTest(0, 10, false, 10, 1, 10);
	}

	@Test
	public void testListModifiableFiveAfterFive() throws Exception {
		doListTest(5, 10, false, 5, 6, 10);
	}

	@Test
	public void testListModifiableTooBigRange() throws Exception {
		doListTest(_AMOUNT + 1, _AMOUNT + 21, false, 0, 0, 0);
	}

	@Test
	public void testListUnmodifiableAllPos() throws Exception {
		doListTest(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, true, _AMOUNT, 1, _AMOUNT);
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
		doListTest(-1, 10, true, 10, 1, 10);
	}

	@Test
	public void testListUnmodifiableFaultyParameters5() throws Exception {
		doListTest(10, 8, true, 0, 0, 0);
	}

	@Test
	public void testListUnmodifiableFirstTen() throws Exception {
		doListTest(0, 10, true, 10, 1, 10);
	}

	@Test
	public void testListUnmodifiableFiveAfterFive() throws Exception {
		doListTest(5, 10, true, 5, 6, 10);
	}

	@Test
	public void testListUnmodifiableTooBigRange() throws Exception {
		doListTest(_AMOUNT + 1, _AMOUNT + 21, true, 0, 0, 0);
	}

	protected static String[] createInserts(int amount) {
		String[] sqls = new String[amount];

		for (int i = 0; i < amount; i++) {
			int id = i + 1;
			int value = Double.valueOf(Math.random() * 1000).intValue();
			sqls[i] = String.format(
				"INSERT INTO QueryUtilTest VALUES (%d, %d)", id, value);
		}

		return sqls;
	}

	protected void doListTest(
			int start, int end, boolean unmodifiable, int expectedResultSize,
			int expectedFirstValue, int expectedLastValue)
		throws Exception {

		Session session = null;

		try {
			session = _sessionFactory.openSession();

			SQLQuery q = session.createSQLQuery(_SELECT);

			List result = QueryUtil.list(
				q, _sessionFactory.getDialect(), start, end, unmodifiable);

			Assert.assertNotNull("Verify that result is not null", result);
			Assert.assertEquals(
				"Verify result size", expectedResultSize, result.size());

			if (expectedResultSize > 0) {
				Object[] firstRow = (Object[])result.get(0);
				Object[] lastRow = (Object[])result.get(result.size() - 1);

				int firstId = (Integer)firstRow[0];
				int lastId  =  (Integer)lastRow[0];

				Assert.assertEquals(
					"Verify firstId", expectedFirstValue, firstId);
				Assert.assertEquals("Verify lastId", expectedLastValue, lastId);
			}

			try {
				Object newItem = new Object[] {_AMOUNT + 1, 2};
				result.add(newItem);
				Assert.assertFalse("Verify modifiable", unmodifiable);
				expectedResultSize++;
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

	private static int _AMOUNT = 20;

	private static String _CREATE_TABLE =
		"create table QueryUtilTest ( id INT not null primary key, " +
		"valueId INT )";

	private static String _DROP_TABLE = "drop table QueryUtilTest";

	private static String _SELECT =
		"select id, valueId from QueryUtilTest order by id asc";

	private SessionFactory _sessionFactory =
		(SessionFactory)PortalBeanLocatorUtil.locate("liferaySessionFactory");

}