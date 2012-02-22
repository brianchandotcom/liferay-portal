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

import com.liferay.portlet.bookmarks.NoSuchFolderException;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.model.impl.BookmarksFolderModelImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class BookmarksFolderPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (BookmarksFolderPersistence)PortalBeanLocatorUtil.locate(BookmarksFolderPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		BookmarksFolder bookmarksFolder = _persistence.create(pk);

		assertNotNull(bookmarksFolder);

		assertEquals(bookmarksFolder.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		BookmarksFolder newBookmarksFolder = addBookmarksFolder();

		_persistence.remove(newBookmarksFolder);

		BookmarksFolder existingBookmarksFolder = _persistence.fetchByPrimaryKey(newBookmarksFolder.getPrimaryKey());

		assertNull(existingBookmarksFolder);
	}

	public void testUpdateNew() throws Exception {
		addBookmarksFolder();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		BookmarksFolder newBookmarksFolder = _persistence.create(pk);

		newBookmarksFolder.setUuid(randomString());

		newBookmarksFolder.setGroupId(nextLong());

		newBookmarksFolder.setCompanyId(nextLong());

		newBookmarksFolder.setUserId(nextLong());

		newBookmarksFolder.setUserName(randomString());

		newBookmarksFolder.setCreateDate(nextDate());

		newBookmarksFolder.setModifiedDate(nextDate());

		newBookmarksFolder.setResourceBlockId(nextLong());

		newBookmarksFolder.setParentFolderId(nextLong());

		newBookmarksFolder.setName(randomString());

		newBookmarksFolder.setDescription(randomString());

		_persistence.update(newBookmarksFolder, false);

		BookmarksFolder existingBookmarksFolder = _persistence.findByPrimaryKey(newBookmarksFolder.getPrimaryKey());

		assertEquals(existingBookmarksFolder.getUuid(),
			newBookmarksFolder.getUuid());
		assertEquals(existingBookmarksFolder.getFolderId(),
			newBookmarksFolder.getFolderId());
		assertEquals(existingBookmarksFolder.getGroupId(),
			newBookmarksFolder.getGroupId());
		assertEquals(existingBookmarksFolder.getCompanyId(),
			newBookmarksFolder.getCompanyId());
		assertEquals(existingBookmarksFolder.getUserId(),
			newBookmarksFolder.getUserId());
		assertEquals(existingBookmarksFolder.getUserName(),
			newBookmarksFolder.getUserName());
		assertEquals(Time.getShortTimestamp(
				existingBookmarksFolder.getCreateDate()),
			Time.getShortTimestamp(newBookmarksFolder.getCreateDate()));
		assertEquals(Time.getShortTimestamp(
				existingBookmarksFolder.getModifiedDate()),
			Time.getShortTimestamp(newBookmarksFolder.getModifiedDate()));
		assertEquals(existingBookmarksFolder.getResourceBlockId(),
			newBookmarksFolder.getResourceBlockId());
		assertEquals(existingBookmarksFolder.getParentFolderId(),
			newBookmarksFolder.getParentFolderId());
		assertEquals(existingBookmarksFolder.getName(),
			newBookmarksFolder.getName());
		assertEquals(existingBookmarksFolder.getDescription(),
			newBookmarksFolder.getDescription());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		BookmarksFolder newBookmarksFolder = addBookmarksFolder();

		BookmarksFolder existingBookmarksFolder = _persistence.findByPrimaryKey(newBookmarksFolder.getPrimaryKey());

		assertEquals(existingBookmarksFolder, newBookmarksFolder);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchFolderException");
		}
		catch (NoSuchFolderException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		BookmarksFolder newBookmarksFolder = addBookmarksFolder();

		BookmarksFolder existingBookmarksFolder = _persistence.fetchByPrimaryKey(newBookmarksFolder.getPrimaryKey());

		assertEquals(existingBookmarksFolder, newBookmarksFolder);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		BookmarksFolder missingBookmarksFolder = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingBookmarksFolder);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		BookmarksFolder newBookmarksFolder = addBookmarksFolder();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(BookmarksFolder.class,
				BookmarksFolder.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("folderId",
				newBookmarksFolder.getFolderId()));

		List<BookmarksFolder> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		BookmarksFolder existingBookmarksFolder = result.get(0);

		assertEquals(existingBookmarksFolder, newBookmarksFolder);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(BookmarksFolder.class,
				BookmarksFolder.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("folderId", nextLong()));

		List<BookmarksFolder> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		BookmarksFolder newBookmarksFolder = addBookmarksFolder();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(BookmarksFolder.class,
				BookmarksFolder.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("folderId"));

		Object newFolderId = newBookmarksFolder.getFolderId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("folderId",
				new Object[] { newFolderId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingFolderId = result.get(0);

		assertEquals(existingFolderId, newFolderId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(BookmarksFolder.class,
				BookmarksFolder.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("folderId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("folderId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		BookmarksFolder newBookmarksFolder = addBookmarksFolder();

		_persistence.clearCache();

		BookmarksFolderModelImpl existingBookmarksFolderModelImpl = (BookmarksFolderModelImpl)_persistence.findByPrimaryKey(newBookmarksFolder.getPrimaryKey());

		assertTrue(Validator.equals(
				existingBookmarksFolderModelImpl.getUuid(),
				existingBookmarksFolderModelImpl.getOriginalUuid()));
		assertEquals(existingBookmarksFolderModelImpl.getGroupId(),
			existingBookmarksFolderModelImpl.getOriginalGroupId());
	}

	protected BookmarksFolder addBookmarksFolder() throws Exception {
		long pk = nextLong();

		BookmarksFolder bookmarksFolder = _persistence.create(pk);

		bookmarksFolder.setUuid(randomString());

		bookmarksFolder.setGroupId(nextLong());

		bookmarksFolder.setCompanyId(nextLong());

		bookmarksFolder.setUserId(nextLong());

		bookmarksFolder.setUserName(randomString());

		bookmarksFolder.setCreateDate(nextDate());

		bookmarksFolder.setModifiedDate(nextDate());

		bookmarksFolder.setResourceBlockId(nextLong());

		bookmarksFolder.setParentFolderId(nextLong());

		bookmarksFolder.setName(randomString());

		bookmarksFolder.setDescription(randomString());

		_persistence.update(bookmarksFolder, false);

		return bookmarksFolder;
	}

	private BookmarksFolderPersistence _persistence;
}