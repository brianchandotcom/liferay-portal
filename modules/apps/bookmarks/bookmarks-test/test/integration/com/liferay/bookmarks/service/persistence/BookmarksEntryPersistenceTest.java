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

package com.liferay.bookmarks.service.persistence;

import com.liferay.bookmarks.exception.NoSuchEntryException;
import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl;
import com.liferay.bookmarks.service.BookmarksEntryLocalServiceUtil;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.TransactionalTestRule;
import com.liferay.portal.tools.DBUpgrader;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.test.RandomTestUtil;

import org.jboss.arquillian.junit.Arquillian;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
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
public class BookmarksEntryPersistenceTest {
	@ClassRule
	public static TransactionalTestRule transactionalTestRule = new TransactionalTestRule(Propagation.REQUIRED);

	@BeforeClass
	public static void setupClass() throws TemplateException {
		try {
			DBUpgrader.upgrade();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		TemplateManagerUtil.init();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<BookmarksEntry> iterator = _bookmarksEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		BookmarksEntry bookmarksEntry = _persistence.create(pk);

		Assert.assertNotNull(bookmarksEntry);

		Assert.assertEquals(bookmarksEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		BookmarksEntry newBookmarksEntry = addBookmarksEntry();

		_persistence.remove(newBookmarksEntry);

		BookmarksEntry existingBookmarksEntry = _persistence.fetchByPrimaryKey(newBookmarksEntry.getPrimaryKey());

		Assert.assertNull(existingBookmarksEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addBookmarksEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		BookmarksEntry newBookmarksEntry = _persistence.create(pk);

		newBookmarksEntry.setUuid(RandomTestUtil.randomString());

		newBookmarksEntry.setGroupId(RandomTestUtil.nextLong());

		newBookmarksEntry.setCompanyId(RandomTestUtil.nextLong());

		newBookmarksEntry.setUserId(RandomTestUtil.nextLong());

		newBookmarksEntry.setUserName(RandomTestUtil.randomString());

		newBookmarksEntry.setCreateDate(RandomTestUtil.nextDate());

		newBookmarksEntry.setModifiedDate(RandomTestUtil.nextDate());

		newBookmarksEntry.setResourceBlockId(RandomTestUtil.nextLong());

		newBookmarksEntry.setFolderId(RandomTestUtil.nextLong());

		newBookmarksEntry.setTreePath(RandomTestUtil.randomString());

		newBookmarksEntry.setName(RandomTestUtil.randomString());

		newBookmarksEntry.setUrl(RandomTestUtil.randomString());

		newBookmarksEntry.setDescription(RandomTestUtil.randomString());

		newBookmarksEntry.setVisits(RandomTestUtil.nextInt());

		newBookmarksEntry.setPriority(RandomTestUtil.nextInt());

		newBookmarksEntry.setStatus(RandomTestUtil.nextInt());

		newBookmarksEntry.setStatusByUserId(RandomTestUtil.nextLong());

		newBookmarksEntry.setStatusByUserName(RandomTestUtil.randomString());

		newBookmarksEntry.setStatusDate(RandomTestUtil.nextDate());

		_bookmarksEntries.add(_persistence.update(newBookmarksEntry));

		BookmarksEntry existingBookmarksEntry = _persistence.findByPrimaryKey(newBookmarksEntry.getPrimaryKey());

		Assert.assertEquals(existingBookmarksEntry.getUuid(),
			newBookmarksEntry.getUuid());
		Assert.assertEquals(existingBookmarksEntry.getEntryId(),
			newBookmarksEntry.getEntryId());
		Assert.assertEquals(existingBookmarksEntry.getGroupId(),
			newBookmarksEntry.getGroupId());
		Assert.assertEquals(existingBookmarksEntry.getCompanyId(),
			newBookmarksEntry.getCompanyId());
		Assert.assertEquals(existingBookmarksEntry.getUserId(),
			newBookmarksEntry.getUserId());
		Assert.assertEquals(existingBookmarksEntry.getUserName(),
			newBookmarksEntry.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingBookmarksEntry.getCreateDate()),
			Time.getShortTimestamp(newBookmarksEntry.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingBookmarksEntry.getModifiedDate()),
			Time.getShortTimestamp(newBookmarksEntry.getModifiedDate()));
		Assert.assertEquals(existingBookmarksEntry.getResourceBlockId(),
			newBookmarksEntry.getResourceBlockId());
		Assert.assertEquals(existingBookmarksEntry.getFolderId(),
			newBookmarksEntry.getFolderId());
		Assert.assertEquals(existingBookmarksEntry.getTreePath(),
			newBookmarksEntry.getTreePath());
		Assert.assertEquals(existingBookmarksEntry.getName(),
			newBookmarksEntry.getName());
		Assert.assertEquals(existingBookmarksEntry.getUrl(),
			newBookmarksEntry.getUrl());
		Assert.assertEquals(existingBookmarksEntry.getDescription(),
			newBookmarksEntry.getDescription());
		Assert.assertEquals(existingBookmarksEntry.getVisits(),
			newBookmarksEntry.getVisits());
		Assert.assertEquals(existingBookmarksEntry.getPriority(),
			newBookmarksEntry.getPriority());
		Assert.assertEquals(existingBookmarksEntry.getStatus(),
			newBookmarksEntry.getStatus());
		Assert.assertEquals(existingBookmarksEntry.getStatusByUserId(),
			newBookmarksEntry.getStatusByUserId());
		Assert.assertEquals(existingBookmarksEntry.getStatusByUserName(),
			newBookmarksEntry.getStatusByUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingBookmarksEntry.getStatusDate()),
			Time.getShortTimestamp(newBookmarksEntry.getStatusDate()));
	}

	@Test
	public void testCountByResourceBlockId() {
		try {
			_persistence.countByResourceBlockId(RandomTestUtil.nextLong());

			_persistence.countByResourceBlockId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByUuid() {
		try {
			_persistence.countByUuid(StringPool.BLANK);

			_persistence.countByUuid(StringPool.NULL);

			_persistence.countByUuid((String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByUUID_G() {
		try {
			_persistence.countByUUID_G(StringPool.BLANK,
				RandomTestUtil.nextLong());

			_persistence.countByUUID_G(StringPool.NULL, 0L);

			_persistence.countByUUID_G((String)null, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByUuid_C() {
		try {
			_persistence.countByUuid_C(StringPool.BLANK,
				RandomTestUtil.nextLong());

			_persistence.countByUuid_C(StringPool.NULL, 0L);

			_persistence.countByUuid_C((String)null, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByCompanyId() {
		try {
			_persistence.countByCompanyId(RandomTestUtil.nextLong());

			_persistence.countByCompanyId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_F() {
		try {
			_persistence.countByG_F(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong());

			_persistence.countByG_F(0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_FArrayable() {
		try {
			_persistence.countByG_F(RandomTestUtil.nextLong(),
				new long[] { RandomTestUtil.nextLong(), 0L });
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_S() {
		try {
			_persistence.countByG_S(RandomTestUtil.nextLong(),
				RandomTestUtil.nextInt());

			_persistence.countByG_S(0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_NotS() {
		try {
			_persistence.countByG_NotS(RandomTestUtil.nextLong(),
				RandomTestUtil.nextInt());

			_persistence.countByG_NotS(0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_NotS() {
		try {
			_persistence.countByC_NotS(RandomTestUtil.nextLong(),
				RandomTestUtil.nextInt());

			_persistence.countByC_NotS(0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_U_S() {
		try {
			_persistence.countByG_U_S(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextInt());

			_persistence.countByG_U_S(0L, 0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_U_NotS() {
		try {
			_persistence.countByG_U_NotS(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextInt());

			_persistence.countByG_U_NotS(0L, 0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_F_S() {
		try {
			_persistence.countByG_F_S(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextInt());

			_persistence.countByG_F_S(0L, 0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_F_SArrayable() {
		try {
			_persistence.countByG_F_S(RandomTestUtil.nextLong(),
				new long[] { RandomTestUtil.nextLong(), 0L },
				RandomTestUtil.nextInt());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_F_NotS() {
		try {
			_persistence.countByG_F_NotS(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextInt());

			_persistence.countByG_F_NotS(0L, 0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_F_NotSArrayable() {
		try {
			_persistence.countByG_F_NotS(RandomTestUtil.nextLong(),
				new long[] { RandomTestUtil.nextLong(), 0L },
				RandomTestUtil.nextInt());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_U_F_S() {
		try {
			_persistence.countByG_U_F_S(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
				RandomTestUtil.nextInt());

			_persistence.countByG_U_F_S(0L, 0L, 0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_U_F_SArrayable() {
		try {
			_persistence.countByG_U_F_S(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(),
				new long[] { RandomTestUtil.nextLong(), 0L },
				RandomTestUtil.nextInt());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		BookmarksEntry newBookmarksEntry = addBookmarksEntry();

		BookmarksEntry existingBookmarksEntry = _persistence.findByPrimaryKey(newBookmarksEntry.getPrimaryKey());

		Assert.assertEquals(existingBookmarksEntry, newBookmarksEntry);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchEntryException");
		}
		catch (NoSuchEntryException nsee) {
		}
	}

	@Test
	public void testFindAll() throws Exception {
		try {
			_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				getOrderByComparator());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	protected OrderByComparator<BookmarksEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("BookmarksEntry", "uuid",
			true, "entryId", true, "groupId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "resourceBlockId", true, "folderId", true,
			"treePath", true, "name", true, "url", true, "description", true,
			"visits", true, "priority", true, "status", true, "statusByUserId",
			true, "statusByUserName", true, "statusDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		BookmarksEntry newBookmarksEntry = addBookmarksEntry();

		BookmarksEntry existingBookmarksEntry = _persistence.fetchByPrimaryKey(newBookmarksEntry.getPrimaryKey());

		Assert.assertEquals(existingBookmarksEntry, newBookmarksEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		BookmarksEntry missingBookmarksEntry = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingBookmarksEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		BookmarksEntry newBookmarksEntry1 = addBookmarksEntry();
		BookmarksEntry newBookmarksEntry2 = addBookmarksEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newBookmarksEntry1.getPrimaryKey());
		primaryKeys.add(newBookmarksEntry2.getPrimaryKey());

		Map<Serializable, BookmarksEntry> bookmarksEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, bookmarksEntries.size());
		Assert.assertEquals(newBookmarksEntry1,
			bookmarksEntries.get(newBookmarksEntry1.getPrimaryKey()));
		Assert.assertEquals(newBookmarksEntry2,
			bookmarksEntries.get(newBookmarksEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, BookmarksEntry> bookmarksEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(bookmarksEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		BookmarksEntry newBookmarksEntry = addBookmarksEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newBookmarksEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, BookmarksEntry> bookmarksEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, bookmarksEntries.size());
		Assert.assertEquals(newBookmarksEntry,
			bookmarksEntries.get(newBookmarksEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, BookmarksEntry> bookmarksEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(bookmarksEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		BookmarksEntry newBookmarksEntry = addBookmarksEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newBookmarksEntry.getPrimaryKey());

		Map<Serializable, BookmarksEntry> bookmarksEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, bookmarksEntries.size());
		Assert.assertEquals(newBookmarksEntry,
			bookmarksEntries.get(newBookmarksEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = BookmarksEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					BookmarksEntry bookmarksEntry = (BookmarksEntry)object;

					Assert.assertNotNull(bookmarksEntry);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		BookmarksEntry newBookmarksEntry = addBookmarksEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(BookmarksEntry.class,
				BookmarksEntry.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("entryId",
				newBookmarksEntry.getEntryId()));

		List<BookmarksEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		BookmarksEntry existingBookmarksEntry = result.get(0);

		Assert.assertEquals(existingBookmarksEntry, newBookmarksEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(BookmarksEntry.class,
				BookmarksEntry.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("entryId",
				RandomTestUtil.nextLong()));

		List<BookmarksEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		BookmarksEntry newBookmarksEntry = addBookmarksEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(BookmarksEntry.class,
				BookmarksEntry.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("entryId"));

		Object newEntryId = newBookmarksEntry.getEntryId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("entryId",
				new Object[] { newEntryId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingEntryId = result.get(0);

		Assert.assertEquals(existingEntryId, newEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(BookmarksEntry.class,
				BookmarksEntry.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("entryId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("entryId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		BookmarksEntry newBookmarksEntry = addBookmarksEntry();

		_persistence.clearCache();

		BookmarksEntryModelImpl existingBookmarksEntryModelImpl = (BookmarksEntryModelImpl)_persistence.findByPrimaryKey(newBookmarksEntry.getPrimaryKey());

		Assert.assertTrue(Validator.equals(
				existingBookmarksEntryModelImpl.getUuid(),
				existingBookmarksEntryModelImpl.getOriginalUuid()));
		Assert.assertEquals(existingBookmarksEntryModelImpl.getGroupId(),
			existingBookmarksEntryModelImpl.getOriginalGroupId());
	}

	protected BookmarksEntry addBookmarksEntry() throws Exception {
		long pk = RandomTestUtil.nextLong();

		BookmarksEntry bookmarksEntry = _persistence.create(pk);

		bookmarksEntry.setUuid(RandomTestUtil.randomString());

		bookmarksEntry.setGroupId(RandomTestUtil.nextLong());

		bookmarksEntry.setCompanyId(RandomTestUtil.nextLong());

		bookmarksEntry.setUserId(RandomTestUtil.nextLong());

		bookmarksEntry.setUserName(RandomTestUtil.randomString());

		bookmarksEntry.setCreateDate(RandomTestUtil.nextDate());

		bookmarksEntry.setModifiedDate(RandomTestUtil.nextDate());

		bookmarksEntry.setResourceBlockId(RandomTestUtil.nextLong());

		bookmarksEntry.setFolderId(RandomTestUtil.nextLong());

		bookmarksEntry.setTreePath(RandomTestUtil.randomString());

		bookmarksEntry.setName(RandomTestUtil.randomString());

		bookmarksEntry.setUrl(RandomTestUtil.randomString());

		bookmarksEntry.setDescription(RandomTestUtil.randomString());

		bookmarksEntry.setVisits(RandomTestUtil.nextInt());

		bookmarksEntry.setPriority(RandomTestUtil.nextInt());

		bookmarksEntry.setStatus(RandomTestUtil.nextInt());

		bookmarksEntry.setStatusByUserId(RandomTestUtil.nextLong());

		bookmarksEntry.setStatusByUserName(RandomTestUtil.randomString());

		bookmarksEntry.setStatusDate(RandomTestUtil.nextDate());

		_bookmarksEntries.add(_persistence.update(bookmarksEntry));

		return bookmarksEntry;
	}

	private static Log _log = LogFactoryUtil.getLog(BookmarksEntryPersistenceTest.class);
	private List<BookmarksEntry> _bookmarksEntries = new ArrayList<BookmarksEntry>();
	private BookmarksEntryPersistence _persistence = BookmarksEntryUtil.getPersistence();
}