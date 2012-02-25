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
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink;
import com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureLinkModelImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class DDMStructureLinkPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (DDMStructureLinkPersistence)PortalBeanLocatorUtil.locate(DDMStructureLinkPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		DDMStructureLink ddmStructureLink = _persistence.create(pk);

		assertNotNull(ddmStructureLink);

		assertEquals(ddmStructureLink.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		DDMStructureLink newDDMStructureLink = addDDMStructureLink();

		_persistence.remove(newDDMStructureLink);

		DDMStructureLink existingDDMStructureLink = _persistence.fetchByPrimaryKey(newDDMStructureLink.getPrimaryKey());

		assertNull(existingDDMStructureLink);
	}

	public void testUpdateNew() throws Exception {
		addDDMStructureLink();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		DDMStructureLink newDDMStructureLink = _persistence.create(pk);

		newDDMStructureLink.setClassNameId(nextLong());

		newDDMStructureLink.setClassPK(nextLong());

		newDDMStructureLink.setStructureId(nextLong());

		_persistence.update(newDDMStructureLink, false);

		DDMStructureLink existingDDMStructureLink = _persistence.findByPrimaryKey(newDDMStructureLink.getPrimaryKey());

		assertEquals(existingDDMStructureLink.getStructureLinkId(),
			newDDMStructureLink.getStructureLinkId());
		assertEquals(existingDDMStructureLink.getClassNameId(),
			newDDMStructureLink.getClassNameId());
		assertEquals(existingDDMStructureLink.getClassPK(),
			newDDMStructureLink.getClassPK());
		assertEquals(existingDDMStructureLink.getStructureId(),
			newDDMStructureLink.getStructureId());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		DDMStructureLink newDDMStructureLink = addDDMStructureLink();

		DDMStructureLink existingDDMStructureLink = _persistence.findByPrimaryKey(newDDMStructureLink.getPrimaryKey());

		assertEquals(existingDDMStructureLink, newDDMStructureLink);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchStructureLinkException");
		}
		catch (NoSuchStructureLinkException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		DDMStructureLink newDDMStructureLink = addDDMStructureLink();

		DDMStructureLink existingDDMStructureLink = _persistence.fetchByPrimaryKey(newDDMStructureLink.getPrimaryKey());

		assertEquals(existingDDMStructureLink, newDDMStructureLink);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		DDMStructureLink missingDDMStructureLink = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingDDMStructureLink);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		DDMStructureLink newDDMStructureLink = addDDMStructureLink();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMStructureLink.class,
				DDMStructureLink.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("structureLinkId",
				newDDMStructureLink.getStructureLinkId()));

		List<DDMStructureLink> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		DDMStructureLink existingDDMStructureLink = result.get(0);

		assertEquals(existingDDMStructureLink, newDDMStructureLink);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMStructureLink.class,
				DDMStructureLink.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("structureLinkId",
				nextLong()));

		List<DDMStructureLink> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		DDMStructureLink newDDMStructureLink = addDDMStructureLink();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMStructureLink.class,
				DDMStructureLink.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"structureLinkId"));

		Object newStructureLinkId = newDDMStructureLink.getStructureLinkId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("structureLinkId",
				new Object[] { newStructureLinkId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingStructureLinkId = result.get(0);

		assertEquals(existingStructureLinkId, newStructureLinkId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMStructureLink.class,
				DDMStructureLink.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"structureLinkId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("structureLinkId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		DDMStructureLink newDDMStructureLink = addDDMStructureLink();

		_persistence.clearCache();

		DDMStructureLinkModelImpl existingDDMStructureLinkModelImpl = (DDMStructureLinkModelImpl)_persistence.findByPrimaryKey(newDDMStructureLink.getPrimaryKey());

		assertEquals(existingDDMStructureLinkModelImpl.getClassPK(),
			existingDDMStructureLinkModelImpl.getOriginalClassPK());
	}

	protected DDMStructureLink addDDMStructureLink() throws Exception {
		long pk = nextLong();

		DDMStructureLink ddmStructureLink = _persistence.create(pk);

		ddmStructureLink.setClassNameId(nextLong());

		ddmStructureLink.setClassPK(nextLong());

		ddmStructureLink.setStructureId(nextLong());

		_persistence.update(ddmStructureLink, false);

		return ddmStructureLink;
	}

	private DDMStructureLinkPersistence _persistence;
}