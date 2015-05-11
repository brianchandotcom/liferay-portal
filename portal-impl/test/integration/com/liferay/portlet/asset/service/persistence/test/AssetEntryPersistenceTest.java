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
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;

import com.liferay.portlet.asset.NoSuchEntryException;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.asset.service.persistence.AssetEntryPersistence;
import com.liferay.portlet.asset.service.persistence.AssetEntryUtil;

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
public class AssetEntryPersistenceTest {
	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = AssetEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<AssetEntry> iterator = _assetEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetEntry assetEntry = _persistence.create(pk);

		Assert.assertNotNull(assetEntry);

		Assert.assertEquals(assetEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		AssetEntry newAssetEntry = addAssetEntry();

		_persistence.remove(newAssetEntry);

		AssetEntry existingAssetEntry = _persistence.fetchByPrimaryKey(newAssetEntry.getPrimaryKey());

		Assert.assertNull(existingAssetEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addAssetEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetEntry newAssetEntry = _persistence.create(pk);

		newAssetEntry.setGroupId(RandomTestUtil.nextLong());

		newAssetEntry.setCompanyId(RandomTestUtil.nextLong());

		newAssetEntry.setUserId(RandomTestUtil.nextLong());

		newAssetEntry.setUserName(RandomTestUtil.randomString());

		newAssetEntry.setCreateDate(RandomTestUtil.nextDate());

		newAssetEntry.setModifiedDate(RandomTestUtil.nextDate());

		newAssetEntry.setClassNameId(RandomTestUtil.nextLong());

		newAssetEntry.setClassPK(RandomTestUtil.nextLong());

		newAssetEntry.setClassUuid(RandomTestUtil.randomString());

		newAssetEntry.setClassTypeId(RandomTestUtil.nextLong());

		newAssetEntry.setListable(RandomTestUtil.randomBoolean());

		newAssetEntry.setVisible(RandomTestUtil.randomBoolean());

		newAssetEntry.setStartDate(RandomTestUtil.nextDate());

		newAssetEntry.setEndDate(RandomTestUtil.nextDate());

		newAssetEntry.setPublishDate(RandomTestUtil.nextDate());

		newAssetEntry.setExpirationDate(RandomTestUtil.nextDate());

		newAssetEntry.setMimeType(RandomTestUtil.randomString());

		newAssetEntry.setTitle(RandomTestUtil.randomString());

		newAssetEntry.setDescription(RandomTestUtil.randomString());

		newAssetEntry.setSummary(RandomTestUtil.randomString());

		newAssetEntry.setUrl(RandomTestUtil.randomString());

		newAssetEntry.setLayoutUuid(RandomTestUtil.randomString());

		newAssetEntry.setHeight(RandomTestUtil.nextInt());

		newAssetEntry.setWidth(RandomTestUtil.nextInt());

		newAssetEntry.setPriority(RandomTestUtil.nextDouble());

		newAssetEntry.setViewCount(RandomTestUtil.nextInt());

		_assetEntries.add(_persistence.update(newAssetEntry));

		AssetEntry existingAssetEntry = _persistence.findByPrimaryKey(newAssetEntry.getPrimaryKey());

		Assert.assertEquals(existingAssetEntry.getEntryId(),
			newAssetEntry.getEntryId());
		Assert.assertEquals(existingAssetEntry.getGroupId(),
			newAssetEntry.getGroupId());
		Assert.assertEquals(existingAssetEntry.getCompanyId(),
			newAssetEntry.getCompanyId());
		Assert.assertEquals(existingAssetEntry.getUserId(),
			newAssetEntry.getUserId());
		Assert.assertEquals(existingAssetEntry.getUserName(),
			newAssetEntry.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingAssetEntry.getCreateDate()),
			Time.getShortTimestamp(newAssetEntry.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingAssetEntry.getModifiedDate()),
			Time.getShortTimestamp(newAssetEntry.getModifiedDate()));
		Assert.assertEquals(existingAssetEntry.getClassNameId(),
			newAssetEntry.getClassNameId());
		Assert.assertEquals(existingAssetEntry.getClassPK(),
			newAssetEntry.getClassPK());
		Assert.assertEquals(existingAssetEntry.getClassUuid(),
			newAssetEntry.getClassUuid());
		Assert.assertEquals(existingAssetEntry.getClassTypeId(),
			newAssetEntry.getClassTypeId());
		Assert.assertEquals(existingAssetEntry.getListable(),
			newAssetEntry.getListable());
		Assert.assertEquals(existingAssetEntry.getVisible(),
			newAssetEntry.getVisible());
		Assert.assertEquals(Time.getShortTimestamp(
				existingAssetEntry.getStartDate()),
			Time.getShortTimestamp(newAssetEntry.getStartDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingAssetEntry.getEndDate()),
			Time.getShortTimestamp(newAssetEntry.getEndDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingAssetEntry.getPublishDate()),
			Time.getShortTimestamp(newAssetEntry.getPublishDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingAssetEntry.getExpirationDate()),
			Time.getShortTimestamp(newAssetEntry.getExpirationDate()));
		Assert.assertEquals(existingAssetEntry.getMimeType(),
			newAssetEntry.getMimeType());
		Assert.assertEquals(existingAssetEntry.getTitle(),
			newAssetEntry.getTitle());
		Assert.assertEquals(existingAssetEntry.getDescription(),
			newAssetEntry.getDescription());
		Assert.assertEquals(existingAssetEntry.getSummary(),
			newAssetEntry.getSummary());
		Assert.assertEquals(existingAssetEntry.getUrl(), newAssetEntry.getUrl());
		Assert.assertEquals(existingAssetEntry.getLayoutUuid(),
			newAssetEntry.getLayoutUuid());
		Assert.assertEquals(existingAssetEntry.getHeight(),
			newAssetEntry.getHeight());
		Assert.assertEquals(existingAssetEntry.getWidth(),
			newAssetEntry.getWidth());
		AssertUtils.assertEquals(existingAssetEntry.getPriority(),
			newAssetEntry.getPriority());
		Assert.assertEquals(existingAssetEntry.getViewCount(),
			newAssetEntry.getViewCount());
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
	public void testCountByVisible() throws Exception {
		_persistence.countByVisible(RandomTestUtil.randomBoolean());

		_persistence.countByVisible(RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByPublishDate() throws Exception {
		_persistence.countByPublishDate(RandomTestUtil.nextDate());

		_persistence.countByPublishDate(RandomTestUtil.nextDate());
	}

	@Test
	public void testCountByExpirationDate() throws Exception {
		_persistence.countByExpirationDate(RandomTestUtil.nextDate());

		_persistence.countByExpirationDate(RandomTestUtil.nextDate());
	}

	@Test
	public void testCountByLayoutUuid() throws Exception {
		_persistence.countByLayoutUuid(StringPool.BLANK);

		_persistence.countByLayoutUuid(StringPool.NULL);

		_persistence.countByLayoutUuid((String)null);
	}

	@Test
	public void testCountByG_CU() throws Exception {
		_persistence.countByG_CU(RandomTestUtil.nextLong(), StringPool.BLANK);

		_persistence.countByG_CU(0L, StringPool.NULL);

		_persistence.countByG_CU(0L, (String)null);
	}

	@Test
	public void testCountByC_C() throws Exception {
		_persistence.countByC_C(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByC_C(0L, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		AssetEntry newAssetEntry = addAssetEntry();

		AssetEntry existingAssetEntry = _persistence.findByPrimaryKey(newAssetEntry.getPrimaryKey());

		Assert.assertEquals(existingAssetEntry, newAssetEntry);
	}

	@Test(expected = NoSuchEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<AssetEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("AssetEntry", "entryId",
			true, "groupId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true,
			"classNameId", true, "classPK", true, "classUuid", true,
			"classTypeId", true, "listable", true, "visible", true,
			"startDate", true, "endDate", true, "publishDate", true,
			"expirationDate", true, "mimeType", true, "title", true,
			"description", true, "summary", true, "url", true, "layoutUuid",
			true, "height", true, "width", true, "priority", true, "viewCount",
			true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		AssetEntry newAssetEntry = addAssetEntry();

		AssetEntry existingAssetEntry = _persistence.fetchByPrimaryKey(newAssetEntry.getPrimaryKey());

		Assert.assertEquals(existingAssetEntry, newAssetEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetEntry missingAssetEntry = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingAssetEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		AssetEntry newAssetEntry1 = addAssetEntry();
		AssetEntry newAssetEntry2 = addAssetEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetEntry1.getPrimaryKey());
		primaryKeys.add(newAssetEntry2.getPrimaryKey());

		Map<Serializable, AssetEntry> assetEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, assetEntries.size());
		Assert.assertEquals(newAssetEntry1,
			assetEntries.get(newAssetEntry1.getPrimaryKey()));
		Assert.assertEquals(newAssetEntry2,
			assetEntries.get(newAssetEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, AssetEntry> assetEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(assetEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		AssetEntry newAssetEntry = addAssetEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, AssetEntry> assetEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, assetEntries.size());
		Assert.assertEquals(newAssetEntry,
			assetEntries.get(newAssetEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, AssetEntry> assetEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(assetEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		AssetEntry newAssetEntry = addAssetEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetEntry.getPrimaryKey());

		Map<Serializable, AssetEntry> assetEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, assetEntries.size());
		Assert.assertEquals(newAssetEntry,
			assetEntries.get(newAssetEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = AssetEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					AssetEntry assetEntry = (AssetEntry)object;

					Assert.assertNotNull(assetEntry);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		AssetEntry newAssetEntry = addAssetEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("entryId",
				newAssetEntry.getEntryId()));

		List<AssetEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		AssetEntry existingAssetEntry = result.get(0);

		Assert.assertEquals(existingAssetEntry, newAssetEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("entryId",
				RandomTestUtil.nextLong()));

		List<AssetEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		AssetEntry newAssetEntry = addAssetEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("entryId"));

		Object newEntryId = newAssetEntry.getEntryId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("entryId",
				new Object[] { newEntryId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingEntryId = result.get(0);

		Assert.assertEquals(existingEntryId, newEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("entryId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("entryId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		AssetEntry newAssetEntry = addAssetEntry();

		_persistence.clearCache();

		AssetEntry existingAssetEntry = _persistence.findByPrimaryKey(newAssetEntry.getPrimaryKey());

		Assert.assertEquals(existingAssetEntry.getGroupId(),
			ReflectionTestUtil.invoke(existingAssetEntry, "getOriginalGroupId",
				new Class<?>[0]));
		Assert.assertTrue(Validator.equals(existingAssetEntry.getClassUuid(),
				ReflectionTestUtil.invoke(existingAssetEntry,
					"getOriginalClassUuid", new Class<?>[0])));

		Assert.assertEquals(existingAssetEntry.getClassNameId(),
			ReflectionTestUtil.invoke(existingAssetEntry,
				"getOriginalClassNameId", new Class<?>[0]));
		Assert.assertEquals(existingAssetEntry.getClassPK(),
			ReflectionTestUtil.invoke(existingAssetEntry, "getOriginalClassPK",
				new Class<?>[0]));
	}

	protected AssetEntry addAssetEntry() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetEntry assetEntry = _persistence.create(pk);

		assetEntry.setGroupId(RandomTestUtil.nextLong());

		assetEntry.setCompanyId(RandomTestUtil.nextLong());

		assetEntry.setUserId(RandomTestUtil.nextLong());

		assetEntry.setUserName(RandomTestUtil.randomString());

		assetEntry.setCreateDate(RandomTestUtil.nextDate());

		assetEntry.setModifiedDate(RandomTestUtil.nextDate());

		assetEntry.setClassNameId(RandomTestUtil.nextLong());

		assetEntry.setClassPK(RandomTestUtil.nextLong());

		assetEntry.setClassUuid(RandomTestUtil.randomString());

		assetEntry.setClassTypeId(RandomTestUtil.nextLong());

		assetEntry.setListable(RandomTestUtil.randomBoolean());

		assetEntry.setVisible(RandomTestUtil.randomBoolean());

		assetEntry.setStartDate(RandomTestUtil.nextDate());

		assetEntry.setEndDate(RandomTestUtil.nextDate());

		assetEntry.setPublishDate(RandomTestUtil.nextDate());

		assetEntry.setExpirationDate(RandomTestUtil.nextDate());

		assetEntry.setMimeType(RandomTestUtil.randomString());

		assetEntry.setTitle(RandomTestUtil.randomString());

		assetEntry.setDescription(RandomTestUtil.randomString());

		assetEntry.setSummary(RandomTestUtil.randomString());

		assetEntry.setUrl(RandomTestUtil.randomString());

		assetEntry.setLayoutUuid(RandomTestUtil.randomString());

		assetEntry.setHeight(RandomTestUtil.nextInt());

		assetEntry.setWidth(RandomTestUtil.nextInt());

		assetEntry.setPriority(RandomTestUtil.nextDouble());

		assetEntry.setViewCount(RandomTestUtil.nextInt());

		_assetEntries.add(_persistence.update(assetEntry));

		return assetEntry;
	}

	private List<AssetEntry> _assetEntries = new ArrayList<AssetEntry>();
	private AssetEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}