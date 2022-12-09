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

package com.liferay.osgi.service.tracker.collections.map;

/**
 * Represents a {@code ServiceTrackerMap} that also checks for portal instance
 * specialization. A best match is registered for both the portal instance and
 * the key. If no such service exists, the next best matches only the key. If no
 * service is registered for the key, a service registered for the portal
 * instance matches.
 *
 * @author Carlos Sierra Andrés
 * @see    com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap
 */
public interface ScopedServiceTrackerMap<T> {

	public void close();

	public T getService(long companyId, String key);

}