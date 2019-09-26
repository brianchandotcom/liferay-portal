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
import com.liferay.asset.list.exception.NoSuchEntryException;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryLocalServiceUtil;
import com.liferay.asset.list.service.persistence.AssetListEntryPersistence;
import com.liferay.asset.list.service.persistence.AssetListEntryUtil;
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
public class AssetListEntryPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.asset.list.service"));

	@Before
	public void setUp() {
		_persistence = AssetListEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<AssetListEntry> iterator = _assetListEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetListEntry assetListEntry = _persistence.create(pk);

		Assert.assertNotNull(assetListEntry);

		Assert.assertEquals(assetListEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		AssetListEntry newAssetListEntry = addAssetListEntry();

		_persistence.remove(newAssetListEntry);

		AssetListEntry existingAssetListEntry = _persistence.fetchByPrimaryKey(
			newAssetListEntry.getPrimaryKey());

		Assert.assertNull(existingAssetListEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addAssetListEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetListEntry newAssetListEntry = _persistence.create(pk);

		newAssetListEntry.setMvccVersion(RandomTestUtil.nextLong());

		newAssetListEntry.setUuid(RandomTestUtil.randomString());

		newAssetListEntry.setGroupId(RandomTestUtil.nextLong());

		newAssetListEntry.setCompanyId(RandomTestUtil.nextLong());

		newAssetListEntry.setUserId(RandomTestUtil.nextLong());

		newAssetListEntry.setUserName(RandomTestUtil.randomString());

		newAssetListEntry.setCreateDate(RandomTestUtil.nextDate());

		newAssetListEntry.setModifiedDate(RandomTestUtil.nextDate());

		newAssetListEntry.setAssetListEntryKey(RandomTestUtil.randomString());

		newAssetListEntry.setTitle(RandomTestUtil.randomString());

		newAssetListEntry.setType(RandomTestUtil.nextInt());

		newAssetListEntry.setLastPublishDate(RandomTestUtil.nextDate());

		_assetListEntries.add(_persistence.update(newAssetListEntry));

		AssetListEntry existingAssetListEntry = _persistence.findByPrimaryKey(
			newAssetListEntry.getPrimaryKey());

		Assert.assertEquals(
			existingAssetListEntry.getMvccVersion(),
			newAssetListEntry.getMvccVersion());
		Assert.assertEquals(
			existingAssetListEntry.getUuid(), newAssetListEntry.getUuid());
		Assert.assertEquals(
			existingAssetListEntry.getAssetListEntryId(),
			newAssetListEntry.getAssetListEntryId());
		Assert.assertEquals(
			existingAssetListEntry.getGroupId(),
			newAssetListEntry.getGroupId());
		Assert.assertEquals(
			existingAssetListEntry.getCompanyId(),
			newAssetListEntry.getCompanyId());
		Assert.assertEquals(
			existingAssetListEntry.getUserId(), newAssetListEntry.getUserId());
		Assert.assertEquals(
			existingAssetListEntry.getUserName(),
			newAssetListEntry.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingAssetListEntry.getCreateDate()),
			Time.getShortTimestamp(newAssetListEntry.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingAssetListEntry.getModifiedDate()),
			Time.getShortTimestamp(newAssetListEntry.getModifiedDate()));
		Assert.assertEquals(
			existingAssetListEntry.getAssetListEntryKey(),
			newAssetListEntry.getAssetListEntryKey());
		Assert.assertEquals(
			existingAssetListEntry.getTitle(), newAssetListEntry.getTitle());
		Assert.assertEquals(
			existingAssetListEntry.getType(), newAssetListEntry.getType());
		Assert.assertEquals(
			Time.getShortTimestamp(existingAssetListEntry.getLastPublishDate()),
			Time.getShortTimestamp(newAssetListEntry.getLastPublishDate()));
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
	public void testCountByGroupId() throws Exception {
		_persistence.countByGroupId(RandomTestUtil.nextLong());

		_persistence.countByGroupId(0L);
	}

	@Test
	public void testCountByG_ALEK() throws Exception {
		_persistence.countByG_ALEK(RandomTestUtil.nextLong(), "");

		_persistence.countByG_ALEK(0L, "null");

		_persistence.countByG_ALEK(0L, (String)null);
	}

	@Test
	public void testCountByG_T() throws Exception {
		_persistence.countByG_T(RandomTestUtil.nextLong(), "");

		_persistence.countByG_T(0L, "null");

		_persistence.countByG_T(0L, (String)null);
	}

	@Test
	public void testCountByG_LikeT() throws Exception {
		_persistence.countByG_LikeT(RandomTestUtil.nextLong(), "");

		_persistence.countByG_LikeT(0L, "null");

		_persistence.countByG_LikeT(0L, (String)null);
	}

	@Test
	public void testCountByG_TY() throws Exception {
		_persistence.countByG_TY(
			RandomTestUtil.nextLong(), RandomTestUtil.nextInt());

		_persistence.countByG_TY(0L, 0);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		AssetListEntry newAssetListEntry = addAssetListEntry();

		AssetListEntry existingAssetListEntry = _persistence.findByPrimaryKey(
			newAssetListEntry.getPrimaryKey());

		Assert.assertEquals(existingAssetListEntry, newAssetListEntry);
	}

	@Test(expected = NoSuchEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	@Test
	public void testFilterFindByGroupId() throws Exception {
		_persistence.filterFindByGroupId(
			0, QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<AssetListEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"AssetListEntry", "mvccVersion", true, "uuid", true,
			"assetListEntryId", true, "groupId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "assetListEntryKey", true, "title", true,
			"type", true, "lastPublishDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		AssetListEntry newAssetListEntry = addAssetListEntry();

		AssetListEntry existingAssetListEntry = _persistence.fetchByPrimaryKey(
			newAssetListEntry.getPrimaryKey());

		Assert.assertEquals(existingAssetListEntry, newAssetListEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetListEntry missingAssetListEntry = _persistence.fetchByPrimaryKey(
			pk);

		Assert.assertNull(missingAssetListEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		AssetListEntry newAssetListEntry1 = addAssetListEntry();
		AssetListEntry newAssetListEntry2 = addAssetListEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetListEntry1.getPrimaryKey());
		primaryKeys.add(newAssetListEntry2.getPrimaryKey());

		Map<Serializable, AssetListEntry> assetListEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, assetListEntries.size());
		Assert.assertEquals(
			newAssetListEntry1,
			assetListEntries.get(newAssetListEntry1.getPrimaryKey()));
		Assert.assertEquals(
			newAssetListEntry2,
			assetListEntries.get(newAssetListEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, AssetListEntry> assetListEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(assetListEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		AssetListEntry newAssetListEntry = addAssetListEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetListEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, AssetListEntry> assetListEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, assetListEntries.size());
		Assert.assertEquals(
			newAssetListEntry,
			assetListEntries.get(newAssetListEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, AssetListEntry> assetListEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(assetListEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		AssetListEntry newAssetListEntry = addAssetListEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetListEntry.getPrimaryKey());

		Map<Serializable, AssetListEntry> assetListEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, assetListEntries.size());
		Assert.assertEquals(
			newAssetListEntry,
			assetListEntries.get(newAssetListEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			AssetListEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<AssetListEntry>() {

				@Override
				public void performAction(AssetListEntry assetListEntry) {
					Assert.assertNotNull(assetListEntry);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		AssetListEntry newAssetListEntry = addAssetListEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AssetListEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"assetListEntryId", newAssetListEntry.getAssetListEntryId()));

		List<AssetListEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		AssetListEntry existingAssetListEntry = result.get(0);

		Assert.assertEquals(existingAssetListEntry, newAssetListEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AssetListEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"assetListEntryId", RandomTestUtil.nextLong()));

		List<AssetListEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		AssetListEntry newAssetListEntry = addAssetListEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AssetListEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("assetListEntryId"));

		Object newAssetListEntryId = newAssetListEntry.getAssetListEntryId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"assetListEntryId", new Object[] {newAssetListEntryId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingAssetListEntryId = result.get(0);

		Assert.assertEquals(existingAssetListEntryId, newAssetListEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AssetListEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("assetListEntryId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"assetListEntryId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		AssetListEntry newAssetListEntry = addAssetListEntry();

		_persistence.clearCache();

		AssetListEntry existingAssetListEntry = _persistence.findByPrimaryKey(
			newAssetListEntry.getPrimaryKey());

		Assert.assertTrue(
			Objects.equals(
				existingAssetListEntry.getUuid(),
				ReflectionTestUtil.invoke(
					existingAssetListEntry, "getOriginalUuid",
					new Class<?>[0])));
		Assert.assertEquals(
			Long.valueOf(existingAssetListEntry.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingAssetListEntry, "getOriginalGroupId", new Class<?>[0]));

		Assert.assertEquals(
			Long.valueOf(existingAssetListEntry.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingAssetListEntry, "getOriginalGroupId", new Class<?>[0]));
		Assert.assertTrue(
			Objects.equals(
				existingAssetListEntry.getAssetListEntryKey(),
				ReflectionTestUtil.invoke(
					existingAssetListEntry, "getOriginalAssetListEntryKey",
					new Class<?>[0])));

		Assert.assertEquals(
			Long.valueOf(existingAssetListEntry.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingAssetListEntry, "getOriginalGroupId", new Class<?>[0]));
		Assert.assertTrue(
			Objects.equals(
				existingAssetListEntry.getTitle(),
				ReflectionTestUtil.invoke(
					existingAssetListEntry, "getOriginalTitle",
					new Class<?>[0])));
	}

	protected AssetListEntry addAssetListEntry() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetListEntry assetListEntry = _persistence.create(pk);

		assetListEntry.setMvccVersion(RandomTestUtil.nextLong());

		assetListEntry.setUuid(RandomTestUtil.randomString());

		assetListEntry.setGroupId(RandomTestUtil.nextLong());

		assetListEntry.setCompanyId(RandomTestUtil.nextLong());

		assetListEntry.setUserId(RandomTestUtil.nextLong());

		assetListEntry.setUserName(RandomTestUtil.randomString());

		assetListEntry.setCreateDate(RandomTestUtil.nextDate());

		assetListEntry.setModifiedDate(RandomTestUtil.nextDate());

		assetListEntry.setAssetListEntryKey(RandomTestUtil.randomString());

		assetListEntry.setTitle(RandomTestUtil.randomString());

		assetListEntry.setType(RandomTestUtil.nextInt());

		assetListEntry.setLastPublishDate(RandomTestUtil.nextDate());

		_assetListEntries.add(_persistence.update(assetListEntry));

		return assetListEntry;
	}

	private List<AssetListEntry> _assetListEntries =
		new ArrayList<AssetListEntry>();
	private AssetListEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}