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

package com.liferay.mail.reader.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.mail.reader.exception.NoSuchFolderException;
import com.liferay.mail.reader.model.Folder;
import com.liferay.mail.reader.service.FolderLocalServiceUtil;
import com.liferay.mail.reader.service.persistence.FolderPersistence;
import com.liferay.mail.reader.service.persistence.FolderUtil;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
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
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;

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
public class FolderPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = FolderUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<Folder> iterator = _folders.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Folder folder = _persistence.create(pk);

		Assert.assertNotNull(folder);

		Assert.assertEquals(folder.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		Folder newFolder = addFolder();

		_persistence.remove(newFolder);

		Folder existingFolder = _persistence.fetchByPrimaryKey(newFolder.getPrimaryKey());

		Assert.assertNull(existingFolder);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addFolder();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Folder newFolder = _persistence.create(pk);

		newFolder.setCompanyId(RandomTestUtil.nextLong());

		newFolder.setUserId(RandomTestUtil.nextLong());

		newFolder.setUserName(RandomTestUtil.randomString());

		newFolder.setCreateDate(RandomTestUtil.nextDate());

		newFolder.setModifiedDate(RandomTestUtil.nextDate());

		newFolder.setAccountId(RandomTestUtil.nextLong());

		newFolder.setFullName(RandomTestUtil.randomString());

		newFolder.setDisplayName(RandomTestUtil.randomString());

		newFolder.setRemoteMessageCount(RandomTestUtil.nextInt());

		_folders.add(_persistence.update(newFolder));

		Folder existingFolder = _persistence.findByPrimaryKey(newFolder.getPrimaryKey());

		Assert.assertEquals(existingFolder.getFolderId(),
			newFolder.getFolderId());
		Assert.assertEquals(existingFolder.getCompanyId(),
			newFolder.getCompanyId());
		Assert.assertEquals(existingFolder.getUserId(), newFolder.getUserId());
		Assert.assertEquals(existingFolder.getUserName(),
			newFolder.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingFolder.getCreateDate()),
			Time.getShortTimestamp(newFolder.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingFolder.getModifiedDate()),
			Time.getShortTimestamp(newFolder.getModifiedDate()));
		Assert.assertEquals(existingFolder.getAccountId(),
			newFolder.getAccountId());
		Assert.assertEquals(existingFolder.getFullName(),
			newFolder.getFullName());
		Assert.assertEquals(existingFolder.getDisplayName(),
			newFolder.getDisplayName());
		Assert.assertEquals(existingFolder.getRemoteMessageCount(),
			newFolder.getRemoteMessageCount());
	}

	@Test
	public void testCountByAccountId() throws Exception {
		_persistence.countByAccountId(RandomTestUtil.nextLong());

		_persistence.countByAccountId(0L);
	}

	@Test
	public void testCountByA_F() throws Exception {
		_persistence.countByA_F(RandomTestUtil.nextLong(), StringPool.BLANK);

		_persistence.countByA_F(0L, StringPool.NULL);

		_persistence.countByA_F(0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		Folder newFolder = addFolder();

		Folder existingFolder = _persistence.findByPrimaryKey(newFolder.getPrimaryKey());

		Assert.assertEquals(existingFolder, newFolder);
	}

	@Test(expected = NoSuchFolderException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<Folder> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("Mail_Folder", "folderId",
			true, "companyId", true, "userId", true, "userName", true,
			"createDate", true, "modifiedDate", true, "accountId", true,
			"fullName", true, "displayName", true, "remoteMessageCount", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		Folder newFolder = addFolder();

		Folder existingFolder = _persistence.fetchByPrimaryKey(newFolder.getPrimaryKey());

		Assert.assertEquals(existingFolder, newFolder);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Folder missingFolder = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingFolder);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		Folder newFolder1 = addFolder();
		Folder newFolder2 = addFolder();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFolder1.getPrimaryKey());
		primaryKeys.add(newFolder2.getPrimaryKey());

		Map<Serializable, Folder> folders = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, folders.size());
		Assert.assertEquals(newFolder1, folders.get(newFolder1.getPrimaryKey()));
		Assert.assertEquals(newFolder2, folders.get(newFolder2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, Folder> folders = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(folders.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		Folder newFolder = addFolder();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFolder.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, Folder> folders = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, folders.size());
		Assert.assertEquals(newFolder, folders.get(newFolder.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, Folder> folders = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(folders.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		Folder newFolder = addFolder();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFolder.getPrimaryKey());

		Map<Serializable, Folder> folders = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, folders.size());
		Assert.assertEquals(newFolder, folders.get(newFolder.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = FolderLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<Folder>() {
				@Override
				public void performAction(Folder folder) {
					Assert.assertNotNull(folder);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Folder newFolder = addFolder();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Folder.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("folderId",
				newFolder.getFolderId()));

		List<Folder> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Folder existingFolder = result.get(0);

		Assert.assertEquals(existingFolder, newFolder);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Folder.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("folderId",
				RandomTestUtil.nextLong()));

		List<Folder> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		Folder newFolder = addFolder();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Folder.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("folderId"));

		Object newFolderId = newFolder.getFolderId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("folderId",
				new Object[] { newFolderId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingFolderId = result.get(0);

		Assert.assertEquals(existingFolderId, newFolderId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Folder.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("folderId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("folderId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		Folder newFolder = addFolder();

		_persistence.clearCache();

		Folder existingFolder = _persistence.findByPrimaryKey(newFolder.getPrimaryKey());

		Assert.assertEquals(Long.valueOf(existingFolder.getAccountId()),
			ReflectionTestUtil.<Long>invoke(existingFolder,
				"getOriginalAccountId", new Class<?>[0]));
		Assert.assertTrue(Objects.equals(existingFolder.getFullName(),
				ReflectionTestUtil.invoke(existingFolder,
					"getOriginalFullName", new Class<?>[0])));
	}

	protected Folder addFolder() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Folder folder = _persistence.create(pk);

		folder.setCompanyId(RandomTestUtil.nextLong());

		folder.setUserId(RandomTestUtil.nextLong());

		folder.setUserName(RandomTestUtil.randomString());

		folder.setCreateDate(RandomTestUtil.nextDate());

		folder.setModifiedDate(RandomTestUtil.nextDate());

		folder.setAccountId(RandomTestUtil.nextLong());

		folder.setFullName(RandomTestUtil.randomString());

		folder.setDisplayName(RandomTestUtil.randomString());

		folder.setRemoteMessageCount(RandomTestUtil.nextInt());

		_folders.add(_persistence.update(folder));

		return folder;
	}

	private List<Folder> _folders = new ArrayList<Folder>();
	private FolderPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}