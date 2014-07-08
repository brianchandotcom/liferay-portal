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

import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @author Carlos Sierra Andrés
 */
public class ObjectServiceTrackerBucketFactory<S>
	implements ServiceTrackerBucketFactory<S, S> {

	public ObjectServiceTrackerBucketFactory(
		Comparator<ServiceReference<S>> comparator) {

		_comparator = comparator;
	}

	@Override
	public ServiceTrackerBucket<S, S> create() {
		return new SingleBucket();
	}

	private Comparator<ServiceReference<S>> _comparator;

	private class SingleBucket implements ServiceTrackerBucket<S, S> {

		public SingleBucket() {
			_serviceReferences = new PriorityQueue<ServiceReference<S>>(
				1, _comparator);

			_service = null;
		}

		@Override
		public S getContent() {
			return _service;
		}

		@Override
		public synchronized boolean isDisposable() {
			return _serviceReferences.isEmpty();
		}

		@Override
		public synchronized void remove(ServiceReference<S> serviceReference) {
			Registry registry = RegistryUtil.getRegistry();

			_serviceReferences.remove(serviceReference);

			ServiceReference<S> headServiceReference =
				_serviceReferences.peek();

			if (headServiceReference != null) {
				try {
					_service = registry.getService(headServiceReference);
				}
				catch (IllegalStateException ise) {
					_service = null;

					_serviceReferences.clear();
				}
			}
			else {
				_service = null;
			}
		}

		@Override
		public synchronized void store(ServiceReference<S> serviceReference) {
			Registry registry = RegistryUtil.getRegistry();

			_serviceReferences.add(serviceReference);

			_service = registry.getService(_serviceReferences.peek());
		}

		private S _service;
		private PriorityQueue<ServiceReference<S>> _serviceReferences;

	}

}