/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.counter.service.impl;

import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.counter.service.base.CounterLocalServiceBaseImpl;
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
	public List<String> getNames() {
		return counterFinder.getNames();
	}

	@Override
	@Transactional(
		isolation = Isolation.COUNTER, propagation = Propagation.REQUIRES_NEW
	)
	public long increment() {
		return counterFinder.increment();
	}

	@Override
	@Transactional(
		isolation = Isolation.COUNTER, propagation = Propagation.REQUIRES_NEW
	)
	public long increment(String name) {
		return counterFinder.increment(name);
	}

	@Override
	@Transactional(
		isolation = Isolation.COUNTER, propagation = Propagation.REQUIRES_NEW
	)
	public long increment(String name, int size) {
		return counterFinder.increment(name, size);
	}

	@Override
	@Transactional(
		isolation = Isolation.COUNTER, propagation = Propagation.REQUIRES_NEW
	)
	public void rename(String oldName, String newName) {
		counterFinder.rename(oldName, newName);
	}

	@Override
	@Transactional(
		isolation = Isolation.COUNTER, propagation = Propagation.REQUIRES_NEW
	)
	public void reset(String name) {
		counterFinder.reset(name);
	}

	@Override
	@Transactional(
		isolation = Isolation.COUNTER, propagation = Propagation.REQUIRES_NEW
	)
	public void reset(String name, long size) {
		counterFinder.reset(name, size);
	}

}