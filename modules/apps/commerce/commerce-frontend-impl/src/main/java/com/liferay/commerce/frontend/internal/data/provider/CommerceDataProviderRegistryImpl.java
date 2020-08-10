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

package com.liferay.commerce.frontend.internal.data.provider;

import com.liferay.commerce.frontend.CommerceDataProviderRegistry;
import com.liferay.commerce.frontend.CommerceDataSetDataProvider;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Marco Leo
 */
@Component(immediate = true, service = CommerceDataProviderRegistry.class)
public class CommerceDataProviderRegistryImpl
	implements CommerceDataProviderRegistry {

	@Override
	public CommerceDataSetDataProvider getCommerceDataProvider(String key) {
		ServiceWrapper<CommerceDataSetDataProvider>
			commerceDataProviderServiceWrapper = _serviceTrackerMap.getService(
				key);

		if (commerceDataProviderServiceWrapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No CommerceDataProvider registered with key " + key);
			}

			return null;
		}

		return commerceDataProviderServiceWrapper.getService();
	}

	@Override
	public List<CommerceDataSetDataProvider> getCommerceDataProviders() {
		List<CommerceDataSetDataProvider> commerceDataProviders =
			new ArrayList<>();

		List<ServiceWrapper<CommerceDataSetDataProvider>>
			commerceDataProviderServiceWrappers = ListUtil.fromCollection(
				_serviceTrackerMap.values());

		for (ServiceWrapper<CommerceDataSetDataProvider>
				commerceDataProviderServiceWrapper :
					commerceDataProviderServiceWrappers) {

			commerceDataProviders.add(
				commerceDataProviderServiceWrapper.getService());
		}

		return Collections.unmodifiableList(commerceDataProviders);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, CommerceDataSetDataProvider.class,
			"commerce.data.provider.key",
			ServiceTrackerCustomizerFactory.
				<CommerceDataSetDataProvider>serviceWrapper(bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceDataProviderRegistryImpl.class);

	private ServiceTrackerMap
		<String, ServiceWrapper<CommerceDataSetDataProvider>>
			_serviceTrackerMap;

}