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

package com.liferay.portal.vulcan.internal.jackson.databind.ser;

import com.fasterxml.jackson.databind.ser.PropertyFilter;

import java.util.Set;

/**
 * @author Javier de Arcos
 */
public interface DynamicPropertyFilter extends PropertyFilter {

	public void addFilteredPropertyKeys(Set<String> filteredPropertyKeys);

	public void clearFilteredPropertyKeys();

}