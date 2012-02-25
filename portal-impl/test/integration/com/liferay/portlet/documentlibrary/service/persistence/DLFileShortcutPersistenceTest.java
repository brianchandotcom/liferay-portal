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

import com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.impl.DLFileShortcutModelImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class DLFileShortcutPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (DLFileShortcutPersistence)PortalBeanLocatorUtil.locate(DLFileShortcutPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		DLFileShortcut dlFileShortcut = _persistence.create(pk);

		assertNotNull(dlFileShortcut);

		assertEquals(dlFileShortcut.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		DLFileShortcut newDLFileShortcut = addDLFileShortcut();

		_persistence.remove(newDLFileShortcut);

		DLFileShortcut existingDLFileShortcut = _persistence.fetchByPrimaryKey(newDLFileShortcut.getPrimaryKey());

		assertNull(existingDLFileShortcut);
	}

	public void testUpdateNew() throws Exception {
		addDLFileShortcut();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		DLFileShortcut newDLFileShortcut = _persistence.create(pk);

		newDLFileShortcut.setUuid(randomString());

		newDLFileShortcut.setGroupId(nextLong());

		newDLFileShortcut.setCompanyId(nextLong());

		newDLFileShortcut.setUserId(nextLong());

		newDLFileShortcut.setUserName(randomString());

		newDLFileShortcut.setCreateDate(nextDate());

		newDLFileShortcut.setModifiedDate(nextDate());

		newDLFileShortcut.setRepositoryId(nextLong());

		newDLFileShortcut.setFolderId(nextLong());

		newDLFileShortcut.setToFileEntryId(nextLong());

		newDLFileShortcut.setStatus(nextInt());

		newDLFileShortcut.setStatusByUserId(nextLong());

		newDLFileShortcut.setStatusByUserName(randomString());

		newDLFileShortcut.setStatusDate(nextDate());

		_persistence.update(newDLFileShortcut, false);

		DLFileShortcut existingDLFileShortcut = _persistence.findByPrimaryKey(newDLFileShortcut.getPrimaryKey());

		assertEquals(existingDLFileShortcut.getUuid(),
			newDLFileShortcut.getUuid());
		assertEquals(existingDLFileShortcut.getFileShortcutId(),
			newDLFileShortcut.getFileShortcutId());
		assertEquals(existingDLFileShortcut.getGroupId(),
			newDLFileShortcut.getGroupId());
		assertEquals(existingDLFileShortcut.getCompanyId(),
			newDLFileShortcut.getCompanyId());
		assertEquals(existingDLFileShortcut.getUserId(),
			newDLFileShortcut.getUserId());
		assertEquals(existingDLFileShortcut.getUserName(),
			newDLFileShortcut.getUserName());
		assertEquals(Time.getShortTimestamp(
				existingDLFileShortcut.getCreateDate()),
			Time.getShortTimestamp(newDLFileShortcut.getCreateDate()));
		assertEquals(Time.getShortTimestamp(
				existingDLFileShortcut.getModifiedDate()),
			Time.getShortTimestamp(newDLFileShortcut.getModifiedDate()));
		assertEquals(existingDLFileShortcut.getRepositoryId(),
			newDLFileShortcut.getRepositoryId());
		assertEquals(existingDLFileShortcut.getFolderId(),
			newDLFileShortcut.getFolderId());
		assertEquals(existingDLFileShortcut.getToFileEntryId(),
			newDLFileShortcut.getToFileEntryId());
		assertEquals(existingDLFileShortcut.getStatus(),
			newDLFileShortcut.getStatus());
		assertEquals(existingDLFileShortcut.getStatusByUserId(),
			newDLFileShortcut.getStatusByUserId());
		assertEquals(existingDLFileShortcut.getStatusByUserName(),
			newDLFileShortcut.getStatusByUserName());
		assertEquals(Time.getShortTimestamp(
				existingDLFileShortcut.getStatusDate()),
			Time.getShortTimestamp(newDLFileShortcut.getStatusDate()));
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		DLFileShortcut newDLFileShortcut = addDLFileShortcut();

		DLFileShortcut existingDLFileShortcut = _persistence.findByPrimaryKey(newDLFileShortcut.getPrimaryKey());

		assertEquals(existingDLFileShortcut, newDLFileShortcut);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchFileShortcutException");
		}
		catch (NoSuchFileShortcutException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		DLFileShortcut newDLFileShortcut = addDLFileShortcut();

		DLFileShortcut existingDLFileShortcut = _persistence.fetchByPrimaryKey(newDLFileShortcut.getPrimaryKey());

		assertEquals(existingDLFileShortcut, newDLFileShortcut);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		DLFileShortcut missingDLFileShortcut = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingDLFileShortcut);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		DLFileShortcut newDLFileShortcut = addDLFileShortcut();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLFileShortcut.class,
				DLFileShortcut.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("fileShortcutId",
				newDLFileShortcut.getFileShortcutId()));

		List<DLFileShortcut> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		DLFileShortcut existingDLFileShortcut = result.get(0);

		assertEquals(existingDLFileShortcut, newDLFileShortcut);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLFileShortcut.class,
				DLFileShortcut.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("fileShortcutId", nextLong()));

		List<DLFileShortcut> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		DLFileShortcut newDLFileShortcut = addDLFileShortcut();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLFileShortcut.class,
				DLFileShortcut.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"fileShortcutId"));

		Object newFileShortcutId = newDLFileShortcut.getFileShortcutId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("fileShortcutId",
				new Object[] { newFileShortcutId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingFileShortcutId = result.get(0);

		assertEquals(existingFileShortcutId, newFileShortcutId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLFileShortcut.class,
				DLFileShortcut.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"fileShortcutId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("fileShortcutId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		DLFileShortcut newDLFileShortcut = addDLFileShortcut();

		_persistence.clearCache();

		DLFileShortcutModelImpl existingDLFileShortcutModelImpl = (DLFileShortcutModelImpl)_persistence.findByPrimaryKey(newDLFileShortcut.getPrimaryKey());

		assertTrue(Validator.equals(existingDLFileShortcutModelImpl.getUuid(),
				existingDLFileShortcutModelImpl.getOriginalUuid()));
		assertEquals(existingDLFileShortcutModelImpl.getGroupId(),
			existingDLFileShortcutModelImpl.getOriginalGroupId());
	}

	protected DLFileShortcut addDLFileShortcut() throws Exception {
		long pk = nextLong();

		DLFileShortcut dlFileShortcut = _persistence.create(pk);

		dlFileShortcut.setUuid(randomString());

		dlFileShortcut.setGroupId(nextLong());

		dlFileShortcut.setCompanyId(nextLong());

		dlFileShortcut.setUserId(nextLong());

		dlFileShortcut.setUserName(randomString());

		dlFileShortcut.setCreateDate(nextDate());

		dlFileShortcut.setModifiedDate(nextDate());

		dlFileShortcut.setRepositoryId(nextLong());

		dlFileShortcut.setFolderId(nextLong());

		dlFileShortcut.setToFileEntryId(nextLong());

		dlFileShortcut.setStatus(nextInt());

		dlFileShortcut.setStatusByUserId(nextLong());

		dlFileShortcut.setStatusByUserName(randomString());

		dlFileShortcut.setStatusDate(nextDate());

		_persistence.update(dlFileShortcut, false);

		return dlFileShortcut;
	}

	private DLFileShortcutPersistence _persistence;
}