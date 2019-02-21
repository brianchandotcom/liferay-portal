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

package com.liferay.arquillian.extension.junit.bridge.remote.manager;

import com.liferay.arquillian.extension.junit.bridge.event.Event;
import com.liferay.arquillian.extension.junit.bridge.listener.ClientExecutorEventListener;
import com.liferay.arquillian.extension.junit.bridge.listener.DeploymentEventListener;
import com.liferay.arquillian.extension.junit.bridge.listener.EventListener;
import com.liferay.arquillian.extension.junit.bridge.listener.ServerExecutorEventListener;

import java.net.URL;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Matthew Tambara
 */
public class Manager {

	public Manager() {
		URL url = Manager.class.getResource("/arquillian.remote.marker");

		if (url == null) {
			_eventListeners.add(new DeploymentEventListener(_registry));
			_eventListeners.add(new ClientExecutorEventListener(_registry));
		}
		else {
			_eventListeners.add(new ServerExecutorEventListener(_registry));
		}
	}

	public void fire(Event event) throws Throwable {
		for (EventListener eventListener : _eventListeners) {
			eventListener.handleEvent(event);
		}
	}

	public Registry getRegistry() {
		return _registry;
	}

	private final List<EventListener> _eventListeners = new ArrayList<>();
	private final Registry _registry = new Registry();

}