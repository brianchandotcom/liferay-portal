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

package com.liferay.frontend.taglib.clay.internal;

import com.liferay.frontend.taglib.clay.attribute.provider.ClayComponentAttributeProvider;
import com.liferay.frontend.taglib.clay.attribute.provider.ClayComponentAttributeProviderRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Rodolfo Roza Miranda
 */
@Component(
	immediate = true, service = ClayComponentAttributeProviderRegistry.class
)
public class ClayComponentAttributeProvidersProvider
	implements ClayComponentAttributeProviderRegistry {

	@Override
	public List<ClayComponentAttributeProvider> get(String key) {
		List<ServiceWrapper<ClayComponentAttributeProvider>> serviceWrappers =
			ListUtil.copy(_serviceTrackerMap.getService(key));

		if (serviceWrappers == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No ClayComponentAttributeProvider registered with key " +
						key);
			}

			return null;
		}

		serviceWrappers.sort(Comparator.comparingInt(this::_getServiceRanking));

		return _extractServices(serviceWrappers);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, ClayComponentAttributeProvider.class,
			"clay.component.attribute.provider.key",
			ServiceTrackerCustomizerFactory.serviceWrapper(bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private List<ClayComponentAttributeProvider> _extractServices(
		List<ServiceWrapper<ClayComponentAttributeProvider>> serviceWrappers) {

		ArrayList<ClayComponentAttributeProvider> services = new ArrayList<>();

		for (ServiceWrapper<ClayComponentAttributeProvider> wrapper :
				serviceWrappers) {

			services.add(wrapper.getService());
		}

		return services;
	}

	private int _getServiceRanking(
		ServiceWrapper<ClayComponentAttributeProvider> service) {

		Map<String, Object> properties = service.getProperties();

		return GetterUtil.getInteger(
			properties.get("service.ranking"), Integer.MIN_VALUE);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ClayComponentAttributeProvidersProvider.class);

	private ServiceTrackerMap
		<String, List<ServiceWrapper<ClayComponentAttributeProvider>>>
			_serviceTrackerMap;

}