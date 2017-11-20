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

package com.liferay.commerce.price.list.qualification.type.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.commerce.price.list.qualification.type.exception.NoSuchPriceListUserRelException;
import com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel;
import com.liferay.commerce.price.list.qualification.type.service.CommercePriceListUserRelLocalServiceUtil;
import com.liferay.commerce.price.list.qualification.type.service.persistence.CommercePriceListUserRelPersistence;
import com.liferay.commerce.price.list.qualification.type.service.persistence.CommercePriceListUserRelUtil;

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
import com.liferay.portal.kernel.util.StringPool;
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
public class CommercePriceListUserRelPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.commerce.price.list.qualification.type.service"));

	@Before
	public void setUp() {
		_persistence = CommercePriceListUserRelUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CommercePriceListUserRel> iterator = _commercePriceListUserRels.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommercePriceListUserRel commercePriceListUserRel = _persistence.create(pk);

		Assert.assertNotNull(commercePriceListUserRel);

		Assert.assertEquals(commercePriceListUserRel.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CommercePriceListUserRel newCommercePriceListUserRel = addCommercePriceListUserRel();

		_persistence.remove(newCommercePriceListUserRel);

		CommercePriceListUserRel existingCommercePriceListUserRel = _persistence.fetchByPrimaryKey(newCommercePriceListUserRel.getPrimaryKey());

		Assert.assertNull(existingCommercePriceListUserRel);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCommercePriceListUserRel();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommercePriceListUserRel newCommercePriceListUserRel = _persistence.create(pk);

		newCommercePriceListUserRel.setUuid(RandomTestUtil.randomString());

		newCommercePriceListUserRel.setGroupId(RandomTestUtil.nextLong());

		newCommercePriceListUserRel.setCompanyId(RandomTestUtil.nextLong());

		newCommercePriceListUserRel.setUserId(RandomTestUtil.nextLong());

		newCommercePriceListUserRel.setUserName(RandomTestUtil.randomString());

		newCommercePriceListUserRel.setCreateDate(RandomTestUtil.nextDate());

		newCommercePriceListUserRel.setModifiedDate(RandomTestUtil.nextDate());

		newCommercePriceListUserRel.setCommercePriceListQualificationTypeRelId(RandomTestUtil.nextLong());

		newCommercePriceListUserRel.setClassNameId(RandomTestUtil.nextLong());

		newCommercePriceListUserRel.setClassPK(RandomTestUtil.nextLong());

		newCommercePriceListUserRel.setLastPublishDate(RandomTestUtil.nextDate());

		_commercePriceListUserRels.add(_persistence.update(
				newCommercePriceListUserRel));

		CommercePriceListUserRel existingCommercePriceListUserRel = _persistence.findByPrimaryKey(newCommercePriceListUserRel.getPrimaryKey());

		Assert.assertEquals(existingCommercePriceListUserRel.getUuid(),
			newCommercePriceListUserRel.getUuid());
		Assert.assertEquals(existingCommercePriceListUserRel.getCommercePriceListUserRelId(),
			newCommercePriceListUserRel.getCommercePriceListUserRelId());
		Assert.assertEquals(existingCommercePriceListUserRel.getGroupId(),
			newCommercePriceListUserRel.getGroupId());
		Assert.assertEquals(existingCommercePriceListUserRel.getCompanyId(),
			newCommercePriceListUserRel.getCompanyId());
		Assert.assertEquals(existingCommercePriceListUserRel.getUserId(),
			newCommercePriceListUserRel.getUserId());
		Assert.assertEquals(existingCommercePriceListUserRel.getUserName(),
			newCommercePriceListUserRel.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommercePriceListUserRel.getCreateDate()),
			Time.getShortTimestamp(newCommercePriceListUserRel.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommercePriceListUserRel.getModifiedDate()),
			Time.getShortTimestamp(
				newCommercePriceListUserRel.getModifiedDate()));
		Assert.assertEquals(existingCommercePriceListUserRel.getCommercePriceListQualificationTypeRelId(),
			newCommercePriceListUserRel.getCommercePriceListQualificationTypeRelId());
		Assert.assertEquals(existingCommercePriceListUserRel.getClassNameId(),
			newCommercePriceListUserRel.getClassNameId());
		Assert.assertEquals(existingCommercePriceListUserRel.getClassPK(),
			newCommercePriceListUserRel.getClassPK());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommercePriceListUserRel.getLastPublishDate()),
			Time.getShortTimestamp(
				newCommercePriceListUserRel.getLastPublishDate()));
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
	public void testCountByCommercePriceListQualificationTypeRelId()
		throws Exception {
		_persistence.countByCommercePriceListQualificationTypeRelId(RandomTestUtil.nextLong());

		_persistence.countByCommercePriceListQualificationTypeRelId(0L);
	}

	@Test
	public void testCountByC_C() throws Exception {
		_persistence.countByC_C(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByC_C(0L, 0L);
	}

	@Test
	public void testCountByC_C_C() throws Exception {
		_persistence.countByC_C_C(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByC_C_C(0L, 0L, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CommercePriceListUserRel newCommercePriceListUserRel = addCommercePriceListUserRel();

		CommercePriceListUserRel existingCommercePriceListUserRel = _persistence.findByPrimaryKey(newCommercePriceListUserRel.getPrimaryKey());

		Assert.assertEquals(existingCommercePriceListUserRel,
			newCommercePriceListUserRel);
	}

	@Test(expected = NoSuchPriceListUserRelException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<CommercePriceListUserRel> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CommercePriceListUserRel",
			"uuid", true, "commercePriceListUserRelId", true, "groupId", true,
			"companyId", true, "userId", true, "userName", true, "createDate",
			true, "modifiedDate", true,
			"commercePriceListQualificationTypeRelId", true, "classNameId",
			true, "classPK", true, "lastPublishDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CommercePriceListUserRel newCommercePriceListUserRel = addCommercePriceListUserRel();

		CommercePriceListUserRel existingCommercePriceListUserRel = _persistence.fetchByPrimaryKey(newCommercePriceListUserRel.getPrimaryKey());

		Assert.assertEquals(existingCommercePriceListUserRel,
			newCommercePriceListUserRel);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommercePriceListUserRel missingCommercePriceListUserRel = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCommercePriceListUserRel);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CommercePriceListUserRel newCommercePriceListUserRel1 = addCommercePriceListUserRel();
		CommercePriceListUserRel newCommercePriceListUserRel2 = addCommercePriceListUserRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommercePriceListUserRel1.getPrimaryKey());
		primaryKeys.add(newCommercePriceListUserRel2.getPrimaryKey());

		Map<Serializable, CommercePriceListUserRel> commercePriceListUserRels = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, commercePriceListUserRels.size());
		Assert.assertEquals(newCommercePriceListUserRel1,
			commercePriceListUserRels.get(
				newCommercePriceListUserRel1.getPrimaryKey()));
		Assert.assertEquals(newCommercePriceListUserRel2,
			commercePriceListUserRels.get(
				newCommercePriceListUserRel2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CommercePriceListUserRel> commercePriceListUserRels = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commercePriceListUserRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CommercePriceListUserRel newCommercePriceListUserRel = addCommercePriceListUserRel();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommercePriceListUserRel.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CommercePriceListUserRel> commercePriceListUserRels = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commercePriceListUserRels.size());
		Assert.assertEquals(newCommercePriceListUserRel,
			commercePriceListUserRels.get(
				newCommercePriceListUserRel.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CommercePriceListUserRel> commercePriceListUserRels = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commercePriceListUserRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CommercePriceListUserRel newCommercePriceListUserRel = addCommercePriceListUserRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommercePriceListUserRel.getPrimaryKey());

		Map<Serializable, CommercePriceListUserRel> commercePriceListUserRels = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commercePriceListUserRels.size());
		Assert.assertEquals(newCommercePriceListUserRel,
			commercePriceListUserRels.get(
				newCommercePriceListUserRel.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CommercePriceListUserRelLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CommercePriceListUserRel>() {
				@Override
				public void performAction(
					CommercePriceListUserRel commercePriceListUserRel) {
					Assert.assertNotNull(commercePriceListUserRel);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CommercePriceListUserRel newCommercePriceListUserRel = addCommercePriceListUserRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommercePriceListUserRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"commercePriceListUserRelId",
				newCommercePriceListUserRel.getCommercePriceListUserRelId()));

		List<CommercePriceListUserRel> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CommercePriceListUserRel existingCommercePriceListUserRel = result.get(0);

		Assert.assertEquals(existingCommercePriceListUserRel,
			newCommercePriceListUserRel);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommercePriceListUserRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"commercePriceListUserRelId", RandomTestUtil.nextLong()));

		List<CommercePriceListUserRel> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CommercePriceListUserRel newCommercePriceListUserRel = addCommercePriceListUserRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommercePriceListUserRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commercePriceListUserRelId"));

		Object newCommercePriceListUserRelId = newCommercePriceListUserRel.getCommercePriceListUserRelId();

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"commercePriceListUserRelId",
				new Object[] { newCommercePriceListUserRelId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCommercePriceListUserRelId = result.get(0);

		Assert.assertEquals(existingCommercePriceListUserRelId,
			newCommercePriceListUserRelId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommercePriceListUserRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commercePriceListUserRelId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"commercePriceListUserRelId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		CommercePriceListUserRel newCommercePriceListUserRel = addCommercePriceListUserRel();

		_persistence.clearCache();

		CommercePriceListUserRel existingCommercePriceListUserRel = _persistence.findByPrimaryKey(newCommercePriceListUserRel.getPrimaryKey());

		Assert.assertTrue(Objects.equals(
				existingCommercePriceListUserRel.getUuid(),
				ReflectionTestUtil.invoke(existingCommercePriceListUserRel,
					"getOriginalUuid", new Class<?>[0])));
		Assert.assertEquals(Long.valueOf(
				existingCommercePriceListUserRel.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingCommercePriceListUserRel,
				"getOriginalGroupId", new Class<?>[0]));
	}

	protected CommercePriceListUserRel addCommercePriceListUserRel()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommercePriceListUserRel commercePriceListUserRel = _persistence.create(pk);

		commercePriceListUserRel.setUuid(RandomTestUtil.randomString());

		commercePriceListUserRel.setGroupId(RandomTestUtil.nextLong());

		commercePriceListUserRel.setCompanyId(RandomTestUtil.nextLong());

		commercePriceListUserRel.setUserId(RandomTestUtil.nextLong());

		commercePriceListUserRel.setUserName(RandomTestUtil.randomString());

		commercePriceListUserRel.setCreateDate(RandomTestUtil.nextDate());

		commercePriceListUserRel.setModifiedDate(RandomTestUtil.nextDate());

		commercePriceListUserRel.setCommercePriceListQualificationTypeRelId(RandomTestUtil.nextLong());

		commercePriceListUserRel.setClassNameId(RandomTestUtil.nextLong());

		commercePriceListUserRel.setClassPK(RandomTestUtil.nextLong());

		commercePriceListUserRel.setLastPublishDate(RandomTestUtil.nextDate());

		_commercePriceListUserRels.add(_persistence.update(
				commercePriceListUserRel));

		return commercePriceListUserRel;
	}

	private List<CommercePriceListUserRel> _commercePriceListUserRels = new ArrayList<CommercePriceListUserRel>();
	private CommercePriceListUserRelPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}