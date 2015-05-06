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
import com.liferay.portal.kernel.messaging.ParallelDestination;
import com.liferay.portal.messaging.DestinationPrototype;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true, property = { "type=parallel" },
	service = DestinationPrototype.class
)
public class ParallelDestinationPrototype implements DestinationPrototype {

	@Override
	public Destination createDestination(DestinationConfig destinationConfig) {
		ParallelDestination parallelDestination = new ParallelDestination();

		parallelDestination.setName(destinationConfig.getDestinationName());

		parallelDestination.setMaximumQueueSize(
			destinationConfig.getMaximumQueueSize());
		parallelDestination.setRejectedExecutionHandler(
			destinationConfig.getRejectedExecutionHandler());
		parallelDestination.setWorkersCoreSize(
			destinationConfig.getWorkersCoreSize());
		parallelDestination.setWorkersMaxSize(
			destinationConfig.getWorkersMaxSize());

		parallelDestination.afterPropertiesSet();

		return parallelDestination;
	}

	@Reference(unbind = "-")
	protected void setPortalExecutorManager(
		PortalExecutorManager portalExecutorManager) {

		_portalExecutorManager = portalExecutorManager;
	}

	private PortalExecutorManager _portalExecutorManager;

}