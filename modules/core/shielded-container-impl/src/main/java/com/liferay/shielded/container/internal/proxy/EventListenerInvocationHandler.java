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

package com.liferay.shielded.container.internal.proxy;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.util.EventObject;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

/**
 * @author Tina Tian
 */
public class EventListenerInvocationHandler
	extends ContextClassLoaderInvocationHandler {

	public EventListenerInvocationHandler(
		ServletContext servletContext, ClassLoader contextClassLoader,
		Object target) {

		super(contextClassLoader, target);

		_servletContext = servletContext;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
		throws Throwable {

		if ((args != null) && (args[0] instanceof EventObject)) {
			EventObject eventObject = (EventObject)args[0];

			Object source = eventObject.getSource();

			if (source instanceof ServletContext) {
				_sourceField.set(eventObject, _servletContext);
			}
			else if (source instanceof HttpSession) {
				HttpSession httpSession = (HttpSession)source;

				Object updatedHttpSession = _servletContext.getAttribute(
					httpSession.getId());

				if (updatedHttpSession != null) {
					_sourceField.set(eventObject, updatedHttpSession);
				}
			}
		}

		return super.invoke(proxy, method, args);
	}

	private static Field _sourceField;

	static {
		try {
			_sourceField = EventObject.class.getDeclaredField("source");

			_sourceField.setAccessible(true);
		}
		catch (NoSuchFieldException noSuchFieldException) {
			throw new ExceptionInInitializerError(noSuchFieldException);
		}
	}

	private final ServletContext _servletContext;

}