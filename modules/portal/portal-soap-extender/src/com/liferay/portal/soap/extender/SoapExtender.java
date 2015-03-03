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
public class SoapExtender {

	public SoapExtender(
		BundleContext bundleContext, String contextPath,
		ExtensionManager extensionManager) {

		_bundleContext = bundleContext;
		_contextPath = contextPath;
		_extensionManager = extensionManager;
	}

	protected Bus createBus() {
		CXFBusFactory cxfBusFactory = (CXFBusFactory)CXFBusFactory.newInstance(
			CXFBusFactory.class.getName());

		return cxfBusFactory.createBus(_extensionManager.getExtensions());
	}

	protected void registerCXFServlet(Bus bus, String contextPath) {
		Dictionary<String, Object> properties = new Hashtable<>();

		Class<?> clazz = getClass();

		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_NAME,
			clazz.getName());
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_PATH, contextPath);

		_servletContextHelperServiceRegistration =
			_bundleContext.registerService(
				ServletContextHelper.class,
				new ServletContextHelper(_bundleContext.getBundle()) {},
				properties);

		properties = new Hashtable<>();

		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT,
			clazz.getName());
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_NAME, "CXFServlet");
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN, "/*");

		CXFNonSpringServlet cxfNonSpringServlet = new CXFNonSpringServlet();

		cxfNonSpringServlet.setBus(bus);

		_servletServiceRegistration = _bundleContext.registerService(
			Servlet.class, cxfNonSpringServlet, properties);
	}

	protected void start() {
		Bus bus = createBus();

		BusFactory.setDefaultBus(bus);

		registerCXFServlet(bus, _contextPath);

		try {
			Filter filter = _bundleContext.createFilter(
				"(&(jaxws=true)(soap.address=*))");

			_serverServiceTracker = new ServiceTracker<>(
				_bundleContext, filter,
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
					"Unable to close server service tracker " +
						_serverServiceTracker);
			}
		}

		try {
			_servletServiceRegistration.unregister();
		}
		catch (Exception e) {
			if (_logger.isWarnEnabled()) {
				_logger.warn(
					"Unable to unregister servlet service registration " +
						_servletServiceRegistration);
			}
		}

		try {
			_servletContextHelperServiceRegistration.unregister();
		}
		catch (Exception e) {
			if (_logger.isWarnEnabled()) {
				_logger.warn(
					"Unable to unregister servlet context helper service " +
						"registration " + _serverServiceTracker);
			}
		}
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		SoapExtender.class);

	private final BundleContext _bundleContext;
	private final String _contextPath;
	private final ExtensionManager _extensionManager;
	private ServiceTracker<Object, ServerTrackingInformation>
		_serverServiceTracker;
	private ServiceRegistration<ServletContextHelper>
		_servletContextHelperServiceRegistration;
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
			ServiceReference<Handler<?>> serviceReference) {

			Handler<?> handler = _bundleContext.getService(serviceReference);

			JaxWsEndpointImpl jaxWsEndpoint =
				(JaxWsEndpointImpl) _server.getEndpoint();

			Binding binding = jaxWsEndpoint.getJaxwsBinding();

			@SuppressWarnings("rawtypes")
			List<Handler> handlers = binding.getHandlerChain();

			handlers.add(handler);

			binding.setHandlerChain(handlers);

			return handler;
		}

		@Override
		public void modifiedService(
			ServiceReference<Handler<?>> serviceReference, Handler<?> handler) {
		}

		@Override
		public void removedService(
			ServiceReference<Handler<?>> serviceReference, Handler<?> handler) {

			JaxWsEndpointImpl jaxWsEndpoint =
				(JaxWsEndpointImpl)_server.getEndpoint();

			Binding binding = jaxWsEndpoint.getJaxwsBinding();

			@SuppressWarnings("rawtypes")
			List<Handler> handlers = binding.getHandlerChain();

			handlers.remove(handler);

			binding.setHandlerChain(handlers);

			_bundleContext.ungetService(serviceReference);
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

			jaxWsServerFactoryBean.setBus(_bus);

			Map<String, Object> properties = getPropertiesAsMap(
				serviceReference);

			jaxWsServerFactoryBean.setProperties(properties);

			Object addressObject = serviceReference.getProperty("soap.address");

			String address = addressObject.toString();

			jaxWsServerFactoryBean.setAddress(address);

			Object endpointNameObject = serviceReference.getProperty(
				"soap.endpoint.name");

			if ((endpointNameObject != null) &&
				endpointNameObject instanceof QName) {

				QName endpointName = (QName)endpointNameObject;

				jaxWsServerFactoryBean.setEndpointName(endpointName);
			}

			jaxWsServerFactoryBean.setServiceBean(service);

			Object serviceClassObject = serviceReference.getProperty(
				"soap.service.class");

			if ((serviceClassObject != null) &&
				serviceClassObject instanceof Class<?>) {

				Class<?> serviceClass = (Class<?>)serviceClassObject;

				jaxWsServerFactoryBean.setServiceClass(serviceClass);
			}

			Object wsdlLocationObject = serviceReference.getProperty(
				"soap.wsdl.location");

			if (wsdlLocationObject != null) {
				jaxWsServerFactoryBean.setWsdlLocation(
					wsdlLocationObject.toString());
			}

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

				_logger.error(t.getMessage(), t);

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

			Server server = serverTrackingInformation.getServer();

			server.destroy();

			ServiceTracker<Handler<?>, Handler<?>> serviceTracker =
				serverTrackingInformation.getServiceTracker();

			serviceTracker.close();

			_bundleContext.ungetService(serviceReference);
		}

		protected Map<String, Object> getPropertiesAsMap(
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

		protected ServiceTracker<Handler<?>, Handler<?>> trackHandlers(
			String address, final Server server) {

			String filterString =
				"(&(objectClass=" + Handler.class.getName() +
					")(soap.address=" + address + "))";

			Filter filter = null;

			try {
				filter = _bundleContext.createFilter(filterString);
			}
			catch (InvalidSyntaxException ise) {
				throw new RuntimeException(ise);
			}

			ServiceTracker<Handler<?>, Handler<?>> serviceTracker =
				new ServiceTracker<>(
					_bundleContext, filter,
					new HandlerServiceTrackerCustomizer(server));

			serviceTracker.open();

			return serviceTracker;
		}

		private final Bus _bus;

	}

}