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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import com.liferay.sync.exception.NoSuchDLObjectException;
import com.liferay.sync.model.SyncDLObject;
import com.liferay.sync.service.SyncDLObjectLocalServiceUtil;
import com.liferay.sync.service.persistence.SyncDLObjectPersistence;
import com.liferay.sync.service.persistence.SyncDLObjectUtil;

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
import java.util.Objects;
import java.util.Set;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class SyncDLObjectPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.sync.service"));

	@Before
	public void setUp() {
		_persistence = SyncDLObjectUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<SyncDLObject> iterator = _syncDLObjects.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SyncDLObject syncDLObject = _persistence.create(pk);

		Assert.assertNotNull(syncDLObject);

		Assert.assertEquals(syncDLObject.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		SyncDLObject newSyncDLObject = addSyncDLObject();

		_persistence.remove(newSyncDLObject);

		SyncDLObject existingSyncDLObject = _persistence.fetchByPrimaryKey(newSyncDLObject.getPrimaryKey());

		Assert.assertNull(existingSyncDLObject);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addSyncDLObject();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SyncDLObject newSyncDLObject = _persistence.create(pk);

		newSyncDLObject.setCompanyId(RandomTestUtil.nextLong());

		newSyncDLObject.setUserId(RandomTestUtil.nextLong());

		newSyncDLObject.setUserName(RandomTestUtil.randomString());

		newSyncDLObject.setCreateTime(RandomTestUtil.nextLong());

		newSyncDLObject.setModifiedTime(RandomTestUtil.nextLong());

		newSyncDLObject.setRepositoryId(RandomTestUtil.nextLong());

		newSyncDLObject.setParentFolderId(RandomTestUtil.nextLong());

		newSyncDLObject.setTreePath(RandomTestUtil.randomString());

		newSyncDLObject.setName(RandomTestUtil.randomString());

		newSyncDLObject.setExtension(RandomTestUtil.randomString());

		newSyncDLObject.setMimeType(RandomTestUtil.randomString());

		newSyncDLObject.setDescription(RandomTestUtil.randomString());

		newSyncDLObject.setChangeLog(RandomTestUtil.randomString());

		newSyncDLObject.setExtraSettings(RandomTestUtil.randomString());

		newSyncDLObject.setVersion(RandomTestUtil.randomString());

		newSyncDLObject.setVersionId(RandomTestUtil.nextLong());

		newSyncDLObject.setSize(RandomTestUtil.nextLong());

		newSyncDLObject.setChecksum(RandomTestUtil.randomString());

		newSyncDLObject.setEvent(RandomTestUtil.randomString());

		newSyncDLObject.setLanTokenKey(RandomTestUtil.randomString());

		newSyncDLObject.setLastPermissionChangeDate(RandomTestUtil.nextDate());

		newSyncDLObject.setLockExpirationDate(RandomTestUtil.nextDate());

		newSyncDLObject.setLockUserId(RandomTestUtil.nextLong());

		newSyncDLObject.setLockUserName(RandomTestUtil.randomString());

		newSyncDLObject.setType(RandomTestUtil.randomString());

		newSyncDLObject.setTypePK(RandomTestUtil.nextLong());

		newSyncDLObject.setTypeUuid(RandomTestUtil.randomString());

		_syncDLObjects.add(_persistence.update(newSyncDLObject));

		SyncDLObject existingSyncDLObject = _persistence.findByPrimaryKey(newSyncDLObject.getPrimaryKey());

		Assert.assertEquals(existingSyncDLObject.getSyncDLObjectId(),
			newSyncDLObject.getSyncDLObjectId());
		Assert.assertEquals(existingSyncDLObject.getCompanyId(),
			newSyncDLObject.getCompanyId());
		Assert.assertEquals(existingSyncDLObject.getUserId(),
			newSyncDLObject.getUserId());
		Assert.assertEquals(existingSyncDLObject.getUserName(),
			newSyncDLObject.getUserName());
		Assert.assertEquals(existingSyncDLObject.getCreateTime(),
			newSyncDLObject.getCreateTime());
		Assert.assertEquals(existingSyncDLObject.getModifiedTime(),
			newSyncDLObject.getModifiedTime());
		Assert.assertEquals(existingSyncDLObject.getRepositoryId(),
			newSyncDLObject.getRepositoryId());
		Assert.assertEquals(existingSyncDLObject.getParentFolderId(),
			newSyncDLObject.getParentFolderId());
		Assert.assertEquals(existingSyncDLObject.getTreePath(),
			newSyncDLObject.getTreePath());
		Assert.assertEquals(existingSyncDLObject.getName(),
			newSyncDLObject.getName());
		Assert.assertEquals(existingSyncDLObject.getExtension(),
			newSyncDLObject.getExtension());
		Assert.assertEquals(existingSyncDLObject.getMimeType(),
			newSyncDLObject.getMimeType());
		Assert.assertEquals(existingSyncDLObject.getDescription(),
			newSyncDLObject.getDescription());
		Assert.assertEquals(existingSyncDLObject.getChangeLog(),
			newSyncDLObject.getChangeLog());
		Assert.assertEquals(existingSyncDLObject.getExtraSettings(),
			newSyncDLObject.getExtraSettings());
		Assert.assertEquals(existingSyncDLObject.getVersion(),
			newSyncDLObject.getVersion());
		Assert.assertEquals(existingSyncDLObject.getVersionId(),
			newSyncDLObject.getVersionId());
		Assert.assertEquals(existingSyncDLObject.getSize(),
			newSyncDLObject.getSize());
		Assert.assertEquals(existingSyncDLObject.getChecksum(),
			newSyncDLObject.getChecksum());
		Assert.assertEquals(existingSyncDLObject.getEvent(),
			newSyncDLObject.getEvent());
		Assert.assertEquals(existingSyncDLObject.getLanTokenKey(),
			newSyncDLObject.getLanTokenKey());
		Assert.assertEquals(Time.getShortTimestamp(
				existingSyncDLObject.getLastPermissionChangeDate()),
			Time.getShortTimestamp(
				newSyncDLObject.getLastPermissionChangeDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingSyncDLObject.getLockExpirationDate()),
			Time.getShortTimestamp(newSyncDLObject.getLockExpirationDate()));
		Assert.assertEquals(existingSyncDLObject.getLockUserId(),
			newSyncDLObject.getLockUserId());
		Assert.assertEquals(existingSyncDLObject.getLockUserName(),
			newSyncDLObject.getLockUserName());
		Assert.assertEquals(existingSyncDLObject.getType(),
			newSyncDLObject.getType());
		Assert.assertEquals(existingSyncDLObject.getTypePK(),
			newSyncDLObject.getTypePK());
		Assert.assertEquals(existingSyncDLObject.getTypeUuid(),
			newSyncDLObject.getTypeUuid());
	}

	@Test
	public void testCountByTreePath() throws Exception {
		_persistence.countByTreePath(StringPool.BLANK);

		_persistence.countByTreePath(StringPool.NULL);

		_persistence.countByTreePath((String)null);
	}

	@Test
	public void testCountByM_R() throws Exception {
		_persistence.countByM_R(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByM_R(0L, 0L);
	}

	@Test
	public void testCountByR_P() throws Exception {
		_persistence.countByR_P(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByR_P(0L, 0L);
	}

	@Test
	public void testCountByR_NotE() throws Exception {
		_persistence.countByR_NotE(RandomTestUtil.nextLong(), StringPool.BLANK);

		_persistence.countByR_NotE(0L, StringPool.NULL);

		_persistence.countByR_NotE(0L, (String)null);
	}

	@Test
	public void testCountByR_T() throws Exception {
		_persistence.countByR_T(RandomTestUtil.nextLong(), StringPool.BLANK);

		_persistence.countByR_T(0L, StringPool.NULL);

		_persistence.countByR_T(0L, (String)null);
	}

	@Test
	public void testCountByT_NotE() throws Exception {
		_persistence.countByT_NotE(StringPool.BLANK, StringPool.BLANK);

		_persistence.countByT_NotE(StringPool.NULL, StringPool.NULL);

		_persistence.countByT_NotE((String)null, (String)null);
	}

	@Test
	public void testCountByV_T() throws Exception {
		_persistence.countByV_T(StringPool.BLANK, StringPool.BLANK);

		_persistence.countByV_T(StringPool.NULL, StringPool.NULL);

		_persistence.countByV_T((String)null, (String)null);
	}

	@Test
	public void testCountByT_T() throws Exception {
		_persistence.countByT_T(StringPool.BLANK, RandomTestUtil.nextLong());

		_persistence.countByT_T(StringPool.NULL, 0L);

		_persistence.countByT_T((String)null, 0L);
	}

	@Test
	public void testCountByM_R_NotE() throws Exception {
		_persistence.countByM_R_NotE(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), StringPool.BLANK);

		_persistence.countByM_R_NotE(0L, 0L, StringPool.NULL);

		_persistence.countByM_R_NotE(0L, 0L, (String)null);
	}

	@Test
	public void testCountByM_R_NotEArrayable() throws Exception {
		_persistence.countByM_R_NotE(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(),
			new String[] {
				RandomTestUtil.randomString(), StringPool.BLANK, StringPool.NULL,
				null, null
			});
	}

	@Test
	public void testCountByR_P_T() throws Exception {
		_persistence.countByR_P_T(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), StringPool.BLANK);

		_persistence.countByR_P_T(0L, 0L, StringPool.NULL);

		_persistence.countByR_P_T(0L, 0L, (String)null);
	}

	@Test
	public void testCountByR_P_TArrayable() throws Exception {
		_persistence.countByR_P_T(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(),
			new String[] {
				RandomTestUtil.randomString(), StringPool.BLANK, StringPool.NULL,
				null, null
			});
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		SyncDLObject newSyncDLObject = addSyncDLObject();

		SyncDLObject existingSyncDLObject = _persistence.findByPrimaryKey(newSyncDLObject.getPrimaryKey());

		Assert.assertEquals(existingSyncDLObject, newSyncDLObject);
	}

	@Test(expected = NoSuchDLObjectException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<SyncDLObject> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("SyncDLObject",
			"syncDLObjectId", true, "companyId", true, "userId", true,
			"userName", true, "createTime", true, "modifiedTime", true,
			"repositoryId", true, "parentFolderId", true, "treePath", true,
			"name", true, "extension", true, "mimeType", true, "description",
			true, "changeLog", true, "version", true, "versionId", true,
			"size", true, "checksum", true, "event", true, "lanTokenKey", true,
			"lastPermissionChangeDate", true, "lockExpirationDate", true,
			"lockUserId", true, "lockUserName", true, "type", true, "typePK",
			true, "typeUuid", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		SyncDLObject newSyncDLObject = addSyncDLObject();

		SyncDLObject existingSyncDLObject = _persistence.fetchByPrimaryKey(newSyncDLObject.getPrimaryKey());

		Assert.assertEquals(existingSyncDLObject, newSyncDLObject);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SyncDLObject missingSyncDLObject = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingSyncDLObject);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		SyncDLObject newSyncDLObject1 = addSyncDLObject();
		SyncDLObject newSyncDLObject2 = addSyncDLObject();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSyncDLObject1.getPrimaryKey());
		primaryKeys.add(newSyncDLObject2.getPrimaryKey());

		Map<Serializable, SyncDLObject> syncDLObjects = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, syncDLObjects.size());
		Assert.assertEquals(newSyncDLObject1,
			syncDLObjects.get(newSyncDLObject1.getPrimaryKey()));
		Assert.assertEquals(newSyncDLObject2,
			syncDLObjects.get(newSyncDLObject2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, SyncDLObject> syncDLObjects = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(syncDLObjects.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		SyncDLObject newSyncDLObject = addSyncDLObject();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSyncDLObject.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, SyncDLObject> syncDLObjects = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, syncDLObjects.size());
		Assert.assertEquals(newSyncDLObject,
			syncDLObjects.get(newSyncDLObject.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, SyncDLObject> syncDLObjects = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(syncDLObjects.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		SyncDLObject newSyncDLObject = addSyncDLObject();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSyncDLObject.getPrimaryKey());

		Map<Serializable, SyncDLObject> syncDLObjects = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, syncDLObjects.size());
		Assert.assertEquals(newSyncDLObject,
			syncDLObjects.get(newSyncDLObject.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = SyncDLObjectLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<SyncDLObject>() {
				@Override
				public void performAction(SyncDLObject syncDLObject) {
					Assert.assertNotNull(syncDLObject);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		SyncDLObject newSyncDLObject = addSyncDLObject();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SyncDLObject.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("syncDLObjectId",
				newSyncDLObject.getSyncDLObjectId()));

		List<SyncDLObject> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		SyncDLObject existingSyncDLObject = result.get(0);

		Assert.assertEquals(existingSyncDLObject, newSyncDLObject);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SyncDLObject.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("syncDLObjectId",
				RandomTestUtil.nextLong()));

		List<SyncDLObject> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		SyncDLObject newSyncDLObject = addSyncDLObject();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SyncDLObject.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"syncDLObjectId"));

		Object newSyncDLObjectId = newSyncDLObject.getSyncDLObjectId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("syncDLObjectId",
				new Object[] { newSyncDLObjectId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingSyncDLObjectId = result.get(0);

		Assert.assertEquals(existingSyncDLObjectId, newSyncDLObjectId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SyncDLObject.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"syncDLObjectId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("syncDLObjectId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		SyncDLObject newSyncDLObject = addSyncDLObject();

		_persistence.clearCache();

		SyncDLObject existingSyncDLObject = _persistence.findByPrimaryKey(newSyncDLObject.getPrimaryKey());

		Assert.assertTrue(Objects.equals(existingSyncDLObject.getType(),
				ReflectionTestUtil.invoke(existingSyncDLObject,
					"getOriginalType", new Class<?>[0])));
		Assert.assertEquals(Long.valueOf(existingSyncDLObject.getTypePK()),
			ReflectionTestUtil.<Long>invoke(existingSyncDLObject,
				"getOriginalTypePK", new Class<?>[0]));
	}

	protected SyncDLObject addSyncDLObject() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SyncDLObject syncDLObject = _persistence.create(pk);

		syncDLObject.setCompanyId(RandomTestUtil.nextLong());

		syncDLObject.setUserId(RandomTestUtil.nextLong());

		syncDLObject.setUserName(RandomTestUtil.randomString());

		syncDLObject.setCreateTime(RandomTestUtil.nextLong());

		syncDLObject.setModifiedTime(RandomTestUtil.nextLong());

		syncDLObject.setRepositoryId(RandomTestUtil.nextLong());

		syncDLObject.setParentFolderId(RandomTestUtil.nextLong());

		syncDLObject.setTreePath(RandomTestUtil.randomString());

		syncDLObject.setName(RandomTestUtil.randomString());

		syncDLObject.setExtension(RandomTestUtil.randomString());

		syncDLObject.setMimeType(RandomTestUtil.randomString());

		syncDLObject.setDescription(RandomTestUtil.randomString());

		syncDLObject.setChangeLog(RandomTestUtil.randomString());

		syncDLObject.setExtraSettings(RandomTestUtil.randomString());

		syncDLObject.setVersion(RandomTestUtil.randomString());

		syncDLObject.setVersionId(RandomTestUtil.nextLong());

		syncDLObject.setSize(RandomTestUtil.nextLong());

		syncDLObject.setChecksum(RandomTestUtil.randomString());

		syncDLObject.setEvent(RandomTestUtil.randomString());

		syncDLObject.setLanTokenKey(RandomTestUtil.randomString());

		syncDLObject.setLastPermissionChangeDate(RandomTestUtil.nextDate());

		syncDLObject.setLockExpirationDate(RandomTestUtil.nextDate());

		syncDLObject.setLockUserId(RandomTestUtil.nextLong());

		syncDLObject.setLockUserName(RandomTestUtil.randomString());

		syncDLObject.setType(RandomTestUtil.randomString());

		syncDLObject.setTypePK(RandomTestUtil.nextLong());

		syncDLObject.setTypeUuid(RandomTestUtil.randomString());

		_syncDLObjects.add(_persistence.update(syncDLObject));

		return syncDLObject;
	}

	private List<SyncDLObject> _syncDLObjects = new ArrayList<SyncDLObject>();
	private SyncDLObjectPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}