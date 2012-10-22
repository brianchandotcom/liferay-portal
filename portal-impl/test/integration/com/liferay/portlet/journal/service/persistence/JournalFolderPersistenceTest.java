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

package com.liferay.portlet.journal.service.persistence;

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

import com.liferay.portlet.journal.NoSuchFolderException;
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.journal.model.impl.JournalFolderModelImpl;

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
public class JournalFolderPersistenceTest {
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

		JournalFolder journalFolder = _persistence.create(pk);

		Assert.assertNotNull(journalFolder);

		Assert.assertEquals(journalFolder.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		JournalFolder newJournalFolder = addJournalFolder();

		_persistence.remove(newJournalFolder);

		JournalFolder existingJournalFolder = _persistence.fetchByPrimaryKey(newJournalFolder.getPrimaryKey());

		Assert.assertNull(existingJournalFolder);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addJournalFolder();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		JournalFolder newJournalFolder = _persistence.create(pk);

		newJournalFolder.setUuid(ServiceTestUtil.randomString());

		newJournalFolder.setGroupId(ServiceTestUtil.nextLong());

		newJournalFolder.setCompanyId(ServiceTestUtil.nextLong());

		newJournalFolder.setUserId(ServiceTestUtil.nextLong());

		newJournalFolder.setUserName(ServiceTestUtil.randomString());

		newJournalFolder.setCreateDate(ServiceTestUtil.nextDate());

		newJournalFolder.setModifiedDate(ServiceTestUtil.nextDate());

		newJournalFolder.setParentFolderId(ServiceTestUtil.nextLong());

		newJournalFolder.setName(ServiceTestUtil.randomString());

		newJournalFolder.setDescription(ServiceTestUtil.randomString());

		_persistence.update(newJournalFolder);

		JournalFolder existingJournalFolder = _persistence.findByPrimaryKey(newJournalFolder.getPrimaryKey());

		Assert.assertEquals(existingJournalFolder.getUuid(),
			newJournalFolder.getUuid());
		Assert.assertEquals(existingJournalFolder.getFolderId(),
			newJournalFolder.getFolderId());
		Assert.assertEquals(existingJournalFolder.getGroupId(),
			newJournalFolder.getGroupId());
		Assert.assertEquals(existingJournalFolder.getCompanyId(),
			newJournalFolder.getCompanyId());
		Assert.assertEquals(existingJournalFolder.getUserId(),
			newJournalFolder.getUserId());
		Assert.assertEquals(existingJournalFolder.getUserName(),
			newJournalFolder.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingJournalFolder.getCreateDate()),
			Time.getShortTimestamp(newJournalFolder.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingJournalFolder.getModifiedDate()),
			Time.getShortTimestamp(newJournalFolder.getModifiedDate()));
		Assert.assertEquals(existingJournalFolder.getParentFolderId(),
			newJournalFolder.getParentFolderId());
		Assert.assertEquals(existingJournalFolder.getName(),
			newJournalFolder.getName());
		Assert.assertEquals(existingJournalFolder.getDescription(),
			newJournalFolder.getDescription());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		JournalFolder newJournalFolder = addJournalFolder();

		JournalFolder existingJournalFolder = _persistence.findByPrimaryKey(newJournalFolder.getPrimaryKey());

		Assert.assertEquals(existingJournalFolder, newJournalFolder);
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
		JournalFolder newJournalFolder = addJournalFolder();

		JournalFolder existingJournalFolder = _persistence.fetchByPrimaryKey(newJournalFolder.getPrimaryKey());

		Assert.assertEquals(existingJournalFolder, newJournalFolder);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		JournalFolder missingJournalFolder = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingJournalFolder);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		JournalFolder newJournalFolder = addJournalFolder();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(JournalFolder.class,
				JournalFolder.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("folderId",
				newJournalFolder.getFolderId()));

		List<JournalFolder> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		JournalFolder existingJournalFolder = result.get(0);

		Assert.assertEquals(existingJournalFolder, newJournalFolder);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(JournalFolder.class,
				JournalFolder.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("folderId",
				ServiceTestUtil.nextLong()));

		List<JournalFolder> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		JournalFolder newJournalFolder = addJournalFolder();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(JournalFolder.class,
				JournalFolder.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("folderId"));

		Object newFolderId = newJournalFolder.getFolderId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("folderId",
				new Object[] { newFolderId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingFolderId = result.get(0);

		Assert.assertEquals(existingFolderId, newFolderId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(JournalFolder.class,
				JournalFolder.class.getClassLoader());

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

		JournalFolder newJournalFolder = addJournalFolder();

		_persistence.clearCache();

		JournalFolderModelImpl existingJournalFolderModelImpl = (JournalFolderModelImpl)_persistence.findByPrimaryKey(newJournalFolder.getPrimaryKey());

		Assert.assertTrue(Validator.equals(
				existingJournalFolderModelImpl.getUuid(),
				existingJournalFolderModelImpl.getOriginalUuid()));
		Assert.assertEquals(existingJournalFolderModelImpl.getGroupId(),
			existingJournalFolderModelImpl.getOriginalGroupId());

		Assert.assertEquals(existingJournalFolderModelImpl.getGroupId(),
			existingJournalFolderModelImpl.getOriginalGroupId());
		Assert.assertTrue(Validator.equals(
				existingJournalFolderModelImpl.getName(),
				existingJournalFolderModelImpl.getOriginalName()));

		Assert.assertEquals(existingJournalFolderModelImpl.getGroupId(),
			existingJournalFolderModelImpl.getOriginalGroupId());
		Assert.assertEquals(existingJournalFolderModelImpl.getParentFolderId(),
			existingJournalFolderModelImpl.getOriginalParentFolderId());
		Assert.assertTrue(Validator.equals(
				existingJournalFolderModelImpl.getName(),
				existingJournalFolderModelImpl.getOriginalName()));
	}

	protected JournalFolder addJournalFolder() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		JournalFolder journalFolder = _persistence.create(pk);

		journalFolder.setUuid(ServiceTestUtil.randomString());

		journalFolder.setGroupId(ServiceTestUtil.nextLong());

		journalFolder.setCompanyId(ServiceTestUtil.nextLong());

		journalFolder.setUserId(ServiceTestUtil.nextLong());

		journalFolder.setUserName(ServiceTestUtil.randomString());

		journalFolder.setCreateDate(ServiceTestUtil.nextDate());

		journalFolder.setModifiedDate(ServiceTestUtil.nextDate());

		journalFolder.setParentFolderId(ServiceTestUtil.nextLong());

		journalFolder.setName(ServiceTestUtil.randomString());

		journalFolder.setDescription(ServiceTestUtil.randomString());

		_persistence.update(journalFolder);

		return journalFolder;
	}

	private static Log _log = LogFactoryUtil.getLog(JournalFolderPersistenceTest.class);
	private JournalFolderPersistence _persistence = (JournalFolderPersistence)PortalBeanLocatorUtil.locate(JournalFolderPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}