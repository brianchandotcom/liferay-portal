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

package com.liferay.portal.crypto.hash.internal.processor;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.crypto.hash.provider.spi.factory.CryptoHashProviderFactory;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Carlos Sierra Andrés
 */
@Component(service = CryptoHashProviderFactoryRegistry.class)
public class CryptoHashProviderFactoryRegistry {

	public CryptoHashProviderFactory getCryptoHashProviderFactory(String name) {
		return _cryptoHashProviderFactories.getService(name);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_cryptoHashProviderFactories =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, CryptoHashProviderFactory.class,
				"crypto.hash.provider.name");
	}

	private ServiceTrackerMap<String, CryptoHashProviderFactory>
		_cryptoHashProviderFactories;

}