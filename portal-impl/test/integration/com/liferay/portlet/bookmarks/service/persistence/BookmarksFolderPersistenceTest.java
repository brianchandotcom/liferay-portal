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
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.ExecutionTestListeners;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.PersistenceEnvConfigTestListener;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.bookmarks.NoSuchFolderException;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.model.impl.BookmarksFolderModelImpl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
@ExecutionTestListeners(listeners =  {
	PersistenceEnvConfigTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class BookmarksFolderPersistenceTest {
	@Before
	public void setUp() throws Exception {
		_persistence = (BookmarksFolderPersistence)PortalBeanLocatorUtil.locate(BookmarksFolderPersistence.class.getName());
	}

	@Test
	public void testCreate() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		BookmarksFolder bookmarksFolder = _persistence.create(pk);

		Assert.assertNotNull(bookmarksFolder);

		Assert.assertEquals(bookmarksFolder.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		BookmarksFolder newBookmarksFolder = addBookmarksFolder();

		_persistence.remove(newBookmarksFolder);

		BookmarksFolder existingBookmarksFolder = _persistence.fetchByPrimaryKey(newBookmarksFolder.getPrimaryKey());

		Assert.assertNull(existingBookmarksFolder);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addBookmarksFolder();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		BookmarksFolder newBookmarksFolder = _persistence.create(pk);

		newBookmarksFolder.setUuid(ServiceTestUtil.randomString());

		newBookmarksFolder.setGroupId(ServiceTestUtil.nextLong());

		newBookmarksFolder.setCompanyId(ServiceTestUtil.nextLong());

		newBookmarksFolder.setUserId(ServiceTestUtil.nextLong());

		newBookmarksFolder.setUserName(ServiceTestUtil.randomString());

		newBookmarksFolder.setCreateDate(ServiceTestUtil.nextDate());

		newBookmarksFolder.setModifiedDate(ServiceTestUtil.nextDate());

		newBookmarksFolder.setResourceBlockId(ServiceTestUtil.nextLong());

		newBookmarksFolder.setParentFolderId(ServiceTestUtil.nextLong());

		newBookmarksFolder.setName(ServiceTestUtil.randomString());

		newBookmarksFolder.setDescription(ServiceTestUtil.randomString());

		_persistence.update(newBookmarksFolder, false);

		BookmarksFolder existingBookmarksFolder = _persistence.findByPrimaryKey(newBookmarksFolder.getPrimaryKey());

		Assert.assertEquals(existingBookmarksFolder.getUuid(),
			newBookmarksFolder.getUuid());
		Assert.assertEquals(existingBookmarksFolder.getFolderId(),
			newBookmarksFolder.getFolderId());
		Assert.assertEquals(existingBookmarksFolder.getGroupId(),
			newBookmarksFolder.getGroupId());
		Assert.assertEquals(existingBookmarksFolder.getCompanyId(),
			newBookmarksFolder.getCompanyId());
		Assert.assertEquals(existingBookmarksFolder.getUserId(),
			newBookmarksFolder.getUserId());
		Assert.assertEquals(existingBookmarksFolder.getUserName(),
			newBookmarksFolder.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingBookmarksFolder.getCreateDate()),
			Time.getShortTimestamp(newBookmarksFolder.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingBookmarksFolder.getModifiedDate()),
			Time.getShortTimestamp(newBookmarksFolder.getModifiedDate()));
		Assert.assertEquals(existingBookmarksFolder.getResourceBlockId(),
			newBookmarksFolder.getResourceBlockId());
		Assert.assertEquals(existingBookmarksFolder.getParentFolderId(),
			newBookmarksFolder.getParentFolderId());
		Assert.assertEquals(existingBookmarksFolder.getName(),
			newBookmarksFolder.getName());
		Assert.assertEquals(existingBookmarksFolder.getDescription(),
			newBookmarksFolder.getDescription());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		BookmarksFolder newBookmarksFolder = addBookmarksFolder();

		BookmarksFolder existingBookmarksFolder = _persistence.findByPrimaryKey(newBookmarksFolder.getPrimaryKey());

		Assert.assertEquals(existingBookmarksFolder, newBookmarksFolder);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchFolderException");
		}
		catch (NoSuchFolderException nsee) {
		}
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		BookmarksFolder newBookmarksFolder = addBookmarksFolder();

		BookmarksFolder existingBookmarksFolder = _persistence.fetchByPrimaryKey(newBookmarksFolder.getPrimaryKey());

		Assert.assertEquals(existingBookmarksFolder, newBookmarksFolder);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		BookmarksFolder missingBookmarksFolder = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingBookmarksFolder);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		BookmarksFolder newBookmarksFolder = addBookmarksFolder();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(BookmarksFolder.class,
				BookmarksFolder.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("folderId",
				newBookmarksFolder.getFolderId()));

		List<BookmarksFolder> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		BookmarksFolder existingBookmarksFolder = result.get(0);

		Assert.assertEquals(existingBookmarksFolder, newBookmarksFolder);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(BookmarksFolder.class,
				BookmarksFolder.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("folderId",
				ServiceTestUtil.nextLong()));

		List<BookmarksFolder> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
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

		Assert.assertEquals(1, result.size());

		Object existingFolderId = result.get(0);

		Assert.assertEquals(existingFolderId, newFolderId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(BookmarksFolder.class,
				BookmarksFolder.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("folderId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("folderId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		BookmarksFolder newBookmarksFolder = addBookmarksFolder();

		_persistence.clearCache();

		BookmarksFolderModelImpl existingBookmarksFolderModelImpl = (BookmarksFolderModelImpl)_persistence.findByPrimaryKey(newBookmarksFolder.getPrimaryKey());

		Assert.assertTrue(Validator.equals(
				existingBookmarksFolderModelImpl.getUuid(),
				existingBookmarksFolderModelImpl.getOriginalUuid()));
		Assert.assertEquals(existingBookmarksFolderModelImpl.getGroupId(),
			existingBookmarksFolderModelImpl.getOriginalGroupId());
	}

	protected BookmarksFolder addBookmarksFolder() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		BookmarksFolder bookmarksFolder = _persistence.create(pk);

		bookmarksFolder.setUuid(ServiceTestUtil.randomString());

		bookmarksFolder.setGroupId(ServiceTestUtil.nextLong());

		bookmarksFolder.setCompanyId(ServiceTestUtil.nextLong());

		bookmarksFolder.setUserId(ServiceTestUtil.nextLong());

		bookmarksFolder.setUserName(ServiceTestUtil.randomString());

		bookmarksFolder.setCreateDate(ServiceTestUtil.nextDate());

		bookmarksFolder.setModifiedDate(ServiceTestUtil.nextDate());

		bookmarksFolder.setResourceBlockId(ServiceTestUtil.nextLong());

		bookmarksFolder.setParentFolderId(ServiceTestUtil.nextLong());

		bookmarksFolder.setName(ServiceTestUtil.randomString());

		bookmarksFolder.setDescription(ServiceTestUtil.randomString());

		_persistence.update(bookmarksFolder, false);

		return bookmarksFolder;
	}

	private BookmarksFolderPersistence _persistence;
}