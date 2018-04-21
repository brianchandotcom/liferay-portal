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

import com.liferay.commerce.exception.NoSuchPriceListUserSegmentEntryRelException;
import com.liferay.commerce.model.CommercePriceListUserSegmentEntryRel;
import com.liferay.commerce.service.CommercePriceListUserSegmentEntryRelLocalServiceUtil;
import com.liferay.commerce.service.persistence.CommercePriceListUserSegmentEntryRelPersistence;
import com.liferay.commerce.service.persistence.CommercePriceListUserSegmentEntryRelUtil;

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
public class CommercePriceListUserSegmentEntryRelPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.commerce.service"));

	@Before
	public void setUp() {
		_persistence = CommercePriceListUserSegmentEntryRelUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CommercePriceListUserSegmentEntryRel> iterator = _commercePriceListUserSegmentEntryRels.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommercePriceListUserSegmentEntryRel commercePriceListUserSegmentEntryRel =
			_persistence.create(pk);

		Assert.assertNotNull(commercePriceListUserSegmentEntryRel);

		Assert.assertEquals(commercePriceListUserSegmentEntryRel.getPrimaryKey(),
			pk);
	}

	@Test
	public void testRemove() throws Exception {
		CommercePriceListUserSegmentEntryRel newCommercePriceListUserSegmentEntryRel =
			addCommercePriceListUserSegmentEntryRel();

		_persistence.remove(newCommercePriceListUserSegmentEntryRel);

		CommercePriceListUserSegmentEntryRel existingCommercePriceListUserSegmentEntryRel =
			_persistence.fetchByPrimaryKey(newCommercePriceListUserSegmentEntryRel.getPrimaryKey());

		Assert.assertNull(existingCommercePriceListUserSegmentEntryRel);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCommercePriceListUserSegmentEntryRel();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommercePriceListUserSegmentEntryRel newCommercePriceListUserSegmentEntryRel =
			_persistence.create(pk);

		newCommercePriceListUserSegmentEntryRel.setUuid(RandomTestUtil.randomString());

		newCommercePriceListUserSegmentEntryRel.setGroupId(RandomTestUtil.nextLong());

		newCommercePriceListUserSegmentEntryRel.setCompanyId(RandomTestUtil.nextLong());

		newCommercePriceListUserSegmentEntryRel.setUserId(RandomTestUtil.nextLong());

		newCommercePriceListUserSegmentEntryRel.setUserName(RandomTestUtil.randomString());

		newCommercePriceListUserSegmentEntryRel.setCreateDate(RandomTestUtil.nextDate());

		newCommercePriceListUserSegmentEntryRel.setModifiedDate(RandomTestUtil.nextDate());

		newCommercePriceListUserSegmentEntryRel.setCommercePriceListId(RandomTestUtil.nextLong());

		newCommercePriceListUserSegmentEntryRel.setCommerceUserSegmentEntryId(RandomTestUtil.nextLong());

		newCommercePriceListUserSegmentEntryRel.setOrder(RandomTestUtil.nextInt());

		newCommercePriceListUserSegmentEntryRel.setLastPublishDate(RandomTestUtil.nextDate());

		_commercePriceListUserSegmentEntryRels.add(_persistence.update(
				newCommercePriceListUserSegmentEntryRel));

		CommercePriceListUserSegmentEntryRel existingCommercePriceListUserSegmentEntryRel =
			_persistence.findByPrimaryKey(newCommercePriceListUserSegmentEntryRel.getPrimaryKey());

		Assert.assertEquals(existingCommercePriceListUserSegmentEntryRel.getUuid(),
			newCommercePriceListUserSegmentEntryRel.getUuid());
		Assert.assertEquals(existingCommercePriceListUserSegmentEntryRel.getCommercePriceListUserSegmentEntryRelId(),
			newCommercePriceListUserSegmentEntryRel.getCommercePriceListUserSegmentEntryRelId());
		Assert.assertEquals(existingCommercePriceListUserSegmentEntryRel.getGroupId(),
			newCommercePriceListUserSegmentEntryRel.getGroupId());
		Assert.assertEquals(existingCommercePriceListUserSegmentEntryRel.getCompanyId(),
			newCommercePriceListUserSegmentEntryRel.getCompanyId());
		Assert.assertEquals(existingCommercePriceListUserSegmentEntryRel.getUserId(),
			newCommercePriceListUserSegmentEntryRel.getUserId());
		Assert.assertEquals(existingCommercePriceListUserSegmentEntryRel.getUserName(),
			newCommercePriceListUserSegmentEntryRel.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommercePriceListUserSegmentEntryRel.getCreateDate()),
			Time.getShortTimestamp(
				newCommercePriceListUserSegmentEntryRel.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommercePriceListUserSegmentEntryRel.getModifiedDate()),
			Time.getShortTimestamp(
				newCommercePriceListUserSegmentEntryRel.getModifiedDate()));
		Assert.assertEquals(existingCommercePriceListUserSegmentEntryRel.getCommercePriceListId(),
			newCommercePriceListUserSegmentEntryRel.getCommercePriceListId());
		Assert.assertEquals(existingCommercePriceListUserSegmentEntryRel.getCommerceUserSegmentEntryId(),
			newCommercePriceListUserSegmentEntryRel.getCommerceUserSegmentEntryId());
		Assert.assertEquals(existingCommercePriceListUserSegmentEntryRel.getOrder(),
			newCommercePriceListUserSegmentEntryRel.getOrder());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommercePriceListUserSegmentEntryRel.getLastPublishDate()),
			Time.getShortTimestamp(
				newCommercePriceListUserSegmentEntryRel.getLastPublishDate()));
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
	public void testCountByC_C() throws Exception {
		_persistence.countByC_C(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByC_C(0L, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CommercePriceListUserSegmentEntryRel newCommercePriceListUserSegmentEntryRel =
			addCommercePriceListUserSegmentEntryRel();

		CommercePriceListUserSegmentEntryRel existingCommercePriceListUserSegmentEntryRel =
			_persistence.findByPrimaryKey(newCommercePriceListUserSegmentEntryRel.getPrimaryKey());

		Assert.assertEquals(existingCommercePriceListUserSegmentEntryRel,
			newCommercePriceListUserSegmentEntryRel);
	}

	@Test(expected = NoSuchPriceListUserSegmentEntryRelException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<CommercePriceListUserSegmentEntryRel> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CPLUserSegmentEntryRel",
			"uuid", true, "commercePriceListUserSegmentEntryRelId", true,
			"groupId", true, "companyId", true, "userId", true, "userName",
			true, "createDate", true, "modifiedDate", true,
			"commercePriceListId", true, "commerceUserSegmentEntryId", true,
			"order", true, "lastPublishDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CommercePriceListUserSegmentEntryRel newCommercePriceListUserSegmentEntryRel =
			addCommercePriceListUserSegmentEntryRel();

		CommercePriceListUserSegmentEntryRel existingCommercePriceListUserSegmentEntryRel =
			_persistence.fetchByPrimaryKey(newCommercePriceListUserSegmentEntryRel.getPrimaryKey());

		Assert.assertEquals(existingCommercePriceListUserSegmentEntryRel,
			newCommercePriceListUserSegmentEntryRel);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommercePriceListUserSegmentEntryRel missingCommercePriceListUserSegmentEntryRel =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCommercePriceListUserSegmentEntryRel);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CommercePriceListUserSegmentEntryRel newCommercePriceListUserSegmentEntryRel1 =
			addCommercePriceListUserSegmentEntryRel();
		CommercePriceListUserSegmentEntryRel newCommercePriceListUserSegmentEntryRel2 =
			addCommercePriceListUserSegmentEntryRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommercePriceListUserSegmentEntryRel1.getPrimaryKey());
		primaryKeys.add(newCommercePriceListUserSegmentEntryRel2.getPrimaryKey());

		Map<Serializable, CommercePriceListUserSegmentEntryRel> commercePriceListUserSegmentEntryRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, commercePriceListUserSegmentEntryRels.size());
		Assert.assertEquals(newCommercePriceListUserSegmentEntryRel1,
			commercePriceListUserSegmentEntryRels.get(
				newCommercePriceListUserSegmentEntryRel1.getPrimaryKey()));
		Assert.assertEquals(newCommercePriceListUserSegmentEntryRel2,
			commercePriceListUserSegmentEntryRels.get(
				newCommercePriceListUserSegmentEntryRel2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CommercePriceListUserSegmentEntryRel> commercePriceListUserSegmentEntryRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commercePriceListUserSegmentEntryRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CommercePriceListUserSegmentEntryRel newCommercePriceListUserSegmentEntryRel =
			addCommercePriceListUserSegmentEntryRel();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommercePriceListUserSegmentEntryRel.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CommercePriceListUserSegmentEntryRel> commercePriceListUserSegmentEntryRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commercePriceListUserSegmentEntryRels.size());
		Assert.assertEquals(newCommercePriceListUserSegmentEntryRel,
			commercePriceListUserSegmentEntryRels.get(
				newCommercePriceListUserSegmentEntryRel.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CommercePriceListUserSegmentEntryRel> commercePriceListUserSegmentEntryRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commercePriceListUserSegmentEntryRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CommercePriceListUserSegmentEntryRel newCommercePriceListUserSegmentEntryRel =
			addCommercePriceListUserSegmentEntryRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommercePriceListUserSegmentEntryRel.getPrimaryKey());

		Map<Serializable, CommercePriceListUserSegmentEntryRel> commercePriceListUserSegmentEntryRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commercePriceListUserSegmentEntryRels.size());
		Assert.assertEquals(newCommercePriceListUserSegmentEntryRel,
			commercePriceListUserSegmentEntryRels.get(
				newCommercePriceListUserSegmentEntryRel.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CommercePriceListUserSegmentEntryRelLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CommercePriceListUserSegmentEntryRel>() {
				@Override
				public void performAction(
					CommercePriceListUserSegmentEntryRel commercePriceListUserSegmentEntryRel) {
					Assert.assertNotNull(commercePriceListUserSegmentEntryRel);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CommercePriceListUserSegmentEntryRel newCommercePriceListUserSegmentEntryRel =
			addCommercePriceListUserSegmentEntryRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommercePriceListUserSegmentEntryRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"commercePriceListUserSegmentEntryRelId",
				newCommercePriceListUserSegmentEntryRel.getCommercePriceListUserSegmentEntryRelId()));

		List<CommercePriceListUserSegmentEntryRel> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CommercePriceListUserSegmentEntryRel existingCommercePriceListUserSegmentEntryRel =
			result.get(0);

		Assert.assertEquals(existingCommercePriceListUserSegmentEntryRel,
			newCommercePriceListUserSegmentEntryRel);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommercePriceListUserSegmentEntryRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"commercePriceListUserSegmentEntryRelId",
				RandomTestUtil.nextLong()));

		List<CommercePriceListUserSegmentEntryRel> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CommercePriceListUserSegmentEntryRel newCommercePriceListUserSegmentEntryRel =
			addCommercePriceListUserSegmentEntryRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommercePriceListUserSegmentEntryRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commercePriceListUserSegmentEntryRelId"));

		Object newCommercePriceListUserSegmentEntryRelId = newCommercePriceListUserSegmentEntryRel.getCommercePriceListUserSegmentEntryRelId();

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"commercePriceListUserSegmentEntryRelId",
				new Object[] { newCommercePriceListUserSegmentEntryRelId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCommercePriceListUserSegmentEntryRelId = result.get(0);

		Assert.assertEquals(existingCommercePriceListUserSegmentEntryRelId,
			newCommercePriceListUserSegmentEntryRelId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommercePriceListUserSegmentEntryRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commercePriceListUserSegmentEntryRelId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"commercePriceListUserSegmentEntryRelId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		CommercePriceListUserSegmentEntryRel newCommercePriceListUserSegmentEntryRel =
			addCommercePriceListUserSegmentEntryRel();

		_persistence.clearCache();

		CommercePriceListUserSegmentEntryRel existingCommercePriceListUserSegmentEntryRel =
			_persistence.findByPrimaryKey(newCommercePriceListUserSegmentEntryRel.getPrimaryKey());

		Assert.assertTrue(Objects.equals(
				existingCommercePriceListUserSegmentEntryRel.getUuid(),
				ReflectionTestUtil.invoke(
					existingCommercePriceListUserSegmentEntryRel,
					"getOriginalUuid", new Class<?>[0])));
		Assert.assertEquals(Long.valueOf(
				existingCommercePriceListUserSegmentEntryRel.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingCommercePriceListUserSegmentEntryRel,
				"getOriginalGroupId", new Class<?>[0]));

		Assert.assertEquals(Long.valueOf(
				existingCommercePriceListUserSegmentEntryRel.getCommercePriceListId()),
			ReflectionTestUtil.<Long>invoke(
				existingCommercePriceListUserSegmentEntryRel,
				"getOriginalCommercePriceListId", new Class<?>[0]));
		Assert.assertEquals(Long.valueOf(
				existingCommercePriceListUserSegmentEntryRel.getCommerceUserSegmentEntryId()),
			ReflectionTestUtil.<Long>invoke(
				existingCommercePriceListUserSegmentEntryRel,
				"getOriginalCommerceUserSegmentEntryId", new Class<?>[0]));
	}

	protected CommercePriceListUserSegmentEntryRel addCommercePriceListUserSegmentEntryRel()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommercePriceListUserSegmentEntryRel commercePriceListUserSegmentEntryRel =
			_persistence.create(pk);

		commercePriceListUserSegmentEntryRel.setUuid(RandomTestUtil.randomString());

		commercePriceListUserSegmentEntryRel.setGroupId(RandomTestUtil.nextLong());

		commercePriceListUserSegmentEntryRel.setCompanyId(RandomTestUtil.nextLong());

		commercePriceListUserSegmentEntryRel.setUserId(RandomTestUtil.nextLong());

		commercePriceListUserSegmentEntryRel.setUserName(RandomTestUtil.randomString());

		commercePriceListUserSegmentEntryRel.setCreateDate(RandomTestUtil.nextDate());

		commercePriceListUserSegmentEntryRel.setModifiedDate(RandomTestUtil.nextDate());

		commercePriceListUserSegmentEntryRel.setCommercePriceListId(RandomTestUtil.nextLong());

		commercePriceListUserSegmentEntryRel.setCommerceUserSegmentEntryId(RandomTestUtil.nextLong());

		commercePriceListUserSegmentEntryRel.setOrder(RandomTestUtil.nextInt());

		commercePriceListUserSegmentEntryRel.setLastPublishDate(RandomTestUtil.nextDate());

		_commercePriceListUserSegmentEntryRels.add(_persistence.update(
				commercePriceListUserSegmentEntryRel));

		return commercePriceListUserSegmentEntryRel;
	}

	private List<CommercePriceListUserSegmentEntryRel> _commercePriceListUserSegmentEntryRels =
		new ArrayList<CommercePriceListUserSegmentEntryRel>();
	private CommercePriceListUserSegmentEntryRelPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}