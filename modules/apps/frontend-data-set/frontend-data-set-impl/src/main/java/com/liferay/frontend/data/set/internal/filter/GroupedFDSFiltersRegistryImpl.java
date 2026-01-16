/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.filter;

import com.liferay.frontend.data.set.filter.GroupedFDSFilters;
import com.liferay.frontend.data.set.filter.GroupedFDSFiltersRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Daniel Sanz
 */
@Component(service = GroupedFDSFiltersRegistry.class)
public class GroupedFDSFiltersRegistryImpl
	implements GroupedFDSFiltersRegistry {

	public GroupedFDSFiltersRegistryImpl() {
	}

	public GroupedFDSFiltersRegistryImpl(
		ServiceTrackerMap<String, ServiceWrapper<GroupedFDSFilters>>
			serviceTrackerMap) {

		_serviceTrackerMap = serviceTrackerMap;
	}

	@Override
	public GroupedFDSFilters getGroupedFDSFilters(String fdsName) {
		ServiceWrapper<GroupedFDSFilters> groupedFDSFiltersServiceWrapper =
			_serviceTrackerMap.getService(fdsName);

		if (groupedFDSFiltersServiceWrapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No grouped frontend data set filters are associated " +
						"with " + fdsName);
			}

			return null;
		}

		return groupedFDSFiltersServiceWrapper.getService();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, GroupedFDSFilters.class, "frontend.data.set.name",
			ServiceTrackerCustomizerFactory.<GroupedFDSFilters>serviceWrapper(
				bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GroupedFDSFiltersRegistryImpl.class);

	private ServiceTrackerMap<String, ServiceWrapper<GroupedFDSFilters>>
		_serviceTrackerMap;

}