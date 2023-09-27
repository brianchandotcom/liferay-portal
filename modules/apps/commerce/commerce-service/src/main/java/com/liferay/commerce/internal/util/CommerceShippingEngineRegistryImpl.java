/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.util;

import com.liferay.commerce.model.CommerceShippingEngine;
import com.liferay.commerce.util.CommerceShippingEngineRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Andrea Di Giorgi
 */
@Component(service = CommerceShippingEngineRegistry.class)
public class CommerceShippingEngineRegistryImpl
	implements CommerceShippingEngineRegistry {

	@Override
	public CommerceShippingEngine getCommerceShippingEngine(String key) {
		return _serviceTrackerMap.getService(key);
	}

	@Override
	public Map<String, CommerceShippingEngine> getCommerceShippingEngines() {
		Map<String, CommerceShippingEngine> commerceShippingEngines =
			new HashMap<>();

		for (String key : _serviceTrackerMap.keySet()) {
			commerceShippingEngines.put(
				key, _serviceTrackerMap.getService(key));
		}

		return Collections.unmodifiableMap(commerceShippingEngines);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, CommerceShippingEngine.class,
			"commerce.shipping.engine.key");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap<String, CommerceShippingEngine>
		_serviceTrackerMap;

}