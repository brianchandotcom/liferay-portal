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

package com.liferay.portal.test.rule.callback;

import com.liferay.portal.kernel.concurrent.ThreadPoolExecutor;
import com.liferay.portal.kernel.executor.PortalExecutorManager;
import com.liferay.portal.kernel.test.rule.callback.BaseTestCallback;
import com.liferay.registry.BasicRegistryImpl;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import org.junit.runner.Description;

/**
 * @author Michael C. Han
 */
public class PortalExecutorManagerTestCallback
	extends BaseTestCallback<Object, Object> {

	public static final PortalExecutorManagerTestCallback INSTANCE =
		new PortalExecutorManagerTestCallback();

	@Override
	public void afterClass(Description description, Object o) {
		_portalExecutorManager.shutdown(true);
	}

	protected class MockPortalExecutorManager implements PortalExecutorManager {

		@Override
		public ThreadPoolExecutor getPortalExecutor(String name) {
			return _threadPoolExecutor;
		}

		@Override
		public ThreadPoolExecutor getPortalExecutor(
			String name, boolean createIfAbsent) {

			return _threadPoolExecutor;
		}

		@Override
		public ThreadPoolExecutor registerPortalExecutor(
			String name, ThreadPoolExecutor threadPoolExecutor) {

			return _threadPoolExecutor;
		}

		@Override
		public void shutdown() {
			shutdown(false);
		}

		@Override
		public void shutdown(boolean interrupt) {
			if (interrupt) {
				_threadPoolExecutor.shutdownNow();
			}
			else {
				_threadPoolExecutor.shutdown();
			}
		}

		private final ThreadPoolExecutor _threadPoolExecutor =
			new ThreadPoolExecutor(0, 1);

	}

	private PortalExecutorManagerTestCallback() {
		RegistryUtil.setRegistry(new BasicRegistryImpl());

		Registry registry = RegistryUtil.getRegistry();

		_portalExecutorManager = new MockPortalExecutorManager();

		registry.registerService(
			PortalExecutorManager.class, _portalExecutorManager);
	}

	private final PortalExecutorManager _portalExecutorManager;

}