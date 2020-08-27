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

package com.liferay.commerce.pricing.internal.type;

import com.liferay.commerce.pricing.internal.comparator.CommercePriceModifierTypeOrderComparator;
import com.liferay.commerce.pricing.type.CommercePriceModifierType;
import com.liferay.commerce.pricing.type.CommercePriceModifierTypeRegistry;
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
 * @author Riccardo Alberti
 */
@Component(immediate = true, service = CommercePriceModifierTypeRegistry.class)
public class CommercePriceModifierTypeRegistryImpl
	implements CommercePriceModifierTypeRegistry {

	@Override
	public CommercePriceModifierType getCommercePriceModifierType(String key) {
		ServiceWrapper<CommercePriceModifierType>
			commercePriceModifierTypeServiceWrapper =
				_serviceTrackerMap.getService(key);

		if (commercePriceModifierTypeServiceWrapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No CommercePriceModifierType registered with key " + key);
			}

			return null;
		}

		return commercePriceModifierTypeServiceWrapper.getService();
	}

	@Override
	public List<CommercePriceModifierType> getCommercePriceModifierTypes() {
		List<CommercePriceModifierType> commercePriceModifierTypes =
			new ArrayList<>();

		List<ServiceWrapper<CommercePriceModifierType>>
			commercePriceModifierTypeServiceWrappers = ListUtil.fromCollection(
				_serviceTrackerMap.values());

		Collections.sort(
			commercePriceModifierTypeServiceWrappers,
			_commercePriceModifierTypeServiceWrapperOrderComparator);

		for (ServiceWrapper<CommercePriceModifierType>
				commercePriceModifierTypeServiceWrapper :
					commercePriceModifierTypeServiceWrappers) {

			commercePriceModifierTypes.add(
				commercePriceModifierTypeServiceWrapper.getService());
		}

		return Collections.unmodifiableList(commercePriceModifierTypes);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, CommercePriceModifierType.class,
			"commerce.price.modifier.type.key",
			ServiceTrackerCustomizerFactory.
				<CommercePriceModifierType>serviceWrapper(bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommercePriceModifierTypeRegistryImpl.class);

	private final Comparator<ServiceWrapper<CommercePriceModifierType>>
		_commercePriceModifierTypeServiceWrapperOrderComparator =
			new CommercePriceModifierTypeOrderComparator();
	private ServiceTrackerMap<String, ServiceWrapper<CommercePriceModifierType>>
		_serviceTrackerMap;

}