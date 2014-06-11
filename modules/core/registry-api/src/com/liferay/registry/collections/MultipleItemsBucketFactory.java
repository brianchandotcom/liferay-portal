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
import java.util.List;

/**
 * @author Carlos Sierra Andrés
 */
public class MultipleItemsBucketFactory<S>
	implements ServiceTrackerMapBucketFactory<S, List<S>> {

	public MultipleItemsBucketFactory(
		Comparator<ServiceReference<S>> comparator) {

		_comparator = comparator;
	}

	@Override
	public ServiceTrackerMapImpl.Bucket<S, List<S>> create() {

		return new ListBucket();
	}

	private final Comparator<ServiceReference<S>> _comparator;

	private class ListBucket
		implements ServiceTrackerMapImpl.Bucket<S, List<S>> {

		ListBucket() {

		}

		@Override
		public List<S> getContent() {
			return null;
		}

		@Override
		public boolean isDisposable() {
			return false;
		}

		@Override
		public synchronized void remove(ServiceReference<S> serviceReference) {

		}

		@Override
		public synchronized void store(ServiceReference<S> serviceReference) {

		}

	}

}