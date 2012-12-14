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

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;

import java.util.List;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Laszlo Csontos
 */
@ExecutionTestListeners(listeners = { PersistenceExecutionTestListener.class} )
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class DB2DialectTest {

	@Before
	public void setUp() throws Exception {
		Assume.assumeTrue(DB.TYPE_DB2.equals(DBFactoryUtil.getDB().getType()));
	}

	@Test
	public void testPagingWithOffset() {
		doPaging(_MOCK_SQL, 10, 20);
	}

	@Test
	public void testPagingWithoutOffset() {
		doPaging(_MOCK_SQL, 0, 20);
	}

	protected void doPaging(String sql, int offset, int limit) {

		Session session = null;

		List<?> result = null;

		try {
			session = _liferaySessionFactory.openSession();

			SQLQuery q = session.createSQLQuery(sql);

			result = QueryUtil.list(
				q, _liferaySessionFactory.getDialect(), offset, offset + limit);
		}
		finally {
			_liferaySessionFactory.closeSession(session);
		}

		Assert.assertNotNull(result);
		Assert.assertEquals(limit, result.size());
	}

	private static final String _MOCK_SQL =
		"SELECT tabname FROM syscat.tables " +
		"WHERE tabschema = 'SYSIBM' ORDER BY tabname";

	private SessionFactory _liferaySessionFactory =
		(SessionFactory) PortalBeanLocatorUtil.locate("liferaySessionFactory");

}