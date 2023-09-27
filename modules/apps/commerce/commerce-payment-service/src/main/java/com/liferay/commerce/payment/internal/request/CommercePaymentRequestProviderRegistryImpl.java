/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.internal.request;

import com.liferay.commerce.payment.request.CommercePaymentRequestProvider;
import com.liferay.commerce.payment.request.CommercePaymentRequestProviderRegistry;
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
 * @author Luca Pellizzon
 */
@Component(service = CommercePaymentRequestProviderRegistry.class)
public class CommercePaymentRequestProviderRegistryImpl
	implements CommercePaymentRequestProviderRegistry {

	@Override
	public CommercePaymentRequestProvider getCommercePaymentRequestProvider(
		String key) {

		return _serviceTrackerMap.getService(key);
	}

	@Override
	public Map<String, CommercePaymentRequestProvider>
		getCommercePaymentRequestProviders() {

		Map<String, CommercePaymentRequestProvider>
			commercePaymentMethodGroupRelMap = new HashMap<>();

		for (String key : _serviceTrackerMap.keySet()) {
			commercePaymentMethodGroupRelMap.put(
				key, _serviceTrackerMap.getService(key));
		}

		return Collections.unmodifiableMap(commercePaymentMethodGroupRelMap);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, CommercePaymentRequestProvider.class,
			"commerce.payment.engine.method.key");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap<String, CommercePaymentRequestProvider>
		_serviceTrackerMap;

}