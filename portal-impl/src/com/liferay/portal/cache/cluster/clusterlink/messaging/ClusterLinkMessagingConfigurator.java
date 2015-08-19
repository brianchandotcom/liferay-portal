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

package com.liferay.portal.cache.cluster.clusterlink.messaging;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.portal.cache.cluster.configuration.PortalCacheClusterConfiguration;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.ParallelDestination;
import com.liferay.portal.kernel.messaging.config.DefaultMessagingConfigurator;

import java.util.Collections;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Tina Tian
 */
@Component(
	configurationPid = "com.liferay.portal.cache.cluster.configuration.PortalCacheClusterConfiguration",
	immediate = true, service = ClusterLinkMessagingConfigurator.class
)
public class ClusterLinkMessagingConfigurator {

	@Activate
	@Modified
	protected void activate(ComponentContext componentContext) {
		PortalCacheClusterConfiguration portalCacheClusterConfiguration =
			Configurable.createConfigurable(
				PortalCacheClusterConfiguration.class,
				componentContext.getProperties());

		String destinationName =
			portalCacheClusterConfiguration.destinationName();

		ParallelDestination parallelDestination = new ParallelDestination();

		parallelDestination.setName(destinationName);

		parallelDestination.afterPropertiesSet();

		MessageListener messageListener =
			new ClusterLinkPortalCacheClusterListener();

		DefaultMessagingConfigurator defaultMessagingConfigurator =
			new DefaultMessagingConfigurator();

		defaultMessagingConfigurator.setDestinations(
			Collections.singletonList((Destination)parallelDestination));

		defaultMessagingConfigurator.setMessageListeners(
			Collections.singletonMap(
				destinationName, Collections.singletonList(messageListener)));

		defaultMessagingConfigurator.afterPropertiesSet();
	}

}