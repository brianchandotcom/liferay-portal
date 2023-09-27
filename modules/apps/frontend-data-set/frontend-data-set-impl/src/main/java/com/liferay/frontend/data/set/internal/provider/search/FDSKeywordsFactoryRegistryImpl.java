/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
@Component(service = FDSKeywordsFactoryRegistry.class)
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
		ServiceWrapper<FDSKeywordsFactory> fdsKeywordsFactory =
			_serviceTrackerMap.getService(fdsDataProviderKey);

		if (fdsKeywordsFactory == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No frontend data set keywords factory is associated " +
						"with " + fdsDataProviderKey);
			}

			return new FDSKeywordsFactoryImpl();
		}

		return fdsKeywordsFactory.getService();
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