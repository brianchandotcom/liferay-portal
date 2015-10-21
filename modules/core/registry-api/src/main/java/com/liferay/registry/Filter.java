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

import java.util.Map;

/**
 * @author Raymond Augé
 */
public interface Filter {

	@Override
	public boolean equals(Object object);

	@Override
	public int hashCode();

	public boolean matches(Map<String, Object> properties);

	public boolean matches(ServiceReference<?> serviceReference);

	public boolean matchesCase(Map<String, Object> properties);

	@Override
	public String toString();

}