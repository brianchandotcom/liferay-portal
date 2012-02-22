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

import com.liferay.portal.NoSuchResourceBlockPermissionException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.model.ResourceBlockPermission;
import com.liferay.portal.model.impl.ResourceBlockPermissionModelImpl;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class ResourceBlockPermissionPersistenceTest
	extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (ResourceBlockPermissionPersistence)PortalBeanLocatorUtil.locate(ResourceBlockPermissionPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		ResourceBlockPermission resourceBlockPermission = _persistence.create(pk);

		assertNotNull(resourceBlockPermission);

		assertEquals(resourceBlockPermission.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		ResourceBlockPermission newResourceBlockPermission = addResourceBlockPermission();

		_persistence.remove(newResourceBlockPermission);

		ResourceBlockPermission existingResourceBlockPermission = _persistence.fetchByPrimaryKey(newResourceBlockPermission.getPrimaryKey());

		assertNull(existingResourceBlockPermission);
	}

	public void testUpdateNew() throws Exception {
		addResourceBlockPermission();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		ResourceBlockPermission newResourceBlockPermission = _persistence.create(pk);

		newResourceBlockPermission.setResourceBlockId(nextLong());

		newResourceBlockPermission.setRoleId(nextLong());

		newResourceBlockPermission.setActionIds(nextLong());

		_persistence.update(newResourceBlockPermission, false);

		ResourceBlockPermission existingResourceBlockPermission = _persistence.findByPrimaryKey(newResourceBlockPermission.getPrimaryKey());

		assertEquals(existingResourceBlockPermission.getResourceBlockPermissionId(),
			newResourceBlockPermission.getResourceBlockPermissionId());
		assertEquals(existingResourceBlockPermission.getResourceBlockId(),
			newResourceBlockPermission.getResourceBlockId());
		assertEquals(existingResourceBlockPermission.getRoleId(),
			newResourceBlockPermission.getRoleId());
		assertEquals(existingResourceBlockPermission.getActionIds(),
			newResourceBlockPermission.getActionIds());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		ResourceBlockPermission newResourceBlockPermission = addResourceBlockPermission();

		ResourceBlockPermission existingResourceBlockPermission = _persistence.findByPrimaryKey(newResourceBlockPermission.getPrimaryKey());

		assertEquals(existingResourceBlockPermission, newResourceBlockPermission);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail(
				"Missing entity did not throw NoSuchResourceBlockPermissionException");
		}
		catch (NoSuchResourceBlockPermissionException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		ResourceBlockPermission newResourceBlockPermission = addResourceBlockPermission();

		ResourceBlockPermission existingResourceBlockPermission = _persistence.fetchByPrimaryKey(newResourceBlockPermission.getPrimaryKey());

		assertEquals(existingResourceBlockPermission, newResourceBlockPermission);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		ResourceBlockPermission missingResourceBlockPermission = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingResourceBlockPermission);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ResourceBlockPermission newResourceBlockPermission = addResourceBlockPermission();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ResourceBlockPermission.class,
				ResourceBlockPermission.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"resourceBlockPermissionId",
				newResourceBlockPermission.getResourceBlockPermissionId()));

		List<ResourceBlockPermission> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		ResourceBlockPermission existingResourceBlockPermission = result.get(0);

		assertEquals(existingResourceBlockPermission, newResourceBlockPermission);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ResourceBlockPermission.class,
				ResourceBlockPermission.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"resourceBlockPermissionId", nextLong()));

		List<ResourceBlockPermission> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		ResourceBlockPermission newResourceBlockPermission = addResourceBlockPermission();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ResourceBlockPermission.class,
				ResourceBlockPermission.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"resourceBlockPermissionId"));

		Object newResourceBlockPermissionId = newResourceBlockPermission.getResourceBlockPermissionId();

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"resourceBlockPermissionId",
				new Object[] { newResourceBlockPermissionId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingResourceBlockPermissionId = result.get(0);

		assertEquals(existingResourceBlockPermissionId,
			newResourceBlockPermissionId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ResourceBlockPermission.class,
				ResourceBlockPermission.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"resourceBlockPermissionId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"resourceBlockPermissionId", new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		ResourceBlockPermission newResourceBlockPermission = addResourceBlockPermission();

		_persistence.clearCache();

		ResourceBlockPermissionModelImpl existingResourceBlockPermissionModelImpl =
			(ResourceBlockPermissionModelImpl)_persistence.findByPrimaryKey(newResourceBlockPermission.getPrimaryKey());

		assertEquals(existingResourceBlockPermissionModelImpl.getResourceBlockId(),
			existingResourceBlockPermissionModelImpl.getOriginalResourceBlockId());
		assertEquals(existingResourceBlockPermissionModelImpl.getRoleId(),
			existingResourceBlockPermissionModelImpl.getOriginalRoleId());
	}

	protected ResourceBlockPermission addResourceBlockPermission()
		throws Exception {
		long pk = nextLong();

		ResourceBlockPermission resourceBlockPermission = _persistence.create(pk);

		resourceBlockPermission.setResourceBlockId(nextLong());

		resourceBlockPermission.setRoleId(nextLong());

		resourceBlockPermission.setActionIds(nextLong());

		_persistence.update(resourceBlockPermission, false);

		return resourceBlockPermission;
	}

	private ResourceBlockPermissionPersistence _persistence;
}