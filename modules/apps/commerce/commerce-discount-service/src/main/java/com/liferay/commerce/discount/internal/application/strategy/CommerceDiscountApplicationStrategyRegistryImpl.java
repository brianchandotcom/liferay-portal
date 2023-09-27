/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.discount.internal.application.strategy;

import com.liferay.commerce.discount.application.strategy.CommerceDiscountApplicationStrategy;
import com.liferay.commerce.discount.application.strategy.CommerceDiscountApplicationStrategyRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapperFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Jiaxu Wei
 */
@Component(service = CommerceDiscountApplicationStrategyRegistry.class)
public class CommerceDiscountApplicationStrategyRegistryImpl
	implements CommerceDiscountApplicationStrategyRegistry {

	@Override
	public CommerceDiscountApplicationStrategy get(
		String commerceDiscountApplicationStrategyKey) {

		return _serviceTrackerMap.getService(
			commerceDiscountApplicationStrategyKey);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, CommerceDiscountApplicationStrategy.class, null,
			ServiceReferenceMapperFactory.create(
				bundleContext,
				(commerceDiscountApplicationStrategy, emitter) -> emitter.emit(
					commerceDiscountApplicationStrategy.
						getCommerceDiscountApplicationStrategyKey())));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap<String, CommerceDiscountApplicationStrategy>
		_serviceTrackerMap;

}