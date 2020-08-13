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

import com.liferay.frontend.taglib.clay.data.set.ClayDataSetContentRendererContextContributor;
import com.liferay.frontend.taglib.clay.data.set.ClayDataSetContentRendererContextContributorRegistry;
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
	service = ClayDataSetContentRendererContextContributorRegistry.class
)
public class ClayDataSetContentRendererContextContributorRegistryImpl
	implements ClayDataSetContentRendererContextContributorRegistry {

	@Override
	public List<ClayDataSetContentRendererContextContributor>
		getClayDataSetContentRendererContextContributors(
			String clayDataSetContentRendererName) {

		List<ServiceWrapper<ClayDataSetContentRendererContextContributor>>
			clayDataSetContentRendererContextContributorServiceWrappers =
				_serviceTrackerMap.getService(clayDataSetContentRendererName);

		if (clayDataSetContentRendererContextContributorServiceWrappers ==
				null) {

			if (_log.isDebugEnabled()) {
				_log.debug(
					"No Clay data set content renderer context contributor " +
						"registered with name " +
							clayDataSetContentRendererName);
			}

			return Collections.emptyList();
		}

		List<ClayDataSetContentRendererContextContributor>
			clayDataSetContentRendererContextContributors = new ArrayList<>();

		for (ServiceWrapper<ClayDataSetContentRendererContextContributor>
				clayDataSetContentRendererContextContributorServiceWrapper :
					clayDataSetContentRendererContextContributorServiceWrappers) {

			clayDataSetContentRendererContextContributors.add(
				clayDataSetContentRendererContextContributorServiceWrapper.
					getService());
		}

		return clayDataSetContentRendererContextContributors;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, ClayDataSetContentRendererContextContributor.class,
			"clay.data.set.content.renderer.name",
			ServiceTrackerCustomizerFactory.
				<ClayDataSetContentRendererContextContributor>serviceWrapper(
					bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ClayDataSetContentRendererContextContributorRegistryImpl.class);

	private ServiceTrackerMap
		<String,
		 List<ServiceWrapper<ClayDataSetContentRendererContextContributor>>>
			_serviceTrackerMap;

}