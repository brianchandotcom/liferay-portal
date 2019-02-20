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

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jboss.arquillian.core.impl.EventPointImpl;
import org.jboss.arquillian.core.impl.InjectionPointImpl;
import org.jboss.arquillian.core.impl.ObserverImpl;
import org.jboss.arquillian.core.impl.Reflections;
import org.jboss.arquillian.core.spi.EventPoint;
import org.jboss.arquillian.core.spi.Extension;
import org.jboss.arquillian.core.spi.InjectionPoint;
import org.jboss.arquillian.core.spi.ObserverMethod;

/**
 * @author Matthew Tambara
 */
public class ExtensionImpl implements Extension {

	public static ExtensionImpl of(Object target) {
		return new ExtensionImpl(
			target,
			_injections(
				target, Reflections.getFieldInjectionPoints(target.getClass())),
			_events(target, Reflections.getEventPoints(target.getClass())),
			_observers(
				target, Reflections.getObserverMethods(target.getClass())));
	}

	@Override
	public List<EventPoint> getEventPoints() {
		return Collections.unmodifiableList(_eventPoints);
	}

	@Override
	public List<InjectionPoint> getInjectionPoints() {
		return Collections.unmodifiableList(_injectionPoints);
	}

	@Override
	public List<ObserverMethod> getObservers() {
		return Collections.unmodifiableList(_observers);
	}

	public Object getTarget() {
		return _target;
	}

	private static List<EventPoint> _events(
		Object extension, List<Field> eventPoints) {

		List<EventPoint> result = new ArrayList<>();

		for (Field method : eventPoints) {
			result.add(EventPointImpl.of(extension, method));
		}

		return result;
	}

	private static List<InjectionPoint> _injections(
		Object extension, List<Field> injectionPoints) {

		List<InjectionPoint> result = new ArrayList<>();

		for (Field field : injectionPoints) {
			result.add(InjectionPointImpl.of(extension, field));
		}

		return result;
	}

	private static List<ObserverMethod> _observers(
		Object extension, List<Method> observerMethods) {

		List<ObserverMethod> result = new ArrayList<>();

		for (Method method : observerMethods) {
			result.add(ObserverImpl.of(extension, method));
		}

		return result;
	}

	private ExtensionImpl(
		Object target, List<InjectionPoint> injectionPoints,
		List<EventPoint> eventPoints, List<ObserverMethod> observers) {

		_target = target;
		_injectionPoints = injectionPoints;
		_eventPoints = eventPoints;
		_observers = observers;
	}

	private final List<EventPoint> _eventPoints;
	private final List<InjectionPoint> _injectionPoints;
	private final List<ObserverMethod> _observers;
	private final Object _target;

}