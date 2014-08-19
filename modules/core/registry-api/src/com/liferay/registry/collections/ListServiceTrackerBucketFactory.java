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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Carlos Sierra Andrés
 */
public class ListServiceTrackerBucketFactory<S>
	implements ServiceTrackerBucketFactory<S, List<S>> {

	public ListServiceTrackerBucketFactory(
		Comparator<ServiceReference<S>> comparator) {

		_comparator = comparator;
	}

	@Override
	public ServiceTrackerBucket<S, List<S>> create() {
		return new ListServiceTrackerBucket();
	}

	private final Comparator<ServiceReference<S>> _comparator;

	private class ListServiceTrackerBucket
		implements ServiceTrackerBucket<S, List<S>> {

		@Override
		public List<S> getContent() {
			return _services;
		}

		@Override
		public synchronized boolean isDisposable() {
			return _serviceReferences.isEmpty();
		}

		@Override
		public synchronized void remove(ServiceReference<S> serviceReference) {
			_serviceReferences.remove(serviceReference);

			rebuild();
		}

		@Override
		public synchronized void store(ServiceReference<S> serviceReference) {
			_serviceReferences.add(serviceReference);

			rebuild();
		}

		protected void rebuild() {
			Registry registry = RegistryUtil.getRegistry();

			try {
				_services = new ArrayList<S>(_serviceReferences.size());

				for (
					ServiceReference<S> serviceReference : _serviceReferences) {

					_services.add(registry.getService(serviceReference));
				}

				_services = Collections.unmodifiableList(_services);
			}
			catch (IllegalStateException ise) {
				_serviceReferences.clear();

				_services = new ArrayList<S>();
			}
		}

		private Set<ServiceReference<S>> _serviceReferences =
			new TreeSet<ServiceReference<S>>(_comparator);
		private List<S> _services = new ArrayList<S>();

	}

}