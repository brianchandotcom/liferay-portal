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

package com.liferay.counter.service.persistence;

import aQute.bnd.annotation.ProviderType;

/**
 * @generated
 */
@ProviderType
public interface CounterFinder {
	public void afterPropertiesSet();

	public java.util.List<java.lang.String> getNames();

	public java.lang.String getRegistryName();

	public long increment();

	public long increment(java.lang.String name);

	public long increment(java.lang.String name, int size);

	public void invalidate();

	public void rename(java.lang.String oldName, java.lang.String newName);

	public void reset(java.lang.String name);

	public void reset(java.lang.String name, long size);
}