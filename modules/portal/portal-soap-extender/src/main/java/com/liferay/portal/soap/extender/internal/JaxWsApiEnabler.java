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

package com.liferay.portal.soap.extender.internal;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.soap.extender.configuration.JaxWsApiConfiguration;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;

import javax.xml.ws.spi.Provider;

import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.jaxws22.spi.ProviderImpl;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Carlos Sierra Andrés
 */
@Component(
	configurationPid = "com.liferay.portal.soap.extender.configuration.JaxWsApiConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE
)
public class JaxWsApiEnabler {

	@Activate
	protected void activate(
			BundleContext bundleContext, Map<String, Object> properties)
		throws InterruptedException, InvalidSyntaxException {

		JaxWsApiConfiguration jaxWsApiConfiguration =
			Configurable.createConfigurable(
				JaxWsApiConfiguration.class, properties);

		String contextPath = jaxWsApiConfiguration.contextPath();

		_serviceTracker = ServiceTrackerFactory.open(
			bundleContext,
			"(&(objectClass=org.apache.cxf.Bus)(" +
				HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_PATH + "=" +
					contextPath + "))");

		_bus = _serviceTracker.waitForService(jaxWsApiConfiguration.timeout());

		if (_bus != null) {
			BusFactory.setDefaultBus(_bus);

			ProviderImpl providerImpl = new ProviderImpl();

			Dictionary<String, Object> providerProperties = new Hashtable<>();

			providerProperties.put(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_PATH,
				contextPath);

			_serviceRegistration = bundleContext.registerService(
				Provider.class, providerImpl, providerProperties);
		}
	}

	@Deactivate
	protected void deactivate() {
		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();
		}

		_serviceTracker.close();
	}

	@Modified
	protected void modified(
			BundleContext bundleContext, Map<String, Object> properties)
		throws InterruptedException, InvalidSyntaxException {

		deactivate();

		BusFactory.clearDefaultBusForAnyThread(_bus);

		activate(bundleContext, properties);
	}

	private Bus _bus;
	private ServiceRegistration<Provider> _serviceRegistration;
	private ServiceTracker<Bus, Bus> _serviceTracker;

}