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

package com.liferay.shielded.container.internal.session;

import com.liferay.shielded.container.internal.ShieldedContainerClassLoader;

import java.io.IOException;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionEvent;

/**
 * @author Shuyang Zhou
 */
public class ShieldedContainerHttpSessionActivationListener
	implements HttpSessionActivationListener, Serializable {

	public static final String NAME =
		ShieldedContainerHttpSessionActivationListener.class.getName();

	@Override
	public void sessionDidActivate(HttpSessionEvent httpSessionEvent) {
		HttpSession httpSession = httpSessionEvent.getSession();

		List<String> scrubbedNames = (List<String>)httpSession.getAttribute(
			_SCRUBBED_NAMES_NAME);

		if (scrubbedNames == null) {
			return;
		}

		httpSession.removeAttribute(_SCRUBBED_NAMES_NAME);

		ServletContext servletContext = httpSession.getServletContext();

		ClassLoader classLoader = (ClassLoader)servletContext.getAttribute(
			ShieldedContainerClassLoader.NAME);

		RuntimeException runtimeException = null;

		for (String scrubbedName : scrubbedNames) {
			try {
				httpSession.setAttribute(
					scrubbedName,
					SerializationUtil.deserialize(
						(byte[])httpSession.getAttribute(scrubbedName),
						classLoader));
			}
			catch (Exception exception) {
				if (runtimeException == null) {
					runtimeException = new RuntimeException(
						"Unable to recover scrubbed value", exception);
				}
				else {
					runtimeException.addSuppressed(exception);
				}
			}
		}

		if (runtimeException != null) {
			throw runtimeException;
		}
	}

	@Override
	public void sessionWillPassivate(HttpSessionEvent httpSessionEvent) {
		HttpSession httpSession = httpSessionEvent.getSession();

		Enumeration<String> enumeration = httpSession.getAttributeNames();

		List<String> scrubbedNames = new ArrayList<>();

		while (enumeration.hasMoreElements()) {
			String name = enumeration.nextElement();

			if (name.equals(NAME)) {
				continue;
			}

			Object value = httpSession.getAttribute(name);

			if (!(value instanceof Serializable) ||
				_safeClasses.contains(value.getClass())) {

				continue;
			}

			try {
				httpSession.setAttribute(
					name, SerializationUtil.serialize((Serializable)value));

				scrubbedNames.add(name);
			}
			catch (IOException ioException) {
				throw new RuntimeException(
					"Unable to scrub value", ioException);
			}
		}

		if (!scrubbedNames.isEmpty()) {
			httpSession.setAttribute(_SCRUBBED_NAMES_NAME, scrubbedNames);
		}
	}

	private static final String _SCRUBBED_NAMES_NAME =
		ShieldedContainerHttpSessionActivationListener.class.getName() +
			"._SCRUBBED_NAMES_NAME";

	private static final Set<Class<?>> _safeClasses = new HashSet<>(
		Arrays.asList(
			Boolean.class, Byte.class, Character.class, Double.class,
			Float.class, Integer.class, Long.class, Short.class, String.class));

}