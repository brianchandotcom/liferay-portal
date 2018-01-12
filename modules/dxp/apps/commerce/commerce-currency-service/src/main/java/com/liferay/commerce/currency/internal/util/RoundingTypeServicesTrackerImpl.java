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

import com.liferay.commerce.currency.internal.util.comparator.RoundingTypeServiceWrapperPriorityComparator;
import com.liferay.commerce.currency.util.RoundingType;
import com.liferay.commerce.currency.util.RoundingTypeServicesTracker;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 */
@Component(immediate = true)
public class RoundingTypeServicesTrackerImpl
	implements RoundingTypeServicesTracker {

	@Override
	public RoundingType getRoundingType(String name) {
		if (Validator.isNull(name)) {
			name = DefaultRoundingTypeImpl.NAME;
		}

		ServiceTrackerCustomizerFactory.ServiceWrapper<RoundingType>
			roundingTypeServiceWrapper = _serviceTrackerMap.getService(name);

		if (roundingTypeServiceWrapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("No rounding types registered with name " + name);
			}

			return null;
		}

		return roundingTypeServiceWrapper.getService();
	}

	@Override
	public List<RoundingType> getRoundingTypes() {
		List<RoundingType> roundingTypes = new ArrayList<>();

		List<ServiceTrackerCustomizerFactory.ServiceWrapper<RoundingType>>
			roundingTypeServiceWrappers = ListUtil.fromCollection(
				_serviceTrackerMap.values());

		Collections.sort(
			roundingTypeServiceWrappers,
			_roundingTypeServiceWrapperPriorityComparator);

		for (ServiceTrackerCustomizerFactory.ServiceWrapper<RoundingType>
				roundingTypeServiceWrapper : roundingTypeServiceWrappers) {

			roundingTypes.add(roundingTypeServiceWrapper.getService());
		}

		return Collections.unmodifiableList(roundingTypes);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, RoundingType.class, "rounding.type.name",
			ServiceTrackerCustomizerFactory.<RoundingType>serviceWrapper(
				bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RoundingTypeServicesTrackerImpl.class);

	private static final
		Comparator<ServiceTrackerCustomizerFactory.ServiceWrapper<RoundingType>>
			_roundingTypeServiceWrapperPriorityComparator =
				new RoundingTypeServiceWrapperPriorityComparator();

	private
		ServiceTrackerMap<String, ServiceTrackerCustomizerFactory.
			ServiceWrapper<RoundingType>> _serviceTrackerMap;

}