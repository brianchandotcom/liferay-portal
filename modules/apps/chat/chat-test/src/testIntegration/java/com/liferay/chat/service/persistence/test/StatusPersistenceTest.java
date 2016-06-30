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

package com.liferay.chat.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.chat.exception.NoSuchStatusException;
import com.liferay.chat.model.Status;
import com.liferay.chat.service.StatusLocalServiceUtil;
import com.liferay.chat.service.persistence.StatusPersistence;
import com.liferay.chat.service.persistence.StatusUtil;

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

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.junit.runner.RunWith;

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
@RunWith(Arquillian.class)
public class StatusPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = StatusUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<Status> iterator = _statuses.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Status status = _persistence.create(pk);

		Assert.assertNotNull(status);

		Assert.assertEquals(status.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		Status newStatus = addStatus();

		_persistence.remove(newStatus);

		Status existingStatus = _persistence.fetchByPrimaryKey(newStatus.getPrimaryKey());

		Assert.assertNull(existingStatus);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addStatus();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Status newStatus = _persistence.create(pk);

		newStatus.setUserId(RandomTestUtil.nextLong());

		newStatus.setModifiedDate(RandomTestUtil.nextLong());

		newStatus.setOnline(RandomTestUtil.randomBoolean());

		newStatus.setAwake(RandomTestUtil.randomBoolean());

		newStatus.setActivePanelIds(RandomTestUtil.randomString());

		newStatus.setMessage(RandomTestUtil.randomString());

		newStatus.setPlaySound(RandomTestUtil.randomBoolean());

		_statuses.add(_persistence.update(newStatus));

		Status existingStatus = _persistence.findByPrimaryKey(newStatus.getPrimaryKey());

		Assert.assertEquals(existingStatus.getStatusId(),
			newStatus.getStatusId());
		Assert.assertEquals(existingStatus.getUserId(), newStatus.getUserId());
		Assert.assertEquals(existingStatus.getModifiedDate(),
			newStatus.getModifiedDate());
		Assert.assertEquals(existingStatus.getOnline(), newStatus.getOnline());
		Assert.assertEquals(existingStatus.getAwake(), newStatus.getAwake());
		Assert.assertEquals(existingStatus.getActivePanelIds(),
			newStatus.getActivePanelIds());
		Assert.assertEquals(existingStatus.getMessage(), newStatus.getMessage());
		Assert.assertEquals(existingStatus.getPlaySound(),
			newStatus.getPlaySound());
	}

	@Test
	public void testCountByUserId() throws Exception {
		_persistence.countByUserId(RandomTestUtil.nextLong());

		_persistence.countByUserId(0L);
	}

	@Test
	public void testCountByModifiedDate() throws Exception {
		_persistence.countByModifiedDate(RandomTestUtil.nextLong());

		_persistence.countByModifiedDate(0L);
	}

	@Test
	public void testCountByOnline() throws Exception {
		_persistence.countByOnline(RandomTestUtil.randomBoolean());

		_persistence.countByOnline(RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByM_O() throws Exception {
		_persistence.countByM_O(RandomTestUtil.nextLong(),
			RandomTestUtil.randomBoolean());

		_persistence.countByM_O(0L, RandomTestUtil.randomBoolean());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		Status newStatus = addStatus();

		Status existingStatus = _persistence.findByPrimaryKey(newStatus.getPrimaryKey());

		Assert.assertEquals(existingStatus, newStatus);
	}

	@Test(expected = NoSuchStatusException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<Status> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("Chat_Status", "statusId",
			true, "userId", true, "modifiedDate", true, "online", true,
			"awake", true, "activePanelIds", true, "message", true,
			"playSound", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		Status newStatus = addStatus();

		Status existingStatus = _persistence.fetchByPrimaryKey(newStatus.getPrimaryKey());

		Assert.assertEquals(existingStatus, newStatus);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Status missingStatus = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingStatus);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		Status newStatus1 = addStatus();
		Status newStatus2 = addStatus();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newStatus1.getPrimaryKey());
		primaryKeys.add(newStatus2.getPrimaryKey());

		Map<Serializable, Status> statuses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, statuses.size());
		Assert.assertEquals(newStatus1, statuses.get(newStatus1.getPrimaryKey()));
		Assert.assertEquals(newStatus2, statuses.get(newStatus2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, Status> statuses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(statuses.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		Status newStatus = addStatus();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newStatus.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, Status> statuses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, statuses.size());
		Assert.assertEquals(newStatus, statuses.get(newStatus.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, Status> statuses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(statuses.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		Status newStatus = addStatus();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newStatus.getPrimaryKey());

		Map<Serializable, Status> statuses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, statuses.size());
		Assert.assertEquals(newStatus, statuses.get(newStatus.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = StatusLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<Status>() {
				@Override
				public void performAction(Status status) {
					Assert.assertNotNull(status);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Status newStatus = addStatus();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Status.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("statusId",
				newStatus.getStatusId()));

		List<Status> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Status existingStatus = result.get(0);

		Assert.assertEquals(existingStatus, newStatus);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Status.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("statusId",
				RandomTestUtil.nextLong()));

		List<Status> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		Status newStatus = addStatus();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Status.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("statusId"));

		Object newStatusId = newStatus.getStatusId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("statusId",
				new Object[] { newStatusId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingStatusId = result.get(0);

		Assert.assertEquals(existingStatusId, newStatusId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Status.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("statusId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("statusId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		Status newStatus = addStatus();

		_persistence.clearCache();

		Status existingStatus = _persistence.findByPrimaryKey(newStatus.getPrimaryKey());

		Assert.assertEquals(Long.valueOf(existingStatus.getUserId()),
			ReflectionTestUtil.<Long>invoke(existingStatus,
				"getOriginalUserId", new Class<?>[0]));
	}

	protected Status addStatus() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Status status = _persistence.create(pk);

		status.setUserId(RandomTestUtil.nextLong());

		status.setModifiedDate(RandomTestUtil.nextLong());

		status.setOnline(RandomTestUtil.randomBoolean());

		status.setAwake(RandomTestUtil.randomBoolean());

		status.setActivePanelIds(RandomTestUtil.randomString());

		status.setMessage(RandomTestUtil.randomString());

		status.setPlaySound(RandomTestUtil.randomBoolean());

		_statuses.add(_persistence.update(status));

		return status;
	}

	private List<Status> _statuses = new ArrayList<Status>();
	private StatusPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}