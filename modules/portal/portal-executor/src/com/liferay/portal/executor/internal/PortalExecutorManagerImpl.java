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

package com.liferay.portal.executor.internal;

import com.liferay.portal.executor.PortalExecutorFactory;
import com.liferay.portal.kernel.concurrent.FutureListener;
import com.liferay.portal.kernel.concurrent.NoticeableFuture;
import com.liferay.portal.kernel.concurrent.ThreadPoolExecutor;
import com.liferay.portal.kernel.executor.PortalExecutorManager;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Future;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Shuyang Zhou
 */
@Component(immediate = true, service = PortalExecutorManager.class)
public class PortalExecutorManagerImpl implements PortalExecutorManager {

	@Override
	public ThreadPoolExecutor getPortalExecutor(String name) {
		return getPortalExecutor(name, true);
	}

	@Override
	public ThreadPoolExecutor getPortalExecutor(
		String name, boolean createIfAbsent) {

		ThreadPoolExecutor threadPoolExecutor = _threadPoolExecutors.get(name);

		if ((threadPoolExecutor == null) && createIfAbsent) {
			threadPoolExecutor = _portalExecutorFactory.createPortalExecutor(
				name);

			ThreadPoolExecutor previousThreadPoolExecutor =
				registerPortalExecutor(name, threadPoolExecutor);

			if (previousThreadPoolExecutor != null) {
				threadPoolExecutor.shutdown();

				threadPoolExecutor = previousThreadPoolExecutor;
			}
		}

		return threadPoolExecutor;
	}

	@Override
	public ThreadPoolExecutor registerPortalExecutor(
		String name, ThreadPoolExecutor threadPoolExecutor) {

		ThreadPoolExecutor previousThreadPoolExecutor =
			_threadPoolExecutors.putIfAbsent(name, threadPoolExecutor);

		if (previousThreadPoolExecutor == null) {
			NoticeableFuture<Void> terminationNoticeableFuture =
				threadPoolExecutor.terminationNoticeableFuture();

			terminationNoticeableFuture.addFutureListener(
				new UnregisterFutureListener(name));
		}

		return previousThreadPoolExecutor;
	}

	@Reference
	public void setPortalExecutorFactory(
		PortalExecutorFactory portalExecutorFactory) {

		_portalExecutorFactory = portalExecutorFactory;
	}

	public void setPortalExecutors(
		Map<String, ThreadPoolExecutor> threadPoolExecutors) {

		if (threadPoolExecutors != null) {
			shutdown(true);

			for (Map.Entry<String, ThreadPoolExecutor> entry :
					threadPoolExecutors.entrySet()) {

				registerPortalExecutor(entry.getKey(), entry.getValue());
			}
		}
	}

	@Override
	public void shutdown() {
		shutdown(false);
	}

	@Override
	public void shutdown(boolean interrupt) {
		for (ThreadPoolExecutor threadPoolExecutor :
				_threadPoolExecutors.values()) {

			if (interrupt) {
				threadPoolExecutor.shutdownNow();
			}
			else {
				threadPoolExecutor.shutdown();
			}
		}
	}

	@Activate
	protected void activate(ComponentContext componentContext) {
		_componentContext = componentContext;

		BundleContext bundleContext = _componentContext.getBundleContext();

		_serviceTracker = new ServiceTracker<>(
			bundleContext, ThreadPoolExecutor.class,
			new ThreadPoolExecutorServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		shutdown(true);

		_serviceTracker.close();

		_serviceTracker = null;

		_componentContext = null;
	}

	protected class UnregisterFutureListener implements FutureListener<Void> {

		@Override
		public void complete(Future<Void> future) {
			_threadPoolExecutors.remove(name);
		}

		protected UnregisterFutureListener(String name) {
			this.name = name;
		}

		protected final String name;

	}

	private ComponentContext _componentContext;
	private PortalExecutorFactory _portalExecutorFactory;
	private ServiceTracker<ThreadPoolExecutor, ThreadPoolExecutor>
		_serviceTracker;
	private final ConcurrentMap<String, ThreadPoolExecutor>
		_threadPoolExecutors = new ConcurrentHashMap<>();

	private class ThreadPoolExecutorServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<ThreadPoolExecutor, ThreadPoolExecutor> {

		@Override
		public ThreadPoolExecutor addingService(
			ServiceReference<ThreadPoolExecutor> serviceReference) {

			BundleContext bundleContext = _componentContext.getBundleContext();

			ThreadPoolExecutor threadPoolExecutor = bundleContext.getService(
				serviceReference);

			String name = (String)serviceReference.getProperty("name");

			if (Validator.isNotNull(name)) {
				registerPortalExecutor(name, threadPoolExecutor);
			}

			return threadPoolExecutor;
		}

		@Override
		public void modifiedService(
			ServiceReference<ThreadPoolExecutor> serviceReference,
			ThreadPoolExecutor service) {
		}

		@Override
		public void removedService(
			ServiceReference<ThreadPoolExecutor> serviceReference,
			ThreadPoolExecutor service) {
		}

	}

}