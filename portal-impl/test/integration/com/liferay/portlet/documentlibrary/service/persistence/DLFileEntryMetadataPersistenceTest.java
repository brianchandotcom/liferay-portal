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
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException;
import com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryMetadataModelImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class DLFileEntryMetadataPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (DLFileEntryMetadataPersistence)PortalBeanLocatorUtil.locate(DLFileEntryMetadataPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		DLFileEntryMetadata dlFileEntryMetadata = _persistence.create(pk);

		assertNotNull(dlFileEntryMetadata);

		assertEquals(dlFileEntryMetadata.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		DLFileEntryMetadata newDLFileEntryMetadata = addDLFileEntryMetadata();

		_persistence.remove(newDLFileEntryMetadata);

		DLFileEntryMetadata existingDLFileEntryMetadata = _persistence.fetchByPrimaryKey(newDLFileEntryMetadata.getPrimaryKey());

		assertNull(existingDLFileEntryMetadata);
	}

	public void testUpdateNew() throws Exception {
		addDLFileEntryMetadata();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		DLFileEntryMetadata newDLFileEntryMetadata = _persistence.create(pk);

		newDLFileEntryMetadata.setUuid(randomString());

		newDLFileEntryMetadata.setDDMStorageId(nextLong());

		newDLFileEntryMetadata.setDDMStructureId(nextLong());

		newDLFileEntryMetadata.setFileEntryTypeId(nextLong());

		newDLFileEntryMetadata.setFileEntryId(nextLong());

		newDLFileEntryMetadata.setFileVersionId(nextLong());

		_persistence.update(newDLFileEntryMetadata, false);

		DLFileEntryMetadata existingDLFileEntryMetadata = _persistence.findByPrimaryKey(newDLFileEntryMetadata.getPrimaryKey());

		assertEquals(existingDLFileEntryMetadata.getUuid(),
			newDLFileEntryMetadata.getUuid());
		assertEquals(existingDLFileEntryMetadata.getFileEntryMetadataId(),
			newDLFileEntryMetadata.getFileEntryMetadataId());
		assertEquals(existingDLFileEntryMetadata.getDDMStorageId(),
			newDLFileEntryMetadata.getDDMStorageId());
		assertEquals(existingDLFileEntryMetadata.getDDMStructureId(),
			newDLFileEntryMetadata.getDDMStructureId());
		assertEquals(existingDLFileEntryMetadata.getFileEntryTypeId(),
			newDLFileEntryMetadata.getFileEntryTypeId());
		assertEquals(existingDLFileEntryMetadata.getFileEntryId(),
			newDLFileEntryMetadata.getFileEntryId());
		assertEquals(existingDLFileEntryMetadata.getFileVersionId(),
			newDLFileEntryMetadata.getFileVersionId());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		DLFileEntryMetadata newDLFileEntryMetadata = addDLFileEntryMetadata();

		DLFileEntryMetadata existingDLFileEntryMetadata = _persistence.findByPrimaryKey(newDLFileEntryMetadata.getPrimaryKey());

		assertEquals(existingDLFileEntryMetadata, newDLFileEntryMetadata);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail(
				"Missing entity did not throw NoSuchFileEntryMetadataException");
		}
		catch (NoSuchFileEntryMetadataException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		DLFileEntryMetadata newDLFileEntryMetadata = addDLFileEntryMetadata();

		DLFileEntryMetadata existingDLFileEntryMetadata = _persistence.fetchByPrimaryKey(newDLFileEntryMetadata.getPrimaryKey());

		assertEquals(existingDLFileEntryMetadata, newDLFileEntryMetadata);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		DLFileEntryMetadata missingDLFileEntryMetadata = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingDLFileEntryMetadata);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		DLFileEntryMetadata newDLFileEntryMetadata = addDLFileEntryMetadata();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLFileEntryMetadata.class,
				DLFileEntryMetadata.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("fileEntryMetadataId",
				newDLFileEntryMetadata.getFileEntryMetadataId()));

		List<DLFileEntryMetadata> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		DLFileEntryMetadata existingDLFileEntryMetadata = result.get(0);

		assertEquals(existingDLFileEntryMetadata, newDLFileEntryMetadata);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLFileEntryMetadata.class,
				DLFileEntryMetadata.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("fileEntryMetadataId",
				nextLong()));

		List<DLFileEntryMetadata> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		DLFileEntryMetadata newDLFileEntryMetadata = addDLFileEntryMetadata();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLFileEntryMetadata.class,
				DLFileEntryMetadata.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"fileEntryMetadataId"));

		Object newFileEntryMetadataId = newDLFileEntryMetadata.getFileEntryMetadataId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("fileEntryMetadataId",
				new Object[] { newFileEntryMetadataId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingFileEntryMetadataId = result.get(0);

		assertEquals(existingFileEntryMetadataId, newFileEntryMetadataId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLFileEntryMetadata.class,
				DLFileEntryMetadata.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"fileEntryMetadataId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("fileEntryMetadataId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		DLFileEntryMetadata newDLFileEntryMetadata = addDLFileEntryMetadata();

		_persistence.clearCache();

		DLFileEntryMetadataModelImpl existingDLFileEntryMetadataModelImpl = (DLFileEntryMetadataModelImpl)_persistence.findByPrimaryKey(newDLFileEntryMetadata.getPrimaryKey());

		assertEquals(existingDLFileEntryMetadataModelImpl.getDDMStructureId(),
			existingDLFileEntryMetadataModelImpl.getOriginalDDMStructureId());
		assertEquals(existingDLFileEntryMetadataModelImpl.getFileVersionId(),
			existingDLFileEntryMetadataModelImpl.getOriginalFileVersionId());

		assertEquals(existingDLFileEntryMetadataModelImpl.getFileEntryId(),
			existingDLFileEntryMetadataModelImpl.getOriginalFileEntryId());
		assertEquals(existingDLFileEntryMetadataModelImpl.getFileVersionId(),
			existingDLFileEntryMetadataModelImpl.getOriginalFileVersionId());
	}

	protected DLFileEntryMetadata addDLFileEntryMetadata()
		throws Exception {
		long pk = nextLong();

		DLFileEntryMetadata dlFileEntryMetadata = _persistence.create(pk);

		dlFileEntryMetadata.setUuid(randomString());

		dlFileEntryMetadata.setDDMStorageId(nextLong());

		dlFileEntryMetadata.setDDMStructureId(nextLong());

		dlFileEntryMetadata.setFileEntryTypeId(nextLong());

		dlFileEntryMetadata.setFileEntryId(nextLong());

		dlFileEntryMetadata.setFileVersionId(nextLong());

		_persistence.update(dlFileEntryMetadata, false);

		return dlFileEntryMetadata;
	}

	private DLFileEntryMetadataPersistence _persistence;
}