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

package com.liferay.portlet.documentlibrary.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.impl.DLFolderModelImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class DLFolderPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (DLFolderPersistence)PortalBeanLocatorUtil.locate(DLFolderPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		DLFolder dlFolder = _persistence.create(pk);

		assertNotNull(dlFolder);

		assertEquals(dlFolder.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		DLFolder newDLFolder = addDLFolder();

		_persistence.remove(newDLFolder);

		DLFolder existingDLFolder = _persistence.fetchByPrimaryKey(newDLFolder.getPrimaryKey());

		assertNull(existingDLFolder);
	}

	public void testUpdateNew() throws Exception {
		addDLFolder();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		DLFolder newDLFolder = _persistence.create(pk);

		newDLFolder.setUuid(randomString());

		newDLFolder.setGroupId(nextLong());

		newDLFolder.setCompanyId(nextLong());

		newDLFolder.setUserId(nextLong());

		newDLFolder.setUserName(randomString());

		newDLFolder.setCreateDate(nextDate());

		newDLFolder.setModifiedDate(nextDate());

		newDLFolder.setRepositoryId(nextLong());

		newDLFolder.setMountPoint(randomBoolean());

		newDLFolder.setParentFolderId(nextLong());

		newDLFolder.setName(randomString());

		newDLFolder.setDescription(randomString());

		newDLFolder.setLastPostDate(nextDate());

		newDLFolder.setDefaultFileEntryTypeId(nextLong());

		newDLFolder.setOverrideFileEntryTypes(randomBoolean());

		_persistence.update(newDLFolder, false);

		DLFolder existingDLFolder = _persistence.findByPrimaryKey(newDLFolder.getPrimaryKey());

		assertEquals(existingDLFolder.getUuid(), newDLFolder.getUuid());
		assertEquals(existingDLFolder.getFolderId(), newDLFolder.getFolderId());
		assertEquals(existingDLFolder.getGroupId(), newDLFolder.getGroupId());
		assertEquals(existingDLFolder.getCompanyId(), newDLFolder.getCompanyId());
		assertEquals(existingDLFolder.getUserId(), newDLFolder.getUserId());
		assertEquals(existingDLFolder.getUserName(), newDLFolder.getUserName());
		assertEquals(Time.getShortTimestamp(existingDLFolder.getCreateDate()),
			Time.getShortTimestamp(newDLFolder.getCreateDate()));
		assertEquals(Time.getShortTimestamp(existingDLFolder.getModifiedDate()),
			Time.getShortTimestamp(newDLFolder.getModifiedDate()));
		assertEquals(existingDLFolder.getRepositoryId(),
			newDLFolder.getRepositoryId());
		assertEquals(existingDLFolder.getMountPoint(),
			newDLFolder.getMountPoint());
		assertEquals(existingDLFolder.getParentFolderId(),
			newDLFolder.getParentFolderId());
		assertEquals(existingDLFolder.getName(), newDLFolder.getName());
		assertEquals(existingDLFolder.getDescription(),
			newDLFolder.getDescription());
		assertEquals(Time.getShortTimestamp(existingDLFolder.getLastPostDate()),
			Time.getShortTimestamp(newDLFolder.getLastPostDate()));
		assertEquals(existingDLFolder.getDefaultFileEntryTypeId(),
			newDLFolder.getDefaultFileEntryTypeId());
		assertEquals(existingDLFolder.getOverrideFileEntryTypes(),
			newDLFolder.getOverrideFileEntryTypes());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		DLFolder newDLFolder = addDLFolder();

		DLFolder existingDLFolder = _persistence.findByPrimaryKey(newDLFolder.getPrimaryKey());

		assertEquals(existingDLFolder, newDLFolder);
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
		DLFolder newDLFolder = addDLFolder();

		DLFolder existingDLFolder = _persistence.fetchByPrimaryKey(newDLFolder.getPrimaryKey());

		assertEquals(existingDLFolder, newDLFolder);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		DLFolder missingDLFolder = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingDLFolder);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		DLFolder newDLFolder = addDLFolder();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLFolder.class,
				DLFolder.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("folderId",
				newDLFolder.getFolderId()));

		List<DLFolder> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		DLFolder existingDLFolder = result.get(0);

		assertEquals(existingDLFolder, newDLFolder);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLFolder.class,
				DLFolder.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("folderId", nextLong()));

		List<DLFolder> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		DLFolder newDLFolder = addDLFolder();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLFolder.class,
				DLFolder.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("folderId"));

		Object newFolderId = newDLFolder.getFolderId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("folderId",
				new Object[] { newFolderId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingFolderId = result.get(0);

		assertEquals(existingFolderId, newFolderId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLFolder.class,
				DLFolder.class.getClassLoader());

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

		DLFolder newDLFolder = addDLFolder();

		_persistence.clearCache();

		DLFolderModelImpl existingDLFolderModelImpl = (DLFolderModelImpl)_persistence.findByPrimaryKey(newDLFolder.getPrimaryKey());

		assertTrue(Validator.equals(existingDLFolderModelImpl.getUuid(),
				existingDLFolderModelImpl.getOriginalUuid()));
		assertEquals(existingDLFolderModelImpl.getGroupId(),
			existingDLFolderModelImpl.getOriginalGroupId());

		assertEquals(existingDLFolderModelImpl.getRepositoryId(),
			existingDLFolderModelImpl.getOriginalRepositoryId());

		assertEquals(existingDLFolderModelImpl.getGroupId(),
			existingDLFolderModelImpl.getOriginalGroupId());
		assertEquals(existingDLFolderModelImpl.getParentFolderId(),
			existingDLFolderModelImpl.getOriginalParentFolderId());
		assertTrue(Validator.equals(existingDLFolderModelImpl.getName(),
				existingDLFolderModelImpl.getOriginalName()));
	}

	protected DLFolder addDLFolder() throws Exception {
		long pk = nextLong();

		DLFolder dlFolder = _persistence.create(pk);

		dlFolder.setUuid(randomString());

		dlFolder.setGroupId(nextLong());

		dlFolder.setCompanyId(nextLong());

		dlFolder.setUserId(nextLong());

		dlFolder.setUserName(randomString());

		dlFolder.setCreateDate(nextDate());

		dlFolder.setModifiedDate(nextDate());

		dlFolder.setRepositoryId(nextLong());

		dlFolder.setMountPoint(randomBoolean());

		dlFolder.setParentFolderId(nextLong());

		dlFolder.setName(randomString());

		dlFolder.setDescription(randomString());

		dlFolder.setLastPostDate(nextDate());

		dlFolder.setDefaultFileEntryTypeId(nextLong());

		dlFolder.setOverrideFileEntryTypes(randomBoolean());

		_persistence.update(dlFolder, false);

		return dlFolder;
	}

	private DLFolderPersistence _persistence;
}