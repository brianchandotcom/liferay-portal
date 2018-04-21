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

import com.liferay.commerce.exception.NoSuchPriceListUserSegmentRelException;
import com.liferay.commerce.model.CommercePriceListUserSegmentRel;
import com.liferay.commerce.service.CommercePriceListUserSegmentRelLocalServiceUtil;
import com.liferay.commerce.service.persistence.CommercePriceListUserSegmentRelPersistence;
import com.liferay.commerce.service.persistence.CommercePriceListUserSegmentRelUtil;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
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
public class CommercePriceListUserSegmentRelPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.commerce.service"));

	@Before
	public void setUp() {
		_persistence = CommercePriceListUserSegmentRelUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CommercePriceListUserSegmentRel> iterator = _commercePriceListUserSegmentRels.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommercePriceListUserSegmentRel commercePriceListUserSegmentRel = _persistence.create(pk);

		Assert.assertNotNull(commercePriceListUserSegmentRel);

		Assert.assertEquals(commercePriceListUserSegmentRel.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CommercePriceListUserSegmentRel newCommercePriceListUserSegmentRel = addCommercePriceListUserSegmentRel();

		_persistence.remove(newCommercePriceListUserSegmentRel);

		CommercePriceListUserSegmentRel existingCommercePriceListUserSegmentRel = _persistence.fetchByPrimaryKey(newCommercePriceListUserSegmentRel.getPrimaryKey());

		Assert.assertNull(existingCommercePriceListUserSegmentRel);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCommercePriceListUserSegmentRel();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommercePriceListUserSegmentRel newCommercePriceListUserSegmentRel = _persistence.create(pk);

		newCommercePriceListUserSegmentRel.setUuid(RandomTestUtil.randomString());

		newCommercePriceListUserSegmentRel.setGroupId(RandomTestUtil.nextLong());

		newCommercePriceListUserSegmentRel.setCompanyId(RandomTestUtil.nextLong());

		newCommercePriceListUserSegmentRel.setUserId(RandomTestUtil.nextLong());

		newCommercePriceListUserSegmentRel.setUserName(RandomTestUtil.randomString());

		newCommercePriceListUserSegmentRel.setCreateDate(RandomTestUtil.nextDate());

		newCommercePriceListUserSegmentRel.setModifiedDate(RandomTestUtil.nextDate());

		newCommercePriceListUserSegmentRel.setCommercePriceListId(RandomTestUtil.nextLong());

		newCommercePriceListUserSegmentRel.setCommerceUserSegmentEntryId(RandomTestUtil.nextLong());

		newCommercePriceListUserSegmentRel.setOrder(RandomTestUtil.nextInt());

		newCommercePriceListUserSegmentRel.setLastPublishDate(RandomTestUtil.nextDate());

		_commercePriceListUserSegmentRels.add(_persistence.update(
				newCommercePriceListUserSegmentRel));

		CommercePriceListUserSegmentRel existingCommercePriceListUserSegmentRel = _persistence.findByPrimaryKey(newCommercePriceListUserSegmentRel.getPrimaryKey());

		Assert.assertEquals(existingCommercePriceListUserSegmentRel.getUuid(),
			newCommercePriceListUserSegmentRel.getUuid());
		Assert.assertEquals(existingCommercePriceListUserSegmentRel.getCommercePriceListUserSegmentRelId(),
			newCommercePriceListUserSegmentRel.getCommercePriceListUserSegmentRelId());
		Assert.assertEquals(existingCommercePriceListUserSegmentRel.getGroupId(),
			newCommercePriceListUserSegmentRel.getGroupId());
		Assert.assertEquals(existingCommercePriceListUserSegmentRel.getCompanyId(),
			newCommercePriceListUserSegmentRel.getCompanyId());
		Assert.assertEquals(existingCommercePriceListUserSegmentRel.getUserId(),
			newCommercePriceListUserSegmentRel.getUserId());
		Assert.assertEquals(existingCommercePriceListUserSegmentRel.getUserName(),
			newCommercePriceListUserSegmentRel.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommercePriceListUserSegmentRel.getCreateDate()),
			Time.getShortTimestamp(
				newCommercePriceListUserSegmentRel.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommercePriceListUserSegmentRel.getModifiedDate()),
			Time.getShortTimestamp(
				newCommercePriceListUserSegmentRel.getModifiedDate()));
		Assert.assertEquals(existingCommercePriceListUserSegmentRel.getCommercePriceListId(),
			newCommercePriceListUserSegmentRel.getCommercePriceListId());
		Assert.assertEquals(existingCommercePriceListUserSegmentRel.getCommerceUserSegmentEntryId(),
			newCommercePriceListUserSegmentRel.getCommerceUserSegmentEntryId());
		Assert.assertEquals(existingCommercePriceListUserSegmentRel.getOrder(),
			newCommercePriceListUserSegmentRel.getOrder());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommercePriceListUserSegmentRel.getLastPublishDate()),
			Time.getShortTimestamp(
				newCommercePriceListUserSegmentRel.getLastPublishDate()));
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
	public void testCountByCommercePriceListId() throws Exception {
		_persistence.countByCommercePriceListId(RandomTestUtil.nextLong());

		_persistence.countByCommercePriceListId(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CommercePriceListUserSegmentRel newCommercePriceListUserSegmentRel = addCommercePriceListUserSegmentRel();

		CommercePriceListUserSegmentRel existingCommercePriceListUserSegmentRel = _persistence.findByPrimaryKey(newCommercePriceListUserSegmentRel.getPrimaryKey());

		Assert.assertEquals(existingCommercePriceListUserSegmentRel,
			newCommercePriceListUserSegmentRel);
	}

	@Test(expected = NoSuchPriceListUserSegmentRelException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<CommercePriceListUserSegmentRel> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CPLUserSegmentRel", "uuid",
			true, "commercePriceListUserSegmentRelId", true, "groupId", true,
			"companyId", true, "userId", true, "userName", true, "createDate",
			true, "modifiedDate", true, "commercePriceListId", true,
			"commerceUserSegmentEntryId", true, "order", true,
			"lastPublishDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CommercePriceListUserSegmentRel newCommercePriceListUserSegmentRel = addCommercePriceListUserSegmentRel();

		CommercePriceListUserSegmentRel existingCommercePriceListUserSegmentRel = _persistence.fetchByPrimaryKey(newCommercePriceListUserSegmentRel.getPrimaryKey());

		Assert.assertEquals(existingCommercePriceListUserSegmentRel,
			newCommercePriceListUserSegmentRel);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommercePriceListUserSegmentRel missingCommercePriceListUserSegmentRel = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCommercePriceListUserSegmentRel);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CommercePriceListUserSegmentRel newCommercePriceListUserSegmentRel1 = addCommercePriceListUserSegmentRel();
		CommercePriceListUserSegmentRel newCommercePriceListUserSegmentRel2 = addCommercePriceListUserSegmentRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommercePriceListUserSegmentRel1.getPrimaryKey());
		primaryKeys.add(newCommercePriceListUserSegmentRel2.getPrimaryKey());

		Map<Serializable, CommercePriceListUserSegmentRel> commercePriceListUserSegmentRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, commercePriceListUserSegmentRels.size());
		Assert.assertEquals(newCommercePriceListUserSegmentRel1,
			commercePriceListUserSegmentRels.get(
				newCommercePriceListUserSegmentRel1.getPrimaryKey()));
		Assert.assertEquals(newCommercePriceListUserSegmentRel2,
			commercePriceListUserSegmentRels.get(
				newCommercePriceListUserSegmentRel2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CommercePriceListUserSegmentRel> commercePriceListUserSegmentRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commercePriceListUserSegmentRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CommercePriceListUserSegmentRel newCommercePriceListUserSegmentRel = addCommercePriceListUserSegmentRel();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommercePriceListUserSegmentRel.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CommercePriceListUserSegmentRel> commercePriceListUserSegmentRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commercePriceListUserSegmentRels.size());
		Assert.assertEquals(newCommercePriceListUserSegmentRel,
			commercePriceListUserSegmentRels.get(
				newCommercePriceListUserSegmentRel.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CommercePriceListUserSegmentRel> commercePriceListUserSegmentRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commercePriceListUserSegmentRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CommercePriceListUserSegmentRel newCommercePriceListUserSegmentRel = addCommercePriceListUserSegmentRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommercePriceListUserSegmentRel.getPrimaryKey());

		Map<Serializable, CommercePriceListUserSegmentRel> commercePriceListUserSegmentRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commercePriceListUserSegmentRels.size());
		Assert.assertEquals(newCommercePriceListUserSegmentRel,
			commercePriceListUserSegmentRels.get(
				newCommercePriceListUserSegmentRel.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CommercePriceListUserSegmentRelLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CommercePriceListUserSegmentRel>() {
				@Override
				public void performAction(
					CommercePriceListUserSegmentRel commercePriceListUserSegmentRel) {
					Assert.assertNotNull(commercePriceListUserSegmentRel);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CommercePriceListUserSegmentRel newCommercePriceListUserSegmentRel = addCommercePriceListUserSegmentRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommercePriceListUserSegmentRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"commercePriceListUserSegmentRelId",
				newCommercePriceListUserSegmentRel.getCommercePriceListUserSegmentRelId()));

		List<CommercePriceListUserSegmentRel> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CommercePriceListUserSegmentRel existingCommercePriceListUserSegmentRel = result.get(0);

		Assert.assertEquals(existingCommercePriceListUserSegmentRel,
			newCommercePriceListUserSegmentRel);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommercePriceListUserSegmentRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"commercePriceListUserSegmentRelId", RandomTestUtil.nextLong()));

		List<CommercePriceListUserSegmentRel> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CommercePriceListUserSegmentRel newCommercePriceListUserSegmentRel = addCommercePriceListUserSegmentRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommercePriceListUserSegmentRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commercePriceListUserSegmentRelId"));

		Object newCommercePriceListUserSegmentRelId = newCommercePriceListUserSegmentRel.getCommercePriceListUserSegmentRelId();

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"commercePriceListUserSegmentRelId",
				new Object[] { newCommercePriceListUserSegmentRelId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCommercePriceListUserSegmentRelId = result.get(0);

		Assert.assertEquals(existingCommercePriceListUserSegmentRelId,
			newCommercePriceListUserSegmentRelId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommercePriceListUserSegmentRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commercePriceListUserSegmentRelId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"commercePriceListUserSegmentRelId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		CommercePriceListUserSegmentRel newCommercePriceListUserSegmentRel = addCommercePriceListUserSegmentRel();

		_persistence.clearCache();

		CommercePriceListUserSegmentRel existingCommercePriceListUserSegmentRel = _persistence.findByPrimaryKey(newCommercePriceListUserSegmentRel.getPrimaryKey());

		Assert.assertTrue(Objects.equals(
				existingCommercePriceListUserSegmentRel.getUuid(),
				ReflectionTestUtil.invoke(
					existingCommercePriceListUserSegmentRel, "getOriginalUuid",
					new Class<?>[0])));
		Assert.assertEquals(Long.valueOf(
				existingCommercePriceListUserSegmentRel.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingCommercePriceListUserSegmentRel, "getOriginalGroupId",
				new Class<?>[0]));
	}

	protected CommercePriceListUserSegmentRel addCommercePriceListUserSegmentRel()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommercePriceListUserSegmentRel commercePriceListUserSegmentRel = _persistence.create(pk);

		commercePriceListUserSegmentRel.setUuid(RandomTestUtil.randomString());

		commercePriceListUserSegmentRel.setGroupId(RandomTestUtil.nextLong());

		commercePriceListUserSegmentRel.setCompanyId(RandomTestUtil.nextLong());

		commercePriceListUserSegmentRel.setUserId(RandomTestUtil.nextLong());

		commercePriceListUserSegmentRel.setUserName(RandomTestUtil.randomString());

		commercePriceListUserSegmentRel.setCreateDate(RandomTestUtil.nextDate());

		commercePriceListUserSegmentRel.setModifiedDate(RandomTestUtil.nextDate());

		commercePriceListUserSegmentRel.setCommercePriceListId(RandomTestUtil.nextLong());

		commercePriceListUserSegmentRel.setCommerceUserSegmentEntryId(RandomTestUtil.nextLong());

		commercePriceListUserSegmentRel.setOrder(RandomTestUtil.nextInt());

		commercePriceListUserSegmentRel.setLastPublishDate(RandomTestUtil.nextDate());

		_commercePriceListUserSegmentRels.add(_persistence.update(
				commercePriceListUserSegmentRel));

		return commercePriceListUserSegmentRel;
	}

	private List<CommercePriceListUserSegmentRel> _commercePriceListUserSegmentRels =
		new ArrayList<CommercePriceListUserSegmentRel>();
	private CommercePriceListUserSegmentRelPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}