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

package com.liferay.commerce.internal.stock.activity;

import com.liferay.commerce.stock.activity.CommerceLowStockActivity;
import com.liferay.commerce.stock.activity.CommerceLowStockActivityRegistry;
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
 * @author Alessio Antonio Rendina
 */
@Component(immediate = true, service = CommerceLowStockActivityRegistry.class)
public class CommerceLowStockActivityRegistryImpl
	implements CommerceLowStockActivityRegistry {

	@Override
	public Map<String, CommerceLowStockActivity>
		getCommerceLowStockActivities() {

		Map<String, CommerceLowStockActivity> commerceLowStockActivities =
			new HashMap<>();

		for (String key : _serviceTrackerMap.keySet()) {
			commerceLowStockActivities.put(
				key, _serviceTrackerMap.getService(key));
		}

		return Collections.unmodifiableMap(commerceLowStockActivities);
	}

	@Override
	public CommerceLowStockActivity getCommerceLowStockActivity(String key) {
		return _serviceTrackerMap.getService(key);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, CommerceLowStockActivity.class,
			"commerce.low.stock.activity.key");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap<String, CommerceLowStockActivity>
		_serviceTrackerMap;

}