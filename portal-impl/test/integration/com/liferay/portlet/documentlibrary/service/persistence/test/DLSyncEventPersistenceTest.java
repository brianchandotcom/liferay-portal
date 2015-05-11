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

package com.liferay.portlet.documentlibrary.service.persistence.test;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;

import com.liferay.portlet.documentlibrary.NoSuchSyncEventException;
import com.liferay.portlet.documentlibrary.model.DLSyncEvent;
import com.liferay.portlet.documentlibrary.service.DLSyncEventLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.persistence.DLSyncEventPersistence;
import com.liferay.portlet.documentlibrary.service.persistence.DLSyncEventUtil;

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
public class DLSyncEventPersistenceTest {
	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = DLSyncEventUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<DLSyncEvent> iterator = _dlSyncEvents.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DLSyncEvent dlSyncEvent = _persistence.create(pk);

		Assert.assertNotNull(dlSyncEvent);

		Assert.assertEquals(dlSyncEvent.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		DLSyncEvent newDLSyncEvent = addDLSyncEvent();

		_persistence.remove(newDLSyncEvent);

		DLSyncEvent existingDLSyncEvent = _persistence.fetchByPrimaryKey(newDLSyncEvent.getPrimaryKey());

		Assert.assertNull(existingDLSyncEvent);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addDLSyncEvent();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DLSyncEvent newDLSyncEvent = _persistence.create(pk);

		newDLSyncEvent.setModifiedTime(RandomTestUtil.nextLong());

		newDLSyncEvent.setEvent(RandomTestUtil.randomString());

		newDLSyncEvent.setType(RandomTestUtil.randomString());

		newDLSyncEvent.setTypePK(RandomTestUtil.nextLong());

		_dlSyncEvents.add(_persistence.update(newDLSyncEvent));

		DLSyncEvent existingDLSyncEvent = _persistence.findByPrimaryKey(newDLSyncEvent.getPrimaryKey());

		Assert.assertEquals(existingDLSyncEvent.getSyncEventId(),
			newDLSyncEvent.getSyncEventId());
		Assert.assertEquals(existingDLSyncEvent.getModifiedTime(),
			newDLSyncEvent.getModifiedTime());
		Assert.assertEquals(existingDLSyncEvent.getEvent(),
			newDLSyncEvent.getEvent());
		Assert.assertEquals(existingDLSyncEvent.getType(),
			newDLSyncEvent.getType());
		Assert.assertEquals(existingDLSyncEvent.getTypePK(),
			newDLSyncEvent.getTypePK());
	}

	@Test
	public void testCountByModifiedTime() throws Exception {
		_persistence.countByModifiedTime(RandomTestUtil.nextLong());

		_persistence.countByModifiedTime(0L);
	}

	@Test
	public void testCountByTypePK() throws Exception {
		_persistence.countByTypePK(RandomTestUtil.nextLong());

		_persistence.countByTypePK(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		DLSyncEvent newDLSyncEvent = addDLSyncEvent();

		DLSyncEvent existingDLSyncEvent = _persistence.findByPrimaryKey(newDLSyncEvent.getPrimaryKey());

		Assert.assertEquals(existingDLSyncEvent, newDLSyncEvent);
	}

	@Test(expected = NoSuchSyncEventException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<DLSyncEvent> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("DLSyncEvent",
			"syncEventId", true, "modifiedTime", true, "event", true, "type",
			true, "typePK", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		DLSyncEvent newDLSyncEvent = addDLSyncEvent();

		DLSyncEvent existingDLSyncEvent = _persistence.fetchByPrimaryKey(newDLSyncEvent.getPrimaryKey());

		Assert.assertEquals(existingDLSyncEvent, newDLSyncEvent);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DLSyncEvent missingDLSyncEvent = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingDLSyncEvent);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		DLSyncEvent newDLSyncEvent1 = addDLSyncEvent();
		DLSyncEvent newDLSyncEvent2 = addDLSyncEvent();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDLSyncEvent1.getPrimaryKey());
		primaryKeys.add(newDLSyncEvent2.getPrimaryKey());

		Map<Serializable, DLSyncEvent> dlSyncEvents = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, dlSyncEvents.size());
		Assert.assertEquals(newDLSyncEvent1,
			dlSyncEvents.get(newDLSyncEvent1.getPrimaryKey()));
		Assert.assertEquals(newDLSyncEvent2,
			dlSyncEvents.get(newDLSyncEvent2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, DLSyncEvent> dlSyncEvents = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(dlSyncEvents.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		DLSyncEvent newDLSyncEvent = addDLSyncEvent();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDLSyncEvent.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, DLSyncEvent> dlSyncEvents = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, dlSyncEvents.size());
		Assert.assertEquals(newDLSyncEvent,
			dlSyncEvents.get(newDLSyncEvent.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, DLSyncEvent> dlSyncEvents = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(dlSyncEvents.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		DLSyncEvent newDLSyncEvent = addDLSyncEvent();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDLSyncEvent.getPrimaryKey());

		Map<Serializable, DLSyncEvent> dlSyncEvents = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, dlSyncEvents.size());
		Assert.assertEquals(newDLSyncEvent,
			dlSyncEvents.get(newDLSyncEvent.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = DLSyncEventLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					DLSyncEvent dlSyncEvent = (DLSyncEvent)object;

					Assert.assertNotNull(dlSyncEvent);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		DLSyncEvent newDLSyncEvent = addDLSyncEvent();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLSyncEvent.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("syncEventId",
				newDLSyncEvent.getSyncEventId()));

		List<DLSyncEvent> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		DLSyncEvent existingDLSyncEvent = result.get(0);

		Assert.assertEquals(existingDLSyncEvent, newDLSyncEvent);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLSyncEvent.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("syncEventId",
				RandomTestUtil.nextLong()));

		List<DLSyncEvent> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		DLSyncEvent newDLSyncEvent = addDLSyncEvent();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLSyncEvent.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("syncEventId"));

		Object newSyncEventId = newDLSyncEvent.getSyncEventId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("syncEventId",
				new Object[] { newSyncEventId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingSyncEventId = result.get(0);

		Assert.assertEquals(existingSyncEventId, newSyncEventId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLSyncEvent.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("syncEventId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("syncEventId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		DLSyncEvent newDLSyncEvent = addDLSyncEvent();

		_persistence.clearCache();

		DLSyncEvent existingDLSyncEvent = _persistence.findByPrimaryKey(newDLSyncEvent.getPrimaryKey());

		Assert.assertEquals(existingDLSyncEvent.getTypePK(),
			ReflectionTestUtil.invoke(existingDLSyncEvent, "getOriginalTypePK",
				new Class<?>[0]));
	}

	protected DLSyncEvent addDLSyncEvent() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DLSyncEvent dlSyncEvent = _persistence.create(pk);

		dlSyncEvent.setModifiedTime(RandomTestUtil.nextLong());

		dlSyncEvent.setEvent(RandomTestUtil.randomString());

		dlSyncEvent.setType(RandomTestUtil.randomString());

		dlSyncEvent.setTypePK(RandomTestUtil.nextLong());

		_dlSyncEvents.add(_persistence.update(dlSyncEvent));

		return dlSyncEvent;
	}

	private List<DLSyncEvent> _dlSyncEvents = new ArrayList<DLSyncEvent>();
	private DLSyncEventPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}