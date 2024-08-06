/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.resolver;

import com.liferay.frontend.data.set.resolver.FDSRestApplicationURLParameterResolver;
import com.liferay.frontend.data.set.resolver.FDSRestApplicationURLParameterResolverRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;

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
@Component(service = FDSRestApplicationURLParameterResolverRegistry.class)
public class FDSRestApplicationURLParameterResolverRegistryImpl
	implements FDSRestApplicationURLParameterResolverRegistry {

	@Override
	public FDSRestApplicationURLParameterResolver getResolver(
		String restApplication, String restSchema) {

		String resolverKey = StringBundler.concat(
			restApplication, "/", restSchema);

		ServiceWrapper<FDSRestApplicationURLParameterResolver>
			resolverServiceWrapper = _serviceTrackerMap.getService(resolverKey);

		if (resolverServiceWrapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No rest application found for: \"" + resolverKey + "\"");
			}

			return null;
		}

		return resolverServiceWrapper.getService();
	}

	@Override
	public List<FDSRestApplicationURLParameterResolver> getResolvers() {
		List<FDSRestApplicationURLParameterResolver> resolvers =
			new ArrayList<>();

		List<ServiceWrapper<FDSRestApplicationURLParameterResolver>>
			resolversServiceWrappers = ListUtil.fromCollection(
				_serviceTrackerMap.values());

		for (ServiceWrapper<FDSRestApplicationURLParameterResolver>
				resolverServiceWrapper : resolversServiceWrappers) {

			resolvers.add(resolverServiceWrapper.getService());
		}

		return Collections.unmodifiableList(resolvers);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, FDSRestApplicationURLParameterResolver.class,
			"fds.rest.application.key",
			ServiceTrackerCustomizerFactory.
				<FDSRestApplicationURLParameterResolver>serviceWrapper(
					bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FDSRestApplicationURLParameterResolverRegistryImpl.class);

	private ServiceTrackerMap
		<String, ServiceWrapper<FDSRestApplicationURLParameterResolver>>
			_serviceTrackerMap;

}