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

import com.liferay.portal.soap.extender.test.activator.configuration.ConfigurationAdminBundleActivator;
import com.liferay.portal.soap.extender.test.handler.SampleHandler;
import com.liferay.portal.soap.extender.test.service.GreeterImpl;
import com.liferay.portal.soap.extender.test.util.WaiterUtil;

import java.util.List;
import java.util.concurrent.TimeoutException;

import javax.xml.ws.Binding;
import javax.xml.ws.Endpoint;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.spi.Provider;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;

/**
 * @author Carlos Sierra Andrés
 */
public class JaxWsApiBundleActivator implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		_configurationAdminBundleActivator =
			new ConfigurationAdminBundleActivator();

		_configurationAdminBundleActivator.start(bundleContext);

		String filterString =
			"(&(objectClass=" + Provider.class.getName() + ")" +
				"("+ HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_PATH +
					"=/soap-test))";

		if (!WaiterUtil.waitForFilter(bundleContext, filterString, 10000)) {
			throw new TimeoutException("Provider register waiting timeout");
		}

		try {
			_endpoint = Endpoint.publish("/greeterApi", new GreeterImpl());

			Binding binding = _endpoint.getBinding();

			@SuppressWarnings("rawtypes")
			List<Handler> handlers = binding.getHandlerChain();

			Handler<?> handler = new SampleHandler();

			handlers.add(handler);

			binding.setHandlerChain(handlers);
		}
		catch (Exception e) {
			cleanUp(bundleContext);
		}
	}

	@Override
	public void stop(BundleContext bundleContext) {
		cleanUp(bundleContext);
	}

	protected void cleanUp(BundleContext bundleContext) {
		try {
			_configurationAdminBundleActivator.stop(bundleContext);
		}
		catch (Exception e) {
		}

		_endpoint.stop();
	}

	private ConfigurationAdminBundleActivator
		_configurationAdminBundleActivator;
	private Endpoint _endpoint;

}