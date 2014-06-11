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

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @author Carlos Sierra Andrés
 */
public class SingleItemBucketFactory<S>
	implements ServiceTrackerMapBucketFactory<S, S> {

	public SingleItemBucketFactory(Comparator<ServiceReference<S>> comparator) {
		_comparator = comparator;
	}

	@Override
	public ServiceTrackerMapImpl.Bucket<S, S> create() {

		return new SingleBucket();
	}

	private final Comparator<ServiceReference<S>> _comparator;

	private class SingleBucket implements ServiceTrackerMapImpl.Bucket<S, S> {

		@Override
		public S getContent() {
			return null;
		}

		@Override
		public synchronized boolean isDisposable() {
			return false;
		}

		@Override
		public synchronized void remove(ServiceReference<S> serviceReference) {

		}

		public SingleBucket() {
			_queue = new PriorityQueue<ServiceReference<S>>(1, _comparator);

		}

		public synchronized void store(ServiceReference<S> serviceReference) {

		}

		private PriorityQueue<ServiceReference<S>> _queue;

	}

}