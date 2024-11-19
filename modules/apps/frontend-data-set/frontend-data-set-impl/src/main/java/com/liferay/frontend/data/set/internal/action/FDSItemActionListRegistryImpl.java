/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.action;

import com.liferay.frontend.data.set.action.FDSItemActionList;
import com.liferay.frontend.data.set.action.FDSItemActionListRegistry;
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
@Component(service = FDSItemActionListRegistry.class)
public class FDSItemActionListRegistryImpl
	implements FDSItemActionListRegistry {

	@Override
	public FDSItemActionList getFDSItemActionList(String fdsName) {
		ServiceTrackerCustomizerFactory.ServiceWrapper<FDSItemActionList>
			serviceWrapper = _serviceTrackerMap.getService(fdsName);

		if (serviceWrapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No frontend data set item action list is associated with " +
						fdsName);
			}

			return null;
		}

		return serviceWrapper.getService();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, FDSItemActionList.class, "frontend.data.set.name",
			ServiceTrackerCustomizerFactory.<FDSItemActionList>serviceWrapper(
				bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FDSItemActionListRegistryImpl.class);

	private ServiceTrackerMap
		<String,
		 ServiceTrackerCustomizerFactory.ServiceWrapper<FDSItemActionList>>
			_serviceTrackerMap;

}