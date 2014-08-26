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

import com.liferay.registry.ServiceReference;
import com.liferay.registry.collections.internal.ServiceReferenceServiceTuple;
import com.liferay.registry.collections.internal.ServiceReferenceServiceTupleComparator;

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

	public ListServiceTrackerBucketFactory() {
		_comparator = Collections.reverseOrder();
	}

	public ListServiceTrackerBucketFactory(
		Comparator<ServiceReference<S>> comparator) {

		_comparator = comparator;
	}

	@Override
	public ServiceTrackerBucket<S, List<S>> create() {
		return new ListServiceTrackerBucket();
	}

	private Comparator<ServiceReference<S>> _comparator;

	private class ListServiceTrackerBucket
		implements ServiceTrackerBucket<S, List<S>> {

		@Override
		public List<S> getContent() {
			return _services;
		}

		@Override
		public synchronized boolean isDisposable() {
			return _serviceTuples.isEmpty();
		}

		@Override
		public synchronized void remove(ServiceReferenceServiceTuple<S> tuple) {
			_serviceTuples.remove(tuple);

			rebuild();
		}

		@Override
		public synchronized void store(ServiceReferenceServiceTuple<S> tuple) {
			_serviceTuples.add(tuple);

			rebuild();
		}

		protected void rebuild() {
			_services = new ArrayList<S>(_serviceTuples.size());

			for (
				ServiceReferenceServiceTuple<S> tuple : _serviceTuples) {

				_services.add(tuple.getService());
			}

			_services = Collections.unmodifiableList(_services);
		}

		private ListServiceTrackerBucket() {
			_serviceTuples = new TreeSet<ServiceReferenceServiceTuple<S>>(
				new ServiceReferenceServiceTupleComparator<S>(_comparator));
		}

		private List<S> _services = new ArrayList<S>();
		private Set<ServiceReferenceServiceTuple<S>> _serviceTuples;

	}

}