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

import com.liferay.frontend.data.set.provider.FDSActionProvider;
import com.liferay.frontend.data.set.provider.FDSActionProviderRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

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
@Component(immediate = true, service = FDSActionProviderRegistry.class)
public class FDSActionProviderRegistryImpl
	implements FDSActionProviderRegistry {

	@Override
	public List<FDSActionProvider> getFDSActionProviders(
		String fdsActionProviderKey) {

		List<ServiceWrapper<FDSActionProvider>>
			fdsActionProviderServiceWrappers = _serviceTrackerMap.getService(
				fdsActionProviderKey);

		if (fdsActionProviderServiceWrappers == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No frontend data set action provider is associated with " +
						fdsActionProviderKey);
			}

			return Collections.emptyList();
		}

		List<FDSActionProvider> fdsActionProviders = new ArrayList<>();

		for (ServiceWrapper<FDSActionProvider>
				tableActionProviderServiceWrapper :
					fdsActionProviderServiceWrappers) {

			fdsActionProviders.add(
				tableActionProviderServiceWrapper.getService());
		}

		return fdsActionProviders;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, FDSActionProvider.class, "fds.data.provider.key",
			ServiceTrackerCustomizerFactory.<FDSActionProvider>serviceWrapper(
				bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FDSActionProviderRegistryImpl.class);

	private ServiceTrackerMap<String, List<ServiceWrapper<FDSActionProvider>>>
		_serviceTrackerMap;

}