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

package com.liferay.portal.crypto.hash.provider.message.digest.internal.factory;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.crypto.hash.provider.message.digest.internal.configuration.MessageDigestCryptoHashProviderConfiguration;
import com.liferay.portal.crypto.hash.provider.spi.CryptoHashProvider;
import com.liferay.portal.kernel.util.HashMapDictionary;

import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Sierra Andrés
 */
@Component(
	configurationPid = "com.liferay.portal.crypto.hash.provider.message.digest.internal.configuration.MessageDigestCryptoHashProviderConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE, service = {}
)
public class MessageDigestCryptoHashProviderRegistrator {

	@Activate
	protected void activate(
			BundleContext bundleContext, Map<String, ?> properties)
		throws Exception {

		_messageDigestCryptoHashProviderConfiguration =
			ConfigurableUtil.createConfigurable(
				MessageDigestCryptoHashProviderConfiguration.class, properties);

		_serviceRegistration = bundleContext.registerService(
			CryptoHashProvider.class,
			_messageDigestCryptoHashProviderFactory.create(
				_messageDigestCryptoHashProviderConfiguration.
					cryptoHashProvider(),
				properties),
			new HashMapDictionary<>(properties));
	}

	@Deactivate
	protected void deactivate() {
		_serviceRegistration.unregister();
	}

	private MessageDigestCryptoHashProviderConfiguration
		_messageDigestCryptoHashProviderConfiguration;

	@Reference
	private MessageDigestCryptoHashProviderFactory
		_messageDigestCryptoHashProviderFactory;

	private ServiceRegistration<CryptoHashProvider> _serviceRegistration;

}