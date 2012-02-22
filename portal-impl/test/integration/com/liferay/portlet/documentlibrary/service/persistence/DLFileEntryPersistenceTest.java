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

import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class DLFileEntryPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (DLFileEntryPersistence)PortalBeanLocatorUtil.locate(DLFileEntryPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		DLFileEntry dlFileEntry = _persistence.create(pk);

		assertNotNull(dlFileEntry);

		assertEquals(dlFileEntry.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		DLFileEntry newDLFileEntry = addDLFileEntry();

		_persistence.remove(newDLFileEntry);

		DLFileEntry existingDLFileEntry = _persistence.fetchByPrimaryKey(newDLFileEntry.getPrimaryKey());

		assertNull(existingDLFileEntry);
	}

	public void testUpdateNew() throws Exception {
		addDLFileEntry();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		DLFileEntry newDLFileEntry = _persistence.create(pk);

		newDLFileEntry.setUuid(randomString());

		newDLFileEntry.setGroupId(nextLong());

		newDLFileEntry.setCompanyId(nextLong());

		newDLFileEntry.setUserId(nextLong());

		newDLFileEntry.setUserName(randomString());

		newDLFileEntry.setVersionUserId(nextLong());

		newDLFileEntry.setVersionUserName(randomString());

		newDLFileEntry.setCreateDate(nextDate());

		newDLFileEntry.setModifiedDate(nextDate());

		newDLFileEntry.setRepositoryId(nextLong());

		newDLFileEntry.setFolderId(nextLong());

		newDLFileEntry.setName(randomString());

		newDLFileEntry.setExtension(randomString());

		newDLFileEntry.setMimeType(randomString());

		newDLFileEntry.setTitle(randomString());

		newDLFileEntry.setDescription(randomString());

		newDLFileEntry.setExtraSettings(randomString());

		newDLFileEntry.setFileEntryTypeId(nextLong());

		newDLFileEntry.setVersion(randomString());

		newDLFileEntry.setSize(nextLong());

		newDLFileEntry.setReadCount(nextInt());

		newDLFileEntry.setSmallImageId(nextLong());

		newDLFileEntry.setLargeImageId(nextLong());

		newDLFileEntry.setCustom1ImageId(nextLong());

		newDLFileEntry.setCustom2ImageId(nextLong());

		_persistence.update(newDLFileEntry, false);

		DLFileEntry existingDLFileEntry = _persistence.findByPrimaryKey(newDLFileEntry.getPrimaryKey());

		assertEquals(existingDLFileEntry.getUuid(), newDLFileEntry.getUuid());
		assertEquals(existingDLFileEntry.getFileEntryId(),
			newDLFileEntry.getFileEntryId());
		assertEquals(existingDLFileEntry.getGroupId(),
			newDLFileEntry.getGroupId());
		assertEquals(existingDLFileEntry.getCompanyId(),
			newDLFileEntry.getCompanyId());
		assertEquals(existingDLFileEntry.getUserId(), newDLFileEntry.getUserId());
		assertEquals(existingDLFileEntry.getUserName(),
			newDLFileEntry.getUserName());
		assertEquals(existingDLFileEntry.getVersionUserId(),
			newDLFileEntry.getVersionUserId());
		assertEquals(existingDLFileEntry.getVersionUserName(),
			newDLFileEntry.getVersionUserName());
		assertEquals(Time.getShortTimestamp(existingDLFileEntry.getCreateDate()),
			Time.getShortTimestamp(newDLFileEntry.getCreateDate()));
		assertEquals(Time.getShortTimestamp(
				existingDLFileEntry.getModifiedDate()),
			Time.getShortTimestamp(newDLFileEntry.getModifiedDate()));
		assertEquals(existingDLFileEntry.getRepositoryId(),
			newDLFileEntry.getRepositoryId());
		assertEquals(existingDLFileEntry.getFolderId(),
			newDLFileEntry.getFolderId());
		assertEquals(existingDLFileEntry.getName(), newDLFileEntry.getName());
		assertEquals(existingDLFileEntry.getExtension(),
			newDLFileEntry.getExtension());
		assertEquals(existingDLFileEntry.getMimeType(),
			newDLFileEntry.getMimeType());
		assertEquals(existingDLFileEntry.getTitle(), newDLFileEntry.getTitle());
		assertEquals(existingDLFileEntry.getDescription(),
			newDLFileEntry.getDescription());
		assertEquals(existingDLFileEntry.getExtraSettings(),
			newDLFileEntry.getExtraSettings());
		assertEquals(existingDLFileEntry.getFileEntryTypeId(),
			newDLFileEntry.getFileEntryTypeId());
		assertEquals(existingDLFileEntry.getVersion(),
			newDLFileEntry.getVersion());
		assertEquals(existingDLFileEntry.getSize(), newDLFileEntry.getSize());
		assertEquals(existingDLFileEntry.getReadCount(),
			newDLFileEntry.getReadCount());
		assertEquals(existingDLFileEntry.getSmallImageId(),
			newDLFileEntry.getSmallImageId());
		assertEquals(existingDLFileEntry.getLargeImageId(),
			newDLFileEntry.getLargeImageId());
		assertEquals(existingDLFileEntry.getCustom1ImageId(),
			newDLFileEntry.getCustom1ImageId());
		assertEquals(existingDLFileEntry.getCustom2ImageId(),
			newDLFileEntry.getCustom2ImageId());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		DLFileEntry newDLFileEntry = addDLFileEntry();

		DLFileEntry existingDLFileEntry = _persistence.findByPrimaryKey(newDLFileEntry.getPrimaryKey());

		assertEquals(existingDLFileEntry, newDLFileEntry);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchFileEntryException");
		}
		catch (NoSuchFileEntryException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		DLFileEntry newDLFileEntry = addDLFileEntry();

		DLFileEntry existingDLFileEntry = _persistence.fetchByPrimaryKey(newDLFileEntry.getPrimaryKey());

		assertEquals(existingDLFileEntry, newDLFileEntry);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		DLFileEntry missingDLFileEntry = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingDLFileEntry);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		DLFileEntry newDLFileEntry = addDLFileEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLFileEntry.class,
				DLFileEntry.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("fileEntryId",
				newDLFileEntry.getFileEntryId()));

		List<DLFileEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		DLFileEntry existingDLFileEntry = result.get(0);

		assertEquals(existingDLFileEntry, newDLFileEntry);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLFileEntry.class,
				DLFileEntry.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("fileEntryId", nextLong()));

		List<DLFileEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		DLFileEntry newDLFileEntry = addDLFileEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLFileEntry.class,
				DLFileEntry.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("fileEntryId"));

		Object newFileEntryId = newDLFileEntry.getFileEntryId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("fileEntryId",
				new Object[] { newFileEntryId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingFileEntryId = result.get(0);

		assertEquals(existingFileEntryId, newFileEntryId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLFileEntry.class,
				DLFileEntry.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("fileEntryId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("fileEntryId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		DLFileEntry newDLFileEntry = addDLFileEntry();

		_persistence.clearCache();

		DLFileEntryModelImpl existingDLFileEntryModelImpl = (DLFileEntryModelImpl)_persistence.findByPrimaryKey(newDLFileEntry.getPrimaryKey());

		assertTrue(Validator.equals(existingDLFileEntryModelImpl.getUuid(),
				existingDLFileEntryModelImpl.getOriginalUuid()));
		assertEquals(existingDLFileEntryModelImpl.getGroupId(),
			existingDLFileEntryModelImpl.getOriginalGroupId());

		assertEquals(existingDLFileEntryModelImpl.getGroupId(),
			existingDLFileEntryModelImpl.getOriginalGroupId());
		assertEquals(existingDLFileEntryModelImpl.getFolderId(),
			existingDLFileEntryModelImpl.getOriginalFolderId());
		assertTrue(Validator.equals(existingDLFileEntryModelImpl.getName(),
				existingDLFileEntryModelImpl.getOriginalName()));

		assertEquals(existingDLFileEntryModelImpl.getGroupId(),
			existingDLFileEntryModelImpl.getOriginalGroupId());
		assertEquals(existingDLFileEntryModelImpl.getFolderId(),
			existingDLFileEntryModelImpl.getOriginalFolderId());
		assertTrue(Validator.equals(existingDLFileEntryModelImpl.getTitle(),
				existingDLFileEntryModelImpl.getOriginalTitle()));
	}

	protected DLFileEntry addDLFileEntry() throws Exception {
		long pk = nextLong();

		DLFileEntry dlFileEntry = _persistence.create(pk);

		dlFileEntry.setUuid(randomString());

		dlFileEntry.setGroupId(nextLong());

		dlFileEntry.setCompanyId(nextLong());

		dlFileEntry.setUserId(nextLong());

		dlFileEntry.setUserName(randomString());

		dlFileEntry.setVersionUserId(nextLong());

		dlFileEntry.setVersionUserName(randomString());

		dlFileEntry.setCreateDate(nextDate());

		dlFileEntry.setModifiedDate(nextDate());

		dlFileEntry.setRepositoryId(nextLong());

		dlFileEntry.setFolderId(nextLong());

		dlFileEntry.setName(randomString());

		dlFileEntry.setExtension(randomString());

		dlFileEntry.setMimeType(randomString());

		dlFileEntry.setTitle(randomString());

		dlFileEntry.setDescription(randomString());

		dlFileEntry.setExtraSettings(randomString());

		dlFileEntry.setFileEntryTypeId(nextLong());

		dlFileEntry.setVersion(randomString());

		dlFileEntry.setSize(nextLong());

		dlFileEntry.setReadCount(nextInt());

		dlFileEntry.setSmallImageId(nextLong());

		dlFileEntry.setLargeImageId(nextLong());

		dlFileEntry.setCustom1ImageId(nextLong());

		dlFileEntry.setCustom2ImageId(nextLong());

		_persistence.update(dlFileEntry, false);

		return dlFileEntry;
	}

	private DLFileEntryPersistence _persistence;
}