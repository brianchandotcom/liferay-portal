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

package com.liferay.portal.mobile.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.mobile.NoSuchProfileException;
import com.liferay.portal.mobile.model.DeviceProfile;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import java.util.List;

/**
 * @author Edward C. Han
 */
public class DeviceProfilePersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (DeviceProfilePersistence)PortalBeanLocatorUtil.locate(DeviceProfilePersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		DeviceProfile deviceProfile = _persistence.create(pk);

		assertNotNull(deviceProfile);

		assertEquals(deviceProfile.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		DeviceProfile newDeviceProfile = addDeviceProfile();

		_persistence.remove(newDeviceProfile);

		DeviceProfile existingDeviceProfile = _persistence.fetchByPrimaryKey(newDeviceProfile.getPrimaryKey());

		assertNull(existingDeviceProfile);
	}

	public void testUpdateNew() throws Exception {
		addDeviceProfile();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		DeviceProfile newDeviceProfile = _persistence.create(pk);

		newDeviceProfile.setUuid(randomString());

		newDeviceProfile.setName(randomString());

		newDeviceProfile.setDescription(randomString());

		_persistence.update(newDeviceProfile, false);

		DeviceProfile existingDeviceProfile = _persistence.findByPrimaryKey(newDeviceProfile.getPrimaryKey());

		assertEquals(existingDeviceProfile.getUuid(), newDeviceProfile.getUuid());
		assertEquals(existingDeviceProfile.getDeviceProfileId(),
			newDeviceProfile.getDeviceProfileId());
		assertEquals(existingDeviceProfile.getName(), newDeviceProfile.getName());
		assertEquals(existingDeviceProfile.getDescription(),
			newDeviceProfile.getDescription());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		DeviceProfile newDeviceProfile = addDeviceProfile();

		DeviceProfile existingDeviceProfile = _persistence.findByPrimaryKey(newDeviceProfile.getPrimaryKey());

		assertEquals(existingDeviceProfile, newDeviceProfile);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchProfileException");
		}
		catch (NoSuchProfileException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		DeviceProfile newDeviceProfile = addDeviceProfile();

		DeviceProfile existingDeviceProfile = _persistence.fetchByPrimaryKey(newDeviceProfile.getPrimaryKey());

		assertEquals(existingDeviceProfile, newDeviceProfile);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		DeviceProfile missingDeviceProfile = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingDeviceProfile);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		DeviceProfile newDeviceProfile = addDeviceProfile();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DeviceProfile.class,
				DeviceProfile.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("deviceProfileId",
				newDeviceProfile.getDeviceProfileId()));

		List<DeviceProfile> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		DeviceProfile existingDeviceProfile = result.get(0);

		assertEquals(existingDeviceProfile, newDeviceProfile);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DeviceProfile.class,
				DeviceProfile.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("deviceProfileId",
				nextLong()));

		List<DeviceProfile> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		DeviceProfile newDeviceProfile = addDeviceProfile();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DeviceProfile.class,
				DeviceProfile.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"deviceProfileId"));

		Object newDeviceProfileId = newDeviceProfile.getDeviceProfileId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("deviceProfileId",
				new Object[] { newDeviceProfileId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingDeviceProfileId = result.get(0);

		assertEquals(existingDeviceProfileId, newDeviceProfileId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DeviceProfile.class,
				DeviceProfile.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"deviceProfileId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("deviceProfileId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected DeviceProfile addDeviceProfile() throws Exception {
		long pk = nextLong();

		DeviceProfile deviceProfile = _persistence.create(pk);

		deviceProfile.setUuid(randomString());

		deviceProfile.setName(randomString());

		deviceProfile.setDescription(randomString());

		_persistence.update(deviceProfile, false);

		return deviceProfile;
	}

	private DeviceProfilePersistence _persistence;
}