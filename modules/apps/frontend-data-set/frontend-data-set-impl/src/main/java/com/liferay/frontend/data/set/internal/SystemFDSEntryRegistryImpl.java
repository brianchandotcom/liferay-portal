/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal;

import com.liferay.frontend.data.set.SystemFDSEntry;
import com.liferay.frontend.data.set.SystemFDSEntryRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapListener;
import com.liferay.petra.string.StringPool;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Daniel Sanz
 */
@Component(service = SystemFDSEntryRegistry.class)
public class SystemFDSEntryRegistryImpl implements SystemFDSEntryRegistry {

	@Override
	public Map<String, SystemFDSEntry> getSystemFDSEntries() {
		if (!_opened) {
			getSystemFDSEntry(StringPool.BLANK);

			_opened = true;
		}

		return Collections.unmodifiableMap(_systemFDSEntries);
	}

	@Override
	public SystemFDSEntry getSystemFDSEntry(String fdsName) {
		return _serviceTrackerMap.getService(fdsName);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, SystemFDSEntry.class, "frontend.data.set.name",
			new ServiceTrackerMapListener
				<String, SystemFDSEntry, SystemFDSEntry>() {

				@Override
				public void keyEmitted(
					ServiceTrackerMap<String, SystemFDSEntry> serviceTrackerMap,
					String key, SystemFDSEntry service,
					SystemFDSEntry content) {

					_systemFDSEntries.put(key, service);
				}

				@Override
				public void keyRemoved(
					ServiceTrackerMap<String, SystemFDSEntry> serviceTrackerMap,
					String key, SystemFDSEntry service,
					SystemFDSEntry content) {

					_systemFDSEntries.remove(key);
				}

			});
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();

		_opened = false;

		_systemFDSEntries.clear();
	}

	private BundleContext _bundleContext;
	private boolean _opened;
	private ServiceTrackerMap<String, SystemFDSEntry> _serviceTrackerMap;
	private final ConcurrentMap<String, SystemFDSEntry> _systemFDSEntries =
		new ConcurrentHashMap<>();

}