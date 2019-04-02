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

package com.liferay.portal.dao.orm.hibernate.event.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.dao.orm.hibernate.SessionFactoryImpl;
import com.liferay.portal.dao.orm.hibernate.VerifySessionFactoryWrapper;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.service.persistence.UserPersistence;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.model.impl.ClassNameImpl;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.hibernate.EmptyInterceptor;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.hibernate.event.AutoFlushEventListener;
import org.hibernate.event.EventListeners;
import org.hibernate.event.FlushEventListener;
import org.hibernate.event.def.DefaultAutoFlushEventListener;
import org.hibernate.event.def.DefaultFlushEventListener;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Matthew Tambara
 */
@RunWith(Arquillian.class)
public class NestableFlushEventListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		SessionFactoryImpl sessionFactoryImpl = null;

		Object liferaySessionFactory = ReflectionTestUtil.getFieldValue(
			_userPersistence, "_sessionFactory");

		if (liferaySessionFactory instanceof VerifySessionFactoryWrapper) {
			sessionFactoryImpl = ReflectionTestUtil.getFieldValue(
				liferaySessionFactory, "_sessionFactoryImpl");
		}
		else {
			sessionFactoryImpl = (SessionFactoryImpl)liferaySessionFactory;
		}

		_sessionFactoryImpl =
			(org.hibernate.impl.SessionFactoryImpl)
				sessionFactoryImpl.getSessionFactoryImplementor();
	}

	@Before
	public void setUp() throws Exception {
		Session session = _sessionFactoryImpl.openSession();

		Query query = session.createQuery(
			"SELECT className FROM ClassName className");

		List<ClassName> classNames = query.list();

		session.close();

		_assignKeys(classNames, _keys);
		_assignValues(classNames, _values);
	}

	@Test
	public void testDefaultAutoFlushEventListener() throws Exception {
		EventListeners eventListeners = _sessionFactoryImpl.getEventListeners();

		AutoFlushEventListener[] autoFlushEventListeners =
			eventListeners.getAutoFlushEventListeners();

		eventListeners.setAutoFlushEventListeners(
			new AutoFlushEventListener[] {new DefaultAutoFlushEventListener()});

		try {
			testNestableAutoFlushEventListener();

			Assert.fail();
		}
		catch (IndexOutOfBoundsException ioobe) {
		}
		finally {
			eventListeners.setAutoFlushEventListeners(autoFlushEventListeners);
		}
	}

	@Test
	public void testDefaultFlushEventListener() throws Exception {
		EventListeners eventListeners = _sessionFactoryImpl.getEventListeners();

		FlushEventListener[] flushEventListeners =
			eventListeners.getFlushEventListeners();

		eventListeners.setFlushEventListeners(
			new FlushEventListener[] {new DefaultFlushEventListener()});

		try {
			testNestableFlushEventListener();

			Assert.fail();
		}
		catch (IndexOutOfBoundsException ioobe) {
		}
		finally {
			eventListeners.setFlushEventListeners(flushEventListeners);
		}
	}

	@Test
	public void testNestableAutoFlushEventListener() throws Exception {
		Session session = _prepareSession();

		Transaction transaction = session.beginTransaction();

		try {
			session.merge(_className1);
			session.merge(_className2);

			Query query = session.createQuery(
				"SELECT className FROM ClassName className");

			List<?> results = query.list();

			Assert.assertFalse(results.toString(), results.isEmpty());
		}
		finally {
			transaction.commit();

			session.close();
		}
	}

	@Test
	public void testNestableFlushEventListener() throws Exception {
		Session session = _prepareSession();

		Transaction transaction = session.beginTransaction();

		try {
			session.merge(_className1);
			session.merge(_className2);

			session.flush();
		}
		finally {
			transaction.commit();

			session.close();
		}
	}

	private void _assignKeys(List<ClassName> classNames, long[] keys) {
		Stream<ClassName> classNameStream = classNames.stream();

		Stream<Long> classNameIdStream = classNameStream.map(
			ClassName::getClassNameId);

		List<Long> classNameIds = classNameIdStream.collect(
			Collectors.toList());

		for (int i = 0; i < keys.length; i++) {
			long key = RandomTestUtil.nextLong();

			if (!classNameIds.contains(key)) {
				classNameIds.add(key);

				keys[i] = key;
			}
		}
	}

	private void _assignValues(List<ClassName> classNames, String[] values) {
		Stream<ClassName> classNameStream = classNames.stream();

		Stream<String> classNameValueStream = classNameStream.map(
			ClassName::getValue);

		List<String> classNameValues = classNameValueStream.collect(
			Collectors.toList());

		for (int i = 0; i < values.length; i++) {
			String value = RandomTestUtil.randomString();

			if (!classNameValues.contains(value)) {
				classNameValues.add(value);

				values[i] = value;
			}
		}
	}

	private Session _prepareSession() throws Exception {
		Session session = _sessionFactoryImpl.openSession(
			new EmptyInterceptor() {

				@Override
				public String getEntityName(Object object) {
					if (object instanceof TestClassNameImpl) {
						return ClassNameImpl.class.getName();
					}

					return super.getEntityName(object);
				}

			});

		Transaction transaction = session.beginTransaction();

		try {
			_className1 = new TestClassNameImpl(session);

			_className1.setPrimaryKey(_keys[0]);
			_className1.setValue(_values[0]);

			session.save(_className1);

			_className2 = new ClassNameImpl();

			_className2.setPrimaryKey(_keys[1]);
			_className2.setValue(_values[1]);

			session.save(_className2);
		}
		finally {
			transaction.commit();
		}

		_className1.setValue(_values[2]);

		_className1.setMvccVersion(_className1.getMvccVersion() + 1);

		_className2.setValue(_values[3]);

		_className2.setMvccVersion(_className1.getMvccVersion() + 1);

		return session;
	}

	private static org.hibernate.impl.SessionFactoryImpl _sessionFactoryImpl;

	@Inject
	private static UserPersistence _userPersistence;

	@DeleteAfterTestRun
	private ClassName _className1;

	@DeleteAfterTestRun
	private ClassName _className2;

	private final long[] _keys = new long[2];
	private final String[] _values = new String[4];

	private class TestClassNameImpl extends ClassNameImpl {

		@Override
		public CacheModel<ClassName> toCacheModel() {
			Query query = _session.createQuery(
				"SELECT release FROM Release release");

			List<?> results = query.list();

			Assert.assertFalse(results.toString(), results.isEmpty());

			return super.toCacheModel();
		}

		private TestClassNameImpl(Session session) {
			_session = session;
		}

		private final Session _session;

	}

}