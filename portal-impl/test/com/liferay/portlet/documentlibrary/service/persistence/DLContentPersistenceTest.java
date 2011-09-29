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
import com.liferay.portal.kernel.dao.jdbc.OutputBlob;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.documentlibrary.NoSuchContentException;
import com.liferay.portlet.documentlibrary.model.DLContent;
import com.liferay.portlet.documentlibrary.model.impl.DLContentModelImpl;

import java.sql.Blob;

import java.util.Arrays;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class DLContentPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (DLContentPersistence)PortalBeanLocatorUtil.locate(DLContentPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		DLContent dlContent = _persistence.create(pk);

		assertNotNull(dlContent);

		assertEquals(dlContent.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		DLContent newDLContent = addDLContent();

		_persistence.remove(newDLContent);

		DLContent existingDLContent = _persistence.fetchByPrimaryKey(newDLContent.getPrimaryKey());

		assertNull(existingDLContent);
	}

	public void testUpdateNew() throws Exception {
		addDLContent();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		DLContent newDLContent = _persistence.create(pk);

		newDLContent.setGroupId(nextLong());

		newDLContent.setCompanyId(nextLong());

		newDLContent.setRepositoryId(nextLong());

		newDLContent.setPath(randomString());

		newDLContent.setVersion(randomString());

		byte[] newDataBytes = randomString().getBytes(StringPool.UTF8);

		Blob newDataBlob = new OutputBlob(new UnsyncByteArrayInputStream(
					newDataBytes), newDataBytes.length);

		newDLContent.setData(newDataBlob);

		newDLContent.setSize(nextLong());

		_persistence.update(newDLContent, false);

		DLContent existingDLContent = _persistence.findByPrimaryKey(newDLContent.getPrimaryKey());

		assertEquals(existingDLContent.getContentId(),
			newDLContent.getContentId());
		assertEquals(existingDLContent.getGroupId(), newDLContent.getGroupId());
		assertEquals(existingDLContent.getCompanyId(),
			newDLContent.getCompanyId());
		assertEquals(existingDLContent.getRepositoryId(),
			newDLContent.getRepositoryId());
		assertEquals(existingDLContent.getPath(), newDLContent.getPath());
		assertEquals(existingDLContent.getVersion(), newDLContent.getVersion());

		Blob existingData = existingDLContent.getData();

		assertTrue(Arrays.equals(existingData.getBytes(1,
					(int)existingData.length()), newDataBytes));
		assertEquals(existingDLContent.getSize(), newDLContent.getSize());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		DLContent newDLContent = addDLContent();

		DLContent existingDLContent = _persistence.findByPrimaryKey(newDLContent.getPrimaryKey());

		assertEquals(existingDLContent, newDLContent);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchContentException");
		}
		catch (NoSuchContentException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		DLContent newDLContent = addDLContent();

		DLContent existingDLContent = _persistence.fetchByPrimaryKey(newDLContent.getPrimaryKey());

		assertEquals(existingDLContent, newDLContent);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		DLContent missingDLContent = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingDLContent);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		DLContent newDLContent = addDLContent();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLContent.class,
				DLContent.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("contentId",
				newDLContent.getContentId()));

		List<DLContent> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		DLContent existingDLContent = result.get(0);

		assertEquals(existingDLContent, newDLContent);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLContent.class,
				DLContent.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("contentId", nextLong()));

		List<DLContent> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		DLContent newDLContent = addDLContent();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLContent.class,
				DLContent.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("contentId"));

		Object newContentId = newDLContent.getContentId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("contentId",
				new Object[] { newContentId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingContentId = result.get(0);

		assertEquals(existingContentId, newContentId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLContent.class,
				DLContent.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("contentId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("contentId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		DLContent newDLContent = addDLContent();

		_persistence.clearCache();

		DLContentModelImpl existingDLContentModelImpl = (DLContentModelImpl)_persistence.findByPrimaryKey(newDLContent.getPrimaryKey());

		assertEquals(existingDLContentModelImpl.getCompanyId(),
			existingDLContentModelImpl.getOriginalCompanyId());
		assertEquals(existingDLContentModelImpl.getRepositoryId(),
			existingDLContentModelImpl.getOriginalRepositoryId());
		assertTrue(Validator.equals(existingDLContentModelImpl.getPath(),
				existingDLContentModelImpl.getOriginalPath()));
		assertTrue(Validator.equals(existingDLContentModelImpl.getVersion(),
				existingDLContentModelImpl.getOriginalVersion()));
	}

	protected DLContent addDLContent() throws Exception {
		long pk = nextLong();

		DLContent dlContent = _persistence.create(pk);

		dlContent.setGroupId(nextLong());

		dlContent.setCompanyId(nextLong());

		dlContent.setRepositoryId(nextLong());

		dlContent.setPath(randomString());

		dlContent.setVersion(randomString());

		byte[] dataBytes = randomString().getBytes(StringPool.UTF8);

		Blob dataBlob = new OutputBlob(new UnsyncByteArrayInputStream(dataBytes),
				dataBytes.length);

		dlContent.setData(dataBlob);

		dlContent.setSize(nextLong());

		_persistence.update(dlContent, false);

		return dlContent;
	}

	private DLContentPersistence _persistence;
}