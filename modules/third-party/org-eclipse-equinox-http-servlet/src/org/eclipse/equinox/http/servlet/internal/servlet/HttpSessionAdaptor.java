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
import org.eclipse.equinox.http.servlet.internal.util.EventListeners;

/* @generated */

// This class adapts HttpSessions in order to return the right ServletContext and attributes
public class HttpSessionAdaptor implements HttpSession, Serializable {
	private static final long serialVersionUID = 3418610936889860782L;

	static class ParentSessionListener implements HttpSessionBindingListener, Serializable {
		private static final long serialVersionUID = 4626167646903550760L;

		private static final String PARENT_SESSION_LISTENER_KEY = "org.eclipse.equinox.http.parent.session.listener"; //$NON-NLS-1$
		transient final Set<HttpSessionAdaptor> innerSessions = new HashSet<HttpSessionAdaptor>();
		@Override
		public void valueBound(HttpSessionBindingEvent event) {
			// do nothing
		}

		@Override
		public void valueUnbound(HttpSessionBindingEvent event) {
			// Here we assume the unbound event is signifying the session is being invalidated.
			// Must invalidate the inner sessions
			Set<HttpSessionAdaptor> innerSessionsToInvalidate;
			synchronized (innerSessions) {
				// copy the sessions to invalidate and clear the set
				innerSessionsToInvalidate = new HashSet<HttpSessionAdaptor>(innerSessions);
				innerSessions.clear();
			}
			for (HttpSessionAdaptor innerSession : innerSessionsToInvalidate) {
				innerSession.invalidate();
			}
		}

		static void addHttpSessionAdaptor(HttpSessionAdaptor innerSession) {
			ParentSessionListener parentListener;
			// need to have a global lock here because we must ensure that this is added only once
			synchronized (ParentSessionListener.class) {
				parentListener = (ParentSessionListener) innerSession.getSession().getAttribute(PARENT_SESSION_LISTENER_KEY);
				if (parentListener == null) {
					parentListener = new ParentSessionListener();
					innerSession.getSession().setAttribute(PARENT_SESSION_LISTENER_KEY, parentListener);
				}
			}
			synchronized (parentListener.innerSessions) {
				parentListener.innerSessions.add(innerSession);
			}
		}

		static void removeHttpSessionAdaptor(HttpSessionAdaptor innerSession) {
			ParentSessionListener parentListener;
			// need to have a global lock here because we must ensure that this is added only once
			synchronized (ParentSessionListener.class) {
				parentListener = (ParentSessionListener) innerSession.getSession().getAttribute(PARENT_SESSION_LISTENER_KEY);
			}
			if (parentListener != null) {
				synchronized (parentListener.innerSessions) {
					parentListener.innerSessions.remove(innerSession);
				}
			}
		}
	}

	class HttpSessionAttributeWrapper implements HttpSessionBindingListener, HttpSessionActivationListener, Serializable {
		private static final long serialVersionUID = 7945998375225990980L;

		final String name;
		final Object value;
		final boolean added;
		final HttpSessionAdaptor innerSession;

		public HttpSessionAttributeWrapper(HttpSessionAdaptor innerSession, String name, Object value, boolean added) {
			this.innerSession = innerSession;
			this.name = name;
			this.value = value;
			this.added = added;
		}

		@Override
		public void valueBound(HttpSessionBindingEvent event) {
			List<HttpSessionAttributeListener> listeners = getEventListeners().get(
				HttpSessionAttributeListener.class);

			for (HttpSessionAttributeListener listener : listeners) {
				if (added) {
					listener.attributeAdded(event);
				}
				else {
					listener.attributeReplaced(event);
				}
			}

			if (value instanceof HttpSessionBindingListener) {
				((HttpSessionBindingListener) value).valueBound(new HttpSessionBindingEvent(innerSession, name, value));
			}
		}

		@Override
		public void valueUnbound(HttpSessionBindingEvent event) {
			if (!added) {
				List<HttpSessionAttributeListener> listeners = getEventListeners().get(
					HttpSessionAttributeListener.class);

				for (HttpSessionAttributeListener listener : listeners) {
					listener.attributeRemoved(event);
				}
			}

			if (value instanceof HttpSessionBindingListener) {
				((HttpSessionBindingListener) value).valueUnbound(new HttpSessionBindingEvent(innerSession, name, value));
			}
		}

		@Override
		public void sessionWillPassivate(HttpSessionEvent se) {
			if (value instanceof HttpSessionActivationListener) {
				((HttpSessionActivationListener) value).sessionWillPassivate(new HttpSessionEvent(innerSession));
			}
		}

		@Override
		public void sessionDidActivate(HttpSessionEvent se) {
			if (value instanceof HttpSessionActivationListener) {
				((HttpSessionActivationListener) value).sessionDidActivate(new HttpSessionEvent(innerSession));
			}
		}

		private EventListeners getEventListeners() {
			return innerSession.getController().getEventListeners();
		}
	}

	private transient final ContextController controller;
	private transient final HttpSession session;
	private transient final ServletContext servletContext;
	private transient final String attributePrefix;

	static public HttpSessionAdaptor createHttpSessionAdaptor(
		HttpSession session, ServletContext servletContext, ContextController controller) {
		HttpSessionAdaptor sessionAdaptor = new HttpSessionAdaptor(session, servletContext, controller);
		ParentSessionListener.addHttpSessionAdaptor(sessionAdaptor);
		return sessionAdaptor;
	}

	private HttpSessionAdaptor(
		HttpSession session, ServletContext servletContext, ContextController controller) {

		this.session = session;
		this.servletContext = servletContext;
		this.controller = controller;
		this.attributePrefix = "equinox.http." + controller.getContextName(); //$NON-NLS-1$
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
		Object result = session.getAttribute(attributePrefix + arg0);

		if (result == null) {
			result = session.getAttribute(arg0);
		}

		if (result instanceof HttpSessionAttributeWrapper) {
			result = ((HttpSessionAttributeWrapper) result).value;
		}
		return result;
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
			listener.sessionDestroyed(httpSessionEvent);
		}
		try {
			for (String attribute : getAttributeNames0()) {
				removeAttribute(attribute);
			}
			ParentSessionListener.removeHttpSessionAdaptor(this);
		}
		catch (IllegalStateException ise) {
			// outer session is already invalidated
		}
		controller.removeActiveSession(session);
	}

	/**@deprecated*/
	public void putValue(String arg0, Object arg1) {
		setAttribute(arg0, arg1);
	}

	public void removeAttribute(String arg0) {
		session.removeAttribute(attributePrefix + arg0);
	}

	/**@deprecated*/
	public void removeValue(String arg0) {
		removeAttribute(arg0);
	}

	public void setAttribute(String name, Object value) {
		boolean added = (session.getAttribute(attributePrefix + name) == null);

		session.setAttribute(attributePrefix + name, new HttpSessionAttributeWrapper(this, name, value, added));
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
		return session.getId();
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
}
