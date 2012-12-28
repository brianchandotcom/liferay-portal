/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.dao.orm;

import com.liferay.portal.dao.db.OracleDB;
import com.liferay.portal.dao.db.PostgreSQLDB;
import com.liferay.portal.dao.db.SybaseDB;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * This test shows the SQL NULL comparison difference across all databases
 * supported by Liferay Portal.<p>
 *
 * The comparison can yield 3 different results : TRUE, FALSE OR NULL.<p>
 *
 * This test tests 3 different values : <br>
 * ''(blank string), null(NULL value) and 0 (number zero)<br>
 * comparing to NULL with 6 comparators : <br>
 * =, !=, IS, IS NOT, LIKE and NOT LIKE.<p>
 *
 * The results show in following table:
 *
 * <table border="1">
 *	<tr>
 *		<th></th>
 *		<th>MySQL/DB2/SQL Server 2005/2008</th>
 *		<th>PostgreSQL</th>
 *		<th>Oracle 10G/11G</th>
 *		<th>Sybase</th>
 *	</tr>
 *	<tr>
 *		<td colspan="5" align="center">'' comparison with NULL</td>
 *	</tr>
 *	<tr>
 *		<td>'' = NULL</td>
 *		<td>NULL</td>
 *		<td>NULL</td>
 *		<td>NULL</td>
 *		<td>FALSE*</td>
 *	</tr>
 *	<tr>
 *		<td>'' != NULL</td>
 *		<td>NULL</td>
 *		<td>NULL</td>
 *		<td>NULL</td>
 *		<td>TRUE*</td>
 *	</tr>
 *	<tr>
 *		<td>'' IS NULL</td>
 *		<td>FALSE</td>
 *		<td>FALSE</td>
 *		<td>TRUE</td>
 *		<td>FALSE*</td>
 *	</tr>
 *	<tr>
 *		<td>'' IS NOT NULL</td>
 *		<td>TRUE</td>
 *		<td>TRUE</td>
 *		<td>FALSE</td>
 *		<td>TRUE*</td>
 *	</tr>
 *	<tr>
 *		<td>'' LIKE NULL</td>
 *		<td>NULL</td>
 *		<td>NULL</td>
 *		<td>NULL</td>
 *		<td>FALSE</td>
 *	</tr>
 *	<tr>
 *		<td>'' NOT LIKE NULL</td>
 *		<td>NULL</td>
 *		<td>NULL</td>
 *		<td>NULL</td>
 *		<td>TRUE</td>
 *	</tr>
 *	<tr>
 *		<td colspan="5" align="center">NULL comparison with NULL</td>
 *	</tr>
 *	<tr>
 *		<td>NULL = NULL</td>
 *		<td>NULL</td>
 *		<td>NULL</td>
 *		<td>NULL</td>
 *		<td>TRUE*</td>
 *	</tr>
 *	<tr>
 *		<td>NULL != NULL</td>
 *		<td>NULL</td>
 *		<td>NULL</td>
 *		<td>NULL</td>
 *		<td>FALSE*</td>
 *	</tr>
 *	<tr>
 *		<td>NULL IS NULL</td>
 *		<td>TRUE</td>
 *		<td>TRUE</td>
 *		<td>TRUE</td>
 *		<td>TRUE*</td>
 *	</tr>
 *	<tr>
 *		<td>NULL IS NOT NULL</td>
 *		<td>FALSE</td>
 *		<td>FALSE</td>
 *		<td>FALSE</td>
 *		<td>FALSE*</td>
 *	</tr>
 *	<tr>
 *		<td>NULL LIKE NULL</td>
 *		<td>NULL</td>
 *		<td>NULL</td>
 *		<td>NULL</td>
 *		<td>FALSE</td>
 *	</tr>
 *	<tr>
 *		<td>NULL NOT LIKE NULL</td>
 *		<td>NULL</td>
 *		<td>NULL</td>
 *		<td>NULL</td>
 *		<td>TRUE</td>
 *	</tr>
 *	<tr>
 *		<td colspan="5" align="center">0 comparison with NULL</td>
 *	</tr>
 *	<tr>
 *		<td>0 = NULL</td>
 *		<td>NULL</td>
 *		<td>NULL</td>
 *		<td>NULL</td>
 *		<td>FALSE</td>
 *	</tr>
 *	<tr>
 *		<td>0 != NULL</td>
 *		<td>NULL</td>
 *		<td>NULL</td>
 *		<td>NULL</td>
 *		<td>TRUE</td>
 *	</tr>
 *	<tr>
 *		<td>0 IS NULL</td>
 *		<td>FALSE</td>
 *		<td>FALSE</td>
 *		<td>FALSE</td>
 *		<td>FALSE</td>
 *	</tr>
 *	<tr>
 *		<td>0 IS NOT NULL</td>
 *		<td>TRUE</td>
 *		<td>TRUE</td>
 *		<td>TRUE</td>
 *		<td>TRUE</td>
 *	</tr>
 *	<tr>
 *		<td>0 LIKE NULL</td>
 *		<td>NULL</td>
 *		<td>NULL*</td>
 *		<td>NULL</td>
 *		<td>FALSE*</td>
 *	</tr>
 *	<tr>
 *		<td>0 NOT LIKE NULL</td>
 *		<td>NULL</td>
 *		<td>NULL*</td>
 *		<td>NULL</td>
 *		<td>TRUE*</td>
 *	</tr>
 * </table>
 *
 * Notice, PostgreSQLDB and SybaseDB can not handle certain comparisons directly
 * , so a CAST or CONVERT is required. See fields whose value followed with *<p>
 *
 * Base on the results table, there are only 4 comparisons behave exactly the
 * same across all databases.
 *
 * <ol>
 *		<li>(NULL IS NULL) = TRUE</li>
 *		<li>(NULL IS NOT NULL) = FALSE</li>
 *		<li>(0 IS NULL) = FALSE</li>
 *		<li>(0 IS NOT NULL) = TRUE</li>
 * </ol>
 *
 * @author Shuyang Zhou
 */
