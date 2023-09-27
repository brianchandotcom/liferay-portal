/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.filter;

import com.liferay.frontend.data.set.filter.FDSFilter;
import com.liferay.frontend.data.set.filter.FDSFilterRegistry;
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
@Component(service = FDSFilterRegistry.class)
public class FDSFilterRegistryImpl implements FDSFilterRegistry {

	@Override
	public List<FDSFilter> getFDSFilters(String fdsName) {
		List<ServiceWrapper<FDSFilter>> fdsFilterServiceWrappers =
			_serviceTrackerMap.getService(fdsName);

		if (fdsFilterServiceWrappers == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No frontend data set filter is associated with " +
						fdsName);
			}

			return Collections.emptyList();
		}

		List<FDSFilter> fdsFilters = new ArrayList<>();

		for (ServiceWrapper<FDSFilter> fdsFilterServiceWrapper :
				fdsFilterServiceWrappers) {

			fdsFilters.add(fdsFilterServiceWrapper.getService());
		}

		return fdsFilters;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, FDSFilter.class, "frontend.data.set.name",
			ServiceTrackerCustomizerFactory.<FDSFilter>serviceWrapper(
				bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FDSFilterRegistryImpl.class);

	private ServiceTrackerMap<String, List<ServiceWrapper<FDSFilter>>>
		_serviceTrackerMap;

}