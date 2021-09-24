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

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Raymond Augé
 */
@ProviderType
public interface Registry {

	public <T> ServiceReference<T>[] getAllServiceReferences(
			String className, String filterString)
		throws Exception;

	public <T> T getService(ServiceReference<T> serviceReference);

	public <T> ServiceReference<T> getServiceReference(Class<T> clazz);

	public <T> ServiceReference<T> getServiceReference(String className);

	public <T> boolean ungetService(ServiceReference<T> serviceReference);

}