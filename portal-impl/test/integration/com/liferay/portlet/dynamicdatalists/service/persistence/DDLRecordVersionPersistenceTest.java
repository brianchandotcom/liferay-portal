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

package com.liferay.portlet.dynamicdatalists.service.persistence;

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

import com.liferay.portlet.dynamicdatalists.NoSuchRecordVersionException;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion;
import com.liferay.portlet.dynamicdatalists.model.impl.DDLRecordVersionModelImpl;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
@ExecutionTestListeners(listeners =  {
	PersistenceExecutionTestListener.class})
@RunWith(LiferayPersistenceIntegrationJUnitTestRunner.class)
public class DDLRecordVersionPersistenceTest {
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

		DDLRecordVersion ddlRecordVersion = _persistence.create(pk);

		Assert.assertNotNull(ddlRecordVersion);

		Assert.assertEquals(ddlRecordVersion.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		DDLRecordVersion newDDLRecordVersion = addDDLRecordVersion();

		_persistence.remove(newDDLRecordVersion);

		DDLRecordVersion existingDDLRecordVersion = _persistence.fetchByPrimaryKey(newDDLRecordVersion.getPrimaryKey());

		Assert.assertNull(existingDDLRecordVersion);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addDDLRecordVersion();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		DDLRecordVersion newDDLRecordVersion = _persistence.create(pk);

		newDDLRecordVersion.setGroupId(ServiceTestUtil.nextLong());

		newDDLRecordVersion.setCompanyId(ServiceTestUtil.nextLong());

		newDDLRecordVersion.setUserId(ServiceTestUtil.nextLong());

		newDDLRecordVersion.setUserName(ServiceTestUtil.randomString());

		newDDLRecordVersion.setCreateDate(ServiceTestUtil.nextDate());

		newDDLRecordVersion.setDDMStorageId(ServiceTestUtil.nextLong());

		newDDLRecordVersion.setRecordSetId(ServiceTestUtil.nextLong());

		newDDLRecordVersion.setRecordId(ServiceTestUtil.nextLong());

		newDDLRecordVersion.setVersion(ServiceTestUtil.randomString());

		newDDLRecordVersion.setDisplayIndex(ServiceTestUtil.nextInt());

		newDDLRecordVersion.setStatus(ServiceTestUtil.nextInt());

		newDDLRecordVersion.setStatusByUserId(ServiceTestUtil.nextLong());

		newDDLRecordVersion.setStatusByUserName(ServiceTestUtil.randomString());

		newDDLRecordVersion.setStatusDate(ServiceTestUtil.nextDate());

		_persistence.update(newDDLRecordVersion);

		DDLRecordVersion existingDDLRecordVersion = _persistence.findByPrimaryKey(newDDLRecordVersion.getPrimaryKey());

		Assert.assertEquals(existingDDLRecordVersion.getRecordVersionId(),
			newDDLRecordVersion.getRecordVersionId());
		Assert.assertEquals(existingDDLRecordVersion.getGroupId(),
			newDDLRecordVersion.getGroupId());
		Assert.assertEquals(existingDDLRecordVersion.getCompanyId(),
			newDDLRecordVersion.getCompanyId());
		Assert.assertEquals(existingDDLRecordVersion.getUserId(),
			newDDLRecordVersion.getUserId());
		Assert.assertEquals(existingDDLRecordVersion.getUserName(),
			newDDLRecordVersion.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingDDLRecordVersion.getCreateDate()),
			Time.getShortTimestamp(newDDLRecordVersion.getCreateDate()));
		Assert.assertEquals(existingDDLRecordVersion.getDDMStorageId(),
			newDDLRecordVersion.getDDMStorageId());
		Assert.assertEquals(existingDDLRecordVersion.getRecordSetId(),
			newDDLRecordVersion.getRecordSetId());
		Assert.assertEquals(existingDDLRecordVersion.getRecordId(),
			newDDLRecordVersion.getRecordId());
		Assert.assertEquals(existingDDLRecordVersion.getVersion(),
			newDDLRecordVersion.getVersion());
		Assert.assertEquals(existingDDLRecordVersion.getDisplayIndex(),
			newDDLRecordVersion.getDisplayIndex());
		Assert.assertEquals(existingDDLRecordVersion.getStatus(),
			newDDLRecordVersion.getStatus());
		Assert.assertEquals(existingDDLRecordVersion.getStatusByUserId(),
			newDDLRecordVersion.getStatusByUserId());
		Assert.assertEquals(existingDDLRecordVersion.getStatusByUserName(),
			newDDLRecordVersion.getStatusByUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingDDLRecordVersion.getStatusDate()),
			Time.getShortTimestamp(newDDLRecordVersion.getStatusDate()));
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		DDLRecordVersion newDDLRecordVersion = addDDLRecordVersion();

		DDLRecordVersion existingDDLRecordVersion = _persistence.findByPrimaryKey(newDDLRecordVersion.getPrimaryKey());

		Assert.assertEquals(existingDDLRecordVersion, newDDLRecordVersion);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchRecordVersionException");
		}
		catch (NoSuchRecordVersionException nsee) {
		}
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		DDLRecordVersion newDDLRecordVersion = addDDLRecordVersion();

		DDLRecordVersion existingDDLRecordVersion = _persistence.fetchByPrimaryKey(newDDLRecordVersion.getPrimaryKey());

		Assert.assertEquals(existingDDLRecordVersion, newDDLRecordVersion);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		DDLRecordVersion missingDDLRecordVersion = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingDDLRecordVersion);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		DDLRecordVersion newDDLRecordVersion = addDDLRecordVersion();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDLRecordVersion.class,
				DDLRecordVersion.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("recordVersionId",
				newDDLRecordVersion.getRecordVersionId()));

		List<DDLRecordVersion> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		DDLRecordVersion existingDDLRecordVersion = result.get(0);

		Assert.assertEquals(existingDDLRecordVersion, newDDLRecordVersion);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDLRecordVersion.class,
				DDLRecordVersion.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("recordVersionId",
				ServiceTestUtil.nextLong()));

		List<DDLRecordVersion> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		DDLRecordVersion newDDLRecordVersion = addDDLRecordVersion();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDLRecordVersion.class,
				DDLRecordVersion.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"recordVersionId"));

		Object newRecordVersionId = newDDLRecordVersion.getRecordVersionId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("recordVersionId",
				new Object[] { newRecordVersionId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingRecordVersionId = result.get(0);

		Assert.assertEquals(existingRecordVersionId, newRecordVersionId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDLRecordVersion.class,
				DDLRecordVersion.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"recordVersionId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("recordVersionId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		DDLRecordVersion newDDLRecordVersion = addDDLRecordVersion();

		_persistence.clearCache();

		DDLRecordVersionModelImpl existingDDLRecordVersionModelImpl = (DDLRecordVersionModelImpl)_persistence.findByPrimaryKey(newDDLRecordVersion.getPrimaryKey());

		Assert.assertEquals(existingDDLRecordVersionModelImpl.getRecordId(),
			existingDDLRecordVersionModelImpl.getOriginalRecordId());
		Assert.assertTrue(Validator.equals(
				existingDDLRecordVersionModelImpl.getVersion(),
				existingDDLRecordVersionModelImpl.getOriginalVersion()));
	}

	protected DDLRecordVersion addDDLRecordVersion() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		DDLRecordVersion ddlRecordVersion = _persistence.create(pk);

		ddlRecordVersion.setGroupId(ServiceTestUtil.nextLong());

		ddlRecordVersion.setCompanyId(ServiceTestUtil.nextLong());

		ddlRecordVersion.setUserId(ServiceTestUtil.nextLong());

		ddlRecordVersion.setUserName(ServiceTestUtil.randomString());

		ddlRecordVersion.setCreateDate(ServiceTestUtil.nextDate());

		ddlRecordVersion.setDDMStorageId(ServiceTestUtil.nextLong());

		ddlRecordVersion.setRecordSetId(ServiceTestUtil.nextLong());

		ddlRecordVersion.setRecordId(ServiceTestUtil.nextLong());

		ddlRecordVersion.setVersion(ServiceTestUtil.randomString());

		ddlRecordVersion.setDisplayIndex(ServiceTestUtil.nextInt());

		ddlRecordVersion.setStatus(ServiceTestUtil.nextInt());

		ddlRecordVersion.setStatusByUserId(ServiceTestUtil.nextLong());

		ddlRecordVersion.setStatusByUserName(ServiceTestUtil.randomString());

		ddlRecordVersion.setStatusDate(ServiceTestUtil.nextDate());

		_persistence.update(ddlRecordVersion);

		return ddlRecordVersion;
	}

	private static Log _log = LogFactoryUtil.getLog(DDLRecordVersionPersistenceTest.class);
	private DDLRecordVersionPersistence _persistence = (DDLRecordVersionPersistence)PortalBeanLocatorUtil.locate(DDLRecordVersionPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}