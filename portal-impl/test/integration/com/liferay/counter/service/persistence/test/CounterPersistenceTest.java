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

package com.liferay.counter.service.persistence.test;

import com.liferay.counter.NoSuchCounterException;
import com.liferay.counter.model.Counter;
import com.liferay.counter.service.persistence.CounterPersistence;
import com.liferay.counter.service.persistence.CounterUtil;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @generated
 */
public class CounterPersistenceTest {
	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = CounterUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<Counter> iterator = _counters.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		String pk = RandomTestUtil.randomString();

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
		String pk = RandomTestUtil.randomString();

		Counter newCounter = _persistence.create(pk);

		newCounter.setCurrentId(RandomTestUtil.nextLong());

		_counters.add(_persistence.update(newCounter));

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

	@Test(expected = NoSuchCounterException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		String pk = RandomTestUtil.randomString();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<Counter> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("Counter", "name", true,
			"currentId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		Counter newCounter = addCounter();

		Counter existingCounter = _persistence.fetchByPrimaryKey(newCounter.getPrimaryKey());

		Assert.assertEquals(existingCounter, newCounter);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		String pk = RandomTestUtil.randomString();

		Counter missingCounter = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCounter);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		Counter newCounter1 = addCounter();
		Counter newCounter2 = addCounter();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCounter1.getPrimaryKey());
		primaryKeys.add(newCounter2.getPrimaryKey());

		Map<Serializable, Counter> counters = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, counters.size());
		Assert.assertEquals(newCounter1,
			counters.get(newCounter1.getPrimaryKey()));
		Assert.assertEquals(newCounter2,
			counters.get(newCounter2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		String pk1 = RandomTestUtil.randomString();

		String pk2 = RandomTestUtil.randomString();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, Counter> counters = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(counters.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		Counter newCounter = addCounter();

		String pk = RandomTestUtil.randomString();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCounter.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, Counter> counters = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, counters.size());
		Assert.assertEquals(newCounter, counters.get(newCounter.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, Counter> counters = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(counters.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		Counter newCounter = addCounter();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCounter.getPrimaryKey());

		Map<Serializable, Counter> counters = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, counters.size());
		Assert.assertEquals(newCounter, counters.get(newCounter.getPrimaryKey()));
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Counter newCounter = addCounter();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Counter.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("name", newCounter.getName()));

		List<Counter> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Counter existingCounter = result.get(0);

		Assert.assertEquals(existingCounter, newCounter);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Counter.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("name",
				RandomTestUtil.randomString()));

		List<Counter> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		Counter newCounter = addCounter();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Counter.class,
				_dynamicQueryClassLoader);

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
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("name"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("name",
				new Object[] { RandomTestUtil.randomString() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected Counter addCounter() throws Exception {
		String pk = RandomTestUtil.randomString();

		Counter counter = _persistence.create(pk);

		counter.setCurrentId(RandomTestUtil.nextLong());

		_counters.add(_persistence.update(counter));

		return counter;
	}

	private List<Counter> _counters = new ArrayList<Counter>();
	private CounterPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}