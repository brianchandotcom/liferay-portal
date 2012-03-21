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

import com.liferay.portal.NoSuchResourceException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.impl.ResourceModelImpl;
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
public class ResourcePersistenceTest {
	@Before
	public void setUp() throws Exception {
		_persistence = (ResourcePersistence)PortalBeanLocatorUtil.locate(ResourcePersistence.class.getName());
	}

	@Test
	public void testCreate() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		Resource resource = _persistence.create(pk);

		Assert.assertNotNull(resource);

		Assert.assertEquals(resource.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		Resource newResource = addResource();

		_persistence.remove(newResource);

		Resource existingResource = _persistence.fetchByPrimaryKey(newResource.getPrimaryKey());

		Assert.assertNull(existingResource);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addResource();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		Resource newResource = _persistence.create(pk);

		newResource.setCodeId(ServiceTestUtil.nextLong());

		newResource.setPrimKey(ServiceTestUtil.randomString());

		_persistence.update(newResource, false);

		Resource existingResource = _persistence.findByPrimaryKey(newResource.getPrimaryKey());

		Assert.assertEquals(existingResource.getResourceId(),
			newResource.getResourceId());
		Assert.assertEquals(existingResource.getCodeId(),
			newResource.getCodeId());
		Assert.assertEquals(existingResource.getPrimKey(),
			newResource.getPrimKey());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		Resource newResource = addResource();

		Resource existingResource = _persistence.findByPrimaryKey(newResource.getPrimaryKey());

		Assert.assertEquals(existingResource, newResource);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchResourceException");
		}
		catch (NoSuchResourceException nsee) {
		}
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		Resource newResource = addResource();

		Resource existingResource = _persistence.fetchByPrimaryKey(newResource.getPrimaryKey());

		Assert.assertEquals(existingResource, newResource);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		Resource missingResource = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingResource);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Resource newResource = addResource();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Resource.class,
				Resource.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("resourceId",
				newResource.getResourceId()));

		List<Resource> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Resource existingResource = result.get(0);

		Assert.assertEquals(existingResource, newResource);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Resource.class,
				Resource.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("resourceId",
				ServiceTestUtil.nextLong()));

		List<Resource> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		Resource newResource = addResource();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Resource.class,
				Resource.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("resourceId"));

		Object newResourceId = newResource.getResourceId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("resourceId",
				new Object[] { newResourceId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingResourceId = result.get(0);

		Assert.assertEquals(existingResourceId, newResourceId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Resource.class,
				Resource.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("resourceId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("resourceId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		Resource newResource = addResource();

		_persistence.clearCache();

		ResourceModelImpl existingResourceModelImpl = (ResourceModelImpl)_persistence.findByPrimaryKey(newResource.getPrimaryKey());

		Assert.assertEquals(existingResourceModelImpl.getCodeId(),
			existingResourceModelImpl.getOriginalCodeId());
		Assert.assertTrue(Validator.equals(
				existingResourceModelImpl.getPrimKey(),
				existingResourceModelImpl.getOriginalPrimKey()));
	}

	protected Resource addResource() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		Resource resource = _persistence.create(pk);

		resource.setCodeId(ServiceTestUtil.nextLong());

		resource.setPrimKey(ServiceTestUtil.randomString());

		_persistence.update(resource, false);

		return resource;
	}

	private ResourcePersistence _persistence;
}