@ExecutionTestListeners(listeners = {
	PersistenceExecutionTestListener.class})
@RunWith(LiferayPersistenceIntegrationJUnitTestRunner.class)
public class SQLNullTest {

	@Before
	public void setUp() {
		_db = DBFactoryUtil.getDB();

		_equalNull = "SELECT DISTINCT 1 FROM ClassName_ WHERE ? = NULL";

		_isNotNull = "SELECT DISTINCT 1 FROM ClassName_ WHERE ? IS NOT NULL";

		_isNull = "SELECT DISTINCT 1 FROM ClassName_ WHERE ? IS NULL";

		_likeNull = "SELECT DISTINCT 1 FROM ClassName_ WHERE ? LIKE NULL";

		_notEqualNull = "SELECT DISTINCT 1 FROM ClassName_ WHERE ? != NULL";

		_notLikeNull =
			"SELECT DISTINCT 1 FROM ClassName_ WHERE ? NOT LIKE NULL";

		_sessionFactory = (SessionFactory)PortalBeanLocatorUtil.locate(
			"liferaySessionFactory");
	}

	@Test
	public void testBlankStringEqualNull() {
		if (_db instanceof SybaseDB) {
			_equalNull = _equalNull.replace("?", "CONVERT(VARCHAR, ?)");
		}

		// ('' = NULL) is NULL, expect for Sybase is false

		Session session = _sessionFactory.openSession();

		try {
			SQLQuery sqlQuery = session.createSQLQuery(_equalNull);

			QueryPos qPos = QueryPos.getInstance(sqlQuery);

			qPos.add(StringPool.BLANK);

			List<Object> list = sqlQuery.list();

			Assert.assertTrue(list.isEmpty());
		}
		finally {
			session.close();
		}
	}

	@Test
	public void testBlankStringIsNotNull() {
		if (_db instanceof SybaseDB) {
			_isNotNull = _isNotNull.replace("?", "CONVERT(VARCHAR, ?)");
		}

		// ('' IS NOT NULL) is true, expect for Oracle is false

		Session session = _sessionFactory.openSession();

		try {
			SQLQuery sqlQuery = session.createSQLQuery(_isNotNull);

			QueryPos qPos = QueryPos.getInstance(sqlQuery);

			qPos.add(StringPool.BLANK);

			List<Object> list = sqlQuery.list();

			if (_db instanceof OracleDB) {
				Assert.assertTrue(list.isEmpty());
			}
			else {
				Assert.assertFalse(list.isEmpty());
			}
		}
		finally {
			session.close();
		}
	}

	@Test
	public void testBlankStringIsNull() {
		if (_db instanceof SybaseDB) {
			_isNull = _isNull.replace("?", "CONVERT(VARCHAR, ?)");
		}

		// ('' IS NULL) is false, expect for Oracle is true

		Session session = _sessionFactory.openSession();

		try {
			SQLQuery sqlQuery = session.createSQLQuery(_isNull);

			QueryPos qPos = QueryPos.getInstance(sqlQuery);

			qPos.add(StringPool.BLANK);

			List<Object> list = sqlQuery.list();

			if (_db instanceof OracleDB) {
				Assert.assertFalse(list.isEmpty());
			}
			else {
				Assert.assertTrue(list.isEmpty());
			}
		}
		finally {
			session.close();
		}
	}

