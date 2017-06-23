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

package com.liferay.portal.configuration.test.util;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.osgi.util.test.OSGiServiceUtil;

import java.util.Dictionary;
import java.util.concurrent.CountDownLatch;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.runtime.ServiceComponentRuntime;
import org.osgi.service.component.runtime.dto.ComponentDescriptionDTO;

/**
 * @author Drew Brokke
 */
public class ConfigurationTemporarySwapper implements AutoCloseable {

	public ConfigurationTemporarySwapper(
			Class<?> serviceClass, String pid,
			Dictionary<String, Object> properties)
		throws Exception {

		_service = OSGiServiceUtil.callService(
			_bundleContext, serviceClass,
			service -> _validateService(serviceClass, service, pid));

		_configuration = OSGiServiceUtil.callService(
			_bundleContext, ConfigurationAdmin.class,
			configurationAdmin -> configurationAdmin.getConfiguration(
				pid, StringPool.QUESTION));

		_oldProperties = _configuration.getProperties();

		_updateProperties(_service, _configuration, properties);
	}

	@Override
	public void close() throws Exception {
		_updateProperties(_service, _configuration, _oldProperties);
	}

	private static void _updateProperties(
			Object service, Configuration configuration,
			Dictionary<String, Object> dictionary)
		throws Exception {

		CountDownLatch countDownLatch = new CountDownLatch(1);

		ServiceListener serviceListener = new ServiceListener() {

			@Override
			public void serviceChanged(ServiceEvent serviceEvent) {
				if (serviceEvent.getType() == ServiceEvent.REGISTERED) {
					return;
				}

				ServiceReference<?> serviceReference =
					serviceEvent.getServiceReference();

				Object changedService = _bundleContext.getService(
					serviceReference);

				if (changedService == service) {
					countDownLatch.countDown();
				}

				_bundleContext.ungetService(serviceReference);
			}

		};

		_bundleContext.addServiceListener(serviceListener);

		try {
			if (dictionary == null) {
				configuration.delete();
			}
			else {
				configuration.update(dictionary);
			}

			countDownLatch.await();
		}
		finally {
			_bundleContext.removeServiceListener(serviceListener);
		}
	}

	private Object _validateService(
			Class<?> serviceClass, Object service, String pid)
		throws Exception {

		if (service == null) {
			throw new ConfigurationTemporarySwapperException.MustFindService(
				serviceClass);
		}

		Bundle bundle = FrameworkUtil.getBundle(service.getClass());

		if (bundle == null) {
			throw new ConfigurationTemporarySwapperException.
				ServiceMustHaveBundle(service.getClass());
		}

		BundleContext bundleContext = bundle.getBundleContext();

		ServiceReference<?> serviceReference =
			bundleContext.getServiceReference(serviceClass);

		String componentName = (String)serviceReference.getProperty(
			"component.name");

		ComponentDescriptionDTO componentDescriptionDTO =
			OSGiServiceUtil.callService(
				bundleContext, ServiceComponentRuntime.class,
				serviceComponentRuntime ->
					serviceComponentRuntime.getComponentDescriptionDTO(
						bundle, componentName));

		for (String curPid : componentDescriptionDTO.configurationPid) {
			if (pid.equals(curPid)) {
				return service;
			}
		}

		throw new ConfigurationTemporarySwapperException.
			ServiceMustConsumeConfiguration(componentName, pid);
	}

	private static final BundleContext _bundleContext;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			ConfigurationTemporarySwapper.class);

		_bundleContext = bundle.getBundleContext();
	}

	private final Configuration _configuration;
	private final Dictionary<String, Object> _oldProperties;
	private final Object _service;

}