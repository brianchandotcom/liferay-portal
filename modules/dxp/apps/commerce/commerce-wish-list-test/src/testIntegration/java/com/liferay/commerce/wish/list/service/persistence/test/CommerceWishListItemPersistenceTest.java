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

package com.liferay.commerce.wish.list.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.commerce.wish.list.exception.NoSuchWishListItemException;
import com.liferay.commerce.wish.list.model.CommerceWishListItem;
import com.liferay.commerce.wish.list.service.CommerceWishListItemLocalServiceUtil;
import com.liferay.commerce.wish.list.service.persistence.CommerceWishListItemPersistence;
import com.liferay.commerce.wish.list.service.persistence.CommerceWishListItemUtil;

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
public class CommerceWishListItemPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.commerce.wish.list.service"));

	@Before
	public void setUp() {
		_persistence = CommerceWishListItemUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CommerceWishListItem> iterator = _commerceWishListItems.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceWishListItem commerceWishListItem = _persistence.create(pk);

		Assert.assertNotNull(commerceWishListItem);

		Assert.assertEquals(commerceWishListItem.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CommerceWishListItem newCommerceWishListItem = addCommerceWishListItem();

		_persistence.remove(newCommerceWishListItem);

		CommerceWishListItem existingCommerceWishListItem = _persistence.fetchByPrimaryKey(newCommerceWishListItem.getPrimaryKey());

		Assert.assertNull(existingCommerceWishListItem);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCommerceWishListItem();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceWishListItem newCommerceWishListItem = _persistence.create(pk);

		newCommerceWishListItem.setGroupId(RandomTestUtil.nextLong());

		newCommerceWishListItem.setCompanyId(RandomTestUtil.nextLong());

		newCommerceWishListItem.setUserId(RandomTestUtil.nextLong());

		newCommerceWishListItem.setUserName(RandomTestUtil.randomString());

		newCommerceWishListItem.setCreateDate(RandomTestUtil.nextDate());

		newCommerceWishListItem.setModifiedDate(RandomTestUtil.nextDate());

		newCommerceWishListItem.setCommerceWishListId(RandomTestUtil.nextLong());

		newCommerceWishListItem.setCPDefinitionId(RandomTestUtil.nextLong());

		newCommerceWishListItem.setCPInstanceId(RandomTestUtil.nextLong());

		newCommerceWishListItem.setJson(RandomTestUtil.randomString());

		_commerceWishListItems.add(_persistence.update(newCommerceWishListItem));

		CommerceWishListItem existingCommerceWishListItem = _persistence.findByPrimaryKey(newCommerceWishListItem.getPrimaryKey());

		Assert.assertEquals(existingCommerceWishListItem.getCommerceWishListItemId(),
			newCommerceWishListItem.getCommerceWishListItemId());
		Assert.assertEquals(existingCommerceWishListItem.getGroupId(),
			newCommerceWishListItem.getGroupId());
		Assert.assertEquals(existingCommerceWishListItem.getCompanyId(),
			newCommerceWishListItem.getCompanyId());
		Assert.assertEquals(existingCommerceWishListItem.getUserId(),
			newCommerceWishListItem.getUserId());
		Assert.assertEquals(existingCommerceWishListItem.getUserName(),
			newCommerceWishListItem.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceWishListItem.getCreateDate()),
			Time.getShortTimestamp(newCommerceWishListItem.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceWishListItem.getModifiedDate()),
			Time.getShortTimestamp(newCommerceWishListItem.getModifiedDate()));
		Assert.assertEquals(existingCommerceWishListItem.getCommerceWishListId(),
			newCommerceWishListItem.getCommerceWishListId());
		Assert.assertEquals(existingCommerceWishListItem.getCPDefinitionId(),
			newCommerceWishListItem.getCPDefinitionId());
		Assert.assertEquals(existingCommerceWishListItem.getCPInstanceId(),
			newCommerceWishListItem.getCPInstanceId());
		Assert.assertEquals(existingCommerceWishListItem.getJson(),
			newCommerceWishListItem.getJson());
	}

	@Test
	public void testCountByCommerceWishListId() throws Exception {
		_persistence.countByCommerceWishListId(RandomTestUtil.nextLong());

		_persistence.countByCommerceWishListId(0L);
	}

	@Test
	public void testCountByCPDefinitionId() throws Exception {
		_persistence.countByCPDefinitionId(RandomTestUtil.nextLong());

		_persistence.countByCPDefinitionId(0L);
	}

	@Test
	public void testCountByCPInstanceId() throws Exception {
		_persistence.countByCPInstanceId(RandomTestUtil.nextLong());

		_persistence.countByCPInstanceId(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CommerceWishListItem newCommerceWishListItem = addCommerceWishListItem();

		CommerceWishListItem existingCommerceWishListItem = _persistence.findByPrimaryKey(newCommerceWishListItem.getPrimaryKey());

		Assert.assertEquals(existingCommerceWishListItem,
			newCommerceWishListItem);
	}

	@Test(expected = NoSuchWishListItemException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<CommerceWishListItem> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CommerceWishListItem",
			"commerceWishListItemId", true, "groupId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "commerceWishListId", true, "CPDefinitionId",
			true, "CPInstanceId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CommerceWishListItem newCommerceWishListItem = addCommerceWishListItem();

		CommerceWishListItem existingCommerceWishListItem = _persistence.fetchByPrimaryKey(newCommerceWishListItem.getPrimaryKey());

		Assert.assertEquals(existingCommerceWishListItem,
			newCommerceWishListItem);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceWishListItem missingCommerceWishListItem = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCommerceWishListItem);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CommerceWishListItem newCommerceWishListItem1 = addCommerceWishListItem();
		CommerceWishListItem newCommerceWishListItem2 = addCommerceWishListItem();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceWishListItem1.getPrimaryKey());
		primaryKeys.add(newCommerceWishListItem2.getPrimaryKey());

		Map<Serializable, CommerceWishListItem> commerceWishListItems = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, commerceWishListItems.size());
		Assert.assertEquals(newCommerceWishListItem1,
			commerceWishListItems.get(newCommerceWishListItem1.getPrimaryKey()));
		Assert.assertEquals(newCommerceWishListItem2,
			commerceWishListItems.get(newCommerceWishListItem2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CommerceWishListItem> commerceWishListItems = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceWishListItems.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CommerceWishListItem newCommerceWishListItem = addCommerceWishListItem();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceWishListItem.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CommerceWishListItem> commerceWishListItems = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceWishListItems.size());
		Assert.assertEquals(newCommerceWishListItem,
			commerceWishListItems.get(newCommerceWishListItem.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CommerceWishListItem> commerceWishListItems = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceWishListItems.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CommerceWishListItem newCommerceWishListItem = addCommerceWishListItem();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceWishListItem.getPrimaryKey());

		Map<Serializable, CommerceWishListItem> commerceWishListItems = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceWishListItems.size());
		Assert.assertEquals(newCommerceWishListItem,
			commerceWishListItems.get(newCommerceWishListItem.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CommerceWishListItemLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CommerceWishListItem>() {
				@Override
				public void performAction(
					CommerceWishListItem commerceWishListItem) {
					Assert.assertNotNull(commerceWishListItem);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CommerceWishListItem newCommerceWishListItem = addCommerceWishListItem();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceWishListItem.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("commerceWishListItemId",
				newCommerceWishListItem.getCommerceWishListItemId()));

		List<CommerceWishListItem> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CommerceWishListItem existingCommerceWishListItem = result.get(0);

		Assert.assertEquals(existingCommerceWishListItem,
			newCommerceWishListItem);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceWishListItem.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("commerceWishListItemId",
				RandomTestUtil.nextLong()));

		List<CommerceWishListItem> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CommerceWishListItem newCommerceWishListItem = addCommerceWishListItem();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceWishListItem.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceWishListItemId"));

		Object newCommerceWishListItemId = newCommerceWishListItem.getCommerceWishListItemId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("commerceWishListItemId",
				new Object[] { newCommerceWishListItemId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCommerceWishListItemId = result.get(0);

		Assert.assertEquals(existingCommerceWishListItemId,
			newCommerceWishListItemId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceWishListItem.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceWishListItemId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("commerceWishListItemId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected CommerceWishListItem addCommerceWishListItem()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceWishListItem commerceWishListItem = _persistence.create(pk);

		commerceWishListItem.setGroupId(RandomTestUtil.nextLong());

		commerceWishListItem.setCompanyId(RandomTestUtil.nextLong());

		commerceWishListItem.setUserId(RandomTestUtil.nextLong());

		commerceWishListItem.setUserName(RandomTestUtil.randomString());

		commerceWishListItem.setCreateDate(RandomTestUtil.nextDate());

		commerceWishListItem.setModifiedDate(RandomTestUtil.nextDate());

		commerceWishListItem.setCommerceWishListId(RandomTestUtil.nextLong());

		commerceWishListItem.setCPDefinitionId(RandomTestUtil.nextLong());

		commerceWishListItem.setCPInstanceId(RandomTestUtil.nextLong());

		commerceWishListItem.setJson(RandomTestUtil.randomString());

		_commerceWishListItems.add(_persistence.update(commerceWishListItem));

		return commerceWishListItem;
	}

	private List<CommerceWishListItem> _commerceWishListItems = new ArrayList<CommerceWishListItem>();
	private CommerceWishListItemPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}