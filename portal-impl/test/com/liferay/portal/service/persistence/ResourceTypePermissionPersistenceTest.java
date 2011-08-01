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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchResourceTypePermissionException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.model.ResourceTypePermission;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class ResourceTypePermissionPersistenceTest
	extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (ResourceTypePermissionPersistence)PortalBeanLocatorUtil.locate(ResourceTypePermissionPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		ResourceTypePermissionPK pk = new ResourceTypePermissionPK(nextLong(),
				nextLong(), randomString(), nextLong());

		ResourceTypePermission resourceTypePermission = _persistence.create(pk);

		assertNotNull(resourceTypePermission);

		assertEquals(resourceTypePermission.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		ResourceTypePermission newResourceTypePermission = addResourceTypePermission();

		_persistence.remove(newResourceTypePermission);

		ResourceTypePermission existingResourceTypePermission = _persistence.fetchByPrimaryKey(newResourceTypePermission.getPrimaryKey());

		assertNull(existingResourceTypePermission);
	}

	public void testUpdateNew() throws Exception {
		addResourceTypePermission();
	}

	public void testUpdateExisting() throws Exception {
		ResourceTypePermissionPK pk = new ResourceTypePermissionPK(nextLong(),
				nextLong(), randomString(), nextLong());

		ResourceTypePermission newResourceTypePermission = _persistence.create(pk);

		newResourceTypePermission.setActionIds(nextLong());

		_persistence.update(newResourceTypePermission, false);

		ResourceTypePermission existingResourceTypePermission = _persistence.findByPrimaryKey(newResourceTypePermission.getPrimaryKey());

		assertEquals(existingResourceTypePermission.getCompanyId(),
			newResourceTypePermission.getCompanyId());
		assertEquals(existingResourceTypePermission.getGroupId(),
			newResourceTypePermission.getGroupId());
		assertEquals(existingResourceTypePermission.getName(),
			newResourceTypePermission.getName());
		assertEquals(existingResourceTypePermission.getRoleId(),
			newResourceTypePermission.getRoleId());
		assertEquals(existingResourceTypePermission.getActionIds(),
			newResourceTypePermission.getActionIds());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		ResourceTypePermission newResourceTypePermission = addResourceTypePermission();

		ResourceTypePermission existingResourceTypePermission = _persistence.findByPrimaryKey(newResourceTypePermission.getPrimaryKey());

		assertEquals(existingResourceTypePermission, newResourceTypePermission);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		ResourceTypePermissionPK pk = new ResourceTypePermissionPK(nextLong(),
				nextLong(), randomString(), nextLong());

		try {
			_persistence.findByPrimaryKey(pk);

			fail(
				"Missing entity did not throw NoSuchResourceTypePermissionException");
		}
		catch (NoSuchResourceTypePermissionException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		ResourceTypePermission newResourceTypePermission = addResourceTypePermission();

		ResourceTypePermission existingResourceTypePermission = _persistence.fetchByPrimaryKey(newResourceTypePermission.getPrimaryKey());

		assertEquals(existingResourceTypePermission, newResourceTypePermission);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		ResourceTypePermissionPK pk = new ResourceTypePermissionPK(nextLong(),
				nextLong(), randomString(), nextLong());

		ResourceTypePermission missingResourceTypePermission = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingResourceTypePermission);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ResourceTypePermission newResourceTypePermission = addResourceTypePermission();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ResourceTypePermission.class,
				ResourceTypePermission.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("id.companyId",
				newResourceTypePermission.getCompanyId()));
		dynamicQuery.add(RestrictionsFactoryUtil.eq("id.groupId",
				newResourceTypePermission.getGroupId()));
		dynamicQuery.add(RestrictionsFactoryUtil.eq("id.name",
				newResourceTypePermission.getName()));
		dynamicQuery.add(RestrictionsFactoryUtil.eq("id.roleId",
				newResourceTypePermission.getRoleId()));

		List<ResourceTypePermission> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		ResourceTypePermission existingResourceTypePermission = result.get(0);

		assertEquals(existingResourceTypePermission, newResourceTypePermission);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ResourceTypePermission.class,
				ResourceTypePermission.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("id.companyId", nextLong()));
		dynamicQuery.add(RestrictionsFactoryUtil.eq("id.groupId", nextLong()));
		dynamicQuery.add(RestrictionsFactoryUtil.eq("id.name", randomString()));
		dynamicQuery.add(RestrictionsFactoryUtil.eq("id.roleId", nextLong()));

		List<ResourceTypePermission> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		ResourceTypePermission newResourceTypePermission = addResourceTypePermission();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ResourceTypePermission.class,
				ResourceTypePermission.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"id.companyId"));

		Object newCompanyId = newResourceTypePermission.getCompanyId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("id.companyId",
				new Object[] { newCompanyId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingCompanyId = result.get(0);

		assertEquals(existingCompanyId, newCompanyId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ResourceTypePermission.class,
				ResourceTypePermission.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"id.companyId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("id.companyId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected ResourceTypePermission addResourceTypePermission()
		throws Exception {
		ResourceTypePermissionPK pk = new ResourceTypePermissionPK(nextLong(),
				nextLong(), randomString(), nextLong());

		ResourceTypePermission resourceTypePermission = _persistence.create(pk);

		resourceTypePermission.setActionIds(nextLong());

		_persistence.update(resourceTypePermission, false);

		return resourceTypePermission;
	}

	private ResourceTypePermissionPersistence _persistence;
}