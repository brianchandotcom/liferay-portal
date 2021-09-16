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

import com.liferay.portal.kernel.module.util.SystemBundleUtil;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListSet;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Iván Zaera
 */
public class BaseDisplayContextProvider<T extends DisplayContextFactory>
	implements DisplayContextProvider {

	public BaseDisplayContextProvider(Class<T> displayContextFactoryClass) {
		_serviceTracker = new ServiceTracker<>(
			_bundleContext,
			SystemBundleUtil.createFilter(
				"(objectClass=" + displayContextFactoryClass.getName() + ")"),
			new DisplayContextFactoryServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	public void destroy() {
		_serviceTracker.close();
	}

	public Iterable<T> getDisplayContextFactories() {
		return new DisplayContextFactoriesIterable<>(
			_displayContextFactoryReferences);
	}

	private final BundleContext _bundleContext =
		SystemBundleUtil.getBundleContext();
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
			T displayContextFactory = _bundleContext.getService(
				serviceReference);

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

			_bundleContext.ungetService(serviceReference);
		}

	}

}