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

import com.liferay.portlet.dynamicdatalists.NoSuchRecordSetException;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatalists.model.impl.DDLRecordSetModelImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class DDLRecordSetPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (DDLRecordSetPersistence)PortalBeanLocatorUtil.locate(DDLRecordSetPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		DDLRecordSet ddlRecordSet = _persistence.create(pk);

		assertNotNull(ddlRecordSet);

		assertEquals(ddlRecordSet.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		DDLRecordSet newDDLRecordSet = addDDLRecordSet();

		_persistence.remove(newDDLRecordSet);

		DDLRecordSet existingDDLRecordSet = _persistence.fetchByPrimaryKey(newDDLRecordSet.getPrimaryKey());

		assertNull(existingDDLRecordSet);
	}

	public void testUpdateNew() throws Exception {
		addDDLRecordSet();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		DDLRecordSet newDDLRecordSet = _persistence.create(pk);

		newDDLRecordSet.setUuid(randomString());

		newDDLRecordSet.setGroupId(nextLong());

		newDDLRecordSet.setCompanyId(nextLong());

		newDDLRecordSet.setUserId(nextLong());

		newDDLRecordSet.setUserName(randomString());

		newDDLRecordSet.setCreateDate(nextDate());

		newDDLRecordSet.setModifiedDate(nextDate());

		newDDLRecordSet.setDDMStructureId(nextLong());

		newDDLRecordSet.setRecordSetKey(randomString());

		newDDLRecordSet.setName(randomString());

		newDDLRecordSet.setDescription(randomString());

		newDDLRecordSet.setMinDisplayRows(nextInt());

		newDDLRecordSet.setScope(nextInt());

		_persistence.update(newDDLRecordSet, false);

		DDLRecordSet existingDDLRecordSet = _persistence.findByPrimaryKey(newDDLRecordSet.getPrimaryKey());

		assertEquals(existingDDLRecordSet.getUuid(), newDDLRecordSet.getUuid());
		assertEquals(existingDDLRecordSet.getRecordSetId(),
			newDDLRecordSet.getRecordSetId());
		assertEquals(existingDDLRecordSet.getGroupId(),
			newDDLRecordSet.getGroupId());
		assertEquals(existingDDLRecordSet.getCompanyId(),
			newDDLRecordSet.getCompanyId());
		assertEquals(existingDDLRecordSet.getUserId(),
			newDDLRecordSet.getUserId());
		assertEquals(existingDDLRecordSet.getUserName(),
			newDDLRecordSet.getUserName());
		assertEquals(Time.getShortTimestamp(
				existingDDLRecordSet.getCreateDate()),
			Time.getShortTimestamp(newDDLRecordSet.getCreateDate()));
		assertEquals(Time.getShortTimestamp(
				existingDDLRecordSet.getModifiedDate()),
			Time.getShortTimestamp(newDDLRecordSet.getModifiedDate()));
		assertEquals(existingDDLRecordSet.getDDMStructureId(),
			newDDLRecordSet.getDDMStructureId());
		assertEquals(existingDDLRecordSet.getRecordSetKey(),
			newDDLRecordSet.getRecordSetKey());
		assertEquals(existingDDLRecordSet.getName(), newDDLRecordSet.getName());
		assertEquals(existingDDLRecordSet.getDescription(),
			newDDLRecordSet.getDescription());
		assertEquals(existingDDLRecordSet.getMinDisplayRows(),
			newDDLRecordSet.getMinDisplayRows());
		assertEquals(existingDDLRecordSet.getScope(), newDDLRecordSet.getScope());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		DDLRecordSet newDDLRecordSet = addDDLRecordSet();

		DDLRecordSet existingDDLRecordSet = _persistence.findByPrimaryKey(newDDLRecordSet.getPrimaryKey());

		assertEquals(existingDDLRecordSet, newDDLRecordSet);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchRecordSetException");
		}
		catch (NoSuchRecordSetException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		DDLRecordSet newDDLRecordSet = addDDLRecordSet();

		DDLRecordSet existingDDLRecordSet = _persistence.fetchByPrimaryKey(newDDLRecordSet.getPrimaryKey());

		assertEquals(existingDDLRecordSet, newDDLRecordSet);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		DDLRecordSet missingDDLRecordSet = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingDDLRecordSet);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		DDLRecordSet newDDLRecordSet = addDDLRecordSet();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDLRecordSet.class,
				DDLRecordSet.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("recordSetId",
				newDDLRecordSet.getRecordSetId()));

		List<DDLRecordSet> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		DDLRecordSet existingDDLRecordSet = result.get(0);

		assertEquals(existingDDLRecordSet, newDDLRecordSet);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDLRecordSet.class,
				DDLRecordSet.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("recordSetId", nextLong()));

		List<DDLRecordSet> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		DDLRecordSet newDDLRecordSet = addDDLRecordSet();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDLRecordSet.class,
				DDLRecordSet.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("recordSetId"));

		Object newRecordSetId = newDDLRecordSet.getRecordSetId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("recordSetId",
				new Object[] { newRecordSetId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingRecordSetId = result.get(0);

		assertEquals(existingRecordSetId, newRecordSetId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDLRecordSet.class,
				DDLRecordSet.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("recordSetId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("recordSetId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		DDLRecordSet newDDLRecordSet = addDDLRecordSet();

		_persistence.clearCache();

		DDLRecordSetModelImpl existingDDLRecordSetModelImpl = (DDLRecordSetModelImpl)_persistence.findByPrimaryKey(newDDLRecordSet.getPrimaryKey());

		assertTrue(Validator.equals(existingDDLRecordSetModelImpl.getUuid(),
				existingDDLRecordSetModelImpl.getOriginalUuid()));
		assertEquals(existingDDLRecordSetModelImpl.getGroupId(),
			existingDDLRecordSetModelImpl.getOriginalGroupId());

		assertEquals(existingDDLRecordSetModelImpl.getGroupId(),
			existingDDLRecordSetModelImpl.getOriginalGroupId());
		assertTrue(Validator.equals(
				existingDDLRecordSetModelImpl.getRecordSetKey(),
				existingDDLRecordSetModelImpl.getOriginalRecordSetKey()));
	}

	protected DDLRecordSet addDDLRecordSet() throws Exception {
		long pk = nextLong();

		DDLRecordSet ddlRecordSet = _persistence.create(pk);

		ddlRecordSet.setUuid(randomString());

		ddlRecordSet.setGroupId(nextLong());

		ddlRecordSet.setCompanyId(nextLong());

		ddlRecordSet.setUserId(nextLong());

		ddlRecordSet.setUserName(randomString());

		ddlRecordSet.setCreateDate(nextDate());

		ddlRecordSet.setModifiedDate(nextDate());

		ddlRecordSet.setDDMStructureId(nextLong());

		ddlRecordSet.setRecordSetKey(randomString());

		ddlRecordSet.setName(randomString());

		ddlRecordSet.setDescription(randomString());

		ddlRecordSet.setMinDisplayRows(nextInt());

		ddlRecordSet.setScope(nextInt());

		_persistence.update(ddlRecordSet, false);

		return ddlRecordSet;
	}

	private DDLRecordSetPersistence _persistence;
}