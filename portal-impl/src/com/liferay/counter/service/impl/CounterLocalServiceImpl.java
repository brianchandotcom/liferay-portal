/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.counter.service.impl;

import com.liferay.counter.service.CounterLocalService;
import com.liferay.counter.service.base.CounterLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Edward Han
 */
public class CounterLocalServiceImpl
	extends CounterLocalServiceBaseImpl implements CounterLocalService {

	@Override
	public List<String> getNames() throws SystemException {
		return counterFinder.getNames();
	}

	@Override
	@Transactional(
		isolation = Isolation.COUNTER, propagation = Propagation.REQUIRES_NEW)
	public long increment() throws SystemException {
		return counterFinder.increment();
	}

	@Override
	@Transactional(
		isolation = Isolation.COUNTER, propagation = Propagation.REQUIRES_NEW)
	public long increment(String name) throws SystemException {
		return counterFinder.increment(name);
	}

	@Override
	@Transactional(
		isolation = Isolation.COUNTER, propagation = Propagation.REQUIRES_NEW)
	public long increment(String name, int size) throws SystemException {
		return counterFinder.increment(name, size);
	}

	@Override
	@Transactional(
		isolation = Isolation.COUNTER, propagation = Propagation.REQUIRES_NEW)
	public void rename(String oldName, String newName) throws SystemException {
		counterFinder.rename(oldName, newName);
	}

	@Override
	@Transactional(
		isolation = Isolation.COUNTER, propagation = Propagation.REQUIRES_NEW)
	public void reset(String name) throws SystemException {
		counterFinder.reset(name);
	}

	@Override
	@Transactional(
		isolation = Isolation.COUNTER, propagation = Propagation.REQUIRES_NEW)
	public void reset(String name, long size) throws SystemException {
		counterFinder.reset(name, size);
	}

}