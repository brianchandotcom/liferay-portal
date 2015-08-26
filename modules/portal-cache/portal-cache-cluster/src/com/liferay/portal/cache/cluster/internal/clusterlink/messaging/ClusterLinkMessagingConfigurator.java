/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.cache.cluster.internal.clusterlink.messaging;

import com.liferay.portal.cache.cluster.internal.DestinationNames;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.util.HashMapDictionary;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Tina Tian
 */
@Component(
	configurationPid = "com.liferay.portal.cache.cluster.configuration.PortalCacheClusterConfiguration",
	immediate = true, service = ClusterLinkMessagingConfigurator.class
)
public class ClusterLinkMessagingConfigurator {

	@Activate
	protected void activate(BundleContext bundleContext) throws Exception {
		DestinationConfiguration destinationConfiguration =
			new DestinationConfiguration(
				DestinationConfiguration.DESTINATION_TYPE_PARALLEL,
				DestinationNames.CACHE_REPLICATION);

		_serviceRegistration = bundleContext.registerService(
			DestinationConfiguration.class, destinationConfiguration,
			new HashMapDictionary<String, Object>());
	}

	@Deactivate
	protected void deactivate() {
		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();
		}

		_serviceRegistration = null;
	}

	private ServiceRegistration<DestinationConfiguration> _serviceRegistration;

}