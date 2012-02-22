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

import com.liferay.portal.NoSuchServiceComponentException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ServiceComponent;
import com.liferay.portal.model.impl.ServiceComponentModelImpl;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class ServiceComponentPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (ServiceComponentPersistence)PortalBeanLocatorUtil.locate(ServiceComponentPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		ServiceComponent serviceComponent = _persistence.create(pk);

		assertNotNull(serviceComponent);

		assertEquals(serviceComponent.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		ServiceComponent newServiceComponent = addServiceComponent();

		_persistence.remove(newServiceComponent);

		ServiceComponent existingServiceComponent = _persistence.fetchByPrimaryKey(newServiceComponent.getPrimaryKey());

		assertNull(existingServiceComponent);
	}

	public void testUpdateNew() throws Exception {
		addServiceComponent();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		ServiceComponent newServiceComponent = _persistence.create(pk);

		newServiceComponent.setBuildNamespace(randomString());

		newServiceComponent.setBuildNumber(nextLong());

		newServiceComponent.setBuildDate(nextLong());

		newServiceComponent.setData(randomString());

		_persistence.update(newServiceComponent, false);

		ServiceComponent existingServiceComponent = _persistence.findByPrimaryKey(newServiceComponent.getPrimaryKey());

		assertEquals(existingServiceComponent.getServiceComponentId(),
			newServiceComponent.getServiceComponentId());
		assertEquals(existingServiceComponent.getBuildNamespace(),
			newServiceComponent.getBuildNamespace());
		assertEquals(existingServiceComponent.getBuildNumber(),
			newServiceComponent.getBuildNumber());
		assertEquals(existingServiceComponent.getBuildDate(),
			newServiceComponent.getBuildDate());
		assertEquals(existingServiceComponent.getData(),
			newServiceComponent.getData());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		ServiceComponent newServiceComponent = addServiceComponent();

		ServiceComponent existingServiceComponent = _persistence.findByPrimaryKey(newServiceComponent.getPrimaryKey());

		assertEquals(existingServiceComponent, newServiceComponent);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchServiceComponentException");
		}
		catch (NoSuchServiceComponentException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		ServiceComponent newServiceComponent = addServiceComponent();

		ServiceComponent existingServiceComponent = _persistence.fetchByPrimaryKey(newServiceComponent.getPrimaryKey());

		assertEquals(existingServiceComponent, newServiceComponent);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		ServiceComponent missingServiceComponent = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingServiceComponent);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ServiceComponent newServiceComponent = addServiceComponent();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ServiceComponent.class,
				ServiceComponent.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("serviceComponentId",
				newServiceComponent.getServiceComponentId()));

		List<ServiceComponent> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		ServiceComponent existingServiceComponent = result.get(0);

		assertEquals(existingServiceComponent, newServiceComponent);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ServiceComponent.class,
				ServiceComponent.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("serviceComponentId",
				nextLong()));

		List<ServiceComponent> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		ServiceComponent newServiceComponent = addServiceComponent();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ServiceComponent.class,
				ServiceComponent.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"serviceComponentId"));

		Object newServiceComponentId = newServiceComponent.getServiceComponentId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("serviceComponentId",
				new Object[] { newServiceComponentId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingServiceComponentId = result.get(0);

		assertEquals(existingServiceComponentId, newServiceComponentId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ServiceComponent.class,
				ServiceComponent.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"serviceComponentId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("serviceComponentId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		ServiceComponent newServiceComponent = addServiceComponent();

		_persistence.clearCache();

		ServiceComponentModelImpl existingServiceComponentModelImpl = (ServiceComponentModelImpl)_persistence.findByPrimaryKey(newServiceComponent.getPrimaryKey());

		assertTrue(Validator.equals(
				existingServiceComponentModelImpl.getBuildNamespace(),
				existingServiceComponentModelImpl.getOriginalBuildNamespace()));
		assertEquals(existingServiceComponentModelImpl.getBuildNumber(),
			existingServiceComponentModelImpl.getOriginalBuildNumber());
	}

	protected ServiceComponent addServiceComponent() throws Exception {
		long pk = nextLong();

		ServiceComponent serviceComponent = _persistence.create(pk);

		serviceComponent.setBuildNamespace(randomString());

		serviceComponent.setBuildNumber(nextLong());

		serviceComponent.setBuildDate(nextLong());

		serviceComponent.setData(randomString());

		_persistence.update(serviceComponent, false);

		return serviceComponent;
	}

	private ServiceComponentPersistence _persistence;
}