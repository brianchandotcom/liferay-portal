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

package com.liferay.commerce.health.status.web.internal.util;

import com.liferay.commerce.health.status.web.internal.util.comparator.CommerceHealthStatusServiceWrapperDisplayOrderComparator;
import com.liferay.commerce.health.status.web.util.CommerceHealthStatus;
import com.liferay.commerce.health.status.web.util.CommerceHealthStatusServiceTracker;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
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
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Alessio Antonio Rendina
 */
@Component(immediate = true)
public class CommerceHealthStatusServiceTrackerImpl
	implements CommerceHealthStatusServiceTracker {

	@Override
	public CommerceHealthStatus getCommerceHealthStatus(String key) {
		if (Validator.isNull(key)) {
			return null;
		}

		ServiceWrapper<CommerceHealthStatus>
			commerceHealthStatusServiceWrapper =
				_commerceHealthStatusServiceTrackerMap.getService(key);

		if (commerceHealthStatusServiceWrapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No commerce health status registered with key " + key);
			}

			return null;
		}

		return commerceHealthStatusServiceWrapper.getService();
	}

	@Override
	public List<CommerceHealthStatus> getCommerceHealthStatuses() {
		List<CommerceHealthStatus> commerceHealthStatuses = new ArrayList<>();

		List<ServiceWrapper<CommerceHealthStatus>>
			commerceHealthStatusServiceWrappers = ListUtil.fromCollection(
				_commerceHealthStatusServiceTrackerMap.values());

		Collections.sort(
			commerceHealthStatusServiceWrappers,
			_commerceHealthStatusServiceWrapperDisplayOrderComparator);

		for (ServiceWrapper<CommerceHealthStatus>
				commerceHealthStatusServiceWrapper :
					commerceHealthStatusServiceWrappers) {

			commerceHealthStatuses.add(
				commerceHealthStatusServiceWrapper.getService());
		}

		return Collections.unmodifiableList(commerceHealthStatuses);
	}

	@Override
	public Set<String> getCommerceHealthStatusKeys() {
		return _commerceHealthStatusServiceTrackerMap.keySet();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_commerceHealthStatusServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, CommerceHealthStatus.class,
				"commerce.health.status.key",
				ServiceTrackerCustomizerFactory.
					<CommerceHealthStatus>serviceWrapper(bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_commerceHealthStatusServiceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceHealthStatusServiceTrackerImpl.class);

	private ServiceTrackerMap<String, ServiceWrapper<CommerceHealthStatus>>
		_commerceHealthStatusServiceTrackerMap;
	private final Comparator<ServiceWrapper<CommerceHealthStatus>>
		_commerceHealthStatusServiceWrapperDisplayOrderComparator =
			new CommerceHealthStatusServiceWrapperDisplayOrderComparator();

}