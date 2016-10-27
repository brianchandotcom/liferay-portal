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

package com.liferay.social.privatemessaging.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import com.liferay.social.privatemessaging.exception.NoSuchUserThreadException;
import com.liferay.social.privatemessaging.model.UserThread;
import com.liferay.social.privatemessaging.service.UserThreadLocalServiceUtil;
import com.liferay.social.privatemessaging.service.persistence.UserThreadPersistence;
import com.liferay.social.privatemessaging.service.persistence.UserThreadUtil;

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
public class UserThreadPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.social.privatemessaging.service"));

	@Before
	public void setUp() {
		_persistence = UserThreadUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<UserThread> iterator = _userThreads.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		UserThread userThread = _persistence.create(pk);

		Assert.assertNotNull(userThread);

		Assert.assertEquals(userThread.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		UserThread newUserThread = addUserThread();

		_persistence.remove(newUserThread);

		UserThread existingUserThread = _persistence.fetchByPrimaryKey(newUserThread.getPrimaryKey());

		Assert.assertNull(existingUserThread);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addUserThread();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		UserThread newUserThread = _persistence.create(pk);

		newUserThread.setCompanyId(RandomTestUtil.nextLong());

		newUserThread.setUserId(RandomTestUtil.nextLong());

		newUserThread.setUserName(RandomTestUtil.randomString());

		newUserThread.setCreateDate(RandomTestUtil.nextDate());

		newUserThread.setModifiedDate(RandomTestUtil.nextDate());

		newUserThread.setMbThreadId(RandomTestUtil.nextLong());

		newUserThread.setTopMBMessageId(RandomTestUtil.nextLong());

		newUserThread.setRead(RandomTestUtil.randomBoolean());

		newUserThread.setDeleted(RandomTestUtil.randomBoolean());

		_userThreads.add(_persistence.update(newUserThread));

		UserThread existingUserThread = _persistence.findByPrimaryKey(newUserThread.getPrimaryKey());

		Assert.assertEquals(existingUserThread.getUserThreadId(),
			newUserThread.getUserThreadId());
		Assert.assertEquals(existingUserThread.getCompanyId(),
			newUserThread.getCompanyId());
		Assert.assertEquals(existingUserThread.getUserId(),
			newUserThread.getUserId());
		Assert.assertEquals(existingUserThread.getUserName(),
			newUserThread.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingUserThread.getCreateDate()),
			Time.getShortTimestamp(newUserThread.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingUserThread.getModifiedDate()),
			Time.getShortTimestamp(newUserThread.getModifiedDate()));
		Assert.assertEquals(existingUserThread.getMbThreadId(),
			newUserThread.getMbThreadId());
		Assert.assertEquals(existingUserThread.getTopMBMessageId(),
			newUserThread.getTopMBMessageId());
		Assert.assertEquals(existingUserThread.getRead(),
			newUserThread.getRead());
		Assert.assertEquals(existingUserThread.getDeleted(),
			newUserThread.getDeleted());
	}

	@Test
	public void testCountByUserId() throws Exception {
		_persistence.countByUserId(RandomTestUtil.nextLong());

		_persistence.countByUserId(0L);
	}

	@Test
	public void testCountByMBThreadId() throws Exception {
		_persistence.countByMBThreadId(RandomTestUtil.nextLong());

		_persistence.countByMBThreadId(0L);
	}

	@Test
	public void testCountByU_M() throws Exception {
		_persistence.countByU_M(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByU_M(0L, 0L);
	}

	@Test
	public void testCountByU_D() throws Exception {
		_persistence.countByU_D(RandomTestUtil.nextLong(),
			RandomTestUtil.randomBoolean());

		_persistence.countByU_D(0L, RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByU_R_D() throws Exception {
		_persistence.countByU_R_D(RandomTestUtil.nextLong(),
			RandomTestUtil.randomBoolean(), RandomTestUtil.randomBoolean());

		_persistence.countByU_R_D(0L, RandomTestUtil.randomBoolean(),
			RandomTestUtil.randomBoolean());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		UserThread newUserThread = addUserThread();

		UserThread existingUserThread = _persistence.findByPrimaryKey(newUserThread.getPrimaryKey());

		Assert.assertEquals(existingUserThread, newUserThread);
	}

	@Test(expected = NoSuchUserThreadException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<UserThread> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("PM_UserThread",
			"userThreadId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true,
			"mbThreadId", true, "topMBMessageId", true, "read", true,
			"deleted", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		UserThread newUserThread = addUserThread();

		UserThread existingUserThread = _persistence.fetchByPrimaryKey(newUserThread.getPrimaryKey());

		Assert.assertEquals(existingUserThread, newUserThread);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		UserThread missingUserThread = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingUserThread);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		UserThread newUserThread1 = addUserThread();
		UserThread newUserThread2 = addUserThread();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newUserThread1.getPrimaryKey());
		primaryKeys.add(newUserThread2.getPrimaryKey());

		Map<Serializable, UserThread> userThreads = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, userThreads.size());
		Assert.assertEquals(newUserThread1,
			userThreads.get(newUserThread1.getPrimaryKey()));
		Assert.assertEquals(newUserThread2,
			userThreads.get(newUserThread2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, UserThread> userThreads = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(userThreads.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		UserThread newUserThread = addUserThread();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newUserThread.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, UserThread> userThreads = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, userThreads.size());
		Assert.assertEquals(newUserThread,
			userThreads.get(newUserThread.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, UserThread> userThreads = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(userThreads.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		UserThread newUserThread = addUserThread();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newUserThread.getPrimaryKey());

		Map<Serializable, UserThread> userThreads = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, userThreads.size());
		Assert.assertEquals(newUserThread,
			userThreads.get(newUserThread.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = UserThreadLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<UserThread>() {
				@Override
				public void performAction(UserThread userThread) {
					Assert.assertNotNull(userThread);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		UserThread newUserThread = addUserThread();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(UserThread.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("userThreadId",
				newUserThread.getUserThreadId()));

		List<UserThread> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		UserThread existingUserThread = result.get(0);

		Assert.assertEquals(existingUserThread, newUserThread);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(UserThread.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("userThreadId",
				RandomTestUtil.nextLong()));

		List<UserThread> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		UserThread newUserThread = addUserThread();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(UserThread.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"userThreadId"));

		Object newUserThreadId = newUserThread.getUserThreadId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("userThreadId",
				new Object[] { newUserThreadId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingUserThreadId = result.get(0);

		Assert.assertEquals(existingUserThreadId, newUserThreadId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(UserThread.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"userThreadId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("userThreadId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		UserThread newUserThread = addUserThread();

		_persistence.clearCache();

		UserThread existingUserThread = _persistence.findByPrimaryKey(newUserThread.getPrimaryKey());

		Assert.assertEquals(Long.valueOf(existingUserThread.getUserId()),
			ReflectionTestUtil.<Long>invoke(existingUserThread,
				"getOriginalUserId", new Class<?>[0]));
		Assert.assertEquals(Long.valueOf(existingUserThread.getMbThreadId()),
			ReflectionTestUtil.<Long>invoke(existingUserThread,
				"getOriginalMbThreadId", new Class<?>[0]));
	}

	protected UserThread addUserThread() throws Exception {
		long pk = RandomTestUtil.nextLong();

		UserThread userThread = _persistence.create(pk);

		userThread.setCompanyId(RandomTestUtil.nextLong());

		userThread.setUserId(RandomTestUtil.nextLong());

		userThread.setUserName(RandomTestUtil.randomString());

		userThread.setCreateDate(RandomTestUtil.nextDate());

		userThread.setModifiedDate(RandomTestUtil.nextDate());

		userThread.setMbThreadId(RandomTestUtil.nextLong());

		userThread.setTopMBMessageId(RandomTestUtil.nextLong());

		userThread.setRead(RandomTestUtil.randomBoolean());

		userThread.setDeleted(RandomTestUtil.randomBoolean());

		_userThreads.add(_persistence.update(userThread));

		return userThread;
	}

	private List<UserThread> _userThreads = new ArrayList<UserThread>();
	private UserThreadPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}