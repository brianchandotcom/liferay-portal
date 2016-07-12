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

import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.ManagedService;

/**
 * @author Iván Zaera
 */
public class ConfigurationBeanManagedService implements ManagedService {

	public ConfigurationBeanManagedService(
		BundleContext bundleContext, Class<?> configurationBeanClass) {

		_bundleContext = bundleContext;
		_configurationBeanClass = configurationBeanClass;

		_configurationPid = ConfigurationPidUtil.getConfigurationPid(
			configurationBeanClass);
	}

	public Object getConfigurationBean() {
		return _configurationBean;
	}

	public Class<?> getConfigurationBeanClass() {
		return _configurationBeanClass;
	}

	public String getConfigurationPid() {
		return _configurationPid;
	}

	public boolean register() {
		if (_bundleContext != null) {
			Dictionary<String, Object> properties = new HashMapDictionary<>();

			properties.put(Constants.SERVICE_PID, _configurationPid);

			_managedServiceServiceRegistration = _bundleContext.registerService(
				ManagedService.class, this, properties);

			return true;
		}

		return false;
	}

	public void setBundleContext(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	public void unregister() {
		_managedServiceServiceRegistration.unregister();
		_configurationBeanServiceRegistration.unregister();
	}

	@Override
	public void updated(final Dictionary<String, ?> properties) {
		if (System.getSecurityManager() != null) {
			AccessController.doPrivileged(
				new UpdatePrivilegedAction(properties));
		}
		else {
			doUdated(properties);
		}
	}

	protected void doUdated(Dictionary<String, ?> properties) {
		if (properties == null) {
			properties = new HashMapDictionary<>();
		}

		_configurationBean = ConfigurableUtil.createConfigurable(
			_configurationBeanClass, properties);

		if (_configurationBeanServiceRegistration != null) {
			_configurationBeanServiceRegistration.unregister();
		}

		_configurationBeanServiceRegistration = _bundleContext.registerService(
			_configurationBeanClass.getName(), _configurationBean,
			new HashMapDictionary<String, Object>());
	}

	protected class UpdatePrivilegedAction implements PrivilegedAction<Void> {

		public UpdatePrivilegedAction(Dictionary<String, ?> properties) {
			_properties = properties;
		}

		@Override
		public Void run() {
			doUdated(_properties);

			return null;
		}

		private final Dictionary<String, ?> _properties;

	}

	private BundleContext _bundleContext;
	private volatile Object _configurationBean;
	private final Class<?> _configurationBeanClass;
	private ServiceRegistration<?> _configurationBeanServiceRegistration;
	private final String _configurationPid;
	private ServiceRegistration<ManagedService>
		_managedServiceServiceRegistration;

}