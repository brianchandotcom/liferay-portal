/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal;

import com.liferay.frontend.data.set.SystemFDSEntry;
import com.liferay.frontend.data.set.SystemFDSEntryRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Daniel Sanz
 */
@Component(service = SystemFDSEntryRegistry.class)
public class SystemFDSEntryRegistryImpl implements SystemFDSEntryRegistry {

	@Override
	public Map<String, SystemFDSEntry> getSystemFDSEntries() {
		return _systemFDSEntries.get();
	}

	@Override
	public SystemFDSEntry getSystemFDSEntry(String fdsName) {
		ServiceTrackerCustomizerFactory.ServiceWrapper<SystemFDSEntry>
			serviceWrapper = _serviceTrackerMap.getService(fdsName);

		if (serviceWrapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No frontend data set system entry is associated with " +
						fdsName);
			}

			return null;
		}

		return serviceWrapper.getService();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_serviceTracker = new ServiceTracker<>(
			_bundleContext, SystemFDSEntry.class,
			new DataSetServiceTrackerCustomizer());

		_serviceTracker.open();

		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, SystemFDSEntry.class, "frontend.data.set.name",
			ServiceTrackerCustomizerFactory.<SystemFDSEntry>serviceWrapper(
				bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();

		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SystemFDSEntryRegistryImpl.class);

	private BundleContext _bundleContext;
	private ServiceTracker<SystemFDSEntry, SystemFDSEntry> _serviceTracker;
	private ServiceTrackerMap
		<String, ServiceTrackerCustomizerFactory.ServiceWrapper<SystemFDSEntry>>
			_serviceTrackerMap;
	private final AtomicReference<Map<String, SystemFDSEntry>>
		_systemFDSEntries = new AtomicReference<>();

	private class DataSetServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<SystemFDSEntry, SystemFDSEntry> {

		@Override
		public SystemFDSEntry addingService(
			ServiceReference<SystemFDSEntry> serviceReference) {

			SystemFDSEntry systemFDSEntry = _bundleContext.getService(
				serviceReference);

			String fdsName = GetterUtil.getString(
				serviceReference.getProperty("frontend.data.set.name"));

			_systemFDSEntries.updateAndGet(
				systemFDSEntries -> {
					if (systemFDSEntries == null) {
						systemFDSEntries = new HashMap<>();
					}
					else {
						systemFDSEntries = new HashMap<>(systemFDSEntries);
					}

					systemFDSEntries.put(fdsName, systemFDSEntry);

					return systemFDSEntries;
				});

			return systemFDSEntry;
		}

		@Override
		public void modifiedService(
			ServiceReference<SystemFDSEntry> serviceReference,
			SystemFDSEntry dataSet) {

			removedService(serviceReference, dataSet);

			addingService(serviceReference);
		}

		@Override
		public void removedService(
			ServiceReference<SystemFDSEntry> serviceReference,
			SystemFDSEntry dataSet) {

			String fdsName = GetterUtil.getString(
				serviceReference.getProperty("frontend.data.set.name"));

			_bundleContext.ungetService(serviceReference);

			_systemFDSEntries.updateAndGet(
				systemFDSEntries -> {
					systemFDSEntries = new HashMap<>(systemFDSEntries);

					systemFDSEntries.remove(fdsName);

					if (systemFDSEntries.isEmpty()) {
						return null;
					}

					return systemFDSEntries;
				});
		}

	}

}