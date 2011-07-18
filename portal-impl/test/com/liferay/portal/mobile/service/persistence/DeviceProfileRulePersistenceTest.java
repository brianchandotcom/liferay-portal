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
import com.liferay.portal.mobile.NoSuchProfileRuleException;
import com.liferay.portal.mobile.model.DeviceProfileRule;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import java.util.List;

/**
 * @author Edward C. Han
 */
public class DeviceProfileRulePersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (DeviceProfileRulePersistence)PortalBeanLocatorUtil.locate(DeviceProfileRulePersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		DeviceProfileRule deviceProfileRule = _persistence.create(pk);

		assertNotNull(deviceProfileRule);

		assertEquals(deviceProfileRule.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		DeviceProfileRule newDeviceProfileRule = addDeviceProfileRule();

		_persistence.remove(newDeviceProfileRule);

		DeviceProfileRule existingDeviceProfileRule = _persistence.fetchByPrimaryKey(newDeviceProfileRule.getPrimaryKey());

		assertNull(existingDeviceProfileRule);
	}

	public void testUpdateNew() throws Exception {
		addDeviceProfileRule();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		DeviceProfileRule newDeviceProfileRule = _persistence.create(pk);

		newDeviceProfileRule.setUuid(randomString());

		newDeviceProfileRule.setDeviceProfileId(nextLong());

		newDeviceProfileRule.setName(randomString());

		newDeviceProfileRule.setDescription(randomString());

		newDeviceProfileRule.setRuleType(randomString());

		newDeviceProfileRule.setRuleTypeSettings(randomString());

		_persistence.update(newDeviceProfileRule, false);

		DeviceProfileRule existingDeviceProfileRule = _persistence.findByPrimaryKey(newDeviceProfileRule.getPrimaryKey());

		assertEquals(existingDeviceProfileRule.getUuid(),
			newDeviceProfileRule.getUuid());
		assertEquals(existingDeviceProfileRule.getDeviceProfileRuleId(),
			newDeviceProfileRule.getDeviceProfileRuleId());
		assertEquals(existingDeviceProfileRule.getDeviceProfileId(),
			newDeviceProfileRule.getDeviceProfileId());
		assertEquals(existingDeviceProfileRule.getName(),
			newDeviceProfileRule.getName());
		assertEquals(existingDeviceProfileRule.getDescription(),
			newDeviceProfileRule.getDescription());
		assertEquals(existingDeviceProfileRule.getRuleType(),
			newDeviceProfileRule.getRuleType());
		assertEquals(existingDeviceProfileRule.getRuleTypeSettings(),
			newDeviceProfileRule.getRuleTypeSettings());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		DeviceProfileRule newDeviceProfileRule = addDeviceProfileRule();

		DeviceProfileRule existingDeviceProfileRule = _persistence.findByPrimaryKey(newDeviceProfileRule.getPrimaryKey());

		assertEquals(existingDeviceProfileRule, newDeviceProfileRule);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchProfileRuleException");
		}
		catch (NoSuchProfileRuleException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		DeviceProfileRule newDeviceProfileRule = addDeviceProfileRule();

		DeviceProfileRule existingDeviceProfileRule = _persistence.fetchByPrimaryKey(newDeviceProfileRule.getPrimaryKey());

		assertEquals(existingDeviceProfileRule, newDeviceProfileRule);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		DeviceProfileRule missingDeviceProfileRule = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingDeviceProfileRule);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		DeviceProfileRule newDeviceProfileRule = addDeviceProfileRule();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DeviceProfileRule.class,
				DeviceProfileRule.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("deviceProfileRuleId",
				newDeviceProfileRule.getDeviceProfileRuleId()));

		List<DeviceProfileRule> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		DeviceProfileRule existingDeviceProfileRule = result.get(0);

		assertEquals(existingDeviceProfileRule, newDeviceProfileRule);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DeviceProfileRule.class,
				DeviceProfileRule.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("deviceProfileRuleId",
				nextLong()));

		List<DeviceProfileRule> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		DeviceProfileRule newDeviceProfileRule = addDeviceProfileRule();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DeviceProfileRule.class,
				DeviceProfileRule.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"deviceProfileRuleId"));

		Object newDeviceProfileRuleId = newDeviceProfileRule.getDeviceProfileRuleId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("deviceProfileRuleId",
				new Object[] { newDeviceProfileRuleId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingDeviceProfileRuleId = result.get(0);

		assertEquals(existingDeviceProfileRuleId, newDeviceProfileRuleId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DeviceProfileRule.class,
				DeviceProfileRule.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"deviceProfileRuleId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("deviceProfileRuleId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected DeviceProfileRule addDeviceProfileRule()
		throws Exception {
		long pk = nextLong();

		DeviceProfileRule deviceProfileRule = _persistence.create(pk);

		deviceProfileRule.setUuid(randomString());

		deviceProfileRule.setDeviceProfileId(nextLong());

		deviceProfileRule.setName(randomString());

		deviceProfileRule.setDescription(randomString());

		deviceProfileRule.setRuleType(randomString());

		deviceProfileRule.setRuleTypeSettings(randomString());

		_persistence.update(deviceProfileRule, false);

		return deviceProfileRule;
	}

	private DeviceProfileRulePersistence _persistence;
}