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

import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl;

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
public class DLFileEntryPersistenceTest {
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

		DLFileEntry dlFileEntry = _persistence.create(pk);

		Assert.assertNotNull(dlFileEntry);

		Assert.assertEquals(dlFileEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		DLFileEntry newDLFileEntry = addDLFileEntry();

		_persistence.remove(newDLFileEntry);

		DLFileEntry existingDLFileEntry = _persistence.fetchByPrimaryKey(newDLFileEntry.getPrimaryKey());

		Assert.assertNull(existingDLFileEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addDLFileEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		DLFileEntry newDLFileEntry = _persistence.create(pk);

		newDLFileEntry.setUuid(ServiceTestUtil.randomString());

		newDLFileEntry.setGroupId(ServiceTestUtil.nextLong());

		newDLFileEntry.setCompanyId(ServiceTestUtil.nextLong());

		newDLFileEntry.setUserId(ServiceTestUtil.nextLong());

		newDLFileEntry.setUserName(ServiceTestUtil.randomString());

		newDLFileEntry.setCreateDate(ServiceTestUtil.nextDate());

		newDLFileEntry.setModifiedDate(ServiceTestUtil.nextDate());

		newDLFileEntry.setClassNameId(ServiceTestUtil.nextLong());

		newDLFileEntry.setClassPK(ServiceTestUtil.nextLong());

		newDLFileEntry.setRepositoryId(ServiceTestUtil.nextLong());

		newDLFileEntry.setFolderId(ServiceTestUtil.nextLong());

		newDLFileEntry.setTreePath(ServiceTestUtil.randomString());

		newDLFileEntry.setName(ServiceTestUtil.randomString());

		newDLFileEntry.setExtension(ServiceTestUtil.randomString());

		newDLFileEntry.setMimeType(ServiceTestUtil.randomString());

		newDLFileEntry.setTitle(ServiceTestUtil.randomString());

		newDLFileEntry.setDescription(ServiceTestUtil.randomString());

		newDLFileEntry.setExtraSettings(ServiceTestUtil.randomString());

		newDLFileEntry.setFileEntryTypeId(ServiceTestUtil.nextLong());

		newDLFileEntry.setVersion(ServiceTestUtil.randomString());

		newDLFileEntry.setSize(ServiceTestUtil.nextLong());

		newDLFileEntry.setReadCount(ServiceTestUtil.nextInt());

		newDLFileEntry.setSmallImageId(ServiceTestUtil.nextLong());

		newDLFileEntry.setLargeImageId(ServiceTestUtil.nextLong());

		newDLFileEntry.setCustom1ImageId(ServiceTestUtil.nextLong());

		newDLFileEntry.setCustom2ImageId(ServiceTestUtil.nextLong());

		newDLFileEntry.setManualCheckInRequired(ServiceTestUtil.randomBoolean());

		_persistence.update(newDLFileEntry);

		DLFileEntry existingDLFileEntry = _persistence.findByPrimaryKey(newDLFileEntry.getPrimaryKey());

		Assert.assertEquals(existingDLFileEntry.getUuid(),
			newDLFileEntry.getUuid());
		Assert.assertEquals(existingDLFileEntry.getFileEntryId(),
			newDLFileEntry.getFileEntryId());
		Assert.assertEquals(existingDLFileEntry.getGroupId(),
			newDLFileEntry.getGroupId());
		Assert.assertEquals(existingDLFileEntry.getCompanyId(),
			newDLFileEntry.getCompanyId());
		Assert.assertEquals(existingDLFileEntry.getUserId(),
			newDLFileEntry.getUserId());
		Assert.assertEquals(existingDLFileEntry.getUserName(),
			newDLFileEntry.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingDLFileEntry.getCreateDate()),
			Time.getShortTimestamp(newDLFileEntry.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingDLFileEntry.getModifiedDate()),
			Time.getShortTimestamp(newDLFileEntry.getModifiedDate()));
		Assert.assertEquals(existingDLFileEntry.getClassNameId(),
			newDLFileEntry.getClassNameId());
		Assert.assertEquals(existingDLFileEntry.getClassPK(),
			newDLFileEntry.getClassPK());
		Assert.assertEquals(existingDLFileEntry.getRepositoryId(),
			newDLFileEntry.getRepositoryId());
		Assert.assertEquals(existingDLFileEntry.getFolderId(),
			newDLFileEntry.getFolderId());
		Assert.assertEquals(existingDLFileEntry.getTreePath(),
			newDLFileEntry.getTreePath());
		Assert.assertEquals(existingDLFileEntry.getName(),
			newDLFileEntry.getName());
		Assert.assertEquals(existingDLFileEntry.getExtension(),
			newDLFileEntry.getExtension());
		Assert.assertEquals(existingDLFileEntry.getMimeType(),
			newDLFileEntry.getMimeType());
		Assert.assertEquals(existingDLFileEntry.getTitle(),
			newDLFileEntry.getTitle());
		Assert.assertEquals(existingDLFileEntry.getDescription(),
			newDLFileEntry.getDescription());
		Assert.assertEquals(existingDLFileEntry.getExtraSettings(),
			newDLFileEntry.getExtraSettings());
		Assert.assertEquals(existingDLFileEntry.getFileEntryTypeId(),
			newDLFileEntry.getFileEntryTypeId());
		Assert.assertEquals(existingDLFileEntry.getVersion(),
			newDLFileEntry.getVersion());
		Assert.assertEquals(existingDLFileEntry.getSize(),
			newDLFileEntry.getSize());
		Assert.assertEquals(existingDLFileEntry.getReadCount(),
			newDLFileEntry.getReadCount());
		Assert.assertEquals(existingDLFileEntry.getSmallImageId(),
			newDLFileEntry.getSmallImageId());
		Assert.assertEquals(existingDLFileEntry.getLargeImageId(),
			newDLFileEntry.getLargeImageId());
		Assert.assertEquals(existingDLFileEntry.getCustom1ImageId(),
			newDLFileEntry.getCustom1ImageId());
		Assert.assertEquals(existingDLFileEntry.getCustom2ImageId(),
			newDLFileEntry.getCustom2ImageId());
		Assert.assertEquals(existingDLFileEntry.getManualCheckInRequired(),
			newDLFileEntry.getManualCheckInRequired());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		DLFileEntry newDLFileEntry = addDLFileEntry();

		DLFileEntry existingDLFileEntry = _persistence.findByPrimaryKey(newDLFileEntry.getPrimaryKey());

		Assert.assertEquals(existingDLFileEntry, newDLFileEntry);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchFileEntryException");
		}
		catch (NoSuchFileEntryException nsee) {
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

	@Test
	public void testFilterFindByGroupId() throws Exception {
		try {
			_persistence.filterFindByGroupId(0, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, getOrderByComparator());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	protected OrderByComparator getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("DLFileEntry", "uuid", true,
			"fileEntryId", true, "groupId", true, "companyId", true, "userId",
			true, "userName", true, "createDate", true, "modifiedDate", true,
			"classNameId", true, "classPK", true, "repositoryId", true,
			"folderId", true, "treePath", true, "name", true, "extension",
			true, "mimeType", true, "title", true, "description", true,
			"extraSettings", true, "fileEntryTypeId", true, "version", true,
			"size", true, "readCount", true, "smallImageId", true,
			"largeImageId", true, "custom1ImageId", true, "custom2ImageId",
			true, "manualCheckInRequired", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		DLFileEntry newDLFileEntry = addDLFileEntry();

		DLFileEntry existingDLFileEntry = _persistence.fetchByPrimaryKey(newDLFileEntry.getPrimaryKey());

		Assert.assertEquals(existingDLFileEntry, newDLFileEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		DLFileEntry missingDLFileEntry = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingDLFileEntry);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = new DLFileEntryActionableDynamicQuery() {
				@Override
				protected void performAction(Object object) {
					DLFileEntry dlFileEntry = (DLFileEntry)object;

					Assert.assertNotNull(dlFileEntry);

					count.increment();
				}
			};

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		DLFileEntry newDLFileEntry = addDLFileEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLFileEntry.class,
				DLFileEntry.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("fileEntryId",
				newDLFileEntry.getFileEntryId()));

		List<DLFileEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		DLFileEntry existingDLFileEntry = result.get(0);

		Assert.assertEquals(existingDLFileEntry, newDLFileEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLFileEntry.class,
				DLFileEntry.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("fileEntryId",
				ServiceTestUtil.nextLong()));

		List<DLFileEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		DLFileEntry newDLFileEntry = addDLFileEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLFileEntry.class,
				DLFileEntry.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("fileEntryId"));

		Object newFileEntryId = newDLFileEntry.getFileEntryId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("fileEntryId",
				new Object[] { newFileEntryId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingFileEntryId = result.get(0);

		Assert.assertEquals(existingFileEntryId, newFileEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLFileEntry.class,
				DLFileEntry.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("fileEntryId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("fileEntryId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		DLFileEntry newDLFileEntry = addDLFileEntry();

		_persistence.clearCache();

		DLFileEntryModelImpl existingDLFileEntryModelImpl = (DLFileEntryModelImpl)_persistence.findByPrimaryKey(newDLFileEntry.getPrimaryKey());

		Assert.assertTrue(Validator.equals(
				existingDLFileEntryModelImpl.getUuid(),
				existingDLFileEntryModelImpl.getOriginalUuid()));
		Assert.assertEquals(existingDLFileEntryModelImpl.getGroupId(),
			existingDLFileEntryModelImpl.getOriginalGroupId());

		Assert.assertEquals(existingDLFileEntryModelImpl.getGroupId(),
			existingDLFileEntryModelImpl.getOriginalGroupId());
		Assert.assertEquals(existingDLFileEntryModelImpl.getFolderId(),
			existingDLFileEntryModelImpl.getOriginalFolderId());
		Assert.assertTrue(Validator.equals(
				existingDLFileEntryModelImpl.getName(),
				existingDLFileEntryModelImpl.getOriginalName()));

		Assert.assertEquals(existingDLFileEntryModelImpl.getGroupId(),
			existingDLFileEntryModelImpl.getOriginalGroupId());
		Assert.assertEquals(existingDLFileEntryModelImpl.getFolderId(),
			existingDLFileEntryModelImpl.getOriginalFolderId());
		Assert.assertTrue(Validator.equals(
				existingDLFileEntryModelImpl.getTitle(),
				existingDLFileEntryModelImpl.getOriginalTitle()));
	}

	protected DLFileEntry addDLFileEntry() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		DLFileEntry dlFileEntry = _persistence.create(pk);

		dlFileEntry.setUuid(ServiceTestUtil.randomString());

		dlFileEntry.setGroupId(ServiceTestUtil.nextLong());

		dlFileEntry.setCompanyId(ServiceTestUtil.nextLong());

		dlFileEntry.setUserId(ServiceTestUtil.nextLong());

		dlFileEntry.setUserName(ServiceTestUtil.randomString());

		dlFileEntry.setCreateDate(ServiceTestUtil.nextDate());

		dlFileEntry.setModifiedDate(ServiceTestUtil.nextDate());

		dlFileEntry.setClassNameId(ServiceTestUtil.nextLong());

		dlFileEntry.setClassPK(ServiceTestUtil.nextLong());

		dlFileEntry.setRepositoryId(ServiceTestUtil.nextLong());

		dlFileEntry.setFolderId(ServiceTestUtil.nextLong());

		dlFileEntry.setTreePath(ServiceTestUtil.randomString());

		dlFileEntry.setName(ServiceTestUtil.randomString());

		dlFileEntry.setExtension(ServiceTestUtil.randomString());

		dlFileEntry.setMimeType(ServiceTestUtil.randomString());

		dlFileEntry.setTitle(ServiceTestUtil.randomString());

		dlFileEntry.setDescription(ServiceTestUtil.randomString());

		dlFileEntry.setExtraSettings(ServiceTestUtil.randomString());

		dlFileEntry.setFileEntryTypeId(ServiceTestUtil.nextLong());

		dlFileEntry.setVersion(ServiceTestUtil.randomString());

		dlFileEntry.setSize(ServiceTestUtil.nextLong());

		dlFileEntry.setReadCount(ServiceTestUtil.nextInt());

		dlFileEntry.setSmallImageId(ServiceTestUtil.nextLong());

		dlFileEntry.setLargeImageId(ServiceTestUtil.nextLong());

		dlFileEntry.setCustom1ImageId(ServiceTestUtil.nextLong());

		dlFileEntry.setCustom2ImageId(ServiceTestUtil.nextLong());

		dlFileEntry.setManualCheckInRequired(ServiceTestUtil.randomBoolean());

		_persistence.update(dlFileEntry);

		return dlFileEntry;
	}

	private static Log _log = LogFactoryUtil.getLog(DLFileEntryPersistenceTest.class);
	private DLFileEntryPersistence _persistence = (DLFileEntryPersistence)PortalBeanLocatorUtil.locate(DLFileEntryPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}