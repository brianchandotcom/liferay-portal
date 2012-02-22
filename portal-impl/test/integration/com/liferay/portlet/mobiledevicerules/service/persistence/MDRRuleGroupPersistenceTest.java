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

import com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupException;
import com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup;
import com.liferay.portlet.mobiledevicerules.model.impl.MDRRuleGroupModelImpl;

import java.util.List;

/**
 * @author Edward C. Han
 */
public class MDRRuleGroupPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (MDRRuleGroupPersistence)PortalBeanLocatorUtil.locate(MDRRuleGroupPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		MDRRuleGroup mdrRuleGroup = _persistence.create(pk);

		assertNotNull(mdrRuleGroup);

		assertEquals(mdrRuleGroup.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		MDRRuleGroup newMDRRuleGroup = addMDRRuleGroup();

		_persistence.remove(newMDRRuleGroup);

		MDRRuleGroup existingMDRRuleGroup = _persistence.fetchByPrimaryKey(newMDRRuleGroup.getPrimaryKey());

		assertNull(existingMDRRuleGroup);
	}

	public void testUpdateNew() throws Exception {
		addMDRRuleGroup();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		MDRRuleGroup newMDRRuleGroup = _persistence.create(pk);

		newMDRRuleGroup.setUuid(randomString());

		newMDRRuleGroup.setGroupId(nextLong());

		newMDRRuleGroup.setCompanyId(nextLong());

		newMDRRuleGroup.setUserId(nextLong());

		newMDRRuleGroup.setUserName(randomString());

		newMDRRuleGroup.setCreateDate(nextDate());

		newMDRRuleGroup.setModifiedDate(nextDate());

		newMDRRuleGroup.setName(randomString());

		newMDRRuleGroup.setDescription(randomString());

		_persistence.update(newMDRRuleGroup, false);

		MDRRuleGroup existingMDRRuleGroup = _persistence.findByPrimaryKey(newMDRRuleGroup.getPrimaryKey());

		assertEquals(existingMDRRuleGroup.getUuid(), newMDRRuleGroup.getUuid());
		assertEquals(existingMDRRuleGroup.getRuleGroupId(),
			newMDRRuleGroup.getRuleGroupId());
		assertEquals(existingMDRRuleGroup.getGroupId(),
			newMDRRuleGroup.getGroupId());
		assertEquals(existingMDRRuleGroup.getCompanyId(),
			newMDRRuleGroup.getCompanyId());
		assertEquals(existingMDRRuleGroup.getUserId(),
			newMDRRuleGroup.getUserId());
		assertEquals(existingMDRRuleGroup.getUserName(),
			newMDRRuleGroup.getUserName());
		assertEquals(Time.getShortTimestamp(
				existingMDRRuleGroup.getCreateDate()),
			Time.getShortTimestamp(newMDRRuleGroup.getCreateDate()));
		assertEquals(Time.getShortTimestamp(
				existingMDRRuleGroup.getModifiedDate()),
			Time.getShortTimestamp(newMDRRuleGroup.getModifiedDate()));
		assertEquals(existingMDRRuleGroup.getName(), newMDRRuleGroup.getName());
		assertEquals(existingMDRRuleGroup.getDescription(),
			newMDRRuleGroup.getDescription());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		MDRRuleGroup newMDRRuleGroup = addMDRRuleGroup();

		MDRRuleGroup existingMDRRuleGroup = _persistence.findByPrimaryKey(newMDRRuleGroup.getPrimaryKey());

		assertEquals(existingMDRRuleGroup, newMDRRuleGroup);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchRuleGroupException");
		}
		catch (NoSuchRuleGroupException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		MDRRuleGroup newMDRRuleGroup = addMDRRuleGroup();

		MDRRuleGroup existingMDRRuleGroup = _persistence.fetchByPrimaryKey(newMDRRuleGroup.getPrimaryKey());

		assertEquals(existingMDRRuleGroup, newMDRRuleGroup);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		MDRRuleGroup missingMDRRuleGroup = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingMDRRuleGroup);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		MDRRuleGroup newMDRRuleGroup = addMDRRuleGroup();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MDRRuleGroup.class,
				MDRRuleGroup.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("ruleGroupId",
				newMDRRuleGroup.getRuleGroupId()));

		List<MDRRuleGroup> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		MDRRuleGroup existingMDRRuleGroup = result.get(0);

		assertEquals(existingMDRRuleGroup, newMDRRuleGroup);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MDRRuleGroup.class,
				MDRRuleGroup.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("ruleGroupId", nextLong()));

		List<MDRRuleGroup> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		MDRRuleGroup newMDRRuleGroup = addMDRRuleGroup();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MDRRuleGroup.class,
				MDRRuleGroup.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("ruleGroupId"));

		Object newRuleGroupId = newMDRRuleGroup.getRuleGroupId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("ruleGroupId",
				new Object[] { newRuleGroupId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingRuleGroupId = result.get(0);

		assertEquals(existingRuleGroupId, newRuleGroupId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MDRRuleGroup.class,
				MDRRuleGroup.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("ruleGroupId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("ruleGroupId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		MDRRuleGroup newMDRRuleGroup = addMDRRuleGroup();

		_persistence.clearCache();

		MDRRuleGroupModelImpl existingMDRRuleGroupModelImpl = (MDRRuleGroupModelImpl)_persistence.findByPrimaryKey(newMDRRuleGroup.getPrimaryKey());

		assertTrue(Validator.equals(existingMDRRuleGroupModelImpl.getUuid(),
				existingMDRRuleGroupModelImpl.getOriginalUuid()));
		assertEquals(existingMDRRuleGroupModelImpl.getGroupId(),
			existingMDRRuleGroupModelImpl.getOriginalGroupId());
	}

	protected MDRRuleGroup addMDRRuleGroup() throws Exception {
		long pk = nextLong();

		MDRRuleGroup mdrRuleGroup = _persistence.create(pk);

		mdrRuleGroup.setUuid(randomString());

		mdrRuleGroup.setGroupId(nextLong());

		mdrRuleGroup.setCompanyId(nextLong());

		mdrRuleGroup.setUserId(nextLong());

		mdrRuleGroup.setUserName(randomString());

		mdrRuleGroup.setCreateDate(nextDate());

		mdrRuleGroup.setModifiedDate(nextDate());

		mdrRuleGroup.setName(randomString());

		mdrRuleGroup.setDescription(randomString());

		_persistence.update(mdrRuleGroup, false);

		return mdrRuleGroup;
	}

	private MDRRuleGroupPersistence _persistence;
}