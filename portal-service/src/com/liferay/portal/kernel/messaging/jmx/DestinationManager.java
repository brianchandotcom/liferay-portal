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

package com.liferay.portal.kernel.messaging.jmx;

import com.liferay.portal.kernel.messaging.Destination;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public class DestinationManager implements DestinationManagerMBean {

	public static ObjectName createObjectName(String destinationName) {
		try {
			return new ObjectName(_OBJECT_NAME_PREFIX + destinationName);
		}
		catch (MalformedObjectNameException mone) {
			throw new IllegalStateException(mone);
		}
	}

	public DestinationManager(Destination destination) {
		_destination = destination;
	}

	@Override
	public int getListenerCount() {
		return _destination.getMessageListenerCount();
	}

	private static final String _OBJECT_NAME_PREFIX =
		"Liferay:product=Portal,type=MessagingDestination,name=";

	private final Destination _destination;

}