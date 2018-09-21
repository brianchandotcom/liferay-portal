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

package com.liferay.asset.list.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.asset.list.exception.NoSuchEntryAssetEntryRelException;
import com.liferay.asset.list.model.AssetListEntryAssetEntryRel;
import com.liferay.asset.list.service.AssetListEntryAssetEntryRelLocalServiceUtil;
import com.liferay.asset.list.service.persistence.AssetListEntryAssetEntryRelPersistence;
import com.liferay.asset.list.service.persistence.AssetListEntryAssetEntryRelUtil;

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
public class AssetListEntryAssetEntryRelPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.asset.list.service"));

	@Before
	public void setUp() {
		_persistence = AssetListEntryAssetEntryRelUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<AssetListEntryAssetEntryRel> iterator = _assetListEntryAssetEntryRels.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetListEntryAssetEntryRel assetListEntryAssetEntryRel = _persistence.create(pk);

		Assert.assertNotNull(assetListEntryAssetEntryRel);

		Assert.assertEquals(assetListEntryAssetEntryRel.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		AssetListEntryAssetEntryRel newAssetListEntryAssetEntryRel = addAssetListEntryAssetEntryRel();

		_persistence.remove(newAssetListEntryAssetEntryRel);

		AssetListEntryAssetEntryRel existingAssetListEntryAssetEntryRel = _persistence.fetchByPrimaryKey(newAssetListEntryAssetEntryRel.getPrimaryKey());

		Assert.assertNull(existingAssetListEntryAssetEntryRel);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addAssetListEntryAssetEntryRel();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetListEntryAssetEntryRel newAssetListEntryAssetEntryRel = _persistence.create(pk);

		newAssetListEntryAssetEntryRel.setAssetListEntryId(RandomTestUtil.nextLong());

		newAssetListEntryAssetEntryRel.setAssetEntryId(RandomTestUtil.nextLong());

		newAssetListEntryAssetEntryRel.setPosition(RandomTestUtil.nextInt());

		_assetListEntryAssetEntryRels.add(_persistence.update(
				newAssetListEntryAssetEntryRel));

		AssetListEntryAssetEntryRel existingAssetListEntryAssetEntryRel = _persistence.findByPrimaryKey(newAssetListEntryAssetEntryRel.getPrimaryKey());

		Assert.assertEquals(existingAssetListEntryAssetEntryRel.getAssetListEntryAssetEntryRelId(),
			newAssetListEntryAssetEntryRel.getAssetListEntryAssetEntryRelId());
		Assert.assertEquals(existingAssetListEntryAssetEntryRel.getAssetListEntryId(),
			newAssetListEntryAssetEntryRel.getAssetListEntryId());
		Assert.assertEquals(existingAssetListEntryAssetEntryRel.getAssetEntryId(),
			newAssetListEntryAssetEntryRel.getAssetEntryId());
		Assert.assertEquals(existingAssetListEntryAssetEntryRel.getPosition(),
			newAssetListEntryAssetEntryRel.getPosition());
	}

	@Test
	public void testCountByAssetListEntryId() throws Exception {
		_persistence.countByAssetListEntryId(RandomTestUtil.nextLong());

		_persistence.countByAssetListEntryId(0L);
	}

	@Test
	public void testCountByA_P() throws Exception {
		_persistence.countByA_P(RandomTestUtil.nextLong(),
			RandomTestUtil.nextInt());

		_persistence.countByA_P(0L, 0);
	}

	@Test
	public void testCountByA_GtP() throws Exception {
		_persistence.countByA_GtP(RandomTestUtil.nextLong(),
			RandomTestUtil.nextInt());

		_persistence.countByA_GtP(0L, 0);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		AssetListEntryAssetEntryRel newAssetListEntryAssetEntryRel = addAssetListEntryAssetEntryRel();

		AssetListEntryAssetEntryRel existingAssetListEntryAssetEntryRel = _persistence.findByPrimaryKey(newAssetListEntryAssetEntryRel.getPrimaryKey());

		Assert.assertEquals(existingAssetListEntryAssetEntryRel,
			newAssetListEntryAssetEntryRel);
	}

	@Test(expected = NoSuchEntryAssetEntryRelException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<AssetListEntryAssetEntryRel> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("AssetListEntryAssetEntryRel",
			"assetListEntryAssetEntryRelId", true, "assetListEntryId", true,
			"assetEntryId", true, "position", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		AssetListEntryAssetEntryRel newAssetListEntryAssetEntryRel = addAssetListEntryAssetEntryRel();

		AssetListEntryAssetEntryRel existingAssetListEntryAssetEntryRel = _persistence.fetchByPrimaryKey(newAssetListEntryAssetEntryRel.getPrimaryKey());

		Assert.assertEquals(existingAssetListEntryAssetEntryRel,
			newAssetListEntryAssetEntryRel);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetListEntryAssetEntryRel missingAssetListEntryAssetEntryRel = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingAssetListEntryAssetEntryRel);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		AssetListEntryAssetEntryRel newAssetListEntryAssetEntryRel1 = addAssetListEntryAssetEntryRel();
		AssetListEntryAssetEntryRel newAssetListEntryAssetEntryRel2 = addAssetListEntryAssetEntryRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetListEntryAssetEntryRel1.getPrimaryKey());
		primaryKeys.add(newAssetListEntryAssetEntryRel2.getPrimaryKey());

		Map<Serializable, AssetListEntryAssetEntryRel> assetListEntryAssetEntryRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, assetListEntryAssetEntryRels.size());
		Assert.assertEquals(newAssetListEntryAssetEntryRel1,
			assetListEntryAssetEntryRels.get(
				newAssetListEntryAssetEntryRel1.getPrimaryKey()));
		Assert.assertEquals(newAssetListEntryAssetEntryRel2,
			assetListEntryAssetEntryRels.get(
				newAssetListEntryAssetEntryRel2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, AssetListEntryAssetEntryRel> assetListEntryAssetEntryRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(assetListEntryAssetEntryRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		AssetListEntryAssetEntryRel newAssetListEntryAssetEntryRel = addAssetListEntryAssetEntryRel();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetListEntryAssetEntryRel.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, AssetListEntryAssetEntryRel> assetListEntryAssetEntryRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, assetListEntryAssetEntryRels.size());
		Assert.assertEquals(newAssetListEntryAssetEntryRel,
			assetListEntryAssetEntryRels.get(
				newAssetListEntryAssetEntryRel.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, AssetListEntryAssetEntryRel> assetListEntryAssetEntryRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(assetListEntryAssetEntryRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		AssetListEntryAssetEntryRel newAssetListEntryAssetEntryRel = addAssetListEntryAssetEntryRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetListEntryAssetEntryRel.getPrimaryKey());

		Map<Serializable, AssetListEntryAssetEntryRel> assetListEntryAssetEntryRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, assetListEntryAssetEntryRels.size());
		Assert.assertEquals(newAssetListEntryAssetEntryRel,
			assetListEntryAssetEntryRels.get(
				newAssetListEntryAssetEntryRel.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = AssetListEntryAssetEntryRelLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<AssetListEntryAssetEntryRel>() {
				@Override
				public void performAction(
					AssetListEntryAssetEntryRel assetListEntryAssetEntryRel) {
					Assert.assertNotNull(assetListEntryAssetEntryRel);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		AssetListEntryAssetEntryRel newAssetListEntryAssetEntryRel = addAssetListEntryAssetEntryRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetListEntryAssetEntryRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"assetListEntryAssetEntryRelId",
				newAssetListEntryAssetEntryRel.getAssetListEntryAssetEntryRelId()));

		List<AssetListEntryAssetEntryRel> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		AssetListEntryAssetEntryRel existingAssetListEntryAssetEntryRel = result.get(0);

		Assert.assertEquals(existingAssetListEntryAssetEntryRel,
			newAssetListEntryAssetEntryRel);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetListEntryAssetEntryRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"assetListEntryAssetEntryRelId", RandomTestUtil.nextLong()));

		List<AssetListEntryAssetEntryRel> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		AssetListEntryAssetEntryRel newAssetListEntryAssetEntryRel = addAssetListEntryAssetEntryRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetListEntryAssetEntryRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"assetListEntryAssetEntryRelId"));

		Object newAssetListEntryAssetEntryRelId = newAssetListEntryAssetEntryRel.getAssetListEntryAssetEntryRelId();

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"assetListEntryAssetEntryRelId",
				new Object[] { newAssetListEntryAssetEntryRelId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingAssetListEntryAssetEntryRelId = result.get(0);

		Assert.assertEquals(existingAssetListEntryAssetEntryRelId,
			newAssetListEntryAssetEntryRelId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetListEntryAssetEntryRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"assetListEntryAssetEntryRelId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"assetListEntryAssetEntryRelId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		AssetListEntryAssetEntryRel newAssetListEntryAssetEntryRel = addAssetListEntryAssetEntryRel();

		_persistence.clearCache();

		AssetListEntryAssetEntryRel existingAssetListEntryAssetEntryRel = _persistence.findByPrimaryKey(newAssetListEntryAssetEntryRel.getPrimaryKey());

		Assert.assertEquals(Long.valueOf(
				existingAssetListEntryAssetEntryRel.getAssetListEntryId()),
			ReflectionTestUtil.<Long>invoke(
				existingAssetListEntryAssetEntryRel,
				"getOriginalAssetListEntryId", new Class<?>[0]));
		Assert.assertEquals(Integer.valueOf(
				existingAssetListEntryAssetEntryRel.getPosition()),
			ReflectionTestUtil.<Integer>invoke(
				existingAssetListEntryAssetEntryRel, "getOriginalPosition",
				new Class<?>[0]));
	}

	protected AssetListEntryAssetEntryRel addAssetListEntryAssetEntryRel()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetListEntryAssetEntryRel assetListEntryAssetEntryRel = _persistence.create(pk);

		assetListEntryAssetEntryRel.setAssetListEntryId(RandomTestUtil.nextLong());

		assetListEntryAssetEntryRel.setAssetEntryId(RandomTestUtil.nextLong());

		assetListEntryAssetEntryRel.setPosition(RandomTestUtil.nextInt());

		_assetListEntryAssetEntryRels.add(_persistence.update(
				assetListEntryAssetEntryRel));

		return assetListEntryAssetEntryRel;
	}

	private List<AssetListEntryAssetEntryRel> _assetListEntryAssetEntryRels = new ArrayList<AssetListEntryAssetEntryRel>();
	private AssetListEntryAssetEntryRelPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}