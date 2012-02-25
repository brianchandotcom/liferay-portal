/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.bookmarks.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.bookmarks.NoSuchEntryException;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.impl.BookmarksEntryModelImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class BookmarksEntryPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (BookmarksEntryPersistence)PortalBeanLocatorUtil.locate(BookmarksEntryPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		BookmarksEntry bookmarksEntry = _persistence.create(pk);

		assertNotNull(bookmarksEntry);

		assertEquals(bookmarksEntry.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		BookmarksEntry newBookmarksEntry = addBookmarksEntry();

		_persistence.remove(newBookmarksEntry);

		BookmarksEntry existingBookmarksEntry = _persistence.fetchByPrimaryKey(newBookmarksEntry.getPrimaryKey());

		assertNull(existingBookmarksEntry);
	}

	public void testUpdateNew() throws Exception {
		addBookmarksEntry();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		BookmarksEntry newBookmarksEntry = _persistence.create(pk);

		newBookmarksEntry.setUuid(randomString());

		newBookmarksEntry.setGroupId(nextLong());

		newBookmarksEntry.setCompanyId(nextLong());

		newBookmarksEntry.setUserId(nextLong());

		newBookmarksEntry.setUserName(randomString());

		newBookmarksEntry.setCreateDate(nextDate());

		newBookmarksEntry.setModifiedDate(nextDate());

		newBookmarksEntry.setResourceBlockId(nextLong());

		newBookmarksEntry.setFolderId(nextLong());

		newBookmarksEntry.setName(randomString());

		newBookmarksEntry.setUrl(randomString());

		newBookmarksEntry.setDescription(randomString());

		newBookmarksEntry.setVisits(nextInt());

		newBookmarksEntry.setPriority(nextInt());

		_persistence.update(newBookmarksEntry, false);

		BookmarksEntry existingBookmarksEntry = _persistence.findByPrimaryKey(newBookmarksEntry.getPrimaryKey());

		assertEquals(existingBookmarksEntry.getUuid(),
			newBookmarksEntry.getUuid());
		assertEquals(existingBookmarksEntry.getEntryId(),
			newBookmarksEntry.getEntryId());
		assertEquals(existingBookmarksEntry.getGroupId(),
			newBookmarksEntry.getGroupId());
		assertEquals(existingBookmarksEntry.getCompanyId(),
			newBookmarksEntry.getCompanyId());
		assertEquals(existingBookmarksEntry.getUserId(),
			newBookmarksEntry.getUserId());
		assertEquals(existingBookmarksEntry.getUserName(),
			newBookmarksEntry.getUserName());
		assertEquals(Time.getShortTimestamp(
				existingBookmarksEntry.getCreateDate()),
			Time.getShortTimestamp(newBookmarksEntry.getCreateDate()));
		assertEquals(Time.getShortTimestamp(
				existingBookmarksEntry.getModifiedDate()),
			Time.getShortTimestamp(newBookmarksEntry.getModifiedDate()));
		assertEquals(existingBookmarksEntry.getResourceBlockId(),
			newBookmarksEntry.getResourceBlockId());
		assertEquals(existingBookmarksEntry.getFolderId(),
			newBookmarksEntry.getFolderId());
		assertEquals(existingBookmarksEntry.getName(),
			newBookmarksEntry.getName());
		assertEquals(existingBookmarksEntry.getUrl(), newBookmarksEntry.getUrl());
		assertEquals(existingBookmarksEntry.getDescription(),
			newBookmarksEntry.getDescription());
		assertEquals(existingBookmarksEntry.getVisits(),
			newBookmarksEntry.getVisits());
		assertEquals(existingBookmarksEntry.getPriority(),
			newBookmarksEntry.getPriority());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		BookmarksEntry newBookmarksEntry = addBookmarksEntry();

		BookmarksEntry existingBookmarksEntry = _persistence.findByPrimaryKey(newBookmarksEntry.getPrimaryKey());

		assertEquals(existingBookmarksEntry, newBookmarksEntry);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchEntryException");
		}
		catch (NoSuchEntryException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		BookmarksEntry newBookmarksEntry = addBookmarksEntry();

		BookmarksEntry existingBookmarksEntry = _persistence.fetchByPrimaryKey(newBookmarksEntry.getPrimaryKey());

		assertEquals(existingBookmarksEntry, newBookmarksEntry);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		BookmarksEntry missingBookmarksEntry = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingBookmarksEntry);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		BookmarksEntry newBookmarksEntry = addBookmarksEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(BookmarksEntry.class,
				BookmarksEntry.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("entryId",
				newBookmarksEntry.getEntryId()));

		List<BookmarksEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		BookmarksEntry existingBookmarksEntry = result.get(0);

		assertEquals(existingBookmarksEntry, newBookmarksEntry);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(BookmarksEntry.class,
				BookmarksEntry.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("entryId", nextLong()));

		List<BookmarksEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

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

		assertEquals(1, result.size());

		Object existingEntryId = result.get(0);

		assertEquals(existingEntryId, newEntryId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(BookmarksEntry.class,
				BookmarksEntry.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("entryId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("entryId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		BookmarksEntry newBookmarksEntry = addBookmarksEntry();

		_persistence.clearCache();

		BookmarksEntryModelImpl existingBookmarksEntryModelImpl = (BookmarksEntryModelImpl)_persistence.findByPrimaryKey(newBookmarksEntry.getPrimaryKey());

		assertTrue(Validator.equals(existingBookmarksEntryModelImpl.getUuid(),
				existingBookmarksEntryModelImpl.getOriginalUuid()));
		assertEquals(existingBookmarksEntryModelImpl.getGroupId(),
			existingBookmarksEntryModelImpl.getOriginalGroupId());
	}

	protected BookmarksEntry addBookmarksEntry() throws Exception {
		long pk = nextLong();

		BookmarksEntry bookmarksEntry = _persistence.create(pk);

		bookmarksEntry.setUuid(randomString());

		bookmarksEntry.setGroupId(nextLong());

		bookmarksEntry.setCompanyId(nextLong());

		bookmarksEntry.setUserId(nextLong());

		bookmarksEntry.setUserName(randomString());

		bookmarksEntry.setCreateDate(nextDate());

		bookmarksEntry.setModifiedDate(nextDate());

		bookmarksEntry.setResourceBlockId(nextLong());

		bookmarksEntry.setFolderId(nextLong());

		bookmarksEntry.setName(randomString());

		bookmarksEntry.setUrl(randomString());

		bookmarksEntry.setDescription(randomString());

		bookmarksEntry.setVisits(nextInt());

		bookmarksEntry.setPriority(nextInt());

		_persistence.update(bookmarksEntry, false);

		return bookmarksEntry;
	}

	private BookmarksEntryPersistence _persistence;
}