/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.impl.DLFileShortcutModelImpl;

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
public class DLFileShortcutPersistenceTest {
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

		DLFileShortcut dlFileShortcut = _persistence.create(pk);

		Assert.assertNotNull(dlFileShortcut);

		Assert.assertEquals(dlFileShortcut.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		DLFileShortcut newDLFileShortcut = addDLFileShortcut();

		_persistence.remove(newDLFileShortcut);

		DLFileShortcut existingDLFileShortcut = _persistence.fetchByPrimaryKey(newDLFileShortcut.getPrimaryKey());

		Assert.assertNull(existingDLFileShortcut);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addDLFileShortcut();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		DLFileShortcut newDLFileShortcut = _persistence.create(pk);

		newDLFileShortcut.setUuid(ServiceTestUtil.randomString());

		newDLFileShortcut.setGroupId(ServiceTestUtil.nextLong());

		newDLFileShortcut.setCompanyId(ServiceTestUtil.nextLong());

		newDLFileShortcut.setUserId(ServiceTestUtil.nextLong());

		newDLFileShortcut.setUserName(ServiceTestUtil.randomString());

		newDLFileShortcut.setCreateDate(ServiceTestUtil.nextDate());

		newDLFileShortcut.setModifiedDate(ServiceTestUtil.nextDate());

		newDLFileShortcut.setRepositoryId(ServiceTestUtil.nextLong());

		newDLFileShortcut.setFolderId(ServiceTestUtil.nextLong());

		newDLFileShortcut.setToFileEntryId(ServiceTestUtil.nextLong());

		newDLFileShortcut.setTreePath(ServiceTestUtil.randomString());

		newDLFileShortcut.setActive(ServiceTestUtil.randomBoolean());

		newDLFileShortcut.setStatus(ServiceTestUtil.nextInt());

		newDLFileShortcut.setStatusByUserId(ServiceTestUtil.nextLong());

		newDLFileShortcut.setStatusByUserName(ServiceTestUtil.randomString());

		newDLFileShortcut.setStatusDate(ServiceTestUtil.nextDate());

		_persistence.update(newDLFileShortcut);

		DLFileShortcut existingDLFileShortcut = _persistence.findByPrimaryKey(newDLFileShortcut.getPrimaryKey());

		Assert.assertEquals(existingDLFileShortcut.getUuid(),
			newDLFileShortcut.getUuid());
		Assert.assertEquals(existingDLFileShortcut.getFileShortcutId(),
			newDLFileShortcut.getFileShortcutId());
		Assert.assertEquals(existingDLFileShortcut.getGroupId(),
			newDLFileShortcut.getGroupId());
		Assert.assertEquals(existingDLFileShortcut.getCompanyId(),
			newDLFileShortcut.getCompanyId());
		Assert.assertEquals(existingDLFileShortcut.getUserId(),
			newDLFileShortcut.getUserId());
		Assert.assertEquals(existingDLFileShortcut.getUserName(),
			newDLFileShortcut.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingDLFileShortcut.getCreateDate()),
			Time.getShortTimestamp(newDLFileShortcut.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingDLFileShortcut.getModifiedDate()),
			Time.getShortTimestamp(newDLFileShortcut.getModifiedDate()));
		Assert.assertEquals(existingDLFileShortcut.getRepositoryId(),
			newDLFileShortcut.getRepositoryId());
		Assert.assertEquals(existingDLFileShortcut.getFolderId(),
			newDLFileShortcut.getFolderId());
		Assert.assertEquals(existingDLFileShortcut.getToFileEntryId(),
			newDLFileShortcut.getToFileEntryId());
		Assert.assertEquals(existingDLFileShortcut.getTreePath(),
			newDLFileShortcut.getTreePath());
		Assert.assertEquals(existingDLFileShortcut.getActive(),
			newDLFileShortcut.getActive());
		Assert.assertEquals(existingDLFileShortcut.getStatus(),
			newDLFileShortcut.getStatus());
		Assert.assertEquals(existingDLFileShortcut.getStatusByUserId(),
			newDLFileShortcut.getStatusByUserId());
		Assert.assertEquals(existingDLFileShortcut.getStatusByUserName(),
			newDLFileShortcut.getStatusByUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingDLFileShortcut.getStatusDate()),
			Time.getShortTimestamp(newDLFileShortcut.getStatusDate()));
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		DLFileShortcut newDLFileShortcut = addDLFileShortcut();

		DLFileShortcut existingDLFileShortcut = _persistence.findByPrimaryKey(newDLFileShortcut.getPrimaryKey());

		Assert.assertEquals(existingDLFileShortcut, newDLFileShortcut);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchFileShortcutException");
		}
		catch (NoSuchFileShortcutException nsee) {
		}
	}

	@Test
	public void testFindAll() throws Exception {
		try {
			_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				getOrderByComparator());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	protected OrderByComparator getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("DLFileShortcut", "uuid",
			true, "fileShortcutId", true, "groupId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "repositoryId", true, "folderId", true,
			"toFileEntryId", true, "treePath", true, "active", true, "status",
			true, "statusByUserId", true, "statusByUserName", true,
			"statusDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		DLFileShortcut newDLFileShortcut = addDLFileShortcut();

		DLFileShortcut existingDLFileShortcut = _persistence.fetchByPrimaryKey(newDLFileShortcut.getPrimaryKey());

		Assert.assertEquals(existingDLFileShortcut, newDLFileShortcut);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		DLFileShortcut missingDLFileShortcut = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingDLFileShortcut);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = new DLFileShortcutActionableDynamicQuery() {
				@Override
				protected void performAction(Object object) {
					DLFileShortcut dlFileShortcut = (DLFileShortcut)object;

					Assert.assertNotNull(dlFileShortcut);

					count.increment();
				}
			};

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		DLFileShortcut newDLFileShortcut = addDLFileShortcut();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLFileShortcut.class,
				DLFileShortcut.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("fileShortcutId",
				newDLFileShortcut.getFileShortcutId()));

		List<DLFileShortcut> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		DLFileShortcut existingDLFileShortcut = result.get(0);

		Assert.assertEquals(existingDLFileShortcut, newDLFileShortcut);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLFileShortcut.class,
				DLFileShortcut.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("fileShortcutId",
				ServiceTestUtil.nextLong()));

		List<DLFileShortcut> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		DLFileShortcut newDLFileShortcut = addDLFileShortcut();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLFileShortcut.class,
				DLFileShortcut.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"fileShortcutId"));

		Object newFileShortcutId = newDLFileShortcut.getFileShortcutId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("fileShortcutId",
				new Object[] { newFileShortcutId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingFileShortcutId = result.get(0);

		Assert.assertEquals(existingFileShortcutId, newFileShortcutId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLFileShortcut.class,
				DLFileShortcut.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"fileShortcutId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("fileShortcutId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		DLFileShortcut newDLFileShortcut = addDLFileShortcut();

		_persistence.clearCache();

		DLFileShortcutModelImpl existingDLFileShortcutModelImpl = (DLFileShortcutModelImpl)_persistence.findByPrimaryKey(newDLFileShortcut.getPrimaryKey());

		Assert.assertTrue(Validator.equals(
				existingDLFileShortcutModelImpl.getUuid(),
				existingDLFileShortcutModelImpl.getOriginalUuid()));
		Assert.assertEquals(existingDLFileShortcutModelImpl.getGroupId(),
			existingDLFileShortcutModelImpl.getOriginalGroupId());
	}

	protected DLFileShortcut addDLFileShortcut() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		DLFileShortcut dlFileShortcut = _persistence.create(pk);

		dlFileShortcut.setUuid(ServiceTestUtil.randomString());

		dlFileShortcut.setGroupId(ServiceTestUtil.nextLong());

		dlFileShortcut.setCompanyId(ServiceTestUtil.nextLong());

		dlFileShortcut.setUserId(ServiceTestUtil.nextLong());

		dlFileShortcut.setUserName(ServiceTestUtil.randomString());

		dlFileShortcut.setCreateDate(ServiceTestUtil.nextDate());

		dlFileShortcut.setModifiedDate(ServiceTestUtil.nextDate());

		dlFileShortcut.setRepositoryId(ServiceTestUtil.nextLong());

		dlFileShortcut.setFolderId(ServiceTestUtil.nextLong());

		dlFileShortcut.setToFileEntryId(ServiceTestUtil.nextLong());

		dlFileShortcut.setTreePath(ServiceTestUtil.randomString());

		dlFileShortcut.setActive(ServiceTestUtil.randomBoolean());

		dlFileShortcut.setStatus(ServiceTestUtil.nextInt());

		dlFileShortcut.setStatusByUserId(ServiceTestUtil.nextLong());

		dlFileShortcut.setStatusByUserName(ServiceTestUtil.randomString());

		dlFileShortcut.setStatusDate(ServiceTestUtil.nextDate());

		_persistence.update(dlFileShortcut);

		return dlFileShortcut;
	}

	private static Log _log = LogFactoryUtil.getLog(DLFileShortcutPersistenceTest.class);
	private DLFileShortcutPersistence _persistence = (DLFileShortcutPersistence)PortalBeanLocatorUtil.locate(DLFileShortcutPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}