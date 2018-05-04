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

import com.liferay.asset.entry.rel.exception.NoSuchEntryAssetDisplayPageRelException;
import com.liferay.asset.entry.rel.model.AssetEntryAssetDisplayPageRel;
import com.liferay.asset.entry.rel.service.AssetEntryAssetDisplayPageRelLocalServiceUtil;
import com.liferay.asset.entry.rel.service.persistence.AssetEntryAssetDisplayPageRelPersistence;
import com.liferay.asset.entry.rel.service.persistence.AssetEntryAssetDisplayPageRelUtil;

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
public class AssetEntryAssetDisplayPageRelPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.asset.entry.rel.service"));

	@Before
	public void setUp() {
		_persistence = AssetEntryAssetDisplayPageRelUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<AssetEntryAssetDisplayPageRel> iterator = _assetEntryAssetDisplayPageRels.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetEntryAssetDisplayPageRel assetEntryAssetDisplayPageRel = _persistence.create(pk);

		Assert.assertNotNull(assetEntryAssetDisplayPageRel);

		Assert.assertEquals(assetEntryAssetDisplayPageRel.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		AssetEntryAssetDisplayPageRel newAssetEntryAssetDisplayPageRel = addAssetEntryAssetDisplayPageRel();

		_persistence.remove(newAssetEntryAssetDisplayPageRel);

		AssetEntryAssetDisplayPageRel existingAssetEntryAssetDisplayPageRel = _persistence.fetchByPrimaryKey(newAssetEntryAssetDisplayPageRel.getPrimaryKey());

		Assert.assertNull(existingAssetEntryAssetDisplayPageRel);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addAssetEntryAssetDisplayPageRel();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetEntryAssetDisplayPageRel newAssetEntryAssetDisplayPageRel = _persistence.create(pk);

		newAssetEntryAssetDisplayPageRel.setAssetEntryId(RandomTestUtil.nextLong());

		newAssetEntryAssetDisplayPageRel.setAssetDisplayPageId(RandomTestUtil.nextLong());

		_assetEntryAssetDisplayPageRels.add(_persistence.update(
				newAssetEntryAssetDisplayPageRel));

		AssetEntryAssetDisplayPageRel existingAssetEntryAssetDisplayPageRel = _persistence.findByPrimaryKey(newAssetEntryAssetDisplayPageRel.getPrimaryKey());

		Assert.assertEquals(existingAssetEntryAssetDisplayPageRel.getAssetEntryAssetDisplayPageId(),
			newAssetEntryAssetDisplayPageRel.getAssetEntryAssetDisplayPageId());
		Assert.assertEquals(existingAssetEntryAssetDisplayPageRel.getAssetEntryId(),
			newAssetEntryAssetDisplayPageRel.getAssetEntryId());
		Assert.assertEquals(existingAssetEntryAssetDisplayPageRel.getAssetDisplayPageId(),
			newAssetEntryAssetDisplayPageRel.getAssetDisplayPageId());
	}

	@Test
	public void testCountByAssetEntryId() throws Exception {
		_persistence.countByAssetEntryId(RandomTestUtil.nextLong());

		_persistence.countByAssetEntryId(0L);
	}

	@Test
	public void testCountByA_A() throws Exception {
		_persistence.countByA_A(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByA_A(0L, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		AssetEntryAssetDisplayPageRel newAssetEntryAssetDisplayPageRel = addAssetEntryAssetDisplayPageRel();

		AssetEntryAssetDisplayPageRel existingAssetEntryAssetDisplayPageRel = _persistence.findByPrimaryKey(newAssetEntryAssetDisplayPageRel.getPrimaryKey());

		Assert.assertEquals(existingAssetEntryAssetDisplayPageRel,
			newAssetEntryAssetDisplayPageRel);
	}

	@Test(expected = NoSuchEntryAssetDisplayPageRelException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<AssetEntryAssetDisplayPageRel> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("AssetEntryAssetDisplayPageRel",
			"assetEntryAssetDisplayPageId", true, "assetEntryId", true,
			"assetDisplayPageId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		AssetEntryAssetDisplayPageRel newAssetEntryAssetDisplayPageRel = addAssetEntryAssetDisplayPageRel();

		AssetEntryAssetDisplayPageRel existingAssetEntryAssetDisplayPageRel = _persistence.fetchByPrimaryKey(newAssetEntryAssetDisplayPageRel.getPrimaryKey());

		Assert.assertEquals(existingAssetEntryAssetDisplayPageRel,
			newAssetEntryAssetDisplayPageRel);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetEntryAssetDisplayPageRel missingAssetEntryAssetDisplayPageRel = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingAssetEntryAssetDisplayPageRel);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		AssetEntryAssetDisplayPageRel newAssetEntryAssetDisplayPageRel1 = addAssetEntryAssetDisplayPageRel();
		AssetEntryAssetDisplayPageRel newAssetEntryAssetDisplayPageRel2 = addAssetEntryAssetDisplayPageRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetEntryAssetDisplayPageRel1.getPrimaryKey());
		primaryKeys.add(newAssetEntryAssetDisplayPageRel2.getPrimaryKey());

		Map<Serializable, AssetEntryAssetDisplayPageRel> assetEntryAssetDisplayPageRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, assetEntryAssetDisplayPageRels.size());
		Assert.assertEquals(newAssetEntryAssetDisplayPageRel1,
			assetEntryAssetDisplayPageRels.get(
				newAssetEntryAssetDisplayPageRel1.getPrimaryKey()));
		Assert.assertEquals(newAssetEntryAssetDisplayPageRel2,
			assetEntryAssetDisplayPageRels.get(
				newAssetEntryAssetDisplayPageRel2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, AssetEntryAssetDisplayPageRel> assetEntryAssetDisplayPageRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(assetEntryAssetDisplayPageRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		AssetEntryAssetDisplayPageRel newAssetEntryAssetDisplayPageRel = addAssetEntryAssetDisplayPageRel();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetEntryAssetDisplayPageRel.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, AssetEntryAssetDisplayPageRel> assetEntryAssetDisplayPageRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, assetEntryAssetDisplayPageRels.size());
		Assert.assertEquals(newAssetEntryAssetDisplayPageRel,
			assetEntryAssetDisplayPageRels.get(
				newAssetEntryAssetDisplayPageRel.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, AssetEntryAssetDisplayPageRel> assetEntryAssetDisplayPageRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(assetEntryAssetDisplayPageRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		AssetEntryAssetDisplayPageRel newAssetEntryAssetDisplayPageRel = addAssetEntryAssetDisplayPageRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetEntryAssetDisplayPageRel.getPrimaryKey());

		Map<Serializable, AssetEntryAssetDisplayPageRel> assetEntryAssetDisplayPageRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, assetEntryAssetDisplayPageRels.size());
		Assert.assertEquals(newAssetEntryAssetDisplayPageRel,
			assetEntryAssetDisplayPageRels.get(
				newAssetEntryAssetDisplayPageRel.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = AssetEntryAssetDisplayPageRelLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<AssetEntryAssetDisplayPageRel>() {
				@Override
				public void performAction(
					AssetEntryAssetDisplayPageRel assetEntryAssetDisplayPageRel) {
					Assert.assertNotNull(assetEntryAssetDisplayPageRel);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		AssetEntryAssetDisplayPageRel newAssetEntryAssetDisplayPageRel = addAssetEntryAssetDisplayPageRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetEntryAssetDisplayPageRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"assetEntryAssetDisplayPageId",
				newAssetEntryAssetDisplayPageRel.getAssetEntryAssetDisplayPageId()));

		List<AssetEntryAssetDisplayPageRel> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		AssetEntryAssetDisplayPageRel existingAssetEntryAssetDisplayPageRel = result.get(0);

		Assert.assertEquals(existingAssetEntryAssetDisplayPageRel,
			newAssetEntryAssetDisplayPageRel);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetEntryAssetDisplayPageRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"assetEntryAssetDisplayPageId", RandomTestUtil.nextLong()));

		List<AssetEntryAssetDisplayPageRel> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		AssetEntryAssetDisplayPageRel newAssetEntryAssetDisplayPageRel = addAssetEntryAssetDisplayPageRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetEntryAssetDisplayPageRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"assetEntryAssetDisplayPageId"));

		Object newAssetEntryAssetDisplayPageId = newAssetEntryAssetDisplayPageRel.getAssetEntryAssetDisplayPageId();

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"assetEntryAssetDisplayPageId",
				new Object[] { newAssetEntryAssetDisplayPageId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingAssetEntryAssetDisplayPageId = result.get(0);

		Assert.assertEquals(existingAssetEntryAssetDisplayPageId,
			newAssetEntryAssetDisplayPageId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetEntryAssetDisplayPageRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"assetEntryAssetDisplayPageId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"assetEntryAssetDisplayPageId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		AssetEntryAssetDisplayPageRel newAssetEntryAssetDisplayPageRel = addAssetEntryAssetDisplayPageRel();

		_persistence.clearCache();

		AssetEntryAssetDisplayPageRel existingAssetEntryAssetDisplayPageRel = _persistence.findByPrimaryKey(newAssetEntryAssetDisplayPageRel.getPrimaryKey());

		Assert.assertEquals(Long.valueOf(
				existingAssetEntryAssetDisplayPageRel.getAssetEntryId()),
			ReflectionTestUtil.<Long>invoke(
				existingAssetEntryAssetDisplayPageRel,
				"getOriginalAssetEntryId", new Class<?>[0]));
		Assert.assertEquals(Long.valueOf(
				existingAssetEntryAssetDisplayPageRel.getAssetDisplayPageId()),
			ReflectionTestUtil.<Long>invoke(
				existingAssetEntryAssetDisplayPageRel,
				"getOriginalAssetDisplayPageId", new Class<?>[0]));
	}

	protected AssetEntryAssetDisplayPageRel addAssetEntryAssetDisplayPageRel()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetEntryAssetDisplayPageRel assetEntryAssetDisplayPageRel = _persistence.create(pk);

		assetEntryAssetDisplayPageRel.setAssetEntryId(RandomTestUtil.nextLong());

		assetEntryAssetDisplayPageRel.setAssetDisplayPageId(RandomTestUtil.nextLong());

		_assetEntryAssetDisplayPageRels.add(_persistence.update(
				assetEntryAssetDisplayPageRel));

		return assetEntryAssetDisplayPageRel;
	}

	private List<AssetEntryAssetDisplayPageRel> _assetEntryAssetDisplayPageRels = new ArrayList<AssetEntryAssetDisplayPageRel>();
	private AssetEntryAssetDisplayPageRelPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}