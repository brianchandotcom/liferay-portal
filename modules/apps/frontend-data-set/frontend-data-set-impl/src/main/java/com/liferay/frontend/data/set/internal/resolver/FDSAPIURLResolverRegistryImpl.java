/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.resolver;

import com.liferay.frontend.data.set.resolver.FDSAPIURLResolver;
import com.liferay.frontend.data.set.resolver.FDSAPIURLResolverRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringBundler;
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
 * @author Gianmarco Brunialti Masera
 */
@Component(service = FDSAPIURLResolverRegistry.class)
public class FDSAPIURLResolverRegistryImpl
	implements FDSAPIURLResolverRegistry {

	@Override
	public FDSAPIURLResolver getFDSAPIURLResolver(
		String restApplication, String restSchema) {

		String key = StringBundler.concat(restApplication, "/", restSchema);

		ServiceWrapper<FDSAPIURLResolver> serviceWrapper =
			_serviceTrackerMap.getService(key);

		if (serviceWrapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("No REST application found for " + key);
			}

			return null;
		}

		return serviceWrapper.getService();
	}

	@Override
	public List<FDSAPIURLResolver> getFDSAPIURLResolvers() {
		List<FDSAPIURLResolver> fdsAPIURLResolvers = new ArrayList<>();

		for (ServiceWrapper<FDSAPIURLResolver> serviceWrapper :
				_serviceTrackerMap.values()) {

			fdsAPIURLResolvers.add(serviceWrapper.getService());
		}

		return Collections.unmodifiableList(fdsAPIURLResolvers);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, FDSAPIURLResolver.class, "fds.rest.application.key",
			ServiceTrackerCustomizerFactory.<FDSAPIURLResolver>serviceWrapper(
				bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FDSAPIURLResolverRegistryImpl.class);

	private ServiceTrackerMap<String, ServiceWrapper<FDSAPIURLResolver>>
		_serviceTrackerMap;

}