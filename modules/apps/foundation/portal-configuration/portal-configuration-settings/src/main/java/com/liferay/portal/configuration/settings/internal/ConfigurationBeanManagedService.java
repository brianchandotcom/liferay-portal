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

package com.liferay.portal.configuration.settings.internal;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.configuration.settings.internal.util.ConfigurationPidUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;

import java.security.AccessController;
import java.security.PrivilegedAction;

import java.util.Dictionary;
import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.function.Consumer;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.ManagedService;

/**
 * @author Iván Zaera
 * @author Shuyang Zhou
 */
public class ConfigurationBeanManagedService implements ManagedService {

	public ConfigurationBeanManagedService(
		BundleContext bundleContext, Class<?> configurationBeanClass,
		Consumer<Object> configurationBeanConsumer) {

		_bundleContext = bundleContext;
		_configurationBeanClass = configurationBeanClass;
		_configurationBeanConsumer = configurationBeanConsumer;

		_configurationPid = ConfigurationPidUtil.getConfigurationPid(
			configurationBeanClass);
	}

	public String getConfigurationPid() {
		return _configurationPid;
	}

	public void register() {
		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(Constants.SERVICE_PID, _configurationPid);

		_managedServiceServiceRegistration = _bundleContext.registerService(
			ManagedService.class, this, properties);
	}

	public void unregister() {
		_managedServiceServiceRegistration.unregister();

		while (true) {
			ServiceRegistration<?> serviceRegistration =
				_configurationBeanServiceRegistrationReference.getReference();

			if (_configurationBeanServiceRegistrationReference.compareAndSet(
					serviceRegistration, null, false, true)) {

				serviceRegistration.unregister();

				return;
			}
		}
	}

	@Override
	public void updated(final Dictionary<String, ?> properties) {
		if (System.getSecurityManager() != null) {
			AccessController.doPrivileged(
				new UpdatePrivilegedAction(properties));
		}
		else {
			doUpdated(properties);
		}
	}

	protected void doUpdated(Dictionary<String, ?> properties) {
		if (properties == null) {
			properties = new HashMapDictionary<>();
		}

		Object configurationBean = ConfigurableUtil.createConfigurable(
			_configurationBeanClass, properties);

		_configurationBeanConsumer.accept(configurationBean);

		ServiceRegistration<?> newServiceRegistration =
			_bundleContext.registerService(
				_configurationBeanClass.getName(), configurationBean,
				new HashMapDictionary<>());

		while (true) {
			if (_configurationBeanServiceRegistrationReference.isMarked()) {
				newServiceRegistration.unregister();

				break;
			}

			ServiceRegistration<?> serviceRegistration =
				_configurationBeanServiceRegistrationReference.getReference();

			if (_configurationBeanServiceRegistrationReference.compareAndSet(
					serviceRegistration, newServiceRegistration, false,
					false)) {

				if (serviceRegistration != null) {
					serviceRegistration.unregister();
				}

				break;
			}
		}
	}

	protected class UpdatePrivilegedAction implements PrivilegedAction<Void> {

		@Override
		public Void run() {
			doUpdated(_properties);

			return null;
		}

		private UpdatePrivilegedAction(Dictionary<String, ?> properties) {
			_properties = properties;
		}

		private final Dictionary<String, ?> _properties;

	}

	private final BundleContext _bundleContext;
	private final Class<?> _configurationBeanClass;
	private final Consumer<Object> _configurationBeanConsumer;
	private AtomicMarkableReference<ServiceRegistration<?>>
		_configurationBeanServiceRegistrationReference =
			new AtomicMarkableReference<>(null, false);
	private final String _configurationPid;
	private ServiceRegistration<ManagedService>
		_managedServiceServiceRegistration;

}