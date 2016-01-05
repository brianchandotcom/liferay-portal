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

package com.liferay.portal.kernel.display.context;

import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @author Iván Zaera
 */
public class BaseDisplayContextProvider<T extends DisplayContextFactory>
	implements DisplayContextProvider {

	public BaseDisplayContextProvider(Class<T> displayContextFactoryClass) {
		Registry registry = RegistryUtil.getRegistry();

		Filter filter = registry.getFilter(
			"(objectClass=" + displayContextFactoryClass.getName() + ")");

		_serviceTracker = registry.trackServices(
			filter, new DisplayContextFactoryServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	public void destroy() {
		_serviceTracker.close();
	}

	public Iterable<T> getDisplayContextFactories() {
		return new DisplayContextFactoriesIterable<>(
			_displayContextFactoryReferences);
	}

	private final SortedSet<DisplayContextFactoryReference<T>>
		_displayContextFactoryReferences = new ConcurrentSkipListSet<>();
	private final ConcurrentMap<T, DisplayContextFactoryReference<T>>
		_displayContextFactoryReferencesMap = new ConcurrentHashMap<>();
	private final ServiceTracker<T, T> _serviceTracker;

	private static class DisplayContextFactoriesIterable
		<T extends DisplayContextFactory>
			implements Iterable<T>, Iterator<T> {

		public DisplayContextFactoriesIterable(
			Iterable<DisplayContextFactoryReference<T>> iterable) {

			_iterator = iterable.iterator();
		}

		@Override
		public boolean hasNext() {
			return _iterator.hasNext();
		}

		@Override
		public Iterator<T> iterator() {
			return this;
		}

		@Override
		public T next() {
			DisplayContextFactoryReference<T> displayContextFactoryReference =
				_iterator.next();

			return displayContextFactoryReference.getDisplayContextFactory();
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("Iterator is read-only");
		}

		private final Iterator<DisplayContextFactoryReference<T>> _iterator;

	}

	private class DisplayContextFactoryServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<T, T> {

		@Override
		public T addingService(ServiceReference<T> serviceReference) {
			Registry registry = RegistryUtil.getRegistry();

			T displayContextFactory = registry.getService(serviceReference);

			DisplayContextFactoryReference<T> displayContextFactoryReference =
				new DisplayContextFactoryReference<>(
					displayContextFactory, serviceReference);

			_displayContextFactoryReferences.add(
				displayContextFactoryReference);

			_displayContextFactoryReferencesMap.put(
				displayContextFactory, displayContextFactoryReference);

			return displayContextFactory;
		}

		@Override
		public void modifiedService(
			ServiceReference<T> serviceReference, T displayContextFactory) {

			DisplayContextFactoryReference<T> displayContextFactoryReference =
				_displayContextFactoryReferencesMap.get(displayContextFactory);

			removedService(
				displayContextFactoryReference.getServiceReference(),
				displayContextFactoryReference.getDisplayContextFactory());

			addingService(serviceReference);
		}

		@Override
		public void removedService(
			ServiceReference<T> serviceReference, T displayContextFactory) {

			DisplayContextFactoryReference<T> displayContextFactoryReference =
				_displayContextFactoryReferencesMap.remove(
					displayContextFactory);

			_displayContextFactoryReferences.remove(
				displayContextFactoryReference);
		}

	}

}