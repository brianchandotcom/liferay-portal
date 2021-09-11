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

package com.liferay.exportimport.kernel.lifecycle;

import com.liferay.portal.kernel.module.util.SystemBundleUtil;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Daniel Kocsis
 */
public class ExportImportLifecycleEventListenerRegistryUtil {

	public static Set<ExportImportLifecycleListener>
		getAsyncExportImportLifecycleListeners() {

		return _asyncExportImportLifecycleListeners;
	}

	public static Set<ExportImportLifecycleListener>
		getSyncExportImportLifecycleListeners() {

		return _syncExportImportLifecycleListeners;
	}

	private static final Set<ExportImportLifecycleListener>
		_asyncExportImportLifecycleListeners = Collections.newSetFromMap(
			new ConcurrentHashMap<>());
	private static final BundleContext _bundleContext =
		SystemBundleUtil.getBundleContext();
	private static final ServiceTracker
		<ExportImportLifecycleListener, ExportImportLifecycleListener>
			_serviceTracker;
	private static final Set<ExportImportLifecycleListener>
		_syncExportImportLifecycleListeners = Collections.newSetFromMap(
			new ConcurrentHashMap<>());

	private static class ExportImportLifecycleListenerServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<ExportImportLifecycleListener, ExportImportLifecycleListener> {

		@Override
		public ExportImportLifecycleListener addingService(
			ServiceReference<ExportImportLifecycleListener> serviceReference) {

			ExportImportLifecycleListener exportImportLifecycleListener =
				_bundleContext.getService(serviceReference);

			if (exportImportLifecycleListener instanceof
					ProcessAwareExportImportLifecycleListener) {

				exportImportLifecycleListener =
					ExportImportLifecycleListenerFactoryUtil.create(
						(ProcessAwareExportImportLifecycleListener)
							exportImportLifecycleListener);
			}
			else if (exportImportLifecycleListener instanceof
						EventAwareExportImportLifecycleListener) {

				exportImportLifecycleListener =
					ExportImportLifecycleListenerFactoryUtil.create(
						(EventAwareExportImportLifecycleListener)
							exportImportLifecycleListener);
			}

			if (exportImportLifecycleListener.isParallel()) {
				_asyncExportImportLifecycleListeners.add(
					exportImportLifecycleListener);
			}
			else {
				_syncExportImportLifecycleListeners.add(
					exportImportLifecycleListener);
			}

			return exportImportLifecycleListener;
		}

		@Override
		public void modifiedService(
			ServiceReference<ExportImportLifecycleListener> serviceReference,
			ExportImportLifecycleListener exportImportLifecycleListener) {
		}

		@Override
		public void removedService(
			ServiceReference<ExportImportLifecycleListener> serviceReference,
			ExportImportLifecycleListener exportImportLifecycleListener) {

			_bundleContext.ungetService(serviceReference);

			if (exportImportLifecycleListener.isParallel()) {
				_asyncExportImportLifecycleListeners.remove(
					exportImportLifecycleListener);
			}
			else {
				_syncExportImportLifecycleListeners.remove(
					exportImportLifecycleListener);
			}
		}

	}

	static {
		_serviceTracker = new ServiceTracker<>(
			_bundleContext, ExportImportLifecycleListener.class,
			new ExportImportLifecycleListenerServiceTrackerCustomizer());

		_serviceTracker.open();
	}

}