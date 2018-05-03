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

package com.liferay.commerce.user.segment.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.commerce.user.segment.exception.NoSuchUserSegmentCriterionException;
import com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion;
import com.liferay.commerce.user.segment.service.CommerceUserSegmentCriterionLocalServiceUtil;
import com.liferay.commerce.user.segment.service.persistence.CommerceUserSegmentCriterionPersistence;
import com.liferay.commerce.user.segment.service.persistence.CommerceUserSegmentCriterionUtil;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.AssertUtils;
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
public class CommerceUserSegmentCriterionPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.commerce.user.segment.service"));

	@Before
	public void setUp() {
		_persistence = CommerceUserSegmentCriterionUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CommerceUserSegmentCriterion> iterator = _commerceUserSegmentCriterions.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceUserSegmentCriterion commerceUserSegmentCriterion = _persistence.create(pk);

		Assert.assertNotNull(commerceUserSegmentCriterion);

		Assert.assertEquals(commerceUserSegmentCriterion.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CommerceUserSegmentCriterion newCommerceUserSegmentCriterion = addCommerceUserSegmentCriterion();

		_persistence.remove(newCommerceUserSegmentCriterion);

		CommerceUserSegmentCriterion existingCommerceUserSegmentCriterion = _persistence.fetchByPrimaryKey(newCommerceUserSegmentCriterion.getPrimaryKey());

		Assert.assertNull(existingCommerceUserSegmentCriterion);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCommerceUserSegmentCriterion();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceUserSegmentCriterion newCommerceUserSegmentCriterion = _persistence.create(pk);

		newCommerceUserSegmentCriterion.setGroupId(RandomTestUtil.nextLong());

		newCommerceUserSegmentCriterion.setCompanyId(RandomTestUtil.nextLong());

		newCommerceUserSegmentCriterion.setUserId(RandomTestUtil.nextLong());

		newCommerceUserSegmentCriterion.setUserName(RandomTestUtil.randomString());

		newCommerceUserSegmentCriterion.setCreateDate(RandomTestUtil.nextDate());

		newCommerceUserSegmentCriterion.setModifiedDate(RandomTestUtil.nextDate());

		newCommerceUserSegmentCriterion.setCommerceUserSegmentEntryId(RandomTestUtil.nextLong());

		newCommerceUserSegmentCriterion.setType(RandomTestUtil.randomString());

		newCommerceUserSegmentCriterion.setTypeSettings(RandomTestUtil.randomString());

		newCommerceUserSegmentCriterion.setPriority(RandomTestUtil.nextDouble());

		_commerceUserSegmentCriterions.add(_persistence.update(
				newCommerceUserSegmentCriterion));

		CommerceUserSegmentCriterion existingCommerceUserSegmentCriterion = _persistence.findByPrimaryKey(newCommerceUserSegmentCriterion.getPrimaryKey());

		Assert.assertEquals(existingCommerceUserSegmentCriterion.getCommerceUserSegmentCriterionId(),
			newCommerceUserSegmentCriterion.getCommerceUserSegmentCriterionId());
		Assert.assertEquals(existingCommerceUserSegmentCriterion.getGroupId(),
			newCommerceUserSegmentCriterion.getGroupId());
		Assert.assertEquals(existingCommerceUserSegmentCriterion.getCompanyId(),
			newCommerceUserSegmentCriterion.getCompanyId());
		Assert.assertEquals(existingCommerceUserSegmentCriterion.getUserId(),
			newCommerceUserSegmentCriterion.getUserId());
		Assert.assertEquals(existingCommerceUserSegmentCriterion.getUserName(),
			newCommerceUserSegmentCriterion.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceUserSegmentCriterion.getCreateDate()),
			Time.getShortTimestamp(
				newCommerceUserSegmentCriterion.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceUserSegmentCriterion.getModifiedDate()),
			Time.getShortTimestamp(
				newCommerceUserSegmentCriterion.getModifiedDate()));
		Assert.assertEquals(existingCommerceUserSegmentCriterion.getCommerceUserSegmentEntryId(),
			newCommerceUserSegmentCriterion.getCommerceUserSegmentEntryId());
		Assert.assertEquals(existingCommerceUserSegmentCriterion.getType(),
			newCommerceUserSegmentCriterion.getType());
		Assert.assertEquals(existingCommerceUserSegmentCriterion.getTypeSettings(),
			newCommerceUserSegmentCriterion.getTypeSettings());
		AssertUtils.assertEquals(existingCommerceUserSegmentCriterion.getPriority(),
			newCommerceUserSegmentCriterion.getPriority());
	}

	@Test
	public void testCountByCommerceUserSegmentEntryId()
		throws Exception {
		_persistence.countByCommerceUserSegmentEntryId(RandomTestUtil.nextLong());

		_persistence.countByCommerceUserSegmentEntryId(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CommerceUserSegmentCriterion newCommerceUserSegmentCriterion = addCommerceUserSegmentCriterion();

		CommerceUserSegmentCriterion existingCommerceUserSegmentCriterion = _persistence.findByPrimaryKey(newCommerceUserSegmentCriterion.getPrimaryKey());

		Assert.assertEquals(existingCommerceUserSegmentCriterion,
			newCommerceUserSegmentCriterion);
	}

	@Test(expected = NoSuchUserSegmentCriterionException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<CommerceUserSegmentCriterion> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CommerceUserSegmentCriterion",
			"commerceUserSegmentCriterionId", true, "groupId", true,
			"companyId", true, "userId", true, "userName", true, "createDate",
			true, "modifiedDate", true, "commerceUserSegmentEntryId", true,
			"type", true, "priority", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CommerceUserSegmentCriterion newCommerceUserSegmentCriterion = addCommerceUserSegmentCriterion();

		CommerceUserSegmentCriterion existingCommerceUserSegmentCriterion = _persistence.fetchByPrimaryKey(newCommerceUserSegmentCriterion.getPrimaryKey());

		Assert.assertEquals(existingCommerceUserSegmentCriterion,
			newCommerceUserSegmentCriterion);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceUserSegmentCriterion missingCommerceUserSegmentCriterion = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCommerceUserSegmentCriterion);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CommerceUserSegmentCriterion newCommerceUserSegmentCriterion1 = addCommerceUserSegmentCriterion();
		CommerceUserSegmentCriterion newCommerceUserSegmentCriterion2 = addCommerceUserSegmentCriterion();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceUserSegmentCriterion1.getPrimaryKey());
		primaryKeys.add(newCommerceUserSegmentCriterion2.getPrimaryKey());

		Map<Serializable, CommerceUserSegmentCriterion> commerceUserSegmentCriterions =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, commerceUserSegmentCriterions.size());
		Assert.assertEquals(newCommerceUserSegmentCriterion1,
			commerceUserSegmentCriterions.get(
				newCommerceUserSegmentCriterion1.getPrimaryKey()));
		Assert.assertEquals(newCommerceUserSegmentCriterion2,
			commerceUserSegmentCriterions.get(
				newCommerceUserSegmentCriterion2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CommerceUserSegmentCriterion> commerceUserSegmentCriterions =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceUserSegmentCriterions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CommerceUserSegmentCriterion newCommerceUserSegmentCriterion = addCommerceUserSegmentCriterion();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceUserSegmentCriterion.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CommerceUserSegmentCriterion> commerceUserSegmentCriterions =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceUserSegmentCriterions.size());
		Assert.assertEquals(newCommerceUserSegmentCriterion,
			commerceUserSegmentCriterions.get(
				newCommerceUserSegmentCriterion.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CommerceUserSegmentCriterion> commerceUserSegmentCriterions =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceUserSegmentCriterions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CommerceUserSegmentCriterion newCommerceUserSegmentCriterion = addCommerceUserSegmentCriterion();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceUserSegmentCriterion.getPrimaryKey());

		Map<Serializable, CommerceUserSegmentCriterion> commerceUserSegmentCriterions =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceUserSegmentCriterions.size());
		Assert.assertEquals(newCommerceUserSegmentCriterion,
			commerceUserSegmentCriterions.get(
				newCommerceUserSegmentCriterion.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CommerceUserSegmentCriterionLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CommerceUserSegmentCriterion>() {
				@Override
				public void performAction(
					CommerceUserSegmentCriterion commerceUserSegmentCriterion) {
					Assert.assertNotNull(commerceUserSegmentCriterion);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CommerceUserSegmentCriterion newCommerceUserSegmentCriterion = addCommerceUserSegmentCriterion();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceUserSegmentCriterion.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"commerceUserSegmentCriterionId",
				newCommerceUserSegmentCriterion.getCommerceUserSegmentCriterionId()));

		List<CommerceUserSegmentCriterion> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CommerceUserSegmentCriterion existingCommerceUserSegmentCriterion = result.get(0);

		Assert.assertEquals(existingCommerceUserSegmentCriterion,
			newCommerceUserSegmentCriterion);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceUserSegmentCriterion.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"commerceUserSegmentCriterionId", RandomTestUtil.nextLong()));

		List<CommerceUserSegmentCriterion> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CommerceUserSegmentCriterion newCommerceUserSegmentCriterion = addCommerceUserSegmentCriterion();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceUserSegmentCriterion.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceUserSegmentCriterionId"));

		Object newCommerceUserSegmentCriterionId = newCommerceUserSegmentCriterion.getCommerceUserSegmentCriterionId();

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"commerceUserSegmentCriterionId",
				new Object[] { newCommerceUserSegmentCriterionId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCommerceUserSegmentCriterionId = result.get(0);

		Assert.assertEquals(existingCommerceUserSegmentCriterionId,
			newCommerceUserSegmentCriterionId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceUserSegmentCriterion.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceUserSegmentCriterionId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"commerceUserSegmentCriterionId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected CommerceUserSegmentCriterion addCommerceUserSegmentCriterion()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceUserSegmentCriterion commerceUserSegmentCriterion = _persistence.create(pk);

		commerceUserSegmentCriterion.setGroupId(RandomTestUtil.nextLong());

		commerceUserSegmentCriterion.setCompanyId(RandomTestUtil.nextLong());

		commerceUserSegmentCriterion.setUserId(RandomTestUtil.nextLong());

		commerceUserSegmentCriterion.setUserName(RandomTestUtil.randomString());

		commerceUserSegmentCriterion.setCreateDate(RandomTestUtil.nextDate());

		commerceUserSegmentCriterion.setModifiedDate(RandomTestUtil.nextDate());

		commerceUserSegmentCriterion.setCommerceUserSegmentEntryId(RandomTestUtil.nextLong());

		commerceUserSegmentCriterion.setType(RandomTestUtil.randomString());

		commerceUserSegmentCriterion.setTypeSettings(RandomTestUtil.randomString());

		commerceUserSegmentCriterion.setPriority(RandomTestUtil.nextDouble());

		_commerceUserSegmentCriterions.add(_persistence.update(
				commerceUserSegmentCriterion));

		return commerceUserSegmentCriterion;
	}

	private List<CommerceUserSegmentCriterion> _commerceUserSegmentCriterions = new ArrayList<CommerceUserSegmentCriterion>();
	private CommerceUserSegmentCriterionPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}