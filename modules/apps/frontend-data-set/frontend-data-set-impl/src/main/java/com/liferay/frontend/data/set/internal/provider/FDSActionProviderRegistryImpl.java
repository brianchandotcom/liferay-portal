/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.provider;

import com.liferay.frontend.data.set.provider.FDSActionProvider;
import com.liferay.frontend.data.set.provider.FDSActionProviderRegistry;
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
@Component(service = FDSActionProviderRegistry.class)
public class FDSActionProviderRegistryImpl
	implements FDSActionProviderRegistry {

	@Override
	public List<FDSActionProvider> getFDSActionProviders(
		String fdsActionProviderKey) {

		List<ServiceWrapper<FDSActionProvider>>
			fdsActionProviderServiceWrappers = _serviceTrackerMap.getService(
				fdsActionProviderKey);

		if (fdsActionProviderServiceWrappers == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No frontend data set action provider is associated with " +
						fdsActionProviderKey);
			}

			return Collections.emptyList();
		}

		List<FDSActionProvider> fdsActionProviders = new ArrayList<>();

		for (ServiceWrapper<FDSActionProvider>
				tableActionProviderServiceWrapper :
					fdsActionProviderServiceWrappers) {

			fdsActionProviders.add(
				tableActionProviderServiceWrapper.getService());
		}

		return fdsActionProviders;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, FDSActionProvider.class, "fds.data.provider.key",
			ServiceTrackerCustomizerFactory.<FDSActionProvider>serviceWrapper(
				bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FDSActionProviderRegistryImpl.class);

	private ServiceTrackerMap<String, List<ServiceWrapper<FDSActionProvider>>>
		_serviceTrackerMap;

}