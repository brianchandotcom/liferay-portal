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

package com.liferay.portal.test.rule;

import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.test.rule.ClassTestRule;

import java.util.HashSet;
import java.util.Set;

import org.junit.runner.Description;

/**
 * @author Javier Gamarra
 */
public class IgnoreMailTestRule extends ClassTestRule<Void> {

	public static final IgnoreMailTestRule INSTANCE = new IgnoreMailTestRule();

	@Override
	public Void beforeClass(Description description) {
		Destination destination = MessageBusUtil.getDestination(
			DestinationNames.MAIL);

		_messageListeners = new HashSet<>(destination.getMessageListeners());

		destination.unregisterMessageListeners();

		return null;
	}

	@Override
	protected void afterClass(Description description, Void unused) {
		Destination destination = MessageBusUtil.getDestination(
			DestinationNames.MAIL);

		for (MessageListener messageListener : _messageListeners) {
			destination.register(messageListener);
		}
	}

	private IgnoreMailTestRule() {
	}

	private Set<MessageListener> _messageListeners;

}