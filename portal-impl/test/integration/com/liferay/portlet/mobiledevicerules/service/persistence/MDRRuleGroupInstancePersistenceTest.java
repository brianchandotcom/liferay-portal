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

import com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupInstanceException;
import com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance;
import com.liferay.portlet.mobiledevicerules.model.impl.MDRRuleGroupInstanceModelImpl;

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
public class MDRRuleGroupInstancePersistenceTest {
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

		MDRRuleGroupInstance mdrRuleGroupInstance = _persistence.create(pk);

		Assert.assertNotNull(mdrRuleGroupInstance);

		Assert.assertEquals(mdrRuleGroupInstance.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		MDRRuleGroupInstance newMDRRuleGroupInstance = addMDRRuleGroupInstance();

		_persistence.remove(newMDRRuleGroupInstance);

		MDRRuleGroupInstance existingMDRRuleGroupInstance = _persistence.fetchByPrimaryKey(newMDRRuleGroupInstance.getPrimaryKey());

		Assert.assertNull(existingMDRRuleGroupInstance);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addMDRRuleGroupInstance();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		MDRRuleGroupInstance newMDRRuleGroupInstance = _persistence.create(pk);

		newMDRRuleGroupInstance.setUuid(ServiceTestUtil.randomString());

		newMDRRuleGroupInstance.setGroupId(ServiceTestUtil.nextLong());

		newMDRRuleGroupInstance.setCompanyId(ServiceTestUtil.nextLong());

		newMDRRuleGroupInstance.setUserId(ServiceTestUtil.nextLong());

		newMDRRuleGroupInstance.setUserName(ServiceTestUtil.randomString());

		newMDRRuleGroupInstance.setCreateDate(ServiceTestUtil.nextDate());

		newMDRRuleGroupInstance.setModifiedDate(ServiceTestUtil.nextDate());

		newMDRRuleGroupInstance.setClassNameId(ServiceTestUtil.nextLong());

		newMDRRuleGroupInstance.setClassPK(ServiceTestUtil.nextLong());

		newMDRRuleGroupInstance.setRuleGroupId(ServiceTestUtil.nextLong());

		newMDRRuleGroupInstance.setPriority(ServiceTestUtil.nextInt());

		_persistence.update(newMDRRuleGroupInstance);

		MDRRuleGroupInstance existingMDRRuleGroupInstance = _persistence.findByPrimaryKey(newMDRRuleGroupInstance.getPrimaryKey());

		Assert.assertEquals(existingMDRRuleGroupInstance.getUuid(),
			newMDRRuleGroupInstance.getUuid());
		Assert.assertEquals(existingMDRRuleGroupInstance.getRuleGroupInstanceId(),
			newMDRRuleGroupInstance.getRuleGroupInstanceId());
		Assert.assertEquals(existingMDRRuleGroupInstance.getGroupId(),
			newMDRRuleGroupInstance.getGroupId());
		Assert.assertEquals(existingMDRRuleGroupInstance.getCompanyId(),
			newMDRRuleGroupInstance.getCompanyId());
		Assert.assertEquals(existingMDRRuleGroupInstance.getUserId(),
			newMDRRuleGroupInstance.getUserId());
		Assert.assertEquals(existingMDRRuleGroupInstance.getUserName(),
			newMDRRuleGroupInstance.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingMDRRuleGroupInstance.getCreateDate()),
			Time.getShortTimestamp(newMDRRuleGroupInstance.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingMDRRuleGroupInstance.getModifiedDate()),
			Time.getShortTimestamp(newMDRRuleGroupInstance.getModifiedDate()));
		Assert.assertEquals(existingMDRRuleGroupInstance.getClassNameId(),
			newMDRRuleGroupInstance.getClassNameId());
		Assert.assertEquals(existingMDRRuleGroupInstance.getClassPK(),
			newMDRRuleGroupInstance.getClassPK());
		Assert.assertEquals(existingMDRRuleGroupInstance.getRuleGroupId(),
			newMDRRuleGroupInstance.getRuleGroupId());
		Assert.assertEquals(existingMDRRuleGroupInstance.getPriority(),
			newMDRRuleGroupInstance.getPriority());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		MDRRuleGroupInstance newMDRRuleGroupInstance = addMDRRuleGroupInstance();

		MDRRuleGroupInstance existingMDRRuleGroupInstance = _persistence.findByPrimaryKey(newMDRRuleGroupInstance.getPrimaryKey());

