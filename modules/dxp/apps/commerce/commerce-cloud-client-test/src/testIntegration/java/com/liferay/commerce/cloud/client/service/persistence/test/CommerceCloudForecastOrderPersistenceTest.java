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

package com.liferay.commerce.cloud.client.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.commerce.cloud.client.exception.NoSuchCloudForecastOrderException;
import com.liferay.commerce.cloud.client.model.CommerceCloudForecastOrder;
import com.liferay.commerce.cloud.client.service.CommerceCloudForecastOrderLocalServiceUtil;
import com.liferay.commerce.cloud.client.service.persistence.CommerceCloudForecastOrderPersistence;
import com.liferay.commerce.cloud.client.service.persistence.CommerceCloudForecastOrderUtil;

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
public class CommerceCloudForecastOrderPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.commerce.cloud.client.service"));

	@Before
	public void setUp() {
		_persistence = CommerceCloudForecastOrderUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CommerceCloudForecastOrder> iterator = _commerceCloudForecastOrders.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceCloudForecastOrder commerceCloudForecastOrder = _persistence.create(pk);

		Assert.assertNotNull(commerceCloudForecastOrder);

		Assert.assertEquals(commerceCloudForecastOrder.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CommerceCloudForecastOrder newCommerceCloudForecastOrder = addCommerceCloudForecastOrder();

		_persistence.remove(newCommerceCloudForecastOrder);

		CommerceCloudForecastOrder existingCommerceCloudForecastOrder = _persistence.fetchByPrimaryKey(newCommerceCloudForecastOrder.getPrimaryKey());

		Assert.assertNull(existingCommerceCloudForecastOrder);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCommerceCloudForecastOrder();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceCloudForecastOrder newCommerceCloudForecastOrder = _persistence.create(pk);

		newCommerceCloudForecastOrder.setGroupId(RandomTestUtil.nextLong());

		newCommerceCloudForecastOrder.setCompanyId(RandomTestUtil.nextLong());

		newCommerceCloudForecastOrder.setCreateDate(RandomTestUtil.nextDate());

		newCommerceCloudForecastOrder.setCommerceOrderId(RandomTestUtil.nextLong());

		newCommerceCloudForecastOrder.setSyncDate(RandomTestUtil.nextDate());

		_commerceCloudForecastOrders.add(_persistence.update(
				newCommerceCloudForecastOrder));

		CommerceCloudForecastOrder existingCommerceCloudForecastOrder = _persistence.findByPrimaryKey(newCommerceCloudForecastOrder.getPrimaryKey());

		Assert.assertEquals(existingCommerceCloudForecastOrder.getCommerceCloudForecastOrderId(),
			newCommerceCloudForecastOrder.getCommerceCloudForecastOrderId());
		Assert.assertEquals(existingCommerceCloudForecastOrder.getGroupId(),
			newCommerceCloudForecastOrder.getGroupId());
		Assert.assertEquals(existingCommerceCloudForecastOrder.getCompanyId(),
			newCommerceCloudForecastOrder.getCompanyId());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceCloudForecastOrder.getCreateDate()),
			Time.getShortTimestamp(
				newCommerceCloudForecastOrder.getCreateDate()));
		Assert.assertEquals(existingCommerceCloudForecastOrder.getCommerceOrderId(),
			newCommerceCloudForecastOrder.getCommerceOrderId());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceCloudForecastOrder.getSyncDate()),
			Time.getShortTimestamp(newCommerceCloudForecastOrder.getSyncDate()));
	}

	@Test
	public void testCountByCommerceOrderId() throws Exception {
		_persistence.countByCommerceOrderId(RandomTestUtil.nextLong());

		_persistence.countByCommerceOrderId(0L);
	}

	@Test
	public void testCountBySyncDate() throws Exception {
		_persistence.countBySyncDate(RandomTestUtil.nextDate());

		_persistence.countBySyncDate(RandomTestUtil.nextDate());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CommerceCloudForecastOrder newCommerceCloudForecastOrder = addCommerceCloudForecastOrder();

		CommerceCloudForecastOrder existingCommerceCloudForecastOrder = _persistence.findByPrimaryKey(newCommerceCloudForecastOrder.getPrimaryKey());

		Assert.assertEquals(existingCommerceCloudForecastOrder,
			newCommerceCloudForecastOrder);
	}

	@Test(expected = NoSuchCloudForecastOrderException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<CommerceCloudForecastOrder> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CommerceCloudForecastOrder",
			"commerceCloudForecastOrderId", true, "groupId", true, "companyId",
			true, "createDate", true, "commerceOrderId", true, "syncDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CommerceCloudForecastOrder newCommerceCloudForecastOrder = addCommerceCloudForecastOrder();

		CommerceCloudForecastOrder existingCommerceCloudForecastOrder = _persistence.fetchByPrimaryKey(newCommerceCloudForecastOrder.getPrimaryKey());

		Assert.assertEquals(existingCommerceCloudForecastOrder,
			newCommerceCloudForecastOrder);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceCloudForecastOrder missingCommerceCloudForecastOrder = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCommerceCloudForecastOrder);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CommerceCloudForecastOrder newCommerceCloudForecastOrder1 = addCommerceCloudForecastOrder();
		CommerceCloudForecastOrder newCommerceCloudForecastOrder2 = addCommerceCloudForecastOrder();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceCloudForecastOrder1.getPrimaryKey());
		primaryKeys.add(newCommerceCloudForecastOrder2.getPrimaryKey());

		Map<Serializable, CommerceCloudForecastOrder> commerceCloudForecastOrders =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, commerceCloudForecastOrders.size());
		Assert.assertEquals(newCommerceCloudForecastOrder1,
			commerceCloudForecastOrders.get(
				newCommerceCloudForecastOrder1.getPrimaryKey()));
		Assert.assertEquals(newCommerceCloudForecastOrder2,
			commerceCloudForecastOrders.get(
				newCommerceCloudForecastOrder2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CommerceCloudForecastOrder> commerceCloudForecastOrders =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceCloudForecastOrders.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CommerceCloudForecastOrder newCommerceCloudForecastOrder = addCommerceCloudForecastOrder();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceCloudForecastOrder.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CommerceCloudForecastOrder> commerceCloudForecastOrders =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceCloudForecastOrders.size());
		Assert.assertEquals(newCommerceCloudForecastOrder,
			commerceCloudForecastOrders.get(
				newCommerceCloudForecastOrder.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CommerceCloudForecastOrder> commerceCloudForecastOrders =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceCloudForecastOrders.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CommerceCloudForecastOrder newCommerceCloudForecastOrder = addCommerceCloudForecastOrder();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceCloudForecastOrder.getPrimaryKey());

		Map<Serializable, CommerceCloudForecastOrder> commerceCloudForecastOrders =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceCloudForecastOrders.size());
		Assert.assertEquals(newCommerceCloudForecastOrder,
			commerceCloudForecastOrders.get(
				newCommerceCloudForecastOrder.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CommerceCloudForecastOrderLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CommerceCloudForecastOrder>() {
				@Override
				public void performAction(
					CommerceCloudForecastOrder commerceCloudForecastOrder) {
					Assert.assertNotNull(commerceCloudForecastOrder);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CommerceCloudForecastOrder newCommerceCloudForecastOrder = addCommerceCloudForecastOrder();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceCloudForecastOrder.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"commerceCloudForecastOrderId",
				newCommerceCloudForecastOrder.getCommerceCloudForecastOrderId()));

		List<CommerceCloudForecastOrder> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CommerceCloudForecastOrder existingCommerceCloudForecastOrder = result.get(0);

		Assert.assertEquals(existingCommerceCloudForecastOrder,
			newCommerceCloudForecastOrder);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceCloudForecastOrder.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"commerceCloudForecastOrderId", RandomTestUtil.nextLong()));

		List<CommerceCloudForecastOrder> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CommerceCloudForecastOrder newCommerceCloudForecastOrder = addCommerceCloudForecastOrder();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceCloudForecastOrder.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceCloudForecastOrderId"));

		Object newCommerceCloudForecastOrderId = newCommerceCloudForecastOrder.getCommerceCloudForecastOrderId();

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"commerceCloudForecastOrderId",
				new Object[] { newCommerceCloudForecastOrderId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCommerceCloudForecastOrderId = result.get(0);

		Assert.assertEquals(existingCommerceCloudForecastOrderId,
			newCommerceCloudForecastOrderId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceCloudForecastOrder.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceCloudForecastOrderId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"commerceCloudForecastOrderId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		CommerceCloudForecastOrder newCommerceCloudForecastOrder = addCommerceCloudForecastOrder();

		_persistence.clearCache();

		CommerceCloudForecastOrder existingCommerceCloudForecastOrder = _persistence.findByPrimaryKey(newCommerceCloudForecastOrder.getPrimaryKey());

		Assert.assertEquals(Long.valueOf(
				existingCommerceCloudForecastOrder.getCommerceOrderId()),
			ReflectionTestUtil.<Long>invoke(
				existingCommerceCloudForecastOrder,
				"getOriginalCommerceOrderId", new Class<?>[0]));
	}

	protected CommerceCloudForecastOrder addCommerceCloudForecastOrder()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceCloudForecastOrder commerceCloudForecastOrder = _persistence.create(pk);

		commerceCloudForecastOrder.setGroupId(RandomTestUtil.nextLong());

		commerceCloudForecastOrder.setCompanyId(RandomTestUtil.nextLong());

		commerceCloudForecastOrder.setCreateDate(RandomTestUtil.nextDate());

		commerceCloudForecastOrder.setCommerceOrderId(RandomTestUtil.nextLong());

		commerceCloudForecastOrder.setSyncDate(RandomTestUtil.nextDate());

		_commerceCloudForecastOrders.add(_persistence.update(
				commerceCloudForecastOrder));

		return commerceCloudForecastOrder;
	}

	private List<CommerceCloudForecastOrder> _commerceCloudForecastOrders = new ArrayList<CommerceCloudForecastOrder>();
	private CommerceCloudForecastOrderPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}