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
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.ExecutionTestListeners;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.PersistenceEnvConfigTestListener;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
@ExecutionTestListeners(listeners =  {
	PersistenceEnvConfigTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class OrgGroupRolePersistenceTest {
	@Before
	public void setUp() throws Exception {
		_persistence = (OrgGroupRolePersistence)PortalBeanLocatorUtil.locate(OrgGroupRolePersistence.class.getName());
	}

	@Test
	public void testCreate() throws Exception {
		OrgGroupRolePK pk = new OrgGroupRolePK(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextLong(), ServiceTestUtil.nextLong());

		OrgGroupRole orgGroupRole = _persistence.create(pk);

		Assert.assertNotNull(orgGroupRole);

		Assert.assertEquals(orgGroupRole.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		OrgGroupRole newOrgGroupRole = addOrgGroupRole();

		_persistence.remove(newOrgGroupRole);

		OrgGroupRole existingOrgGroupRole = _persistence.fetchByPrimaryKey(newOrgGroupRole.getPrimaryKey());

		Assert.assertNull(existingOrgGroupRole);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addOrgGroupRole();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		OrgGroupRolePK pk = new OrgGroupRolePK(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextLong(), ServiceTestUtil.nextLong());

		OrgGroupRole newOrgGroupRole = _persistence.create(pk);

		_persistence.update(newOrgGroupRole, false);

		OrgGroupRole existingOrgGroupRole = _persistence.findByPrimaryKey(newOrgGroupRole.getPrimaryKey());

		Assert.assertEquals(existingOrgGroupRole.getOrganizationId(),
			newOrgGroupRole.getOrganizationId());
		Assert.assertEquals(existingOrgGroupRole.getGroupId(),
			newOrgGroupRole.getGroupId());
		Assert.assertEquals(existingOrgGroupRole.getRoleId(),
			newOrgGroupRole.getRoleId());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		OrgGroupRole newOrgGroupRole = addOrgGroupRole();

		OrgGroupRole existingOrgGroupRole = _persistence.findByPrimaryKey(newOrgGroupRole.getPrimaryKey());

		Assert.assertEquals(existingOrgGroupRole, newOrgGroupRole);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		OrgGroupRolePK pk = new OrgGroupRolePK(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextLong(), ServiceTestUtil.nextLong());

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchOrgGroupRoleException");
		}
		catch (NoSuchOrgGroupRoleException nsee) {
		}
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		OrgGroupRole newOrgGroupRole = addOrgGroupRole();

		OrgGroupRole existingOrgGroupRole = _persistence.fetchByPrimaryKey(newOrgGroupRole.getPrimaryKey());

		Assert.assertEquals(existingOrgGroupRole, newOrgGroupRole);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		OrgGroupRolePK pk = new OrgGroupRolePK(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextLong(), ServiceTestUtil.nextLong());

		OrgGroupRole missingOrgGroupRole = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingOrgGroupRole);
	}

	@Test
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

		Assert.assertEquals(1, result.size());

		OrgGroupRole existingOrgGroupRole = result.get(0);

		Assert.assertEquals(existingOrgGroupRole, newOrgGroupRole);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(OrgGroupRole.class,
				OrgGroupRole.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("id.organizationId",
				ServiceTestUtil.nextLong()));
		dynamicQuery.add(RestrictionsFactoryUtil.eq("id.groupId",
				ServiceTestUtil.nextLong()));
		dynamicQuery.add(RestrictionsFactoryUtil.eq("id.roleId",
				ServiceTestUtil.nextLong()));

		List<OrgGroupRole> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
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

		Assert.assertEquals(1, result.size());

		Object existingOrganizationId = result.get(0);

		Assert.assertEquals(existingOrganizationId, newOrganizationId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(OrgGroupRole.class,
				OrgGroupRole.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"id.organizationId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("id.organizationId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected OrgGroupRole addOrgGroupRole() throws Exception {
		OrgGroupRolePK pk = new OrgGroupRolePK(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextLong(), ServiceTestUtil.nextLong());

		OrgGroupRole orgGroupRole = _persistence.create(pk);

		_persistence.update(orgGroupRole, false);

		return orgGroupRole;
	}

	private OrgGroupRolePersistence _persistence;
}