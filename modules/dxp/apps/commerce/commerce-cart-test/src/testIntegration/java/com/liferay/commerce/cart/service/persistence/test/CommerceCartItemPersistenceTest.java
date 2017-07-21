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

package com.liferay.commerce.cart.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.commerce.cart.exception.NoSuchCartItemException;
import com.liferay.commerce.cart.model.CommerceCartItem;
import com.liferay.commerce.cart.service.CommerceCartItemLocalServiceUtil;
import com.liferay.commerce.cart.service.persistence.CommerceCartItemPersistence;
import com.liferay.commerce.cart.service.persistence.CommerceCartItemUtil;

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
public class CommerceCartItemPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.commerce.cart.service"));

	@Before
	public void setUp() {
		_persistence = CommerceCartItemUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CommerceCartItem> iterator = _commerceCartItems.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceCartItem commerceCartItem = _persistence.create(pk);

		Assert.assertNotNull(commerceCartItem);

		Assert.assertEquals(commerceCartItem.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CommerceCartItem newCommerceCartItem = addCommerceCartItem();

		_persistence.remove(newCommerceCartItem);

		CommerceCartItem existingCommerceCartItem = _persistence.fetchByPrimaryKey(newCommerceCartItem.getPrimaryKey());

		Assert.assertNull(existingCommerceCartItem);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCommerceCartItem();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceCartItem newCommerceCartItem = _persistence.create(pk);

		newCommerceCartItem.setGroupId(RandomTestUtil.nextLong());

		newCommerceCartItem.setCompanyId(RandomTestUtil.nextLong());

		newCommerceCartItem.setUserId(RandomTestUtil.nextLong());

		newCommerceCartItem.setUserName(RandomTestUtil.randomString());

		newCommerceCartItem.setCreateDate(RandomTestUtil.nextDate());

		newCommerceCartItem.setModifiedDate(RandomTestUtil.nextDate());

		newCommerceCartItem.setCommerceCartId(RandomTestUtil.nextLong());

		newCommerceCartItem.setCPDefinitionId(RandomTestUtil.nextLong());

		newCommerceCartItem.setCPInstanceId(RandomTestUtil.nextLong());

		newCommerceCartItem.setQuantity(RandomTestUtil.nextInt());

		newCommerceCartItem.setJson(RandomTestUtil.randomString());

		_commerceCartItems.add(_persistence.update(newCommerceCartItem));

		CommerceCartItem existingCommerceCartItem = _persistence.findByPrimaryKey(newCommerceCartItem.getPrimaryKey());

		Assert.assertEquals(existingCommerceCartItem.getCommerceCartItemId(),
			newCommerceCartItem.getCommerceCartItemId());
		Assert.assertEquals(existingCommerceCartItem.getGroupId(),
			newCommerceCartItem.getGroupId());
		Assert.assertEquals(existingCommerceCartItem.getCompanyId(),
			newCommerceCartItem.getCompanyId());
		Assert.assertEquals(existingCommerceCartItem.getUserId(),
			newCommerceCartItem.getUserId());
		Assert.assertEquals(existingCommerceCartItem.getUserName(),
			newCommerceCartItem.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceCartItem.getCreateDate()),
			Time.getShortTimestamp(newCommerceCartItem.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceCartItem.getModifiedDate()),
			Time.getShortTimestamp(newCommerceCartItem.getModifiedDate()));
		Assert.assertEquals(existingCommerceCartItem.getCommerceCartId(),
			newCommerceCartItem.getCommerceCartId());
		Assert.assertEquals(existingCommerceCartItem.getCPDefinitionId(),
			newCommerceCartItem.getCPDefinitionId());
		Assert.assertEquals(existingCommerceCartItem.getCPInstanceId(),
			newCommerceCartItem.getCPInstanceId());
		Assert.assertEquals(existingCommerceCartItem.getQuantity(),
			newCommerceCartItem.getQuantity());
		Assert.assertEquals(existingCommerceCartItem.getJson(),
			newCommerceCartItem.getJson());
	}

	@Test
	public void testCountByCommerceCartId() throws Exception {
		_persistence.countByCommerceCartId(RandomTestUtil.nextLong());

		_persistence.countByCommerceCartId(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CommerceCartItem newCommerceCartItem = addCommerceCartItem();

		CommerceCartItem existingCommerceCartItem = _persistence.findByPrimaryKey(newCommerceCartItem.getPrimaryKey());

		Assert.assertEquals(existingCommerceCartItem, newCommerceCartItem);
	}

	@Test(expected = NoSuchCartItemException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<CommerceCartItem> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CommerceCartItem",
			"CommerceCartItemId", true, "groupId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "CommerceCartId", true, "CPDefinitionId",
			true, "CPInstanceId", true, "quantity", true, "json", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CommerceCartItem newCommerceCartItem = addCommerceCartItem();

		CommerceCartItem existingCommerceCartItem = _persistence.fetchByPrimaryKey(newCommerceCartItem.getPrimaryKey());

		Assert.assertEquals(existingCommerceCartItem, newCommerceCartItem);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceCartItem missingCommerceCartItem = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCommerceCartItem);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CommerceCartItem newCommerceCartItem1 = addCommerceCartItem();
		CommerceCartItem newCommerceCartItem2 = addCommerceCartItem();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceCartItem1.getPrimaryKey());
		primaryKeys.add(newCommerceCartItem2.getPrimaryKey());

		Map<Serializable, CommerceCartItem> commerceCartItems = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, commerceCartItems.size());
		Assert.assertEquals(newCommerceCartItem1,
			commerceCartItems.get(newCommerceCartItem1.getPrimaryKey()));
		Assert.assertEquals(newCommerceCartItem2,
			commerceCartItems.get(newCommerceCartItem2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CommerceCartItem> commerceCartItems = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceCartItems.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CommerceCartItem newCommerceCartItem = addCommerceCartItem();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceCartItem.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CommerceCartItem> commerceCartItems = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceCartItems.size());
		Assert.assertEquals(newCommerceCartItem,
			commerceCartItems.get(newCommerceCartItem.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CommerceCartItem> commerceCartItems = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceCartItems.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CommerceCartItem newCommerceCartItem = addCommerceCartItem();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceCartItem.getPrimaryKey());

		Map<Serializable, CommerceCartItem> commerceCartItems = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceCartItems.size());
		Assert.assertEquals(newCommerceCartItem,
			commerceCartItems.get(newCommerceCartItem.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CommerceCartItemLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CommerceCartItem>() {
				@Override
				public void performAction(CommerceCartItem commerceCartItem) {
					Assert.assertNotNull(commerceCartItem);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CommerceCartItem newCommerceCartItem = addCommerceCartItem();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceCartItem.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("CommerceCartItemId",
				newCommerceCartItem.getCommerceCartItemId()));

		List<CommerceCartItem> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CommerceCartItem existingCommerceCartItem = result.get(0);

		Assert.assertEquals(existingCommerceCartItem, newCommerceCartItem);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceCartItem.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("CommerceCartItemId",
				RandomTestUtil.nextLong()));

		List<CommerceCartItem> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CommerceCartItem newCommerceCartItem = addCommerceCartItem();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceCartItem.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"CommerceCartItemId"));

		Object newCommerceCartItemId = newCommerceCartItem.getCommerceCartItemId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("CommerceCartItemId",
				new Object[] { newCommerceCartItemId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCommerceCartItemId = result.get(0);

		Assert.assertEquals(existingCommerceCartItemId, newCommerceCartItemId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceCartItem.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"CommerceCartItemId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("CommerceCartItemId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected CommerceCartItem addCommerceCartItem() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceCartItem commerceCartItem = _persistence.create(pk);

		commerceCartItem.setGroupId(RandomTestUtil.nextLong());

		commerceCartItem.setCompanyId(RandomTestUtil.nextLong());

		commerceCartItem.setUserId(RandomTestUtil.nextLong());

		commerceCartItem.setUserName(RandomTestUtil.randomString());

		commerceCartItem.setCreateDate(RandomTestUtil.nextDate());

		commerceCartItem.setModifiedDate(RandomTestUtil.nextDate());

		commerceCartItem.setCommerceCartId(RandomTestUtil.nextLong());

		commerceCartItem.setCPDefinitionId(RandomTestUtil.nextLong());

		commerceCartItem.setCPInstanceId(RandomTestUtil.nextLong());

		commerceCartItem.setQuantity(RandomTestUtil.nextInt());

		commerceCartItem.setJson(RandomTestUtil.randomString());

		_commerceCartItems.add(_persistence.update(commerceCartItem));

		return commerceCartItem;
	}

	private List<CommerceCartItem> _commerceCartItems = new ArrayList<CommerceCartItem>();
	private CommerceCartItemPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}