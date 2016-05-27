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

package com.liferay.shopping.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;

import com.liferay.shopping.exception.NoSuchItemFieldException;
import com.liferay.shopping.model.ShoppingItemField;
import com.liferay.shopping.service.ShoppingItemFieldLocalServiceUtil;
import com.liferay.shopping.service.persistence.ShoppingItemFieldPersistence;
import com.liferay.shopping.service.persistence.ShoppingItemFieldUtil;

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
public class ShoppingItemFieldPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = ShoppingItemFieldUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<ShoppingItemField> iterator = _shoppingItemFields.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ShoppingItemField shoppingItemField = _persistence.create(pk);

		Assert.assertNotNull(shoppingItemField);

		Assert.assertEquals(shoppingItemField.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		ShoppingItemField newShoppingItemField = addShoppingItemField();

		_persistence.remove(newShoppingItemField);

		ShoppingItemField existingShoppingItemField = _persistence.fetchByPrimaryKey(newShoppingItemField.getPrimaryKey());

		Assert.assertNull(existingShoppingItemField);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addShoppingItemField();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ShoppingItemField newShoppingItemField = _persistence.create(pk);

		newShoppingItemField.setCompanyId(RandomTestUtil.nextLong());

		newShoppingItemField.setItemId(RandomTestUtil.nextLong());

		newShoppingItemField.setName(RandomTestUtil.randomString());

		newShoppingItemField.setValues(RandomTestUtil.randomString());

		newShoppingItemField.setDescription(RandomTestUtil.randomString());

		_shoppingItemFields.add(_persistence.update(newShoppingItemField));

		ShoppingItemField existingShoppingItemField = _persistence.findByPrimaryKey(newShoppingItemField.getPrimaryKey());

		Assert.assertEquals(existingShoppingItemField.getItemFieldId(),
			newShoppingItemField.getItemFieldId());
		Assert.assertEquals(existingShoppingItemField.getCompanyId(),
			newShoppingItemField.getCompanyId());
		Assert.assertEquals(existingShoppingItemField.getItemId(),
			newShoppingItemField.getItemId());
		Assert.assertEquals(existingShoppingItemField.getName(),
			newShoppingItemField.getName());
		Assert.assertEquals(existingShoppingItemField.getValues(),
			newShoppingItemField.getValues());
		Assert.assertEquals(existingShoppingItemField.getDescription(),
			newShoppingItemField.getDescription());
	}

	@Test
	public void testCountByItemId() throws Exception {
		_persistence.countByItemId(RandomTestUtil.nextLong());

		_persistence.countByItemId(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		ShoppingItemField newShoppingItemField = addShoppingItemField();

		ShoppingItemField existingShoppingItemField = _persistence.findByPrimaryKey(newShoppingItemField.getPrimaryKey());

		Assert.assertEquals(existingShoppingItemField, newShoppingItemField);
	}

	@Test(expected = NoSuchItemFieldException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<ShoppingItemField> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("ShoppingItemField",
			"itemFieldId", true, "companyId", true, "itemId", true, "name",
			true, "values", true, "description", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		ShoppingItemField newShoppingItemField = addShoppingItemField();

		ShoppingItemField existingShoppingItemField = _persistence.fetchByPrimaryKey(newShoppingItemField.getPrimaryKey());

		Assert.assertEquals(existingShoppingItemField, newShoppingItemField);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ShoppingItemField missingShoppingItemField = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingShoppingItemField);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		ShoppingItemField newShoppingItemField1 = addShoppingItemField();
		ShoppingItemField newShoppingItemField2 = addShoppingItemField();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newShoppingItemField1.getPrimaryKey());
		primaryKeys.add(newShoppingItemField2.getPrimaryKey());

		Map<Serializable, ShoppingItemField> shoppingItemFields = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, shoppingItemFields.size());
		Assert.assertEquals(newShoppingItemField1,
			shoppingItemFields.get(newShoppingItemField1.getPrimaryKey()));
		Assert.assertEquals(newShoppingItemField2,
			shoppingItemFields.get(newShoppingItemField2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, ShoppingItemField> shoppingItemFields = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(shoppingItemFields.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		ShoppingItemField newShoppingItemField = addShoppingItemField();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newShoppingItemField.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, ShoppingItemField> shoppingItemFields = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, shoppingItemFields.size());
		Assert.assertEquals(newShoppingItemField,
			shoppingItemFields.get(newShoppingItemField.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, ShoppingItemField> shoppingItemFields = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(shoppingItemFields.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		ShoppingItemField newShoppingItemField = addShoppingItemField();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newShoppingItemField.getPrimaryKey());

		Map<Serializable, ShoppingItemField> shoppingItemFields = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, shoppingItemFields.size());
		Assert.assertEquals(newShoppingItemField,
			shoppingItemFields.get(newShoppingItemField.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = ShoppingItemFieldLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<ShoppingItemField>() {
				@Override
				public void performAction(ShoppingItemField shoppingItemField) {
					Assert.assertNotNull(shoppingItemField);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ShoppingItemField newShoppingItemField = addShoppingItemField();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingItemField.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("itemFieldId",
				newShoppingItemField.getItemFieldId()));

		List<ShoppingItemField> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		ShoppingItemField existingShoppingItemField = result.get(0);

		Assert.assertEquals(existingShoppingItemField, newShoppingItemField);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingItemField.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("itemFieldId",
				RandomTestUtil.nextLong()));

		List<ShoppingItemField> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		ShoppingItemField newShoppingItemField = addShoppingItemField();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingItemField.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("itemFieldId"));

		Object newItemFieldId = newShoppingItemField.getItemFieldId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("itemFieldId",
				new Object[] { newItemFieldId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingItemFieldId = result.get(0);

		Assert.assertEquals(existingItemFieldId, newItemFieldId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingItemField.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("itemFieldId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("itemFieldId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected ShoppingItemField addShoppingItemField()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		ShoppingItemField shoppingItemField = _persistence.create(pk);

		shoppingItemField.setCompanyId(RandomTestUtil.nextLong());

		shoppingItemField.setItemId(RandomTestUtil.nextLong());

		shoppingItemField.setName(RandomTestUtil.randomString());

		shoppingItemField.setValues(RandomTestUtil.randomString());

		shoppingItemField.setDescription(RandomTestUtil.randomString());

		_shoppingItemFields.add(_persistence.update(shoppingItemField));

		return shoppingItemField;
	}

	private List<ShoppingItemField> _shoppingItemFields = new ArrayList<ShoppingItemField>();
	private ShoppingItemFieldPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}