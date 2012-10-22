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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.impl.DLFolderModelImpl;

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
public class DLFolderPersistenceTest {
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

		DLFolder dlFolder = _persistence.create(pk);

		Assert.assertNotNull(dlFolder);

		Assert.assertEquals(dlFolder.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		DLFolder newDLFolder = addDLFolder();

		_persistence.remove(newDLFolder);

		DLFolder existingDLFolder = _persistence.fetchByPrimaryKey(newDLFolder.getPrimaryKey());

		Assert.assertNull(existingDLFolder);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addDLFolder();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		DLFolder newDLFolder = _persistence.create(pk);

		newDLFolder.setUuid(ServiceTestUtil.randomString());

		newDLFolder.setGroupId(ServiceTestUtil.nextLong());

		newDLFolder.setCompanyId(ServiceTestUtil.nextLong());

		newDLFolder.setUserId(ServiceTestUtil.nextLong());

		newDLFolder.setUserName(ServiceTestUtil.randomString());

		newDLFolder.setCreateDate(ServiceTestUtil.nextDate());

		newDLFolder.setModifiedDate(ServiceTestUtil.nextDate());

		newDLFolder.setRepositoryId(ServiceTestUtil.nextLong());

		newDLFolder.setMountPoint(ServiceTestUtil.randomBoolean());

		newDLFolder.setParentFolderId(ServiceTestUtil.nextLong());

		newDLFolder.setName(ServiceTestUtil.randomString());

		newDLFolder.setDescription(ServiceTestUtil.randomString());

		newDLFolder.setLastPostDate(ServiceTestUtil.nextDate());

		newDLFolder.setDefaultFileEntryTypeId(ServiceTestUtil.nextLong());

		newDLFolder.setHidden(ServiceTestUtil.randomBoolean());

		newDLFolder.setOverrideFileEntryTypes(ServiceTestUtil.randomBoolean());

		newDLFolder.setStatus(ServiceTestUtil.nextInt());

		newDLFolder.setStatusByUserId(ServiceTestUtil.nextLong());

		newDLFolder.setStatusByUserName(ServiceTestUtil.randomString());

		newDLFolder.setStatusDate(ServiceTestUtil.nextDate());

		_persistence.update(newDLFolder);

		DLFolder existingDLFolder = _persistence.findByPrimaryKey(newDLFolder.getPrimaryKey());

		Assert.assertEquals(existingDLFolder.getUuid(), newDLFolder.getUuid());
		Assert.assertEquals(existingDLFolder.getFolderId(),
			newDLFolder.getFolderId());
		Assert.assertEquals(existingDLFolder.getGroupId(),
			newDLFolder.getGroupId());
		Assert.assertEquals(existingDLFolder.getCompanyId(),
			newDLFolder.getCompanyId());
		Assert.assertEquals(existingDLFolder.getUserId(),
			newDLFolder.getUserId());
		Assert.assertEquals(existingDLFolder.getUserName(),
			newDLFolder.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingDLFolder.getCreateDate()),
			Time.getShortTimestamp(newDLFolder.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingDLFolder.getModifiedDate()),
			Time.getShortTimestamp(newDLFolder.getModifiedDate()));
		Assert.assertEquals(existingDLFolder.getRepositoryId(),
			newDLFolder.getRepositoryId());
		Assert.assertEquals(existingDLFolder.getMountPoint(),
			newDLFolder.getMountPoint());
		Assert.assertEquals(existingDLFolder.getParentFolderId(),
			newDLFolder.getParentFolderId());
		Assert.assertEquals(existingDLFolder.getName(), newDLFolder.getName());
		Assert.assertEquals(existingDLFolder.getDescription(),
			newDLFolder.getDescription());
		Assert.assertEquals(Time.getShortTimestamp(
				existingDLFolder.getLastPostDate()),
			Time.getShortTimestamp(newDLFolder.getLastPostDate()));
		Assert.assertEquals(existingDLFolder.getDefaultFileEntryTypeId(),
			newDLFolder.getDefaultFileEntryTypeId());
		Assert.assertEquals(existingDLFolder.getHidden(),
			newDLFolder.getHidden());
		Assert.assertEquals(existingDLFolder.getOverrideFileEntryTypes(),
			newDLFolder.getOverrideFileEntryTypes());
		Assert.assertEquals(existingDLFolder.getStatus(),
			newDLFolder.getStatus());
		Assert.assertEquals(existingDLFolder.getStatusByUserId(),
			newDLFolder.getStatusByUserId());
		Assert.assertEquals(existingDLFolder.getStatusByUserName(),
			newDLFolder.getStatusByUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingDLFolder.getStatusDate()),
			Time.getShortTimestamp(newDLFolder.getStatusDate()));
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		DLFolder newDLFolder = addDLFolder();

		DLFolder existingDLFolder = _persistence.findByPrimaryKey(newDLFolder.getPrimaryKey());

		Assert.assertEquals(existingDLFolder, newDLFolder);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchFolderException");
		}
		catch (NoSuchFolderException nsee) {
		}
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		DLFolder newDLFolder = addDLFolder();

		DLFolder existingDLFolder = _persistence.fetchByPrimaryKey(newDLFolder.getPrimaryKey());

		Assert.assertEquals(existingDLFolder, newDLFolder);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		DLFolder missingDLFolder = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingDLFolder);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		DLFolder newDLFolder = addDLFolder();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLFolder.class,
				DLFolder.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("folderId",
				newDLFolder.getFolderId()));

		List<DLFolder> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		DLFolder existingDLFolder = result.get(0);

		Assert.assertEquals(existingDLFolder, newDLFolder);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLFolder.class,
				DLFolder.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("folderId",
				ServiceTestUtil.nextLong()));

		List<DLFolder> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		DLFolder newDLFolder = addDLFolder();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLFolder.class,
				DLFolder.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("folderId"));

		Object newFolderId = newDLFolder.getFolderId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("folderId",
				new Object[] { newFolderId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingFolderId = result.get(0);

		Assert.assertEquals(existingFolderId, newFolderId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLFolder.class,
				DLFolder.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("folderId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("folderId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		DLFolder newDLFolder = addDLFolder();

		_persistence.clearCache();

		DLFolderModelImpl existingDLFolderModelImpl = (DLFolderModelImpl)_persistence.findByPrimaryKey(newDLFolder.getPrimaryKey());

		Assert.assertTrue(Validator.equals(
				existingDLFolderModelImpl.getUuid(),
				existingDLFolderModelImpl.getOriginalUuid()));
		Assert.assertEquals(existingDLFolderModelImpl.getGroupId(),
			existingDLFolderModelImpl.getOriginalGroupId());

		Assert.assertEquals(existingDLFolderModelImpl.getRepositoryId(),
			existingDLFolderModelImpl.getOriginalRepositoryId());

		Assert.assertEquals(existingDLFolderModelImpl.getGroupId(),
			existingDLFolderModelImpl.getOriginalGroupId());
		Assert.assertEquals(existingDLFolderModelImpl.getParentFolderId(),
			existingDLFolderModelImpl.getOriginalParentFolderId());
		Assert.assertTrue(Validator.equals(
				existingDLFolderModelImpl.getName(),
				existingDLFolderModelImpl.getOriginalName()));
	}

	protected DLFolder addDLFolder() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		DLFolder dlFolder = _persistence.create(pk);

		dlFolder.setUuid(ServiceTestUtil.randomString());

		dlFolder.setGroupId(ServiceTestUtil.nextLong());

		dlFolder.setCompanyId(ServiceTestUtil.nextLong());

		dlFolder.setUserId(ServiceTestUtil.nextLong());

		dlFolder.setUserName(ServiceTestUtil.randomString());

		dlFolder.setCreateDate(ServiceTestUtil.nextDate());

		dlFolder.setModifiedDate(ServiceTestUtil.nextDate());

		dlFolder.setRepositoryId(ServiceTestUtil.nextLong());

		dlFolder.setMountPoint(ServiceTestUtil.randomBoolean());

		dlFolder.setParentFolderId(ServiceTestUtil.nextLong());

		dlFolder.setName(ServiceTestUtil.randomString());

		dlFolder.setDescription(ServiceTestUtil.randomString());

		dlFolder.setLastPostDate(ServiceTestUtil.nextDate());

		dlFolder.setDefaultFileEntryTypeId(ServiceTestUtil.nextLong());

		dlFolder.setHidden(ServiceTestUtil.randomBoolean());

		dlFolder.setOverrideFileEntryTypes(ServiceTestUtil.randomBoolean());

		dlFolder.setStatus(ServiceTestUtil.nextInt());

		dlFolder.setStatusByUserId(ServiceTestUtil.nextLong());

		dlFolder.setStatusByUserName(ServiceTestUtil.randomString());

		dlFolder.setStatusDate(ServiceTestUtil.nextDate());

		_persistence.update(dlFolder);

		return dlFolder;
	}

	private static Log _log = LogFactoryUtil.getLog(DLFolderPersistenceTest.class);
	private DLFolderPersistence _persistence = (DLFolderPersistence)PortalBeanLocatorUtil.locate(DLFolderPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}