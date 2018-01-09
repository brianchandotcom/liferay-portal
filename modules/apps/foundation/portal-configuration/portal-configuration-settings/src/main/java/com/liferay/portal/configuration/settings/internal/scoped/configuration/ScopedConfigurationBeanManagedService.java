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

package com.liferay.portal.configuration.settings.internal.scoped.configuration;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.configuration.metatype.util.ConfigurationScopedPidUtil;
import com.liferay.portal.configuration.settings.internal.util.ConfigurationPidUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;

import java.security.AccessController;
import java.security.PrivilegedAction;

import java.util.Dictionary;
import java.util.function.Consumer;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.ManagedService;

/**
 * @author Drew Brokke
 */
public class ScopedConfigurationBeanManagedService implements ManagedService {

	public ScopedConfigurationBeanManagedService(
		BundleContext bundleContext, Class<?> configurationBeanClass,
		Consumer<Object> configurationBeanConsumer, ScopeKey scopeKey) {

		_bundleContext = bundleContext;
		_configurationBeanClass = configurationBeanClass;
		_configurationBeanConsumer = configurationBeanConsumer;
		_scopeKey = scopeKey;
	}

	public void register() {
		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(
			Constants.SERVICE_PID,
			ConfigurationScopedPidUtil.buildConfigurationScopedPid(
				ConfigurationPidUtil.getConfigurationPid(
					_configurationBeanClass),
				_scopeKey.getScope(), _scopeKey.getScopePrimKey()));

		_managedServiceServiceRegistration = _bundleContext.registerService(
			ManagedService.class, this, properties);
	}

	public void unregister() {
		_managedServiceServiceRegistration.unregister();
	}

	@Override
	public void updated(final Dictionary<String, ?> properties) {
		if (System.getSecurityManager() != null) {
			AccessController.doPrivileged(
				new ScopedConfigurationBeanManagedService.
					UpdatePrivilegedAction(properties));
		}
		else {
			doUpdated(properties);
		}
	}

	protected void doUpdated(Dictionary<String, ?> properties) {
		if (properties == null) {
			_configurationBeanConsumer.accept(null);
		}
		else {
			_configurationBeanConsumer.accept(
				ConfigurableUtil.createConfigurable(
					_configurationBeanClass, properties));
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
	private ServiceRegistration<ManagedService>
		_managedServiceServiceRegistration;
	private final ScopeKey _scopeKey;

}