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

package com.liferay.osgi.service.tracker.collections.internal.map;

import com.liferay.osgi.service.tracker.collections.internal.common.ServiceReferenceServiceTuple;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerBucket;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerBucketFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.osgi.framework.ServiceReference;

/**
 * @author Carlos Sierra Andrés
 */
public class MultiValueServiceTrackerBucketFactory<SR, TS>
	implements ServiceTrackerBucketFactory<SR, TS, List<TS>> {

	public MultiValueServiceTrackerBucketFactory() {
		_comparator = Collections.reverseOrder();
	}

	public MultiValueServiceTrackerBucketFactory(
		Comparator<ServiceReference<SR>> comparator) {

		_comparator = comparator;
	}

	@Override
	public ServiceTrackerBucket<SR, TS, List<TS>> create() {
		return new ListServiceTrackerBucket();
	}

	private final Comparator<ServiceReference<SR>> _comparator;

	private class ListServiceTrackerBucket
		implements ServiceTrackerBucket<SR, TS, List<TS>> {

		@Override
		public List<TS> getContent() {
			return _services;
		}

		@Override
		public synchronized boolean isDisposable() {
			return _serviceReferenceServiceTuples.isEmpty();
		}

		@Override
		public synchronized void remove(
			ServiceReferenceServiceTuple<SR, TS>
				serviceReferenceServiceTuple) {

			_serviceReferenceServiceTuples.remove(serviceReferenceServiceTuple);

			rebuild();
		}

		@Override
		public synchronized void store(
			ServiceReferenceServiceTuple<SR, TS>
				serviceReferenceServiceTuple) {

			_serviceReferenceServiceTuples.add(serviceReferenceServiceTuple);

			rebuild();
		}

		protected void rebuild() {
			_services = new ArrayList<>(_serviceReferenceServiceTuples.size());

			for (
				ServiceReferenceServiceTuple<SR, TS>
					serviceReferenceServiceTuple :
						_serviceReferenceServiceTuples) {

				_services.add(serviceReferenceServiceTuple.getService());
			}

			_services = Collections.unmodifiableList(_services);
		}

		private ListServiceTrackerBucket() {
			ServiceReferenceServiceTupleComparator<SR>
				serviceReferenceServiceTupleComparator =
					new ServiceReferenceServiceTupleComparator<>(_comparator);

			_serviceReferenceServiceTuples = new TreeSet<>(
				serviceReferenceServiceTupleComparator);
		}

		private final Set<ServiceReferenceServiceTuple<SR, TS>>
			_serviceReferenceServiceTuples;
		private List<TS> _services = new ArrayList<>();

	}

}