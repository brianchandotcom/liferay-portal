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

package com.liferay.apio.architect.wiring.osgi.internal.service.tracker.map.listener;

import static com.liferay.apio.architect.wiring.osgi.internal.manager.cache.ManagerCache.INSTANCE;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapListener;

/**
 * Provides an implementation of a {@link ServiceTrackerMapListener} that clears
 * the {@link
 * com.liferay.apio.architect.wiring.osgi.internal.manager.cache.ManagerCache}
 * on every change.
 *
 * @author Alejandro Hernández
 */
public class ClearCacheServiceTrackerMapListener<T, U>
	implements ServiceTrackerMapListener<U, T, T> {

	@Override
	public void keyEmitted(
		ServiceTrackerMap<U, T> serviceTrackerMap, U s, T t1, T t2) {

		INSTANCE.clear();
	}

	@Override
	public void keyRemoved(
		ServiceTrackerMap<U, T> serviceTrackerMap, U s, T t1, T t2) {

		INSTANCE.clear();
	}

}