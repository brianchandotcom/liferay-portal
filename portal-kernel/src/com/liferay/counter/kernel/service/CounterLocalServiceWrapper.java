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

package com.liferay.counter.kernel.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CounterLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see CounterLocalService
 * @generated
 */
public class CounterLocalServiceWrapper
	implements CounterLocalService, ServiceWrapper<CounterLocalService> {

	public CounterLocalServiceWrapper(CounterLocalService counterLocalService) {
		_counterLocalService = counterLocalService;
	}

	@Override
	public java.util.List<String> getNames() {
		return _counterLocalService.getNames();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _counterLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public long increment() {
		return _counterLocalService.increment();
	}

	@Override
	public long increment(String name) {
		return _counterLocalService.increment(name);
	}

	@Override
	public long increment(String name, int size) {
		return _counterLocalService.increment(name, size);
	}

	@Override
	public void rename(String oldName, String newName) {
		_counterLocalService.rename(oldName, newName);
	}

	@Override
	public void reset(String name) {
		_counterLocalService.reset(name);
	}

	@Override
	public void reset(String name, long size) {
		_counterLocalService.reset(name, size);
	}

	@Override
	public CounterLocalService getWrappedService() {
		return _counterLocalService;
	}

	@Override
	public void setWrappedService(CounterLocalService counterLocalService) {
		_counterLocalService = counterLocalService;
	}

	private CounterLocalService _counterLocalService;

}