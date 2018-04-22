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

import com.liferay.commerce.shipping.engine.fixed.exception.NoSuchShippingFixedOptionRelException;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel;
import com.liferay.commerce.shipping.engine.fixed.service.CommerceShippingFixedOptionRelLocalServiceUtil;
import com.liferay.commerce.shipping.engine.fixed.service.persistence.CommerceShippingFixedOptionRelPersistence;
import com.liferay.commerce.shipping.engine.fixed.service.persistence.CommerceShippingFixedOptionRelUtil;

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

import java.math.BigDecimal;

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
public class CommerceShippingFixedOptionRelPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.commerce.shipping.engine.fixed.service"));

	@Before
	public void setUp() {
		_persistence = CommerceShippingFixedOptionRelUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CommerceShippingFixedOptionRel> iterator = _commerceShippingFixedOptionRels.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceShippingFixedOptionRel commerceShippingFixedOptionRel = _persistence.create(pk);

		Assert.assertNotNull(commerceShippingFixedOptionRel);

		Assert.assertEquals(commerceShippingFixedOptionRel.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CommerceShippingFixedOptionRel newCommerceShippingFixedOptionRel = addCommerceShippingFixedOptionRel();

		_persistence.remove(newCommerceShippingFixedOptionRel);

		CommerceShippingFixedOptionRel existingCommerceShippingFixedOptionRel = _persistence.fetchByPrimaryKey(newCommerceShippingFixedOptionRel.getPrimaryKey());

		Assert.assertNull(existingCommerceShippingFixedOptionRel);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCommerceShippingFixedOptionRel();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceShippingFixedOptionRel newCommerceShippingFixedOptionRel = _persistence.create(pk);

		newCommerceShippingFixedOptionRel.setGroupId(RandomTestUtil.nextLong());

		newCommerceShippingFixedOptionRel.setCompanyId(RandomTestUtil.nextLong());

		newCommerceShippingFixedOptionRel.setUserId(RandomTestUtil.nextLong());

		newCommerceShippingFixedOptionRel.setUserName(RandomTestUtil.randomString());

		newCommerceShippingFixedOptionRel.setCreateDate(RandomTestUtil.nextDate());

		newCommerceShippingFixedOptionRel.setModifiedDate(RandomTestUtil.nextDate());

		newCommerceShippingFixedOptionRel.setCommerceShippingMethodId(RandomTestUtil.nextLong());

		newCommerceShippingFixedOptionRel.setCommerceShippingFixedOptionId(RandomTestUtil.nextLong());

		newCommerceShippingFixedOptionRel.setCommerceWarehouseId(RandomTestUtil.nextLong());

		newCommerceShippingFixedOptionRel.setCommerceCountryId(RandomTestUtil.nextLong());

		newCommerceShippingFixedOptionRel.setCommerceRegionId(RandomTestUtil.nextLong());

		newCommerceShippingFixedOptionRel.setZip(RandomTestUtil.randomString());

		newCommerceShippingFixedOptionRel.setWeightFrom(RandomTestUtil.nextDouble());

		newCommerceShippingFixedOptionRel.setWeightTo(RandomTestUtil.nextDouble());

		newCommerceShippingFixedOptionRel.setFixedPrice(new BigDecimal(
				RandomTestUtil.nextDouble()));

		newCommerceShippingFixedOptionRel.setRateUnitWeightPrice(new BigDecimal(
				RandomTestUtil.nextDouble()));

		newCommerceShippingFixedOptionRel.setRatePercentage(RandomTestUtil.nextDouble());

		_commerceShippingFixedOptionRels.add(_persistence.update(
				newCommerceShippingFixedOptionRel));

		CommerceShippingFixedOptionRel existingCommerceShippingFixedOptionRel = _persistence.findByPrimaryKey(newCommerceShippingFixedOptionRel.getPrimaryKey());

		Assert.assertEquals(existingCommerceShippingFixedOptionRel.getCommerceShippingFixedOptionRelId(),
			newCommerceShippingFixedOptionRel.getCommerceShippingFixedOptionRelId());
		Assert.assertEquals(existingCommerceShippingFixedOptionRel.getGroupId(),
			newCommerceShippingFixedOptionRel.getGroupId());
		Assert.assertEquals(existingCommerceShippingFixedOptionRel.getCompanyId(),
			newCommerceShippingFixedOptionRel.getCompanyId());
		Assert.assertEquals(existingCommerceShippingFixedOptionRel.getUserId(),
			newCommerceShippingFixedOptionRel.getUserId());
		Assert.assertEquals(existingCommerceShippingFixedOptionRel.getUserName(),
			newCommerceShippingFixedOptionRel.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceShippingFixedOptionRel.getCreateDate()),
			Time.getShortTimestamp(
				newCommerceShippingFixedOptionRel.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceShippingFixedOptionRel.getModifiedDate()),
			Time.getShortTimestamp(
				newCommerceShippingFixedOptionRel.getModifiedDate()));
		Assert.assertEquals(existingCommerceShippingFixedOptionRel.getCommerceShippingMethodId(),
			newCommerceShippingFixedOptionRel.getCommerceShippingMethodId());
		Assert.assertEquals(existingCommerceShippingFixedOptionRel.getCommerceShippingFixedOptionId(),
			newCommerceShippingFixedOptionRel.getCommerceShippingFixedOptionId());
		Assert.assertEquals(existingCommerceShippingFixedOptionRel.getCommerceWarehouseId(),
			newCommerceShippingFixedOptionRel.getCommerceWarehouseId());
		Assert.assertEquals(existingCommerceShippingFixedOptionRel.getCommerceCountryId(),
			newCommerceShippingFixedOptionRel.getCommerceCountryId());
		Assert.assertEquals(existingCommerceShippingFixedOptionRel.getCommerceRegionId(),
			newCommerceShippingFixedOptionRel.getCommerceRegionId());
		Assert.assertEquals(existingCommerceShippingFixedOptionRel.getZip(),
			newCommerceShippingFixedOptionRel.getZip());
		AssertUtils.assertEquals(existingCommerceShippingFixedOptionRel.getWeightFrom(),
			newCommerceShippingFixedOptionRel.getWeightFrom());
		AssertUtils.assertEquals(existingCommerceShippingFixedOptionRel.getWeightTo(),
			newCommerceShippingFixedOptionRel.getWeightTo());
		Assert.assertEquals(existingCommerceShippingFixedOptionRel.getFixedPrice(),
			newCommerceShippingFixedOptionRel.getFixedPrice());
		Assert.assertEquals(existingCommerceShippingFixedOptionRel.getRateUnitWeightPrice(),
			newCommerceShippingFixedOptionRel.getRateUnitWeightPrice());
		AssertUtils.assertEquals(existingCommerceShippingFixedOptionRel.getRatePercentage(),
			newCommerceShippingFixedOptionRel.getRatePercentage());
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
		CommerceShippingFixedOptionRel newCommerceShippingFixedOptionRel = addCommerceShippingFixedOptionRel();

		CommerceShippingFixedOptionRel existingCommerceShippingFixedOptionRel = _persistence.findByPrimaryKey(newCommerceShippingFixedOptionRel.getPrimaryKey());

		Assert.assertEquals(existingCommerceShippingFixedOptionRel,
			newCommerceShippingFixedOptionRel);
	}

	@Test(expected = NoSuchShippingFixedOptionRelException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<CommerceShippingFixedOptionRel> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CShippingFixedOptionRel",
			"commerceShippingFixedOptionRelId", true, "groupId", true,
			"companyId", true, "userId", true, "userName", true, "createDate",
			true, "modifiedDate", true, "commerceShippingMethodId", true,
			"commerceShippingFixedOptionId", true, "commerceWarehouseId", true,
			"commerceCountryId", true, "commerceRegionId", true, "zip", true,
			"weightFrom", true, "weightTo", true, "fixedPrice", true,
			"rateUnitWeightPrice", true, "ratePercentage", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CommerceShippingFixedOptionRel newCommerceShippingFixedOptionRel = addCommerceShippingFixedOptionRel();

		CommerceShippingFixedOptionRel existingCommerceShippingFixedOptionRel = _persistence.fetchByPrimaryKey(newCommerceShippingFixedOptionRel.getPrimaryKey());

		Assert.assertEquals(existingCommerceShippingFixedOptionRel,
			newCommerceShippingFixedOptionRel);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceShippingFixedOptionRel missingCommerceShippingFixedOptionRel = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCommerceShippingFixedOptionRel);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CommerceShippingFixedOptionRel newCommerceShippingFixedOptionRel1 = addCommerceShippingFixedOptionRel();
		CommerceShippingFixedOptionRel newCommerceShippingFixedOptionRel2 = addCommerceShippingFixedOptionRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceShippingFixedOptionRel1.getPrimaryKey());
		primaryKeys.add(newCommerceShippingFixedOptionRel2.getPrimaryKey());

		Map<Serializable, CommerceShippingFixedOptionRel> commerceShippingFixedOptionRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, commerceShippingFixedOptionRels.size());
		Assert.assertEquals(newCommerceShippingFixedOptionRel1,
			commerceShippingFixedOptionRels.get(
				newCommerceShippingFixedOptionRel1.getPrimaryKey()));
		Assert.assertEquals(newCommerceShippingFixedOptionRel2,
			commerceShippingFixedOptionRels.get(
				newCommerceShippingFixedOptionRel2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CommerceShippingFixedOptionRel> commerceShippingFixedOptionRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceShippingFixedOptionRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CommerceShippingFixedOptionRel newCommerceShippingFixedOptionRel = addCommerceShippingFixedOptionRel();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceShippingFixedOptionRel.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CommerceShippingFixedOptionRel> commerceShippingFixedOptionRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceShippingFixedOptionRels.size());
		Assert.assertEquals(newCommerceShippingFixedOptionRel,
			commerceShippingFixedOptionRels.get(
				newCommerceShippingFixedOptionRel.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CommerceShippingFixedOptionRel> commerceShippingFixedOptionRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceShippingFixedOptionRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CommerceShippingFixedOptionRel newCommerceShippingFixedOptionRel = addCommerceShippingFixedOptionRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceShippingFixedOptionRel.getPrimaryKey());

		Map<Serializable, CommerceShippingFixedOptionRel> commerceShippingFixedOptionRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceShippingFixedOptionRels.size());
		Assert.assertEquals(newCommerceShippingFixedOptionRel,
			commerceShippingFixedOptionRels.get(
				newCommerceShippingFixedOptionRel.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CommerceShippingFixedOptionRelLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CommerceShippingFixedOptionRel>() {
				@Override
				public void performAction(
					CommerceShippingFixedOptionRel commerceShippingFixedOptionRel) {
					Assert.assertNotNull(commerceShippingFixedOptionRel);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CommerceShippingFixedOptionRel newCommerceShippingFixedOptionRel = addCommerceShippingFixedOptionRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceShippingFixedOptionRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"commerceShippingFixedOptionRelId",
				newCommerceShippingFixedOptionRel.getCommerceShippingFixedOptionRelId()));

		List<CommerceShippingFixedOptionRel> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CommerceShippingFixedOptionRel existingCommerceShippingFixedOptionRel = result.get(0);

		Assert.assertEquals(existingCommerceShippingFixedOptionRel,
			newCommerceShippingFixedOptionRel);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceShippingFixedOptionRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"commerceShippingFixedOptionRelId", RandomTestUtil.nextLong()));

		List<CommerceShippingFixedOptionRel> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CommerceShippingFixedOptionRel newCommerceShippingFixedOptionRel = addCommerceShippingFixedOptionRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceShippingFixedOptionRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceShippingFixedOptionRelId"));

		Object newCommerceShippingFixedOptionRelId = newCommerceShippingFixedOptionRel.getCommerceShippingFixedOptionRelId();

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"commerceShippingFixedOptionRelId",
				new Object[] { newCommerceShippingFixedOptionRelId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCommerceShippingFixedOptionRelId = result.get(0);

		Assert.assertEquals(existingCommerceShippingFixedOptionRelId,
			newCommerceShippingFixedOptionRelId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceShippingFixedOptionRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceShippingFixedOptionRelId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"commerceShippingFixedOptionRelId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected CommerceShippingFixedOptionRel addCommerceShippingFixedOptionRel()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceShippingFixedOptionRel commerceShippingFixedOptionRel = _persistence.create(pk);

		commerceShippingFixedOptionRel.setGroupId(RandomTestUtil.nextLong());

		commerceShippingFixedOptionRel.setCompanyId(RandomTestUtil.nextLong());

		commerceShippingFixedOptionRel.setUserId(RandomTestUtil.nextLong());

		commerceShippingFixedOptionRel.setUserName(RandomTestUtil.randomString());

		commerceShippingFixedOptionRel.setCreateDate(RandomTestUtil.nextDate());

		commerceShippingFixedOptionRel.setModifiedDate(RandomTestUtil.nextDate());

		commerceShippingFixedOptionRel.setCommerceShippingMethodId(RandomTestUtil.nextLong());

		commerceShippingFixedOptionRel.setCommerceShippingFixedOptionId(RandomTestUtil.nextLong());

		commerceShippingFixedOptionRel.setCommerceWarehouseId(RandomTestUtil.nextLong());

		commerceShippingFixedOptionRel.setCommerceCountryId(RandomTestUtil.nextLong());

		commerceShippingFixedOptionRel.setCommerceRegionId(RandomTestUtil.nextLong());

		commerceShippingFixedOptionRel.setZip(RandomTestUtil.randomString());

		commerceShippingFixedOptionRel.setWeightFrom(RandomTestUtil.nextDouble());

		commerceShippingFixedOptionRel.setWeightTo(RandomTestUtil.nextDouble());

		commerceShippingFixedOptionRel.setFixedPrice(new BigDecimal(
				RandomTestUtil.nextDouble()));

		commerceShippingFixedOptionRel.setRateUnitWeightPrice(new BigDecimal(
				RandomTestUtil.nextDouble()));

		commerceShippingFixedOptionRel.setRatePercentage(RandomTestUtil.nextDouble());

		_commerceShippingFixedOptionRels.add(_persistence.update(
				commerceShippingFixedOptionRel));

		return commerceShippingFixedOptionRel;
	}

	private List<CommerceShippingFixedOptionRel> _commerceShippingFixedOptionRels =
		new ArrayList<CommerceShippingFixedOptionRel>();
	private CommerceShippingFixedOptionRelPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}