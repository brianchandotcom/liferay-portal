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

import com.liferay.portlet.mobiledevicerules.NoSuchActionException;
import com.liferay.portlet.mobiledevicerules.model.MDRAction;
import com.liferay.portlet.mobiledevicerules.model.impl.MDRActionModelImpl;

import java.util.List;

/**
 * @author Edward C. Han
 */
public class MDRActionPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (MDRActionPersistence)PortalBeanLocatorUtil.locate(MDRActionPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		MDRAction mdrAction = _persistence.create(pk);

		assertNotNull(mdrAction);

		assertEquals(mdrAction.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		MDRAction newMDRAction = addMDRAction();

		_persistence.remove(newMDRAction);

		MDRAction existingMDRAction = _persistence.fetchByPrimaryKey(newMDRAction.getPrimaryKey());

		assertNull(existingMDRAction);
	}

	public void testUpdateNew() throws Exception {
		addMDRAction();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		MDRAction newMDRAction = _persistence.create(pk);

		newMDRAction.setUuid(randomString());

		newMDRAction.setGroupId(nextLong());

		newMDRAction.setCompanyId(nextLong());

		newMDRAction.setUserId(nextLong());

		newMDRAction.setUserName(randomString());

		newMDRAction.setCreateDate(nextDate());

		newMDRAction.setModifiedDate(nextDate());

		newMDRAction.setClassNameId(nextLong());

		newMDRAction.setClassPK(nextLong());

		newMDRAction.setRuleGroupInstanceId(nextLong());

		newMDRAction.setName(randomString());

		newMDRAction.setDescription(randomString());

		newMDRAction.setType(randomString());

		newMDRAction.setTypeSettings(randomString());

		_persistence.update(newMDRAction, false);

		MDRAction existingMDRAction = _persistence.findByPrimaryKey(newMDRAction.getPrimaryKey());

		assertEquals(existingMDRAction.getUuid(), newMDRAction.getUuid());
		assertEquals(existingMDRAction.getActionId(), newMDRAction.getActionId());
		assertEquals(existingMDRAction.getGroupId(), newMDRAction.getGroupId());
		assertEquals(existingMDRAction.getCompanyId(),
			newMDRAction.getCompanyId());
		assertEquals(existingMDRAction.getUserId(), newMDRAction.getUserId());
		assertEquals(existingMDRAction.getUserName(), newMDRAction.getUserName());
		assertEquals(Time.getShortTimestamp(existingMDRAction.getCreateDate()),
			Time.getShortTimestamp(newMDRAction.getCreateDate()));
		assertEquals(Time.getShortTimestamp(existingMDRAction.getModifiedDate()),
			Time.getShortTimestamp(newMDRAction.getModifiedDate()));
		assertEquals(existingMDRAction.getClassNameId(),
			newMDRAction.getClassNameId());
		assertEquals(existingMDRAction.getClassPK(), newMDRAction.getClassPK());
		assertEquals(existingMDRAction.getRuleGroupInstanceId(),
			newMDRAction.getRuleGroupInstanceId());
		assertEquals(existingMDRAction.getName(), newMDRAction.getName());
		assertEquals(existingMDRAction.getDescription(),
			newMDRAction.getDescription());
		assertEquals(existingMDRAction.getType(), newMDRAction.getType());
		assertEquals(existingMDRAction.getTypeSettings(),
			newMDRAction.getTypeSettings());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		MDRAction newMDRAction = addMDRAction();

		MDRAction existingMDRAction = _persistence.findByPrimaryKey(newMDRAction.getPrimaryKey());

		assertEquals(existingMDRAction, newMDRAction);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchActionException");
		}
		catch (NoSuchActionException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		MDRAction newMDRAction = addMDRAction();

		MDRAction existingMDRAction = _persistence.fetchByPrimaryKey(newMDRAction.getPrimaryKey());

		assertEquals(existingMDRAction, newMDRAction);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		MDRAction missingMDRAction = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingMDRAction);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		MDRAction newMDRAction = addMDRAction();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MDRAction.class,
				MDRAction.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("actionId",
				newMDRAction.getActionId()));

		List<MDRAction> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		MDRAction existingMDRAction = result.get(0);

		assertEquals(existingMDRAction, newMDRAction);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MDRAction.class,
				MDRAction.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("actionId", nextLong()));

		List<MDRAction> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		MDRAction newMDRAction = addMDRAction();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MDRAction.class,
				MDRAction.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("actionId"));

		Object newActionId = newMDRAction.getActionId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("actionId",
				new Object[] { newActionId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingActionId = result.get(0);

		assertEquals(existingActionId, newActionId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MDRAction.class,
				MDRAction.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("actionId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("actionId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		MDRAction newMDRAction = addMDRAction();

		_persistence.clearCache();

		MDRActionModelImpl existingMDRActionModelImpl = (MDRActionModelImpl)_persistence.findByPrimaryKey(newMDRAction.getPrimaryKey());

		assertTrue(Validator.equals(existingMDRActionModelImpl.getUuid(),
				existingMDRActionModelImpl.getOriginalUuid()));
		assertEquals(existingMDRActionModelImpl.getGroupId(),
			existingMDRActionModelImpl.getOriginalGroupId());
	}

	protected MDRAction addMDRAction() throws Exception {
		long pk = nextLong();

		MDRAction mdrAction = _persistence.create(pk);

		mdrAction.setUuid(randomString());

		mdrAction.setGroupId(nextLong());

		mdrAction.setCompanyId(nextLong());

		mdrAction.setUserId(nextLong());

		mdrAction.setUserName(randomString());

		mdrAction.setCreateDate(nextDate());

		mdrAction.setModifiedDate(nextDate());

		mdrAction.setClassNameId(nextLong());

		mdrAction.setClassPK(nextLong());

		mdrAction.setRuleGroupInstanceId(nextLong());

		mdrAction.setName(randomString());

		mdrAction.setDescription(randomString());

		mdrAction.setType(randomString());

		mdrAction.setTypeSettings(randomString());

		_persistence.update(mdrAction, false);

		return mdrAction;
	}

	private MDRActionPersistence _persistence;
}