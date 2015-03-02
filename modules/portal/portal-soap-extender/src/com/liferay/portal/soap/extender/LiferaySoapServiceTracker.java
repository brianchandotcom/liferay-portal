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

package com.liferay.portal.soap.extender;

import com.liferay.portal.soap.extender.configuration.ExtensionManager;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.Servlet;

import javax.xml.namespace.QName;
import javax.xml.ws.Binding;
import javax.xml.ws.handler.Handler;

import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.bus.CXFBusFactory;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.apache.cxf.jaxws.support.JaxWsEndpointImpl;
import org.apache.cxf.transport.servlet.CXFNonSpringServlet;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.service.http.context.ServletContextHelper;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Carlos Sierra Andrés
 */
public class LiferaySoapServiceTracker {

	public LiferaySoapServiceTracker(
		BundleContext bundleContext, String contextPath,
		ExtensionManager extensionManager) {

		_bundleContext = bundleContext;
		_contextPath = contextPath;
		_extensionManager = extensionManager;
	}

	protected void start() {
		Bus bus = _createBus();

		BusFactory.setDefaultBus(bus);

		_registerCXFServlet(bus, _contextPath);

		try {
			Filter servicesFilter = _bundleContext.createFilter(
				"(&(address=*)(jaxws=true))");

			_serverServiceTracker = new ServiceTracker<>(
				_bundleContext, servicesFilter,
				new ServerServiceTrackerCustomizer(bus));
		}
		catch (InvalidSyntaxException ise) {
			throw new RuntimeException(ise);
		}

		_serverServiceTracker.open();
	}

	protected void stop() {
		try {
			_serverServiceTracker.close();
		}
		catch (Exception e) {
			if (_logger.isWarnEnabled()) {
				_logger.warn(
					"Could not close servicetracker " + _serverServiceTracker);
			}
		}

		try {
			_servletServiceRegistration.unregister();
		}
		catch (Exception e) {
			if (_logger.isWarnEnabled()) {
				_logger.warn(
					"Could not unregister CXF servlet " +
						_servletServiceRegistration);
			}
		}

		try {
			_servletContextHelperRegistration.unregister();
		}
		catch (Exception e) {
			if (_logger.isWarnEnabled()) {
				_logger.warn(
					"Could not unregister Servlet context " +
						_serverServiceTracker);
			}
		}
	}

	private Bus _createBus() {
		CXFBusFactory busFactory = (CXFBusFactory)CXFBusFactory.newInstance(
			CXFBusFactory.class.getName());

		return busFactory.createBus(_extensionManager.getExtensions());
	}

