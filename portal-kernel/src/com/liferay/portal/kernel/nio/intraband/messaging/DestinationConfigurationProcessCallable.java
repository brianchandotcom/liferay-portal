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

package com.liferay.portal.kernel.nio.intraband.messaging;

import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.process.ProcessCallable;
import com.liferay.portal.kernel.process.ProcessException;

/**
 * @author Shuyang Zhou
 */
public class DestinationConfigurationProcessCallable
	implements ProcessCallable<Boolean> {

	public DestinationConfigurationProcessCallable(String destinationName) {
		_destinationName = destinationName;
	}

	@Override
	public Boolean call() throws ProcessException {
		MessageBus messageBus = MessageBusUtil.getMessageBus();

		Destination destination = messageBus.getDestination(_destinationName);

		if (destination == null) {
			throw new ProcessException("No such destination " + destination);
		}

		if (destination instanceof IntrabandBridgeDestination) {
			return Boolean.FALSE;
		}

		IntrabandBridgeDestination intrabandBridgeDestination =
			new IntrabandBridgeDestination(destination);

		messageBus.addDestination(intrabandBridgeDestination);

		return Boolean.TRUE;
	}

	private static final long serialVersionUID = 1L;

	private final String _destinationName;

}