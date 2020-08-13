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

package com.liferay.frontend.taglib.clay.internal.data.set.provider;

import com.liferay.frontend.taglib.clay.data.set.provider.ClayDataSetDataProvider;
import com.liferay.frontend.taglib.clay.data.set.provider.ClayDataSetProviderRegistry;
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
@Component(immediate = true, service = ClayDataSetProviderRegistry.class)
public class ClayDataProviderRegistryImpl
	implements ClayDataSetProviderRegistry {

	@Override
	public ClayDataSetDataProvider getClayDataSetProvider(
		String clayDataProviderKey) {

		ServiceWrapper<ClayDataSetDataProvider> clayDataProviderServiceWrapper =
			_serviceTrackerMap.getService(clayDataProviderKey);

		if (clayDataProviderServiceWrapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No Clay data provider registered with key " +
						clayDataProviderKey);
			}

			return null;
		}

		return clayDataProviderServiceWrapper.getService();
	}

	@Override
	public List<ClayDataSetDataProvider> getClayDataSetProviders() {
		List<ClayDataSetDataProvider> clayDataProviders = new ArrayList<>();

		List<ServiceWrapper<ClayDataSetDataProvider>>
			clayDataProviderServiceWrappers = ListUtil.fromCollection(
				_serviceTrackerMap.values());

		for (ServiceWrapper<ClayDataSetDataProvider>
				clayDataProviderServiceWrapper :
					clayDataProviderServiceWrappers) {

			clayDataProviders.add(clayDataProviderServiceWrapper.getService());
		}

		return Collections.unmodifiableList(clayDataProviders);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, ClayDataSetDataProvider.class,
			"clay.data.provider.key",
			ServiceTrackerCustomizerFactory.
				<ClayDataSetDataProvider>serviceWrapper(bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ClayDataProviderRegistryImpl.class);

	private ServiceTrackerMap<String, ServiceWrapper<ClayDataSetDataProvider>>
		_serviceTrackerMap;

}