	@Test
	public void testBlankStringLikeNull() {

		// ('' LIKE NULL) is NULL, expect for Sybase is false

		Session session = _sessionFactory.openSession();

		try {
			SQLQuery sqlQuery = session.createSQLQuery(_likeNull);

			QueryPos qPos = QueryPos.getInstance(sqlQuery);

			qPos.add(StringPool.BLANK);

			List<Object> list = sqlQuery.list();

			Assert.assertTrue(list.isEmpty());
		}
		finally {
			session.close();
		}
	}

	@Test
	public void testBlankStringNotEqualNull() {
		if (_db instanceof SybaseDB) {
			_notEqualNull = _notEqualNull.replace("?", "CONVERT(VARCHAR, ?)");
		}

		// ('' != NULL) is NULL, expect for Sybase is true

		Session session = _sessionFactory.openSession();

		try {
			SQLQuery sqlQuery = session.createSQLQuery(_notEqualNull);

			QueryPos qPos = QueryPos.getInstance(sqlQuery);

			qPos.add(StringPool.BLANK);

			List<Object> list = sqlQuery.list();

			if (_db instanceof SybaseDB) {
				Assert.assertFalse(list.isEmpty());
			}
			else {
				Assert.assertTrue(list.isEmpty());
			}
		}
		finally {
			session.close();
		}
	}

	@Test
	public void testBlankStringNotLikeNull() {

		// ('' NOT LIKE NULL) is NULL, expect for Sybase is true

		Session session = _sessionFactory.openSession();

		try {
			SQLQuery sqlQuery = session.createSQLQuery(_notLikeNull);

			QueryPos qPos = QueryPos.getInstance(sqlQuery);

			qPos.add(StringPool.BLANK);

			List<Object> list = sqlQuery.list();

			if (_db instanceof SybaseDB) {
				Assert.assertFalse(list.isEmpty());
			}
			else {
				Assert.assertTrue(list.isEmpty());
			}
		}
		finally {
			session.close();
		}
	}

	@Test
	public void testNullEqualNull() {
		if (_db instanceof SybaseDB) {
			_equalNull = _equalNull.replace("?", "CONVERT(VARCHAR, ?)");
		}

		// (null = NULL) is NULL, expect for Sybase is true

		Session session = _sessionFactory.openSession();

		try {
			SQLQuery sqlQuery = session.createSQLQuery(_equalNull);

			QueryPos qPos = QueryPos.getInstance(sqlQuery);

			qPos.add((Object)null);

			List<Object> list = sqlQuery.list();

			if (_db instanceof SybaseDB) {
				Assert.assertFalse(list.isEmpty());
			}
			else {
				Assert.assertTrue(list.isEmpty());
			}
		}
		finally {
			session.close();
		}
	}

	@Test
	public void testNullIsNotNull() {
		if (_db instanceof SybaseDB) {
			_isNotNull = _isNotNull.replace("?", "CONVERT(VARCHAR, ?)");
		}

		// (null IS NOT NULL) is false

		Session session = _sessionFactory.openSession();

		try {
			SQLQuery sqlQuery = session.createSQLQuery(_isNotNull);

			QueryPos qPos = QueryPos.getInstance(sqlQuery);

			qPos.add((Object)null);

			List<Object> list = sqlQuery.list();

			Assert.assertTrue(list.isEmpty());
		}
		finally {
			session.close();
		}
	}

	@Test
	public void testNullIsNull() {
		if (_db instanceof SybaseDB) {
			_isNull = _isNull.replace("?", "CONVERT(VARCHAR, ?)");
		}

		// (null IS NULL) is true

		Session session = _sessionFactory.openSession();

		try {
			SQLQuery sqlQuery = session.createSQLQuery(_isNull);

			QueryPos qPos = QueryPos.getInstance(sqlQuery);

			qPos.add((Object)null);

			List<Object> list = sqlQuery.list();

			Assert.assertFalse(list.isEmpty());
		}
		finally {
			session.close();
		}
	}

	@Test
	public void testNullLikeNull() {

		// (null LIKE NULL) is NULL, expect for Sybase is false

		Session session = _sessionFactory.openSession();

		try {
			SQLQuery sqlQuery = session.createSQLQuery(_likeNull);

			QueryPos qPos = QueryPos.getInstance(sqlQuery);

			qPos.add((Object)null);

			List<Object> list = sqlQuery.list();

			Assert.assertTrue(list.isEmpty());
		}
		finally {
			session.close();
		}
	}

	@Test
	public void testNullNotEqualNull() {
		if (_db instanceof SybaseDB) {
			_notEqualNull = _notEqualNull.replace("?", "CONVERT(VARCHAR, ?)");
		}

		// (null != NULL) is NULL, expect for Sybase is false

		Session session = _sessionFactory.openSession();

		try {
			SQLQuery sqlQuery = session.createSQLQuery(_notEqualNull);

			QueryPos qPos = QueryPos.getInstance(sqlQuery);

			qPos.add((Object)null);

			List<Object> list = sqlQuery.list();

			Assert.assertTrue(list.isEmpty());
		}
		finally {
			session.close();
		}
	}

