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

import com.liferay.commerce.cart.exception.NoSuchCCartItemException;
import com.liferay.commerce.cart.model.CCartItem;
import com.liferay.commerce.cart.service.CCartItemLocalServiceUtil;
import com.liferay.commerce.cart.service.persistence.CCartItemPersistence;
import com.liferay.commerce.cart.service.persistence.CCartItemUtil;

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
public class CCartItemPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.commerce.cart.service"));

	@Before
	public void setUp() {
		_persistence = CCartItemUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CCartItem> iterator = _cCartItems.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CCartItem cCartItem = _persistence.create(pk);

		Assert.assertNotNull(cCartItem);

		Assert.assertEquals(cCartItem.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CCartItem newCCartItem = addCCartItem();

		_persistence.remove(newCCartItem);

		CCartItem existingCCartItem = _persistence.fetchByPrimaryKey(newCCartItem.getPrimaryKey());

		Assert.assertNull(existingCCartItem);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCCartItem();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CCartItem newCCartItem = _persistence.create(pk);

		newCCartItem.setUuid(RandomTestUtil.randomString());

		newCCartItem.setGroupId(RandomTestUtil.nextLong());

		newCCartItem.setCompanyId(RandomTestUtil.nextLong());

		newCCartItem.setUserId(RandomTestUtil.nextLong());

		newCCartItem.setUserName(RandomTestUtil.randomString());

		newCCartItem.setCreateDate(RandomTestUtil.nextDate());

		newCCartItem.setModifiedDate(RandomTestUtil.nextDate());

		newCCartItem.setCCartId(RandomTestUtil.nextLong());

		newCCartItem.setCPDefinitionId(RandomTestUtil.nextLong());

		newCCartItem.setCPInstanceId(RandomTestUtil.nextLong());

		newCCartItem.setQuantity(RandomTestUtil.nextInt());

		newCCartItem.setJson(RandomTestUtil.randomString());

		_cCartItems.add(_persistence.update(newCCartItem));

		CCartItem existingCCartItem = _persistence.findByPrimaryKey(newCCartItem.getPrimaryKey());

		Assert.assertEquals(existingCCartItem.getUuid(), newCCartItem.getUuid());
		Assert.assertEquals(existingCCartItem.getCCartItemId(),
			newCCartItem.getCCartItemId());
		Assert.assertEquals(existingCCartItem.getGroupId(),
			newCCartItem.getGroupId());
		Assert.assertEquals(existingCCartItem.getCompanyId(),
			newCCartItem.getCompanyId());
		Assert.assertEquals(existingCCartItem.getUserId(),
			newCCartItem.getUserId());
		Assert.assertEquals(existingCCartItem.getUserName(),
			newCCartItem.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCCartItem.getCreateDate()),
			Time.getShortTimestamp(newCCartItem.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCCartItem.getModifiedDate()),
			Time.getShortTimestamp(newCCartItem.getModifiedDate()));
		Assert.assertEquals(existingCCartItem.getCCartId(),
			newCCartItem.getCCartId());
		Assert.assertEquals(existingCCartItem.getCPDefinitionId(),
			newCCartItem.getCPDefinitionId());
		Assert.assertEquals(existingCCartItem.getCPInstanceId(),
			newCCartItem.getCPInstanceId());
		Assert.assertEquals(existingCCartItem.getQuantity(),
			newCCartItem.getQuantity());
		Assert.assertEquals(existingCCartItem.getJson(), newCCartItem.getJson());
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
	public void testCountByCCartId() throws Exception {
		_persistence.countByCCartId(RandomTestUtil.nextLong());

		_persistence.countByCCartId(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CCartItem newCCartItem = addCCartItem();

		CCartItem existingCCartItem = _persistence.findByPrimaryKey(newCCartItem.getPrimaryKey());

		Assert.assertEquals(existingCCartItem, newCCartItem);
	}

	@Test(expected = NoSuchCCartItemException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<CCartItem> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CCartItem", "uuid", true,
			"CCartItemId", true, "groupId", true, "companyId", true, "userId",
			true, "userName", true, "createDate", true, "modifiedDate", true,
			"CCartId", true, "CPDefinitionId", true, "CPInstanceId", true,
			"quantity", true, "json", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CCartItem newCCartItem = addCCartItem();

		CCartItem existingCCartItem = _persistence.fetchByPrimaryKey(newCCartItem.getPrimaryKey());

		Assert.assertEquals(existingCCartItem, newCCartItem);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CCartItem missingCCartItem = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCCartItem);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CCartItem newCCartItem1 = addCCartItem();
		CCartItem newCCartItem2 = addCCartItem();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCCartItem1.getPrimaryKey());
		primaryKeys.add(newCCartItem2.getPrimaryKey());

		Map<Serializable, CCartItem> cCartItems = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, cCartItems.size());
		Assert.assertEquals(newCCartItem1,
			cCartItems.get(newCCartItem1.getPrimaryKey()));
		Assert.assertEquals(newCCartItem2,
			cCartItems.get(newCCartItem2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CCartItem> cCartItems = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(cCartItems.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CCartItem newCCartItem = addCCartItem();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCCartItem.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CCartItem> cCartItems = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, cCartItems.size());
		Assert.assertEquals(newCCartItem,
			cCartItems.get(newCCartItem.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CCartItem> cCartItems = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(cCartItems.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CCartItem newCCartItem = addCCartItem();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCCartItem.getPrimaryKey());

		Map<Serializable, CCartItem> cCartItems = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, cCartItems.size());
		Assert.assertEquals(newCCartItem,
			cCartItems.get(newCCartItem.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CCartItemLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CCartItem>() {
				@Override
				public void performAction(CCartItem cCartItem) {
					Assert.assertNotNull(cCartItem);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CCartItem newCCartItem = addCCartItem();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CCartItem.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("CCartItemId",
				newCCartItem.getCCartItemId()));

		List<CCartItem> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CCartItem existingCCartItem = result.get(0);

		Assert.assertEquals(existingCCartItem, newCCartItem);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CCartItem.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("CCartItemId",
				RandomTestUtil.nextLong()));

		List<CCartItem> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CCartItem newCCartItem = addCCartItem();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CCartItem.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("CCartItemId"));

		Object newCCartItemId = newCCartItem.getCCartItemId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("CCartItemId",
				new Object[] { newCCartItemId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCCartItemId = result.get(0);

		Assert.assertEquals(existingCCartItemId, newCCartItemId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CCartItem.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("CCartItemId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("CCartItemId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		CCartItem newCCartItem = addCCartItem();

		_persistence.clearCache();

		CCartItem existingCCartItem = _persistence.findByPrimaryKey(newCCartItem.getPrimaryKey());

		Assert.assertTrue(Objects.equals(existingCCartItem.getUuid(),
				ReflectionTestUtil.invoke(existingCCartItem, "getOriginalUuid",
					new Class<?>[0])));
		Assert.assertEquals(Long.valueOf(existingCCartItem.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingCCartItem,
				"getOriginalGroupId", new Class<?>[0]));
	}

	protected CCartItem addCCartItem() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CCartItem cCartItem = _persistence.create(pk);

		cCartItem.setUuid(RandomTestUtil.randomString());

		cCartItem.setGroupId(RandomTestUtil.nextLong());

		cCartItem.setCompanyId(RandomTestUtil.nextLong());

		cCartItem.setUserId(RandomTestUtil.nextLong());

		cCartItem.setUserName(RandomTestUtil.randomString());

		cCartItem.setCreateDate(RandomTestUtil.nextDate());

		cCartItem.setModifiedDate(RandomTestUtil.nextDate());

		cCartItem.setCCartId(RandomTestUtil.nextLong());

		cCartItem.setCPDefinitionId(RandomTestUtil.nextLong());

		cCartItem.setCPInstanceId(RandomTestUtil.nextLong());

		cCartItem.setQuantity(RandomTestUtil.nextInt());

		cCartItem.setJson(RandomTestUtil.randomString());

		_cCartItems.add(_persistence.update(cCartItem));

		return cCartItem;
	}

	private List<CCartItem> _cCartItems = new ArrayList<CCartItem>();
	private CCartItemPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}