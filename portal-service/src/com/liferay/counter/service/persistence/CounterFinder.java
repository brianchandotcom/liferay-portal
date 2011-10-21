/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

/**
 * @author Brian Wing Shun Chan
 */
public interface CounterFinder {
	public void afterPropertiesSet();

	public java.util.List<java.lang.String> getNames()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.lang.String getRegistryName();

	public long increment()
		throws com.liferay.portal.kernel.exception.SystemException;

	public long increment(java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	public long increment(java.lang.String name, int size)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void invalidate();

	public void rename(java.lang.String oldName, java.lang.String newName)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void reset(java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void reset(java.lang.String name, long size)
		throws com.liferay.portal.kernel.exception.SystemException;
}