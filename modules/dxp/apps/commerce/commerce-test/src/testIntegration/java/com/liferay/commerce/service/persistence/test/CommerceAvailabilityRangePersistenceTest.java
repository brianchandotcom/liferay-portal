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

import com.liferay.commerce.exception.NoSuchAvailabilityRangeException;
import com.liferay.commerce.model.CommerceAvailabilityRange;
import com.liferay.commerce.service.CommerceAvailabilityRangeLocalServiceUtil;
import com.liferay.commerce.service.persistence.CommerceAvailabilityRangePersistence;
import com.liferay.commerce.service.persistence.CommerceAvailabilityRangeUtil;

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
public class CommerceAvailabilityRangePersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.commerce.service"));

	@Before
	public void setUp() {
		_persistence = CommerceAvailabilityRangeUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CommerceAvailabilityRange> iterator = _commerceAvailabilityRanges.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceAvailabilityRange commerceAvailabilityRange = _persistence.create(pk);

		Assert.assertNotNull(commerceAvailabilityRange);

		Assert.assertEquals(commerceAvailabilityRange.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CommerceAvailabilityRange newCommerceAvailabilityRange = addCommerceAvailabilityRange();

		_persistence.remove(newCommerceAvailabilityRange);

		CommerceAvailabilityRange existingCommerceAvailabilityRange = _persistence.fetchByPrimaryKey(newCommerceAvailabilityRange.getPrimaryKey());

		Assert.assertNull(existingCommerceAvailabilityRange);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCommerceAvailabilityRange();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceAvailabilityRange newCommerceAvailabilityRange = _persistence.create(pk);

		newCommerceAvailabilityRange.setUuid(RandomTestUtil.randomString());

		newCommerceAvailabilityRange.setGroupId(RandomTestUtil.nextLong());

		newCommerceAvailabilityRange.setCompanyId(RandomTestUtil.nextLong());

		newCommerceAvailabilityRange.setUserId(RandomTestUtil.nextLong());

		newCommerceAvailabilityRange.setUserName(RandomTestUtil.randomString());

		newCommerceAvailabilityRange.setCreateDate(RandomTestUtil.nextDate());

		newCommerceAvailabilityRange.setModifiedDate(RandomTestUtil.nextDate());

		newCommerceAvailabilityRange.setTitle(RandomTestUtil.randomString());

		newCommerceAvailabilityRange.setPriority(RandomTestUtil.nextDouble());

		newCommerceAvailabilityRange.setLastPublishDate(RandomTestUtil.nextDate());

		_commerceAvailabilityRanges.add(_persistence.update(
				newCommerceAvailabilityRange));

		CommerceAvailabilityRange existingCommerceAvailabilityRange = _persistence.findByPrimaryKey(newCommerceAvailabilityRange.getPrimaryKey());

		Assert.assertEquals(existingCommerceAvailabilityRange.getUuid(),
			newCommerceAvailabilityRange.getUuid());
		Assert.assertEquals(existingCommerceAvailabilityRange.getCommerceAvailabilityRangeId(),
			newCommerceAvailabilityRange.getCommerceAvailabilityRangeId());
		Assert.assertEquals(existingCommerceAvailabilityRange.getGroupId(),
			newCommerceAvailabilityRange.getGroupId());
		Assert.assertEquals(existingCommerceAvailabilityRange.getCompanyId(),
			newCommerceAvailabilityRange.getCompanyId());
		Assert.assertEquals(existingCommerceAvailabilityRange.getUserId(),
			newCommerceAvailabilityRange.getUserId());
		Assert.assertEquals(existingCommerceAvailabilityRange.getUserName(),
			newCommerceAvailabilityRange.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceAvailabilityRange.getCreateDate()),
			Time.getShortTimestamp(newCommerceAvailabilityRange.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceAvailabilityRange.getModifiedDate()),
			Time.getShortTimestamp(
				newCommerceAvailabilityRange.getModifiedDate()));
		Assert.assertEquals(existingCommerceAvailabilityRange.getTitle(),
			newCommerceAvailabilityRange.getTitle());
		AssertUtils.assertEquals(existingCommerceAvailabilityRange.getPriority(),
			newCommerceAvailabilityRange.getPriority());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceAvailabilityRange.getLastPublishDate()),
			Time.getShortTimestamp(
				newCommerceAvailabilityRange.getLastPublishDate()));
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
	public void testFindByPrimaryKeyExisting() throws Exception {
		CommerceAvailabilityRange newCommerceAvailabilityRange = addCommerceAvailabilityRange();

		CommerceAvailabilityRange existingCommerceAvailabilityRange = _persistence.findByPrimaryKey(newCommerceAvailabilityRange.getPrimaryKey());

		Assert.assertEquals(existingCommerceAvailabilityRange,
			newCommerceAvailabilityRange);
	}

	@Test(expected = NoSuchAvailabilityRangeException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<CommerceAvailabilityRange> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CommerceAvailabilityRange",
			"uuid", true, "commerceAvailabilityRangeId", true, "groupId", true,
			"companyId", true, "userId", true, "userName", true, "createDate",
			true, "modifiedDate", true, "title", true, "priority", true,
			"lastPublishDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CommerceAvailabilityRange newCommerceAvailabilityRange = addCommerceAvailabilityRange();

		CommerceAvailabilityRange existingCommerceAvailabilityRange = _persistence.fetchByPrimaryKey(newCommerceAvailabilityRange.getPrimaryKey());

		Assert.assertEquals(existingCommerceAvailabilityRange,
			newCommerceAvailabilityRange);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceAvailabilityRange missingCommerceAvailabilityRange = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCommerceAvailabilityRange);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CommerceAvailabilityRange newCommerceAvailabilityRange1 = addCommerceAvailabilityRange();
		CommerceAvailabilityRange newCommerceAvailabilityRange2 = addCommerceAvailabilityRange();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceAvailabilityRange1.getPrimaryKey());
		primaryKeys.add(newCommerceAvailabilityRange2.getPrimaryKey());

		Map<Serializable, CommerceAvailabilityRange> commerceAvailabilityRanges = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, commerceAvailabilityRanges.size());
		Assert.assertEquals(newCommerceAvailabilityRange1,
			commerceAvailabilityRanges.get(
				newCommerceAvailabilityRange1.getPrimaryKey()));
		Assert.assertEquals(newCommerceAvailabilityRange2,
			commerceAvailabilityRanges.get(
				newCommerceAvailabilityRange2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CommerceAvailabilityRange> commerceAvailabilityRanges = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceAvailabilityRanges.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CommerceAvailabilityRange newCommerceAvailabilityRange = addCommerceAvailabilityRange();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceAvailabilityRange.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CommerceAvailabilityRange> commerceAvailabilityRanges = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceAvailabilityRanges.size());
		Assert.assertEquals(newCommerceAvailabilityRange,
			commerceAvailabilityRanges.get(
				newCommerceAvailabilityRange.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CommerceAvailabilityRange> commerceAvailabilityRanges = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceAvailabilityRanges.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CommerceAvailabilityRange newCommerceAvailabilityRange = addCommerceAvailabilityRange();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceAvailabilityRange.getPrimaryKey());

		Map<Serializable, CommerceAvailabilityRange> commerceAvailabilityRanges = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceAvailabilityRanges.size());
		Assert.assertEquals(newCommerceAvailabilityRange,
			commerceAvailabilityRanges.get(
				newCommerceAvailabilityRange.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CommerceAvailabilityRangeLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CommerceAvailabilityRange>() {
				@Override
				public void performAction(
					CommerceAvailabilityRange commerceAvailabilityRange) {
					Assert.assertNotNull(commerceAvailabilityRange);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CommerceAvailabilityRange newCommerceAvailabilityRange = addCommerceAvailabilityRange();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceAvailabilityRange.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"commerceAvailabilityRangeId",
				newCommerceAvailabilityRange.getCommerceAvailabilityRangeId()));

		List<CommerceAvailabilityRange> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CommerceAvailabilityRange existingCommerceAvailabilityRange = result.get(0);

		Assert.assertEquals(existingCommerceAvailabilityRange,
			newCommerceAvailabilityRange);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceAvailabilityRange.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"commerceAvailabilityRangeId", RandomTestUtil.nextLong()));

		List<CommerceAvailabilityRange> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CommerceAvailabilityRange newCommerceAvailabilityRange = addCommerceAvailabilityRange();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceAvailabilityRange.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceAvailabilityRangeId"));

		Object newCommerceAvailabilityRangeId = newCommerceAvailabilityRange.getCommerceAvailabilityRangeId();

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"commerceAvailabilityRangeId",
				new Object[] { newCommerceAvailabilityRangeId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCommerceAvailabilityRangeId = result.get(0);

		Assert.assertEquals(existingCommerceAvailabilityRangeId,
			newCommerceAvailabilityRangeId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceAvailabilityRange.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceAvailabilityRangeId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"commerceAvailabilityRangeId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		CommerceAvailabilityRange newCommerceAvailabilityRange = addCommerceAvailabilityRange();

		_persistence.clearCache();

		CommerceAvailabilityRange existingCommerceAvailabilityRange = _persistence.findByPrimaryKey(newCommerceAvailabilityRange.getPrimaryKey());

		Assert.assertTrue(Objects.equals(
				existingCommerceAvailabilityRange.getUuid(),
				ReflectionTestUtil.invoke(existingCommerceAvailabilityRange,
					"getOriginalUuid", new Class<?>[0])));
		Assert.assertEquals(Long.valueOf(
				existingCommerceAvailabilityRange.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingCommerceAvailabilityRange,
				"getOriginalGroupId", new Class<?>[0]));
	}

	protected CommerceAvailabilityRange addCommerceAvailabilityRange()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceAvailabilityRange commerceAvailabilityRange = _persistence.create(pk);

		commerceAvailabilityRange.setUuid(RandomTestUtil.randomString());

		commerceAvailabilityRange.setGroupId(RandomTestUtil.nextLong());

		commerceAvailabilityRange.setCompanyId(RandomTestUtil.nextLong());

		commerceAvailabilityRange.setUserId(RandomTestUtil.nextLong());

		commerceAvailabilityRange.setUserName(RandomTestUtil.randomString());

		commerceAvailabilityRange.setCreateDate(RandomTestUtil.nextDate());

		commerceAvailabilityRange.setModifiedDate(RandomTestUtil.nextDate());

		commerceAvailabilityRange.setTitle(RandomTestUtil.randomString());

		commerceAvailabilityRange.setPriority(RandomTestUtil.nextDouble());

		commerceAvailabilityRange.setLastPublishDate(RandomTestUtil.nextDate());

		_commerceAvailabilityRanges.add(_persistence.update(
				commerceAvailabilityRange));

		return commerceAvailabilityRange;
	}

	private List<CommerceAvailabilityRange> _commerceAvailabilityRanges = new ArrayList<CommerceAvailabilityRange>();
	private CommerceAvailabilityRangePersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}