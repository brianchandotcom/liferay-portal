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

package com.liferay.portal.kernel.messaging.sender;

import com.liferay.portal.kernel.messaging.MessageBus;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Michael C. Han
 */
public class DefaultSingleDestinationMessageSenderFactory
	implements SingleDestinationMessageSenderFactory {

	@Override
	public SingleDestinationMessageSender createSingleDestinationMessageSender(
		String destinationName) {

		DefaultSingleDestinationMessageSender
			defaultSingleDestinationMessageSender =
				_singleDestinationMessageSenders.get(destinationName);

		if (defaultSingleDestinationMessageSender == null) {
			defaultSingleDestinationMessageSender =
				new DefaultSingleDestinationMessageSender();

			defaultSingleDestinationMessageSender.setDestinationName(
				destinationName);

			defaultSingleDestinationMessageSender.setMessageBus(_messageBus);

			_singleDestinationMessageSenders.put(
				destinationName, defaultSingleDestinationMessageSender);
		}

		return defaultSingleDestinationMessageSender;
	}

	@Override
	public SingleDestinationSynchronousMessageSender
		createSingleDestinationSynchronousMessageSender(
			String destinationName, SynchronousMessageSender.Mode mode) {

		DefaultSingleDestinationSynchronousMessageSender
			defaultSingleDestinationSynchronousMessageSender =
				_singleDestinationSynchronousMessageSenders.get(
					destinationName);

		if (defaultSingleDestinationSynchronousMessageSender == null) {
			SynchronousMessageSender synchronousMessageSender =
				_synchronousMessageSenders.get(mode);

			if (synchronousMessageSender == null) {
				throw new IllegalStateException(
					"No SynchronousMessageSender configured for: " + mode);
			}

			defaultSingleDestinationSynchronousMessageSender =
				new DefaultSingleDestinationSynchronousMessageSender();

			defaultSingleDestinationSynchronousMessageSender.setDestinationName(
				destinationName);

			defaultSingleDestinationSynchronousMessageSender.
				setSynchronousMessageSender(synchronousMessageSender);

			_singleDestinationSynchronousMessageSenders.put(
				destinationName,
				defaultSingleDestinationSynchronousMessageSender);
		}

		return defaultSingleDestinationSynchronousMessageSender;
	}

	public void setMessageBus(MessageBus messageBus) {
		_messageBus = messageBus;
	}

	public void setSynchronousMessageSenders(
		Map<SynchronousMessageSender.Mode, SynchronousMessageSender>
			synchronousMessageSenders) {

		_synchronousMessageSenders.putAll(synchronousMessageSenders);
	}

	private MessageBus _messageBus;
	private final Map<String, DefaultSingleDestinationMessageSender>
		_singleDestinationMessageSenders = new ConcurrentHashMap<>();
	private final Map<String, DefaultSingleDestinationSynchronousMessageSender>
		_singleDestinationSynchronousMessageSenders = new ConcurrentHashMap<>();
	private final Map<SynchronousMessageSender.Mode, SynchronousMessageSender>
		_synchronousMessageSenders = new HashMap<>();

}