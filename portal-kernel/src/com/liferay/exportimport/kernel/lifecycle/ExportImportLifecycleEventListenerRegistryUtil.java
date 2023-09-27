/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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