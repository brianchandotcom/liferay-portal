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

package com.liferay.knowledge.base.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.knowledge.base.exception.NoSuchFolderException;
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.knowledge.base.service.KBFolderLocalServiceUtil;
import com.liferay.knowledge.base.service.persistence.KBFolderPersistence;
import com.liferay.knowledge.base.service.persistence.KBFolderUtil;
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
public class KBFolderPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.knowledge.base.service"));

	@Before
	public void setUp() {
		_persistence = KBFolderUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<KBFolder> iterator = _kbFolders.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		KBFolder kbFolder = _persistence.create(pk);

		Assert.assertNotNull(kbFolder);

		Assert.assertEquals(kbFolder.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		KBFolder newKBFolder = addKBFolder();

		_persistence.remove(newKBFolder);

		KBFolder existingKBFolder = _persistence.fetchByPrimaryKey(
			newKBFolder.getPrimaryKey());

		Assert.assertNull(existingKBFolder);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addKBFolder();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		KBFolder newKBFolder = _persistence.create(pk);

		newKBFolder.setMvccVersion(RandomTestUtil.nextLong());

		newKBFolder.setUuid(RandomTestUtil.randomString());

		newKBFolder.setGroupId(RandomTestUtil.nextLong());

		newKBFolder.setCompanyId(RandomTestUtil.nextLong());

		newKBFolder.setUserId(RandomTestUtil.nextLong());

		newKBFolder.setUserName(RandomTestUtil.randomString());

		newKBFolder.setCreateDate(RandomTestUtil.nextDate());

		newKBFolder.setModifiedDate(RandomTestUtil.nextDate());

		newKBFolder.setParentKBFolderId(RandomTestUtil.nextLong());

		newKBFolder.setName(RandomTestUtil.randomString());

		newKBFolder.setUrlTitle(RandomTestUtil.randomString());

		newKBFolder.setDescription(RandomTestUtil.randomString());

		newKBFolder.setLastPublishDate(RandomTestUtil.nextDate());

		_kbFolders.add(_persistence.update(newKBFolder));

		KBFolder existingKBFolder = _persistence.findByPrimaryKey(
			newKBFolder.getPrimaryKey());

		Assert.assertEquals(
			existingKBFolder.getMvccVersion(), newKBFolder.getMvccVersion());
		Assert.assertEquals(existingKBFolder.getUuid(), newKBFolder.getUuid());
		Assert.assertEquals(
			existingKBFolder.getKbFolderId(), newKBFolder.getKbFolderId());
		Assert.assertEquals(
			existingKBFolder.getGroupId(), newKBFolder.getGroupId());
		Assert.assertEquals(
			existingKBFolder.getCompanyId(), newKBFolder.getCompanyId());
		Assert.assertEquals(
			existingKBFolder.getUserId(), newKBFolder.getUserId());
		Assert.assertEquals(
			existingKBFolder.getUserName(), newKBFolder.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingKBFolder.getCreateDate()),
			Time.getShortTimestamp(newKBFolder.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingKBFolder.getModifiedDate()),
			Time.getShortTimestamp(newKBFolder.getModifiedDate()));
		Assert.assertEquals(
			existingKBFolder.getParentKBFolderId(),
			newKBFolder.getParentKBFolderId());
		Assert.assertEquals(existingKBFolder.getName(), newKBFolder.getName());
		Assert.assertEquals(
			existingKBFolder.getUrlTitle(), newKBFolder.getUrlTitle());
		Assert.assertEquals(
			existingKBFolder.getDescription(), newKBFolder.getDescription());
		Assert.assertEquals(
			Time.getShortTimestamp(existingKBFolder.getLastPublishDate()),
			Time.getShortTimestamp(newKBFolder.getLastPublishDate()));
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
	public void testCountByG_P() throws Exception {
		_persistence.countByG_P(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByG_P(0L, 0L);
	}

	@Test
	public void testCountByG_P_N() throws Exception {
		_persistence.countByG_P_N(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(), "");

		_persistence.countByG_P_N(0L, 0L, "null");

		_persistence.countByG_P_N(0L, 0L, (String)null);
	}

	@Test
	public void testCountByG_P_UT() throws Exception {
		_persistence.countByG_P_UT(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(), "");

		_persistence.countByG_P_UT(0L, 0L, "null");

		_persistence.countByG_P_UT(0L, 0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		KBFolder newKBFolder = addKBFolder();

		KBFolder existingKBFolder = _persistence.findByPrimaryKey(
			newKBFolder.getPrimaryKey());

		Assert.assertEquals(existingKBFolder, newKBFolder);
	}

	@Test(expected = NoSuchFolderException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<KBFolder> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"KBFolder", "mvccVersion", true, "uuid", true, "kbFolderId", true,
			"groupId", true, "companyId", true, "userId", true, "userName",
			true, "createDate", true, "modifiedDate", true, "parentKBFolderId",
			true, "name", true, "urlTitle", true, "description", true,
			"lastPublishDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		KBFolder newKBFolder = addKBFolder();

		KBFolder existingKBFolder = _persistence.fetchByPrimaryKey(
			newKBFolder.getPrimaryKey());

		Assert.assertEquals(existingKBFolder, newKBFolder);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		KBFolder missingKBFolder = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingKBFolder);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		KBFolder newKBFolder1 = addKBFolder();
		KBFolder newKBFolder2 = addKBFolder();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newKBFolder1.getPrimaryKey());
		primaryKeys.add(newKBFolder2.getPrimaryKey());

		Map<Serializable, KBFolder> kbFolders = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertEquals(2, kbFolders.size());
		Assert.assertEquals(
			newKBFolder1, kbFolders.get(newKBFolder1.getPrimaryKey()));
		Assert.assertEquals(
			newKBFolder2, kbFolders.get(newKBFolder2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, KBFolder> kbFolders = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertTrue(kbFolders.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		KBFolder newKBFolder = addKBFolder();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newKBFolder.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, KBFolder> kbFolders = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertEquals(1, kbFolders.size());
		Assert.assertEquals(
			newKBFolder, kbFolders.get(newKBFolder.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, KBFolder> kbFolders = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertTrue(kbFolders.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		KBFolder newKBFolder = addKBFolder();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newKBFolder.getPrimaryKey());

		Map<Serializable, KBFolder> kbFolders = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertEquals(1, kbFolders.size());
		Assert.assertEquals(
			newKBFolder, kbFolders.get(newKBFolder.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			KBFolderLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<KBFolder>() {

				@Override
				public void performAction(KBFolder kbFolder) {
					Assert.assertNotNull(kbFolder);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		KBFolder newKBFolder = addKBFolder();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			KBFolder.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"kbFolderId", newKBFolder.getKbFolderId()));

		List<KBFolder> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		KBFolder existingKBFolder = result.get(0);

		Assert.assertEquals(existingKBFolder, newKBFolder);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			KBFolder.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"kbFolderId", RandomTestUtil.nextLong()));

		List<KBFolder> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		KBFolder newKBFolder = addKBFolder();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			KBFolder.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("kbFolderId"));

		Object newKbFolderId = newKBFolder.getKbFolderId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"kbFolderId", new Object[] {newKbFolderId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingKbFolderId = result.get(0);

		Assert.assertEquals(existingKbFolderId, newKbFolderId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			KBFolder.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("kbFolderId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"kbFolderId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		KBFolder newKBFolder = addKBFolder();

		_persistence.clearCache();

		KBFolder existingKBFolder = _persistence.findByPrimaryKey(
			newKBFolder.getPrimaryKey());

		Assert.assertTrue(
			Objects.equals(
				existingKBFolder.getUuid(),
				ReflectionTestUtil.invoke(
					existingKBFolder, "getOriginalUuid", new Class<?>[0])));
		Assert.assertEquals(
			Long.valueOf(existingKBFolder.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingKBFolder, "getOriginalGroupId", new Class<?>[0]));

		Assert.assertEquals(
			Long.valueOf(existingKBFolder.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingKBFolder, "getOriginalGroupId", new Class<?>[0]));
		Assert.assertEquals(
			Long.valueOf(existingKBFolder.getParentKBFolderId()),
			ReflectionTestUtil.<Long>invoke(
				existingKBFolder, "getOriginalParentKBFolderId",
				new Class<?>[0]));
		Assert.assertTrue(
			Objects.equals(
				existingKBFolder.getName(),
				ReflectionTestUtil.invoke(
					existingKBFolder, "getOriginalName", new Class<?>[0])));

		Assert.assertEquals(
			Long.valueOf(existingKBFolder.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingKBFolder, "getOriginalGroupId", new Class<?>[0]));
		Assert.assertEquals(
			Long.valueOf(existingKBFolder.getParentKBFolderId()),
			ReflectionTestUtil.<Long>invoke(
				existingKBFolder, "getOriginalParentKBFolderId",
				new Class<?>[0]));
		Assert.assertTrue(
			Objects.equals(
				existingKBFolder.getUrlTitle(),
				ReflectionTestUtil.invoke(
					existingKBFolder, "getOriginalUrlTitle", new Class<?>[0])));
	}

	protected KBFolder addKBFolder() throws Exception {
		long pk = RandomTestUtil.nextLong();

		KBFolder kbFolder = _persistence.create(pk);

		kbFolder.setMvccVersion(RandomTestUtil.nextLong());

		kbFolder.setUuid(RandomTestUtil.randomString());

		kbFolder.setGroupId(RandomTestUtil.nextLong());

		kbFolder.setCompanyId(RandomTestUtil.nextLong());

		kbFolder.setUserId(RandomTestUtil.nextLong());

		kbFolder.setUserName(RandomTestUtil.randomString());

		kbFolder.setCreateDate(RandomTestUtil.nextDate());

		kbFolder.setModifiedDate(RandomTestUtil.nextDate());

		kbFolder.setParentKBFolderId(RandomTestUtil.nextLong());

		kbFolder.setName(RandomTestUtil.randomString());

		kbFolder.setUrlTitle(RandomTestUtil.randomString());

		kbFolder.setDescription(RandomTestUtil.randomString());

		kbFolder.setLastPublishDate(RandomTestUtil.nextDate());

		_kbFolders.add(_persistence.update(kbFolder));

		return kbFolder;
	}

	private List<KBFolder> _kbFolders = new ArrayList<KBFolder>();
	private KBFolderPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}