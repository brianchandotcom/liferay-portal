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

import com.liferay.commerce.product.exception.NoSuchCPOptionCategoryException;
import com.liferay.commerce.product.model.CPOptionCategory;
import com.liferay.commerce.product.service.CPOptionCategoryLocalServiceUtil;
import com.liferay.commerce.product.service.persistence.CPOptionCategoryPersistence;
import com.liferay.commerce.product.service.persistence.CPOptionCategoryUtil;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.AssertUtils;
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
import java.util.Objects;
import java.util.Set;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class CPOptionCategoryPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.commerce.product.service"));

	@Before
	public void setUp() {
		_persistence = CPOptionCategoryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CPOptionCategory> iterator = _cpOptionCategories.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPOptionCategory cpOptionCategory = _persistence.create(pk);

		Assert.assertNotNull(cpOptionCategory);

		Assert.assertEquals(cpOptionCategory.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CPOptionCategory newCPOptionCategory = addCPOptionCategory();

		_persistence.remove(newCPOptionCategory);

		CPOptionCategory existingCPOptionCategory = _persistence.fetchByPrimaryKey(newCPOptionCategory.getPrimaryKey());

		Assert.assertNull(existingCPOptionCategory);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCPOptionCategory();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPOptionCategory newCPOptionCategory = _persistence.create(pk);

		newCPOptionCategory.setUuid(RandomTestUtil.randomString());

		newCPOptionCategory.setGroupId(RandomTestUtil.nextLong());

		newCPOptionCategory.setCompanyId(RandomTestUtil.nextLong());

		newCPOptionCategory.setUserId(RandomTestUtil.nextLong());

		newCPOptionCategory.setUserName(RandomTestUtil.randomString());

		newCPOptionCategory.setCreateDate(RandomTestUtil.nextDate());

		newCPOptionCategory.setModifiedDate(RandomTestUtil.nextDate());

		newCPOptionCategory.setTitle(RandomTestUtil.randomString());

		newCPOptionCategory.setDescription(RandomTestUtil.randomString());

		newCPOptionCategory.setPriority(RandomTestUtil.nextDouble());

		newCPOptionCategory.setKey(RandomTestUtil.randomString());

		newCPOptionCategory.setLastPublishDate(RandomTestUtil.nextDate());

		_cpOptionCategories.add(_persistence.update(newCPOptionCategory));

		CPOptionCategory existingCPOptionCategory = _persistence.findByPrimaryKey(newCPOptionCategory.getPrimaryKey());

		Assert.assertEquals(existingCPOptionCategory.getUuid(),
			newCPOptionCategory.getUuid());
		Assert.assertEquals(existingCPOptionCategory.getCPOptionCategoryId(),
			newCPOptionCategory.getCPOptionCategoryId());
		Assert.assertEquals(existingCPOptionCategory.getGroupId(),
			newCPOptionCategory.getGroupId());
		Assert.assertEquals(existingCPOptionCategory.getCompanyId(),
			newCPOptionCategory.getCompanyId());
		Assert.assertEquals(existingCPOptionCategory.getUserId(),
			newCPOptionCategory.getUserId());
		Assert.assertEquals(existingCPOptionCategory.getUserName(),
			newCPOptionCategory.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCPOptionCategory.getCreateDate()),
			Time.getShortTimestamp(newCPOptionCategory.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCPOptionCategory.getModifiedDate()),
			Time.getShortTimestamp(newCPOptionCategory.getModifiedDate()));
		Assert.assertEquals(existingCPOptionCategory.getTitle(),
			newCPOptionCategory.getTitle());
		Assert.assertEquals(existingCPOptionCategory.getDescription(),
			newCPOptionCategory.getDescription());
		AssertUtils.assertEquals(existingCPOptionCategory.getPriority(),
			newCPOptionCategory.getPriority());
		Assert.assertEquals(existingCPOptionCategory.getKey(),
			newCPOptionCategory.getKey());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCPOptionCategory.getLastPublishDate()),
			Time.getShortTimestamp(newCPOptionCategory.getLastPublishDate()));
	}

	@Test
	public void testCountByUuid() throws Exception {
		_persistence.countByUuid("");

		_persistence.countByUuid("null");

		_persistence.countByUuid((String)null);
	}

	@Test
	public void testCountByUUID_G() throws Exception {
		_persistence.countByUUID_G("", RandomTestUtil.nextLong());

		_persistence.countByUUID_G("null", 0L);

		_persistence.countByUUID_G((String)null, 0L);
	}

	@Test
	public void testCountByUuid_C() throws Exception {
		_persistence.countByUuid_C("", RandomTestUtil.nextLong());

		_persistence.countByUuid_C("null", 0L);

		_persistence.countByUuid_C((String)null, 0L);
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
	public void testCountByG_K() throws Exception {
		_persistence.countByG_K(RandomTestUtil.nextLong(), "");

		_persistence.countByG_K(0L, "null");

		_persistence.countByG_K(0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CPOptionCategory newCPOptionCategory = addCPOptionCategory();

		CPOptionCategory existingCPOptionCategory = _persistence.findByPrimaryKey(newCPOptionCategory.getPrimaryKey());

		Assert.assertEquals(existingCPOptionCategory, newCPOptionCategory);
	}

	@Test(expected = NoSuchCPOptionCategoryException.class)
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

	protected OrderByComparator<CPOptionCategory> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CPOptionCategory", "uuid",
			true, "CPOptionCategoryId", true, "groupId", true, "companyId",
			true, "userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "title", true, "description", true,
			"priority", true, "key", true, "lastPublishDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CPOptionCategory newCPOptionCategory = addCPOptionCategory();

		CPOptionCategory existingCPOptionCategory = _persistence.fetchByPrimaryKey(newCPOptionCategory.getPrimaryKey());

		Assert.assertEquals(existingCPOptionCategory, newCPOptionCategory);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPOptionCategory missingCPOptionCategory = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCPOptionCategory);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CPOptionCategory newCPOptionCategory1 = addCPOptionCategory();
		CPOptionCategory newCPOptionCategory2 = addCPOptionCategory();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCPOptionCategory1.getPrimaryKey());
		primaryKeys.add(newCPOptionCategory2.getPrimaryKey());

		Map<Serializable, CPOptionCategory> cpOptionCategories = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, cpOptionCategories.size());
		Assert.assertEquals(newCPOptionCategory1,
			cpOptionCategories.get(newCPOptionCategory1.getPrimaryKey()));
		Assert.assertEquals(newCPOptionCategory2,
			cpOptionCategories.get(newCPOptionCategory2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CPOptionCategory> cpOptionCategories = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(cpOptionCategories.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CPOptionCategory newCPOptionCategory = addCPOptionCategory();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCPOptionCategory.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CPOptionCategory> cpOptionCategories = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, cpOptionCategories.size());
		Assert.assertEquals(newCPOptionCategory,
			cpOptionCategories.get(newCPOptionCategory.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CPOptionCategory> cpOptionCategories = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(cpOptionCategories.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CPOptionCategory newCPOptionCategory = addCPOptionCategory();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCPOptionCategory.getPrimaryKey());

		Map<Serializable, CPOptionCategory> cpOptionCategories = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, cpOptionCategories.size());
		Assert.assertEquals(newCPOptionCategory,
			cpOptionCategories.get(newCPOptionCategory.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CPOptionCategoryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CPOptionCategory>() {
				@Override
				public void performAction(CPOptionCategory cpOptionCategory) {
					Assert.assertNotNull(cpOptionCategory);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CPOptionCategory newCPOptionCategory = addCPOptionCategory();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CPOptionCategory.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("CPOptionCategoryId",
				newCPOptionCategory.getCPOptionCategoryId()));

		List<CPOptionCategory> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CPOptionCategory existingCPOptionCategory = result.get(0);

		Assert.assertEquals(existingCPOptionCategory, newCPOptionCategory);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CPOptionCategory.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("CPOptionCategoryId",
				RandomTestUtil.nextLong()));

		List<CPOptionCategory> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CPOptionCategory newCPOptionCategory = addCPOptionCategory();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CPOptionCategory.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"CPOptionCategoryId"));

		Object newCPOptionCategoryId = newCPOptionCategory.getCPOptionCategoryId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("CPOptionCategoryId",
				new Object[] { newCPOptionCategoryId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCPOptionCategoryId = result.get(0);

		Assert.assertEquals(existingCPOptionCategoryId, newCPOptionCategoryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CPOptionCategory.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"CPOptionCategoryId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("CPOptionCategoryId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		CPOptionCategory newCPOptionCategory = addCPOptionCategory();

		_persistence.clearCache();

		CPOptionCategory existingCPOptionCategory = _persistence.findByPrimaryKey(newCPOptionCategory.getPrimaryKey());

		Assert.assertTrue(Objects.equals(existingCPOptionCategory.getUuid(),
				ReflectionTestUtil.invoke(existingCPOptionCategory,
					"getOriginalUuid", new Class<?>[0])));
		Assert.assertEquals(Long.valueOf(existingCPOptionCategory.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingCPOptionCategory,
				"getOriginalGroupId", new Class<?>[0]));

		Assert.assertEquals(Long.valueOf(existingCPOptionCategory.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingCPOptionCategory,
				"getOriginalGroupId", new Class<?>[0]));
		Assert.assertTrue(Objects.equals(existingCPOptionCategory.getKey(),
				ReflectionTestUtil.invoke(existingCPOptionCategory,
					"getOriginalKey", new Class<?>[0])));
	}

	protected CPOptionCategory addCPOptionCategory() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPOptionCategory cpOptionCategory = _persistence.create(pk);

		cpOptionCategory.setUuid(RandomTestUtil.randomString());

		cpOptionCategory.setGroupId(RandomTestUtil.nextLong());

		cpOptionCategory.setCompanyId(RandomTestUtil.nextLong());

		cpOptionCategory.setUserId(RandomTestUtil.nextLong());

		cpOptionCategory.setUserName(RandomTestUtil.randomString());

		cpOptionCategory.setCreateDate(RandomTestUtil.nextDate());

		cpOptionCategory.setModifiedDate(RandomTestUtil.nextDate());

		cpOptionCategory.setTitle(RandomTestUtil.randomString());

		cpOptionCategory.setDescription(RandomTestUtil.randomString());

		cpOptionCategory.setPriority(RandomTestUtil.nextDouble());

		cpOptionCategory.setKey(RandomTestUtil.randomString());

		cpOptionCategory.setLastPublishDate(RandomTestUtil.nextDate());

		_cpOptionCategories.add(_persistence.update(cpOptionCategory));

		return cpOptionCategory;
	}

	private List<CPOptionCategory> _cpOptionCategories = new ArrayList<CPOptionCategory>();
	private CPOptionCategoryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}