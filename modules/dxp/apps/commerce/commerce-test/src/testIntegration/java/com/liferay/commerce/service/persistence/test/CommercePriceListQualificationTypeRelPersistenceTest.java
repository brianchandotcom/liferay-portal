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

import com.liferay.commerce.exception.NoSuchPriceListQualificationTypeRelException;
import com.liferay.commerce.model.CommercePriceListQualificationTypeRel;
import com.liferay.commerce.service.CommercePriceListQualificationTypeRelLocalServiceUtil;
import com.liferay.commerce.service.persistence.CommercePriceListQualificationTypeRelPersistence;
import com.liferay.commerce.service.persistence.CommercePriceListQualificationTypeRelUtil;

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
public class CommercePriceListQualificationTypeRelPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.commerce.service"));

	@Before
	public void setUp() {
		_persistence = CommercePriceListQualificationTypeRelUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CommercePriceListQualificationTypeRel> iterator = _commercePriceListQualificationTypeRels.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommercePriceListQualificationTypeRel commercePriceListQualificationTypeRel =
			_persistence.create(pk);

		Assert.assertNotNull(commercePriceListQualificationTypeRel);

		Assert.assertEquals(commercePriceListQualificationTypeRel.getPrimaryKey(),
			pk);
	}

	@Test
	public void testRemove() throws Exception {
		CommercePriceListQualificationTypeRel newCommercePriceListQualificationTypeRel =
			addCommercePriceListQualificationTypeRel();

		_persistence.remove(newCommercePriceListQualificationTypeRel);

		CommercePriceListQualificationTypeRel existingCommercePriceListQualificationTypeRel =
			_persistence.fetchByPrimaryKey(newCommercePriceListQualificationTypeRel.getPrimaryKey());

		Assert.assertNull(existingCommercePriceListQualificationTypeRel);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCommercePriceListQualificationTypeRel();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommercePriceListQualificationTypeRel newCommercePriceListQualificationTypeRel =
			_persistence.create(pk);

		newCommercePriceListQualificationTypeRel.setUuid(RandomTestUtil.randomString());

		newCommercePriceListQualificationTypeRel.setGroupId(RandomTestUtil.nextLong());

		newCommercePriceListQualificationTypeRel.setCompanyId(RandomTestUtil.nextLong());

		newCommercePriceListQualificationTypeRel.setUserId(RandomTestUtil.nextLong());

		newCommercePriceListQualificationTypeRel.setUserName(RandomTestUtil.randomString());

		newCommercePriceListQualificationTypeRel.setCreateDate(RandomTestUtil.nextDate());

		newCommercePriceListQualificationTypeRel.setModifiedDate(RandomTestUtil.nextDate());

		newCommercePriceListQualificationTypeRel.setCommercePriceListId(RandomTestUtil.nextLong());

		newCommercePriceListQualificationTypeRel.setCommercePriceListQualificationType(RandomTestUtil.randomString());

		newCommercePriceListQualificationTypeRel.setOrder(RandomTestUtil.nextInt());

		newCommercePriceListQualificationTypeRel.setLastPublishDate(RandomTestUtil.nextDate());

		_commercePriceListQualificationTypeRels.add(_persistence.update(
				newCommercePriceListQualificationTypeRel));

		CommercePriceListQualificationTypeRel existingCommercePriceListQualificationTypeRel =
			_persistence.findByPrimaryKey(newCommercePriceListQualificationTypeRel.getPrimaryKey());

		Assert.assertEquals(existingCommercePriceListQualificationTypeRel.getUuid(),
			newCommercePriceListQualificationTypeRel.getUuid());
		Assert.assertEquals(existingCommercePriceListQualificationTypeRel.getCommercePriceListQualificationTypeRelId(),
			newCommercePriceListQualificationTypeRel.getCommercePriceListQualificationTypeRelId());
		Assert.assertEquals(existingCommercePriceListQualificationTypeRel.getGroupId(),
			newCommercePriceListQualificationTypeRel.getGroupId());
		Assert.assertEquals(existingCommercePriceListQualificationTypeRel.getCompanyId(),
			newCommercePriceListQualificationTypeRel.getCompanyId());
		Assert.assertEquals(existingCommercePriceListQualificationTypeRel.getUserId(),
			newCommercePriceListQualificationTypeRel.getUserId());
		Assert.assertEquals(existingCommercePriceListQualificationTypeRel.getUserName(),
			newCommercePriceListQualificationTypeRel.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommercePriceListQualificationTypeRel.getCreateDate()),
			Time.getShortTimestamp(
				newCommercePriceListQualificationTypeRel.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommercePriceListQualificationTypeRel.getModifiedDate()),
			Time.getShortTimestamp(
				newCommercePriceListQualificationTypeRel.getModifiedDate()));
		Assert.assertEquals(existingCommercePriceListQualificationTypeRel.getCommercePriceListId(),
			newCommercePriceListQualificationTypeRel.getCommercePriceListId());
		Assert.assertEquals(existingCommercePriceListQualificationTypeRel.getCommercePriceListQualificationType(),
			newCommercePriceListQualificationTypeRel.getCommercePriceListQualificationType());
		Assert.assertEquals(existingCommercePriceListQualificationTypeRel.getOrder(),
			newCommercePriceListQualificationTypeRel.getOrder());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommercePriceListQualificationTypeRel.getLastPublishDate()),
			Time.getShortTimestamp(
				newCommercePriceListQualificationTypeRel.getLastPublishDate()));
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
		_persistence.countByC_C("", RandomTestUtil.nextLong());

		_persistence.countByC_C("null", 0L);

		_persistence.countByC_C((String)null, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CommercePriceListQualificationTypeRel newCommercePriceListQualificationTypeRel =
			addCommercePriceListQualificationTypeRel();

		CommercePriceListQualificationTypeRel existingCommercePriceListQualificationTypeRel =
			_persistence.findByPrimaryKey(newCommercePriceListQualificationTypeRel.getPrimaryKey());

		Assert.assertEquals(existingCommercePriceListQualificationTypeRel,
			newCommercePriceListQualificationTypeRel);
	}

	@Test(expected = NoSuchPriceListQualificationTypeRelException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<CommercePriceListQualificationTypeRel> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CommercePriceListQualificationTypeRel",
			"uuid", true, "commercePriceListQualificationTypeRelId", true,
			"groupId", true, "companyId", true, "userId", true, "userName",
			true, "createDate", true, "modifiedDate", true,
			"commercePriceListId", true, "commercePriceListQualificationType",
			true, "order", true, "lastPublishDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CommercePriceListQualificationTypeRel newCommercePriceListQualificationTypeRel =
			addCommercePriceListQualificationTypeRel();

		CommercePriceListQualificationTypeRel existingCommercePriceListQualificationTypeRel =
			_persistence.fetchByPrimaryKey(newCommercePriceListQualificationTypeRel.getPrimaryKey());

		Assert.assertEquals(existingCommercePriceListQualificationTypeRel,
			newCommercePriceListQualificationTypeRel);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommercePriceListQualificationTypeRel missingCommercePriceListQualificationTypeRel =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCommercePriceListQualificationTypeRel);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CommercePriceListQualificationTypeRel newCommercePriceListQualificationTypeRel1 =
			addCommercePriceListQualificationTypeRel();
		CommercePriceListQualificationTypeRel newCommercePriceListQualificationTypeRel2 =
			addCommercePriceListQualificationTypeRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommercePriceListQualificationTypeRel1.getPrimaryKey());
		primaryKeys.add(newCommercePriceListQualificationTypeRel2.getPrimaryKey());

		Map<Serializable, CommercePriceListQualificationTypeRel> commercePriceListQualificationTypeRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, commercePriceListQualificationTypeRels.size());
		Assert.assertEquals(newCommercePriceListQualificationTypeRel1,
			commercePriceListQualificationTypeRels.get(
				newCommercePriceListQualificationTypeRel1.getPrimaryKey()));
		Assert.assertEquals(newCommercePriceListQualificationTypeRel2,
			commercePriceListQualificationTypeRels.get(
				newCommercePriceListQualificationTypeRel2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CommercePriceListQualificationTypeRel> commercePriceListQualificationTypeRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commercePriceListQualificationTypeRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CommercePriceListQualificationTypeRel newCommercePriceListQualificationTypeRel =
			addCommercePriceListQualificationTypeRel();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommercePriceListQualificationTypeRel.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CommercePriceListQualificationTypeRel> commercePriceListQualificationTypeRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commercePriceListQualificationTypeRels.size());
		Assert.assertEquals(newCommercePriceListQualificationTypeRel,
			commercePriceListQualificationTypeRels.get(
				newCommercePriceListQualificationTypeRel.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CommercePriceListQualificationTypeRel> commercePriceListQualificationTypeRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commercePriceListQualificationTypeRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CommercePriceListQualificationTypeRel newCommercePriceListQualificationTypeRel =
			addCommercePriceListQualificationTypeRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommercePriceListQualificationTypeRel.getPrimaryKey());

		Map<Serializable, CommercePriceListQualificationTypeRel> commercePriceListQualificationTypeRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commercePriceListQualificationTypeRels.size());
		Assert.assertEquals(newCommercePriceListQualificationTypeRel,
			commercePriceListQualificationTypeRels.get(
				newCommercePriceListQualificationTypeRel.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CommercePriceListQualificationTypeRelLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CommercePriceListQualificationTypeRel>() {
				@Override
				public void performAction(
					CommercePriceListQualificationTypeRel commercePriceListQualificationTypeRel) {
					Assert.assertNotNull(commercePriceListQualificationTypeRel);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CommercePriceListQualificationTypeRel newCommercePriceListQualificationTypeRel =
			addCommercePriceListQualificationTypeRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommercePriceListQualificationTypeRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"commercePriceListQualificationTypeRelId",
				newCommercePriceListQualificationTypeRel.getCommercePriceListQualificationTypeRelId()));

		List<CommercePriceListQualificationTypeRel> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CommercePriceListQualificationTypeRel existingCommercePriceListQualificationTypeRel =
			result.get(0);

		Assert.assertEquals(existingCommercePriceListQualificationTypeRel,
			newCommercePriceListQualificationTypeRel);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommercePriceListQualificationTypeRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"commercePriceListQualificationTypeRelId",
				RandomTestUtil.nextLong()));

		List<CommercePriceListQualificationTypeRel> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CommercePriceListQualificationTypeRel newCommercePriceListQualificationTypeRel =
			addCommercePriceListQualificationTypeRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommercePriceListQualificationTypeRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commercePriceListQualificationTypeRelId"));

		Object newCommercePriceListQualificationTypeRelId = newCommercePriceListQualificationTypeRel.getCommercePriceListQualificationTypeRelId();

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"commercePriceListQualificationTypeRelId",
				new Object[] { newCommercePriceListQualificationTypeRelId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCommercePriceListQualificationTypeRelId = result.get(0);

		Assert.assertEquals(existingCommercePriceListQualificationTypeRelId,
			newCommercePriceListQualificationTypeRelId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommercePriceListQualificationTypeRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commercePriceListQualificationTypeRelId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"commercePriceListQualificationTypeRelId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		CommercePriceListQualificationTypeRel newCommercePriceListQualificationTypeRel =
			addCommercePriceListQualificationTypeRel();

		_persistence.clearCache();

		CommercePriceListQualificationTypeRel existingCommercePriceListQualificationTypeRel =
			_persistence.findByPrimaryKey(newCommercePriceListQualificationTypeRel.getPrimaryKey());

		Assert.assertTrue(Objects.equals(
				existingCommercePriceListQualificationTypeRel.getUuid(),
				ReflectionTestUtil.invoke(
					existingCommercePriceListQualificationTypeRel,
					"getOriginalUuid", new Class<?>[0])));
		Assert.assertEquals(Long.valueOf(
				existingCommercePriceListQualificationTypeRel.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingCommercePriceListQualificationTypeRel,
				"getOriginalGroupId", new Class<?>[0]));

		Assert.assertTrue(Objects.equals(
				existingCommercePriceListQualificationTypeRel.getCommercePriceListQualificationType(),
				ReflectionTestUtil.invoke(
					existingCommercePriceListQualificationTypeRel,
					"getOriginalCommercePriceListQualificationType",
					new Class<?>[0])));
		Assert.assertEquals(Long.valueOf(
				existingCommercePriceListQualificationTypeRel.getCommercePriceListId()),
			ReflectionTestUtil.<Long>invoke(
				existingCommercePriceListQualificationTypeRel,
				"getOriginalCommercePriceListId", new Class<?>[0]));
	}

	protected CommercePriceListQualificationTypeRel addCommercePriceListQualificationTypeRel()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommercePriceListQualificationTypeRel commercePriceListQualificationTypeRel =
			_persistence.create(pk);

		commercePriceListQualificationTypeRel.setUuid(RandomTestUtil.randomString());

		commercePriceListQualificationTypeRel.setGroupId(RandomTestUtil.nextLong());

		commercePriceListQualificationTypeRel.setCompanyId(RandomTestUtil.nextLong());

		commercePriceListQualificationTypeRel.setUserId(RandomTestUtil.nextLong());

		commercePriceListQualificationTypeRel.setUserName(RandomTestUtil.randomString());

		commercePriceListQualificationTypeRel.setCreateDate(RandomTestUtil.nextDate());

		commercePriceListQualificationTypeRel.setModifiedDate(RandomTestUtil.nextDate());

		commercePriceListQualificationTypeRel.setCommercePriceListId(RandomTestUtil.nextLong());

		commercePriceListQualificationTypeRel.setCommercePriceListQualificationType(RandomTestUtil.randomString());

		commercePriceListQualificationTypeRel.setOrder(RandomTestUtil.nextInt());

		commercePriceListQualificationTypeRel.setLastPublishDate(RandomTestUtil.nextDate());

		_commercePriceListQualificationTypeRels.add(_persistence.update(
				commercePriceListQualificationTypeRel));

		return commercePriceListQualificationTypeRel;
	}

	private List<CommercePriceListQualificationTypeRel> _commercePriceListQualificationTypeRels =
		new ArrayList<CommercePriceListQualificationTypeRel>();
	private CommercePriceListQualificationTypeRelPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}