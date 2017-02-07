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

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Laszlo Csontos
 */
public class DB2DialectTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), TransactionalTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		DB db = DBManagerUtil.getDB();

		Assume.assumeTrue(db.getDBType() == DBType.DB2);
	}

	@Test
	public void testPagingWithNegativeStart() {
		testPaging(_SQL, -10, 20, 20);
	}

	@Test
	public void testPagingWithNegativeStartAndNegativeEnd() {
		testPaging(_SQL, -10, -5, 0);
	}

	@Test
	public void testPagingWithOffset() {
		testPaging(_SQL, 10, 30, 20);
	}

	@Test
	public void testPagingWithoutOffset() {
		testPaging(_SQL, 0, 20, 20);
	}

	@Test
	public void testPagingWithStartAfterEnd() {
		testPaging(_SQL, 10, 5, 0);
	}

	protected void testPaging(
		String sql, int start, int end, int expectedResultSize) {

		Session session = null;

		try {
			session = _sessionFactory.openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			List<?> result = QueryUtil.list(
				q, _sessionFactory.getDialect(), start, end);

			Assert.assertNotNull(result);
			Assert.assertEquals(
				result.toString(), expectedResultSize, result.size());
		}
		finally {
			_sessionFactory.closeSession(session);
		}
	}

	private static final String _SQL =
		"SELECT tabname FROM syscat.tables WHERE tabschema = 'SYSIBM' ORDER " +
			"BY tabname";

	private final SessionFactory _sessionFactory =
		(SessionFactory)PortalBeanLocatorUtil.locate("liferaySessionFactory");

}