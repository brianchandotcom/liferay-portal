/*******************************************************************************
 * Copyright (c) 2015 Raymond Augé and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Raymond Augé <raymond.auge@liferay.com> - Bug 436698
 ******************************************************************************/

package com.liferay.portal.osgi.web.http.servlet.internal.registration;

import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.osgi.web.http.servlet.internal.context.ContextController;
import com.liferay.portal.osgi.web.http.servlet.internal.servlet.HttpSessionAdaptor;
import com.liferay.portal.osgi.web.http.servlet.internal.util.EventListeners;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.EventListener;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionListener;

import org.osgi.framework.Bundle;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.service.http.runtime.dto.ListenerDTO;

/**
 * @author Raymond Augé
 */
public class ListenerRegistration
	extends Registration<EventListener, ListenerDTO> {

	public ListenerRegistration(
		ContextController.ServiceHolder<EventListener> serviceHolder,
		List<Class<? extends EventListener>> classes, ListenerDTO listenerDTO,
		ServletContext servletContext, ContextController contextController) {

		super(serviceHolder.get(), listenerDTO);

		_serviceHolder = serviceHolder;
		_classes = classes;
		_servletContext = servletContext;
		_contextController = contextController;

		Bundle bundle = serviceHolder.getBundle();

		BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

		_classLoader = bundleWiring.getClassLoader();

		_contextController.createContextAttributes();

		_eventListenerProxy = (EventListener)ProxyUtil.newProxyInstance(
			getClass().getClassLoader(), classes.toArray(new Class<?>[0]),
			new EventListenerInvocationHandler());
	}

	@Override
	public synchronized void destroy() {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(_classLoader);

			Set<ListenerRegistration> listenerRegistrations =
				_contextController.getListenerRegistrations();

			listenerRegistrations.remove(this);

			EventListeners eventListeners =
				_contextController.getEventListeners();

			eventListeners.remove(_classes, this);

			_contextController.ungetServletContextHelper(
				_serviceHolder.getBundle());

			super.destroy();

			if (_classes.contains(HttpSessionBindingListener.class) ||
				_classes.contains(HttpSessionAttributeListener.class) ||
				_classes.contains(HttpSessionListener.class)) {

				Map<String, HttpSessionAdaptor> activeSessions =
					_contextController.getActiveSessions();

				for (HttpSessionAdaptor adaptor : activeSessions.values()) {
					adaptor.invokeSessionListeners(_classes, super.getT());
				}
			}

			if (_classes.contains(ServletContextListener.class)) {
				ServletContextListener servletContextListener =
					(ServletContextListener)super.getT();

				servletContextListener.contextDestroyed(
					new ServletContextEvent(_servletContext));
			}
		}
		finally {
			_contextController.destroyContextAttributes();

			currentThread.setContextClassLoader(contextClassLoader);

			_serviceHolder.release();
		}
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof ListenerRegistration)) {
			return false;
		}

		ListenerRegistration listenerRegistration =
			(ListenerRegistration)object;

		return Objects.equals(listenerRegistration.getT(), super.getT());
	}

	public ServletContext getServletContext() {
		return _servletContext;
	}

	@Override
	public EventListener getT() {
		return _eventListenerProxy;
	}

	@Override
	public int hashCode() {
		return Long.hashCode(getD().serviceId);
	}

	private final List<Class<? extends EventListener>> _classes;
	private final ClassLoader _classLoader;
	private final ContextController _contextController;
	private final EventListener _eventListenerProxy;
	private final ContextController.ServiceHolder<EventListener> _serviceHolder;
	private final ServletContext _servletContext;

	private class EventListenerInvocationHandler implements InvocationHandler {

		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {

			Thread currentThread = Thread.currentThread();

			ClassLoader contextClassLoader =
				currentThread.getContextClassLoader();

			try {
				currentThread.setContextClassLoader(_classLoader);

				try {
					return method.invoke(
						ListenerRegistration.super.getT(), args);
				}
				catch (InvocationTargetException invocationTargetException) {
					throw invocationTargetException.getCause();
				}
			}
			finally {
				currentThread.setContextClassLoader(contextClassLoader);
			}
		}

	}

}