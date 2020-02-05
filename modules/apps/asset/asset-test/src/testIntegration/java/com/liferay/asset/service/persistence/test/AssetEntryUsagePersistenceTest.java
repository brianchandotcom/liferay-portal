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

package com.liferay.asset.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.exception.NoSuchEntryUsageException;
import com.liferay.asset.model.AssetEntryUsage;
import com.liferay.asset.service.AssetEntryUsageLocalServiceUtil;
import com.liferay.asset.service.persistence.AssetEntryUsagePersistence;
import com.liferay.asset.service.persistence.AssetEntryUsageUtil;
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
 * @deprecated As of Mueller (7.2.x), replaced by {@link
 com.liferay.layout.model.impl.LayoutClassedModelUsageImpl}
 * @generated
 */
@Deprecated
@RunWith(Arquillian.class)
public class AssetEntryUsagePersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.asset.service"));

	@Before
	public void setUp() {
		_persistence = AssetEntryUsageUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<AssetEntryUsage> iterator = _assetEntryUsages.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetEntryUsage assetEntryUsage = _persistence.create(pk);

		Assert.assertNotNull(assetEntryUsage);

		Assert.assertEquals(assetEntryUsage.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		AssetEntryUsage newAssetEntryUsage = addAssetEntryUsage();

		_persistence.remove(newAssetEntryUsage);

		AssetEntryUsage existingAssetEntryUsage =
			_persistence.fetchByPrimaryKey(newAssetEntryUsage.getPrimaryKey());

		Assert.assertNull(existingAssetEntryUsage);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addAssetEntryUsage();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetEntryUsage newAssetEntryUsage = _persistence.create(pk);

		newAssetEntryUsage.setMvccVersion(RandomTestUtil.nextLong());

		newAssetEntryUsage.setCtCollectionId(RandomTestUtil.nextLong());

		newAssetEntryUsage.setUuid(RandomTestUtil.randomString());

		newAssetEntryUsage.setGroupId(RandomTestUtil.nextLong());

		newAssetEntryUsage.setCompanyId(RandomTestUtil.nextLong());

		newAssetEntryUsage.setCreateDate(RandomTestUtil.nextDate());

		newAssetEntryUsage.setModifiedDate(RandomTestUtil.nextDate());

		newAssetEntryUsage.setAssetEntryId(RandomTestUtil.nextLong());

		newAssetEntryUsage.setContainerType(RandomTestUtil.nextLong());

		newAssetEntryUsage.setContainerKey(RandomTestUtil.randomString());

		newAssetEntryUsage.setPlid(RandomTestUtil.nextLong());

		newAssetEntryUsage.setType(RandomTestUtil.nextInt());

		newAssetEntryUsage.setLastPublishDate(RandomTestUtil.nextDate());

		_assetEntryUsages.add(_persistence.update(newAssetEntryUsage));

		AssetEntryUsage existingAssetEntryUsage = _persistence.findByPrimaryKey(
			newAssetEntryUsage.getPrimaryKey());

		Assert.assertEquals(
			existingAssetEntryUsage.getMvccVersion(),
			newAssetEntryUsage.getMvccVersion());
		Assert.assertEquals(
			existingAssetEntryUsage.getCtCollectionId(),
			newAssetEntryUsage.getCtCollectionId());
		Assert.assertEquals(
			existingAssetEntryUsage.getUuid(), newAssetEntryUsage.getUuid());
		Assert.assertEquals(
			existingAssetEntryUsage.getAssetEntryUsageId(),
			newAssetEntryUsage.getAssetEntryUsageId());
		Assert.assertEquals(
			existingAssetEntryUsage.getGroupId(),
			newAssetEntryUsage.getGroupId());
		Assert.assertEquals(
			existingAssetEntryUsage.getCompanyId(),
			newAssetEntryUsage.getCompanyId());
		Assert.assertEquals(
			Time.getShortTimestamp(existingAssetEntryUsage.getCreateDate()),
			Time.getShortTimestamp(newAssetEntryUsage.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingAssetEntryUsage.getModifiedDate()),
			Time.getShortTimestamp(newAssetEntryUsage.getModifiedDate()));
		Assert.assertEquals(
			existingAssetEntryUsage.getAssetEntryId(),
			newAssetEntryUsage.getAssetEntryId());
		Assert.assertEquals(
			existingAssetEntryUsage.getContainerType(),
			newAssetEntryUsage.getContainerType());
		Assert.assertEquals(
			existingAssetEntryUsage.getContainerKey(),
			newAssetEntryUsage.getContainerKey());
		Assert.assertEquals(
			existingAssetEntryUsage.getPlid(), newAssetEntryUsage.getPlid());
		Assert.assertEquals(
			existingAssetEntryUsage.getType(), newAssetEntryUsage.getType());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingAssetEntryUsage.getLastPublishDate()),
			Time.getShortTimestamp(newAssetEntryUsage.getLastPublishDate()));
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
	public void testCountByAssetEntryId() throws Exception {
		_persistence.countByAssetEntryId(RandomTestUtil.nextLong());

		_persistence.countByAssetEntryId(0L);
	}

	@Test
	public void testCountByPlid() throws Exception {
		_persistence.countByPlid(RandomTestUtil.nextLong());

		_persistence.countByPlid(0L);
	}

	@Test
	public void testCountByA_T() throws Exception {
		_persistence.countByA_T(
			RandomTestUtil.nextLong(), RandomTestUtil.nextInt());

		_persistence.countByA_T(0L, 0);
	}

	@Test
	public void testCountByC_C_P() throws Exception {
		_persistence.countByC_C_P(
			RandomTestUtil.nextLong(), "", RandomTestUtil.nextLong());

		_persistence.countByC_C_P(0L, "null", 0L);

		_persistence.countByC_C_P(0L, (String)null, 0L);
	}

	@Test
	public void testCountByA_C_C_P() throws Exception {
		_persistence.countByA_C_C_P(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(), "",
			RandomTestUtil.nextLong());

		_persistence.countByA_C_C_P(0L, 0L, "null", 0L);

		_persistence.countByA_C_C_P(0L, 0L, (String)null, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		AssetEntryUsage newAssetEntryUsage = addAssetEntryUsage();

		AssetEntryUsage existingAssetEntryUsage = _persistence.findByPrimaryKey(
			newAssetEntryUsage.getPrimaryKey());

		Assert.assertEquals(existingAssetEntryUsage, newAssetEntryUsage);
	}

	@Test(expected = NoSuchEntryUsageException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<AssetEntryUsage> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"AssetEntryUsage", "mvccVersion", true, "ctCollectionId", true,
			"uuid", true, "assetEntryUsageId", true, "groupId", true,
			"companyId", true, "createDate", true, "modifiedDate", true,
			"assetEntryId", true, "containerType", true, "containerKey", true,
			"plid", true, "type", true, "lastPublishDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		AssetEntryUsage newAssetEntryUsage = addAssetEntryUsage();

		AssetEntryUsage existingAssetEntryUsage =
			_persistence.fetchByPrimaryKey(newAssetEntryUsage.getPrimaryKey());

		Assert.assertEquals(existingAssetEntryUsage, newAssetEntryUsage);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetEntryUsage missingAssetEntryUsage = _persistence.fetchByPrimaryKey(
			pk);

		Assert.assertNull(missingAssetEntryUsage);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		AssetEntryUsage newAssetEntryUsage1 = addAssetEntryUsage();
		AssetEntryUsage newAssetEntryUsage2 = addAssetEntryUsage();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetEntryUsage1.getPrimaryKey());
		primaryKeys.add(newAssetEntryUsage2.getPrimaryKey());

		Map<Serializable, AssetEntryUsage> assetEntryUsages =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, assetEntryUsages.size());
		Assert.assertEquals(
			newAssetEntryUsage1,
			assetEntryUsages.get(newAssetEntryUsage1.getPrimaryKey()));
		Assert.assertEquals(
			newAssetEntryUsage2,
			assetEntryUsages.get(newAssetEntryUsage2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, AssetEntryUsage> assetEntryUsages =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(assetEntryUsages.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		AssetEntryUsage newAssetEntryUsage = addAssetEntryUsage();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetEntryUsage.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, AssetEntryUsage> assetEntryUsages =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, assetEntryUsages.size());
		Assert.assertEquals(
			newAssetEntryUsage,
			assetEntryUsages.get(newAssetEntryUsage.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, AssetEntryUsage> assetEntryUsages =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(assetEntryUsages.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		AssetEntryUsage newAssetEntryUsage = addAssetEntryUsage();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetEntryUsage.getPrimaryKey());

		Map<Serializable, AssetEntryUsage> assetEntryUsages =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, assetEntryUsages.size());
		Assert.assertEquals(
			newAssetEntryUsage,
			assetEntryUsages.get(newAssetEntryUsage.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			AssetEntryUsageLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<AssetEntryUsage>() {

				@Override
				public void performAction(AssetEntryUsage assetEntryUsage) {
					Assert.assertNotNull(assetEntryUsage);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		AssetEntryUsage newAssetEntryUsage = addAssetEntryUsage();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AssetEntryUsage.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"assetEntryUsageId",
				newAssetEntryUsage.getAssetEntryUsageId()));

		List<AssetEntryUsage> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		AssetEntryUsage existingAssetEntryUsage = result.get(0);

		Assert.assertEquals(existingAssetEntryUsage, newAssetEntryUsage);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AssetEntryUsage.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"assetEntryUsageId", RandomTestUtil.nextLong()));

		List<AssetEntryUsage> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		AssetEntryUsage newAssetEntryUsage = addAssetEntryUsage();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AssetEntryUsage.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("assetEntryUsageId"));

		Object newAssetEntryUsageId = newAssetEntryUsage.getAssetEntryUsageId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"assetEntryUsageId", new Object[] {newAssetEntryUsageId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingAssetEntryUsageId = result.get(0);

		Assert.assertEquals(existingAssetEntryUsageId, newAssetEntryUsageId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AssetEntryUsage.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("assetEntryUsageId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"assetEntryUsageId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		AssetEntryUsage newAssetEntryUsage = addAssetEntryUsage();

		_persistence.clearCache();

		AssetEntryUsage existingAssetEntryUsage = _persistence.findByPrimaryKey(
			newAssetEntryUsage.getPrimaryKey());

		Assert.assertTrue(
			Objects.equals(
				existingAssetEntryUsage.getUuid(),
				ReflectionTestUtil.invoke(
					existingAssetEntryUsage, "getOriginalUuid",
					new Class<?>[0])));
		Assert.assertEquals(
			Long.valueOf(existingAssetEntryUsage.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingAssetEntryUsage, "getOriginalGroupId",
				new Class<?>[0]));

		Assert.assertEquals(
			Long.valueOf(existingAssetEntryUsage.getAssetEntryId()),
			ReflectionTestUtil.<Long>invoke(
				existingAssetEntryUsage, "getOriginalAssetEntryId",
				new Class<?>[0]));
		Assert.assertEquals(
			Long.valueOf(existingAssetEntryUsage.getContainerType()),
			ReflectionTestUtil.<Long>invoke(
				existingAssetEntryUsage, "getOriginalContainerType",
				new Class<?>[0]));
		Assert.assertTrue(
			Objects.equals(
				existingAssetEntryUsage.getContainerKey(),
				ReflectionTestUtil.invoke(
					existingAssetEntryUsage, "getOriginalContainerKey",
					new Class<?>[0])));
		Assert.assertEquals(
			Long.valueOf(existingAssetEntryUsage.getPlid()),
			ReflectionTestUtil.<Long>invoke(
				existingAssetEntryUsage, "getOriginalPlid", new Class<?>[0]));
	}

	protected AssetEntryUsage addAssetEntryUsage() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetEntryUsage assetEntryUsage = _persistence.create(pk);

		assetEntryUsage.setMvccVersion(RandomTestUtil.nextLong());

		assetEntryUsage.setCtCollectionId(RandomTestUtil.nextLong());

		assetEntryUsage.setUuid(RandomTestUtil.randomString());

		assetEntryUsage.setGroupId(RandomTestUtil.nextLong());

		assetEntryUsage.setCompanyId(RandomTestUtil.nextLong());

		assetEntryUsage.setCreateDate(RandomTestUtil.nextDate());

		assetEntryUsage.setModifiedDate(RandomTestUtil.nextDate());

		assetEntryUsage.setAssetEntryId(RandomTestUtil.nextLong());

		assetEntryUsage.setContainerType(RandomTestUtil.nextLong());

		assetEntryUsage.setContainerKey(RandomTestUtil.randomString());

		assetEntryUsage.setPlid(RandomTestUtil.nextLong());

		assetEntryUsage.setType(RandomTestUtil.nextInt());

		assetEntryUsage.setLastPublishDate(RandomTestUtil.nextDate());

		_assetEntryUsages.add(_persistence.update(assetEntryUsage));

		return assetEntryUsage;
	}

	private List<AssetEntryUsage> _assetEntryUsages =
		new ArrayList<AssetEntryUsage>();
	private AssetEntryUsagePersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}