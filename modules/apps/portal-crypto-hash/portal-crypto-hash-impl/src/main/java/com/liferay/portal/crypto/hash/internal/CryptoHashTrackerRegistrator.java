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

package com.liferay.portal.crypto.hash.internal;

import com.liferay.osgi.service.tracker.collections.ServiceReferenceServiceTuple;
import com.liferay.portal.crypto.hash.CryptoHashGenerator;
import com.liferay.portal.crypto.hash.CryptoHashVerifier;
import com.liferay.portal.crypto.hash.spi.CryptoHashProviderFactory;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;

import org.apache.aries.component.dsl.CachingServiceReference;
import org.apache.aries.component.dsl.OSGi;
import org.apache.aries.component.dsl.OSGiResult;
import org.apache.aries.component.dsl.Utils;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Carlos Sierra Andrés
 */
@Component(immediate = true, service = {})
public class CryptoHashTrackerRegistrator {

	@Activate
	protected void activate(BundleContext bundleContext) {
		CryptoHashProviderFactoryRegistry cryptoHashProviderFactoryRegistry =
			new CryptoHashProviderFactoryRegistry();

		OSGi<?> osgi = OSGi.serviceReferences(
			CryptoHashProviderFactory.class
		).map(
			CachingServiceReference::getServiceReference
		).flatMap(
			serviceReference -> OSGi.combine(
				ServiceReferenceServiceTuple::new, OSGi.just(serviceReference),
				OSGi.service(serviceReference))
		).splitBy(
			serviceReferenceServiceTuple -> {
				CryptoHashProviderFactory cryptoHashProviderFactory =
					serviceReferenceServiceTuple.getService();

				return OSGi.just(
					cryptoHashProviderFactory.
						getCryptoHashProviderFactoryName());
			},
			(key, serviceReferenceTupleOsgi) -> Utils.highest(
				serviceReferenceTupleOsgi)
		).effects(
			serviceReferenceServiceTuple -> {
				CryptoHashProviderFactory cryptoHashProviderFactory =
					serviceReferenceServiceTuple.getService();

				cryptoHashProviderFactoryRegistry.register(
					cryptoHashProviderFactory);
			},
			serviceReferenceServiceTuple -> {
				CryptoHashProviderFactory cryptoHashProviderFactory =
					serviceReferenceServiceTuple.getService();

				cryptoHashProviderFactoryRegistry.unregister(
					cryptoHashProviderFactory.
						getCryptoHashProviderFactoryName());
			}
		).filter(
			serviceReferenceServiceTuple -> {
				ServiceReference<CryptoHashProviderFactory> serviceReference =
					serviceReferenceServiceTuple.getServiceReference();

				return Validator.isNotNull(
					GetterUtil.getString(
						serviceReference.getProperty("configuration.pid")));
			}
		).flatMap(
			serviceReferenceServiceTuple -> {
				ServiceReference<CryptoHashProviderFactory> serviceReference =
					serviceReferenceServiceTuple.getServiceReference();

				return OSGi.configurations(
					GetterUtil.getString(
						serviceReference.getProperty("configuration.pid"))
				).flatMap(
					properties -> {
						CryptoHashProviderFactory cryptoHashProviderFactory =
							serviceReferenceServiceTuple.getService();

						try {
							Map<String, ?> cryptoHashProviderProperties =
								HashMapBuilder.putAll(
									properties
								).put(
									"crypto.hash.provider.factory.name",
									cryptoHashProviderFactory.
										getCryptoHashProviderFactoryName()
								).build();

							CryptoHashGenerator cryptoHashGenerator =
								new CryptoHashGeneratorImpl(
									cryptoHashProviderFactory.create(
										cryptoHashProviderProperties));

							return OSGi.register(
								CryptoHashGenerator.class,
								() -> cryptoHashGenerator,
								() -> cryptoHashProviderProperties);
						}
						catch (Exception exception) {
							return OSGi.nothing();
						}
					}
				);
			}
		);

		_osgiResult = osgi.run(bundleContext);

		_cryptoHashVerifierServiceRegistration = bundleContext.registerService(
			CryptoHashVerifier.class,
			new CryptoHashVerifierImpl(cryptoHashProviderFactoryRegistry),
			new HashMapDictionary<>());
	}

	@Deactivate
	protected void deactivate() {
		_cryptoHashVerifierServiceRegistration.unregister();

		_osgiResult.close();
	}

	private ServiceRegistration<CryptoHashVerifier>
		_cryptoHashVerifierServiceRegistration;
	private OSGiResult _osgiResult;

}