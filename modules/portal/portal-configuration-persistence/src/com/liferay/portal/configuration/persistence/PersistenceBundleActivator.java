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

package com.liferay.portal.configuration.persistence;

import java.util.Collection;
import java.util.Dictionary;
import java.util.Hashtable;
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
public class PersistenceBundleActivator implements BundleActivator {

	@Override
	public void start(final BundleContext bundleContext) throws Exception {
		Collection<ServiceReference<DataSource>> serviceReferences =
			bundleContext.getServiceReferences(
				DataSource.class, "(bean.id=liferayDataSource)");

		if ((serviceReferences == null) || serviceReferences.isEmpty()) {
			throw new IllegalStateException("LiferayDataSource is needed");
		}

		Iterator<ServiceReference<DataSource>> iterator =
			serviceReferences.iterator();

		_dataSourceServiceReference = iterator.next();

		_configurationPersistenceManager =
			new ConfigurationPersistenceManager();

		_configurationPersistenceManager.setDataSource(
			bundleContext.getService(_dataSourceServiceReference));

		_configurationPersistenceManager.start();

		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put(Constants.SERVICE_RANKING, (Integer.MAX_VALUE - 1000));

		_serviceRegistration = bundleContext.registerService(
			PersistenceManager.class, _configurationPersistenceManager,
			properties);
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();
		}

		_configurationPersistenceManager.stop();

		if (_dataSourceServiceReference != null) {
			bundleContext.ungetService(_dataSourceServiceReference);
		}
	}

	private ConfigurationPersistenceManager _configurationPersistenceManager;
	private ServiceReference<DataSource> _dataSourceServiceReference;
	private ServiceRegistration<?> _serviceRegistration;

}