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

package com.liferay.portal.kernel.log;

import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.util.BasePortalLifecycle;
import com.liferay.portal.kernel.util.PortalLifecycle;
import com.liferay.portal.kernel.util.PortalLifecycleUtil;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Tina Tian
 */
public class LogContextRegistryUtil {

	public static Set<LogContext> getLogContexts() {
		return _logContexts;
	}

	public static void registerLogContext(LogContext logContext) {
		_logContexts.add(logContext);
	}

	public static void unregisterLogContext(LogContext logContext) {
		_logContexts.remove(logContext);
	}

	private static final Set<LogContext> _logContexts =
		Collections.newSetFromMap(new ConcurrentHashMap<>());

	private static class LogContextTrackerCustomizer
		implements ServiceTrackerCustomizer<LogContext, LogContext> {

		@Override
		public LogContext addingService(
			ServiceReference<LogContext> serviceReference) {

			BundleContext bundleContext = SystemBundleUtil.getBundleContext();

			LogContext logContext = bundleContext.getService(serviceReference);

			_logContexts.add(logContext);

			return logContext;
		}

		@Override
		public void modifiedService(
			ServiceReference<LogContext> serviceReference,
			LogContext logContext) {
		}

		@Override
		public void removedService(
			ServiceReference<LogContext> serviceReference,
			LogContext logContext) {

			_logContexts.remove(logContext);

			BundleContext bundleContext = SystemBundleUtil.getBundleContext();

			bundleContext.ungetService(serviceReference);
		}

	}

	static {
		PortalLifecycleUtil.register(
			new BasePortalLifecycle() {

				@Override
				protected void doPortalDestroy() {
					if (_serviceTracker != null) {
						_serviceTracker.close();
					}
				}

				@Override
				protected void doPortalInit() {
					_serviceTracker = new ServiceTracker<>(
						SystemBundleUtil.getBundleContext(), LogContext.class,
						new LogContextTrackerCustomizer());

					_serviceTracker.open();
				}

				private volatile ServiceTracker<LogContext, LogContext>
					_serviceTracker;

			},
			PortalLifecycle.METHOD_ALL);
	}

}