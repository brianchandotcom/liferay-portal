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

package com.liferay.portlet.mobiledevicerules.service.persistence.test;

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

import com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupInstanceException;
import com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance;
import com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupInstanceLocalServiceUtil;
import com.liferay.portlet.mobiledevicerules.service.persistence.MDRRuleGroupInstancePersistence;
import com.liferay.portlet.mobiledevicerules.service.persistence.MDRRuleGroupInstanceUtil;

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
public class MDRRuleGroupInstancePersistenceTest {
	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = MDRRuleGroupInstanceUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<MDRRuleGroupInstance> iterator = _mdrRuleGroupInstances.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MDRRuleGroupInstance mdrRuleGroupInstance = _persistence.create(pk);

		Assert.assertNotNull(mdrRuleGroupInstance);

		Assert.assertEquals(mdrRuleGroupInstance.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		MDRRuleGroupInstance newMDRRuleGroupInstance = addMDRRuleGroupInstance();

		_persistence.remove(newMDRRuleGroupInstance);

		MDRRuleGroupInstance existingMDRRuleGroupInstance = _persistence.fetchByPrimaryKey(newMDRRuleGroupInstance.getPrimaryKey());

		Assert.assertNull(existingMDRRuleGroupInstance);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addMDRRuleGroupInstance();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MDRRuleGroupInstance newMDRRuleGroupInstance = _persistence.create(pk);

		newMDRRuleGroupInstance.setUuid(RandomTestUtil.randomString());

		newMDRRuleGroupInstance.setGroupId(RandomTestUtil.nextLong());

		newMDRRuleGroupInstance.setCompanyId(RandomTestUtil.nextLong());

		newMDRRuleGroupInstance.setUserId(RandomTestUtil.nextLong());

		newMDRRuleGroupInstance.setUserName(RandomTestUtil.randomString());

		newMDRRuleGroupInstance.setCreateDate(RandomTestUtil.nextDate());

		newMDRRuleGroupInstance.setModifiedDate(RandomTestUtil.nextDate());

		newMDRRuleGroupInstance.setClassNameId(RandomTestUtil.nextLong());

		newMDRRuleGroupInstance.setClassPK(RandomTestUtil.nextLong());

		newMDRRuleGroupInstance.setRuleGroupId(RandomTestUtil.nextLong());

		newMDRRuleGroupInstance.setPriority(RandomTestUtil.nextInt());

		_mdrRuleGroupInstances.add(_persistence.update(newMDRRuleGroupInstance));

		MDRRuleGroupInstance existingMDRRuleGroupInstance = _persistence.findByPrimaryKey(newMDRRuleGroupInstance.getPrimaryKey());

		Assert.assertEquals(existingMDRRuleGroupInstance.getUuid(),
			newMDRRuleGroupInstance.getUuid());
		Assert.assertEquals(existingMDRRuleGroupInstance.getRuleGroupInstanceId(),
			newMDRRuleGroupInstance.getRuleGroupInstanceId());
		Assert.assertEquals(existingMDRRuleGroupInstance.getGroupId(),
			newMDRRuleGroupInstance.getGroupId());
		Assert.assertEquals(existingMDRRuleGroupInstance.getCompanyId(),
			newMDRRuleGroupInstance.getCompanyId());
		Assert.assertEquals(existingMDRRuleGroupInstance.getUserId(),
			newMDRRuleGroupInstance.getUserId());
		Assert.assertEquals(existingMDRRuleGroupInstance.getUserName(),
			newMDRRuleGroupInstance.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingMDRRuleGroupInstance.getCreateDate()),
			Time.getShortTimestamp(newMDRRuleGroupInstance.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingMDRRuleGroupInstance.getModifiedDate()),
			Time.getShortTimestamp(newMDRRuleGroupInstance.getModifiedDate()));
		Assert.assertEquals(existingMDRRuleGroupInstance.getClassNameId(),
			newMDRRuleGroupInstance.getClassNameId());
		Assert.assertEquals(existingMDRRuleGroupInstance.getClassPK(),
			newMDRRuleGroupInstance.getClassPK());
		Assert.assertEquals(existingMDRRuleGroupInstance.getRuleGroupId(),
			newMDRRuleGroupInstance.getRuleGroupId());
		Assert.assertEquals(existingMDRRuleGroupInstance.getPriority(),
			newMDRRuleGroupInstance.getPriority());
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
	public void testCountByGroupId() throws Exception {
		_persistence.countByGroupId(RandomTestUtil.nextLong());

		_persistence.countByGroupId(0L);
	}

