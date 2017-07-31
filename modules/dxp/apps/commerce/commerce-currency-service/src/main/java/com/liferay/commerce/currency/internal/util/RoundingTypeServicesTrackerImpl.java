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

package com.liferay.commerce.currency.internal.util;

import com.liferay.commerce.currency.util.RoundingType;
import com.liferay.commerce.currency.util.RoundingTypeServicesTracker;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import java.util.Collections;
import java.util.NavigableMap;
import java.util.TreeMap;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Andrea Di Giorgi
 */
@Component(immediate = true)
public class RoundingTypeServicesTrackerImpl
	implements RoundingTypeServicesTracker {

	@Override
	public NavigableMap<String, RoundingType> getRoundingTypes() {
		NavigableMap<String, RoundingType> roundingTypes = new TreeMap<>();

		for (String name : _roundingTypeServiceTrackerMap.keySet()) {
			RoundingType roundingType =
				_roundingTypeServiceTrackerMap.getService(name);

			roundingTypes.put(name, roundingType);
		}

		return Collections.unmodifiableNavigableMap(roundingTypes);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_roundingTypeServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, RoundingType.class, "rounding.type.name");
	}

	@Deactivate
	protected void deactivate() {
		_roundingTypeServiceTrackerMap.close();
	}

	private ServiceTrackerMap<String, RoundingType>
		_roundingTypeServiceTrackerMap;

}