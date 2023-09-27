/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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