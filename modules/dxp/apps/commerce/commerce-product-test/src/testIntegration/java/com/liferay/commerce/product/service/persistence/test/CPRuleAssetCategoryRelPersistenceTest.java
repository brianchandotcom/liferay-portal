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

import com.liferay.commerce.product.exception.NoSuchCPRuleAssetCategoryRelException;
import com.liferay.commerce.product.model.CPRuleAssetCategoryRel;
import com.liferay.commerce.product.service.CPRuleAssetCategoryRelLocalServiceUtil;
import com.liferay.commerce.product.service.persistence.CPRuleAssetCategoryRelPersistence;
import com.liferay.commerce.product.service.persistence.CPRuleAssetCategoryRelUtil;

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
public class CPRuleAssetCategoryRelPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.commerce.product.service"));

	@Before
	public void setUp() {
		_persistence = CPRuleAssetCategoryRelUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CPRuleAssetCategoryRel> iterator = _cpRuleAssetCategoryRels.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPRuleAssetCategoryRel cpRuleAssetCategoryRel = _persistence.create(pk);

		Assert.assertNotNull(cpRuleAssetCategoryRel);

		Assert.assertEquals(cpRuleAssetCategoryRel.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CPRuleAssetCategoryRel newCPRuleAssetCategoryRel = addCPRuleAssetCategoryRel();

		_persistence.remove(newCPRuleAssetCategoryRel);

		CPRuleAssetCategoryRel existingCPRuleAssetCategoryRel = _persistence.fetchByPrimaryKey(newCPRuleAssetCategoryRel.getPrimaryKey());

		Assert.assertNull(existingCPRuleAssetCategoryRel);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCPRuleAssetCategoryRel();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPRuleAssetCategoryRel newCPRuleAssetCategoryRel = _persistence.create(pk);

		newCPRuleAssetCategoryRel.setGroupId(RandomTestUtil.nextLong());

		newCPRuleAssetCategoryRel.setCompanyId(RandomTestUtil.nextLong());

		newCPRuleAssetCategoryRel.setUserId(RandomTestUtil.nextLong());

		newCPRuleAssetCategoryRel.setUserName(RandomTestUtil.randomString());

		newCPRuleAssetCategoryRel.setCreateDate(RandomTestUtil.nextDate());

		newCPRuleAssetCategoryRel.setModifiedDate(RandomTestUtil.nextDate());

		newCPRuleAssetCategoryRel.setCPRuleId(RandomTestUtil.nextLong());

		newCPRuleAssetCategoryRel.setAssetCategoryId(RandomTestUtil.nextLong());

		_cpRuleAssetCategoryRels.add(_persistence.update(
				newCPRuleAssetCategoryRel));

		CPRuleAssetCategoryRel existingCPRuleAssetCategoryRel = _persistence.findByPrimaryKey(newCPRuleAssetCategoryRel.getPrimaryKey());

		Assert.assertEquals(existingCPRuleAssetCategoryRel.getCPRuleAssetCategoryRelId(),
			newCPRuleAssetCategoryRel.getCPRuleAssetCategoryRelId());
		Assert.assertEquals(existingCPRuleAssetCategoryRel.getGroupId(),
			newCPRuleAssetCategoryRel.getGroupId());
		Assert.assertEquals(existingCPRuleAssetCategoryRel.getCompanyId(),
			newCPRuleAssetCategoryRel.getCompanyId());
		Assert.assertEquals(existingCPRuleAssetCategoryRel.getUserId(),
			newCPRuleAssetCategoryRel.getUserId());
		Assert.assertEquals(existingCPRuleAssetCategoryRel.getUserName(),
			newCPRuleAssetCategoryRel.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCPRuleAssetCategoryRel.getCreateDate()),
			Time.getShortTimestamp(newCPRuleAssetCategoryRel.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCPRuleAssetCategoryRel.getModifiedDate()),
			Time.getShortTimestamp(newCPRuleAssetCategoryRel.getModifiedDate()));
		Assert.assertEquals(existingCPRuleAssetCategoryRel.getCPRuleId(),
			newCPRuleAssetCategoryRel.getCPRuleId());
		Assert.assertEquals(existingCPRuleAssetCategoryRel.getAssetCategoryId(),
			newCPRuleAssetCategoryRel.getAssetCategoryId());
	}

	@Test
	public void testCountByCPRuleId() throws Exception {
		_persistence.countByCPRuleId(RandomTestUtil.nextLong());

		_persistence.countByCPRuleId(0L);
	}

	@Test
	public void testCountByAssetCategoryId() throws Exception {
		_persistence.countByAssetCategoryId(RandomTestUtil.nextLong());

		_persistence.countByAssetCategoryId(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CPRuleAssetCategoryRel newCPRuleAssetCategoryRel = addCPRuleAssetCategoryRel();

		CPRuleAssetCategoryRel existingCPRuleAssetCategoryRel = _persistence.findByPrimaryKey(newCPRuleAssetCategoryRel.getPrimaryKey());

		Assert.assertEquals(existingCPRuleAssetCategoryRel,
			newCPRuleAssetCategoryRel);
	}

	@Test(expected = NoSuchCPRuleAssetCategoryRelException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<CPRuleAssetCategoryRel> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CPRuleAssetCategoryRel",
			"CPRuleAssetCategoryRelId", true, "groupId", true, "companyId",
			true, "userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "CPRuleId", true, "assetCategoryId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CPRuleAssetCategoryRel newCPRuleAssetCategoryRel = addCPRuleAssetCategoryRel();

		CPRuleAssetCategoryRel existingCPRuleAssetCategoryRel = _persistence.fetchByPrimaryKey(newCPRuleAssetCategoryRel.getPrimaryKey());

		Assert.assertEquals(existingCPRuleAssetCategoryRel,
			newCPRuleAssetCategoryRel);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPRuleAssetCategoryRel missingCPRuleAssetCategoryRel = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCPRuleAssetCategoryRel);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CPRuleAssetCategoryRel newCPRuleAssetCategoryRel1 = addCPRuleAssetCategoryRel();
		CPRuleAssetCategoryRel newCPRuleAssetCategoryRel2 = addCPRuleAssetCategoryRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCPRuleAssetCategoryRel1.getPrimaryKey());
		primaryKeys.add(newCPRuleAssetCategoryRel2.getPrimaryKey());

		Map<Serializable, CPRuleAssetCategoryRel> cpRuleAssetCategoryRels = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, cpRuleAssetCategoryRels.size());
		Assert.assertEquals(newCPRuleAssetCategoryRel1,
			cpRuleAssetCategoryRels.get(
				newCPRuleAssetCategoryRel1.getPrimaryKey()));
		Assert.assertEquals(newCPRuleAssetCategoryRel2,
			cpRuleAssetCategoryRels.get(
				newCPRuleAssetCategoryRel2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CPRuleAssetCategoryRel> cpRuleAssetCategoryRels = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(cpRuleAssetCategoryRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CPRuleAssetCategoryRel newCPRuleAssetCategoryRel = addCPRuleAssetCategoryRel();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCPRuleAssetCategoryRel.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CPRuleAssetCategoryRel> cpRuleAssetCategoryRels = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, cpRuleAssetCategoryRels.size());
		Assert.assertEquals(newCPRuleAssetCategoryRel,
			cpRuleAssetCategoryRels.get(
				newCPRuleAssetCategoryRel.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CPRuleAssetCategoryRel> cpRuleAssetCategoryRels = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(cpRuleAssetCategoryRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CPRuleAssetCategoryRel newCPRuleAssetCategoryRel = addCPRuleAssetCategoryRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCPRuleAssetCategoryRel.getPrimaryKey());

		Map<Serializable, CPRuleAssetCategoryRel> cpRuleAssetCategoryRels = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, cpRuleAssetCategoryRels.size());
		Assert.assertEquals(newCPRuleAssetCategoryRel,
			cpRuleAssetCategoryRels.get(
				newCPRuleAssetCategoryRel.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CPRuleAssetCategoryRelLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CPRuleAssetCategoryRel>() {
				@Override
				public void performAction(
					CPRuleAssetCategoryRel cpRuleAssetCategoryRel) {
					Assert.assertNotNull(cpRuleAssetCategoryRel);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CPRuleAssetCategoryRel newCPRuleAssetCategoryRel = addCPRuleAssetCategoryRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CPRuleAssetCategoryRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"CPRuleAssetCategoryRelId",
				newCPRuleAssetCategoryRel.getCPRuleAssetCategoryRelId()));

		List<CPRuleAssetCategoryRel> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CPRuleAssetCategoryRel existingCPRuleAssetCategoryRel = result.get(0);

		Assert.assertEquals(existingCPRuleAssetCategoryRel,
			newCPRuleAssetCategoryRel);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CPRuleAssetCategoryRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"CPRuleAssetCategoryRelId", RandomTestUtil.nextLong()));

		List<CPRuleAssetCategoryRel> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CPRuleAssetCategoryRel newCPRuleAssetCategoryRel = addCPRuleAssetCategoryRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CPRuleAssetCategoryRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"CPRuleAssetCategoryRelId"));

		Object newCPRuleAssetCategoryRelId = newCPRuleAssetCategoryRel.getCPRuleAssetCategoryRelId();

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"CPRuleAssetCategoryRelId",
				new Object[] { newCPRuleAssetCategoryRelId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCPRuleAssetCategoryRelId = result.get(0);

		Assert.assertEquals(existingCPRuleAssetCategoryRelId,
			newCPRuleAssetCategoryRelId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CPRuleAssetCategoryRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"CPRuleAssetCategoryRelId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"CPRuleAssetCategoryRelId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected CPRuleAssetCategoryRel addCPRuleAssetCategoryRel()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPRuleAssetCategoryRel cpRuleAssetCategoryRel = _persistence.create(pk);

		cpRuleAssetCategoryRel.setGroupId(RandomTestUtil.nextLong());

		cpRuleAssetCategoryRel.setCompanyId(RandomTestUtil.nextLong());

		cpRuleAssetCategoryRel.setUserId(RandomTestUtil.nextLong());

		cpRuleAssetCategoryRel.setUserName(RandomTestUtil.randomString());

		cpRuleAssetCategoryRel.setCreateDate(RandomTestUtil.nextDate());

		cpRuleAssetCategoryRel.setModifiedDate(RandomTestUtil.nextDate());

		cpRuleAssetCategoryRel.setCPRuleId(RandomTestUtil.nextLong());

		cpRuleAssetCategoryRel.setAssetCategoryId(RandomTestUtil.nextLong());

		_cpRuleAssetCategoryRels.add(_persistence.update(cpRuleAssetCategoryRel));

		return cpRuleAssetCategoryRel;
	}

	private List<CPRuleAssetCategoryRel> _cpRuleAssetCategoryRels = new ArrayList<CPRuleAssetCategoryRel>();
	private CPRuleAssetCategoryRelPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}