/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.counter.NoSuchCounterException;
import com.liferay.counter.model.Counter;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class CounterPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (CounterPersistence)PortalBeanLocatorUtil.locate(CounterPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		String pk = randomString();

		Counter counter = _persistence.create(pk);

		assertNotNull(counter);

		assertEquals(counter.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		Counter newCounter = addCounter();

		_persistence.remove(newCounter);

		Counter existingCounter = _persistence.fetchByPrimaryKey(newCounter.getPrimaryKey());

		assertNull(existingCounter);
	}

	public void testUpdateNew() throws Exception {
		addCounter();
	}

	public void testUpdateExisting() throws Exception {
		String pk = randomString();

		Counter newCounter = _persistence.create(pk);

		newCounter.setCurrentId(nextLong());

		_persistence.update(newCounter, false);

		Counter existingCounter = _persistence.findByPrimaryKey(newCounter.getPrimaryKey());

		assertEquals(existingCounter.getName(), newCounter.getName());
		assertEquals(existingCounter.getCurrentId(), newCounter.getCurrentId());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		Counter newCounter = addCounter();

		Counter existingCounter = _persistence.findByPrimaryKey(newCounter.getPrimaryKey());

		assertEquals(existingCounter, newCounter);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		String pk = randomString();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchCounterException");
		}
		catch (NoSuchCounterException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		Counter newCounter = addCounter();

		Counter existingCounter = _persistence.fetchByPrimaryKey(newCounter.getPrimaryKey());

		assertEquals(existingCounter, newCounter);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		String pk = randomString();

		Counter missingCounter = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingCounter);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Counter newCounter = addCounter();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Counter.class,
				Counter.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("name", newCounter.getName()));

		List<Counter> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Counter existingCounter = result.get(0);

		assertEquals(existingCounter, newCounter);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Counter.class,
				Counter.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("name", randomString()));

		List<Counter> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		Counter newCounter = addCounter();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Counter.class,
				Counter.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("name"));

		Object newName = newCounter.getName();

		dynamicQuery.add(RestrictionsFactoryUtil.in("name",
				new Object[] { newName }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingName = result.get(0);

		assertEquals(existingName, newName);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Counter.class,
				Counter.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("name"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("name",
				new Object[] { randomString() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected Counter addCounter() throws Exception {
		String pk = randomString();

		Counter counter = _persistence.create(pk);

		counter.setCurrentId(nextLong());

		_persistence.update(counter, false);

		return counter;
	}

	private CounterPersistence _persistence;
}