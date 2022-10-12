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

package com.liferay.commerce.product.internal.channel;

import com.liferay.commerce.product.channel.CommerceChannelType;
import com.liferay.commerce.product.channel.CommerceChannelTypeRegistry;
import com.liferay.commerce.product.internal.channel.comparator.CommerceChannelTypeOrderComparator;
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
 * @author Alec Sloan
 */
@Component(immediate = true, service = CommerceChannelTypeRegistry.class)
public class CommerceChannelTypeRegistryImpl
	implements CommerceChannelTypeRegistry {

	@Override
	public CommerceChannelType getCommerceChannelType(String key) {
		ServiceWrapper<CommerceChannelType> commerceChannelTypeServiceWrapper =
			_serviceTrackerMap.getService(key);

		if (commerceChannelTypeServiceWrapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("No CommerceChannelType registered with key " + key);
			}

			return null;
		}

		return commerceChannelTypeServiceWrapper.getService();
	}

	@Override
	public List<CommerceChannelType> getCommerceChannelTypes() {
		List<CommerceChannelType> commerceChannelTypes = new ArrayList<>();

		List<ServiceWrapper<CommerceChannelType>>
			commerceChannelTypeServiceWrappers = ListUtil.fromCollection(
				_serviceTrackerMap.values());

		Collections.sort(
			commerceChannelTypeServiceWrappers,
			_commerceChannelTypeServiceWrapperOrderComparator);

		for (ServiceWrapper<CommerceChannelType>
				commerceChannelTypeServiceWrapper :
					commerceChannelTypeServiceWrappers) {

			commerceChannelTypes.add(
				commerceChannelTypeServiceWrapper.getService());
		}

		return Collections.unmodifiableList(commerceChannelTypes);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, CommerceChannelType.class,
			"commerce.product.channel.type.key",
			ServiceTrackerCustomizerFactory.<CommerceChannelType>serviceWrapper(
				bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceChannelTypeRegistryImpl.class);

	private final Comparator<ServiceWrapper<CommerceChannelType>>
		_commerceChannelTypeServiceWrapperOrderComparator =
			new CommerceChannelTypeOrderComparator();
	private ServiceTrackerMap<String, ServiceWrapper<CommerceChannelType>>
		_serviceTrackerMap;

}