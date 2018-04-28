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

package com.liferay.commerce.discount.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.commerce.discount.exception.NoSuchDiscountUserSegmentRelException;
import com.liferay.commerce.discount.model.CommerceDiscountUserSegmentRel;
import com.liferay.commerce.discount.service.CommerceDiscountUserSegmentRelLocalServiceUtil;
import com.liferay.commerce.discount.service.persistence.CommerceDiscountUserSegmentRelPersistence;
import com.liferay.commerce.discount.service.persistence.CommerceDiscountUserSegmentRelUtil;

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
public class CommerceDiscountUserSegmentRelPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.commerce.discount.service"));

	@Before
	public void setUp() {
		_persistence = CommerceDiscountUserSegmentRelUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CommerceDiscountUserSegmentRel> iterator = _commerceDiscountUserSegmentRels.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceDiscountUserSegmentRel commerceDiscountUserSegmentRel = _persistence.create(pk);

		Assert.assertNotNull(commerceDiscountUserSegmentRel);

		Assert.assertEquals(commerceDiscountUserSegmentRel.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CommerceDiscountUserSegmentRel newCommerceDiscountUserSegmentRel = addCommerceDiscountUserSegmentRel();

		_persistence.remove(newCommerceDiscountUserSegmentRel);

		CommerceDiscountUserSegmentRel existingCommerceDiscountUserSegmentRel = _persistence.fetchByPrimaryKey(newCommerceDiscountUserSegmentRel.getPrimaryKey());

		Assert.assertNull(existingCommerceDiscountUserSegmentRel);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCommerceDiscountUserSegmentRel();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceDiscountUserSegmentRel newCommerceDiscountUserSegmentRel = _persistence.create(pk);

		newCommerceDiscountUserSegmentRel.setGroupId(RandomTestUtil.nextLong());

		newCommerceDiscountUserSegmentRel.setCompanyId(RandomTestUtil.nextLong());

		newCommerceDiscountUserSegmentRel.setUserId(RandomTestUtil.nextLong());

		newCommerceDiscountUserSegmentRel.setUserName(RandomTestUtil.randomString());

		newCommerceDiscountUserSegmentRel.setCreateDate(RandomTestUtil.nextDate());

		newCommerceDiscountUserSegmentRel.setModifiedDate(RandomTestUtil.nextDate());

		newCommerceDiscountUserSegmentRel.setCommerceDiscountId(RandomTestUtil.nextLong());

		newCommerceDiscountUserSegmentRel.setCommerceUserSegmentEntryId(RandomTestUtil.nextLong());

		_commerceDiscountUserSegmentRels.add(_persistence.update(
				newCommerceDiscountUserSegmentRel));

		CommerceDiscountUserSegmentRel existingCommerceDiscountUserSegmentRel = _persistence.findByPrimaryKey(newCommerceDiscountUserSegmentRel.getPrimaryKey());

		Assert.assertEquals(existingCommerceDiscountUserSegmentRel.getCommerceDiscountUserSegmentRelId(),
			newCommerceDiscountUserSegmentRel.getCommerceDiscountUserSegmentRelId());
		Assert.assertEquals(existingCommerceDiscountUserSegmentRel.getGroupId(),
			newCommerceDiscountUserSegmentRel.getGroupId());
		Assert.assertEquals(existingCommerceDiscountUserSegmentRel.getCompanyId(),
			newCommerceDiscountUserSegmentRel.getCompanyId());
		Assert.assertEquals(existingCommerceDiscountUserSegmentRel.getUserId(),
			newCommerceDiscountUserSegmentRel.getUserId());
		Assert.assertEquals(existingCommerceDiscountUserSegmentRel.getUserName(),
			newCommerceDiscountUserSegmentRel.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceDiscountUserSegmentRel.getCreateDate()),
			Time.getShortTimestamp(
				newCommerceDiscountUserSegmentRel.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceDiscountUserSegmentRel.getModifiedDate()),
			Time.getShortTimestamp(
				newCommerceDiscountUserSegmentRel.getModifiedDate()));
		Assert.assertEquals(existingCommerceDiscountUserSegmentRel.getCommerceDiscountId(),
			newCommerceDiscountUserSegmentRel.getCommerceDiscountId());
		Assert.assertEquals(existingCommerceDiscountUserSegmentRel.getCommerceUserSegmentEntryId(),
			newCommerceDiscountUserSegmentRel.getCommerceUserSegmentEntryId());
	}

	@Test
	public void testCountByCommerceDiscountId() throws Exception {
		_persistence.countByCommerceDiscountId(RandomTestUtil.nextLong());

		_persistence.countByCommerceDiscountId(0L);
	}

	@Test
	public void testCountByCommerceUserSegmentEntryId()
		throws Exception {
		_persistence.countByCommerceUserSegmentEntryId(RandomTestUtil.nextLong());

		_persistence.countByCommerceUserSegmentEntryId(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CommerceDiscountUserSegmentRel newCommerceDiscountUserSegmentRel = addCommerceDiscountUserSegmentRel();

		CommerceDiscountUserSegmentRel existingCommerceDiscountUserSegmentRel = _persistence.findByPrimaryKey(newCommerceDiscountUserSegmentRel.getPrimaryKey());

		Assert.assertEquals(existingCommerceDiscountUserSegmentRel,
			newCommerceDiscountUserSegmentRel);
	}

	@Test(expected = NoSuchDiscountUserSegmentRelException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<CommerceDiscountUserSegmentRel> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CommerceDiscountUserSegmentRel",
			"commerceDiscountUserSegmentRelId", true, "groupId", true,
			"companyId", true, "userId", true, "userName", true, "createDate",
			true, "modifiedDate", true, "commerceDiscountId", true,
			"commerceUserSegmentEntryId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CommerceDiscountUserSegmentRel newCommerceDiscountUserSegmentRel = addCommerceDiscountUserSegmentRel();

		CommerceDiscountUserSegmentRel existingCommerceDiscountUserSegmentRel = _persistence.fetchByPrimaryKey(newCommerceDiscountUserSegmentRel.getPrimaryKey());

		Assert.assertEquals(existingCommerceDiscountUserSegmentRel,
			newCommerceDiscountUserSegmentRel);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceDiscountUserSegmentRel missingCommerceDiscountUserSegmentRel = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCommerceDiscountUserSegmentRel);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CommerceDiscountUserSegmentRel newCommerceDiscountUserSegmentRel1 = addCommerceDiscountUserSegmentRel();
		CommerceDiscountUserSegmentRel newCommerceDiscountUserSegmentRel2 = addCommerceDiscountUserSegmentRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceDiscountUserSegmentRel1.getPrimaryKey());
		primaryKeys.add(newCommerceDiscountUserSegmentRel2.getPrimaryKey());

		Map<Serializable, CommerceDiscountUserSegmentRel> commerceDiscountUserSegmentRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, commerceDiscountUserSegmentRels.size());
		Assert.assertEquals(newCommerceDiscountUserSegmentRel1,
			commerceDiscountUserSegmentRels.get(
				newCommerceDiscountUserSegmentRel1.getPrimaryKey()));
		Assert.assertEquals(newCommerceDiscountUserSegmentRel2,
			commerceDiscountUserSegmentRels.get(
				newCommerceDiscountUserSegmentRel2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CommerceDiscountUserSegmentRel> commerceDiscountUserSegmentRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceDiscountUserSegmentRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CommerceDiscountUserSegmentRel newCommerceDiscountUserSegmentRel = addCommerceDiscountUserSegmentRel();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceDiscountUserSegmentRel.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CommerceDiscountUserSegmentRel> commerceDiscountUserSegmentRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceDiscountUserSegmentRels.size());
		Assert.assertEquals(newCommerceDiscountUserSegmentRel,
			commerceDiscountUserSegmentRels.get(
				newCommerceDiscountUserSegmentRel.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CommerceDiscountUserSegmentRel> commerceDiscountUserSegmentRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceDiscountUserSegmentRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CommerceDiscountUserSegmentRel newCommerceDiscountUserSegmentRel = addCommerceDiscountUserSegmentRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceDiscountUserSegmentRel.getPrimaryKey());

		Map<Serializable, CommerceDiscountUserSegmentRel> commerceDiscountUserSegmentRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceDiscountUserSegmentRels.size());
		Assert.assertEquals(newCommerceDiscountUserSegmentRel,
			commerceDiscountUserSegmentRels.get(
				newCommerceDiscountUserSegmentRel.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CommerceDiscountUserSegmentRelLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CommerceDiscountUserSegmentRel>() {
				@Override
				public void performAction(
					CommerceDiscountUserSegmentRel commerceDiscountUserSegmentRel) {
					Assert.assertNotNull(commerceDiscountUserSegmentRel);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CommerceDiscountUserSegmentRel newCommerceDiscountUserSegmentRel = addCommerceDiscountUserSegmentRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceDiscountUserSegmentRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"commerceDiscountUserSegmentRelId",
				newCommerceDiscountUserSegmentRel.getCommerceDiscountUserSegmentRelId()));

		List<CommerceDiscountUserSegmentRel> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CommerceDiscountUserSegmentRel existingCommerceDiscountUserSegmentRel = result.get(0);

		Assert.assertEquals(existingCommerceDiscountUserSegmentRel,
			newCommerceDiscountUserSegmentRel);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceDiscountUserSegmentRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"commerceDiscountUserSegmentRelId", RandomTestUtil.nextLong()));

		List<CommerceDiscountUserSegmentRel> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CommerceDiscountUserSegmentRel newCommerceDiscountUserSegmentRel = addCommerceDiscountUserSegmentRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceDiscountUserSegmentRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceDiscountUserSegmentRelId"));

		Object newCommerceDiscountUserSegmentRelId = newCommerceDiscountUserSegmentRel.getCommerceDiscountUserSegmentRelId();

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"commerceDiscountUserSegmentRelId",
				new Object[] { newCommerceDiscountUserSegmentRelId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCommerceDiscountUserSegmentRelId = result.get(0);

		Assert.assertEquals(existingCommerceDiscountUserSegmentRelId,
			newCommerceDiscountUserSegmentRelId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceDiscountUserSegmentRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceDiscountUserSegmentRelId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"commerceDiscountUserSegmentRelId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected CommerceDiscountUserSegmentRel addCommerceDiscountUserSegmentRel()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceDiscountUserSegmentRel commerceDiscountUserSegmentRel = _persistence.create(pk);

		commerceDiscountUserSegmentRel.setGroupId(RandomTestUtil.nextLong());

		commerceDiscountUserSegmentRel.setCompanyId(RandomTestUtil.nextLong());

		commerceDiscountUserSegmentRel.setUserId(RandomTestUtil.nextLong());

		commerceDiscountUserSegmentRel.setUserName(RandomTestUtil.randomString());

		commerceDiscountUserSegmentRel.setCreateDate(RandomTestUtil.nextDate());

		commerceDiscountUserSegmentRel.setModifiedDate(RandomTestUtil.nextDate());

		commerceDiscountUserSegmentRel.setCommerceDiscountId(RandomTestUtil.nextLong());

		commerceDiscountUserSegmentRel.setCommerceUserSegmentEntryId(RandomTestUtil.nextLong());

		_commerceDiscountUserSegmentRels.add(_persistence.update(
				commerceDiscountUserSegmentRel));

		return commerceDiscountUserSegmentRel;
	}

	private List<CommerceDiscountUserSegmentRel> _commerceDiscountUserSegmentRels =
		new ArrayList<CommerceDiscountUserSegmentRel>();
	private CommerceDiscountUserSegmentRelPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}