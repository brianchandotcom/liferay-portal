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

package com.liferay.commerce.cloud.client.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.commerce.cloud.client.exception.NoSuchCloudOrderForecastSyncException;
import com.liferay.commerce.cloud.client.model.CommerceCloudOrderForecastSync;
import com.liferay.commerce.cloud.client.service.CommerceCloudOrderForecastSyncLocalServiceUtil;
import com.liferay.commerce.cloud.client.service.persistence.CommerceCloudOrderForecastSyncPersistence;
import com.liferay.commerce.cloud.client.service.persistence.CommerceCloudOrderForecastSyncUtil;

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
public class CommerceCloudOrderForecastSyncPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.commerce.cloud.client.service"));

	@Before
	public void setUp() {
		_persistence = CommerceCloudOrderForecastSyncUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CommerceCloudOrderForecastSync> iterator = _commerceCloudOrderForecastSyncs.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceCloudOrderForecastSync commerceCloudOrderForecastSync = _persistence.create(pk);

		Assert.assertNotNull(commerceCloudOrderForecastSync);

		Assert.assertEquals(commerceCloudOrderForecastSync.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CommerceCloudOrderForecastSync newCommerceCloudOrderForecastSync = addCommerceCloudOrderForecastSync();

		_persistence.remove(newCommerceCloudOrderForecastSync);

		CommerceCloudOrderForecastSync existingCommerceCloudOrderForecastSync = _persistence.fetchByPrimaryKey(newCommerceCloudOrderForecastSync.getPrimaryKey());

		Assert.assertNull(existingCommerceCloudOrderForecastSync);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCommerceCloudOrderForecastSync();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceCloudOrderForecastSync newCommerceCloudOrderForecastSync = _persistence.create(pk);

		newCommerceCloudOrderForecastSync.setGroupId(RandomTestUtil.nextLong());

		newCommerceCloudOrderForecastSync.setCompanyId(RandomTestUtil.nextLong());

		newCommerceCloudOrderForecastSync.setCreateDate(RandomTestUtil.nextDate());

		newCommerceCloudOrderForecastSync.setCommerceOrderId(RandomTestUtil.nextLong());

		newCommerceCloudOrderForecastSync.setSyncDate(RandomTestUtil.nextDate());

		_commerceCloudOrderForecastSyncs.add(_persistence.update(
				newCommerceCloudOrderForecastSync));

		CommerceCloudOrderForecastSync existingCommerceCloudOrderForecastSync = _persistence.findByPrimaryKey(newCommerceCloudOrderForecastSync.getPrimaryKey());

		Assert.assertEquals(existingCommerceCloudOrderForecastSync.getCommerceCloudOrderForecastSyncId(),
			newCommerceCloudOrderForecastSync.getCommerceCloudOrderForecastSyncId());
		Assert.assertEquals(existingCommerceCloudOrderForecastSync.getGroupId(),
			newCommerceCloudOrderForecastSync.getGroupId());
		Assert.assertEquals(existingCommerceCloudOrderForecastSync.getCompanyId(),
			newCommerceCloudOrderForecastSync.getCompanyId());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceCloudOrderForecastSync.getCreateDate()),
			Time.getShortTimestamp(
				newCommerceCloudOrderForecastSync.getCreateDate()));
		Assert.assertEquals(existingCommerceCloudOrderForecastSync.getCommerceOrderId(),
			newCommerceCloudOrderForecastSync.getCommerceOrderId());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceCloudOrderForecastSync.getSyncDate()),
			Time.getShortTimestamp(
				newCommerceCloudOrderForecastSync.getSyncDate()));
	}

	@Test
	public void testCountByCommerceOrderId() throws Exception {
		_persistence.countByCommerceOrderId(RandomTestUtil.nextLong());

		_persistence.countByCommerceOrderId(0L);
	}

	@Test
	public void testCountBySyncDate() throws Exception {
		_persistence.countBySyncDate(RandomTestUtil.nextDate());

		_persistence.countBySyncDate(RandomTestUtil.nextDate());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CommerceCloudOrderForecastSync newCommerceCloudOrderForecastSync = addCommerceCloudOrderForecastSync();

		CommerceCloudOrderForecastSync existingCommerceCloudOrderForecastSync = _persistence.findByPrimaryKey(newCommerceCloudOrderForecastSync.getPrimaryKey());

		Assert.assertEquals(existingCommerceCloudOrderForecastSync,
			newCommerceCloudOrderForecastSync);
	}

	@Test(expected = NoSuchCloudOrderForecastSyncException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<CommerceCloudOrderForecastSync> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CCOrderForecastSync",
			"commerceCloudOrderForecastSyncId", true, "groupId", true,
			"companyId", true, "createDate", true, "commerceOrderId", true,
			"syncDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CommerceCloudOrderForecastSync newCommerceCloudOrderForecastSync = addCommerceCloudOrderForecastSync();

		CommerceCloudOrderForecastSync existingCommerceCloudOrderForecastSync = _persistence.fetchByPrimaryKey(newCommerceCloudOrderForecastSync.getPrimaryKey());

		Assert.assertEquals(existingCommerceCloudOrderForecastSync,
			newCommerceCloudOrderForecastSync);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceCloudOrderForecastSync missingCommerceCloudOrderForecastSync = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCommerceCloudOrderForecastSync);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CommerceCloudOrderForecastSync newCommerceCloudOrderForecastSync1 = addCommerceCloudOrderForecastSync();
		CommerceCloudOrderForecastSync newCommerceCloudOrderForecastSync2 = addCommerceCloudOrderForecastSync();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceCloudOrderForecastSync1.getPrimaryKey());
		primaryKeys.add(newCommerceCloudOrderForecastSync2.getPrimaryKey());

		Map<Serializable, CommerceCloudOrderForecastSync> commerceCloudOrderForecastSyncs =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, commerceCloudOrderForecastSyncs.size());
		Assert.assertEquals(newCommerceCloudOrderForecastSync1,
			commerceCloudOrderForecastSyncs.get(
				newCommerceCloudOrderForecastSync1.getPrimaryKey()));
		Assert.assertEquals(newCommerceCloudOrderForecastSync2,
			commerceCloudOrderForecastSyncs.get(
				newCommerceCloudOrderForecastSync2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CommerceCloudOrderForecastSync> commerceCloudOrderForecastSyncs =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceCloudOrderForecastSyncs.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CommerceCloudOrderForecastSync newCommerceCloudOrderForecastSync = addCommerceCloudOrderForecastSync();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceCloudOrderForecastSync.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CommerceCloudOrderForecastSync> commerceCloudOrderForecastSyncs =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceCloudOrderForecastSyncs.size());
		Assert.assertEquals(newCommerceCloudOrderForecastSync,
			commerceCloudOrderForecastSyncs.get(
				newCommerceCloudOrderForecastSync.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CommerceCloudOrderForecastSync> commerceCloudOrderForecastSyncs =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceCloudOrderForecastSyncs.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CommerceCloudOrderForecastSync newCommerceCloudOrderForecastSync = addCommerceCloudOrderForecastSync();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceCloudOrderForecastSync.getPrimaryKey());

		Map<Serializable, CommerceCloudOrderForecastSync> commerceCloudOrderForecastSyncs =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceCloudOrderForecastSyncs.size());
		Assert.assertEquals(newCommerceCloudOrderForecastSync,
			commerceCloudOrderForecastSyncs.get(
				newCommerceCloudOrderForecastSync.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CommerceCloudOrderForecastSyncLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CommerceCloudOrderForecastSync>() {
				@Override
				public void performAction(
					CommerceCloudOrderForecastSync commerceCloudOrderForecastSync) {
					Assert.assertNotNull(commerceCloudOrderForecastSync);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CommerceCloudOrderForecastSync newCommerceCloudOrderForecastSync = addCommerceCloudOrderForecastSync();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceCloudOrderForecastSync.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"commerceCloudOrderForecastSyncId",
				newCommerceCloudOrderForecastSync.getCommerceCloudOrderForecastSyncId()));

		List<CommerceCloudOrderForecastSync> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CommerceCloudOrderForecastSync existingCommerceCloudOrderForecastSync = result.get(0);

		Assert.assertEquals(existingCommerceCloudOrderForecastSync,
			newCommerceCloudOrderForecastSync);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceCloudOrderForecastSync.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"commerceCloudOrderForecastSyncId", RandomTestUtil.nextLong()));

		List<CommerceCloudOrderForecastSync> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CommerceCloudOrderForecastSync newCommerceCloudOrderForecastSync = addCommerceCloudOrderForecastSync();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceCloudOrderForecastSync.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceCloudOrderForecastSyncId"));

		Object newCommerceCloudOrderForecastSyncId = newCommerceCloudOrderForecastSync.getCommerceCloudOrderForecastSyncId();

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"commerceCloudOrderForecastSyncId",
				new Object[] { newCommerceCloudOrderForecastSyncId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCommerceCloudOrderForecastSyncId = result.get(0);

		Assert.assertEquals(existingCommerceCloudOrderForecastSyncId,
			newCommerceCloudOrderForecastSyncId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceCloudOrderForecastSync.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceCloudOrderForecastSyncId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"commerceCloudOrderForecastSyncId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		CommerceCloudOrderForecastSync newCommerceCloudOrderForecastSync = addCommerceCloudOrderForecastSync();

		_persistence.clearCache();

		CommerceCloudOrderForecastSync existingCommerceCloudOrderForecastSync = _persistence.findByPrimaryKey(newCommerceCloudOrderForecastSync.getPrimaryKey());

		Assert.assertEquals(Long.valueOf(
				existingCommerceCloudOrderForecastSync.getCommerceOrderId()),
			ReflectionTestUtil.<Long>invoke(
				existingCommerceCloudOrderForecastSync,
				"getOriginalCommerceOrderId", new Class<?>[0]));
	}

	protected CommerceCloudOrderForecastSync addCommerceCloudOrderForecastSync()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceCloudOrderForecastSync commerceCloudOrderForecastSync = _persistence.create(pk);

		commerceCloudOrderForecastSync.setGroupId(RandomTestUtil.nextLong());

		commerceCloudOrderForecastSync.setCompanyId(RandomTestUtil.nextLong());

		commerceCloudOrderForecastSync.setCreateDate(RandomTestUtil.nextDate());

		commerceCloudOrderForecastSync.setCommerceOrderId(RandomTestUtil.nextLong());

		commerceCloudOrderForecastSync.setSyncDate(RandomTestUtil.nextDate());

		_commerceCloudOrderForecastSyncs.add(_persistence.update(
				commerceCloudOrderForecastSync));

		return commerceCloudOrderForecastSync;
	}

	private List<CommerceCloudOrderForecastSync> _commerceCloudOrderForecastSyncs =
		new ArrayList<CommerceCloudOrderForecastSync>();
	private CommerceCloudOrderForecastSyncPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}