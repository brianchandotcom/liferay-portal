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

package com.liferay.portlet.dynamicdatamapping.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.dynamicdatamapping.NoSuchStructureException;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureModelImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class DDMStructurePersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (DDMStructurePersistence)PortalBeanLocatorUtil.locate(DDMStructurePersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		DDMStructure ddmStructure = _persistence.create(pk);

		assertNotNull(ddmStructure);

		assertEquals(ddmStructure.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		DDMStructure newDDMStructure = addDDMStructure();

		_persistence.remove(newDDMStructure);

		DDMStructure existingDDMStructure = _persistence.fetchByPrimaryKey(newDDMStructure.getPrimaryKey());

		assertNull(existingDDMStructure);
	}

	public void testUpdateNew() throws Exception {
		addDDMStructure();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		DDMStructure newDDMStructure = _persistence.create(pk);

		newDDMStructure.setUuid(randomString());

		newDDMStructure.setGroupId(nextLong());

		newDDMStructure.setCompanyId(nextLong());

		newDDMStructure.setUserId(nextLong());

		newDDMStructure.setUserName(randomString());

		newDDMStructure.setCreateDate(nextDate());

		newDDMStructure.setModifiedDate(nextDate());

		newDDMStructure.setClassNameId(nextLong());

		newDDMStructure.setStructureKey(randomString());

		newDDMStructure.setName(randomString());

		newDDMStructure.setDescription(randomString());

		newDDMStructure.setXsd(randomString());

		newDDMStructure.setStorageType(randomString());

		newDDMStructure.setType(nextInt());

		_persistence.update(newDDMStructure, false);

		DDMStructure existingDDMStructure = _persistence.findByPrimaryKey(newDDMStructure.getPrimaryKey());

		assertEquals(existingDDMStructure.getUuid(), newDDMStructure.getUuid());
		assertEquals(existingDDMStructure.getStructureId(),
			newDDMStructure.getStructureId());
		assertEquals(existingDDMStructure.getGroupId(),
			newDDMStructure.getGroupId());
		assertEquals(existingDDMStructure.getCompanyId(),
			newDDMStructure.getCompanyId());
		assertEquals(existingDDMStructure.getUserId(),
			newDDMStructure.getUserId());
		assertEquals(existingDDMStructure.getUserName(),
			newDDMStructure.getUserName());
		assertEquals(Time.getShortTimestamp(
				existingDDMStructure.getCreateDate()),
			Time.getShortTimestamp(newDDMStructure.getCreateDate()));
		assertEquals(Time.getShortTimestamp(
				existingDDMStructure.getModifiedDate()),
			Time.getShortTimestamp(newDDMStructure.getModifiedDate()));
		assertEquals(existingDDMStructure.getClassNameId(),
			newDDMStructure.getClassNameId());
		assertEquals(existingDDMStructure.getStructureKey(),
			newDDMStructure.getStructureKey());
		assertEquals(existingDDMStructure.getName(), newDDMStructure.getName());
		assertEquals(existingDDMStructure.getDescription(),
			newDDMStructure.getDescription());
		assertEquals(existingDDMStructure.getXsd(), newDDMStructure.getXsd());
		assertEquals(existingDDMStructure.getStorageType(),
			newDDMStructure.getStorageType());
		assertEquals(existingDDMStructure.getType(), newDDMStructure.getType());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		DDMStructure newDDMStructure = addDDMStructure();

		DDMStructure existingDDMStructure = _persistence.findByPrimaryKey(newDDMStructure.getPrimaryKey());

		assertEquals(existingDDMStructure, newDDMStructure);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchStructureException");
		}
		catch (NoSuchStructureException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		DDMStructure newDDMStructure = addDDMStructure();

		DDMStructure existingDDMStructure = _persistence.fetchByPrimaryKey(newDDMStructure.getPrimaryKey());

		assertEquals(existingDDMStructure, newDDMStructure);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		DDMStructure missingDDMStructure = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingDDMStructure);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		DDMStructure newDDMStructure = addDDMStructure();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMStructure.class,
				DDMStructure.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("structureId",
				newDDMStructure.getStructureId()));

		List<DDMStructure> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		DDMStructure existingDDMStructure = result.get(0);

		assertEquals(existingDDMStructure, newDDMStructure);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMStructure.class,
				DDMStructure.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("structureId", nextLong()));

		List<DDMStructure> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		DDMStructure newDDMStructure = addDDMStructure();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMStructure.class,
				DDMStructure.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("structureId"));

		Object newStructureId = newDDMStructure.getStructureId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("structureId",
				new Object[] { newStructureId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingStructureId = result.get(0);

		assertEquals(existingStructureId, newStructureId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMStructure.class,
				DDMStructure.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("structureId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("structureId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		DDMStructure newDDMStructure = addDDMStructure();

		_persistence.clearCache();

		DDMStructureModelImpl existingDDMStructureModelImpl = (DDMStructureModelImpl)_persistence.findByPrimaryKey(newDDMStructure.getPrimaryKey());

		assertTrue(Validator.equals(existingDDMStructureModelImpl.getUuid(),
				existingDDMStructureModelImpl.getOriginalUuid()));
		assertEquals(existingDDMStructureModelImpl.getGroupId(),
			existingDDMStructureModelImpl.getOriginalGroupId());

		assertEquals(existingDDMStructureModelImpl.getGroupId(),
			existingDDMStructureModelImpl.getOriginalGroupId());
		assertTrue(Validator.equals(
				existingDDMStructureModelImpl.getStructureKey(),
				existingDDMStructureModelImpl.getOriginalStructureKey()));
	}

	protected DDMStructure addDDMStructure() throws Exception {
		long pk = nextLong();

		DDMStructure ddmStructure = _persistence.create(pk);

		ddmStructure.setUuid(randomString());

		ddmStructure.setGroupId(nextLong());

		ddmStructure.setCompanyId(nextLong());

		ddmStructure.setUserId(nextLong());

		ddmStructure.setUserName(randomString());

		ddmStructure.setCreateDate(nextDate());

		ddmStructure.setModifiedDate(nextDate());

		ddmStructure.setClassNameId(nextLong());

		ddmStructure.setStructureKey(randomString());

		ddmStructure.setName(randomString());

		ddmStructure.setDescription(randomString());

		ddmStructure.setXsd(randomString());

		ddmStructure.setStorageType(randomString());

		ddmStructure.setType(nextInt());

		_persistence.update(ddmStructure, false);

		return ddmStructure;
	}

	private DDMStructurePersistence _persistence;
}