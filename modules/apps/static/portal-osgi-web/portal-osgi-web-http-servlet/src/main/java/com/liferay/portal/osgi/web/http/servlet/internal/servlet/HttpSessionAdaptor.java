/*******************************************************************************
 * Copyright (c) 2005, 2015 Cognos Incorporated, IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Cognos Incorporated - initial API and implementation
 *     IBM Corporation - bug fixes and enhancements
 *     Raymond Augé <raymond.auge@liferay.com> - Bug 436698
 *******************************************************************************/

package com.liferay.portal.osgi.web.http.servlet.internal.servlet;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.osgi.web.http.servlet.internal.context.ContextController;
import com.liferay.portal.osgi.web.http.servlet.internal.util.EventListeners;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author Cognos Incorporated
 * @author IBM Corporation
 * @author Raymond Augé
 */
public class HttpSessionAdaptor implements HttpSession, Serializable {

	public static HttpSessionAdaptor createHttpSessionAdaptor(
		HttpSession httpSession, ServletContext servletContext,
		ContextController contextController) {

		HttpSessionAdaptor sessionAdaptor = new HttpSessionAdaptor(
			httpSession, servletContext, contextController);

		HttpSessionTracker.addHttpSessionAdaptor(sessionAdaptor);

		return sessionAdaptor;
	}

	@Override
	public Object getAttribute(String arg0) {
		return _httpSession.getAttribute(_attributePrefix.concat(arg0));
	}

	@Override
	public Enumeration<String> getAttributeNames() {
		return Collections.enumeration(_getAttributeNames());
	}

	public ContextController getController() {
		return _contextController;
	}

	@Override
	public long getCreationTime() {
		return _httpSession.getCreationTime();
	}

	@Override
	public String getId() {
		return _id;
	}

	@Override
	public long getLastAccessedTime() {
		return _httpSession.getLastAccessedTime();
	}

	@Override
	public int getMaxInactiveInterval() {
		return _httpSession.getMaxInactiveInterval();
	}

	@Override
	public ServletContext getServletContext() {
		return _servletContext;
	}

	@Override
	public HttpSessionContext getSessionContext() {
		return _httpSession.getSessionContext();
	}

	@Override
	public Object getValue(String arg0) {
		return getAttribute(arg0);
	}

	@Override
	public String[] getValueNames() {
		Collection<String> result = _getAttributeNames();

		return result.toArray(new String[0]);
	}

	@Override
	public void invalidate() {
		HttpSessionEvent httpSessionEvent = new HttpSessionEvent(this);

		EventListeners eventListeners = _contextController.getEventListeners();

		for (HttpSessionListener listener :
				eventListeners.get(HttpSessionListener.class)) {

			try {
				listener.sessionDestroyed(httpSessionEvent);
			}
			catch (IllegalStateException illegalStateException) {
				if (_log.isDebugEnabled()) {
					_log.debug(illegalStateException);
				}
			}
		}

		try {
			for (String attribute : _getAttributeNames()) {
				removeAttribute(attribute);
			}
		}
		catch (IllegalStateException illegalStateException) {
			if (_log.isDebugEnabled()) {
				_log.debug(illegalStateException);
			}
		}

		try {
			HttpSessionTracker.removeHttpSessionAdaptor(this);
		}
		catch (IllegalStateException illegalStateException) {
			if (_log.isDebugEnabled()) {
				_log.debug(illegalStateException);
			}
		}

		_contextController.removeActiveSession(_id);
	}

	public void invokeSessionListeners(
		List<Class<? extends EventListener>> classes, EventListener listener) {

		if (classes == null) {
			return;
		}

		for (Class<? extends EventListener> clazz : classes) {
			if (clazz.equals(HttpSessionListener.class)) {
				HttpSessionEvent sessionEvent = new HttpSessionEvent(this);
				HttpSessionListener httpSessionListener =
					(HttpSessionListener)listener;

				httpSessionListener.sessionDestroyed(sessionEvent);
			}

			if (clazz.equals(HttpSessionBindingListener.class) ||
				clazz.equals(HttpSessionAttributeListener.class)) {

				Enumeration<String> attributeNamesEnumeration =
					getAttributeNames();

				while (attributeNamesEnumeration.hasMoreElements()) {
					String attributeName =
						attributeNamesEnumeration.nextElement();

					HttpSessionBindingEvent sessionBindingEvent =
						new HttpSessionBindingEvent(this, attributeName);

					if (clazz.equals(HttpSessionBindingListener.class)) {
						HttpSessionBindingListener httpSessionBindingListener =
							(HttpSessionBindingListener)listener;

						httpSessionBindingListener.valueUnbound(
							sessionBindingEvent);
					}

					if (clazz.equals(HttpSessionAttributeListener.class)) {
						HttpSessionAttributeListener
							httpSessionAttributeListener =
								(HttpSessionAttributeListener)listener;

						httpSessionAttributeListener.attributeRemoved(
							sessionBindingEvent);
					}
				}
			}
		}
	}

