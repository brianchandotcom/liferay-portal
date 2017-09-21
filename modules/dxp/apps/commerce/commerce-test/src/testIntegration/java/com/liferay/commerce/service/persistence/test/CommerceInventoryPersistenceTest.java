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

import com.liferay.commerce.exception.NoSuchInventoryException;
import com.liferay.commerce.model.CommerceInventory;
import com.liferay.commerce.service.CommerceInventoryLocalServiceUtil;
import com.liferay.commerce.service.persistence.CommerceInventoryPersistence;
import com.liferay.commerce.service.persistence.CommerceInventoryUtil;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
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
import java.util.Objects;
import java.util.Set;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class CommerceInventoryPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.commerce.service"));

	@Before
	public void setUp() {
		_persistence = CommerceInventoryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CommerceInventory> iterator = _commerceInventories.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceInventory commerceInventory = _persistence.create(pk);

		Assert.assertNotNull(commerceInventory);

		Assert.assertEquals(commerceInventory.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CommerceInventory newCommerceInventory = addCommerceInventory();

		_persistence.remove(newCommerceInventory);

		CommerceInventory existingCommerceInventory = _persistence.fetchByPrimaryKey(newCommerceInventory.getPrimaryKey());

		Assert.assertNull(existingCommerceInventory);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCommerceInventory();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceInventory newCommerceInventory = _persistence.create(pk);

		newCommerceInventory.setUuid(RandomTestUtil.randomString());

		newCommerceInventory.setGroupId(RandomTestUtil.nextLong());

		newCommerceInventory.setCompanyId(RandomTestUtil.nextLong());

		newCommerceInventory.setUserId(RandomTestUtil.nextLong());

		newCommerceInventory.setUserName(RandomTestUtil.randomString());

		newCommerceInventory.setCreateDate(RandomTestUtil.nextDate());

		newCommerceInventory.setModifiedDate(RandomTestUtil.nextDate());

		newCommerceInventory.setCPDefinitionId(RandomTestUtil.nextLong());

		newCommerceInventory.setCommerceInventoryEngine(RandomTestUtil.randomString());

		newCommerceInventory.setLowStockActivity(RandomTestUtil.randomString());

		newCommerceInventory.setDisplayAvailability(RandomTestUtil.randomBoolean());

		newCommerceInventory.setDisplayStockQuantity(RandomTestUtil.randomBoolean());

		newCommerceInventory.setMinStockQuantity(RandomTestUtil.nextInt());

		newCommerceInventory.setBackOrders(RandomTestUtil.randomBoolean());

		newCommerceInventory.setMinCartQuantity(RandomTestUtil.nextInt());

		newCommerceInventory.setMaxCartQuantity(RandomTestUtil.nextInt());

		newCommerceInventory.setAllowedCartQuantities(RandomTestUtil.randomString());

		newCommerceInventory.setMultipleCartQuantity(RandomTestUtil.nextInt());

		_commerceInventories.add(_persistence.update(newCommerceInventory));

		CommerceInventory existingCommerceInventory = _persistence.findByPrimaryKey(newCommerceInventory.getPrimaryKey());

		Assert.assertEquals(existingCommerceInventory.getUuid(),
			newCommerceInventory.getUuid());
		Assert.assertEquals(existingCommerceInventory.getCommerceInventoryId(),
			newCommerceInventory.getCommerceInventoryId());
		Assert.assertEquals(existingCommerceInventory.getGroupId(),
			newCommerceInventory.getGroupId());
		Assert.assertEquals(existingCommerceInventory.getCompanyId(),
			newCommerceInventory.getCompanyId());
		Assert.assertEquals(existingCommerceInventory.getUserId(),
			newCommerceInventory.getUserId());
		Assert.assertEquals(existingCommerceInventory.getUserName(),
			newCommerceInventory.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceInventory.getCreateDate()),
			Time.getShortTimestamp(newCommerceInventory.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceInventory.getModifiedDate()),
			Time.getShortTimestamp(newCommerceInventory.getModifiedDate()));
		Assert.assertEquals(existingCommerceInventory.getCPDefinitionId(),
			newCommerceInventory.getCPDefinitionId());
		Assert.assertEquals(existingCommerceInventory.getCommerceInventoryEngine(),
			newCommerceInventory.getCommerceInventoryEngine());
		Assert.assertEquals(existingCommerceInventory.getLowStockActivity(),
			newCommerceInventory.getLowStockActivity());
		Assert.assertEquals(existingCommerceInventory.getDisplayAvailability(),
			newCommerceInventory.getDisplayAvailability());
		Assert.assertEquals(existingCommerceInventory.getDisplayStockQuantity(),
			newCommerceInventory.getDisplayStockQuantity());
		Assert.assertEquals(existingCommerceInventory.getMinStockQuantity(),
			newCommerceInventory.getMinStockQuantity());
		Assert.assertEquals(existingCommerceInventory.getBackOrders(),
			newCommerceInventory.getBackOrders());
		Assert.assertEquals(existingCommerceInventory.getMinCartQuantity(),
			newCommerceInventory.getMinCartQuantity());
		Assert.assertEquals(existingCommerceInventory.getMaxCartQuantity(),
			newCommerceInventory.getMaxCartQuantity());
		Assert.assertEquals(existingCommerceInventory.getAllowedCartQuantities(),
			newCommerceInventory.getAllowedCartQuantities());
		Assert.assertEquals(existingCommerceInventory.getMultipleCartQuantity(),
			newCommerceInventory.getMultipleCartQuantity());
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
	public void testCountByCPDefinitionId() throws Exception {
		_persistence.countByCPDefinitionId(RandomTestUtil.nextLong());

		_persistence.countByCPDefinitionId(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CommerceInventory newCommerceInventory = addCommerceInventory();

		CommerceInventory existingCommerceInventory = _persistence.findByPrimaryKey(newCommerceInventory.getPrimaryKey());

		Assert.assertEquals(existingCommerceInventory, newCommerceInventory);
	}

	@Test(expected = NoSuchInventoryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<CommerceInventory> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CommerceInventory", "uuid",
			true, "commerceInventoryId", true, "groupId", true, "companyId",
			true, "userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "CPDefinitionId", true,
			"commerceInventoryEngine", true, "lowStockActivity", true,
			"displayAvailability", true, "displayStockQuantity", true,
			"minStockQuantity", true, "backOrders", true, "minCartQuantity",
			true, "maxCartQuantity", true, "allowedCartQuantities", true,
			"multipleCartQuantity", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CommerceInventory newCommerceInventory = addCommerceInventory();

		CommerceInventory existingCommerceInventory = _persistence.fetchByPrimaryKey(newCommerceInventory.getPrimaryKey());

		Assert.assertEquals(existingCommerceInventory, newCommerceInventory);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceInventory missingCommerceInventory = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCommerceInventory);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CommerceInventory newCommerceInventory1 = addCommerceInventory();
		CommerceInventory newCommerceInventory2 = addCommerceInventory();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceInventory1.getPrimaryKey());
		primaryKeys.add(newCommerceInventory2.getPrimaryKey());

		Map<Serializable, CommerceInventory> commerceInventories = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, commerceInventories.size());
		Assert.assertEquals(newCommerceInventory1,
			commerceInventories.get(newCommerceInventory1.getPrimaryKey()));
		Assert.assertEquals(newCommerceInventory2,
			commerceInventories.get(newCommerceInventory2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CommerceInventory> commerceInventories = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceInventories.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CommerceInventory newCommerceInventory = addCommerceInventory();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceInventory.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CommerceInventory> commerceInventories = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceInventories.size());
		Assert.assertEquals(newCommerceInventory,
			commerceInventories.get(newCommerceInventory.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CommerceInventory> commerceInventories = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceInventories.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CommerceInventory newCommerceInventory = addCommerceInventory();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceInventory.getPrimaryKey());

		Map<Serializable, CommerceInventory> commerceInventories = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceInventories.size());
		Assert.assertEquals(newCommerceInventory,
			commerceInventories.get(newCommerceInventory.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CommerceInventoryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CommerceInventory>() {
				@Override
				public void performAction(CommerceInventory commerceInventory) {
					Assert.assertNotNull(commerceInventory);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CommerceInventory newCommerceInventory = addCommerceInventory();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceInventory.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("commerceInventoryId",
				newCommerceInventory.getCommerceInventoryId()));

		List<CommerceInventory> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CommerceInventory existingCommerceInventory = result.get(0);

		Assert.assertEquals(existingCommerceInventory, newCommerceInventory);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceInventory.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("commerceInventoryId",
				RandomTestUtil.nextLong()));

		List<CommerceInventory> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CommerceInventory newCommerceInventory = addCommerceInventory();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceInventory.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceInventoryId"));

		Object newCommerceInventoryId = newCommerceInventory.getCommerceInventoryId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("commerceInventoryId",
				new Object[] { newCommerceInventoryId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCommerceInventoryId = result.get(0);

		Assert.assertEquals(existingCommerceInventoryId, newCommerceInventoryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceInventory.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceInventoryId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("commerceInventoryId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		CommerceInventory newCommerceInventory = addCommerceInventory();

		_persistence.clearCache();

		CommerceInventory existingCommerceInventory = _persistence.findByPrimaryKey(newCommerceInventory.getPrimaryKey());

		Assert.assertTrue(Objects.equals(existingCommerceInventory.getUuid(),
				ReflectionTestUtil.invoke(existingCommerceInventory,
					"getOriginalUuid", new Class<?>[0])));
		Assert.assertEquals(Long.valueOf(existingCommerceInventory.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingCommerceInventory,
				"getOriginalGroupId", new Class<?>[0]));

		Assert.assertEquals(Long.valueOf(
				existingCommerceInventory.getCPDefinitionId()),
			ReflectionTestUtil.<Long>invoke(existingCommerceInventory,
				"getOriginalCPDefinitionId", new Class<?>[0]));
	}

	protected CommerceInventory addCommerceInventory()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceInventory commerceInventory = _persistence.create(pk);

		commerceInventory.setUuid(RandomTestUtil.randomString());

		commerceInventory.setGroupId(RandomTestUtil.nextLong());

		commerceInventory.setCompanyId(RandomTestUtil.nextLong());

		commerceInventory.setUserId(RandomTestUtil.nextLong());

		commerceInventory.setUserName(RandomTestUtil.randomString());

		commerceInventory.setCreateDate(RandomTestUtil.nextDate());

		commerceInventory.setModifiedDate(RandomTestUtil.nextDate());

		commerceInventory.setCPDefinitionId(RandomTestUtil.nextLong());

		commerceInventory.setCommerceInventoryEngine(RandomTestUtil.randomString());

		commerceInventory.setLowStockActivity(RandomTestUtil.randomString());

		commerceInventory.setDisplayAvailability(RandomTestUtil.randomBoolean());

		commerceInventory.setDisplayStockQuantity(RandomTestUtil.randomBoolean());

		commerceInventory.setMinStockQuantity(RandomTestUtil.nextInt());

		commerceInventory.setBackOrders(RandomTestUtil.randomBoolean());

		commerceInventory.setMinCartQuantity(RandomTestUtil.nextInt());

		commerceInventory.setMaxCartQuantity(RandomTestUtil.nextInt());

		commerceInventory.setAllowedCartQuantities(RandomTestUtil.randomString());

		commerceInventory.setMultipleCartQuantity(RandomTestUtil.nextInt());

		_commerceInventories.add(_persistence.update(commerceInventory));

		return commerceInventory;
	}

	private List<CommerceInventory> _commerceInventories = new ArrayList<CommerceInventory>();
	private CommerceInventoryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}