	@Test
	public void testCountByRuleGroupId() throws Exception {
		_persistence.countByRuleGroupId(RandomTestUtil.nextLong());

		_persistence.countByRuleGroupId(0L);
	}

	@Test
	public void testCountByC_C() throws Exception {
		_persistence.countByC_C(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByC_C(0L, 0L);
	}

	@Test
	public void testCountByG_C_C() throws Exception {
		_persistence.countByG_C_C(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByG_C_C(0L, 0L, 0L);
	}

	@Test
	public void testCountByC_C_R() throws Exception {
		_persistence.countByC_C_R(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByC_C_R(0L, 0L, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		MDRRuleGroupInstance newMDRRuleGroupInstance = addMDRRuleGroupInstance();

		MDRRuleGroupInstance existingMDRRuleGroupInstance = _persistence.findByPrimaryKey(newMDRRuleGroupInstance.getPrimaryKey());

		Assert.assertEquals(existingMDRRuleGroupInstance,
			newMDRRuleGroupInstance);
	}

	@Test(expected = NoSuchRuleGroupInstanceException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	@Test
	public void testFilterFindByGroupId() throws Exception {
		_persistence.filterFindByGroupId(0, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<MDRRuleGroupInstance> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("MDRRuleGroupInstance",
			"uuid", true, "ruleGroupInstanceId", true, "groupId", true,
			"companyId", true, "userId", true, "userName", true, "createDate",
			true, "modifiedDate", true, "classNameId", true, "classPK", true,
			"ruleGroupId", true, "priority", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		MDRRuleGroupInstance newMDRRuleGroupInstance = addMDRRuleGroupInstance();

		MDRRuleGroupInstance existingMDRRuleGroupInstance = _persistence.fetchByPrimaryKey(newMDRRuleGroupInstance.getPrimaryKey());

		Assert.assertEquals(existingMDRRuleGroupInstance,
			newMDRRuleGroupInstance);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MDRRuleGroupInstance missingMDRRuleGroupInstance = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingMDRRuleGroupInstance);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		MDRRuleGroupInstance newMDRRuleGroupInstance1 = addMDRRuleGroupInstance();
		MDRRuleGroupInstance newMDRRuleGroupInstance2 = addMDRRuleGroupInstance();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newMDRRuleGroupInstance1.getPrimaryKey());
		primaryKeys.add(newMDRRuleGroupInstance2.getPrimaryKey());

		Map<Serializable, MDRRuleGroupInstance> mdrRuleGroupInstances = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, mdrRuleGroupInstances.size());
		Assert.assertEquals(newMDRRuleGroupInstance1,
			mdrRuleGroupInstances.get(newMDRRuleGroupInstance1.getPrimaryKey()));
		Assert.assertEquals(newMDRRuleGroupInstance2,
			mdrRuleGroupInstances.get(newMDRRuleGroupInstance2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, MDRRuleGroupInstance> mdrRuleGroupInstances = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(mdrRuleGroupInstances.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		MDRRuleGroupInstance newMDRRuleGroupInstance = addMDRRuleGroupInstance();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newMDRRuleGroupInstance.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, MDRRuleGroupInstance> mdrRuleGroupInstances = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, mdrRuleGroupInstances.size());
		Assert.assertEquals(newMDRRuleGroupInstance,
			mdrRuleGroupInstances.get(newMDRRuleGroupInstance.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, MDRRuleGroupInstance> mdrRuleGroupInstances = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(mdrRuleGroupInstances.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		MDRRuleGroupInstance newMDRRuleGroupInstance = addMDRRuleGroupInstance();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newMDRRuleGroupInstance.getPrimaryKey());

		Map<Serializable, MDRRuleGroupInstance> mdrRuleGroupInstances = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, mdrRuleGroupInstances.size());
		Assert.assertEquals(newMDRRuleGroupInstance,
			mdrRuleGroupInstances.get(newMDRRuleGroupInstance.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = MDRRuleGroupInstanceLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					MDRRuleGroupInstance mdrRuleGroupInstance = (MDRRuleGroupInstance)object;

					Assert.assertNotNull(mdrRuleGroupInstance);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		MDRRuleGroupInstance newMDRRuleGroupInstance = addMDRRuleGroupInstance();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MDRRuleGroupInstance.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("ruleGroupInstanceId",
				newMDRRuleGroupInstance.getRuleGroupInstanceId()));

		List<MDRRuleGroupInstance> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		MDRRuleGroupInstance existingMDRRuleGroupInstance = result.get(0);

		Assert.assertEquals(existingMDRRuleGroupInstance,
			newMDRRuleGroupInstance);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MDRRuleGroupInstance.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("ruleGroupInstanceId",
				RandomTestUtil.nextLong()));

		List<MDRRuleGroupInstance> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		MDRRuleGroupInstance newMDRRuleGroupInstance = addMDRRuleGroupInstance();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MDRRuleGroupInstance.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"ruleGroupInstanceId"));

		Object newRuleGroupInstanceId = newMDRRuleGroupInstance.getRuleGroupInstanceId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("ruleGroupInstanceId",
				new Object[] { newRuleGroupInstanceId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingRuleGroupInstanceId = result.get(0);

		Assert.assertEquals(existingRuleGroupInstanceId, newRuleGroupInstanceId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MDRRuleGroupInstance.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"ruleGroupInstanceId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("ruleGroupInstanceId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		MDRRuleGroupInstance newMDRRuleGroupInstance = addMDRRuleGroupInstance();

		_persistence.clearCache();

		MDRRuleGroupInstance existingMDRRuleGroupInstance = _persistence.findByPrimaryKey(newMDRRuleGroupInstance.getPrimaryKey());

		Assert.assertTrue(Validator.equals(
				existingMDRRuleGroupInstance.getUuid(),
				ReflectionTestUtil.invoke(existingMDRRuleGroupInstance,
					"getOriginalUuid", new Class<?>[0])));
		Assert.assertEquals(existingMDRRuleGroupInstance.getGroupId(),
			ReflectionTestUtil.invoke(existingMDRRuleGroupInstance,
				"getOriginalGroupId", new Class<?>[0]));

		Assert.assertEquals(existingMDRRuleGroupInstance.getClassNameId(),
			ReflectionTestUtil.invoke(existingMDRRuleGroupInstance,
				"getOriginalClassNameId", new Class<?>[0]));
		Assert.assertEquals(existingMDRRuleGroupInstance.getClassPK(),
			ReflectionTestUtil.invoke(existingMDRRuleGroupInstance,
				"getOriginalClassPK", new Class<?>[0]));
		Assert.assertEquals(existingMDRRuleGroupInstance.getRuleGroupId(),
			ReflectionTestUtil.invoke(existingMDRRuleGroupInstance,
				"getOriginalRuleGroupId", new Class<?>[0]));
	}

	protected MDRRuleGroupInstance addMDRRuleGroupInstance()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		MDRRuleGroupInstance mdrRuleGroupInstance = _persistence.create(pk);

		mdrRuleGroupInstance.setUuid(RandomTestUtil.randomString());

		mdrRuleGroupInstance.setGroupId(RandomTestUtil.nextLong());

		mdrRuleGroupInstance.setCompanyId(RandomTestUtil.nextLong());

		mdrRuleGroupInstance.setUserId(RandomTestUtil.nextLong());

		mdrRuleGroupInstance.setUserName(RandomTestUtil.randomString());

		mdrRuleGroupInstance.setCreateDate(RandomTestUtil.nextDate());

		mdrRuleGroupInstance.setModifiedDate(RandomTestUtil.nextDate());

		mdrRuleGroupInstance.setClassNameId(RandomTestUtil.nextLong());

		mdrRuleGroupInstance.setClassPK(RandomTestUtil.nextLong());

		mdrRuleGroupInstance.setRuleGroupId(RandomTestUtil.nextLong());

		mdrRuleGroupInstance.setPriority(RandomTestUtil.nextInt());

		_mdrRuleGroupInstances.add(_persistence.update(mdrRuleGroupInstance));

		return mdrRuleGroupInstance;
	}

	private List<MDRRuleGroupInstance> _mdrRuleGroupInstances = new ArrayList<MDRRuleGroupInstance>();
	private MDRRuleGroupInstancePersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}