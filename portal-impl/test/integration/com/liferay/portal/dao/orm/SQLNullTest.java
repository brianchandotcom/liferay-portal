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

package com.liferay.portal.dao.orm;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * This test shows the SQL <code>Null</code> comparison differences across all
 * databases supported by Liferay Portal.
 *
 * <p>
 * This class tests three different values: <code>''</code> (blank string),
 * <code>null</code> (<code>NULL</code> value), and <code>0</code> (number zero)
 * in comparison to <code>NULL</code> with six comparators: =, !=, IS, IS NOT,
 * LIKE, and NOT LIKE. The comparisons can yield three different results:
 * <code>TRUE</code>, <code>FALSE</code>, or <code>NULL</code>. The results are
 * displayed in the following table:
 * </p>
 *
 * <table border="1">
 *
 * 	<tr>
 * 		<th>
 * 		</th>
 * 		<th>
 * 			MySQL/DB2/SQL Server 2005/2008
 * 		</th>
 * 		<th>
 * 			PostgreSQL
 * 		</th>
 * 		<th>
 * 			Oracle 10G/11G
 * 		</th>
 * 		<th>
 * 			Sybase
 * 		</th>
 * 		<th>
 * 			Hypersonic
 * 		</th>
 * 	</tr>
 *
 * 	<tr>
 * 		<td colspan="6" align="center">
 * 			<code>''</code> comparison with <code>NULL</code>
 * 		</td>
 * 	</tr>
 *
 * 	<tr>
 * 		<td>
 * 			<code>''</code> = <code>NULL</code>
 * 		</td>
 * 		<td>
 * 			<code>NULL</code>
 * 		</td>
 * 		<td>
 * 			<code>NULL</code>
 * 		</td>
 * 		<td>
 * 			<code>NULL</code>
 * 		</td>
 * 		<td>
 * 			<code>FALSE</code><sup>*</sup>
 * 		</td>
 * 	<td>
 * 			<code>NULL</code><sup>*</sup>
 * 		</td>
 * 	</tr>
 *
 * 	<tr>
 * 		<td>
 * 			<code>''</code> != <code>NULL</code>
 * 		</td>
 * 		<td>
 * 			<code>NULL</code>
 * 		</td>
 * 		<td>
 * 			<code>NULL</code>
 * 		</td>
 * 		<td>
 * 			<code>NULL</code>
 * 		</td>
 * 		<td>
 * 			<code>TRUE</code><sup>*</sup>
 * 		</td>
 * 	<td>
 * 			<code>NULL</code><sup>*</sup>
 * 		</td>
 * 	</tr>
 *
 * 	<tr>
 * 		<td>
 * 			<code>''</code> IS <code>NULL</code>
 * 		</td>
 * 		<td>
 * 			<code>FALSE</code>
 * 		</td>
 * 		<td>
 * 			<code>FALSE</code>
 * 		</td>
 * 		<td>
 * 			<code>TRUE</code>
 * 		</td>
 * 		<td>
 * 			<code>FALSE</code><sup>*</sup>
 * 		</td>
 * 		<td>
 * 			<code>FALSE</code>
 * 		</td>
 * 	</tr>
 *
 * 	<tr>
 * 		<td>
 * 			<code>''</code> IS NOT <code>NULL</code>
 * 		</td>
 * 		<td>
 * 			<code>TRUE</code>
 * 		</td>
 * 		<td>
 * 			<code>TRUE</code>
 * 		</td>
 * 		<td>
 * 			<code>FALSE</code>
 * 		</td>
 * 		<td>
 * 			<code>TRUE</code><sup>*</sup>
 * 		</td>
 * 		<td>
 * 			<code>TRUE</code>
 * 		</td>
 * 	</tr>
 *
 * 	<tr>
 * 		<td>
 * 			<code>''</code> LIKE <code>NULL</code>
 * 		</td>
 * 		<td>
 * 			<code>NULL</code>
 * 		</td>
 * 		<td>
 * 			<code>NULL</code>
 * 		</td>
 * 		<td>
 * 			<code>NULL</code>
 * 		</td>
 * 		<td>
 * 			<code>FALSE</code>
 * 		</td>
 * 		<td>
 * 			<code>NULL</code><sup>*</sup>
 * 		</td>
 * 	</tr>
 *
 * 	<tr>
 * 		<td>
 * 			<code>''</code> NOT LIKE <code>NULL</code>
 * 		</td>
 * 		<td>
 * 			<code>NULL</code>
 * 		</td>
 * 		<td>
 * 			<code>NULL</code>
 * 		</td>
 * 		<td>
 * 			<code>NULL</code>
 * 		</td>
 * 		<td>
 * 			<code>TRUE</code>
 * 		</td>
 * 		<td>
 * 			<code>NULL</code><sup>*</sup>
 * 		</td>
 * 	</tr>
 *
 * 	<tr>
 * 		<td colspan="6" align="center">
 * 			<code>NULL</code> comparison with <code>NULL</code>
 * 		</td>
 * 	</tr>
 *
 * 	<tr>
 * 		<td>
 * 			<code>NULL</code> = <code>NULL</code>
 * 		</td>
 * 		<td>
 * 			<code>NULL</code>
 * 		</td>
 * 		<td>
 * 			<code>NULL</code>
 * 		</td>
 * 		<td>
 * 			<code>NULL</code>
 * 		</td>
 * 		<td>
 * 			<code>TRUE</code><sup>*</sup>
 * 		</td>
 * 		<td>
 * 			<code>NULL</code><sup>*</sup>
 * 		</td>
 * 	</tr>
 *
 * 	<tr>
 * 		<td>
 * 			<code>NULL</code> != <code>NULL</code>
 * 		</td>
 * 		<td>
 * 			<code>NULL</code>
 * 		</td>
 * 		<td>
 * 			<code>NULL</code>
 * 		</td>
 * 		<td>
 * 			<code>NULL</code>
 * 		</td>
 * 		<td>
 * 			<code>FALSE</code><sup>*</sup>
 * 		</td>
 * 		<td>
 * 			<code>NULL</code><sup>*</sup>
 * 		</td>
 * 	</tr>
 *
 * 	<tr>
 * 		<td>
 * 			<code>NULL</code> IS <code>NULL</code>
 * 		</td>
 * 		<td>
 * 			<code>TRUE</code>
 * 		</td>
 * 		<td>
 * 			<code>TRUE</code>
 * 		</td>
 * 		<td>
 * 			<code>TRUE</code>
 * 		</td>
 * 		<td>
 * 			<code>TRUE</code><sup>*</sup>
 * 		</td>
 * 		<td>
 * 			<code>TRUE</code>
 * 		</td>
 * 	</tr>
 *
 * 	<tr>
 * 		<td>
 * 			<code>NULL</code> IS NOT <code>NULL</code>
 * 		</td>
 * 		<td>
 * 			<code>FALSE</code>
 * 		</td>
 * 		<td>
 * 			<code>FALSE</code>
 * 		</td>
 * 		<td>
 * 			<code>FALSE</code>
 * 		</td>
 * 		<td>
 * 			<code>FALSE</code><sup>*</sup>
 * 		</td>
 * 		<td>
 * 			<code>FALSE</code>
 * 		</td>
 * 	</tr>
 *
 * 	<tr>
 * 		<td>
 * 			<code>NULL</code> LIKE <code>NULL</code>
 * 		</td>
 * 		<td>
 * 			<code>NULL</code>
 * 		</td>
 * 		<td>
 * 			<code>NULL</code>
 * 		</td>
 * 		<td>
 * 			<code>NULL</code>
 * 		</td>
 * 		<td>
 * 		<code>FALSE</code>
 * 		</td>
 * 		<td>
 * 			<code>NULL</code><sup>*</sup>
 * 		</td>
 * 	</tr>
 *
 * 	<tr>
 * 		<td>
 * 			<code>NULL</code> NOT LIKE <code>NULL</code>
 * 		</td>
 * 		<td>
 * 			<code>NULL</code>
 * 		</td>
 * 		<td>
 * 			<code>NULL</code>
 * 		</td>
 * 		<td>
 * 			<code>NULL</code>
 * 		</td>
 * 		<td>
 * 			<code>TRUE</code>
 * 		</td>
 * 		<td>
 * 			<code>NULL</code><sup>*</sup>
 * 		</td>
 * 	</tr>
 *
 * 	<tr>
 * 		<td colspan="6" align="center">
 * 			<code>0</code> comparison with <code>NULL</code>
 * 		</td>
 * 	</tr>
 *
 * 	<tr>
 * 		<td>
 * 			<code>0</code> = <code>NULL</code>
 * 		</td>
 * 		<td>
 * 			<code>NULL</code>
 * 		</td>
 * 		<td>
 * 			<code>NULL</code>
 * 		</td>
 * 		<td>
 * 			<code>NULL</code>
 * 		</td>
 * 		<td>
 * 			<code>FALSE</code>
 * 		</td>
 * 		<td>
 * 			<code>NULL</code><sup>*</sup>
 * 		</td>
 * 	</tr>
 *
 * 	<tr>
 * 		<td>
 * 			<code>0</code> != <code>NULL</code>
 * 		</td>
 * 		<td>
 * 			<code>NULL</code>
 * 		</td>
 * 		<td>
 * 			<code>NULL</code>
 * 		</td>
 * 		<td>
 * 			<code>NULL</code>
 * 		</td>
 * 		<td>
 * 			<code>TRUE</code>
 * 		</td>
 * 		<td>
 * 			<code>NULL</code><sup>*</sup>
 * 		</td>
 * 	</tr>
 *
 * 	<tr>
 * 		<td>
 * 			<code>0</code> IS <code>NULL</code>
 * 		</td>
 * 		<td>
 * 			<code>FALSE</code>
 * 		</td>
 * 		<td>
 * 			<code>FALSE</code>
 * 		</td>
 * 		<td>
 * 			<code>FALSE</code>
 * 		</td>
 * 		<td>
 * 			<code>FALSE</code>
 * 		</td>
 * 		<td>
 * 			<code>FALSE</code>
 * 		</td>
 * 	</tr>
 *
 * 	<tr>
 * 		<td>
 * 			<code>0</code> IS NOT <code>NULL</code>
 * 		</td>
 * 		<td>
 * 			<code>TRUE</code>
 * 		</td>
 * 		<td>
 * 			<code>TRUE</code>
 * 		</td>
 * 		<td>
 * 			<code>TRUE</code>
 * 		</td>
 * 		<td>
 * 			<code>TRUE</code>
 * 		</td>
 * 		<td>
 * 			<code>TRUE</code>
 * 		</td>
 * 	</tr>
 *
 * 	<tr>
 * 		<td>
 * 			<code>0</code> LIKE <code>NULL</code>
 * 		</td>
 * 		<td>
 * 			<code>NULL</code>
 * 		</td>
 * 		<td>
 * 			<code>NULL</code><sup>*</sup>
 * 		</td>
 * 		<td>
 * 			<code>NULL</code>
 * 		</td>
 * 		<td>
 * 			<code>FALSE</code><sup>*</sup>
 * 		</td>
 * 		<td>
 * 			<code>NULL</code><sup>*</sup>
 * 		</td>
 * 	</tr>
 *
 * 	<tr>
 * 		<td>
 * 			<code>0</code> NOT LIKE <code>NULL</code>
 * 		</td>
 * 		<td>
 * 			<code>NULL</code>
 * 		</td>
 * 		<td>
 * 			<code>NULL</code><sup>*</sup>
 * 		</td>
 * 		<td>
 * 			<code>NULL</code>
 * 		</td>
 * 		<td>
 * 			<code>TRUE</code><sup>*</sup>
 * 		</td>
 * 		<td>
 * 			<code>NULL</code><sup>*</sup>
 * 		</td>
 * 	</tr>
 *
 * </table>
 *
 * <caption>
 * <sup>*</sup> <i>denotes where specific databases cannot handle certain
 * comparisons directly. In these cases, a <code>CAST</code> or
 * <code>CONVERT</code> is required.</i>
 * </caption>
 *
 * <p>
 * Based on the results table, there are only four comparisons that behave the
 * same across all databases:
 * </p>
 *
 * <ul>
 * <li>
 * (<code>NULL</code> IS <code>NULL</code>) = <code>TRUE</code>
 * </li>
 * <li>
 * (<code>NULL</code> IS NOT <code>NULL</code>) = <code>FALSE</code>
 * </li>
 * <li>
 * (<code>0</code> IS <code>NULL</code>) = <code>FALSE</code>
 * </li>
 * <li>
 * (<code>0</code> IS NOT <code>NULL</code>) = <code>TRUE</code>
 * </li>
 * </ul>
 *
 * @author Shuyang Zhou
 */
