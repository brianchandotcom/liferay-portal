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

package com.liferay.registry;

import java.util.SortedMap;

/**
 * @author Raymond Augé
 */
public interface ServiceTracker<S, T> {

	public T addingService(ServiceReference<S> serviceReference);

	public void close();

	@Override
	public boolean equals(Object object);

	public T getService();

	public T getService(ServiceReference<S> serviceReference);

	public ServiceReference<S> getServiceReference();

	public ServiceReference<S>[] getServiceReferences();

	public Object[] getServices();

	public T[] getServices(T[] services);

	public SortedMap<ServiceReference<S>, T> getTrackedServiceReferences();

	public int getUpdateMarker();

	@Override
	public int hashCode();

	public boolean isEmpty();

	public void modifiedService(
		ServiceReference<S> serviceReference, T service);

	public void open();

	public void open(boolean trackAllServices);

	public void remove(ServiceReference<S> serviceReference);

	public void removedService(ServiceReference<S> serviceReference, T service);

	public int size();

	@Override
	public String toString();

	public T waitForService(long timeout) throws InterruptedException;

}