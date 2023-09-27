/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.crypto.hash.internal;

import com.liferay.portal.crypto.hash.CryptoHashGenerator;
import com.liferay.portal.crypto.hash.exception.CryptoHashException;
import com.liferay.portal.crypto.hash.spi.CryptoHashProviderFactory;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.Dictionary;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedServiceFactory;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Carlos Sierra Andrés
 */
@Component(service = {})
public class CryptoHashTrackerRegistrator {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTracker =
			new ServiceTracker
				<CryptoHashProviderFactory, ServiceRegistration<?>>(
					bundleContext, CryptoHashProviderFactory.class,
					new ServiceTrackerCustomizer
						<CryptoHashProviderFactory, ServiceRegistration<?>>() {

						@Override
						public ServiceRegistration<?> addingService(
							ServiceReference<CryptoHashProviderFactory>
								serviceReference) {

							return bundleContext.registerService(
								ManagedServiceFactory.class,
								new CryptoHashGeneratorManagedServiceFactory(
									bundleContext,
									bundleContext.getService(serviceReference)),
								MapUtil.singletonDictionary(
									Constants.SERVICE_PID,
									serviceReference.getProperty(
										"configuration.pid")));
						}

						@Override
						public void modifiedService(
							ServiceReference<CryptoHashProviderFactory>
								serviceReference,
							ServiceRegistration<?> serviceRegistration) {

							Object pid = serviceReference.getProperty(
								"configuration.pid");

							if (pid == null) {
								serviceRegistration.setProperties(null);
							}
							else {
								serviceRegistration.setProperties(
									MapUtil.singletonDictionary(
										Constants.SERVICE_PID,
										serviceReference.getProperty(
											"configuration.pid")));
							}
						}

						@Override
						public void removedService(
							ServiceReference<CryptoHashProviderFactory>
								serviceReference,
							ServiceRegistration<?> serviceRegistration) {

							serviceRegistration.unregister();
						}

					});

		_serviceTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();
	}

	private ServiceTracker<CryptoHashProviderFactory, ServiceRegistration<?>>
		_serviceTracker;

	private static class CryptoHashGeneratorManagedServiceFactory
		implements ManagedServiceFactory {

		@Override
		public void deleted(String pid) {
			ServiceRegistration<?> serviceRegistration =
				_serviceRegistrations.remove(pid);

			if (serviceRegistration != null) {
				serviceRegistration.unregister();
			}
		}

		@Override
		public String getName() {
			return _cryptoHashProviderFactory.
				getCryptoHashProviderFactoryName();
		}

		@Override
		public void updated(String pid, Dictionary<String, ?> properties)
			throws ConfigurationException {

			Map<String, Object> cryptoHashProviderProperties =
				HashMapBuilder.<String, Object>putAll(
					properties
				).put(
					"crypto.hash.provider.factory.name",
					_cryptoHashProviderFactory.
						getCryptoHashProviderFactoryName()
				).build();

			try {
				_serviceRegistrations.put(
					pid,
					_bundleContext.registerService(
						CryptoHashGenerator.class,
						new CryptoHashGeneratorImpl(
							_cryptoHashProviderFactory.create(
								cryptoHashProviderProperties)),
						new HashMapDictionary<>(cryptoHashProviderProperties)));
			}
			catch (CryptoHashException cryptoHashException) {
				throw new ConfigurationException(
					(String)properties.get("configurationName"),
					cryptoHashException.getMessage(), cryptoHashException);
			}
		}

		private CryptoHashGeneratorManagedServiceFactory(
			BundleContext bundleContext,
			CryptoHashProviderFactory cryptoHashProviderFactory) {

			_bundleContext = bundleContext;
			_cryptoHashProviderFactory = cryptoHashProviderFactory;
		}

		private final BundleContext _bundleContext;
		private final CryptoHashProviderFactory _cryptoHashProviderFactory;
		private final Map<String, ServiceRegistration<?>>
			_serviceRegistrations = new ConcurrentHashMap<>();

	}

}