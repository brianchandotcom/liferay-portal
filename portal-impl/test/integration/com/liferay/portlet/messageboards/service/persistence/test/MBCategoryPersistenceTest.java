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

package com.liferay.portlet.messageboards.service.persistence.test;

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

import com.liferay.portlet.messageboards.NoSuchCategoryException;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.service.MBCategoryLocalServiceUtil;
import com.liferay.portlet.messageboards.service.persistence.MBCategoryPersistence;
import com.liferay.portlet.messageboards.service.persistence.MBCategoryUtil;

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
public class MBCategoryPersistenceTest {
	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = MBCategoryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<MBCategory> iterator = _mbCategories.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MBCategory mbCategory = _persistence.create(pk);

		Assert.assertNotNull(mbCategory);

		Assert.assertEquals(mbCategory.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		MBCategory newMBCategory = addMBCategory();

		_persistence.remove(newMBCategory);

		MBCategory existingMBCategory = _persistence.fetchByPrimaryKey(newMBCategory.getPrimaryKey());

		Assert.assertNull(existingMBCategory);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addMBCategory();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MBCategory newMBCategory = _persistence.create(pk);

		newMBCategory.setUuid(RandomTestUtil.randomString());

		newMBCategory.setGroupId(RandomTestUtil.nextLong());

		newMBCategory.setCompanyId(RandomTestUtil.nextLong());

		newMBCategory.setUserId(RandomTestUtil.nextLong());

		newMBCategory.setUserName(RandomTestUtil.randomString());

		newMBCategory.setCreateDate(RandomTestUtil.nextDate());

		newMBCategory.setModifiedDate(RandomTestUtil.nextDate());

		newMBCategory.setParentCategoryId(RandomTestUtil.nextLong());

		newMBCategory.setName(RandomTestUtil.randomString());

		newMBCategory.setDescription(RandomTestUtil.randomString());

		newMBCategory.setDisplayStyle(RandomTestUtil.randomString());

		newMBCategory.setThreadCount(RandomTestUtil.nextInt());

		newMBCategory.setMessageCount(RandomTestUtil.nextInt());

		newMBCategory.setLastPostDate(RandomTestUtil.nextDate());

		newMBCategory.setStatus(RandomTestUtil.nextInt());

		newMBCategory.setStatusByUserId(RandomTestUtil.nextLong());

		newMBCategory.setStatusByUserName(RandomTestUtil.randomString());

		newMBCategory.setStatusDate(RandomTestUtil.nextDate());

		_mbCategories.add(_persistence.update(newMBCategory));

		MBCategory existingMBCategory = _persistence.findByPrimaryKey(newMBCategory.getPrimaryKey());

		Assert.assertEquals(existingMBCategory.getUuid(),
			newMBCategory.getUuid());
		Assert.assertEquals(existingMBCategory.getCategoryId(),
			newMBCategory.getCategoryId());
		Assert.assertEquals(existingMBCategory.getGroupId(),
			newMBCategory.getGroupId());
		Assert.assertEquals(existingMBCategory.getCompanyId(),
			newMBCategory.getCompanyId());
		Assert.assertEquals(existingMBCategory.getUserId(),
			newMBCategory.getUserId());
		Assert.assertEquals(existingMBCategory.getUserName(),
			newMBCategory.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingMBCategory.getCreateDate()),
			Time.getShortTimestamp(newMBCategory.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingMBCategory.getModifiedDate()),
			Time.getShortTimestamp(newMBCategory.getModifiedDate()));
		Assert.assertEquals(existingMBCategory.getParentCategoryId(),
			newMBCategory.getParentCategoryId());
		Assert.assertEquals(existingMBCategory.getName(),
			newMBCategory.getName());
		Assert.assertEquals(existingMBCategory.getDescription(),
			newMBCategory.getDescription());
		Assert.assertEquals(existingMBCategory.getDisplayStyle(),
			newMBCategory.getDisplayStyle());
		Assert.assertEquals(existingMBCategory.getThreadCount(),
			newMBCategory.getThreadCount());
		Assert.assertEquals(existingMBCategory.getMessageCount(),
			newMBCategory.getMessageCount());
		Assert.assertEquals(Time.getShortTimestamp(
				existingMBCategory.getLastPostDate()),
			Time.getShortTimestamp(newMBCategory.getLastPostDate()));
		Assert.assertEquals(existingMBCategory.getStatus(),
			newMBCategory.getStatus());
		Assert.assertEquals(existingMBCategory.getStatusByUserId(),
			newMBCategory.getStatusByUserId());
		Assert.assertEquals(existingMBCategory.getStatusByUserName(),
			newMBCategory.getStatusByUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingMBCategory.getStatusDate()),
			Time.getShortTimestamp(newMBCategory.getStatusDate()));
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
	public void testCountByG_P() throws Exception {
		_persistence.countByG_P(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByG_P(0L, 0L);
	}

	@Test
	public void testCountByG_PArrayable() throws Exception {
		_persistence.countByG_P(RandomTestUtil.nextLong(),
			new long[] { RandomTestUtil.nextLong(), 0L });
	}

	@Test
	public void testCountByG_S() throws Exception {
		_persistence.countByG_S(RandomTestUtil.nextLong(),
			RandomTestUtil.nextInt());

		_persistence.countByG_S(0L, 0);
	}

	@Test
	public void testCountByC_S() throws Exception {
		_persistence.countByC_S(RandomTestUtil.nextLong(),
			RandomTestUtil.nextInt());

		_persistence.countByC_S(0L, 0);
	}

	@Test
	public void testCountByNotC_G_P() throws Exception {
		_persistence.countByNotC_G_P(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByNotC_G_P(0L, 0L, 0L);
	}

	@Test
	public void testCountByNotC_G_PArrayable() throws Exception {
		_persistence.countByNotC_G_P(new long[] { RandomTestUtil.nextLong(), 0L },
			RandomTestUtil.nextLong(),
			new long[] { RandomTestUtil.nextLong(), 0L });
	}

	@Test
	public void testCountByG_P_S() throws Exception {
		_persistence.countByG_P_S(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), RandomTestUtil.nextInt());

		_persistence.countByG_P_S(0L, 0L, 0);
	}

	@Test
	public void testCountByG_P_SArrayable() throws Exception {
		_persistence.countByG_P_S(RandomTestUtil.nextLong(),
			new long[] { RandomTestUtil.nextLong(), 0L },
			RandomTestUtil.nextInt());
	}

	@Test
	public void testCountByNotC_G_P_S() throws Exception {
		_persistence.countByNotC_G_P_S(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			RandomTestUtil.nextInt());

		_persistence.countByNotC_G_P_S(0L, 0L, 0L, 0);
	}

	@Test
	public void testCountByNotC_G_P_SArrayable() throws Exception {
		_persistence.countByNotC_G_P_S(new long[] { RandomTestUtil.nextLong(), 0L },
			RandomTestUtil.nextLong(),
			new long[] { RandomTestUtil.nextLong(), 0L },
			RandomTestUtil.nextInt());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		MBCategory newMBCategory = addMBCategory();

		MBCategory existingMBCategory = _persistence.findByPrimaryKey(newMBCategory.getPrimaryKey());

		Assert.assertEquals(existingMBCategory, newMBCategory);
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

	protected OrderByComparator<MBCategory> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("MBCategory", "uuid", true,
			"categoryId", true, "groupId", true, "companyId", true, "userId",
			true, "userName", true, "createDate", true, "modifiedDate", true,
			"parentCategoryId", true, "name", true, "description", true,
			"displayStyle", true, "threadCount", true, "messageCount", true,
			"lastPostDate", true, "status", true, "statusByUserId", true,
			"statusByUserName", true, "statusDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		MBCategory newMBCategory = addMBCategory();

		MBCategory existingMBCategory = _persistence.fetchByPrimaryKey(newMBCategory.getPrimaryKey());

		Assert.assertEquals(existingMBCategory, newMBCategory);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MBCategory missingMBCategory = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingMBCategory);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		MBCategory newMBCategory1 = addMBCategory();
		MBCategory newMBCategory2 = addMBCategory();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newMBCategory1.getPrimaryKey());
		primaryKeys.add(newMBCategory2.getPrimaryKey());

		Map<Serializable, MBCategory> mbCategories = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, mbCategories.size());
		Assert.assertEquals(newMBCategory1,
			mbCategories.get(newMBCategory1.getPrimaryKey()));
		Assert.assertEquals(newMBCategory2,
			mbCategories.get(newMBCategory2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, MBCategory> mbCategories = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(mbCategories.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		MBCategory newMBCategory = addMBCategory();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newMBCategory.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, MBCategory> mbCategories = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, mbCategories.size());
		Assert.assertEquals(newMBCategory,
			mbCategories.get(newMBCategory.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, MBCategory> mbCategories = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(mbCategories.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		MBCategory newMBCategory = addMBCategory();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newMBCategory.getPrimaryKey());

		Map<Serializable, MBCategory> mbCategories = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, mbCategories.size());
		Assert.assertEquals(newMBCategory,
			mbCategories.get(newMBCategory.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = MBCategoryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					MBCategory mbCategory = (MBCategory)object;

					Assert.assertNotNull(mbCategory);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		MBCategory newMBCategory = addMBCategory();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBCategory.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("categoryId",
				newMBCategory.getCategoryId()));

		List<MBCategory> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		MBCategory existingMBCategory = result.get(0);

		Assert.assertEquals(existingMBCategory, newMBCategory);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBCategory.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("categoryId",
				RandomTestUtil.nextLong()));

		List<MBCategory> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		MBCategory newMBCategory = addMBCategory();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBCategory.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("categoryId"));

		Object newCategoryId = newMBCategory.getCategoryId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("categoryId",
				new Object[] { newCategoryId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCategoryId = result.get(0);

		Assert.assertEquals(existingCategoryId, newCategoryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBCategory.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("categoryId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("categoryId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		MBCategory newMBCategory = addMBCategory();

		_persistence.clearCache();

		MBCategory existingMBCategory = _persistence.findByPrimaryKey(newMBCategory.getPrimaryKey());

		Assert.assertTrue(Validator.equals(existingMBCategory.getUuid(),
				ReflectionTestUtil.invoke(existingMBCategory,
					"getOriginalUuid", new Class<?>[0])));
		Assert.assertEquals(existingMBCategory.getGroupId(),
			ReflectionTestUtil.invoke(existingMBCategory, "getOriginalGroupId",
				new Class<?>[0]));
	}

	protected MBCategory addMBCategory() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MBCategory mbCategory = _persistence.create(pk);

		mbCategory.setUuid(RandomTestUtil.randomString());

		mbCategory.setGroupId(RandomTestUtil.nextLong());

		mbCategory.setCompanyId(RandomTestUtil.nextLong());

		mbCategory.setUserId(RandomTestUtil.nextLong());

		mbCategory.setUserName(RandomTestUtil.randomString());

		mbCategory.setCreateDate(RandomTestUtil.nextDate());

		mbCategory.setModifiedDate(RandomTestUtil.nextDate());

		mbCategory.setParentCategoryId(RandomTestUtil.nextLong());

		mbCategory.setName(RandomTestUtil.randomString());

		mbCategory.setDescription(RandomTestUtil.randomString());

		mbCategory.setDisplayStyle(RandomTestUtil.randomString());

		mbCategory.setThreadCount(RandomTestUtil.nextInt());

		mbCategory.setMessageCount(RandomTestUtil.nextInt());

		mbCategory.setLastPostDate(RandomTestUtil.nextDate());

		mbCategory.setStatus(RandomTestUtil.nextInt());

		mbCategory.setStatusByUserId(RandomTestUtil.nextLong());

		mbCategory.setStatusByUserName(RandomTestUtil.randomString());

		mbCategory.setStatusDate(RandomTestUtil.nextDate());

		_mbCategories.add(_persistence.update(mbCategory));

		return mbCategory;
	}

	private List<MBCategory> _mbCategories = new ArrayList<MBCategory>();
	private MBCategoryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}