	@Override
	public boolean isNew() {
		return _httpSession.isNew();
	}

	@Override
	public void putValue(String key, Object value) {
		setAttribute(key, value);
	}

	@Override
	public void removeAttribute(String arg0) {
		String newName = _attributePrefix.concat(arg0);

		Object value = _httpSession.getAttribute(newName);

		_httpSession.removeAttribute(newName);

		if (value == null) {
			return;
		}

		EventListeners eventListeners = _contextController.getEventListeners();

		List<HttpSessionAttributeListener> httpSessionAttributeListeners =
			eventListeners.get(HttpSessionAttributeListener.class);

		if (httpSessionAttributeListeners.isEmpty()) {
			return;
		}

		HttpSessionBindingEvent httpSessionBindingEvent =
			new HttpSessionBindingEvent(this, newName);

		for (HttpSessionAttributeListener httpSessionAttributeListener :
				httpSessionAttributeListeners) {

			httpSessionAttributeListener.attributeRemoved(
				httpSessionBindingEvent);
		}
	}

	@Override
	public void removeValue(String arg0) {
		removeAttribute(arg0);
	}

	@Override
	public void setAttribute(String name, Object value) {
		String newName = _attributePrefix.concat(name);

		if (value == null) {
			_httpSession.setAttribute(newName, null);

			return;
		}

		boolean added = false;

		if (_httpSession.getAttribute(newName) == null) {
			added = true;
		}

		_httpSession.setAttribute(newName, value);

		EventListeners eventListeners = _contextController.getEventListeners();

		List<HttpSessionAttributeListener> httpSessionAttributeListeners =
			eventListeners.get(HttpSessionAttributeListener.class);

		if (!httpSessionAttributeListeners.isEmpty()) {
			HttpSessionBindingEvent httpSessionBindingEvent =
				new HttpSessionBindingEvent(this, newName, value);

			for (HttpSessionAttributeListener httpSessionAttributeListener :
					httpSessionAttributeListeners) {

				if (added) {
					httpSessionAttributeListener.attributeAdded(
						httpSessionBindingEvent);
				}
				else {
					httpSessionAttributeListener.attributeReplaced(
						httpSessionBindingEvent);
				}
			}
		}
	}

	@Override
	public void setMaxInactiveInterval(int maxInactiveInterval) {
		_httpSession.setMaxInactiveInterval(maxInactiveInterval);
	}

	@Override
	public String toString() {
		String value = _string;

		if (value == null) {
			value = StringBundler.concat(
				HttpSessionAdaptor.class.getSimpleName(), '[',
				_httpSession.getId(), ", ", _attributePrefix, ']');

			_string = value;
		}

		return value;
	}

	private HttpSessionAdaptor(
		HttpSession httpSession, ServletContext servletContext,
		ContextController contextController) {

		_httpSession = httpSession;
		_servletContext = servletContext;
		_contextController = contextController;

		_attributePrefix = "equinox.http." + contextController.getContextName();

		_id = httpSession.getId();
	}

	private Collection<String> _getAttributeNames() {
		Collection<String> attributeNames = new ArrayList<>();

		Enumeration<String> attributeNamesEnumeration =
			_httpSession.getAttributeNames();

		while (attributeNamesEnumeration.hasMoreElements()) {
			String attribute = attributeNamesEnumeration.nextElement();

			if (attribute.startsWith(_attributePrefix)) {
				attributeNames.add(
					attribute.substring(_attributePrefix.length()));
			}
		}

		return attributeNames;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		HttpSessionAdaptor.class.getName());

	private static final long serialVersionUID = 3418610936889860782L;

	private final transient String _attributePrefix;
	private final transient ContextController _contextController;
	private final transient HttpSession _httpSession;
	private final String _id;
	private final transient ServletContext _servletContext;
	private String _string;

}