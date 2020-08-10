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

import com.liferay.commerce.health.status.CommerceHealthHttpStatus;
import com.liferay.commerce.health.status.CommerceHealthHttpStatusRegistry;
import com.liferay.commerce.internal.health.status.comparator.CommerceHealthStatusServiceWrapperDisplayOrderComparator;
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

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Alessio Antonio Rendina
 */
@Component(immediate = true, service = CommerceHealthHttpStatusRegistry.class)
public class CommerceHealthHttpStatusRegistryImpl
	implements CommerceHealthHttpStatusRegistry {

	@Override
	public CommerceHealthHttpStatus getCommerceHealthStatus(String key) {
		if (Validator.isNull(key)) {
			return null;
		}

		ServiceWrapper<CommerceHealthHttpStatus>
			commerceHealthStatusServiceWrapper =
				_commerceHealthStatusRegistryMap.getService(key);

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
	public List<CommerceHealthHttpStatus> getCommerceHealthStatuses(int type) {
		List<CommerceHealthHttpStatus> commerceHealthHttpStatuses =
			new ArrayList<>();

		List<ServiceWrapper<CommerceHealthHttpStatus>>
			commerceHealthStatusServiceWrappers = ListUtil.fromCollection(
				_commerceHealthStatusRegistryMap.values());

		Collections.sort(
			commerceHealthStatusServiceWrappers,
			_commerceHealthStatusServiceWrapperDisplayOrderComparator);

		for (ServiceWrapper<CommerceHealthHttpStatus>
				commerceHealthStatusServiceWrapper :
					commerceHealthStatusServiceWrappers) {

			CommerceHealthHttpStatus commerceHealthHttpStatus =
				commerceHealthStatusServiceWrapper.getService();

			if (type == commerceHealthHttpStatus.getType()) {
				commerceHealthHttpStatuses.add(commerceHealthHttpStatus);
			}
		}

		return Collections.unmodifiableList(commerceHealthHttpStatuses);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_commerceHealthStatusRegistryMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, CommerceHealthHttpStatus.class,
				"commerce.health.status.key",
				ServiceTrackerCustomizerFactory.
					<CommerceHealthHttpStatus>serviceWrapper(bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_commerceHealthStatusRegistryMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceHealthHttpStatusRegistryImpl.class);

	private static final Comparator<ServiceWrapper<CommerceHealthHttpStatus>>
		_commerceHealthStatusServiceWrapperDisplayOrderComparator =
			new CommerceHealthStatusServiceWrapperDisplayOrderComparator();

	private ServiceTrackerMap<String, ServiceWrapper<CommerceHealthHttpStatus>>
		_commerceHealthStatusRegistryMap;

}