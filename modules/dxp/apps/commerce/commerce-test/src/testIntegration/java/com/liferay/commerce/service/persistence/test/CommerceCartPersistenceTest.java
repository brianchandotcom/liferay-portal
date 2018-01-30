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

import com.liferay.commerce.exception.NoSuchCartException;
import com.liferay.commerce.model.CommerceCart;
import com.liferay.commerce.service.CommerceCartLocalServiceUtil;
import com.liferay.commerce.service.persistence.CommerceCartPersistence;
import com.liferay.commerce.service.persistence.CommerceCartUtil;

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
public class CommerceCartPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.commerce.service"));

	@Before
	public void setUp() {
		_persistence = CommerceCartUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CommerceCart> iterator = _commerceCarts.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceCart commerceCart = _persistence.create(pk);

		Assert.assertNotNull(commerceCart);

		Assert.assertEquals(commerceCart.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CommerceCart newCommerceCart = addCommerceCart();

		_persistence.remove(newCommerceCart);

		CommerceCart existingCommerceCart = _persistence.fetchByPrimaryKey(newCommerceCart.getPrimaryKey());

		Assert.assertNull(existingCommerceCart);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCommerceCart();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceCart newCommerceCart = _persistence.create(pk);

		newCommerceCart.setUuid(RandomTestUtil.randomString());

		newCommerceCart.setGroupId(RandomTestUtil.nextLong());

		newCommerceCart.setCompanyId(RandomTestUtil.nextLong());

		newCommerceCart.setUserId(RandomTestUtil.nextLong());

		newCommerceCart.setUserName(RandomTestUtil.randomString());

		newCommerceCart.setCreateDate(RandomTestUtil.nextDate());

		newCommerceCart.setModifiedDate(RandomTestUtil.nextDate());

		newCommerceCart.setName(RandomTestUtil.randomString());

		newCommerceCart.setDefaultCart(RandomTestUtil.randomBoolean());

		newCommerceCart.setType(RandomTestUtil.nextInt());

		newCommerceCart.setBillingAddressId(RandomTestUtil.nextLong());

		newCommerceCart.setShippingAddressId(RandomTestUtil.nextLong());

		newCommerceCart.setCommercePaymentMethodId(RandomTestUtil.nextLong());

		newCommerceCart.setCommerceShippingMethodId(RandomTestUtil.nextLong());

		newCommerceCart.setShippingOptionName(RandomTestUtil.randomString());

		newCommerceCart.setShippingPrice(RandomTestUtil.nextDouble());

		_commerceCarts.add(_persistence.update(newCommerceCart));

		CommerceCart existingCommerceCart = _persistence.findByPrimaryKey(newCommerceCart.getPrimaryKey());

		Assert.assertEquals(existingCommerceCart.getUuid(),
			newCommerceCart.getUuid());
		Assert.assertEquals(existingCommerceCart.getCommerceCartId(),
			newCommerceCart.getCommerceCartId());
		Assert.assertEquals(existingCommerceCart.getGroupId(),
			newCommerceCart.getGroupId());
		Assert.assertEquals(existingCommerceCart.getCompanyId(),
			newCommerceCart.getCompanyId());
		Assert.assertEquals(existingCommerceCart.getUserId(),
			newCommerceCart.getUserId());
		Assert.assertEquals(existingCommerceCart.getUserName(),
			newCommerceCart.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceCart.getCreateDate()),
			Time.getShortTimestamp(newCommerceCart.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceCart.getModifiedDate()),
			Time.getShortTimestamp(newCommerceCart.getModifiedDate()));
		Assert.assertEquals(existingCommerceCart.getName(),
			newCommerceCart.getName());
		Assert.assertEquals(existingCommerceCart.getDefaultCart(),
			newCommerceCart.getDefaultCart());
		Assert.assertEquals(existingCommerceCart.getType(),
			newCommerceCart.getType());
		Assert.assertEquals(existingCommerceCart.getBillingAddressId(),
			newCommerceCart.getBillingAddressId());
		Assert.assertEquals(existingCommerceCart.getShippingAddressId(),
			newCommerceCart.getShippingAddressId());
		Assert.assertEquals(existingCommerceCart.getCommercePaymentMethodId(),
			newCommerceCart.getCommercePaymentMethodId());
		Assert.assertEquals(existingCommerceCart.getCommerceShippingMethodId(),
			newCommerceCart.getCommerceShippingMethodId());
		Assert.assertEquals(existingCommerceCart.getShippingOptionName(),
			newCommerceCart.getShippingOptionName());
		AssertUtils.assertEquals(existingCommerceCart.getShippingPrice(),
			newCommerceCart.getShippingPrice());
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
	public void testCountByBillingAddressId() throws Exception {
		_persistence.countByBillingAddressId(RandomTestUtil.nextLong());

		_persistence.countByBillingAddressId(0L);
	}

	@Test
	public void testCountByShippingAddressId() throws Exception {
		_persistence.countByShippingAddressId(RandomTestUtil.nextLong());

		_persistence.countByShippingAddressId(0L);
	}

	@Test
	public void testCountByG_T() throws Exception {
		_persistence.countByG_T(RandomTestUtil.nextLong(),
			RandomTestUtil.nextInt());

		_persistence.countByG_T(0L, 0);
	}

	@Test
	public void testCountByG_U_N_T() throws Exception {
		_persistence.countByG_U_N_T(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), "", RandomTestUtil.nextInt());

		_persistence.countByG_U_N_T(0L, 0L, "null", 0);

		_persistence.countByG_U_N_T(0L, 0L, (String)null, 0);
	}

	@Test
	public void testCountByG_U_D_T() throws Exception {
		_persistence.countByG_U_D_T(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean(),
			RandomTestUtil.nextInt());

		_persistence.countByG_U_D_T(0L, 0L, RandomTestUtil.randomBoolean(), 0);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CommerceCart newCommerceCart = addCommerceCart();

		CommerceCart existingCommerceCart = _persistence.findByPrimaryKey(newCommerceCart.getPrimaryKey());

		Assert.assertEquals(existingCommerceCart, newCommerceCart);
	}

	@Test(expected = NoSuchCartException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<CommerceCart> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CommerceCart", "uuid",
			true, "commerceCartId", true, "groupId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "name", true, "defaultCart", true, "type",
			true, "billingAddressId", true, "shippingAddressId", true,
			"commercePaymentMethodId", true, "commerceShippingMethodId", true,
			"shippingOptionName", true, "shippingPrice", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CommerceCart newCommerceCart = addCommerceCart();

		CommerceCart existingCommerceCart = _persistence.fetchByPrimaryKey(newCommerceCart.getPrimaryKey());

		Assert.assertEquals(existingCommerceCart, newCommerceCart);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceCart missingCommerceCart = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCommerceCart);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CommerceCart newCommerceCart1 = addCommerceCart();
		CommerceCart newCommerceCart2 = addCommerceCart();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceCart1.getPrimaryKey());
		primaryKeys.add(newCommerceCart2.getPrimaryKey());

		Map<Serializable, CommerceCart> commerceCarts = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, commerceCarts.size());
		Assert.assertEquals(newCommerceCart1,
			commerceCarts.get(newCommerceCart1.getPrimaryKey()));
		Assert.assertEquals(newCommerceCart2,
			commerceCarts.get(newCommerceCart2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CommerceCart> commerceCarts = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceCarts.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CommerceCart newCommerceCart = addCommerceCart();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceCart.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CommerceCart> commerceCarts = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceCarts.size());
		Assert.assertEquals(newCommerceCart,
			commerceCarts.get(newCommerceCart.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CommerceCart> commerceCarts = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceCarts.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CommerceCart newCommerceCart = addCommerceCart();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceCart.getPrimaryKey());

		Map<Serializable, CommerceCart> commerceCarts = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceCarts.size());
		Assert.assertEquals(newCommerceCart,
			commerceCarts.get(newCommerceCart.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CommerceCartLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CommerceCart>() {
				@Override
				public void performAction(CommerceCart commerceCart) {
					Assert.assertNotNull(commerceCart);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CommerceCart newCommerceCart = addCommerceCart();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceCart.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("commerceCartId",
				newCommerceCart.getCommerceCartId()));

		List<CommerceCart> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CommerceCart existingCommerceCart = result.get(0);

		Assert.assertEquals(existingCommerceCart, newCommerceCart);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceCart.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("commerceCartId",
				RandomTestUtil.nextLong()));

		List<CommerceCart> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CommerceCart newCommerceCart = addCommerceCart();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceCart.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceCartId"));

		Object newCommerceCartId = newCommerceCart.getCommerceCartId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("commerceCartId",
				new Object[] { newCommerceCartId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCommerceCartId = result.get(0);

		Assert.assertEquals(existingCommerceCartId, newCommerceCartId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceCart.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceCartId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("commerceCartId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		CommerceCart newCommerceCart = addCommerceCart();

		_persistence.clearCache();

		CommerceCart existingCommerceCart = _persistence.findByPrimaryKey(newCommerceCart.getPrimaryKey());

		Assert.assertTrue(Objects.equals(existingCommerceCart.getUuid(),
				ReflectionTestUtil.invoke(existingCommerceCart,
					"getOriginalUuid", new Class<?>[0])));
		Assert.assertEquals(Long.valueOf(existingCommerceCart.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingCommerceCart,
				"getOriginalGroupId", new Class<?>[0]));
	}

	protected CommerceCart addCommerceCart() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceCart commerceCart = _persistence.create(pk);

		commerceCart.setUuid(RandomTestUtil.randomString());

		commerceCart.setGroupId(RandomTestUtil.nextLong());

		commerceCart.setCompanyId(RandomTestUtil.nextLong());

		commerceCart.setUserId(RandomTestUtil.nextLong());

		commerceCart.setUserName(RandomTestUtil.randomString());

		commerceCart.setCreateDate(RandomTestUtil.nextDate());

		commerceCart.setModifiedDate(RandomTestUtil.nextDate());

		commerceCart.setName(RandomTestUtil.randomString());

		commerceCart.setDefaultCart(RandomTestUtil.randomBoolean());

		commerceCart.setType(RandomTestUtil.nextInt());

		commerceCart.setBillingAddressId(RandomTestUtil.nextLong());

		commerceCart.setShippingAddressId(RandomTestUtil.nextLong());

		commerceCart.setCommercePaymentMethodId(RandomTestUtil.nextLong());

		commerceCart.setCommerceShippingMethodId(RandomTestUtil.nextLong());

		commerceCart.setShippingOptionName(RandomTestUtil.randomString());

		commerceCart.setShippingPrice(RandomTestUtil.nextDouble());

		_commerceCarts.add(_persistence.update(commerceCart));

		return commerceCart;
	}

	private List<CommerceCart> _commerceCarts = new ArrayList<CommerceCart>();
	private CommerceCartPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}