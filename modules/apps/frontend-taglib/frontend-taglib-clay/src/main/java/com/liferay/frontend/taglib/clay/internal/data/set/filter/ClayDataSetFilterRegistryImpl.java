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

package com.liferay.frontend.taglib.clay.internal.data.set.filter;

import com.liferay.frontend.taglib.clay.data.set.filter.ClayDataSetFilter;
import com.liferay.frontend.taglib.clay.data.set.filter.ClayDataSetFilterRegistry;
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
@Component(immediate = true, service = ClayDataSetFilterRegistry.class)
public class ClayDataSetFilterRegistryImpl
	implements ClayDataSetFilterRegistry {

	@Override
	public List<ClayDataSetFilter> getClayDataSetFilters(
		String clayDataSetDisplayName) {

		List<ServiceWrapper<ClayDataSetFilter>>
			clayDataSetFilterServiceWrappers = _serviceTrackerMap.getService(
				clayDataSetDisplayName);

		if (clayDataSetFilterServiceWrappers == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No Clay data set filter registered with name " +
						clayDataSetDisplayName);
			}

			return Collections.emptyList();
		}

		List<ClayDataSetFilter> clayDataSetFilters = new ArrayList<>();

		for (ServiceWrapper<ClayDataSetFilter> clayDataSetFilterServiceWrapper :
				clayDataSetFilterServiceWrappers) {

			clayDataSetFilters.add(
				clayDataSetFilterServiceWrapper.getService());
		}

		return clayDataSetFilters;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, ClayDataSetFilter.class,
			"clay.data.set.display.name",
			ServiceTrackerCustomizerFactory.<ClayDataSetFilter>serviceWrapper(
				bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ClayDataSetFilterRegistryImpl.class);

	private ServiceTrackerMap<String, List<ServiceWrapper<ClayDataSetFilter>>>
		_serviceTrackerMap;

}