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
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.ExecutionTestListeners;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.PersistenceEnvConfigTestListener;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
@ExecutionTestListeners(listeners =  {
	PersistenceEnvConfigTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class CounterPersistenceTest {
	@Before
	public void setUp() throws Exception {
		_persistence = (CounterPersistence)PortalBeanLocatorUtil.locate(CounterPersistence.class.getName());
	}

	@Test
	public void testCreate() throws Exception {
		String pk = ServiceTestUtil.randomString();

		Counter counter = _persistence.create(pk);

		Assert.assertNotNull(counter);

		Assert.assertEquals(counter.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		Counter newCounter = addCounter();

		_persistence.remove(newCounter);

		Counter existingCounter = _persistence.fetchByPrimaryKey(newCounter.getPrimaryKey());

		Assert.assertNull(existingCounter);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCounter();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		String pk = ServiceTestUtil.randomString();

		Counter newCounter = _persistence.create(pk);

		newCounter.setCurrentId(ServiceTestUtil.nextLong());

		_persistence.update(newCounter, false);

		Counter existingCounter = _persistence.findByPrimaryKey(newCounter.getPrimaryKey());

		Assert.assertEquals(existingCounter.getName(), newCounter.getName());
		Assert.assertEquals(existingCounter.getCurrentId(),
			newCounter.getCurrentId());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		Counter newCounter = addCounter();

		Counter existingCounter = _persistence.findByPrimaryKey(newCounter.getPrimaryKey());

		Assert.assertEquals(existingCounter, newCounter);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		String pk = ServiceTestUtil.randomString();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchCounterException");
		}
		catch (NoSuchCounterException nsee) {
		}
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		Counter newCounter = addCounter();

		Counter existingCounter = _persistence.fetchByPrimaryKey(newCounter.getPrimaryKey());

		Assert.assertEquals(existingCounter, newCounter);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		String pk = ServiceTestUtil.randomString();

		Counter missingCounter = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCounter);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Counter newCounter = addCounter();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Counter.class,
				Counter.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("name", newCounter.getName()));

		List<Counter> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Counter existingCounter = result.get(0);

		Assert.assertEquals(existingCounter, newCounter);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Counter.class,
				Counter.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("name",
				ServiceTestUtil.randomString()));

		List<Counter> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
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

		Assert.assertEquals(1, result.size());

		Object existingName = result.get(0);

		Assert.assertEquals(existingName, newName);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Counter.class,
				Counter.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("name"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("name",
				new Object[] { ServiceTestUtil.randomString() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected Counter addCounter() throws Exception {
		String pk = ServiceTestUtil.randomString();

		Counter counter = _persistence.create(pk);

		counter.setCurrentId(ServiceTestUtil.nextLong());

		_persistence.update(counter, false);

		return counter;
	}

	private CounterPersistence _persistence;
}