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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.documentlibrary.NoSuchFileEntryTypeException;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryTypeModelImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class DLFileEntryTypePersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (DLFileEntryTypePersistence)PortalBeanLocatorUtil.locate(DLFileEntryTypePersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		DLFileEntryType dlFileEntryType = _persistence.create(pk);

		assertNotNull(dlFileEntryType);

		assertEquals(dlFileEntryType.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		DLFileEntryType newDLFileEntryType = addDLFileEntryType();

		_persistence.remove(newDLFileEntryType);

		DLFileEntryType existingDLFileEntryType = _persistence.fetchByPrimaryKey(newDLFileEntryType.getPrimaryKey());

		assertNull(existingDLFileEntryType);
	}

	public void testUpdateNew() throws Exception {
		addDLFileEntryType();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		DLFileEntryType newDLFileEntryType = _persistence.create(pk);

		newDLFileEntryType.setUuid(randomString());

		newDLFileEntryType.setGroupId(nextLong());

		newDLFileEntryType.setCompanyId(nextLong());

		newDLFileEntryType.setUserId(nextLong());

		newDLFileEntryType.setUserName(randomString());

		newDLFileEntryType.setCreateDate(nextDate());

		newDLFileEntryType.setModifiedDate(nextDate());

		newDLFileEntryType.setName(randomString());

		newDLFileEntryType.setDescription(randomString());

		_persistence.update(newDLFileEntryType, false);

		DLFileEntryType existingDLFileEntryType = _persistence.findByPrimaryKey(newDLFileEntryType.getPrimaryKey());

		assertEquals(existingDLFileEntryType.getUuid(),
			newDLFileEntryType.getUuid());
		assertEquals(existingDLFileEntryType.getFileEntryTypeId(),
			newDLFileEntryType.getFileEntryTypeId());
		assertEquals(existingDLFileEntryType.getGroupId(),
			newDLFileEntryType.getGroupId());
		assertEquals(existingDLFileEntryType.getCompanyId(),
			newDLFileEntryType.getCompanyId());
		assertEquals(existingDLFileEntryType.getUserId(),
			newDLFileEntryType.getUserId());
		assertEquals(existingDLFileEntryType.getUserName(),
			newDLFileEntryType.getUserName());
		assertEquals(Time.getShortTimestamp(
				existingDLFileEntryType.getCreateDate()),
			Time.getShortTimestamp(newDLFileEntryType.getCreateDate()));
		assertEquals(Time.getShortTimestamp(
				existingDLFileEntryType.getModifiedDate()),
			Time.getShortTimestamp(newDLFileEntryType.getModifiedDate()));
		assertEquals(existingDLFileEntryType.getName(),
			newDLFileEntryType.getName());
		assertEquals(existingDLFileEntryType.getDescription(),
			newDLFileEntryType.getDescription());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		DLFileEntryType newDLFileEntryType = addDLFileEntryType();

		DLFileEntryType existingDLFileEntryType = _persistence.findByPrimaryKey(newDLFileEntryType.getPrimaryKey());

		assertEquals(existingDLFileEntryType, newDLFileEntryType);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchFileEntryTypeException");
		}
		catch (NoSuchFileEntryTypeException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		DLFileEntryType newDLFileEntryType = addDLFileEntryType();

		DLFileEntryType existingDLFileEntryType = _persistence.fetchByPrimaryKey(newDLFileEntryType.getPrimaryKey());

		assertEquals(existingDLFileEntryType, newDLFileEntryType);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		DLFileEntryType missingDLFileEntryType = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingDLFileEntryType);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		DLFileEntryType newDLFileEntryType = addDLFileEntryType();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLFileEntryType.class,
				DLFileEntryType.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("fileEntryTypeId",
				newDLFileEntryType.getFileEntryTypeId()));

		List<DLFileEntryType> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		DLFileEntryType existingDLFileEntryType = result.get(0);

		assertEquals(existingDLFileEntryType, newDLFileEntryType);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLFileEntryType.class,
				DLFileEntryType.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("fileEntryTypeId",
				nextLong()));

		List<DLFileEntryType> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		DLFileEntryType newDLFileEntryType = addDLFileEntryType();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLFileEntryType.class,
				DLFileEntryType.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"fileEntryTypeId"));

		Object newFileEntryTypeId = newDLFileEntryType.getFileEntryTypeId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("fileEntryTypeId",
				new Object[] { newFileEntryTypeId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingFileEntryTypeId = result.get(0);

		assertEquals(existingFileEntryTypeId, newFileEntryTypeId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLFileEntryType.class,
				DLFileEntryType.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"fileEntryTypeId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("fileEntryTypeId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		DLFileEntryType newDLFileEntryType = addDLFileEntryType();

		_persistence.clearCache();

		DLFileEntryTypeModelImpl existingDLFileEntryTypeModelImpl = (DLFileEntryTypeModelImpl)_persistence.findByPrimaryKey(newDLFileEntryType.getPrimaryKey());

		assertTrue(Validator.equals(
				existingDLFileEntryTypeModelImpl.getUuid(),
				existingDLFileEntryTypeModelImpl.getOriginalUuid()));
		assertEquals(existingDLFileEntryTypeModelImpl.getGroupId(),
			existingDLFileEntryTypeModelImpl.getOriginalGroupId());

		assertEquals(existingDLFileEntryTypeModelImpl.getGroupId(),
			existingDLFileEntryTypeModelImpl.getOriginalGroupId());
		assertTrue(Validator.equals(
				existingDLFileEntryTypeModelImpl.getName(),
				existingDLFileEntryTypeModelImpl.getOriginalName()));
	}

	protected DLFileEntryType addDLFileEntryType() throws Exception {
		long pk = nextLong();

		DLFileEntryType dlFileEntryType = _persistence.create(pk);

		dlFileEntryType.setUuid(randomString());

		dlFileEntryType.setGroupId(nextLong());

		dlFileEntryType.setCompanyId(nextLong());

		dlFileEntryType.setUserId(nextLong());

		dlFileEntryType.setUserName(randomString());

		dlFileEntryType.setCreateDate(nextDate());

		dlFileEntryType.setModifiedDate(nextDate());

		dlFileEntryType.setName(randomString());

		dlFileEntryType.setDescription(randomString());

		_persistence.update(dlFileEntryType, false);

		return dlFileEntryType;
	}

	private DLFileEntryTypePersistence _persistence;
}