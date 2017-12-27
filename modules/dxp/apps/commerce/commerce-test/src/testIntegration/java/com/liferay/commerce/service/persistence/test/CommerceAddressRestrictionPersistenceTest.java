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

import com.liferay.commerce.exception.NoSuchAddressRestrictionException;
import com.liferay.commerce.model.CommerceAddressRestriction;
import com.liferay.commerce.service.CommerceAddressRestrictionLocalServiceUtil;
import com.liferay.commerce.service.persistence.CommerceAddressRestrictionPersistence;
import com.liferay.commerce.service.persistence.CommerceAddressRestrictionUtil;

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
public class CommerceAddressRestrictionPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.commerce.service"));

	@Before
	public void setUp() {
		_persistence = CommerceAddressRestrictionUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CommerceAddressRestriction> iterator = _commerceAddressRestrictions.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceAddressRestriction commerceAddressRestriction = _persistence.create(pk);

		Assert.assertNotNull(commerceAddressRestriction);

		Assert.assertEquals(commerceAddressRestriction.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CommerceAddressRestriction newCommerceAddressRestriction = addCommerceAddressRestriction();

		_persistence.remove(newCommerceAddressRestriction);

		CommerceAddressRestriction existingCommerceAddressRestriction = _persistence.fetchByPrimaryKey(newCommerceAddressRestriction.getPrimaryKey());

		Assert.assertNull(existingCommerceAddressRestriction);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCommerceAddressRestriction();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceAddressRestriction newCommerceAddressRestriction = _persistence.create(pk);

		newCommerceAddressRestriction.setGroupId(RandomTestUtil.nextLong());

		newCommerceAddressRestriction.setCompanyId(RandomTestUtil.nextLong());

		newCommerceAddressRestriction.setUserId(RandomTestUtil.nextLong());

		newCommerceAddressRestriction.setUserName(RandomTestUtil.randomString());

		newCommerceAddressRestriction.setCreateDate(RandomTestUtil.nextDate());

		newCommerceAddressRestriction.setModifiedDate(RandomTestUtil.nextDate());

		newCommerceAddressRestriction.setClassNameId(RandomTestUtil.nextLong());

		newCommerceAddressRestriction.setClassPK(RandomTestUtil.nextLong());

		newCommerceAddressRestriction.setCommerceCountryId(RandomTestUtil.nextLong());

		_commerceAddressRestrictions.add(_persistence.update(
				newCommerceAddressRestriction));

		CommerceAddressRestriction existingCommerceAddressRestriction = _persistence.findByPrimaryKey(newCommerceAddressRestriction.getPrimaryKey());

		Assert.assertEquals(existingCommerceAddressRestriction.getCommerceAddressRestrictionId(),
			newCommerceAddressRestriction.getCommerceAddressRestrictionId());
		Assert.assertEquals(existingCommerceAddressRestriction.getGroupId(),
			newCommerceAddressRestriction.getGroupId());
		Assert.assertEquals(existingCommerceAddressRestriction.getCompanyId(),
			newCommerceAddressRestriction.getCompanyId());
		Assert.assertEquals(existingCommerceAddressRestriction.getUserId(),
			newCommerceAddressRestriction.getUserId());
		Assert.assertEquals(existingCommerceAddressRestriction.getUserName(),
			newCommerceAddressRestriction.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceAddressRestriction.getCreateDate()),
			Time.getShortTimestamp(
				newCommerceAddressRestriction.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceAddressRestriction.getModifiedDate()),
			Time.getShortTimestamp(
				newCommerceAddressRestriction.getModifiedDate()));
		Assert.assertEquals(existingCommerceAddressRestriction.getClassNameId(),
			newCommerceAddressRestriction.getClassNameId());
		Assert.assertEquals(existingCommerceAddressRestriction.getClassPK(),
			newCommerceAddressRestriction.getClassPK());
		Assert.assertEquals(existingCommerceAddressRestriction.getCommerceCountryId(),
			newCommerceAddressRestriction.getCommerceCountryId());
	}

	@Test
	public void testCountByCommerceCountryId() throws Exception {
		_persistence.countByCommerceCountryId(RandomTestUtil.nextLong());

		_persistence.countByCommerceCountryId(0L);
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
		CommerceAddressRestriction newCommerceAddressRestriction = addCommerceAddressRestriction();

		CommerceAddressRestriction existingCommerceAddressRestriction = _persistence.findByPrimaryKey(newCommerceAddressRestriction.getPrimaryKey());

		Assert.assertEquals(existingCommerceAddressRestriction,
			newCommerceAddressRestriction);
	}

	@Test(expected = NoSuchAddressRestrictionException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<CommerceAddressRestriction> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CommerceAddressRestriction",
			"commerceAddressRestrictionId", true, "groupId", true, "companyId",
			true, "userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "classNameId", true, "classPK", true,
			"commerceCountryId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CommerceAddressRestriction newCommerceAddressRestriction = addCommerceAddressRestriction();

		CommerceAddressRestriction existingCommerceAddressRestriction = _persistence.fetchByPrimaryKey(newCommerceAddressRestriction.getPrimaryKey());

		Assert.assertEquals(existingCommerceAddressRestriction,
			newCommerceAddressRestriction);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceAddressRestriction missingCommerceAddressRestriction = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCommerceAddressRestriction);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CommerceAddressRestriction newCommerceAddressRestriction1 = addCommerceAddressRestriction();
		CommerceAddressRestriction newCommerceAddressRestriction2 = addCommerceAddressRestriction();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceAddressRestriction1.getPrimaryKey());
		primaryKeys.add(newCommerceAddressRestriction2.getPrimaryKey());

		Map<Serializable, CommerceAddressRestriction> commerceAddressRestrictions =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, commerceAddressRestrictions.size());
		Assert.assertEquals(newCommerceAddressRestriction1,
			commerceAddressRestrictions.get(
				newCommerceAddressRestriction1.getPrimaryKey()));
		Assert.assertEquals(newCommerceAddressRestriction2,
			commerceAddressRestrictions.get(
				newCommerceAddressRestriction2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CommerceAddressRestriction> commerceAddressRestrictions =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceAddressRestrictions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CommerceAddressRestriction newCommerceAddressRestriction = addCommerceAddressRestriction();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceAddressRestriction.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CommerceAddressRestriction> commerceAddressRestrictions =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceAddressRestrictions.size());
		Assert.assertEquals(newCommerceAddressRestriction,
			commerceAddressRestrictions.get(
				newCommerceAddressRestriction.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CommerceAddressRestriction> commerceAddressRestrictions =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceAddressRestrictions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CommerceAddressRestriction newCommerceAddressRestriction = addCommerceAddressRestriction();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceAddressRestriction.getPrimaryKey());

		Map<Serializable, CommerceAddressRestriction> commerceAddressRestrictions =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceAddressRestrictions.size());
		Assert.assertEquals(newCommerceAddressRestriction,
			commerceAddressRestrictions.get(
				newCommerceAddressRestriction.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CommerceAddressRestrictionLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CommerceAddressRestriction>() {
				@Override
				public void performAction(
					CommerceAddressRestriction commerceAddressRestriction) {
					Assert.assertNotNull(commerceAddressRestriction);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CommerceAddressRestriction newCommerceAddressRestriction = addCommerceAddressRestriction();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceAddressRestriction.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"commerceAddressRestrictionId",
				newCommerceAddressRestriction.getCommerceAddressRestrictionId()));

		List<CommerceAddressRestriction> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CommerceAddressRestriction existingCommerceAddressRestriction = result.get(0);

		Assert.assertEquals(existingCommerceAddressRestriction,
			newCommerceAddressRestriction);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceAddressRestriction.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"commerceAddressRestrictionId", RandomTestUtil.nextLong()));

		List<CommerceAddressRestriction> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CommerceAddressRestriction newCommerceAddressRestriction = addCommerceAddressRestriction();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceAddressRestriction.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceAddressRestrictionId"));

		Object newCommerceAddressRestrictionId = newCommerceAddressRestriction.getCommerceAddressRestrictionId();

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"commerceAddressRestrictionId",
				new Object[] { newCommerceAddressRestrictionId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCommerceAddressRestrictionId = result.get(0);

		Assert.assertEquals(existingCommerceAddressRestrictionId,
			newCommerceAddressRestrictionId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceAddressRestriction.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceAddressRestrictionId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"commerceAddressRestrictionId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		CommerceAddressRestriction newCommerceAddressRestriction = addCommerceAddressRestriction();

		_persistence.clearCache();

		CommerceAddressRestriction existingCommerceAddressRestriction = _persistence.findByPrimaryKey(newCommerceAddressRestriction.getPrimaryKey());

		Assert.assertEquals(Long.valueOf(
				existingCommerceAddressRestriction.getClassNameId()),
			ReflectionTestUtil.<Long>invoke(
				existingCommerceAddressRestriction, "getOriginalClassNameId",
				new Class<?>[0]));
		Assert.assertEquals(Long.valueOf(
				existingCommerceAddressRestriction.getClassPK()),
			ReflectionTestUtil.<Long>invoke(
				existingCommerceAddressRestriction, "getOriginalClassPK",
				new Class<?>[0]));
		Assert.assertEquals(Long.valueOf(
				existingCommerceAddressRestriction.getCommerceCountryId()),
			ReflectionTestUtil.<Long>invoke(
				existingCommerceAddressRestriction,
				"getOriginalCommerceCountryId", new Class<?>[0]));
	}

	protected CommerceAddressRestriction addCommerceAddressRestriction()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceAddressRestriction commerceAddressRestriction = _persistence.create(pk);

		commerceAddressRestriction.setGroupId(RandomTestUtil.nextLong());

		commerceAddressRestriction.setCompanyId(RandomTestUtil.nextLong());

		commerceAddressRestriction.setUserId(RandomTestUtil.nextLong());

		commerceAddressRestriction.setUserName(RandomTestUtil.randomString());

		commerceAddressRestriction.setCreateDate(RandomTestUtil.nextDate());

		commerceAddressRestriction.setModifiedDate(RandomTestUtil.nextDate());

		commerceAddressRestriction.setClassNameId(RandomTestUtil.nextLong());

		commerceAddressRestriction.setClassPK(RandomTestUtil.nextLong());

		commerceAddressRestriction.setCommerceCountryId(RandomTestUtil.nextLong());

		_commerceAddressRestrictions.add(_persistence.update(
				commerceAddressRestriction));

		return commerceAddressRestriction;
	}

	private List<CommerceAddressRestriction> _commerceAddressRestrictions = new ArrayList<CommerceAddressRestriction>();
	private CommerceAddressRestrictionPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}