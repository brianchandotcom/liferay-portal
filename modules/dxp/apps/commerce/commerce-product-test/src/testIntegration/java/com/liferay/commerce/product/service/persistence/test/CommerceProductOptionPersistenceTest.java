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

import com.liferay.commerce.product.exception.NoSuchProductOptionException;
import com.liferay.commerce.product.model.CommerceProductOption;
import com.liferay.commerce.product.service.CommerceProductOptionLocalServiceUtil;
import com.liferay.commerce.product.service.persistence.CommerceProductOptionPersistence;
import com.liferay.commerce.product.service.persistence.CommerceProductOptionUtil;

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
public class CommerceProductOptionPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.commerce.product.service"));

	@Before
	public void setUp() {
		_persistence = CommerceProductOptionUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CommerceProductOption> iterator = _commerceProductOptions.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceProductOption commerceProductOption = _persistence.create(pk);

		Assert.assertNotNull(commerceProductOption);

		Assert.assertEquals(commerceProductOption.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CommerceProductOption newCommerceProductOption = addCommerceProductOption();

		_persistence.remove(newCommerceProductOption);

		CommerceProductOption existingCommerceProductOption = _persistence.fetchByPrimaryKey(newCommerceProductOption.getPrimaryKey());

		Assert.assertNull(existingCommerceProductOption);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCommerceProductOption();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceProductOption newCommerceProductOption = _persistence.create(pk);

		newCommerceProductOption.setGroupId(RandomTestUtil.nextLong());

		newCommerceProductOption.setCompanyId(RandomTestUtil.nextLong());

		newCommerceProductOption.setUserId(RandomTestUtil.nextLong());

		newCommerceProductOption.setUserName(RandomTestUtil.randomString());

		newCommerceProductOption.setCreateDate(RandomTestUtil.nextDate());

		newCommerceProductOption.setModifiedDate(RandomTestUtil.nextDate());

		newCommerceProductOption.setName(RandomTestUtil.randomString());

		newCommerceProductOption.setDescription(RandomTestUtil.randomString());

		newCommerceProductOption.setDDMFormFieldTypeName(RandomTestUtil.randomString());

		_commerceProductOptions.add(_persistence.update(
				newCommerceProductOption));

		CommerceProductOption existingCommerceProductOption = _persistence.findByPrimaryKey(newCommerceProductOption.getPrimaryKey());

		Assert.assertEquals(existingCommerceProductOption.getCommerceProductOptionId(),
			newCommerceProductOption.getCommerceProductOptionId());
		Assert.assertEquals(existingCommerceProductOption.getGroupId(),
			newCommerceProductOption.getGroupId());
		Assert.assertEquals(existingCommerceProductOption.getCompanyId(),
			newCommerceProductOption.getCompanyId());
		Assert.assertEquals(existingCommerceProductOption.getUserId(),
			newCommerceProductOption.getUserId());
		Assert.assertEquals(existingCommerceProductOption.getUserName(),
			newCommerceProductOption.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceProductOption.getCreateDate()),
			Time.getShortTimestamp(newCommerceProductOption.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceProductOption.getModifiedDate()),
			Time.getShortTimestamp(newCommerceProductOption.getModifiedDate()));
		Assert.assertEquals(existingCommerceProductOption.getName(),
			newCommerceProductOption.getName());
		Assert.assertEquals(existingCommerceProductOption.getDescription(),
			newCommerceProductOption.getDescription());
		Assert.assertEquals(existingCommerceProductOption.getDDMFormFieldTypeName(),
			newCommerceProductOption.getDDMFormFieldTypeName());
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
	public void testFindByPrimaryKeyExisting() throws Exception {
		CommerceProductOption newCommerceProductOption = addCommerceProductOption();

		CommerceProductOption existingCommerceProductOption = _persistence.findByPrimaryKey(newCommerceProductOption.getPrimaryKey());

		Assert.assertEquals(existingCommerceProductOption,
			newCommerceProductOption);
	}

	@Test(expected = NoSuchProductOptionException.class)
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

	protected OrderByComparator<CommerceProductOption> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CommerceProductOption",
			"commerceProductOptionId", true, "groupId", true, "companyId",
			true, "userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "name", true, "description", true,
			"DDMFormFieldTypeName", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CommerceProductOption newCommerceProductOption = addCommerceProductOption();

		CommerceProductOption existingCommerceProductOption = _persistence.fetchByPrimaryKey(newCommerceProductOption.getPrimaryKey());

		Assert.assertEquals(existingCommerceProductOption,
			newCommerceProductOption);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceProductOption missingCommerceProductOption = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCommerceProductOption);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CommerceProductOption newCommerceProductOption1 = addCommerceProductOption();
		CommerceProductOption newCommerceProductOption2 = addCommerceProductOption();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceProductOption1.getPrimaryKey());
		primaryKeys.add(newCommerceProductOption2.getPrimaryKey());

		Map<Serializable, CommerceProductOption> commerceProductOptions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, commerceProductOptions.size());
		Assert.assertEquals(newCommerceProductOption1,
			commerceProductOptions.get(
				newCommerceProductOption1.getPrimaryKey()));
		Assert.assertEquals(newCommerceProductOption2,
			commerceProductOptions.get(
				newCommerceProductOption2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CommerceProductOption> commerceProductOptions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceProductOptions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CommerceProductOption newCommerceProductOption = addCommerceProductOption();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceProductOption.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CommerceProductOption> commerceProductOptions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceProductOptions.size());
		Assert.assertEquals(newCommerceProductOption,
			commerceProductOptions.get(newCommerceProductOption.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CommerceProductOption> commerceProductOptions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceProductOptions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CommerceProductOption newCommerceProductOption = addCommerceProductOption();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceProductOption.getPrimaryKey());

		Map<Serializable, CommerceProductOption> commerceProductOptions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceProductOptions.size());
		Assert.assertEquals(newCommerceProductOption,
			commerceProductOptions.get(newCommerceProductOption.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CommerceProductOptionLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CommerceProductOption>() {
				@Override
				public void performAction(
					CommerceProductOption commerceProductOption) {
					Assert.assertNotNull(commerceProductOption);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CommerceProductOption newCommerceProductOption = addCommerceProductOption();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceProductOption.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("commerceProductOptionId",
				newCommerceProductOption.getCommerceProductOptionId()));

		List<CommerceProductOption> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CommerceProductOption existingCommerceProductOption = result.get(0);

		Assert.assertEquals(existingCommerceProductOption,
			newCommerceProductOption);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceProductOption.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("commerceProductOptionId",
				RandomTestUtil.nextLong()));

		List<CommerceProductOption> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CommerceProductOption newCommerceProductOption = addCommerceProductOption();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceProductOption.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceProductOptionId"));

		Object newCommerceProductOptionId = newCommerceProductOption.getCommerceProductOptionId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("commerceProductOptionId",
				new Object[] { newCommerceProductOptionId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCommerceProductOptionId = result.get(0);

		Assert.assertEquals(existingCommerceProductOptionId,
			newCommerceProductOptionId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceProductOption.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceProductOptionId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("commerceProductOptionId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected CommerceProductOption addCommerceProductOption()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceProductOption commerceProductOption = _persistence.create(pk);

		commerceProductOption.setGroupId(RandomTestUtil.nextLong());

		commerceProductOption.setCompanyId(RandomTestUtil.nextLong());

		commerceProductOption.setUserId(RandomTestUtil.nextLong());

		commerceProductOption.setUserName(RandomTestUtil.randomString());

		commerceProductOption.setCreateDate(RandomTestUtil.nextDate());

		commerceProductOption.setModifiedDate(RandomTestUtil.nextDate());

		commerceProductOption.setName(RandomTestUtil.randomString());

		commerceProductOption.setDescription(RandomTestUtil.randomString());

		commerceProductOption.setDDMFormFieldTypeName(RandomTestUtil.randomString());

		_commerceProductOptions.add(_persistence.update(commerceProductOption));

		return commerceProductOption;
	}

	private List<CommerceProductOption> _commerceProductOptions = new ArrayList<CommerceProductOption>();
	private CommerceProductOptionPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}