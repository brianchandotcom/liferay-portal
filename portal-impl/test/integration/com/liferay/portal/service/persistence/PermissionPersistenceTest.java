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

import com.liferay.portal.NoSuchPermissionException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Permission;
import com.liferay.portal.model.impl.PermissionModelImpl;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.ExecutionTestListeners;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.PersistenceEnvConfigTestListener;
import com.liferay.portal.util.PropsValues;

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
public class PermissionPersistenceTest {
	@Before
	public void setUp() throws Exception {
		_persistence = (PermissionPersistence)PortalBeanLocatorUtil.locate(PermissionPersistence.class.getName());
	}

	@Test
	public void testCreate() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		Permission permission = _persistence.create(pk);

		Assert.assertNotNull(permission);

		Assert.assertEquals(permission.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		Permission newPermission = addPermission();

		_persistence.remove(newPermission);

		Permission existingPermission = _persistence.fetchByPrimaryKey(newPermission.getPrimaryKey());

		Assert.assertNull(existingPermission);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addPermission();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		Permission newPermission = _persistence.create(pk);

		newPermission.setCompanyId(ServiceTestUtil.nextLong());

		newPermission.setActionId(ServiceTestUtil.randomString());

		newPermission.setResourceId(ServiceTestUtil.nextLong());

		_persistence.update(newPermission, false);

		Permission existingPermission = _persistence.findByPrimaryKey(newPermission.getPrimaryKey());

		Assert.assertEquals(existingPermission.getPermissionId(),
			newPermission.getPermissionId());
		Assert.assertEquals(existingPermission.getCompanyId(),
			newPermission.getCompanyId());
		Assert.assertEquals(existingPermission.getActionId(),
			newPermission.getActionId());
		Assert.assertEquals(existingPermission.getResourceId(),
			newPermission.getResourceId());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		Permission newPermission = addPermission();

		Permission existingPermission = _persistence.findByPrimaryKey(newPermission.getPrimaryKey());

		Assert.assertEquals(existingPermission, newPermission);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchPermissionException");
		}
		catch (NoSuchPermissionException nsee) {
		}
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		Permission newPermission = addPermission();

		Permission existingPermission = _persistence.fetchByPrimaryKey(newPermission.getPrimaryKey());

		Assert.assertEquals(existingPermission, newPermission);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		Permission missingPermission = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingPermission);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Permission newPermission = addPermission();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Permission.class,
				Permission.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("permissionId",
				newPermission.getPermissionId()));

		List<Permission> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Permission existingPermission = result.get(0);

		Assert.assertEquals(existingPermission, newPermission);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Permission.class,
				Permission.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("permissionId",
				ServiceTestUtil.nextLong()));

		List<Permission> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		Permission newPermission = addPermission();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Permission.class,
				Permission.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"permissionId"));

		Object newPermissionId = newPermission.getPermissionId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("permissionId",
				new Object[] { newPermissionId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingPermissionId = result.get(0);

		Assert.assertEquals(existingPermissionId, newPermissionId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Permission.class,
				Permission.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"permissionId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("permissionId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		Permission newPermission = addPermission();

		_persistence.clearCache();

		PermissionModelImpl existingPermissionModelImpl = (PermissionModelImpl)_persistence.findByPrimaryKey(newPermission.getPrimaryKey());

		Assert.assertTrue(Validator.equals(
				existingPermissionModelImpl.getActionId(),
				existingPermissionModelImpl.getOriginalActionId()));
		Assert.assertEquals(existingPermissionModelImpl.getResourceId(),
			existingPermissionModelImpl.getOriginalResourceId());
	}

	protected Permission addPermission() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		Permission permission = _persistence.create(pk);

		permission.setCompanyId(ServiceTestUtil.nextLong());

		permission.setActionId(ServiceTestUtil.randomString());

		permission.setResourceId(ServiceTestUtil.nextLong());

		_persistence.update(permission, false);

		return permission;
	}

	private PermissionPersistence _persistence;
}