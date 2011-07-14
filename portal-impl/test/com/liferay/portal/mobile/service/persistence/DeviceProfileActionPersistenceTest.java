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
import com.liferay.portal.mobile.NoSuchProfileActionException;
import com.liferay.portal.mobile.model.DeviceProfileAction;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import java.util.List;

/**
 * @author Edward C. Han
 */
public class DeviceProfileActionPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (DeviceProfileActionPersistence)PortalBeanLocatorUtil.locate(DeviceProfileActionPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		DeviceProfileAction deviceProfileAction = _persistence.create(pk);

		assertNotNull(deviceProfileAction);

		assertEquals(deviceProfileAction.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		DeviceProfileAction newDeviceProfileAction = addDeviceProfileAction();

		_persistence.remove(newDeviceProfileAction);

		DeviceProfileAction existingDeviceProfileAction = _persistence.fetchByPrimaryKey(newDeviceProfileAction.getPrimaryKey());

		assertNull(existingDeviceProfileAction);
	}

	public void testUpdateNew() throws Exception {
		addDeviceProfileAction();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		DeviceProfileAction newDeviceProfileAction = _persistence.create(pk);

		newDeviceProfileAction.setUuid(randomString());

		newDeviceProfileAction.setDeviceProfileId(nextLong());

		newDeviceProfileAction.setDeviceProfileRuleId(nextLong());

		newDeviceProfileAction.setName(randomString());

		newDeviceProfileAction.setDescription(randomString());

		newDeviceProfileAction.setType(randomString());

		newDeviceProfileAction.setTypeSettings(randomString());

		_persistence.update(newDeviceProfileAction, false);

		DeviceProfileAction existingDeviceProfileAction = _persistence.findByPrimaryKey(newDeviceProfileAction.getPrimaryKey());

		assertEquals(existingDeviceProfileAction.getUuid(),
			newDeviceProfileAction.getUuid());
		assertEquals(existingDeviceProfileAction.getDeviceProfileActionId(),
			newDeviceProfileAction.getDeviceProfileActionId());
		assertEquals(existingDeviceProfileAction.getDeviceProfileId(),
			newDeviceProfileAction.getDeviceProfileId());
		assertEquals(existingDeviceProfileAction.getDeviceProfileRuleId(),
			newDeviceProfileAction.getDeviceProfileRuleId());
		assertEquals(existingDeviceProfileAction.getName(),
			newDeviceProfileAction.getName());
		assertEquals(existingDeviceProfileAction.getDescription(),
			newDeviceProfileAction.getDescription());
		assertEquals(existingDeviceProfileAction.getType(),
			newDeviceProfileAction.getType());
		assertEquals(existingDeviceProfileAction.getTypeSettings(),
			newDeviceProfileAction.getTypeSettings());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		DeviceProfileAction newDeviceProfileAction = addDeviceProfileAction();

		DeviceProfileAction existingDeviceProfileAction = _persistence.findByPrimaryKey(newDeviceProfileAction.getPrimaryKey());

		assertEquals(existingDeviceProfileAction, newDeviceProfileAction);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchProfileActionException");
		}
		catch (NoSuchProfileActionException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		DeviceProfileAction newDeviceProfileAction = addDeviceProfileAction();

		DeviceProfileAction existingDeviceProfileAction = _persistence.fetchByPrimaryKey(newDeviceProfileAction.getPrimaryKey());

		assertEquals(existingDeviceProfileAction, newDeviceProfileAction);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		DeviceProfileAction missingDeviceProfileAction = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingDeviceProfileAction);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		DeviceProfileAction newDeviceProfileAction = addDeviceProfileAction();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DeviceProfileAction.class,
				DeviceProfileAction.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("deviceProfileActionId",
				newDeviceProfileAction.getDeviceProfileActionId()));

		List<DeviceProfileAction> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		DeviceProfileAction existingDeviceProfileAction = result.get(0);

		assertEquals(existingDeviceProfileAction, newDeviceProfileAction);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DeviceProfileAction.class,
				DeviceProfileAction.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("deviceProfileActionId",
				nextLong()));

		List<DeviceProfileAction> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		DeviceProfileAction newDeviceProfileAction = addDeviceProfileAction();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DeviceProfileAction.class,
				DeviceProfileAction.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"deviceProfileActionId"));

		Object newDeviceProfileActionId = newDeviceProfileAction.getDeviceProfileActionId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("deviceProfileActionId",
				new Object[] { newDeviceProfileActionId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingDeviceProfileActionId = result.get(0);

		assertEquals(existingDeviceProfileActionId, newDeviceProfileActionId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DeviceProfileAction.class,
				DeviceProfileAction.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"deviceProfileActionId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("deviceProfileActionId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected DeviceProfileAction addDeviceProfileAction()
		throws Exception {
		long pk = nextLong();

		DeviceProfileAction deviceProfileAction = _persistence.create(pk);

		deviceProfileAction.setUuid(randomString());

		deviceProfileAction.setDeviceProfileId(nextLong());

		deviceProfileAction.setDeviceProfileRuleId(nextLong());

		deviceProfileAction.setName(randomString());

		deviceProfileAction.setDescription(randomString());

		deviceProfileAction.setType(randomString());

		deviceProfileAction.setTypeSettings(randomString());

		_persistence.update(deviceProfileAction, false);

		return deviceProfileAction;
	}

	private DeviceProfileActionPersistence _persistence;
}