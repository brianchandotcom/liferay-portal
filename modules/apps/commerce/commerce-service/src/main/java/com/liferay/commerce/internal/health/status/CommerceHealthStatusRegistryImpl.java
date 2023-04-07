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

package com.liferay.commerce.internal.health.status;

import com.liferay.commerce.health.status.CommerceHealthStatus;
import com.liferay.commerce.health.status.CommerceHealthStatusRegistry;
import com.liferay.commerce.internal.health.status.comparator.CommerceHealthStatusServiceWrapperDisplayOrderComparator;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Comparator;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = CommerceHealthStatusRegistry.class)
public class CommerceHealthStatusRegistryImpl
	implements CommerceHealthStatusRegistry {

	@Override
	public List<CommerceHealthStatus> getActiveCommerceHealthStatuses(
		int type) {

		return TransformUtil.transform(
			ListUtil.sort(
				ListUtil.fromCollection(_serviceTrackerMap.values()),
				_commerceHealthStatusServiceWrapperDisplayOrderComparator),
			commerceHealthStatusServiceWrapper -> {
				CommerceHealthStatus commerceHealthStatus =
					commerceHealthStatusServiceWrapper.getService();

				if ((type == commerceHealthStatus.getType()) &&
					commerceHealthStatus.isActive()) {

					return commerceHealthStatus;
				}

				return null;
			});
	}

	@Override
	public CommerceHealthStatus getCommerceHealthStatus(String key) {
		if (Validator.isNull(key)) {
			return null;
		}

		ServiceWrapper<CommerceHealthStatus>
			commerceHealthStatusServiceWrapper = _serviceTrackerMap.getService(
				key);

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
	public List<CommerceHealthStatus> getCommerceHealthStatuses(int type) {
		return TransformUtil.transform(
			ListUtil.sort(
				ListUtil.fromCollection(_serviceTrackerMap.values()),
				_commerceHealthStatusServiceWrapperDisplayOrderComparator),
			commerceHealthStatusServiceWrapper -> {
				CommerceHealthStatus commerceHealthStatus =
					commerceHealthStatusServiceWrapper.getService();

				if (type == commerceHealthStatus.getType()) {
					return commerceHealthStatus;
				}

				return null;
			});
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, CommerceHealthStatus.class,
			"commerce.health.status.key",
			ServiceTrackerCustomizerFactory.
				<CommerceHealthStatus>serviceWrapper(bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceHealthStatusRegistryImpl.class);

	private static final Comparator<ServiceWrapper<CommerceHealthStatus>>
		_commerceHealthStatusServiceWrapperDisplayOrderComparator =
			new CommerceHealthStatusServiceWrapperDisplayOrderComparator();

	private ServiceTrackerMap<String, ServiceWrapper<CommerceHealthStatus>>
		_serviceTrackerMap;

}