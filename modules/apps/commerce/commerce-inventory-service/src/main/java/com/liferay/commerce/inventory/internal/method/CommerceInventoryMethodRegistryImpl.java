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

package com.liferay.commerce.inventory.internal.method;

import com.liferay.commerce.inventory.internal.method.comparator.CommerceInventoryMethodOrderComparator;
import com.liferay.commerce.inventory.method.CommerceInventoryMethod;
import com.liferay.commerce.inventory.method.CommerceInventoryMethodRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;

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
@Component(immediate = true, service = CommerceInventoryMethodRegistry.class)
public class CommerceInventoryMethodRegistryImpl
	implements CommerceInventoryMethodRegistry {

	@Override
	public CommerceInventoryMethod getCommerceInventoryMethod(String key) {
		ServiceTrackerCustomizerFactory.ServiceWrapper<CommerceInventoryMethod>
			commerceInventoryMethodServiceWrapper =
				_serviceTrackerMap.getService(key);

		if (commerceInventoryMethodServiceWrapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No commerce inventory method registered with key " + key);
			}

			return null;
		}

		return commerceInventoryMethodServiceWrapper.getService();
	}

	@Override
	public List<CommerceInventoryMethod> getCommerceInventoryMethods() {
		List<CommerceInventoryMethod> commerceInventoryMethods =
			new ArrayList<>();

		List
			<ServiceTrackerCustomizerFactory.ServiceWrapper
				<CommerceInventoryMethod>>
					commerceInventoryMethodServiceWrappers =
						ListUtil.fromCollection(_serviceTrackerMap.values());

		Collections.sort(
			commerceInventoryMethodServiceWrappers,
			_commerceInventoryMethodServiceWrapperOrderComparator);

		for (ServiceTrackerCustomizerFactory.ServiceWrapper
				<CommerceInventoryMethod>
					commerceInventoryMethodServiceWrapper :
						commerceInventoryMethodServiceWrappers) {

			commerceInventoryMethods.add(
				commerceInventoryMethodServiceWrapper.getService());
		}

		return Collections.unmodifiableList(commerceInventoryMethods);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, CommerceInventoryMethod.class,
			"commerce.inventory.method.key",
			ServiceTrackerCustomizerFactory.
				<CommerceInventoryMethod>serviceWrapper(bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceInventoryMethodRegistryImpl.class);

	private final Comparator
		<ServiceTrackerCustomizerFactory.ServiceWrapper
			<CommerceInventoryMethod>>
				_commerceInventoryMethodServiceWrapperOrderComparator =
					new CommerceInventoryMethodOrderComparator();
	private ServiceTrackerMap
		<String,
		 ServiceTrackerCustomizerFactory.ServiceWrapper
			 <CommerceInventoryMethod>> _serviceTrackerMap;

}