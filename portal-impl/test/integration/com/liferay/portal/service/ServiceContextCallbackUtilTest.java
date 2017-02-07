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

package com.liferay.portal.service;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextCallbackUtil;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.test.LayoutTestUtil;
import com.liferay.portlet.exportimport.staging.ProxiedLayoutsThreadLocal;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Akos Thurzo
 */
public class ServiceContextCallbackUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_layout = LayoutTestUtil.addLayout(_group);
	}

	@Test
	public void testConcurrentAccess() throws Exception {
		ServiceContextCallbackUtil.registerPopCallback(
			"testClear",
			() -> {
				ProxiedLayoutsThreadLocal.clearProxiedLayouts();

				if (_log.isDebugEnabled()) {
					_log.debug(
						Thread.currentThread().getName() +
							" pop callback finished.");
				}

				return null;
			});

		ServiceContextCallbackUtil.registerPushCallback(
			"testClear",
			() -> {
				ProxiedLayoutsThreadLocal.clearProxiedLayouts();

				if (_log.isDebugEnabled()) {
					_log.debug(
						Thread.currentThread().getName() +
							" push callback finished.");
				}

				return null;
			});

		final Lock lock = new Lock();

		Runnable task1 = () -> {
			String threadName = Thread.currentThread().getName();

			if (_log.isDebugEnabled()) {
				_log.debug(threadName + " started.");
			}

			ServiceContextThreadLocal.pushServiceContext(new ServiceContext());

			Map<Layout, Object> proxiedLayouts1 =
				ProxiedLayoutsThreadLocal.getProxiedLayouts();

			proxiedLayouts1.put(_layout, threadName);

			try {
				synchronized (lock) {
					while (!lock.isPushCalled()) {
						lock.wait();
					}

					// this should be called after thread2.push

					Map<Layout, Object> proxiedLayouts2 =
						ProxiedLayoutsThreadLocal.getProxiedLayouts();

					Assert.assertEquals(
						proxiedLayouts1.get(_layout),
						proxiedLayouts2.get(_layout));

					ServiceContextThreadLocal.popServiceContext();
				}
			}
			catch (InterruptedException ie) {
				_log.error(threadName, ie);
			}
		};

		Thread thread1 = new Thread(task1);

		thread1.setName("thread1");
		thread1.start();

		Runnable task2 = () -> {
			String threadName = Thread.currentThread().getName();

			if (_log.isDebugEnabled()) {
				_log.debug(threadName + " started.");
			}

			Map<Layout, Object> proxiedLayouts1 =
				ProxiedLayoutsThreadLocal.getProxiedLayouts();

			proxiedLayouts1.put(_layout, threadName);

			synchronized (lock) {
				ServiceContextThreadLocal.pushServiceContext(
					new ServiceContext());
				lock.setPushCalled(true);
				lock.notify();
			}

			ServiceContextThreadLocal.popServiceContext();
		};

		Thread thread2 = new Thread(task2);

		thread2.setName("thread2");
		thread2.start();

		thread1.join();
		thread2.join();

		ServiceContextCallbackUtil.unRegisterPopCallback("testClear");
		ServiceContextCallbackUtil.unRegisterPushCallback("testClear");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ServiceContextCallbackUtilTest.class);

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

	private class Lock {

		public boolean isPushCalled() {
			return _pushCalled;
		}

		public void setPushCalled(boolean pushCalled) {
			_pushCalled = pushCalled;
		}

		private boolean _pushCalled;

	}

}