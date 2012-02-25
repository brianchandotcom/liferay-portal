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

import com.liferay.portlet.mobiledevicerules.NoSuchRuleException;
import com.liferay.portlet.mobiledevicerules.model.MDRRule;
import com.liferay.portlet.mobiledevicerules.model.impl.MDRRuleModelImpl;

import java.util.List;

/**
 * @author Edward C. Han
 */
public class MDRRulePersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (MDRRulePersistence)PortalBeanLocatorUtil.locate(MDRRulePersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		MDRRule mdrRule = _persistence.create(pk);

		assertNotNull(mdrRule);

		assertEquals(mdrRule.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		MDRRule newMDRRule = addMDRRule();

		_persistence.remove(newMDRRule);

		MDRRule existingMDRRule = _persistence.fetchByPrimaryKey(newMDRRule.getPrimaryKey());

		assertNull(existingMDRRule);
	}

	public void testUpdateNew() throws Exception {
		addMDRRule();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		MDRRule newMDRRule = _persistence.create(pk);

		newMDRRule.setUuid(randomString());

		newMDRRule.setGroupId(nextLong());

		newMDRRule.setCompanyId(nextLong());

		newMDRRule.setUserId(nextLong());

		newMDRRule.setUserName(randomString());

		newMDRRule.setCreateDate(nextDate());

		newMDRRule.setModifiedDate(nextDate());

		newMDRRule.setRuleGroupId(nextLong());

		newMDRRule.setName(randomString());

		newMDRRule.setDescription(randomString());

		newMDRRule.setType(randomString());

		newMDRRule.setTypeSettings(randomString());

		_persistence.update(newMDRRule, false);

		MDRRule existingMDRRule = _persistence.findByPrimaryKey(newMDRRule.getPrimaryKey());

		assertEquals(existingMDRRule.getUuid(), newMDRRule.getUuid());
		assertEquals(existingMDRRule.getRuleId(), newMDRRule.getRuleId());
		assertEquals(existingMDRRule.getGroupId(), newMDRRule.getGroupId());
		assertEquals(existingMDRRule.getCompanyId(), newMDRRule.getCompanyId());
		assertEquals(existingMDRRule.getUserId(), newMDRRule.getUserId());
		assertEquals(existingMDRRule.getUserName(), newMDRRule.getUserName());
		assertEquals(Time.getShortTimestamp(existingMDRRule.getCreateDate()),
			Time.getShortTimestamp(newMDRRule.getCreateDate()));
		assertEquals(Time.getShortTimestamp(existingMDRRule.getModifiedDate()),
			Time.getShortTimestamp(newMDRRule.getModifiedDate()));
		assertEquals(existingMDRRule.getRuleGroupId(),
			newMDRRule.getRuleGroupId());
		assertEquals(existingMDRRule.getName(), newMDRRule.getName());
		assertEquals(existingMDRRule.getDescription(),
			newMDRRule.getDescription());
		assertEquals(existingMDRRule.getType(), newMDRRule.getType());
		assertEquals(existingMDRRule.getTypeSettings(),
			newMDRRule.getTypeSettings());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		MDRRule newMDRRule = addMDRRule();

		MDRRule existingMDRRule = _persistence.findByPrimaryKey(newMDRRule.getPrimaryKey());

		assertEquals(existingMDRRule, newMDRRule);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchRuleException");
		}
		catch (NoSuchRuleException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		MDRRule newMDRRule = addMDRRule();

		MDRRule existingMDRRule = _persistence.fetchByPrimaryKey(newMDRRule.getPrimaryKey());

		assertEquals(existingMDRRule, newMDRRule);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		MDRRule missingMDRRule = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingMDRRule);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		MDRRule newMDRRule = addMDRRule();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MDRRule.class,
				MDRRule.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("ruleId",
				newMDRRule.getRuleId()));

		List<MDRRule> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		MDRRule existingMDRRule = result.get(0);

		assertEquals(existingMDRRule, newMDRRule);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MDRRule.class,
				MDRRule.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("ruleId", nextLong()));

		List<MDRRule> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		MDRRule newMDRRule = addMDRRule();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MDRRule.class,
				MDRRule.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("ruleId"));

		Object newRuleId = newMDRRule.getRuleId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("ruleId",
				new Object[] { newRuleId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingRuleId = result.get(0);

		assertEquals(existingRuleId, newRuleId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MDRRule.class,
				MDRRule.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("ruleId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("ruleId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		MDRRule newMDRRule = addMDRRule();

		_persistence.clearCache();

		MDRRuleModelImpl existingMDRRuleModelImpl = (MDRRuleModelImpl)_persistence.findByPrimaryKey(newMDRRule.getPrimaryKey());

		assertTrue(Validator.equals(existingMDRRuleModelImpl.getUuid(),
				existingMDRRuleModelImpl.getOriginalUuid()));
		assertEquals(existingMDRRuleModelImpl.getGroupId(),
			existingMDRRuleModelImpl.getOriginalGroupId());
	}

	protected MDRRule addMDRRule() throws Exception {
		long pk = nextLong();

		MDRRule mdrRule = _persistence.create(pk);

		mdrRule.setUuid(randomString());

		mdrRule.setGroupId(nextLong());

		mdrRule.setCompanyId(nextLong());

		mdrRule.setUserId(nextLong());

		mdrRule.setUserName(randomString());

		mdrRule.setCreateDate(nextDate());

		mdrRule.setModifiedDate(nextDate());

		mdrRule.setRuleGroupId(nextLong());

		mdrRule.setName(randomString());

		mdrRule.setDescription(randomString());

		mdrRule.setType(randomString());

		mdrRule.setTypeSettings(randomString());

		_persistence.update(mdrRule, false);

		return mdrRule;
	}

	private MDRRulePersistence _persistence;
}