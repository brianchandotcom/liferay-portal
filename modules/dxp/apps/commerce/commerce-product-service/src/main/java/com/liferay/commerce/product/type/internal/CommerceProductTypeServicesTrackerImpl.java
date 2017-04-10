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

package com.liferay.commerce.product.type.internal;

import com.liferay.commerce.product.type.CommerceProductType;
import com.liferay.commerce.product.type.CommerceProductTypeServicesTracker;
import com.liferay.commerce.product.type.internal.util.comparator.CommerceProductTypeServiceWrapperDisplayOrderComparator;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;

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
 * @author Marco Leo
 */
@Component(immediate = true)
public class CommerceProductTypeServicesTrackerImpl
	implements CommerceProductTypeServicesTracker {

	@Override
	public CommerceProductType getCommerceProductType(String name) {
		ServiceWrapper<CommerceProductType> commerceProductTypeServiceWrapper =
			_commerceProductTypeServiceTrackerMap.getService(name);

		if (commerceProductTypeServiceWrapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No commerce product type registered with name " + name);
			}

			return null;
		}

		return commerceProductTypeServiceWrapper.getService();
	}

	@Override
	public Set<String> getCommerceProductTypeNames() {
		return _commerceProductTypeServiceTrackerMap.keySet();
	}

	@Override
	public List<CommerceProductType> getCommerceProductTypes() {
		List<CommerceProductType> commerceProductTypes = new ArrayList<>();

		List<ServiceWrapper<CommerceProductType>>
			commerceProductTypeServiceWrappers = ListUtil.fromCollection(
				_commerceProductTypeServiceTrackerMap.values());

		Collections.sort(
			commerceProductTypeServiceWrappers,
			_commerceProductTypeServiceWrapperDisplayOrderComparator);

		for (ServiceWrapper<CommerceProductType>
				commerceProductTypeServiceWrapper :
					commerceProductTypeServiceWrappers) {

			commerceProductTypes.add(
				commerceProductTypeServiceWrapper.getService());
		}

		return Collections.unmodifiableList(commerceProductTypes);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_commerceProductTypeServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, CommerceProductType.class,
				"commerce.product.type.name",
				ServiceTrackerCustomizerFactory.
					<CommerceProductType>serviceWrapper(bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_commerceProductTypeServiceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceProductTypeServicesTrackerImpl.class);

	private ServiceTrackerMap<String, ServiceWrapper<CommerceProductType>>
		_commerceProductTypeServiceTrackerMap;
	private final Comparator<ServiceWrapper<CommerceProductType>>
		_commerceProductTypeServiceWrapperDisplayOrderComparator =
			new CommerceProductTypeServiceWrapperDisplayOrderComparator();

}