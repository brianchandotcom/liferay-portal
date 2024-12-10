/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.sort;

import com.liferay.frontend.data.set.sort.FDSSortList;
import com.liferay.frontend.data.set.sort.FDSSortListRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
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
@Component(service = FDSSortListRegistry.class)
public class FDSSortListRegistryImpl implements FDSSortListRegistry {

	@Override
	public FDSSortList getFDSSortList(String fdsName) {
		ServiceTrackerCustomizerFactory.ServiceWrapper<FDSSortList>
			serviceWrapper = _serviceTrackerMap.getService(fdsName);

		if (serviceWrapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No frontend data set sort list is associated with " +
						fdsName);
			}

			return null;
		}

		return serviceWrapper.getService();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, FDSSortList.class, "frontend.data.set.name",
			ServiceTrackerCustomizerFactory.<FDSSortList>serviceWrapper(
				bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FDSSortListRegistryImpl.class);

	private ServiceTrackerMap
		<String, ServiceTrackerCustomizerFactory.ServiceWrapper<FDSSortList>>
			_serviceTrackerMap;

}