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

package com.liferay.asset.display.page.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.asset.display.page.exception.NoSuchDisplayPageEntryException;
import com.liferay.asset.display.page.model.AssetDisplayPageEntry;
import com.liferay.asset.display.page.service.AssetDisplayPageEntryLocalServiceUtil;
import com.liferay.asset.display.page.service.persistence.AssetDisplayPageEntryPersistence;
import com.liferay.asset.display.page.service.persistence.AssetDisplayPageEntryUtil;

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
public class AssetDisplayPageEntryPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.asset.display.page.service"));

	@Before
	public void setUp() {
		_persistence = AssetDisplayPageEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<AssetDisplayPageEntry> iterator = _assetDisplayPageEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetDisplayPageEntry assetDisplayPageEntry = _persistence.create(pk);

		Assert.assertNotNull(assetDisplayPageEntry);

		Assert.assertEquals(assetDisplayPageEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		AssetDisplayPageEntry newAssetDisplayPageEntry = addAssetDisplayPageEntry();

		_persistence.remove(newAssetDisplayPageEntry);

		AssetDisplayPageEntry existingAssetDisplayPageEntry = _persistence.fetchByPrimaryKey(newAssetDisplayPageEntry.getPrimaryKey());

		Assert.assertNull(existingAssetDisplayPageEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addAssetDisplayPageEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetDisplayPageEntry newAssetDisplayPageEntry = _persistence.create(pk);

		newAssetDisplayPageEntry.setAssetEntryId(RandomTestUtil.nextLong());

		newAssetDisplayPageEntry.setLayoutId(RandomTestUtil.nextLong());

		_assetDisplayPageEntries.add(_persistence.update(
				newAssetDisplayPageEntry));

		AssetDisplayPageEntry existingAssetDisplayPageEntry = _persistence.findByPrimaryKey(newAssetDisplayPageEntry.getPrimaryKey());

		Assert.assertEquals(existingAssetDisplayPageEntry.getAssetDisplayPageEntryId(),
			newAssetDisplayPageEntry.getAssetDisplayPageEntryId());
		Assert.assertEquals(existingAssetDisplayPageEntry.getAssetEntryId(),
			newAssetDisplayPageEntry.getAssetEntryId());
		Assert.assertEquals(existingAssetDisplayPageEntry.getLayoutId(),
			newAssetDisplayPageEntry.getLayoutId());
	}

	@Test
	public void testCountByAssetEntryId() throws Exception {
		_persistence.countByAssetEntryId(RandomTestUtil.nextLong());

		_persistence.countByAssetEntryId(0L);
	}

	@Test
	public void testCountByA_L() throws Exception {
		_persistence.countByA_L(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByA_L(0L, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		AssetDisplayPageEntry newAssetDisplayPageEntry = addAssetDisplayPageEntry();

		AssetDisplayPageEntry existingAssetDisplayPageEntry = _persistence.findByPrimaryKey(newAssetDisplayPageEntry.getPrimaryKey());

		Assert.assertEquals(existingAssetDisplayPageEntry,
			newAssetDisplayPageEntry);
	}

	@Test(expected = NoSuchDisplayPageEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<AssetDisplayPageEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("AssetDisplayPageEntry",
			"assetDisplayPageEntryId", true, "assetEntryId", true, "layoutId",
			true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		AssetDisplayPageEntry newAssetDisplayPageEntry = addAssetDisplayPageEntry();

		AssetDisplayPageEntry existingAssetDisplayPageEntry = _persistence.fetchByPrimaryKey(newAssetDisplayPageEntry.getPrimaryKey());

		Assert.assertEquals(existingAssetDisplayPageEntry,
			newAssetDisplayPageEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetDisplayPageEntry missingAssetDisplayPageEntry = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingAssetDisplayPageEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		AssetDisplayPageEntry newAssetDisplayPageEntry1 = addAssetDisplayPageEntry();
		AssetDisplayPageEntry newAssetDisplayPageEntry2 = addAssetDisplayPageEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetDisplayPageEntry1.getPrimaryKey());
		primaryKeys.add(newAssetDisplayPageEntry2.getPrimaryKey());

		Map<Serializable, AssetDisplayPageEntry> assetDisplayPageEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, assetDisplayPageEntries.size());
		Assert.assertEquals(newAssetDisplayPageEntry1,
			assetDisplayPageEntries.get(
				newAssetDisplayPageEntry1.getPrimaryKey()));
		Assert.assertEquals(newAssetDisplayPageEntry2,
			assetDisplayPageEntries.get(
				newAssetDisplayPageEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, AssetDisplayPageEntry> assetDisplayPageEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(assetDisplayPageEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		AssetDisplayPageEntry newAssetDisplayPageEntry = addAssetDisplayPageEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetDisplayPageEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, AssetDisplayPageEntry> assetDisplayPageEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, assetDisplayPageEntries.size());
		Assert.assertEquals(newAssetDisplayPageEntry,
			assetDisplayPageEntries.get(
				newAssetDisplayPageEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, AssetDisplayPageEntry> assetDisplayPageEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(assetDisplayPageEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		AssetDisplayPageEntry newAssetDisplayPageEntry = addAssetDisplayPageEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetDisplayPageEntry.getPrimaryKey());

		Map<Serializable, AssetDisplayPageEntry> assetDisplayPageEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, assetDisplayPageEntries.size());
		Assert.assertEquals(newAssetDisplayPageEntry,
			assetDisplayPageEntries.get(
				newAssetDisplayPageEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = AssetDisplayPageEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<AssetDisplayPageEntry>() {
				@Override
				public void performAction(
					AssetDisplayPageEntry assetDisplayPageEntry) {
					Assert.assertNotNull(assetDisplayPageEntry);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		AssetDisplayPageEntry newAssetDisplayPageEntry = addAssetDisplayPageEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetDisplayPageEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("assetDisplayPageEntryId",
				newAssetDisplayPageEntry.getAssetDisplayPageEntryId()));

		List<AssetDisplayPageEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		AssetDisplayPageEntry existingAssetDisplayPageEntry = result.get(0);

		Assert.assertEquals(existingAssetDisplayPageEntry,
			newAssetDisplayPageEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetDisplayPageEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("assetDisplayPageEntryId",
				RandomTestUtil.nextLong()));

		List<AssetDisplayPageEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		AssetDisplayPageEntry newAssetDisplayPageEntry = addAssetDisplayPageEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetDisplayPageEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"assetDisplayPageEntryId"));

		Object newAssetDisplayPageEntryId = newAssetDisplayPageEntry.getAssetDisplayPageEntryId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("assetDisplayPageEntryId",
				new Object[] { newAssetDisplayPageEntryId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingAssetDisplayPageEntryId = result.get(0);

		Assert.assertEquals(existingAssetDisplayPageEntryId,
			newAssetDisplayPageEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetDisplayPageEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"assetDisplayPageEntryId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("assetDisplayPageEntryId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		AssetDisplayPageEntry newAssetDisplayPageEntry = addAssetDisplayPageEntry();

		_persistence.clearCache();

		AssetDisplayPageEntry existingAssetDisplayPageEntry = _persistence.findByPrimaryKey(newAssetDisplayPageEntry.getPrimaryKey());

		Assert.assertEquals(Long.valueOf(
				existingAssetDisplayPageEntry.getAssetEntryId()),
			ReflectionTestUtil.<Long>invoke(existingAssetDisplayPageEntry,
				"getOriginalAssetEntryId", new Class<?>[0]));
		Assert.assertEquals(Long.valueOf(
				existingAssetDisplayPageEntry.getLayoutId()),
			ReflectionTestUtil.<Long>invoke(existingAssetDisplayPageEntry,
				"getOriginalLayoutId", new Class<?>[0]));
	}

	protected AssetDisplayPageEntry addAssetDisplayPageEntry()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetDisplayPageEntry assetDisplayPageEntry = _persistence.create(pk);

		assetDisplayPageEntry.setAssetEntryId(RandomTestUtil.nextLong());

		assetDisplayPageEntry.setLayoutId(RandomTestUtil.nextLong());

		_assetDisplayPageEntries.add(_persistence.update(assetDisplayPageEntry));

		return assetDisplayPageEntry;
	}

	private List<AssetDisplayPageEntry> _assetDisplayPageEntries = new ArrayList<AssetDisplayPageEntry>();
	private AssetDisplayPageEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}