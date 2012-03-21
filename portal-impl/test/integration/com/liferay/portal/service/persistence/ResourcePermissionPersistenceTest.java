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

import com.liferay.portal.NoSuchResourcePermissionException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.model.impl.ResourcePermissionModelImpl;
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
public class ResourcePermissionPersistenceTest {
	@Before
	public void setUp() throws Exception {
		_persistence = (ResourcePermissionPersistence)PortalBeanLocatorUtil.locate(ResourcePermissionPersistence.class.getName());
	}

	@Test
	public void testCreate() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		ResourcePermission resourcePermission = _persistence.create(pk);

		Assert.assertNotNull(resourcePermission);

		Assert.assertEquals(resourcePermission.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		ResourcePermission newResourcePermission = addResourcePermission();

		_persistence.remove(newResourcePermission);

		ResourcePermission existingResourcePermission = _persistence.fetchByPrimaryKey(newResourcePermission.getPrimaryKey());

		Assert.assertNull(existingResourcePermission);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addResourcePermission();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		ResourcePermission newResourcePermission = _persistence.create(pk);

		newResourcePermission.setCompanyId(ServiceTestUtil.nextLong());

		newResourcePermission.setName(ServiceTestUtil.randomString());

		newResourcePermission.setScope(ServiceTestUtil.nextInt());

		newResourcePermission.setPrimKey(ServiceTestUtil.randomString());

		newResourcePermission.setRoleId(ServiceTestUtil.nextLong());

		newResourcePermission.setOwnerId(ServiceTestUtil.nextLong());

		newResourcePermission.setActionIds(ServiceTestUtil.nextLong());

		_persistence.update(newResourcePermission, false);

		ResourcePermission existingResourcePermission = _persistence.findByPrimaryKey(newResourcePermission.getPrimaryKey());

		Assert.assertEquals(existingResourcePermission.getResourcePermissionId(),
			newResourcePermission.getResourcePermissionId());
		Assert.assertEquals(existingResourcePermission.getCompanyId(),
			newResourcePermission.getCompanyId());
		Assert.assertEquals(existingResourcePermission.getName(),
			newResourcePermission.getName());
		Assert.assertEquals(existingResourcePermission.getScope(),
			newResourcePermission.getScope());
		Assert.assertEquals(existingResourcePermission.getPrimKey(),
			newResourcePermission.getPrimKey());
		Assert.assertEquals(existingResourcePermission.getRoleId(),
			newResourcePermission.getRoleId());
		Assert.assertEquals(existingResourcePermission.getOwnerId(),
			newResourcePermission.getOwnerId());
		Assert.assertEquals(existingResourcePermission.getActionIds(),
			newResourcePermission.getActionIds());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		ResourcePermission newResourcePermission = addResourcePermission();

		ResourcePermission existingResourcePermission = _persistence.findByPrimaryKey(newResourcePermission.getPrimaryKey());

		Assert.assertEquals(existingResourcePermission, newResourcePermission);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchResourcePermissionException");
		}
		catch (NoSuchResourcePermissionException nsee) {
		}
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		ResourcePermission newResourcePermission = addResourcePermission();

		ResourcePermission existingResourcePermission = _persistence.fetchByPrimaryKey(newResourcePermission.getPrimaryKey());

		Assert.assertEquals(existingResourcePermission, newResourcePermission);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		ResourcePermission missingResourcePermission = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingResourcePermission);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ResourcePermission newResourcePermission = addResourcePermission();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ResourcePermission.class,
				ResourcePermission.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("resourcePermissionId",
				newResourcePermission.getResourcePermissionId()));

		List<ResourcePermission> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		ResourcePermission existingResourcePermission = result.get(0);

		Assert.assertEquals(existingResourcePermission, newResourcePermission);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ResourcePermission.class,
				ResourcePermission.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("resourcePermissionId",
				ServiceTestUtil.nextLong()));

		List<ResourcePermission> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		ResourcePermission newResourcePermission = addResourcePermission();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ResourcePermission.class,
				ResourcePermission.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"resourcePermissionId"));

		Object newResourcePermissionId = newResourcePermission.getResourcePermissionId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("resourcePermissionId",
				new Object[] { newResourcePermissionId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingResourcePermissionId = result.get(0);

		Assert.assertEquals(existingResourcePermissionId,
			newResourcePermissionId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ResourcePermission.class,
				ResourcePermission.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"resourcePermissionId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("resourcePermissionId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		ResourcePermission newResourcePermission = addResourcePermission();

		_persistence.clearCache();

		ResourcePermissionModelImpl existingResourcePermissionModelImpl = (ResourcePermissionModelImpl)_persistence.findByPrimaryKey(newResourcePermission.getPrimaryKey());

		Assert.assertEquals(existingResourcePermissionModelImpl.getCompanyId(),
			existingResourcePermissionModelImpl.getOriginalCompanyId());
		Assert.assertTrue(Validator.equals(
				existingResourcePermissionModelImpl.getName(),
				existingResourcePermissionModelImpl.getOriginalName()));
		Assert.assertEquals(existingResourcePermissionModelImpl.getScope(),
			existingResourcePermissionModelImpl.getOriginalScope());
		Assert.assertTrue(Validator.equals(
				existingResourcePermissionModelImpl.getPrimKey(),
				existingResourcePermissionModelImpl.getOriginalPrimKey()));
		Assert.assertEquals(existingResourcePermissionModelImpl.getRoleId(),
			existingResourcePermissionModelImpl.getOriginalRoleId());
		Assert.assertEquals(existingResourcePermissionModelImpl.getOwnerId(),
			existingResourcePermissionModelImpl.getOriginalOwnerId());
		Assert.assertEquals(existingResourcePermissionModelImpl.getActionIds(),
			existingResourcePermissionModelImpl.getOriginalActionIds());
	}

	protected ResourcePermission addResourcePermission()
		throws Exception {
		long pk = ServiceTestUtil.nextLong();

		ResourcePermission resourcePermission = _persistence.create(pk);

		resourcePermission.setCompanyId(ServiceTestUtil.nextLong());

		resourcePermission.setName(ServiceTestUtil.randomString());

		resourcePermission.setScope(ServiceTestUtil.nextInt());

		resourcePermission.setPrimKey(ServiceTestUtil.randomString());

		resourcePermission.setRoleId(ServiceTestUtil.nextLong());

		resourcePermission.setOwnerId(ServiceTestUtil.nextLong());

		resourcePermission.setActionIds(ServiceTestUtil.nextLong());

		_persistence.update(resourcePermission, false);

		return resourcePermission;
	}

	private ResourcePermissionPersistence _persistence;
}