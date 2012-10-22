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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.mobiledevicerules.NoSuchRuleException;
import com.liferay.portlet.mobiledevicerules.model.MDRRule;
import com.liferay.portlet.mobiledevicerules.model.impl.MDRRuleModelImpl;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Edward C. Han
 */
@ExecutionTestListeners(listeners =  {
	PersistenceExecutionTestListener.class})
@RunWith(LiferayPersistenceIntegrationJUnitTestRunner.class)
public class MDRRulePersistenceTest {
	@After
	public void tearDown() throws Exception {
		Map<Serializable, BasePersistence<?>> basePersistences = _transactionalPersistenceAdvice.getBasePersistences();

		Set<Serializable> primaryKeys = basePersistences.keySet();

		for (Serializable primaryKey : primaryKeys) {
			BasePersistence<?> basePersistence = basePersistences.get(primaryKey);

			try {
				basePersistence.remove(primaryKey);
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug("The model with primary key " + primaryKey +
						" was already deleted");
				}
			}
		}

		_transactionalPersistenceAdvice.reset();
	}

	@Test
	public void testCreate() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		MDRRule mdrRule = _persistence.create(pk);

		Assert.assertNotNull(mdrRule);

		Assert.assertEquals(mdrRule.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		MDRRule newMDRRule = addMDRRule();

		_persistence.remove(newMDRRule);

		MDRRule existingMDRRule = _persistence.fetchByPrimaryKey(newMDRRule.getPrimaryKey());

		Assert.assertNull(existingMDRRule);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addMDRRule();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		MDRRule newMDRRule = _persistence.create(pk);

		newMDRRule.setUuid(ServiceTestUtil.randomString());

		newMDRRule.setGroupId(ServiceTestUtil.nextLong());

		newMDRRule.setCompanyId(ServiceTestUtil.nextLong());

		newMDRRule.setUserId(ServiceTestUtil.nextLong());

		newMDRRule.setUserName(ServiceTestUtil.randomString());

		newMDRRule.setCreateDate(ServiceTestUtil.nextDate());

		newMDRRule.setModifiedDate(ServiceTestUtil.nextDate());

		newMDRRule.setRuleGroupId(ServiceTestUtil.nextLong());

		newMDRRule.setName(ServiceTestUtil.randomString());

		newMDRRule.setDescription(ServiceTestUtil.randomString());

		newMDRRule.setType(ServiceTestUtil.randomString());

		newMDRRule.setTypeSettings(ServiceTestUtil.randomString());

		_persistence.update(newMDRRule);

		MDRRule existingMDRRule = _persistence.findByPrimaryKey(newMDRRule.getPrimaryKey());

		Assert.assertEquals(existingMDRRule.getUuid(), newMDRRule.getUuid());
		Assert.assertEquals(existingMDRRule.getRuleId(), newMDRRule.getRuleId());
		Assert.assertEquals(existingMDRRule.getGroupId(),
			newMDRRule.getGroupId());
		Assert.assertEquals(existingMDRRule.getCompanyId(),
			newMDRRule.getCompanyId());
		Assert.assertEquals(existingMDRRule.getUserId(), newMDRRule.getUserId());
		Assert.assertEquals(existingMDRRule.getUserName(),
			newMDRRule.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingMDRRule.getCreateDate()),
			Time.getShortTimestamp(newMDRRule.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingMDRRule.getModifiedDate()),
			Time.getShortTimestamp(newMDRRule.getModifiedDate()));
		Assert.assertEquals(existingMDRRule.getRuleGroupId(),
			newMDRRule.getRuleGroupId());
		Assert.assertEquals(existingMDRRule.getName(), newMDRRule.getName());
		Assert.assertEquals(existingMDRRule.getDescription(),
			newMDRRule.getDescription());
		Assert.assertEquals(existingMDRRule.getType(), newMDRRule.getType());
		Assert.assertEquals(existingMDRRule.getTypeSettings(),
			newMDRRule.getTypeSettings());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		MDRRule newMDRRule = addMDRRule();

		MDRRule existingMDRRule = _persistence.findByPrimaryKey(newMDRRule.getPrimaryKey());

		Assert.assertEquals(existingMDRRule, newMDRRule);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchRuleException");
		}
		catch (NoSuchRuleException nsee) {
		}
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		MDRRule newMDRRule = addMDRRule();

		MDRRule existingMDRRule = _persistence.fetchByPrimaryKey(newMDRRule.getPrimaryKey());

		Assert.assertEquals(existingMDRRule, newMDRRule);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		MDRRule missingMDRRule = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingMDRRule);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		MDRRule newMDRRule = addMDRRule();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MDRRule.class,
				MDRRule.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("ruleId",
				newMDRRule.getRuleId()));

		List<MDRRule> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		MDRRule existingMDRRule = result.get(0);

		Assert.assertEquals(existingMDRRule, newMDRRule);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MDRRule.class,
				MDRRule.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("ruleId",
				ServiceTestUtil.nextLong()));

		List<MDRRule> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
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

		Assert.assertEquals(1, result.size());

		Object existingRuleId = result.get(0);

		Assert.assertEquals(existingRuleId, newRuleId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MDRRule.class,
				MDRRule.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("ruleId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("ruleId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		MDRRule newMDRRule = addMDRRule();

		_persistence.clearCache();

		MDRRuleModelImpl existingMDRRuleModelImpl = (MDRRuleModelImpl)_persistence.findByPrimaryKey(newMDRRule.getPrimaryKey());

		Assert.assertTrue(Validator.equals(existingMDRRuleModelImpl.getUuid(),
				existingMDRRuleModelImpl.getOriginalUuid()));
		Assert.assertEquals(existingMDRRuleModelImpl.getGroupId(),
			existingMDRRuleModelImpl.getOriginalGroupId());
	}

	protected MDRRule addMDRRule() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		MDRRule mdrRule = _persistence.create(pk);

		mdrRule.setUuid(ServiceTestUtil.randomString());

		mdrRule.setGroupId(ServiceTestUtil.nextLong());

		mdrRule.setCompanyId(ServiceTestUtil.nextLong());

		mdrRule.setUserId(ServiceTestUtil.nextLong());

		mdrRule.setUserName(ServiceTestUtil.randomString());

		mdrRule.setCreateDate(ServiceTestUtil.nextDate());

		mdrRule.setModifiedDate(ServiceTestUtil.nextDate());

		mdrRule.setRuleGroupId(ServiceTestUtil.nextLong());

		mdrRule.setName(ServiceTestUtil.randomString());

		mdrRule.setDescription(ServiceTestUtil.randomString());

		mdrRule.setType(ServiceTestUtil.randomString());

		mdrRule.setTypeSettings(ServiceTestUtil.randomString());

		_persistence.update(mdrRule);

		return mdrRule;
	}

	private static Log _log = LogFactoryUtil.getLog(MDRRulePersistenceTest.class);
	private MDRRulePersistence _persistence = (MDRRulePersistence)PortalBeanLocatorUtil.locate(MDRRulePersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}