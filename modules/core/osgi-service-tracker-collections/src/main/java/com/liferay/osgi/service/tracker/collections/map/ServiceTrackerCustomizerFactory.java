/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osgi.service.tracker.collections.map;

import com.liferay.osgi.service.tracker.collections.ServiceReferenceServiceTuple;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Carlos Sierra Andrés
 */
public class ServiceTrackerCustomizerFactory {

	public static <S, T> Function<BundleContext, ServiceTrackerCustomizer<S, T>>
		createFromFunction(BiFunction<ServiceReference<S>, S, T> function) {

		return b -> new ServiceTrackerCustomizer<S, T>() {

			@Override
			public T addingService(ServiceReference<S> serviceReference) {
				S service = b.getService(serviceReference);

				try {
					return function.apply(serviceReference, service);
				}
				catch (Exception exception) {
					b.ungetService(serviceReference);

					throw exception;
				}
			}

			@Override
			public void modifiedService(
				ServiceReference<S> serviceReference, T t) {

				removedService(serviceReference, t);

				addingService(serviceReference);
			}

			@Override
			public void removedService(
				ServiceReference<S> serviceReference, T t) {

				b.ungetService(serviceReference);
			}

		};
	}

	public static <S>
		ServiceTrackerCustomizer<S, ServiceReferenceServiceTuple<S, S>>
			serviceReferenceServiceTuple(final BundleContext bundleContext) {

		return new ServiceTrackerCustomizer
			<S, ServiceReferenceServiceTuple<S, S>>() {

			@Override
			public ServiceReferenceServiceTuple<S, S> addingService(
				ServiceReference<S> serviceReference) {

				S service = bundleContext.getService(serviceReference);

				if (service == null) {
					return null;
				}

				return new ServiceReferenceServiceTuple<>(
					serviceReference, service);
			}

			@Override
			public void modifiedService(
				ServiceReference<S> serviceReference,
				ServiceReferenceServiceTuple<S, S>
					serviceReferenceServiceTuple) {
			}

			@Override
			public void removedService(
				ServiceReference<S> serviceReference,
				ServiceReferenceServiceTuple<S, S>
					serviceReferenceServiceTuple) {

				bundleContext.ungetService(serviceReference);
			}

		};
	}

	public static <S> ServiceTrackerCustomizer<S, ServiceWrapper<S>>
		serviceWrapper(BundleContext bundleContext) {

		return new ServiceTrackerCustomizer<S, ServiceWrapper<S>>() {

			@Override
			public ServiceWrapper<S> addingService(
				ServiceReference<S> serviceReference) {

				S service = bundleContext.getService(serviceReference);

				if (service == null) {
					return null;
				}

				try {
					return new ServiceWrapperImpl<>(serviceReference, service);
				}
				catch (Throwable throwable) {
					bundleContext.ungetService(serviceReference);

					throw throwable;
				}
			}

			@Override
			public void modifiedService(
				ServiceReference<S> serviceReference,
				ServiceWrapper<S> serviceWrapper) {

				ServiceWrapperImpl<S> serviceWrapperImpl =
					(ServiceWrapperImpl<S>)serviceWrapper;

				serviceWrapperImpl._resetProperties();
			}

			@Override
			public void removedService(
				ServiceReference<S> serviceReference,
				ServiceWrapper<S> serviceWrapper) {

				bundleContext.ungetService(serviceReference);
			}

		};
	}

	public interface ServiceWrapper<S> {

		public Map<String, Object> getProperties();

		public S getService();

	}

	private static class ServiceWrapperImpl<S> implements ServiceWrapper<S> {

		@Override
		public Map<String, Object> getProperties() {
			Map<String, Object> properties = _properties;

			if (properties == null) {
				properties = new HashMap<>();

				String[] propertyKeys = _serviceReference.getPropertyKeys();

				for (String propertyKey : propertyKeys) {
					properties.put(
						propertyKey,
						_serviceReference.getProperty(propertyKey));
				}

				_properties = properties;
			}

			return properties;
		}

		@Override
		public S getService() {
			return _service;
		}

		private ServiceWrapperImpl(
			ServiceReference<S> serviceReference, S service) {

			_serviceReference = serviceReference;
			_service = service;
		}

		private void _resetProperties() {
			_properties = null;
		}

		private volatile Map<String, Object> _properties;
		private final S _service;
		private final ServiceReference<S> _serviceReference;

	}

}