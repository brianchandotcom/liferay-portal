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

package com.liferay.view.count.service.impl.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.lang.SafeClosable;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.messaging.proxy.ProxyModeThreadLocal;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.view.count.model.ViewCountEntry;
import com.liferay.view.count.service.ViewCountEntryLocalService;
import com.liferay.view.count.service.persistence.ViewCountEntryFinder;
import com.liferay.view.count.service.persistence.ViewCountEntryPK;

import java.lang.reflect.InvocationTargetException;

import java.util.Objects;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.FutureTask;

import org.apache.log4j.Level;

import org.hibernate.util.JDBCExceptionReporter;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Shuyang Zhou
 */
@RunWith(Arquillian.class)
public class ViewCountEntryLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() {
		_className = _classNameLocalService.getClassName(
			ViewCountEntryLocalServiceTest.class.getName());
	}

	@Test
	public void testLazyCreationWithRaceCondition() throws Throwable {
		DB db = DBManagerUtil.getDB();

		Assume.assumeFalse(
			"HSQL does not allow concurrent Session assess, skip test.",
			db.getDBType() == DBType.HYPERSONIC);

		long classPK = 0;
		int viewCount = 100;

		ViewCountEntryPK viewCountEntryPK = new ViewCountEntryPK(
			TestPropsValues.getCompanyId(), _className.getClassNameId(),
			classPK);

		Assert.assertNull(
			_viewCountEntryLocalService.fetchViewCountEntry(viewCountEntryPK));

		SessionFactory sessionFactory = ReflectionTestUtil.getFieldValue(
			_viewCountEntryFinder, "_sessionFactory");

		CyclicBarrier cyclicBarrier = new CyclicBarrier(2);

		ReflectionTestUtil.setFieldValue(
			_viewCountEntryFinder, "_sessionFactory",
			_createSessionFactoryProxy(sessionFactory, cyclicBarrier));

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					JDBCExceptionReporter.class.getName(), Level.OFF)) {

			FutureTask<Void> futureTask = new FutureTask<>(
				() -> {
					try (SafeClosable safeClosable =
							ProxyModeThreadLocal.setWithSafeClosable(true)) {

						_viewCountEntryLocalService.incrementViewCount(
							TestPropsValues.getCompanyId(),
							_className.getClassNameId(), classPK, viewCount);
					}

					return null;
				});

			Thread thread = new Thread(
				futureTask, "Inner view count incrementer");

			thread.start();

			_viewCountEntryLocalService.incrementViewCount(
				TestPropsValues.getCompanyId(), _className.getClassNameId(),
				classPK, viewCount);

			futureTask.get();
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				_viewCountEntryFinder, "_sessionFactory", sessionFactory);
		}

		_viewCountEntry = _viewCountEntryLocalService.getViewCountEntry(
			viewCountEntryPK);

		Assert.assertEquals(viewCount * 2, _viewCountEntry.getViewCount());
	}

	private Object _createSessionFactoryProxy(
		SessionFactory sessionFactory, CyclicBarrier cyclicBarrier) {

		return ProxyUtil.newProxyInstance(
			SessionFactory.class.getClassLoader(),
			new Class<?>[] {SessionFactory.class},
			(proxy, method, args) -> {
				if (Objects.equals("openSession", method.getName())) {
					return _createSessionProxy(
						sessionFactory.openSession(), cyclicBarrier);
				}

				return method.invoke(sessionFactory, args);
			});
	}

	private Object _createSessionProxy(
		Session session, CyclicBarrier cyclicBarrier) {

		return ProxyUtil.newProxyInstance(
			Session.class.getClassLoader(), new Class<?>[] {Session.class},
			(proxy, method, args) -> {
				if (Objects.equals("flush", method.getName())) {
					cyclicBarrier.await();
				}

				try {
					return method.invoke(session, args);
				}
				catch (InvocationTargetException ite) {
					throw ite.getCause();
				}
			});
	}

	@DeleteAfterTestRun
	private static ClassName _className;

	@Inject
	private static ClassNameLocalService _classNameLocalService;

	@Inject
	private static ViewCountEntryFinder _viewCountEntryFinder;

	@Inject
	private static ViewCountEntryLocalService _viewCountEntryLocalService;

	@DeleteAfterTestRun
	private ViewCountEntry _viewCountEntry;

}