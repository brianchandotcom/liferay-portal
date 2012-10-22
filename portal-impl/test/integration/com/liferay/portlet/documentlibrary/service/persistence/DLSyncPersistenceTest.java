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

package com.liferay.portlet.documentlibrary.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.documentlibrary.NoSuchSyncException;
import com.liferay.portlet.documentlibrary.model.DLSync;
import com.liferay.portlet.documentlibrary.model.impl.DLSyncModelImpl;

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
public class DLSyncPersistenceTest {
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

		DLSync dlSync = _persistence.create(pk);

		Assert.assertNotNull(dlSync);

		Assert.assertEquals(dlSync.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		DLSync newDLSync = addDLSync();

		_persistence.remove(newDLSync);

		DLSync existingDLSync = _persistence.fetchByPrimaryKey(newDLSync.getPrimaryKey());

		Assert.assertNull(existingDLSync);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addDLSync();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		DLSync newDLSync = _persistence.create(pk);

		newDLSync.setCompanyId(ServiceTestUtil.nextLong());

		newDLSync.setCreateDate(ServiceTestUtil.nextDate());

		newDLSync.setModifiedDate(ServiceTestUtil.nextDate());

		newDLSync.setFileId(ServiceTestUtil.nextLong());

		newDLSync.setFileUuid(ServiceTestUtil.randomString());

		newDLSync.setRepositoryId(ServiceTestUtil.nextLong());

		newDLSync.setParentFolderId(ServiceTestUtil.nextLong());

		newDLSync.setName(ServiceTestUtil.randomString());

		newDLSync.setDescription(ServiceTestUtil.randomString());

		newDLSync.setEvent(ServiceTestUtil.randomString());

		newDLSync.setType(ServiceTestUtil.randomString());

		newDLSync.setVersion(ServiceTestUtil.randomString());

		_persistence.update(newDLSync);

		DLSync existingDLSync = _persistence.findByPrimaryKey(newDLSync.getPrimaryKey());

		Assert.assertEquals(existingDLSync.getSyncId(), newDLSync.getSyncId());
		Assert.assertEquals(existingDLSync.getCompanyId(),
			newDLSync.getCompanyId());
		Assert.assertEquals(Time.getShortTimestamp(
				existingDLSync.getCreateDate()),
			Time.getShortTimestamp(newDLSync.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingDLSync.getModifiedDate()),
			Time.getShortTimestamp(newDLSync.getModifiedDate()));
		Assert.assertEquals(existingDLSync.getFileId(), newDLSync.getFileId());
		Assert.assertEquals(existingDLSync.getFileUuid(),
			newDLSync.getFileUuid());
		Assert.assertEquals(existingDLSync.getRepositoryId(),
			newDLSync.getRepositoryId());
		Assert.assertEquals(existingDLSync.getParentFolderId(),
			newDLSync.getParentFolderId());
		Assert.assertEquals(existingDLSync.getName(), newDLSync.getName());
		Assert.assertEquals(existingDLSync.getDescription(),
			newDLSync.getDescription());
		Assert.assertEquals(existingDLSync.getEvent(), newDLSync.getEvent());
		Assert.assertEquals(existingDLSync.getType(), newDLSync.getType());
		Assert.assertEquals(existingDLSync.getVersion(), newDLSync.getVersion());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		DLSync newDLSync = addDLSync();

		DLSync existingDLSync = _persistence.findByPrimaryKey(newDLSync.getPrimaryKey());

		Assert.assertEquals(existingDLSync, newDLSync);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchSyncException");
		}
		catch (NoSuchSyncException nsee) {
		}
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		DLSync newDLSync = addDLSync();

		DLSync existingDLSync = _persistence.fetchByPrimaryKey(newDLSync.getPrimaryKey());

		Assert.assertEquals(existingDLSync, newDLSync);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		DLSync missingDLSync = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingDLSync);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		DLSync newDLSync = addDLSync();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLSync.class,
				DLSync.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("syncId",
				newDLSync.getSyncId()));

		List<DLSync> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		DLSync existingDLSync = result.get(0);

		Assert.assertEquals(existingDLSync, newDLSync);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLSync.class,
				DLSync.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("syncId",
				ServiceTestUtil.nextLong()));

		List<DLSync> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		DLSync newDLSync = addDLSync();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLSync.class,
				DLSync.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("syncId"));

		Object newSyncId = newDLSync.getSyncId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("syncId",
				new Object[] { newSyncId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingSyncId = result.get(0);

		Assert.assertEquals(existingSyncId, newSyncId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLSync.class,
				DLSync.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("syncId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("syncId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		DLSync newDLSync = addDLSync();

		_persistence.clearCache();

		DLSyncModelImpl existingDLSyncModelImpl = (DLSyncModelImpl)_persistence.findByPrimaryKey(newDLSync.getPrimaryKey());

		Assert.assertEquals(existingDLSyncModelImpl.getFileId(),
			existingDLSyncModelImpl.getOriginalFileId());
	}

	protected DLSync addDLSync() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		DLSync dlSync = _persistence.create(pk);

		dlSync.setCompanyId(ServiceTestUtil.nextLong());

		dlSync.setCreateDate(ServiceTestUtil.nextDate());

		dlSync.setModifiedDate(ServiceTestUtil.nextDate());

		dlSync.setFileId(ServiceTestUtil.nextLong());

		dlSync.setFileUuid(ServiceTestUtil.randomString());

		dlSync.setRepositoryId(ServiceTestUtil.nextLong());

		dlSync.setParentFolderId(ServiceTestUtil.nextLong());

		dlSync.setName(ServiceTestUtil.randomString());

		dlSync.setDescription(ServiceTestUtil.randomString());

		dlSync.setEvent(ServiceTestUtil.randomString());

		dlSync.setType(ServiceTestUtil.randomString());

		dlSync.setVersion(ServiceTestUtil.randomString());

		_persistence.update(dlSync);

		return dlSync;
	}

	private static Log _log = LogFactoryUtil.getLog(DLSyncPersistenceTest.class);
	private DLSyncPersistence _persistence = (DLSyncPersistence)PortalBeanLocatorUtil.locate(DLSyncPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}