	@Test
	public void testNullNotLikeNull() {

		// (null not LIKE NULL) is NULL, expect for Sybase is true

		Session session = _sessionFactory.openSession();

		try {
			SQLQuery sqlQuery = session.createSQLQuery(_notLikeNull);

			QueryPos qPos = QueryPos.getInstance(sqlQuery);

			qPos.add((Object)null);

			List<Object> list = sqlQuery.list();

			if (_db instanceof SybaseDB) {
				Assert.assertFalse(list.isEmpty());
			}
			else {
				Assert.assertTrue(list.isEmpty());
			}
		}
		finally {
			session.close();
		}
	}

	@Test
	public void testZeroEqualNull() {

		// (0 = NULL) is NULL, expect for Sybase is false

		Session session = _sessionFactory.openSession();

		try {
			SQLQuery sqlQuery = session.createSQLQuery(_equalNull);

			QueryPos qPos = QueryPos.getInstance(sqlQuery);

			qPos.add(0);

			List<Object> list = sqlQuery.list();

			Assert.assertTrue(list.isEmpty());
		}
		finally {
			session.close();
		}
	}

	@Test
	public void testZeroIsNotNull() {

		// (0 IS NOT NULL) is true

		Session session = _sessionFactory.openSession();

		try {
			SQLQuery sqlQuery = session.createSQLQuery(_isNotNull);

			QueryPos qPos = QueryPos.getInstance(sqlQuery);

			qPos.add(0);

			List<Object> list = sqlQuery.list();

			Assert.assertFalse(list.isEmpty());
		}
		finally {
			session.close();
		}
	}

	@Test
	public void testZeroIsNull() {

		// (0 IS NULL) is false

		Session session = _sessionFactory.openSession();

		try {
			SQLQuery sqlQuery = session.createSQLQuery(_isNull);

			QueryPos qPos = QueryPos.getInstance(sqlQuery);

			qPos.add(0);

			List<Object> list = sqlQuery.list();

			Assert.assertTrue(list.isEmpty());
		}
		finally {
			session.close();
		}
	}

	@Test
	public void testZeroLikeNull() {
		if (_db instanceof PostgreSQLDB) {
			_likeNull = _likeNull.replace("?", "CAST(? AS VARCHAR)");
		}

		if (_db instanceof SybaseDB) {
			_likeNull = _likeNull.replace("?", "CONVERT(VARCHAR, ?)");
		}

		// (0 LIKE NULL) is NULL, expect for Sybase is false

		Session session = _sessionFactory.openSession();

		try {
			SQLQuery sqlQuery = session.createSQLQuery(_likeNull);

			QueryPos qPos = QueryPos.getInstance(sqlQuery);

			qPos.add(0);

			List<Object> list = sqlQuery.list();

			Assert.assertTrue(list.isEmpty());
		}
		finally {
			session.close();
		}
	}

	@Test
	public void testZeroNotEqualNull() {

		// (0 != NULL) is NULL, expect for Sybase is true

		Session session = _sessionFactory.openSession();

		try {
			SQLQuery sqlQuery = session.createSQLQuery(_notEqualNull);

			QueryPos qPos = QueryPos.getInstance(sqlQuery);

			qPos.add(0);

			List<Object> list = sqlQuery.list();

			if (_db instanceof SybaseDB) {
				Assert.assertFalse(list.isEmpty());
			}
			else {
				Assert.assertTrue(list.isEmpty());
			}
		}
		finally {
			session.close();
		}
	}

	@Test
	public void testZeroNotLikeNull() {
		if (_db instanceof PostgreSQLDB) {
			_notLikeNull = _notLikeNull.replace("?", "CAST(? AS VARCHAR)");
		}

		if (_db instanceof SybaseDB) {
			_notLikeNull = _notLikeNull.replace("?", "CONVERT(VARCHAR, ?)");
		}

		// (0 not LIKE NULL) is NULL, expect for Sybase is true

		Session session = _sessionFactory.openSession();

		try {
			SQLQuery sqlQuery = session.createSQLQuery(_notLikeNull);

			QueryPos qPos = QueryPos.getInstance(sqlQuery);

			qPos.add(0);

			List<Object> list = sqlQuery.list();

			if (_db instanceof SybaseDB) {
				Assert.assertFalse(list.isEmpty());
			}
			else {
				Assert.assertTrue(list.isEmpty());
			}
		}
		finally {
			session.close();
		}
	}

	private DB _db;
	private String _equalNull;
	private String _isNotNull;
	private String _isNull;
	private String _likeNull;
	private String _notEqualNull;
	private String _notLikeNull;
	private SessionFactory _sessionFactory;

}