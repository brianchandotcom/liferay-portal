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

import com.liferay.arquillian.extension.junit.bridge.event.controller.ContainerEventController;
import com.liferay.arquillian.extension.junit.bridge.protocol.jmx.JMXMethodExecutor;
import com.liferay.arquillian.extension.junit.bridge.remote.observer.JUnitBridgeObserver;

import java.lang.reflect.InvocationTargetException;

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
			_observers = new ArrayList<>();

			_observers.addAll(
				Observer.getObservers(
					new ContainerEventController(_registry), _registry));
			_observers.addAll(
				Observer.getObservers(
					new JMXMethodExecutor(_registry), _registry));
		}
		else {
			_observers = Observer.getObservers(
				new JUnitBridgeObserver(_registry), _registry);
		}
	}

	public <T> void fire(T event) throws Throwable {
		for (Observer observer : _observers) {
			Class<?> clazz = observer.getType();

			if (clazz.isInstance(event)) {
				try {
					observer.invoke(event);
				}
				catch (InvocationTargetException ite) {
					throw ite.getCause();
				}
			}
		}
	}

	public Registry getRegistry() {
		return _registry;
	}

	private final List<Observer> _observers;
	private final Registry _registry = new Registry();

}