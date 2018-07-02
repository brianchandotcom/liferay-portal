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

package com.liferay.portal.messaging.internal.sender;

import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.sender.SingleDestinationMessageSender;
import com.liferay.portal.kernel.messaging.sender.SingleDestinationMessageSenderFactory;
import com.liferay.portal.kernel.messaging.sender.SingleDestinationSynchronousMessageSender;
import com.liferay.portal.kernel.messaging.sender.SynchronousMessageSender;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author     Michael C. Han
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@Component(
	immediate = true, service = SingleDestinationMessageSenderFactory.class
)
@Deprecated
public class DefaultSingleDestinationMessageSenderFactory
	implements SingleDestinationMessageSenderFactory {

	@Override
	public SingleDestinationMessageSender createSingleDestinationMessageSender(
		String destinationName) {

		DefaultSingleDestinationMessageSender
			defaultSingleDestinationMessageSender =
				_defaultSingleDestinationMessageSenders.get(destinationName);

		if (defaultSingleDestinationMessageSender == null) {
			defaultSingleDestinationMessageSender =
				new DefaultSingleDestinationMessageSender();

			defaultSingleDestinationMessageSender.setDestinationName(
				destinationName);
			defaultSingleDestinationMessageSender.setMessageBus(_messageBus);

			_defaultSingleDestinationMessageSenders.put(
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
				_defaultSingleDestinationSynchronousMessageSenders.get(
					destinationName);

		if (defaultSingleDestinationSynchronousMessageSender == null) {
			SynchronousMessageSender synchronousMessageSender =
				_synchronousMessageSenders.get(mode);

			if (synchronousMessageSender == null) {
				throw new IllegalStateException(
					"No synchronous message sender configured for " + mode);
			}

			defaultSingleDestinationSynchronousMessageSender =
				new DefaultSingleDestinationSynchronousMessageSender();

			defaultSingleDestinationSynchronousMessageSender.setDestinationName(
				destinationName);
			defaultSingleDestinationSynchronousMessageSender.
				setSynchronousMessageSender(synchronousMessageSender);

			_defaultSingleDestinationSynchronousMessageSenders.put(
				destinationName,
				defaultSingleDestinationSynchronousMessageSender);
		}

		return defaultSingleDestinationSynchronousMessageSender;
	}

	@Override
	public int getModesCount() {
		return _synchronousMessageSenders.size();
	}

	@Override
	public SynchronousMessageSender getSynchronousMessageSender(
		SynchronousMessageSender.Mode mode) {

		return _synchronousMessageSenders.get(mode);
	}

	protected SynchronousMessageSender.Mode getMode(
		Map<String, Object> properties) {

		String mode = GetterUtil.getString(properties.get("mode"));

		return SynchronousMessageSender.Mode.valueOf(mode);
	}

	@Reference(unbind = "-")
	protected void setMessageBus(MessageBus messageBus) {
		_messageBus = messageBus;
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void setSynchronousMessageSender(
		SynchronousMessageSender synchronousMessageSender,
		Map<String, Object> properties) {

		_synchronousMessageSenders.put(
			getMode(properties), synchronousMessageSender);
	}

	protected void unsetSynchronousMessageSender(
		SynchronousMessageSender synchronousMessageSender,
		Map<String, Object> properties) {

		_synchronousMessageSenders.remove(getMode(properties));
	}

	private final Map<String, DefaultSingleDestinationMessageSender>
		_defaultSingleDestinationMessageSenders = new ConcurrentHashMap<>();
	private final Map<String, DefaultSingleDestinationSynchronousMessageSender>
		_defaultSingleDestinationSynchronousMessageSenders =
			new ConcurrentHashMap<>();
	private MessageBus _messageBus;
	private final Map<SynchronousMessageSender.Mode, SynchronousMessageSender>
		_synchronousMessageSenders = new HashMap<>();

}