public class SQLNullTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			TransactionalTestRule.INSTANCE);

	@Test
	public void testBlankStringEqualsNull() {
		String sql = _SQL_EQUALS_NULL;

		if (isSybase()) {
			sql = transformSybaseSQL(sql);
		}
		else if (isHypersonic()) {
			sql = transformHypersonicSQL(sql);
		}

		Session session = _sessionFactory.openSession();

		try {
			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			QueryPos qPos = QueryPos.getInstance(sqlQuery);

			qPos.add(StringPool.BLANK);

			List<Object> list = sqlQuery.list();

			Assert.assertTrue(list.isEmpty());
		}
		finally {
			_sessionFactory.closeSession(session);
		}
	}

	@Test
	public void testBlankStringIsNotNull() {
		String sql = _SQL_IS_NOT_NULL;

		if (isSybase()) {
			sql = transformSybaseSQL(sql);
		}

		Session session = _sessionFactory.openSession();

		try {
			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			QueryPos qPos = QueryPos.getInstance(sqlQuery);

			qPos.add(StringPool.BLANK);

			List<Object> list = sqlQuery.list();

			if (isOracle()) {
				Assert.assertTrue(list.isEmpty());
			}
			else {
				Assert.assertFalse(list.isEmpty());
			}
		}
		finally {
			_sessionFactory.closeSession(session);
		}
	}

	@Test
	public void testBlankStringIsNull() {
		String sql = _SQL_IS_NULL;

		if (isSybase()) {
			sql = transformSybaseSQL(sql);
		}

		Session session = _sessionFactory.openSession();

		try {
			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			QueryPos qPos = QueryPos.getInstance(sqlQuery);

			qPos.add(StringPool.BLANK);

			List<Object> list = sqlQuery.list();

			if (isOracle()) {
				Assert.assertFalse(list.isEmpty());
			}
			else {
				Assert.assertTrue(list.isEmpty());
			}
		}
		finally {
			_sessionFactory.closeSession(session);
		}
	}

	@Test
	public void testBlankStringLikeNull() {
		String sql = _SQL_LIKE_NULL;

		if (isHypersonic()) {
			sql = transformHypersonicSQL(sql);
		}

		Session session = _sessionFactory.openSession();

		try {
			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			QueryPos qPos = QueryPos.getInstance(sqlQuery);

			qPos.add(StringPool.BLANK);

			List<Object> list = sqlQuery.list();

			Assert.assertTrue(list.isEmpty());
		}
		finally {
			_sessionFactory.closeSession(session);
		}
	}

	@Test
	public void testBlankStringNotEqualsNull() {
		String sql = _SQL_NOT_EQUALS_NULL;

		if (isSybase()) {
			sql = transformSybaseSQL(sql);
		}
		else if (isHypersonic()) {
			sql = transformHypersonicSQL(sql);
		}

		Session session = _sessionFactory.openSession();

		try {
			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			QueryPos qPos = QueryPos.getInstance(sqlQuery);

			qPos.add(StringPool.BLANK);

			List<Object> list = sqlQuery.list();

			if (isSybase()) {
				Assert.assertFalse(list.isEmpty());
			}
			else {
				Assert.assertTrue(list.isEmpty());
			}
		}
		finally {
			_sessionFactory.closeSession(session);
		}
	}

	@Test
	public void testBlankStringNotLikeNull() {
		String sql = _SQL_NOT_LIKE_NULL;

		if (isHypersonic()) {
			sql = transformHypersonicSQL(sql);
		}

		Session session = _sessionFactory.openSession();

		try {
			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			QueryPos qPos = QueryPos.getInstance(sqlQuery);

			qPos.add(StringPool.BLANK);

			List<Object> list = sqlQuery.list();

			if (isSybase()) {
				Assert.assertFalse(list.isEmpty());
			}
			else {
				Assert.assertTrue(list.isEmpty());
			}
		}
		finally {
			_sessionFactory.closeSession(session);
		}
	}

	@Test
	public void testNullEqualsNull() {
		String sql = _SQL_EQUALS_NULL;

		if (isSybase()) {
			sql = transformSybaseSQL(sql);
		}
		else if (isHypersonic()) {
			sql = transformHypersonicSQL(sql);
		}

		Session session = _sessionFactory.openSession();

		try {
			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			QueryPos qPos = QueryPos.getInstance(sqlQuery);

			qPos.add((Object)null);

			List<Object> list = sqlQuery.list();

			if (isSybase()) {
				Assert.assertFalse(list.isEmpty());
			}
			else {
				Assert.assertTrue(list.isEmpty());
			}
		}
		finally {
			_sessionFactory.closeSession(session);
		}
	}

	@Test
	public void testNullIsNotNull() {
		String sql = _SQL_IS_NOT_NULL;

		if (isSybase()) {
			sql = transformSybaseSQL(sql);
		}

		Session session = _sessionFactory.openSession();

		try {
			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			QueryPos qPos = QueryPos.getInstance(sqlQuery);

			qPos.add((Object)null);

			List<Object> list = sqlQuery.list();

			Assert.assertTrue(list.isEmpty());
		}
		finally {
			_sessionFactory.closeSession(session);
		}
	}

	@Test
	public void testNullIsNull() {
		String sql = _SQL_IS_NULL;

		if (isSybase()) {
			sql = transformSybaseSQL(sql);
		}

		Session session = _sessionFactory.openSession();

		try {
			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			QueryPos qPos = QueryPos.getInstance(sqlQuery);

			qPos.add((Object)null);

			List<Object> list = sqlQuery.list();

			Assert.assertFalse(list.isEmpty());
		}
		finally {
			_sessionFactory.closeSession(session);
		}
	}

	@Test
	public void testNullLikeNull() {
		String sql = _SQL_LIKE_NULL;

		if (isHypersonic()) {
			sql = transformHypersonicSQL(sql);
		}

		Session session = _sessionFactory.openSession();

		try {
			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			QueryPos qPos = QueryPos.getInstance(sqlQuery);

			qPos.add((Object)null);

			List<Object> list = sqlQuery.list();

			Assert.assertTrue(list.isEmpty());
		}
		finally {
			_sessionFactory.closeSession(session);
		}
	}

	@Test
	public void testNullNotEqualsNull() {
		String sql = _SQL_NOT_EQUALS_NULL;

		if (isSybase()) {
			sql = transformSybaseSQL(sql);
		}
		else if (isHypersonic()) {
			sql = transformHypersonicSQL(sql);
		}

		Session session = _sessionFactory.openSession();

		try {
			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			QueryPos qPos = QueryPos.getInstance(sqlQuery);

			qPos.add((Object)null);

			List<Object> list = sqlQuery.list();

			Assert.assertTrue(list.isEmpty());
		}
		finally {
			_sessionFactory.closeSession(session);
		}
	}

	@Test
	public void testNullNotLikeNull() {
		String sql = _SQL_NOT_LIKE_NULL;

		if (isHypersonic()) {
			sql = transformHypersonicSQL(sql);
		}

		Session session = _sessionFactory.openSession();

		try {
			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			QueryPos qPos = QueryPos.getInstance(sqlQuery);

			qPos.add((Object)null);

			List<Object> list = sqlQuery.list();

			if (isSybase()) {
				Assert.assertFalse(list.isEmpty());
			}
			else {
				Assert.assertTrue(list.isEmpty());
			}
		}
		finally {
			_sessionFactory.closeSession(session);
		}
	}

	@Test
	public void testZeroEqualsNull() {
		String sql = _SQL_EQUALS_NULL;

		if (isHypersonic()) {
			sql = transformHypersonicSQL(sql);
		}

		Session session = _sessionFactory.openSession();

		try {
			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			QueryPos qPos = QueryPos.getInstance(sqlQuery);

			qPos.add(0);

			List<Object> list = sqlQuery.list();

			Assert.assertTrue(list.isEmpty());
		}
		finally {
			_sessionFactory.closeSession(session);
		}
	}

	@Test
	public void testZeroIsNotNull() {
		Session session = _sessionFactory.openSession();

		try {
			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(
				_SQL_IS_NOT_NULL);

			QueryPos qPos = QueryPos.getInstance(sqlQuery);

			qPos.add(0);

			List<Object> list = sqlQuery.list();

			Assert.assertFalse(list.isEmpty());
		}
		finally {
			_sessionFactory.closeSession(session);
		}
	}

	@Test
	public void testZeroIsNull() {
		Session session = _sessionFactory.openSession();

		try {
			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(
				_SQL_IS_NULL);

			QueryPos qPos = QueryPos.getInstance(sqlQuery);

			qPos.add(0);

			List<Object> list = sqlQuery.list();

			Assert.assertTrue(list.isEmpty());
		}
		finally {
			_sessionFactory.closeSession(session);
		}
	}

	@Test
	public void testZeroLikeNull() {
		String sql = _SQL_LIKE_NULL;

		if (isPostgreSQL()) {
			sql = transformPostgreSQL(sql);
		}
		else if (isSybase()) {
			sql = transformSybaseSQL(sql);
		}
		else if (isHypersonic()) {
			sql = transformHypersonicSQL(sql);
		}

		Session session = _sessionFactory.openSession();

		try {
			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			QueryPos qPos = QueryPos.getInstance(sqlQuery);

			qPos.add(0);

			List<Object> list = sqlQuery.list();

			Assert.assertTrue(list.isEmpty());
		}
		finally {
			_sessionFactory.closeSession(session);
		}
	}

	@Test
	public void testZeroNotEqualsNull() {
		String sql = _SQL_NOT_EQUALS_NULL;

		if (isHypersonic()) {
			sql = transformHypersonicSQL(sql);
		}

		Session session = _sessionFactory.openSession();

		try {
			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			QueryPos qPos = QueryPos.getInstance(sqlQuery);

			qPos.add(0);

			List<Object> list = sqlQuery.list();

			if (isSybase()) {
				Assert.assertFalse(list.isEmpty());
			}
			else {
				Assert.assertTrue(list.isEmpty());
			}
		}
		finally {
			_sessionFactory.closeSession(session);
		}
	}

	@Test
	public void testZeroNotLikeNull() {
		String sql = _SQL_NOT_LIKE_NULL;

		if (isPostgreSQL()) {
			sql = transformPostgreSQL(sql);
		}
		else if (isSybase()) {
			sql = transformSybaseSQL(sql);
		}
		else if (isHypersonic()) {
			sql = transformHypersonicSQL(sql);
		}

		Session session = _sessionFactory.openSession();

		try {
			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			QueryPos qPos = QueryPos.getInstance(sqlQuery);

			qPos.add(0);

			List<Object> list = sqlQuery.list();

			if (isSybase()) {
				Assert.assertFalse(list.isEmpty());
			}
			else {
				Assert.assertTrue(list.isEmpty());
			}
		}
		finally {
			_sessionFactory.closeSession(session);
		}
	}

	protected boolean isDBType(DBType dbType) {
		DB db = DBFactoryUtil.getDB();

		return dbType == db.getDBType();
	}

	protected boolean isHypersonic() {
		return isDBType(DBType.HYPERSONIC);
	}

	protected boolean isOracle() {
		return isDBType(DBType.ORACLE);
	}

	protected boolean isPostgreSQL() {
		return isDBType(DBType.POSTGRESQL);
	}

	protected boolean isSybase() {
		return isDBType(DBType.SYBASE);
	}

	protected String transformHypersonicSQL(String sql) {
		return sql.replace("NULL", "CAST_TEXT(NULL)");
	}

	protected String transformPostgreSQL(String sql) {
		return sql.replace("?", "CAST(? AS VARCHAR)");
	}

	protected String transformSybaseSQL(String sql) {
		return sql.replace("?", "CONVERT(VARCHAR, ?)");
	}

	private static final String _SQL_EQUALS_NULL =
		"SELECT DISTINCT 1 FROM Counter WHERE ? = NULL";

	private static final String _SQL_IS_NOT_NULL =
		"SELECT DISTINCT 1 FROM Counter WHERE ? IS NOT NULL";

	private static final String _SQL_IS_NULL =
		"SELECT DISTINCT 1 FROM Counter WHERE ? IS NULL";

	private static final String _SQL_LIKE_NULL =
		"SELECT DISTINCT 1 FROM Counter WHERE ? LIKE NULL";

	private static final String _SQL_NOT_EQUALS_NULL =
		"SELECT DISTINCT 1 FROM Counter WHERE ? != NULL";

	private static final String _SQL_NOT_LIKE_NULL =
		"SELECT DISTINCT 1 FROM Counter WHERE ? NOT LIKE NULL";

	private final SessionFactory _sessionFactory =
		(SessionFactory)PortalBeanLocatorUtil.locate("liferaySessionFactory");

}