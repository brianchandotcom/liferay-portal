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

package com.liferay.portal.messaging.internal;

import com.liferay.portal.kernel.executor.PortalExecutorManager;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfig;
import com.liferay.portal.kernel.messaging.SerialDestination;
import com.liferay.portal.messaging.DestinationPrototype;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true, property = { "type=serial" },
	service = DestinationPrototype.class
)
public class SerialDestinationPrototype implements DestinationPrototype {

	@Override
	public Destination createDestination(DestinationConfig destinationConfig) {
		SerialDestination serialDestination = new SerialDestination();

		serialDestination.setName(destinationConfig.getDestinationName());

		serialDestination.setMaximumQueueSize(
			destinationConfig.getMaximumQueueSize());
		serialDestination.setRejectedExecutionHandler(
			destinationConfig.getRejectedExecutionHandler());
		serialDestination.setWorkersCoreSize(_WORKERS_CORE_SIZE);
		serialDestination.setWorkersMaxSize(_WORKERS_MAX_SIZE);

		serialDestination.afterPropertiesSet();

		return serialDestination;
	}

	@Reference(unbind = "-")
	protected void setPortalExecutorManager(
		PortalExecutorManager portalExecutorManager) {

		_portalExecutorManager = portalExecutorManager;
	}

	private static final int _WORKERS_CORE_SIZE = 1;

	private static final int _WORKERS_MAX_SIZE = 1;

	private PortalExecutorManager _portalExecutorManager;

}