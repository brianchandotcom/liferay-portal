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

package com.liferay.commerce.internal.price;

import com.liferay.commerce.internal.price.comparator.CommercePriceListQualificationTypeOrderComparator;
import com.liferay.commerce.price.CommercePriceListQualificationType;
import com.liferay.commerce.price.CommercePriceListQualificationTypeRegistry;
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
 * @author Marco Leo
 */
@Component(
	immediate = true, service = CommercePriceListQualificationTypeRegistry.class
)
public class CommercePriceListQualificationTypeRegistryImpl
	implements CommercePriceListQualificationTypeRegistry {

	@Override
	public CommercePriceListQualificationType
		getCommercePriceListQualificationType(String key) {

		ServiceWrapper<CommercePriceListQualificationType>
			commercePriceListQualificationTypeServiceWrapper =
				_serviceTrackerMap.getService(key);

		if (commercePriceListQualificationTypeServiceWrapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No CommercePriceListQualificationType" +
						"registered with key " + key);
			}

			return null;
		}

		return commercePriceListQualificationTypeServiceWrapper.getService();
	}

	@Override
	public List<CommercePriceListQualificationType>
		getCommercePriceListQualificationTypes() {

		List<CommercePriceListQualificationType>
			commercePriceListQualificationTypes = new ArrayList<>();

		List<ServiceTrackerCustomizerFactory.
			ServiceWrapper<CommercePriceListQualificationType>>
				commercePriceListQualificationTypeServiceWrappers =
					ListUtil.fromCollection(_serviceTrackerMap.values());

		Collections.sort(
			commercePriceListQualificationTypeServiceWrappers,
			_commercePriceListQualificationTypeServiceWrapperOrderComparator);

		for (ServiceTrackerCustomizerFactory.
				ServiceWrapper<CommercePriceListQualificationType>
					commercePriceListQualificationTypeServiceWrapper :
						commercePriceListQualificationTypeServiceWrappers) {

			commercePriceListQualificationTypes.add(
				commercePriceListQualificationTypeServiceWrapper.getService());
		}

		return Collections.unmodifiableList(
			commercePriceListQualificationTypes);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, CommercePriceListQualificationType.class,
			"commerce.price.list.qualification.type.key",
			ServiceTrackerCustomizerFactory.
				<CommercePriceListQualificationType>serviceWrapper(
					bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommercePriceListQualificationTypeRegistryImpl.class);

	private final Comparator<ServiceTrackerCustomizerFactory.
		ServiceWrapper<CommercePriceListQualificationType>>
			_commercePriceListQualificationTypeServiceWrapperOrderComparator =
				new CommercePriceListQualificationTypeOrderComparator();
	private ServiceTrackerMap<String,
		ServiceWrapper<CommercePriceListQualificationType>> _serviceTrackerMap;

}