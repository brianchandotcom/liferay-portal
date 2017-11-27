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

import com.liferay.commerce.product.internal.util.comparator.CommerceStarterServiceWrapperOrderComparator;
import com.liferay.commerce.product.util.CommerceStarter;
import com.liferay.commerce.product.util.CommerceStarterRegistry;
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

import javax.servlet.http.HttpServletRequest;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Alessio Antonio Rendina
 */
@Component(immediate = true)
public class CommerceStarterRegistryImpl implements CommerceStarterRegistry {

	@Override
	public CommerceStarter getCommerceStarter(String key) {
		if (Validator.isNull(key)) {
			return null;
		}

		ServiceWrapper<CommerceStarter> commerceStarterServiceWrapper =
			_serviceTrackerMap.getService(key);

		if (commerceStarterServiceWrapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("No commerce starter registered with key " + key);
			}

			return null;
		}

		return commerceStarterServiceWrapper.getService();
	}

	@Override
	public List<CommerceStarter> getCommerceStarters(
		boolean active, HttpServletRequest httpServletRequest) {

		List<CommerceStarter> commerceStarters = new ArrayList<>();

		List<ServiceWrapper<CommerceStarter>> commerceStarterServiceWrappers =
			ListUtil.fromCollection(_serviceTrackerMap.values());

		Collections.sort(
			commerceStarterServiceWrappers,
			_commerceStarterServiceWrapperOrderComparator);

		for (ServiceWrapper<CommerceStarter> commerceStarterServiceWrapper :
				commerceStarterServiceWrappers) {

			CommerceStarter commerceStarter =
				commerceStarterServiceWrapper.getService();

			if (!active ||
				(active && commerceStarter.isActive(httpServletRequest))) {

				commerceStarters.add(commerceStarter);
			}
		}

		return Collections.unmodifiableList(commerceStarters);
	}

	@Override
	public List<CommerceStarter> getCommerceStarters(
		HttpServletRequest httpServletRequest) {

		return getCommerceStarters(false, httpServletRequest);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, CommerceStarter.class, "commerce.starter.key",
			ServiceTrackerCustomizerFactory.<CommerceStarter>serviceWrapper(
				bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceStarterRegistryImpl.class);

	private static final Comparator<ServiceWrapper<CommerceStarter>>
		_commerceStarterServiceWrapperOrderComparator =
			new CommerceStarterServiceWrapperOrderComparator();

	private ServiceTrackerMap<String, ServiceWrapper<CommerceStarter>>
		_serviceTrackerMap;

}