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

package com.liferay.frontend.taglib.clay.internal.data.set;

import com.liferay.frontend.taglib.clay.data.set.ClayDataSetActionProvider;
import com.liferay.frontend.taglib.clay.data.set.ClayDataSetActionProviderRegistry;
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
@Component(immediate = true, service = ClayDataSetActionProviderRegistry.class)
public class ClayDataSetActionProviderRegistryImpl
	implements ClayDataSetActionProviderRegistry {

	@Override
	public List<ClayDataSetActionProvider> getClayDataSetActionProviders(
		String clayDataProviderKey) {

		List<ServiceWrapper<ClayDataSetActionProvider>>
			clayDataSetActionProviderServiceWrappers =
				_serviceTrackerMap.getService(clayDataProviderKey);

		if (clayDataSetActionProviderServiceWrappers == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No Clay data set action provider registered with key " +
						clayDataProviderKey);
			}

			return Collections.emptyList();
		}

		List<ClayDataSetActionProvider> clayDataSetActionProviders =
			new ArrayList<>();

		for (ServiceWrapper<ClayDataSetActionProvider>
				tableActionProviderServiceWrapper :
					clayDataSetActionProviderServiceWrappers) {

			clayDataSetActionProviders.add(
				tableActionProviderServiceWrapper.getService());
		}

		return clayDataSetActionProviders;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, ClayDataSetActionProvider.class,
			"clay.data.provider.key",
			ServiceTrackerCustomizerFactory.
				<ClayDataSetActionProvider>serviceWrapper(bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ClayDataSetActionProviderRegistryImpl.class);

	private ServiceTrackerMap
		<String, List<ServiceWrapper<ClayDataSetActionProvider>>>
			_serviceTrackerMap;

}