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

package com.liferay.commerce.shipping.engine.fixed.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.commerce.shipping.engine.fixed.exception.NoSuchCShippingFixedOptionRelException;
import com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel;
import com.liferay.commerce.shipping.engine.fixed.service.CShippingFixedOptionRelLocalServiceUtil;
import com.liferay.commerce.shipping.engine.fixed.service.persistence.CShippingFixedOptionRelPersistence;
import com.liferay.commerce.shipping.engine.fixed.service.persistence.CShippingFixedOptionRelUtil;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.AssertUtils;
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
public class CShippingFixedOptionRelPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.commerce.shipping.engine.fixed.service"));

	@Before
	public void setUp() {
		_persistence = CShippingFixedOptionRelUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CShippingFixedOptionRel> iterator = _cShippingFixedOptionRels.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CShippingFixedOptionRel cShippingFixedOptionRel = _persistence.create(pk);

		Assert.assertNotNull(cShippingFixedOptionRel);

		Assert.assertEquals(cShippingFixedOptionRel.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CShippingFixedOptionRel newCShippingFixedOptionRel = addCShippingFixedOptionRel();

		_persistence.remove(newCShippingFixedOptionRel);

		CShippingFixedOptionRel existingCShippingFixedOptionRel = _persistence.fetchByPrimaryKey(newCShippingFixedOptionRel.getPrimaryKey());

		Assert.assertNull(existingCShippingFixedOptionRel);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCShippingFixedOptionRel();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CShippingFixedOptionRel newCShippingFixedOptionRel = _persistence.create(pk);

		newCShippingFixedOptionRel.setGroupId(RandomTestUtil.nextLong());

		newCShippingFixedOptionRel.setCompanyId(RandomTestUtil.nextLong());

		newCShippingFixedOptionRel.setUserId(RandomTestUtil.nextLong());

		newCShippingFixedOptionRel.setUserName(RandomTestUtil.randomString());

		newCShippingFixedOptionRel.setCreateDate(RandomTestUtil.nextDate());

		newCShippingFixedOptionRel.setModifiedDate(RandomTestUtil.nextDate());

		newCShippingFixedOptionRel.setCommerceShippingMethodId(RandomTestUtil.nextLong());

		newCShippingFixedOptionRel.setCommerceShippingFixedOptionId(RandomTestUtil.nextLong());

		newCShippingFixedOptionRel.setCommerceWarehouseId(RandomTestUtil.nextLong());

		newCShippingFixedOptionRel.setCommerceCountryId(RandomTestUtil.nextLong());

		newCShippingFixedOptionRel.setCommerceRegionId(RandomTestUtil.nextLong());

		newCShippingFixedOptionRel.setZip(RandomTestUtil.randomString());

		newCShippingFixedOptionRel.setWeightFrom(RandomTestUtil.nextDouble());

		newCShippingFixedOptionRel.setWeightTo(RandomTestUtil.nextDouble());

		newCShippingFixedOptionRel.setFixedPrice(RandomTestUtil.nextDouble());

		newCShippingFixedOptionRel.setRateUnitWeightPrice(RandomTestUtil.nextDouble());

		newCShippingFixedOptionRel.setRatePercentage(RandomTestUtil.nextDouble());

		_cShippingFixedOptionRels.add(_persistence.update(
				newCShippingFixedOptionRel));

		CShippingFixedOptionRel existingCShippingFixedOptionRel = _persistence.findByPrimaryKey(newCShippingFixedOptionRel.getPrimaryKey());

		Assert.assertEquals(existingCShippingFixedOptionRel.getCShippingFixedOptionRelId(),
			newCShippingFixedOptionRel.getCShippingFixedOptionRelId());
		Assert.assertEquals(existingCShippingFixedOptionRel.getGroupId(),
			newCShippingFixedOptionRel.getGroupId());
		Assert.assertEquals(existingCShippingFixedOptionRel.getCompanyId(),
			newCShippingFixedOptionRel.getCompanyId());
		Assert.assertEquals(existingCShippingFixedOptionRel.getUserId(),
			newCShippingFixedOptionRel.getUserId());
		Assert.assertEquals(existingCShippingFixedOptionRel.getUserName(),
			newCShippingFixedOptionRel.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCShippingFixedOptionRel.getCreateDate()),
			Time.getShortTimestamp(newCShippingFixedOptionRel.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCShippingFixedOptionRel.getModifiedDate()),
			Time.getShortTimestamp(newCShippingFixedOptionRel.getModifiedDate()));
		Assert.assertEquals(existingCShippingFixedOptionRel.getCommerceShippingMethodId(),
			newCShippingFixedOptionRel.getCommerceShippingMethodId());
		Assert.assertEquals(existingCShippingFixedOptionRel.getCommerceShippingFixedOptionId(),
			newCShippingFixedOptionRel.getCommerceShippingFixedOptionId());
		Assert.assertEquals(existingCShippingFixedOptionRel.getCommerceWarehouseId(),
			newCShippingFixedOptionRel.getCommerceWarehouseId());
		Assert.assertEquals(existingCShippingFixedOptionRel.getCommerceCountryId(),
			newCShippingFixedOptionRel.getCommerceCountryId());
		Assert.assertEquals(existingCShippingFixedOptionRel.getCommerceRegionId(),
			newCShippingFixedOptionRel.getCommerceRegionId());
		Assert.assertEquals(existingCShippingFixedOptionRel.getZip(),
			newCShippingFixedOptionRel.getZip());
		AssertUtils.assertEquals(existingCShippingFixedOptionRel.getWeightFrom(),
			newCShippingFixedOptionRel.getWeightFrom());
		AssertUtils.assertEquals(existingCShippingFixedOptionRel.getWeightTo(),
			newCShippingFixedOptionRel.getWeightTo());
		AssertUtils.assertEquals(existingCShippingFixedOptionRel.getFixedPrice(),
			newCShippingFixedOptionRel.getFixedPrice());
		AssertUtils.assertEquals(existingCShippingFixedOptionRel.getRateUnitWeightPrice(),
			newCShippingFixedOptionRel.getRateUnitWeightPrice());
		AssertUtils.assertEquals(existingCShippingFixedOptionRel.getRatePercentage(),
			newCShippingFixedOptionRel.getRatePercentage());
	}

	@Test
	public void testCountByCommerceShippingMethodId() throws Exception {
		_persistence.countByCommerceShippingMethodId(RandomTestUtil.nextLong());

		_persistence.countByCommerceShippingMethodId(0L);
	}

	@Test
	public void testCountByCommerceShippingFixedOptionId()
		throws Exception {
		_persistence.countByCommerceShippingFixedOptionId(RandomTestUtil.nextLong());

		_persistence.countByCommerceShippingFixedOptionId(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CShippingFixedOptionRel newCShippingFixedOptionRel = addCShippingFixedOptionRel();

		CShippingFixedOptionRel existingCShippingFixedOptionRel = _persistence.findByPrimaryKey(newCShippingFixedOptionRel.getPrimaryKey());

		Assert.assertEquals(existingCShippingFixedOptionRel,
			newCShippingFixedOptionRel);
	}

	@Test(expected = NoSuchCShippingFixedOptionRelException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<CShippingFixedOptionRel> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CShippingFixedOptionRel",
			"CShippingFixedOptionRelId", true, "groupId", true, "companyId",
			true, "userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "commerceShippingMethodId", true,
			"commerceShippingFixedOptionId", true, "commerceWarehouseId", true,
			"commerceCountryId", true, "commerceRegionId", true, "zip", true,
			"weightFrom", true, "weightTo", true, "fixedPrice", true,
			"rateUnitWeightPrice", true, "ratePercentage", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CShippingFixedOptionRel newCShippingFixedOptionRel = addCShippingFixedOptionRel();

		CShippingFixedOptionRel existingCShippingFixedOptionRel = _persistence.fetchByPrimaryKey(newCShippingFixedOptionRel.getPrimaryKey());

		Assert.assertEquals(existingCShippingFixedOptionRel,
			newCShippingFixedOptionRel);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CShippingFixedOptionRel missingCShippingFixedOptionRel = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCShippingFixedOptionRel);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CShippingFixedOptionRel newCShippingFixedOptionRel1 = addCShippingFixedOptionRel();
		CShippingFixedOptionRel newCShippingFixedOptionRel2 = addCShippingFixedOptionRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCShippingFixedOptionRel1.getPrimaryKey());
		primaryKeys.add(newCShippingFixedOptionRel2.getPrimaryKey());

		Map<Serializable, CShippingFixedOptionRel> cShippingFixedOptionRels = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, cShippingFixedOptionRels.size());
		Assert.assertEquals(newCShippingFixedOptionRel1,
			cShippingFixedOptionRels.get(
				newCShippingFixedOptionRel1.getPrimaryKey()));
		Assert.assertEquals(newCShippingFixedOptionRel2,
			cShippingFixedOptionRels.get(
				newCShippingFixedOptionRel2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CShippingFixedOptionRel> cShippingFixedOptionRels = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(cShippingFixedOptionRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CShippingFixedOptionRel newCShippingFixedOptionRel = addCShippingFixedOptionRel();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCShippingFixedOptionRel.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CShippingFixedOptionRel> cShippingFixedOptionRels = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, cShippingFixedOptionRels.size());
		Assert.assertEquals(newCShippingFixedOptionRel,
			cShippingFixedOptionRels.get(
				newCShippingFixedOptionRel.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CShippingFixedOptionRel> cShippingFixedOptionRels = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(cShippingFixedOptionRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CShippingFixedOptionRel newCShippingFixedOptionRel = addCShippingFixedOptionRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCShippingFixedOptionRel.getPrimaryKey());

		Map<Serializable, CShippingFixedOptionRel> cShippingFixedOptionRels = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, cShippingFixedOptionRels.size());
		Assert.assertEquals(newCShippingFixedOptionRel,
			cShippingFixedOptionRels.get(
				newCShippingFixedOptionRel.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CShippingFixedOptionRelLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CShippingFixedOptionRel>() {
				@Override
				public void performAction(
					CShippingFixedOptionRel cShippingFixedOptionRel) {
					Assert.assertNotNull(cShippingFixedOptionRel);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CShippingFixedOptionRel newCShippingFixedOptionRel = addCShippingFixedOptionRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CShippingFixedOptionRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"CShippingFixedOptionRelId",
				newCShippingFixedOptionRel.getCShippingFixedOptionRelId()));

		List<CShippingFixedOptionRel> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CShippingFixedOptionRel existingCShippingFixedOptionRel = result.get(0);

		Assert.assertEquals(existingCShippingFixedOptionRel,
			newCShippingFixedOptionRel);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CShippingFixedOptionRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"CShippingFixedOptionRelId", RandomTestUtil.nextLong()));

		List<CShippingFixedOptionRel> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CShippingFixedOptionRel newCShippingFixedOptionRel = addCShippingFixedOptionRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CShippingFixedOptionRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"CShippingFixedOptionRelId"));

		Object newCShippingFixedOptionRelId = newCShippingFixedOptionRel.getCShippingFixedOptionRelId();

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"CShippingFixedOptionRelId",
				new Object[] { newCShippingFixedOptionRelId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCShippingFixedOptionRelId = result.get(0);

		Assert.assertEquals(existingCShippingFixedOptionRelId,
			newCShippingFixedOptionRelId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CShippingFixedOptionRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"CShippingFixedOptionRelId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"CShippingFixedOptionRelId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected CShippingFixedOptionRel addCShippingFixedOptionRel()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		CShippingFixedOptionRel cShippingFixedOptionRel = _persistence.create(pk);

		cShippingFixedOptionRel.setGroupId(RandomTestUtil.nextLong());

		cShippingFixedOptionRel.setCompanyId(RandomTestUtil.nextLong());

		cShippingFixedOptionRel.setUserId(RandomTestUtil.nextLong());

		cShippingFixedOptionRel.setUserName(RandomTestUtil.randomString());

		cShippingFixedOptionRel.setCreateDate(RandomTestUtil.nextDate());

		cShippingFixedOptionRel.setModifiedDate(RandomTestUtil.nextDate());

		cShippingFixedOptionRel.setCommerceShippingMethodId(RandomTestUtil.nextLong());

		cShippingFixedOptionRel.setCommerceShippingFixedOptionId(RandomTestUtil.nextLong());

		cShippingFixedOptionRel.setCommerceWarehouseId(RandomTestUtil.nextLong());

		cShippingFixedOptionRel.setCommerceCountryId(RandomTestUtil.nextLong());

		cShippingFixedOptionRel.setCommerceRegionId(RandomTestUtil.nextLong());

		cShippingFixedOptionRel.setZip(RandomTestUtil.randomString());

		cShippingFixedOptionRel.setWeightFrom(RandomTestUtil.nextDouble());

		cShippingFixedOptionRel.setWeightTo(RandomTestUtil.nextDouble());

		cShippingFixedOptionRel.setFixedPrice(RandomTestUtil.nextDouble());

		cShippingFixedOptionRel.setRateUnitWeightPrice(RandomTestUtil.nextDouble());

		cShippingFixedOptionRel.setRatePercentage(RandomTestUtil.nextDouble());

		_cShippingFixedOptionRels.add(_persistence.update(
				cShippingFixedOptionRel));

		return cShippingFixedOptionRel;
	}

	private List<CShippingFixedOptionRel> _cShippingFixedOptionRels = new ArrayList<CShippingFixedOptionRel>();
	private CShippingFixedOptionRelPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}