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

package com.liferay.commerce.cart.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.commerce.cart.exception.NoSuchCCartException;
import com.liferay.commerce.cart.model.CCart;
import com.liferay.commerce.cart.service.CCartLocalServiceUtil;
import com.liferay.commerce.cart.service.persistence.CCartPersistence;
import com.liferay.commerce.cart.service.persistence.CCartUtil;

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
public class CCartPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.commerce.cart.service"));

	@Before
	public void setUp() {
		_persistence = CCartUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CCart> iterator = _cCarts.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CCart cCart = _persistence.create(pk);

		Assert.assertNotNull(cCart);

		Assert.assertEquals(cCart.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CCart newCCart = addCCart();

		_persistence.remove(newCCart);

		CCart existingCCart = _persistence.fetchByPrimaryKey(newCCart.getPrimaryKey());

		Assert.assertNull(existingCCart);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCCart();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CCart newCCart = _persistence.create(pk);

		newCCart.setUuid(RandomTestUtil.randomString());

		newCCart.setGroupId(RandomTestUtil.nextLong());

		newCCart.setCompanyId(RandomTestUtil.nextLong());

		newCCart.setUserId(RandomTestUtil.nextLong());

		newCCart.setUserName(RandomTestUtil.randomString());

		newCCart.setCreateDate(RandomTestUtil.nextDate());

		newCCart.setModifiedDate(RandomTestUtil.nextDate());

		newCCart.setCartUserId(RandomTestUtil.nextLong());

		newCCart.setTitle(RandomTestUtil.randomString());

		newCCart.setType(RandomTestUtil.nextInt());

		_cCarts.add(_persistence.update(newCCart));

		CCart existingCCart = _persistence.findByPrimaryKey(newCCart.getPrimaryKey());

		Assert.assertEquals(existingCCart.getUuid(), newCCart.getUuid());
		Assert.assertEquals(existingCCart.getCCartId(), newCCart.getCCartId());
		Assert.assertEquals(existingCCart.getGroupId(), newCCart.getGroupId());
		Assert.assertEquals(existingCCart.getCompanyId(),
			newCCart.getCompanyId());
		Assert.assertEquals(existingCCart.getUserId(), newCCart.getUserId());
		Assert.assertEquals(existingCCart.getUserName(), newCCart.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCCart.getCreateDate()),
			Time.getShortTimestamp(newCCart.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCCart.getModifiedDate()),
			Time.getShortTimestamp(newCCart.getModifiedDate()));
		Assert.assertEquals(existingCCart.getCartUserId(),
			newCCart.getCartUserId());
		Assert.assertEquals(existingCCart.getTitle(), newCCart.getTitle());
		Assert.assertEquals(existingCCart.getType(), newCCart.getType());
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
	public void testCountByType() throws Exception {
		_persistence.countByType(RandomTestUtil.nextInt());

		_persistence.countByType(0);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CCart newCCart = addCCart();

		CCart existingCCart = _persistence.findByPrimaryKey(newCCart.getPrimaryKey());

		Assert.assertEquals(existingCCart, newCCart);
	}

	@Test(expected = NoSuchCCartException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<CCart> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CCart", "uuid", true,
			"CCartId", true, "groupId", true, "companyId", true, "userId",
			true, "userName", true, "createDate", true, "modifiedDate", true,
			"cartUserId", true, "title", true, "type", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CCart newCCart = addCCart();

		CCart existingCCart = _persistence.fetchByPrimaryKey(newCCart.getPrimaryKey());

		Assert.assertEquals(existingCCart, newCCart);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CCart missingCCart = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCCart);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CCart newCCart1 = addCCart();
		CCart newCCart2 = addCCart();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCCart1.getPrimaryKey());
		primaryKeys.add(newCCart2.getPrimaryKey());

		Map<Serializable, CCart> cCarts = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, cCarts.size());
		Assert.assertEquals(newCCart1, cCarts.get(newCCart1.getPrimaryKey()));
		Assert.assertEquals(newCCart2, cCarts.get(newCCart2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CCart> cCarts = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(cCarts.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CCart newCCart = addCCart();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCCart.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CCart> cCarts = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, cCarts.size());
		Assert.assertEquals(newCCart, cCarts.get(newCCart.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CCart> cCarts = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(cCarts.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CCart newCCart = addCCart();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCCart.getPrimaryKey());

		Map<Serializable, CCart> cCarts = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, cCarts.size());
		Assert.assertEquals(newCCart, cCarts.get(newCCart.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CCartLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CCart>() {
				@Override
				public void performAction(CCart cCart) {
					Assert.assertNotNull(cCart);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CCart newCCart = addCCart();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CCart.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("CCartId",
				newCCart.getCCartId()));

		List<CCart> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CCart existingCCart = result.get(0);

		Assert.assertEquals(existingCCart, newCCart);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CCart.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("CCartId",
				RandomTestUtil.nextLong()));

		List<CCart> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CCart newCCart = addCCart();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CCart.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("CCartId"));

		Object newCCartId = newCCart.getCCartId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("CCartId",
				new Object[] { newCCartId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCCartId = result.get(0);

		Assert.assertEquals(existingCCartId, newCCartId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CCart.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("CCartId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("CCartId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		CCart newCCart = addCCart();

		_persistence.clearCache();

		CCart existingCCart = _persistence.findByPrimaryKey(newCCart.getPrimaryKey());

		Assert.assertTrue(Objects.equals(existingCCart.getUuid(),
				ReflectionTestUtil.invoke(existingCCart, "getOriginalUuid",
					new Class<?>[0])));
		Assert.assertEquals(Long.valueOf(existingCCart.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingCCart,
				"getOriginalGroupId", new Class<?>[0]));
	}

	protected CCart addCCart() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CCart cCart = _persistence.create(pk);

		cCart.setUuid(RandomTestUtil.randomString());

		cCart.setGroupId(RandomTestUtil.nextLong());

		cCart.setCompanyId(RandomTestUtil.nextLong());

		cCart.setUserId(RandomTestUtil.nextLong());

		cCart.setUserName(RandomTestUtil.randomString());

		cCart.setCreateDate(RandomTestUtil.nextDate());

		cCart.setModifiedDate(RandomTestUtil.nextDate());

		cCart.setCartUserId(RandomTestUtil.nextLong());

		cCart.setTitle(RandomTestUtil.randomString());

		cCart.setType(RandomTestUtil.nextInt());

		_cCarts.add(_persistence.update(cCart));

		return cCart;
	}

	private List<CCart> _cCarts = new ArrayList<CCart>();
	private CCartPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}