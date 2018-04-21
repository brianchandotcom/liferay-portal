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

import com.liferay.commerce.exception.NoSuchCountryException;
import com.liferay.commerce.model.CommerceCountry;
import com.liferay.commerce.service.CommerceCountryLocalServiceUtil;
import com.liferay.commerce.service.persistence.CommerceCountryPersistence;
import com.liferay.commerce.service.persistence.CommerceCountryUtil;

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
public class CommerceCountryPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.commerce.service"));

	@Before
	public void setUp() {
		_persistence = CommerceCountryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CommerceCountry> iterator = _commerceCountries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceCountry commerceCountry = _persistence.create(pk);

		Assert.assertNotNull(commerceCountry);

		Assert.assertEquals(commerceCountry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CommerceCountry newCommerceCountry = addCommerceCountry();

		_persistence.remove(newCommerceCountry);

		CommerceCountry existingCommerceCountry = _persistence.fetchByPrimaryKey(newCommerceCountry.getPrimaryKey());

		Assert.assertNull(existingCommerceCountry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCommerceCountry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceCountry newCommerceCountry = _persistence.create(pk);

		newCommerceCountry.setUuid(RandomTestUtil.randomString());

		newCommerceCountry.setGroupId(RandomTestUtil.nextLong());

		newCommerceCountry.setCompanyId(RandomTestUtil.nextLong());

		newCommerceCountry.setUserId(RandomTestUtil.nextLong());

		newCommerceCountry.setUserName(RandomTestUtil.randomString());

		newCommerceCountry.setCreateDate(RandomTestUtil.nextDate());

		newCommerceCountry.setModifiedDate(RandomTestUtil.nextDate());

		newCommerceCountry.setName(RandomTestUtil.randomString());

		newCommerceCountry.setBillingAllowed(RandomTestUtil.randomBoolean());

		newCommerceCountry.setShippingAllowed(RandomTestUtil.randomBoolean());

		newCommerceCountry.setTwoLettersISOCode(RandomTestUtil.randomString());

		newCommerceCountry.setThreeLettersISOCode(RandomTestUtil.randomString());

		newCommerceCountry.setNumericISOCode(RandomTestUtil.nextInt());

		newCommerceCountry.setSubjectToVAT(RandomTestUtil.randomBoolean());

		newCommerceCountry.setPriority(RandomTestUtil.nextDouble());

		newCommerceCountry.setActive(RandomTestUtil.randomBoolean());

		newCommerceCountry.setLastPublishDate(RandomTestUtil.nextDate());

		_commerceCountries.add(_persistence.update(newCommerceCountry));

		CommerceCountry existingCommerceCountry = _persistence.findByPrimaryKey(newCommerceCountry.getPrimaryKey());

		Assert.assertEquals(existingCommerceCountry.getUuid(),
			newCommerceCountry.getUuid());
		Assert.assertEquals(existingCommerceCountry.getCommerceCountryId(),
			newCommerceCountry.getCommerceCountryId());
		Assert.assertEquals(existingCommerceCountry.getGroupId(),
			newCommerceCountry.getGroupId());
		Assert.assertEquals(existingCommerceCountry.getCompanyId(),
			newCommerceCountry.getCompanyId());
		Assert.assertEquals(existingCommerceCountry.getUserId(),
			newCommerceCountry.getUserId());
		Assert.assertEquals(existingCommerceCountry.getUserName(),
			newCommerceCountry.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceCountry.getCreateDate()),
			Time.getShortTimestamp(newCommerceCountry.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceCountry.getModifiedDate()),
			Time.getShortTimestamp(newCommerceCountry.getModifiedDate()));
		Assert.assertEquals(existingCommerceCountry.getName(),
			newCommerceCountry.getName());
		Assert.assertEquals(existingCommerceCountry.isBillingAllowed(),
			newCommerceCountry.isBillingAllowed());
		Assert.assertEquals(existingCommerceCountry.isShippingAllowed(),
			newCommerceCountry.isShippingAllowed());
		Assert.assertEquals(existingCommerceCountry.getTwoLettersISOCode(),
			newCommerceCountry.getTwoLettersISOCode());
		Assert.assertEquals(existingCommerceCountry.getThreeLettersISOCode(),
			newCommerceCountry.getThreeLettersISOCode());
		Assert.assertEquals(existingCommerceCountry.getNumericISOCode(),
			newCommerceCountry.getNumericISOCode());
		Assert.assertEquals(existingCommerceCountry.isSubjectToVAT(),
			newCommerceCountry.isSubjectToVAT());
		AssertUtils.assertEquals(existingCommerceCountry.getPriority(),
			newCommerceCountry.getPriority());
		Assert.assertEquals(existingCommerceCountry.isActive(),
			newCommerceCountry.isActive());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceCountry.getLastPublishDate()),
			Time.getShortTimestamp(newCommerceCountry.getLastPublishDate()));
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
	public void testCountByG_N() throws Exception {
		_persistence.countByG_N(RandomTestUtil.nextLong(),
			RandomTestUtil.nextInt());

		_persistence.countByG_N(0L, 0);
	}

	@Test
	public void testCountByG_A() throws Exception {
		_persistence.countByG_A(RandomTestUtil.nextLong(),
			RandomTestUtil.randomBoolean());

		_persistence.countByG_A(0L, RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByG_B_A() throws Exception {
		_persistence.countByG_B_A(RandomTestUtil.nextLong(),
			RandomTestUtil.randomBoolean(), RandomTestUtil.randomBoolean());

		_persistence.countByG_B_A(0L, RandomTestUtil.randomBoolean(),
			RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByG_S_A() throws Exception {
		_persistence.countByG_S_A(RandomTestUtil.nextLong(),
			RandomTestUtil.randomBoolean(), RandomTestUtil.randomBoolean());

		_persistence.countByG_S_A(0L, RandomTestUtil.randomBoolean(),
			RandomTestUtil.randomBoolean());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CommerceCountry newCommerceCountry = addCommerceCountry();

		CommerceCountry existingCommerceCountry = _persistence.findByPrimaryKey(newCommerceCountry.getPrimaryKey());

		Assert.assertEquals(existingCommerceCountry, newCommerceCountry);
	}

	@Test(expected = NoSuchCountryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<CommerceCountry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CommerceCountry", "uuid",
			true, "commerceCountryId", true, "groupId", true, "companyId",
			true, "userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "name", true, "billingAllowed", true,
			"shippingAllowed", true, "twoLettersISOCode", true,
			"threeLettersISOCode", true, "numericISOCode", true,
			"subjectToVAT", true, "priority", true, "active", true,
			"lastPublishDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CommerceCountry newCommerceCountry = addCommerceCountry();

		CommerceCountry existingCommerceCountry = _persistence.fetchByPrimaryKey(newCommerceCountry.getPrimaryKey());

		Assert.assertEquals(existingCommerceCountry, newCommerceCountry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceCountry missingCommerceCountry = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCommerceCountry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CommerceCountry newCommerceCountry1 = addCommerceCountry();
		CommerceCountry newCommerceCountry2 = addCommerceCountry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceCountry1.getPrimaryKey());
		primaryKeys.add(newCommerceCountry2.getPrimaryKey());

		Map<Serializable, CommerceCountry> commerceCountries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, commerceCountries.size());
		Assert.assertEquals(newCommerceCountry1,
			commerceCountries.get(newCommerceCountry1.getPrimaryKey()));
		Assert.assertEquals(newCommerceCountry2,
			commerceCountries.get(newCommerceCountry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CommerceCountry> commerceCountries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceCountries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CommerceCountry newCommerceCountry = addCommerceCountry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceCountry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CommerceCountry> commerceCountries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceCountries.size());
		Assert.assertEquals(newCommerceCountry,
			commerceCountries.get(newCommerceCountry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CommerceCountry> commerceCountries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceCountries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CommerceCountry newCommerceCountry = addCommerceCountry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceCountry.getPrimaryKey());

		Map<Serializable, CommerceCountry> commerceCountries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceCountries.size());
		Assert.assertEquals(newCommerceCountry,
			commerceCountries.get(newCommerceCountry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CommerceCountryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CommerceCountry>() {
				@Override
				public void performAction(CommerceCountry commerceCountry) {
					Assert.assertNotNull(commerceCountry);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CommerceCountry newCommerceCountry = addCommerceCountry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceCountry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("commerceCountryId",
				newCommerceCountry.getCommerceCountryId()));

		List<CommerceCountry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CommerceCountry existingCommerceCountry = result.get(0);

		Assert.assertEquals(existingCommerceCountry, newCommerceCountry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceCountry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("commerceCountryId",
				RandomTestUtil.nextLong()));

		List<CommerceCountry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CommerceCountry newCommerceCountry = addCommerceCountry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceCountry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceCountryId"));

		Object newCommerceCountryId = newCommerceCountry.getCommerceCountryId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("commerceCountryId",
				new Object[] { newCommerceCountryId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCommerceCountryId = result.get(0);

		Assert.assertEquals(existingCommerceCountryId, newCommerceCountryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceCountry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceCountryId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("commerceCountryId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		CommerceCountry newCommerceCountry = addCommerceCountry();

		_persistence.clearCache();

		CommerceCountry existingCommerceCountry = _persistence.findByPrimaryKey(newCommerceCountry.getPrimaryKey());

		Assert.assertTrue(Objects.equals(existingCommerceCountry.getUuid(),
				ReflectionTestUtil.invoke(existingCommerceCountry,
					"getOriginalUuid", new Class<?>[0])));
		Assert.assertEquals(Long.valueOf(existingCommerceCountry.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingCommerceCountry,
				"getOriginalGroupId", new Class<?>[0]));

		Assert.assertEquals(Long.valueOf(existingCommerceCountry.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingCommerceCountry,
				"getOriginalGroupId", new Class<?>[0]));
		Assert.assertEquals(Integer.valueOf(
				existingCommerceCountry.getNumericISOCode()),
			ReflectionTestUtil.<Integer>invoke(existingCommerceCountry,
				"getOriginalNumericISOCode", new Class<?>[0]));
	}

	protected CommerceCountry addCommerceCountry() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceCountry commerceCountry = _persistence.create(pk);

		commerceCountry.setUuid(RandomTestUtil.randomString());

		commerceCountry.setGroupId(RandomTestUtil.nextLong());

		commerceCountry.setCompanyId(RandomTestUtil.nextLong());

		commerceCountry.setUserId(RandomTestUtil.nextLong());

		commerceCountry.setUserName(RandomTestUtil.randomString());

		commerceCountry.setCreateDate(RandomTestUtil.nextDate());

		commerceCountry.setModifiedDate(RandomTestUtil.nextDate());

		commerceCountry.setName(RandomTestUtil.randomString());

		commerceCountry.setBillingAllowed(RandomTestUtil.randomBoolean());

		commerceCountry.setShippingAllowed(RandomTestUtil.randomBoolean());

		commerceCountry.setTwoLettersISOCode(RandomTestUtil.randomString());

		commerceCountry.setThreeLettersISOCode(RandomTestUtil.randomString());

		commerceCountry.setNumericISOCode(RandomTestUtil.nextInt());

		commerceCountry.setSubjectToVAT(RandomTestUtil.randomBoolean());

		commerceCountry.setPriority(RandomTestUtil.nextDouble());

		commerceCountry.setActive(RandomTestUtil.randomBoolean());

		commerceCountry.setLastPublishDate(RandomTestUtil.nextDate());

		_commerceCountries.add(_persistence.update(commerceCountry));

		return commerceCountry;
	}

	private List<CommerceCountry> _commerceCountries = new ArrayList<CommerceCountry>();
	private CommerceCountryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}