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

package com.liferay.view.count.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.spring.aop.AopInvocationHandler;
import com.liferay.portal.spring.transaction.DefaultTransactionExecutor;
import com.liferay.portal.spring.transaction.TransactionAttributeAdapter;
import com.liferay.portal.spring.transaction.TransactionAttributeBuilder;
import com.liferay.portal.spring.transaction.TransactionInterceptor;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.view.count.model.ViewCountEntry;
import com.liferay.view.count.service.ViewCountEntryLocalService;
import com.liferay.view.count.service.persistence.ViewCountEntryFinder;
import com.liferay.view.count.service.persistence.ViewCountEntryPK;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Preston Crary
 */
@RunWith(Arquillian.class)
public class ViewCountEntryFinderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_className = _classNameLocalService.getClassName(
			ViewCountEntryFinderTest.class.getName());

		_viewCountEntry = _viewCountEntryLocalService.createViewCountEntry(
			new ViewCountEntryPK(
				TestPropsValues.getCompanyId(), _className.getClassNameId(),
				-1));

		_viewCountEntry.setCompanyId(TestPropsValues.getCompanyId());

		_viewCountEntryLocalService.addViewCountEntry(_viewCountEntry);

		Runtime runtime = Runtime.getRuntime();

		_executorService = Executors.newFixedThreadPool(
			runtime.availableProcessors());
	}

	@After
	public void tearDown() {
		_executorService.shutdownNow();
	}

	@Test
	public void testIncrementViewCount() throws Exception {
		AopInvocationHandler aopInvocationHandler =
			ProxyUtil.fetchInvocationHandler(
				_viewCountEntryLocalService, AopInvocationHandler.class);

		Assert.assertNotNull(aopInvocationHandler);

		TransactionInterceptor transactionInterceptor =
			ReflectionTestUtil.getFieldValue(
				aopInvocationHandler, "_transactionInterceptor");

		DefaultTransactionExecutor transactionExecutor =
			ReflectionTestUtil.getFieldValue(
				transactionInterceptor, "_transactionHandler");

		List<Callable<Void>> callables = new ArrayList<>(_INCREMENTS_COUNT);

		for (int i = 0; i < _INCREMENTS_COUNT; i++) {
			callables.add(
				() -> {
					try {
						return transactionExecutor.execute(
							_transactionAttributeAdapter,
							() -> {
								_viewCountEntryFinder.incrementViewCount(
									_viewCountEntry.getCompanyId(),
									_viewCountEntry.getClassNameId(),
									_viewCountEntry.getClassPK(), 1);

								return null;
							});
					}
					catch (Throwable t) {
						return ReflectionUtil.throwException(t);
					}
				});
		}

		List<Future<Void>> futures = _executorService.invokeAll(callables);

		for (Future<Void> future : futures) {
			future.get();
		}

		ViewCountEntry reloadedViewCountEntry =
			_viewCountEntryLocalService.fetchViewCountEntry(
				_viewCountEntry.getPrimaryKey());

		Assert.assertNotNull(reloadedViewCountEntry);

		Assert.assertEquals(
			_INCREMENTS_COUNT, reloadedViewCountEntry.getViewCount());
	}

	private static final int _INCREMENTS_COUNT = 1000;

	@Inject
	private static ClassNameLocalService _classNameLocalService;

	private static final TransactionAttributeAdapter
		_transactionAttributeAdapter = new TransactionAttributeAdapter(
			TransactionAttributeBuilder.build(
				Propagation.REQUIRES_NEW, new Class<?>[] {Exception.class}));

	@Inject
	private static ViewCountEntryFinder _viewCountEntryFinder;

	@Inject
	private static ViewCountEntryLocalService _viewCountEntryLocalService;

	@DeleteAfterTestRun
	private ClassName _className;

	private ExecutorService _executorService;

	@DeleteAfterTestRun
	private ViewCountEntry _viewCountEntry;

}