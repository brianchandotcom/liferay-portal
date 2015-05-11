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

import com.liferay.portlet.mobiledevicerules.NoSuchActionException;
import com.liferay.portlet.mobiledevicerules.model.MDRAction;
import com.liferay.portlet.mobiledevicerules.service.MDRActionLocalServiceUtil;
import com.liferay.portlet.mobiledevicerules.service.persistence.MDRActionPersistence;
import com.liferay.portlet.mobiledevicerules.service.persistence.MDRActionUtil;

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
public class MDRActionPersistenceTest {
	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = MDRActionUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<MDRAction> iterator = _mdrActions.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MDRAction mdrAction = _persistence.create(pk);

		Assert.assertNotNull(mdrAction);

		Assert.assertEquals(mdrAction.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		MDRAction newMDRAction = addMDRAction();

		_persistence.remove(newMDRAction);

		MDRAction existingMDRAction = _persistence.fetchByPrimaryKey(newMDRAction.getPrimaryKey());

		Assert.assertNull(existingMDRAction);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addMDRAction();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MDRAction newMDRAction = _persistence.create(pk);

		newMDRAction.setUuid(RandomTestUtil.randomString());

		newMDRAction.setGroupId(RandomTestUtil.nextLong());

		newMDRAction.setCompanyId(RandomTestUtil.nextLong());

		newMDRAction.setUserId(RandomTestUtil.nextLong());

		newMDRAction.setUserName(RandomTestUtil.randomString());

		newMDRAction.setCreateDate(RandomTestUtil.nextDate());

		newMDRAction.setModifiedDate(RandomTestUtil.nextDate());

		newMDRAction.setClassNameId(RandomTestUtil.nextLong());

		newMDRAction.setClassPK(RandomTestUtil.nextLong());

		newMDRAction.setRuleGroupInstanceId(RandomTestUtil.nextLong());

		newMDRAction.setName(RandomTestUtil.randomString());

		newMDRAction.setDescription(RandomTestUtil.randomString());

		newMDRAction.setType(RandomTestUtil.randomString());

		newMDRAction.setTypeSettings(RandomTestUtil.randomString());

		_mdrActions.add(_persistence.update(newMDRAction));

		MDRAction existingMDRAction = _persistence.findByPrimaryKey(newMDRAction.getPrimaryKey());

		Assert.assertEquals(existingMDRAction.getUuid(), newMDRAction.getUuid());
		Assert.assertEquals(existingMDRAction.getActionId(),
			newMDRAction.getActionId());
		Assert.assertEquals(existingMDRAction.getGroupId(),
			newMDRAction.getGroupId());
		Assert.assertEquals(existingMDRAction.getCompanyId(),
			newMDRAction.getCompanyId());
		Assert.assertEquals(existingMDRAction.getUserId(),
			newMDRAction.getUserId());
		Assert.assertEquals(existingMDRAction.getUserName(),
			newMDRAction.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingMDRAction.getCreateDate()),
			Time.getShortTimestamp(newMDRAction.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingMDRAction.getModifiedDate()),
			Time.getShortTimestamp(newMDRAction.getModifiedDate()));
		Assert.assertEquals(existingMDRAction.getClassNameId(),
			newMDRAction.getClassNameId());
		Assert.assertEquals(existingMDRAction.getClassPK(),
			newMDRAction.getClassPK());
		Assert.assertEquals(existingMDRAction.getRuleGroupInstanceId(),
			newMDRAction.getRuleGroupInstanceId());
		Assert.assertEquals(existingMDRAction.getName(), newMDRAction.getName());
		Assert.assertEquals(existingMDRAction.getDescription(),
			newMDRAction.getDescription());
		Assert.assertEquals(existingMDRAction.getType(), newMDRAction.getType());
		Assert.assertEquals(existingMDRAction.getTypeSettings(),
			newMDRAction.getTypeSettings());
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
	public void testCountByRuleGroupInstanceId() throws Exception {
		_persistence.countByRuleGroupInstanceId(RandomTestUtil.nextLong());

		_persistence.countByRuleGroupInstanceId(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		MDRAction newMDRAction = addMDRAction();

		MDRAction existingMDRAction = _persistence.findByPrimaryKey(newMDRAction.getPrimaryKey());

		Assert.assertEquals(existingMDRAction, newMDRAction);
	}

	@Test(expected = NoSuchActionException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<MDRAction> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("MDRAction", "uuid", true,
			"actionId", true, "groupId", true, "companyId", true, "userId",
			true, "userName", true, "createDate", true, "modifiedDate", true,
			"classNameId", true, "classPK", true, "ruleGroupInstanceId", true,
			"name", true, "description", true, "type", true, "typeSettings",
			true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		MDRAction newMDRAction = addMDRAction();

		MDRAction existingMDRAction = _persistence.fetchByPrimaryKey(newMDRAction.getPrimaryKey());

		Assert.assertEquals(existingMDRAction, newMDRAction);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MDRAction missingMDRAction = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingMDRAction);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		MDRAction newMDRAction1 = addMDRAction();
		MDRAction newMDRAction2 = addMDRAction();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newMDRAction1.getPrimaryKey());
		primaryKeys.add(newMDRAction2.getPrimaryKey());

		Map<Serializable, MDRAction> mdrActions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, mdrActions.size());
		Assert.assertEquals(newMDRAction1,
			mdrActions.get(newMDRAction1.getPrimaryKey()));
		Assert.assertEquals(newMDRAction2,
			mdrActions.get(newMDRAction2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, MDRAction> mdrActions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(mdrActions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		MDRAction newMDRAction = addMDRAction();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newMDRAction.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, MDRAction> mdrActions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, mdrActions.size());
		Assert.assertEquals(newMDRAction,
			mdrActions.get(newMDRAction.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, MDRAction> mdrActions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(mdrActions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		MDRAction newMDRAction = addMDRAction();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newMDRAction.getPrimaryKey());

		Map<Serializable, MDRAction> mdrActions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, mdrActions.size());
		Assert.assertEquals(newMDRAction,
			mdrActions.get(newMDRAction.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = MDRActionLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					MDRAction mdrAction = (MDRAction)object;

					Assert.assertNotNull(mdrAction);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		MDRAction newMDRAction = addMDRAction();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MDRAction.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("actionId",
				newMDRAction.getActionId()));

		List<MDRAction> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		MDRAction existingMDRAction = result.get(0);

		Assert.assertEquals(existingMDRAction, newMDRAction);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MDRAction.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("actionId",
				RandomTestUtil.nextLong()));

		List<MDRAction> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		MDRAction newMDRAction = addMDRAction();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MDRAction.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("actionId"));

		Object newActionId = newMDRAction.getActionId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("actionId",
				new Object[] { newActionId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingActionId = result.get(0);

		Assert.assertEquals(existingActionId, newActionId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MDRAction.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("actionId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("actionId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		MDRAction newMDRAction = addMDRAction();

		_persistence.clearCache();

		MDRAction existingMDRAction = _persistence.findByPrimaryKey(newMDRAction.getPrimaryKey());

		Assert.assertTrue(Validator.equals(existingMDRAction.getUuid(),
				ReflectionTestUtil.invoke(existingMDRAction, "getOriginalUuid",
					new Class<?>[0])));
		Assert.assertEquals(existingMDRAction.getGroupId(),
			ReflectionTestUtil.invoke(existingMDRAction, "getOriginalGroupId",
				new Class<?>[0]));
	}

	protected MDRAction addMDRAction() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MDRAction mdrAction = _persistence.create(pk);

		mdrAction.setUuid(RandomTestUtil.randomString());

		mdrAction.setGroupId(RandomTestUtil.nextLong());

		mdrAction.setCompanyId(RandomTestUtil.nextLong());

		mdrAction.setUserId(RandomTestUtil.nextLong());

		mdrAction.setUserName(RandomTestUtil.randomString());

		mdrAction.setCreateDate(RandomTestUtil.nextDate());

		mdrAction.setModifiedDate(RandomTestUtil.nextDate());

		mdrAction.setClassNameId(RandomTestUtil.nextLong());

		mdrAction.setClassPK(RandomTestUtil.nextLong());

		mdrAction.setRuleGroupInstanceId(RandomTestUtil.nextLong());

		mdrAction.setName(RandomTestUtil.randomString());

		mdrAction.setDescription(RandomTestUtil.randomString());

		mdrAction.setType(RandomTestUtil.randomString());

		mdrAction.setTypeSettings(RandomTestUtil.randomString());

		_mdrActions.add(_persistence.update(mdrAction));

		return mdrAction;
	}

	private List<MDRAction> _mdrActions = new ArrayList<MDRAction>();
	private MDRActionPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}