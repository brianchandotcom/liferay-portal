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

package com.liferay.users.admin.web.internal.users.admin.management.toolbar;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.users.admin.management.toolbar.FilterContributor;

import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Drew Brokke
 */
@Component(service = FilterContributorTracker.class)
public class FilterContributorTracker {

	public FilterContributor[] getFilterContributors(String id) {
		List<FilterContributor> filterContributors =
			_serviceTrackerMap.getService(id);

		if (filterContributors == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("No filter contributors found for ID " + id);
			}

			return new FilterContributor[0];
		}

		return filterContributors.toArray(new FilterContributor[0]);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, FilterContributor.class, null,
			(serviceReference, emitter) -> {
				FilterContributor filterContributor = bundleContext.getService(
					serviceReference);

				emitter.emit(filterContributor.getManagementToolbarKey());
			});
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FilterContributorTracker.class);

	private ServiceTrackerMap<String, List<FilterContributor>>
		_serviceTrackerMap;

}