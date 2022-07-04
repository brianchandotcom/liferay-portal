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

package com.liferay.commerce.product.internal.option;

import com.liferay.commerce.product.internal.option.comparator.CommerceOptionTypeServiceWrapperDisplayOrderComparator;
import com.liferay.commerce.product.option.CommerceOptionType;
import com.liferay.commerce.product.option.CommerceOptionTypeRegistry;
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
@Component(
	enabled = false, immediate = true,
	service = CommerceOptionTypeRegistry.class
)
public class CommerceOptionTypeRegistryImpl
	implements CommerceOptionTypeRegistry {

	@Override
	public CommerceOptionType getCommerceOptionType(String key) {
		if (Validator.isNull(key)) {
			return null;
		}

		ServiceWrapper<CommerceOptionType> commerceOptionTypeServiceWrapper =
			_commerceOptionTypeRegistryMap.getService(key);

		if (commerceOptionTypeServiceWrapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No commerce option type registered with key " + key);
			}

			return null;
		}

		return commerceOptionTypeServiceWrapper.getService();
	}

	@Override
	public List<CommerceOptionType> getCommerceOptionTypes() {
		List<CommerceOptionType> commerceOptionTypes = new ArrayList<>();

		List<ServiceWrapper<CommerceOptionType>>
			commerceOptionTypeServiceWrappers = ListUtil.fromCollection(
				_commerceOptionTypeRegistryMap.values());

		Collections.sort(
			commerceOptionTypeServiceWrappers,
			_commerceOptionTypeServiceWrapperDisplayOrderComparator);

		for (ServiceWrapper<CommerceOptionType>
				commerceOptionTypeServiceWrapper :
					commerceOptionTypeServiceWrappers) {

			commerceOptionTypes.add(
				commerceOptionTypeServiceWrapper.getService());
		}

		return Collections.unmodifiableList(commerceOptionTypes);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_commerceOptionTypeRegistryMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, CommerceOptionType.class,
				"commerce.option.type.key",
				ServiceTrackerCustomizerFactory.
					<CommerceOptionType>serviceWrapper(bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_commerceOptionTypeRegistryMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceOptionTypeRegistryImpl.class);

	private static final Comparator<ServiceWrapper<CommerceOptionType>>
		_commerceOptionTypeServiceWrapperDisplayOrderComparator =
			new CommerceOptionTypeServiceWrapperDisplayOrderComparator();

	private ServiceTrackerMap<String, ServiceWrapper<CommerceOptionType>>
		_commerceOptionTypeRegistryMap;

}