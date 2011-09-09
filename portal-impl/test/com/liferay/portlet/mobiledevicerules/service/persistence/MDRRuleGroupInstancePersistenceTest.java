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

package com.liferay.portlet.mobiledevicerules.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupInstanceException;
import com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance;
import com.liferay.portlet.mobiledevicerules.model.impl.MDRRuleGroupInstanceModelImpl;

import java.util.List;

/**
 * @author Edward C. Han
 */
public class MDRRuleGroupInstancePersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (MDRRuleGroupInstancePersistence)PortalBeanLocatorUtil.locate(MDRRuleGroupInstancePersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		MDRRuleGroupInstance mdrRuleGroupInstance = _persistence.create(pk);

		assertNotNull(mdrRuleGroupInstance);

		assertEquals(mdrRuleGroupInstance.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		MDRRuleGroupInstance newMDRRuleGroupInstance = addMDRRuleGroupInstance();

		_persistence.remove(newMDRRuleGroupInstance);

		MDRRuleGroupInstance existingMDRRuleGroupInstance = _persistence.fetchByPrimaryKey(newMDRRuleGroupInstance.getPrimaryKey());

		assertNull(existingMDRRuleGroupInstance);
	}

	public void testUpdateNew() throws Exception {
		addMDRRuleGroupInstance();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		MDRRuleGroupInstance newMDRRuleGroupInstance = _persistence.create(pk);

		newMDRRuleGroupInstance.setUuid(randomString());

		newMDRRuleGroupInstance.setGroupId(nextLong());

		newMDRRuleGroupInstance.setCompanyId(nextLong());

		newMDRRuleGroupInstance.setUserId(nextLong());

		newMDRRuleGroupInstance.setUserName(randomString());

		newMDRRuleGroupInstance.setCreateDate(nextDate());

		newMDRRuleGroupInstance.setModifiedDate(nextDate());

		newMDRRuleGroupInstance.setClassNameId(nextLong());

		newMDRRuleGroupInstance.setClassPK(nextLong());

		newMDRRuleGroupInstance.setRuleGroupId(nextLong());

		newMDRRuleGroupInstance.setPriority(nextInt());

		_persistence.update(newMDRRuleGroupInstance, false);

		MDRRuleGroupInstance existingMDRRuleGroupInstance = _persistence.findByPrimaryKey(newMDRRuleGroupInstance.getPrimaryKey());

		assertEquals(existingMDRRuleGroupInstance.getUuid(),
			newMDRRuleGroupInstance.getUuid());
		assertEquals(existingMDRRuleGroupInstance.getRuleGroupInstanceId(),
			newMDRRuleGroupInstance.getRuleGroupInstanceId());
		assertEquals(existingMDRRuleGroupInstance.getGroupId(),
			newMDRRuleGroupInstance.getGroupId());
		assertEquals(existingMDRRuleGroupInstance.getCompanyId(),
			newMDRRuleGroupInstance.getCompanyId());
		assertEquals(existingMDRRuleGroupInstance.getUserId(),
			newMDRRuleGroupInstance.getUserId());
		assertEquals(existingMDRRuleGroupInstance.getUserName(),
			newMDRRuleGroupInstance.getUserName());
		assertEquals(Time.getShortTimestamp(
				existingMDRRuleGroupInstance.getCreateDate()),
			Time.getShortTimestamp(newMDRRuleGroupInstance.getCreateDate()));
		assertEquals(Time.getShortTimestamp(
				existingMDRRuleGroupInstance.getModifiedDate()),
			Time.getShortTimestamp(newMDRRuleGroupInstance.getModifiedDate()));
		assertEquals(existingMDRRuleGroupInstance.getClassNameId(),
			newMDRRuleGroupInstance.getClassNameId());
		assertEquals(existingMDRRuleGroupInstance.getClassPK(),
			newMDRRuleGroupInstance.getClassPK());
		assertEquals(existingMDRRuleGroupInstance.getRuleGroupId(),
			newMDRRuleGroupInstance.getRuleGroupId());
		assertEquals(existingMDRRuleGroupInstance.getPriority(),
			newMDRRuleGroupInstance.getPriority());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		MDRRuleGroupInstance newMDRRuleGroupInstance = addMDRRuleGroupInstance();

		MDRRuleGroupInstance existingMDRRuleGroupInstance = _persistence.findByPrimaryKey(newMDRRuleGroupInstance.getPrimaryKey());

		assertEquals(existingMDRRuleGroupInstance, newMDRRuleGroupInstance);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail(
				"Missing entity did not throw NoSuchRuleGroupInstanceException");
		}
		catch (NoSuchRuleGroupInstanceException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		MDRRuleGroupInstance newMDRRuleGroupInstance = addMDRRuleGroupInstance();

		MDRRuleGroupInstance existingMDRRuleGroupInstance = _persistence.fetchByPrimaryKey(newMDRRuleGroupInstance.getPrimaryKey());

		assertEquals(existingMDRRuleGroupInstance, newMDRRuleGroupInstance);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		MDRRuleGroupInstance missingMDRRuleGroupInstance = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingMDRRuleGroupInstance);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		MDRRuleGroupInstance newMDRRuleGroupInstance = addMDRRuleGroupInstance();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MDRRuleGroupInstance.class,
				MDRRuleGroupInstance.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("ruleGroupInstanceId",
				newMDRRuleGroupInstance.getRuleGroupInstanceId()));

		List<MDRRuleGroupInstance> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		MDRRuleGroupInstance existingMDRRuleGroupInstance = result.get(0);

		assertEquals(existingMDRRuleGroupInstance, newMDRRuleGroupInstance);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MDRRuleGroupInstance.class,
				MDRRuleGroupInstance.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("ruleGroupInstanceId",
				nextLong()));

		List<MDRRuleGroupInstance> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		MDRRuleGroupInstance newMDRRuleGroupInstance = addMDRRuleGroupInstance();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MDRRuleGroupInstance.class,
				MDRRuleGroupInstance.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"ruleGroupInstanceId"));

		Object newRuleGroupInstanceId = newMDRRuleGroupInstance.getRuleGroupInstanceId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("ruleGroupInstanceId",
				new Object[] { newRuleGroupInstanceId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingRuleGroupInstanceId = result.get(0);

		assertEquals(existingRuleGroupInstanceId, newRuleGroupInstanceId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MDRRuleGroupInstance.class,
				MDRRuleGroupInstance.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"ruleGroupInstanceId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("ruleGroupInstanceId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		MDRRuleGroupInstance newMDRRuleGroupInstance = addMDRRuleGroupInstance();

		_persistence.clearCache();

		MDRRuleGroupInstanceModelImpl existingMDRRuleGroupInstanceModelImpl = (MDRRuleGroupInstanceModelImpl)_persistence.findByPrimaryKey(newMDRRuleGroupInstance.getPrimaryKey());

		assertTrue(Validator.equals(
				existingMDRRuleGroupInstanceModelImpl.getUuid(),
				existingMDRRuleGroupInstanceModelImpl.getOriginalUuid()));
		assertEquals(existingMDRRuleGroupInstanceModelImpl.getGroupId(),
			existingMDRRuleGroupInstanceModelImpl.getOriginalGroupId());

		assertEquals(existingMDRRuleGroupInstanceModelImpl.getClassNameId(),
			existingMDRRuleGroupInstanceModelImpl.getOriginalClassNameId());
		assertEquals(existingMDRRuleGroupInstanceModelImpl.getClassPK(),
			existingMDRRuleGroupInstanceModelImpl.getOriginalClassPK());
		assertEquals(existingMDRRuleGroupInstanceModelImpl.getRuleGroupId(),
			existingMDRRuleGroupInstanceModelImpl.getOriginalRuleGroupId());
	}

	protected MDRRuleGroupInstance addMDRRuleGroupInstance()
		throws Exception {
		long pk = nextLong();

		MDRRuleGroupInstance mdrRuleGroupInstance = _persistence.create(pk);

		mdrRuleGroupInstance.setUuid(randomString());

		mdrRuleGroupInstance.setGroupId(nextLong());

		mdrRuleGroupInstance.setCompanyId(nextLong());

		mdrRuleGroupInstance.setUserId(nextLong());

		mdrRuleGroupInstance.setUserName(randomString());

		mdrRuleGroupInstance.setCreateDate(nextDate());

		mdrRuleGroupInstance.setModifiedDate(nextDate());

		mdrRuleGroupInstance.setClassNameId(nextLong());

		mdrRuleGroupInstance.setClassPK(nextLong());

		mdrRuleGroupInstance.setRuleGroupId(nextLong());

		mdrRuleGroupInstance.setPriority(nextInt());

		_persistence.update(mdrRuleGroupInstance, false);

		return mdrRuleGroupInstance;
	}

	private MDRRuleGroupInstancePersistence _persistence;
}