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

package com.liferay.frontend.data.set.internal.filter;

import com.liferay.frontend.data.set.filter.FrontendDataSetFilterContextContributor;
import com.liferay.frontend.data.set.filter.FrontendDataSetFilterContextContributorRegistry;
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
@Component(
	immediate = true,
	service = FrontendDataSetFilterContextContributorRegistry.class
)
public class FrontendDataSetFilterContextContributorRegistryImpl
	implements FrontendDataSetFilterContextContributorRegistry {

	@Override
	public List<FrontendDataSetFilterContextContributor>
		getFrontendDataSetFilterContextContributors(
			String frontendDataSetFilterType) {

		List<ServiceWrapper<FrontendDataSetFilterContextContributor>>
			frontendDataSetFilterContextContributorServiceWrappers =
				_serviceTrackerMap.getService(frontendDataSetFilterType);

		if (frontendDataSetFilterContextContributorServiceWrappers == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No frontend data set filter context contributor is " +
						"associated with " + frontendDataSetFilterType);
			}

			return Collections.emptyList();
		}

		List<FrontendDataSetFilterContextContributor>
			frontendDataSetFilterContextContributors = new ArrayList<>();

		for (ServiceWrapper<FrontendDataSetFilterContextContributor>
				frontendDataSetFilterContextContributorServiceWrapper :
					frontendDataSetFilterContextContributorServiceWrappers) {

			frontendDataSetFilterContextContributors.add(
				frontendDataSetFilterContextContributorServiceWrapper.
					getService());
		}

		return frontendDataSetFilterContextContributors;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, FrontendDataSetFilterContextContributor.class,
			"frontend.data.set.filter.type",
			ServiceTrackerCustomizerFactory.
				<FrontendDataSetFilterContextContributor>serviceWrapper(
					bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FrontendDataSetFilterContextContributorRegistryImpl.class);

	private ServiceTrackerMap
		<String, List<ServiceWrapper<FrontendDataSetFilterContextContributor>>>
			_serviceTrackerMap;

}