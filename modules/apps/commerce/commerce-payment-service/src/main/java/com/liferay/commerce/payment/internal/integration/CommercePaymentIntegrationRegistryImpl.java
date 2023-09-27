/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.internal.integration;

import com.liferay.commerce.payment.integration.CommercePaymentIntegration;
import com.liferay.commerce.payment.integration.CommercePaymentIntegrationRegistry;
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
@Component(service = CommercePaymentIntegrationRegistry.class)
public class CommercePaymentIntegrationRegistryImpl
	implements CommercePaymentIntegrationRegistry {

	@Override
	public CommercePaymentIntegration getCommercePaymentIntegration(
		String key) {

		return _serviceTrackerMap.getService(key);
	}

	@Override
	public Map<String, CommercePaymentIntegration>
		getCommercePaymentIntegrations() {

		Map<String, CommercePaymentIntegration> commercePaymentIntegrations =
			new HashMap<>();

		for (String key : _serviceTrackerMap.keySet()) {
			commercePaymentIntegrations.put(
				key, _serviceTrackerMap.getService(key));
		}

		return Collections.unmodifiableMap(commercePaymentIntegrations);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, CommercePaymentIntegration.class, null,
			(serviceReference, emitter) -> {
				CommercePaymentIntegration commercePaymentIntegration =
					bundleContext.getService(serviceReference);

				try {
					if (commercePaymentIntegration.getKey() != null) {
						emitter.emit(commercePaymentIntegration.getKey());
					}
				}
				finally {
					bundleContext.ungetService(serviceReference);
				}
			});
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap<String, CommercePaymentIntegration>
		_serviceTrackerMap;

}