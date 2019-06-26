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

package com.liferay.portal.configuration.persistence.internal.activator;

import com.liferay.portal.configuration.persistence.ReloadablePersistenceManager;
import com.liferay.portal.configuration.persistence.internal.ConfigurationPersistenceManager;
import com.liferay.portal.configuration.persistence.internal.upgrade.ConfigurationUpgradeStepFactoryImpl;
import com.liferay.portal.configuration.persistence.upgrade.ConfigurationUpgradeStepFactory;
import com.liferay.portal.kernel.util.HashMapDictionary;

import java.util.Collection;
import java.util.Dictionary;
import java.util.Iterator;

import javax.sql.DataSource;

import org.apache.felix.cm.PersistenceManager;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Carlos Sierra Andrés
 */
public class ConfigurationPersistenceImplBundleActivator
	implements BundleActivator {

	@Override
	public void start(final BundleContext bundleContext) throws Exception {
		Collection<ServiceReference<DataSource>> serviceReferences =
			bundleContext.getServiceReferences(
				DataSource.class, "(bean.id=liferayDataSource)");

		if ((serviceReferences == null) || serviceReferences.isEmpty()) {
			throw new IllegalStateException(
				"Liferay data source is not available");
		}

		Iterator<ServiceReference<DataSource>> iterator =
			serviceReferences.iterator();

		_serviceReference = iterator.next();

		_configurationPersistenceManager = new ConfigurationPersistenceManager(
			bundleContext, bundleContext.getService(_serviceReference));

		_configurationPersistenceManager.start();

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(
			PersistenceManager.PROPERTY_NAME,
			ConfigurationPersistenceManager.class.getName());
		properties.put(Constants.SERVICE_RANKING, Integer.MAX_VALUE - 1000);

		_configurationPersistenceManagerServiceRegistration =
			bundleContext.registerService(
				new String[] {
					PersistenceManager.class.getName(),
					ReloadablePersistenceManager.class.getName()
				},
				_configurationPersistenceManager, properties);

		_configurationUpgradeStepFactoryRegistration =
			bundleContext.registerService(
				ConfigurationUpgradeStepFactory.class,
				new ConfigurationUpgradeStepFactoryImpl(
					_configurationPersistenceManager),
				null);
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		if (_configurationUpgradeStepFactoryRegistration != null) {
			_configurationUpgradeStepFactoryRegistration.unregister();
		}

		if (_configurationPersistenceManagerServiceRegistration != null) {
			_configurationPersistenceManagerServiceRegistration.unregister();
		}

		_configurationPersistenceManager.stop();

		if (_serviceReference != null) {
			bundleContext.ungetService(_serviceReference);
		}
	}

	private ConfigurationPersistenceManager _configurationPersistenceManager;
	private ServiceRegistration<?>
		_configurationPersistenceManagerServiceRegistration;
	private ServiceRegistration<ConfigurationUpgradeStepFactory>
		_configurationUpgradeStepFactoryRegistration;
	private ServiceReference<DataSource> _serviceReference;

}