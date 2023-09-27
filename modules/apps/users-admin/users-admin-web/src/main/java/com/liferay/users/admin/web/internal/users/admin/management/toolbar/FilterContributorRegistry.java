/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
@Component(service = FilterContributorRegistry.class)
public class FilterContributorRegistry {

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
		FilterContributorRegistry.class);

	private ServiceTrackerMap<String, List<FilterContributor>>
		_serviceTrackerMap;

}