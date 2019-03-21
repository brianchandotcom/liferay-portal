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
import com.liferay.asset.list.exception.NoSuchAssetEntryAssetListEntryRelException;
import com.liferay.asset.list.model.AssetEntryAssetListEntryRel;
import com.liferay.asset.list.service.AssetEntryAssetListEntryRelLocalServiceUtil;
import com.liferay.asset.list.service.persistence.AssetEntryAssetListEntryRelPersistence;
import com.liferay.asset.list.service.persistence.AssetEntryAssetListEntryRelUtil;
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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class AssetEntryAssetListEntryRelPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.asset.list.service"));

	@Before
	public void setUp() {
		_persistence = AssetEntryAssetListEntryRelUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<AssetEntryAssetListEntryRel> iterator =
			_assetEntryAssetListEntryRels.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetEntryAssetListEntryRel assetEntryAssetListEntryRel =
			_persistence.create(pk);

		Assert.assertNotNull(assetEntryAssetListEntryRel);

		Assert.assertEquals(assetEntryAssetListEntryRel.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		AssetEntryAssetListEntryRel newAssetEntryAssetListEntryRel =
			addAssetEntryAssetListEntryRel();

		_persistence.remove(newAssetEntryAssetListEntryRel);

		AssetEntryAssetListEntryRel existingAssetEntryAssetListEntryRel =
			_persistence.fetchByPrimaryKey(
				newAssetEntryAssetListEntryRel.getPrimaryKey());

		Assert.assertNull(existingAssetEntryAssetListEntryRel);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addAssetEntryAssetListEntryRel();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetEntryAssetListEntryRel newAssetEntryAssetListEntryRel =
			_persistence.create(pk);

		newAssetEntryAssetListEntryRel.setUuid(RandomTestUtil.randomString());

		newAssetEntryAssetListEntryRel.setGroupId(RandomTestUtil.nextLong());

		newAssetEntryAssetListEntryRel.setCompanyId(RandomTestUtil.nextLong());

		newAssetEntryAssetListEntryRel.setUserId(RandomTestUtil.nextLong());

		newAssetEntryAssetListEntryRel.setUserName(
			RandomTestUtil.randomString());

		newAssetEntryAssetListEntryRel.setCreateDate(RandomTestUtil.nextDate());

		newAssetEntryAssetListEntryRel.setModifiedDate(
			RandomTestUtil.nextDate());

		newAssetEntryAssetListEntryRel.setAssetEntryId(
			RandomTestUtil.nextLong());

		newAssetEntryAssetListEntryRel.setAssetListEntryId(
			RandomTestUtil.nextLong());

		newAssetEntryAssetListEntryRel.setSegmentsEntryId(
			RandomTestUtil.nextLong());

		newAssetEntryAssetListEntryRel.setPosition(RandomTestUtil.nextInt());

		newAssetEntryAssetListEntryRel.setLastPublishDate(
			RandomTestUtil.nextDate());

		_assetEntryAssetListEntryRels.add(
			_persistence.update(newAssetEntryAssetListEntryRel));

		AssetEntryAssetListEntryRel existingAssetEntryAssetListEntryRel =
			_persistence.findByPrimaryKey(
				newAssetEntryAssetListEntryRel.getPrimaryKey());

		Assert.assertEquals(
			existingAssetEntryAssetListEntryRel.getUuid(),
			newAssetEntryAssetListEntryRel.getUuid());
		Assert.assertEquals(
			existingAssetEntryAssetListEntryRel.
				getAssetEntryAssetListEntryRelId(),
			newAssetEntryAssetListEntryRel.getAssetEntryAssetListEntryRelId());
		Assert.assertEquals(
			existingAssetEntryAssetListEntryRel.getGroupId(),
			newAssetEntryAssetListEntryRel.getGroupId());
		Assert.assertEquals(
			existingAssetEntryAssetListEntryRel.getCompanyId(),
			newAssetEntryAssetListEntryRel.getCompanyId());
		Assert.assertEquals(
			existingAssetEntryAssetListEntryRel.getUserId(),
			newAssetEntryAssetListEntryRel.getUserId());
		Assert.assertEquals(
			existingAssetEntryAssetListEntryRel.getUserName(),
			newAssetEntryAssetListEntryRel.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingAssetEntryAssetListEntryRel.getCreateDate()),
			Time.getShortTimestamp(
				newAssetEntryAssetListEntryRel.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingAssetEntryAssetListEntryRel.getModifiedDate()),
			Time.getShortTimestamp(
				newAssetEntryAssetListEntryRel.getModifiedDate()));
		Assert.assertEquals(
			existingAssetEntryAssetListEntryRel.getAssetEntryId(),
			newAssetEntryAssetListEntryRel.getAssetEntryId());
		Assert.assertEquals(
			existingAssetEntryAssetListEntryRel.getAssetListEntryId(),
			newAssetEntryAssetListEntryRel.getAssetListEntryId());
		Assert.assertEquals(
			existingAssetEntryAssetListEntryRel.getSegmentsEntryId(),
			newAssetEntryAssetListEntryRel.getSegmentsEntryId());
		Assert.assertEquals(
			existingAssetEntryAssetListEntryRel.getPosition(),
			newAssetEntryAssetListEntryRel.getPosition());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingAssetEntryAssetListEntryRel.getLastPublishDate()),
			Time.getShortTimestamp(
				newAssetEntryAssetListEntryRel.getLastPublishDate()));
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
	public void testCountByAssetListEntryId() throws Exception {
		_persistence.countByAssetListEntryId(RandomTestUtil.nextLong());

		_persistence.countByAssetListEntryId(0L);
	}

	@Test
	public void testCountByA_P() throws Exception {
		_persistence.countByA_P(
			RandomTestUtil.nextLong(), RandomTestUtil.nextInt());

		_persistence.countByA_P(0L, 0);
	}

	@Test
	public void testCountByA_GtP() throws Exception {
		_persistence.countByA_GtP(
			RandomTestUtil.nextLong(), RandomTestUtil.nextInt());

		_persistence.countByA_GtP(0L, 0);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		AssetEntryAssetListEntryRel newAssetEntryAssetListEntryRel =
			addAssetEntryAssetListEntryRel();

		AssetEntryAssetListEntryRel existingAssetEntryAssetListEntryRel =
			_persistence.findByPrimaryKey(
				newAssetEntryAssetListEntryRel.getPrimaryKey());

		Assert.assertEquals(
			existingAssetEntryAssetListEntryRel,
			newAssetEntryAssetListEntryRel);
	}

	@Test(expected = NoSuchAssetEntryAssetListEntryRelException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<AssetEntryAssetListEntryRel>
		getOrderByComparator() {

		return OrderByComparatorFactoryUtil.create(
			"AssetEntryAssetListEntryRel", "uuid", true,
			"assetEntryAssetListEntryRelId", true, "groupId", true, "companyId",
			true, "userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "assetEntryId", true, "assetListEntryId",
			true, "segmentsEntryId", true, "position", true, "lastPublishDate",
			true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		AssetEntryAssetListEntryRel newAssetEntryAssetListEntryRel =
			addAssetEntryAssetListEntryRel();

		AssetEntryAssetListEntryRel existingAssetEntryAssetListEntryRel =
			_persistence.fetchByPrimaryKey(
				newAssetEntryAssetListEntryRel.getPrimaryKey());

		Assert.assertEquals(
			existingAssetEntryAssetListEntryRel,
			newAssetEntryAssetListEntryRel);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetEntryAssetListEntryRel missingAssetEntryAssetListEntryRel =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingAssetEntryAssetListEntryRel);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		AssetEntryAssetListEntryRel newAssetEntryAssetListEntryRel1 =
			addAssetEntryAssetListEntryRel();
		AssetEntryAssetListEntryRel newAssetEntryAssetListEntryRel2 =
			addAssetEntryAssetListEntryRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetEntryAssetListEntryRel1.getPrimaryKey());
		primaryKeys.add(newAssetEntryAssetListEntryRel2.getPrimaryKey());

		Map<Serializable, AssetEntryAssetListEntryRel>
			assetEntryAssetListEntryRels = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(2, assetEntryAssetListEntryRels.size());
		Assert.assertEquals(
			newAssetEntryAssetListEntryRel1,
			assetEntryAssetListEntryRels.get(
				newAssetEntryAssetListEntryRel1.getPrimaryKey()));
		Assert.assertEquals(
			newAssetEntryAssetListEntryRel2,
			assetEntryAssetListEntryRels.get(
				newAssetEntryAssetListEntryRel2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, AssetEntryAssetListEntryRel>
			assetEntryAssetListEntryRels = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(assetEntryAssetListEntryRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		AssetEntryAssetListEntryRel newAssetEntryAssetListEntryRel =
			addAssetEntryAssetListEntryRel();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetEntryAssetListEntryRel.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, AssetEntryAssetListEntryRel>
			assetEntryAssetListEntryRels = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, assetEntryAssetListEntryRels.size());
		Assert.assertEquals(
			newAssetEntryAssetListEntryRel,
			assetEntryAssetListEntryRels.get(
				newAssetEntryAssetListEntryRel.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, AssetEntryAssetListEntryRel>
			assetEntryAssetListEntryRels = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(assetEntryAssetListEntryRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		AssetEntryAssetListEntryRel newAssetEntryAssetListEntryRel =
			addAssetEntryAssetListEntryRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetEntryAssetListEntryRel.getPrimaryKey());

		Map<Serializable, AssetEntryAssetListEntryRel>
			assetEntryAssetListEntryRels = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, assetEntryAssetListEntryRels.size());
		Assert.assertEquals(
			newAssetEntryAssetListEntryRel,
			assetEntryAssetListEntryRels.get(
				newAssetEntryAssetListEntryRel.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			AssetEntryAssetListEntryRelLocalServiceUtil.
				getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<AssetEntryAssetListEntryRel>() {

				@Override
				public void performAction(
					AssetEntryAssetListEntryRel assetEntryAssetListEntryRel) {

					Assert.assertNotNull(assetEntryAssetListEntryRel);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		AssetEntryAssetListEntryRel newAssetEntryAssetListEntryRel =
			addAssetEntryAssetListEntryRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AssetEntryAssetListEntryRel.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"assetEntryAssetListEntryRelId",
				newAssetEntryAssetListEntryRel.
					getAssetEntryAssetListEntryRelId()));

		List<AssetEntryAssetListEntryRel> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		AssetEntryAssetListEntryRel existingAssetEntryAssetListEntryRel =
			result.get(0);

		Assert.assertEquals(
			existingAssetEntryAssetListEntryRel,
			newAssetEntryAssetListEntryRel);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AssetEntryAssetListEntryRel.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"assetEntryAssetListEntryRelId", RandomTestUtil.nextLong()));

		List<AssetEntryAssetListEntryRel> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		AssetEntryAssetListEntryRel newAssetEntryAssetListEntryRel =
			addAssetEntryAssetListEntryRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AssetEntryAssetListEntryRel.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("assetEntryAssetListEntryRelId"));

		Object newAssetEntryAssetListEntryRelId =
			newAssetEntryAssetListEntryRel.getAssetEntryAssetListEntryRelId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"assetEntryAssetListEntryRelId",
				new Object[] {newAssetEntryAssetListEntryRelId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingAssetEntryAssetListEntryRelId = result.get(0);

		Assert.assertEquals(
			existingAssetEntryAssetListEntryRelId,
			newAssetEntryAssetListEntryRelId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AssetEntryAssetListEntryRel.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("assetEntryAssetListEntryRelId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"assetEntryAssetListEntryRelId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		AssetEntryAssetListEntryRel newAssetEntryAssetListEntryRel =
			addAssetEntryAssetListEntryRel();

		_persistence.clearCache();

		AssetEntryAssetListEntryRel existingAssetEntryAssetListEntryRel =
			_persistence.findByPrimaryKey(
				newAssetEntryAssetListEntryRel.getPrimaryKey());

		Assert.assertTrue(
			Objects.equals(
				existingAssetEntryAssetListEntryRel.getUuid(),
				ReflectionTestUtil.invoke(
					existingAssetEntryAssetListEntryRel, "getOriginalUuid",
					new Class<?>[0])));
		Assert.assertEquals(
			Long.valueOf(existingAssetEntryAssetListEntryRel.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingAssetEntryAssetListEntryRel, "getOriginalGroupId",
				new Class<?>[0]));

		Assert.assertEquals(
			Long.valueOf(
				existingAssetEntryAssetListEntryRel.getAssetListEntryId()),
			ReflectionTestUtil.<Long>invoke(
				existingAssetEntryAssetListEntryRel,
				"getOriginalAssetListEntryId", new Class<?>[0]));
		Assert.assertEquals(
			Integer.valueOf(existingAssetEntryAssetListEntryRel.getPosition()),
			ReflectionTestUtil.<Integer>invoke(
				existingAssetEntryAssetListEntryRel, "getOriginalPosition",
				new Class<?>[0]));
	}

	protected AssetEntryAssetListEntryRel addAssetEntryAssetListEntryRel()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		AssetEntryAssetListEntryRel assetEntryAssetListEntryRel =
			_persistence.create(pk);

		assetEntryAssetListEntryRel.setUuid(RandomTestUtil.randomString());

		assetEntryAssetListEntryRel.setGroupId(RandomTestUtil.nextLong());

		assetEntryAssetListEntryRel.setCompanyId(RandomTestUtil.nextLong());

		assetEntryAssetListEntryRel.setUserId(RandomTestUtil.nextLong());

		assetEntryAssetListEntryRel.setUserName(RandomTestUtil.randomString());

		assetEntryAssetListEntryRel.setCreateDate(RandomTestUtil.nextDate());

		assetEntryAssetListEntryRel.setModifiedDate(RandomTestUtil.nextDate());

		assetEntryAssetListEntryRel.setAssetEntryId(RandomTestUtil.nextLong());

		assetEntryAssetListEntryRel.setAssetListEntryId(
			RandomTestUtil.nextLong());

		assetEntryAssetListEntryRel.setSegmentsEntryId(
			RandomTestUtil.nextLong());

		assetEntryAssetListEntryRel.setPosition(RandomTestUtil.nextInt());

		assetEntryAssetListEntryRel.setLastPublishDate(
			RandomTestUtil.nextDate());

		_assetEntryAssetListEntryRels.add(
			_persistence.update(assetEntryAssetListEntryRel));

		return assetEntryAssetListEntryRel;
	}

	private List<AssetEntryAssetListEntryRel> _assetEntryAssetListEntryRels =
		new ArrayList<AssetEntryAssetListEntryRel>();
	private AssetEntryAssetListEntryRelPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}