/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osgi.service.tracker.collections.internal.map;

import com.liferay.osgi.service.tracker.collections.ServiceReferenceServiceTuple;
import com.liferay.osgi.service.tracker.collections.internal.ServiceReferenceServiceTupleComparator;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerBucket;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerBucketFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
			ServiceReferenceServiceTuple<SR, TS> serviceReferenceServiceTuple) {

			_serviceReferenceServiceTuples.remove(serviceReferenceServiceTuple);

			_rebuild();
		}

		@Override
		public synchronized void store(
			ServiceReferenceServiceTuple<SR, TS> serviceReferenceServiceTuple) {

			int index = Collections.binarySearch(
				_serviceReferenceServiceTuples, serviceReferenceServiceTuple,
				_serviceReferenceServiceTupleComparator);

			if (index < 0) {
				index = -index - 1;
			}

			_serviceReferenceServiceTuples.add(
				index, serviceReferenceServiceTuple);

			_rebuild();
		}

		private void _rebuild() {
			_services = new ArrayList<>(_serviceReferenceServiceTuples.size());

			for (ServiceReferenceServiceTuple<SR, TS>
					serviceReferenceServiceTuple :
						_serviceReferenceServiceTuples) {

				_services.add(serviceReferenceServiceTuple.getService());
			}

			_services = Collections.unmodifiableList(_services);
		}

		private final ServiceReferenceServiceTupleComparator<SR>
			_serviceReferenceServiceTupleComparator =
				new ServiceReferenceServiceTupleComparator<>(_comparator);
		private final List<ServiceReferenceServiceTuple<SR, TS>>
			_serviceReferenceServiceTuples = new ArrayList<>();
		private List<TS> _services = new ArrayList<>();

	}

}