		Assert.assertEquals(existingMDRRuleGroupInstance,
			newMDRRuleGroupInstance);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchRuleGroupInstanceException");
		}
		catch (NoSuchRuleGroupInstanceException nsee) {
		}
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		MDRRuleGroupInstance newMDRRuleGroupInstance = addMDRRuleGroupInstance();

		MDRRuleGroupInstance existingMDRRuleGroupInstance = _persistence.fetchByPrimaryKey(newMDRRuleGroupInstance.getPrimaryKey());

		Assert.assertEquals(existingMDRRuleGroupInstance,
			newMDRRuleGroupInstance);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		MDRRuleGroupInstance missingMDRRuleGroupInstance = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingMDRRuleGroupInstance);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		MDRRuleGroupInstance newMDRRuleGroupInstance = addMDRRuleGroupInstance();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MDRRuleGroupInstance.class,
				MDRRuleGroupInstance.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("ruleGroupInstanceId",
				newMDRRuleGroupInstance.getRuleGroupInstanceId()));

		List<MDRRuleGroupInstance> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		MDRRuleGroupInstance existingMDRRuleGroupInstance = result.get(0);

		Assert.assertEquals(existingMDRRuleGroupInstance,
			newMDRRuleGroupInstance);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MDRRuleGroupInstance.class,
				MDRRuleGroupInstance.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("ruleGroupInstanceId",
				ServiceTestUtil.nextLong()));

		List<MDRRuleGroupInstance> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
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

		Assert.assertEquals(1, result.size());

		Object existingRuleGroupInstanceId = result.get(0);

		Assert.assertEquals(existingRuleGroupInstanceId, newRuleGroupInstanceId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MDRRuleGroupInstance.class,
				MDRRuleGroupInstance.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"ruleGroupInstanceId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("ruleGroupInstanceId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		MDRRuleGroupInstance newMDRRuleGroupInstance = addMDRRuleGroupInstance();

		_persistence.clearCache();

		MDRRuleGroupInstanceModelImpl existingMDRRuleGroupInstanceModelImpl = (MDRRuleGroupInstanceModelImpl)_persistence.findByPrimaryKey(newMDRRuleGroupInstance.getPrimaryKey());

		Assert.assertTrue(Validator.equals(
				existingMDRRuleGroupInstanceModelImpl.getUuid(),
				existingMDRRuleGroupInstanceModelImpl.getOriginalUuid()));
		Assert.assertEquals(existingMDRRuleGroupInstanceModelImpl.getGroupId(),
			existingMDRRuleGroupInstanceModelImpl.getOriginalGroupId());

		Assert.assertEquals(existingMDRRuleGroupInstanceModelImpl.getClassNameId(),
			existingMDRRuleGroupInstanceModelImpl.getOriginalClassNameId());
		Assert.assertEquals(existingMDRRuleGroupInstanceModelImpl.getClassPK(),
			existingMDRRuleGroupInstanceModelImpl.getOriginalClassPK());
		Assert.assertEquals(existingMDRRuleGroupInstanceModelImpl.getRuleGroupId(),
			existingMDRRuleGroupInstanceModelImpl.getOriginalRuleGroupId());
	}

	protected MDRRuleGroupInstance addMDRRuleGroupInstance()
		throws Exception {
		long pk = ServiceTestUtil.nextLong();

		MDRRuleGroupInstance mdrRuleGroupInstance = _persistence.create(pk);

		mdrRuleGroupInstance.setUuid(ServiceTestUtil.randomString());

		mdrRuleGroupInstance.setGroupId(ServiceTestUtil.nextLong());

		mdrRuleGroupInstance.setCompanyId(ServiceTestUtil.nextLong());

		mdrRuleGroupInstance.setUserId(ServiceTestUtil.nextLong());

		mdrRuleGroupInstance.setUserName(ServiceTestUtil.randomString());

		mdrRuleGroupInstance.setCreateDate(ServiceTestUtil.nextDate());

		mdrRuleGroupInstance.setModifiedDate(ServiceTestUtil.nextDate());

		mdrRuleGroupInstance.setClassNameId(ServiceTestUtil.nextLong());

		mdrRuleGroupInstance.setClassPK(ServiceTestUtil.nextLong());

		mdrRuleGroupInstance.setRuleGroupId(ServiceTestUtil.nextLong());

		mdrRuleGroupInstance.setPriority(ServiceTestUtil.nextInt());

		_persistence.update(mdrRuleGroupInstance);

		return mdrRuleGroupInstance;
	}

	private static Log _log = LogFactoryUtil.getLog(MDRRuleGroupInstancePersistenceTest.class);
	private MDRRuleGroupInstancePersistence _persistence = (MDRRuleGroupInstancePersistence)PortalBeanLocatorUtil.locate(MDRRuleGroupInstancePersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}