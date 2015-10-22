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

package com.liferay.resources.importer.internal;

import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.util.HashMapDictionary;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Daniel Kocsis
 */
public class ResourceImporterBundleActivator implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		DestinationConfiguration destinationConfiguration =
			new DestinationConfiguration(
				DestinationConfiguration.DESTINATION_TYPE_SERIAL,
				"liferay/resources_importer");

		_destinationServiceRegistration = bundleContext.registerService(
			DestinationConfiguration.class, destinationConfiguration,
			new HashMapDictionary<String, Object>());
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		_destinationServiceRegistration.unregister();
	}

	private ServiceRegistration<DestinationConfiguration>
		_destinationServiceRegistration;

}