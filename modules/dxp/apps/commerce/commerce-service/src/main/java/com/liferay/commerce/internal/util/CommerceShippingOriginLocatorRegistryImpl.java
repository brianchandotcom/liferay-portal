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

package com.liferay.commerce.internal.util;

import com.liferay.commerce.model.CommerceShippingOriginLocator;
import com.liferay.commerce.util.CommerceShippingOriginLocatorRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Andrea Di Giorgi
 */
@Component(immediate = true)
public class CommerceShippingOriginLocatorRegistryImpl
	implements CommerceShippingOriginLocatorRegistry {

	@Override
	public CommerceShippingOriginLocator getCommerceShippingOriginLocator(
		String key) {

		return _serviceTrackerMap.getService(key);
	}

	@Override
	public Map<String, CommerceShippingOriginLocator>
		getCommerceShippingOriginLocators() {

		Map<String, CommerceShippingOriginLocator>
			commerceShippingOriginLocators = new TreeMap<>();

		for (String key : _serviceTrackerMap.keySet()) {
			commerceShippingOriginLocators.put(
				key, _serviceTrackerMap.getService(key));
		}

		return Collections.unmodifiableMap(commerceShippingOriginLocators);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, CommerceShippingOriginLocator.class,
			"commerce.shipping.origin.locator.key");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap<String, CommerceShippingOriginLocator>
		_serviceTrackerMap;

}