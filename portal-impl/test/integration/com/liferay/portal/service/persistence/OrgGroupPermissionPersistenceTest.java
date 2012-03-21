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

import com.liferay.portal.NoSuchOrgGroupPermissionException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.model.OrgGroupPermission;
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
public class OrgGroupPermissionPersistenceTest {
	@Before
	public void setUp() throws Exception {
		_persistence = (OrgGroupPermissionPersistence)PortalBeanLocatorUtil.locate(OrgGroupPermissionPersistence.class.getName());
	}

	@Test
	public void testCreate() throws Exception {
		OrgGroupPermissionPK pk = new OrgGroupPermissionPK(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextLong(), ServiceTestUtil.nextLong());

		OrgGroupPermission orgGroupPermission = _persistence.create(pk);

		Assert.assertNotNull(orgGroupPermission);

		Assert.assertEquals(orgGroupPermission.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		OrgGroupPermission newOrgGroupPermission = addOrgGroupPermission();

		_persistence.remove(newOrgGroupPermission);

		OrgGroupPermission existingOrgGroupPermission = _persistence.fetchByPrimaryKey(newOrgGroupPermission.getPrimaryKey());

		Assert.assertNull(existingOrgGroupPermission);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addOrgGroupPermission();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		OrgGroupPermissionPK pk = new OrgGroupPermissionPK(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextLong(), ServiceTestUtil.nextLong());

		OrgGroupPermission newOrgGroupPermission = _persistence.create(pk);

		_persistence.update(newOrgGroupPermission, false);

		OrgGroupPermission existingOrgGroupPermission = _persistence.findByPrimaryKey(newOrgGroupPermission.getPrimaryKey());

		Assert.assertEquals(existingOrgGroupPermission.getOrganizationId(),
			newOrgGroupPermission.getOrganizationId());
		Assert.assertEquals(existingOrgGroupPermission.getGroupId(),
			newOrgGroupPermission.getGroupId());
		Assert.assertEquals(existingOrgGroupPermission.getPermissionId(),
			newOrgGroupPermission.getPermissionId());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		OrgGroupPermission newOrgGroupPermission = addOrgGroupPermission();

		OrgGroupPermission existingOrgGroupPermission = _persistence.findByPrimaryKey(newOrgGroupPermission.getPrimaryKey());

		Assert.assertEquals(existingOrgGroupPermission, newOrgGroupPermission);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		OrgGroupPermissionPK pk = new OrgGroupPermissionPK(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextLong(), ServiceTestUtil.nextLong());

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchOrgGroupPermissionException");
		}
		catch (NoSuchOrgGroupPermissionException nsee) {
		}
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		OrgGroupPermission newOrgGroupPermission = addOrgGroupPermission();

		OrgGroupPermission existingOrgGroupPermission = _persistence.fetchByPrimaryKey(newOrgGroupPermission.getPrimaryKey());

		Assert.assertEquals(existingOrgGroupPermission, newOrgGroupPermission);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		OrgGroupPermissionPK pk = new OrgGroupPermissionPK(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextLong(), ServiceTestUtil.nextLong());

		OrgGroupPermission missingOrgGroupPermission = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingOrgGroupPermission);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		OrgGroupPermission newOrgGroupPermission = addOrgGroupPermission();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(OrgGroupPermission.class,
				OrgGroupPermission.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("id.organizationId",
				newOrgGroupPermission.getOrganizationId()));
		dynamicQuery.add(RestrictionsFactoryUtil.eq("id.groupId",
				newOrgGroupPermission.getGroupId()));
		dynamicQuery.add(RestrictionsFactoryUtil.eq("id.permissionId",
				newOrgGroupPermission.getPermissionId()));

		List<OrgGroupPermission> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		OrgGroupPermission existingOrgGroupPermission = result.get(0);

		Assert.assertEquals(existingOrgGroupPermission, newOrgGroupPermission);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(OrgGroupPermission.class,
				OrgGroupPermission.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("id.organizationId",
				ServiceTestUtil.nextLong()));
		dynamicQuery.add(RestrictionsFactoryUtil.eq("id.groupId",
				ServiceTestUtil.nextLong()));
		dynamicQuery.add(RestrictionsFactoryUtil.eq("id.permissionId",
				ServiceTestUtil.nextLong()));

		List<OrgGroupPermission> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		OrgGroupPermission newOrgGroupPermission = addOrgGroupPermission();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(OrgGroupPermission.class,
				OrgGroupPermission.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"id.organizationId"));

		Object newOrganizationId = newOrgGroupPermission.getOrganizationId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("id.organizationId",
				new Object[] { newOrganizationId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingOrganizationId = result.get(0);

		Assert.assertEquals(existingOrganizationId, newOrganizationId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(OrgGroupPermission.class,
				OrgGroupPermission.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"id.organizationId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("id.organizationId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected OrgGroupPermission addOrgGroupPermission()
		throws Exception {
		OrgGroupPermissionPK pk = new OrgGroupPermissionPK(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextLong(), ServiceTestUtil.nextLong());

		OrgGroupPermission orgGroupPermission = _persistence.create(pk);

		_persistence.update(orgGroupPermission, false);

		return orgGroupPermission;
	}

	private OrgGroupPermissionPersistence _persistence;
}