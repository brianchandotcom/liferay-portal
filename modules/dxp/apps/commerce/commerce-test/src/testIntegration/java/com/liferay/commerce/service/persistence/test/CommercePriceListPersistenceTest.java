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

import com.liferay.commerce.exception.NoSuchPriceListException;
import com.liferay.commerce.model.CommercePriceList;
import com.liferay.commerce.service.CommercePriceListLocalServiceUtil;
import com.liferay.commerce.service.persistence.CommercePriceListPersistence;
import com.liferay.commerce.service.persistence.CommercePriceListUtil;

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
public class CommercePriceListPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.commerce.service"));

	@Before
	public void setUp() {
		_persistence = CommercePriceListUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CommercePriceList> iterator = _commercePriceLists.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommercePriceList commercePriceList = _persistence.create(pk);

		Assert.assertNotNull(commercePriceList);

		Assert.assertEquals(commercePriceList.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CommercePriceList newCommercePriceList = addCommercePriceList();

		_persistence.remove(newCommercePriceList);

		CommercePriceList existingCommercePriceList = _persistence.fetchByPrimaryKey(newCommercePriceList.getPrimaryKey());

		Assert.assertNull(existingCommercePriceList);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCommercePriceList();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommercePriceList newCommercePriceList = _persistence.create(pk);

		newCommercePriceList.setUuid(RandomTestUtil.randomString());

		newCommercePriceList.setGroupId(RandomTestUtil.nextLong());

		newCommercePriceList.setCompanyId(RandomTestUtil.nextLong());

		newCommercePriceList.setUserId(RandomTestUtil.nextLong());

		newCommercePriceList.setUserName(RandomTestUtil.randomString());

		newCommercePriceList.setCreateDate(RandomTestUtil.nextDate());

		newCommercePriceList.setModifiedDate(RandomTestUtil.nextDate());

		newCommercePriceList.setParentCommercePriceListId(RandomTestUtil.nextLong());

		newCommercePriceList.setCommerceCurrencyId(RandomTestUtil.nextLong());

		newCommercePriceList.setName(RandomTestUtil.randomString());

		newCommercePriceList.setPriority(RandomTestUtil.nextDouble());

		newCommercePriceList.setDisplayDate(RandomTestUtil.nextDate());

		newCommercePriceList.setExpirationDate(RandomTestUtil.nextDate());

		newCommercePriceList.setLastPublishDate(RandomTestUtil.nextDate());

		newCommercePriceList.setStatus(RandomTestUtil.nextInt());

		newCommercePriceList.setStatusByUserId(RandomTestUtil.nextLong());

		newCommercePriceList.setStatusByUserName(RandomTestUtil.randomString());

		newCommercePriceList.setStatusDate(RandomTestUtil.nextDate());

		_commercePriceLists.add(_persistence.update(newCommercePriceList));

		CommercePriceList existingCommercePriceList = _persistence.findByPrimaryKey(newCommercePriceList.getPrimaryKey());

		Assert.assertEquals(existingCommercePriceList.getUuid(),
			newCommercePriceList.getUuid());
		Assert.assertEquals(existingCommercePriceList.getCommercePriceListId(),
			newCommercePriceList.getCommercePriceListId());
		Assert.assertEquals(existingCommercePriceList.getGroupId(),
			newCommercePriceList.getGroupId());
		Assert.assertEquals(existingCommercePriceList.getCompanyId(),
			newCommercePriceList.getCompanyId());
		Assert.assertEquals(existingCommercePriceList.getUserId(),
			newCommercePriceList.getUserId());
		Assert.assertEquals(existingCommercePriceList.getUserName(),
			newCommercePriceList.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommercePriceList.getCreateDate()),
			Time.getShortTimestamp(newCommercePriceList.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommercePriceList.getModifiedDate()),
			Time.getShortTimestamp(newCommercePriceList.getModifiedDate()));
		Assert.assertEquals(existingCommercePriceList.getParentCommercePriceListId(),
			newCommercePriceList.getParentCommercePriceListId());
		Assert.assertEquals(existingCommercePriceList.getCommerceCurrencyId(),
			newCommercePriceList.getCommerceCurrencyId());
		Assert.assertEquals(existingCommercePriceList.getName(),
			newCommercePriceList.getName());
		AssertUtils.assertEquals(existingCommercePriceList.getPriority(),
			newCommercePriceList.getPriority());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommercePriceList.getDisplayDate()),
			Time.getShortTimestamp(newCommercePriceList.getDisplayDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommercePriceList.getExpirationDate()),
			Time.getShortTimestamp(newCommercePriceList.getExpirationDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommercePriceList.getLastPublishDate()),
			Time.getShortTimestamp(newCommercePriceList.getLastPublishDate()));
		Assert.assertEquals(existingCommercePriceList.getStatus(),
			newCommercePriceList.getStatus());
		Assert.assertEquals(existingCommercePriceList.getStatusByUserId(),
			newCommercePriceList.getStatusByUserId());
		Assert.assertEquals(existingCommercePriceList.getStatusByUserName(),
			newCommercePriceList.getStatusByUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommercePriceList.getStatusDate()),
			Time.getShortTimestamp(newCommercePriceList.getStatusDate()));
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
	public void testCountByParentCommercePriceListId()
		throws Exception {
		_persistence.countByParentCommercePriceListId(RandomTestUtil.nextLong());

		_persistence.countByParentCommercePriceListId(0L);
	}

	@Test
	public void testCountByGroupId() throws Exception {
		_persistence.countByGroupId(RandomTestUtil.nextLong());

		_persistence.countByGroupId(0L);
	}

	@Test
	public void testCountByCompanyId() throws Exception {
		_persistence.countByCompanyId(RandomTestUtil.nextLong());

		_persistence.countByCompanyId(0L);
	}

	@Test
	public void testCountByCommerceCurrencyId() throws Exception {
		_persistence.countByCommerceCurrencyId(RandomTestUtil.nextLong());

		_persistence.countByCommerceCurrencyId(0L);
	}

	@Test
	public void testCountByG_S() throws Exception {
		_persistence.countByG_S(RandomTestUtil.nextLong(),
			RandomTestUtil.nextInt());

		_persistence.countByG_S(0L, 0);
	}

	@Test
	public void testCountByG_NotS() throws Exception {
		_persistence.countByG_NotS(RandomTestUtil.nextLong(),
			RandomTestUtil.nextInt());

		_persistence.countByG_NotS(0L, 0);
	}

	@Test
	public void testCountByLtD_S() throws Exception {
		_persistence.countByLtD_S(RandomTestUtil.nextDate(),
			RandomTestUtil.nextInt());

		_persistence.countByLtD_S(RandomTestUtil.nextDate(), 0);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CommercePriceList newCommercePriceList = addCommercePriceList();

		CommercePriceList existingCommercePriceList = _persistence.findByPrimaryKey(newCommercePriceList.getPrimaryKey());

		Assert.assertEquals(existingCommercePriceList, newCommercePriceList);
	}

	@Test(expected = NoSuchPriceListException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<CommercePriceList> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CommercePriceList", "uuid",
			true, "commercePriceListId", true, "groupId", true, "companyId",
			true, "userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "parentCommercePriceListId", true,
			"commerceCurrencyId", true, "name", true, "priority", true,
			"displayDate", true, "expirationDate", true, "lastPublishDate",
			true, "status", true, "statusByUserId", true, "statusByUserName",
			true, "statusDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CommercePriceList newCommercePriceList = addCommercePriceList();

		CommercePriceList existingCommercePriceList = _persistence.fetchByPrimaryKey(newCommercePriceList.getPrimaryKey());

		Assert.assertEquals(existingCommercePriceList, newCommercePriceList);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommercePriceList missingCommercePriceList = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCommercePriceList);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CommercePriceList newCommercePriceList1 = addCommercePriceList();
		CommercePriceList newCommercePriceList2 = addCommercePriceList();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommercePriceList1.getPrimaryKey());
		primaryKeys.add(newCommercePriceList2.getPrimaryKey());

		Map<Serializable, CommercePriceList> commercePriceLists = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, commercePriceLists.size());
		Assert.assertEquals(newCommercePriceList1,
			commercePriceLists.get(newCommercePriceList1.getPrimaryKey()));
		Assert.assertEquals(newCommercePriceList2,
			commercePriceLists.get(newCommercePriceList2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CommercePriceList> commercePriceLists = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commercePriceLists.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CommercePriceList newCommercePriceList = addCommercePriceList();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommercePriceList.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CommercePriceList> commercePriceLists = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commercePriceLists.size());
		Assert.assertEquals(newCommercePriceList,
			commercePriceLists.get(newCommercePriceList.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CommercePriceList> commercePriceLists = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commercePriceLists.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CommercePriceList newCommercePriceList = addCommercePriceList();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommercePriceList.getPrimaryKey());

		Map<Serializable, CommercePriceList> commercePriceLists = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commercePriceLists.size());
		Assert.assertEquals(newCommercePriceList,
			commercePriceLists.get(newCommercePriceList.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CommercePriceListLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CommercePriceList>() {
				@Override
				public void performAction(CommercePriceList commercePriceList) {
					Assert.assertNotNull(commercePriceList);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CommercePriceList newCommercePriceList = addCommercePriceList();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommercePriceList.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("commercePriceListId",
				newCommercePriceList.getCommercePriceListId()));

		List<CommercePriceList> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CommercePriceList existingCommercePriceList = result.get(0);

		Assert.assertEquals(existingCommercePriceList, newCommercePriceList);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommercePriceList.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("commercePriceListId",
				RandomTestUtil.nextLong()));

		List<CommercePriceList> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CommercePriceList newCommercePriceList = addCommercePriceList();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommercePriceList.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commercePriceListId"));

		Object newCommercePriceListId = newCommercePriceList.getCommercePriceListId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("commercePriceListId",
				new Object[] { newCommercePriceListId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCommercePriceListId = result.get(0);

		Assert.assertEquals(existingCommercePriceListId, newCommercePriceListId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommercePriceList.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commercePriceListId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("commercePriceListId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		CommercePriceList newCommercePriceList = addCommercePriceList();

		_persistence.clearCache();

		CommercePriceList existingCommercePriceList = _persistence.findByPrimaryKey(newCommercePriceList.getPrimaryKey());

		Assert.assertTrue(Objects.equals(existingCommercePriceList.getUuid(),
				ReflectionTestUtil.invoke(existingCommercePriceList,
					"getOriginalUuid", new Class<?>[0])));
		Assert.assertEquals(Long.valueOf(existingCommercePriceList.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingCommercePriceList,
				"getOriginalGroupId", new Class<?>[0]));

		Assert.assertEquals(Long.valueOf(
				existingCommercePriceList.getParentCommercePriceListId()),
			ReflectionTestUtil.<Long>invoke(existingCommercePriceList,
				"getOriginalParentCommercePriceListId", new Class<?>[0]));
	}

	protected CommercePriceList addCommercePriceList()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommercePriceList commercePriceList = _persistence.create(pk);

		commercePriceList.setUuid(RandomTestUtil.randomString());

		commercePriceList.setGroupId(RandomTestUtil.nextLong());

		commercePriceList.setCompanyId(RandomTestUtil.nextLong());

		commercePriceList.setUserId(RandomTestUtil.nextLong());

		commercePriceList.setUserName(RandomTestUtil.randomString());

		commercePriceList.setCreateDate(RandomTestUtil.nextDate());

		commercePriceList.setModifiedDate(RandomTestUtil.nextDate());

		commercePriceList.setParentCommercePriceListId(RandomTestUtil.nextLong());

		commercePriceList.setCommerceCurrencyId(RandomTestUtil.nextLong());

		commercePriceList.setName(RandomTestUtil.randomString());

		commercePriceList.setPriority(RandomTestUtil.nextDouble());

		commercePriceList.setDisplayDate(RandomTestUtil.nextDate());

		commercePriceList.setExpirationDate(RandomTestUtil.nextDate());

		commercePriceList.setLastPublishDate(RandomTestUtil.nextDate());

		commercePriceList.setStatus(RandomTestUtil.nextInt());

		commercePriceList.setStatusByUserId(RandomTestUtil.nextLong());

		commercePriceList.setStatusByUserName(RandomTestUtil.randomString());

		commercePriceList.setStatusDate(RandomTestUtil.nextDate());

		_commercePriceLists.add(_persistence.update(commercePriceList));

		return commercePriceList;
	}

	private List<CommercePriceList> _commercePriceLists = new ArrayList<CommercePriceList>();
	private CommercePriceListPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}