/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.sort;

import com.liferay.frontend.data.set.sort.FDSSorts;
import com.liferay.frontend.data.set.sort.FDSSortsRegistry;
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
@Component(service = FDSSortsRegistry.class)
public class FDSSortsRegistryImpl implements FDSSortsRegistry {

	public FDSSortsRegistryImpl() {
	}

	public FDSSortsRegistryImpl(
		ServiceTrackerMap
			<String, ServiceTrackerCustomizerFactory.ServiceWrapper<FDSSorts>>
				serviceTrackerMap) {

		_serviceTrackerMap = serviceTrackerMap;
	}

	@Override
	public FDSSorts getFDSSorts(String fdsName) {
		ServiceTrackerCustomizerFactory.ServiceWrapper<FDSSorts>
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
			bundleContext, FDSSorts.class, "frontend.data.set.name",
			ServiceTrackerCustomizerFactory.<FDSSorts>serviceWrapper(
				bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FDSSortsRegistryImpl.class);

	private ServiceTrackerMap
		<String, ServiceTrackerCustomizerFactory.ServiceWrapper<FDSSorts>>
			_serviceTrackerMap;

}