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

package com.liferay.portal.soap.extender.test.activator.handler;

import com.liferay.portal.soap.extender.test.activator.config.ConfigAdminBundleActivator;
import com.liferay.portal.soap.extender.test.handler.SampleHandler;
import com.liferay.portal.soap.extender.test.service.GreeterImpl;

import java.util.List;

import javax.xml.ws.Binding;
import javax.xml.ws.Endpoint;
import javax.xml.ws.handler.Handler;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * @author Carlos Sierra Andrés
 */
public class JaxWsApiBundleActivator implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		_configAdminBundleActivator = new ConfigAdminBundleActivator();

		_configAdminBundleActivator.start(bundleContext);

		_endpoint = Endpoint.publish("/greeterApi", new GreeterImpl());

		Binding binding = _endpoint.getBinding();

		@SuppressWarnings("rawtypes")
		List<Handler> handlers = binding.getHandlerChain();

		Handler<?> handler = new SampleHandler();

		handlers.add(handler);

		binding.setHandlerChain(handlers);
	}

	@Override
	public void stop(BundleContext bundleContext) {
		try {
			_configAdminBundleActivator.stop(bundleContext);
		}
		catch (Exception e) {
		}

		_endpoint.stop();
	}

	private ConfigAdminBundleActivator _configAdminBundleActivator;
	private Endpoint _endpoint;

}