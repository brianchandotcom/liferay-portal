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

package com.liferay.asset.entry.rel.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.asset.entry.rel.exception.NoSuchEntryDisplayPageRelException;
import com.liferay.asset.entry.rel.model.AssetEntryDisplayPageRel;
import com.liferay.asset.entry.rel.service.AssetEntryDisplayPageRelLocalServiceUtil;
import com.liferay.asset.entry.rel.service.persistence.AssetEntryDisplayPageRelPersistence;
import com.liferay.asset.entry.rel.service.persistence.AssetEntryDisplayPageRelUtil;

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
public class AssetEntryDisplayPageRelPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.asset.entry.rel.service"));

	@Before
	public void setUp() {
		_persistence = AssetEntryDisplayPageRelUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<AssetEntryDisplayPageRel> iterator = _assetEntryDisplayPageRels.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetEntryDisplayPageRel assetEntryDisplayPageRel = _persistence.create(pk);

		Assert.assertNotNull(assetEntryDisplayPageRel);

		Assert.assertEquals(assetEntryDisplayPageRel.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		AssetEntryDisplayPageRel newAssetEntryDisplayPageRel = addAssetEntryDisplayPageRel();

		_persistence.remove(newAssetEntryDisplayPageRel);

		AssetEntryDisplayPageRel existingAssetEntryDisplayPageRel = _persistence.fetchByPrimaryKey(newAssetEntryDisplayPageRel.getPrimaryKey());

		Assert.assertNull(existingAssetEntryDisplayPageRel);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addAssetEntryDisplayPageRel();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetEntryDisplayPageRel newAssetEntryDisplayPageRel = _persistence.create(pk);

		newAssetEntryDisplayPageRel.setAssetEntryId(RandomTestUtil.nextLong());

		newAssetEntryDisplayPageRel.setDisplayPageId(RandomTestUtil.nextLong());

		_assetEntryDisplayPageRels.add(_persistence.update(
				newAssetEntryDisplayPageRel));

		AssetEntryDisplayPageRel existingAssetEntryDisplayPageRel = _persistence.findByPrimaryKey(newAssetEntryDisplayPageRel.getPrimaryKey());

		Assert.assertEquals(existingAssetEntryDisplayPageRel.getAssetEntryDisplayPageRelId(),
			newAssetEntryDisplayPageRel.getAssetEntryDisplayPageRelId());
		Assert.assertEquals(existingAssetEntryDisplayPageRel.getAssetEntryId(),
			newAssetEntryDisplayPageRel.getAssetEntryId());
		Assert.assertEquals(existingAssetEntryDisplayPageRel.getDisplayPageId(),
			newAssetEntryDisplayPageRel.getDisplayPageId());
	}

	@Test
	public void testCountByAssetEntryId() throws Exception {
		_persistence.countByAssetEntryId(RandomTestUtil.nextLong());

		_persistence.countByAssetEntryId(0L);
	}

	@Test
	public void testCountByA_D() throws Exception {
		_persistence.countByA_D(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByA_D(0L, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		AssetEntryDisplayPageRel newAssetEntryDisplayPageRel = addAssetEntryDisplayPageRel();

		AssetEntryDisplayPageRel existingAssetEntryDisplayPageRel = _persistence.findByPrimaryKey(newAssetEntryDisplayPageRel.getPrimaryKey());

		Assert.assertEquals(existingAssetEntryDisplayPageRel,
			newAssetEntryDisplayPageRel);
	}

	@Test(expected = NoSuchEntryDisplayPageRelException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<AssetEntryDisplayPageRel> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("AssetEntryDisplayPageRel",
			"assetEntryDisplayPageRelId", true, "assetEntryId", true,
			"displayPageId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		AssetEntryDisplayPageRel newAssetEntryDisplayPageRel = addAssetEntryDisplayPageRel();

		AssetEntryDisplayPageRel existingAssetEntryDisplayPageRel = _persistence.fetchByPrimaryKey(newAssetEntryDisplayPageRel.getPrimaryKey());

		Assert.assertEquals(existingAssetEntryDisplayPageRel,
			newAssetEntryDisplayPageRel);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetEntryDisplayPageRel missingAssetEntryDisplayPageRel = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingAssetEntryDisplayPageRel);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		AssetEntryDisplayPageRel newAssetEntryDisplayPageRel1 = addAssetEntryDisplayPageRel();
		AssetEntryDisplayPageRel newAssetEntryDisplayPageRel2 = addAssetEntryDisplayPageRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetEntryDisplayPageRel1.getPrimaryKey());
		primaryKeys.add(newAssetEntryDisplayPageRel2.getPrimaryKey());

		Map<Serializable, AssetEntryDisplayPageRel> assetEntryDisplayPageRels = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, assetEntryDisplayPageRels.size());
		Assert.assertEquals(newAssetEntryDisplayPageRel1,
			assetEntryDisplayPageRels.get(
				newAssetEntryDisplayPageRel1.getPrimaryKey()));
		Assert.assertEquals(newAssetEntryDisplayPageRel2,
			assetEntryDisplayPageRels.get(
				newAssetEntryDisplayPageRel2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, AssetEntryDisplayPageRel> assetEntryDisplayPageRels = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(assetEntryDisplayPageRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		AssetEntryDisplayPageRel newAssetEntryDisplayPageRel = addAssetEntryDisplayPageRel();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetEntryDisplayPageRel.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, AssetEntryDisplayPageRel> assetEntryDisplayPageRels = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, assetEntryDisplayPageRels.size());
		Assert.assertEquals(newAssetEntryDisplayPageRel,
			assetEntryDisplayPageRels.get(
				newAssetEntryDisplayPageRel.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, AssetEntryDisplayPageRel> assetEntryDisplayPageRels = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(assetEntryDisplayPageRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		AssetEntryDisplayPageRel newAssetEntryDisplayPageRel = addAssetEntryDisplayPageRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetEntryDisplayPageRel.getPrimaryKey());

		Map<Serializable, AssetEntryDisplayPageRel> assetEntryDisplayPageRels = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, assetEntryDisplayPageRels.size());
		Assert.assertEquals(newAssetEntryDisplayPageRel,
			assetEntryDisplayPageRels.get(
				newAssetEntryDisplayPageRel.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = AssetEntryDisplayPageRelLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<AssetEntryDisplayPageRel>() {
				@Override
				public void performAction(
					AssetEntryDisplayPageRel assetEntryDisplayPageRel) {
					Assert.assertNotNull(assetEntryDisplayPageRel);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		AssetEntryDisplayPageRel newAssetEntryDisplayPageRel = addAssetEntryDisplayPageRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetEntryDisplayPageRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"assetEntryDisplayPageRelId",
				newAssetEntryDisplayPageRel.getAssetEntryDisplayPageRelId()));

		List<AssetEntryDisplayPageRel> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		AssetEntryDisplayPageRel existingAssetEntryDisplayPageRel = result.get(0);

		Assert.assertEquals(existingAssetEntryDisplayPageRel,
			newAssetEntryDisplayPageRel);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetEntryDisplayPageRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"assetEntryDisplayPageRelId", RandomTestUtil.nextLong()));

		List<AssetEntryDisplayPageRel> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		AssetEntryDisplayPageRel newAssetEntryDisplayPageRel = addAssetEntryDisplayPageRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetEntryDisplayPageRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"assetEntryDisplayPageRelId"));

		Object newAssetEntryDisplayPageRelId = newAssetEntryDisplayPageRel.getAssetEntryDisplayPageRelId();

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"assetEntryDisplayPageRelId",
				new Object[] { newAssetEntryDisplayPageRelId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingAssetEntryDisplayPageRelId = result.get(0);

		Assert.assertEquals(existingAssetEntryDisplayPageRelId,
			newAssetEntryDisplayPageRelId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetEntryDisplayPageRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"assetEntryDisplayPageRelId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"assetEntryDisplayPageRelId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		AssetEntryDisplayPageRel newAssetEntryDisplayPageRel = addAssetEntryDisplayPageRel();

		_persistence.clearCache();

		AssetEntryDisplayPageRel existingAssetEntryDisplayPageRel = _persistence.findByPrimaryKey(newAssetEntryDisplayPageRel.getPrimaryKey());

		Assert.assertEquals(Long.valueOf(
				existingAssetEntryDisplayPageRel.getAssetEntryId()),
			ReflectionTestUtil.<Long>invoke(existingAssetEntryDisplayPageRel,
				"getOriginalAssetEntryId", new Class<?>[0]));
		Assert.assertEquals(Long.valueOf(
				existingAssetEntryDisplayPageRel.getDisplayPageId()),
			ReflectionTestUtil.<Long>invoke(existingAssetEntryDisplayPageRel,
				"getOriginalDisplayPageId", new Class<?>[0]));
	}

	protected AssetEntryDisplayPageRel addAssetEntryDisplayPageRel()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetEntryDisplayPageRel assetEntryDisplayPageRel = _persistence.create(pk);

		assetEntryDisplayPageRel.setAssetEntryId(RandomTestUtil.nextLong());

		assetEntryDisplayPageRel.setDisplayPageId(RandomTestUtil.nextLong());

		_assetEntryDisplayPageRels.add(_persistence.update(
				assetEntryDisplayPageRel));

		return assetEntryDisplayPageRel;
	}

	private List<AssetEntryDisplayPageRel> _assetEntryDisplayPageRels = new ArrayList<AssetEntryDisplayPageRel>();
	private AssetEntryDisplayPageRelPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}