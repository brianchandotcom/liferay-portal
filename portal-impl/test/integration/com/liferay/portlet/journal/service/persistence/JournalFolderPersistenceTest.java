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

package com.liferay.portlet.journal.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.journal.NoSuchFolderException;
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.journal.model.impl.JournalFolderModelImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class JournalFolderPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (JournalFolderPersistence)PortalBeanLocatorUtil.locate(JournalFolderPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		JournalFolder journalFolder = _persistence.create(pk);

		assertNotNull(journalFolder);

		assertEquals(journalFolder.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		JournalFolder newJournalFolder = addJournalFolder();

		_persistence.remove(newJournalFolder);

		JournalFolder existingJournalFolder = _persistence.fetchByPrimaryKey(newJournalFolder.getPrimaryKey());

		assertNull(existingJournalFolder);
	}

	public void testUpdateNew() throws Exception {
		addJournalFolder();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		JournalFolder newJournalFolder = _persistence.create(pk);

		newJournalFolder.setUuid(randomString());

		newJournalFolder.setGroupId(nextLong());

		newJournalFolder.setCompanyId(nextLong());

		newJournalFolder.setUserId(nextLong());

		newJournalFolder.setUserName(randomString());

		newJournalFolder.setCreateDate(nextDate());

		newJournalFolder.setModifiedDate(nextDate());

		newJournalFolder.setParentFolderId(nextLong());

		newJournalFolder.setName(randomString());

		newJournalFolder.setDescription(randomString());

		_persistence.update(newJournalFolder, false);

		JournalFolder existingJournalFolder = _persistence.findByPrimaryKey(newJournalFolder.getPrimaryKey());

		assertEquals(existingJournalFolder.getUuid(), newJournalFolder.getUuid());
		assertEquals(existingJournalFolder.getFolderId(),
			newJournalFolder.getFolderId());
		assertEquals(existingJournalFolder.getGroupId(),
			newJournalFolder.getGroupId());
		assertEquals(existingJournalFolder.getCompanyId(),
			newJournalFolder.getCompanyId());
		assertEquals(existingJournalFolder.getUserId(),
			newJournalFolder.getUserId());
		assertEquals(existingJournalFolder.getUserName(),
			newJournalFolder.getUserName());
		assertEquals(Time.getShortTimestamp(
				existingJournalFolder.getCreateDate()),
			Time.getShortTimestamp(newJournalFolder.getCreateDate()));
		assertEquals(Time.getShortTimestamp(
				existingJournalFolder.getModifiedDate()),
			Time.getShortTimestamp(newJournalFolder.getModifiedDate()));
		assertEquals(existingJournalFolder.getParentFolderId(),
			newJournalFolder.getParentFolderId());
		assertEquals(existingJournalFolder.getName(), newJournalFolder.getName());
		assertEquals(existingJournalFolder.getDescription(),
			newJournalFolder.getDescription());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		JournalFolder newJournalFolder = addJournalFolder();

		JournalFolder existingJournalFolder = _persistence.findByPrimaryKey(newJournalFolder.getPrimaryKey());

		assertEquals(existingJournalFolder, newJournalFolder);
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
		JournalFolder newJournalFolder = addJournalFolder();

		JournalFolder existingJournalFolder = _persistence.fetchByPrimaryKey(newJournalFolder.getPrimaryKey());

		assertEquals(existingJournalFolder, newJournalFolder);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		JournalFolder missingJournalFolder = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingJournalFolder);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		JournalFolder newJournalFolder = addJournalFolder();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(JournalFolder.class,
				JournalFolder.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("folderId",
				newJournalFolder.getFolderId()));

		List<JournalFolder> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		JournalFolder existingJournalFolder = result.get(0);

		assertEquals(existingJournalFolder, newJournalFolder);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(JournalFolder.class,
				JournalFolder.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("folderId", nextLong()));

		List<JournalFolder> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		JournalFolder newJournalFolder = addJournalFolder();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(JournalFolder.class,
				JournalFolder.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("folderId"));

		Object newFolderId = newJournalFolder.getFolderId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("folderId",
				new Object[] { newFolderId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingFolderId = result.get(0);

		assertEquals(existingFolderId, newFolderId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(JournalFolder.class,
				JournalFolder.class.getClassLoader());

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

		JournalFolder newJournalFolder = addJournalFolder();

		_persistence.clearCache();

		JournalFolderModelImpl existingJournalFolderModelImpl = (JournalFolderModelImpl)_persistence.findByPrimaryKey(newJournalFolder.getPrimaryKey());

		assertTrue(Validator.equals(existingJournalFolderModelImpl.getUuid(),
				existingJournalFolderModelImpl.getOriginalUuid()));
		assertEquals(existingJournalFolderModelImpl.getGroupId(),
			existingJournalFolderModelImpl.getOriginalGroupId());
	}

	protected JournalFolder addJournalFolder() throws Exception {
		long pk = nextLong();

		JournalFolder journalFolder = _persistence.create(pk);

		journalFolder.setUuid(randomString());

		journalFolder.setGroupId(nextLong());

		journalFolder.setCompanyId(nextLong());

		journalFolder.setUserId(nextLong());

		journalFolder.setUserName(randomString());

		journalFolder.setCreateDate(nextDate());

		journalFolder.setModifiedDate(nextDate());

		journalFolder.setParentFolderId(nextLong());

		journalFolder.setName(randomString());

		journalFolder.setDescription(randomString());

		_persistence.update(journalFolder, false);

		return journalFolder;
	}

	private JournalFolderPersistence _persistence;
}