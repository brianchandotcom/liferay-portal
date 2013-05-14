/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.messaging;

import java.util.Random;
import java.util.concurrent.Callable;

/**
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 */
public class DestinationRegistrationTask implements Callable<Object> {

	public DestinationRegistrationTask(
		Destination destination, MessageListener[] listeners,
		int taskIterationCount, boolean register) {

		_destination = destination;
		_listeners = listeners;
		_taskIterationCount = taskIterationCount;
		_register = register;
	}

	@Override
	public Object call() {
		Random random = new Random();

		for (int i = 0; i < _taskIterationCount; i++) {
			MessageListener listener = _listeners[random.nextInt(
				_listeners.length)];

			if (_register) {
				_destination.register(listener);
			}
			else {
				_destination.unregister(listener);
			}
		}

		return null;
	}

	private Destination _destination;
	private MessageListener[] _listeners;
	private boolean _register;
	private int _taskIterationCount;

}