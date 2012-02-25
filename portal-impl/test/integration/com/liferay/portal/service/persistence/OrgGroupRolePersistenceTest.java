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

import com.liferay.portal.NoSuchOrgGroupRoleException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.model.OrgGroupRole;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class OrgGroupRolePersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (OrgGroupRolePersistence)PortalBeanLocatorUtil.locate(OrgGroupRolePersistence.class.getName());
	}

	public void testCreate() throws Exception {
		OrgGroupRolePK pk = new OrgGroupRolePK(nextLong(), nextLong(),
				nextLong());

		OrgGroupRole orgGroupRole = _persistence.create(pk);

		assertNotNull(orgGroupRole);

		assertEquals(orgGroupRole.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		OrgGroupRole newOrgGroupRole = addOrgGroupRole();

		_persistence.remove(newOrgGroupRole);

		OrgGroupRole existingOrgGroupRole = _persistence.fetchByPrimaryKey(newOrgGroupRole.getPrimaryKey());

		assertNull(existingOrgGroupRole);
	}

	public void testUpdateNew() throws Exception {
		addOrgGroupRole();
	}

	public void testUpdateExisting() throws Exception {
		OrgGroupRolePK pk = new OrgGroupRolePK(nextLong(), nextLong(),
				nextLong());

		OrgGroupRole newOrgGroupRole = _persistence.create(pk);

		_persistence.update(newOrgGroupRole, false);

		OrgGroupRole existingOrgGroupRole = _persistence.findByPrimaryKey(newOrgGroupRole.getPrimaryKey());

		assertEquals(existingOrgGroupRole.getOrganizationId(),
			newOrgGroupRole.getOrganizationId());
		assertEquals(existingOrgGroupRole.getGroupId(),
			newOrgGroupRole.getGroupId());
		assertEquals(existingOrgGroupRole.getRoleId(),
			newOrgGroupRole.getRoleId());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		OrgGroupRole newOrgGroupRole = addOrgGroupRole();

		OrgGroupRole existingOrgGroupRole = _persistence.findByPrimaryKey(newOrgGroupRole.getPrimaryKey());

		assertEquals(existingOrgGroupRole, newOrgGroupRole);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		OrgGroupRolePK pk = new OrgGroupRolePK(nextLong(), nextLong(),
				nextLong());

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchOrgGroupRoleException");
		}
		catch (NoSuchOrgGroupRoleException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		OrgGroupRole newOrgGroupRole = addOrgGroupRole();

		OrgGroupRole existingOrgGroupRole = _persistence.fetchByPrimaryKey(newOrgGroupRole.getPrimaryKey());

		assertEquals(existingOrgGroupRole, newOrgGroupRole);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		OrgGroupRolePK pk = new OrgGroupRolePK(nextLong(), nextLong(),
				nextLong());

		OrgGroupRole missingOrgGroupRole = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingOrgGroupRole);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		OrgGroupRole newOrgGroupRole = addOrgGroupRole();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(OrgGroupRole.class,
				OrgGroupRole.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("id.organizationId",
				newOrgGroupRole.getOrganizationId()));
		dynamicQuery.add(RestrictionsFactoryUtil.eq("id.groupId",
				newOrgGroupRole.getGroupId()));
		dynamicQuery.add(RestrictionsFactoryUtil.eq("id.roleId",
				newOrgGroupRole.getRoleId()));

		List<OrgGroupRole> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		OrgGroupRole existingOrgGroupRole = result.get(0);

		assertEquals(existingOrgGroupRole, newOrgGroupRole);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(OrgGroupRole.class,
				OrgGroupRole.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("id.organizationId",
				nextLong()));
		dynamicQuery.add(RestrictionsFactoryUtil.eq("id.groupId", nextLong()));
		dynamicQuery.add(RestrictionsFactoryUtil.eq("id.roleId", nextLong()));

		List<OrgGroupRole> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		OrgGroupRole newOrgGroupRole = addOrgGroupRole();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(OrgGroupRole.class,
				OrgGroupRole.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"id.organizationId"));

		Object newOrganizationId = newOrgGroupRole.getOrganizationId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("id.organizationId",
				new Object[] { newOrganizationId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingOrganizationId = result.get(0);

		assertEquals(existingOrganizationId, newOrganizationId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(OrgGroupRole.class,
				OrgGroupRole.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"id.organizationId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("id.organizationId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected OrgGroupRole addOrgGroupRole() throws Exception {
		OrgGroupRolePK pk = new OrgGroupRolePK(nextLong(), nextLong(),
				nextLong());

		OrgGroupRole orgGroupRole = _persistence.create(pk);

		_persistence.update(orgGroupRole, false);

		return orgGroupRole;
	}

	private OrgGroupRolePersistence _persistence;
}