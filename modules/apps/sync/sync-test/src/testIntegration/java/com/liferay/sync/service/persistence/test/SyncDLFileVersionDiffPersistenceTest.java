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

package com.liferay.sync.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
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
import com.liferay.sync.exception.NoSuchDLFileVersionDiffException;
import com.liferay.sync.model.SyncDLFileVersionDiff;
import com.liferay.sync.service.SyncDLFileVersionDiffLocalServiceUtil;
import com.liferay.sync.service.persistence.SyncDLFileVersionDiffPersistence;
import com.liferay.sync.service.persistence.SyncDLFileVersionDiffUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
public class SyncDLFileVersionDiffPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.sync.service"));

	@Before
	public void setUp() {
		_persistence = SyncDLFileVersionDiffUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<SyncDLFileVersionDiff> iterator =
			_syncDLFileVersionDiffs.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SyncDLFileVersionDiff syncDLFileVersionDiff = _persistence.create(pk);

		Assert.assertNotNull(syncDLFileVersionDiff);

		Assert.assertEquals(syncDLFileVersionDiff.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		SyncDLFileVersionDiff newSyncDLFileVersionDiff =
			addSyncDLFileVersionDiff();

		_persistence.remove(newSyncDLFileVersionDiff);

		SyncDLFileVersionDiff existingSyncDLFileVersionDiff =
			_persistence.fetchByPrimaryKey(
				newSyncDLFileVersionDiff.getPrimaryKey());

		Assert.assertNull(existingSyncDLFileVersionDiff);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addSyncDLFileVersionDiff();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SyncDLFileVersionDiff newSyncDLFileVersionDiff = _persistence.create(
			pk);

		newSyncDLFileVersionDiff.setCompanyId(RandomTestUtil.nextLong());

		newSyncDLFileVersionDiff.setFileEntryId(RandomTestUtil.nextLong());

		newSyncDLFileVersionDiff.setSourceFileVersionId(
			RandomTestUtil.nextLong());

		newSyncDLFileVersionDiff.setTargetFileVersionId(
			RandomTestUtil.nextLong());

		newSyncDLFileVersionDiff.setDataFileEntryId(RandomTestUtil.nextLong());

		newSyncDLFileVersionDiff.setSize(RandomTestUtil.nextLong());

		newSyncDLFileVersionDiff.setExpirationDate(RandomTestUtil.nextDate());

		_syncDLFileVersionDiffs.add(
			_persistence.update(newSyncDLFileVersionDiff));

		SyncDLFileVersionDiff existingSyncDLFileVersionDiff =
			_persistence.findByPrimaryKey(
				newSyncDLFileVersionDiff.getPrimaryKey());

		Assert.assertEquals(
			existingSyncDLFileVersionDiff.getSyncDLFileVersionDiffId(),
			newSyncDLFileVersionDiff.getSyncDLFileVersionDiffId());
		Assert.assertEquals(
			existingSyncDLFileVersionDiff.getCompanyId(),
			newSyncDLFileVersionDiff.getCompanyId());
		Assert.assertEquals(
			existingSyncDLFileVersionDiff.getFileEntryId(),
			newSyncDLFileVersionDiff.getFileEntryId());
		Assert.assertEquals(
			existingSyncDLFileVersionDiff.getSourceFileVersionId(),
			newSyncDLFileVersionDiff.getSourceFileVersionId());
		Assert.assertEquals(
			existingSyncDLFileVersionDiff.getTargetFileVersionId(),
			newSyncDLFileVersionDiff.getTargetFileVersionId());
		Assert.assertEquals(
			existingSyncDLFileVersionDiff.getDataFileEntryId(),
			newSyncDLFileVersionDiff.getDataFileEntryId());
		Assert.assertEquals(
			existingSyncDLFileVersionDiff.getSize(),
			newSyncDLFileVersionDiff.getSize());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingSyncDLFileVersionDiff.getExpirationDate()),
			Time.getShortTimestamp(
				newSyncDLFileVersionDiff.getExpirationDate()));
	}

	@Test
	public void testCountByFileEntryId() throws Exception {
		_persistence.countByFileEntryId(RandomTestUtil.nextLong());

		_persistence.countByFileEntryId(0L);
	}

	@Test
	public void testCountByExpirationDate() throws Exception {
		_persistence.countByExpirationDate(RandomTestUtil.nextDate());

		_persistence.countByExpirationDate(RandomTestUtil.nextDate());
	}

	@Test
	public void testCountByF_S_T() throws Exception {
		_persistence.countByF_S_T(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByF_S_T(0L, 0L, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		SyncDLFileVersionDiff newSyncDLFileVersionDiff =
			addSyncDLFileVersionDiff();

		SyncDLFileVersionDiff existingSyncDLFileVersionDiff =
			_persistence.findByPrimaryKey(
				newSyncDLFileVersionDiff.getPrimaryKey());

		Assert.assertEquals(
			existingSyncDLFileVersionDiff, newSyncDLFileVersionDiff);
	}

	@Test(expected = NoSuchDLFileVersionDiffException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<SyncDLFileVersionDiff> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"SyncDLFileVersionDiff", "syncDLFileVersionDiffId", true,
			"companyId", true, "fileEntryId", true, "sourceFileVersionId", true,
			"targetFileVersionId", true, "dataFileEntryId", true, "size", true,
			"expirationDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		SyncDLFileVersionDiff newSyncDLFileVersionDiff =
			addSyncDLFileVersionDiff();

		SyncDLFileVersionDiff existingSyncDLFileVersionDiff =
			_persistence.fetchByPrimaryKey(
				newSyncDLFileVersionDiff.getPrimaryKey());

		Assert.assertEquals(
			existingSyncDLFileVersionDiff, newSyncDLFileVersionDiff);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SyncDLFileVersionDiff missingSyncDLFileVersionDiff =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingSyncDLFileVersionDiff);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		SyncDLFileVersionDiff newSyncDLFileVersionDiff1 =
			addSyncDLFileVersionDiff();
		SyncDLFileVersionDiff newSyncDLFileVersionDiff2 =
			addSyncDLFileVersionDiff();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSyncDLFileVersionDiff1.getPrimaryKey());
		primaryKeys.add(newSyncDLFileVersionDiff2.getPrimaryKey());

		Map<Serializable, SyncDLFileVersionDiff> syncDLFileVersionDiffs =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, syncDLFileVersionDiffs.size());
		Assert.assertEquals(
			newSyncDLFileVersionDiff1,
			syncDLFileVersionDiffs.get(
				newSyncDLFileVersionDiff1.getPrimaryKey()));
		Assert.assertEquals(
			newSyncDLFileVersionDiff2,
			syncDLFileVersionDiffs.get(
				newSyncDLFileVersionDiff2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, SyncDLFileVersionDiff> syncDLFileVersionDiffs =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(syncDLFileVersionDiffs.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		SyncDLFileVersionDiff newSyncDLFileVersionDiff =
			addSyncDLFileVersionDiff();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSyncDLFileVersionDiff.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, SyncDLFileVersionDiff> syncDLFileVersionDiffs =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, syncDLFileVersionDiffs.size());
		Assert.assertEquals(
			newSyncDLFileVersionDiff,
			syncDLFileVersionDiffs.get(
				newSyncDLFileVersionDiff.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, SyncDLFileVersionDiff> syncDLFileVersionDiffs =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(syncDLFileVersionDiffs.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		SyncDLFileVersionDiff newSyncDLFileVersionDiff =
			addSyncDLFileVersionDiff();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSyncDLFileVersionDiff.getPrimaryKey());

		Map<Serializable, SyncDLFileVersionDiff> syncDLFileVersionDiffs =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, syncDLFileVersionDiffs.size());
		Assert.assertEquals(
			newSyncDLFileVersionDiff,
			syncDLFileVersionDiffs.get(
				newSyncDLFileVersionDiff.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			SyncDLFileVersionDiffLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<SyncDLFileVersionDiff>() {

				@Override
				public void performAction(
					SyncDLFileVersionDiff syncDLFileVersionDiff) {

					Assert.assertNotNull(syncDLFileVersionDiff);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		SyncDLFileVersionDiff newSyncDLFileVersionDiff =
			addSyncDLFileVersionDiff();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			SyncDLFileVersionDiff.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"syncDLFileVersionDiffId",
				newSyncDLFileVersionDiff.getSyncDLFileVersionDiffId()));

		List<SyncDLFileVersionDiff> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		SyncDLFileVersionDiff existingSyncDLFileVersionDiff = result.get(0);

		Assert.assertEquals(
			existingSyncDLFileVersionDiff, newSyncDLFileVersionDiff);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			SyncDLFileVersionDiff.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"syncDLFileVersionDiffId", RandomTestUtil.nextLong()));

		List<SyncDLFileVersionDiff> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		SyncDLFileVersionDiff newSyncDLFileVersionDiff =
			addSyncDLFileVersionDiff();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			SyncDLFileVersionDiff.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("syncDLFileVersionDiffId"));

		Object newSyncDLFileVersionDiffId =
			newSyncDLFileVersionDiff.getSyncDLFileVersionDiffId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"syncDLFileVersionDiffId",
				new Object[] {newSyncDLFileVersionDiffId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingSyncDLFileVersionDiffId = result.get(0);

		Assert.assertEquals(
			existingSyncDLFileVersionDiffId, newSyncDLFileVersionDiffId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			SyncDLFileVersionDiff.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("syncDLFileVersionDiffId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"syncDLFileVersionDiffId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		SyncDLFileVersionDiff newSyncDLFileVersionDiff =
			addSyncDLFileVersionDiff();

		_persistence.clearCache();

		SyncDLFileVersionDiff existingSyncDLFileVersionDiff =
			_persistence.findByPrimaryKey(
				newSyncDLFileVersionDiff.getPrimaryKey());

		Assert.assertEquals(
			Long.valueOf(existingSyncDLFileVersionDiff.getFileEntryId()),
			ReflectionTestUtil.<Long>invoke(
				existingSyncDLFileVersionDiff, "getOriginalFileEntryId",
				new Class<?>[0]));
		Assert.assertEquals(
			Long.valueOf(
				existingSyncDLFileVersionDiff.getSourceFileVersionId()),
			ReflectionTestUtil.<Long>invoke(
				existingSyncDLFileVersionDiff, "getOriginalSourceFileVersionId",
				new Class<?>[0]));
		Assert.assertEquals(
			Long.valueOf(
				existingSyncDLFileVersionDiff.getTargetFileVersionId()),
			ReflectionTestUtil.<Long>invoke(
				existingSyncDLFileVersionDiff, "getOriginalTargetFileVersionId",
				new Class<?>[0]));
	}

	protected SyncDLFileVersionDiff addSyncDLFileVersionDiff()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		SyncDLFileVersionDiff syncDLFileVersionDiff = _persistence.create(pk);

		syncDLFileVersionDiff.setCompanyId(RandomTestUtil.nextLong());

		syncDLFileVersionDiff.setFileEntryId(RandomTestUtil.nextLong());

		syncDLFileVersionDiff.setSourceFileVersionId(RandomTestUtil.nextLong());

		syncDLFileVersionDiff.setTargetFileVersionId(RandomTestUtil.nextLong());

		syncDLFileVersionDiff.setDataFileEntryId(RandomTestUtil.nextLong());

		syncDLFileVersionDiff.setSize(RandomTestUtil.nextLong());

		syncDLFileVersionDiff.setExpirationDate(RandomTestUtil.nextDate());

		_syncDLFileVersionDiffs.add(_persistence.update(syncDLFileVersionDiff));

		return syncDLFileVersionDiff;
	}

	private List<SyncDLFileVersionDiff> _syncDLFileVersionDiffs =
		new ArrayList<SyncDLFileVersionDiff>();
	private SyncDLFileVersionDiffPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}