/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.util;

import com.liferay.commerce.price.CommerceOrderPriceCalculation;
import com.liferay.commerce.price.CommercePriceCalculationRegistry;
import com.liferay.commerce.price.CommerceProductPriceCalculation;
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
 * @author Riccardo Alberti
 */
@Component(service = CommercePriceCalculationRegistry.class)
public class CommercePriceCalculationRegistryImpl
	implements CommercePriceCalculationRegistry {

	@Override
	public CommerceOrderPriceCalculation getCommerceOrderPriceCalculation(
		String key) {

		return _orderPriceServiceTrackerMap.getService(key);
	}

	@Override
	public Map<String, CommerceOrderPriceCalculation>
		getCommerceOrderPriceCalculations() {

		Map<String, CommerceOrderPriceCalculation>
			commerceOrderPriceCalculations = new HashMap<>();

		for (String key : _orderPriceServiceTrackerMap.keySet()) {
			commerceOrderPriceCalculations.put(
				key, _orderPriceServiceTrackerMap.getService(key));
		}

		return Collections.unmodifiableMap(commerceOrderPriceCalculations);
	}

	@Override
	public CommerceProductPriceCalculation getCommerceProductPriceCalculation(
		String key) {

		return _productPriceServiceTrackerMap.getService(key);
	}

	@Override
	public Map<String, CommerceProductPriceCalculation>
		getCommerceProductPriceCalculations() {

		Map<String, CommerceProductPriceCalculation>
			commerceProductPriceCalculations = new HashMap<>();

		for (String key : _productPriceServiceTrackerMap.keySet()) {
			commerceProductPriceCalculations.put(
				key, _productPriceServiceTrackerMap.getService(key));
		}

		return Collections.unmodifiableMap(commerceProductPriceCalculations);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_productPriceServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, CommerceProductPriceCalculation.class,
				"commerce.price.calculation.key");

		_orderPriceServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, CommerceOrderPriceCalculation.class,
				"commerce.price.calculation.key");
	}

	@Deactivate
	protected void deactivate() {
		_productPriceServiceTrackerMap.close();
	}

	private ServiceTrackerMap<String, CommerceOrderPriceCalculation>
		_orderPriceServiceTrackerMap;
	private ServiceTrackerMap<String, CommerceProductPriceCalculation>
		_productPriceServiceTrackerMap;

}