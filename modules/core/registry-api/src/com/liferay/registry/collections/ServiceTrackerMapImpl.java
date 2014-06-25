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

package com.liferay.registry.collections;

import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Carlos Sierra Andrés
 */
public class ServiceTrackerMapImpl<K, S, R> implements ServiceTrackerMap<K, R> {

	public ServiceTrackerMapImpl(
		Class<S> clazz, String filterString,
		ServiceReferenceMapper<K> serviceReferenceMapper,
		ServiceTrackerBucketFactory<S, R> serviceTrackerMapBucketFactory) {

		_serviceReferenceMapper = serviceReferenceMapper;
		_serviceTrackerMapBucketFactory = serviceTrackerMapBucketFactory;

		Registry registry = RegistryUtil.getRegistry();

		if (filterString != null) {
			Filter filter = registry.getFilter(
				"(&(objectClass=" + clazz.getName() + ")" + filterString + ")");

			_serviceTracker = registry.trackServices(
				filter, new ServiceReferenceServiceTrackerCustomizer());
		}
		else {
			_serviceTracker = registry.trackServices(
				clazz, new ServiceReferenceServiceTrackerCustomizer());
		}
	}

	@Override
	public void close() {
		_serviceTracker.close();
	}

	@Override
	public R getService(K key) {
		ServiceTrackerBucket<S, R> serviceTrackerBucket =
			_serviceTrackerBuckets.get(key);

		if (serviceTrackerBucket == null) {
			return null;
		}

		return serviceTrackerBucket.getContent();
	}

	@Override
	public void open() {
		_serviceTracker.open();
	}

	private ServiceReferenceMapper<K> _serviceReferenceMapper;
	private ServiceTracker<S, ServiceReference<S>> _serviceTracker;
	private ConcurrentHashMap<K, ServiceTrackerBucket<S, R>>
		_serviceTrackerBuckets =
			new ConcurrentHashMap<K, ServiceTrackerBucket<S, R>>();
	private ServiceTrackerBucketFactory<S, R> _serviceTrackerMapBucketFactory;

	private class ServiceReferenceServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<S, ServiceReference<S>> {

		@Override
		public ServiceReference<S> addingService(
			final ServiceReference<S> serviceReference) {

			_serviceReferenceMapper.map(
				serviceReference,
				new ServiceReferenceMapper.Emitter<K>() {

					@Override
					public void emit(K key) {
						ServiceTrackerBucket<S, R> serviceTrackerBucket =
							_serviceTrackerBuckets.get(key);

						if (serviceTrackerBucket == null) {
							ServiceTrackerBucket<S, R> newServiceTrackerBucket =
								_serviceTrackerMapBucketFactory.create();

							serviceTrackerBucket =
								_serviceTrackerBuckets.putIfAbsent(
									key, newServiceTrackerBucket);

							if (serviceTrackerBucket == null) {
								serviceTrackerBucket = newServiceTrackerBucket;
							}
						}

						serviceTrackerBucket.store(serviceReference);
					}

				});

			return serviceReference;
		}

		@Override
		public void modifiedService(
			ServiceReference<S> serviceReference, ServiceReference<S> service) {

			removedService(service, service);

			addingService(serviceReference);
		}

		@Override
		public void removedService(
			final ServiceReference<S> serviceReference,
			ServiceReference<S> service) {

			_serviceReferenceMapper.map(
				serviceReference,
				new ServiceReferenceMapper.Emitter<K>() {

					@Override
					public void emit(K key) {
						ServiceTrackerBucket<S, R> serviceTrackerBucket =
							_serviceTrackerBuckets.get(key);

						if (serviceTrackerBucket == null) {
							return;
						}

						serviceTrackerBucket.remove(serviceReference);

						if (serviceTrackerBucket.isDisposable()) {
							_serviceTrackerBuckets.remove(key);
						}
					}

				});
		}
	}

}