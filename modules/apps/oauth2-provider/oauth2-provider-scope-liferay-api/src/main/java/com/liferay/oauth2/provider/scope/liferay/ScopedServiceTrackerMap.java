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

package com.liferay.oauth2.provider.scope.liferay;

/**
 * Represents a ServiceTrackerMap that also checks for company specialization.
 * A service is the best match if it is registered for both the company and the
 * key. If not such a service exists the best next candidate would be if it
 * matches only the key. If no service is registered for the key then a service
 * registered for the company only will be searched.
 *
 * @author Carlos Sierra Andrés
 * @see com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap
 * @review
 */
public interface ScopedServiceTrackerMap<T> {

	public void close();

	public T getService(long companyId, String key);

}