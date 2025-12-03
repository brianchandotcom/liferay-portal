/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.filter;

import com.liferay.frontend.data.set.filter.FDSFiltersGroups;
import com.liferay.frontend.data.set.filter.FDSFiltersGroupsRegistry;
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
@Component(service = FDSFiltersGroupsRegistry.class)
public class FDSFiltersGroupsRegistryImpl implements FDSFiltersGroupsRegistry {

	public FDSFiltersGroupsRegistryImpl() {
	}

	public FDSFiltersGroupsRegistryImpl(
		ServiceTrackerMap<String, ServiceWrapper<FDSFiltersGroups>>
			serviceTrackerMap) {

		_serviceTrackerMap = serviceTrackerMap;
	}

	@Override
	public FDSFiltersGroups getFDSFiltersGroups(String fdsName) {
		ServiceWrapper<FDSFiltersGroups> fdsFiltersGroupsServiceWrapper =
			_serviceTrackerMap.getService(fdsName);

		if (fdsFiltersGroupsServiceWrapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No frontend data set filters groups are associated with " +
						fdsName);
			}

			return null;
		}

		return fdsFiltersGroupsServiceWrapper.getService();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, FDSFiltersGroups.class, "frontend.data.set.name",
			ServiceTrackerCustomizerFactory.<FDSFiltersGroups>serviceWrapper(
				bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FDSFiltersGroupsRegistryImpl.class);

	private ServiceTrackerMap<String, ServiceWrapper<FDSFiltersGroups>>
		_serviceTrackerMap;

}