/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.messaging.internal;

import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.MessageListenerRegistry;

/**
 * @author Michael C. Han
 */
public class SynchronousDestinationPrototype implements DestinationPrototype {

	public SynchronousDestinationPrototype(
		MessageListenerRegistry messageListenerRegistry) {

		_messageListenerRegistry = messageListenerRegistry;
	}

	@Override
	public Destination createDestination(
		DestinationConfiguration destinationConfiguration) {

		SynchronousDestination synchronousDestination =
			new SynchronousDestination();

		synchronousDestination.setDestinationType(
			destinationConfiguration.getDestinationType());
		synchronousDestination.setMessageListenerRegistry(
			_messageListenerRegistry);
		synchronousDestination.setName(
			destinationConfiguration.getDestinationName());

		return synchronousDestination;
	}

	private final MessageListenerRegistry _messageListenerRegistry;

}