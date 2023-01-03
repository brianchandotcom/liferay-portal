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