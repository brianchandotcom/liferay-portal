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

package com.liferay.commerce.discount.internal.target;

import com.liferay.commerce.discount.internal.target.comparator.CommerceDiscountTargetTypeOrderComparator;
import com.liferay.commerce.discount.target.CommerceDiscountTargetType;
import com.liferay.commerce.discount.target.CommerceDiscountTargetTypeRegistry;
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

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Alessio Antonio Rendina
 */
@Component(immediate = true)
public class CommerceDiscountTargetTypeRegistryImpl
	implements CommerceDiscountTargetTypeRegistry {

	@Override
	public CommerceDiscountTargetType getCommerceDiscountTargetType(
		String key) {

		ServiceWrapper<CommerceDiscountTargetType>
			commerceDiscountTargetTypeServiceWrapper =
				_serviceTrackerMap.getService(key);

		if (commerceDiscountTargetTypeServiceWrapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No CommerceDiscountTargetType registered with key " + key);
			}

			return null;
		}

		return commerceDiscountTargetTypeServiceWrapper.getService();
	}

	@Override
	public List<CommerceDiscountTargetType> getCommerceDiscountTargetTypes() {
		List<CommerceDiscountTargetType> commerceDiscountTargetTypes =
			new ArrayList<>();

		List<ServiceWrapper<CommerceDiscountTargetType>>
			commerceDiscountTargetTypeServiceWrappers = ListUtil.fromCollection(
				_serviceTrackerMap.values());

		Collections.sort(
			commerceDiscountTargetTypeServiceWrappers,
			_commerceDiscountTargetTypeServiceWrapperOrderComparator);

		for (ServiceWrapper<CommerceDiscountTargetType>
				commerceDiscountTargetTypeServiceWrapper :
					commerceDiscountTargetTypeServiceWrappers) {

			commerceDiscountTargetTypes.add(
				commerceDiscountTargetTypeServiceWrapper.getService());
		}

		return Collections.unmodifiableList(commerceDiscountTargetTypes);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, CommerceDiscountTargetType.class,
			"commerce.discount.target.type.key",
			ServiceTrackerCustomizerFactory.
				<CommerceDiscountTargetType>serviceWrapper(bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceDiscountTargetTypeRegistryImpl.class);

	private final Comparator<ServiceWrapper<CommerceDiscountTargetType>>
		_commerceDiscountTargetTypeServiceWrapperOrderComparator =
			new CommerceDiscountTargetTypeOrderComparator();
	private ServiceTrackerMap<String,
		ServiceWrapper<CommerceDiscountTargetType>> _serviceTrackerMap;

}