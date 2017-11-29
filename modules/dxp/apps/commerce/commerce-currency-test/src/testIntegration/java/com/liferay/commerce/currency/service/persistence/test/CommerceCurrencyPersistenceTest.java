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

package com.liferay.commerce.currency.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.commerce.currency.exception.NoSuchCurrencyException;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalServiceUtil;
import com.liferay.commerce.currency.service.persistence.CommerceCurrencyPersistence;
import com.liferay.commerce.currency.service.persistence.CommerceCurrencyUtil;

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
public class CommerceCurrencyPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.commerce.currency.service"));

	@Before
	public void setUp() {
		_persistence = CommerceCurrencyUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CommerceCurrency> iterator = _commerceCurrencies.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceCurrency commerceCurrency = _persistence.create(pk);

		Assert.assertNotNull(commerceCurrency);

		Assert.assertEquals(commerceCurrency.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CommerceCurrency newCommerceCurrency = addCommerceCurrency();

		_persistence.remove(newCommerceCurrency);

		CommerceCurrency existingCommerceCurrency = _persistence.fetchByPrimaryKey(newCommerceCurrency.getPrimaryKey());

		Assert.assertNull(existingCommerceCurrency);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCommerceCurrency();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceCurrency newCommerceCurrency = _persistence.create(pk);

		newCommerceCurrency.setUuid(RandomTestUtil.randomString());

		newCommerceCurrency.setGroupId(RandomTestUtil.nextLong());

		newCommerceCurrency.setCompanyId(RandomTestUtil.nextLong());

		newCommerceCurrency.setUserId(RandomTestUtil.nextLong());

		newCommerceCurrency.setUserName(RandomTestUtil.randomString());

		newCommerceCurrency.setCreateDate(RandomTestUtil.nextDate());

		newCommerceCurrency.setModifiedDate(RandomTestUtil.nextDate());

		newCommerceCurrency.setCode(RandomTestUtil.randomString());

		newCommerceCurrency.setName(RandomTestUtil.randomString());

		newCommerceCurrency.setRate(RandomTestUtil.nextDouble());

		newCommerceCurrency.setRoundingType(RandomTestUtil.randomString());

		newCommerceCurrency.setPrimary(RandomTestUtil.randomBoolean());

		newCommerceCurrency.setPriority(RandomTestUtil.nextDouble());

		newCommerceCurrency.setActive(RandomTestUtil.randomBoolean());

		newCommerceCurrency.setLastPublishDate(RandomTestUtil.nextDate());

		_commerceCurrencies.add(_persistence.update(newCommerceCurrency));

		CommerceCurrency existingCommerceCurrency = _persistence.findByPrimaryKey(newCommerceCurrency.getPrimaryKey());

		Assert.assertEquals(existingCommerceCurrency.getUuid(),
			newCommerceCurrency.getUuid());
		Assert.assertEquals(existingCommerceCurrency.getCommerceCurrencyId(),
			newCommerceCurrency.getCommerceCurrencyId());
		Assert.assertEquals(existingCommerceCurrency.getGroupId(),
			newCommerceCurrency.getGroupId());
		Assert.assertEquals(existingCommerceCurrency.getCompanyId(),
			newCommerceCurrency.getCompanyId());
		Assert.assertEquals(existingCommerceCurrency.getUserId(),
			newCommerceCurrency.getUserId());
		Assert.assertEquals(existingCommerceCurrency.getUserName(),
			newCommerceCurrency.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceCurrency.getCreateDate()),
			Time.getShortTimestamp(newCommerceCurrency.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceCurrency.getModifiedDate()),
			Time.getShortTimestamp(newCommerceCurrency.getModifiedDate()));
		Assert.assertEquals(existingCommerceCurrency.getCode(),
			newCommerceCurrency.getCode());
		Assert.assertEquals(existingCommerceCurrency.getName(),
			newCommerceCurrency.getName());
		AssertUtils.assertEquals(existingCommerceCurrency.getRate(),
			newCommerceCurrency.getRate());
		Assert.assertEquals(existingCommerceCurrency.getRoundingType(),
			newCommerceCurrency.getRoundingType());
		Assert.assertEquals(existingCommerceCurrency.getPrimary(),
			newCommerceCurrency.getPrimary());
		AssertUtils.assertEquals(existingCommerceCurrency.getPriority(),
			newCommerceCurrency.getPriority());
		Assert.assertEquals(existingCommerceCurrency.getActive(),
			newCommerceCurrency.getActive());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceCurrency.getLastPublishDate()),
			Time.getShortTimestamp(newCommerceCurrency.getLastPublishDate()));
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
	public void testCountByG_P() throws Exception {
		_persistence.countByG_P(RandomTestUtil.nextLong(),
			RandomTestUtil.randomBoolean());

		_persistence.countByG_P(0L, RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByG_A() throws Exception {
		_persistence.countByG_A(RandomTestUtil.nextLong(),
			RandomTestUtil.randomBoolean());

		_persistence.countByG_A(0L, RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByG_P_A() throws Exception {
		_persistence.countByG_P_A(RandomTestUtil.nextLong(),
			RandomTestUtil.randomBoolean(), RandomTestUtil.randomBoolean());

		_persistence.countByG_P_A(0L, RandomTestUtil.randomBoolean(),
			RandomTestUtil.randomBoolean());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CommerceCurrency newCommerceCurrency = addCommerceCurrency();

		CommerceCurrency existingCommerceCurrency = _persistence.findByPrimaryKey(newCommerceCurrency.getPrimaryKey());

		Assert.assertEquals(existingCommerceCurrency, newCommerceCurrency);
	}

	@Test(expected = NoSuchCurrencyException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<CommerceCurrency> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CommerceCurrency", "uuid",
			true, "commerceCurrencyId", true, "groupId", true, "companyId",
			true, "userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "code", true, "name", true, "rate", true,
			"roundingType", true, "primary", true, "priority", true, "active",
			true, "lastPublishDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CommerceCurrency newCommerceCurrency = addCommerceCurrency();

		CommerceCurrency existingCommerceCurrency = _persistence.fetchByPrimaryKey(newCommerceCurrency.getPrimaryKey());

		Assert.assertEquals(existingCommerceCurrency, newCommerceCurrency);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceCurrency missingCommerceCurrency = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCommerceCurrency);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CommerceCurrency newCommerceCurrency1 = addCommerceCurrency();
		CommerceCurrency newCommerceCurrency2 = addCommerceCurrency();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceCurrency1.getPrimaryKey());
		primaryKeys.add(newCommerceCurrency2.getPrimaryKey());

		Map<Serializable, CommerceCurrency> commerceCurrencies = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, commerceCurrencies.size());
		Assert.assertEquals(newCommerceCurrency1,
			commerceCurrencies.get(newCommerceCurrency1.getPrimaryKey()));
		Assert.assertEquals(newCommerceCurrency2,
			commerceCurrencies.get(newCommerceCurrency2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CommerceCurrency> commerceCurrencies = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceCurrencies.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CommerceCurrency newCommerceCurrency = addCommerceCurrency();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceCurrency.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CommerceCurrency> commerceCurrencies = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceCurrencies.size());
		Assert.assertEquals(newCommerceCurrency,
			commerceCurrencies.get(newCommerceCurrency.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CommerceCurrency> commerceCurrencies = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceCurrencies.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CommerceCurrency newCommerceCurrency = addCommerceCurrency();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceCurrency.getPrimaryKey());

		Map<Serializable, CommerceCurrency> commerceCurrencies = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceCurrencies.size());
		Assert.assertEquals(newCommerceCurrency,
			commerceCurrencies.get(newCommerceCurrency.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CommerceCurrencyLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CommerceCurrency>() {
				@Override
				public void performAction(CommerceCurrency commerceCurrency) {
					Assert.assertNotNull(commerceCurrency);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CommerceCurrency newCommerceCurrency = addCommerceCurrency();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceCurrency.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("commerceCurrencyId",
				newCommerceCurrency.getCommerceCurrencyId()));

		List<CommerceCurrency> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CommerceCurrency existingCommerceCurrency = result.get(0);

		Assert.assertEquals(existingCommerceCurrency, newCommerceCurrency);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceCurrency.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("commerceCurrencyId",
				RandomTestUtil.nextLong()));

		List<CommerceCurrency> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CommerceCurrency newCommerceCurrency = addCommerceCurrency();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceCurrency.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceCurrencyId"));

		Object newCommerceCurrencyId = newCommerceCurrency.getCommerceCurrencyId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("commerceCurrencyId",
				new Object[] { newCommerceCurrencyId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCommerceCurrencyId = result.get(0);

		Assert.assertEquals(existingCommerceCurrencyId, newCommerceCurrencyId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceCurrency.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceCurrencyId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("commerceCurrencyId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		CommerceCurrency newCommerceCurrency = addCommerceCurrency();

		_persistence.clearCache();

		CommerceCurrency existingCommerceCurrency = _persistence.findByPrimaryKey(newCommerceCurrency.getPrimaryKey());

		Assert.assertTrue(Objects.equals(existingCommerceCurrency.getUuid(),
				ReflectionTestUtil.invoke(existingCommerceCurrency,
					"getOriginalUuid", new Class<?>[0])));
		Assert.assertEquals(Long.valueOf(existingCommerceCurrency.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingCommerceCurrency,
				"getOriginalGroupId", new Class<?>[0]));
	}

	protected CommerceCurrency addCommerceCurrency() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceCurrency commerceCurrency = _persistence.create(pk);

		commerceCurrency.setUuid(RandomTestUtil.randomString());

		commerceCurrency.setGroupId(RandomTestUtil.nextLong());

		commerceCurrency.setCompanyId(RandomTestUtil.nextLong());

		commerceCurrency.setUserId(RandomTestUtil.nextLong());

		commerceCurrency.setUserName(RandomTestUtil.randomString());

		commerceCurrency.setCreateDate(RandomTestUtil.nextDate());

		commerceCurrency.setModifiedDate(RandomTestUtil.nextDate());

		commerceCurrency.setCode(RandomTestUtil.randomString());

		commerceCurrency.setName(RandomTestUtil.randomString());

		commerceCurrency.setRate(RandomTestUtil.nextDouble());

		commerceCurrency.setRoundingType(RandomTestUtil.randomString());

		commerceCurrency.setPrimary(RandomTestUtil.randomBoolean());

		commerceCurrency.setPriority(RandomTestUtil.nextDouble());

		commerceCurrency.setActive(RandomTestUtil.randomBoolean());

		commerceCurrency.setLastPublishDate(RandomTestUtil.nextDate());

		_commerceCurrencies.add(_persistence.update(commerceCurrency));

		return commerceCurrency;
	}

	private List<CommerceCurrency> _commerceCurrencies = new ArrayList<CommerceCurrency>();
	private CommerceCurrencyPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}