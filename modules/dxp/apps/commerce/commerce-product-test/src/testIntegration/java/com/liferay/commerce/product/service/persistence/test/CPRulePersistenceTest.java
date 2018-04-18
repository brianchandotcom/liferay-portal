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

package com.liferay.commerce.product.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.commerce.product.exception.NoSuchCPRuleException;
import com.liferay.commerce.product.model.CPRule;
import com.liferay.commerce.product.service.CPRuleLocalServiceUtil;
import com.liferay.commerce.product.service.persistence.CPRulePersistence;
import com.liferay.commerce.product.service.persistence.CPRuleUtil;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
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
public class CPRulePersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.commerce.product.service"));

	@Before
	public void setUp() {
		_persistence = CPRuleUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CPRule> iterator = _cpRules.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPRule cpRule = _persistence.create(pk);

		Assert.assertNotNull(cpRule);

		Assert.assertEquals(cpRule.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CPRule newCPRule = addCPRule();

		_persistence.remove(newCPRule);

		CPRule existingCPRule = _persistence.fetchByPrimaryKey(newCPRule.getPrimaryKey());

		Assert.assertNull(existingCPRule);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCPRule();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPRule newCPRule = _persistence.create(pk);

		newCPRule.setGroupId(RandomTestUtil.nextLong());

		newCPRule.setCompanyId(RandomTestUtil.nextLong());

		newCPRule.setUserId(RandomTestUtil.nextLong());

		newCPRule.setUserName(RandomTestUtil.randomString());

		newCPRule.setCreateDate(RandomTestUtil.nextDate());

		newCPRule.setModifiedDate(RandomTestUtil.nextDate());

		newCPRule.setName(RandomTestUtil.randomString());

		newCPRule.setActive(RandomTestUtil.randomBoolean());

		newCPRule.setType(RandomTestUtil.randomString());

		newCPRule.setTypeSettings(RandomTestUtil.randomString());

		_cpRules.add(_persistence.update(newCPRule));

		CPRule existingCPRule = _persistence.findByPrimaryKey(newCPRule.getPrimaryKey());

		Assert.assertEquals(existingCPRule.getCPRuleId(),
			newCPRule.getCPRuleId());
		Assert.assertEquals(existingCPRule.getGroupId(), newCPRule.getGroupId());
		Assert.assertEquals(existingCPRule.getCompanyId(),
			newCPRule.getCompanyId());
		Assert.assertEquals(existingCPRule.getUserId(), newCPRule.getUserId());
		Assert.assertEquals(existingCPRule.getUserName(),
			newCPRule.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCPRule.getCreateDate()),
			Time.getShortTimestamp(newCPRule.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCPRule.getModifiedDate()),
			Time.getShortTimestamp(newCPRule.getModifiedDate()));
		Assert.assertEquals(existingCPRule.getName(), newCPRule.getName());
		Assert.assertEquals(existingCPRule.getActive(), newCPRule.getActive());
		Assert.assertEquals(existingCPRule.getType(), newCPRule.getType());
		Assert.assertEquals(existingCPRule.getTypeSettings(),
			newCPRule.getTypeSettings());
	}

	@Test
	public void testCountByGroupId() throws Exception {
		_persistence.countByGroupId(RandomTestUtil.nextLong());

		_persistence.countByGroupId(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CPRule newCPRule = addCPRule();

		CPRule existingCPRule = _persistence.findByPrimaryKey(newCPRule.getPrimaryKey());

		Assert.assertEquals(existingCPRule, newCPRule);
	}

	@Test(expected = NoSuchCPRuleException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<CPRule> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CPRule", "CPRuleId", true,
			"groupId", true, "companyId", true, "userId", true, "userName",
			true, "createDate", true, "modifiedDate", true, "name", true,
			"active", true, "type", true, "typeSettings", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CPRule newCPRule = addCPRule();

		CPRule existingCPRule = _persistence.fetchByPrimaryKey(newCPRule.getPrimaryKey());

		Assert.assertEquals(existingCPRule, newCPRule);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPRule missingCPRule = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCPRule);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CPRule newCPRule1 = addCPRule();
		CPRule newCPRule2 = addCPRule();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCPRule1.getPrimaryKey());
		primaryKeys.add(newCPRule2.getPrimaryKey());

		Map<Serializable, CPRule> cpRules = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, cpRules.size());
		Assert.assertEquals(newCPRule1, cpRules.get(newCPRule1.getPrimaryKey()));
		Assert.assertEquals(newCPRule2, cpRules.get(newCPRule2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CPRule> cpRules = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(cpRules.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CPRule newCPRule = addCPRule();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCPRule.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CPRule> cpRules = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, cpRules.size());
		Assert.assertEquals(newCPRule, cpRules.get(newCPRule.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CPRule> cpRules = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(cpRules.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CPRule newCPRule = addCPRule();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCPRule.getPrimaryKey());

		Map<Serializable, CPRule> cpRules = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, cpRules.size());
		Assert.assertEquals(newCPRule, cpRules.get(newCPRule.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CPRuleLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CPRule>() {
				@Override
				public void performAction(CPRule cpRule) {
					Assert.assertNotNull(cpRule);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CPRule newCPRule = addCPRule();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CPRule.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("CPRuleId",
				newCPRule.getCPRuleId()));

		List<CPRule> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CPRule existingCPRule = result.get(0);

		Assert.assertEquals(existingCPRule, newCPRule);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CPRule.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("CPRuleId",
				RandomTestUtil.nextLong()));

		List<CPRule> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CPRule newCPRule = addCPRule();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CPRule.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("CPRuleId"));

		Object newCPRuleId = newCPRule.getCPRuleId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("CPRuleId",
				new Object[] { newCPRuleId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCPRuleId = result.get(0);

		Assert.assertEquals(existingCPRuleId, newCPRuleId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CPRule.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("CPRuleId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("CPRuleId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected CPRule addCPRule() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPRule cpRule = _persistence.create(pk);

		cpRule.setGroupId(RandomTestUtil.nextLong());

		cpRule.setCompanyId(RandomTestUtil.nextLong());

		cpRule.setUserId(RandomTestUtil.nextLong());

		cpRule.setUserName(RandomTestUtil.randomString());

		cpRule.setCreateDate(RandomTestUtil.nextDate());

		cpRule.setModifiedDate(RandomTestUtil.nextDate());

		cpRule.setName(RandomTestUtil.randomString());

		cpRule.setActive(RandomTestUtil.randomBoolean());

		cpRule.setType(RandomTestUtil.randomString());

		cpRule.setTypeSettings(RandomTestUtil.randomString());

		_cpRules.add(_persistence.update(cpRule));

		return cpRule;
	}

	private List<CPRule> _cpRules = new ArrayList<CPRule>();
	private CPRulePersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}