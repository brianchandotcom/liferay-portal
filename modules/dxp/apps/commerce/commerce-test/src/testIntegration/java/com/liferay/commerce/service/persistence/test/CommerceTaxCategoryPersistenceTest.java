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

import com.liferay.commerce.exception.NoSuchTaxCategoryException;
import com.liferay.commerce.model.CommerceTaxCategory;
import com.liferay.commerce.service.CommerceTaxCategoryLocalServiceUtil;
import com.liferay.commerce.service.persistence.CommerceTaxCategoryPersistence;
import com.liferay.commerce.service.persistence.CommerceTaxCategoryUtil;

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
public class CommerceTaxCategoryPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.commerce.service"));

	@Before
	public void setUp() {
		_persistence = CommerceTaxCategoryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CommerceTaxCategory> iterator = _commerceTaxCategories.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceTaxCategory commerceTaxCategory = _persistence.create(pk);

		Assert.assertNotNull(commerceTaxCategory);

		Assert.assertEquals(commerceTaxCategory.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CommerceTaxCategory newCommerceTaxCategory = addCommerceTaxCategory();

		_persistence.remove(newCommerceTaxCategory);

		CommerceTaxCategory existingCommerceTaxCategory = _persistence.fetchByPrimaryKey(newCommerceTaxCategory.getPrimaryKey());

		Assert.assertNull(existingCommerceTaxCategory);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCommerceTaxCategory();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceTaxCategory newCommerceTaxCategory = _persistence.create(pk);

		newCommerceTaxCategory.setGroupId(RandomTestUtil.nextLong());

		newCommerceTaxCategory.setCompanyId(RandomTestUtil.nextLong());

		newCommerceTaxCategory.setUserId(RandomTestUtil.nextLong());

		newCommerceTaxCategory.setUserName(RandomTestUtil.randomString());

		newCommerceTaxCategory.setCreateDate(RandomTestUtil.nextDate());

		newCommerceTaxCategory.setModifiedDate(RandomTestUtil.nextDate());

		newCommerceTaxCategory.setName(RandomTestUtil.randomString());

		newCommerceTaxCategory.setDescription(RandomTestUtil.randomString());

		_commerceTaxCategories.add(_persistence.update(newCommerceTaxCategory));

		CommerceTaxCategory existingCommerceTaxCategory = _persistence.findByPrimaryKey(newCommerceTaxCategory.getPrimaryKey());

		Assert.assertEquals(existingCommerceTaxCategory.getCommerceTaxCategoryId(),
			newCommerceTaxCategory.getCommerceTaxCategoryId());
		Assert.assertEquals(existingCommerceTaxCategory.getGroupId(),
			newCommerceTaxCategory.getGroupId());
		Assert.assertEquals(existingCommerceTaxCategory.getCompanyId(),
			newCommerceTaxCategory.getCompanyId());
		Assert.assertEquals(existingCommerceTaxCategory.getUserId(),
			newCommerceTaxCategory.getUserId());
		Assert.assertEquals(existingCommerceTaxCategory.getUserName(),
			newCommerceTaxCategory.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceTaxCategory.getCreateDate()),
			Time.getShortTimestamp(newCommerceTaxCategory.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceTaxCategory.getModifiedDate()),
			Time.getShortTimestamp(newCommerceTaxCategory.getModifiedDate()));
		Assert.assertEquals(existingCommerceTaxCategory.getName(),
			newCommerceTaxCategory.getName());
		Assert.assertEquals(existingCommerceTaxCategory.getDescription(),
			newCommerceTaxCategory.getDescription());
	}

	@Test
	public void testCountByGroupId() throws Exception {
		_persistence.countByGroupId(RandomTestUtil.nextLong());

		_persistence.countByGroupId(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CommerceTaxCategory newCommerceTaxCategory = addCommerceTaxCategory();

		CommerceTaxCategory existingCommerceTaxCategory = _persistence.findByPrimaryKey(newCommerceTaxCategory.getPrimaryKey());

		Assert.assertEquals(existingCommerceTaxCategory, newCommerceTaxCategory);
	}

	@Test(expected = NoSuchTaxCategoryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<CommerceTaxCategory> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CommerceTaxCategory",
			"commerceTaxCategoryId", true, "groupId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "name", true, "description", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CommerceTaxCategory newCommerceTaxCategory = addCommerceTaxCategory();

		CommerceTaxCategory existingCommerceTaxCategory = _persistence.fetchByPrimaryKey(newCommerceTaxCategory.getPrimaryKey());

		Assert.assertEquals(existingCommerceTaxCategory, newCommerceTaxCategory);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceTaxCategory missingCommerceTaxCategory = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCommerceTaxCategory);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CommerceTaxCategory newCommerceTaxCategory1 = addCommerceTaxCategory();
		CommerceTaxCategory newCommerceTaxCategory2 = addCommerceTaxCategory();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceTaxCategory1.getPrimaryKey());
		primaryKeys.add(newCommerceTaxCategory2.getPrimaryKey());

		Map<Serializable, CommerceTaxCategory> commerceTaxCategories = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, commerceTaxCategories.size());
		Assert.assertEquals(newCommerceTaxCategory1,
			commerceTaxCategories.get(newCommerceTaxCategory1.getPrimaryKey()));
		Assert.assertEquals(newCommerceTaxCategory2,
			commerceTaxCategories.get(newCommerceTaxCategory2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CommerceTaxCategory> commerceTaxCategories = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceTaxCategories.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CommerceTaxCategory newCommerceTaxCategory = addCommerceTaxCategory();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceTaxCategory.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CommerceTaxCategory> commerceTaxCategories = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceTaxCategories.size());
		Assert.assertEquals(newCommerceTaxCategory,
			commerceTaxCategories.get(newCommerceTaxCategory.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CommerceTaxCategory> commerceTaxCategories = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceTaxCategories.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CommerceTaxCategory newCommerceTaxCategory = addCommerceTaxCategory();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceTaxCategory.getPrimaryKey());

		Map<Serializable, CommerceTaxCategory> commerceTaxCategories = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceTaxCategories.size());
		Assert.assertEquals(newCommerceTaxCategory,
			commerceTaxCategories.get(newCommerceTaxCategory.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CommerceTaxCategoryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CommerceTaxCategory>() {
				@Override
				public void performAction(
					CommerceTaxCategory commerceTaxCategory) {
					Assert.assertNotNull(commerceTaxCategory);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CommerceTaxCategory newCommerceTaxCategory = addCommerceTaxCategory();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceTaxCategory.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("commerceTaxCategoryId",
				newCommerceTaxCategory.getCommerceTaxCategoryId()));

		List<CommerceTaxCategory> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CommerceTaxCategory existingCommerceTaxCategory = result.get(0);

		Assert.assertEquals(existingCommerceTaxCategory, newCommerceTaxCategory);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceTaxCategory.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("commerceTaxCategoryId",
				RandomTestUtil.nextLong()));

		List<CommerceTaxCategory> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CommerceTaxCategory newCommerceTaxCategory = addCommerceTaxCategory();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceTaxCategory.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceTaxCategoryId"));

		Object newCommerceTaxCategoryId = newCommerceTaxCategory.getCommerceTaxCategoryId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("commerceTaxCategoryId",
				new Object[] { newCommerceTaxCategoryId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCommerceTaxCategoryId = result.get(0);

		Assert.assertEquals(existingCommerceTaxCategoryId,
			newCommerceTaxCategoryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceTaxCategory.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceTaxCategoryId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("commerceTaxCategoryId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected CommerceTaxCategory addCommerceTaxCategory()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceTaxCategory commerceTaxCategory = _persistence.create(pk);

		commerceTaxCategory.setGroupId(RandomTestUtil.nextLong());

		commerceTaxCategory.setCompanyId(RandomTestUtil.nextLong());

		commerceTaxCategory.setUserId(RandomTestUtil.nextLong());

		commerceTaxCategory.setUserName(RandomTestUtil.randomString());

		commerceTaxCategory.setCreateDate(RandomTestUtil.nextDate());

		commerceTaxCategory.setModifiedDate(RandomTestUtil.nextDate());

		commerceTaxCategory.setName(RandomTestUtil.randomString());

		commerceTaxCategory.setDescription(RandomTestUtil.randomString());

		_commerceTaxCategories.add(_persistence.update(commerceTaxCategory));

		return commerceTaxCategory;
	}

	private List<CommerceTaxCategory> _commerceTaxCategories = new ArrayList<CommerceTaxCategory>();
	private CommerceTaxCategoryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}