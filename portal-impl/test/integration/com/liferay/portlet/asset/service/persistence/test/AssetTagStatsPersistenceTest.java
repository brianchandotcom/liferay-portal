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

package com.liferay.portlet.asset.service.persistence.test;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;

import com.liferay.portlet.asset.NoSuchTagStatsException;
import com.liferay.portlet.asset.model.AssetTagStats;
import com.liferay.portlet.asset.service.AssetTagStatsLocalServiceUtil;
import com.liferay.portlet.asset.service.persistence.AssetTagStatsPersistence;
import com.liferay.portlet.asset.service.persistence.AssetTagStatsUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

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
public class AssetTagStatsPersistenceTest {
	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = AssetTagStatsUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<AssetTagStats> iterator = _assetTagStatses.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetTagStats assetTagStats = _persistence.create(pk);

		Assert.assertNotNull(assetTagStats);

		Assert.assertEquals(assetTagStats.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		AssetTagStats newAssetTagStats = addAssetTagStats();

		_persistence.remove(newAssetTagStats);

		AssetTagStats existingAssetTagStats = _persistence.fetchByPrimaryKey(newAssetTagStats.getPrimaryKey());

		Assert.assertNull(existingAssetTagStats);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addAssetTagStats();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetTagStats newAssetTagStats = _persistence.create(pk);

		newAssetTagStats.setTagId(RandomTestUtil.nextLong());

		newAssetTagStats.setClassNameId(RandomTestUtil.nextLong());

		newAssetTagStats.setAssetCount(RandomTestUtil.nextInt());

		_assetTagStatses.add(_persistence.update(newAssetTagStats));

		AssetTagStats existingAssetTagStats = _persistence.findByPrimaryKey(newAssetTagStats.getPrimaryKey());

		Assert.assertEquals(existingAssetTagStats.getTagStatsId(),
			newAssetTagStats.getTagStatsId());
		Assert.assertEquals(existingAssetTagStats.getTagId(),
			newAssetTagStats.getTagId());
		Assert.assertEquals(existingAssetTagStats.getClassNameId(),
			newAssetTagStats.getClassNameId());
		Assert.assertEquals(existingAssetTagStats.getAssetCount(),
			newAssetTagStats.getAssetCount());
	}

	@Test
	public void testCountByTagId() throws Exception {
		_persistence.countByTagId(RandomTestUtil.nextLong());

		_persistence.countByTagId(0L);
	}

	@Test
	public void testCountByClassNameId() throws Exception {
		_persistence.countByClassNameId(RandomTestUtil.nextLong());

		_persistence.countByClassNameId(0L);
	}

	@Test
	public void testCountByT_C() throws Exception {
		_persistence.countByT_C(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByT_C(0L, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		AssetTagStats newAssetTagStats = addAssetTagStats();

		AssetTagStats existingAssetTagStats = _persistence.findByPrimaryKey(newAssetTagStats.getPrimaryKey());

		Assert.assertEquals(existingAssetTagStats, newAssetTagStats);
	}

	@Test(expected = NoSuchTagStatsException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<AssetTagStats> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("AssetTagStats",
			"tagStatsId", true, "tagId", true, "classNameId", true,
			"assetCount", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		AssetTagStats newAssetTagStats = addAssetTagStats();

		AssetTagStats existingAssetTagStats = _persistence.fetchByPrimaryKey(newAssetTagStats.getPrimaryKey());

		Assert.assertEquals(existingAssetTagStats, newAssetTagStats);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetTagStats missingAssetTagStats = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingAssetTagStats);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		AssetTagStats newAssetTagStats1 = addAssetTagStats();
		AssetTagStats newAssetTagStats2 = addAssetTagStats();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetTagStats1.getPrimaryKey());
		primaryKeys.add(newAssetTagStats2.getPrimaryKey());

		Map<Serializable, AssetTagStats> assetTagStatses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, assetTagStatses.size());
		Assert.assertEquals(newAssetTagStats1,
			assetTagStatses.get(newAssetTagStats1.getPrimaryKey()));
		Assert.assertEquals(newAssetTagStats2,
			assetTagStatses.get(newAssetTagStats2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, AssetTagStats> assetTagStatses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(assetTagStatses.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		AssetTagStats newAssetTagStats = addAssetTagStats();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetTagStats.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, AssetTagStats> assetTagStatses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, assetTagStatses.size());
		Assert.assertEquals(newAssetTagStats,
			assetTagStatses.get(newAssetTagStats.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, AssetTagStats> assetTagStatses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(assetTagStatses.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		AssetTagStats newAssetTagStats = addAssetTagStats();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetTagStats.getPrimaryKey());

		Map<Serializable, AssetTagStats> assetTagStatses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, assetTagStatses.size());
		Assert.assertEquals(newAssetTagStats,
			assetTagStatses.get(newAssetTagStats.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = AssetTagStatsLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					AssetTagStats assetTagStats = (AssetTagStats)object;

					Assert.assertNotNull(assetTagStats);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		AssetTagStats newAssetTagStats = addAssetTagStats();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetTagStats.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("tagStatsId",
				newAssetTagStats.getTagStatsId()));

		List<AssetTagStats> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		AssetTagStats existingAssetTagStats = result.get(0);

		Assert.assertEquals(existingAssetTagStats, newAssetTagStats);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetTagStats.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("tagStatsId",
				RandomTestUtil.nextLong()));

		List<AssetTagStats> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		AssetTagStats newAssetTagStats = addAssetTagStats();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetTagStats.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("tagStatsId"));

		Object newTagStatsId = newAssetTagStats.getTagStatsId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("tagStatsId",
				new Object[] { newTagStatsId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingTagStatsId = result.get(0);

		Assert.assertEquals(existingTagStatsId, newTagStatsId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetTagStats.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("tagStatsId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("tagStatsId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		AssetTagStats newAssetTagStats = addAssetTagStats();

		_persistence.clearCache();

		AssetTagStats existingAssetTagStats = _persistence.findByPrimaryKey(newAssetTagStats.getPrimaryKey());

		Assert.assertEquals(existingAssetTagStats.getTagId(),
			ReflectionTestUtil.invoke(existingAssetTagStats,
				"getOriginalTagId", new Class<?>[0]));
		Assert.assertEquals(existingAssetTagStats.getClassNameId(),
			ReflectionTestUtil.invoke(existingAssetTagStats,
				"getOriginalClassNameId", new Class<?>[0]));
	}

	protected AssetTagStats addAssetTagStats() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetTagStats assetTagStats = _persistence.create(pk);

		assetTagStats.setTagId(RandomTestUtil.nextLong());

		assetTagStats.setClassNameId(RandomTestUtil.nextLong());

		assetTagStats.setAssetCount(RandomTestUtil.nextInt());

		_assetTagStatses.add(_persistence.update(assetTagStats));

		return assetTagStats;
	}

	private List<AssetTagStats> _assetTagStatses = new ArrayList<AssetTagStats>();
	private AssetTagStatsPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}