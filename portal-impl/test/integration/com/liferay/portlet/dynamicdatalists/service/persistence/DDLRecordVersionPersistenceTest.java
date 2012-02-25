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

package com.liferay.portlet.dynamicdatalists.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.dynamicdatalists.NoSuchRecordVersionException;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion;
import com.liferay.portlet.dynamicdatalists.model.impl.DDLRecordVersionModelImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class DDLRecordVersionPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (DDLRecordVersionPersistence)PortalBeanLocatorUtil.locate(DDLRecordVersionPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		DDLRecordVersion ddlRecordVersion = _persistence.create(pk);

		assertNotNull(ddlRecordVersion);

		assertEquals(ddlRecordVersion.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		DDLRecordVersion newDDLRecordVersion = addDDLRecordVersion();

		_persistence.remove(newDDLRecordVersion);

		DDLRecordVersion existingDDLRecordVersion = _persistence.fetchByPrimaryKey(newDDLRecordVersion.getPrimaryKey());

		assertNull(existingDDLRecordVersion);
	}

	public void testUpdateNew() throws Exception {
		addDDLRecordVersion();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		DDLRecordVersion newDDLRecordVersion = _persistence.create(pk);

		newDDLRecordVersion.setGroupId(nextLong());

		newDDLRecordVersion.setCompanyId(nextLong());

		newDDLRecordVersion.setUserId(nextLong());

		newDDLRecordVersion.setUserName(randomString());

		newDDLRecordVersion.setCreateDate(nextDate());

		newDDLRecordVersion.setDDMStorageId(nextLong());

		newDDLRecordVersion.setRecordSetId(nextLong());

		newDDLRecordVersion.setRecordId(nextLong());

		newDDLRecordVersion.setVersion(randomString());

		newDDLRecordVersion.setDisplayIndex(nextInt());

		newDDLRecordVersion.setStatus(nextInt());

		newDDLRecordVersion.setStatusByUserId(nextLong());

		newDDLRecordVersion.setStatusByUserName(randomString());

		newDDLRecordVersion.setStatusDate(nextDate());

		_persistence.update(newDDLRecordVersion, false);

		DDLRecordVersion existingDDLRecordVersion = _persistence.findByPrimaryKey(newDDLRecordVersion.getPrimaryKey());

		assertEquals(existingDDLRecordVersion.getRecordVersionId(),
			newDDLRecordVersion.getRecordVersionId());
		assertEquals(existingDDLRecordVersion.getGroupId(),
			newDDLRecordVersion.getGroupId());
		assertEquals(existingDDLRecordVersion.getCompanyId(),
			newDDLRecordVersion.getCompanyId());
		assertEquals(existingDDLRecordVersion.getUserId(),
			newDDLRecordVersion.getUserId());
		assertEquals(existingDDLRecordVersion.getUserName(),
			newDDLRecordVersion.getUserName());
		assertEquals(Time.getShortTimestamp(
				existingDDLRecordVersion.getCreateDate()),
			Time.getShortTimestamp(newDDLRecordVersion.getCreateDate()));
		assertEquals(existingDDLRecordVersion.getDDMStorageId(),
			newDDLRecordVersion.getDDMStorageId());
		assertEquals(existingDDLRecordVersion.getRecordSetId(),
			newDDLRecordVersion.getRecordSetId());
		assertEquals(existingDDLRecordVersion.getRecordId(),
			newDDLRecordVersion.getRecordId());
		assertEquals(existingDDLRecordVersion.getVersion(),
			newDDLRecordVersion.getVersion());
		assertEquals(existingDDLRecordVersion.getDisplayIndex(),
			newDDLRecordVersion.getDisplayIndex());
		assertEquals(existingDDLRecordVersion.getStatus(),
			newDDLRecordVersion.getStatus());
		assertEquals(existingDDLRecordVersion.getStatusByUserId(),
			newDDLRecordVersion.getStatusByUserId());
		assertEquals(existingDDLRecordVersion.getStatusByUserName(),
			newDDLRecordVersion.getStatusByUserName());
		assertEquals(Time.getShortTimestamp(
				existingDDLRecordVersion.getStatusDate()),
			Time.getShortTimestamp(newDDLRecordVersion.getStatusDate()));
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		DDLRecordVersion newDDLRecordVersion = addDDLRecordVersion();

		DDLRecordVersion existingDDLRecordVersion = _persistence.findByPrimaryKey(newDDLRecordVersion.getPrimaryKey());

		assertEquals(existingDDLRecordVersion, newDDLRecordVersion);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchRecordVersionException");
		}
		catch (NoSuchRecordVersionException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		DDLRecordVersion newDDLRecordVersion = addDDLRecordVersion();

		DDLRecordVersion existingDDLRecordVersion = _persistence.fetchByPrimaryKey(newDDLRecordVersion.getPrimaryKey());

		assertEquals(existingDDLRecordVersion, newDDLRecordVersion);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		DDLRecordVersion missingDDLRecordVersion = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingDDLRecordVersion);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		DDLRecordVersion newDDLRecordVersion = addDDLRecordVersion();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDLRecordVersion.class,
				DDLRecordVersion.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("recordVersionId",
				newDDLRecordVersion.getRecordVersionId()));

		List<DDLRecordVersion> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		DDLRecordVersion existingDDLRecordVersion = result.get(0);

		assertEquals(existingDDLRecordVersion, newDDLRecordVersion);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDLRecordVersion.class,
				DDLRecordVersion.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("recordVersionId",
				nextLong()));

		List<DDLRecordVersion> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		DDLRecordVersion newDDLRecordVersion = addDDLRecordVersion();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDLRecordVersion.class,
				DDLRecordVersion.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"recordVersionId"));

		Object newRecordVersionId = newDDLRecordVersion.getRecordVersionId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("recordVersionId",
				new Object[] { newRecordVersionId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingRecordVersionId = result.get(0);

		assertEquals(existingRecordVersionId, newRecordVersionId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDLRecordVersion.class,
				DDLRecordVersion.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"recordVersionId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("recordVersionId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		DDLRecordVersion newDDLRecordVersion = addDDLRecordVersion();

		_persistence.clearCache();

		DDLRecordVersionModelImpl existingDDLRecordVersionModelImpl = (DDLRecordVersionModelImpl)_persistence.findByPrimaryKey(newDDLRecordVersion.getPrimaryKey());

		assertEquals(existingDDLRecordVersionModelImpl.getRecordId(),
			existingDDLRecordVersionModelImpl.getOriginalRecordId());
		assertTrue(Validator.equals(
				existingDDLRecordVersionModelImpl.getVersion(),
				existingDDLRecordVersionModelImpl.getOriginalVersion()));
	}

	protected DDLRecordVersion addDDLRecordVersion() throws Exception {
		long pk = nextLong();

		DDLRecordVersion ddlRecordVersion = _persistence.create(pk);

		ddlRecordVersion.setGroupId(nextLong());

		ddlRecordVersion.setCompanyId(nextLong());

		ddlRecordVersion.setUserId(nextLong());

		ddlRecordVersion.setUserName(randomString());

		ddlRecordVersion.setCreateDate(nextDate());

		ddlRecordVersion.setDDMStorageId(nextLong());

		ddlRecordVersion.setRecordSetId(nextLong());

		ddlRecordVersion.setRecordId(nextLong());

		ddlRecordVersion.setVersion(randomString());

		ddlRecordVersion.setDisplayIndex(nextInt());

		ddlRecordVersion.setStatus(nextInt());

		ddlRecordVersion.setStatusByUserId(nextLong());

		ddlRecordVersion.setStatusByUserName(randomString());

		ddlRecordVersion.setStatusDate(nextDate());

		_persistence.update(ddlRecordVersion, false);

		return ddlRecordVersion;
	}

	private DDLRecordVersionPersistence _persistence;
}