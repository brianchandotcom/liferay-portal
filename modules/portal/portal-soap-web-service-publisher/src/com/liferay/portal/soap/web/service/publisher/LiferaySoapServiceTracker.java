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

package com.liferay.portal.soap.web.service.publisher;

import com.liferay.portal.soap.web.service.publisher.configuration.ExtensionManager;

import java.util.Dictionary;
import java.util.Hashtable;

import javax.servlet.Servlet;
import javax.xml.ws.spi.Provider;

import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.bus.CXFBusFactory;
import org.apache.cxf.jaxws22.spi.ProviderImpl;
import org.apache.cxf.transport.servlet.CXFNonSpringServlet;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.http.context.ServletContextHelper;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;

/**
 * @author Carlos Sierra Andrés
 */
public class LiferaySoapServiceTracker {

	public static final String CONTEXT_NAME = "LIFERAY_CXF_CONTEXT";

	public LiferaySoapServiceTracker(
		BundleContext bundleContext, String contextPath,
		ExtensionManager extensionManager) {

		_bundleContext = bundleContext;
		_contextPath = contextPath;
		_extensionManager = extensionManager;
	}

	protected void start() {
		Bus bus = _createBus();

		_registerLiferayJaxWsProvider();

		BusFactory.setDefaultBus(bus);
		
		_registerCXFServlet(bus, _contextPath);
	}

	protected void stop() {
		try {
			_providerServiceRegistration.unregister();
		}
		catch (Exception e) {
		}

		try {
			_servletServiceRegistration.unregister();
		}
		catch (Exception e) {
		}

		try {
			_cxfServlet.destroy();
		}
		catch (Exception e) {
		}

		try {
			_servletContextHelperRegistration.unregister();
		}
		catch (Exception e) {
		}
	}

	private Bus _createBus() {
		CXFBusFactory busFactory = (CXFBusFactory)CXFBusFactory.newInstance(
			"org.apache.cxf.bus.CXFBusFactory");

		return busFactory.createBus(_extensionManager.getExtensions());
	}

	private void _registerCXFServlet(Bus bus, String contextPath) {
		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_NAME, CONTEXT_NAME);
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_PATH, contextPath);

		_servletContextHelperRegistration = _bundleContext.registerService(
			ServletContextHelper.class,
			new ServletContextHelper(_bundleContext.getBundle()) {},
			properties);

		properties = new Hashtable<>();

		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT,
			CONTEXT_NAME);
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_NAME, "CXFServlet");
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN, "/*");

		_cxfServlet = new CXFNonSpringServlet();

		_cxfServlet.setBus(bus);

		_servletServiceRegistration = _bundleContext.registerService(
			Servlet.class, _cxfServlet, properties);
	}

	private void _registerLiferayJaxWsProvider() {
		ProviderImpl providerImpl = new ProviderImpl();

		_providerServiceRegistration = _bundleContext.registerService(
			Provider.class, providerImpl, null);
	}

	private final BundleContext _bundleContext;
	private final String _contextPath;
	private CXFNonSpringServlet _cxfServlet;
	private final ExtensionManager _extensionManager;
	private ServiceRegistration<Provider> _providerServiceRegistration;
	private ServiceRegistration<ServletContextHelper>
		_servletContextHelperRegistration;
	private ServiceRegistration<Servlet> _servletServiceRegistration;

}
