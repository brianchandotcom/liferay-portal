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

package com.liferay.commerce.notification.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.commerce.notification.exception.NoSuchNotificationTemplateUserSegmentRelException;
import com.liferay.commerce.notification.model.CommerceNotificationTemplateUserSegmentRel;
import com.liferay.commerce.notification.service.CommerceNotificationTemplateUserSegmentRelLocalServiceUtil;
import com.liferay.commerce.notification.service.persistence.CommerceNotificationTemplateUserSegmentRelPersistence;
import com.liferay.commerce.notification.service.persistence.CommerceNotificationTemplateUserSegmentRelUtil;

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
import java.util.Set;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class CommerceNotificationTemplateUserSegmentRelPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.commerce.notification.service"));

	@Before
	public void setUp() {
		_persistence = CommerceNotificationTemplateUserSegmentRelUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CommerceNotificationTemplateUserSegmentRel> iterator = _commerceNotificationTemplateUserSegmentRels.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceNotificationTemplateUserSegmentRel commerceNotificationTemplateUserSegmentRel =
			_persistence.create(pk);

		Assert.assertNotNull(commerceNotificationTemplateUserSegmentRel);

		Assert.assertEquals(commerceNotificationTemplateUserSegmentRel.getPrimaryKey(),
			pk);
	}

	@Test
	public void testRemove() throws Exception {
		CommerceNotificationTemplateUserSegmentRel newCommerceNotificationTemplateUserSegmentRel =
			addCommerceNotificationTemplateUserSegmentRel();

		_persistence.remove(newCommerceNotificationTemplateUserSegmentRel);

		CommerceNotificationTemplateUserSegmentRel existingCommerceNotificationTemplateUserSegmentRel =
			_persistence.fetchByPrimaryKey(newCommerceNotificationTemplateUserSegmentRel.getPrimaryKey());

		Assert.assertNull(existingCommerceNotificationTemplateUserSegmentRel);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCommerceNotificationTemplateUserSegmentRel();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceNotificationTemplateUserSegmentRel newCommerceNotificationTemplateUserSegmentRel =
			_persistence.create(pk);

		newCommerceNotificationTemplateUserSegmentRel.setGroupId(RandomTestUtil.nextLong());

		newCommerceNotificationTemplateUserSegmentRel.setCompanyId(RandomTestUtil.nextLong());

		newCommerceNotificationTemplateUserSegmentRel.setUserId(RandomTestUtil.nextLong());

		newCommerceNotificationTemplateUserSegmentRel.setUserName(RandomTestUtil.randomString());

		newCommerceNotificationTemplateUserSegmentRel.setCreateDate(RandomTestUtil.nextDate());

		newCommerceNotificationTemplateUserSegmentRel.setModifiedDate(RandomTestUtil.nextDate());

		newCommerceNotificationTemplateUserSegmentRel.setCommerceNotificationTemplateId(RandomTestUtil.nextLong());

		newCommerceNotificationTemplateUserSegmentRel.setCommerceUserSegmentEntryId(RandomTestUtil.nextLong());

		_commerceNotificationTemplateUserSegmentRels.add(_persistence.update(
				newCommerceNotificationTemplateUserSegmentRel));

		CommerceNotificationTemplateUserSegmentRel existingCommerceNotificationTemplateUserSegmentRel =
			_persistence.findByPrimaryKey(newCommerceNotificationTemplateUserSegmentRel.getPrimaryKey());

		Assert.assertEquals(existingCommerceNotificationTemplateUserSegmentRel.getCommerceNotificationTemplateUserSegmentRelId(),
			newCommerceNotificationTemplateUserSegmentRel.getCommerceNotificationTemplateUserSegmentRelId());
		Assert.assertEquals(existingCommerceNotificationTemplateUserSegmentRel.getGroupId(),
			newCommerceNotificationTemplateUserSegmentRel.getGroupId());
		Assert.assertEquals(existingCommerceNotificationTemplateUserSegmentRel.getCompanyId(),
			newCommerceNotificationTemplateUserSegmentRel.getCompanyId());
		Assert.assertEquals(existingCommerceNotificationTemplateUserSegmentRel.getUserId(),
			newCommerceNotificationTemplateUserSegmentRel.getUserId());
		Assert.assertEquals(existingCommerceNotificationTemplateUserSegmentRel.getUserName(),
			newCommerceNotificationTemplateUserSegmentRel.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceNotificationTemplateUserSegmentRel.getCreateDate()),
			Time.getShortTimestamp(
				newCommerceNotificationTemplateUserSegmentRel.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceNotificationTemplateUserSegmentRel.getModifiedDate()),
			Time.getShortTimestamp(
				newCommerceNotificationTemplateUserSegmentRel.getModifiedDate()));
		Assert.assertEquals(existingCommerceNotificationTemplateUserSegmentRel.getCommerceNotificationTemplateId(),
			newCommerceNotificationTemplateUserSegmentRel.getCommerceNotificationTemplateId());
		Assert.assertEquals(existingCommerceNotificationTemplateUserSegmentRel.getCommerceUserSegmentEntryId(),
			newCommerceNotificationTemplateUserSegmentRel.getCommerceUserSegmentEntryId());
	}

	@Test
	public void testCountByCommerceNotificationTemplateId()
		throws Exception {
		_persistence.countByCommerceNotificationTemplateId(RandomTestUtil.nextLong());

		_persistence.countByCommerceNotificationTemplateId(0L);
	}

	@Test
	public void testCountByCommerceUserSegmentEntryId()
		throws Exception {
		_persistence.countByCommerceUserSegmentEntryId(RandomTestUtil.nextLong());

		_persistence.countByCommerceUserSegmentEntryId(0L);
	}

	@Test
	public void testCountByC_C() throws Exception {
		_persistence.countByC_C(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByC_C(0L, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CommerceNotificationTemplateUserSegmentRel newCommerceNotificationTemplateUserSegmentRel =
			addCommerceNotificationTemplateUserSegmentRel();

		CommerceNotificationTemplateUserSegmentRel existingCommerceNotificationTemplateUserSegmentRel =
			_persistence.findByPrimaryKey(newCommerceNotificationTemplateUserSegmentRel.getPrimaryKey());

		Assert.assertEquals(existingCommerceNotificationTemplateUserSegmentRel,
			newCommerceNotificationTemplateUserSegmentRel);
	}

	@Test(expected = NoSuchNotificationTemplateUserSegmentRelException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<CommerceNotificationTemplateUserSegmentRel> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CNTemplateUserSegmentRel",
			"commerceNotificationTemplateUserSegmentRelId", true, "groupId",
			true, "companyId", true, "userId", true, "userName", true,
			"createDate", true, "modifiedDate", true,
			"commerceNotificationTemplateId", true,
			"commerceUserSegmentEntryId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CommerceNotificationTemplateUserSegmentRel newCommerceNotificationTemplateUserSegmentRel =
			addCommerceNotificationTemplateUserSegmentRel();

		CommerceNotificationTemplateUserSegmentRel existingCommerceNotificationTemplateUserSegmentRel =
			_persistence.fetchByPrimaryKey(newCommerceNotificationTemplateUserSegmentRel.getPrimaryKey());

		Assert.assertEquals(existingCommerceNotificationTemplateUserSegmentRel,
			newCommerceNotificationTemplateUserSegmentRel);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceNotificationTemplateUserSegmentRel missingCommerceNotificationTemplateUserSegmentRel =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCommerceNotificationTemplateUserSegmentRel);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CommerceNotificationTemplateUserSegmentRel newCommerceNotificationTemplateUserSegmentRel1 =
			addCommerceNotificationTemplateUserSegmentRel();
		CommerceNotificationTemplateUserSegmentRel newCommerceNotificationTemplateUserSegmentRel2 =
			addCommerceNotificationTemplateUserSegmentRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceNotificationTemplateUserSegmentRel1.getPrimaryKey());
		primaryKeys.add(newCommerceNotificationTemplateUserSegmentRel2.getPrimaryKey());

		Map<Serializable, CommerceNotificationTemplateUserSegmentRel> commerceNotificationTemplateUserSegmentRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2,
			commerceNotificationTemplateUserSegmentRels.size());
		Assert.assertEquals(newCommerceNotificationTemplateUserSegmentRel1,
			commerceNotificationTemplateUserSegmentRels.get(
				newCommerceNotificationTemplateUserSegmentRel1.getPrimaryKey()));
		Assert.assertEquals(newCommerceNotificationTemplateUserSegmentRel2,
			commerceNotificationTemplateUserSegmentRels.get(
				newCommerceNotificationTemplateUserSegmentRel2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CommerceNotificationTemplateUserSegmentRel> commerceNotificationTemplateUserSegmentRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceNotificationTemplateUserSegmentRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CommerceNotificationTemplateUserSegmentRel newCommerceNotificationTemplateUserSegmentRel =
			addCommerceNotificationTemplateUserSegmentRel();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceNotificationTemplateUserSegmentRel.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CommerceNotificationTemplateUserSegmentRel> commerceNotificationTemplateUserSegmentRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1,
			commerceNotificationTemplateUserSegmentRels.size());
		Assert.assertEquals(newCommerceNotificationTemplateUserSegmentRel,
			commerceNotificationTemplateUserSegmentRels.get(
				newCommerceNotificationTemplateUserSegmentRel.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CommerceNotificationTemplateUserSegmentRel> commerceNotificationTemplateUserSegmentRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceNotificationTemplateUserSegmentRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CommerceNotificationTemplateUserSegmentRel newCommerceNotificationTemplateUserSegmentRel =
			addCommerceNotificationTemplateUserSegmentRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceNotificationTemplateUserSegmentRel.getPrimaryKey());

		Map<Serializable, CommerceNotificationTemplateUserSegmentRel> commerceNotificationTemplateUserSegmentRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1,
			commerceNotificationTemplateUserSegmentRels.size());
		Assert.assertEquals(newCommerceNotificationTemplateUserSegmentRel,
			commerceNotificationTemplateUserSegmentRels.get(
				newCommerceNotificationTemplateUserSegmentRel.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CommerceNotificationTemplateUserSegmentRelLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CommerceNotificationTemplateUserSegmentRel>() {
				@Override
				public void performAction(
					CommerceNotificationTemplateUserSegmentRel commerceNotificationTemplateUserSegmentRel) {
					Assert.assertNotNull(commerceNotificationTemplateUserSegmentRel);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CommerceNotificationTemplateUserSegmentRel newCommerceNotificationTemplateUserSegmentRel =
			addCommerceNotificationTemplateUserSegmentRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceNotificationTemplateUserSegmentRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"commerceNotificationTemplateUserSegmentRelId",
				newCommerceNotificationTemplateUserSegmentRel.getCommerceNotificationTemplateUserSegmentRelId()));

		List<CommerceNotificationTemplateUserSegmentRel> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CommerceNotificationTemplateUserSegmentRel existingCommerceNotificationTemplateUserSegmentRel =
			result.get(0);

		Assert.assertEquals(existingCommerceNotificationTemplateUserSegmentRel,
			newCommerceNotificationTemplateUserSegmentRel);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceNotificationTemplateUserSegmentRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"commerceNotificationTemplateUserSegmentRelId",
				RandomTestUtil.nextLong()));

		List<CommerceNotificationTemplateUserSegmentRel> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CommerceNotificationTemplateUserSegmentRel newCommerceNotificationTemplateUserSegmentRel =
			addCommerceNotificationTemplateUserSegmentRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceNotificationTemplateUserSegmentRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceNotificationTemplateUserSegmentRelId"));

		Object newCommerceNotificationTemplateUserSegmentRelId = newCommerceNotificationTemplateUserSegmentRel.getCommerceNotificationTemplateUserSegmentRelId();

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"commerceNotificationTemplateUserSegmentRelId",
				new Object[] { newCommerceNotificationTemplateUserSegmentRelId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCommerceNotificationTemplateUserSegmentRelId = result.get(0);

		Assert.assertEquals(existingCommerceNotificationTemplateUserSegmentRelId,
			newCommerceNotificationTemplateUserSegmentRelId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceNotificationTemplateUserSegmentRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceNotificationTemplateUserSegmentRelId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"commerceNotificationTemplateUserSegmentRelId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		CommerceNotificationTemplateUserSegmentRel newCommerceNotificationTemplateUserSegmentRel =
			addCommerceNotificationTemplateUserSegmentRel();

		_persistence.clearCache();

		CommerceNotificationTemplateUserSegmentRel existingCommerceNotificationTemplateUserSegmentRel =
			_persistence.findByPrimaryKey(newCommerceNotificationTemplateUserSegmentRel.getPrimaryKey());

		Assert.assertEquals(Long.valueOf(
				existingCommerceNotificationTemplateUserSegmentRel.getCommerceNotificationTemplateId()),
			ReflectionTestUtil.<Long>invoke(
				existingCommerceNotificationTemplateUserSegmentRel,
				"getOriginalCommerceNotificationTemplateId", new Class<?>[0]));
		Assert.assertEquals(Long.valueOf(
				existingCommerceNotificationTemplateUserSegmentRel.getCommerceUserSegmentEntryId()),
			ReflectionTestUtil.<Long>invoke(
				existingCommerceNotificationTemplateUserSegmentRel,
				"getOriginalCommerceUserSegmentEntryId", new Class<?>[0]));
	}

	protected CommerceNotificationTemplateUserSegmentRel addCommerceNotificationTemplateUserSegmentRel()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceNotificationTemplateUserSegmentRel commerceNotificationTemplateUserSegmentRel =
			_persistence.create(pk);

		commerceNotificationTemplateUserSegmentRel.setGroupId(RandomTestUtil.nextLong());

		commerceNotificationTemplateUserSegmentRel.setCompanyId(RandomTestUtil.nextLong());

		commerceNotificationTemplateUserSegmentRel.setUserId(RandomTestUtil.nextLong());

		commerceNotificationTemplateUserSegmentRel.setUserName(RandomTestUtil.randomString());

		commerceNotificationTemplateUserSegmentRel.setCreateDate(RandomTestUtil.nextDate());

		commerceNotificationTemplateUserSegmentRel.setModifiedDate(RandomTestUtil.nextDate());

		commerceNotificationTemplateUserSegmentRel.setCommerceNotificationTemplateId(RandomTestUtil.nextLong());

		commerceNotificationTemplateUserSegmentRel.setCommerceUserSegmentEntryId(RandomTestUtil.nextLong());

		_commerceNotificationTemplateUserSegmentRels.add(_persistence.update(
				commerceNotificationTemplateUserSegmentRel));

		return commerceNotificationTemplateUserSegmentRel;
	}

	private List<CommerceNotificationTemplateUserSegmentRel> _commerceNotificationTemplateUserSegmentRels =
		new ArrayList<CommerceNotificationTemplateUserSegmentRel>();
	private CommerceNotificationTemplateUserSegmentRelPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}