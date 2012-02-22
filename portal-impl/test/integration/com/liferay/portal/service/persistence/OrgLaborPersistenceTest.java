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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchOrgLaborException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.model.OrgLabor;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class OrgLaborPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (OrgLaborPersistence)PortalBeanLocatorUtil.locate(OrgLaborPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		OrgLabor orgLabor = _persistence.create(pk);

		assertNotNull(orgLabor);

		assertEquals(orgLabor.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		OrgLabor newOrgLabor = addOrgLabor();

		_persistence.remove(newOrgLabor);

		OrgLabor existingOrgLabor = _persistence.fetchByPrimaryKey(newOrgLabor.getPrimaryKey());

		assertNull(existingOrgLabor);
	}

	public void testUpdateNew() throws Exception {
		addOrgLabor();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		OrgLabor newOrgLabor = _persistence.create(pk);

		newOrgLabor.setOrganizationId(nextLong());

		newOrgLabor.setTypeId(nextInt());

		newOrgLabor.setSunOpen(nextInt());

		newOrgLabor.setSunClose(nextInt());

		newOrgLabor.setMonOpen(nextInt());

		newOrgLabor.setMonClose(nextInt());

		newOrgLabor.setTueOpen(nextInt());

		newOrgLabor.setTueClose(nextInt());

		newOrgLabor.setWedOpen(nextInt());

		newOrgLabor.setWedClose(nextInt());

		newOrgLabor.setThuOpen(nextInt());

		newOrgLabor.setThuClose(nextInt());

		newOrgLabor.setFriOpen(nextInt());

		newOrgLabor.setFriClose(nextInt());

		newOrgLabor.setSatOpen(nextInt());

		newOrgLabor.setSatClose(nextInt());

		_persistence.update(newOrgLabor, false);

		OrgLabor existingOrgLabor = _persistence.findByPrimaryKey(newOrgLabor.getPrimaryKey());

		assertEquals(existingOrgLabor.getOrgLaborId(),
			newOrgLabor.getOrgLaborId());
		assertEquals(existingOrgLabor.getOrganizationId(),
			newOrgLabor.getOrganizationId());
		assertEquals(existingOrgLabor.getTypeId(), newOrgLabor.getTypeId());
		assertEquals(existingOrgLabor.getSunOpen(), newOrgLabor.getSunOpen());
		assertEquals(existingOrgLabor.getSunClose(), newOrgLabor.getSunClose());
		assertEquals(existingOrgLabor.getMonOpen(), newOrgLabor.getMonOpen());
		assertEquals(existingOrgLabor.getMonClose(), newOrgLabor.getMonClose());
		assertEquals(existingOrgLabor.getTueOpen(), newOrgLabor.getTueOpen());
		assertEquals(existingOrgLabor.getTueClose(), newOrgLabor.getTueClose());
		assertEquals(existingOrgLabor.getWedOpen(), newOrgLabor.getWedOpen());
		assertEquals(existingOrgLabor.getWedClose(), newOrgLabor.getWedClose());
		assertEquals(existingOrgLabor.getThuOpen(), newOrgLabor.getThuOpen());
		assertEquals(existingOrgLabor.getThuClose(), newOrgLabor.getThuClose());
		assertEquals(existingOrgLabor.getFriOpen(), newOrgLabor.getFriOpen());
		assertEquals(existingOrgLabor.getFriClose(), newOrgLabor.getFriClose());
		assertEquals(existingOrgLabor.getSatOpen(), newOrgLabor.getSatOpen());
		assertEquals(existingOrgLabor.getSatClose(), newOrgLabor.getSatClose());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		OrgLabor newOrgLabor = addOrgLabor();

		OrgLabor existingOrgLabor = _persistence.findByPrimaryKey(newOrgLabor.getPrimaryKey());

		assertEquals(existingOrgLabor, newOrgLabor);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchOrgLaborException");
		}
		catch (NoSuchOrgLaborException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		OrgLabor newOrgLabor = addOrgLabor();

		OrgLabor existingOrgLabor = _persistence.fetchByPrimaryKey(newOrgLabor.getPrimaryKey());

		assertEquals(existingOrgLabor, newOrgLabor);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		OrgLabor missingOrgLabor = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingOrgLabor);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		OrgLabor newOrgLabor = addOrgLabor();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(OrgLabor.class,
				OrgLabor.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("orgLaborId",
				newOrgLabor.getOrgLaborId()));

		List<OrgLabor> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		OrgLabor existingOrgLabor = result.get(0);

		assertEquals(existingOrgLabor, newOrgLabor);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(OrgLabor.class,
				OrgLabor.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("orgLaborId", nextLong()));

		List<OrgLabor> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		OrgLabor newOrgLabor = addOrgLabor();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(OrgLabor.class,
				OrgLabor.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("orgLaborId"));

		Object newOrgLaborId = newOrgLabor.getOrgLaborId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("orgLaborId",
				new Object[] { newOrgLaborId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingOrgLaborId = result.get(0);

		assertEquals(existingOrgLaborId, newOrgLaborId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(OrgLabor.class,
				OrgLabor.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("orgLaborId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("orgLaborId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected OrgLabor addOrgLabor() throws Exception {
		long pk = nextLong();

		OrgLabor orgLabor = _persistence.create(pk);

		orgLabor.setOrganizationId(nextLong());

		orgLabor.setTypeId(nextInt());

		orgLabor.setSunOpen(nextInt());

		orgLabor.setSunClose(nextInt());

		orgLabor.setMonOpen(nextInt());

		orgLabor.setMonClose(nextInt());

		orgLabor.setTueOpen(nextInt());

		orgLabor.setTueClose(nextInt());

		orgLabor.setWedOpen(nextInt());

		orgLabor.setWedClose(nextInt());

		orgLabor.setThuOpen(nextInt());

		orgLabor.setThuClose(nextInt());

		orgLabor.setFriOpen(nextInt());

		orgLabor.setFriClose(nextInt());

		orgLabor.setSatOpen(nextInt());

		orgLabor.setSatClose(nextInt());

		_persistence.update(orgLabor, false);

		return orgLabor;
	}

	private OrgLaborPersistence _persistence;
}