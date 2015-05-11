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

package com.liferay.portlet.messageboards.service.persistence.test;

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
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;

import com.liferay.portlet.messageboards.NoSuchStatsUserException;
import com.liferay.portlet.messageboards.model.MBStatsUser;
import com.liferay.portlet.messageboards.service.MBStatsUserLocalServiceUtil;
import com.liferay.portlet.messageboards.service.persistence.MBStatsUserPersistence;
import com.liferay.portlet.messageboards.service.persistence.MBStatsUserUtil;

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
public class MBStatsUserPersistenceTest {
	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = MBStatsUserUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<MBStatsUser> iterator = _mbStatsUsers.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MBStatsUser mbStatsUser = _persistence.create(pk);

		Assert.assertNotNull(mbStatsUser);

		Assert.assertEquals(mbStatsUser.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		MBStatsUser newMBStatsUser = addMBStatsUser();

		_persistence.remove(newMBStatsUser);

		MBStatsUser existingMBStatsUser = _persistence.fetchByPrimaryKey(newMBStatsUser.getPrimaryKey());

		Assert.assertNull(existingMBStatsUser);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addMBStatsUser();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MBStatsUser newMBStatsUser = _persistence.create(pk);

		newMBStatsUser.setGroupId(RandomTestUtil.nextLong());

		newMBStatsUser.setUserId(RandomTestUtil.nextLong());

		newMBStatsUser.setMessageCount(RandomTestUtil.nextInt());

		newMBStatsUser.setLastPostDate(RandomTestUtil.nextDate());

		_mbStatsUsers.add(_persistence.update(newMBStatsUser));

		MBStatsUser existingMBStatsUser = _persistence.findByPrimaryKey(newMBStatsUser.getPrimaryKey());

		Assert.assertEquals(existingMBStatsUser.getStatsUserId(),
			newMBStatsUser.getStatsUserId());
		Assert.assertEquals(existingMBStatsUser.getGroupId(),
			newMBStatsUser.getGroupId());
		Assert.assertEquals(existingMBStatsUser.getUserId(),
			newMBStatsUser.getUserId());
		Assert.assertEquals(existingMBStatsUser.getMessageCount(),
			newMBStatsUser.getMessageCount());
		Assert.assertEquals(Time.getShortTimestamp(
				existingMBStatsUser.getLastPostDate()),
			Time.getShortTimestamp(newMBStatsUser.getLastPostDate()));
	}

	@Test
	public void testCountByGroupId() throws Exception {
		_persistence.countByGroupId(RandomTestUtil.nextLong());

		_persistence.countByGroupId(0L);
	}

	@Test
	public void testCountByUserId() throws Exception {
		_persistence.countByUserId(RandomTestUtil.nextLong());

		_persistence.countByUserId(0L);
	}

	@Test
	public void testCountByG_U() throws Exception {
		_persistence.countByG_U(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByG_U(0L, 0L);
	}

	@Test
	public void testCountByG_NotU_NotM() throws Exception {
		_persistence.countByG_NotU_NotM(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), RandomTestUtil.nextInt());

		_persistence.countByG_NotU_NotM(0L, 0L, 0);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		MBStatsUser newMBStatsUser = addMBStatsUser();

		MBStatsUser existingMBStatsUser = _persistence.findByPrimaryKey(newMBStatsUser.getPrimaryKey());

		Assert.assertEquals(existingMBStatsUser, newMBStatsUser);
	}

	@Test(expected = NoSuchStatsUserException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<MBStatsUser> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("MBStatsUser",
			"statsUserId", true, "groupId", true, "userId", true,
			"messageCount", true, "lastPostDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		MBStatsUser newMBStatsUser = addMBStatsUser();

		MBStatsUser existingMBStatsUser = _persistence.fetchByPrimaryKey(newMBStatsUser.getPrimaryKey());

		Assert.assertEquals(existingMBStatsUser, newMBStatsUser);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MBStatsUser missingMBStatsUser = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingMBStatsUser);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		MBStatsUser newMBStatsUser1 = addMBStatsUser();
		MBStatsUser newMBStatsUser2 = addMBStatsUser();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newMBStatsUser1.getPrimaryKey());
		primaryKeys.add(newMBStatsUser2.getPrimaryKey());

		Map<Serializable, MBStatsUser> mbStatsUsers = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, mbStatsUsers.size());
		Assert.assertEquals(newMBStatsUser1,
			mbStatsUsers.get(newMBStatsUser1.getPrimaryKey()));
		Assert.assertEquals(newMBStatsUser2,
			mbStatsUsers.get(newMBStatsUser2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, MBStatsUser> mbStatsUsers = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(mbStatsUsers.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		MBStatsUser newMBStatsUser = addMBStatsUser();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newMBStatsUser.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, MBStatsUser> mbStatsUsers = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, mbStatsUsers.size());
		Assert.assertEquals(newMBStatsUser,
			mbStatsUsers.get(newMBStatsUser.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, MBStatsUser> mbStatsUsers = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(mbStatsUsers.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		MBStatsUser newMBStatsUser = addMBStatsUser();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newMBStatsUser.getPrimaryKey());

		Map<Serializable, MBStatsUser> mbStatsUsers = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, mbStatsUsers.size());
		Assert.assertEquals(newMBStatsUser,
			mbStatsUsers.get(newMBStatsUser.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = MBStatsUserLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					MBStatsUser mbStatsUser = (MBStatsUser)object;

					Assert.assertNotNull(mbStatsUser);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		MBStatsUser newMBStatsUser = addMBStatsUser();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBStatsUser.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("statsUserId",
				newMBStatsUser.getStatsUserId()));

		List<MBStatsUser> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		MBStatsUser existingMBStatsUser = result.get(0);

		Assert.assertEquals(existingMBStatsUser, newMBStatsUser);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBStatsUser.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("statsUserId",
				RandomTestUtil.nextLong()));

		List<MBStatsUser> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		MBStatsUser newMBStatsUser = addMBStatsUser();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBStatsUser.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("statsUserId"));

		Object newStatsUserId = newMBStatsUser.getStatsUserId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("statsUserId",
				new Object[] { newStatsUserId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingStatsUserId = result.get(0);

		Assert.assertEquals(existingStatsUserId, newStatsUserId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBStatsUser.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("statsUserId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("statsUserId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		MBStatsUser newMBStatsUser = addMBStatsUser();

		_persistence.clearCache();

		MBStatsUser existingMBStatsUser = _persistence.findByPrimaryKey(newMBStatsUser.getPrimaryKey());

		Assert.assertEquals(existingMBStatsUser.getGroupId(),
			ReflectionTestUtil.invoke(existingMBStatsUser,
				"getOriginalGroupId", new Class<?>[0]));
		Assert.assertEquals(existingMBStatsUser.getUserId(),
			ReflectionTestUtil.invoke(existingMBStatsUser, "getOriginalUserId",
				new Class<?>[0]));
	}

	protected MBStatsUser addMBStatsUser() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MBStatsUser mbStatsUser = _persistence.create(pk);

		mbStatsUser.setGroupId(RandomTestUtil.nextLong());

		mbStatsUser.setUserId(RandomTestUtil.nextLong());

		mbStatsUser.setMessageCount(RandomTestUtil.nextInt());

		mbStatsUser.setLastPostDate(RandomTestUtil.nextDate());

		_mbStatsUsers.add(_persistence.update(mbStatsUser));

		return mbStatsUser;
	}

	private List<MBStatsUser> _mbStatsUsers = new ArrayList<MBStatsUser>();
	private MBStatsUserPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}