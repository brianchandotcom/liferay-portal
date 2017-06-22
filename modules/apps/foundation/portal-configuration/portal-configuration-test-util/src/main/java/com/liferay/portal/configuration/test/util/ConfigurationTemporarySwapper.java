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

import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.osgi.util.test.OSGiServiceUtil;

import java.util.Dictionary;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

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

		Bundle bundle = FrameworkUtil.getBundle(
			ConfigurationTemporarySwapper.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_configuration = OSGiServiceUtil.callService(
			bundleContext, ConfigurationAdmin.class,
			configurationAdmin -> configurationAdmin.getConfiguration(
				pid, StringPool.QUESTION));

		_oldProperties = _configuration.getProperties();

		_serviceListener = OSGiServiceUtil.callService(
			bundleContext, serviceClass,
			service -> {
				_validateService(serviceClass, service, pid);

				return new ConfigurationServiceListener(serviceClass, service);
			});

		_serviceListener.register();

		_serviceListener._countDownLatch = new CountDownLatch(1);

		_configuration.update(properties);

		_serviceListener._countDownLatch.await(
			TestPropsValues.CI_TEST_TIMEOUT_TIME, TimeUnit.MILLISECONDS);
	}

	@Override
	public void close() throws Exception {
		try {
			if (_configuration != null) {
				_serviceListener._countDownLatch = new CountDownLatch(1);

				if (_oldProperties != null) {
					_configuration.update(_oldProperties);
				}
				else {
					_configuration.delete();
				}

				_serviceListener._countDownLatch.await(
					TestPropsValues.CI_TEST_TIMEOUT_TIME,
					TimeUnit.MILLISECONDS);
			}
		}
		finally {
			_serviceListener.unregister();
		}
	}

	private void _validateService(
			Class<?> serviceClass, Object service, String pid)
		throws Exception {

		if (service == null) {
			throw new ConfigurationTemporarySwapperException.MustFindService(
				serviceClass.getName());
		}

		Bundle bundle = FrameworkUtil.getBundle(service.getClass());

		if (bundle == null) {
			throw new ConfigurationTemporarySwapperException.
				ServiceMustHaveBundle(service.getClass().getName());
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
				return;
			}
		}

		throw new ConfigurationTemporarySwapperException.
			ServiceMustConsumeConfiguration(componentName, pid);
	}

	private final Configuration _configuration;
	private final Dictionary<String, Object> _oldProperties;
	private final ConfigurationServiceListener _serviceListener;

	private static class ConfigurationServiceListener
		implements ServiceListener {

		public void register() throws Exception {
			_bundleContext.addServiceListener(
				this, "(objectClass=" + _serviceClass.getName() + ")");
		}

		@Override
		public void serviceChanged(ServiceEvent serviceEvent) {
			if (serviceEvent.getType() == ServiceEvent.MODIFIED) {
				_countDownLatch.countDown();
			}
		}

		public void unregister() {
			_bundleContext.removeServiceListener(this);
		}

		private ConfigurationServiceListener(
			Class<?> serviceClass, Object service) {

			_serviceClass = serviceClass;

			Bundle bundle = FrameworkUtil.getBundle(service.getClass());

			_bundleContext = bundle.getBundleContext();
		}

		private final BundleContext _bundleContext;
		private CountDownLatch _countDownLatch;
		private final Class<?> _serviceClass;

	}

}