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

package com.liferay.commerce.product.internal.util;

import com.liferay.commerce.product.internal.util.comparator.CPSubscriptionTypeServiceWrapperOrderComparator;
import com.liferay.commerce.product.util.CPSubscriptionType;
import com.liferay.commerce.product.util.CPSubscriptionTypeRegistry;
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
@Component(immediate = true, service = CPSubscriptionTypeRegistry.class)
public class CPSubscriptionTypeRegistryImpl
	implements CPSubscriptionTypeRegistry {

	@Override
	public CPSubscriptionType getCPSubscriptionType(String name) {
		if (Validator.isNull(name)) {
			return null;
		}

		ServiceWrapper<CPSubscriptionType> cpSubscriptionTypeServiceWrapper =
			_serviceTrackerMap.getService(name);

		if (cpSubscriptionTypeServiceWrapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No commerce product subscription type registered with " +
						"name " + name);
			}

			return null;
		}

		return cpSubscriptionTypeServiceWrapper.getService();
	}

	@Override
	public List<CPSubscriptionType> getCPSubscriptionTypes() {
		List<CPSubscriptionType> cpSubscriptionTypes = new ArrayList<>();

		List<ServiceWrapper<CPSubscriptionType>>
			cpSubscriptionTypeServiceWrappers = ListUtil.fromCollection(
				_serviceTrackerMap.values());

		Collections.sort(
			cpSubscriptionTypeServiceWrappers,
			_cpSubscriptionTypeServiceWrapperOrderComparator);

		for (ServiceWrapper<CPSubscriptionType>
				cpSubscriptionTypeServiceWrapper :
					cpSubscriptionTypeServiceWrappers) {

			cpSubscriptionTypes.add(
				cpSubscriptionTypeServiceWrapper.getService());
		}

		return Collections.unmodifiableList(cpSubscriptionTypes);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, CPSubscriptionType.class,
			"commerce.product.subscription.type.name",
			ServiceTrackerCustomizerFactory.<CPSubscriptionType>serviceWrapper(
				bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPSubscriptionTypeRegistryImpl.class);

	private static final Comparator<ServiceWrapper<CPSubscriptionType>>
		_cpSubscriptionTypeServiceWrapperOrderComparator =
			new CPSubscriptionTypeServiceWrapperOrderComparator();

	private ServiceTrackerMap<String, ServiceWrapper<CPSubscriptionType>>
		_serviceTrackerMap;

}