	private void _registerCXFServlet(Bus bus, String contextPath) {
		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_NAME,
			_CONTEXT_NAME);
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_PATH, contextPath);

		_servletContextHelperRegistration = _bundleContext.registerService(
			ServletContextHelper.class,
			new ServletContextHelper(_bundleContext.getBundle()) {},
			properties);

		properties = new Hashtable<>();

		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT,
			_CONTEXT_NAME);
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_NAME, "CXFServlet");
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN, "/*");

		CXFNonSpringServlet cxfServlet = new CXFNonSpringServlet();

		cxfServlet.setBus(bus);

		_servletServiceRegistration = _bundleContext.registerService(
			Servlet.class, cxfServlet, properties);
	}

	private static final String _CONTEXT_NAME = "LIFERAY_CXF_CONTEXT";

	private static final Logger _logger = LoggerFactory.getLogger(
		LiferaySoapServiceTracker.class);

	private final BundleContext _bundleContext;
	private final String _contextPath;
	private final ExtensionManager _extensionManager;
	private ServiceTracker<Object, ServerTrackingInformation>
		_serverServiceTracker;
	private ServiceRegistration<ServletContextHelper>
		_servletContextHelperRegistration;
	private ServiceRegistration<Servlet> _servletServiceRegistration;

	private static class ServerTrackingInformation {

		public ServerTrackingInformation(
			Server server,
			ServiceTracker<Handler<?>, Handler<?>> serviceTracker) {

			_server = server;
			_serviceTracker = serviceTracker;
		}

		public Server getServer() {
			return _server;
		}

		public ServiceTracker<Handler<?>, Handler<?>> getServiceTracker() {
			return _serviceTracker;
		}

		private final Server _server;
		private final ServiceTracker<Handler<?>, Handler<?>> _serviceTracker;

	}

	private class HandlerServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<Handler<?>, Handler<?>> {

		public HandlerServiceTrackerCustomizer(Server server) {
			_server = server;
		}

		@Override
		public Handler<?> addingService(
			ServiceReference<Handler<?>> reference) {

			Handler<?> handler = _bundleContext.getService(reference);

			JaxWsEndpointImpl jaxWsEndpoint =
				(JaxWsEndpointImpl) _server.getEndpoint();

			Binding binding = jaxWsEndpoint.getJaxwsBinding();

			List<Handler> handlerChain = binding.getHandlerChain();

			handlerChain.add(handler);

			binding.setHandlerChain(handlerChain);

			return handler;
		}

		@Override
		public void modifiedService(
			ServiceReference<Handler<?>> reference, Handler<?> service) {
		}

		@Override
		public void removedService(
			ServiceReference<Handler<?>> reference, Handler<?> handler) {

			JaxWsEndpointImpl jaxWsEndpoint =
				(JaxWsEndpointImpl) _server.getEndpoint();

			Binding binding = jaxWsEndpoint.getJaxwsBinding();

			List<Handler> handlerChain = binding.getHandlerChain();

			handlerChain.remove(handler);

			binding.setHandlerChain(handlerChain);

			_bundleContext.ungetService(reference);
		}

		private final Server _server;

	}

	private class ServerServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<Object, ServerTrackingInformation> {

		public ServerServiceTrackerCustomizer(Bus bus) {
			_bus = bus;
		}

		@Override
		public ServerTrackingInformation addingService(
			ServiceReference<Object> serviceReference) {

			Object service = _bundleContext.getService(serviceReference);

			JaxWsServerFactoryBean jaxWsServerFactoryBean =
				new JaxWsServerFactoryBean();

			Map<String, Object> properties = getPropertiesAsMap(
				serviceReference);

			jaxWsServerFactoryBean.setProperties(properties);

			jaxWsServerFactoryBean.setBus(_bus);

			Object addressObject = serviceReference.getProperty("address");

			String address = addressObject.toString();

			jaxWsServerFactoryBean.setAddress(address);

			Object endpointNameObject = serviceReference.getProperty(
				"endpointName");

			if ((endpointNameObject != null) &&
				endpointNameObject instanceof QName) {

				QName endpointName = (QName)endpointNameObject;

				jaxWsServerFactoryBean.setEndpointName(endpointName);
			}

			Object serviceClassObject = serviceReference.getProperty(
				"serviceClass");

			if ((serviceClassObject != null) &&
				serviceClassObject instanceof Class<?>) {

				Class<?> serviceClass = (Class<?>)serviceClassObject;

				jaxWsServerFactoryBean.setServiceClass(serviceClass);
			}

			Object wsdlLocationObject = serviceReference.getProperty(
				"wsdlLocation");

			if (wsdlLocationObject != null) {
				jaxWsServerFactoryBean.setWsdlLocation(
					wsdlLocationObject.toString());
			}

			jaxWsServerFactoryBean.setServiceBean(service);

			Thread thread = Thread.currentThread();

			ClassLoader contextClassLoader = thread.getContextClassLoader();

			try {
				Bundle bundle = serviceReference.getBundle();

				BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

				thread.setContextClassLoader(bundleWiring.getClassLoader());

				Server server = jaxWsServerFactoryBean.create();

				ServiceTracker<Handler<?>, Handler<?>> handlerServiceTracker =
					trackHandlers(address, server);

				if (_logger.isInfoEnabled()) {
					_logger.info(
						"Created JAX-WS server at location " + address +
							" using " + service);
				}

				return new ServerTrackingInformation(
					server, handlerServiceTracker);
			}
			catch (Throwable t) {
				_bundleContext.ungetService(serviceReference);

				if (_logger.isErrorEnabled()) {
					_logger.error(t.getMessage(), t);
				}

				return null;
			}
			finally {
				thread.setContextClassLoader(contextClassLoader);
			}
		}

		@Override
		public void modifiedService(
			ServiceReference<Object> serviceReference,
			ServerTrackingInformation serverTrackingInformation) {

			removedService(serviceReference, serverTrackingInformation);

			addingService(serviceReference);
		}

		@Override
		public void removedService(
			ServiceReference<Object> serviceReference,
			ServerTrackingInformation serverTrackingInformation) {

			serverTrackingInformation.getServer().destroy();

			serverTrackingInformation.getServiceTracker().close();

			_bundleContext.ungetService(serviceReference);
		}

		private Map<String, Object> getPropertiesAsMap(
			ServiceReference<Object> serviceReference) {

			String[] propertyKeys = serviceReference.getPropertyKeys();

			HashMap<String, Object> properties = new HashMap<>(
				propertyKeys.length);

			for (String propertyKey : propertyKeys) {
				properties.put(
					propertyKey, serviceReference.getProperty(propertyKey));
			}

			return properties;
		}

		private ServiceTracker<Handler<?>, Handler<?>> trackHandlers(
			String address, final Server server) {

			String handlersFilterString =
				"(&(objectClass=" + Handler.class.getName() + ")(address=" +
					address + "))";

			Filter handlersFilter;

			try {
				handlersFilter = _bundleContext.createFilter(
					handlersFilterString);
			}
			catch (InvalidSyntaxException ise) {
				throw new RuntimeException(ise);
			}

			ServiceTracker<Handler<?>, Handler<?>> handlerServiceTracker =
				new ServiceTracker<>(
					_bundleContext, handlersFilter,
					new HandlerServiceTrackerCustomizer(server));

			handlerServiceTracker.open();

			return handlerServiceTracker;
		}

		private final Bus _bus;

	}

}