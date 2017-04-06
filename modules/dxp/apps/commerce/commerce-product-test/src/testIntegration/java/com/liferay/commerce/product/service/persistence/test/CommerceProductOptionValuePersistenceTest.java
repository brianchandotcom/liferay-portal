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

import com.liferay.commerce.product.exception.NoSuchProductOptionValueException;
import com.liferay.commerce.product.model.CommerceProductOptionValue;
import com.liferay.commerce.product.service.CommerceProductOptionValueLocalServiceUtil;
import com.liferay.commerce.product.service.persistence.CommerceProductOptionValuePersistence;
import com.liferay.commerce.product.service.persistence.CommerceProductOptionValueUtil;

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
public class CommerceProductOptionValuePersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.commerce.product.service"));

	@Before
	public void setUp() {
		_persistence = CommerceProductOptionValueUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CommerceProductOptionValue> iterator = _commerceProductOptionValues.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceProductOptionValue commerceProductOptionValue = _persistence.create(pk);

		Assert.assertNotNull(commerceProductOptionValue);

		Assert.assertEquals(commerceProductOptionValue.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CommerceProductOptionValue newCommerceProductOptionValue = addCommerceProductOptionValue();

		_persistence.remove(newCommerceProductOptionValue);

		CommerceProductOptionValue existingCommerceProductOptionValue = _persistence.fetchByPrimaryKey(newCommerceProductOptionValue.getPrimaryKey());

		Assert.assertNull(existingCommerceProductOptionValue);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCommerceProductOptionValue();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceProductOptionValue newCommerceProductOptionValue = _persistence.create(pk);

		newCommerceProductOptionValue.setGroupId(RandomTestUtil.nextLong());

		newCommerceProductOptionValue.setCompanyId(RandomTestUtil.nextLong());

		newCommerceProductOptionValue.setUserId(RandomTestUtil.nextLong());

		newCommerceProductOptionValue.setUserName(RandomTestUtil.randomString());

		newCommerceProductOptionValue.setCreateDate(RandomTestUtil.nextDate());

		newCommerceProductOptionValue.setModifiedDate(RandomTestUtil.nextDate());

		newCommerceProductOptionValue.setCommerceProductOptionId(RandomTestUtil.nextLong());

		newCommerceProductOptionValue.setTitle(RandomTestUtil.randomString());

		newCommerceProductOptionValue.setPriority(RandomTestUtil.nextInt());

		_commerceProductOptionValues.add(_persistence.update(
				newCommerceProductOptionValue));

		CommerceProductOptionValue existingCommerceProductOptionValue = _persistence.findByPrimaryKey(newCommerceProductOptionValue.getPrimaryKey());

		Assert.assertEquals(existingCommerceProductOptionValue.getCommerceProductOptionValueId(),
			newCommerceProductOptionValue.getCommerceProductOptionValueId());
		Assert.assertEquals(existingCommerceProductOptionValue.getGroupId(),
			newCommerceProductOptionValue.getGroupId());
		Assert.assertEquals(existingCommerceProductOptionValue.getCompanyId(),
			newCommerceProductOptionValue.getCompanyId());
		Assert.assertEquals(existingCommerceProductOptionValue.getUserId(),
			newCommerceProductOptionValue.getUserId());
		Assert.assertEquals(existingCommerceProductOptionValue.getUserName(),
			newCommerceProductOptionValue.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceProductOptionValue.getCreateDate()),
			Time.getShortTimestamp(
				newCommerceProductOptionValue.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceProductOptionValue.getModifiedDate()),
			Time.getShortTimestamp(
				newCommerceProductOptionValue.getModifiedDate()));
		Assert.assertEquals(existingCommerceProductOptionValue.getCommerceProductOptionId(),
			newCommerceProductOptionValue.getCommerceProductOptionId());
		Assert.assertEquals(existingCommerceProductOptionValue.getTitle(),
			newCommerceProductOptionValue.getTitle());
		Assert.assertEquals(existingCommerceProductOptionValue.getPriority(),
			newCommerceProductOptionValue.getPriority());
	}

	@Test
	public void testCountByGroupId() throws Exception {
		_persistence.countByGroupId(RandomTestUtil.nextLong());

		_persistence.countByGroupId(0L);
	}

	@Test
	public void testCountByCompanyId() throws Exception {
		_persistence.countByCompanyId(RandomTestUtil.nextLong());

		_persistence.countByCompanyId(0L);
	}

	@Test
	public void testCountByCommerceProductOptionId() throws Exception {
		_persistence.countByCommerceProductOptionId(RandomTestUtil.nextLong());

		_persistence.countByCommerceProductOptionId(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CommerceProductOptionValue newCommerceProductOptionValue = addCommerceProductOptionValue();

		CommerceProductOptionValue existingCommerceProductOptionValue = _persistence.findByPrimaryKey(newCommerceProductOptionValue.getPrimaryKey());

		Assert.assertEquals(existingCommerceProductOptionValue,
			newCommerceProductOptionValue);
	}

	@Test(expected = NoSuchProductOptionValueException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<CommerceProductOptionValue> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CommerceProductOptionValue",
			"commerceProductOptionValueId", true, "groupId", true, "companyId",
			true, "userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "commerceProductOptionId", true, "title",
			true, "priority", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CommerceProductOptionValue newCommerceProductOptionValue = addCommerceProductOptionValue();

		CommerceProductOptionValue existingCommerceProductOptionValue = _persistence.fetchByPrimaryKey(newCommerceProductOptionValue.getPrimaryKey());

		Assert.assertEquals(existingCommerceProductOptionValue,
			newCommerceProductOptionValue);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceProductOptionValue missingCommerceProductOptionValue = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCommerceProductOptionValue);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CommerceProductOptionValue newCommerceProductOptionValue1 = addCommerceProductOptionValue();
		CommerceProductOptionValue newCommerceProductOptionValue2 = addCommerceProductOptionValue();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceProductOptionValue1.getPrimaryKey());
		primaryKeys.add(newCommerceProductOptionValue2.getPrimaryKey());

		Map<Serializable, CommerceProductOptionValue> commerceProductOptionValues =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, commerceProductOptionValues.size());
		Assert.assertEquals(newCommerceProductOptionValue1,
			commerceProductOptionValues.get(
				newCommerceProductOptionValue1.getPrimaryKey()));
		Assert.assertEquals(newCommerceProductOptionValue2,
			commerceProductOptionValues.get(
				newCommerceProductOptionValue2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CommerceProductOptionValue> commerceProductOptionValues =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceProductOptionValues.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CommerceProductOptionValue newCommerceProductOptionValue = addCommerceProductOptionValue();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceProductOptionValue.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CommerceProductOptionValue> commerceProductOptionValues =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceProductOptionValues.size());
		Assert.assertEquals(newCommerceProductOptionValue,
			commerceProductOptionValues.get(
				newCommerceProductOptionValue.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CommerceProductOptionValue> commerceProductOptionValues =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceProductOptionValues.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CommerceProductOptionValue newCommerceProductOptionValue = addCommerceProductOptionValue();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceProductOptionValue.getPrimaryKey());

		Map<Serializable, CommerceProductOptionValue> commerceProductOptionValues =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceProductOptionValues.size());
		Assert.assertEquals(newCommerceProductOptionValue,
			commerceProductOptionValues.get(
				newCommerceProductOptionValue.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CommerceProductOptionValueLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CommerceProductOptionValue>() {
				@Override
				public void performAction(
					CommerceProductOptionValue commerceProductOptionValue) {
					Assert.assertNotNull(commerceProductOptionValue);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CommerceProductOptionValue newCommerceProductOptionValue = addCommerceProductOptionValue();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceProductOptionValue.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"commerceProductOptionValueId",
				newCommerceProductOptionValue.getCommerceProductOptionValueId()));

		List<CommerceProductOptionValue> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CommerceProductOptionValue existingCommerceProductOptionValue = result.get(0);

		Assert.assertEquals(existingCommerceProductOptionValue,
			newCommerceProductOptionValue);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceProductOptionValue.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"commerceProductOptionValueId", RandomTestUtil.nextLong()));

		List<CommerceProductOptionValue> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CommerceProductOptionValue newCommerceProductOptionValue = addCommerceProductOptionValue();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceProductOptionValue.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceProductOptionValueId"));

		Object newCommerceProductOptionValueId = newCommerceProductOptionValue.getCommerceProductOptionValueId();

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"commerceProductOptionValueId",
				new Object[] { newCommerceProductOptionValueId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCommerceProductOptionValueId = result.get(0);

		Assert.assertEquals(existingCommerceProductOptionValueId,
			newCommerceProductOptionValueId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceProductOptionValue.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceProductOptionValueId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"commerceProductOptionValueId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected CommerceProductOptionValue addCommerceProductOptionValue()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceProductOptionValue commerceProductOptionValue = _persistence.create(pk);

		commerceProductOptionValue.setGroupId(RandomTestUtil.nextLong());

		commerceProductOptionValue.setCompanyId(RandomTestUtil.nextLong());

		commerceProductOptionValue.setUserId(RandomTestUtil.nextLong());

		commerceProductOptionValue.setUserName(RandomTestUtil.randomString());

		commerceProductOptionValue.setCreateDate(RandomTestUtil.nextDate());

		commerceProductOptionValue.setModifiedDate(RandomTestUtil.nextDate());

		commerceProductOptionValue.setCommerceProductOptionId(RandomTestUtil.nextLong());

		commerceProductOptionValue.setTitle(RandomTestUtil.randomString());

		commerceProductOptionValue.setPriority(RandomTestUtil.nextInt());

		_commerceProductOptionValues.add(_persistence.update(
				commerceProductOptionValue));

		return commerceProductOptionValue;
	}

	private List<CommerceProductOptionValue> _commerceProductOptionValues = new ArrayList<CommerceProductOptionValue>();
	private CommerceProductOptionValuePersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}