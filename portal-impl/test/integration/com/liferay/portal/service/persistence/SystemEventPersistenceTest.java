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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchSystemEventException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.SystemEvent;
import com.liferay.portal.service.SystemEventLocalServiceUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.test.TransactionalPersistenceAdvice;
import com.liferay.portal.util.test.RandomTestUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.io.Serializable;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
@ExecutionTestListeners(listeners =  {
	PersistenceExecutionTestListener.class})
@RunWith(LiferayPersistenceIntegrationJUnitTestRunner.class)
public class SystemEventPersistenceTest {
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<SystemEvent> modelListener : _modelListeners) {
			_persistence.unregisterListener(modelListener);
		}
	}

	@After
	public void tearDown() throws Exception {
		Map<Serializable, BasePersistence<?>> basePersistences = _transactionalPersistenceAdvice.getBasePersistences();

		Set<Serializable> primaryKeys = basePersistences.keySet();

		for (Serializable primaryKey : primaryKeys) {
			BasePersistence<?> basePersistence = basePersistences.get(primaryKey);

			try {
				basePersistence.remove(primaryKey);
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug("The model with primary key " + primaryKey +
						" was already deleted");
				}
			}
		}

		_transactionalPersistenceAdvice.reset();

		for (ModelListener<SystemEvent> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SystemEvent systemEvent = _persistence.create(pk);

		Assert.assertNotNull(systemEvent);

		Assert.assertEquals(systemEvent.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		SystemEvent newSystemEvent = addSystemEvent();

		_persistence.remove(newSystemEvent);

		SystemEvent existingSystemEvent = _persistence.fetchByPrimaryKey(newSystemEvent.getPrimaryKey());

		Assert.assertNull(existingSystemEvent);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addSystemEvent();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SystemEvent newSystemEvent = _persistence.create(pk);

		newSystemEvent.setMvccVersion(RandomTestUtil.nextLong());

		newSystemEvent.setGroupId(RandomTestUtil.nextLong());

		newSystemEvent.setCompanyId(RandomTestUtil.nextLong());

		newSystemEvent.setUserId(RandomTestUtil.nextLong());

		newSystemEvent.setUserName(RandomTestUtil.randomString());

		newSystemEvent.setCreateDate(RandomTestUtil.nextDate());

		newSystemEvent.setClassNameId(RandomTestUtil.nextLong());

		newSystemEvent.setClassPK(RandomTestUtil.nextLong());

		newSystemEvent.setClassUuid(RandomTestUtil.randomString());

		newSystemEvent.setReferrerClassNameId(RandomTestUtil.nextLong());

		newSystemEvent.setParentSystemEventId(RandomTestUtil.nextLong());

		newSystemEvent.setSystemEventSetKey(RandomTestUtil.nextLong());

		newSystemEvent.setType(RandomTestUtil.nextInt());

		newSystemEvent.setExtraData(RandomTestUtil.randomString());

		_persistence.update(newSystemEvent);

		SystemEvent existingSystemEvent = _persistence.findByPrimaryKey(newSystemEvent.getPrimaryKey());

		Assert.assertEquals(existingSystemEvent.getMvccVersion(),
			newSystemEvent.getMvccVersion());
		Assert.assertEquals(existingSystemEvent.getSystemEventId(),
			newSystemEvent.getSystemEventId());
		Assert.assertEquals(existingSystemEvent.getGroupId(),
			newSystemEvent.getGroupId());
		Assert.assertEquals(existingSystemEvent.getCompanyId(),
			newSystemEvent.getCompanyId());
		Assert.assertEquals(existingSystemEvent.getUserId(),
			newSystemEvent.getUserId());
		Assert.assertEquals(existingSystemEvent.getUserName(),
			newSystemEvent.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingSystemEvent.getCreateDate()),
			Time.getShortTimestamp(newSystemEvent.getCreateDate()));
		Assert.assertEquals(existingSystemEvent.getClassNameId(),
			newSystemEvent.getClassNameId());
		Assert.assertEquals(existingSystemEvent.getClassPK(),
			newSystemEvent.getClassPK());
		Assert.assertEquals(existingSystemEvent.getClassUuid(),
			newSystemEvent.getClassUuid());
		Assert.assertEquals(existingSystemEvent.getReferrerClassNameId(),
			newSystemEvent.getReferrerClassNameId());
		Assert.assertEquals(existingSystemEvent.getParentSystemEventId(),
			newSystemEvent.getParentSystemEventId());
		Assert.assertEquals(existingSystemEvent.getSystemEventSetKey(),
			newSystemEvent.getSystemEventSetKey());
		Assert.assertEquals(existingSystemEvent.getType(),
			newSystemEvent.getType());
		Assert.assertEquals(existingSystemEvent.getExtraData(),
			newSystemEvent.getExtraData());
	}

	@Test
	public void testCountByGroupId() {
		try {
			_persistence.countByGroupId(RandomTestUtil.nextLong());

			_persistence.countByGroupId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_S() {
		try {
			_persistence.countByG_S(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong());

			_persistence.countByG_S(0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_C_C() {
		try {
			_persistence.countByG_C_C(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

			_persistence.countByG_C_C(0L, 0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_C_C_T() {
		try {
			_persistence.countByG_C_C_T(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
				RandomTestUtil.nextInt());

			_persistence.countByG_C_C_T(0L, 0L, 0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		SystemEvent newSystemEvent = addSystemEvent();

		SystemEvent existingSystemEvent = _persistence.findByPrimaryKey(newSystemEvent.getPrimaryKey());

		Assert.assertEquals(existingSystemEvent, newSystemEvent);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchSystemEventException");
		}
		catch (NoSuchSystemEventException nsee) {
		}
	}

	@Test
	public void testFindAll() throws Exception {
		try {
			_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				getOrderByComparator());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	protected OrderByComparator getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("SystemEvent",
			"mvccVersion", true, "systemEventId", true, "groupId", true,
			"companyId", true, "userId", true, "userName", true, "createDate",
			true, "classNameId", true, "classPK", true, "classUuid", true,
			"referrerClassNameId", true, "parentSystemEventId", true,
			"systemEventSetKey", true, "type", true, "extraData", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		SystemEvent newSystemEvent = addSystemEvent();

		SystemEvent existingSystemEvent = _persistence.fetchByPrimaryKey(newSystemEvent.getPrimaryKey());

		Assert.assertEquals(existingSystemEvent, newSystemEvent);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SystemEvent missingSystemEvent = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingSystemEvent);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		SystemEvent newSystemEvent1 = addSystemEvent();
		SystemEvent newSystemEvent2 = addSystemEvent();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSystemEvent1.getPrimaryKey());
		primaryKeys.add(newSystemEvent2.getPrimaryKey());

		Map<Serializable, SystemEvent> systemEvents = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, systemEvents.size());
		Assert.assertEquals(newSystemEvent1,
			systemEvents.get(newSystemEvent1.getPrimaryKey()));
		Assert.assertEquals(newSystemEvent2,
			systemEvents.get(newSystemEvent2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, SystemEvent> systemEvents = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(systemEvents.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		SystemEvent newSystemEvent = addSystemEvent();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSystemEvent.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, SystemEvent> systemEvents = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, systemEvents.size());
		Assert.assertEquals(newSystemEvent,
			systemEvents.get(newSystemEvent.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, SystemEvent> systemEvents = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(systemEvents.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		SystemEvent newSystemEvent = addSystemEvent();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSystemEvent.getPrimaryKey());

		Map<Serializable, SystemEvent> systemEvents = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, systemEvents.size());
		Assert.assertEquals(newSystemEvent,
			systemEvents.get(newSystemEvent.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = SystemEventLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					SystemEvent systemEvent = (SystemEvent)object;

					Assert.assertNotNull(systemEvent);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		SystemEvent newSystemEvent = addSystemEvent();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SystemEvent.class,
				SystemEvent.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("systemEventId",
				newSystemEvent.getSystemEventId()));

		List<SystemEvent> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		SystemEvent existingSystemEvent = result.get(0);

		Assert.assertEquals(existingSystemEvent, newSystemEvent);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SystemEvent.class,
				SystemEvent.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("systemEventId",
				RandomTestUtil.nextLong()));

		List<SystemEvent> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		SystemEvent newSystemEvent = addSystemEvent();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SystemEvent.class,
				SystemEvent.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"systemEventId"));

		Object newSystemEventId = newSystemEvent.getSystemEventId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("systemEventId",
				new Object[] { newSystemEventId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingSystemEventId = result.get(0);

		Assert.assertEquals(existingSystemEventId, newSystemEventId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SystemEvent.class,
				SystemEvent.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"systemEventId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("systemEventId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected SystemEvent addSystemEvent() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SystemEvent systemEvent = _persistence.create(pk);

		systemEvent.setMvccVersion(RandomTestUtil.nextLong());

		systemEvent.setGroupId(RandomTestUtil.nextLong());

		systemEvent.setCompanyId(RandomTestUtil.nextLong());

		systemEvent.setUserId(RandomTestUtil.nextLong());

		systemEvent.setUserName(RandomTestUtil.randomString());

		systemEvent.setCreateDate(RandomTestUtil.nextDate());

		systemEvent.setClassNameId(RandomTestUtil.nextLong());

		systemEvent.setClassPK(RandomTestUtil.nextLong());

		systemEvent.setClassUuid(RandomTestUtil.randomString());

		systemEvent.setReferrerClassNameId(RandomTestUtil.nextLong());

		systemEvent.setParentSystemEventId(RandomTestUtil.nextLong());

		systemEvent.setSystemEventSetKey(RandomTestUtil.nextLong());

		systemEvent.setType(RandomTestUtil.nextInt());

		systemEvent.setExtraData(RandomTestUtil.randomString());

		_persistence.update(systemEvent);

		return systemEvent;
	}

	private static Log _log = LogFactoryUtil.getLog(SystemEventPersistenceTest.class);
	private ModelListener<SystemEvent>[] _modelListeners;
	private SystemEventPersistence _persistence = (SystemEventPersistence)PortalBeanLocatorUtil.locate(SystemEventPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}