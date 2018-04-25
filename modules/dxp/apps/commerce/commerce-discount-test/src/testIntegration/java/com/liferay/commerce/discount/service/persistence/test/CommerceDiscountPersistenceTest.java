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

import com.liferay.commerce.discount.exception.NoSuchDiscountException;
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.service.CommerceDiscountLocalServiceUtil;
import com.liferay.commerce.discount.service.persistence.CommerceDiscountPersistence;
import com.liferay.commerce.discount.service.persistence.CommerceDiscountUtil;

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

import java.math.BigDecimal;

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
public class CommerceDiscountPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.commerce.discount.service"));

	@Before
	public void setUp() {
		_persistence = CommerceDiscountUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CommerceDiscount> iterator = _commerceDiscounts.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceDiscount commerceDiscount = _persistence.create(pk);

		Assert.assertNotNull(commerceDiscount);

		Assert.assertEquals(commerceDiscount.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CommerceDiscount newCommerceDiscount = addCommerceDiscount();

		_persistence.remove(newCommerceDiscount);

		CommerceDiscount existingCommerceDiscount = _persistence.fetchByPrimaryKey(newCommerceDiscount.getPrimaryKey());

		Assert.assertNull(existingCommerceDiscount);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCommerceDiscount();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceDiscount newCommerceDiscount = _persistence.create(pk);

		newCommerceDiscount.setUuid(RandomTestUtil.randomString());

		newCommerceDiscount.setGroupId(RandomTestUtil.nextLong());

		newCommerceDiscount.setCompanyId(RandomTestUtil.nextLong());

		newCommerceDiscount.setUserId(RandomTestUtil.nextLong());

		newCommerceDiscount.setUserName(RandomTestUtil.randomString());

		newCommerceDiscount.setCreateDate(RandomTestUtil.nextDate());

		newCommerceDiscount.setModifiedDate(RandomTestUtil.nextDate());

		newCommerceDiscount.setTarget(RandomTestUtil.randomString());

		newCommerceDiscount.setType(RandomTestUtil.randomString());

		newCommerceDiscount.setTypeSettings(RandomTestUtil.randomString());

		newCommerceDiscount.setTitle(RandomTestUtil.randomString());

		newCommerceDiscount.setUseCouponCode(RandomTestUtil.randomBoolean());

		newCommerceDiscount.setCouponCode(RandomTestUtil.randomString());

		newCommerceDiscount.setLimitationType(RandomTestUtil.randomString());

		newCommerceDiscount.setLimitationTimes(RandomTestUtil.nextInt());

		newCommerceDiscount.setNumberOfUse(RandomTestUtil.nextInt());

		newCommerceDiscount.setCumulative(RandomTestUtil.randomBoolean());

		newCommerceDiscount.setUsePercentage(RandomTestUtil.randomBoolean());

		newCommerceDiscount.setLevel1(new BigDecimal(
				RandomTestUtil.nextDouble()));

		newCommerceDiscount.setLevel2(new BigDecimal(
				RandomTestUtil.nextDouble()));

		newCommerceDiscount.setLevel3(new BigDecimal(
				RandomTestUtil.nextDouble()));

		newCommerceDiscount.setMaximumDiscountAmount(new BigDecimal(
				RandomTestUtil.nextDouble()));

		newCommerceDiscount.setActive(RandomTestUtil.randomBoolean());

		newCommerceDiscount.setLastPublishDate(RandomTestUtil.nextDate());

		newCommerceDiscount.setStatus(RandomTestUtil.nextInt());

		newCommerceDiscount.setStatusByUserId(RandomTestUtil.nextLong());

		newCommerceDiscount.setStatusByUserName(RandomTestUtil.randomString());

		newCommerceDiscount.setStatusDate(RandomTestUtil.nextDate());

		_commerceDiscounts.add(_persistence.update(newCommerceDiscount));

		CommerceDiscount existingCommerceDiscount = _persistence.findByPrimaryKey(newCommerceDiscount.getPrimaryKey());

		Assert.assertEquals(existingCommerceDiscount.getUuid(),
			newCommerceDiscount.getUuid());
		Assert.assertEquals(existingCommerceDiscount.getCommerceDiscountId(),
			newCommerceDiscount.getCommerceDiscountId());
		Assert.assertEquals(existingCommerceDiscount.getGroupId(),
			newCommerceDiscount.getGroupId());
		Assert.assertEquals(existingCommerceDiscount.getCompanyId(),
			newCommerceDiscount.getCompanyId());
		Assert.assertEquals(existingCommerceDiscount.getUserId(),
			newCommerceDiscount.getUserId());
		Assert.assertEquals(existingCommerceDiscount.getUserName(),
			newCommerceDiscount.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceDiscount.getCreateDate()),
			Time.getShortTimestamp(newCommerceDiscount.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceDiscount.getModifiedDate()),
			Time.getShortTimestamp(newCommerceDiscount.getModifiedDate()));
		Assert.assertEquals(existingCommerceDiscount.getTarget(),
			newCommerceDiscount.getTarget());
		Assert.assertEquals(existingCommerceDiscount.getType(),
			newCommerceDiscount.getType());
		Assert.assertEquals(existingCommerceDiscount.getTypeSettings(),
			newCommerceDiscount.getTypeSettings());
		Assert.assertEquals(existingCommerceDiscount.getTitle(),
			newCommerceDiscount.getTitle());
		Assert.assertEquals(existingCommerceDiscount.isUseCouponCode(),
			newCommerceDiscount.isUseCouponCode());
		Assert.assertEquals(existingCommerceDiscount.getCouponCode(),
			newCommerceDiscount.getCouponCode());
		Assert.assertEquals(existingCommerceDiscount.getLimitationType(),
			newCommerceDiscount.getLimitationType());
		Assert.assertEquals(existingCommerceDiscount.getLimitationTimes(),
			newCommerceDiscount.getLimitationTimes());
		Assert.assertEquals(existingCommerceDiscount.getNumberOfUse(),
			newCommerceDiscount.getNumberOfUse());
		Assert.assertEquals(existingCommerceDiscount.isCumulative(),
			newCommerceDiscount.isCumulative());
		Assert.assertEquals(existingCommerceDiscount.isUsePercentage(),
			newCommerceDiscount.isUsePercentage());
		Assert.assertEquals(existingCommerceDiscount.getLevel1(),
			newCommerceDiscount.getLevel1());
		Assert.assertEquals(existingCommerceDiscount.getLevel2(),
			newCommerceDiscount.getLevel2());
		Assert.assertEquals(existingCommerceDiscount.getLevel3(),
			newCommerceDiscount.getLevel3());
		Assert.assertEquals(existingCommerceDiscount.getMaximumDiscountAmount(),
			newCommerceDiscount.getMaximumDiscountAmount());
		Assert.assertEquals(existingCommerceDiscount.isActive(),
			newCommerceDiscount.isActive());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceDiscount.getLastPublishDate()),
			Time.getShortTimestamp(newCommerceDiscount.getLastPublishDate()));
		Assert.assertEquals(existingCommerceDiscount.getStatus(),
			newCommerceDiscount.getStatus());
		Assert.assertEquals(existingCommerceDiscount.getStatusByUserId(),
			newCommerceDiscount.getStatusByUserId());
		Assert.assertEquals(existingCommerceDiscount.getStatusByUserName(),
			newCommerceDiscount.getStatusByUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceDiscount.getStatusDate()),
			Time.getShortTimestamp(newCommerceDiscount.getStatusDate()));
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
	public void testFindByPrimaryKeyExisting() throws Exception {
		CommerceDiscount newCommerceDiscount = addCommerceDiscount();

		CommerceDiscount existingCommerceDiscount = _persistence.findByPrimaryKey(newCommerceDiscount.getPrimaryKey());

		Assert.assertEquals(existingCommerceDiscount, newCommerceDiscount);
	}

	@Test(expected = NoSuchDiscountException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<CommerceDiscount> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CommerceDiscount", "uuid",
			true, "commerceDiscountId", true, "groupId", true, "companyId",
			true, "userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "target", true, "type", true, "typeSettings",
			true, "title", true, "useCouponCode", true, "couponCode", true,
			"limitationType", true, "limitationTimes", true, "numberOfUse",
			true, "cumulative", true, "usePercentage", true, "level1", true,
			"level2", true, "level3", true, "maximumDiscountAmount", true,
			"active", true, "lastPublishDate", true, "status", true,
			"statusByUserId", true, "statusByUserName", true, "statusDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CommerceDiscount newCommerceDiscount = addCommerceDiscount();

		CommerceDiscount existingCommerceDiscount = _persistence.fetchByPrimaryKey(newCommerceDiscount.getPrimaryKey());

		Assert.assertEquals(existingCommerceDiscount, newCommerceDiscount);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceDiscount missingCommerceDiscount = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCommerceDiscount);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CommerceDiscount newCommerceDiscount1 = addCommerceDiscount();
		CommerceDiscount newCommerceDiscount2 = addCommerceDiscount();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceDiscount1.getPrimaryKey());
		primaryKeys.add(newCommerceDiscount2.getPrimaryKey());

		Map<Serializable, CommerceDiscount> commerceDiscounts = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, commerceDiscounts.size());
		Assert.assertEquals(newCommerceDiscount1,
			commerceDiscounts.get(newCommerceDiscount1.getPrimaryKey()));
		Assert.assertEquals(newCommerceDiscount2,
			commerceDiscounts.get(newCommerceDiscount2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CommerceDiscount> commerceDiscounts = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceDiscounts.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CommerceDiscount newCommerceDiscount = addCommerceDiscount();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceDiscount.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CommerceDiscount> commerceDiscounts = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceDiscounts.size());
		Assert.assertEquals(newCommerceDiscount,
			commerceDiscounts.get(newCommerceDiscount.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CommerceDiscount> commerceDiscounts = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceDiscounts.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CommerceDiscount newCommerceDiscount = addCommerceDiscount();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceDiscount.getPrimaryKey());

		Map<Serializable, CommerceDiscount> commerceDiscounts = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceDiscounts.size());
		Assert.assertEquals(newCommerceDiscount,
			commerceDiscounts.get(newCommerceDiscount.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CommerceDiscountLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CommerceDiscount>() {
				@Override
				public void performAction(CommerceDiscount commerceDiscount) {
					Assert.assertNotNull(commerceDiscount);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CommerceDiscount newCommerceDiscount = addCommerceDiscount();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceDiscount.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("commerceDiscountId",
				newCommerceDiscount.getCommerceDiscountId()));

		List<CommerceDiscount> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CommerceDiscount existingCommerceDiscount = result.get(0);

		Assert.assertEquals(existingCommerceDiscount, newCommerceDiscount);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceDiscount.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("commerceDiscountId",
				RandomTestUtil.nextLong()));

		List<CommerceDiscount> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CommerceDiscount newCommerceDiscount = addCommerceDiscount();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceDiscount.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceDiscountId"));

		Object newCommerceDiscountId = newCommerceDiscount.getCommerceDiscountId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("commerceDiscountId",
				new Object[] { newCommerceDiscountId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCommerceDiscountId = result.get(0);

		Assert.assertEquals(existingCommerceDiscountId, newCommerceDiscountId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceDiscount.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceDiscountId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("commerceDiscountId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		CommerceDiscount newCommerceDiscount = addCommerceDiscount();

		_persistence.clearCache();

		CommerceDiscount existingCommerceDiscount = _persistence.findByPrimaryKey(newCommerceDiscount.getPrimaryKey());

		Assert.assertTrue(Objects.equals(existingCommerceDiscount.getUuid(),
				ReflectionTestUtil.invoke(existingCommerceDiscount,
					"getOriginalUuid", new Class<?>[0])));
		Assert.assertEquals(Long.valueOf(existingCommerceDiscount.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingCommerceDiscount,
				"getOriginalGroupId", new Class<?>[0]));
	}

	protected CommerceDiscount addCommerceDiscount() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceDiscount commerceDiscount = _persistence.create(pk);

		commerceDiscount.setUuid(RandomTestUtil.randomString());

		commerceDiscount.setGroupId(RandomTestUtil.nextLong());

		commerceDiscount.setCompanyId(RandomTestUtil.nextLong());

		commerceDiscount.setUserId(RandomTestUtil.nextLong());

		commerceDiscount.setUserName(RandomTestUtil.randomString());

		commerceDiscount.setCreateDate(RandomTestUtil.nextDate());

		commerceDiscount.setModifiedDate(RandomTestUtil.nextDate());

		commerceDiscount.setTarget(RandomTestUtil.randomString());

		commerceDiscount.setType(RandomTestUtil.randomString());

		commerceDiscount.setTypeSettings(RandomTestUtil.randomString());

		commerceDiscount.setTitle(RandomTestUtil.randomString());

		commerceDiscount.setUseCouponCode(RandomTestUtil.randomBoolean());

		commerceDiscount.setCouponCode(RandomTestUtil.randomString());

		commerceDiscount.setLimitationType(RandomTestUtil.randomString());

		commerceDiscount.setLimitationTimes(RandomTestUtil.nextInt());

		commerceDiscount.setNumberOfUse(RandomTestUtil.nextInt());

		commerceDiscount.setCumulative(RandomTestUtil.randomBoolean());

		commerceDiscount.setUsePercentage(RandomTestUtil.randomBoolean());

		commerceDiscount.setLevel1(new BigDecimal(RandomTestUtil.nextDouble()));

		commerceDiscount.setLevel2(new BigDecimal(RandomTestUtil.nextDouble()));

		commerceDiscount.setLevel3(new BigDecimal(RandomTestUtil.nextDouble()));

		commerceDiscount.setMaximumDiscountAmount(new BigDecimal(
				RandomTestUtil.nextDouble()));

		commerceDiscount.setActive(RandomTestUtil.randomBoolean());

		commerceDiscount.setLastPublishDate(RandomTestUtil.nextDate());

		commerceDiscount.setStatus(RandomTestUtil.nextInt());

		commerceDiscount.setStatusByUserId(RandomTestUtil.nextLong());

		commerceDiscount.setStatusByUserName(RandomTestUtil.randomString());

		commerceDiscount.setStatusDate(RandomTestUtil.nextDate());

		_commerceDiscounts.add(_persistence.update(commerceDiscount));

		return commerceDiscount;
	}

	private List<CommerceDiscount> _commerceDiscounts = new ArrayList<CommerceDiscount>();
	private CommerceDiscountPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}