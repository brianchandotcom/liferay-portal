/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.web.internal.hashed.files;

import com.liferay.frontend.js.web.internal.hashed.files.osgi.util.tracker.HashedFileURIsRegistryServiceTrackerCustomizer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

import javax.servlet.ServletContext;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Iván Zaera Avellón
 */
@Component(service = HashedFileURIsRegistry.class)
public class HashedFileURIsRegistry {

	public void forEach(BiConsumer<String, String> biConsumer) {
		_loadMap();

		for (Map.Entry<String, String> entry : _map.entrySet()) {
			biConsumer.accept(entry.getKey(), entry.getValue());
		}
	}

	public String get(String nonhashedFileURI) {
		_loadMap();

		return _map.get(nonhashedFileURI);
	}

	public void putAll(Map<String, String> map) {
		_map.putAll(map);
	}

	public void removeAll(Map<String, String> map) {
		for (String key : map.keySet()) {
			_map.remove(key);
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	@Deactivate
	protected void deactivate() {
		if (_serviceTracker != null) {
			_serviceTracker.close();

			_serviceTracker = null;
		}

		_bundleContext = null;

		_map.clear();
	}

	private synchronized void _loadMap() {
		if (_serviceTracker != null) {
			return;
		}

		if (_bundleContext == null) {
			throw new IllegalStateException("Bundle context is null");
		}

		_serviceTracker = new ServiceTracker<>(
			_bundleContext, ServletContext.class,
			new HashedFileURIsRegistryServiceTrackerCustomizer(
				_bundleContext, this));

		_serviceTracker.open();
	}

	private BundleContext _bundleContext;
	private final Map<String, String> _map = new ConcurrentHashMap<>();
	private ServiceTracker<ServletContext, Map<String, String>> _serviceTracker;

}