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

package com.liferay.portal.messaging.internal.configuration.persistence.listener;

import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.MessageBusUtil;

import java.util.Dictionary;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jonathan McCann
 */
@Component(
	configurationPid = "com.liferay.portal.messaging.internal.configuration.DestinationWorkerConfiguration",
	property = "model.class.name=com.liferay.portal.messaging.internal.configuration.DestinationWorkerConfiguration",
	service = ConfigurationModelListener.class
)
public class DestinationWorkerConfigurationModelListener
	implements ConfigurationModelListener {

	@Override
	public void onBeforeSave(
		String pid, Dictionary<String, Object> properties) {

		String destinationName = (String)properties.get("destinationName");

		Destination destination = MessageBusUtil.getDestination(
			destinationName);

		if ((destination != null) &&
			Objects.equals(
				destination.getDestinationType(),
				DestinationConfiguration.DESTINATION_TYPE_SERIAL)) {

			properties.put("workerCoreSize", _WORKER_CORE_SIZE);
			properties.put("workerMaxSize", _WORKER_MAX_SIZE);
		}
	}

	private static final int _WORKER_CORE_SIZE = 1;

	private static final int _WORKER_MAX_SIZE = 1;

}