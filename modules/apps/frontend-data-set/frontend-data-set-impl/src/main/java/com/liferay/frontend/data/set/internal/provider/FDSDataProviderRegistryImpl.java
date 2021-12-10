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

package com.liferay.frontend.data.set.internal.provider;

import com.liferay.frontend.data.set.provider.FDSDataProvider;
import com.liferay.frontend.data.set.provider.FDSDataProviderRegistry;
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
@Component(immediate = true, service = FDSDataProviderRegistry.class)
public class FDSDataProviderRegistryImpl implements FDSDataProviderRegistry {

	@Override
	public FDSDataProvider getFDSDataProvider(String fdsDataProviderKey) {
		ServiceWrapper<FDSDataProvider> fdsDataProviderServiceWrapper =
			_serviceTrackerMap.getService(fdsDataProviderKey);

		if (fdsDataProviderServiceWrapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No frontend data provider is associated with " +
						fdsDataProviderKey);
			}

			return null;
		}

		return fdsDataProviderServiceWrapper.getService();
	}

	@Override
	public List<FDSDataProvider> getFDSDataProviders() {
		List<FDSDataProvider> fdsDataProviders = new ArrayList<>();

		List<ServiceWrapper<FDSDataProvider>> fdsDataProviderServiceWrappers =
			ListUtil.fromCollection(_serviceTrackerMap.values());

		for (ServiceWrapper<FDSDataProvider> fdsDataProviderServiceWrapper :
				fdsDataProviderServiceWrappers) {

			fdsDataProviders.add(fdsDataProviderServiceWrapper.getService());
		}

		return Collections.unmodifiableList(fdsDataProviders);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, FDSDataProvider.class, "fds.data.provider.key",
			ServiceTrackerCustomizerFactory.<FDSDataProvider>serviceWrapper(
				bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FDSDataProviderRegistryImpl.class);

	private ServiceTrackerMap<String, ServiceWrapper<FDSDataProvider>>
		_serviceTrackerMap;

}