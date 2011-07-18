/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import com.liferay.portlet.documentlibrary.NoSuchSyncException;
import com.liferay.portlet.documentlibrary.model.DLSync;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class DLSyncPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (DLSyncPersistence)PortalBeanLocatorUtil.locate(DLSyncPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		String pk = randomString();

		DLSync dlSync = _persistence.create(pk);

		assertNotNull(dlSync);

		assertEquals(dlSync.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		DLSync newDLSync = addDLSync();

		_persistence.remove(newDLSync);

		DLSync existingDLSync = _persistence.fetchByPrimaryKey(newDLSync.getPrimaryKey());

		assertNull(existingDLSync);
	}

	public void testUpdateNew() throws Exception {
		addDLSync();
	}

	public void testUpdateExisting() throws Exception {
		String pk = randomString();

		DLSync newDLSync = _persistence.create(pk);

		newDLSync.setCompanyId(nextLong());

		newDLSync.setModifiedDate(nextDate());

		newDLSync.setRepositoryId(nextLong());

		newDLSync.setEvent(randomString());

		newDLSync.setType(randomString());

		_persistence.update(newDLSync, false);

		DLSync existingDLSync = _persistence.findByPrimaryKey(newDLSync.getPrimaryKey());

		assertEquals(existingDLSync.getFileId(), newDLSync.getFileId());
		assertEquals(existingDLSync.getCompanyId(), newDLSync.getCompanyId());
		assertEquals(Time.getShortTimestamp(existingDLSync.getModifiedDate()),
			Time.getShortTimestamp(newDLSync.getModifiedDate()));
		assertEquals(existingDLSync.getRepositoryId(),
			newDLSync.getRepositoryId());
		assertEquals(existingDLSync.getEvent(), newDLSync.getEvent());
		assertEquals(existingDLSync.getType(), newDLSync.getType());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		DLSync newDLSync = addDLSync();

		DLSync existingDLSync = _persistence.findByPrimaryKey(newDLSync.getPrimaryKey());

		assertEquals(existingDLSync, newDLSync);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		String pk = randomString();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchSyncException");
		}
		catch (NoSuchSyncException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		DLSync newDLSync = addDLSync();

		DLSync existingDLSync = _persistence.fetchByPrimaryKey(newDLSync.getPrimaryKey());

		assertEquals(existingDLSync, newDLSync);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		String pk = randomString();

		DLSync missingDLSync = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingDLSync);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		DLSync newDLSync = addDLSync();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLSync.class,
				DLSync.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("fileId",
				newDLSync.getFileId()));

		List<DLSync> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		DLSync existingDLSync = result.get(0);

		assertEquals(existingDLSync, newDLSync);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLSync.class,
				DLSync.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("fileId", randomString()));

		List<DLSync> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		DLSync newDLSync = addDLSync();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLSync.class,
				DLSync.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("fileId"));

		Object newFileId = newDLSync.getFileId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("fileId",
				new Object[] { newFileId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingFileId = result.get(0);

		assertEquals(existingFileId, newFileId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLSync.class,
				DLSync.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("fileId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("fileId",
				new Object[] { randomString() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected DLSync addDLSync() throws Exception {
		String pk = randomString();

		DLSync dlSync = _persistence.create(pk);

		dlSync.setCompanyId(nextLong());

		dlSync.setModifiedDate(nextDate());

		dlSync.setRepositoryId(nextLong());

		dlSync.setEvent(randomString());

		dlSync.setType(randomString());

		_persistence.update(dlSync, false);

		return dlSync;
	}

	private DLSyncPersistence _persistence;
}