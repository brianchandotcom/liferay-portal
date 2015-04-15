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

import com.liferay.portal.soap.extender.test.activator.config.ConfigAdminActivator;
import com.liferay.portal.soap.extender.test.handler.SampleComponentHandler;
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
public class JaxwsApiBundleActivator implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		_configAdminActivator = new ConfigAdminActivator();

		_configAdminActivator.start(bundleContext);

		_endpoint = Endpoint.publish("/greeterApi", new GreeterImpl());

		Binding binding = _endpoint.getBinding();

		@SuppressWarnings("rawtypes")
		List<Handler> handlerChain = binding.getHandlerChain();

		Handler<?> handler = new SampleComponentHandler();

		handlerChain.add(handler);

		binding.setHandlerChain(handlerChain);
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		_configAdminActivator.stop(bundleContext);

		_endpoint.stop();
	}

	private ConfigAdminActivator _configAdminActivator;
	private Endpoint _endpoint;

}