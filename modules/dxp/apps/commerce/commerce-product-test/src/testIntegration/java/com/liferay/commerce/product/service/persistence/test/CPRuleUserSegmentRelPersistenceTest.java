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

import com.liferay.commerce.product.exception.NoSuchCPRuleUserSegmentRelException;
import com.liferay.commerce.product.model.CPRuleUserSegmentRel;
import com.liferay.commerce.product.service.CPRuleUserSegmentRelLocalServiceUtil;
import com.liferay.commerce.product.service.persistence.CPRuleUserSegmentRelPersistence;
import com.liferay.commerce.product.service.persistence.CPRuleUserSegmentRelUtil;

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
public class CPRuleUserSegmentRelPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.commerce.product.service"));

	@Before
	public void setUp() {
		_persistence = CPRuleUserSegmentRelUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CPRuleUserSegmentRel> iterator = _cpRuleUserSegmentRels.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPRuleUserSegmentRel cpRuleUserSegmentRel = _persistence.create(pk);

		Assert.assertNotNull(cpRuleUserSegmentRel);

		Assert.assertEquals(cpRuleUserSegmentRel.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CPRuleUserSegmentRel newCPRuleUserSegmentRel = addCPRuleUserSegmentRel();

		_persistence.remove(newCPRuleUserSegmentRel);

		CPRuleUserSegmentRel existingCPRuleUserSegmentRel = _persistence.fetchByPrimaryKey(newCPRuleUserSegmentRel.getPrimaryKey());

		Assert.assertNull(existingCPRuleUserSegmentRel);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCPRuleUserSegmentRel();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPRuleUserSegmentRel newCPRuleUserSegmentRel = _persistence.create(pk);

		newCPRuleUserSegmentRel.setGroupId(RandomTestUtil.nextLong());

		newCPRuleUserSegmentRel.setCompanyId(RandomTestUtil.nextLong());

		newCPRuleUserSegmentRel.setUserId(RandomTestUtil.nextLong());

		newCPRuleUserSegmentRel.setUserName(RandomTestUtil.randomString());

		newCPRuleUserSegmentRel.setCreateDate(RandomTestUtil.nextDate());

		newCPRuleUserSegmentRel.setModifiedDate(RandomTestUtil.nextDate());

		newCPRuleUserSegmentRel.setCPRuleId(RandomTestUtil.nextLong());

		newCPRuleUserSegmentRel.setCommerceUserSegmentEntryId(RandomTestUtil.randomBoolean());

		_cpRuleUserSegmentRels.add(_persistence.update(newCPRuleUserSegmentRel));

		CPRuleUserSegmentRel existingCPRuleUserSegmentRel = _persistence.findByPrimaryKey(newCPRuleUserSegmentRel.getPrimaryKey());

		Assert.assertEquals(existingCPRuleUserSegmentRel.getCPRuleUserSegmentRelId(),
			newCPRuleUserSegmentRel.getCPRuleUserSegmentRelId());
		Assert.assertEquals(existingCPRuleUserSegmentRel.getGroupId(),
			newCPRuleUserSegmentRel.getGroupId());
		Assert.assertEquals(existingCPRuleUserSegmentRel.getCompanyId(),
			newCPRuleUserSegmentRel.getCompanyId());
		Assert.assertEquals(existingCPRuleUserSegmentRel.getUserId(),
			newCPRuleUserSegmentRel.getUserId());
		Assert.assertEquals(existingCPRuleUserSegmentRel.getUserName(),
			newCPRuleUserSegmentRel.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCPRuleUserSegmentRel.getCreateDate()),
			Time.getShortTimestamp(newCPRuleUserSegmentRel.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCPRuleUserSegmentRel.getModifiedDate()),
			Time.getShortTimestamp(newCPRuleUserSegmentRel.getModifiedDate()));
		Assert.assertEquals(existingCPRuleUserSegmentRel.getCPRuleId(),
			newCPRuleUserSegmentRel.getCPRuleId());
		Assert.assertEquals(existingCPRuleUserSegmentRel.isCommerceUserSegmentEntryId(),
			newCPRuleUserSegmentRel.isCommerceUserSegmentEntryId());
	}

	@Test
	public void testCountByCPRuleId() throws Exception {
		_persistence.countByCPRuleId(RandomTestUtil.nextLong());

		_persistence.countByCPRuleId(0L);
	}

	@Test
	public void testCountBycommerceUserSegmentEntryId()
		throws Exception {
		_persistence.countBycommerceUserSegmentEntryId(RandomTestUtil.randomBoolean());

		_persistence.countBycommerceUserSegmentEntryId(RandomTestUtil.randomBoolean());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CPRuleUserSegmentRel newCPRuleUserSegmentRel = addCPRuleUserSegmentRel();

		CPRuleUserSegmentRel existingCPRuleUserSegmentRel = _persistence.findByPrimaryKey(newCPRuleUserSegmentRel.getPrimaryKey());

		Assert.assertEquals(existingCPRuleUserSegmentRel,
			newCPRuleUserSegmentRel);
	}

	@Test(expected = NoSuchCPRuleUserSegmentRelException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<CPRuleUserSegmentRel> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CPRuleUserSegmentRel",
			"CPRuleUserSegmentRelId", true, "groupId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "CPRuleId", true,
			"commerceUserSegmentEntryId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CPRuleUserSegmentRel newCPRuleUserSegmentRel = addCPRuleUserSegmentRel();

		CPRuleUserSegmentRel existingCPRuleUserSegmentRel = _persistence.fetchByPrimaryKey(newCPRuleUserSegmentRel.getPrimaryKey());

		Assert.assertEquals(existingCPRuleUserSegmentRel,
			newCPRuleUserSegmentRel);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPRuleUserSegmentRel missingCPRuleUserSegmentRel = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCPRuleUserSegmentRel);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CPRuleUserSegmentRel newCPRuleUserSegmentRel1 = addCPRuleUserSegmentRel();
		CPRuleUserSegmentRel newCPRuleUserSegmentRel2 = addCPRuleUserSegmentRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCPRuleUserSegmentRel1.getPrimaryKey());
		primaryKeys.add(newCPRuleUserSegmentRel2.getPrimaryKey());

		Map<Serializable, CPRuleUserSegmentRel> cpRuleUserSegmentRels = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, cpRuleUserSegmentRels.size());
		Assert.assertEquals(newCPRuleUserSegmentRel1,
			cpRuleUserSegmentRels.get(newCPRuleUserSegmentRel1.getPrimaryKey()));
		Assert.assertEquals(newCPRuleUserSegmentRel2,
			cpRuleUserSegmentRels.get(newCPRuleUserSegmentRel2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CPRuleUserSegmentRel> cpRuleUserSegmentRels = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(cpRuleUserSegmentRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CPRuleUserSegmentRel newCPRuleUserSegmentRel = addCPRuleUserSegmentRel();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCPRuleUserSegmentRel.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CPRuleUserSegmentRel> cpRuleUserSegmentRels = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, cpRuleUserSegmentRels.size());
		Assert.assertEquals(newCPRuleUserSegmentRel,
			cpRuleUserSegmentRels.get(newCPRuleUserSegmentRel.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CPRuleUserSegmentRel> cpRuleUserSegmentRels = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(cpRuleUserSegmentRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CPRuleUserSegmentRel newCPRuleUserSegmentRel = addCPRuleUserSegmentRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCPRuleUserSegmentRel.getPrimaryKey());

		Map<Serializable, CPRuleUserSegmentRel> cpRuleUserSegmentRels = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, cpRuleUserSegmentRels.size());
		Assert.assertEquals(newCPRuleUserSegmentRel,
			cpRuleUserSegmentRels.get(newCPRuleUserSegmentRel.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CPRuleUserSegmentRelLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CPRuleUserSegmentRel>() {
				@Override
				public void performAction(
					CPRuleUserSegmentRel cpRuleUserSegmentRel) {
					Assert.assertNotNull(cpRuleUserSegmentRel);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CPRuleUserSegmentRel newCPRuleUserSegmentRel = addCPRuleUserSegmentRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CPRuleUserSegmentRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("CPRuleUserSegmentRelId",
				newCPRuleUserSegmentRel.getCPRuleUserSegmentRelId()));

		List<CPRuleUserSegmentRel> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CPRuleUserSegmentRel existingCPRuleUserSegmentRel = result.get(0);

		Assert.assertEquals(existingCPRuleUserSegmentRel,
			newCPRuleUserSegmentRel);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CPRuleUserSegmentRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("CPRuleUserSegmentRelId",
				RandomTestUtil.nextLong()));

		List<CPRuleUserSegmentRel> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CPRuleUserSegmentRel newCPRuleUserSegmentRel = addCPRuleUserSegmentRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CPRuleUserSegmentRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"CPRuleUserSegmentRelId"));

		Object newCPRuleUserSegmentRelId = newCPRuleUserSegmentRel.getCPRuleUserSegmentRelId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("CPRuleUserSegmentRelId",
				new Object[] { newCPRuleUserSegmentRelId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCPRuleUserSegmentRelId = result.get(0);

		Assert.assertEquals(existingCPRuleUserSegmentRelId,
			newCPRuleUserSegmentRelId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CPRuleUserSegmentRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"CPRuleUserSegmentRelId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("CPRuleUserSegmentRelId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected CPRuleUserSegmentRel addCPRuleUserSegmentRel()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPRuleUserSegmentRel cpRuleUserSegmentRel = _persistence.create(pk);

		cpRuleUserSegmentRel.setGroupId(RandomTestUtil.nextLong());

		cpRuleUserSegmentRel.setCompanyId(RandomTestUtil.nextLong());

		cpRuleUserSegmentRel.setUserId(RandomTestUtil.nextLong());

		cpRuleUserSegmentRel.setUserName(RandomTestUtil.randomString());

		cpRuleUserSegmentRel.setCreateDate(RandomTestUtil.nextDate());

		cpRuleUserSegmentRel.setModifiedDate(RandomTestUtil.nextDate());

		cpRuleUserSegmentRel.setCPRuleId(RandomTestUtil.nextLong());

		cpRuleUserSegmentRel.setCommerceUserSegmentEntryId(RandomTestUtil.randomBoolean());

		_cpRuleUserSegmentRels.add(_persistence.update(cpRuleUserSegmentRel));

		return cpRuleUserSegmentRel;
	}

	private List<CPRuleUserSegmentRel> _cpRuleUserSegmentRels = new ArrayList<CPRuleUserSegmentRel>();
	private CPRuleUserSegmentRelPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}