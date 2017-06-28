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

package com.liferay.portal.test.rule.callback;

import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.test.rule.callback.BaseTestCallback;
import com.liferay.portal.kernel.util.ReflectionUtil;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import org.junit.runner.Description;

/**
 * @author Shuyang Zhou
 */
public class DestinationAwaitTestCallback
	extends BaseTestCallback<Set<CountDownLatch>, Void> {

	public static final DestinationAwaitTestCallback INSTANCE =
		new DestinationAwaitTestCallback(
			DestinationNames.DOCUMENT_LIBRARY_SYNC_EVENT_PROCESSOR,
			DestinationNames.HOT_DEPLOY);

	public DestinationAwaitTestCallback(String... destinationNames) {
		_destinationNames = destinationNames;
	}

	@Override
	public void afterClass(
			Description description, Set<CountDownLatch> endCountDownLatches)
		throws Throwable {

		endCountDownLatches.forEach(CountDownLatch::countDown);
	}

	@Override
	public Set<CountDownLatch> beforeClass(Description description)
		throws InterruptedException {

		Set<CountDownLatch> endCountdownLatches = new HashSet<>();

		for (String destinationName : _destinationNames) {
			Destination destination = MessageBusUtil.getDestination(
				destinationName);

			final CountDownLatch startCountDownLatch = new CountDownLatch(1);

			final CountDownLatch endCountDownLatch = new CountDownLatch(1);

			final Message countDownMessage = new Message();

			destination.register(
				new MessageListener() {

					@Override
					public void receive(Message message) {
						if (countDownMessage == message) {
							startCountDownLatch.countDown();

							try {
								endCountDownLatch.await();

								destination.unregister(this);
							}
							catch (InterruptedException ie) {
								ReflectionUtil.throwException(ie);
							}
						}
					}

				});

			destination.send(countDownMessage);

			startCountDownLatch.await();

			endCountdownLatches.add(endCountDownLatch);
		}

		return endCountdownLatches;
	}

	private final String[] _destinationNames;

}