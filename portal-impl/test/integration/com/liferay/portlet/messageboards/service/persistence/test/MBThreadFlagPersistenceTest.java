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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;

import com.liferay.portlet.messageboards.NoSuchThreadFlagException;
import com.liferay.portlet.messageboards.model.MBThreadFlag;
import com.liferay.portlet.messageboards.service.MBThreadFlagLocalServiceUtil;
import com.liferay.portlet.messageboards.service.persistence.MBThreadFlagPersistence;
import com.liferay.portlet.messageboards.service.persistence.MBThreadFlagUtil;

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
public class MBThreadFlagPersistenceTest {
	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = MBThreadFlagUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<MBThreadFlag> iterator = _mbThreadFlags.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MBThreadFlag mbThreadFlag = _persistence.create(pk);

		Assert.assertNotNull(mbThreadFlag);

		Assert.assertEquals(mbThreadFlag.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		MBThreadFlag newMBThreadFlag = addMBThreadFlag();

		_persistence.remove(newMBThreadFlag);

		MBThreadFlag existingMBThreadFlag = _persistence.fetchByPrimaryKey(newMBThreadFlag.getPrimaryKey());

		Assert.assertNull(existingMBThreadFlag);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addMBThreadFlag();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MBThreadFlag newMBThreadFlag = _persistence.create(pk);

		newMBThreadFlag.setUuid(RandomTestUtil.randomString());

		newMBThreadFlag.setGroupId(RandomTestUtil.nextLong());

		newMBThreadFlag.setCompanyId(RandomTestUtil.nextLong());

		newMBThreadFlag.setUserId(RandomTestUtil.nextLong());

		newMBThreadFlag.setUserName(RandomTestUtil.randomString());

		newMBThreadFlag.setCreateDate(RandomTestUtil.nextDate());

		newMBThreadFlag.setModifiedDate(RandomTestUtil.nextDate());

		newMBThreadFlag.setThreadId(RandomTestUtil.nextLong());

		_mbThreadFlags.add(_persistence.update(newMBThreadFlag));

		MBThreadFlag existingMBThreadFlag = _persistence.findByPrimaryKey(newMBThreadFlag.getPrimaryKey());

		Assert.assertEquals(existingMBThreadFlag.getUuid(),
			newMBThreadFlag.getUuid());
		Assert.assertEquals(existingMBThreadFlag.getThreadFlagId(),
			newMBThreadFlag.getThreadFlagId());
		Assert.assertEquals(existingMBThreadFlag.getGroupId(),
			newMBThreadFlag.getGroupId());
		Assert.assertEquals(existingMBThreadFlag.getCompanyId(),
			newMBThreadFlag.getCompanyId());
		Assert.assertEquals(existingMBThreadFlag.getUserId(),
			newMBThreadFlag.getUserId());
		Assert.assertEquals(existingMBThreadFlag.getUserName(),
			newMBThreadFlag.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingMBThreadFlag.getCreateDate()),
			Time.getShortTimestamp(newMBThreadFlag.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingMBThreadFlag.getModifiedDate()),
			Time.getShortTimestamp(newMBThreadFlag.getModifiedDate()));
		Assert.assertEquals(existingMBThreadFlag.getThreadId(),
			newMBThreadFlag.getThreadId());
	}

	@Test
	public void testCountByUuid() throws Exception {
		_persistence.countByUuid(StringPool.BLANK);

		_persistence.countByUuid(StringPool.NULL);

		_persistence.countByUuid((String)null);
	}

	@Test
	public void testCountByUUID_G() throws Exception {
		_persistence.countByUUID_G(StringPool.BLANK, RandomTestUtil.nextLong());

		_persistence.countByUUID_G(StringPool.NULL, 0L);

		_persistence.countByUUID_G((String)null, 0L);
	}

	@Test
	public void testCountByUuid_C() throws Exception {
		_persistence.countByUuid_C(StringPool.BLANK, RandomTestUtil.nextLong());

		_persistence.countByUuid_C(StringPool.NULL, 0L);

		_persistence.countByUuid_C((String)null, 0L);
	}

	@Test
	public void testCountByUserId() throws Exception {
		_persistence.countByUserId(RandomTestUtil.nextLong());

		_persistence.countByUserId(0L);
	}

	@Test
	public void testCountByThreadId() throws Exception {
		_persistence.countByThreadId(RandomTestUtil.nextLong());

		_persistence.countByThreadId(0L);
	}

	@Test
	public void testCountByU_T() throws Exception {
		_persistence.countByU_T(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByU_T(0L, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		MBThreadFlag newMBThreadFlag = addMBThreadFlag();

		MBThreadFlag existingMBThreadFlag = _persistence.findByPrimaryKey(newMBThreadFlag.getPrimaryKey());

		Assert.assertEquals(existingMBThreadFlag, newMBThreadFlag);
	}

	@Test(expected = NoSuchThreadFlagException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<MBThreadFlag> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("MBThreadFlag", "uuid",
			true, "threadFlagId", true, "groupId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "threadId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		MBThreadFlag newMBThreadFlag = addMBThreadFlag();

		MBThreadFlag existingMBThreadFlag = _persistence.fetchByPrimaryKey(newMBThreadFlag.getPrimaryKey());

		Assert.assertEquals(existingMBThreadFlag, newMBThreadFlag);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MBThreadFlag missingMBThreadFlag = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingMBThreadFlag);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		MBThreadFlag newMBThreadFlag1 = addMBThreadFlag();
		MBThreadFlag newMBThreadFlag2 = addMBThreadFlag();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newMBThreadFlag1.getPrimaryKey());
		primaryKeys.add(newMBThreadFlag2.getPrimaryKey());

		Map<Serializable, MBThreadFlag> mbThreadFlags = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, mbThreadFlags.size());
		Assert.assertEquals(newMBThreadFlag1,
			mbThreadFlags.get(newMBThreadFlag1.getPrimaryKey()));
		Assert.assertEquals(newMBThreadFlag2,
			mbThreadFlags.get(newMBThreadFlag2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, MBThreadFlag> mbThreadFlags = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(mbThreadFlags.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		MBThreadFlag newMBThreadFlag = addMBThreadFlag();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newMBThreadFlag.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, MBThreadFlag> mbThreadFlags = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, mbThreadFlags.size());
		Assert.assertEquals(newMBThreadFlag,
			mbThreadFlags.get(newMBThreadFlag.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, MBThreadFlag> mbThreadFlags = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(mbThreadFlags.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		MBThreadFlag newMBThreadFlag = addMBThreadFlag();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newMBThreadFlag.getPrimaryKey());

		Map<Serializable, MBThreadFlag> mbThreadFlags = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, mbThreadFlags.size());
		Assert.assertEquals(newMBThreadFlag,
			mbThreadFlags.get(newMBThreadFlag.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = MBThreadFlagLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					MBThreadFlag mbThreadFlag = (MBThreadFlag)object;

					Assert.assertNotNull(mbThreadFlag);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		MBThreadFlag newMBThreadFlag = addMBThreadFlag();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBThreadFlag.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("threadFlagId",
				newMBThreadFlag.getThreadFlagId()));

		List<MBThreadFlag> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		MBThreadFlag existingMBThreadFlag = result.get(0);

		Assert.assertEquals(existingMBThreadFlag, newMBThreadFlag);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBThreadFlag.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("threadFlagId",
				RandomTestUtil.nextLong()));

		List<MBThreadFlag> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		MBThreadFlag newMBThreadFlag = addMBThreadFlag();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBThreadFlag.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"threadFlagId"));

		Object newThreadFlagId = newMBThreadFlag.getThreadFlagId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("threadFlagId",
				new Object[] { newThreadFlagId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingThreadFlagId = result.get(0);

		Assert.assertEquals(existingThreadFlagId, newThreadFlagId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBThreadFlag.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"threadFlagId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("threadFlagId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		MBThreadFlag newMBThreadFlag = addMBThreadFlag();

		_persistence.clearCache();

		MBThreadFlag existingMBThreadFlag = _persistence.findByPrimaryKey(newMBThreadFlag.getPrimaryKey());

		Assert.assertTrue(Validator.equals(existingMBThreadFlag.getUuid(),
				ReflectionTestUtil.invoke(existingMBThreadFlag,
					"getOriginalUuid", new Class<?>[0])));
		Assert.assertEquals(existingMBThreadFlag.getGroupId(),
			ReflectionTestUtil.invoke(existingMBThreadFlag,
				"getOriginalGroupId", new Class<?>[0]));

		Assert.assertEquals(existingMBThreadFlag.getUserId(),
			ReflectionTestUtil.invoke(existingMBThreadFlag,
				"getOriginalUserId", new Class<?>[0]));
		Assert.assertEquals(existingMBThreadFlag.getThreadId(),
			ReflectionTestUtil.invoke(existingMBThreadFlag,
				"getOriginalThreadId", new Class<?>[0]));
	}

	protected MBThreadFlag addMBThreadFlag() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MBThreadFlag mbThreadFlag = _persistence.create(pk);

		mbThreadFlag.setUuid(RandomTestUtil.randomString());

		mbThreadFlag.setGroupId(RandomTestUtil.nextLong());

		mbThreadFlag.setCompanyId(RandomTestUtil.nextLong());

		mbThreadFlag.setUserId(RandomTestUtil.nextLong());

		mbThreadFlag.setUserName(RandomTestUtil.randomString());

		mbThreadFlag.setCreateDate(RandomTestUtil.nextDate());

		mbThreadFlag.setModifiedDate(RandomTestUtil.nextDate());

		mbThreadFlag.setThreadId(RandomTestUtil.nextLong());

		_mbThreadFlags.add(_persistence.update(mbThreadFlag));

		return mbThreadFlag;
	}

	private List<MBThreadFlag> _mbThreadFlags = new ArrayList<MBThreadFlag>();
	private MBThreadFlagPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}