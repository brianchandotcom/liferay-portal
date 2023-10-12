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
package org.eclipse.equinox.http.servlet.internal.servlet;

import java.io.Serializable;
import java.util.*;
import javax.servlet.ServletContext;
import javax.servlet.http.*;
import org.eclipse.equinox.http.servlet.internal.context.ContextController;

// This class adapts HttpSessions in order to return the right ServletContext and attributes
public class HttpSessionAdaptor implements HttpSession, Serializable {
	private static final long serialVersionUID = 3418610936889860782L;

	private transient final ContextController controller;
	private transient final HttpSession session;
	private transient final ServletContext servletContext;
	private transient final String attributePrefix;
	private String string;
	private final String id;

	static public HttpSessionAdaptor createHttpSessionAdaptor(
		HttpSession session, ServletContext servletContext, ContextController controller) {
		HttpSessionAdaptor sessionAdaptor = new HttpSessionAdaptor(session, servletContext, controller);

		HttpSessionTracker.addHttpSessionAdaptor(sessionAdaptor);

		return sessionAdaptor;
	}

	private HttpSessionAdaptor(
		HttpSession session, ServletContext servletContext, ContextController controller) {

		this.session = session;
		this.servletContext = servletContext;
		this.controller = controller;
		this.attributePrefix = "equinox.http." + controller.getContextName(); //$NON-NLS-1$

		this.id = session.getId();
	}

	public ContextController getController() {
		return controller;
	}

	public HttpSession getSession() {
		return session;
	}

	public ServletContext getServletContext() {
		return servletContext;
	}

	public Object getAttribute(String arg0) {
		return session.getAttribute(attributePrefix.concat(arg0));
	}

	public Enumeration<String> getAttributeNames() {
		return Collections.enumeration(getAttributeNames0());
	}

	private Collection<String> getAttributeNames0() {
		Collection<String> result = new ArrayList<String>();
		Enumeration<String> containerSessionAttributes = session.getAttributeNames();
		while(containerSessionAttributes.hasMoreElements()) {
			String attribute = containerSessionAttributes.nextElement();
			if (attribute.startsWith(attributePrefix)) {
				result.add(attribute.substring(attributePrefix.length()));
			}
		}
		return result;
	}

	/**@deprecated*/
	public Object getValue(String arg0) {
		return getAttribute(arg0);
	}

	/**@deprecated*/
	public String[] getValueNames() {
		Collection<String> result = getAttributeNames0();
		return result.toArray(new String[result.size()]);
	}

	public void invalidate() {
		HttpSessionEvent httpSessionEvent = new HttpSessionEvent(this);

		for (HttpSessionListener listener : controller.getEventListeners().get(HttpSessionListener.class)) {
			try {
				listener.sessionDestroyed(httpSessionEvent);
			}
			catch (IllegalStateException ise) {
				// outer session is already invalidated
			}
		}

		try {
			for (String attribute : getAttributeNames0()) {
				removeAttribute(attribute);
			}
		}
		catch (IllegalStateException ise) {
			// outer session is already invalidated
		}

		try {
			HttpSessionTracker.removeHttpSessionAdaptor(this);
		}
		catch (IllegalStateException ise) {
			// outer session is already invalidated
		}

		controller.removeActiveSession(id);
	}

	public void invokeSessionListeners (List<Class<? extends EventListener>> classes, EventListener listener) {
		if (classes == null) {
			return;
		}

		for (Class<? extends EventListener> clazz : classes) {
			if (clazz.equals(HttpSessionListener.class)){
				HttpSessionEvent sessionEvent = new HttpSessionEvent(this);
				HttpSessionListener httpSessionListener = (HttpSessionListener) listener;
				httpSessionListener.sessionDestroyed(sessionEvent);
			}

			if (clazz.equals(HttpSessionBindingListener.class) || clazz.equals(HttpSessionAttributeListener.class)) {
				Enumeration<String> attributeNames = getAttributeNames();
				while (attributeNames.hasMoreElements()) {
					String attributeName = attributeNames.nextElement();
					HttpSessionBindingEvent sessionBindingEvent = new HttpSessionBindingEvent(this, attributeName);

					if (clazz.equals(HttpSessionBindingListener.class)) {
						HttpSessionBindingListener httpSessionBindingListener = (HttpSessionBindingListener) listener;
						httpSessionBindingListener.valueUnbound(sessionBindingEvent);
					}

					if (clazz.equals(HttpSessionAttributeListener.class)) {
						HttpSessionAttributeListener httpSessionAttributeListener = (HttpSessionAttributeListener) listener;
						httpSessionAttributeListener.attributeRemoved(sessionBindingEvent);
					}
				}
			}
		}
	}

	/**@deprecated*/
	public void putValue(String arg0, Object arg1) {
		setAttribute(arg0, arg1);
	}

	public void removeAttribute(String arg0) {
		String newName = attributePrefix.concat(arg0);

		Object value = session.getAttribute(newName);

		session.removeAttribute(newName);

		if (value == null) {
			return;
		}

		List<HttpSessionAttributeListener> listeners =
			controller.getEventListeners().get(
				HttpSessionAttributeListener.class);

		if (listeners.isEmpty()) {
			return;
		}

		HttpSessionBindingEvent httpSessionBindingEvent =
			new HttpSessionBindingEvent(this, newName);

		for (HttpSessionAttributeListener listener : listeners) {
			listener.attributeRemoved(httpSessionBindingEvent);
		}
	}

	/**@deprecated*/
	public void removeValue(String arg0) {
		removeAttribute(arg0);
	}

	public void setAttribute(String name, Object value) {
		String newName = attributePrefix.concat(name);

		if (value == null) {
			session.setAttribute(newName, null);

			return;
		}

		boolean added = session.getAttribute(newName) == null;

		session.setAttribute(newName, value);

		List<HttpSessionAttributeListener> listeners =
			controller.getEventListeners().get(
				HttpSessionAttributeListener.class);

		if (!listeners.isEmpty()) {
			HttpSessionBindingEvent httpSessionBindingEvent =
				new HttpSessionBindingEvent(this, newName, value);

			for (HttpSessionAttributeListener listener : listeners) {
				if (added) {
					listener.attributeAdded(httpSessionBindingEvent);
				}
				else {
					listener.attributeReplaced(httpSessionBindingEvent);
				}
			}
		}
	}

	public void setMaxInactiveInterval(int arg0) {
		// Not sure this can be done per context helper
		session.setMaxInactiveInterval(arg0);
	}

	public long getCreationTime() {
		// Not sure this can be done per context helper
		return session.getCreationTime();
	}

	public String getId() {
		// Not sure this can be done per context helper
		return id;
	}

	public long getLastAccessedTime() {
		// Not sure this can be done per context helper
		return session.getLastAccessedTime();
	}

	public int getMaxInactiveInterval() {
		// Not sure this can be done per context helper
		return session.getMaxInactiveInterval();
	}

	/**@deprecated*/
	public javax.servlet.http.HttpSessionContext getSessionContext() {
		// Not sure this can be done per context helper and I think null is returned anyway
		return session.getSessionContext();
	}

	public boolean isNew() {
		// Not sure this can be done per context helper
		return session.isNew();
	}

	@Override
	public String toString() {
		String value = string;

		if (value == null) {
			value = SIMPLE_NAME + '[' + session.getId() + ", " + attributePrefix + ']'; //$NON-NLS-1$

			string = value;
		}

		return value;
	}

	private static final String SIMPLE_NAME =
		HttpSessionAdaptor.class.getSimpleName();

}
