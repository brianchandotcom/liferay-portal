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

package com.liferay.portal.dao.orm.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.service.persistence.UserPersistence;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.model.impl.ReleaseImpl;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Shuyang Zhou
 */
@RunWith(Arquillian.class)
public class SQLDateTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), TransactionalTestRule.INSTANCE);

	@Before
	public void setUp() {
		_sessionFactory = ReflectionTestUtil.getFieldValue(
			_userPersistence, "_sessionFactory");
	}

	@Test
	public void testMillisecondsHibernate() {
		long time = readTimeHibernate() / Time.SECOND * Time.SECOND;

		for (int i = 0; i < Time.SECOND; i++) {
			writeTimeHibernate(time);

			Assert.assertEquals(time++, readTimeHibernate());
		}
	}

	@Test
	public void testMillisecondsJDBC() throws SQLException {
		long time = readTimeJDBC() / Time.SECOND * Time.SECOND;

		for (int i = 0; i < Time.SECOND; i++) {
			writeTimeJDBC(time);

			Assert.assertEquals(time++, readTimeJDBC());
		}
	}

	protected long readTimeHibernate() {
		Session session = _sessionFactory.openSession();

		try {
			Release release = (Release)session.get(ReleaseImpl.class, 1L);

			Date date = release.getModifiedDate();

			return date.getTime();
		}
		finally {
			_sessionFactory.closeSession(session);
		}
	}

	protected long readTimeJDBC() throws SQLException {
		try (Connection connection = DataAccess.getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(
				_READ_RELEASE_MODIFIED_DATE)) {

			Assert.assertTrue(resultSet.next());

			Timestamp timestamp = resultSet.getTimestamp("modifiedDate");

			Assert.assertFalse(resultSet.next());

			return timestamp.getTime();
		}
	}

	protected void writeTimeHibernate(long time) {
		Session session = _sessionFactory.openSession();

		try {
			Release release = (Release)session.get(ReleaseImpl.class, 1L);

			release.setModifiedDate(new Timestamp(time));

			session.saveOrUpdate(release);

			session.flush();

			session.clear();
		}
		finally {
			_sessionFactory.closeSession(session);
		}
	}

	protected void writeTimeJDBC(long time) throws SQLException {
		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				_WRITE_RELEASE_MODIFIED_DATE)) {

			preparedStatement.setTimestamp(1, new Timestamp(time));

			Assert.assertEquals(1, preparedStatement.executeUpdate());
		}
	}

	private static final String _READ_RELEASE_MODIFIED_DATE =
		"select modifiedDate from Release_ where releaseId = 1";

	private static final String _WRITE_RELEASE_MODIFIED_DATE =
		"update Release_ set modifiedDate=? where releaseId = 1";

	private SessionFactory _sessionFactory;

	@Inject
	private UserPersistence _userPersistence;

}