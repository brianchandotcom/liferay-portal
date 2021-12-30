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

package com.liferay.frontend.data.set.internal.provider.search;

import com.liferay.frontend.data.set.provider.search.FDSKeywordsFactory;
import com.liferay.frontend.data.set.provider.search.FDSKeywordsFactoryRegistry;
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
@Component(immediate = true, service = FDSKeywordsFactoryRegistry.class)
public class FDSKeywordsFactoryRegistryImpl
	implements FDSKeywordsFactoryRegistry {

	@Override
	public List<FDSKeywordsFactory> getFDSKeywordsFactories() {
		List<FDSKeywordsFactory> filterFactories = new ArrayList<>();

		List<ServiceWrapper<FDSKeywordsFactory>> filterFactoryServiceWrappers =
			ListUtil.fromCollection(_serviceTrackerMap.values());

		for (ServiceWrapper<FDSKeywordsFactory> filterFactoryServiceWrapper :
				filterFactoryServiceWrappers) {

			filterFactories.add(filterFactoryServiceWrapper.getService());
		}

		return Collections.unmodifiableList(filterFactories);
	}

	@Override
	public FDSKeywordsFactory getFDSKeywordsFactory(String fdsDataProviderKey) {
		ServiceWrapper<FDSKeywordsFactory> filterFactoryServiceWrapper =
			_serviceTrackerMap.getService(fdsDataProviderKey);

		if (filterFactoryServiceWrapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No filter factory is associated with " +
						fdsDataProviderKey);
			}

			return new FDSKeywordsFactoryImpl();
		}

		return filterFactoryServiceWrapper.getService();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, FDSKeywordsFactory.class, "fds.data.provider.key",
			ServiceTrackerCustomizerFactory.<FDSKeywordsFactory>serviceWrapper(
				bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FDSKeywordsFactoryRegistryImpl.class);

	private ServiceTrackerMap<String, ServiceWrapper<FDSKeywordsFactory>>
		_serviceTrackerMap;

}