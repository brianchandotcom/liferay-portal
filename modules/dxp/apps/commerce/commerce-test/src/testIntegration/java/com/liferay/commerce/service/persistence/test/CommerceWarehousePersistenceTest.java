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

package com.liferay.commerce.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.commerce.exception.NoSuchWarehouseException;
import com.liferay.commerce.model.CommerceWarehouse;
import com.liferay.commerce.service.CommerceWarehouseLocalServiceUtil;
import com.liferay.commerce.service.persistence.CommerceWarehousePersistence;
import com.liferay.commerce.service.persistence.CommerceWarehouseUtil;

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
public class CommerceWarehousePersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.commerce.service"));

	@Before
	public void setUp() {
		_persistence = CommerceWarehouseUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CommerceWarehouse> iterator = _commerceWarehouses.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceWarehouse commerceWarehouse = _persistence.create(pk);

		Assert.assertNotNull(commerceWarehouse);

		Assert.assertEquals(commerceWarehouse.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CommerceWarehouse newCommerceWarehouse = addCommerceWarehouse();

		_persistence.remove(newCommerceWarehouse);

		CommerceWarehouse existingCommerceWarehouse = _persistence.fetchByPrimaryKey(newCommerceWarehouse.getPrimaryKey());

		Assert.assertNull(existingCommerceWarehouse);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCommerceWarehouse();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceWarehouse newCommerceWarehouse = _persistence.create(pk);

		newCommerceWarehouse.setGroupId(RandomTestUtil.nextLong());

		newCommerceWarehouse.setCompanyId(RandomTestUtil.nextLong());

		newCommerceWarehouse.setUserId(RandomTestUtil.nextLong());

		newCommerceWarehouse.setUserName(RandomTestUtil.randomString());

		newCommerceWarehouse.setCreateDate(RandomTestUtil.nextDate());

		newCommerceWarehouse.setModifiedDate(RandomTestUtil.nextDate());

		newCommerceWarehouse.setName(RandomTestUtil.randomString());

		newCommerceWarehouse.setDescription(RandomTestUtil.randomString());

		newCommerceWarehouse.setActive(RandomTestUtil.randomBoolean());

		newCommerceWarehouse.setStreet1(RandomTestUtil.randomString());

		newCommerceWarehouse.setStreet2(RandomTestUtil.randomString());

		newCommerceWarehouse.setStreet3(RandomTestUtil.randomString());

		newCommerceWarehouse.setCity(RandomTestUtil.randomString());

		newCommerceWarehouse.setZip(RandomTestUtil.randomString());

		newCommerceWarehouse.setCommerceRegionId(RandomTestUtil.nextLong());

		newCommerceWarehouse.setCommerceCountryId(RandomTestUtil.nextLong());

		newCommerceWarehouse.setLatitude(RandomTestUtil.nextDouble());

		newCommerceWarehouse.setLongitude(RandomTestUtil.nextDouble());

		_commerceWarehouses.add(_persistence.update(newCommerceWarehouse));

		CommerceWarehouse existingCommerceWarehouse = _persistence.findByPrimaryKey(newCommerceWarehouse.getPrimaryKey());

		Assert.assertEquals(existingCommerceWarehouse.getCommerceWarehouseId(),
			newCommerceWarehouse.getCommerceWarehouseId());
		Assert.assertEquals(existingCommerceWarehouse.getGroupId(),
			newCommerceWarehouse.getGroupId());
		Assert.assertEquals(existingCommerceWarehouse.getCompanyId(),
			newCommerceWarehouse.getCompanyId());
		Assert.assertEquals(existingCommerceWarehouse.getUserId(),
			newCommerceWarehouse.getUserId());
		Assert.assertEquals(existingCommerceWarehouse.getUserName(),
			newCommerceWarehouse.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceWarehouse.getCreateDate()),
			Time.getShortTimestamp(newCommerceWarehouse.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceWarehouse.getModifiedDate()),
			Time.getShortTimestamp(newCommerceWarehouse.getModifiedDate()));
		Assert.assertEquals(existingCommerceWarehouse.getName(),
			newCommerceWarehouse.getName());
		Assert.assertEquals(existingCommerceWarehouse.getDescription(),
			newCommerceWarehouse.getDescription());
		Assert.assertEquals(existingCommerceWarehouse.isActive(),
			newCommerceWarehouse.isActive());
		Assert.assertEquals(existingCommerceWarehouse.getStreet1(),
			newCommerceWarehouse.getStreet1());
		Assert.assertEquals(existingCommerceWarehouse.getStreet2(),
			newCommerceWarehouse.getStreet2());
		Assert.assertEquals(existingCommerceWarehouse.getStreet3(),
			newCommerceWarehouse.getStreet3());
		Assert.assertEquals(existingCommerceWarehouse.getCity(),
			newCommerceWarehouse.getCity());
		Assert.assertEquals(existingCommerceWarehouse.getZip(),
			newCommerceWarehouse.getZip());
		Assert.assertEquals(existingCommerceWarehouse.getCommerceRegionId(),
			newCommerceWarehouse.getCommerceRegionId());
		Assert.assertEquals(existingCommerceWarehouse.getCommerceCountryId(),
			newCommerceWarehouse.getCommerceCountryId());
		AssertUtils.assertEquals(existingCommerceWarehouse.getLatitude(),
			newCommerceWarehouse.getLatitude());
		AssertUtils.assertEquals(existingCommerceWarehouse.getLongitude(),
			newCommerceWarehouse.getLongitude());
	}

	@Test
	public void testCountByGroupId() throws Exception {
		_persistence.countByGroupId(RandomTestUtil.nextLong());

		_persistence.countByGroupId(0L);
	}

	@Test
	public void testCountByG_A() throws Exception {
		_persistence.countByG_A(RandomTestUtil.nextLong(),
			RandomTestUtil.randomBoolean());

		_persistence.countByG_A(0L, RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByG_C() throws Exception {
		_persistence.countByG_C(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByG_C(0L, 0L);
	}

	@Test
	public void testCountByG_A_C() throws Exception {
		_persistence.countByG_A_C(RandomTestUtil.nextLong(),
			RandomTestUtil.randomBoolean(), RandomTestUtil.nextLong());

		_persistence.countByG_A_C(0L, RandomTestUtil.randomBoolean(), 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CommerceWarehouse newCommerceWarehouse = addCommerceWarehouse();

		CommerceWarehouse existingCommerceWarehouse = _persistence.findByPrimaryKey(newCommerceWarehouse.getPrimaryKey());

		Assert.assertEquals(existingCommerceWarehouse, newCommerceWarehouse);
	}

	@Test(expected = NoSuchWarehouseException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<CommerceWarehouse> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CommerceWarehouse",
			"commerceWarehouseId", true, "groupId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "name", true, "description", true, "active",
			true, "street1", true, "street2", true, "street3", true, "city",
			true, "zip", true, "commerceRegionId", true, "commerceCountryId",
			true, "latitude", true, "longitude", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CommerceWarehouse newCommerceWarehouse = addCommerceWarehouse();

		CommerceWarehouse existingCommerceWarehouse = _persistence.fetchByPrimaryKey(newCommerceWarehouse.getPrimaryKey());

		Assert.assertEquals(existingCommerceWarehouse, newCommerceWarehouse);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceWarehouse missingCommerceWarehouse = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCommerceWarehouse);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CommerceWarehouse newCommerceWarehouse1 = addCommerceWarehouse();
		CommerceWarehouse newCommerceWarehouse2 = addCommerceWarehouse();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceWarehouse1.getPrimaryKey());
		primaryKeys.add(newCommerceWarehouse2.getPrimaryKey());

		Map<Serializable, CommerceWarehouse> commerceWarehouses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, commerceWarehouses.size());
		Assert.assertEquals(newCommerceWarehouse1,
			commerceWarehouses.get(newCommerceWarehouse1.getPrimaryKey()));
		Assert.assertEquals(newCommerceWarehouse2,
			commerceWarehouses.get(newCommerceWarehouse2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CommerceWarehouse> commerceWarehouses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceWarehouses.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CommerceWarehouse newCommerceWarehouse = addCommerceWarehouse();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceWarehouse.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CommerceWarehouse> commerceWarehouses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceWarehouses.size());
		Assert.assertEquals(newCommerceWarehouse,
			commerceWarehouses.get(newCommerceWarehouse.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CommerceWarehouse> commerceWarehouses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceWarehouses.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CommerceWarehouse newCommerceWarehouse = addCommerceWarehouse();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceWarehouse.getPrimaryKey());

		Map<Serializable, CommerceWarehouse> commerceWarehouses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceWarehouses.size());
		Assert.assertEquals(newCommerceWarehouse,
			commerceWarehouses.get(newCommerceWarehouse.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CommerceWarehouseLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CommerceWarehouse>() {
				@Override
				public void performAction(CommerceWarehouse commerceWarehouse) {
					Assert.assertNotNull(commerceWarehouse);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CommerceWarehouse newCommerceWarehouse = addCommerceWarehouse();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceWarehouse.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("commerceWarehouseId",
				newCommerceWarehouse.getCommerceWarehouseId()));

		List<CommerceWarehouse> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CommerceWarehouse existingCommerceWarehouse = result.get(0);

		Assert.assertEquals(existingCommerceWarehouse, newCommerceWarehouse);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceWarehouse.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("commerceWarehouseId",
				RandomTestUtil.nextLong()));

		List<CommerceWarehouse> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CommerceWarehouse newCommerceWarehouse = addCommerceWarehouse();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceWarehouse.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceWarehouseId"));

		Object newCommerceWarehouseId = newCommerceWarehouse.getCommerceWarehouseId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("commerceWarehouseId",
				new Object[] { newCommerceWarehouseId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCommerceWarehouseId = result.get(0);

		Assert.assertEquals(existingCommerceWarehouseId, newCommerceWarehouseId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceWarehouse.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceWarehouseId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("commerceWarehouseId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected CommerceWarehouse addCommerceWarehouse()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceWarehouse commerceWarehouse = _persistence.create(pk);

		commerceWarehouse.setGroupId(RandomTestUtil.nextLong());

		commerceWarehouse.setCompanyId(RandomTestUtil.nextLong());

		commerceWarehouse.setUserId(RandomTestUtil.nextLong());

		commerceWarehouse.setUserName(RandomTestUtil.randomString());

		commerceWarehouse.setCreateDate(RandomTestUtil.nextDate());

		commerceWarehouse.setModifiedDate(RandomTestUtil.nextDate());

		commerceWarehouse.setName(RandomTestUtil.randomString());

		commerceWarehouse.setDescription(RandomTestUtil.randomString());

		commerceWarehouse.setActive(RandomTestUtil.randomBoolean());

		commerceWarehouse.setStreet1(RandomTestUtil.randomString());

		commerceWarehouse.setStreet2(RandomTestUtil.randomString());

		commerceWarehouse.setStreet3(RandomTestUtil.randomString());

		commerceWarehouse.setCity(RandomTestUtil.randomString());

		commerceWarehouse.setZip(RandomTestUtil.randomString());

		commerceWarehouse.setCommerceRegionId(RandomTestUtil.nextLong());

		commerceWarehouse.setCommerceCountryId(RandomTestUtil.nextLong());

		commerceWarehouse.setLatitude(RandomTestUtil.nextDouble());

		commerceWarehouse.setLongitude(RandomTestUtil.nextDouble());

		_commerceWarehouses.add(_persistence.update(commerceWarehouse));

		return commerceWarehouse;
	}

	private List<CommerceWarehouse> _commerceWarehouses = new ArrayList<CommerceWarehouse>();
	private CommerceWarehousePersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}