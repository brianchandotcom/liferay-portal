/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osgi.service.tracker.collections.map;

import com.liferay.osgi.service.tracker.collections.internal.DefaultServiceTrackerCustomizer;

import java.util.List;
import java.util.function.Supplier;

import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Carlos Sierra Andrés
 */
public class ScopedServiceTrackerMapFactory<T> {

	public static <K, SR, T> ScopedServiceTrackerMap<T> create(
		BundleContext bundleContext, Class<SR> clazz, String filterString,
		ServiceReferenceMapper<String, SR> serviceReferenceMapper,
		ServiceTrackerCustomizer<SR, T> serviceTrackerCustomizer) {

		return new ScopedServiceTrackerMapImpl<>(
			bundleContext, clazz, null, filterString, () -> null,
			() -> {
			},
			serviceReferenceMapper, serviceTrackerCustomizer);
	}

	public static <T> ScopedServiceTrackerMap<T> create(
		BundleContext bundleContext, Class<T> clazz, String property,
		String filterString, Supplier<T> defaultServiceSupplier) {

		return create(
			bundleContext, clazz, property, filterString,
			defaultServiceSupplier,
			() -> {
			});
	}

	public static <T> ScopedServiceTrackerMap<T> create(
		BundleContext bundleContext, Class<T> clazz, String property,
		String filterString, Supplier<T> defaultServiceSupplier,
		Runnable onChangeRunnable) {

		return new ScopedServiceTrackerMapImpl<>(
			bundleContext, clazz, property, filterString,
			defaultServiceSupplier, onChangeRunnable,
			new PropertyServiceReferenceMapper<>(property),
			new DefaultServiceTrackerCustomizer<>(bundleContext));
	}

	public static <T> ScopedServiceTrackerMap<T> create(
		BundleContext bundleContext, Class<T> clazz, String property,
		Supplier<T> defaultServiceSupplier) {

		return create(
			bundleContext, clazz, property, "", defaultServiceSupplier,
			() -> {
			});
	}

	private static class ScopedServiceTrackerMapImpl<SR, T>
		implements ScopedServiceTrackerMap<T> {

		@Override
		public void close() {
			if (_servicesByCompany != null) {
				_servicesByCompany.close();
			}

			_servicesByCompanyAndKey.close();
			_servicesByKey.close();
		}

		@Override
		public T getService(long companyId, String key) {
			String companyIdString = String.valueOf(companyId);

			List<T> services = _servicesByCompanyAndKey.getService(
				companyIdString + "-" + key);

			if ((services != null) && !services.isEmpty()) {
				return services.get(0);
			}

			services = _servicesByKey.getService(key);

			if ((services != null) && !services.isEmpty()) {
				return services.get(0);
			}

			if (_servicesByCompany != null) {
				services = _servicesByCompany.getService(companyIdString);
			}

			if ((services != null) && !services.isEmpty()) {
				return services.get(0);
			}

			return _defaultServiceSupplier.get();
		}

		private ScopedServiceTrackerMapImpl(
			BundleContext bundleContext, Class<SR> clazz, String property,
			String filterString, Supplier<T> defaultServiceSupplier,
			Runnable onChangeRunnable,
			ServiceReferenceMapper<String, SR> serviceReferenceMapper,
			ServiceTrackerCustomizer<SR, T> serviceTrackerCustomizer) {

			_defaultServiceSupplier = defaultServiceSupplier;
			_onChangeRunnable = onChangeRunnable;

			String propertyFilterString = "";

			if (property == null) {
				_servicesByCompany = null;
			}
			else {
				propertyFilterString = "(" + property + "=*)";

				_servicesByCompany = ServiceTrackerMapFactory.openMultiValueMap(
					bundleContext, clazz,
					"(&(companyId=*)(!" + propertyFilterString + ")" +
						filterString + ")",
					new PropertyServiceReferenceMapper<>("companyId"),
					serviceTrackerCustomizer,
					new ServiceTrackerMapListenerImpl());
			}

			_servicesByCompanyAndKey =
				ServiceTrackerMapFactory.openMultiValueMap(
					bundleContext, clazz,
					"(&(companyId=*)" + propertyFilterString + filterString +
						")",
					(serviceReference, emitter) -> {
						ServiceReferenceMapper<String, SR> companyMapper =
							new PropertyServiceReferenceMapper<>("companyId");

						companyMapper.map(
							serviceReference,
							key1 -> serviceReferenceMapper.map(
								serviceReference,
								key2 -> emitter.emit(key1 + "-" + key2)));
					},
					serviceTrackerCustomizer,
					new ServiceTrackerMapListenerImpl());
			_servicesByKey = ServiceTrackerMapFactory.openMultiValueMap(
				bundleContext, clazz,
				"(&" + propertyFilterString +
					"(|(!(companyId=*))(companyId=0))" + filterString + ")",
				serviceReferenceMapper, serviceTrackerCustomizer,
				new ServiceTrackerMapListenerImpl());
		}

		private final Supplier<T> _defaultServiceSupplier;
		private final Runnable _onChangeRunnable;
		private final ServiceTrackerMap<String, List<T>> _servicesByCompany;
		private final ServiceTrackerMap<String, List<T>>
			_servicesByCompanyAndKey;
		private final ServiceTrackerMap<String, List<T>> _servicesByKey;

		private class ServiceTrackerMapListenerImpl
			implements ServiceTrackerMapListener<String, T, List<T>> {

			@Override
			public void keyEmitted(
				ServiceTrackerMap<String, List<T>> serviceTrackerMap,
				String key, T service, List<T> content) {

				_onChangeRunnable.run();
			}

			@Override
			public void keyRemoved(
				ServiceTrackerMap<String, List<T>> serviceTrackerMap,
				String key, T service, List<T> content) {

				_onChangeRunnable.run();
			}

		}

	}

}