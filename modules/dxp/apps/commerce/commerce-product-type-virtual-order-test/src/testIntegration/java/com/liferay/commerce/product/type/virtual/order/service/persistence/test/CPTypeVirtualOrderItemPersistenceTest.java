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

package com.liferay.commerce.product.type.virtual.order.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.commerce.product.type.virtual.order.exception.NoSuchCPTypeVirtualOrderItemException;
import com.liferay.commerce.product.type.virtual.order.model.CPTypeVirtualOrderItem;
import com.liferay.commerce.product.type.virtual.order.service.CPTypeVirtualOrderItemLocalServiceUtil;
import com.liferay.commerce.product.type.virtual.order.service.persistence.CPTypeVirtualOrderItemPersistence;
import com.liferay.commerce.product.type.virtual.order.service.persistence.CPTypeVirtualOrderItemUtil;

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
public class CPTypeVirtualOrderItemPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.commerce.product.type.virtual.order.service"));

	@Before
	public void setUp() {
		_persistence = CPTypeVirtualOrderItemUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CPTypeVirtualOrderItem> iterator = _cpTypeVirtualOrderItems.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPTypeVirtualOrderItem cpTypeVirtualOrderItem = _persistence.create(pk);

		Assert.assertNotNull(cpTypeVirtualOrderItem);

		Assert.assertEquals(cpTypeVirtualOrderItem.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CPTypeVirtualOrderItem newCPTypeVirtualOrderItem = addCPTypeVirtualOrderItem();

		_persistence.remove(newCPTypeVirtualOrderItem);

		CPTypeVirtualOrderItem existingCPTypeVirtualOrderItem = _persistence.fetchByPrimaryKey(newCPTypeVirtualOrderItem.getPrimaryKey());

		Assert.assertNull(existingCPTypeVirtualOrderItem);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCPTypeVirtualOrderItem();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPTypeVirtualOrderItem newCPTypeVirtualOrderItem = _persistence.create(pk);

		newCPTypeVirtualOrderItem.setUuid(RandomTestUtil.randomString());

		newCPTypeVirtualOrderItem.setGroupId(RandomTestUtil.nextLong());

		newCPTypeVirtualOrderItem.setCompanyId(RandomTestUtil.nextLong());

		newCPTypeVirtualOrderItem.setUserId(RandomTestUtil.nextLong());

		newCPTypeVirtualOrderItem.setUserName(RandomTestUtil.randomString());

		newCPTypeVirtualOrderItem.setCreateDate(RandomTestUtil.nextDate());

		newCPTypeVirtualOrderItem.setModifiedDate(RandomTestUtil.nextDate());

		newCPTypeVirtualOrderItem.setCommerceOrderItemId(RandomTestUtil.nextLong());

		newCPTypeVirtualOrderItem.setFileEntryId(RandomTestUtil.nextLong());

		newCPTypeVirtualOrderItem.setUrl(RandomTestUtil.randomString());

		newCPTypeVirtualOrderItem.setActivationStatus(RandomTestUtil.randomString());

		newCPTypeVirtualOrderItem.setDuration(RandomTestUtil.nextLong());

		newCPTypeVirtualOrderItem.setUsages(RandomTestUtil.nextInt());

		newCPTypeVirtualOrderItem.setMaxUsages(RandomTestUtil.nextInt());

		newCPTypeVirtualOrderItem.setStartDate(RandomTestUtil.nextDate());

		newCPTypeVirtualOrderItem.setEndDate(RandomTestUtil.nextDate());

		_cpTypeVirtualOrderItems.add(_persistence.update(
				newCPTypeVirtualOrderItem));

		CPTypeVirtualOrderItem existingCPTypeVirtualOrderItem = _persistence.findByPrimaryKey(newCPTypeVirtualOrderItem.getPrimaryKey());

		Assert.assertEquals(existingCPTypeVirtualOrderItem.getUuid(),
			newCPTypeVirtualOrderItem.getUuid());
		Assert.assertEquals(existingCPTypeVirtualOrderItem.getCPTypeVirtualOrderItemId(),
			newCPTypeVirtualOrderItem.getCPTypeVirtualOrderItemId());
		Assert.assertEquals(existingCPTypeVirtualOrderItem.getGroupId(),
			newCPTypeVirtualOrderItem.getGroupId());
		Assert.assertEquals(existingCPTypeVirtualOrderItem.getCompanyId(),
			newCPTypeVirtualOrderItem.getCompanyId());
		Assert.assertEquals(existingCPTypeVirtualOrderItem.getUserId(),
			newCPTypeVirtualOrderItem.getUserId());
		Assert.assertEquals(existingCPTypeVirtualOrderItem.getUserName(),
			newCPTypeVirtualOrderItem.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCPTypeVirtualOrderItem.getCreateDate()),
			Time.getShortTimestamp(newCPTypeVirtualOrderItem.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCPTypeVirtualOrderItem.getModifiedDate()),
			Time.getShortTimestamp(newCPTypeVirtualOrderItem.getModifiedDate()));
		Assert.assertEquals(existingCPTypeVirtualOrderItem.getCommerceOrderItemId(),
			newCPTypeVirtualOrderItem.getCommerceOrderItemId());
		Assert.assertEquals(existingCPTypeVirtualOrderItem.getFileEntryId(),
			newCPTypeVirtualOrderItem.getFileEntryId());
		Assert.assertEquals(existingCPTypeVirtualOrderItem.getUrl(),
			newCPTypeVirtualOrderItem.getUrl());
		Assert.assertEquals(existingCPTypeVirtualOrderItem.getActivationStatus(),
			newCPTypeVirtualOrderItem.getActivationStatus());
		Assert.assertEquals(existingCPTypeVirtualOrderItem.getDuration(),
			newCPTypeVirtualOrderItem.getDuration());
		Assert.assertEquals(existingCPTypeVirtualOrderItem.getUsages(),
			newCPTypeVirtualOrderItem.getUsages());
		Assert.assertEquals(existingCPTypeVirtualOrderItem.getMaxUsages(),
			newCPTypeVirtualOrderItem.getMaxUsages());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCPTypeVirtualOrderItem.getStartDate()),
			Time.getShortTimestamp(newCPTypeVirtualOrderItem.getStartDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCPTypeVirtualOrderItem.getEndDate()),
			Time.getShortTimestamp(newCPTypeVirtualOrderItem.getEndDate()));
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
	public void testCountByCommerceOrderItemId() throws Exception {
		_persistence.countByCommerceOrderItemId(RandomTestUtil.nextLong());

		_persistence.countByCommerceOrderItemId(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CPTypeVirtualOrderItem newCPTypeVirtualOrderItem = addCPTypeVirtualOrderItem();

		CPTypeVirtualOrderItem existingCPTypeVirtualOrderItem = _persistence.findByPrimaryKey(newCPTypeVirtualOrderItem.getPrimaryKey());

		Assert.assertEquals(existingCPTypeVirtualOrderItem,
			newCPTypeVirtualOrderItem);
	}

	@Test(expected = NoSuchCPTypeVirtualOrderItemException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<CPTypeVirtualOrderItem> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CPTypeVirtualOrderItem",
			"uuid", true, "CPTypeVirtualOrderItemId", true, "groupId", true,
			"companyId", true, "userId", true, "userName", true, "createDate",
			true, "modifiedDate", true, "commerceOrderItemId", true,
			"fileEntryId", true, "url", true, "activationStatus", true,
			"duration", true, "usages", true, "maxUsages", true, "startDate",
			true, "endDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CPTypeVirtualOrderItem newCPTypeVirtualOrderItem = addCPTypeVirtualOrderItem();

		CPTypeVirtualOrderItem existingCPTypeVirtualOrderItem = _persistence.fetchByPrimaryKey(newCPTypeVirtualOrderItem.getPrimaryKey());

		Assert.assertEquals(existingCPTypeVirtualOrderItem,
			newCPTypeVirtualOrderItem);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPTypeVirtualOrderItem missingCPTypeVirtualOrderItem = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCPTypeVirtualOrderItem);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CPTypeVirtualOrderItem newCPTypeVirtualOrderItem1 = addCPTypeVirtualOrderItem();
		CPTypeVirtualOrderItem newCPTypeVirtualOrderItem2 = addCPTypeVirtualOrderItem();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCPTypeVirtualOrderItem1.getPrimaryKey());
		primaryKeys.add(newCPTypeVirtualOrderItem2.getPrimaryKey());

		Map<Serializable, CPTypeVirtualOrderItem> cpTypeVirtualOrderItems = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, cpTypeVirtualOrderItems.size());
		Assert.assertEquals(newCPTypeVirtualOrderItem1,
			cpTypeVirtualOrderItems.get(
				newCPTypeVirtualOrderItem1.getPrimaryKey()));
		Assert.assertEquals(newCPTypeVirtualOrderItem2,
			cpTypeVirtualOrderItems.get(
				newCPTypeVirtualOrderItem2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CPTypeVirtualOrderItem> cpTypeVirtualOrderItems = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(cpTypeVirtualOrderItems.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CPTypeVirtualOrderItem newCPTypeVirtualOrderItem = addCPTypeVirtualOrderItem();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCPTypeVirtualOrderItem.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CPTypeVirtualOrderItem> cpTypeVirtualOrderItems = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, cpTypeVirtualOrderItems.size());
		Assert.assertEquals(newCPTypeVirtualOrderItem,
			cpTypeVirtualOrderItems.get(
				newCPTypeVirtualOrderItem.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CPTypeVirtualOrderItem> cpTypeVirtualOrderItems = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(cpTypeVirtualOrderItems.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CPTypeVirtualOrderItem newCPTypeVirtualOrderItem = addCPTypeVirtualOrderItem();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCPTypeVirtualOrderItem.getPrimaryKey());

		Map<Serializable, CPTypeVirtualOrderItem> cpTypeVirtualOrderItems = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, cpTypeVirtualOrderItems.size());
		Assert.assertEquals(newCPTypeVirtualOrderItem,
			cpTypeVirtualOrderItems.get(
				newCPTypeVirtualOrderItem.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CPTypeVirtualOrderItemLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CPTypeVirtualOrderItem>() {
				@Override
				public void performAction(
					CPTypeVirtualOrderItem cpTypeVirtualOrderItem) {
					Assert.assertNotNull(cpTypeVirtualOrderItem);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CPTypeVirtualOrderItem newCPTypeVirtualOrderItem = addCPTypeVirtualOrderItem();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CPTypeVirtualOrderItem.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"CPTypeVirtualOrderItemId",
				newCPTypeVirtualOrderItem.getCPTypeVirtualOrderItemId()));

		List<CPTypeVirtualOrderItem> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CPTypeVirtualOrderItem existingCPTypeVirtualOrderItem = result.get(0);

		Assert.assertEquals(existingCPTypeVirtualOrderItem,
			newCPTypeVirtualOrderItem);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CPTypeVirtualOrderItem.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"CPTypeVirtualOrderItemId", RandomTestUtil.nextLong()));

		List<CPTypeVirtualOrderItem> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CPTypeVirtualOrderItem newCPTypeVirtualOrderItem = addCPTypeVirtualOrderItem();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CPTypeVirtualOrderItem.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"CPTypeVirtualOrderItemId"));

		Object newCPTypeVirtualOrderItemId = newCPTypeVirtualOrderItem.getCPTypeVirtualOrderItemId();

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"CPTypeVirtualOrderItemId",
				new Object[] { newCPTypeVirtualOrderItemId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCPTypeVirtualOrderItemId = result.get(0);

		Assert.assertEquals(existingCPTypeVirtualOrderItemId,
			newCPTypeVirtualOrderItemId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CPTypeVirtualOrderItem.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"CPTypeVirtualOrderItemId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"CPTypeVirtualOrderItemId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		CPTypeVirtualOrderItem newCPTypeVirtualOrderItem = addCPTypeVirtualOrderItem();

		_persistence.clearCache();

		CPTypeVirtualOrderItem existingCPTypeVirtualOrderItem = _persistence.findByPrimaryKey(newCPTypeVirtualOrderItem.getPrimaryKey());

		Assert.assertTrue(Objects.equals(
				existingCPTypeVirtualOrderItem.getUuid(),
				ReflectionTestUtil.invoke(existingCPTypeVirtualOrderItem,
					"getOriginalUuid", new Class<?>[0])));
		Assert.assertEquals(Long.valueOf(
				existingCPTypeVirtualOrderItem.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingCPTypeVirtualOrderItem,
				"getOriginalGroupId", new Class<?>[0]));

		Assert.assertEquals(Long.valueOf(
				existingCPTypeVirtualOrderItem.getCommerceOrderItemId()),
			ReflectionTestUtil.<Long>invoke(existingCPTypeVirtualOrderItem,
				"getOriginalCommerceOrderItemId", new Class<?>[0]));
	}

	protected CPTypeVirtualOrderItem addCPTypeVirtualOrderItem()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPTypeVirtualOrderItem cpTypeVirtualOrderItem = _persistence.create(pk);

		cpTypeVirtualOrderItem.setUuid(RandomTestUtil.randomString());

		cpTypeVirtualOrderItem.setGroupId(RandomTestUtil.nextLong());

		cpTypeVirtualOrderItem.setCompanyId(RandomTestUtil.nextLong());

		cpTypeVirtualOrderItem.setUserId(RandomTestUtil.nextLong());

		cpTypeVirtualOrderItem.setUserName(RandomTestUtil.randomString());

		cpTypeVirtualOrderItem.setCreateDate(RandomTestUtil.nextDate());

		cpTypeVirtualOrderItem.setModifiedDate(RandomTestUtil.nextDate());

		cpTypeVirtualOrderItem.setCommerceOrderItemId(RandomTestUtil.nextLong());

		cpTypeVirtualOrderItem.setFileEntryId(RandomTestUtil.nextLong());

		cpTypeVirtualOrderItem.setUrl(RandomTestUtil.randomString());

		cpTypeVirtualOrderItem.setActivationStatus(RandomTestUtil.randomString());

		cpTypeVirtualOrderItem.setDuration(RandomTestUtil.nextLong());

		cpTypeVirtualOrderItem.setUsages(RandomTestUtil.nextInt());

		cpTypeVirtualOrderItem.setMaxUsages(RandomTestUtil.nextInt());

		cpTypeVirtualOrderItem.setStartDate(RandomTestUtil.nextDate());

		cpTypeVirtualOrderItem.setEndDate(RandomTestUtil.nextDate());

		_cpTypeVirtualOrderItems.add(_persistence.update(cpTypeVirtualOrderItem));

		return cpTypeVirtualOrderItem;
	}

	private List<CPTypeVirtualOrderItem> _cpTypeVirtualOrderItems = new ArrayList<CPTypeVirtualOrderItem>();
	private CPTypeVirtualOrderItemPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}