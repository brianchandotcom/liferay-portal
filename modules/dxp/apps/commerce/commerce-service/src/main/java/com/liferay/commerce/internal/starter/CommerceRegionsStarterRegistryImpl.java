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

package com.liferay.commerce.internal.starter;

import com.liferay.commerce.starter.CommerceRegionsStarter;
import com.liferay.commerce.starter.CommerceRegionsStarterRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Marco Leo
 */
@Component(immediate = true)
public class CommerceRegionsStarterRegistryImpl
	implements CommerceRegionsStarterRegistry {

	@Override
	public CommerceRegionsStarter getCommerceRegionsStarter(String key) {
		if (Validator.isNull(key)) {
			return null;
		}

		return _serviceTrackerMap.getService(key);
	}

	@Override
	public List<CommerceRegionsStarter> getCommerceRegionsStarters() {
		List<CommerceRegionsStarter> commerceRegionsStarters =
			new ArrayList<>();

		for (String key : _serviceTrackerMap.keySet()) {
			commerceRegionsStarters.add(_serviceTrackerMap.getService(key));
		}

		return Collections.unmodifiableList(commerceRegionsStarters);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, CommerceRegionsStarter.class,
			"commerce.region.starter.key");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap<String, CommerceRegionsStarter>
		_serviceTrackerMap;

}