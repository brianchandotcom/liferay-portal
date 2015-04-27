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

package com.liferay.portal.soap.extender.test.activator;

import com.liferay.portal.soap.extender.test.activator.config.ConfigAdminBundleActivator;
import com.liferay.portal.soap.extender.test.service.GreeterImpl;

import javax.xml.ws.Endpoint;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * @author Carlos Sierra Andrés
 */
public class JaxwsApiBundleActivator1 implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		_configAdminActivator = new ConfigAdminBundleActivator();

		_configAdminActivator.start(bundleContext);

		_endpoint = Endpoint.publish("/greeterApi", new GreeterImpl());
	}

	@Override
	public void stop(BundleContext bundleContext) {
		try {
			_endpoint.stop();
		}
		catch (Exception e) {
		}

		_configAdminActivator.stop(bundleContext);
	}

	private ConfigAdminBundleActivator _configAdminActivator;
	private Endpoint _endpoint;

}