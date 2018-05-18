/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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