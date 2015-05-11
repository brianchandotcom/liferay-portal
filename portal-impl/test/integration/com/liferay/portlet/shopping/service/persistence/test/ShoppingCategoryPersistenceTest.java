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

package com.liferay.portlet.shopping.service.persistence.test;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;

import com.liferay.portlet.shopping.NoSuchCategoryException;
import com.liferay.portlet.shopping.model.ShoppingCategory;
import com.liferay.portlet.shopping.service.ShoppingCategoryLocalServiceUtil;
import com.liferay.portlet.shopping.service.persistence.ShoppingCategoryPersistence;
import com.liferay.portlet.shopping.service.persistence.ShoppingCategoryUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

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
public class ShoppingCategoryPersistenceTest {
	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = ShoppingCategoryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<ShoppingCategory> iterator = _shoppingCategories.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ShoppingCategory shoppingCategory = _persistence.create(pk);

		Assert.assertNotNull(shoppingCategory);

		Assert.assertEquals(shoppingCategory.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		ShoppingCategory newShoppingCategory = addShoppingCategory();

		_persistence.remove(newShoppingCategory);

		ShoppingCategory existingShoppingCategory = _persistence.fetchByPrimaryKey(newShoppingCategory.getPrimaryKey());

		Assert.assertNull(existingShoppingCategory);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addShoppingCategory();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ShoppingCategory newShoppingCategory = _persistence.create(pk);

		newShoppingCategory.setGroupId(RandomTestUtil.nextLong());

		newShoppingCategory.setCompanyId(RandomTestUtil.nextLong());

		newShoppingCategory.setUserId(RandomTestUtil.nextLong());

		newShoppingCategory.setUserName(RandomTestUtil.randomString());

		newShoppingCategory.setCreateDate(RandomTestUtil.nextDate());

		newShoppingCategory.setModifiedDate(RandomTestUtil.nextDate());

		newShoppingCategory.setParentCategoryId(RandomTestUtil.nextLong());

		newShoppingCategory.setName(RandomTestUtil.randomString());

		newShoppingCategory.setDescription(RandomTestUtil.randomString());

		_shoppingCategories.add(_persistence.update(newShoppingCategory));

		ShoppingCategory existingShoppingCategory = _persistence.findByPrimaryKey(newShoppingCategory.getPrimaryKey());

		Assert.assertEquals(existingShoppingCategory.getCategoryId(),
			newShoppingCategory.getCategoryId());
		Assert.assertEquals(existingShoppingCategory.getGroupId(),
			newShoppingCategory.getGroupId());
		Assert.assertEquals(existingShoppingCategory.getCompanyId(),
			newShoppingCategory.getCompanyId());
		Assert.assertEquals(existingShoppingCategory.getUserId(),
			newShoppingCategory.getUserId());
		Assert.assertEquals(existingShoppingCategory.getUserName(),
			newShoppingCategory.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingShoppingCategory.getCreateDate()),
			Time.getShortTimestamp(newShoppingCategory.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingShoppingCategory.getModifiedDate()),
			Time.getShortTimestamp(newShoppingCategory.getModifiedDate()));
		Assert.assertEquals(existingShoppingCategory.getParentCategoryId(),
			newShoppingCategory.getParentCategoryId());
		Assert.assertEquals(existingShoppingCategory.getName(),
			newShoppingCategory.getName());
		Assert.assertEquals(existingShoppingCategory.getDescription(),
			newShoppingCategory.getDescription());
	}

	@Test
	public void testCountByGroupId() throws Exception {
		_persistence.countByGroupId(RandomTestUtil.nextLong());

		_persistence.countByGroupId(0L);
	}

	@Test
	public void testCountByG_P() throws Exception {
		_persistence.countByG_P(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByG_P(0L, 0L);
	}

	@Test
	public void testCountByG_N() throws Exception {
		_persistence.countByG_N(RandomTestUtil.nextLong(), StringPool.BLANK);

		_persistence.countByG_N(0L, StringPool.NULL);

		_persistence.countByG_N(0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		ShoppingCategory newShoppingCategory = addShoppingCategory();

		ShoppingCategory existingShoppingCategory = _persistence.findByPrimaryKey(newShoppingCategory.getPrimaryKey());

		Assert.assertEquals(existingShoppingCategory, newShoppingCategory);
	}

	@Test(expected = NoSuchCategoryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	@Test
	public void testFilterFindByGroupId() throws Exception {
		_persistence.filterFindByGroupId(0, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<ShoppingCategory> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("ShoppingCategory",
			"categoryId", true, "groupId", true, "companyId", true, "userId",
			true, "userName", true, "createDate", true, "modifiedDate", true,
			"parentCategoryId", true, "name", true, "description", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		ShoppingCategory newShoppingCategory = addShoppingCategory();

		ShoppingCategory existingShoppingCategory = _persistence.fetchByPrimaryKey(newShoppingCategory.getPrimaryKey());

		Assert.assertEquals(existingShoppingCategory, newShoppingCategory);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ShoppingCategory missingShoppingCategory = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingShoppingCategory);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		ShoppingCategory newShoppingCategory1 = addShoppingCategory();
		ShoppingCategory newShoppingCategory2 = addShoppingCategory();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newShoppingCategory1.getPrimaryKey());
		primaryKeys.add(newShoppingCategory2.getPrimaryKey());

		Map<Serializable, ShoppingCategory> shoppingCategories = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, shoppingCategories.size());
		Assert.assertEquals(newShoppingCategory1,
			shoppingCategories.get(newShoppingCategory1.getPrimaryKey()));
		Assert.assertEquals(newShoppingCategory2,
			shoppingCategories.get(newShoppingCategory2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, ShoppingCategory> shoppingCategories = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(shoppingCategories.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		ShoppingCategory newShoppingCategory = addShoppingCategory();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newShoppingCategory.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, ShoppingCategory> shoppingCategories = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, shoppingCategories.size());
		Assert.assertEquals(newShoppingCategory,
			shoppingCategories.get(newShoppingCategory.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, ShoppingCategory> shoppingCategories = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(shoppingCategories.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		ShoppingCategory newShoppingCategory = addShoppingCategory();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newShoppingCategory.getPrimaryKey());

		Map<Serializable, ShoppingCategory> shoppingCategories = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, shoppingCategories.size());
		Assert.assertEquals(newShoppingCategory,
			shoppingCategories.get(newShoppingCategory.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = ShoppingCategoryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					ShoppingCategory shoppingCategory = (ShoppingCategory)object;

					Assert.assertNotNull(shoppingCategory);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ShoppingCategory newShoppingCategory = addShoppingCategory();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingCategory.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("categoryId",
				newShoppingCategory.getCategoryId()));

		List<ShoppingCategory> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		ShoppingCategory existingShoppingCategory = result.get(0);

		Assert.assertEquals(existingShoppingCategory, newShoppingCategory);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingCategory.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("categoryId",
				RandomTestUtil.nextLong()));

		List<ShoppingCategory> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		ShoppingCategory newShoppingCategory = addShoppingCategory();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingCategory.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("categoryId"));

		Object newCategoryId = newShoppingCategory.getCategoryId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("categoryId",
				new Object[] { newCategoryId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCategoryId = result.get(0);

		Assert.assertEquals(existingCategoryId, newCategoryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingCategory.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("categoryId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("categoryId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		ShoppingCategory newShoppingCategory = addShoppingCategory();

		_persistence.clearCache();

		ShoppingCategory existingShoppingCategory = _persistence.findByPrimaryKey(newShoppingCategory.getPrimaryKey());

		Assert.assertEquals(existingShoppingCategory.getGroupId(),
			ReflectionTestUtil.invoke(existingShoppingCategory,
				"getOriginalGroupId", new Class<?>[0]));
		Assert.assertTrue(Validator.equals(existingShoppingCategory.getName(),
				ReflectionTestUtil.invoke(existingShoppingCategory,
					"getOriginalName", new Class<?>[0])));
	}

	protected ShoppingCategory addShoppingCategory() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ShoppingCategory shoppingCategory = _persistence.create(pk);

		shoppingCategory.setGroupId(RandomTestUtil.nextLong());

		shoppingCategory.setCompanyId(RandomTestUtil.nextLong());

		shoppingCategory.setUserId(RandomTestUtil.nextLong());

		shoppingCategory.setUserName(RandomTestUtil.randomString());

		shoppingCategory.setCreateDate(RandomTestUtil.nextDate());

		shoppingCategory.setModifiedDate(RandomTestUtil.nextDate());

		shoppingCategory.setParentCategoryId(RandomTestUtil.nextLong());

		shoppingCategory.setName(RandomTestUtil.randomString());

		shoppingCategory.setDescription(RandomTestUtil.randomString());

		_shoppingCategories.add(_persistence.update(shoppingCategory));

		return shoppingCategory;
	}

	private List<ShoppingCategory> _shoppingCategories = new ArrayList<ShoppingCategory>();
	private ShoppingCategoryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}