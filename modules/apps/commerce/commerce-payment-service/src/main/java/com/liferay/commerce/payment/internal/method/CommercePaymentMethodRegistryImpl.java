/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.internal.method;

import com.liferay.commerce.payment.method.CommercePaymentMethod;
import com.liferay.commerce.payment.method.CommercePaymentMethodRegistry;
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
@Component(service = CommercePaymentMethodRegistry.class)
public class CommercePaymentMethodRegistryImpl
	implements CommercePaymentMethodRegistry {

	@Override
	public CommercePaymentMethod getCommercePaymentMethod(String key) {
		return _serviceTrackerMap.getService(key);
	}

	@Override
	public Map<String, CommercePaymentMethod> getCommercePaymentMethods() {
		Map<String, CommercePaymentMethod> commercePaymentMethodGroupRelMap =
			new HashMap<>();

		for (String key : _serviceTrackerMap.keySet()) {
			commercePaymentMethodGroupRelMap.put(
				key, _serviceTrackerMap.getService(key));
		}

		return Collections.unmodifiableMap(commercePaymentMethodGroupRelMap);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, CommercePaymentMethod.class, null,
			(serviceReference, emitter) -> {
				CommercePaymentMethod commercePaymentMethod =
					bundleContext.getService(serviceReference);

				try {
					if (commercePaymentMethod.getKey() != null) {
						emitter.emit(commercePaymentMethod.getKey());
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

	private ServiceTrackerMap<String, CommercePaymentMethod> _serviceTrackerMap;

}