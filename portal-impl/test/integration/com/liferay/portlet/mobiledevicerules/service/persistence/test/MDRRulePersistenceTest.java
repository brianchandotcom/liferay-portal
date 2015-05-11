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

import com.liferay.portlet.mobiledevicerules.NoSuchRuleException;
import com.liferay.portlet.mobiledevicerules.model.MDRRule;
import com.liferay.portlet.mobiledevicerules.service.MDRRuleLocalServiceUtil;
import com.liferay.portlet.mobiledevicerules.service.persistence.MDRRulePersistence;
import com.liferay.portlet.mobiledevicerules.service.persistence.MDRRuleUtil;

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
public class MDRRulePersistenceTest {
	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = MDRRuleUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<MDRRule> iterator = _mdrRules.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MDRRule mdrRule = _persistence.create(pk);

		Assert.assertNotNull(mdrRule);

		Assert.assertEquals(mdrRule.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		MDRRule newMDRRule = addMDRRule();

		_persistence.remove(newMDRRule);

		MDRRule existingMDRRule = _persistence.fetchByPrimaryKey(newMDRRule.getPrimaryKey());

		Assert.assertNull(existingMDRRule);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addMDRRule();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MDRRule newMDRRule = _persistence.create(pk);

		newMDRRule.setUuid(RandomTestUtil.randomString());

		newMDRRule.setGroupId(RandomTestUtil.nextLong());

		newMDRRule.setCompanyId(RandomTestUtil.nextLong());

		newMDRRule.setUserId(RandomTestUtil.nextLong());

		newMDRRule.setUserName(RandomTestUtil.randomString());

		newMDRRule.setCreateDate(RandomTestUtil.nextDate());

		newMDRRule.setModifiedDate(RandomTestUtil.nextDate());

		newMDRRule.setRuleGroupId(RandomTestUtil.nextLong());

		newMDRRule.setName(RandomTestUtil.randomString());

		newMDRRule.setDescription(RandomTestUtil.randomString());

		newMDRRule.setType(RandomTestUtil.randomString());

		newMDRRule.setTypeSettings(RandomTestUtil.randomString());

		_mdrRules.add(_persistence.update(newMDRRule));

		MDRRule existingMDRRule = _persistence.findByPrimaryKey(newMDRRule.getPrimaryKey());

		Assert.assertEquals(existingMDRRule.getUuid(), newMDRRule.getUuid());
		Assert.assertEquals(existingMDRRule.getRuleId(), newMDRRule.getRuleId());
		Assert.assertEquals(existingMDRRule.getGroupId(),
			newMDRRule.getGroupId());
		Assert.assertEquals(existingMDRRule.getCompanyId(),
			newMDRRule.getCompanyId());
		Assert.assertEquals(existingMDRRule.getUserId(), newMDRRule.getUserId());
		Assert.assertEquals(existingMDRRule.getUserName(),
			newMDRRule.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingMDRRule.getCreateDate()),
			Time.getShortTimestamp(newMDRRule.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingMDRRule.getModifiedDate()),
			Time.getShortTimestamp(newMDRRule.getModifiedDate()));
		Assert.assertEquals(existingMDRRule.getRuleGroupId(),
			newMDRRule.getRuleGroupId());
		Assert.assertEquals(existingMDRRule.getName(), newMDRRule.getName());
		Assert.assertEquals(existingMDRRule.getDescription(),
			newMDRRule.getDescription());
		Assert.assertEquals(existingMDRRule.getType(), newMDRRule.getType());
		Assert.assertEquals(existingMDRRule.getTypeSettings(),
			newMDRRule.getTypeSettings());
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
	public void testCountByRuleGroupId() throws Exception {
		_persistence.countByRuleGroupId(RandomTestUtil.nextLong());

		_persistence.countByRuleGroupId(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		MDRRule newMDRRule = addMDRRule();

		MDRRule existingMDRRule = _persistence.findByPrimaryKey(newMDRRule.getPrimaryKey());

		Assert.assertEquals(existingMDRRule, newMDRRule);
	}

	@Test(expected = NoSuchRuleException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<MDRRule> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("MDRRule", "uuid", true,
			"ruleId", true, "groupId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true,
			"ruleGroupId", true, "name", true, "description", true, "type",
			true, "typeSettings", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		MDRRule newMDRRule = addMDRRule();

		MDRRule existingMDRRule = _persistence.fetchByPrimaryKey(newMDRRule.getPrimaryKey());

		Assert.assertEquals(existingMDRRule, newMDRRule);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MDRRule missingMDRRule = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingMDRRule);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		MDRRule newMDRRule1 = addMDRRule();
		MDRRule newMDRRule2 = addMDRRule();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newMDRRule1.getPrimaryKey());
		primaryKeys.add(newMDRRule2.getPrimaryKey());

		Map<Serializable, MDRRule> mdrRules = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, mdrRules.size());
		Assert.assertEquals(newMDRRule1,
			mdrRules.get(newMDRRule1.getPrimaryKey()));
		Assert.assertEquals(newMDRRule2,
			mdrRules.get(newMDRRule2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, MDRRule> mdrRules = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(mdrRules.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		MDRRule newMDRRule = addMDRRule();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newMDRRule.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, MDRRule> mdrRules = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, mdrRules.size());
		Assert.assertEquals(newMDRRule, mdrRules.get(newMDRRule.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, MDRRule> mdrRules = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(mdrRules.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		MDRRule newMDRRule = addMDRRule();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newMDRRule.getPrimaryKey());

		Map<Serializable, MDRRule> mdrRules = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, mdrRules.size());
		Assert.assertEquals(newMDRRule, mdrRules.get(newMDRRule.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = MDRRuleLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					MDRRule mdrRule = (MDRRule)object;

					Assert.assertNotNull(mdrRule);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		MDRRule newMDRRule = addMDRRule();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MDRRule.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("ruleId",
				newMDRRule.getRuleId()));

		List<MDRRule> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		MDRRule existingMDRRule = result.get(0);

		Assert.assertEquals(existingMDRRule, newMDRRule);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MDRRule.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("ruleId",
				RandomTestUtil.nextLong()));

		List<MDRRule> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		MDRRule newMDRRule = addMDRRule();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MDRRule.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("ruleId"));

		Object newRuleId = newMDRRule.getRuleId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("ruleId",
				new Object[] { newRuleId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingRuleId = result.get(0);

		Assert.assertEquals(existingRuleId, newRuleId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MDRRule.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("ruleId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("ruleId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		MDRRule newMDRRule = addMDRRule();

		_persistence.clearCache();

		MDRRule existingMDRRule = _persistence.findByPrimaryKey(newMDRRule.getPrimaryKey());

		Assert.assertTrue(Validator.equals(existingMDRRule.getUuid(),
				ReflectionTestUtil.invoke(existingMDRRule, "getOriginalUuid",
					new Class<?>[0])));
		Assert.assertEquals(existingMDRRule.getGroupId(),
			ReflectionTestUtil.invoke(existingMDRRule, "getOriginalGroupId",
				new Class<?>[0]));
	}

	protected MDRRule addMDRRule() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MDRRule mdrRule = _persistence.create(pk);

		mdrRule.setUuid(RandomTestUtil.randomString());

		mdrRule.setGroupId(RandomTestUtil.nextLong());

		mdrRule.setCompanyId(RandomTestUtil.nextLong());

		mdrRule.setUserId(RandomTestUtil.nextLong());

		mdrRule.setUserName(RandomTestUtil.randomString());

		mdrRule.setCreateDate(RandomTestUtil.nextDate());

		mdrRule.setModifiedDate(RandomTestUtil.nextDate());

		mdrRule.setRuleGroupId(RandomTestUtil.nextLong());

		mdrRule.setName(RandomTestUtil.randomString());

		mdrRule.setDescription(RandomTestUtil.randomString());

		mdrRule.setType(RandomTestUtil.randomString());

		mdrRule.setTypeSettings(RandomTestUtil.randomString());

		_mdrRules.add(_persistence.update(mdrRule));

		return mdrRule;
	}

	private List<MDRRule> _mdrRules = new ArrayList<MDRRule>();
	private MDRRulePersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}